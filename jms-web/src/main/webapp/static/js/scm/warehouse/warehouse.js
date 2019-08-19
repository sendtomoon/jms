$(function () {
	//下拉框
	JY.Dict.setSelect("selectisValid","isValid",2,"全部");
	JY.Dict.setSelect("priceType","SCM_BALANCE_TYPE",2,"请选择");
	//新增按钮
	$("#accessoriesAdd").tooltip({hide:{effect:"slideDown",delay:250}});
	initOrgSelectData();
	loadOrgTree();
	JY.Dict.selectData("provincees","cityes","countyes");
	//增加回车事件
	$("#baseWarehouseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	/*新增仓库*/
	$('#addBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		cleanForm();
		var _orgId = $("#baseWarehouseForm input[name$='orgId']").val();
		var _orgName = $("#baseWarehouseForm input[name$='orgName']").val();
		$("#warForm input[name$='orgId']").prop("value",_orgId);
		$("#warForm input[name$='orgName']").prop("value",_orgName);
		$("#orgInput").prop("value",_orgName);
		JY.Ajax.doRequest(null,jypath +'/scm/warehouse/whetherAdd',{id:_orgId,type:1},function(){
			/*获取区域代码*/
			JY.Ajax.doRequest(null,jypath +'/scm/warehouse/findDistcode',{id:_orgId},function(data){
				$("#warForm input[name$='distcode']").val(JY.Object.notEmpty(data.obj));
			});
			editInfo({id:"warDiv",title:"新增仓库",height:570,width:500,savefn:function(){
				 if(JY.Validate.form("warForm")){
					 var that =$(this);
					 JY.Ajax.doRequest("warForm",jypath +'/scm/warehouse/add',null,function(data){
					     that.dialog("close");      
					     JY.Model.info(data.resMsg,function(){search();});
					 });
				 }	
			}});
		});
	});
	//批量删除(仓库)
	$('#delBatchBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#baseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			toDel(chks.toString());
		}		
	});
	//添加角色
	$('#addRole').on('click', function(e) {	
	});
	
	/*新增仓位*/
	$('#addBtnLoca').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		cleanLocation();
		editInfo({id:"warLoDiv",title:"新增仓位",height:550,width:380,savefn:function(){
			
			 if(JY.Validate.form("LocationForm")){
				 var that =$(this);
				 JY.Ajax.doRequest("LocationForm",jypath +'/scm/warehouseLocation/add',null,function(data){
				     that.dialog("close");      
				     JY.Model.info(data.resMsg,function(){edits($('#warehouse').val());});
				 });
			 }	
		}});
	});
	
	
	/*批量删除  仓位 */
	$('#delBtnLoca').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#locationTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			delLocation(chks.toString());
		}	
	});
});

function search(){
	$("#searchBtn").trigger("click");
}

/*******组件：选择组织机构 start*******/
var preisShow=false;//窗口是否显示
function showOrgComp() {
	if(preisShow){
		hideOrgComp();
	}else{
		var obj = $("#orgInput");
		var offpos = $("#orgInput").position();
		$("#orgContentList").css({left:offpos.left+"px",top:offpos.top+obj.heith+"px"}).slideDown("fast");	
		preisShow=true;
	}
}
function hideOrgComp(){
	$("#orgContentList").fadeOut("fast");
	preisShow=false;
}
function emptyOrgComp(){
	$("#orgInput").prop("value","");
}
function initOrgSelectData(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getOrgTree',null,function(data){
		//设置
		$.fn.zTree.init($("#selectOrgTree"),{view:{dblClickExpand:false,selectedMulti:false,nameIsHTML:true},data:{simpleData:{enable: true}},callback:{onClick:clickPreOrg}},data.obj);
	});
}
function clickPreOrg(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("selectOrgTree"),
	nodes = zTree.getSelectedNodes(),v ="",n ="";	
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";//获取name值
		n += nodes[i].id + ",";	//获取id值
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (n.length > 0 ) n = n.substring(0, n.length-1);
	$("#orgInput").prop("value",v);
	$("#warForm input[name$='orgId']").prop("value",n);
	$("#warForm input[name$='orgName']").prop("value",v);
	//因为单选选择后直接关闭，如果多选请另外写关闭方法
	hideOrgComp();
	/*获取区域代码*/
	JY.Ajax.doRequest(null,jypath +'/scm/warehouse/findDistcode',{id:n},function(data){
		$("#warForm input[name$='distcode']").val(JY.Object.notEmpty(data.obj));
	});
}
/*******组件：选择组织机构 end*******/



/*仓库列表*/
function getbaseList(init){
	if(init==1)$("#baseWarehouseForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("baseWarehouseForm",jypath +'/scm/warehouse/findByPage',null,function(data){
		 $("#baseTable tbody").empty();
        	 var obj=data.obj;
        	 var list=obj.list;
        	 var results=list.results;
        	 var permitBtn=obj.permitBtn;
         	 var pageNum=list.pageNum,pageSize=list.pageSize,totalRecord=list.totalRecord;
        	 var html="";
    		 if(results!=null&&results.length>0){
        		 var leng=(pageNum-1)*pageSize;//计算序号
        		 for(var i = 0;i<results.length;i++){
            		 var l=results[i];
            		 html+="<tr>";
            		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
            		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.name)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.code)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.orgName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.distcode)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.director)+"</td>";
            		 if(l.type==0) html+="<td class='center hidden-480'><span class='label label-sm label-success'>系统定义</span></td>";
            		 else             html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>用户定义</span></td>";
            		 if(l.status==1) html+="<td class='center hidden-480'><span class='label label-sm label-success'>有效</span></td>";
            		 else             html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>无效</span></td>";
            		 if(l.defaults==1) html+="<td class='center hidden-480'><span class='label label-sm label-success'>是</span></td>";
            		 else             html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>否</span></td>";
            		 
            		 if(l.type==0){
            			 html+=JY.Tags.setFunction1(l.id,permitBtn,'edit,toDel,');
            		 }else{
            			 html+=JY.Tags.setFunction(l.id,permitBtn);
            		 }
            		// html+=JY.Tags.setFunction(l.id,permitBtn);
            		 html+="</tr>";		 
            	 } 
        		 $("#baseTable tbody").append(html);
        		 JY.Page.setPage("baseWarehouseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='11' class='center'>没有相关数据</td></tr>";
        		$("#baseTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
    		 JY.Model.loadingClose();
	 });
}

/*清空仓库信息*/
function cleanForm(){
	$("#warForm input[name$='code']").removeAttr('readonly');
	$('#warForm').find('input,select,textarea').removeAttr('disabled');
	$("#warForm input[name$='id']").val("");
	JY.Tags.cleanForm("warForm");
	JY.Tags.isValid("warForm","1");
}

/*设置仓库信息*/
function setForm(data){
	var l=data.obj;
	JY.Tools.populateForm("warForm",l);
	$("#orgInput").prop("value",JY.Object.notEmpty(l.orgName));
	$("#warForm textarea[name$='address']").val(JY.Object.notEmpty(l.address));
	$("#warForm textarea[name$='description']").val(JY.Object.notEmpty(l.description));//描述
	JY.Tags.isValid("warForm",(JY.Object.notNull(l.status)?l.status:"0"));
	JY.Dict.selectData("provincees","cityes","countyes",l.province,l.city,l.county);
}
/****左区组织菜单 start*****/
function loadOrgTree(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getOrgTree',null,function(data){
		//设置
		$.fn.zTree.init($("#orgTree"),{view:{selectedMulti:false,fontCss:{color:"#393939"}},data:{simpleData: {enable: true}},callback:{onClick:clickOrg}},data.obj);
		var treeObj = $.fn.zTree.getZTreeObj("orgTree");
		var nodes = treeObj.getNodes();
		treeObj.expandAll(true);
		if(nodes.length>0){
			//默认选中第一个
			treeObj.selectNode(nodes[0]);
			clickOrg(null,null,nodes[0]);
		}
	});
}
function clickOrg(event,treeId,treeNode) {
	$("#baseWarehouseForm input[name$='orgId']").val(treeNode.id);
	$("#baseWarehouseForm input[name$='orgName']").val(treeNode.name);
	$("#orgName1").html("");
	$("#orgName2").html("");
	if(JY.Object.notNull(treeNode.pId)){
		var treeObj = $.fn.zTree.getZTreeObj("orgTree");
		var ptreeNode=treeObj.getNodeByParam("id",treeNode.pId,null);
		$("#orgName1").html(ptreeNode.name);
		$("#orgName2").html(treeNode.name);
	}else{
		$("#orgName1").html(treeNode.name);
	}
	getbaseList(1);
}
/****左区组织菜单 end*****/

/*查看页面*/
function viewInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
}
/*编辑页面*/
function editInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
}

/*编辑仓库信息*/
function edit(id){
	cleanForm();
	$("#warForm input[name$='code']").attr('readonly','readonly');
	JY.Ajax.doRequest(null,jypath +'/scm/warehouse/whetherAdd',{id:id,type:2},function(){
		JY.Ajax.doRequest(null,jypath +'/scm/warehouse/find',{id:id},function(data){
		    setForm(data);   
		    editInfo({id:"warDiv",title:"修改仓库",height:570,width:500,savefn:function(){
		    	if(JY.Validate.form("warForm")){
					var that =$(this);
					JY.Ajax.doRequest("warForm",jypath +'/scm/warehouse/update',null,function(data){
						that.dialog("close");
						JY.Model.info(data.resMsg,function(){search();});	
					});
					
		    	}
		    }});
		});
	});
}


/*查看仓库信息*/
function find(id){
	//加载dialog
	cleanForm();
	$('#warForm').find('input,select,textarea').attr('disabled','disabled');
	JY.Ajax.doRequest(null,jypath +'/scm/warehouse/find',{id:id},function(data){ 
		setForm(data);
		viewInfo({id:"warDiv",title:"仓库明细",height:570,width:500})
	});
}

/*加载仓位列表信息*/
function edits(id){
	JY.Model.loading();
	JY.Ajax.doRequest(null,jypath +'/scm/warehouseLocation/findByPage',{warehouseid:id},function(data){
		$('#warehouse').val(id);
		$("#locationTable tbody").empty();
      	var obj=data.obj;
      	var html="";
      	if(obj!=null&&obj.length>0){
      		 for(var i = 0;i<obj.length;i++){
      			 var l=obj[i];
            		 html+="<tr>";
            		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.sort)+"</td>";
            		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.name)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.code)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.warehousecode)+"</td>";
            		 if(l.type==0) html+="<td class='center hidden-480'><span class='label label-sm label-success'>系统定义</span></td>";
            		 else             html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>用户定义</span></td>";
            		 if(l.status==1) html+="<td class='center hidden-480'><span class='label label-sm label-success'>有效</span></td>";
            		 else             html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>无效</span></td>";
            		 if(l.defaults==1) html+="<td class='center hidden-480'><span class='label label-sm label-success'>是</span></td>";
            		 else             html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>否</span></td>";
            		 html+="<td >";
            		 if(l.type==1){
            			 $('#btnLocation').show();
            			 html+="<div class='inline position-relative'>";
       					 html+="<a class='aBtnNoTD dropbtn' onclick='editLocation(&apos;"+l.id+"&apos;,&apos;"+id+"&apos;)' title='修改' href='#' style='margin: 3px'>修改</a>";
       					 html+="</div>";
       					 html+="<div class='inline position-relative'>";
       					 html+="<a class='aBtnNoTD dropbtn' onclick='delLocation(&apos;"+l.id+"&apos;)' title='删除' href='#'>删除</a>";
       					 html+="</div>";
            		 }else{
            			 $('#btnLocation').hide();
            		 }
   					 html+="</td>";
            		 html+="</tr>";		 
            	 } 
        		 $("#locationTable tbody").append(html);
        	 }else{
        		html+="<tr><td colspan='9' class='center'>没有相关数据</td></tr>";
        		$("#locationTable tbody").append(html);
        	 }
    		 JY.Model.loadingClose();
	 });
	 JY.Ajax.doRequest(null,jypath +'/scm/warehouse/find',{id:id},function(data){ 
		$("#LocationForm input[name$='warehouseName']").val(data.obj.name);
		$("#LocationForm input[name$='warehouseid']").val(data.obj.id);
	 });
	 viewInfo({id:"locationDiv",title:"仓位列表",height:"600",width:"800"})
	 
}

/*仓位删除*/
function delLocation(id){
	var warehouseId=$("#LocationForm input[name$='warehouseid']").val();
	JY.Ajax.doRequest(null,jypath +'/scm/warehouseLocation/todel',{locationId:id},function(data){
		if(data.obj==0){
			JY.Model.confirm("确认删除吗？",function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/warehouseLocation/del',{id:id},function(data){
					JY.Model.info(data.resMsg,function(){edits(warehouseId);});
				});
		    });
		}else if(data.obj==1){
			JY.Model.confirm("仓库中的商品需移库，确认删除？",function(){	
				warehouseForm(id);
				editInfo({id:"warehousingDiv",title:"商品入库",height:"400",width:"300",savefn:function(){
					if ($('#selectWarehouse').val()!=0 && $('#selectWarehouseLocation').val()!=0 && $('#warehouseidOld').val()!="") {
						var that = $(this);
						JY.Ajax.doRequest("warLoForm", jypath + '/scm/warehouseLocation/dodel',null, function(data) {
							that.dialog("close");
							JY.Model.info(data.resMsg, function() {
								edits(warehouseId);
							});
						});
				    }
				}});
			});
		}else if(data.obj==2){
			JY.Model.info("仓位为系统定义不能删除");	
		}
	});
	
}



/*仓位修改*/
function editLocation(id,wareId){
	cleanLocation();
	$("#LocationForm input[name='code']").attr('readonly','readonly');
	JY.Ajax.doRequest(null,jypath +'/scm/warehouse/whetherAdd',{id:id,type:3},function(){
		JY.Ajax.doRequest(null,jypath +'/scm/warehouseLocation/find',{id:id},function(data){
			setLocation(data);   
			editInfo({id:"warLoDiv",title:"修改仓位",height:550,width:380,savefn:function(){
		    	if(JY.Validate.form("LocationForm")){
					var that =$(this);
					JY.Ajax.doRequest("LocationForm",jypath +'/scm/warehouseLocation/update',null,function(data){
						that.dialog("close");
						JY.Model.info(data.resMsg,function(){edits(wareId);});	
					});
					
		    	}
			}});
		});
	});
}

/*设置仓位信息*/
function setLocation(data){
	var l=data.obj;
	$("#LocationForm input[name$='id']").val(JY.Object.notEmpty(l.id));
	JY.Tags.isValid("LocationForm",(JY.Object.notNull(l.status)?l.status:"0"));
	JY.Tags.isValid1("LocationForm",(JY.Object.notNull(l.defaults)?l.defaults:"0"));
	$("#LocationForm input[name$='name']").val(JY.Object.notEmpty(l.name));
	$("#LocationForm input[name$='code']").val(JY.Object.notEmpty(l.code));
	$("#LocationForm input[name$='defaults']").val(JY.Object.notEmpty(l.defaults));
	$("#LocationForm input[name$='status']").val(JY.Object.notEmpty(l.status));
	$("#LocationForm input[name$='warehouseid']").val(JY.Object.notEmpty(l.warehouseid));
	$("#LocationForm input[name$='sort']").val(JY.Object.notEmpty(l.sort));
	$("#LocationForm input[name$='status']").val(JY.Object.notEmpty(l.status));
	$("#LocationForm textarea[name$='description']").val(JY.Object.notEmpty(l.description));//描述
	
}

/*清空仓位信息*/
function cleanLocation(){
	$("#id").val("");
	$("#LocationForm input[name='code']").removeAttr('readonly');
	$("#LocationForm input[name$='name']").val("");
	$("#LocationForm input[name$='code']").val("");
	$("#LocationForm input[name$='sort']").val("");
	$("#LocationForm textarea[name$='description']").val("");//描述
	$("#LocationForm input[name$='status'][value=0]").attr("checked",false);
}

/*仓库中是否有商品*/
function toDel(id){
	JY.Ajax.doRequest(null,jypath +'/scm/warehouse/toDel',{warehouseId:id},function(data){
		if(data.obj==0){
			JY.Model.confirm("确认删除吗？",function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/warehouse/del',{id:id},function(data){
					JY.Model.info(data.resMsg,function(){search();});
				});
		    });
		}else if(data.obj==1){
			JY.Model.info("改仓库为系统定义不能删除");	
		}else if(data.obj==2){
			JY.Model.info("请先删除该仓库下的仓位");	
		}
	});
}

//----------------仓库和仓位选择  开始------------------------///
/*表单添默认值(仓库列表和默认的仓库/仓位) */
function warehouseForm(id){
	$("#warLoForm input[name$='warehouseidOld']").val(id);
	var selectWarehouse = $('#selectWarehouse');
	JY.Ajax.doRequest(null,jypath +'/scm/warehousing/selectWarehousing',null,function(data){
	    var obj=data.obj.list;
	    selectWarehouse.html("");
	    $('#selectWarehouseLocation').html("");
	    selectWarehouse.append('<option value="0">请选择</option>');
		for (var i = 0; i < obj.length; i++) {
			if(data.obj.warehouse!=undefined && obj[i].key==data.obj.warehouse.id){
				selectWarehouse.append('<option selected="selected" value="'+obj[i].key+'">'+obj[i].value+'</option>');
			}else{
				selectWarehouse.append('<option value="'+obj[i].key+'">'+obj[i].value+'</option>');
			}
        }
		$('#selectWarehouseLocation').append('<option value="'+data.obj.location.id+'" selected="selected">'+data.obj.location.name+'</option>');
	});
}
/*仓位列表*/
function chgWarehouseLocation(obj){
	var va = obj.val();
	var selectWarehouse = $('#selectWarehouseLocation');
	JY.Ajax.doRequest(null,jypath +'/scm/warehousing/selectWarehousingLocation',{id:va},function(data){
	    var obj=data.obj;
	    selectWarehouse.html("");
	    selectWarehouse.append('<option value="0">请选择</option>');
		for (var i = 0; i < obj.length; i++) {
			selectWarehouse.append('<option value="'+obj[i].key+'">'+obj[i].value+'</option>');
        }
	});
}

//-----------------仓库和仓位选择结束------------------------///