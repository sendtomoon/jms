$(function(){
	getbaseList();
	JY.Dict.setSelect("typeForm","SCM_DATA_MATERIALTYPE",1);
	JY.Dict.setSelect("selectisValid","SCM_ORDER_STATUS",2,"全部");
	warehouseForm("selectWarehouse");
	
	
	/*2：不相同的信息*/
	$('#addBtn').on('click', function(e) {
		e.preventDefault();
		cleanForm();
		addInfo({id:"moveDiv",title:"移库信息",height:"520",width:"400",savefn:function(e){
			var that =$(this);
			var html="";		    		
			if(JY.Validate.form("transferForm")){
				var code=$("#transferForm input[name$='code']").val();
				JY.Ajax.doRequest(null,jypath +'/scm/transfer/findByCode',{code:code},function(data){
					setForm(data);
					html+="<tr>";
					 var remarks= $("#transferForm textarea[name$='remarks']").val();
	        		 if(remarks ==""){remarks="无";}
	        		 alert($(".customTable input[name$='aaa']").val())
	        		 html+="<td class='center'><label> <input  type='checkbox' name='ids' value='"+data.obj.code+"' class='ace' /><span class='lbl'></span></label></td>";
	    			 html+="<td style='padding:0px;'><input jyValidate='required' type='text' name='code' readonly value='"+data.obj.code+"' style='width:100%;height:30px;border:none;'/><input type='hidden' name='remarks' value='"+remarks+"'/><input type='hidden' name='id' value='0'/></td>";
	    			 html+="<td style='padding:0px;'><input jyValidate='required' type='text' name='num' onkeyup='JY.Validate.limitNum(this)' value='"+$("#transferForm input[name$='num']").val()+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
	        		 html+="<td style='padding:0px;'><input jyValidate='required' type='text' name='weight' onkeyup='JY.Validate.limitDouble(this)' value='"+$("#transferForm input[name$='weight']").val()+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
	        		 html+="<td style='padding:0px;'><input jyValidate='required' type='text' name='diffWeight' onkeyup='JY.Validate.limitDouble(this)' value='"+$("#transferForm input[name$='diffWeight']").val()+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
	        		 html+="<td style='padding:0px;'><input readonly type='text' name='outWarehouseIdName' value='"+data.obj.outWarehouseIdName+"' style='width:100%;height:30px;border:1px solid #ccc;'/><input type='hidden' name='outWarehouseId' value='"+data.obj.outWarehouseId+"'/></td>";
	    			 html+="<td style='padding:0px;'><input readonly type='text' name='outLocationIdName'  value='"+data.obj.outLocationIdName+"' style='width:100%;height:30px;border:1px solid #ccc;'/><input type='hidden' name='outLocationId' value='"+data.obj.outLocationId+"'/></td>";
	    			 html+="<td style='padding:0px;'><select id='ow_"+data.obj.code+"' jyValidate='required' type='text'  name='inWarehouseId' value='' style='width:100%;height:30px;border:1px solid #ccc;' "+"onchange=chgWarehouseLocation('ow_"+data.obj.code+"','ol_"+data.obj.code+"')></select></td>";
	    			 html+="<td style='padding:0px;'><select id='ol_"+data.obj.code+"' jyValidate='required' type='text'  name='inLocationId' value='' style='width:100%;height:30px;border:1px solid #ccc;'></select></td>";
	    			 html+="</tr>";	
		    		 $("#transferAdd tbody").append(html);
		    		 warehouseForm('ow_'+data.obj.code);
		    		 chgWarehouseLocation('selectWarehouse','ol_'+data.obj.code,$("#transferForm select[name$='inLocationId']").val());
		    		 $("#ow_"+data.obj.code).val($("#transferForm select[name$='inWarehouseId']").val());
		    		 if(e==0){
		    			 that.dialog("close");
		    		 }
				});
			}
		}})	
	});
	//---------------------	
	//新增移库
	$('#move').on('click', function(e) {
		 //通知浏览器不要执行与事件关联的默认动作		
		 e.preventDefault();
		 cleanForm();	
		 cleanTransfer();
		 $('.btnClass').show();
		 /*$('#move-transferForm').find('input,select,textarea').removeAttr('disabled');*/
		 baseInfo({id:"transferDiv",title:"新增移库",height:"720",width:"1024",savefn:function(type){
			if(JY.Validate.form("move-transferForm")){
				var that =$(this);
				var json_data="";
		 	    var last_index=$('#transferAdd tbody tr').length-1;
		 	    $('#transferAdd tbody tr').each(function(element,index){
			 	    var data='{"code":"'+$(this).find("input[name=code]").val()+'","num":"'+$(this).
			 	    find("input[name=num]").val()+'","weight":"'+$(this).
			 	    find("input[name=weight]").val()+'","diffWeight":"'+$(this).
			 	    find("input[name=diffWeight]").val()+'","outWarehouseId":"'+$(this).
			 	    find("input[name=outWarehouseId]").val()+'","outLocationId":"'+$(this).
			 	    find("input[name=outLocationId]").val()+'","inWarehouseId":"'+$(this).
			 	    find("select[name=inWarehouseId]").val()+'","inLocationId":"'+$(this).
			 	    find("select[name=inLocationId]").val()+'","description":"'+$(this).
			 	    find("input[name=description]").val()+'"}';	
			 	    if($(this).index()==last_index){
			        	json_data+=data;
			        }else{
			        	json_data+=data+',';
			        }
			    });
			    json_data='['+json_data+']';
			    $.ajax({type:"POST",url:jypath+"/scm/transfer/move",
			    	data: "t="+json_data+"&status="+type+"&remarks="+$('#move-transferForm').find("textarea[name=remarksTwo]").val(),dataType:"text",success:function(data,textStatus){ 
					   var json_obj=eval('('+data+')');
					   if(json_obj.res==1){
			        		that.dialog("close");      
			        		JY.Model.info(json_obj.resMsg,function(){search();});
			        	}else{
			        		 JY.Model.error(json_obj.resMsg);
			        	}     	
			         }
			    });
			}	
		}});
	});
	
	
	
	var moudtlMap = {};
	$("#queryCode").typeahead({
        source: function (query, process) {
        	$.ajax({
				type:'POST',
				url:jypath+'/scm/purstorage/out/findSetCode',
				data:{code:query},
				dataType:'json',
				success:function(result,textStatus){
					moudtlMap = {};
					var list= [];
					 $.each(result.obj, function (index, ele) {
						 moudtlMap[ele.code] = ele.id;
						 list.push(ele.code);
                     });
					process(list);
				}
			});
        	
        },
        items: 20,
        afterSelect: function (value) {
        	$.ajax({
        		type:'POST',
        		url:jypath +'/scm/transfer/findByCode',
        		data:{code:value},
        		dataType:'json',
        		success:function(data,textStatus){
        			JY.Tools.populateForm("transferForm",data.obj);
        			/*setForm(data);*/
        		}
        	});
        },
        delay: 500,
        highlighter: function (item) {//item是用户输入
	        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')//这是将字符串安全格式化为正则表达式的源码
	            return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	                return '<strong style="color:red;">' + match + '</strong>'//将下拉列表中符合项中的LI的innerHTML相等部分加个strong标签
	            })
	    }
    });
});


function subcode(){
	var code=$("input[id='enteryno']").val();
	if(code.length != 0 && !code.match(/^\s+$/g)){
		JY.Ajax.doRequest(null,jypath +'/scm/transfer/findByCode',{code:code},function(data){
			 var html="";
			 html+="<tr>";
			 var remarks= $("#transferForm textarea[name$='remarks']").val();
			 if(remarks ==""){remarks="无";}
			 html+="<td class='center'><label> <input  type='checkbox' name='ids' value='"+data.obj.code+"' class='ace' /><span class='lbl'></span></label></td>";
			 html+="<td style='padding:0px;'><input jyValidate='required' type='text' name='code' readonly value='"+data.obj.code+"' style='width:100%;height:30px;border:none;'/><input type='hidden' name='remarks' value='"+remarks+"'/><input type='hidden' name='id' value='"+data.obj.id+"'/></td>";
			 html+="<td style='padding:0px;'><input jyValidate='required' type='text' name='num' onkeyup='JY.Validate.limitNum(this)' value='"+data.obj.num+"' style='width:100%;height:30px;border:1px solid #ccc;' onfocus=\"this.value=\"/></td>";
			 html+="<td style='padding:0px;'><input jyValidate='required' type='text' name='weight' onkeyup='JY.Validate.limitDouble(this)' value='"+data.obj.weight+"' style='width:100%;height:30px;border:1px solid #ccc;' onfocus=\"this.value=\"/></td>";
			 html+="<td style='padding:0px;'><input jyValidate='required' type='text' name='diffWeight' onkeyup='JY.Validate.limitDouble(this)' value='"+$("#transferForm input[name$='diffWeight']").val()+"' style='width:100%;height:30px;border:1px solid #ccc;' onfocus=\"this.value=\"/></td>";
			 html+="<td style='padding:0px;'><input readonly type='text' name='outWarehouseIdName' value='"+data.obj.outWarehouseIdName+"' style='width:100%;height:30px;border:1px solid #ccc;'/><input type='hidden' name='outWarehouseId' value='"+data.obj.outWarehouseId+"'/></td>";
			 html+="<td style='padding:0px;'><input readonly type='text' name='outLocationIdName'  value='"+data.obj.outLocationIdName+"' style='width:100%;height:30px;border:1px solid #ccc;'/><input type='hidden' name='outLocationId' value='"+data.obj.outLocationId+"'/></td>";
			 html+="<td style='padding:0px;'><select id='ow_"+data.obj.code+"' jyValidate='required' type='text'  name='inWarehouseId' value='' style='width:100%;height:30px;border:1px solid #ccc;' "+"onchange=chgWarehouseLocation('ow_"+data.obj.code+"','ol_"+data.obj.code+"')></select></td>";
			 html+="<td style='padding:0px;'><select id='ol_"+data.obj.code+"' jyValidate='required' type='text'  name='inLocationId' value='' style='width:100%;height:30px;border:1px solid #ccc;'></select></td>";
			 html+="</tr>";	
			 $("#transferAdd tbody").append(html);
			 warehouseForm("ow_"+data.obj.code);
		});
		$("input[id='enteryno']").val("");
	}
}




function transfer(code){
	JY.Ajax.doRequest(null,jypath +'/scm/transfer/findByCode',{code:code},function(data){
		setForm(data)
	});
	
}

function delMaterialin(){
	$('#transferAdd input[name="ids"]:checked').each(function(){  
		$(this).parent().parent().parent().remove();
	});  
}


function getbaseList(){
	JY.Model.loading();
	JY.Ajax.doRequest("transferListForm",jypath +'/scm/transfer/findByPage',null,function(data){
		 $("#transferTable tbody").empty();
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
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.transferNo)+"</td>";
            		 if(l.type=='0') html+="<td class='center hidden-480'><span class='label label-sm label-success'>原料</span></td>";
            		 else if(l.type == '1') html+="<td class='center hidden-480'><span class='label label-sm label-success'>辅料</span></td>";
            		 else if(l.type=='3')html+="<td class='center hidden-480'><span class='label label-sm label-success'>易耗品</span></td>";  		 
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.createUser)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.checkUser)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(new Date(l.createTime).Format('yyyy-MM-dd hh:mm:ss'))+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(new Date(l.updateTime).Format('yyyy-MM-dd hh:mm:ss'))+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(new Date(l.checkTime).Format('yyyy-MM-dd hh:mm:ss'))+"</td>";
            		 if(l.status=='0'){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>草稿</span></td>";
            			 html+=JY.Tags.setFunction1(l.id,permitBtn,'auditing,');
            		 }else if(l.status=='1'){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>待审核</span></td>";
            			 html+=JY.Tags.setFunction1(l.id,permitBtn,',');
            		 }else if(l.status=='2'){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已拒绝</span></td>";
            			 html+=JY.Tags.setFunction1(l.id,permitBtn,'del,examine,auditing,');
            		 }else if(l.status=='3'){
            			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>已完成</span></td>";
            			 html+=JY.Tags.setFunction1(l.id,permitBtn,'del,edit,examine,auditing,');
            		 }else if(l.status=='9'){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已删除</span></td>";
            			 html+=JY.Tags.setFunction1(l.id,permitBtn,'del,edit,examine,auditing,');
            		 }
            		 html+="</tr>";		 
            	 } 

        		 $("#transferTable tbody").append(html);
        		 JY.Page.setPage("transferListForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='11' class='center'>没有相关数据</td></tr>";
        		$("#transferTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
 	 
    	 JY.Model.loadingClose();
	 });
}

//保存和修改
function baseInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
    	buttons:[{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存为草稿","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
    	         {html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存并提交","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},
    	         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}

	
//加载查看页面
function viewInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
}
//加载修改页面
function editInfo(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,height:700,width:1050,modal:true,
		title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",
		title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",
			click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'>" +
					"</i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function')
					{cancelfn.call(this);}}}]});
}


function checkInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
		buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,3,"您确认审核通过吗");}}},
		        {html: "<i class='icon-remove bigger-110'></i>&nbsp;不通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,2,"您确认审核不通过吗");}}},
		        {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
	});
}

//清空页面
function cleanForm(){
	JY.Tags.cleanForm("transferForm");
	JY.Tags.cleanForm("baseForm");
	$("#transferForm input[name='num']").val("");
	$("#transferDivForm input[id='status']").val("0");
	JY.Tools.formDisabled("transferForm",false,function(){
		$("#transferForm .icon-remove").show();
		$("#transferForm .ui-datepicker-trigger").show();
	});
	
	
}

function search(){
	$("#searchBtn").trigger("click");
}


function addInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({
    	resizable:false,
    	height:attr.height,
    	width:attr.width,
    	modal:true,
    	title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",
     title_html:true,
     buttons:[{
     	html: "<i class='icon-ok bigger-110'></i>&nbsp;继续添加",
     	"class":"btn btn-primary btn-xs",
     	click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},
     	{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存",
     	 "class":"btn btn-primary btn-xs",
     	 click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
     	 {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",
     	 click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);
     	 }
     	}
     	}]
     });
}

/*删除移库*/
function del(id){
	JY.Model.confirm("确认删除吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/transfer/del',{id:id},function(data){
			JY.Model.info(data.resMsg,function(){search();});
		});
	});
}



/*
	移库审核
	function auditing(id){
		cleanAdd();
		$('#baseForm').find('.caozuo').hide();
		$('#baseForm').find('.btnClass').hide();
		$('#baseForm').find('input,select,textarea').attr('disabled','disabled');
		JY.Ajax.doRequest(null,jypath +'/scm/materialin/view',{id:id},function(data){
			setForm(data);
			checkInfo({id:"baseDiv",title:"审核入库单",height:"700",width:"1024",savefn:function(checkType,checkTitle){
				var that =$(this);
				JY.Model.confirm(checkTitle,function(){	
					JY.Ajax.doRequest(null,jypath +'/scm/materialin/check',{id:id,status:checkType},function(data){
						JY.Model.info(data.resMsg,function(){search();});
						that.dialog("close");
					});
				});
			}});
			
		});
	}
*/

//加载仓库
/*	function chgWarehouseLocation(obj){
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
}*/
/*	//加载仓位
	function  warehouseForm(id){
		var selectWarehouse = $('#'+id);
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
			//$('#selectWarehouseLocation').append('<option value="'+data.obj.location.id+'" selected="selected">'+data.obj.location.name+'</option>');
		});
	}
	
	function chgWarehouseLocation(dom,wind){
		var va =$('#'+dom+' option:selected').val();
		JY.Ajax.doRequest(null,jypath +'/scm/warehousing/selectWarehousingLocation',{id:va},function(data){
		    var obj=data.obj;
		    $("#"+wind).empty();
		    var _html = '<option value="">请选择</option>';
			for (var i = 0; i < obj.length; i++) {
				_html += '<option value="'+obj[i].key+'">'+obj[i].value+'</option>';
	        }
			$("#"+wind).append(_html);
		});
	}*/

/*仓库的列表和添默认值 */
function warehouseForm(selectWarehouse){
	var selectWarehouse = $('#'+selectWarehouse+'');
	JY.Ajax.doRequest(null,jypath +'/scm/warehousing/selectWarehousing',null,function(data){
	    var obj=data.obj.list;
	    selectWarehouse.html("");
	    selectWarehouse.append('<option value="">请选择</option>');
		for (var i = 0; i < obj.length; i++) {
			if(data.obj.warehouse!=undefined && obj[i].key==data.obj.warehouse.id){
				selectWarehouse.append('<option selected="selected" value="'+obj[i].key+'">'+obj[i].value+'</option>');
			}else{
				selectWarehouse.append('<option value="'+obj[i].key+'">'+obj[i].value+'</option>');
			}
        }
	});
}
/*仓位列表*/
function chgWarehouseLocation(obj,selectWarehouseLocation,locationId){
	var va =$('#'+obj+' option:selected').val();
	var selectWarehouse = $('#'+selectWarehouseLocation+'');
	JY.Ajax.doRequest(null,jypath +'/scm/warehousing/selectWarehousingLocation',{id:va},function(data){
	    var obj=data.obj;
	    selectWarehouse.html("");
	    selectWarehouse.append('<option value="">请选择</option>');
		for (var i = 0; i < obj.length; i++) {
			selectWarehouse.append('<option value="'+obj[i].key+'">'+obj[i].value+'</option>');
        }
		if(locationId!=undefined){$('#'+selectWarehouseLocation+'').val(locationId);}
	});
}


/*表单添默认值 */
/*function warehouseForm(selectWarehouse,selectWarehouseLocation){
	var selectWarehouse = $('#'+selectWarehouse+'');
	JY.Ajax.doRequest(null,jypath +'/scm/warehousing/selectWarehousing',null,function(data){
	    var obj=data.obj.list;
	    selectWarehouse.html("");
	    $('#'+selectWarehouseLocation+'').html("");
	    selectWarehouse.append('<option value="">请选择</option>');
		for (var i = 0; i < obj.length; i++) {
			if(data.obj.warehouse!=undefined && obj[i].key==data.obj.warehouse.id){
				selectWarehouse.append('<option selected="selected" value="'+obj[i].key+'">'+obj[i].value+'</option>');
			}else{
				selectWarehouse.append('<option value="'+obj[i].key+'">'+obj[i].value+'</option>');
			}
        }
		if(data.obj.location!=undefined){$('#'+selectWarehouseLocation+'').append('<option value="'+data.obj.location.id+'" selected="selected">'+data.obj.location.name+'</option>');}
		
	});
}*/


//set赋值
function setForm(data){
	var l=data.obj;
	$("#transferForm input[name$='transferId']").val(JY.Object.notEmpty(l.transferId));
	$("#transferForm input[name$='transferNo']").val(JY.Object.notEmpty(l.transferNo));
	$("#transferForm input[name$='code']").val(JY.Object.notEmpty(l.code));
	$("#transferForm input[name$='num']").val(JY.Object.notEmpty(l.num));
	$("#transferForm input[name$='weight']").val(JY.Object.notEmpty(l.weight));
	$("#transferForm input[name$='diffWeight']").val(JY.Object.notEmpty(l.diffWeight));
	$("#transferForm input[name$='outWarehouseId']").val(JY.Object.notEmpty(l.outWarehouseId));
	$("#transferForm input[name$='outLocationId']").val(JY.Object.notEmpty(l.outLocationId));
//	$("#transferForm #selectWarehouse").val(JY.Object.notEmpty(l.inWarehouseId));
//	$("#transferForm #selectWarehouseLocation").val(JY.Object.notEmpty(l.inLocationId));
	$("#transferForm input[name$='description']").val(JY.Object.notEmpty(l.description));
	
	$("#move-transferForm input[name$='checkUser']").val(JY.Object.notEmpty(l.checkUser));
	$("#move-transferForm input[name$='checKtime']").val(JY.Object.notEmpty(l.checKtime));
	$("#move-transferForm input[name$='updateUser']").val(JY.Object.notEmpty(l.updateUser));
	$("#move-transferForm input[name$='updateTime']").val(JY.Object.notEmpty(l.updateTime));
	$("#move-transferForm input[name$='createuser']").val(JY.Object.notEmpty(l.createuser));
	$("#move-transferForm input[name$='createTime']").val(JY.Object.notEmpty(l.createTime));
	$("#move-transferForm input[name$='description']").val(JY.Object.notEmpty(l.description));
	$("#move-transferForm input[name$='remarks']").val(JY.Object.notEmpty(l.remarks));
	$("#move-transferForm input[name$='transferNo']").val(JY.Object.notEmpty(l.transferNo));
	
	$("#ScmaterialForm #type").val(JY.Object.notEmpty(l.type));
	$("#move-transferForm input[name$='status']").val(JY.Object.notEmpty(l.status));
	
	

}


/*删除*/
function delMaterialin(){
	var chks =[];    
	$('#transferAdd input[name="ids"]:checked').each(function(){ 
		chks.push($(this).val());    
	});     
	if(chks.length==0) {
		JY.Model.info("您没有选择任何内容!"); 
	}else{
		JY.Model.confirm("确认要删除选中的数据吗?",function(){	
			$('#transferAdd input[name="ids"]:checked').each(function(){  
				$(this).parent().parent().parent().remove();
			}); 
		});		
	}	
}

function check(id){
	cleanTransfer();
	$('.btnClass').hide();
	/*$('#move-transferForm').find('input,select,textarea').attr('disabled','disabled');*/
	JY.Ajax.doRequest(null,jypath +'/scm/transfer/view',{id:id},function(data){
		setFormTransfer(data);
		viewInfo({id:"transferDiv",title:"查看移库单",height:"700",width:"1024"})
	});
}

function auditing(id){
	cleanTransfer();
	$('.btnClass').hide();
	/*$('#move-transferForm').find('input,select,textarea').attr('disabled','disabled');*/
	JY.Ajax.doRequest(null,jypath +'/scm/transfer/view',{id:id},function(data){
		setFormTransfer(data);
	});
	checkInfo({id:"transferDiv",title:"查看移库单",height:"700",width:"1024",savefn:function(e){
		JY.Model.confirm("确认审核吗？",function(){	
			JY.Ajax.doRequest(null,jypath +'/scm/transfer/auditing',{id:id},function(data){
				JY.Model.info(data.resMsg,function(){search();});
			});
		});
	}});
}

function edit(id){
	cleanTransfer();
	$('.btnClass').show();
	/*$('#move-transferForm').find('input,select,textarea').removeAttr('disabled');*/
	JY.Ajax.doRequest(null,jypath +'/scm/transfer/view',{id:id},function(data){
		setFormTransfer(data);
		viewInfo({id:"transferDiv",title:"查看移库单",height:"700",width:"1024"})
	});
}

/*设置表单值*/
function setFormTransfer(data){
	 $("#move-transferForm span[id$='createUser']").text(JY.Object.notEmpty(data.obj.list.createName));
	 if(JY.Object.notEmpty(data.obj.list.createTime)){
		 $("#move-transferForm span[id$='createTime']").text(JY.Object.notEmpty(new Date(data.obj.list.createTime).Format("yyyy/MM/dd hh:mm:ss")));
	 }
	 $("#move-transferForm span[id$='updateUser']").text(JY.Object.notEmpty(data.obj.list.updateName));
	 if(JY.Object.notEmpty(data.obj.list.updateTime)){
		 $("#move-transferForm span[id$='updateTime']").text(JY.Object.notEmpty(new Date(data.obj.list.updateTime).Format("yyyy/MM/dd hh:mm:ss")));
	 }
	 $("#move-transferForm span[id$='checkUser']").text(JY.Object.notEmpty(data.obj.list.checkName));
	 if(JY.Object.notEmpty(data.obj.list.checkTime)){
		 $("#move-transferForm span[id$='checkTime']").text(JY.Object.notEmpty(new Date(data.obj.list.checkTime).Format("yyyy/MM/dd hh:mm:ss")));
	 }
	 $("#move-transferForm input[name$='transferNo']").val(JY.Object.notEmpty(data.obj.list.transferNo));
	 if(data.obj.list.type=="0"){
		 $("#move-transferForm input[name$='typeName']").val("移库");
	 }
	 $("#move-transferForm input[name$='type']").val(JY.Object.notEmpty(data.obj.list.type));
	 $("#move-transferForm textarea[name$='remarksTwo']").val(data.obj.list.description);
	 
	 
	
	 var results=data.obj.details;
	 if(results!=null&&results.length>0){
		 var html="";
		 for(var i = 0;i<results.length;i++){
			 var l=results[i];
			 html+="<tr>";
			 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /><span class='lbl'></span></label></td>";
			/* html+="<td class='center hidden-480'>"+l.name+"</td>";*/
			 html+="<td class='center hidden-480'>"+l.code+"</td>";
			/* if(l.feeType=='1'){
				 html+="<td class='center hidden-480'>克 </td>";
			 }else{
				 html+="<td class='center hidden-480'>件</td>";
			 }*/
			 html+="<td class='center hidden-480'>"+l.num+"</td>";
			 html+="<td class='center hidden-480'>"+l.weight+"</td>";
			 html+="<td class='center hidden-480'>"+l.diffWeight+"</td>";
			 html+="<td class='center hidden-480'>"+l.outWarehouseIdName+"</td>";
			 html+="<td class='center hidden-480'>"+l.outLocationIdName+"</td>";
			 html+="<td class='center hidden-480'>"+l.inWarehouseIdName+"</td>";
			 html+="<td class='center hidden-480'>"+l.inLocationIdName+"</td>";
			 html+="</tr>";	
		 }
		 $("#transferAdd tbody").append(html);
	 }	 
}

/*清空表单弹出列表数据数据*/
function cleanTransfer(){
	 $('.btnClass').show();
	/* $('#move-transferForm').find('input,select,textarea').removeAttr('disabled');*/
	 JY.Tags.cleanForm("move-transferForm");
	 $("#transferAdd tbody").html("");
	 $("#move-transferForm span[id$='createUser']").text("");
	 $("#move-transferForm span[id$='createTime']").text("");
	 $("#move-transferForm span[id$='updateUser']").text("");
	 $("#move-transferForm span[id$='updateTime']").text("");
	 $("#move-transferForm span[id$='checkUser']").text("");
	 $("#move-transferForm span[id$='checkTime']").text("");
}
