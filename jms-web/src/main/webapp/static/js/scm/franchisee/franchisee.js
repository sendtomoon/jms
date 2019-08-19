$(function(){
	//下拉框
	JY.Dict.setSelect("selectisValid","status",2,"全部");
	
	getbaseList();
	//增加回车事件
	$("#FranchiseBaseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	//自动补全
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
        	$("#FranchiseBaseForm input[name$='id']").val(input[item]);
        },
        delay: 500,
        highlighter: function (item) {//item是用户输入
	        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')//这是将字符串安全格式化为正则表达式的源码
	            return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	                return '<strong style="color:red;">' + match + '</strong>'//将下拉列表中符合项中的LI的innerHTML相等部分加个strong标签
	            })
	        	        }
        
		
	});
	//新加
	$('#addBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		$("#FranchiseeauForm input[name$='id']").prop("readonly",false);
		$("#FranchiseeauForm input[name$='code']").prop("readonly",false);
		e.preventDefault();
		cleanForm();	
		JY.Dict.selectData("provincees","cityes","countyes");
		editInfo("FranchiseeauDiv","新增",function(){
			if(JY.Validate.form("FranchiseeauForm")){
				var that =$(this);
					JY.Ajax.doRequest("FranchiseeauForm",jypath +'/scm/franchisee/add',null,function(data){
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
		$('#FranchisebaseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});  
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			JY.Model.confirm("确认要删除选中的数据吗?",function(){
				JY.Ajax.doRequest(null,jypath +'/scm/franchisee/delBatch',{chks:chks.toString()},function(data){
					   JY.Model.info(data.resMsg,function(){
							search();
					   });	
				});
			});
		}		
	});
});

//分页查询
function getbaseList(){
	JY.Model.loading();
	JY.Ajax.doRequest("FranchiseBaseForm",jypath +'/scm/franchisee/findByPage',null,function(data){
		 $("#FranchisebaseTable tbody").empty();
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
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.code)+"</td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.name)+"</td>";
            		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.longName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.legalName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.licenseNum)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.email)+"</td>";
            		 if(l.status== "1") html+="<td class='center hidden-480'><span class='label label-sm label-success'>有效</span></td>";
            		 else if(l.status == "0") html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>无效</span></td>";
            		 else if(l.status=="9") html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>删除</span></td>";
            		 html+=JY.Tags.setFunction(l.id,permitBtn);
            		 html+="</tr>";		 
            	 } 
        		 $("#FranchisebaseTable tbody").append(html);
        		 JY.Page.setPage("FranchiseBaseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
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
	JY.Ajax.doRequest(null,jypath +'/scm/franchisee/del',{id:id},function(data){
		JY.Model.confirm("确认删除吗？",function(){	
			JY.Model.info(data.resMsg,function(){
				search();
				
			});
		});
		
	});
	
}
//查看按钮
function check(id){
	cleanForm();
	
	
		JY.Ajax.doRequest(null,jypath +'/scm/franchisee/find',{id:id},function(data){ 
			viewInfo("FranchiseeauDiv");
			setForm(data);
			JY.Tools.formDisabled("FranchiseeauForm",true,function(){
				$("#FranchiseeauForm .icon-remove").hide();
				$("#FranchiseeauForm .ui-datepicker-trigger").hide();
			});
		});
}


//加载查看页面
function viewInfo(id,title,fn){	
	$("#"+id).removeClass('hide').dialog({resizable:false,height:530,width:680,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(fn) == 'function'){fn.call(this);}}}]});
}
//加载修改页面
function editInfo(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,height:530,width:680,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
}
//修改按钮
function edit(id){
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/scm/franchisee/find',{id:id},function(data){
	    setForm(data); 
	    $("#FranchiseeauForm input[name$='code']").prop("readonly",true);
	    editInfo("FranchiseeauDiv","修改",function(){
	    	if(JY.Validate.form("FranchiseeauForm")){
				var that =$(this);
				JY.Ajax.doRequest("FranchiseeauForm",jypath +'/scm/franchisee/update',null,function(data){
				    that.dialog("close");
				    JY.Model.info(data.resMsg,function(){
				    	search();
				    });	
				});
			}	
	    });
	});
}
//清空
function cleanForm(){
	JY.Tags.cleanForm("FranchiseeauForm");
	JY.Tools.formDisabled("FranchiseeauForm",false,function(){
		$("#FranchiseeauForm .icon-remove").show();
		$("#FranchiseeauForm .ui-datepicker-trigger").show();
	});
}
//set赋值
function setForm(data){
	var l=data.obj;
	$("#FranchiseeauForm input[name$='id']").val(JY.Object.notEmpty(l.id));
	$("#FranchiseeauForm input[name$='code']").val(JY.Object.notEmpty(l.code));
	$("#FranchiseeauForm input[name$='name']").val(JY.Object.notEmpty(l.name));
	JY.Tags.isValid("FranchiseeauForm",(JY.Object.notNull(l.status)?l.status:"0"));
	$("#FranchiseeauForm input[name$='longName']").val(JY.Object.notEmpty(l.longName));
	$("#FranchiseeauForm input[name$='legalName']").val(JY.Object.notEmpty(l.legalName));
	$("#FranchiseeauForm input[name$='licenseNum']").val(JY.Object.notEmpty(l.licenseNum));
	$("#FranchiseeauForm input[name$='email']").val(JY.Object.notEmpty(l.email));
	$("#FranchiseeauForm input[name$='bankName']").val(JY.Object.notEmpty(l.bankName));
	$("#FranchiseeauForm input[name$='taxNum']").val(JY.Object.notEmpty(l.taxNum));
	$("#FranchiseeauForm input[name$='accountName']").val(JY.Object.notEmpty(l.accountName));
	$("#FranchiseeauForm input[name$='contactor']").val(JY.Object.notEmpty(l.contactor));
	$("#FranchiseeauForm input[name$='contactnum']").val(JY.Object.notEmpty(l.contactnum));
	$("#FranchiseeauForm input[name$='accountNum']").val(JY.Object.notEmpty(l.accountNum));
	$("#FranchiseeauForm input[name$='accountCode']").val(JY.Object.notEmpty(l.accountCode));
	$("#FranchiseeauForm input[name$='taxNum']").val(JY.Object.notEmpty(l.taxNum));
	JY.Dict.selectData("provincees","cityes","countyes",l.province,l.city,l.county);
	$("#FranchiseeauForm textarea[name$='address']").val(JY.Object.notEmpty(l.address));
	
}
function search(){
	$("#searchBtn").trigger("click");
}

