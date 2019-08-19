$(function () {
	JY.Dict.setSelect("selectAttrType","ATTRGROUPTYPE");
	JY.Dict.setSelect("selectisValid","status",2,"全部");
	getbaseList();
    
	var dictMap={};
	$("#selectDict").typeahead({
        source: function (query, process) {
        	$.ajax({
				type:'POST',
				url:jypath+'/backstage/attribute/getDicts',
				data:{dictName:query},
				dataType:'json',
				success:function(result,textStatus){ 
					dictMap = {};
					var array= [];
					 $.each(result, function (index, ele) {
							 dictMap[ele.value] = ele.key;
	                         array.push(ele.value);
                     });
					process(array);
				}
			});
        },
        items: 20,
        afterSelect: function (item) {
        		$("#attributedictbaseForm input[name$='dictId']").val(dictMap[item]);
        		$("#attributedictbaseForm input[name$='dictName']").prop("readonly",true);
        },
        delay: 500,
        highlighter: function (item) {//item是用户输入
	        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')//这是将字符串安全格式化为正则表达式的源码
	            return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	                return '<strong style="color:red;">' + match + '</strong>'//将下拉列表中符合项中的LI的innerHTML相等部分加个strong标签
	            })
	    }
    });
	
	//增加回车事件
	$("#attributedictListForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	//新加
	$('#addBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		cleanForm();
		
		editInfo("attributedictbaseDiv","新增",function(){
			if(JY.Validate.form("attributedictbaseForm")){
					var dictId=$("#attributedictbaseForm input[name$='dictId']").val();
				var that =$(this),dictName=$("#attributedictbaseForm input[name$='dictName']").val();
				 if(dictId!="" && dictName==""){
					 dictId = "";
				 }
				 if(dictId=="" && dictName!=""){
					 $("#attributedictbaseForm input[name$='dictName']").tips({
                         side: 1,
                         msg: "请输入有效的字典！",
                         bg: '#FF2D2D',
                         time: 1
                     });
					 return false;
				 }
					JY.Ajax.doRequest("attributedictbaseForm",jypath +'/scm/attrdict/add',null,function(data){
					     that.dialog("close");      
					     JY.Model.info(data.resMsg,function(){search();});
				    });
			}	
		});
	});
	
});
/**组装并加载table数据列表**/
function getbaseList(){
	JY.Model.loading();
	JY.Ajax.doRequest("attributedictListForm",jypath +'/scm/attrdict/findByPage',null,function(data){
		 $("#attributedictTable tbody").empty();
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
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.name)+"</td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.code)+"</td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.type)+"</td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.length)+"</td>";
            		 if(l.nullable=='1') html+='<td style="text-align: center;">允许</td>';
					 else html+='<td style="text-align: center;">不允许</td>';
					 html+="<td class='center'>"+JY.Object.notEmpty(l.dictName)+"</td>";
					 if(l.filtertag=='1')html+='<td style="text-align: center;">是</td>';
					 else html+='<td style="text-align: center;">不是</td>';
					 if(l.status=='1')html+='<td id="state" style="text-align: center;color:green;font-weight:bold;">有效</td>';
					 else if(l.status=='0') html+='<td id="state" style="text-align: center;color:green;font-weight:bold;">无效</td>';
					 else{
						 html+='<td id="state" style="text-align: center;color:green;font-weight:bold;">删除</td>';
					 }
					 html+=JY.Tags.setFunction(l.id,permitBtn);
            		 html+="</tr>";		 
            	 } 
        		 $("#attributedictTable tbody").append(html);
        		 JY.Page.setPage("attributedictListForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
        		$("#attributedictTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
 	 
    	 JY.Model.loadingClose();
	 });
}
function editInfo(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,height:530,width:450,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
}
function cleanForm(){
	JY.Tags.isValid1("attributedictbaseForm",1);
	JY.Tags.isValid2("attributedictbaseForm",1);
	JY.Tags.isValid3("attributedictbaseForm",1);
	JY.Tags.cleanForm("attributedictbaseForm");
	$("#type").val("");
	$("#attributedictbaseForm input[name$='length']").val("");
	$("#attributedictbaseForm input[name$='sort']").val("");
	$("#attributedictbaseForm input[name$='code']").prop("readonly",false);
	$("#attributedictbaseForm input[name$='dictName']").prop("readonly",false);
}
function search(){
	$("#searchBtn").trigger("click");
}

//修改按钮
function edit(id){
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/scm/attrdict/find',{id:id},function(data){
	    setForm(data); 
	    $("#attributedictbaseForm input[name$='code']").prop("readonly",true);
	    editInfo("attributedictbaseDiv","修改",function(){
	    	if(JY.Validate.form("attributedictbaseForm")){
				var that =$(this);
				JY.Ajax.doRequest("attributedictbaseForm",jypath +'/scm/attrdict/update',null,function(data){
				    that.dialog("close");
				    JY.Model.info(data.resMsg,function(){
				    	search();
				    });	
				});
			}	
	    });
	});
}

function del(id){
	JY.Ajax.doRequest(null,jypath +'/scm/attrdict/del',{id:id},function(data){
		JY.Model.confirm("确认删除吗？",function(){	
			JY.Model.info(data.resMsg,function(){search();});
		});
	});
}

function setForm(data){
	var l=data.obj;
	JY.Tools.populateForm("attributedictbaseForm",data.obj);
	$("#type").prop('value',l.type);
	if(l.dictName=="-"){
		$("#attributedictbaseForm input[name$='dictName']").val("");
		$("#attributedictbaseForm input[name$='dictName']").prop("readonly",false);
	}else{
		$("#attributedictbaseForm input[name$='dictName']").val(l.dictName);
		$("#attributedictbaseForm input[name$='dictName']").prop("readonly",true);
	}
	
	$("#attributedictbaseForm input[name$='sort']").val(l.sort);
	JY.Tags.isValid3("attributedictbaseForm",(JY.Object.notNull(l.status)?l.status:"0"));
	JY.Tags.isValid1("attributedictbaseForm",(JY.Object.notNull(l.nullable)?l.nullable:"0"));
	JY.Tags.isValid2("attributedictbaseForm",(JY.Object.notNull(l.filtertag)?l.filtertag:"0"));
	
}

function clearSupplier(){
	$("#attributedictbaseForm input[name$='dictId']").val('');
	$("#attributedictbaseForm input[name$='dictName']").val('');
	$("#attributedictbaseForm input[name$='dictName']").removeAttr("readonly");
}
