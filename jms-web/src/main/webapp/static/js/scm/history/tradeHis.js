$(function(){
	initOrgSelectData();
	initOrgSelectDataOut();
	
	JY.Dict.setSelect("selectisValid","SCMHISTYPE",2,"全部");
	
	$("#dateRender1").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd',
		changeYear: true,
		changeMonth:true,
		defaultViewDate:new Date(),
		onSelect:function(dateText, inst) { }
	});
	$("#dateRender2").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd',
		changeYear: true,
		changeMonth:true,
		onSelect:function(dateText, inst) { }
	});
	//增加回车事件
	$("#tradeHisForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	setBaseData();
	getbaseList(1);
})

/*刷新列表页*/
function search(){
	$("#searchTradeHis").trigger("click");
}

/*列表*/
function getbaseList(init){
	if(init==1)$("#tradeHisForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("tradeHisForm",jypath +'/scm/tradeHis/findByPage',null,function(data){
		 	$("#tradeHisTable tbody").empty();
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
            		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace'/> <span class='lbl'></span></label></td>";
            		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
            		 html+="<td class='center hidden-480'><a style='cursor:pointer;' onclick='views(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.code)+"</a></td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.productid)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.type)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradeorder)+"</td>";
            		/* html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradenum)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradeweight)+"</td>";*/
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradewholesale)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradecostprice)+"</td>";
	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradecheckprice)+"</td>";
	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradefinanceprice)+"</td>";
	        		 
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.inorgid)+"</td>";
            		 /*html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.inwarehouseid)+"</td>";*/
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.outorgid)+"</td>";
            		 /*html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.outwarehouseid)+"</td>";*/
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.createTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
            		 html+=JY.Tags.setFunction(l.id,permitBtn);
            		 html+="</tr>";		 
            	 } 
        		 $("#tradeHisTable tbody").append(html);
        		 JY.Page.setPage("tradeHisForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='14' class='center'>没有相关数据</td></tr>";
        		$("#tradeHisTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
    		 JY.Model.loadingClose();
	 });
}

function views(code){
	JY.Ajax.doRequest(null,jypath +'/scm/tradeHis/views',{code:code},function(data){
		$("#tradeHisDetailTable tbody").empty();
			 var html="";
			 var results=data.obj;
			 if(results!=null&&results.length>0){
	    		 for(var i = 0;i<results.length;i++){
	        		 var l=results[i];
	        		 html+="<tr>";
	        		 html+="<td class='center hidden-480'>"+(i+1)+"</td>";
	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.type)+"</td>";
	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradeorder)+"</td>";
	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradenum)+"</td>";
	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradeweight)+"</td>";

	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradegoldprice)+"</td>";
	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradebasicwage)+"</td>";
	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradeaddwage)+"</td>";
	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradeotherwage)+"</td>";
	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradeunitprice)+"</td>";
	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradetotalprice)+"</td>";
	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradeactureprice)+"</td>";
	        		 /*html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradewholesale)+"</td>";*/
	        		 /*html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradecostprice)+"</td>";
	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradecheckprice)+"</td>";
	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.tradefinanceprice)+"</td>";*/                                                                                          
	        		 
	        		 
	        		 
	        		 /*html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.inorgid)+"</td>";*/
	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.inwarehouseid)+"</td>";
	        		/* html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.outorgid)+"</td>";*/
	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.outwarehouseid)+"</td>";
	        		 /*html+="<td class='center hidden-480'>"+JY.Date.Format(l.createTime,"yyyy-MM-dd hh:mm:ss")+"</td>";*/
	        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.createUser)+"</td>";
	        		 html+="</tr>";		 
	        	 } 
	    		 $("#tradeHisDetailTable tbody").append(html);
	    	 }else{
	    		html+="<tr><td colspan='11' class='center'>没有相关数据</td></tr>";
	    		$("#tradeHisDetailTable tbody").append(html);
	    	 }	
		viewInfo({id:"tradeHisDetailDiv",title:"交易记录明细",height:"700",width:"1200"})
	});
}


function viewInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});

}



/*******组件：选择组织机构(拨入单位) start*******/
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
	$("#tradeHisForm input[name$='inorgid']").val("");
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
	$("#tradeHisForm input[name$='inorgid']").prop("value",n);
	$("#tradeHisForm input[name$='inorgName']").prop("value",v);
	//因为单选选择后直接关闭，如果多选请另外写关闭方法
	hideOrgComp();
}
/*******组件：选择组织机构 end*******/




/*******组件：选择组织机构 start*******/
var preisShowOut=false;//窗口是否显示
function showOrgCompOut() {
	if(preisShowOut){
		hideOrgCompOut();
	}else{
		var obj = $("#orgInputOut");
		var offpos = $("#orgInputOut").position();
		$("#orgContentListOut").css({left:offpos.left+"px",top:offpos.top+obj.heith+"px"}).slideDown("fast");	
		preisShowOut=true;
	}
}
function hideOrgCompOut(){
	$("#orgContentListOut").fadeOut("fast");
	preisShowOut=false;
}
function emptyOrgCompOut(){
	$("#tradeHisForm input[name$='outorgid']").val("");
	$("#orgInputOut").prop("value","");
}
function initOrgSelectDataOut(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getOrgTree',null,function(data){
		//设置
		$.fn.zTree.init($("#selectOrgTreeOut"),{view:{dblClickExpand:false,selectedMulti:false,nameIsHTML:true},data:{simpleData:{enable: true}},callback:{onClick:clickPreOrgOut}},data.obj);
	});
}

function clickPreOrgOut(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("selectOrgTreeOut"),
	nodes = zTree.getSelectedNodes(),v ="",n ="";	
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";//获取name值
		n += nodes[i].id + ",";	//获取id值
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (n.length > 0 ) n = n.substring(0, n.length-1);
	$("#orgInputOut").prop("value",v);
	$("#tradeHisForm input[name$='outorgid']").prop("value",n);
	$("#tradeHisForm input[name$='outorgName']").prop("value",v);
	//因为单选选择后直接关闭，如果多选请另外写关闭方法
	hideOrgCompOut();
}
/*******组件：选择组织机构 end*******/

function cleanBaseForm(){
	JY.Tags.cleanForm("tradeHisForm");
	$("#productBaseForm input[name$='inorgid']").val("");
	$("#productBaseForm input[name$='outorgid']").val("");
	setBaseData();
}

function setBaseData(){
	var date=new Date;
	var year=date.getFullYear(); 
	$("#dateRender1").val(year+"/1/1")
	$("#dateRender2").val(year+"/12/31")
}
