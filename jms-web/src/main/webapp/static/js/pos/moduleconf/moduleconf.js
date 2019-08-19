$(function(){
	//下拉框
	JY.Dict.setSelect("selectisValid","status",2,"全部");
	getbaseList();
	//增加回车事件
	$("#ModuleConfList").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	 // 删除按钮
    $('#delBtn').on('click', function() {
        var chks = [];
        $('#ModuleConfTable input[name="ids"]:checked').each(function(){
            chks.push($(this).val());
        });
        if(chks.length == 0) {
            JY.Model.info("您没有选择任何内容!");
        }else {
                delByBtn(chks);
            }
    });

    // 查看按钮
    $('#viewBtn').on('click', function(e) {
        var chks =[];
        $('#ModuleConfTable input[name="ids"]:checked').each(function(){
            chks.push($(this).val());
        });
        if(chks.length == 0) {
            JY.Model.info("您没有选择任何内容!");
        }else if(chks.length > 1){
            JY.Model.info("您只能选择一条内容!");
        } else {
            view(chks[0]);
        }
    });
	//新加
	$('#addBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作	
		e.preventDefault();
		cleanForm();	
		editInfo("ModuleConfDiv","新增",function(){
			if(JY.Validate.form("ModuleConfForm")){
				var that =$(this);
					JY.Ajax.doRequest("ModuleConfForm",jypath +'/pos/moduleconf/add',null,function(data){
					     that.dialog("close");      
					     JY.Model.info(data.resMsg,function(){search();});
				    });
			}	
		});
	});
    // 修改按钮
    $('#updateBtn').on('click', function(e) {
        var chks =[];
        $('#ModuleConfTable input[name="ids"]:checked').each(function(){
            chks.push($(this).val());
        });
        if(chks.length == 0) {
            JY.Model.info("您没有选择任何内容!");
        }else if(chks.length > 1){
            JY.Model.info("您只能选择一条内容!");
        }else {
                update(chks[0]);
            }
        
    });

});
function getbaseList(init) {

    if(init == 1) {
        $("#ModuleConfList .pageNum").val(1);
    }
    JY.Model.loading();
    JY.Ajax.doRequest("ModuleConfList",jypath +'/pos/moduleconf/findByPage',null,function(data) {
        $("#ModuleConfTable tbody").empty();
        var obj = data.obj;
        var list = obj.list;
        var results = list.results;
        var permitBtn = obj.permitBtn;
        var pageNum = list.pageNum, pageSize = list.pageSize, totalRecord = list.totalRecord;
        var html = "";
        if(null != results && results.length > 0) {
            var leng = (pageNum-1) * pageSize;//计算序号
            for(var i = 0;i < results.length;i++) {
                var l = results[i];
                html += "<tr>";
                html += "<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
                html += "<td class='center hidden-480'>"+(i+leng+1)+"</td>";
                html += "<td class='center hidden-480'>"+JY.Object.notEmpty(l.sysCode)+"</td>";
                html += "<td class='center hidden-480'>"+JY.Object.notEmpty(l.sysName)+"</td>";
                html += "<td class='center hidden-480'>"+JY.Object.notEmpty(l.moduleCode)+"</td>";
                html += "<td class='center hidden-480'>"+JY.Object.notEmpty(l.moduleName)+"</td>";
                if (l.status == 0) {
                    html += "<td class='center hidden-480'><span name='statusValue' class='label label-sm arrowed-in'>无效</span></td>";
                } else if (l.status == 1) {
                    html += "<td class='center hidden-480'><span name='statusValue' class='label label-sm label-success'>有效</span></td>";
                }
                html += "</tr>";
            }
            $("#ModuleConfTable tbody").append(html);
            JY.Page.setPage("ModuleConfList","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        }else{
            html += "<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
            $("#ModuleConfTable tbody").append(html);
            $("#pageing ul").empty();//清空分页
        }
        JY.Model.loadingClose();
    });

}
//查看按钮
function view(id){
	cleanForm();
		JY.Ajax.doRequest(null,jypath +'/pos/moduleconf/find',{id:id},function(data){ 
			viewInfo("ModuleConfDiv");
			setForm(data);
			JY.Tools.formDisabled("ModuleConfForm",true,function(){
				$("#ModuleConfForm .icon-remove").hide();
				$("#ModuleConfForm .ui-datepicker-trigger").hide();
			});
		});
}
//修改按钮
function update(id){
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/pos/moduleconf/find',{id:id},function(data){
	    setForm(data); 
	    editInfo("ModuleConfDiv","修改",function(){
	    	if(JY.Validate.form("ModuleConfForm")){
				var that =$(this);
				JY.Ajax.doRequest("ModuleConfForm",jypath +'/pos/moduleconf/update',null,function(data){
				    that.dialog("close");
				    JY.Model.info(data.resMsg,function(){
				    	search();
				    });	
				});
			}	
	    });
	});
}
function delByBtn(obj) {
    JY.Model.confirm("确认删除吗？",function(){
        JY.Ajax.doRequest(null,jypath +'/pos/moduleconf/delete',{ids: obj.toString()},function(data){
            JY.Model.info(data.resMsg,function(){
                search();
            });
        });
    });
}
//加载查看页面
function viewInfo(id,title,fn){	
	$("#"+id).removeClass('hide').dialog({resizable:false,height:400,width:400,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(fn) == 'function'){fn.call(this);}}}]});
}
//加载修改页面
function editInfo(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,height:400,width:400,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
}
function search(){
	$("#searchBtn").trigger("click");
}
function cleanBaseForm(){
    JY.Tags.cleanForm("ModuleConfList");
}
function cleanForm(){
	JY.Tags.cleanForm("ModuleConfForm");
	JY.Tools.formDisabled("ModuleConfForm",false,function(){
		$("#ModuleConfForm .icon-remove").show();
		$("#ModuleConfForm .ui-datepicker-trigger").show();
	});
}
//set赋值
function setForm(data){
	var l=data.obj;
	JY.Tools.populateForm("ModuleConfForm",data.obj);
	JY.Tags.isValid("ModuleConfForm",(JY.Object.notNull(l.status)?l.status:"0"));
	
}