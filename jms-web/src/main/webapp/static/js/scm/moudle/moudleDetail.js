$(function () {
	//下拉框
	JY.Dict.setSelect("selectisValid","isValid",2,"全部");
	
	JY.Dict.selectRender(jypath+'/scm/moudle/select',"authRoleList",2,"选择");
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
        items: 20,
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
	
	getbaseList();
	//增加回车事件
	$("#baseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	//新加
	$('#addBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		uploader.addButton({
			id : '#filePicker2',
			label : '点击选择图片'
		});
		cleanForm();
		var cid=$("#baseForm input[name$='categoryid']").val();
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
						 search();
					 }
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
				JY.Ajax.doRequest(null,jypath +'/scm/moudle/detail/delBatch',{chks:chks.toString()},function(data){
					JY.Model.info(data.resMsg,function(){search();});
				});
			});		
		}		
	});

});

function search(){
	$("#searchBtn").trigger("click");
}



//function getbaseList(init){
//	if(init==1)$("#baseForm .pageNum").val(1);	
//	JY.Model.loading();
//	JY.Ajax.doRequest("baseForm",jypath +'/scm/moudle/detail/findByPage',null,function(data){
//		 $("#baseTable tbody").empty();
//        	 var obj=data.obj;
//        	 var list=obj.list;
//        	 var results=list.results;
//        	 var permitBtn=obj.permitBtn;
//         	 var pageNum=list.pageNum,pageSize=list.pageSize,totalRecord=list.totalRecord;
//        	 var html="";
//    		 if(results!=null&&results.length>0){
//        		 var leng=(pageNum-1)*pageSize;//计算序号
//        		 for(var i = 0;i<results.length;i++){
//            		 var l=results[i];
//            		 html+="<tr>";
//            		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
//            		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
//            		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.moudleName)+"</td>";
//            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.supplierName)+"</td>";
//            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.suppmouCode)+"</td>";
//            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.laborCost)+"</td>";
//            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.addLaborCost)+"</td>";
//            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.saleLaborCost)+"</td>";
//            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.saleLossRate)+"</td>";
//            		 if(l.majorFlag==1){
//            			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>是</span></td>";
//            		 }else{
//            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>否</span></td>";
//            		 } 
//            		 if(l.status==1){
//            			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>有效</span></td>";
//            		 }else{
//            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>无效</span></td>";
//            		 } 
//            		 html+=JY.Tags.setFunction(l.id,permitBtn);
//            		 html+="</tr>";		 
//            	 } 
//        		 $("#baseTable tbody").append(html);
//        		 JY.Page.setPage("baseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
//        	 }else{
//        		html+="<tr><td colspan='12' class='center'>没有相关数据</td></tr>";
//        		$("#baseTable tbody").append(html);
//        		$("#pageing ul").empty();//清空分页
//        	 }	
// 	 
//    		 JY.Model.loadingClose();
//	 });
//}


function edit(id){
	cleanForm();
	findImgList(id);
	var cid=$("#baseForm input[name$='categoryid']").val();
	JY.Ajax.doRequest(null,jypath +'/scm/moudle/detail/find',{id:id},function(data){
	    setForm(data);   
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
						 search();
					 }
				 });
				
	    	}
	    });
	});
}
function cleanForm(){
	JY.Tags.cleanForm("moForm");
	JY.Tags.isValid("moForm","1");
	JY.Tags.isValid1("moForm","0");
	var moudleid=$("#baseForm input[name$='moudleid']").val();
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

function setForm(data){
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
	$("#moudleid").val(JY.Object.notEmpty(l.moudleid));
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

function out(){
	var menu=$("#menuPId").val();;
	window.location.href=jypath+"/scm/moudle/index?menu="+menu;
}

//查看
function find(id){
	
	cleanForm();
	checkImgs(id);
	var cid=$("#baseForm input[name$='categoryid']").val();
	JY.Ajax.doRequest(null,jypath +'/scm/moudle/detail/find',{id:id},function(data){
	    setForm(data);   
	    JY.Module.Field.load("moForm",{cid:cid,bid:id,type:'MOUDLEDETAIL'},2);
	    viewInfo("moDiv","修改",function(){
	    	if(JY.Validate.form("moForm")){
				var that =$(this);
				
	    	}
	    });
	});
}
//删除
function del(id){
	JY.Model.confirm("确认删除吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/moudle/detail/del',{id:id},function(data){
			JY.Model.info(data.resMsg,function(){search();});
		});
    });
}

function clearSupplier(){
	$("#moForm input[name$='supplierCode']").val('');
	$("#moForm input[name$='supplierName']").val('');
	$("#moForm input[name$='supplierName']").removeAttr("readonly");
}