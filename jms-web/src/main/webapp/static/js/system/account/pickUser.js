$(function () {
//	loadOrgTree();
});

function getUserList(init){
	if(init==1)$("#userForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("userForm",jypath +'/backstage/account/findByPage',null,function(data){
		 $("#userTable tbody").empty();
        	 var obj=data.obj;
        	 var list=obj.list;
        	 var results=list.results;
        	 var permitBtn=obj.permitBtn;
         	 var pageNum=list.pageNum,pageSize=list.pageSize,totalRecord=list.totalRecord;
        	 var html="";
        	 var pickUserId="";
        	 pickUserId=$("#pickUserId").val();
    		 if(results!=null&&results.length>0){
        		 var leng=(pageNum-1)*pageSize;//计算序号
        		 for(var i = 0;i<results.length;i++){
            		 var l=results[i];
            		 html+="<tr>";
            		 if(pickUserId.indexOf(l.accountId)==-1){
            			 html+="<td class='center'><label> <input type='checkbox' name='ids' id='"+l.accountId+"' value='"+l.accountId+"' class='ace' onclick='pickUserClick(&apos;"+l.accountId+"&apos;,&apos;"+l.name+"&apos;)' /> <span class='lbl'></span></label></td>";
            		 }else{
            			 html+="<td class='center'><label> <input type='checkbox' checked='checked' id='"+l.accountId+"' name='ids' value='"+l.accountId+"' class='ace' onclick='pickUserClick(&apos;"+l.accountId+"&apos;,&apos;"+l.name+"&apos;)' /> <span class='lbl'></span></label></td>";
            		 }
            		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.name)+"</td>";
            	 } 
        		 $("#userTable tbody").append(html);
        		 JY.Page.setPage("userForm","userPageing",pageSize,pageNum,totalRecord,"getUserList");
        	 }else{
        		html+="<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
        		$("#userTable tbody").append(html);
        		$("#userPageing ul").empty();//清空分页
        	 }	
 	 
    	 JY.Model.loadingClose();
	 });
}
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
	$("#userForm input[name$='orgId']").val(treeNode.id);
	$("#userForm input[name$='_orgName']").val(treeNode.name);
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
	getUserList(1);
}
function pickUserClick(userId,userName){
	var userCh=$("#"+userId);
	var pickUserId=$("#pickUserId").val();
	var pickUserName=$("#pickUserName").val();
	if(userCh.is(':checked')){
		if(JY.Object.notEmpty(pickUserName)==""){
			pickUserName+=userName;
			pickUserId+=userId;
		}else{
			pickUserName+= ","+userName;
			pickUserId+= ","+userId;
		}
		
	}else{
//		pickUserName=pickUserName.replace(userName+",","");
//		pickUserId=pickUserId.replace(userId+",","");
		
		var pickUserNames =pickUserName.split(",");
		var pickUserIds=pickUserId.split(",");
		for(var i=0; i<pickUserNames.length; i++) {
			if(pickUserNames[i] == userName) {
				pickUserNames.splice(i, 1);
				pickUserIds.splice(i, 1);
				break;
			}
		}
		pickUserName=pickUserNames.toString();
		pickUserId=pickUserIds.toString();
		
	}
	$("#pickUserName").val(pickUserName);
	$("#pickUserId").val(pickUserId);
}
function pickUserInfo(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,modal:true,height:700,width:720,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
}