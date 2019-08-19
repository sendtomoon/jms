$(function(){
	getbaseList(1);
	JY.Dict.setSelect("selectisValid","SCMWAGEMOD",2,"全部");
	/*增加回车事件*/
	$("#baseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	
	//查看
	$('#find').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#baseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val()); 
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{	
			find(chks[0])
		}
	});
});

/*刷新列表页*/
function search(){
	$("#searchBtn").trigger("click");
}

/*原料入库列表*/
function getbaseList(init){
	if(init==1)$("#baseForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("baseForm",jypath +'/scm/matinventory/findByPage',null,function(data){
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
        		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace'/> <span class='lbl'></span></label></td>";
        		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
        		 html+="<td class='center hidden-480'><a style='cursor:pointer;' onclick='find(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.code)+"</a></td>";
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.name)+"</td>";
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.warehouseId)+"</td>";
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.locationId)+"</td>";
        		 if(l.type=='1'){
        			 html+="<td class='center hidden-480'>件</td>";
    			 }else{
    				 html+="<td class='center hidden-480'>克</td>";
    			 }
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.color)+"</td>";
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.clartity)+"</td>";
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.cut)+"</td>";
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.availNum)+"</td>";
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.availWeight)+"</td>";
        		 html+="</tr>";		 
        	 } 
    		 $("#baseTable tbody").append(html);
    		 JY.Page.setPage("baseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
    	 }else{
    		 html+="<tr><td colspan='12' class='center'>没有相关数据</td></tr>";
    		 $("#baseTable tbody").append(html);
    		 $("#pageing ul").empty();//清空分页
    	 }	
		 JY.Model.loadingClose();
	 });
}

function find(id){
	cleanAdd();
	JY.Ajax.doRequest(null,jypath +'/scm/matinventory/find',{id:id},function(data){
		setForm(data);
		viewInfo({id:"matinventory",title:"查看库存明细",height:"500",width:"850"})
	});
}

function setForm(data){
	JY.Tools.populateForm("matinventory",data.obj);
	/*if(data.obj.type=='1'){
		$("#matinventory input[name='type']").val("克");
	}else{
		$("#matinventory input[name='type']").val("件");
	}*/
	$("#matinventory input[name='createTime']").val(JY.Object.notEmpty(new Date(data.obj.createTime).Format('yyyy-MM-dd hh:mm:ss')));
	$("#matinventory input[name='updateTime']").val(JY.Object.notEmpty(new Date(data.obj.updateTime).Format('yyyy-MM-dd hh:mm:ss')));
	$("#matinventory textarea[name$='remarks']").val(JY.Object.notEmpty(data.obj.remarks));
}


/*清空表单弹出列表数据数据*/
function cleanAdd(){
	 $('#matinventory').find('input,select,textarea').attr('disabled','disabled');
	 JY.Tags.cleanForm("matinventory");
}


function viewInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
}
