$(function(){
	JY.Dict.setSelect("selectRank","rank",2,"职级选择");
	JY.Dict.setSelect("selectGrade","grade",2,"等级选择");
	JY.Dict.setSelect("selectUserType","userType",2,"类型选择");
	JY.Dict.setSelect("selectOrgGrade","orgGrade",2,'请选择');
	JY.Dict.selectData("provincees","cityes","countyes");
	
	initOrgSelectData();
	JY.Ajax.doRequest(null,jypath +'/backstage/district/getDistrictTree',{status:"1"},function(data){
		var setting={rootId:'disList',displayId:'disInput',data:data.obj,clickFn:function(node){
			$("#auOrgForm input[name$='distcode']").val(node.id);
		},isExpand:true};
		$.jy.dropTree.init(setting);
	});
	//时间渲染
	$("#dateRender").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd',
		changeYear: true,
		changeMonth:true,
		onSelect: function(dateText, inst) { }
	});
});

function backwardOrg(){
	
}
function forwardOrg(){
	if(JY.Validate.form("auOrgForm")){
		JY.Ajax.doRequest("auOrgForm",jypath +'/scm/initialization/org/addOrg',null,function(data){
			if(JY.Object.notEmpty(data.resMsg) !=""){
				alert(data.resMsg);
				JY.Model.info(data.resMsg);
			}else{
				var l=data.obj;
				var orgId=l.orgId;
				var orgName=$("#auOrgForm input[name='name']").val();
				var distCode=l.distCode;
				$("#auOrgForm input[name='id']").val(l.id)
				$("#auRoleForm input[name='orgId']").val(orgId);
				$("#auRoleForm input[name='orgName']").val(orgName);
				$("#auUserForm input[name='orgId']").val(orgId);
				$("#auUserForm input[name='orgName']").val(orgName);
				$("#auUserForm input[name='company']").val(orgId);
				$("#auUserForm input[name='companyName']").val(orgName);
				$("#warForm input[name='orgId']").val(orgId);
				$("#warForm input[name='orgName']").val(orgName);
				$("#warForm input[name='distcode']").val(distCode);
				$("#auOrgDiv").addClass('hide');
				$("#auRoleDiv").removeClass('hide');
			}
		});
	}
	
}
function forwardRole(){
	if(JY.Validate.form("auRoleForm")){
		JY.Ajax.doRequest("auRoleForm",jypath +'/scm/initialization/store/addRole',null,function(data){
			if(JY.Object.notEmpty(data.resMsg) !=""){
				JY.Model.info(data.resMsg)
			}else{
				var l=data.obj;
				$("#auRoleForm input[name='id']").val(l.id)
			    $("#auUserDiv").removeClass('hide');
			    $("#auRoleDiv").addClass('hide');
			}
		});
	}
}
function backwardUser(){
	$("#auRoleDiv").removeClass('hide');
	$("#auUserDiv").addClass('hide');
}
function forwardUser(){
	if(JY.Validate.form("auUserForm")){
		 JY.Ajax.doRequest("auUserForm",jypath +'/scm/initialization/store/addUser',null,function(data){
			 if(JY.Object.notEmpty(data.resMsg) !=""){
				 JY.Model.info(data.resMsg)
			 }else{
				 var l=data.obj;
				 $("#auUserForm input[name='id']").val(l.id)
				 $("#warDiv").removeClass('hide');
				 $("#auUserDiv").addClass('hide');
			 }
		 });
	 }
}
function backwardWar(){
	$("#auUserDiv").removeClass('hide');
	$("#warDiv").addClass('hide');
}
function forwardWar(){
	if(JY.Validate.form("warForm")){
		 JY.Ajax.doRequest("warForm",jypath +'/scm/initialization/store/addWar',null,function(data){
			 $("#warDiv").addClass('hide');
			 $("#auOrgDiv").removeClass('hide');
			 claenForm();
		 });
	 }
//	$("#warLoDiv").removeClass('hide');
//	$("#warDiv").addClass('hide');
}
function backwardWarLo(){
	$("#warDiv").removeClass('hide');
	$("#warLoDiv").addClass('hide');
}
function forwardWarLo(){
	
}
function claenForm(){
	JY.Tags.cleanForm("auOrgForm");
	JY.Tags.cleanForm("auRoleForm");
	JY.Tags.cleanForm("auUserForm");
	JY.Tags.cleanForm("warForm");
}

/*******组件：选择组织机构 start*******/
var preisShow=false;//窗口是否显示
function showOrgComp() {
	if(preisShow){
		hideOrgComp();
	}else{
		var obj = $("#preOrg");
		var offpos = $("#preOrg").position();
		$("#preOrgContent").css({left:offpos.left+"px",top:offpos.top+obj.heith+"px"}).slideDown("fast");	
		preisShow=true;
	}
}
function hideOrgComp(){
	$("#preOrgContent").fadeOut("fast");
	preisShow=false;
}
function emptyOrgComp(){
	$("#preOrg").prop("value","");
	$("#auOrgForm input[name$='pId']").prop("value","");
}
function initOrgSelectData(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getPreOrgTree',null,function(data){
		//设置
		
		$.fn.zTree.init($("#preOrgTree"),{view:{dblClickExpand:false,selectedMulti:false,nameIsHTML:true},data:{simpleData:{enable: true}},callback:{onClick:clickPreOrg}},data.obj);
	});
}
function clickPreOrg(e, treeId, treeNode) {
//	if(treeNode.orgGrade!="1")return;
	var zTree = $.fn.zTree.getZTreeObj("preOrgTree"),
	nodes = zTree.getSelectedNodes(),v ="",n ="";	
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";//获取name值
		n += nodes[i].id + ",";	//获取id值
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (n.length > 0 ) n = n.substring(0, n.length-1);
	$("#preOrg").prop("value",v);
	$("#auOrgForm input[name$='pId']").prop("value",n);
	$("#auOrgForm input[name$='orgName']").prop("value",v);
	//因为单选选择后直接关闭，如果多选请另外写关闭方法
	hideOrgComp();
}
/*******组件：选择组织机构 end*******/