$(function(){
	/*多个商品入库*/
	$('#warehousingBatch').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#productList input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			warehouseForm(chks)
			editInfo({id:"warehousingDiv",title:"商品入库",height:"400",width:"300",savefn:function(){
				var that = $(this);
				if ($('#selectWarehouse').val()!=0 && $('#selectWarehouseLocation').val()!=0 && $('#productId').val()!="") {
					JY.Ajax.doRequest("warLoForm", jypath + '/scm/warehousing/warehousingBatch',null, function(data) {
						that.dialog("close");
						JY.Model.info(data.resMsg, function() {
							search();
						});
					});
				}
			}});	
		}		
	});
	
})

function chgWarehouseLocation(obj){
		var va = obj.val();
		var selectWarehouse = $('#selectWarehouseLocation');
		JY.Ajax.doRequest(null,jypath +'/scm/warehousing/selectWarehousingLocation',{id:va},function(data){
		    var obj=data.obj;
		    selectWarehouse.html("");
		    selectWarehouse.append('<option value="0">请选择</option>');
			for (var i = 0; i < obj.length; i++) {
				selectWarehouse.append('<option value="'+obj[i].key+'">'+obj[i].value+'</option>');
	        }
		});
}

function editInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
}


/*单个商品入库*/
function warehousing(id) {
	warehouseForm(id);
	editInfo({id:"warehousingDiv",title:"商品入库",height:"400",width:"300",savefn:function(){
		if ($('#selectWarehouse').val()!=0 && $('#selectWarehouseLocation').val()!=0 && $('#productId').val()!="") {
			var that = $(this);
			JY.Ajax.doRequest("warLoForm", jypath + '/scm/warehousing/warehousing',null, function(data) {
				that.dialog("close");
				JY.Model.info(data.resMsg, function() {
				search();
				});
			});
	    }
	}});
}

/*表单添默认值 */
function warehouseForm(id){
	$("#warLoForm input[name$='productId']").val(id);
	var selectWarehouse = $('#selectWarehouse');
	JY.Ajax.doRequest(null,jypath +'/scm/warehousing/selectWarehousing',null,function(data){
	    var obj=data.obj.list;
	    selectWarehouse.html("");
	    $('#selectWarehouseLocation').html("");
	    selectWarehouse.append('<option value="0">请选择</option>');
		for (var i = 0; i < obj.length; i++) {
			if(data.obj.warehouse!=undefined && obj[i].key==data.obj.warehouse.id){
				selectWarehouse.append('<option selected="selected" value="'+obj[i].key+'">'+obj[i].value+'</option>');
			}else{
				selectWarehouse.append('<option value="'+obj[i].key+'">'+obj[i].value+'</option>');
			}
        }
		$('#selectWarehouseLocation').append('<option value="'+data.obj.location.id+'" selected="selected">'+data.obj.location.name+'</option>');
	});
}
