$(function () {
	//加载菜单树
	loadOrgTree();
	//下拉框
	JY.Dict.setSelect("selectStoreStatus","status",2,"全部");
	JY.Dict.setSelect("selectStoreType","storeType",2,"请选择门店类型");
	JY.Dict.setSelect("selectStoreProp","storeProp",2,"请选择门店性质");
	initOrgSelectData();//初始化组织结构选择组件
	//getbaseList();
	//增加回车事件
	$("#storeBaseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	$("#storeInfoForm #orgContentList").mouseleave(function(){
		  hideOrgComp();
	});
	//新加
	$('#addBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var _orgGrade = $("#storeBaseForm input[name$='orgGrade']").val();
		if(_orgGrade!='1'){
			JY.Model.error("抱歉,只允许在部门机构下创建门店,请您重新选择！");
			return;
		}
		cleanForm();
		var _orgId = $("#storeBaseForm input[name$='orgId']").val();
		var _orgName = $("#storeBaseForm input[name$='_orgName']").val();
		$("#storeInfoForm input[name$='orgId']").prop("value",_orgId);
		$("#storeInfoForm input[name$='orgName']").prop("value",_orgName);
		$("#orgInput").prop("value",_orgName);
		$("#storeInfoForm input[name$='pId']").prop("value",_orgId);
		editInfo("storeDiv","新增",function(){
			 if(JY.Validate.form("storeInfoForm")){
				 var that =$(this);
				 JY.Ajax.doRequest("storeInfoForm",jypath +'/scm/store/add',null,function(data){
				     that.dialog("close");      
				     JY.Model.info(data.resMsg,function(){search();});
				 });
			 }	
		});
	});
	//批量删除
	$('#delBatchBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#storeList input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			JY.Model.confirm("确认要删除选中的数据吗?",function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/store/delBatch',{chks:chks.toString()},function(data){
					JY.Model.info(data.resMsg,function(){search();});
				});
			});		
		}	
	});
	JY.Ajax.doRequest(null,jypath +'/backstage/district/getDistrictTree',{status:"1"},function(data){
		var setting={rootId:'disList',displayId:'disInput',data:data.obj,clickFn:function(node){
			$("#storeInfoForm input[name$='distCode']").val(node.id);
		},isExpand:true};
		$.jy.dropTree.init(setting);
	});

});
function search(){
	$("#searchBtn").trigger("click");
}


/****左区组织菜单start*****/
function loadOrgTree(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getOrgTreeE4',null,function(data){
		//设置
		$.fn.zTree.init($("#orgTree"),{view:{selectedMulti:false,fontCss:{color:"#393939"}},data:{simpleData: {enable: true}},callback:{onClick:clickOrg}},data.obj);
		var treeObj = $.fn.zTree.getZTreeObj("orgTree");
		var nodes = treeObj.getNodes();
		treeObj.expandNode(nodes[0],true,false,false,false);
		if(nodes.length>0){
			//默认选中第一个
			treeObj.selectNode(nodes[0]);
			clickOrg(null,null,nodes[0]);
		}
	});
}
function clickOrg(event,treeId,treeNode) {
	$("#storeBaseForm input[name$='orgId']").val(treeNode.id);
	$("#storeBaseForm input[name$='_orgName']").val(treeNode.name);
	$("#storeBaseForm input[name$='orgGrade']").val(treeNode.orgGrade);
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
/****左区组织菜单end*****/

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
	$("#storeInfoForm input[name$='pId']").prop("value","");
}
function initOrgSelectData(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/selectOrgTreeE4',null,function(data){
		//设置
		$.fn.zTree.init($("#selectOrgTree"),{view:{dblClickExpand:false,selectedMulti:false,nameIsHTML:true},data:{simpleData:{enable: true}},callback:{onClick:clickPreOrg}},data.obj);
	});
}
function clickPreOrg(e, treeId, treeNode) {
	if(treeNode.orgGrade!="1")return;
	var zTree = $.fn.zTree.getZTreeObj("selectOrgTree"),
	nodes = zTree.getSelectedNodes(),v ="",n ="";	
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";//获取name值
		n += nodes[i].id + ",";	//获取id值
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (n.length > 0 ) n = n.substring(0, n.length-1);
	$("#orgInput").prop("value",v);
	//$("#storeInfoForm input[name$='pId']").prop("value",n);
	$("#storeInfoForm input[name$='orgId']").prop("value",n);
	$("#storeInfoForm input[name$='orgName']").prop("value",v);
	//因为单选选择后直接关闭，如果多选请另外写关闭方法
	hideOrgComp();
}
/*******组件：选择组织机构 end*******/


/**组装并加载table数据列表**/
function getbaseList(init){
	if(init==1)$("#storeBaseForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("storeBaseForm",jypath +'/scm/store/findByPage',null,function(data){
		 $("#storeList tbody").empty();
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
            		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"-"+l.orgId+"' class='ace' /> <span class='lbl'></span></label></td>";
            		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.name)+"</td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.longName)+"</td>";
            		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.director)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.directorNm)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.address)+"</td>";
            		 if(l.status==1) html+="<td class='center hidden-480'><span class='label label-sm label-success'>有效</span></td>";
            		 else             html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>无效</span></td>";
            		 html+=JY.Tags.setFunction(l.id,permitBtn);
            		 html+="</tr>";		 
            	 } 
        		 $("#storeList tbody").append(html);
        		 JY.Page.setPage("storeBaseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
        		$("#storeList tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
 	 
    	 JY.Model.loadingClose();
	 });
}

function viewInfo(id,title,fn){	
	$("#"+id).removeClass('hide').dialog({resizable:false,height:600,width:720,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(fn) == 'function'){fn.call(this);}}}]});
}
function editInfo(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,height:600,width:720,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
}
function setForm(data){
	var _data=data.obj;
	$("#storeInfoForm input[name$='id']").val(_data.id);
	JY.Tags.isValid("storeInfoForm",(JY.Object.notNull(_data.status)?_data.status:"0"));
	$("#storeInfoForm input[name$='code']").val(JY.Object.notEmpty(_data.code));
//	$("#storeInfoForm input[name$='code']").prop("disabled",true); 
	$("#storeInfoForm input[name$='code']").attr("readonly", true);
	$("#storeInfoForm input[name$='name']").val(JY.Object.notEmpty(_data.name));
	$("#storeInfoForm input[name$='longName']").val(JY.Object.notEmpty(_data.longName));
	$("#storeInfoForm input[name$='director']").val(JY.Object.notEmpty(_data.director));
	$("#storeInfoForm input[name$='directorNm']").val(JY.Object.notEmpty(_data.directorNm));
	$("#storeInfoForm input[name$='contractor']").val(JY.Object.notEmpty(_data.contractor));
	$("#storeInfoForm input[name$='contractorNm']").val(JY.Object.notEmpty(_data.contractorNm));
	$("#storeInfoForm input[name$='orgId']").prop("value",JY.Object.notEmpty(_data.orgId));
	$("#storeInfoForm input[name$='orgName']").prop("value",JY.Object.notEmpty(_data.orgName));
	$("#orgInput").prop("value",JY.Object.notEmpty(_data.orgName));
	$("#storeInfoForm input[name$='pId']").prop("value",JY.Object.notEmpty(_data.pId));
	$("#storeType").val(JY.Object.notEmpty(_data.type));
	$("#storeProp").val(JY.Object.notEmpty(_data.perpoty));
	$("#storeInfoForm textarea[name$='address']").val(JY.Object.notEmpty(_data.address));
	$("#storeInfoForm textarea[name$='postAddress']").val(JY.Object.notEmpty(_data.postAddress));
	$("#storeInfoForm textarea[name$='description']").val(JY.Object.notEmpty(_data.description));
	$("#storeInfoForm input[name$='distCode']").val(_data.distCode);
	/*$.each(distCodes,function(i,e){
		if(e.id==_data.distCode){
			$("#disInput").val(JY.Object.notEmpty(e.name));
			return false;
		}
	});*/
	$.jy.dropTree.checkNode("disList","disInput",_data.distCode);
	
}
//重置form表单
function cleanForm(){
	JY.Tags.cleanForm("storeInfoForm");
	JY.Tools.formDisabled("storeInfoForm",false,function(){
		$("#storeInfoForm .icon-remove").show();
	});
	//复原下拉框，角色，职级，等级
	//$("#roleId").val('');
	$("#storeProp").val('');
	$("#storeType").val('');
	$("#storeInfoForm input[name$='directorNm']").val('');
	$("#storeInfoForm input[name$='contractorNm']").val('');
//	$("#storeInfoForm input[name$='code']").prop("disabled",false); 
	$("#storeInfoForm input[name$='code']").attr("readonly", false);
}

/*
 * 查看门店信息
 */
function view(storeId){
	//加载dialog
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/scm/store/find',{id:storeId},function(data){ 
		setForm(data);
		JY.Tools.formDisabled("storeInfoForm",true,function(){
			$("#storeInfoForm .icon-remove").hide();
		});
		viewInfo("storeDiv");
	});
}
/*
 * 修改门店信息
 */
function edit(storeId){
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/scm/store/find',{id:storeId},function(data){
	    setForm(data);   
	    editInfo("storeDiv","修改",function(){
	    	if(JY.Validate.form("storeInfoForm")){
				var that =$(this);
				JY.Ajax.doRequest("storeInfoForm",jypath +'/scm/store/update',null,function(data){
					that.dialog("close");
					JY.Model.info(data.resMsg,function(){search();});	
				});
				
	    	}
	    });
	});
}
/*
 * 删除门店
 */
function del(storeId){
	JY.Model.confirm("确认删除吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/store/del',{id:storeId},function(data){
			JY.Model.info(data.resMsg,function(){search();});
		});
	});
}
