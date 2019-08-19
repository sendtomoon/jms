$(function () {
	JY.Dict.setSelect("selectisValid","isValid",2,'全部');
	JY.Dict.setSelect("selectOrgType","orgType",2,'请选择');
	JY.Dict.setSelect("selectOrgGrade","orgGrade",2,'请选择');
	loadOrgTree();
	OrgData("orgInput","auOrgForm","distcode");
	//增加回车事件
	$("#baseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 }
	});
	//form显示选择
	$("#authorityDiv button").bind('click', function(){		
		$("#authorityDiv button").removeClass("btn-success");
		$(this).addClass("btn-success");
		var layer=$(this).val();
		$("#authorityDiv input[name$='layer']").val(layer);
		//重新加载资源
		var auth=$("#authorityDiv input[name$='auth']").val();
		if(auth=='org'){
			var orgId=$("#baseForm input[name$='orgId']").val();
			JY.Ajax.doRequest("",jypath +'/backstage/org/role/orglistAuthorized',{id:orgId,layer:layer},function(data){
				$.fn.zTree.init($("#authorityTree"),{check: {enable: true,chkDisabledInherit: true},data:{simpleData:{enable: true}}},data.obj);
			});
		}else if(auth=='role'){
			var roleId=$("#authorityDiv input[name$='roleId']").val();
			JY.Ajax.doRequest(null,jypath +'/backstage/org/role/listAuthorized',{id:roleId,layer:layer},function(data){
				$.fn.zTree.init($("#authorityTree"),{check: {enable: true,chkDisabledInherit: true},data:{simpleData:{enable: true}}},data.obj);
			});
		}else{
			JY.Model.error("选择显示层级有误！");
		}
	});
	//授权机构组织
	$('#authOrgBtn').on('click', function(e) {
		cleanAuthForm();
		$("#authorityDiv input[name$='auth']").val("org");
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var orgId=$("#baseForm input[name$='orgId']").val();
		var layer=$("#authorityDiv input[name$='layer']").val();
		if(JY.Object.notNull(orgId)){
			JY.Ajax.doRequest("",jypath +'/backstage/org/role/orglistAuthorized',{id:orgId,layer:layer},function(data){
			         $.fn.zTree.init($("#authorityTree"),{check: {enable: true,chkDisabledInherit: true},data:{simpleData:{enable: true}}},data.obj);
			         JY.Model.edit("authorityDiv","机构组织授权",function(){
			        		var zTree = $.fn.zTree.getZTreeObj("authorityTree"),
		 					nodes = zTree.getCheckedNodes(),aus ="";
		 					for (var i=0, l=nodes.length; i<l; i++) {
		 						aus += nodes[i].id + ",";
		 					}
		 					if (aus.length > 0 ) aus = aus.substring(0, aus.length-1);
		 					var that =$(this);
		 					layer=$("#authorityDiv input[name$='layer']").val();
		 					JY.Ajax.doRequest(null,jypath +'/backstage/org/role/saveOrgAuthorized',{id:orgId,aus:aus,layer:layer},function(data){
		 	 					 that.dialog("close");
		 	 					 JY.Model.info(data.resMsg,function(){search();});			        		 	
		 					});
			         });
			});
		}else{
			 JY.Model.error("请选择机构组织");
		}
	});
	 function clearSelect(){
	        //获取select dom对象
	        var allSelect = document.getElementById("orgGrade");
	        //获取select下拉框下所有的选项
	        var optionArr = allSelect.getElementsByTagName("option");
	        var len = optionArr.length;
	        for(var i=0;i<len;i++){
	            //将其对应序号的option删除
	        	if(optionArr[i].innerText=="门店"){
	        		
	        		allSelect.remove(i);
	        	}
	        }
	    };
	//新加机构组织
	$('#addOrgBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		clearSelect();
		cleanOrgForm();
		loadPreOrgTree();
		var orgId=$("#baseForm input[name$='orgId']").val();
		var treeObj = $.fn.zTree.getZTreeObj("orgTree");
	    var ptreeNode=treeObj.getNodeByParam("id",orgId,null);
	    $("#preOrg").val(ptreeNode.name);
	    $("#preId").val(ptreeNode.id);
		JY.Model.edit("auOrgDiv","新增机构组织",function(){
			 if(JY.Validate.form("auOrgForm")){
				 var that =$(this);
				 JY.Ajax.doRequest("auOrgForm",jypath +'/backstage/org/role/addOrg',null,function(data){
						 that.dialog("close"); 
		        		 JY.Model.info(data.resMsg,function(){refreshOrgTree();});
				 }); 
			 }	
		});
	});
	//修改机构组织
	$('#editOrgBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		cleanOrgForm();
		clearSelect();
		$("#preOrgTR").addClass('hide');
		loadPreOrgTree();
		var orgId=$("#baseForm input[name$='orgId']").val();
		JY.Ajax.doRequest(null,jypath+'/backstage/org/role/findOrg',{id:orgId},function(data){
			if(data.res==1){
				if(data.obj.orgGrade==4){
					JY.Model.info("请到门店管理进行编辑");
					return;
				}
				setOrgForm(data);    
				JY.Model.edit("auOrgDiv","修改机构组织",function(){		
					if(JY.Validate.form("auOrgForm")){
						var that =$(this);
						JY.Ajax.doRequest("auOrgForm",jypath +'/backstage/org/role/updateOrg',null,function(data){
							that.dialog("close"); 
				        	JY.Model.info(data.resMsg,function(){refreshOrgTree();});     
						}); 
					}	
				});
			}else{
	    		JY.Model.info(data.resMsg);
	    	}   
		});		
	});
	function editInfo(id,title,savefn,cancelfn){
		$("#"+id).removeClass('hide').dialog({resizable:false,modal:true,width:300,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
		$("#addDiv").removeClass('hide')
	}
	//删除机构组织
	$('#delOrgBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var orgId=$("#baseForm input[name$='orgId']").val();
		var treeObj = $.fn.zTree.getZTreeObj("orgTree");
		var ptreeNode=treeObj.getNodeByParam("id",orgId,null);
		JY.Model.confirm("确认要删除【"+ptreeNode.name+"】吗？",function(){		
			JY.Ajax.doRequest(null,jypath+'/backstage/org/role/delOrg',{id:orgId},function(data){
				JY.Model.info(data.resMsg,function(){loadOrgTree();});
			});
		});	
	});
	
	//新加角色
	$('#addBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		cleanForm();
		 var orgId=$("#baseForm input[name$='orgId']").val();		
		 if(JY.Object.notNull(orgId)){
			 var treeObj = $.fn.zTree.getZTreeObj("orgTree");
		     var ptreeNode=treeObj.getNodeByParam("id",orgId,null);
			 if(JY.Object.notNull(ptreeNode.pId)&&ptreeNode.pId!="0"){
				JY.Model.edit("auDiv","新增角色",function(){
				 if(JY.Validate.form("auForm")){
					 var that =$(this);			
					 JY.Ajax.doRequest("auForm",jypath +'/backstage/org/role/add',{orgId:orgId},function(data){
			        		 that.dialog("close");      
			        		 JY.Model.info(data.resMsg,function(){search();});
					 }); 
				 }	
			});
			 }else{
				 JY.Model.error("请在二级机构组织新增角色"); 
			 }
		 }else{
			 JY.Model.error("请先选择机构组织");
		 }
	});
	//批量删除
	$('#delBatchBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#baseTable input[name="ids"]:checked').each(function(){chks.push($(this).val());});  	
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			JY.Model.confirm("确认要删除选中的角色吗?",function(){
				JY.Ajax.doRequest(null,jypath +'/backstage/org/role/delBatch',{chks:chks.toString()},function(data){
					JY.Model.info(data.resMsg,function(){search();});
				});
			});	
		}	
	});

});
function search(){
	$("#searchBtn").trigger("click");
}
function loadOrgTree(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getOrgTree',null,function(data){
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
function refreshOrgTree(){
	 JY.Model.loading();
	 JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getOrgTree',null,function(data){
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
			JY.Model.loadingClose();	 
	 });	 
}
function loadPreOrgTree(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getPreOrgTree',null,function(data){
		//设置
		$.fn.zTree.init($("#preOrgTree"),{view:{dblClickExpand:false,selectedMulti:false,nameIsHTML:true},data:{simpleData:{enable: true}},callback:{onClick:clickPreOrg}},data.obj);
	});
}
function emptyPreOrg(){
	$("#preOrg").prop("value","");
	$("#auOrgForm input[name$='pId']").prop("value","0");
}
var preisShow=false;//窗口是否显示
function showPreOrg() {
	if(preisShow){
		hidePreOrg();
	}else{
		var obj = $("#preOrg");
		var offpos = $("#preOrg").position();
		$("#preOrgContent").css({left:offpos.left+"px",top:offpos.top+obj.heith+"px"}).slideDown("fast");	
		preisShow=true;
	}
}
function clickPreOrg(e, treeId, treeNode) {
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
		//因为单选选择后直接关闭，如果多选请另外写关闭方法
		hidePreOrg();
}
function hidePreOrg(){
	$("#preOrgContent").fadeOut("fast");
	preisShow=false;
}
function clickOrg(event,treeId,treeNode) {
	$("#baseForm input[name$='orgId']").val(treeNode.id);
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
	getbaseList();
}
function getbaseList(init){
	if(init==1)$("#baseForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("baseForm",jypath +'/backstage/org/role/findByPage',null,function(data){
		 $("#baseTable tbody").empty();
    		 var obj=data.obj;
        	 var list=obj.list;
        	 var results=list.results;
         	 var pageNum=list.pageNum,pageSize=list.pageSize,totalRecord=list.totalRecord;
         	 var permitBtn=obj.permitBtn;
        	 var html="";
    		 if(results!=null&&results.length>0){			
        		 var leng=(pageNum-1)*pageSize;//计算序号
        		 for(var i = 0;i<results.length;i++){
            		 var l=results[i];
            		 html+="<tr>";
            		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
            		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.name)+"</td>";
            		 if(l.isValid==1){
            			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>有效</span></td>";
            		 }else if(l.isValid==2){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>系统角色</span></td>";
            		 }else{
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>无效</span></td>";
            		 }
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.description)+"</td>";
            		 html+=JY.Tags.setFunction(l.id,permitBtn);
            		 html+="</tr>";		 
            	 } 
        		 $("#baseTable tbody").append(html);
        		 JY.Page.setPage("baseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='6' class='center'>没有相关数据</td></tr>";
        		$("#baseTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	 	        	 
    	 JY.Model.loadingClose();	
	});
}
function authorized(id){
	cleanAuthForm();
	$("#authorityDiv input[name$='roleId']").val(id);
	$("#authorityDiv input[name$='auth']").val("role");
	var layer=$("#authorityDiv input[name$='layer']").val();
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/listAuthorized',{id:id,layer:layer},function(data){
	         //获取数据
	    	 var zNodes=data.obj;
	         //设置
	    	 var setting = {check: {enable: true,chkDisabledInherit: true},data:{simpleData:{enable: true}}};
	         $.fn.zTree.init($("#authorityTree"),setting,zNodes);	      
	         JY.Model.edit("authorityDiv","角色授权",function(){
	        		var zTree = $.fn.zTree.getZTreeObj("authorityTree"),
 					nodes = zTree.getCheckedNodes(),aus ="";
 					for (var i=0, l=nodes.length; i<l; i++) {
 						aus += nodes[i].id + ",";
 					}
 					if (aus.length > 0 ) aus = aus.substring(0, aus.length-1);
 					var that =$(this);
 					layer=$("#authorityDiv input[name$='layer']").val();
 					JY.Ajax.doRequest(null,jypath +'/backstage/org/role/saveAuthorized',{id:id,aus:aus,layer:layer},function(data){
 	 					  that.dialog("close");
 	 					  JY.Model.info(data.resMsg,function(){search();});			        		 	
 					});
	         });
	});
}
function showCode(str) {
	if (!code) code = $("#code");
	code.empty();
	code.append("<li>"+str+"</li>");
}
function check(id){
	//清空表单
	cleanForm();
	JY.Ajax.doRequest(null,jypath+'/backstage/org/role/find',{id:id},function(data){
		setForm(data);    
    	JY.Model.check("auDiv");     
	});
}
function edit(id){
	//清空表单
	cleanForm();	
	JY.Ajax.doRequest(null,jypath+'/backstage/org/role/find',{id:id},function(data){
    		setForm(data);    
    		JY.Model.edit("auDiv","修改角色",function(){
    			 if(JY.Validate.form("auForm")){
					 var that =$(this);
					 JY.Ajax.doRequest("auForm",jypath+'/backstage/org/role/update',null,function(data){
			        		 that.dialog("close");
			        		 JY.Model.info(data.resMsg,function(){search();});      				        		 	
					 });
				}
    		});  
	});
}
function del(id){
	JY.Model.confirm("确认删除角色吗？",function(){	
		JY.Ajax.doRequest(null,jypath+'/backstage/org/role/del',{id:id},function(data){
			 JY.Model.info(data.resMsg,function(){search();});
		});
	});	
}
function cleanOrgForm(){
	JY.Tags.cleanForm("auOrgForm");
	$("#auOrgForm input[name$='pId']").val('0');//上级资源
	$("#preOrgTR").removeClass('hide');
	
	var distTree = $.fn.zTree.getZTreeObj("selectorgTree");
	var nodes = distTree.getSelectedNodes();
	if (nodes.length >0) { 
		distTree.cancelSelectedNode(nodes[0]);
	}
	hidePreOrg();
}
function cleanForm(){
	JY.Tags.cleanForm("auForm");
	JY.Tags.isValid("auForm","1");
}

function setOrgForm(data){
	var l=data.obj;
	JY.Tags.isValid("auOrgForm",(JY.Object.notNull(l.status)?l.status:"0"));
	$("#auOrgForm input[name$='id']").val(JY.Object.notEmpty(l.id));
	$("#auOrgForm input[name$='name']").val(JY.Object.notEmpty(l.name));
	$("#auOrgForm input[name$='longName']").val(JY.Object.notEmpty(l.longName))
//	$("#orgType").val(JY.Object.notEmpty(l.orgType));
	$("#orgGrade").val(JY.Object.notEmpty(l.orgGrade));
	$("#auOrgForm input[name$='sort']").val(JY.Object.notEmpty(l.sort))
	$("#auOrgForm textarea[name$='description']").val(JY.Object.notEmpty(l.description));//描述
	$("#preOrg").val(JY.Object.notEmpty(l.pName));
	$("#auOrgForm input[name$='pId']").val(JY.Object.notEmpty(l.pId));//上级资源
	if(l.orgType==0){
		$("#optionsRadios1").attr("checked",true);
	}else{
		$("#optionsRadios2").attr("checked",true);
	}
	var distcode = JY.Object.notEmpty(l.distcode);
	var distTreeInputStr = "";
	var node = null;
	var distTree = $.fn.zTree.getZTreeObj("selectorgTree");
	if(null != distcode && "" != distcode){
		node = distTree.getNodeByParam("id", distcode, null);
		if(null != node){
			distTree.selectNode(node, false);
			distTreeInputStr = node.name;
		}
	}
	distTree.selectNode(node,false);
	$("#orgInput").val(distTreeInputStr);
	$("#auOrgForm input[name$='distcode']").val(distcode);

	$("#auOrgForm input[name$='code']").val(JY.Object.notEmpty(l.code));
	
};  
function setForm(data){
	var l=data.obj;
	$("#auForm input[name$='id']").val(JY.Object.notEmpty(l.id));
	JY.Tags.isValid("auForm",(JY.Object.notNull(l.isValid)?l.isValid:"0"));
	$("#auForm input[name$='name']").val(JY.Object.notEmpty(l.name));
	$("#auForm textarea[name$='description']").val(JY.Object.notEmpty(l.description));//描述
}
function cleanAuthForm(){
	$("#authorityDiv input[name$='auth']").val("org");
	$("#authorityDiv button").removeClass("btn-success");
	$("#authorityDiv input[name$='layer']").val("1");
	$("#authorityDiv button[value$='1']").addClass("btn-success");
	$("#authorityDiv input[name$='roleId']").val("");
}
