
$(function () {
	//下拉框
	JY.Dict.setSelect("selectisValid","isValid",2,"全部");
	JY.Dict.selectRender(jypath+'/scm/warehouse/select',"authRoleList",2,"选择仓库");
	getbaseList();
	//增加回车事件
	$("#baseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	//
	$('#addBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		cleanForm();
		editInfo("warLoDiv","新增",function(){
			alert(1)
			 if(JY.Validate.form("warLoForm")){
				 var that =$(this);
				 JY.Ajax.doRequest("warLoForm",jypath +'/scm/warehouseLocation/add',null,function(data){
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
			JY.Model.confirm("确认要删除选中的数据吗?",function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/warehouseLocation/delBatch',{chks:chks.toString()},function(data){
					JY.Model.info(data.resMsg,function(){search();});
				});
			});		
		}		
	});

});

function out(){
	var menu=$("#menuPId").val();;
	window.location.href=jypath+"/scm/warehouse/index?menu="+menu;
}
function search(){
	$("#searchBtn").trigger("click");
}



function getbaseList(init){
	if(init==1)$("#baseForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("baseWarehouseLocForm",jypath +'/scm/warehouseLocation/findByPage',null,function(data){
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
            		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.name)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.code)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.warehousecode)+"</td>";
            		 if(l.status==1) html+="<td class='center hidden-480'><span class='label label-sm label-success'>有效</span></td>";
            		 else             html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>无效</span></td>";
            		 if(l.defaults==1) html+="<td class='center hidden-480'><span class='label label-sm label-success'>是</span></td>";
            		 else             html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>否</span></td>";
            		 html+=JY.Tags.setFunction(l.id,permitBtn);
            		 html+="</tr>";		 
            	 } 
        		 $("#baseTable tbody").append(html);
        		 JY.Page.setPage("baseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
        		$("#baseTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
 	 
    		 JY.Model.loadingClose();
	 });
}


function editLocation(id){
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/scm/warehouseLocation/find',{id:id},function(data){
	    setForm(data);   
	    editInfo("warLoDiv","修改",function(){
	    	if(JY.Validate.form("warLoForm")){
				var that =$(this);
				JY.Ajax.doRequest("warLoForm",jypath +'/scm/warehouseLocation/update',null,function(data){
					that.dialog("close");
					JY.Model.info(data.resMsg,function(){search();});	
				});
				
	    	}
	    });
	});
}
function cleanForm(){
//	$("#warLoForm input[name$='name']").val("");
//	$("#warLoForm input[name$='code']").val("");
//	$("#warLoForm input[name$='defaults']").val("");
//	$("#warehouseid").val("");
//	$("#warLoForm input[name$='sort']").val("");
//	$("#warLoForm textarea[name$='description']").val("");//描述
//	$("#warLoForm input[name$='defaults'][value=0]").attr("checked",false);
//	$("#warLoForm input[name$='defaults'][value=1]").attr("checked",false);
	JY.Tags.cleanForm("warLoForm");
	JY.Tags.isValid("warLoForm","1");
	JY.Tags.isValid1("warLoForm","1");
}

function setForm(data){
	var l=data.obj;
	$("#warLoForm input[name$='id']").val(JY.Object.notEmpty(l.id));
	JY.Tags.isValid("warLoForm",(JY.Object.notNull(l.status)?l.status:"0"));
	JY.Tags.isValid1("warLoForm",(JY.Object.notNull(l.defaults)?l.defaults:"0"));
	$("#warLoForm input[name$='name']").val(JY.Object.notEmpty(l.name));
	$("#warLoForm input[name$='code']").val(JY.Object.notEmpty(l.code));
	$("#warLoForm input[name$='defaults']").val(JY.Object.notEmpty(l.defaults));
	$("#warLoForm input[name$='status']").val(JY.Object.notEmpty(l.status));
	alert(l.warehouseid)
	$("#warehouseid").val(JY.Object.notEmpty(l.warehouseid));
	$("#warLoForm input[name$='sort']").val(JY.Object.notEmpty(l.sort));
	$("#warLoForm input[name$='status']").val(JY.Object.notEmpty(l.status));
//	if(l.defaults=='0') $("#warLoForm input[name$='defaults'][value=0]").attr("checked",'checked');
//	if(l.defaults=='1') $("#warLoForm input[name$='defaults'][value=1]").attr("checked",'checked');
	$("#warLoForm textarea[name$='description']").val(JY.Object.notEmpty(l.description));//描述
	
}

function viewInfo(id,title,fn){	
	$("#"+id).removeClass('hide').dialog({resizable:false,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(fn) == 'function'){fn.call(this);}}}]});
}
function editInfo(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
}


//查看
function authRole(id){
	JY.Model.info("开发中。。。");
}
//删除
function del(id){
	JY.Model.confirm("确认删除吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/warehouseLocation/del',{id:id},function(data){
			JY.Model.info(data.resMsg,function(){search();});
		});
    });
}
