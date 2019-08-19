$(function () {
	JY.Dict.setSelect("selectisValid","isValid",2,'全部');
	JY.Dict.setSelect("selectType1","caType",2,'请选择');
	JY.Dict.setSelect("selectType","caType",2,'请选择');
	loadCgyTree();
	loadPreTree();
	//增加回车事件  
	$("#baseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 }
	});
	

	//新加类别 	
	$('#addCgyBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		cleanCgyForm();
		loadPreOrgTree();
		JY.Model.edit("auCgyDiv","新增类别",function(){
			 if(JY.Validate.form("auCgyForm")){
				 var that =$(this);
				 JY.Ajax.doRequest("auCgyForm",jypath +'/scm/moudle/addCgy',null,function(data){
						 that.dialog("close"); 
		        		 JY.Model.info(data.resMsg,function(){refreshCgyTree();});
				 }); 
			 }	
		});
	});
	//修改类别
	$('#editCgyBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		cleanCgyForm();
		loadPreOrgTree();
		var categoryid=$("#baseForm input[name$='categoryid']").val();
		JY.Ajax.doRequest(null,jypath+'/scm/moudle/findCgy',{id:categoryid},function(data){
			if(data.res==1){
				setCgyForm(data);    
				JY.Model.edit("auCgyDiv","修改类别",function(){		
					 if(JY.Validate.form("auCgyForm")){
						 var that =$(this);
						 JY.Ajax.doRequest("auCgyForm",jypath +'/scm/moudle/updateCgy',null,function(data){
								 that.dialog("close"); 
				        		 JY.Model.info(data.resMsg,function(){refreshCgyTree();});     
						 }); 
					 }	
				});
			}else{
	    		JY.Model.info(data.resMsg);
	    	}   
		});		
	});
	
	//删除类别
	$('#delCgyBtn').on('click', function(e) {
		e.preventDefault();
		var categoryid=$("#baseForm input[name$='categoryid']").val();
		var treeObj = $.fn.zTree.getZTreeObj("categoryTree");
		var ptreeNode=treeObj.getNodeByParam("id",categoryid,null);
		JY.Model.confirm("确认要删除【"+ptreeNode.name+"】吗？",function(){		
			JY.Ajax.doRequest(null,jypath+'/scm/moudle/delCgy',{id:categoryid},function(data){
				JY.Model.info(data.resMsg,function(){refreshCgyTree();});
			});
		});	
	});
	
	//新加
	$('#addBtn').on('click', function(e) {
		e.preventDefault();
		move_dom("#wrapper","#moudleDiv");
		uploader.addButton({
			id : '#filePicker2',
			label : '点击选择图片'
		});
		cleanForm();
		var categoryid=$("#baseForm input[name$='categoryid']").val();
		if(JY.Object.notNull(categoryid)){
			$("#auForm input[name$='categoryid']").val(categoryid);
			JY.Module.Field.load("auForm",{cid:categoryid,type:'MOUDLE'},2);
			editInfo("auDiv","新增",function(){
				 if(JY.Validate.form("auForm")){
					 var that =$(this);
					 JY.Module.Field.save("auForm",jypath +'/scm/moudle/add',function(){
						 that.dialog("close"); 
						 search();
					 });
				 }	
			});
		}else{
			JY.Model.error("请先选类别");
		}
	});
	//批量删除
	$('#delBatchBtn').on('click', function(e) {
		e.preventDefault();
		var chks =[];    
		$('#baseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			JY.Model.confirm("确认要删除选中的数据吗?",function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/moudle/delBatch',{chks:chks.toString()},function(data){
					JY.Model.info(data.resMsg,function(){search();});
				});
			});		
		}		
	});
	
	/**
	 * 工厂款号
	 */
	/*供应商自动检索*/
	var input={};
	$("#inputName").typeahead({
		source:function(longName,process){
			$.ajax({
				type:'POST',
				url:jypath+'/scm/franchisee/findLongNamePage',
				data:{longName:longName},
				dataType:'json',
				success:function(data,textStatus){
						input={};
						var array=[];
						 $.each(data.obj, function (index, ele) {
							 input[ele.longName] = ele.id;
	                         array.push(ele.longName);
	                     });
						 process(array);
				}
			});
		},
        items: 10,
        afterSelect: function (item) {
        	$("#moForm input[name$='supplierCode']").val(input[item]);
        	$("#moForm input[name$='supplierName']").prop("readonly",true);
        },
        delay: 500,
        highlighter: function (item) {//item是用户输入
	        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')//这是将字符串安全格式化为正则表达式的源码
	            return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	                return '<strong style="color:red;">' + match + '</strong>'//将下拉列表中符合项中的LI的innerHTML相等部分加个strong标签
	            })
	        	        }
        
		
	});
	
	$('#addBtnDetail').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		uploader.addButton({
			id : '#filePicker2',
			label : '点击选择图片'
		});
		cleanDetailForm();
		move_dom("#wrapper","#detailDiv1");
		var cid=$("#baseForm1 input[name$='categoryid']").val();
		JY.Module.Field.load("moForm",{cid:cid,type:'MOUDLEDETAIL'},2);
		var _supplierDom = $("#moForm input[name$='supplierName']");
		editInfo("moDiv","新增",function(){
			 if(JY.Validate.form("moForm")){
				 var that =$(this),_supName=_supplierDom.val(),_supCode = $("#moForm input[name$='supplierCode']").val();
				 if(_supCode!="" && _supName==""){
					 _supCode = "";
				 }
				 if(_supCode=="" && _supName!=""){
					 $(_supplierDom).tips({
                         side: 1,
                         msg: "请输入有效的供应商名称！",
                         bg: '#FF2D2D',
                         time: 1
                     });
					 return false;
				 }
				 JY.Module.Field.save("moForm",jypath +'/scm/moudle/detail/add',function(data){
					 if(data.res==1){
						 that.dialog("close"); 
						 search1();
					 }
				 });
			 }	
		});
	});
	//批量删除
	$('#delBtnDetail').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#baseTable1 input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			JY.Model.confirm("确认要删除选中的数据吗?",function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/moudle/detail/delBatch',{chks:chks.toString()},function(data){
					JY.Model.info(data.resMsg,function(){search1();});
				});
			});		
		}		
	});
});
function search(){
	$("#searchBtn").trigger("click");
}
function search1(){
	$("#searchBtn1").trigger("click");
}
/*左侧类别树 start */
function loadCgyTree(){
	JY.Ajax.doRequest(null,jypath +'/scm/moudle/getCgyTree',null,function(data){
		//设置
		$.fn.zTree.init($("#categoryTree"),{view:{selectedMulti:false,fontCss:{color:"#393939"}},data:{simpleData: {enable: true}},callback:{onClick:clickOrg}},data.obj);
		var treeObj = $.fn.zTree.getZTreeObj("categoryTree");
		var nodes = treeObj.getNodes();
		if(nodes.length>0){
			//默认选中第一个
			treeObj.selectNode(nodes[0]);
			clickOrg(null,null,nodes[0]);
		}
	});
}
function refreshCgyTree(){
	 JY.Model.loading();
	 JY.Ajax.doRequest(null,jypath +'/scm/moudle/getCgyTree',null,function(data){
			//设置
			$.fn.zTree.init($("#categoryTree"),{view:{selectedMulti:false,fontCss:{color:"#393939"}},data:{simpleData: {enable: true}},callback:{onClick:clickOrg}},data.obj);
			JY.Model.loadingClose();	 
	 });	 
}
function clickOrg(event,treeId,treeNode) {
	$("#baseForm input[name$='categoryid']").val(treeNode.id);
	$("#orgName1").html("");
	$("#orgName2").html("");
	if(JY.Object.notNull(treeNode.pId)){
		var treeObj = $.fn.zTree.getZTreeObj("categoryTree");
		var ptreeNode=treeObj.getNodeByParam("id",treeNode.pId,null);
		$("#orgName1").html(ptreeNode.name);
		$("#orgName2").html(treeNode.name);
	}else{
		$("#orgName1").html(treeNode.name);
	}
	getbaseList();
}
/*左侧类别树  end*/

/*下拉选择树 start*/
function loadPreTree(){
	JY.Ajax.doRequest(null,jypath +'/scm/moudle/getPreTree',null,function(data){
		//设置
		$.fn.zTree.init($("#preTree"),{view:{dblClickExpand:false,selectedMulti:false,nameIsHTML:true},data:{simpleData:{enable: true}},callback:{onClick:clickPre}},data.obj);
	});
}
function emptyPre(){
	$("#pre").prop("value","");
	$("#auCgyForm input[name$='pid']").prop("value","0");
}
var preisShow=false;//窗口是否显示
function showPre() {
	if(preisShow){
		hidePre();
	}else{
		var obj = $("#pre");
		var offpos = $("#pre").position();
		$("#preContent").css({left:offpos.left+"px",top:offpos.top+obj.heith+"px"}).slideDown("fast");	
		preisShow=true;
	}
}
function clickPre(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("preTree"),
	nodes = zTree.getSelectedNodes(),v ="",n ="";	
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";//获取name值
		n += nodes[i].id + ",";	//获取id值
	}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		if (n.length > 0 ) n = n.substring(0, n.length-1);
		$("#pre").prop("value",v);
		$("#auForm input[name$='pid']").prop("value",n);
		//因为单选选择后直接关闭，如果多选请另外写关闭方法
		hidePre();
}
function hidePre(){
	$("#preContent").fadeOut("fast");
	preisShow=false;
}
/*下拉选择树 end*/

/*下拉选机构择树 start*/
function loadPreOrgTree(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getPreOrgTree',null,function(data){
		//设置
		$.fn.zTree.init($("#preOrgTree"),{view:{dblClickExpand:false,selectedMulti:false,nameIsHTML:true},data:{simpleData:{enable: true}},callback:{onClick:clickPreOrg}},data.obj);
	});
}
function emptyOrgPre(){
	$("#pre").prop("value","");
	$("#auCgyForm input[name$='orgid']").prop("value","0");
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
		$("#auCgyForm input[name$='orgid']").prop("value",n);
		//因为单选选择后直接关闭，如果多选请另外写关闭方法
		hidePreOrg();
}
function hidePreOrg(){
	$("#preOrgContent").fadeOut("fast");
	preisShow=false;
}
/*下拉选机构择树 end*/

function getbaseList(init){
	if(init==1)$("#baseForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("baseForm",jypath +'/scm/moudle/findByPage',null,function(data){
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
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.code)+"</td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.name)+"</td>";
            		 if(l.status==1){
            			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>有效</span></td>";
            		 }else{
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>无效</span></td>";
            		 } 
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.description)+"</td>";
            		 html+=JY.Tags.setFunction1(l.id+","+l.categoryid,permitBtn,'editDetail,findDetail,delDetail,');
            		 html+="</tr>";		 
            	 } 
        		 $("#baseTable tbody").append(html);
        		 JY.Page.setPage("baseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='7' class='center'>没有相关数据</td></tr>";
        		$("#baseTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	 	        	 
    	 JY.Model.loadingClose();	
	});
}

function showCode(str) {
	if (!code) code = $("#code");
	code.empty();
	code.append("<li>"+str+"</li>");
}
function find(ids){
	//清空表单
	cleanForm();
//	$("moudleDiv").empty();
//	$("detailDiv").empty();
//	$("moudleDiv").append('<%@include file="img.jsp" %>');
	move_dom("#wrapper","#moudleDiv");
	var id=ids.split(",");
	checkImgs(id[0]);
	JY.Ajax.doRequest(null,jypath+'/scm/moudle/find',{id:id[0]},function(data){
		setForm(data);    
		 JY.Module.Field.load("auForm",{cid:id[1],bid:id[0],type:'MOUDLE'},2);
		    viewInfo("auDiv","查看",function(){
		    	if(JY.Validate.form("auForm")){
					var that =$(this);
					
		    	}
		    });
	});
}
function edit(ids){
	//清空表单
	cleanForm();
//	$("moudleDiv").empty();
//	$("detailDiv").empty();
//	$("moudleDiv").append('<%@include file="img.jsp" %>');
	move_dom("#wrapper","#moudleDiv");
	var id=ids.split(",");
	findImgList(id[0]);
	JY.Ajax.doRequest(null,jypath+'/scm/moudle/find',{id:id[0]},function(data){
    		setForm(data);    
    		 JY.Module.Field.load("auForm",{cid:id[1],bid:id[0],type:'MOUDLE'},2);
    		    editInfo("auDiv","修改",function(){
    		    	if(JY.Validate.form("auForm")){
    					var that =$(this);
    					JY.Module.Field.save("auForm",jypath +'/scm/moudle/update',function(){
    						 that.dialog("close"); 
    						 search();
    					 });
    					
    		    	}
    		    });
	});
}
function del(ids){
	var idss=ids.split(",")
	var id=idss[0];
	JY.Model.confirm("确认删除吗？",function(){	
		JY.Ajax.doRequest(null,jypath+'/scm/moudle/del',{id:id},function(data){
			 JY.Model.info(data.resMsg,function(){search();});
		});
	});	
}
//查看明细
function edits(ids){
	var id=ids.split(",")
	var menu=$("#menuId").val();
	var menuPId=$("#menuPId").val();
	$("#baseForm1 input[name='moudleid']").val(id[0]);
	$("#baseForm1 input[name='categoryid']").val(id[1])
	
//	window.location.href=jypath+"/scm/moudle/detail/index?id="+id[0]+"&menu="+menu+"&categoryid="+id[1]+"&menuPId="+menuPId;
	getbaseList1();
	viewInfo1({id:"detailDiv",title:"工厂款号",height:"700",width:"1024"})
}
function getbaseList1(){
	JY.Model.loading();
	JY.Ajax.doRequest("baseForm1",jypath +'/scm/moudle/detail/findByPage',null,function(data){
		 $("#baseTable1 tbody").empty();
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
            		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.moudleName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.supplierName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.suppmouCode)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.laborCost)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.addLaborCost)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.saleLaborCost)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.saleLossRate)+"</td>";
            		 if(l.majorFlag==1){
            			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>是</span></td>";
            		 }else{
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>否</span></td>";
            		 } 
            		 if(l.status==1){
            			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>有效</span></td>";
            		 }else{
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>无效</span></td>";
            		 } 
            		 html+=JY.Tags.setFunction1(l.id,permitBtn,'edit,find,del,edits,');
            		 html+="</tr>";		 
            	 } 
        		 $("#baseTable1 tbody").append(html);
        		 JY.Page.setPage("baseForm1","pageing1",pageSize,pageNum,totalRecord,"getbaseList1");
        	 }else{
        		html+="<tr><td colspan='12' class='center'>没有相关数据</td></tr>";
        		$("#baseTable1 tbody").append(html);
        		$("#pageing1 ul").empty();//清空分页
        	 }	
 	 
    		 JY.Model.loadingClose();
	 });
}
function cleanCgyForm(){
	JY.Tags.cleanForm("auCgyForm");
	JY.Tags.isValid("auCgyForm","1");
	var categoryid=$("#baseForm input[name$='categoryid']").val();
	$("#auCgyForm input[name$='pid']").val(categoryid);//上级资源
	hidePreOrg();
}
function cleanForm(){
	JY.Tags.cleanForm("auForm");
	JY.Tags.isValid1("auForm","1");
	$("#imgId").val("");
	// 移除所有缩略图并将上传文件移出上传序列

	cleanImgs();
}

function setCgyForm(data){
	var l=data.obj;
	JY.Tags.isValid("auCgyForm",(JY.Object.notNull(l.status)?l.status:"0"));
	$("#auCgyForm input[name$='status']").val(JY.Object.notEmpty(l.status));
	$("#auCgyForm input[name$='id']").val(JY.Object.notEmpty(l.id));
	$("#auCgyForm input[name$='name']").val(JY.Object.notEmpty(l.name));
//	$("#auCgyForm input[name$='type']").val(JY.Object.notEmpty(l.type))
	$("#type1").val(JY.Object.notEmpty(l.type));
//	$("#auOrgForm input[name$='orgGrade']").val(JY.Object.notEmpty(l.orgGrade))
	$("#auCgyForm input[name$='sort']").val(JY.Object.notEmpty(l.sort))
	$("#auCgyForm textarea[name$='description']").val(JY.Object.notEmpty(l.description));//描述
	$("#auCgyForm input[name$='pid']").val(JY.Object.notEmpty(l.pid));//上级资源
	$("#pre").val(l.pid=="0"?"分类":JY.Object.notEmpty(l.pName));
	$("#orgid").val(JY.Object.notEmpty(l.orgid));
	$("#auCgyForm input[name$='code']").val(JY.Object.notEmpty(l.code));
	$("#preOrg").val(JY.Object.notEmpty(l.orgName));
};  
function setForm(data){
	var l=data.obj;
	$("#auForm input[name$='id']").val(JY.Object.notEmpty(l.id));
	JY.Tags.isValid1("auForm",(JY.Object.notNull(l.status)?l.status:"0"));
	$("#auForm input[name$='status']").val(JY.Object.notEmpty(l.status));
	$("#auForm input[name$='categoryid']").val(JY.Object.notEmpty(l.categoryid));
	$("#auForm input[name$='name']").val(JY.Object.notEmpty(l.name));
//	$("#auForm input[name$='type']").val(JY.Object.notEmpty(l.type));
	$("#type").val(JY.Object.notEmpty(l.type));
	$("#auForm input[name$='code']").val(JY.Object.notEmpty(l.code));
	$("#auForm textarea[name$='description']").val(JY.Object.notEmpty(l.description));//描述
	uploader.addButton({
		id : '#filePicker2',
		label : '点击选择图片'
	});
	
}
function viewInfo(id,title,fn){	
	$("#"+id).removeClass('hide').dialog({resizable:false,width:550,height:570,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(fn) == 'function'){fn.call(this);}}}]});
}
function editInfo(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,width:550,height:570,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
}
function viewInfo1(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
}
/**
 * 工厂款号
 */

//查看
function findDetail(id){
//	$("moudleDiv").empty();
//	$("detailDiv").empty();
//	$("detailDiv").append('<%@include file="img.jsp" %>');
	cleanDetailForm();
	move_dom("#wrapper","#detailDiv1");
	checkImgs(id);
	var cid=$("#baseForm1 input[name$='categoryid']").val();
	JY.Ajax.doRequest(null,jypath +'/scm/moudle/detail/find',{id:id},function(data){
	    setDetailForm(data);   
	    JY.Module.Field.load("moForm",{cid:cid,bid:id,type:'MOUDLEDETAIL'},2);
	    viewInfo("moDiv","修改",function(){
	    	if(JY.Validate.form("moForm")){
				var that =$(this);
				
	    	}
	    });
	});
}
//删除
function delDetail(id){
	JY.Model.confirm("确认删除吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/moudle/detail/del',{id:id},function(data){
			JY.Model.info(data.resMsg,function(){search1();});
		});
    });
}
function editDetail	(id){
//	$("moudleDiv").empty();
//	$("detailDiv").empty();
//	$("detailDiv").append('<%@include file="img.jsp" %>');
	cleanDetailForm();
	move_dom("#wrapper","#detailDiv1");
	findImgList(id);
	var cid=$("#baseForm input[name$='categoryid']").val();
	JY.Ajax.doRequest(null,jypath +'/scm/moudle/detail/find',{id:id},function(data){
	    setDetailForm(data);   
	    JY.Module.Field.load("moForm",{cid:cid,bid:id,type:'MOUDLEDETAIL'},2);
	    editInfo("moDiv","修改",function(){
	    	var _supplierDom = $("#moForm input[name$='supplierName']");
	    	if(JY.Validate.form("moForm")){
				var that =$(this),_supCode = $("#moForm input[name$='supplierCode']").val(),_supName=_supplierDom.val();
				if(_supCode!="" && _supName==""){
					 _supCode = "";
				 }
				 if(_supCode=="" && _supName!=""){
					 $(_supplierDom).tips({
                        side: 1,
                        msg: "请输入有效的供应商名称！",
                        bg: '#FF2D2D',
                        time: 1
                    });
					 return false;
				 }
				JY.Module.Field.save("moForm",jypath +'/scm/moudle/detail/update',function(data){
					 if(data.res==1){
						 that.dialog("close"); 
						 search1();
					 }
				 });
				
	    	}
	    });
	});
}
function cleanDetailForm(){
	JY.Tags.cleanForm("moForm");
	JY.Tags.isValid("moForm","1");
	JY.Tags.isValid1("moForm","0");
	var moudleid=$("#baseForm1 input[name$='moudleid']").val();
	$("#moForm input[name$='moudleid']").val(moudleid);
	$("#moForm input[name$='supplierName']").removeAttr("readonly");
	for (var i = 0; i < uploader.getFiles().length; i++) {
		// 将图片从上传序列移除
		uploader.removeFile(uploader.getFiles()[i]);
		uploader.removeFile(uploader.getFiles()[i], true);
		delete uploader.getFiles()[i];
		// 将图片从缩略图容器移除
		var $li = $('#' + uploader.getFiles()[i].id);
		$li.off().remove();
	}
	// 重置uploader，目前只重置了文件队列
	uploader.reset();
	$('#files').html("");

	cleanImgs();
}

function setDetailForm(data){
	var l=data.obj;
	JY.Tags.isValid("moForm",(JY.Object.notNull(l.status)?l.status:"0"));
	JY.Tags.isValid1("moForm",(JY.Object.notNull(l.majorFlag)?l.majorFlag:"0"));
	$("#moForm input[name$='majorFlag']").val(JY.Object.notEmpty(l.majorFlag));
	$("#moForm input[name$='status']").val(JY.Object.notEmpty(l.status));
	$("#moForm input[name$='id']").val(JY.Object.notEmpty(l.id));
	$("#moForm input[name$='moudleid']").val(JY.Object.notEmpty(l.moudleid));
	$("#moForm input[name$='supplierCode']").val(JY.Object.notEmpty(l.supplierCode));
	$("#moForm input[name$='supplierName']").val(JY.Object.notEmpty(l.supplierName));
	$("#moForm input[name$='supplierName']").prop("readonly",true);
	$("#moForm input[name$='suppmouCode']").val(JY.Object.notEmpty(l.suppmouCode));
	$("#moForm input[name$='laborCost']").val(JY.Object.notEmpty(l.laborCost));
	$("#moForm input[name$='addLaborCost']").val(JY.Object.notEmpty(l.addLaborCost));
	$("#moForm input[name$='saleLaborCost']").val(JY.Object.notEmpty(l.saleLaborCost));
	$("#moForm input[name$='saleLossRate']").val(JY.Object.notEmpty(l.saleLossRate));
	$("#moForm input[name$='moudleid]'").val(JY.Object.notEmpty(l.moudleid));
	uploader.addButton({
		id : '#filePicker2',
		label : '点击选择图片'
	});
}
function clearSupplier(){
	$("#moForm input[name$='supplierCode']").val('');
	$("#moForm input[name$='supplierName']").val('');
	$("#moForm input[name$='supplierName']").removeAttr("readonly");
}