$(function(){
	JY.Dict.setSelect("selectisValid","CRM_POINTS_TYPE",2,"全部");
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
	setBaseData();
	getbaseList(1);
})
/*列表*/
function getbaseList(init){
	if(init==1)$("#pointsBaseForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("pointsBaseForm",jypath +'/crm/points/findByPage',null,function(data){
	 	 $("#pointsBaseTable tbody").empty();
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
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.memberName)+"</td>";
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.systemId)+"</td>";
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.moduleId)+"</td>";
        		 if(l.source==1) {html+="<td class='center hidden-480'>销售</td>";}
        		 else  {html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.source)+"</td>";}
        		 if(l.type==0) {html+="<td class='center hidden-480'><span class='label label-sm  label-success'>获得</span></td>";}
        		 else if(l.type==1)  { html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>消费</span></td>";}
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.pointNum)+"</td>";
        		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.createTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.createUser)+"</td>"; 
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.createIp)+"</td>";
        		 html+="</tr>";		 
        	 } 
    		 $("#pointsBaseTable tbody").append(html);
    		 JY.Page.setPage("pointsBaseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
    	 }else{
    		html+="<tr><td colspan='14' class='center'>没有相关数据</td></tr>";
    		$("#pointsBaseTable tbody").append(html);
    		$("#pageing ul").empty();//清空分页
    	 }	
		 JY.Model.loadingClose();
	 });
}

function cleanBaseForm(){
	JY.Tags.cleanForm("pointsBaseForm");
	setBaseData();
}

function setBaseData(){
	var date=new Date;
	var year=date.getFullYear(); 
	$("#dateRender1").val(year+"/1/1")
	$("#dateRender2").val(year+"/12/31")
}
