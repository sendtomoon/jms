var imgList=[];
$(function() {
	getbaseList(1);
	// 新加
	$('#addBtn').on('click',function(e) {
				// 重新初始化按钮
				uploader.addButton({
					id : '#filePicker2',
					label : '点击选择图片'
				});
				
				// 通知浏览器不要执行与事件关联的默认动作
				cleanForm();
				editInfo("auDiv", "增加附件", function() {
					var that =$(this);
					if (JY.Validate.form("auForm")) {
						var that = $(this);
						JY.Ajax.doRequest('auForm', jypath
								+ '/backstage/accessory/addTwo', null,
								function(data) {
									JY.Model.info(data.resMsg, function() {
										search();
									});
									that.dialog("close");
								});
					}
				});
			});

	// 批量删除
	$('#delBatchBtn').on(
			'click',
			function(e) {
				// 通知浏览器不要执行与事件关联的默认动作
				e.preventDefault();
				var chks = [];
				$('#baseTable input[name="ids"]:checked').each(function() {
					chks.push($(this).val());
				});
				if (chks.length == 0) {
					JY.Model.info("您没有选择任何内容!");
				} else {
					JY.Model.confirm("确认要删除选中的数据吗?", function() {
						JY.Ajax.doRequest(null, jypath
								+ '/backstage/accessory/delBatch', {
							chks : chks.toString()
						}, function(data) {
							JY.Model.info(data.resMsg, function() {
								search();
							});
						});
					});
				}
			});
});
function search() {
	$("#searchBtn").trigger("click");
}

function getbaseList(init) {
	if (init == 1)
		$("#baseForm .pageNum").val(1);
	JY.Model.loading();
	JY.Ajax
			.doRequest(
					"baseForm",
					jypath + '/backstage/accessory/findByPage',
					null,
					function(data) {
						$("#baseTable tbody").empty();
						var obj = data.obj;
						var list = obj.list;
						var results = list.results;
						var permitBtn = obj.permitBtn;
						var pageNum = list.pageNum, pageSize = list.pageSize, totalRecord = list.totalRecord;
						var html = "";
						if (results != null && results.length > 0) {
							var leng = (pageNum - 1) * pageSize;// 计算序号
							for (var i = 0; i < results.length; i++) {
								var l = results[i];
								html += "<tr>";
								html += "<td class='center'><label> <input type='checkbox' name='ids' value='"
										+ l.id
										+ "' class='ace' /> <span class='lbl'></span></label></td>";
								html += "<td class='center'>" + (i + leng + 1)
										+ "</td>";
								html += "<td class='center hidden-480'>"
										+ JY.Object.notEmpty(l.name) + "</td>";
								html += "<td class='center hidden-480' >"
										+ JY.Object.notEmpty(l.path) + "</td>";
								html += "<td class='center hidden-480'>"
										+ JY.Object.notEmpty(l.createtime)
										+ "</td>";
								html += JY.Tags.setFunction(l.id, permitBtn);
								html += "</tr>";
							}
							$("#baseTable tbody").append(html);
							JY.Page.setPage("baseForm", "pageing", pageSize,
									pageNum, totalRecord, "getbaseList");
						} else {
							html += "<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
							$("#baseTable tbody").append(html);
							$("#pageing ul").empty();// 清空分页
						}
						JY.Model.loadingClose();
					});
}

// 清空form表单
function cleanForm() {
	itemArry = new Array();
	itemNum = 1;
	JY.Tags.cleanForm("auForm");
	JY.Tags.isValid("auForm", "1");
	JY.Tags.cleanForm("itemDiv");
	$("#itemDiv input[name$='sort']").val('1');
	$("#itemsTable tbody").empty();
	$("#auForm input[name$='dataKey']").prop("disabled", false);
	cleanImgs();
}

// 修改按钮
function edit(id) {
	cleanForm();
	
	JY.Ajax.doRequest(null, jypath + '/backstage/accessory/find', {
		id : id
	}, function(data) {
		// 启用图片选择
		findImgList(data.obj.busnessid);
		setForm(data);
		editInfo("auDiv", "修改", function() {
			var that =$(this);
			JY.Ajax.doRequest('auForm',jypath +'/backstage/accessory/update',null,function(data){
				JY.Model.info(data.resMsg,function(){search();});
				that.dialog("close");
			});
		});
	});
	

}


// form表单中设置数据
function setForm(data) {
	var l = data.obj;
	// 重新初始化按钮
	uploader.addButton({
		id : '#filePicker2',
		label : '点击选择图片'
	});
}

//查看按钮
function check(id){
	cleanForm();
	JY.Ajax.doRequest(null,jypath + '/backstage/accessory/find',{id : id},function(data){ 
		// 启用图片选择
		checkImgs(data.obj.busnessid);
		viewInfo("auDiv");
		setForm(data);
	});
	
}

//删除按钮
function del(id){
	JY.Model.confirm("确认删除吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/backstage/accessory/del',{id:id},function(data){
			JY.Model.info(data.resMsg,function(){search();});
		});
	});
}

// 加载查看页面
function viewInfo(id, title, fn) {
	$("#" + id).removeClass('hide').dialog({
		resizable : false,
		height : 540,
		width : 640,
		modal : true,
		title : "<div class='widget-header'><h4 class='smaller'>"+ (JY.Object.notNull(title) ? title : "查看")+ "</h4></div>",
		title_html : true,
		buttons : [{
			html : "<i class='icon-remove bigger-110'></i>&nbsp;取消",
			"class" : "btn btn-xs",
			click : function() {
				$(this).dialog("close");
				if (typeof (fn) == 'function') {
					fn.call(this);
				}
			}
		}]
	});
}
// 加载修改页面
function editInfo(id, title, savefn, cancelfn) {
	$("#" + id).removeClass('hide').dialog(
			{
				resizable : false,
				height : 540,
				width : 640,
				modal : true,
				title : "<div class='widget-header'><h4 class='smaller'>"
						+ (JY.Object.notNull(title) ? title : "修改")
						+ "</h4></div>",
				title_html : true,
				buttons : [ {
					html : "<i class='icon-ok bigger-110'></i>&nbsp;保存",
					"class" : "btn btn-primary btn-xs",
					click : function() {
						if (typeof (savefn) == 'function') {
							savefn.call(this);
						}
					}
				}, {
					html : "<i class='icon-remove bigger-110'></i>&nbsp;取消",
					"class" : "btn btn-xs",
					click : function() {
						$(this).dialog("close");
						if (typeof (cancelfn) == 'function') {
							cancelfn.call(this);
						}
					}
				} ]
			});
}