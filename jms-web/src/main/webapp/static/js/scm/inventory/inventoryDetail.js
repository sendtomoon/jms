var inventoryData;
var codeArray = [];
var detailStatusArray = [];
var tmpCode;
var itemNum = 0;

$(function() {
	
	JY.Dict.setSelect("selectDiffType","SCM_INVENTORYDETAIL_STATUS",2,"全部");
	// 得到盘点详情的所有状态值
	detailStatusArray = gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_INVENTORYDETAIL_STATUS",detailStatusArray);
	// 获取盘点计划
	getbaseList(1);
	
	// 查询盘点计划列表回车事件
	$("#InventoryDetailForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	// 输入条码回车事件
	$("#enteryno").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 subCode();
		 } 
	});
	
	// 开始盘点按钮事件
	$('#checkBtn').on('click', function(e) {
		var chks =[];    
		$('#InventoryDetailTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("您只能选择一条内容!"); 
		} else {
			var orderNum = $('#InventoryDetailTable input[name="ids"]:checked').parent().parent().parent().find('input[name="orderNumValue"]').val()
			check(chks[0], orderNum);
		}
	});
	
	// 列出其他待盘点商品信息按钮事件
	$('#listBtn').on('click', function(e) {
		e.preventDefault();
		var jsonData = "";
		var trLastIndex = $('#ScmInventoryDetailTable tbody tr').length - 1;
		$('#ScmInventoryDetailTable tbody tr').each(function(index, element) {
			var data = '{"id":"' + $(this).find('input[name="detailId"]').val() + '", "code":"' + $(this).find('input[name="code"]').val() 
				+ '", "name":"' + $(this).find('input[name="name"]').val() + '", "numbers":' + $(this).find('input[name="numbers"]').val()
				+ ', "weight":' + $(this).find('input[name="weight"]').val() + ', "chkNum":' + $(this).find('input[name="chkNum"]').val()
				+ ', "chkWeight":' + $(this).find('input[name="chkWeight"]').val() + ', "diff":"' + $(this).find('select[name="diff"]').val()
				+ '", "diffRemark":"' + $(this).find('input[name="diffRemark"]').val() + '"}';
			if ($(this).index() === trLastIndex) {
				jsonData += data;
			} else {
				jsonData += data + ',';
			}
		});
		jsonData = '[' + jsonData + ']';
		var inventoryDetailData = '&inventoryId=' + $("input[id='inventoryId']").val() + '&inventoryNo=' + $("input[id='inventoryNo']").val();;
		$.ajax({
			type:"POST",
			url:jypath+"/scm/inventoryDetail/findOtherDetail",
			data: "myData="+jsonData + inventoryDetailData,
			dataType:"json",
			success:function(data) {
				if (data.res == 0) {
					JY.Model.info(data.resMsg);
				} else if (data.res == 1) {
					commCode2(data.obj);
				}
			}
	    });
	});
	
})

//获取盘点计划数据
function getbaseList(init) {
	if(init == 1) {
		$("#InventoryDetailForm .pageNum").val(1);
	}
	JY.Model.loading();
	JY.Ajax.doRequest("InventoryDetailForm",jypath +'/scm/inventory/findForDetail',null,function(data) {
		inventoryData = data.obj.list.results;
		 $("#InventoryDetailTable tbody").empty();
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
        		 html += "<td class='center'><input name='orderNumValue' type='hidden' value="+i+" /><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
        		 html += "<td class='center hidden-480'>"+JY.Object.notEmpty(l.inventoryNo)+"</td>";
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
        		 html += "<td class='center hidden-480'><input name='orgIdValue' type='hidden' value="+JY.Object.notNumber(l.orgId)+" />"+JY.Object.notNumber(l.orgName)+"</td>";
        		 html += "<td class='center hidden-480'><input name='whouseIdValue' type='hidden' value="+JY.Object.notNumber(l.whouseId)+" />"+JY.Object.notNumber(l.whouseName)+"</td>";
        		 html += "<td class='center hidden-480'><input name='locationIdValue' type='hidden' value="+JY.Object.notNumber(l.locationId)+" />"+JY.Object.notNumber(l.locationName)+"</td>";
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
        		 html += "</tr>";	 
        	 } 
    		 $("#InventoryDetailTable tbody").append(html);
    		 JY.Page.setPage("InventoryDetailForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
    	 }else{
    		html += "<tr><td colspan='13' class='center'>没有相关数据</td></tr>";
    		$("#InventoryDetailTable tbody").append(html);
    		$("#pageing ul").empty();//清空分页
    	 }	
		 JY.Model.loadingClose();
	 });
}

function cleanAdd(){
	$("input[id='enteryno']").val("");
	$("input[id='inventoryId']").val("");
	$("input[id='inventoryNo']").val("");
	$("input[id='orgId']").val("");
	$("input[id='whouseId']").val("");
	$("input[id='locationId']").val("");
	$('#ScmInventoryDetailTable tbody').empty();
	$('#ScmInventoryDetailTable tfoot').empty();
}

function commCode2(obj){
	var html="";
	if(obj != null) {
		for(var i = 0;i< obj.length; i++){ 
			if(codeArray.indexOf(obj[i].code) == -1){
				 html+="<tr>";
				 html+="<td class='center'><label> <input type='checkbox' name='detailId' value='"+JY.Object.notEmpty(obj[i].id)+"' class='ace' /><span class='lbl'></span></label></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='code' readonly  value='"+JY.Object.notEmpty(obj[i].code)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='name' readonly value='"+JY.Object.notEmpty(obj[i].name)+"' style='width:100%;height:30px;border:none;'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='numbers' onkeyup='JY.Validate.limitNum(this)' value='"+JY.Object.notNumber(obj[i].numbers)+"' style='width:100%;height:30px;border:none;' readonly/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='weight' onkeyup='JY.Validate.limitDouble(this)'  value='"+JY.Object.notNumber(obj[i].weight)+"'  style='width:100%;height:30px;border:none;' readonly/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='chkNum' onkeyup='JY.Validate.limitNum(this)' value='"+JY.Object.notNumber(obj[i].chkNum)+"' style='width:100%;height:30px;border:none;' /></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='chkWeight' onkeyup='JY.Validate.limitDouble(this)'  value='"+JY.Object.notNumber(obj[i].chkWeight)+"'  style='width:100%;height:30px;border:none;' /></td>";
				 html+="<td style='padding:1px;'><select name='diff' id='dColor"+itemNum+"'  style='width:100%;'>"+options(detailStatusArray, JY.Object.notEmpty(obj[i].diff))+"</select></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='diffRemark' value='"+JY.Object.notEmpty(obj[i].diffRemark)+"'  style='width:100%;height:30px;border:none;' /></td>";
				 html+="</tr>";
				 itemNum++;
				 codeArray.push(obj[i].code);
			}else{
				JY.Model.info("商品条码已经存在，请核对后再试!");
			}	
		}
		$("#ScmInventoryDetailTable tbody").append(html);
//	    commFoot();
	}
}

function commCode(obj){
	var list=[];
	var html="";
	var detail = obj.detail;
	var product = obj.product;
	
	if (product.warehouseId === $("input[id='whouseId']").val() && product.locationId === $("input[id='locationId']").val()) {
		if(detail != null) {
			if(codeArray.indexOf(detail.code) == -1) {
				 html+="<tr>";
				 html+="<td class='center'><label> <input type='checkbox' name='detailId' value='"+JY.Object.notEmpty(detail.id)+"' class='ace' /><span class='lbl'></span></label></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='code' readonly  value='"+JY.Object.notEmpty(detail.code)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='name' readonly value='"+JY.Object.notEmpty(detail.name)+"' style='width:100%;height:30px;border:none;'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='numbers' onkeyup='JY.Validate.limitNum(this)' value='"+JY.Object.notNumber(detail.numbers)+"' style='width:100%;height:30px;border:none;' readonly/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='weight' onkeyup='JY.Validate.limitDouble(this)'  value='"+JY.Object.notNumber(detail.weight)+"'  style='width:100%;height:30px;border:none;' readonly/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='chkNum' onkeyup='JY.Validate.limitNum(this)' value='"+JY.Object.notNumber(detail.chkNum)+"' style='width:100%;height:30px;border:none;' /></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='chkWeight' onkeyup='JY.Validate.limitDouble(this)'  value='"+JY.Object.notNumber(detail.chkWeight)+"'  style='width:100%;height:30px;border:none;' /></td>";
				 html+="<td style='padding:1px;'><select name='diff' id='dColor"+itemNum+"'  style='width:100%;'>"+options(detailStatusArray, JY.Object.notEmpty(detail.diff))+"</select></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='diffRemark' value='"+JY.Object.notEmpty(detail.diffRemark)+"'  style='width:100%;height:30px;border:none;' /></td>";
				 html+="</tr>";
				 itemNum++;
			} else {
				JY.Model.info("商品条码已经存在，请核对后再试!");
			}
		    $("#ScmInventoryDetailTable tbody").append(html);
		    codeArray.push(tmpCode);
//		    commFoot();
		}
	} else {
		JY.Model.info("该商品不在盘点计划的仓库仓位，请核对后再试!");
	}
}
function baseCheck(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
    	buttons:[{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存并继续","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
    	         {html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;完成盘点","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},
    	         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}

function addInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
    	buttons:[{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;添加","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
    	         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}

//更改查询条件按回车键触发查询按钮的点击事件
function search(){
	$("#searchBtn").trigger("click");
}

function check(id, num) {
	
	cleanAdd();
	codeArray = [];
	itemNum = 0;
	
	$("input[id='inventoryId']").val(id);
	$("input[id='inventoryNo']").val(inventoryData[num].inventoryNo);
	$("input[id='orgId']").val(inventoryData[num].orgId);
	$("input[id='whouseId']").val(inventoryData[num].whouseId);
	$("input[id='locationId']").val(inventoryData[num].locationId);
	
	baseCheck({id:"ScmInventoryDetailDiv",title:"开始盘点",height:"570",width:"1024",savefn:function(type){
		var that =$(this);
//		if(JY.Validate.form("ScmInventoryDetailForm") && $('#ScmInventoryDetailTable tbody tr').length > 0) {
			
			var jsonData = "";
			var trLastIndex = $('#ScmInventoryDetailTable tbody tr').length - 1;
			var judgeArray = [];
			
			$('#ScmInventoryDetailTable tbody tr').each(function(index, element) {
				var diffv = $(this).find('select[name="diff"]').val();
				var numbersv = $(this).find('input[name="numbers"]').val();
				var chknumv = $(this).find('input[name="chkNum"]').val();
				var judge = ($(this).find('input[name="numbers"]').val() != $(this).find('input[name="chkNum"]').val()) 
					&& ($(this).find('select[name="diff"]').val() == '0' || $(this).find('select[name="diff"]').val() == '' || $(this).find('select[name="diff"]').val() == null);
				if (judge) {
					judgeArray.push(false);
					return false;
				} else {
					judgeArray.push(true);
					var data = '{"id":"' + $(this).find('input[name="detailId"]').val() + '", "code":"' + $(this).find('input[name="code"]').val() 
					+ '", "name":"' + $(this).find('input[name="name"]').val() + '", "numbers":' + $(this).find('input[name="numbers"]').val()
					+ ', "weight":' + $(this).find('input[name="weight"]').val() + ', "chkNum":' + $(this).find('input[name="chkNum"]').val()
					+ ', "chkWeight":' + $(this).find('input[name="chkWeight"]').val() + ', "diff":"' + $(this).find('select[name="diff"]').val()
					+ '", "diffRemark":"' + $(this).find('input[name="diffRemark"]').val() + '"}';
					if ($(this).index() === trLastIndex) {
						jsonData += data;
					} else {
						jsonData += data + ',';
					}
				}
			});
			if (judgeArray[judgeArray.length - 1] === false) {
				JY.Model.info("有商品存在差异且并未指定差异原因！");
			} else {
				jsonData = '[' + jsonData + ']'; 
				JY.Ajax.doRequest(null,jypath +'/scm/inventoryDetail/updateDetails',{myData: jsonData, type: type, inventoryId: $("input[id='inventoryId']").val(), inventoryNo: $("input[id='inventoryNo']").val()},function(data){
					if (type == 0) {
						$('#ScmInventoryDetailTable tbody').empty();
						$('#ScmInventoryDetailTable tfoot').empty();
						codeArray = [];
					} else if (type == 1) {
						if (data.res == 1) {
							JY.Model.info(data.resMsg);
							that.dialog("close");
							search();
						} else {
							JY.Model.error(data.resMsg);
						}
						
					}
				});
			}
//		} else {
//			JY.Model.info("必须有数据时才能进行操作！");
//		}
	}});
}

// 扫条码进行添加
function subCode(){
	var code=$("input[id='enteryno']").val();
	tmpCode = code;
	var inventoryId = $("input[id='inventoryId']").val();
	if(codeArray.indexOf(code) == -1) {
		// TODO：判断中商品条码可能需要加上正则判断(暂时不做)
		if (code.length != 0) {
			JY.Ajax.doRequest(null,jypath +'/scm/inventoryDetail/findByCode',{code: code, inventoryId: inventoryId},function(data){
				 commCode(data.obj);
			});
			$("input[id='enteryno']").val("");
		} else {
			JY.Model.info("请填写正确的商品条码!"); 
		}
	} else {
		JY.Model.info("商品条码已经存在，请核对后再试!");
	}
}

// 添加盘点计划详情
function addInventoryDetail(e){
	JY.Tags.cleanForm("ScmInventoryDetailAddDiv");

	addInfo({id:"ScmInventoryDetailAddDiv",title:"添加盘点详情信息",height:"500",width:"300",savefn:function(e){
		var that =$(this);
	}})	
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
	$('#ScmInventoryDetailTable input[name="detailId"]:checked').each(function(){ 
		chks.push($(this).val());
	});     
	if(chks.length==0) {
		JY.Model.info("您没有选择任何内容!"); 
	}else{
		JY.Model.confirm("确认要删除选中的数据吗?",function(){	
			$('#ScmInventoryDetailTable input[name="detailId"]:checked').each(function(){  
				$(this).parent().parent().parent().remove();
				codeArray = [];
			}); 
			$('#ScmInventoryDetailTable input[type="checkbox"]').each(function(){  
				$(this).attr('checked', false);
			}); 
		});		
	}
	$('#ScmInventoryDetailTable input[name="code"]').each(function(){ 
		codeArray.push($(this).val());
	});
}