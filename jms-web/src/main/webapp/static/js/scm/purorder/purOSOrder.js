$(function(){
	/*warehouseForm('selectOutWarehouseId','selectOutLocationId');*/
	setBaseData();
	getbaseList(1);
	warehouseForm();
	var yes = $("#stateYes").val();
	if(yes=="true"){
		$("#selectisValid").hide();
	}else{
		JY.Dict.setSelect("selectisValid","SCM_ORDER_STATUS",2,"全部");
	}
	
	JY.Dict.setSelect("selectType","SCM_OUTBOUND_TYPE",2,"请选择");
	JY.Dict.setSelect("balancetype","SCM_BALANCE_TYPE",2,"请选择");
	
	/*var wdata = getWarehouseData();*/
	
	$("#dateBegin").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd',
		changeYear: true,
		changeMonth:true,
		onSelect:function(dateText, inst) { }
	});
	$("#dateEnd").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd',
		changeYear: true,
		changeMonth:true,
		onSelect:function(dateText, inst) { }
	});

	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getPreOrgTree',null,function(data){
		$.jy.dropTree.init({
			rootId:"queryinOrg",
			displayId:"queryinOrgName",
			data:data.obj,
			dataFormat:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'},
			isExpand:true,
			resetFn:function(){
				$("#purOSOrderBaseForm #queryinOrgName").val('');
				$("#purOSOrderBaseForm #queryinOrgId").val('');
			},
			clickFn:function(node){
				$("#purOSOrderBaseForm #queryinOrgId").val(node.id);
			}
		});
		$.jy.dropTree.init({
			rootId:"queryoutOrg",
			displayId:"queryoutOrgName",
			data:data.obj,
			dataFormat:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'},
			isExpand:true,
			resetFn:function(){
				$("#purOSOrderBaseForm #queryoutOrgName").val('');
				$("#purOSOrderBaseForm #queryoutOrgId").val('');
			},
			clickFn:function(node){
				$("#purOSOrderBaseForm #outOrgId").val(node.id);
			}
		});
	});

	//出库单物料条码检索
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
        		url:jypath+'/scm/purstorage/out/findByCode',
        		data:{code:value,type:0},
        		dataType:'json',
        		success:function(data,textStatus){
        			JY.Tools.populateForm("purOSOrderForm",data.obj);
        			var str="";
        			 if(data.obj.feeType=='2'){
        				 str+="克";
        				 /*$("#purOSOrderForm input[name$='weight']").setAttribute("onchange",changePrice);*/
    				 }else{
    					 str+="件";
    					 /*$("#purOSOrderForm input[name$='num']").setAttribute("onchange",changePrice);*/
    				 }
        			 $("#purOSOrderForm input[name$='feeType']").val(str)
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
	
	
	//供应商检索
	$("#queryFranchisee").typeahead({
	    source: function (query, process) {
	    	$.ajax({
				type:'POST',
				url:jypath+'/scm/franchisee/findLongNamePage',
				data:{longName:query},
				dataType:'json',
				success:function(result,textStatus){ 
					supplierMap = {};
					var array= [];
					 $.each(result.obj, function (index, ele) {
						 supplierMap[ele.longName] = ele.id;
						 array.push(ele.longName);
	                 });
					process(array);
				}
			});
	    	
	    },
	    items: 20,
	    afterSelect: function (item) {
	    	$("#baseForm input[name$='supplierId']").val(supplierMap[item]);
	    },
	    delay: 500,
	    highlighter: function (item) {
	        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')
	            return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	                return '<strong style="color:red;">' + match + '</strong>';
	            })
	    }
	});
	
	
	/*增加搜索条码回车事件*/
	$("#enteryno").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 subcode2();
			 search();
		 } 
	});
	
	/*增加搜索入库单回车事件*/
	$("#productinNo").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 subcode();
			 search();
		 } 
	});
	
	/*添加采购出库单*/
	$('#add').on('click', function(e) {
		e.preventDefault();
		cleanPurOSOrderForm();
		cleanAdd();
		loadOrgTree();
		baseInfo({id:"baseDiv",title:"出库单增加",height:"700",width:"1024",savefn:function(types){
			var that =$(this);
			if(JY.Validate.form("baseForm")){
				var json_data="";
		 	    var last_index=$('#purOSOrderAdd tbody tr').length-1;
		 	    $('#purOSOrderAdd tbody tr').each(function(element,index){
		 	    var data='{"code":"'+$(this).find("input[name=code]").val()+'","num":"'+$(this).find("input[name=num]").val()+'","weight":"'+$(this).find("input[name=weight]").val()+'"}';	
		 	    if($(this).index()==last_index){
		        	json_data+=data;
		        }else{
		        	json_data+=data+',';
		        }
			    });
			    json_data='['+json_data+']';
			   $.ajax({type:"POST",url:jypath+"/scm/purstorage/out/add",data: "myData="+json_data+"&remarks="+$("textarea[name='remarks']").val()+"&balancetype="+$("select[id='balancetype']").val()+"&ratio="+$("input[name='ratio']").val()+"&warehouseid="+$("select[name='warehouseid']").val()+"&inOrgId="+$('#baseForm').find("#inOrgId").val()+"&status="+types+"&orderNum="+$('#baseForm').find("input[name=orderNum]").val()+"&type="+$('#baseForm').find("select[name=type]").val()+"&supplierId="+$('#baseForm').find("input[name=supplierId]").val(),dataType:"text",success:function(data,textStatus){  			        	 
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
		}})	
	});
	/*2：不相同的出信息*/
	$('#addBtn').on('click', function(e) {
		e.preventDefault();
		cleanPurOSOrderForm();
		$("#purOSOrderForm input[name$='num']").prop("readonly",false);
		$("#purOSOrderForm input[name$='weight']").prop("readonly",false);
		addInfo2({id:"purOSOrderDiv",title:"出库信息",height:"540",width:"380",savefn:function(e){
			var that =$(this);
			var html="";		    		
			if(JY.Validate.form("purOSOrderForm")){
				var code=$("#purOSOrderForm input[name$='code']").val();
				var num=$("#purOSOrderForm input[name$='num']").val();
				var weight=$("#purOSOrderForm input[name$='weight']").val();
				JY.Ajax.doRequest(null,jypath +'/scm/purstorage/out/queryCode',{code:code,num:num,weight:weight,type:0},function(data){
					 commCode(data.obj);
		    		 if(e==0){
		    			 that.dialog("close");
		    		 }
		    		 cleanPurOSOrderForm();
				});
			}
		}})	
	});
	
	//查看
	$('#view').on('click', function(e) {
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
			view(chks[0])
		}
	});
	
	//修改
	$('#edit').on('click', function(e) {
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
			edit(chks[0])
		}
	});
	
	//批量删除   
	$('#del').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#purOSOrderTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			$("#jyConfirm").children().children().find(".causesDiv").remove();
			JY.Model.confirm("确认要删除选中的数据吗?",function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/purstorage/out/delete',{cheks:chks.toString()},function(data){
					JY.Model.info(data.resMsg,function(){search();});
				});
			});	
		}		
	});
	
	//审核
	$('#aduit').on('click', function(e) {
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
			check(chks[0])
		}
	});
})


/*搜索条码*/
function subcode2(){
	var list=[];
	var code=$("input[id='enteryno']").val();
	var balancetype=$("select[id='balancetype']").val();
	var ratio=$("input[name='ratio']").val();
	var warehouseid=$("select[name='warehouseid']").val();
	var type=0;
	if(code.length!=0 && balancetype.length && ratio.length && warehouseid.length!=0){
		JY.Ajax.doRequest(null,jypath +'/scm/purstorage/out/queryCode',{code:code,balancetype:balancetype,ratio:ratio,type:type,warehouseid:warehouseid},function(data){
			if(JY.Object.notEmpty(data.resMsg).length>0){
				$('#enteryno').tips({
			         msg: data.resMsg,
			         bg: '#FF2D2D',
			         time: 1
			     });
			}else{
				commCode(data.obj);
			}
		});
		$("input[id='enteryno']").val("");
	}else{
		$('#enteryno').tips({
	         msg: "选择结价类型和仓库、填写系数!",
	         bg: '#FF2D2D',
	         time: 1
	    });
	}
}

/*搜索入库单号*/
function subcode(){
	var code=$("input[id='productinNo']").val();
	var balancetype=$("select[id='balancetype']").val();
	var ratio=$("input[name='ratio']").val();
	var warehouseid=$("select[name='warehouseid']").val();
	var type=1;
	if(code.length!=0 && balancetype.length && ratio.length && warehouseid.length!=0){
		JY.Ajax.doRequest(null,jypath +'/scm/purstorage/out/queryCode',{code:code,balancetype:balancetype,ratio:ratio,type:type,warehouseid:warehouseid},function(data){
			if(JY.Object.notEmpty(data.resMsg).length>0){
				$('#productinNo').tips({
			         msg: data.resMsg,
			         bg: '#FF2D2D',
			         time: 1
			    });
			}else{
				commCode(data.obj);
			}
		});
		$("input[id='productinNo']").val("");
	}else{
		$('#productinNo').tips({
	         msg: "选择结价类型和仓库、填写系数!",
	         bg: '#FF2D2D',
	         time: 1
	    });
	}
}


/*搜索条码或入库单号公共数据*/
function commCode(obj){
	var list=[];
	var html="";
	if(obj!=null){
		for(var i=0;i<obj.length;i++){ 
			$("#purOSOrderAdd input[name$='code']").each(function(){  
				list.push($(this).val());
			});
			if(list.indexOf(obj[i].code)==-1){
				 html+="<tr>";
				 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+JY.Object.notEmpty(obj[i].code)+"' class='ace' /><span class='lbl'></span></label></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='name' readonly  value='"+JY.Object.notEmpty(obj[i].name)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='code' readonly value='"+JY.Object.notEmpty(obj[i].code)+"' style='width:100%;height:30px;border:none;'/><input type='hidden' name='remarks' value='无'/><input type='hidden' name='id' value='"+JY.Object.notEmpty(obj[i].id)+"'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='cerno' readonly  value='"+JY.Object.notEmpty(obj[i].cerno)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='proRemarks' readonly  value='"+JY.Object.notEmpty(obj[i].proRemarks)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 if(obj[i].feeType=='2'){
					 html+="<td style='padding:1px;'><input class='center' type='text' name='feeType' readonly  value='克' style='width:100%;height:30px;border:none;' readonly /></td>";
				 }else{
					 html+="<td style='padding:1px;'><input class='center' type='text' name='feeType' readonly  value='件' style='width:100%;height:30px;border:none;' readonly /></td>";
				 }
				 html+="<td style='padding:1px;'><input class='center' type='text' name='num' onkeyup='JY.Validate.limitNum(this)'  value='"+JY.Object.notNumber(obj[i].num)+"' onchange='changePrice($(this))' style='width:100%;height:30px;border:none;'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='weight' onkeyup='JY.Validate.limitDouble(this)' value='"+parseFloat(JY.Object.notNumber(obj[i].weight)).toFixed(4)+"' onchange='changePrice($(this))' style='width:100%;height:30px;border:none;'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='price' readonly  value='"+parseFloat(JY.Object.notNumber(obj[i].price)).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='costing' readonly  value='"+parseFloat(JY.Object.notNumber(obj[i].costing)).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='pradeprice' readonly  value='"+parseFloat(JY.Object.notNumber(obj[i].pradeprice)).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 html+="</tr>";	
			}else{
				$("input[id='enteryno']").val("");
				$('#enteryno').tips({
			         msg: "条码已存在!",
			         bg: '#FF2D2D',
			         time: 1
			    });
			}	
		}
	    $("#purOSOrderAdd tbody").append(html);
	    commFoot();
	}
}

function changePrice(num){
	var id=num.parent().parent().find('input[name="id"]').val();
	var code=num.parent().parent().find('input[name="code"]').val();
	var weight=num.parent().parent().find('input[name="weight"]').val();
	var number=num.parent().parent().find('input[name="num"]').val();
	var balancetype=$("select[id='balancetype']").val();
	var ratio=$("input[name='ratio']").val();
	var warehouseid=$("select[name='warehouseid']").val();
	JY.Ajax.doRequest(null,jypath +'/scm/purstorage/out/queryCode',{code:code,weight:weight,num:number,type:0,id:id,warehouseid:warehouseid,balancetype:balancetype,ratio:ratio},function(data){
		num.parent().parent().find('input[name="weight"]').val(parseFloat(JY.Object.notNumber(data.obj[0].weight)).toFixed(4));
		num.parent().parent().find('input[name="num"]').val(JY.Object.notNumber(data.obj[0].num));
		num.parent().parent().find('input[name="costing"]').val(parseFloat(JY.Object.notNumber(data.obj[0].costing)).toFixed(4));
		num.parent().parent().find('input[name="pradeprice"]').val(parseFloat(JY.Object.notNumber(data.obj[0].pradeprice)).toFixed(4));
		commFoot();
	});
}

function commFoot(){
	 $("#purOSOrderAdd tfoot").html("");
     var num=0,weight=0,costing=0,pradeprice=0,price=0;
	 $('#purOSOrderAdd').find('input[name="num"]').each(function(element,index){
		 if($(this).val()!=''){num+=parseFloat($(this).val());}
	 });
	 $('#purOSOrderAdd').find('input[name="weight"]').each(function(element,index){
		 if($(this).val()!=''){ weight+=parseFloat($(this).val());}
	 });
	 $('#purOSOrderAdd').find('input[name="costing"]').each(function(element,index){
		 if($(this).val()!=''){costing+=parseFloat($(this).val());}
	 });
	 $('#purOSOrderAdd').find('input[name="pradeprice"]').each(function(element,index){
		 if($(this).val()!=''){pradeprice+=parseFloat($(this).val());}
	 });
	 $('#purOSOrderAdd').find('input[name="price"]').each(function(element,index){
		 if($(this).val()!=''){price+=parseFloat($(this).val());}
	 });
     var foot="";
	 foot+="<tr>";
	 foot+="<td class='center'>合计</td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+num+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(weight).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(price).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(costing).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(pradeprice).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="</tr>";	
	 $("#purOSOrderAdd tfoot").append(foot);
}




function delpurOSOrder(){
	var chks =[];    
	$('#purOSOrderAdd input[name="ids"]:checked').each(function(){ 
		chks.push($(this).val());    
	});     
	if(chks.length==0) {
		JY.Model.info("您没有选择任何内容!"); 
	}else{
		$("#jyConfirm").children().children().find(".causesDiv").remove();
		JY.Model.confirm("确认要删除选中的数据吗?",function(){	
			$('#purOSOrderAdd input[name="ids"]:checked').each(function(){  
				$(this).parent().parent().parent().remove();
				commFoot();
				reomoveContext("purOSOrderAdd");
			}); 
		});		
	}	
}

function cleanAdd(){
	$.jy.dropTree.restStyle("inOrg");
	$.jy.dropTree.restStyle("outOrg");
	JY.Tags.cleanForm("baseForm");
	$("#purOSOrderAdd tbody").empty();
	$('#baseForm').find('.btnClass').show();
	$('#baseForm').find('input,select,textarea').removeAttr('disabled');
	$("#baseForm span[id$='createUser']").text('');
	$("#baseForm span[id$='createTime']").text('');
	$("#baseForm span[id$='updateUser']").text('');
	$("#baseForm span[id$='updateTime']").text('');
	$("#baseForm span[id$='checkUser']").text('');
	$("#baseForm span[id$='checkTime']").text('');
	$('#baseForm').find('td[name="typeOne"]').hide();
	$('#baseForm').find('td[name="typeTwo"]').hide();
	$("#baseForm #causesDiv").addClass("hide");
}



function search(){
	$("#searchBtn").trigger("click");
}


function chgOutLocationId(obj,selectOutLocationId,locationId){
	var selectWarehouse = $('#'+selectOutLocationId+'');
		var va=obj;
	JY.Ajax.doRequest(null,jypath +'/scm/warehousing/selectWarehousingLocation',{id:va},function(data){
	    var obj=data.obj;
	    selectWarehouse.html("");
	    selectWarehouse.append('<option value="">请选择</option>');
		for (var i = 0; i < obj.length; i++) {
			
				selectWarehouse.append('<option value="'+obj[i].key+'">'+obj[i].value+'</option>');
			
        }
		if(locationId!=undefined){$('#'+selectOutLocationId+'').val(locationId);}
	});
}

function renderOption(dom,wind){
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
}

function cleanPurOSOrderForm(){
	$("#purOSOrderForm input[name='num']").val("");
	JY.Tags.cleanForm("purOSOrderForm");
}



function viewOut(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,
		buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;打印预览","class":"btn btn-primary btn-xs",click:function(){print()}},
		         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
}
function editInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
}
/*function AudingInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"审核")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;不通过","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
}*/

function checkInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
		buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,2,"您确认审核通过吗");}}},
		        {html: "<i class='icon-remove bigger-110'></i>&nbsp;不通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,4,"您确认审核不通过吗");}}},
		        {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
	});
}

function addInfo2(attr){
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
/*出库列表*/
function getbaseList(init){
	if(init==1)$("#purOSOrderBaseForm .pageNum").val(1);	
	var state = $('#stateValue').val();
	JY.Model.loading();
	JY.Ajax.doRequest("purOSOrderBaseForm",jypath +'/scm/purstorage/out/findByPage?state='+state,null,function(data){
		 $("#purOSOrderTable tbody").empty();
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
            		 html+="<td class='center hidden-480'><a style='cursor:pointer;' onclick='view(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.outBoundNo)+"</a></td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.orderNum)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.inOrgName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notNumber(l.num)+"</td>";
            		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notNumber(l.weight)).toFixed(4)+"</td>";
            		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notNumber(l.totalCosing)).toFixed(4)+"</td>";
            		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notNumber(l.totalPradeprice)).toFixed(4)+"</td>";
            		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notNumber(l.totalPrice)).toFixed(4)+"</td>";
            		 if(l.type=="1"){
               			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>采购出库</span></td>";
               		 }else if(l.type=="2"){
               			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>销售出库</span></td>";
               		 }else if(l.type=="3"){
               			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>调拨出库</span></td>";
               		 }else{
               			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>委外加工</span></td>";
               		 }
            		 if(l.status=="0"){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>草稿</span></td>";
            		 }else if(l.status=="1"){
            			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>待审核</span></td>";
            		 }else if(l.status=="2"){
            			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>已审核</span></td>";
            		 }else if(l.status=="3"){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已完成</span></td>";
            		 }else if(l.status=="9") {
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已删除</span></td>";
            		 }else if(l.status=="4"){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已拒绝</span></td>";
            		 }
            		 if(l.deleteTag==0){
            			 html+= "<td class='center hidden-480'><span class='label label-sm label-success'>正常</span></td>";
            		 }else{
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in' >删除</span></td>";
            			
            		 }
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.outOrgName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(new Date(l.createTime).Format('yyyy-MM-dd'))+"</td>";
            		 html+="</tr>";		 
            	 } 
        		 $("#purOSOrderTable tbody").append(html);
        		 JY.Page.setPage("purOSOrderBaseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='17' class='center'>没有相关数据</td></tr>";
        		$("#purOSOrderTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
    		 JY.Model.loadingClose();
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

/*出库明细*/
function view(id){
	cleanAdd();
	$('#baseForm').find('.btnClass').hide();
	JY.Ajax.doRequest(null,jypath +'/scm/purstorage/out/find',{id:id},function(data){
		setForm2(data);
		if(data.obj.purOSOrder.status=="4"){
			$("#baseForm #causesDiv").removeClass("hide");
		}
		$('#baseForm').find('input,select,textarea').attr('disabled','disabled');
		viewOut({id:"baseDiv",title:"出库查看",height:"700",width:"1024"})
	});
}

/*修改出库*/
function edit(id){
	cleanAdd();
	cleanPurOSOrderForm();
	loadOrgTree();
	JY.Ajax.doRequest(null,jypath +'/scm/purstorage/out/find',{id:id,type:1},function(data){
		setForm2(data);
		if(data.obj.purOSOrder.status=="4"){
			$("#baseForm #causesDiv").removeClass("hide");
		}
		baseInfo({id:"baseDiv",title:"出库修改",height:"720",width:"1024",savefn:function(types){
			var that =$(this);
			if(JY.Validate.form("baseForm")){
				var json_data="";
		 	    var last_index=$('#purOSOrderAdd tbody tr').length-1;
		 	    $('#purOSOrderAdd tbody tr').each(function(element,index){
		 	    	var data='{"id":"'+$(this).find("input[name=id]").val()+'","outBoundNo":"'+$(this).find("input[name=outBoundNo]").val()+'","code":"'+$(this).find("input[name=code]").val()+'","name":"'+$(this).find("input[name=name]").val()+'","num":"'+$(this).find("input[name=num]").val()+'","weight":"'+$(this).find("input[name=weight]").val()+'","wageBasic":"'+$(this).find("input[name=wageBasic]").val()+'","wageAdd":"'+$(this).find("input[name=wageAdd]").val()+'","outWarehouseId":"'+$(this).find("select[name=outWarehouseId]").val()+'","outLocationId":"'+$(this).find("select[name=outLocationId]").val()+'","totalFee":"'+$(this).find("input[name=totalFee]").val()+'","remarks":"'+$(this).find("input[name=remarks]").val()+'"}';	
			 	    if($(this).index()==last_index){
			        	json_data+=data;
			        }else{
			        	json_data+=data+',';
			        }
			    });
			    json_data='['+json_data+']';
			   $.ajax({type:"POST",url:jypath+"/scm/purstorage/out/update",data: "myData="+json_data+"&remarks="+$("textarea[name='remarks']").val()+"&balancetype="+$("select[id='balancetype']").val()+"&ratio="+$("input[name='ratio']").val()+"&warehouseid="+$("select[name='warehouseid']").val()+"&outBoundNo="+$('#baseForm').find("input[name=outBoundNo]").val()+"&inOrgId="+$('#baseForm').find("#inOrgId").val()+"&outOrgId="+$('#baseForm').find("#outOrgId").val()+"&id="+$('#baseForm').find("input[name=id]").val()+"&status="+types+"&orderNum="+$('#baseForm').find("input[name=orderNum]").val()+"&type="+$('#baseForm').find("select[name=type]").val()+"&supplierId="+$('#baseForm').find("input[name=supplierId]").val(),dataType:"text",success:function(data,textStatus){  			        	 
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
}


function baseInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({
    	resizable:false,
    	height:attr.height,
    	width:attr.width,
    	modal:true,
    	title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",
     title_html:true,
     buttons:[
     	{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存为草稿",
        	 "class":"btn btn-primary btn-xs",
        	 click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
     	{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存并提交",
     	 "class":"btn btn-primary btn-xs",
     	 click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},
     	 {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",
     	 click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);
     	 }
     	}
     	}]
     });
}

/*设置表单值*/
function setForm2(data){
	 var p=data.obj.purOSOrder;
	 $("#baseForm select[name$='balancetype']").val(JY.Object.notEmpty(p.balancetype));
	 $("#baseForm select[name$='warehouseid']").val(JY.Object.notEmpty(p.warehouseid));
	 $("#baseForm input[name$='ratio']").val(JY.Object.notEmpty(p.ratio));
	 $("#baseForm input[name$='outBoundNo']").val(JY.Object.notEmpty(p.outBoundNo));
	 $("#baseForm input[name$='id']").val(JY.Object.notEmpty(p.id));
	 $("#inOrgId").val(JY.Object.notEmpty(p.inOrgId));
	 $("#outOrgId").val(JY.Object.notEmpty(p.outOrgId));
	 $("#inOrgName").val(JY.Object.notEmpty(p.inOrgName));
	 $("#outOrgName").val(JY.Object.notEmpty(p.outOrgName));
	 $("#outType").val(JY.Object.notEmpty(p.type));
	 $("#baseForm textarea[name$='description']").val(p.description);
	 $("#baseForm textarea[name$='remarks']").val(p.remarks);
	 $("#baseForm input[name$='orderNum']").val(JY.Object.notEmpty(p.orderNum));
	 $("#baseForm span[id$='createUser']").text(JY.Object.notEmpty(p.createName));
	 if(JY.Object.notEmpty(p.createTime)){
		 $("#baseForm span[id$='createTime']").text(JY.Object.notEmpty(new Date(p.createTime).Format("yyyy/MM/dd hh:mm")));
	 }	
	 $("#baseForm span[id$='updateUser']").text(JY.Object.notEmpty(p.updateName));
	 if(JY.Object.notEmpty(p.updateTime)){
		 $("#baseForm span[id$='updateTime']").text(JY.Object.notEmpty(new Date(p.updateTime).Format("yyyy/MM/dd hh:mm")));
	 }
	 $("#baseForm span[id$='checkUser']").text(JY.Object.notEmpty(p.checkName));
	 if(JY.Object.notEmpty(p.checkTime)){
		 $("#baseForm span[id$='checkTime']").text(JY.Object.notEmpty(new Date(p.checkTime).Format("yyyy/MM/dd hh:mm")));
	 }
	 $("#baseForm input[name$='supplierId']").val(JY.Object.notEmpty(p.supplierId));
	 $("#baseForm input[name$='franchiseeName']").val(JY.Object.notEmpty(p.franchiseeNameShort));
	 selectType(p.type)
	 commCode(data.obj.detail);
}
/*function getWarehouseData(){
	var _html = '<option value="">请选择</option>';
	$.ajax({
        type: 'POST',
        url: jypath +'/scm/warehousing/selectWarehousing',
        data: {},
        dataType: 'json',
        async: false,
        success: function(result, textStatus) {
        	var obj=result.obj.list;
    		for (var i = 0; i < obj.length; i++) {
    			_html += '<option value="'+obj[i].key+'">'+obj[i].value+'</option>';
            }
        }
    });
	return _html;
}*/

/*function aduit(id){
	cleanAdd();
	cleanPurOSOrderForm();
	$('#baseForm').find('.btnClass').hide();
	JY.Ajax.doRequest(null,jypath +'/scm/purstorage/out/find',{id:id,type:2},function(data){
		setForm2(data);
		$('#baseForm').find('input,select').attr('disabled','disabled');
		AudingInfo({id:"baseDiv",title:"审核出库",height:"700",width:"1024",
			savefn:function(){
				var description=$("#baseForm textarea[name$='description']").val();
					JY.Model.confirm("确认审核吗？",function(){
						JY.Ajax.doRequest(null,jypath +'/scm/purstorage/out/aduit',{id:id,status:"2",description:description},function(data){
							JY.Model.info(data.resMsg,function(){
								$("#baseDiv").dialog("close");
								search();
						});
					});
				});
			},
			cancelfn:function(){
				var description=$("#baseForm textarea[name$='description']").val();
				JY.Ajax.doRequest(null,jypath +'/scm/purstorage/out/aduit',{id:id,status:"4",description:description},function(data){
					JY.Model.info(data.resMsg,function(){
						$("#baseDiv").dialog("close");
						search();
					});
				});
			}
		});
	});
}*/

/*审核原料出库*/
function check(id){
	cleanAdd();
	cleanPurOSOrderForm();
	$('#baseForm').find('.btnClass').hide();
	JY.Ajax.doRequest(null,jypath +'/scm/purstorage/out/find',{id:id,type:2},function(data){
		setForm2(data);
		$('#baseForm').find('input,select,textarea').attr('disabled','disabled');
		checkInfo({id:"baseDiv",title:"审核出库",height:"700",width:"1024",savefn:function(checkType,checkTitle){
			var model1 =$(this);
			var html="";
			$("#jyConfirm").children().children().find(".causesDiv").remove();
			if(checkType==4){
				$("#jyConfirm").children().children().append('<div class="causesDiv"><form id="causesForm"><br><div style="float:left;font-size:13px;"><span style="float:left">驳回原因</span><br><input type="text" jyValidate="required" id="causesTxt" placeholder="请输入原因" style="width: 265px;"></div></form></div>');
			}
			var flag = false;
			if(checkType==4){
				flag = true;
			}
			JY.Model.confirm(checkTitle,function(){
				var model2 = $(this);
				var causes = $("#causesTxt").val();
				if(checkType==4&&!JY.Validate.form("causesForm")){
					return false;
				}
				JY.Ajax.doRequest(null,jypath +'/scm/purstorage/out/aduit',{id:id,status:checkType,description:causes,remarks:$("textarea[name='remarks']").val()},function(data){
					model1.dialog("close");
					model2.dialog("close"); 
					JY.Model.info(data.resMsg,function(){search();});
				});
			},function(){},flag)
		}});
	});
}

function loadOrgTree(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getPreOrgTree',null,function(data){
		$.jy.dropTree.init({
			rootId:"inOrg",
			displayId:"inOrgName",
			data:data.obj,
			dataFormat:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'},
			isExpand:true,
			resetFn:function(){
				$("#baseDiv #inOrgName").val('');
				$("#baseDiv #inOrgId").val('');
			},
			clickFn:function(node){
				$("#baseDiv #inOrgId").val(node.id);
			}
		});
		$.jy.dropTree.init({
			rootId:"outOrg",
			displayId:"outOrgName",
			data:data.obj,
			dataFormat:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'},
			isExpand:true,
			resetFn:function(){
				$("#baseDiv #outOrgName").val('');
				$("#baseDiv #outOrgId").val('');
			},
			clickFn:function(node){
				$("#baseDiv #outOrgId").val(node.id);
			}
		});
		$.jy.dropTree.checkNode("outOrg","outOrgName",curUser.orgId,function(){
			$("#baseDiv #outOrgId").val(curUser.orgId);
			$("#baseDiv #outOrgName").prop('disabled','disabled');
			$("#outOrg_clear").hide();
		});
	});
}

function selectType(type){
	if(type=='4'){
		$('#baseForm').find('td[name="typeOne"]').hide();
		$('#baseForm').find('td[name="typeTwo"]').show();
	}else{
		$('#baseForm').find('td[name="typeOne"]').show();
		$('#baseForm').find('td[name="typeTwo"]').hide();
	}
}


function selectInOrgId(){
	var orderNum=$("#baseDiv #orderNum").val();
	$("#baseDiv #orderNum").val("");
	JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/selectInOrgId',{orderNum:orderNum},function(data){
		$("#inOrgId").val(JY.Object.notEmpty(data.obj.inOrgId));
		$("#inOrgName").val(JY.Object.notEmpty(data.obj.inOrgName));
		$("#baseDiv #orderNum").val(JY.Object.notEmpty(data.obj.orderNum));
	});
}




/*仓库的列表和添默认值 */
function warehouseForm(){
	var selectWarehouse = $('#selectWarehouse');
	JY.Ajax.doRequest(null,jypath +'/scm/warehousing/warehousAll',null,function(data){
	    var obj=data.obj;
	    selectWarehouse.html("");
	    selectWarehouse.append('<option value="">请选择</option>');
		for (var i = 0; i < obj.length; i++) {
			selectWarehouse.append('<option value="'+obj[i].key+'">'+obj[i].value+'</option>');
        }
	});
}


function print(){
	 var id = $("#baseForm input[name='id']").val();
	 var reportNo = $("#baseForm input[name='outBoundNo']").val();
	 $("#printDiv").load(jypath +'/scm/purstorage/print?id='+id+'&type='+1,function(){
		 if(LODOP){
			 LODOP=getLodop();  
			 LODOP.PRINT_INIT("打印控件功能演示");
			 LODOP.ADD_PRINT_BARCODE(16,78,166,21,"128B",reportNo);
			 LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
			 LODOP.ADD_PRINT_HTM(0,0,"100%","100%",document.getElementById("testPrintdiv").innerHTML);
			 LODOP.PREVIEW();
		 }
	 })

}

function cleanBaseForm(){
	JY.Tags.cleanForm("purOSOrderBaseForm");
	$("#purOSOrderBaseForm input[name$='inOrgId']").val("");
	$("#purOSOrderBaseForm input[name$='outOrgId']").val("");
	setBaseData();
}
function setBaseData(){
	var date=new Date;
	var year=date.getFullYear(); 
	$("#dateBegin").val(year+"/1/1")
	$("#dateEnd").val(year+"/12/31")
}
