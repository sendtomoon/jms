var detailStatusArray = [];
$(function() {
	JY.Dict.setSelect("selectisValid","SCM_INVENTORYREPORT_STATUS",2,"全部");
	getbaseList();
	
	// 得到盘点详情的所有状态值
	detailStatusArray = gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_INVENTORYDETAIL_STATUS",detailStatusArray);
	
	// 查询盘点计划列表回车事件
	$("#baseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	// 查看按钮
	$('#findBtn').on('click', function(e) {
		var chks =[];    
		$('#baseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("您只能选择一条内容!"); 
		}else{
			var ino = $('#baseTable input[name="ids"]:checked').parent().parent().parent().find('input[name="inventoryNoValue"]').val();
			find(chks[0], ino);
		}
	});
	// 编辑按钮
	$('#editBtn').on('click', function() {
		var chks =[];    
		$('#baseTable input[name="ids"]:checked').each(function(){
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("您只能选择一条内容!"); 
		}else{
			var reportStatus = $('#baseTable input[name="ids"]:checked').parent().parent().parent().find('span[name="reportStatus"]').text();
			if (reportStatus == '草稿') {
				var ino = $('#baseTable input[name="ids"]:checked').parent().parent().parent().find('input[name="inventoryNoValue"]').val();
				edit(chks[0], ino);
			} else {
				JY.Model.info("您只能编辑状态为草稿的盘点报告!");
			}
			
		}
	});
	// 审核按钮
	$('#checkBtn').on('click', function() {
		var chks =[];    
		$('#baseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("您只能选择一条内容!"); 
		}else{
			var reportStatus = $('#baseTable input[name="ids"]:checked').parent().parent().parent().find('span[name="reportStatus"]').text();
			if (reportStatus == '待审核') {
				var ino = $('#baseTable input[name="ids"]:checked').parent().parent().parent().find('input[name="inventoryNoValue"]').val();
				check(chks[0], ino);
			} else {
				JY.Model.info("您只能审核状态为待审核的盘点报告!");
			}
		}
	});
	// 删除按钮
	$('#deleteBtn').on('click', function() {
		var chks = [];
		var values = [];
		var ids = [];
		$('#baseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());
			values.push($(this).parent().parent().parent().find('span[name="reportStatus"]').text());
			ids.push($(this).parent().parent().parent().find('input[name="ids"]').val());
		});
		if(chks.length == 0) {
			JY.Model.info("您没有选择任何内容!"); 
		} else {
			if (values.indexOf("草稿") != -1 || values.indexOf("待审核") != -1) {
				JY.Model.info("您只能删除状态为已审核的盘点报告!");
			} else {
				del(ids);
			}
		}
	});
})

function getbaseList(init) {
	JY.Model.loading();
	JY.Ajax.doRequest("baseForm",jypath +'/scm/inventoryReport/findByPage',null,function(data) {
		 $("#baseTable tbody").empty();
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
            		 html += "<td class='center hidden-480'><input name='inventoryNoValue' type='hidden' value='"+JY.Object.notEmpty(l.inventoryNo)+"' >  "+(i+leng+1)+"</td>";
            		 html += "<td class='center hidden-480'><a style='cursor:pointer;' onclick='find(&apos;"+JY.Object.notEmpty(l.id)+"&apos;,&apos;"+JY.Object.notEmpty(l.inventoryNo)+"&apos;)'>"+JY.Object.notEmpty(l.inventoryNo)+"</a></td>";
            		 html += "<td class='center hidden-480'>"+JY.Object.notNumber(l.orgName)+"</td>";
            		 html += "<td class='center hidden-480'>"+JY.Object.notNumber(l.whouseName)+"</td>";
            		 html += "<td class='center hidden-480'>"+JY.Object.notNumber(l.locationName)+"</td>";
            		 if(l.status == "0") {
            			 html += "<td class='center hidden-480'><span name='reportStatus' class='label label-sm arrowed-in'>草稿</span></td>";
            		 } else if(l.status == "1") {
            			 html += "<td class='center hidden-480'><span name='reportStatus' class='label label-sm label-success'>待审核</span></td>";
            		 } else if(l.status == "2") {
            			 html += "<td class='center hidden-480'><span name='reportStatus' class='label label-sm label-success'>已审核</span></td>";
            		 }
            		 html += "<td class='center hidden-480'>"+JY.Object.notEmpty(l.checkName)+"</td>";
            		 html += "<td class='center hidden-480'>"+JY.Date.Format(l.checkTime,'yyyy-MM-dd hh:mm:ss')+"</td>";
            		 html += "<td class='center hidden-480'>"+JY.Object.notEmpty(l.createName)+"</td>";
            		 html += "<td class='center hidden-480'>"+JY.Date.Format(l.createTime,'yyyy-MM-dd hh:mm:ss')+"</td>";
            		 html += "</tr>";		 
            	 } 
        		 $("#baseTable tbody").append(html);
        		 JY.Page.setPage("baseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html += "<tr><td colspan='11' class='center'>没有相关数据</td></tr>";
        		$("#baseTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
    		 JY.Model.loadingClose();
	 });
}

function cleanForm(){
	JY.Tags.cleanForm("reportForm");
}

function cleanTable() {
	JY.Tags.cleanForm("reportForm");
	$('#reoprtTable tbody').empty();
}

function viewInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
}
function editInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
    	buttons:[{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存为草稿","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
    	         {html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存并提交","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},
    	         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}
function checkInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
    	buttons:[{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,2);}}},
    	         {html: "<i id='save_btn' class='icon-remove bigger-110'></i>&nbsp;不通过","class":"btn btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
    	         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}
// 更改查询条件按回车键触发查询按钮的点击事件
function search(){
	$("#searchBtn").trigger("click");
}

function find(id, ino){
	cleanTable();
	JY.Ajax.doRequest(null,jypath +'/scm/inventoryReport/find',{id:id, inventoryNo:ino},function(data){
		$('#reportForm textarea[name="content"]').attr('readonly',true);
		setForm(data);
		viewInfo({id:"reportForm",title:"查看盘点报告",height:"720",width:"1024"})
	});
}

function edit(id, ino){
	cleanTable();
	JY.Ajax.doRequest(null,jypath +'/scm/inventoryReport/find',{id:id, inventoryNo:ino},function(data){
		$('#reportForm textarea[name="content"]').attr('readonly',false);
		setForm(data);
		editInfo({id:"reportForm",title:"修改盘点报告",height:"720",width:"1024", savefn:function(type){
			var that =$(this);
			JY.Ajax.doRequest(null,jypath +'/scm/inventoryReport/updateStatus',{id: $('#reportForm input[name="id"]').val(), content: $('#reportForm textarea[name="content"]').val(), type: type},function(data){
				if (data.res == 1) {
					JY.Model.info(data.resMsg);
					that.dialog("close");
					search();
				} else {
					JY.Model.error(data.resMsg);
				}
			});
		}})
	});
}

function check(id, ino){
	cleanTable();
	JY.Ajax.doRequest(null,jypath +'/scm/inventoryReport/find',{id:id, inventoryNo:ino},function(data){
		$('#reportForm textarea[name="content"]').attr('readonly',true);
		setForm(data);
		checkInfo({id:"reportForm",title:"审核盘点报告",height:"720",width:"1024", savefn:function(type){
			var that =$(this);
			JY.Ajax.doRequest(null,jypath +'/scm/inventoryReport/updateStatus',{id: $('#reportForm input[name="id"]').val(), content: $('#reportForm textarea[name="content"]').val(), type: type},function(data){
				if (data.res == 1) {
					JY.Model.info(data.resMsg);
					that.dialog("close");
					search();
				} else {
					JY.Model.error(data.resMsg);
				}
			});
		}})
	});
}

function del(obj) {
	JY.Model.confirm("确认删除吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/inventoryReport/del',{ids: obj.toString()},function(data){
			JY.Model.info(data.resMsg,function(){
				search();
			});
		});
	});
}

function setForm(data){
	var report = data.obj.report;
	var details = data.obj.details;
	$("#reportForm input[name$='inventoryNo']").val(JY.Object.notEmpty(report.inventoryNo));
	$("#reportForm textarea[name$='content']").val(JY.Object.notEmpty(report.content));
	$("#reportForm input[name$='id']").val(JY.Object.notEmpty(report.id));
	$("#reportForm input[name$='inventoryId']").val(JY.Object.notEmpty(report.inventoryId));
	
	fillTable(details);
}

function fillTable(obj) {
	var html="";
	if(obj != null) {
		for(var i = 0;i< obj.length; i++){ 
			 html+="<tr>";
			 html+="<td style='padding:1px;'><input class='center' type='text' name='code' readonly  value='"+JY.Object.notEmpty(obj[i].code)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
			 html+="<td style='padding:1px;'><input class='center' type='text' name='name' readonly value='"+JY.Object.notEmpty(obj[i].name)+"' style='width:100%;height:30px;border:none;'/></td>";
			 html+="<td style='padding:1px;'><input class='center' type='text' name='numbers' onkeyup='JY.Validate.limitNum(this)' value='"+JY.Object.notNumber(obj[i].numbers)+"' style='width:100%;height:30px;border:none;' readonly/></td>";
			 html+="<td style='padding:1px;'><input class='center' type='text' name='weight' onkeyup='JY.Validate.limitDouble(this)'  value='"+JY.Object.notNumber(obj[i].weight)+"'  style='width:100%;height:30px;border:none;' readonly/></td>";
			 html+="<td style='padding:1px;'><input class='center' type='text' name='chkNum' onkeyup='JY.Validate.limitNum(this)' value='"+JY.Object.notNumber(obj[i].chkNum)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
			 html+="<td style='padding:1px;'><input class='center' type='text' name='chkWeight' onkeyup='JY.Validate.limitDouble(this)'  value='"+JY.Object.notNumber(obj[i].chkWeight)+"'  style='width:100%;height:30px;border:none;' readonly /></td>";
			 html+="<td style='padding:1px;'><select name='diff' disabled='disabled'  style='width:100%;'>"+options(detailStatusArray, JY.Object.notEmpty(obj[i].diff))+"</select></td>";
			 html+="<td style='padding:1px;'><input class='center' type='text' name='diffRemark' value='"+JY.Object.notEmpty(obj[i].diffRemark)+"'  style='width:100%;height:30px;border:none;' readonly /></td>";
			 html+="</tr>";
		}
		$("#reoprtTable tbody").append(html);
	}
}

function gridSelect(url,keys,selects){
	JY.Ajax.doRequest("",url,{ids:"1",keys:keys},function(data){
		var map=data.obj;
		var list=map["1"];
		$.each(list.items,function(i,e){
			var a ={key:e.value,text:e.name};
			selects[i]=a;
		});
	})
	return selects;
}

function options(obj,val){
	var str = "<option value=''>请选择</option>";
	
	for(var i=0;i<obj.length;i++){
		str += "<option value='" + obj[i].key + "'";
		if(val==obj[i].key){
			str+=" selected='true' ";
		}
		str+=">" + obj[i].text + "</option>";
	}
	return str;
}
