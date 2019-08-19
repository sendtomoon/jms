$(function(){
	/*增加回车事件*/
	$("#materialinBaseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	/*增加回车事件*/
	$("#enteryno").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 subcode();
		 } 
	});
	
	warehouseForm('selectWarehouse','selectWarehouseLocation');
	getbaseList(1);
	JY.Dict.setSelect("selectisValid","SCM_ORDER_STATUS",2,"全部");
	/*1：先填写公共的原料入库*/
	$('#add').on('click', function(e) {
		e.preventDefault();
		cleanAdd();
		$('#baseForm').find('.enterynoDiv').hide();
		baseInfo({id:"baseDiv",title:"新增入库单",height:"720",width:"1024",savefn:function(type){
			var that =$(this);
			if(JY.Validate.form("baseForm") && $('#materialinAdd tbody tr').length>0){
				var json_data="";
		 	    var last_index=$('#materialinAdd tbody tr').length-1;
		 	    $('#materialinAdd tbody tr').each(function(element,index){
		 	        var data='{"id":"'+$(this).find("input[name=id]").val()+'","enteryno":"'+$(this).find("input[name=enteryno]").val()+'","code":"'+$(this).find("input[name=code]").val()+'","num":"'+$(this).find("input[name=num]").val()+'","weight":"'+$(this).find("input[name=weight]").val()+'","feeType":"'+$(this).find("input[name=feeType]").val()+'","price":"'+$(this).find("input[name=price]").val()+'","purcost":"'+$(this).find("input[name=purcost]").val()+'","saleprice":"'+$(this).find("input[name=saleprice]").val()+'","finacost":"'+$(this).find("input[name=finacost]").val()+'","diffweight":"'+$(this).find("input[name=diffweight]").val()+'","remarks":"'+$(this).find("input[name=remarks]").val()+'"}';	
			 	    if($(this).index()==last_index){
			        	json_data+=data;
			        }else{
			        	json_data+=data+',';
			        };
			    });
		        json_data='['+json_data+']';
		        $.ajax({type:"POST",url:jypath+"/scm/materialin/add",data: "myData="+json_data+"&warehouseid="+$('#baseForm').find("select[name=warehouseId]").val()+"&locationid="+$('#baseForm').
				   find("select[name=locationId]").val()+"&remarks="+$('#baseForm').find("textarea[name=remarksTwo]").val()+"&id="+$('#baseForm').
				   find("input[name=id]").val()+"&status="+type+"&purno="+$('#baseForm').find("input[name=purno]").val(),dataType:"text",success:function(data,textStatus){ 
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
	/*2：不相同的原料信息*/
	$('#addBtn').on('click', function(e) {
		e.preventDefault();
		cleanMaterialinForm();
		addInfo({id:"materialinDiv",title:"原料入库信息",height:"600",width:"400",savefn:function(e){
			var that =$(this);
			var html="";		    		
			if(JY.Validate.form("materialinForm")){
				var code=$("#materialinForm input[name$='code']").val();
				JY.Ajax.doRequest(null,jypath +'/scm/materialin/findByCode',{code:code},function(data){
					 var remarks= $("#materialinForm textarea[name$='remarks']").val();
	        		 if(remarks ==""){remarks="无";}
	        		 html+="<tr>";
					 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+JY.Object.notEmpty(data.obj[0].code)+"' class='ace' /><span class='lbl'></span></label></td>";
					 html+="<td style='padding:1px;'><input class='center' type='text' name='name' readonly  value='"+JY.Object.notEmpty(data.obj[0].name)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
					 html+="<td style='padding:1px;'><input class='center' type='text' name='code' readonly value='"+JY.Object.notEmpty(data.obj[0].code)+"' style='width:100%;height:30px;border:none;'/><input type='hidden' name='remarks' value='无'/><input type='hidden' name='feeType' value='"+JY.Object.notEmpty(data.obj[0].feeType)+"'/><input type='hidden' name='id' value='"+JY.Object.notEmpty(data.obj[0].id)+"'/></td>";
					 if(data.obj[0].feeType=='0'){
						 html+="<td style='padding:1px;'><input class='center' type='text' name='feeType' readonly  value='克' style='width:100%;height:30px;border:none;' readonly /></td>";
					 }else{
						 html+="<td style='padding:1px;'><input class='center' type='text' name='feeType' readonly  value='件' style='width:100%;height:30px;border:none;' readonly /></td>";
					 }
					 html+="<td style='padding:1px;'><input class='center' type='text' name='num' onblur='commFoot()' onkeyup='JY.Validate.limitNum(this)'  value='"+$("#materialinForm input[name$='num']").val()+"'  style='width:100%;height:30px;border:none;'/></td>";
					 html+="<td style='padding:1px;'><input class='center' type='text' name='weight' onblur='commFoot()' onkeyup='JY.Validate.limitDouble(this)' value='"+$("#materialinForm input[name$='weight']").val()+"' style='width:100%;height:30px;border:none;'/></td>";
					 html+="<td style='padding:1px;'><input class='center' type='text' name='diffweight' onblur='commFoot()' onkeyup='JY.Validate.limitNum(this)'  value='"+$("#materialinForm input[name$='diffweight']").val()+"' style='width:100%;height:30px;border:none;'/></td>";
					 html+="<td style='padding:1px;'><input class='center' type='text' name='price' onblur='commFoot()' onkeyup='JY.Validate.limitDouble(this)' value='"+$("#materialinForm input[name$='price']").val()+"'  style='width:100%;height:30px;border:none;'/></td>";
					 html+="<td style='padding:1px;'><input class='center' type='text' name='purcost' onblur='commFoot()' onkeyup='JY.Validate.limitNum(this)'  value='"+$("#materialinForm input[name$='purcost']").val()+"' style='width:100%;height:30px;border:none;'/></td>";
					 html+="<td style='padding:1px;'><input class='center' type='text' name='saleprice' onblur='commFoot()' onkeyup='JY.Validate.limitDouble(this)' value='"+$("#materialinForm input[name$='saleprice']").val()+"' style='width:100%;height:30px;border:none;'/></td>";
					/* html+="<td style='padding:1px;'><input class='center' type='text' name='finacost' onblur='commFoot()' onkeyup='JY.Validate.limitNum(this)'  value='"+$("#materialinForm input[name$='finacost']").val()+"' style='width:100%;height:30px;border:none;'/></td>";*/
					 html+="</tr>";
		    		 $("#materialinAdd tbody").append(html);
		    		 if(e==0){
		    			 that.dialog("close");
		    		 }
		    		 commFoot();
		    		 cleanMaterialinForm();
				});
			}
		}})	
	});
	
	var moudtlMap = {};
	$("#findCode").typeahead({
        source: function (query, process) {
        	$.ajax({
				type:'POST',
				url:jypath+'/scm/materialin/findSetCode',
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
        		url:jypath+'/scm/materialin/findByCode',
        		data:{code:value},
        		dataType:'json',
        		success:function(data,textStatus){
        			JY.Tools.populateForm("materialinForm",data.obj[0]);
        			if(data.obj.feeType=='1'){
        				 $("#materialinForm input[name='feeType']").val("克");
        			}else{
        				 $("#materialinForm input[name='feeType']").val("件");
        			}
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
	
	//查看
	$('#view').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#materialinTable input[name="ids"]:checked').each(function(){    
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
		$('#materialinTable input[name="ids"]:checked').each(function(){    
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
	
	//删除
	$('#del').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#materialinTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val()); 
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{	
			$("#jyConfirm").children().children().find(".causesDiv").remove();
			JY.Model.confirm("确认删除吗？",function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/materialin/del',{cheks:chks.toString()},function(data){
					JY.Model.info(data.resMsg,function(){
						search();
					});
				});
			});
		}
	});
	
	//审核
	$('#check').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#materialinTable input[name="ids"]:checked').each(function(){    
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
	
	//打印
	/*$('#print').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#materialinTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val()); 
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{	
			 var id = chks[0].toString();
			 var reportNo = $("#baseForm input[name='enteryno']").val();
			 $("#printDiv").load(jypath +'/scm/materialin/print?id='+id+'&type='+2,function(){
				 if(LODOP){
					 LODOP=getLodop();  
					 LODOP.PRINT_INIT("打印控件功能演示");
					 LODOP.ADD_PRINT_BARCODE(16,78,166,21,"128B",reportNo);
					 LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
					 LODOP.ADD_PRINT_HTM(0,0,"100%","100%",document.getElementById("testPrintdiv").innerHTML);
					 LODOP.PREVIEW();
				 }
			 })
			print(chks[0])
		}
	});*/
})

/*直接扫条码添加*/
/*function subcode(){
	var code=$("input[id='enteryno']").val();
	if(code.length != 0 && !code.match(/^\s+$/g)){
		JY.Ajax.doRequest(null,jypath +'/scm/materialin/findByCode',{code:code},function(data){
			commCode(data.obj)
		});
		$("input[id='enteryno']").val("");
	}
}*/


/*直接扫条码添加*/
function subcode(){
	var code=$("input[id='enteryno']").val();
	var purno=$("input[id='purno']").val();
	if(code.length != 0 && !code.match(/^\s+$/g) && purno.length != 0){
		JY.Ajax.doRequest(null,jypath +'/scm/materialin/findByCode',{code:code,purno:purno},function(data){
			if(JY.Object.notEmpty(data.resMsg).length>0){
				$('#enteryno').tips({
			         msg: data.resMsg,
			         bg: '#FF2D2D',
			         time: 1
			     });
			}else{
				 commAddCode(data.obj);
				 $('#baseForm').find("input[name='purno']").attr('disabled','disabled');
			}
		});
		$("input[id='enteryno']").val("");
	}else{
		$('#enteryno').tips({
            msg: "请填写正确的批次号和条码!",
            bg: '#FF2D2D',
            time: 1
        });
	}
}

function commAddCode(obj){
	var list=[];
	var html="";
	if(obj!=null){
		for(var i=0;i<obj.length;i++){ 
			$("#materialinAdd input[name$='code']").each(function(){  
				list.push($(this).val());
			});
			if(list.indexOf(obj[i].code)==-1){
				 html+="<tr>";
				 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+JY.Object.notEmpty(obj[i].code)+"' class='ace' /><span class='lbl'></span></label></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='code' readonly value='"+JY.Object.notEmpty(obj[i].code)+"' style='width:100%;height:30px;border:none;'/><input type='hidden' name='remarks' value='无'/><input type='hidden' name='feeType' value='"+JY.Object.notEmpty(obj[i].feeType)+"'/><input type='hidden' name='id' value='"+JY.Object.notEmpty(obj[i].id)+"'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='name' readonly  value='"+JY.Object.notEmpty(obj[i].name)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 if(obj[i].feeType=='1'){
					 html+="<td style='padding:1px;'><input class='center' type='text' name='feeType' readonly  value='件' style='width:100%;height:30px;border:none;' readonly /></td>";
				 }else{
					 html+="<td style='padding:1px;'><input class='center' type='text' name='feeType' readonly  value='克' style='width:100%;height:30px;border:none;' readonly /></td>";
				 }
				 html+="<td style='padding:1px;'><input class='center' type='text' jyvalidate='required' name='num' onblur='commFoot()' onkeyup='JY.Validate.limitNum(this)'  value='"+JY.Object.notEmpty(obj[i].num)+"'  style='width:100%;height:30px;border:none;'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' jyvalidate='required' name='weight' onblur='commFoot()' onkeyup='JY.Validate.limitDouble(this)' value='"+JY.Object.notEmpty(obj[i].weight)+"' style='width:100%;height:30px;border:none;'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' jyvalidate='required' name='diffweight' onblur='commFoot()' onkeyup='JY.Validate.limitNum(this)'  value='"+JY.Object.notEmpty(obj[i].diffweight)+"' style='width:100%;height:30px;border:none;'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' jyvalidate='required' name='price' onblur='commFoot()' onkeyup='JY.Validate.limitDouble(this)' value='"+JY.Object.notEmpty(obj[i].price)+"'  style='width:100%;height:30px;border:none;'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' jyvalidate='required' name='purcost' onblur='commFoot()' onkeyup='JY.Validate.limitNum(this)'  value='"+JY.Object.notEmpty(obj[i].purcost)+"' style='width:100%;height:30px;border:none;'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' jyvalidate='required' name='saleprice' onblur='commFoot()' onkeyup='JY.Validate.limitDouble(this)' value='"+JY.Object.notEmpty(obj[i].saleprice)+"' style='width:100%;height:30px;border:none;'/></td>";
				/* html+="<td style='padding:1px;'><input class='center' type='text' name='finacost' onblur='commFoot()' onkeyup='JY.Validate.limitNum(this)'  value='"+JY.Object.notNumber(obj[i].finacost)+"' style='width:100%;height:30px;border:none;'/></td>";*/
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
	    $("#materialinAdd tbody").append(html);
	    commFoot();
	}
}


/*搜索条码或入库单号公共数据*/
function commCode(obj){
	var list=[];
	var html="";
	if(obj!=null){
		for(var i=0;i<obj.length;i++){ 
			$("#materialinAdd input[name$='code']").each(function(){  
				list.push($(this).val());
			});
			if(list.indexOf(obj[i].code)==-1){
				 html+="<tr>";
				 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+JY.Object.notEmpty(obj[i].code)+"' class='ace' /><span class='lbl'></span></label></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='code' readonly value='"+JY.Object.notEmpty(obj[i].code)+"' style='width:100%;height:30px;border:none;'/><input type='hidden' name='remarks' value='无'/><input type='hidden' name='feeType' value='"+JY.Object.notEmpty(obj[i].feeType)+"'/><input type='hidden' name='id' value='"+JY.Object.notEmpty(obj[i].id)+"'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='name' readonly  value='"+JY.Object.notEmpty(obj[i].name)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 if(obj[i].feeType=='1'){
					 html+="<td style='padding:1px;'><input class='center' type='text' name='feeType' readonly  value='件' style='width:100%;height:30px;border:none;' readonly /></td>";
				 }else{
					 html+="<td style='padding:1px;'><input class='center' type='text' name='feeType' readonly  value='克' style='width:100%;height:30px;border:none;' readonly /></td>";
				 }
				 html+="<td style='padding:1px;'><input class='center' type='text' jyvalidate='required' name='num' onblur='commFoot()' onkeyup='JY.Validate.limitNum(this)'  value='"+JY.Object.notNumber(obj[i].num)+"'  style='width:100%;height:30px;border:none;'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' jyvalidate='required' name='weight' onblur='commFoot()' onkeyup='JY.Validate.limitDouble(this)' value='"+obj[i].weight+"' style='width:100%;height:30px;border:none;'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' jyvalidate='required' name='diffweight' onblur='commFoot()' onkeyup='JY.Validate.limitNum(this)'  value='"+parseFloat(JY.Object.notNumber(obj[i].diffweight)).toFixed(4)+"' style='width:100%;height:30px;border:none;'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' jyvalidate='required' name='price' onblur='commFoot()' onkeyup='JY.Validate.limitDouble(this)' value='"+parseFloat(JY.Object.notNumber(obj[i].price)).toFixed(4)+"'  style='width:100%;height:30px;border:none;'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' jyvalidate='required' name='purcost' onblur='commFoot()' onkeyup='JY.Validate.limitNum(this)'  value='"+parseFloat(JY.Object.notNumber(obj[i].purcost)).toFixed(4)+"' style='width:100%;height:30px;border:none;'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' jyvalidate='required' name='saleprice' onblur='commFoot()' onkeyup='JY.Validate.limitDouble(this)' value='"+parseFloat(JY.Object.notNumber(obj[i].saleprice)).toFixed(4)+"' style='width:100%;height:30px;border:none;'/></td>";
				/* html+="<td style='padding:1px;'><input class='center' type='text' name='finacost' onblur='commFoot()' onkeyup='JY.Validate.limitNum(this)'  value='"+JY.Object.notNumber(obj[i].finacost)+"' style='width:100%;height:30px;border:none;'/></td>";*/
				 html+="</tr>";
			}else{
				$("input[id='enteryno']").val("");
				JY.Model.info("条码已存在!");
			}	
		}
	    $("#materialinAdd tbody").append(html);
	    commFoot();
	}
}

/*底部合计*/
function commFoot(){
	 $("#materialinAdd tfoot").html("");
	 var price=0,purcost=0,finacost=0,diffweight=0,count=0,num=0,weight=0,saleprice=0;
	 $('#materialinAdd').find('input[name="num"]').each(function(element,index){
		 if($(this).val()!=''){num+=parseFloat($(this).val());}
		count++;
	 });
	 $('#materialinAdd').find('input[name="weight"]').each(function(element,index){
		 if($(this).val()!=''){weight+=parseFloat($(this).val());}
		count++;
	 });
	 $('#materialinAdd').find('input[name="price"]').each(function(element,index){
		 if($(this).val()!=''){price+=parseFloat($(this).val());}
		count++;
	 });
	 $('#materialinAdd').find('input[name="purcost"]').each(function(element,index){
		 if($(this).val()!=''){ purcost+=parseFloat($(this).val());}
	 });
	 $('#materialinAdd').find('input[name="finacost"]').each(function(element,index){
		 if($(this).val()!=''){finacost+=parseFloat($(this).val());}
	 });
	 $('#materialinAdd').find('input[name="diffweight"]').each(function(element,index){
		 if($(this).val()!=''){diffweight+=parseFloat($(this).val());}
	 });
	 $('#materialinAdd').find('input[name="saleprice"]').each(function(element,index){
		 if($(this).val()!=''){saleprice+=parseFloat($(this).val());}
	 });
 	 var input_array=new Array();
 	 $('#materialinAdd').find('input[name="code"]').each(function(element,index){
 	  	 if($.inArray($(this).val(),input_array)=="-1"){
 	  	  	input_array.push($(this).val());
 	  	  }
 	  })
     var foot="";
	 foot+="<tr>";
	 foot+="<td class='center'>合计</td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+num+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(weight).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(diffweight).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(price).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(purcost).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(saleprice).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 /*foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(finacost).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";*/
	 foot+="</tr>";	
	 $("#materialinAdd tfoot").append(foot);
}

/*仓库的列表和添默认值 */
function warehouseForm(selectWarehouse,selectWarehouseLocation){
	var selectWarehouse = $('#'+selectWarehouse+'');
	JY.Ajax.doRequest(null,jypath +'/scm/warehousing/warehousAll',null,function(data){
	    var obj=data.obj;
	    selectWarehouse.html("");
	    $('#'+selectWarehouseLocation+'').html("");
	    selectWarehouse.append('<option value="">请选择</option>');
		for (var i = 0; i < obj.length; i++) {
			selectWarehouse.append('<option value="'+obj[i].key+'">'+obj[i].value+'</option>');
        }
	});
}
/*仓位列表*/
function chgWarehouseLocation(obj,selectWarehouseLocation,locationId){
	var va = obj;
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

/*刷新列表页*/
function search(){
	$("#searchBtn").trigger("click");
}

/*原料入库列表*/
function getbaseList(init){
	if(init==1)$("#materialinBaseForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("materialinBaseForm",jypath +'/scm/materialin/findByPage',null,function(data){
		 $("#materialinTable tbody").empty();
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
            		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace'/> <span class='lbl'></span></label></td>";
            		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
            		 html+="<td class='center hidden-480'><a style='cursor:pointer;' onclick='view(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.enteryno)+"</a></td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.orgName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.totalnum)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.totalcount)+"</td>";
            		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notEmpty(l.totalWeight)).toFixed(4)+"</td>";
            		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notEmpty(l.totalprice)).toFixed(4)+"</td>";
            		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notEmpty(l.purcost)).toFixed(4)+"</td>";
            		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notEmpty(l.saleprice)).toFixed(4)+"</td>";
            		 if(l.status==1) {html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>待审核</span></td>";}
            		 else if(l.status==3)  { html+="<td class='center hidden-480'><span class='label label-sm  label-success'>已完成</span></td>";}   
            		 else if(l.status==0)  { html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>草稿</span></td>";}
            		 else if(l.status==2)  { html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已审核</span></td>";}
            		 else if(l.status==4)  { html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已拒绝</span></td>";}
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.createTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
            		 html+="</tr>";		 
            	 } 
        		 $("#materialinTable tbody").append(html);
        		 JY.Page.setPage("materialinBaseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='13' class='center'>没有相关数据</td></tr>";
        		$("#materialinTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
    		 JY.Model.loadingClose();
	 });
}


function viewInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,
		buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;打印预览","class":"btn btn-primary btn-xs",click:function(){print()}},
		         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
}

function addInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
    	buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;继续添加","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},
    	         {html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
    	         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}

function baseInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
    	buttons:[{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存为草稿","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
    	         {html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存并提交","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},
    	         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}

function checkInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
		buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,3,"您确认审核通过吗");}}},
		        {html: "<i class='icon-remove bigger-110'></i>&nbsp;不通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,4,"您确认审核不通过吗");}}},
		        {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
	});
}

/*清空表单弹出列表数据数据*/
function cleanAdd(){
	 $("#baseForm #causesDiv").addClass("hide");
	 $('#baseForm').find('.btnClass').show();
	 $('#baseForm').find('.enterynoDiv').show();
	 $('#baseForm').find('.caozuo').show();
	 $('#baseForm').find('input,select,textarea').removeAttr('disabled');
	 JY.Tags.cleanForm("baseForm");
	 $("#baseForm input[id='totalNum']").val(0);
	 $("#baseForm input[id='totalCount']").val(0);
	 $("#baseForm input[id='totalprices']").val(0);
	 $("#baseForm input[id='finacosts']").val(0);
	 $("#baseForm input[id='purcosts']").val(0);
	 $("#baseForm input[id='diffweights']").val(0);
	 $("#baseForm input[id='checkcosts']").val(0);
	 $("#materialinAdd tbody").html("");
	 $("#materialinAdd tfoot").html("");
	 $("#baseForm span[id$='createUser']").text("");
	 $("#baseForm span[id$='createTime']").text("");
	 $("#baseForm span[id$='updateUser']").text("");
	 $("#baseForm span[id$='updateTime']").text("");
	 $("#baseForm span[id$='checkUser']").text("");
	 $("#baseForm span[id$='checkTime']").text("");
}

/*清空入库单明细表单数据*/
function cleanMaterialinForm(){
	$("#materialinForm input[name='num']").val("");
	JY.Tags.cleanForm("materialinForm");
}

/*设置修改表单值*/
function setUpdateForm(data){
	commForm(data);
	commCode(data.obj.purenterydetails);
}

function commForm(data){
	 $("#baseForm input[id='totalNum']").val(data.obj.purenteries.totalnum);
	 $("#baseForm input[id='totalCount']").val(data.obj.purenteries.totalcount);
	 $("#baseForm input[id='totalprices']").val(data.obj.purenteries.totalprice);
	 $("#baseForm input[id='finacosts']").val(data.obj.purenteries.finacost);
	 $("#baseForm input[id='purcosts']").val(data.obj.purenteries.purcost);
	 $("#baseForm input[id='diffweights']").val(data.obj.purenteries.diffweight);
	 $("#baseForm input[id='checkcosts']").val(data.obj.purenteries.checkcost);
	 $("#baseForm span[id$='createUser']").text(JY.Object.notEmpty(data.obj.purenteries.createName));
	 if(JY.Object.notEmpty(data.obj.purenteries.createTime)){
		 $("#baseForm span[id$='createTime']").text(JY.Object.notEmpty(new Date(data.obj.purenteries.createTime).Format("yyyy/MM/dd hh:mm:ss")));
	 }
	 $("#baseForm span[id$='updateUser']").text(JY.Object.notEmpty(data.obj.purenteries.updateName));
	 if(JY.Object.notEmpty(data.obj.purenteries.updateTime)){
		 $("#baseForm span[id$='updateTime']").text(JY.Object.notEmpty(new Date(data.obj.purenteries.updateTime).Format("yyyy/MM/dd hh:mm:ss")));
	 }
	 $("#baseForm span[id$='checkUser']").text(JY.Object.notEmpty(data.obj.purenteries.checkName));
	 if(JY.Object.notEmpty(data.obj.purenteries.checkTime)){
		 $("#baseForm span[id$='checkTime']").text(JY.Object.notEmpty(new Date(data.obj.purenteries.checkTime).Format("yyyy/MM/dd hh:mm:ss")));
	 }
	 $("#baseForm select[id$='selectWarehouse']").val(data.obj.purenteries.warehouseid);
	 chgWarehouseLocation(data.obj.purenteries.warehouseid,'selectWarehouseLocation',data.obj.purenteries.locationid);
	 $("#baseForm input[name$='purno']").val(JY.Object.notEmpty(data.obj.purenteries.purno));
	 $("#baseForm input[name$='enteryno']").val(data.obj.purenteries.enteryno);
	 $("#baseForm input[name$='id']").val(data.obj.purenteries.id);
	 $("#baseForm textarea[name$='remarksTwo']").val(data.obj.purenteries.remarks);
	 $("#baseForm textarea[name$='description']").val(JY.Object.notEmpty(data.obj.purenteries.description));
}

/*设置表单值*/
function setForm(data){
	 commForm(data)
	 commCode(data.obj.purenterydetails);
	 $('#baseForm').find('input,select,textarea').attr('disabled','disabled');
}

/*原料入库明细*/
function view(id){
	cleanAdd();
	$('#baseForm').find('.caozuo').hide();
	$('#baseForm').find('.btnClass').hide();
	JY.Ajax.doRequest(null,jypath +'/scm/materialin/view',{id:id},function(data){
		setForm(data);
		if(data.obj.purenteries.status=="4"){
			$("#baseForm #causesDiv").removeClass("hide");
		}
		viewInfo({id:"baseDiv",title:"查看入库单",height:"700",width:"1024"})
	});
}

/*修改原料入库*/
function edit(id){
	cleanAdd();
	$("#baseForm input[name$='purno']").attr('disabled','disabled');
	JY.Ajax.doRequest(null,jypath +'/scm/materialin/view',{id:id,type:1},function(data){
		setUpdateForm(data);
		if(data.obj.purenteries.status=="4"){
			$("#baseForm #causesDiv").removeClass("hide");
		}
		baseInfo({id:"baseDiv",title:"修改入库单",height:"720",width:"1024",savefn:function(type){
			var that =$(this);
			if(JY.Validate.form("baseForm") && $('#materialinAdd tbody tr').length>0){
				var json_data="";
				var trueFalse=1;
		 	    var last_index=$('#materialinAdd tbody tr').length-1;
		 	    $('#materialinAdd tbody tr').each(function(element,index){
				 	   var data='{"id":"'+$(this).find("input[name=id]").val()+'","enteryno":"'+$(this).find("input[name=enteryno]").val()+'","feeType":"'+$(this).find("input[name=feeType]").val()+'","type":"'+$(this).find("input[name=type]").val()+'","code":"'+$(this).find("input[name=code]").val()+'","num":"'+$(this).find("input[name=num]").val()+'","weight":"'+$(this).find("input[name=weight]").val()+'","price":"'+$(this).find("input[name=price]").val()+'","purcost":"'+$(this).find("input[name=purcost]").val()+'","saleprice":"'+$(this).find("input[name=saleprice]").val()+'","finacost":"'+$(this).find("input[name=finacost]").val()+'","diffweight":"'+$(this).find("input[name=diffweight]").val()+'","remarks":"'+$(this).find("input[name=remarks]").val()+'"}';	
				 	   if($(this).index()==last_index){
				 		   json_data+=data;
				       }else{
				    	   json_data+=data+',';
				       }
			    });
			    json_data='['+json_data+']';
			    $.ajax({type:"POST",url:jypath+"/scm/materialin/edit",data: "myData="+json_data+"&warehouseid="+$('#baseForm').find("select[name=warehouseId]").val()+"&locationid="+$('#baseForm').find("select[name=locationId]").val()+"&remarks="+$('#baseForm').find("textarea[name=remarksTwo]").val()+"&id="+$('#baseForm').find("input[name=id]").val()+"&status="+type,dataType:"text",success:function(data,textStatus){  			        	 
				   var json_obj=eval('('+data+')');
				   if(json_obj.res==1){
		        		that.dialog("close");      
		        		JY.Model.info(json_obj.resMsg,function(){search();});
		           }else{
		        		JY.Model.error(json_obj.resMsg);
		           }     	
		       }});
			}
		}});
	});
}


/*审核原料入库*/
function check(id){
	cleanAdd();
	$('#baseForm').find('.caozuo').hide();
	$('#baseForm').find('.btnClass').hide();
	JY.Ajax.doRequest(null,jypath +'/scm/materialin/view',{id:id,type:2},function(data){
		setForm(data);
		checkInfo({id:"baseDiv",title:"审核入库单",height:"700",width:"1024",savefn:function(checkType,checkTitle){
			/*var that =$(this);
			JY.Model.confirm(checkTitle,function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/materialin/check',{id:id,status:checkType},function(data){
					JY.Model.info(data.resMsg,function(){search();});
					that.dialog("close");
				});
			});*/
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
				JY.Ajax.doRequest(null,jypath +'/scm/materialin/check',{id:id,status:checkType,description:causes},function(data){
					model1.dialog("close");
					model2.dialog("close"); 
					JY.Model.info(data.resMsg,function(){search();});
				});
			},function(){},flag)
		}});
	});
}

/*删除原料入库*/
/*function del(id){
	JY.Model.confirm("确认删除吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/materialin/del',{id:id},function(data){
			JY.Model.info(data.resMsg,function(){
				search();
			});
		});
	});
}*/

/*删除*/
function delMaterialin(){
	var chks =[];    
	$('#materialinAdd input[name="ids"]:checked').each(function(){ 
		chks.push($(this).val());    
	});     
	if(chks.length==0) {
		JY.Model.info("您没有选择任何内容!"); 
	}else{
		$("#jyConfirm").children().children().find(".causesDiv").remove();
		JY.Model.confirm("确认要删除选中的数据吗?",function(){	
			$('#materialinAdd input[name="ids"]:checked').each(function(){  
				$(this).parent().parent().parent().remove();
			}); 
			addNum();
		});		
	}	
}

function print(){
	 var id = $("#baseForm input[name='id']").val();
	 var reportNo = $("#baseForm input[name='enteryno']").val();
	 $("#printDiv").load(jypath +'/scm/materialin/print?id='+id+'&type='+2,function(){
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