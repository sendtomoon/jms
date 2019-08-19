$(function () {
	//下拉框
	JY.Dict.setSelect("selectisValid","isValid",2,"全部");
	JY.Dict.setSelect("selectRank","rank",2,"职级选择");
	JY.Dict.setSelect("selectGrade","grade",2,"等级选择");
	JY.Dict.selectRender(jypath+'/backstage/account/role/select',"authRoleList",2,"选择角色");
	JY.Dict.setSelect("selectUserType","userType",2,"类型选择");
	loadOrgTree();
	initOrgSelectData()//
	//getbaseList();
	//增加回车事件
	$("#baseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	$("#orgContentList").mouseleave(function(){
		  hideOrgComp();
	});
	//新加
	$('#addBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		cleanForm();	
		loadRoleTree();
		var _orgId = $("#baseForm input[name$='orgId']").val();
		var _orgName = $("#baseForm input[name$='_orgName']").val();
		$("#auForm input[name$='orgId']").prop("value",_orgId);
		$("#auForm input[name$='orgName']").prop("value",_orgName);
		$("#orgInput").prop("value",_orgName);
		editInfo("auDiv","新增",function(){
			if(JY.Validate.form("auForm")){
				var that =$(this);
				var flag = false;
				$('#auForm input[name="sex"]').each(function(){if(this.checked)flag = true;}); 
				if(!flag){
					$("#sexLabel").tips({side:1,msg : "必要字段！",bg :'#FF2D2D',time:1});$("#sexLabel").focus();
				}else{
					JY.Ajax.doRequest("auForm",jypath +'/backstage/account/add',null,function(data){
					     that.dialog("close");      
					     JY.Model.info(data.resMsg,function(){search();});
				    });
				}
			}	
		});
	});
	//批量删除
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
			JY.Model.confirm("确认要删除选中的数据吗?",function(){	
				JY.Ajax.doRequest(null,jypath +'/backstage/account/logicBatchDel',{chks:chks.toString()},function(data){
					JY.Model.info(data.resMsg,function(){search();});
				});
			});		
		}		
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
function search(){
	$("#searchBtn").trigger("click");
}
function loadRoleTree(){
	JY.Ajax.doRequest(null,jypath +'/backstage/account/roleTree',null,function(data){
		$.fn.zTree.init($("#roleTree"),{view:{dblClickExpand:false,selectedMulti:false,nameIsHTML:true},data:{simpleData:{enable: true}},callback:{onClick:clickRole}},data.obj);
	});
}
function emptyRole(){
	$("#roleName").prop("value","");
	$("#auForm input[name$='roleId']").prop("value","0");
}
var preisShow=false;//窗口是否显示
function showRole() {
	if(preisShow){
		hideRole();
	}else{
		var obj = $("#roleName");
		var offpos = $("#roleName").position();
		$("#roleContent").css({left:offpos.left+"px",top:offpos.top+obj.heith+"px"}).slideDown("fast");	
		preisShow=true;
	}
}
function clickRole(e, treeId, treeNode) {
	var check = (treeNode && !treeNode.isParent);
	if(check){
		var zTree = $.fn.zTree.getZTreeObj("roleTree"),
		nodes = zTree.getSelectedNodes(),v ="",n ="",o="",p="";	
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";//获取name值
			n += nodes[i].id + ",";//获取id值
			o += nodes[i].other + ",";//获取自定义值
			var pathNodes=nodes[i].getPath();
			for(var y=0;y<pathNodes.length;y++){
				p+=pathNodes[y].name+"/";//获取path/name值
			}
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);	
		if (n.length > 0 ) n = n.substring(0, n.length-1);
		if (o.length > 0 ) o = o.substring(0, o.length-1);
		if (p.length > 0 ) p = p.substring(0, p.length-1);
		if(o=='r'){//判断是否角色	
			$("#roleName").val(p);
			n=n.replace("role","");
			$("#auForm input[name$='roleId']").prop("value",n);
			//因为单选选择后直接关闭，如果多选请另外写关闭方法
			hideRole();
		}	
	}
}
function hideRole(){
	$("#roleContent").fadeOut("fast");
	preisShow=false;
}
function getbaseList(init){
	if(init==1)$("#baseForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("baseForm",jypath +'/backstage/account/findByPage',null,function(data){
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
            		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.accountId+"' class='ace' /> <span class='lbl'></span></label></td>";
            		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.loginName)+"</td>";
            		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.name)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.company)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.email)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.mobile)+"</td>";
            		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.orgName)+"</td>";
            		 if(l.orgGrade=="0")html+="<td class='center hidden-480' ><span class='label label-warning arrowed arrowed-right'>公司</span></td>";
            		 else if(l.orgGrade=="1")html+="<td class='center hidden-480' ><span class='label label-warning arrowed arrowed-right'>部门</span></td>";
            		 else if(l.orgGrade=="4")html+="<td class='center hidden-480' ><span class='label label-warning arrowed arrowed-right'>门店</span></td>";
            		 if(l.isValid==1) html+="<td class='center hidden-480'><span class='label label-sm label-success'>有效</span></td>";
            		 else             html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>无效</span></td>";
            		 html+=JY.Tags.setFunction(l.accountId,permitBtn);
            		 html+="</tr>";		 
            	 } 
        		 $("#baseTable tbody").append(html);
        		 JY.Page.setPage("baseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='11' class='center'>没有相关数据</td></tr>";
        		$("#baseTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
 	 
    	 JY.Model.loadingClose();
	 });
}
function check(accountId){
	cleanForm();
	
	JY.Ajax.doRequest(null,jypath +'/backstage/account/roleTree',null,function(data){
		$.fn.zTree.init($("#roleTree"),{view:{dblClickExpand:false,selectedMulti:false,nameIsHTML:true},data:{simpleData:{enable: true}},callback:{onClick:clickRole}},data.obj);
		JY.Ajax.doRequest(null,jypath +'/backstage/account/find',{accountId:accountId},function(data){ 
			setForm(data);
			viewInfo("auDiv");
			JY.Tools.formDisabled("auForm",true,function(){
				$("#auForm .icon-remove").hide();
				$("#auForm .ui-datepicker-trigger").hide();
			});
		});
	});
}
function del(accountId){
	JY.Model.confirm("确认删除吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/backstage/account/logicDel',{accountId:accountId},function(data){
			JY.Model.info(data.resMsg,function(){search();});
		});
	});
}
function edit(accountId){
	cleanForm();
		JY.Ajax.doRequest(null,jypath +'/backstage/account/find',{accountId:accountId},function(data){
		    setForm(data);   
		    editInfo("auDiv","修改",function(){
		    	if(JY.Validate.form("auForm")){
					var that =$(this);
					JY.Ajax.doRequest("auForm",jypath +'/backstage/account/update',null,function(data){
					    that.dialog("close");
					    JY.Model.info(data.resMsg,function(){search();});	
					});
				}	
		    });
		});
}
function resetPwd(accountId){
	$("#resetPwdFrom input[name$='accountId']").val(accountId);//类型
	$("#resetPwdFrom input[name$='pwd']").val('');//类型
	$("#resetPwdDiv").removeClass('hide').dialog({resizable: false,modal:true,title:"<div class='widget-header'><h4 class='smaller'>密码重置</h4></div>",title_html: true,
		buttons: [
		  {  
			 html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class" : "btn btn-primary btn-xs",
			 click: function() {
				 if(JY.Validate.form("resetPwdFrom")){
					 var that =$(this);
					 JY.Ajax.doRequest("resetPwdFrom",jypath +'/backstage/account/resetPwd',null,function(data){
						 that.dialog("close");
			        	JY.Model.info(data.resMsg,function(){search();});		
					 });
				 }		
			}
		  },
		   {
			 html: "<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",
			 click: function() {
				$(this).dialog("close");
			 }
		   }
		]
	});
}
function cleanForm(){
	JY.Tags.isValid("auForm","1");
	JY.Tags.cleanForm("auForm");
	//复原下拉框，角色，职级，等级
	$("#grade").val('');
	$("#rank").val('');
	$("#userType").val('');
	$("#auForm input[name$='mobile']").val('');
	$("#auForm input[name$='loginName']").prop("readonly",false); 
	$("#auForm input[name$='emercontractNm']").val('');
	JY.Tools.formDisabled("auForm",false,function(){
		$("#auForm .icon-remove").show();
		$("#auForm .ui-datepicker-trigger").show();
	});
	hideRole();
}
function setForm(data){
	var l=data.obj;
	$("#auForm input[name$='accountId']").val(l.accountId);
	JY.Tags.isValid("auForm",(JY.Object.notNull(l.isValid)?l.isValid:"0"));
	$("#auForm input[name$='loginName']").val(JY.Object.notEmpty(l.loginName));
	$("#auForm input[name$='loginName']").prop("readonly",true); 
	$("#auForm input[name$='name']").val(JY.Object.notEmpty(l.name));
	$("#auForm input[name$='email']").val(JY.Object.notEmpty(l.email));
	$("#auForm textarea[name$='description']").val(JY.Object.notEmpty(l.description));//描述
	$("#auForm input[name$='orgId']").prop("value",JY.Object.notEmpty(l.orgId));
	$("#auForm input[name$='orgName']").prop("value",JY.Object.notEmpty(l.orgName));
	$("#orgInput").prop("value",JY.Object.notEmpty(l.orgName));
	$("#auForm input[name$='pId']").prop("value",JY.Object.notEmpty(l.orgId));
	$("#auForm input[name$='company']").val(JY.Object.notEmpty(l.company));
	$("#auForm input[name$='userNo']").val(JY.Object.notEmpty(l.userNo));
	$("#auForm input[name$='mobile']").val(JY.Object.notEmpty(l.mobile));
	$("#userType").val(JY.Object.notEmpty(l.type));
	$("#rank").val(JY.Object.notEmpty(l.rank));
	$("#grade").val(JY.Object.notEmpty(l.grade));
	$("#auForm input[name$='birthdaty']").val(JY.Object.notEmpty(l.birthdaty));
	$("#auForm input[name$='emercontractor']").val(JY.Object.notEmpty(l.emercontractor));
	$("#auForm input[name$='emercontractNm']").val(JY.Object.notEmpty(l.emercontractNm));
	$("#auForm input[name$='address']").val(JY.Object.notEmpty(l.address));
	if(l.sex=='0') $("#auForm input[name$='sex'][value=0]").attr("checked",'checked');
	if(l.sex=='1') $("#auForm input[name$='sex'][value=1]").attr("checked",'checked');
	$("#roleId").val(JY.Object.notEmpty(l.roleId));
	$("#auForm input[name$='roleId2']").val(JY.Object.notEmpty(l.roleId));
}
/****左区组织菜单*****/
function loadOrgTree(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getOrgTree',null,function(data){
		//设置
		$.fn.zTree.init($("#orgTree"),{view:{selectedMulti:false,fontCss:{color:"#393939"}},data:{simpleData: {enable: true}},callback:{onClick:clickOrg}},data.obj);
		var treeObj = $.fn.zTree.getZTreeObj("orgTree");
		var nodes = treeObj.getNodes();
		//treeObj.expandAll(true);
		treeObj.expandNode(nodes[0],true,false,false,false);
		if(nodes.length>0){
			//默认选中第一个
			treeObj.selectNode(nodes[0]);
			clickOrg(null,null,nodes[0]);
		}
	});
}
function clickOrg(event,treeId,treeNode) {
	$("#baseForm input[name$='orgId']").val(treeNode.id);
	$("#baseForm input[name$='_orgName']").val(treeNode.name);
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
/****左区组织菜单*****/

function viewInfo(id,title,fn){	
	$("#"+id).removeClass('hide').dialog({resizable:false,height:570,width:720,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(fn) == 'function'){fn.call(this);}}}]});
}
function editInfo(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,height:570,width:720,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
}

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
	$("#auForm input[name$='pId']").prop("value","");
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
	$("#auForm input[name$='pId']").prop("value",n);
	$("#auForm input[name$='orgId']").prop("value",n);
	$("#auForm input[name$='orgName']").prop("value",v);
	//因为单选选择后直接关闭，如果多选请另外写关闭方法
	hideOrgComp();
}
/*orgId参数*/
function authRole(accountId){
	JY.Ajax.doRequest(null,jypath +'/backstage/account/roleList',{accountId:accountId},function(data){
		var zNodes=data.obj;
		var setting = {check: {enable: true},data:{simpleData:{enable: true}}};
	    $.fn.zTree.init($("#userRoleTree"),setting,zNodes);	
		JY.Model.edit("authRoles","角色授权",function(){
			var that = $(this);
	        var zTree = $.fn.zTree.getZTreeObj("userRoleTree"),
			nodes = zTree.getCheckedNodes(),roles="";
			for (var i=0, l=nodes.length; i<l; i++) {
				roles += nodes[i].id + ",";
			}
			if (roles.length > 0 ) roles = roles.substring(0, roles.length-1);
			JY.Ajax.doRequest(null,jypath +'/backstage/account/saveRoles',{userId:accountId,roles:roles},function(result){
				that.dialog("close");
	        	JY.Model.info(result.resMsg,function(){search();});
			})
		});
	});
	
}


