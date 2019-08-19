$(function () {
	//加载菜单树
	loadOrgTree();
	//下拉框
	initOrgSelectData();
	JY.Dict.setSelect("selectAttrType","ATTRGROUPTYPE");
	
	getbaseList();
	
	checkRender = renderCheckHtml();
	
	//增加回车事件
	$("#attrBaseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	//添加属性组
	$('#addBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		cleanForm();
		$("#attrGroupForm select[name$='type']").val("DEFAULT");
		$("#cateGoryInput").prop("value",$("#attrBaseForm input[name$='categoryName']").val());
		$("#attrGroupForm input[name$='categoryId']").prop("value",$("#attrBaseForm input[name$='categoryId']").val());
		var obj={},crowd = [],params={};
		$("#chbox").append(checkRender);
		$(".pigchoice>input").click(function() {
			crowd = [];
			$(".pigchoice>input:checkbox:checked").each(function() {
					obj ={};
					obj.attrdictId = this.value;
					crowd.push(obj);
			});
		});
		editInfo({
			id:"attrGroupDiv",
			title:"新增属性组",
			height:"720",
			width:"900",
			savefn:function(){
				var that = $(this);
				if(JY.Validate.form("attrGroupForm")){
					var sort = $("#attrGroupForm input[name$='sort']").val();
					 var name = $("#attrGroupForm input[name$='name']").val();
					 var categoryId = $("#attrGroupForm input[name$='categoryId']").val();
					 var description = $("#attrGroupForm textarea[name$='description']").val();
					 var type=$("#attrGroupType").val();
					  params ={sort:sort,name:name,categoryId:categoryId,description:description,type:type,items:crowd};	
					  $.ajax({type:'POST',url:jypath+'/backstage/attribute/add',data:JSON.stringify(params),dataType:'json',contentType:"application/json",success:function(data,textStatus){
				        	 if(data.res==1){
				        		 that.dialog("close");      
				        		JY.Model.info(data.resMsg,function(){search();});
				        	 }else{
				        		 JY.Model.error(data.resMsg);
				        	 }     	
				         }
				     }); 
				}
			}				 				
		});
	});
	
});

function cleanForm(){
	JY.Tags.cleanForm("attrGroupForm");
	JY.Tools.formDisabled("attrGroupForm",false,function(){
		$("#attrGroupForm .icon-remove").show();
		$("#itemAttrAdd").show();
	});
	$("#attrGroupForm input[name$='sort']").val('');
	$("#chbox tbody").empty();
}

function search(){
	$("#searchBtn").trigger("click");
}


/****左区组织菜单start*****/
function loadOrgTree(){
	JY.Ajax.doRequest(null,jypath +'/scm/moudle/getCgyTree',null,function(data){
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
	$("#attrBaseForm input[name$='categoryId']").val(treeNode.id);
	$("#attrBaseForm input[name$='categoryName']").val(treeNode.name);
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
		var obj = $("#cateGoryInput");
		var offpos = $("#cateGoryInput").position();
		$("#categoryContentList").css({left:offpos.left+"px",top:offpos.top+obj.heith+"px"}).slideDown("fast");	
		preisShow=true;
	}
}
function hideOrgComp(){
	$("#categoryContentList").fadeOut("fast");
	preisShow=false;
}
function emptyOrgComp(){
	$("#cateGoryInput").prop("value","");
	$("#attrGroupForm input[name$='pId']").prop("value","");
}
function initOrgSelectData(){
	JY.Ajax.doRequest(null,jypath +'/scm/moudle/getCgyTree',null,function(data){
		//设置
		$.fn.zTree.init($("#selectCategoryTree"),{view:{dblClickExpand:false,selectedMulti:false,nameIsHTML:true},data:{simpleData:{enable: true}},callback:{onClick:clickPreOrg}},data.obj);
	});
}
function clickPreOrg(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("selectCategoryTree"),
	nodes = zTree.getSelectedNodes(),v ="",n ="";	
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";//获取name值
		n += nodes[i].id + ",";	//获取id值
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (n.length > 0 ) n = n.substring(0, n.length-1);
	$("#cateGoryInput").prop("value",v);
	$("#attrGroupForm input[name$='categoryId']").prop("value",n);
	$("#attrGroupForm input[name$='categoryName']").prop("value",v);
	//因为单选选择后直接关闭，如果多选请另外写关闭方法
	hideOrgComp();
}
/*******组件：选择组织机构 end*******/

/**组装并加载table数据列表**/
function getbaseList(init){
	if(init==1)$("#attrBaseForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("attrBaseForm",jypath +'/backstage/attribute/findByPage',null,function(data){
		 $("#attrList tbody").empty();
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
            		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.categoryName)+"</td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.name)+"</td>";
            		 html+=JY.Tags.setFunction(l.id,permitBtn);
            		 html+="</tr>";		 
            	 } 
        		 $("#attrList tbody").append(html);
        		 JY.Page.setPage("attrBaseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
        		$("#attrList tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
 	 
    	 JY.Model.loadingClose();
	 });
}

function viewInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
}
function editInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
}

function view(id){
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/backstage/attribute/find',{id:id},function(data){
		setForm(data,false);
		var _crowd,_obj={};
		$("#chbox").append(checkRender);
		//数据回显 勾选
		$.each(data.obj.items,function(index,element){
			$.each($("#chbox").find("input:checkbox"),function(i,e){
				if(element.attrdictId == e.value){
					e.checked="checked";
					return;
				}
			});
		});
		viewInfo({id:"attrGroupDiv",title:"查看属性组",height:"720",width:"900"})	
		JY.Tools.formDisabled("attrGroupForm",true,function(){
			$("#attrGroupForm .icon-remove").hide();
			$("#itemAttrAdd").hide();
		});
	});
}

function edit(id){
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/backstage/attribute/find',{id:id},function(data){
		setForm(data,true);
		var _crowd,_obj={};
		$("#chbox").append(checkRender);
		//数据回显 勾选
		$.each(data.obj.items,function(index,element){
			$.each($("#chbox").find("input:checkbox"),function(i,e){
				if(element.attrdictId == e.value){
					e.checked="checked";
					return;
				}
			});
		});
		
		$(".pigchoice>input").click(function() {
			_crowd = []
			$(".pigchoice>input:checkbox:checked").each(function() {
					_obj={};
					_obj.attrdictId = this.value;
					_crowd.push(_obj);
			});
		});
		editInfo({
			id:"attrGroupDiv",
			title:"新增属性组",
			height:"720",
			width:"900",
			savefn:function(){
				var that = $(this);
				if(JY.Validate.form("attrGroupForm")){
					 var id = $("#attrGroupForm input[name$='id']").val();
					 var sort = $("#attrGroupForm input[name$='sort']").val();
					 var name = $("#attrGroupForm input[name$='name']").val();
					 var categoryId = $("#attrGroupForm input[name$='categoryId']").val();
					 var description = $("#attrGroupForm textarea[name$='description']").val();
					 var type=$("#attrGroupType").val();
					  params ={id:id,sort:sort,name:name,categoryId:categoryId,description:description,type:type,items:_crowd};	
					  $.ajax({type:'POST',url:jypath+'/backstage/attribute/add',data:JSON.stringify(params),dataType:'json',contentType:"application/json",success:function(data,textStatus){
				        	 if(data.res==1){
				        		 that.dialog("close");      
				        		JY.Model.info(data.resMsg,function(){search();});
				        	 }else{
				        		 JY.Model.error(data.resMsg);
				        	 }     	
				         }
				     }); 
				}
			}				 				
		});
	});
}

function setForm(data,flag){
	var l=data.obj;
	$("#attrGroupForm input[name$='id']").val(l.id);
	$("#attrGroupForm input[name$='name']").val(JY.Object.notEmpty(l.name));
	$("#attrGroupForm input[name$='sort']").val(JY.Object.notEmpty(l.sort));
	$("#attrGroupForm input[name$='categoryId']").prop("value",JY.Object.notEmpty(l.categoryId));
	$("#attrGroupForm input[name$='categoryName']").prop("value",JY.Object.notEmpty(l.categoryName));
	$("#cateGoryInput").prop("value",JY.Object.notEmpty(l.categoryName));
	$("#attrGroupForm select[name$='type']").val(l.type);
	$("#attrGroupForm textarea[name$='description']").val(JY.Object.notEmpty(l.description));
}

var checkRender;
function renderCheckHtml(){
	var input = "";
	$.ajax({
        type: 'POST',
        url: jypath + '/scm/attrdict/getByName',
        data: {},
        dataType: 'json',
        async: false,
        success: function(result, textStatus) {	
        	$.each(result.obj,function(i,obj){
    			input+="<tr style='margin:7px;float:left;width:90px;'>";
    			input+="<td class='pigchoice'><input type=checkbox  value="+obj.id+" /><lable>"+obj.name+"</lable></td>";
    			input+="</tr>"
    		});
        }
    });
	return input;
}

	
