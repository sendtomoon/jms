var chkss=[];
function splitAll(num){
	trNum=num;
	var stockNum=$("#item"+num).attr("stockNum")
	var numbers=$("#item"+num).attr("numbers")
	var mateNum=$("#item"+num).attr("mateNum");
	var okNum=$("#item"+num).attr("okNum");
	
	if(parseInt(stockNum)==0&&parseInt(mateNum)==0){
		JY.Model.info("没有足够的库存");
		return;
	}
	if(parseInt(numbers)==parseInt(okNum)+parseInt(mateNum)&&parseInt(mateNum)>0){
		mateOnlyClick(num);
	}else if(parseInt(numbers)==parseInt(okNum)+parseInt(mateNum)&&parseInt(mateNum)<1){
		JY.Model.info("商品已经匹配完成，并且出库");
	}else{
		mateOnlyClick(num);
	}
	
}
function mateOnlyClick(num){
	chkss=[];
	cleanItemForm();
	var mdCode=$("#item"+num).attr("mdCode")
	var ids=$("#item"+num).attr("ids")
	JY.Ajax.doRequest("moForm",jypath +'/scm/moudle/find',{id:mdCode},function(data){
		var l=data.obj;
		if(JY.Object.notEmpty(l.type)=="0"){
			$("#mouCodeName1").val(getSelectValue(mdCode,mdCodes));
			$("#materialForm input[name$='moudleCode']").val(mdCode);
			editInfo ("materialDiv","原料选择",function(){
				var that =$(this);
				var chks =[];
				var splits=[];
				var list=$("#materialTable1 input[name='items']").map(function(){return $(this).val();}).get().join(",");
//				var numbers=$("#item"+num).attr("lackNum");
//				var chkNum=$("#item"+num).attr("mateNum");
//				var weight=$("#item"+num).attr("weight");
//				var sun=parseInt(numbers)+parseInt(chkNum);
				if(JY.Object.notEmpty(list)!=''){
					chks=list.split(",");
					$.each(chks,function(i,e){
						var numbers = $("#numbers"+e).val();
						var weight = $("#weight"+e).val();
						var productId = $("#item"+e).attr("productId");
						var param={productId:productId,numbers:numbers,weight:weight};
						splits.push(param);
					});
				}
				if(JY.Validate.form("itemDiv")){	
//				$('#splitTable3 input[name="ids"]:checked').each(function(){  
//					chks.push($(this).val());    
//				 });
//					if(chks.length>sun){
//						JY.Model.error("选择的商品数量大于缺货数量");
//						return;
//					}
					var ids=$("#item"+num).attr("ids");
					var orderId = $("#splitForm input[name$='id']").val();
					var mateNum = $("#item"+num).attr("mateNum");
					var params ={id:ids,orderId:orderId,splits:splits};	
					if(JY.Object.notEmpty(list)!='' || mateNum>0){
						$.ajax({type:'POST',url:jypath+'/scm/purorder/detail/updateMaterialSplit',data:JSON.stringify(params),dataType:'json',contentType:"application/json",success:function(data,textStatus){  			        	 
							if(data.res==2){
								JY.Model.info(data.resMsg);
								getMaterial();
								getMaterial2(ids);
							}else{
								JY.Model.info(data.resMsg);     
								splitList();
								that.dialog("close");
							}
							
						}
						});
						
					}
				}
			});
			getMaterial();
			getMaterial2(ids);
		}else{
			getselect(gMaterials,"gMaterialSelect");
			getselect(gWeights,"gWeightSelect");
			getselect(dWeights,"dWeightSelect");
			getselect(dColors,"dColorSelect");
			getselect(dClaritys,"dClaritySelect");
			getselect(cuts,"dCutSelect");
			var chk=$("#item"+num).attr("list");
			chkss.push(chk.split(","));
			chkss=chkss[0];
			$("#gMaterialSelect").val($("#item"+num).attr("gMaterial"));
			$("#gWeightSelect").val($("#item"+num).attr("gWeight"));
			$("#dWeightSelect").val($("#item"+num).attr("dWeight"));
			$("#dColorSelect").val($("#item"+num).attr("dColor"));
			$("#dClaritySelect").val($("#item"+num).attr("dClarity"));
			$("#dCutSelect").val($("#item"+num).attr("cut"));
			$("#dWeightSelect").val($("#item"+num).attr("dWeight"));
			$("#splitItemForm input[name$='circel']").val($("#item"+num).attr("circel"));
			$("#splitItemForm input[name$='mouCode']").val($("#item"+num).attr("mdCode"));
			$("#splitItemForm input[name$='id']").val(chk);
			$("#mouCodeName").val(getSelectValue(mdCode,mdCodes));
			editInfo ("itemDiv","商品选择",function(){
				var that =$(this);
				var chks =[];
				var list=$("#splitTable3 input[name='items']").map(function(){return $(this).val();}).get().join(",");
				var numbers=$("#item"+num).attr("lackNum");
				var chkNum=$("#item"+num).attr("mateNum");
				var sun=parseInt(numbers)+parseInt(chkNum);
				if(JY.Object.notEmpty(list)!=''){
					chks=list.split(",");
				}
				if(JY.Validate.form("itemDiv")){	
//				$('#splitTable3 input[name="ids"]:checked').each(function(){  
//					chks.push($(this).val());    
//				 });
					if(chks.length>sun){
						JY.Model.error("选择的商品数量大于缺货数量");
						return;
					}
					var ids=$("#item"+num).attr("ids");
					var orderId = $("#splitForm input[name$='id']").val();
					var mateNum = $("#item"+num).attr("mateNum");
					var params ={id:ids,orderId:orderId,list:chks};	
					if(JY.Object.notEmpty(list)!='' || mateNum>0){
						$.ajax({type:'POST',url:jypath+'/scm/purorder/detail/updateSplit',data:JSON.stringify(params),dataType:'json',contentType:"application/json",success:function(data,textStatus){  			        	 
							if(data.res==2){
								JY.Model.info(data.resMsg);     
								getItem();
								getItem1();
							}else{
								JY.Model.info(data.resMsg);     
								splitList();
								that.dialog("close");
							}
						}
						});
						
					}
				}
			});
			getItem();
			getItem1();
		}
	})
}
function getMaterial2(ids){
	JY.Ajax.doRequest(null,jypath +'/scm/purorder/detail/findSplit',{orderDetailId:ids},function(data){
		 $("#materialTable1 tbody").empty();
		var obj=data.obj;
		var results=obj.list;
//		var results=list.results;
		var html="";
		for(var i=0;i<results.length;i++){
			var l=results[i];
			html+="<tr id='tr"+l.id+"'>";
			html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
			html+="<td>"+JY.Object.notEmpty(l.productCode)+"</td>";
			html+="<td style='padding:0px;'>"+"<input type='text' readonly  id='numbers"+l.id+"' name='numbers' jyValidate='required' style='width:100%;height:30px;'value='"+JY.Object.notEmpty(l.numbers)+"'>"+"</td>";
			html+="<td style='padding:0px;'>"+"<input type='text' readonly  id='weight"+l.id+"' name='weight' jyValidate='required' style='width:100%;height:30px;' value='"+JY.Object.notEmpty(l.weight)+"'>"+"</td>";
//			html+="<td>"+JY.Object.notEmpty(l.numbers)+"</td>";
//			html+="<td>"+JY.Object.notEmpty(l.weight)+"</td>";
			html+="<input type='hidden' name='items'id='item"+l.id+"' value='"+l.id+"'ids='"+l.id+"'productId='"+JY.Object.notEmpty(l.productId)+"' numbers='"+JY.Object.notEmpty(l.numbers)+"' weight='"+JY.Object.notEmpty(l.weight)+"' >";
			html+="</tr>";
		}
		$("#materialTable1").append(html);
	});
}
function getMaterial(){
	JY.Ajax.doRequest("materialForm",jypath +'/scm/materialin/findMaterialinSplit',null,function(data){
		$("#materialTable2 tbody").empty();
		var obj=data.obj;
		var list=obj.list;
		var results=list.results;
		var pageNum=list.pageNum,pageSize=list.pageSize,totalRecord=list.totalRecord;
		var html="";
		if(results!=null&&results.length>0){
			
			for(var i=0;i<results.length;i++){
				var l=results[i];
				html+="<tr id='tr1"+l.id+"'>";
	       		html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
	       		html+="<td class='center'>"+JY.Object.notEmpty(l.name)+"</td>";
	       		html+="<td class='center'>"+JY.Object.notEmpty(l.code)+"</td>";
	       		html+="<td class='center'>"+JY.Object.notEmpty(l.weight)+"</td>";
	       		html+="<td class='center'>"+JY.Object.notEmpty(l.num)+"</td>";
	       		html+="<td class='center'>"+JY.Object.notEmpty(l.price)+"</td>";
	       		html+="<input type='hidden' name='items' id ='item"+l.id+"' value='"+l.code+"' code='"+l.code+"' numbers='"+JY.Object.notEmpty(l.num)+"' weight='"+JY.Object.notEmpty(l.weight)+"'>";
	       		html+="</tr>";	
			}
			$("#materialTable2").append(html);
			JY.Page.setPage("materialForm","pageing3",pageSize,pageNum,totalRecord,"getMaterial");
		}else{
   		html+="<tr><td colspan='5' class='center'>没有相关数据</td></tr>";
   		$("#materialTable2").append(html);
   		$("#pageing3 ul").empty();//清空分页
   	}	
	 
		JY.Model.loadingClose();
	});
}

function getItem2(){
	JY.Ajax.doRequest("moForm",jypath +'/scm/moudle/findByPage',null,function(data){
		$("#moTable1 tbody").empty();
		var obj=data.obj;
		var list=obj.list;
		var results=list.results;
		var pageNum=list.pageNum,pageSize=list.pageSize,totalRecord=list.totalRecord;
		var html="";
		if(results!=null&&results.length>0){
			
			for(var i=0;i<results.length;i++){
				var l=results[i];
				html+="<tr id='tr"+l.id+"'>";
	       		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
	       		 html+="<td class='center'>"+JY.Object.notEmpty(l.name)+"</td>";
	       		 html+="<td class='center'>"+JY.Object.notEmpty(l.code)+"</td>";
	       		 if(l.status==1){
	       			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>正常</span></td>";
	       		 }else{
	       			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>无效</span></td>";
	       		 } 
	       		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.description)+"</td>";
	       		 html+="<input type='hidden' name='items' id ='item"+l.id+"' value='"+l.id+"'ids='"+l.id+"' code='"+l.code+"'>";
	       		 html+="</tr>";	
			}
			$("#moTable1").append(html);
			JY.Page.setPage("moForm","pageing2",pageSize,pageNum,totalRecord,"getItem2");
		}else{
   		html+="<tr><td colspan='5' class='center'>没有相关数据</td></tr>";
   		$("#moTable1").append(html);
   		$("#pageing2 ul").empty();//清空分页
   	}	
	 
		JY.Model.loadingClose();
	});
}
function getItem(){
	JY.Ajax.doRequest("splitItemForm",jypath +'/scm/product/findProductSplit',null,function(data){
		 $("#splitTable2 tbody").empty();
		var obj=data.obj;
		var list=obj.list;
		var results=list.results;
		var pageNum=list.pageNum,pageSize=list.pageSize,totalRecord=list.totalRecord;
		var html="";
		if(results!=null&&results.length>0){
			
			for(var i=0;i<results.length;i++){
				var l=results[i];
				html+="<tr id='tr"+l.id+"'>";
				html+="<td class='center'><label> <input type='checkbox'  checked='checked'  name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
				html+="<td>"+JY.Object.notEmpty(l.name)+"</td>";
				html+="<td>"+getSelectValue(l.goldType,gMaterials)+"</td>";
				html+="<td>"+parseFloat(l.totalWeight).toFixed(4)+"</td>";
				html+="<td>"+JY.Object.notEmpty(l.circel)+"</td>";
				html+="<td>"+parseFloat(l.stoneweight).toFixed(4)+"</td>";
				html+="<td>"+JY.Object.notEmpty(l.color)+"</td>";
				html+="<td>"+JY.Object.notEmpty(l.clarity)+"</td>";
				html+="<td>"+JY.Object.notEmpty(l.cut)+"</td>";
				html+="<input type='hidden' name='items' id ='item"+l.id+"' value='"+l.id+"'ids='"+l.id+"' names='"+l.name+"'>";
				html+="</tr>";
			}
			$("#splitTable2").append(html);
			JY.Page.setPage("splitItemForm","pageing1",pageSize,pageNum,totalRecord,"getItem");
		}else{
    		html+="<tr><td colspan='9' class='center'>没有相关数据</td></tr>";
    		$("#splitTable2").append(html);
    		$("#pageing1 ul").empty();//清空分页
    	}	
		JY.Model.loadingClose();
	});
}
function getItem1(){
	JY.Ajax.doRequest("splitItemForm",jypath +'/scm/product/findProductSplitOk',null,function(data){
		 $("#splitTable3 tbody").empty();
		var obj=data.obj;
		var results=obj.list;
//		var results=list.results;
		var html="";
		for(var i=0;i<results.length;i++){
			var l=results[i];
			html+="<tr id='tr"+l.id+"'>";
			html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
			html+="<td>"+JY.Object.notEmpty(l.name)+"</td>";
			html+="<input type='hidden' name='items'id='item"+l.id+"' value='"+l.id+"'ids='"+l.id+"'  >";
			html+="</tr>";
		}
		$("#splitTable3").append(html);
	});
}

function splitList(){
	var id = $("#splitForm input[name$='id']").val();
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/scm/purorder/findSplit',{id:id},function(data){
	    setForm(data,1);   
	});
}
function mateClick(){
	var nums = $("#splitTable1 input[name='items']").map(function(){return $(this).val();}).get().join(",");
	var chks=[];
	if(JY.Object.notNull(nums)){
		var numList=nums.split(",");
		for(var i=0;i<numList.length;i++){
			var num=numList[i];
			var id=$("#item"+num).attr("ids");
			chks.push(id);
		}
		JY.Ajax.doRequest(null,jypath +'/scm/purorder/detail/batchUpdateSplit',{chks:chks.toString()},function(data){
			JY.Model.info(data.resMsg,function(){splitList();});
		});
	}
}
function deliverySelect(id){
	JY.Ajax.doRequest("",jypath +'/scm/product/fundSplitOut',{orderDetailId:id},function(data){
		 $("#deliveryTable tbody").empty();
		var obj=data.obj;
		var list=obj.list;
		var results=list.results;
		var pageNum=list.pageNum,pageSize=list.pageSize,totalRecord=list.totalRecord;
		var html="";
		if(results!=null&&results.length>0){
			
			for(var i=0;i<results.length;i++){
				var l=results[i];
				html+="<tr id='tr"+l.id+"'>";
				html+="<td>"+JY.Object.notEmpty(l.name)+"</td>";
				html+="<td>"+getSelectValue(l.goldType,gMaterials)+"</td>";
				html+="<td>"+parseFloat(l.totalWeight).toFixed(4)+"</td>";
				html+="<td>"+parseFloat(l.stoneweight).toFixed(4)+"</td>";
				html+="<td>"+JY.Object.notEmpty(l.circel)+"</td>";
				html+="<td>"+JY.Object.notEmpty(l.price)+"</td>"; 
				html+="<td>"+JY.Object.notEmpty(l.outboundno)+"</td>";
				html+="<td>"+JY.Date.Format(l.checkTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
				html+="</tr>";
			}
			$("#deliveryTable").append(html);
//			JY.Page.setPage(null,"deliveryPageing",pageSize,pageNum,totalRecord,"deliverySelect(&apos;"+id+"&apos;)");
		}else{
	   		html+="<tr><td colspan='8' class='center'>没有相关数据</td></tr>";
	   		$("#deliveryTable").append(html);
//	   		$("#deliveryPageing ul").empty();//清空分页
		}	
		JY.Model.loadingClose();
	});
}
function deliveryClick(id){
	viewInfo ("deliveryDiv","已发货商品",500,750,null,null);
	deliverySelect(id);
}
function initFoot(isHide){
	
	var list="";
	if(1==isHide){
		 $("#splitTable1 tfoot").empty();
		 list=$("#splitTable1 input[name='items']").map(function(){return $(this).val();}).get().join(",");
	}else{
		$("#orderTable tfoot").empty();
		list=$("#orderTable input[name='items']").map(function(){return $(this).val();}).get().join(",");
	}
	var totWeight=0;
	var totNumbers=0;
	var totOkNum=0;
	var totMateNum=0;
	var totStockNum=0;
	if(list!=null&&list!=""){
		var chks=list.split(",");
		for(var i=0;i<chks.length;i++){
			totWeight+=parseFloat($("#item"+chks[i]).attr("weight"))>0?parseFloat($("#item"+chks[i]).attr("weight")):0;
			totNumbers+=parseInt($("#item"+chks[i]).attr("numbers"))>0?parseInt($("#item"+chks[i]).attr("numbers")):0;
			totOkNum+=parseInt($("#item"+chks[i]).attr("okNum"))>0?parseInt($("#item"+chks[i]).attr("okNum")):0;
			totMateNum+=parseInt($("#item"+chks[i]).attr("mateNum"))>0?parseInt($("#item"+chks[i]).attr("mateNum")):0;
			totStockNum+=parseInt($("#item"+chks[i]).attr("stockNum"))>0?parseInt($("#item"+chks[i]).attr("stockNum")):0;
		}
	}
	var html="";
	html+="<tr><td class='center'>合计</td>";
	html+="<td class='center'></td>";
	html+="<td class='center'></td>";
	html+="<td class='center'></td>";
	html+="<td class='center'></td>";
	html+="<td class='center'></td>";
	html+="<td class='center'></td>";
	html+="<td class='center'></td>";
//	html+="<td >"+totWeight+"</td>";
	html+="<td >"+totNumbers+"</td>";
	if(1==isHide){
		html+="<td >"+totOkNum+"</td>";
		html+="<td>"+totMateNum+"</td>";
		html+="<td >"+totStockNum+"</td>";
		html+="<td class='center'></td>";
		html+="<td class='center'></td>";
		html+="<td class='center'></td></tr>";
		
		$("#splitTable1 tfoot").append(html);
	}else{
		html+="<td class='center'></td>";
		html+="<td class='center'></td></tr>";
		$("#orderTable tfoot").append(html);
	}
}