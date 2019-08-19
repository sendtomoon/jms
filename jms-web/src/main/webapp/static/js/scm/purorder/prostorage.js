var dataPrice=0;
$(function(){
	$.ajax({
		type:'POST',
		url:jypath +'/scm/product/query',
		data:{},
		dataType:'json',
		success:function(data,textStatus){ 
			/* $('.hidePrice').each(function(element,index){*/
				 if(data==1){
					 $('.hidePrice').show(); 
				 }else if(data==0){
					 $('.hidePrice').hide(); 
				}
		/*	 });*/
			dataPrice=data;
		}
	});
	setBaseData();
	getbaseList(1);
	var yes = $("#stateYes").val();
	if(yes=="true"){
		$("#selectisValid").hide();
	}else{
		JY.Dict.setSelect("selectisValid","SCM_ORDER_STATUS",2,"全部");
	}
	JY.Dict.setSelect("balancetype","SCM_BALANCE_TYPE",2,"请选择");
	JY.Dict.setSelect("selectType","SCM_OUTBOUND_TYPE",2,"请选择");
	
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

	
	var moudtlMap = {};
	$("#queryCode").typeahead({
        source: function (query, process) {
        	$.ajax({
				type:'POST',
				url:jypath+'/scm/prostorage/out/findSetCode',
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
        afterSelect: function (value) {
        	$.ajax({
        		type:'POST',
        		url:jypath+'/scm/prostorage/out/findByCode',
        		data:{code:value,balancetype:$("select[id='balancetype']").val(),ratio:$("input[name='ratio']").val(),type:0},
        		dataType:'json',
        		success:function(data,textStatus){
        			JY.Tools.populateForm("purOSOrderForm",data.obj);
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
	
	
	/*增加搜索条码回车事件*/
	$("#enteryno").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 subcode2();
		 } 
	});
	
	/*增加搜索入库单回车事件*/
	$("#productinNo").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 subcode();
		 } 
	});
	
	/*添加采购出库单*/
	$('#add').on('click', function(e) {
		e.preventDefault();
		cleanPurOSOrderForm();
		cleanAdd();
		loadOrgTree();
		$('#contextPage').hide();
		baseInfo({id:"baseDiv",title:"出库单增加",height:"700",width:"1068",savefn:function(types){
			var that =$(this);
			if(JY.Validate.form("baseForm") && $("#purOSOrderAdd input[name='id']").length!=0){
				var json_data="";
		 	    var last_index=$('#purOSOrderAdd tbody tr').length-1;
		 	    $('#purOSOrderAdd tbody tr').each(function(element,index){
		 	    var data='{"code":"'+$(this).find("input[name=code]").val()+'"}';	
		 	    if($(this).index()==last_index){
		        	json_data+=data;
		        }else{
		        	json_data+=data+',';
		        }
			    });
			    json_data='['+json_data+']';
			   $.ajax({type:"POST",url:jypath+"/scm/prostorage/out/add",data: "myData="+json_data+"&balancetype="+$("select[id='balancetype']").val()+"&remarks="+$("textarea[name='remarksPro']").val()+"&ratio="+$("input[name='ratio']").val()+"&outBoundNo="+$('#baseForm').find("input[name=outBoundNo]").val()+"&inOrgId="+$('#baseForm').find("#inOrgId").val()+"&outOrgId="+$('#baseForm').find("#outOrgId").val()+"" +"&id="+$('#baseForm').find("input[name=id]").val()+"&status="+types+"&orderNum="+$('#baseForm').find("input[name=orderNum]").val()+"&type="+$('#baseForm').find("select[name=type]").val(),dataType:"text",success:function(data,textStatus){  			        	 
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
		var balancetype=$("select[id='balancetype']").val();
		var ratio=$("input[name='ratio']").val();
		if(balancetype.length!=0 && ratio.length!=0){
			addInfo2({id:"purOSOrderDiv",title:"出库信息",height:"540",width:"380",savefn:function(e){
				var that =$(this);
				var html="";		    		
				if(JY.Validate.form("purOSOrderForm")){
					JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/queryCode',{code:$("#purOSOrderForm input[name$='code']").val(),balancetype:balancetype,ratio:ratio,type:0},function(data){
						commCode(data.obj);
						 if(e==0){
			    			 that.dialog("close");
			    		 }
			    		cleanPurOSOrderForm();
					});
				}
			}})	
		}else{
			JY.Model.info("选择结价类型、填写系数!");
		}
	});
	
	//查看
	$('#find').on('click', function(e) {
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
	$('#update').on('click', function(e) {
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
	$('#delete').on('click', function(e) {
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
				JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/delete',{cheks:chks.toString()},function(data){
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
			aduit(chks[0])
		}
	});
})


/*搜索条码*/
function subcode2(){
	var list=[];
	var code=$("input[id='enteryno']").val().trim();
	var balancetype=$("select[id='balancetype']").val();
	var ratio=$("input[name='ratio']").val();
	var type=0;
	if(code.length!=0){
		JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/queryCode',{code:code,balancetype:balancetype,ratio:ratio,type:type},function(data){
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
	}
}

/*修改出库单的结价类型和系数*/
function updateCode(){
	var chks =[];   
	var balancetype=$("select[id='balancetype']").val();
	var ratio=$("input[name='ratio']").val();
	if($('#purOSOrderAdd tbody tr').length!=0 && balancetype.length!=0 && ratio.length!=0){
		var type=0;
		var json_data="";
	    var last_index=$('#purOSOrderAdd tbody tr').length-1;
		$('#purOSOrderAdd tbody tr').each(function(element,index){
			var data='{"id":"'+$(this).find("input[name=id]").val()+'","code":"'+$(this).find("input[name=code]").val()+'"}';	
		    if($(this).index()==last_index){
		    	json_data+=data;
		    }else{
		    	json_data+=data+',';
		    }
	    });
		json_data='['+json_data+']';  
		JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/updateCode',{codes:json_data,balancetype:balancetype,ratio:ratio,type:type},function(data){
			$("#purOSOrderAdd tbody").html("");
			commCode(data.obj);
		});
		$("input[id='enteryno']").val("");
	}
}

/*搜索入库单号*/
function subcode(){
	var code=$("input[id='productinNo']").val().trim();
	var balancetype=$("select[id='balancetype']").val();
	var ratio=$("input[name='ratio']").val();
	var type=1;
	if(code.length!=0){
		JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/queryCode',{code:code,balancetype:balancetype,ratio:ratio,type:type},function(data){
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
				 html+="<td style='padding:1px;'><input class='center' type='text' name='code' readonly value='"+JY.Object.notEmpty(obj[i].code)+"' style='width:100%;height:30px;border:none;'/><input type='hidden' name='remarks' value='无'/><input type='hidden' name='id' value='"+JY.Object.notEmpty(obj[i].id)+"'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='name' readonly  value='"+JY.Object.notEmpty(obj[i].name)+"' style='width:100%;height:30px;border:none;' readonly /></td>";html+="<td style='padding:1px;'><input class='center' type='text' name='cerno' readonly  value='"+JY.Object.notEmpty(obj[i].cerno)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='proRemarks' readonly  value='"+JY.Object.notEmpty(obj[i].proRemarks)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='num' readonly  value='"+JY.Object.notNumber(obj[i].num)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='weight' readonly  value='"+parseFloat(JY.Object.notNumber(obj[i].weight)).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='wage' readonly  value='"+parseFloat(JY.Object.notNumber(obj[i].wage)).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='tageprice' readonly  value='"+parseFloat(JY.Object.notNumber(obj[i].tageprice)).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 if(dataPrice==1){
					 html+="<td style='padding:1px;'><input class='center' type='text' name='costing' readonly  value='"+parseFloat(JY.Object.notNumber(obj[i].costing)).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
					 html+="<td style='padding:1px;'><input class='center' type='text' name='pradeprice' readonly  value='"+parseFloat(JY.Object.notNumber(obj[i].pradeprice)).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 }
				 html+="<td style='padding:1px;'><input class='center' type='text' name='price' readonly  value='"+parseFloat(JY.Object.notNumber(obj[i].price)).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 html+="</tr>";	
			}else{
				$('#enteryno').tips({
			         msg: "条码已存在!",
			         bg: '#FF2D2D',
			         time: 1
			     });
				$("input[id='enteryno']").val("");
			}	
		}
	    $("#purOSOrderAdd tbody").append(html);
	    commFoot();
	    /*hide("purOSOrderAdd");*/
	}
}

function commFoot(){
	 $("#purOSOrderAdd tfoot").html("");
     var num=0,weight=0,costing=0,wage=0,tageprice=0,pradeprice=0,price=0;
	 $('#purOSOrderAdd').find('input[name="num"]').each(function(element,index){
		 if($(this).val()!=''){num+=parseFloat($(this).val());}
	 });
	 $('#purOSOrderAdd').find('input[name="weight"]').each(function(element,index){
		 if($(this).val()!=''){ weight+=parseFloat($(this).val());}
	 });
	 $('#purOSOrderAdd').find('input[name="costing"]').each(function(element,index){
		 if($(this).val()!=''){costing+=parseFloat($(this).val());}
	 });
	 $('#purOSOrderAdd').find('input[name="wage"]').each(function(element,index){
		 if($(this).val()!=''){wage+=parseFloat($(this).val());}
	 });
	 $('#purOSOrderAdd').find('input[name="tageprice"]').each(function(element,index){
		 if($(this).val()!=''){tageprice+=parseFloat($(this).val());}
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
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+num+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(weight).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(wage).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(tageprice).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 if(dataPrice==1){
		 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(costing).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
		 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(pradeprice).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 }
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(price).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
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
				//reomoveContext("purOSOrderAdd");
			}); 
		});		
	}	
}

function cleanAdd(){
	$.jy.dropTree.restStyle("inOrg");
	$.jy.dropTree.restStyle("outOrg");
	JY.Tags.cleanForm("baseForm");
	$("#purOSOrderAdd tbody").empty();
	$("#purOSOrderAdd tfoot").empty();
	$('#baseForm').find('.btnClass').show();
	$('#baseForm').find('input,select,textarea').attr('disabled',false);
	$("#baseForm span[id$='createUser']").text('');
	$("#baseForm span[id$='createTime']").text('');
	$("#baseForm span[id$='updateUser']").text('');
	$("#baseForm span[id$='updateTime']").text('');
	$("#baseForm span[id$='checkUser']").text('');
	$("#baseForm span[id$='checkTime']").text('');
	$("#baseForm #causesDiv").addClass("hide");
}



function search(){
	$("#searchBtn").trigger("click");
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

function baseInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存为草稿","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存并提交","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
}

/*function baseInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存为草稿","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存并提交","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");(attr.quxiao)();if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
}*/

function addInfo2(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;继续添加","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
}

/*出库列表*/
function getbaseList(init){
	if(init==1)$("#purOSOrderBaseForm .pageNum").val(1);	
	var state = $('#stateValue').val();
	JY.Model.loading();
	JY.Ajax.doRequest("purOSOrderBaseForm",jypath +'/scm/prostorage/out/findByPage?state='+state,null,function(data){
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
            		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notNumber(l.totalWage)).toFixed(4)+"</td>";
            		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notNumber(l.totalTageprice)).toFixed(4)+"</td>";
            		 if(dataPrice==1){
	            		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notNumber(l.totalCosing)).toFixed(4)+"</td>";
	            		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notNumber(l.totalPradeprice)).toFixed(4)+"</td>";
            		 }
            		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notNumber(l.totalPrice)).toFixed(4)+"</td>";
            		 if(l.type=="1"){
               			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>采购出库</span></td>";
               		 }else if(l.type=="2"){
               			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>销售出库</span></td>";
               		 }else if(l.type=="3"){
               			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>调拨出库</span></td>";
               		 }
            		 if(l.status=="0"){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>草稿</span></td>";
            		 }else if(l.status=="1"){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>待审核</span></td>";
            		 }else if(l.status=="2"){
            			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>已审核</span></td>";
            		 }else if(l.status=="3"){
            			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>已完成</span></td>";
            		 }else if(l.status=="9") {
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已删除</span></td>";
            		 }else if(l.status=="4") {
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
        		html+="<tr><td colspan='22' class='center'>没有相关数据</td></tr>";
        		$("#purOSOrderTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
    		 JY.Model.loadingClose();
	 });
}



/*出库明细*/
function view(id){
	cleanAdd();
	$('#baseForm').find('.btnClass').hide();
	$('#baseForm').find('input,select,textarea').attr('disabled','disabled');
	JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/find',{id:id},function(data){
		setBaseForm(data);
		if(data.obj.purOSOrder.status=="4"){
			$("#baseForm #causesDiv").removeClass("hide");
		}
		viewOut({id:"baseDiv",title:"出库查看",height:"700",width:"1024"})
	});
}

/*修改出库*/
function edit(item){
	cleanAdd();
	cleanPurOSOrderForm();
	loadOrgTree();
	JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/find',{id:item,type:1},function(data){
		setBaseForm(data);
		if(data.obj.purOSOrder.status=="4"){
			$("#baseForm #causesDiv").removeClass("hide");
		}
		baseInfo({id:"baseDiv",title:"出库修改",height:"720",width:"1068",savefn:function(types){
			var that =$(this);
			if(JY.Validate.form("baseForm") && $("#purOSOrderAdd input[name='id']").length!=0){
				var json_data="";
		 	    var last_index=$('#purOSOrderAdd tbody tr').length-1;
		 	    $('#purOSOrderAdd tbody tr').each(function(element,index){
		 	    var data='{"id":"'+$(this).find("input[name=id]").val()+'","code":"'+$(this).find("input[name=code]").val()+'"}';	
		 	    if($(this).index()==last_index){
		        	json_data+=data;
		        }else{
		        	json_data+=data+',';
		        }
			    });
			    json_data='['+json_data+']';
			    $.ajax({type:"POST",url:jypath+"/scm/prostorage/out/update",data: "myData="+json_data+"&remarks="+$("textarea[name='remarksPro']").val()+"&balancetype="+$("select[id='balancetype']").val()+"&ratio="+$("input[name='ratio']").val()+"&outBoundNo="+$('#baseForm').find("input[name=outBoundNo]").val()+"&inOrgId="+$('#baseForm').find("#inOrgId").val()+"&outOrgId="+$('#baseForm').find("#outOrgId").val()+"&id="+$('#baseForm').find("input[name=id]").val()+"&status="+types+"&orderNum="+$('#baseForm').find("input[name=orderNum]").val()+"&type="+$('#baseForm').find("select[name=type]").val(),dataType:"text",success:function(data,textStatus){  			        	 
				   var json_obj=eval('('+data+')');
				   if(json_obj.res==1){
					  JY.Model.info(json_obj.resMsg,function(){search();});
		        	  that.dialog("close");
		           }    	
		         }
		       });
			}
		}});
	});
}

/*function edit(outId){
	cleanAdd();
	cleanPurOSOrderForm();
	loadOrgTree();
	JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/find',{id:outId,type:1},function(data){
		setBaseForm(data);
		baseInfo({id:"baseDiv",title:"出库修改",height:"720",width:"1068",savefn:function(types){
			var that =$(this);
			if(JY.Validate.form("baseForm") && $("#purOSOrderAdd input[name='id']").length!=0){
				var json_data="";
		 	    var last_index=$('#purOSOrderAdd tbody tr').length-1;
		 	    $('#purOSOrderAdd tbody tr').each(function(element,index){
		 	    var data='{"id":"'+$(this).find("input[name=id]").val()+'","code":"'+$(this).find("input[name=code]").val()+'","balancetype":"'+$("select[name=balancetype]").val()+'","ratio":"'+$("input[name=ratio]").val()+'"}';	
		 	    if($(this).index()==last_index){
		        	json_data+=data;
		        }else{
		        	json_data+=data+',';
		        }
			    });
			    json_data='['+json_data+']';
			   $.ajax({type:"POST",url:jypath+"/scm/prostorage/out/update",data: "myData="+json_data+"&outBoundNo="+$('#baseForm').find("input[name=outBoundNo]").val()+"&inOrgId="+$('#baseForm').find("#inOrgId").val()+"&outOrgId="+$('#baseForm').find("#outOrgId").val()+"&id="+$('#baseForm').find("input[name=id]").val()+"&status="+types+"&orderNum="+$('#baseForm').find("input[name=orderNum]").val()+"&type="+$('#baseForm').find("select[name=type]").val(),dataType:"text",success:function(data,textStatus){  			        	 
				   var json_obj=eval('('+data+')');
				   if(json_obj.res==1){
		        		that.dialog("close");   
		        		JY.Model.info(json_obj.resMsg,function(){search();});
		        		$('#baseDiv').remove();
		        	}else{
		        		 JY.Model.error(json_obj.resMsg);
		        		 $('#baseDiv').remove();
		        	}     	
		         }
		       });
			}
		}});
	});
}*/




/*设置表单值*/
function setBaseForm(data){
	 var p=data.obj.purOSOrder;
	 $("#baseForm select[name$='balancetype']").val(JY.Object.notEmpty(p.balancetype));
	 $("#baseForm input[name$='ratio']").val(JY.Object.notEmpty(p.ratio));
	 
	 $("#baseForm input[name$='outBoundNo']").val(JY.Object.notEmpty(p.outBoundNo));
	 $("#baseForm input[name$='id']").val(JY.Object.notEmpty(p.id));
	 $("#inOrgId").val(JY.Object.notEmpty(p.inOrgId));
	 $("#outOrgId").val(JY.Object.notEmpty(p.outOrgId));
	 $("#inOrgName").val(JY.Object.notEmpty(p.inOrgName));
	 $("#outOrgName").val(JY.Object.notEmpty(p.outOrgName));
	 $("#outType").val(JY.Object.notEmpty(p.type));
	 $("textarea[name='remarksPro']").val(p.remarks)
	  $("textarea[name='description']").val(p.description)
	 //$("#baseForm textarea[name$='description']").val(p.description);
	 $("#baseForm input[name$='orderNum']").val(JY.Object.notEmpty(p.orderNum));
	 $("#baseForm span[id$='createUser']").text(JY.Object.notEmpty(p.createName));
	 if(JY.Object.notEmpty(p.createTime)){
		 $("#baseForm span[id$='createTime']").text(JY.Object.notEmpty(new Date(p.createTime).Format("yyyy/MM/dd hh:mm:ss")));
	 }	
	 $("#baseForm span[id$='updateUser']").text(JY.Object.notEmpty(p.updateName));
	 if(JY.Object.notEmpty(p.updateTime)){
		 $("#baseForm span[id$='updateTime']").text(JY.Object.notEmpty(new Date(p.updateTime).Format("yyyy/MM/dd hh:mm:ss")));
	 }
	 $("#baseForm span[id$='checkUser']").text(JY.Object.notEmpty(p.checkName));
	 if(JY.Object.notEmpty(p.checkTime)){
		 $("#baseForm span[id$='checkTime']").text(JY.Object.notEmpty(new Date(p.checkTime).Format("yyyy/MM/dd hh:mm:ss")));
	 }
	 commCode(data.obj.detail)
}

/*删除出库*/
function del(id){
	$("#jyConfirm").children().children().find(".causesDiv").remove();
	JY.Model.confirm("确认删除吗？",function(){
		JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/delete',{id:id},function(data){
				JY.Model.info(data.obj,function(){
					search();
				});
		});
	});
	
	
}


function aduit(id){
	cleanAdd();
	cleanPurOSOrderForm();
	$('#baseForm').find('.btnClass').hide();
	$('#baseForm').find('input,select').attr('disabled','disabled');
	JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/find',{id:id,type:2},function(data){
		setBaseForm(data);
		$("#baseForm select[id='balancetype']").attr('disabled',false);
		$("#baseForm input[name='ratio']").attr('disabled',false);
		checkInfo({id:"baseDiv",title:"出库单审核",height:"730",width:"1024",savefn:function(checkType,checkTitle){
			var balancetype=$("select[id='balancetype']").val();
			var ratio=$("input[name='ratio']").val();
			var model1 =$(this);
			$("#jyConfirm").children().children().find(".causesDiv").remove();
			if(checkType==2){
				if(balancetype.length==0){
					$('#balancetype').tips({
				         msg: "结价类型不能为空!",
				         bg: '#FF2D2D',
				         time: 1
				     });
				}else if(ratio.length==0){
					$('#ratio').tips({
				         msg: "系数不能为空!",
				         bg: '#FF2D2D',
				         time: 1
				     });
				}else{
					JY.Model.confirm(checkTitle,function(){
						JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/aduit',{id:id,status:checkType,remarks:$("textarea[name='remarksPro']").val(),balancetype:balancetype,ratio:ratio},function(data){
							model1.dialog("close");
							JY.Model.info(data.resMsg,function(){search();});
						});
					})
				}
			}else{
				var html="";
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
					JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/aduit',{id:id,status:checkType,description:causes,remarks:$("textarea[name='remarksPro']").val(),balancetype:balancetype,ratio:ratio},function(data){
						model1.dialog("close");
						model2.dialog("close"); 
						JY.Model.info(data.resMsg,function(){search();});
					});
				},function(){},flag)
			}
		}});
		/*AudingInfo({id:"baseDiv",title:"审核出库",height:"700",width:"1024",
			savefn:function(){
				var description=$("#baseForm textarea[name$='description']").val();
					JY.Model.confirm("确认审核吗？",function(){
						JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/aduit',{id:id,status:"2",description:description},function(data){
							JY.Model.info(data.resMsg,function(){
								$("#baseDiv").dialog("close");
								search();
						});
					});
				});
			},
			cancelfn:function(){
				var html="";
				$("#jyConfirm").children().children().find(".causesDiv").remove();
				$("#jyConfirm").children().children().append('<div class="causesDiv"><form id="causesForm"><br><div style="float:left;font-size:13px;"><span style="float:left">驳回原因</span><br><input type="text" jyValidate="required" id="causesTxt" placeholder="请输入原因" style="width: 265px;"></div></form></div>');
				var flag = true;
				JY.Model.confirm("您确认审核不通过吗?",function(){
					var model2 = $(this);
					var causes = $("#causesTxt").val();
					if(!JY.Validate.form("causesForm")){
						return false;
					}
					JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/aduit',{id:id,status:"4",description:causes},function(data){
						model2.dialog("close"); 
						$("#baseDiv").dialog("close");
						JY.Model.info(data.resMsg,function(){search();});
					});
				},function(){},flag)
				var description=$("#baseForm textarea[name$='description']").val();
				JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/aduit',{id:id,status:"4",description:description},function(data){
					JY.Model.info(data.resMsg,function(){
						$("#baseDiv").dialog("close");
						search();
					});
				});
			}
		});*/
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
		/*JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/getOrgId',null,function(data){
			$.jy.dropTree.checkNode("outOrg","outOrgName",data.obj,function(){
				$("#baseDiv #outOrgId").val(data.obj);
				$("#baseDiv #outOrgName").prop('disabled','disabled');
				$("#outOrg_clear").hide();
			});
		});*/
	});
}


function selectInOrgId(){
	var orderNum=$("#baseDiv #orderNum").val();
	$("#baseDiv #orderNum").val("");
	JY.Ajax.doRequest(null,jypath +'/scm/prostorage/out/selectInOrgId',{orderNum:orderNum},function(data){
		if(JY.Object.notEmpty(data.resMsg).length>0){
			$('#orderNum').tips({
		         msg: data.resMsg,
		         bg: '#FF2D2D',
		         time: 1
		     });
		}else{
			$("#inOrgId").val(JY.Object.notEmpty(data.obj.inOrgId));
			$("#inOrgName").val(JY.Object.notEmpty(data.obj.inOrgName));
			$("#baseDiv #orderNum").val(JY.Object.notEmpty(data.obj.orderNum));
		}
	});
}

function print(){
	 var id = $("#baseForm input[name='id']").val();
	 var reportNo = $("#baseForm input[name='outBoundNo']").val();
	 $("#printDiv").load(jypath +'/scm/purstorage/print?id='+id+'&type='+2,function(){
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