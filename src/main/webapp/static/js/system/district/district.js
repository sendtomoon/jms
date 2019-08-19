$(function () {
	//下拉框
	JY.Dict.setSelect("selectisValid","isValid",2,"全部");
	//加载菜单树
	loadOrgTree();
	//初始化数据
	getbaseList();
	initOrgSelectData();
	//增加回车事件
	$("#districtListForm").keydown(function(e){
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

		$("#auForm input[name$='id']").prop("readonly",false); 
		var pid=$("#districtListForm input[name$='pid']").val();
		$("#auForm input[name$='pid']").prop("value","0");
		editInfo("auDiv","新增",function(){
			if(JY.Validate.form("auForm")){
				var that =$(this);
				var flag = false;
					JY.Ajax.doRequest("auForm",jypath +'/backstage/district/add',null,function(data){
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
		$('#baseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			JY.Ajax.doRequest(null,jypath +'/backstage/district/delBatch',{chks:chks.toString()},function(data){
				JY.Model.confirm("确认要删除选中的数据吗?",function(){
					JY.Model.info(data.resMsg,function(){
						search();
						loadOrgTree();
					});
				});		
				
			});
		
		}		
	});
	//加载时间控件
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

//分页查询
function getbaseList(){
	JY.Model.loading();
	JY.Ajax.doRequest("districtListForm",jypath +'/backstage/district/findByPage',null,function(data){
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
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.id)+"</td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.pid)+"</td>";
            		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.name)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.descrition)+"</td>";
            		 if(l.status==1) html+="<td class='center hidden-480'><span class='label label-sm label-success'>有效</span></td>";
            		 else if(l.status == 0) html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>无效</span></td>";
            		 else html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>删除</span></td>";
            		 html+=JY.Tags.setFunction(l.id,permitBtn);
            		 html+="</tr>";		 
            	 } 
        		 $("#baseTable tbody").append(html);
        		 JY.Page.setPage("districtListForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
        		$("#baseTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
 	 
    	 JY.Model.loadingClose();
	 });
}
//删除按钮
function del(id){
	JY.Ajax.doRequest(null,jypath +'/backstage/district/del',{id:id},function(data){
		JY.Model.confirm("确认删除吗？",function(){	
			JY.Model.info(data.resMsg,function(){
				search();
				loadOrgTree();
			});
			
		});
	});
	
}
//set赋值
function setForm(data){
	var l=data.obj;
	$("#auForm input[name$='id']").val(JY.Object.notEmpty(l.id));
	$("#orgInput").val(JY.Object.notEmpty(l.pName));
	$("#auForm input[name$='pid']").val(JY.Object.notEmpty(l.pid));
	$("#auForm input[name$='name']").val(JY.Object.notEmpty(l.name));
	JY.Tags.isValid("auForm",(JY.Object.notNull(l.status)?l.status:"0"));
	$("#auForm input[name$='descrition']").val(JY.Object.notEmpty(l.descrition));
	$("#auForm input[name$='sort']").val(JY.Object.notEmpty(l.sort));
	
}
//查看按钮
function check(id){
	cleanForm();
	$("#auForm input[name$='id']").prop("readonly",true);
		JY.Ajax.doRequest(null,jypath +'/backstage/district/find',{id:id},function(data){ 
			viewInfo("auDiv");
			setForm(data);
			JY.Tools.formDisabled("auForm",true,function(){
				$("#auForm .icon-remove").hide();
				$("#auForm .ui-datepicker-trigger").hide();
			});
		});
		
}
//加载查看页面
function viewInfo(id,title,fn){	
	$("#"+id).removeClass('hide').dialog({resizable:false,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"查看")+"</h4></div>",title_html:true,});
}
//加载修改页面
function editInfo(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
}
//修改按钮
function edit(id){
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/backstage/district/find',{id:id},function(data){
	    setForm(data); 
	    $("#auForm input[name$='id']").prop("readonly",true); 
	    editInfo("auDiv","修改",function(){
	    	if(JY.Validate.form("auForm")){
				var that =$(this);
				JY.Ajax.doRequest("auForm",jypath +'/backstage/district/update',null,function(data){
				    that.dialog("close");
				    JY.Model.info(data.resMsg,function(){
				    	search();
				    });	
				});
			}	
	    });
	});
}
//清空数据 
function cleanForm(){
	JY.Tags.cleanForm("auForm");
	$("#auForm input[name$='id']").val('');
	$("#orgInput").val('');
	$("#auForm input[name$='name']").val('');
	$("#auForm input[name$='descrition']").val('');
	$("#auForm input[name$='sort']").val('');
	JY.Tools.formDisabled("auForm",false,function(){
		$("#auForm .icon-remove").show();
		$("#auForm .ui-datepicker-trigger").show();
	});
}

function search(){
	$("#searchBtn").trigger("click");
}
//树形控件 clickOrg
/****左区组织菜单start*****/
function loadOrgTree(){
	JY.Ajax.doRequest(null,jypath +'/backstage/district/getDistrictTree',null,function(data){
		//设置
		$.fn.zTree.init($("#districtorgTree"),{
			view:{selectedMulti:false,fontCss:{color:"#393939"}},
			data:{
				//层级关系
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "pid",
					rootPId: '0'
					}
			},
			callback:{onClick:clickOrg}},
			data.obj);
		var treeObj = $.fn.zTree.getZTreeObj("districtorgTree");
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
	$("#districtListForm input[name$='id']").val(treeNode.id);
	$("#districtListForm input[name$='name']").val(treeNode.name);
	$("#orgName1").html("");
	$("#orgName2").html("");
	if(JY.Object.notNull(treeNode.pId)){
		var treeObj = $.fn.zTree.getZTreeObj("districtorgTree");
		var ptreeNode=treeObj.getNodeByParam("id",treeNode.pId,null);
		$("#orgName1").html(ptreeNode.name);
		$("#orgName2").html(treeNode.name);
	}else{
		$("#orgName1").html(treeNode.name);
	}
	getbaseList();

}/****左区组织菜单end*****/
/*******组件：选择地区 start*******/
//下拉窗口是否显示
var preisShow=false;
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
//清空
function emptyOrgComp(){
	$("#orgInput").prop("value","");
	$("#auForm input[name$='pId']").prop("value","");
}

function initOrgSelectData(){  
	JY.Ajax.doRequest(null,jypath +'/backstage/district/getDistrictTree',null,function(data){
		//设置
		$.fn.zTree.init($("#selectdistrictorgTree"),{view:{dblClickExpand:false,selectedMulti:false,nameIsHTML:true},
			data:{
				simpleData:{
					enable: true,
				idKey: "id",
				pIdKey: "pid",
					rootPId: '0'
				}},
			callback:{onClick:clickPreOrg}},data.obj);
	});
}
function clickPreOrg(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("selectdistrictorgTree"),
	nodes = zTree.getSelectedNodes(),v ="",n ="";	
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";//获取name值
		n += nodes[i].id + ",";	//获取id值
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (n.length > 0 ) n = n.substring(0, n.length-1);
	$("#orgInput").prop("value",v);
	$("#auForm input[name$='pid']").prop("value",n);
	//因为单选选择后直接关闭，如果多选请另外写关闭方法
	hideOrgComp();
}
/*******组件：选择组织机构 end*******/

