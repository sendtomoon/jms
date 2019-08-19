var detailStatusArray = [];

$(function() {
	JY.Dict.setSelect("selectisValid","SCM_INVENTORY_STATUS",2,"全部");
	JY.Dict.setSelect("selProdCate","SCM_PRO_CATE",1);
	JY.Dict.setSelect("selJewelryType","SCM_PRO_CATE_JEWELRY",1);
	JY.Dict.setSelect("selCategory","SCM_PRO_CATE",1);
	JY.Dict.setSelect("selGroup","SCM_PRO_CATE_JEWELRY",1);
	
	setBaseData();
	getbaseList(1);
	initOrg();
	
	// 得到盘点详情的所有状态值
	detailStatusArray = gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_INVENTORYDETAIL_STATUS",detailStatusArray);
	
	// 更改查询条件按回车键触发查询按钮的点击事件
	$("#InventoryForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode == 13) {
			 search();
		 } 
	});
	
	// 新增按钮事件
	$('#addBtn').on('click', function(e) {
		e.preventDefault();
		cleanAdd();
		$.jy.dropTree.checkNode("productOrg","productOrgName", curUser.orgId);
		$("#ScmInventoryForm input[id='productOrgId']").val(curUser.orgId);
		changeOrg(curUser.orgId, 'selectWarehouse');
		editInfo({id:"ScmInventoryDiv",title:"新建盘点计划",height:"720",width:"1024",savefn:function(type){
			var that =$(this);
			if(JY.Validate.form("ScmInventoryForm") && $('#ScmInventoryAdd tbody tr').length>0){
				var executeDate = JY.Object.notEmpty(new Date($("#ScmInventoryForm input[name='executeTime']").val()).getTime());
				var currentDate = JY.Object.notEmpty(new Date().Format("yyyy/MM/dd"));
				currentDate = new Date(currentDate).getTime();
				if (executeDate < currentDate) {
					JY.Model.info("盘点执行日期不能早于今天！");
				} else {
					var json_data="";
			 	    var last_index=$('#ScmInventoryAdd tbody tr').length-1;
			 	    $('#ScmInventoryAdd tbody tr').each(function(element,index){
			 	        var data='{"code":"'+$(this).find("input[name=code]").val()+'","name":"'+$(this).find("input[name=name]").val()+'","numbers":'+$(this).find("input[name=num]").val()+',"weight":'+$(this).find("input[name=weight]").val()+'}';	
				 	    if($(this).index()==last_index){
				        	json_data+=data;
				        }else{
				        	json_data+=data+',';
				        };
				    });
			        json_data='['+json_data+']';
			        var inventoryData = "&whouseId="+$('#selectWarehouse').val()+"&locationId="+$('#selectWarehouseLocation').val()
		        	+"&categoryId="+$('#selProdCate select').val()+"&groupId="+$('#selJewelryType select').val()+"&orgId="+$('#productOrgId').val()
		        	+"&remark="+$("#ScmInventoryForm textarea[name='note']").val()+"&executeTime="+$("#ScmInventoryForm input[name='executeTime']").val()
		        	+"&status="+type;
			        $.ajax({type:"POST",url:jypath+"/scm/inventory/add",data: "myData="+json_data+inventoryData,dataType:"text",success:function(data,textStatus){ 
					   var json_obj=eval('('+data+')');
					   if(json_obj.res==1){
			        		that.dialog("close");      
			        		JY.Model.info(json_obj.resMsg,function(){search();});
			        	}else{
			        		 JY.Model.error(json_obj.resMsg);
			        	}     	
			         }
			       });
				}
			} else if (JY.Validate.form("ScmInventoryForm") && !($('#ScmInventoryAdd tbody tr').length > 0)) {
				JY.Model.info("未检索到商品时不能保存，请核对后再试！");
			}
		}})	
	});
	
	// 下发按钮事件
	$('#publishBtn').on('click', function(e) {
		var chks =[];    
		$('#InventoryTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("您只能选择一条内容!"); 
		} else if ($('#InventoryTable input[name="ids"]:checked').parent().parent().parent().find('span[name="statusValue"]').text() == '草稿') {
			publish(chks[0]);
		} else {
			JY.Model.info("只能下发状态为草稿的盘点计划!"); 
		}
	});
	
	// 删除按钮
	$('#deleteBtn').on('click', function() {
		var chks = [];
		var values = [];
		var ids = [];
		$('#InventoryTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());
			values.push($(this).parent().parent().parent().find('span[name="statusValue"]').text());
			ids.push($(this).parent().parent().parent().find('input[name="ids"]').val());
		});
		if(chks.length == 0) {
			JY.Model.info("您没有选择任何内容!"); 
		} else {
			if (values.indexOf("草稿") != -1 || values.indexOf("盘点中") != -1 || values.indexOf("待盘点") != -1) {
				JY.Model.info("您只能删除状态为已盘点的盘点计划!");
			} else {
				delByBtn(ids);
			}
		}
	});
	
	// 表单的列出盘点商品的点击事件
	$('#queryProductBtn').click(function() {
		
		var orgid = $('#productOrgId').val();
		var warehouseid = $('#selectWarehouse').val();
		var locationid = $('#selectWarehouseLocation').val();
		var cateid = $('#selProdCate select').val();
		var catejewelrytype = $('#selJewelryType select').val();
		
		if (JY.Object.notNull(orgid) && JY.Object.notNull(warehouseid) && JY.Object.notNull(locationid)) {
			$.ajax({
				type: 'POST',
                url: jypath +'/scm/product/findForInventory',
                data: {orgid:orgid,  warehouseid:warehouseid, locationid:locationid, cateid:cateid, catejewelrytype:catejewelrytype},
                dataType: 'json',
                success: function(data, textStatus) {
                    if (data.res == 1) {
                    	$("#ScmInventoryAdd tbody").html("");
    					$("#ScmInventoryAdd tfoot").html("");
    					commCode(data.obj);
                    } else {
                    	$("#ScmInventoryAdd tbody").html("");
    					$("#ScmInventoryAdd tfoot").html("");
                        if (JY.Object.notNull(data.resMsg))
                            JY.Model.info(data.resMsg);
                    }
                }
			});
		} else {
			JY.Model.info("机构、仓库、仓位为必选项，请选择后再检索！");
		}
	});
	
	//时间渲染
	$("#dateRender").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd ',
		changeYear: true,
		changeMonth:true,
		onSelect: function(dateText, inst) {
			if(new Date(dateText) < new Date((new Date()).Format("yyyy/MM/dd"))){
				$(this).val('');
				$(this).tips({side: 1,msg: "盘点执行日期不能早于今天！",bg: '#FF2D2D',time: 1});
				return false;
			}
		}
	});
	$("#dateBegin").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd',
		changeYear: true,
		changeMonth:true,
		onSelect:function(dateText, inst) { }
	});
	$("#dateEnd").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd',
		changeYear: true,
		changeMonth:true,
		onSelect:function(dateText, inst) { }
	});
	
	// 仓库、仓位、货类、货组的 select 标签内容改变事件
	$('#selectWarehouse, #selectWarehouseLocation, #selProdCate select[name="cateId"], #selJewelryType select[name="cateJewelryId"]').change(function() {
		$("#ScmInventoryAdd tbody").html("");
		$("#ScmInventoryAdd tfoot").html("");
	});
	
})

function getbaseList(init) {
	if(init == 1) {
		$("#InventoryForm .pageNum").val(1);
	}
	JY.Model.loading();
	JY.Ajax.doRequest("InventoryForm",jypath +'/scm/inventory/findByPage',null,function(data) {
		 $("#InventoryTable tbody").empty();
        	 var obj = data.obj;
        	 var list = obj.list;
        	 var results = list.results;
        	 var permitBtn = obj.permitBtn;
         	 var pageNum = list.pageNum, pageSize = list.pageSize, totalRecord = list.totalRecord;
        	 var html = "";
    		 if(null != results && results.length > 0) {
        		 var leng = (pageNum-1) * pageSize;//计算序号
        		 for(var i = 0;i < results.length;i++) {
            		 var l = results[i];
            		 html += "<tr>";
            		 html += "<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
            		 html += "<td class='center hidden-480'>"+(i+leng+1)+"</td>";
            		 html += "<td class='center hidden-480'><a style='cursor:pointer;' onclick='view(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.inventoryNo)+"</a></td>";
            		 if (l.categoryName == null) {
            			 html += "<td class='center hidden-480'>"+"所有"+"</td>";
            		 } else {
            			 html += "<td class='center hidden-480'>"+JY.Object.notNumber(l.categoryName)+"</td>";
            		 }
            		 if (l.groupName == null) {
            			 html += "<td class='center hidden-480'>"+"所有"+"</td>";
            		 } else {
            			 html += "<td class='center hidden-480'>"+JY.Object.notNumber(l.groupName)+"</td>";
            		 }
            		 html += "<td class='center hidden-480'>"+JY.Object.notNumber(l.orgName)+"</td>";
            		 html += "<td class='center hidden-480'>"+JY.Object.notNumber(l.whouseName)+"</td>";
            		 html += "<td class='center hidden-480'>"+JY.Object.notNumber(l.locationName)+"</td>";
            		 if(l.status == "0") {
            			 html += "<td class='center hidden-480'><span name='statusValue' class='label label-sm arrowed-in'>草稿</span></td>";
            		 } else if(l.status == "1") {
            			 html += "<td class='center hidden-480'><span name='statusValue' class='label label-sm arrowed-in'>待盘点</span></td>";
            		 } else if(l.status == "2") {
            			 html += "<td class='center hidden-480'><span name='statusValue' class='label label-sm label-success'>盘点中</span></td>";
            		 } else if(l.status == "3") {
            			 html += "<td class='center hidden-480'><span name='statusValue' class='label label-sm label-success'>已完成</span></td>";
            		 }
            		 html += "<td class='center hidden-480'>"+JY.Object.notEmpty(l.createName)+"</td>";
            		 html += "<td class='center hidden-480'>"+JY.Object.notEmpty(new Date(l.executeTime).Format('yyyy-MM-dd'))+"</td>";
            		 if(l.status == 0){
            			 html += JY.Tags.setFunction(l.id,permitBtn);
            		 } else {
            			 html += JY.Tags.setFunction1(l.id,permitBtn,'edit,del,');
            		 }
            		 html += "</tr>";		 
            	 } 
        		 $("#InventoryTable tbody").append(html);
        		 JY.Page.setPage("InventoryForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html += "<tr><td colspan='13' class='center'>没有相关数据</td></tr>";
        		$("#InventoryTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
    		 JY.Model.loadingClose();
	 });
}

function cleanAdd(){
	$('#ScmInventoryForm').find('#productOrg_clear').show();
	$('#ScmInventoryForm').find('#queryProductBtn').show();
	$('#ScmInventoryForm').find('.btnClass').show();
	$('#ScmInventoryForm').find('.ui-datepicker-trigger').show();
	$('#ScmInventoryForm').find('input,select,textarea').removeAttr('disabled');
	JY.Tags.cleanForm("ScmInventoryForm");
	$("#ScmInventoryForm input[name='inventoryNo']").val("");
	$("#ScmInventoryForm input[id='productOrgName']").val("");
	$("#ScmInventoryForm input[id='productOrgId']").val("");
	$("#ScmInventoryForm select[id='selectWarehouse']").html("");
	$("#ScmInventoryForm select[id='selectWarehouseLocation']").html("");
	$("#ScmInventoryForm select[name='cateId']").val(0);
	$("#ScmInventoryForm select[name='cateJewelryId']").val(0);
	$("#ScmInventoryAdd tbody").html("");
	$("#ScmInventoryAdd tfoot").html("");
	$("#ScmInventoryForm span[id$='createUser']").text("");
	$("#ScmInventoryForm span[id$='createTime']").text("");
	$("#ScmInventoryForm span[id$='updateUser']").text("");
	$("#ScmInventoryForm span[id$='updateTime']").text("");
	$("#ScmInventoryForm span[id$='checkUser']").text("");
	$("#ScmInventoryForm span[id$='checkTime']").text("");
}

function viewInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
}

function baseInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
    	buttons:[{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;下发","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},
    	         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}
function editInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
    	buttons:[{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存为草稿","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
    	         {html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存并下发","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},
    	         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}

// 机构树初始化
function initOrg(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getOrgTree',{},function(data){
		$.jy.dropTree.init({
			rootId:"productOrg",
			displayId:"productOrgName",
			data:data.obj,
			dataFormat:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'},
			isExpand:true,
			resetFn:function(){
				$("#ScmInventoryForm #productOrgName").val('');
				$("#ScmInventoryForm #productOrgId").val('');
			},
			clickFn:function(node){
				$("#ScmInventoryForm #productOrgId").val(node.id);
				changeOrg(node.id, 'selectWarehouse');
				$("#ScmInventoryAdd tbody").html("");
				$("#ScmInventoryAdd tfoot").html("");
			}
		});
	});
}
// 更改查询条件按回车键触发查询按钮的点击事件
function search(){
	$("#searchBtn").trigger("click");
}
// 更改选中的组织的事件
function changeOrg(obj, selectWarehouse, warehouseId){
	var va = obj;
	var selectWarehouseObj = $('#'+selectWarehouse+'');
	selectWarehouseObj.empty();
	JY.Ajax.doRequest(null,jypath +'/scm/warehousing/selectWarehousingByOrgId',{id:va},function(data){
	    var obj=data.obj;
	    var html = '<option value="">请选择</option>';
	    $('#selectWarehouseLocation').html("");
		for (var i = 0; i < obj.length; i++) {
			html += '<option value="'+obj[i].key+'">'+obj[i].value+'</option>';
        }
		selectWarehouseObj.append(html);
		if(undefined != warehouseId) {
			$('#'+selectWarehouse+'').val(warehouseId);
		}
	});
	
}
// 更改选中的组织的同步事件
function syncChangeOrg(obj, selectWarehouse, warehouseId){
	var va = obj;
	var selectWarehouseObj = $('#'+selectWarehouse+'');
	$.ajax({
		type: "post",
		url: jypath +'/scm/warehousing/selectWarehousingByOrgId',
		data: {id:va},
		async: false,
		dataType: 'json',
		success: function(data){
			var obj=data.obj;
		    var html = '<option value="">请选择</option>';
		    $('#selectWarehouseLocation').html("");
			for (var i = 0; i < obj.length; i++) {
				html += '<option value="'+obj[i].key+'">'+obj[i].value+'</option>';
	        }
			selectWarehouseObj.append(html);
			if(undefined != warehouseId) {
				$('#'+selectWarehouse+'').val(warehouseId);
			}
		}
	});
}
// 更改选中的仓库的事件
function changeWarehouse(obj, selectWarehouseLocation, warehouseLocationId){
	var va = obj;
	var selectWarehouseLocationObj = $('#'+selectWarehouseLocation+'');
	selectWarehouseLocationObj.empty();
	JY.Ajax.doRequest(null,jypath +'/scm/warehousing/selectWarehousingLocation',{id:va},function(data){
	    var obj=data.obj;
	    var html ='<option value="">请选择</option>';
		for (var i = 0; i < obj.length; i++) {
			html+='<option value="'+obj[i].key+'">'+obj[i].value+'</option>';
        }
		selectWarehouseLocationObj.append(html);
		if(undefined != warehouseLocationId) {
			$('#'+selectWarehouseLocation+'').val(warehouseLocationId);
		}
	});
}
// 更改选中的仓库的同步事件
function syncChangeWarehouse(obj, selectWarehouseLocation, warehouseLocationId){
	var va = obj;
	var selectWarehouseLocationObj = $('#'+selectWarehouseLocation+'');
	$.ajax({
		type: "post",
		url: jypath +'/scm/warehousing/selectWarehousingLocation',
		data: {id:va},
		async: false,
		dataType: 'json',
		success: function(data){
			var l=data.obj;
		    var html ='<option value="">请选择</option>';
			for (var i = 0; i < l.length; i++) {
				html+='<option value="'+l[i].key+'">'+l[i].value+'</option>';
	        }
			selectWarehouseLocationObj.append(html);
			if(undefined != warehouseLocationId) {
				$('#'+selectWarehouseLocation+'').val(warehouseLocationId);
			}
		}
	});
}
function commCode(obj){
	var list=[];
	var html="";
	if(obj!=null){
		for(var i=0;i<obj.length;i++){ 
			$("#ScmInventoryAdd input[name$='code']").each(function(){  
				list.push($(this).val());
			});
			if(list.indexOf(obj[i].code) == -1){
				 html+="<tr>";
				 html+="<td class='center'><label><input type='checkbox' name='productId' value='"+JY.Object.notEmpty(obj[i].id)+"' class='ace targetClass' /><span class='lbl'></span></label></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='code' readonly  value='"+JY.Object.notEmpty(obj[i].code)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='name' readonly value='"+JY.Object.notEmpty(obj[i].name)+"' style='width:100%;height:30px;border:none;'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='num' onkeyup='JY.Validate.limitNum(this)' value='"+JY.Object.notNumber(obj[i].count)+"' style='width:100%;height:30px;border:none;' readonly/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='weight' onkeyup='JY.Validate.limitDouble(this)'  value='"+JY.Object.notNumber(obj[i].totalWeight)+"'  style='width:100%;height:30px;border:none;' readonly/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='chkNum' onkeyup='JY.Validate.limitNum(this)' value='"+JY.Object.notNumber(obj[i].chkNum)+"' style='width:100%;height:30px;border:none;' readonly/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='chkWeight' onkeyup='JY.Validate.limitDouble(this)'  value='"+JY.Object.notNumber(obj[i].chkWeight)+"'  style='width:100%;height:30px;border:none;' readonly/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='code' readonly  value='"+JY.Object.notEmpty(obj[i].diff)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='name' readonly value='"+JY.Object.notEmpty(obj[i].diffRemark)+"' style='width:100%;height:30px;border:none;'/></td>";
				 html+="</tr>";
			}else{
				JY.Model.info("商品条码已存在，请核对后再试!");
			}	
		}
	    $("#ScmInventoryAdd tbody").append(html);
	    commFoot();
	}
}

function commFoot(){
	$("#ScmInventoryAdd tfoot").html("");
	var num = 0, weight = 0, chkNum = 0, chkWeight = 0;
	$('#ScmInventoryAdd').find('input[name="num"]').each(function(element,index){
		if('' != $(this).val()) {
			num += parseFloat($(this).val());
		}
	});
	$('#ScmInventoryAdd').find('input[name="weight"]').each(function(element,index){
		if('' != $(this).val()) {
			weight += parseFloat($(this).val());
		}
	});
	$('#ScmInventoryAdd').find('input[name="chkNum"]').each(function(element,index){
		if('' != $(this).val()) {
			chkNum += parseFloat($(this).val());
		}
	});
	$('#ScmInventoryAdd').find('input[name="chkWeight"]').each(function(element,index){
		if('' != $(this).val()) {
			chkWeight += parseFloat($(this).val());
		}
	});
	var foot = "";
	foot += "<tr>";
	foot += "<td class='center'>合计</td>";
	foot += "<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	foot += "<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	foot += "<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+num+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	foot += "<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(weight).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	foot += "<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+chkNum+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	foot += "<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(chkWeight).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	foot += "<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	foot += "<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	foot += "</tr>";	
	$("#ScmInventoryAdd tfoot").append(foot);
}

function del(id){
	JY.Model.confirm("确认删除吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/inventory/del',{id:id},function(data){
			JY.Model.info(data.resMsg,function(){
				search();
			});
		});
	});
}

function delByBtn(obj) {
	JY.Model.confirm("确认删除吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/inventory/delete',{ids: obj.toString()},function(data){
			JY.Model.info(data.resMsg,function(){
				search();
			});
		});
	});
}

function edit(id){
	cleanAdd();
	JY.Ajax.doRequest(null,jypath +'/scm/inventory/view',{id:id},function(data){
		setUpdateForm(data);
		var serializeFormData = JSON.stringify(JY.Object.serialize($('#ScmInventoryForm')));
		
		editInfo({id:"ScmInventoryDiv",title:"修改盘点计划",height:"720",width:"1024",savefn:function(type){
			var that =$(this);
			
			if (type == 0) {
				var executeDate = JY.Object.notEmpty(new Date($("#ScmInventoryForm input[name='executeTime']").val()).getTime());
				var currentDate = JY.Object.notEmpty(new Date().Format("yyyy/MM/dd"));
				currentDate = new Date(currentDate).getTime();
				
				if (JSON.stringify(JY.Object.serialize($('#ScmInventoryForm'))) === serializeFormData) {
					JY.Model.info("您未进行任何修改!");
				} else {
					if(JY.Validate.form("ScmInventoryForm") && $('#ScmInventoryAdd tbody tr').length <= 0) {
						JY.Model.info("您必须先检索才能保存!");
					} else if (JY.Validate.form("ScmInventoryForm") && $('#ScmInventoryAdd tbody tr').length > 0 && executeDate < currentDate) {
						JY.Model.info("盘点执行日期不能早于今天！");
					} else if (JY.Validate.form("ScmInventoryForm") && $('#ScmInventoryAdd tbody tr').length > 0 && executeDate >= currentDate) {
						var json_data="";
				 	    var last_index=$('#ScmInventoryAdd tbody tr').length-1;
				 	    $('#ScmInventoryAdd tbody tr').each(function(element,index){
				 	        var data='{"code":"'+$(this).find("input[name=code]").val()+'","name":"'+$(this).find("input[name=name]").val()+'","numbers":'+$(this).find("input[name=num]").val()+',"weight":'+$(this).find("input[name=weight]").val()+'}';	
					 	    if($(this).index()==last_index){
					        	json_data+=data;
					        }else{
					        	json_data+=data+',';
					        };
					    });
				        json_data='['+json_data+']';
				        var inventoryData = "&whouseId="+$('#selectWarehouse').val()+"&locationId="+$('#selectWarehouseLocation').val()
			        	+"&categoryId="+$('#selProdCate select').val()+"&groupId="+$('#selJewelryType select').val()+"&orgId="+$('#productOrgId').val()
			        	+"&remark="+$("#ScmInventoryForm textarea[name='note']").val()+"&inventoryNo="+$(this).find("input[name=inventoryNo]").val()
			        	+"&id="+$(this).find("input[name=id]").val()+"&status="+type+"&executeTime="+$("#ScmInventoryForm input[name='executeTime']").val();
					    $.ajax({type:"POST",url:jypath+"/scm/inventory/edit",data: "myData="+json_data+inventoryData,
				        	dataType:"text",success:function(data,textStatus){   			        	 
						   var json_obj=eval('('+data+')');
						   if(json_obj.res==1){
				        		that.dialog("close");      
				        		JY.Model.info(json_obj.resMsg,function(){search();});
				           }else{
				        		JY.Model.error(json_obj.resMsg);
				           }     	
				       }});
					}
				}
			} else if (type == 1) {
				var json_data="";
		 	    var last_index=$('#ScmInventoryAdd tbody tr').length-1;
		 	    $('#ScmInventoryAdd tbody tr').each(function(element,index){
		 	        var data='{"code":"'+$(this).find("input[name=code]").val()+'","name":"'+$(this).find("input[name=name]").val()+'","numbers":'+$(this).find("input[name=num]").val()+',"weight":'+$(this).find("input[name=weight]").val()+'}';	
			 	    if($(this).index()==last_index){
			        	json_data+=data;
			        }else{
			        	json_data+=data+',';
			        };
			    });
		        json_data='['+json_data+']';
		        var inventoryData = "&whouseId="+$('#selectWarehouse').val()+"&locationId="+$('#selectWarehouseLocation').val()
	        	+"&categoryId="+$('#selProdCate select').val()+"&groupId="+$('#selJewelryType select').val()+"&orgId="+$('#productOrgId').val()
	        	+"&remark="+$("#ScmInventoryForm textarea[name='note']").val()+"&inventoryNo="+$(this).find("input[name=inventoryNo]").val()
	        	+"&id="+$(this).find("input[name=id]").val()+"&status="+type+"&executeTime="+$("#ScmInventoryForm input[name='executeTime']").val();
			    $.ajax({type:"POST",url:jypath+"/scm/inventory/edit",data: "myData="+json_data+inventoryData,
		        	dataType:"text",success:function(data,textStatus){   			        	 
				   var json_obj=eval('('+data+')');
				   if(json_obj.res==1){
		        		that.dialog("close");      
		        		JY.Model.info(json_obj.resMsg,function(){search();});
		           }else{
		        		JY.Model.error(json_obj.resMsg);
		           }     	
		       }});
			}
		}});
	});
}

function view(id){
	cleanAdd();
	$('#ScmInventoryForm').find('#productOrg_clear').hide();
	$('#ScmInventoryForm').find('#queryProductBtn').hide();
	$('#ScmInventoryForm').find('.btnClass').hide();
	$('#ScmInventoryForm').find('.ui-datepicker-trigger').hide();
	JY.Ajax.doRequest(null,jypath +'/scm/inventory/view',{id:id},function(data){
		setForm(data);
		viewInfo({id:"ScmInventoryDiv",title:"查看盘点计划",height:"720",width:"1024"});
	});
}

function publish(id) {
	cleanAdd();
	$('#ScmInventoryForm').find('#productOrg_clear').hide();
	$('#ScmInventoryForm').find('#queryProductBtn').hide();
	$('#ScmInventoryForm').find('.btnClass').hide();
	$('#ScmInventoryForm').find('.ui-datepicker-trigger').hide();
	JY.Ajax.doRequest(null,jypath +'/scm/inventory/view',{id:id},function(data){
		setForm(data);
		var serializeFormData = JSON.stringify(JY.Object.serialize($('#ScmInventoryForm')));
		
		baseInfo({id:"ScmInventoryDiv",title:"修改盘点计划",height:"720",width:"1024",savefn:function(type){
			var that =$(this);
			
			var json_data="";
	 	    var last_index=$('#ScmInventoryAdd tbody tr').length-1;
	 	    $('#ScmInventoryAdd tbody tr').each(function(element,index){
	 	        var data='{"code":"'+$(this).find("input[name=code]").val()+'","name":"'+$(this).find("input[name=name]").val()+'","numbers":'+$(this).find("input[name=num]").val()+',"weight":'+$(this).find("input[name=weight]").val()+'}';	
		 	    if($(this).index()==last_index){
		        	json_data+=data;
		        }else{
		        	json_data+=data+',';
		        };
		    });
	        json_data='['+json_data+']';
	        var inventoryData = "&whouseId="+$('#selectWarehouse').val()+"&locationId="+$('#selectWarehouseLocation').val()
        	+"&categoryId="+$('#selProdCate select').val()+"&groupId="+$('#selJewelryType select').val()+"&orgId="+$('#productOrgId').val()
        	+"&remark="+$("#ScmInventoryForm textarea[name='note']").val()+"&inventoryNo="+$(this).find("input[name=inventoryNo]").val()
        	+"&id="+$(this).find("input[name=id]").val()+"&status="+type+"&executeTime="+$("#ScmInventoryForm input[name='executeTime']").val();
		    $.ajax({type:"POST",url:jypath+"/scm/inventory/edit",data: "myData="+json_data+inventoryData,
	        	dataType:"text",success:function(data,textStatus){   			        	 
			   var json_obj=eval('('+data+')');
			   if(json_obj.res==1){
	        		that.dialog("close");      
	        		JY.Model.info(json_obj.resMsg,function(){search();});
	           }else{
	        		JY.Model.error(json_obj.resMsg);
	           }     	
	       }});
		    
		}});
	});
}

function setUpdateForm(data){
	 commForm(data);
	 commCode1(data.obj.inventoryDetails);
}
function cleanBaseForm(){
	JY.Tags.cleanForm("InventoryForm");
	setBaseData();
}
function setBaseData(){
	var date=new Date;
	var year=date.getFullYear(); 
	$("#dateBegin").val(year+"/1/1")
	$("#dateEnd").val(year+"/12/31")
}
function setForm(data){
	 commForm(data);
	 commCode1(data.obj.inventoryDetails);
	 $('#ScmInventoryForm').find('input,select,textarea').attr('disabled','disabled');
}

function commForm(data){
	$("#ScmInventoryTab input[name='id']").val(JY.Object.notEmpty(data.obj.inventories.id));
	$("#ScmInventoryTab input[name='status']").val(JY.Object.notEmpty(data.obj.inventories.status));
	$("#ScmInventoryTab input[name='inventoryNo']").val(JY.Object.notEmpty(data.obj.inventories.inventoryNo));
	
	$.jy.dropTree.checkNode("productOrg","productOrgName",data.obj.inventories.orgId);
	syncChangeOrg(data.obj.inventories.orgId, 'selectWarehouse', data.obj.inventories.whouseId);
	syncChangeWarehouse(data.obj.inventories.whouseId,"selectWarehouseLocation", data.obj.inventories.locationId);
	
	$("#ScmInventoryForm input[id='productOrgId']").val(data.obj.inventories.orgId);
	$("#selProdCate select[name='cateId']").prop("value",JY.Object.notEmpty(data.obj.inventories.categoryId));
	$("#ScmInventoryTab select[name='cateJewelryId']").prop("value",JY.Object.notEmpty(data.obj.inventories.groupId));
	if(JY.Object.notEmpty(data.obj.inventories.executeTime)){
		$("#ScmInventoryForm input[id='dateRender']").val(JY.Object.notEmpty(new Date(data.obj.inventories.executeTime).Format("yyyy/MM/dd")));
	}
	$("#ScmInventoryForm textarea[name='note']").prop("value",JY.Object.notEmpty(data.obj.inventories.remark));
	$("#ScmInventoryForm span[id='createUser']").text(JY.Object.notEmpty(data.obj.inventories.createName));
	if(JY.Object.notEmpty(data.obj.inventories.createTime)){
		$("#ScmInventoryForm span[id='createTime']").text(JY.Object.notEmpty(new Date(data.obj.inventories.createTime).Format("yyyy/MM/dd hh:mm:ss")));
	}
	$("#ScmInventoryForm span[id='updateUser']").text(JY.Object.notEmpty(data.obj.inventories.updateName));
	if(JY.Object.notEmpty(data.obj.inventories.updateTime)){
		$("#ScmInventoryForm span[id='updateTime']").text(JY.Object.notEmpty(new Date(data.obj.inventories.updateTime).Format("yyyy/MM/dd hh:mm:ss")));
	}
	$("#ScmInventoryForm span[id='checkUser']").text(JY.Object.notEmpty(data.obj.inventories.checkName));
	if(JY.Object.notEmpty(data.obj.inventories.checkTime)){
		$("#ScmInventoryForm span[id='checkTime']").text(JY.Object.notEmpty(new Date(data.obj.inventories.checkTime).Format("yyyy/MM/dd hh:mm:ss")));
	}
}

function commCode1(obj){
	var list=[];
	var html="";
	if(obj!=null){
		for(var i=0;i<obj.length;i++){ 
			$("#ScmInventoryAdd input[name$='code']").each(function(){  
				list.push($(this).val());
			});
			if(list.indexOf(obj[i].code) == -1){
				 html+="<tr>";
				 html+="<td class='center'><label> <input type='checkbox' name='detailId' value='"+JY.Object.notEmpty(obj[i].id)+"' class='ace targetClass' /><span class='lbl'></span></label></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='code' readonly  value='"+JY.Object.notEmpty(obj[i].code)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='name' readonly value='"+JY.Object.notEmpty(obj[i].name)+"' style='width:100%;height:30px;border:none;'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='num' onkeyup='JY.Validate.limitNum(this)' value='"+JY.Object.notNumber(obj[i].numbers)+"' style='width:100%;height:30px;border:none;' readonly/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='weight' onkeyup='JY.Validate.limitDouble(this)'  value='"+JY.Object.notNumber(obj[i].weight)+"'  style='width:100%;height:30px;border:none;' readonly/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='chkNum' onkeyup='JY.Validate.limitNum(this)' value='"+JY.Object.notNumber(obj[i].chkNum)+"' style='width:100%;height:30px;border:none;' readonly/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='chkWeight' onkeyup='JY.Validate.limitDouble(this)'  value='"+JY.Object.notNumber(obj[i].chkWeight)+"'  style='width:100%;height:30px;border:none;' readonly/></td>";
				 if (obj[i].diff == null) {
					 html+="<td style='padding:1px;'><input class='center' type='text' name='diff' style='width:100%;height:30px;border:none;' readonly/></td>";
				 } else {
					 html+="<td style='padding:1px;'><select name='diff'  style='width:100%;'>"+options(detailStatusArray, JY.Object.notEmpty(obj[i].diff))+"</select></td>";
				 }
				 html+="<td style='padding:1px;'><input class='center' type='text' name='diffRemark' value='"+JY.Object.notEmpty(obj[i].diffRemark)+"'  style='width:100%;height:30px;border:none;' readonly/></td>";
				 html+="</tr>";
			}else{
				JY.Model.info("商品条码已存在，请核对后再试!");
			}	
		}
	    $("#ScmInventoryAdd tbody").append(html);
	    commFoot();
	}
}

function gridSelect(url,keys,selects){
	JY.Ajax.doRequest("",url,{ids:"1",keys:keys},function(data){
		var map=data.obj;
		var list=map["1"];
		$.each(list.items,function(i,e){
			var a ={key:e.value,text:e.name};
			selects[i]=a;
		});
	})
	return selects;
}

function options(obj,val){
	var str = "<option value=''>请选择</option>";
	
	for(var i=0;i<obj.length;i++){
		str += "<option value='" + obj[i].key + "'";
		if(val==obj[i].key){
			str+=" selected='true' ";
		}
		str+=">" + obj[i].text + "</option>";
	}
	return str;
}

function delInventoryDetail(){
	var chks =[];    
	$('#ScmInventoryAdd input[class$="targetClass"]:checked').each(function(){ 
		chks.push($(this).val());    
	});     
	if(chks.length==0) {
		JY.Model.info("您没有选择任何内容!"); 
	}else{
		JY.Model.confirm("确认要删除选中的数据吗?",function(){	
			$('#ScmInventoryAdd input[class$="targetClass"]:checked').each(function(){  
				$(this).parent().parent().parent().remove();
			}); 
			$('#ScmInventoryAdd input[type="checkbox"]').each(function(){  
				$(this).attr('checked', false);
			}); 
			commFoot();
		});		
	}	
}