$(function () {
	//下拉框
	JY.Dict.setSelect("selectExpress","SCM_EXPRESS",2,"请选择");
	JY.Dict.selectData("recProvince","recCity","recCounty");
	JY.Dict.selectData("senProvince","senCity","senCounty");
	
	//查看
	$('#edits').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#purOSOrderTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val()); 
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{	
			edits(chks[0])
		}
	});
})


/*修改原料入库*/
function edits(id){
	JY.Ajax.doRequest(null,jypath +'/scm/distribution/edits',{id:id},function(data){
		setForm(data);
		//当前的单位是发件人则可以修改，当前的单位是收件则只能查看
		if(data.obj.type=='1'){
			//当配送信息未增加时，只保存，已增加的，则能修改和删除
		    if(data.obj.id==null){
				addInfo({id:"logisticsDiv",title:"配送信息",height:"720",width:"700",savefn:function(){
					var that =$(this);
					if(JY.Validate.form("logisticsForm")){
						JY.Ajax.doRequest('logisticsForm',jypath +'/scm/distribution/addOrUpdate',null,function(data){
							that.dialog("close");   
							JY.Model.info(data.resMsg,function(){search();});
							JY.Model.info(data.resMsg,function(){search();});
						});
					}
				}});
			}else{
				editInfo({id:"logisticsDiv",title:"配送信息",height:"720",width:"700",savefn:function(type){
					var that =$(this);
					//操作是删除或修改
					if(type=='2'){
						JY.Model.confirm("确认删除吗？",function(){	
							JY.Ajax.doRequest('logisticsForm',jypath +'/scm/distribution/addOrUpdate',{type:type},function(data){
								that.dialog("close");   
								JY.Model.info(data.resMsg,function(){search();});
							});
						});
					}else{
						if(JY.Validate.form("logisticsForm")){
							JY.Ajax.doRequest('logisticsForm',jypath +'/scm/distribution/addOrUpdate',null,function(data){
								that.dialog("close");   
								JY.Model.info(data.resMsg,function(){search();});
							});
						}
					}
					
				}});
			}
		}else{
			JY.Ajax.doRequest(null,jypath +'/scm/distribution/edits',{id:id},function(data){
				setForm(data);
				viewInfo({id:"logisticsDiv",title:"配送信息",height:"720",width:"700"});
			});
		}
	});
}



/*修改或删除*/
function editInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",
		title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this);}}},
		                         {html:"<i class='icon-remove bigger-110'></i>&nbsp;删除","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,2);}}},
		                         {html: "<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
}

/*修改或删除*/
function addInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",
		title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this);}}},
		                         {html: "<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
}

/*查看*/
function viewInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",
		title_html:true,buttons:[{html: "<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
}


/*设置值*/
function setForm(data){
	var l=data.obj;
	JY.Tools.populateForm("logisticsForm",l);
	$("#logisticsForm textarea[name$='recAddress']").val(JY.Object.notEmpty(l.recAddress));
	$("#logisticsForm textarea[name$='sendAddress']").val(JY.Object.notEmpty(l.sendAddress));
	JY.Dict.selectData("recProvince","recCity","recCounty",l.recProvince,l.recCity,l.recCounty);
	JY.Dict.selectData("senProvince","senCity","senCounty",l.senProvince,l.senCity,l.senCounty);
}