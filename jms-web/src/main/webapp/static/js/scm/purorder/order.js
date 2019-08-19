var feeTypes=[];
var mdCodes=[];
var dColors=[];
var dClaritys=[];
var gMaterials=[];
var gWeights=[];
var dWeights=[];
var jewelrys=[];
var cuts=[];
var trNum=0;
$(function () {
	//下拉框
	JY.Dict.setSelect("selectStoreStatus","SCM_ORDER_STATUS",2,"全部");
	JY.Dict.setSelect("selectStoreLabel","ORDER_LABEL",2,"全部");
	setBaseData();
//	JY.Dict.setSelect("jewelry","SCM_PRO_CATE_JEWELRY",2,"全部");
	jewelrys=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_PRO_CATE_JEWELRY",jewelrys);
	feeTypes=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"feeType",feeTypes);
	dColors=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_DATA_COLOR",dColors);
	dClaritys=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_DATA_CLARITY",dClaritys);
	gMaterials=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_PRO_GOLD_TYPE",gMaterials);
	gWeights=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_ATTR_GOLDWEIGHT",gWeights);
	dWeights=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"stoneSize",dWeights);
	cuts=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_DATA_CUT",cuts);
	
	mdCodes=gridSelect1(jypath +'/scm/moudle/select',mdCodes);
	initOrgSelectData();
	initOrgBaseData();
	getbaseList();
	//增加回车事件
	$("#storeBaseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	//新加
	$('#addBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		cleanForm();
		var userName=$("#userName").val();
		$("#orderForm span[name='createName']").text(userName);
		var _ztree = $.fn.zTree.getZTreeObj("selectOrgTree");
  	  	var _node = _ztree.getNodeByParam("id", curUser.orgId, null);
  	  	_ztree.selectNode(_node,false);
  	    $("#orderForm #orgInput").prop("value",_node.name);
  	  	$("#orderForm input[name$='orgId']").prop("value",_node.id);
  	  	$("#orderForm input[name$='orgName']").prop("value",_node.name);
		addInfo("orderDiv","新增",function(type){
			 if(JY.Validate.form("orderForm")){
				 var that =$(this);
				 var id=$("#orderForm input[name$='id']").val();
				 var orderNo=$("#orderForm input[name$='orderNo']").val();
				 var totalNum=$("#orderForm input[name$='totalNum']").val();
				 var weight=$("#orderForm input[name$='weight']").val();
				 var totalFee=$("#orderForm input[name$='totalFee']").val();
				 var arrivalDate=new Date($("#orderForm input[name$='arrivalDate']").val());
				 var description=$("#orderForm textarea[name$='description']").val();
				 var orgId=$("#orderForm input[name$='orgId']").val();
//				 var orderType=$("#orderForm input[name$='orderType']").val();
				 var orderType="0";
				 setItemArry("#orderTable");
				 var params ={id:id,orderNo:orderNo,totalNum:totalNum,weight:weight,totalFee:totalFee,arrivalDate:arrivalDate,description:description,orgId:orgId,orderType:orderType,status:type,items:itemArry};								 	 
				 $.ajax({type:'POST',url:jypath+'/scm/purorder/add',data:JSON.stringify(params),dataType:'json',contentType:"application/json",success:function(data,textStatus){  			        	 
			        	 if(data.res==1){
			        		 that.dialog("close");      
			        		JY.Model.info(data.resMsg,function(){search();});
			        	 }else{
			        		 JY.Model.error(data.resMsg);
			        	 }     	
			         }
			     });
			 }	
		});
		
	});
	//批量审核
	$('#checkBatch').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#baseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			JY.Model.confirm("确认要审核选中的订单吗?",function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/purorder/checkBatch',{chks:chks.toString()},function(data){
					JY.Model.info(data.resMsg,function(){search();});
				});
			});		
		}	
	});
	$('#findBtn').on('click', function(e) {
		var chks =[];    
		$('#baseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{
			find(chks[0]);
		}
	});
	$('#printBtn').on('click', function(e) {
		var chks =[];    
		$('#baseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{
			JY.Ajax.doRequest(null,jypath +'/scm/purorder/find',{id:chks[0]},function(data){
				$("#orderForm input[name='id']").val(data.obj.id);
				$("#orderForm input[name='orderNo']").val(data.obj.orderNo);
				print();
			});
		}
	});
	$('#splitBtn').on('click', function(e) {
		var chks =[];    
		$('#baseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{
			split(chks[0]);
		}
	});
	$('#poolBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#baseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			JY.Model.confirm("确认要汇总选中的订单吗?",function(){	
				cleanForm();
				JY.Ajax.doRequest(null,jypath +'/scm/purorder/pool',{chks:chks.toString()},function(data){
					setItems(data.obj.list);
					getTotNum();
					addInfo("orderDiv","汇总",function(type){
						 if(JY.Validate.form("orderForm")){
							 var that =$(this);
							 var id=$("#orderForm input[name$='id']").val();
							 var orderNo=$("#orderForm input[name$='orderNo']").val();
							 var totalNum=$("#orderForm input[name$='totalNum']").val();
							 var weight=$("#orderForm input[name$='weight']").val();
							 var totalFee=$("#orderForm input[name$='totalFee']").val();
							 var arrivalDate=new Date($("#orderForm input[name$='arrivalDate']").val());
							 var description=$("#orderForm textarea[name$='description']").val();
							 var orgId=$("#orderForm input[name$='orgId']").val();
//							 var orderType=$("#orderForm input[name$='orderType']").val();
							 var orderType="0";
							 setItemArry("#orderTable");
							 var params ={id:id,orderNo:orderNo,totalNum:totalNum,weight:weight,totalFee:totalFee,arrivalDate:arrivalDate,description:description,orgId:orgId,orderType:orderType,status:type,orderIds:chks,items:itemArry};								 	 
							 $.ajax({type:'POST',url:jypath+'/scm/purorder/add',data:JSON.stringify(params),dataType:'json',contentType:"application/json",success:function(data,textStatus){  			        	 
						        	 if(data.res==1){
						        		 that.dialog("close");      
						        		JY.Model.info(data.resMsg,function(){search();});
						        	 }else{
						        		 JY.Model.error(data.resMsg);
						        	 }     	
						         }
						     });
						 }	
					});
				});
			});		
		}	
	});
	$('#addZBT').on('click', function(e) {
		JY.Model.confirm("确认要确认要导入珠宝通订单吗?",function(){	
			JY.Ajax.doRequest(null,jypath +'/scm/purorder/addZBT',null,function(data){
				JY.Model.info(data.resMsg,function(){search();});
			});
		});
	});
	//时间渲染
	$("#dateRender").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd',
		changeYear: true,
		changeMonth:true,
		onSelect:function(dateText, inst) {
			if(new Date(dateText) < new Date((new Date()).Format("yyyy/MM/dd"))){
				$(this).val('');
				$(this).tips({side: 1,msg: "要求到货日期不能早于要货日期！",bg: '#FF2D2D',time: 1});
				return false;
			}
		}
	});
	$("#dateRender1").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd',
		changeYear: true,
		changeMonth:true,
		onSelect:function(dateText, inst) { }
	});
	$("#dateRender2").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd',
		changeYear: true,
		changeMonth:true,
		onSelect:function(dateText, inst) { }
	});
	/* 款式选择*/
	$("#addRow").on('click', function(e) {
		getselect1(jewelrys,"jewelry");
		editInfo ("selectMoDiv","款式选择",function(){
			var chks =[];
			var list=$("#moTable input[name='items']").map(function(){return $(this).val();}).get().join(",");
			if(list!=null && list!=''){
				chks=list.split(",");
			}
			var html="";
			$.each(chks,function(i, e) {
				html+=getTableRow(e);
			});
			$("#orderTable").append(html);
			$(this).dialog("close");
		});
		getItem2();
	});
	/**
	 * 匹配方法
	 */
	$("#addRowList").on('click', function(e) {
		
		setDetialList();
	});
	
	/* 拆分商品选择*/
	$('#itemAttrAdd').on('click', function(e) {
		var html="";
		var numbers=$("#item"+trNum).attr("lackNum");
		var chkNum=$("#item"+trNum).attr("mateNum");
		var sun=parseInt(numbers)+parseInt(chkNum);
		var list=[];
		$('#splitTable3 input[name="ids"]').each(function(){ 
			list.push($(this).val());
		});
		var i=list.length;
		$('#splitTable2 input[name="ids"]:checked').each(function(){
			var num=$(this).val();
			if(i>sun){
				alert('选择的商品数量大于缺货数量');
				return false;
			}
			if(list.indexOf(num)==-1){
				var id=$("#item"+num).attr("ids");
				var name=$("#item"+num).attr("names");
				html+="<tr id='tr"+num+"' >";
				html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+num+"' class='ace' /> <span class='lbl'></span></label></td>";
				html+="<td>"+name+"</td>";
				html+="<input type='hidden' name='items'id='item"+num+"' value='"+num+"'ids='"+num+"'  >";
				html+=" </tr>";
				i++;
			}
		 });  
		 $("#splitTable3").append(html);
	});
	/* 拆分商品移除*/
	$('#itemAttrDel').on('click', function(e) {
		$('#splitTable3 input[name="ids"]:checked').each(function(){ 
			var trid=$(this).val();
			$("#tr"+trid).remove();
		});
	});
	/* 选原料*/
	$('#materialAdd').on('click', function(e) {
		var html="";
		var list=[];
		$('#materialTable1 input[name="ids"]').each(function(){ 
			list.push($(this).val());
		});
		$('#materialTable2 input[name="ids"]:checked').each(function(){
			var num=$(this).val();
			var numbers1=$("#item"+num).attr("numbers");
			var weight1 =$("#item"+num).attr("weight");
			var code1 =$("#item"+num).attr("code");
			if(list.indexOf(num)==-1){
				html+="<tr id='tr"+num+"' >";
				html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+num+"' class='ace' /> <span class='lbl'></span></label></td>";
				html+="<td>"+code1+"</td>";
				html+="<td style='padding:0px;'>"+"<input type='text'  id='numbers"+num+"' name='numbers' jyValidate='required' style='width:100%;height:30px;'>"+"</td>";
				html+="<td style='padding:0px;'>"+"<input type='text'  id='weight"+num+"' name='weight' jyValidate='required' style='width:100%;height:30px;'>"+"</td>";
				html+="<input type='hidden' name='items'id='item"+num+"' value='"+num+"'ids='"+num+"' numbers='' weight =''productId='"+JY.Object.notEmpty(num)+"'>";
				html+=" </tr>";
			}
		 });  
		$("#materialTable1").append(html);
	});
	/* 删除原料*/
	$('#materialDel').on('click', function(e) {
		$('#materialTable1 input[name="ids"]:checked').each(function(){ 
			var trid=$(this).val();
			$("#tr"+trid).remove();
		});
	});
	
	/* 选款式*/
	$('#moAttrAdd').on('click', function(e) {
		var html="";
		var list=[];
		$('#moTable input[name="ids"]').each(function(){ 
			list.push($(this).val());
		});
		var i=list.length;
		$('#moTable1 input[name="ids"]:checked').each(function(){
			var num=$(this).val();
			if(list.indexOf(num)==-1){
				var id=$("#item"+num).attr("ids");
				var name=$("#item"+num).attr("code");
				html+="<tr id='tr"+num+"' >";
				html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+num+"' class='ace' /> <span class='lbl'></span></label></td>";
				html+="<td>"+name+"</td>";
				html+="<input type='hidden' name='items'id='item"+num+"' value='"+num+"'ids='"+num+"'  >";
				html+=" </tr>";
				i++;
			}
		});  
		$("#moTable").append(html);
	});
	
	/* 已选款式移除*/
	$('#moAttrDel').on('click', function(e) {
		$('#moTable input[name="ids"]:checked').each(function(){ 
			var trid=$(this).val();
			$("#tr"+trid).remove();
		});
	});
	
	var mdAddInput={};
	$("#mdAddInput").typeahead({
		source:function(code,process){
			$.ajax({
				type:'POST',
				url:jypath+'/scm/moudle/findScmMoudleByCode',
				data:{code:code},
				dataType:'json',
				success:function(data,textStatus){
					mdAddInput={};
					var array=[];
					$.each(data.obj, function (index, ele) {
						mdAddInput[ele.code] = ele.id;
						array.push(ele.code);
					});
					process(array);
				}
			});
		},
        items: 10,
        afterSelect: function (item) {
//        	$("#itemForm input[name$='mdCodeId']").val(mdAddInput[item]);
        	var html=getTableRow(mdAddInput[item]);
        	$("#orderTable").append(html);
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
function getTableRow(e){
	var html="";
	html+="<tr id='temtr"+itemNum+"' >";
	html+="<td class='center'>"+ getSelectValue(e,mdCodes) +"</td>";
	html+="<td style='display:none'>"+"</td>";
	html+="<td style='padding:0px;'> <select id='gMaterial"+itemNum+"' jyValidate='required' style='width:100%;'  onchange= upInput("+itemNum+",'gMaterial')>"+options(gMaterials)+"</select></td>";
	html+="<td style='padding:0px;'> <select id='gWeight"+itemNum+"' jyValidate='required' style='width:100%;'  onchange= upInput("+itemNum+",'gWeight')>"+options(gWeights)+"</select></td>";
	html+="<td style='padding:0px;'> <select id='dWeight"+itemNum+"'  style='width:100%;'  onchange= upInput("+itemNum+",'dWeight')>"+options(dWeights)+"</select></td>";
	html+="<td style='padding:0px;'> <select id='dClarity"+itemNum+"'  style='width:100%;'  onchange= upInput("+itemNum+",'dClarity')>"+options(dClaritys)+"</select></td>";
	html+="<td style='padding:0px;'> <select id='dColor"+itemNum+"'  style='width:100%;'  onchange= upInput("+itemNum+",'dColor')>"+options(dColors)+"</select></td>";
	html+="<td style='padding:0px;'><input type='text'  id='weight"+itemNum+"' jyValidate='required' style='width:100%;height:30px;' onchange=upInput("+itemNum+",'weight') value=''>"+"</td>";
	html+="<td style='padding:0px;'>"+"<input type='text'  id='numbers"+itemNum+"' name='numbers' jyValidate='required' style='width:100%;height:30px;' onchange=upInput("+itemNum+",'numbers')></td>";
	html+="<td style='padding:0px;'>"+"</td>";
	html+="<td style='display:none'>"+"</td>";
	html+="<td style='display:none'>"+"</td>";
	html+="<td style='display:none'>"+"</td>";
	html+="<td style='display:none'>"+"</td>";
	html+="<td style='display:none'>"+"</td>";
	html+="<td>";
	html+="<a class='aBtnNoTD' onclick='editDetail(&apos;"+itemNum+"&apos;)' title='编辑' href='#'><i class='icon-edit color-blue bigger-120'></i></a>";		
	html+="<a class='aBtnNoTD' onclick='delDetail(&apos;"+itemNum+"&apos;)' title='删除' href='#'><i class='icon-remove-sign color-red bigger-120'></i></a>";		
	html+="<input type='hidden' name='items' id='item"+itemNum+"' value='"+itemNum+"' mdCode='"+e+"' mdtlCode='"+"' gMaterial='"+"' gWeight='"+"' dWeight='"
	+"' dClarity='"+"' dColor='"+"' weight='"+"' numbers='"+"' feeType='"+"' basicCost='"
	+"' additionCost='"+"' otherCost='"+"' unitprice='"+"' totalFee='"+"' status='"+1+"' ids='"+"' description='"+"'list='"+"' lackNum='"+"' mateNum='"+"' beforeId=''>";			
	html+="</td>";
	html+="</tr>";
	itemNum++;
	return html;
}
function upInput(num,name){
	var gMaterial=$("#"+name+num).val();
	 $("#item"+num).attr(name,gMaterial);
	 if(name=="numbers"){
		 getTotNum();
	 }
}
function getTotNum(){
	var nums = $("#orderTable input[name='items']").map(function(){return $(this).val();}).get().join(",");
	var totNum=0;
	if(JY.Object.notNull(nums)){
		var numList=nums.split(",");
		for(var i=0;i<numList.length;i++){
			var numbers=$("#item"+numList[i]).attr("numbers");
			totNum+=Number(numbers);
		}
	}
	$("#orderForm input[name$='totalNum']").val(totNum);
}
function delDetail(num){
	$("#temtr"+num).remove();
	getTotNum();
}
function editDetail(num){
	cleanItemForm1();
	var mdCode=$("#item"+num).attr("mdCode");
	var mdtlCode=$("#item"+num).attr("mdtlCode");
	var mdtlCodeName=$("#item"+num).attr("mdtlCodeName");
	var gMaterial=$("#item"+num).attr("gMaterial");
	var gWeight=$("#item"+num).attr("gWeight");
	var dWeight=$("#item"+num).attr("dWeight");
	var dClarity=$("#item"+num).attr("dClarity");
	var dColor=$("#item"+num).attr("dColor");
	var weight=$("#item"+num).attr("weight");
	var numbers1=$("#item"+num).attr("numbers");
	var feeType=$("#item"+num).attr("feeType");
	var basicCost=$("#item"+num).attr("basicCost");
	var additionCost=$("#item"+num).attr("additionCost");
	var otherCost=$("#item"+num).attr("otherCost");
	var unitprice=$("#item"+num).attr("unitprice");
	var totalFee=$("#item"+num).attr("totalFee");
	var status=$("#item"+num).attr("status");
	var ids=$("#item"+num).attr("ids");
	var description=$("#item"+num).attr("description");
	$("#orderForm1 input[name$='mdCode']").val(getSelectValue(mdCode,mdCodes));
	$("#orderForm1 input[name$='mdCode']").attr("readonly", true);
	$("#orderForm1 input[name$='mdtlCode']").val(JY.Object.notEmpty(mdtlCode));
	$("#orderForm1 input[name$='mdtlCodeName']").attr("readonly", true);
	$("#orderForm1 input[name$='mdtlCodeName']").val(JY.Object.notEmpty(mdtlCodeName));
	$("#gMaterialSelect1").val(JY.Object.notEmpty(gMaterial));
	$("#gWeightSelect1").val(JY.Object.notEmpty(gWeight));
	$("#dWeightSelect1").val(JY.Object.notEmpty(dWeight));
	$("#dClaritySelect1").val(JY.Object.notEmpty(dClarity));
	$("#dColorSelect1").val(JY.Object.notEmpty(dColor));
//	$("#weightSelect1").val(JY.Object.notEmpty(weight));
	$("#orderForm1 input[name$='weight']").val(weight);
	$("#orderForm1 input[name$='numbers']").val(numbers1);
	$("#feeTypeSelect1").val(JY.Object.notEmpty(feeType));
	$("#orderForm1 input[name$='basicCost']").val(JY.Object.notEmpty(basicCost));
	$("#orderForm1 input[name$='additionCost']").val(JY.Object.notEmpty(additionCost));
	$("#orderForm1 input[name$='otherCost']").val(JY.Object.notEmpty(otherCost));
	$("#orderForm1 input[name$='unitprice']").val(JY.Object.notEmpty(unitprice));
	$("#orderForm1 input[name$='totalFee']").val(JY.Object.notEmpty(totalFee));
//	$("#itemDiv input[name$='status']").val(status);
	$("#orderForm1 textarea[name$='description']").val(JY.Object.notEmpty(description));
	editInfo1 ("orderDiv1","修改明细",function(){
		var mdtlCode=$("#orderForm1 input[name$='mdtlCode']").val();
		var mdtlCodeName=$("#orderForm1 input[name$='mdtlCodeName']").val();
		var gMaterial=$("#gMaterialSelect1 option:selected").val();
		var gWeight=$("#gWeightSelect1 option:selected").val();
		var dWeight=$("#dWeightSelect1 option:selected").val();
		var dClarity=$("#dClaritySelect1 option:selected").val();
		var dColor=$("#dColorSelect1 option:selected").val();
//		var weight=$("#weightSelect1 option:selected").val();
		var weight=$("#orderForm1 input[name$='weight']").val();
		var numbers=$("#orderForm1 input[name$='numbers']").val();
		var feeType=$("#feeTypeSelect1 option:selected").val();
		var basicCost=$("#orderForm1 input[name$='basicCost']").val();
		var additionCost=$("#orderForm1 input[name$='additionCost']").val();
		var otherCost=$("#orderForm1 input[name$='otherCost']").val();
		var unitprice=$("#orderForm1 input[name$='unitprice']").val();
		var totalFee=$("#orderForm1 input[name$='totalFee']").val();
		var status=$("#orderForm1 input[name$='status']").val();
		var description=$("#orderForm1 textarea[name$='description']").val();
		var status=$("#orderForm1 input[name$='status']").val();
		if(JY.Validate.form("orderForm1")){	
//			 $("#item"+num).attr("mdCode",mdCode);
			 $("#item"+num).attr("mdtlCodeName",mdtlCodeName);
			 $("#item"+num).attr("gMaterial",gMaterial);
			 $("#item"+num).attr("gWeight",gWeight);
			 $("#item"+num).attr("dWeight",dWeight);
			 $("#item"+num).attr("dClarity",dClarity);
			 $("#item"+num).attr("dColor",dColor);
			 $("#item"+num).attr("weight",weight);
			 $("#item"+num).attr("numbers",numbers);
			 $("#item"+num).attr("feeType",feeType);
			 $("#item"+num).attr("basicCost",basicCost);
			 $("#item"+num).attr("additionCost",additionCost);
			 $("#item"+num).attr("otherCost",otherCost);
			 $("#item"+num).attr("unitprice",unitprice);
			 $("#item"+num).attr("totalFee",totalFee);
//			 $("#item"+num).attr("status",status);
			 $("#item"+num).attr("description",description);
			 
//			 $("#temtr"+num).find("td").eq(0).html(mdCode);
			 $("#temtr"+num).find("td").eq(1).html(mdtlCodeName);
//			 $("#temtr"+num).find("td").eq(2).html(getSelectValue(gMaterial,gMaterials));
//			 $("#temtr"+num).find("td").eq(3).html(getSelectValue(gWeight,gWeights));
//			 $("#temtr"+num).find("td").eq(4).html(getSelectValue(dWeight,dWeights));
//			 $("#temtr"+num).find("td").eq(5).html(getSelectValue(dClarity,dClaritys));
//			 $("#temtr"+num).find("td").eq(6).html(getSelectValue(dColor,dColors));
//			 $("#temtr"+num).find("td").eq(7).html(getSelectValue(weight,gWeights));
//			 $("#temtr"+num).find("td").eq(8).html(numbers);
			 $("#gMaterial"+num).val(gMaterial);
			 $("#gWeight"+num).val(gWeight);
			 $("#dWeight"+num).val(dWeight);
			 $("#dClarity"+num).val(dClarity);
			 $("#dColor"+num).val(dColor);
			 $("#numbers"+num).val(numbers);
			 $("#feeType"+num).val(feeType);
			 $("#weight"+num).val(weight);
//			 $("#temtr"+num).find("td").eq(9).html(feeType);
			 $("#temtr"+num).find("td").eq(10).html(basicCost);
			 $("#temtr"+num).find("td").eq(11).html(additionCost);
			 $("#temtr"+num).find("td").eq(12).html(otherCost);
			 $("#temtr"+num).find("td").eq(13).html(unitprice);
			 $("#temtr"+num).find("td").eq(14).html(totalFee);
			 getTotNum();
			 $(this).dialog("close");
		 }
	});
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
function getselect(selects,opts){
	$("#"+opts).empty();
	var op="";
	var op="<option value=''>" +"请选择" + "</option>";
	$.each(selects,function(i, e) {
		op+="<option value='" + e.key + "'>" + e.text + "</option>"
    });
	$("#"+opts).append(op);
}
function getselect1(selects,opts){
	$("#"+opts).empty();
	var op="";
	var op="<option value=''>" +"请选择" + "</option>";
	$.each(selects,function(i, e) {
		op+="<option value='" + e.text + "'>" + e.text + "</option>"
    });
	$("#"+opts).append(op);
}
function gridSelect1(url,selects){
	$.ajax({
        type: 'POST',
        url: url,
        data: {},
        dataType: 'json',
        success: function(data, textStatus) {
        	$.each(data,function(i,e){
    			var a ={key:e.key,text:e.value};
    			selects[i]=a;
    		});
               
        }
    })
	return selects;
}
function search(){
	$("#searchBtn").trigger("click");
}

/**组装并加载table数据列表**/
function getbaseList(init){
	if(init==1)$("#baseForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("baseForm",jypath +'/scm/purorder/findByPage',null,function(data){
		 $("#baseTable tbody").empty();
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
            		 var labelStr;
            		 if(l.label==1) labelStr="已汇总"; else if(l.label==2) labelStr="已拆分";else labelStr="未处理";
            		 var statusStr
            		 if(l.status==1)statusStr="待审核";else if(l.status==2)statusStr="已审核";else if(l.status==3)statusStr="已完成";else if(l.status==4)statusStr="已拒绝"; else if(l.status==9)statusStr="已删除"; else statusStr="草稿";
            		 html+="<tr>";
            		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
            		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
            		 html+="<td class='center'><a style='cursor:pointer;' onclick='find(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.orderNo)+"</a></td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.orgName)+"</td>"
            		 html+="<td class='left'>"+JY.Object.notEmpty(l.totalNum)+"</td>";
            		 html+="<td class='left'>"+JY.Object.notEmpty(l.match)+"</td>";
//            		 html+="<td class='left hidden-480' >"+JY.Object.notEmpty(l.weight)+"</td>";
//            		 html+="<td class='left hidden-480'>"+JY.Object.notEmpty(l.totalFee)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.arrivalDate,"yyyy-MM-dd")+"</td>";
            		 html+="<td class='center hidden-480'>"+labelStr+"</td>";
            		 html+="<td class='center hidden-480'>"+statusStr+"</td>";
//            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.checkName)+"</td>";
//            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.checkTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.createName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.createTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
//            		 if(l.status==1){
//            			 html+=JY.Tags.setFunction1(l.id,permitBtn,'edit,del,split,submit,uCheck,');
//            		 }else if(l.status==2){
//            			 if(l.orgId==curUser.orgRootId){
//            				 html+=JY.Tags.setFunction1(l.id,permitBtn,"check,edit,split,del,submit,"+(l.label!=0?"uCheck,":""));
//            			 }else{
//            				 html+=JY.Tags.setFunction1(l.id,permitBtn,"check,edit,del,submit,"+(l.label!=0?"uCheck,":""));
//            			 }
//            		 }else if(l.status==3){
//            			 html+=JY.Tags.setFunction1(l.id,permitBtn,'check,edit,del,split,uCheck,submit,');
//            		 }else if(l.status==0||l.status==4){
//            			 html+=JY.Tags.setFunction1(l.id,permitBtn,'check,split,uCheck,');
//            		 }else{
//            			 html+=JY.Tags.setFunction(l.id,permitBtn);
//            		 }
            		 html+="</tr>";		 
            	 } 
        		 $("#baseTable tbody").append(html);
        		 JY.Page.setPage("baseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='13' class='center'>没有相关数据</td></tr>";
        		$("#baseTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
 	 
    		 JY.Model.loadingClose();
	 });
}
function viewInfo(id,title,height,width,fn,savefn){	
	var butts =Array();
	var html1={html: "<i class='icon-ok bigger-110'></i>&nbsp;拆分","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this,2);}}};
	var html2={html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(fn) == 'function'){fn.call(this);}}};
	var html3={html: "<i class='icon-ok bigger-110'></i>&nbsp;打印预览","class":"btn btn-primary btn-xs",click:function(){print()}};
	if(JY.Object.notEmpty(savefn)!=""){
		butts.push(html1);
	}else{
		butts.push(html3);
	}
	butts.push(html2);
	$("#"+id).removeClass('hide').dialog({resizable:false,modal:true,open:function(event,ui){$(".ui-dialog-titlebar-close").children().hide();},height:height,width:width,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"查看")+"</h4></div>",title_html:true,buttons:butts});
	$("#addDiv").addClass('hide') ;
	JY.Tools.formReadonly("orderForm",true);
	$("#emptyOrg").removeClass('lrspace3 aBtnNoTD');
	$("#emptyOrg").addClass('hide') ;
	$("#orgInput").attr("onclick", "");
	
}
function editInfo(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,modal:true,height:768,width:1024,open:function(event,ui){$(".ui-dialog-titlebar-close").children().hide();},title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
	$("#addDiv").removeClass('hide');
//	JY.Tools.formReadonly("orderForm",false);
	$("#emptyOrg").removeClass('hide');
	$("#orgInput").attr("readonly", true);
//	$("#createName").attr("readonly", true);
	$("#dateRender").attr("readonly", true);
	$("#description").attr("readonly", false);
	$("#mdAddInput").attr("readonly", false);
	$("#emptyOrg").addClass('lrspace3 aBtnNoTD') ;
	$("#orgInput").attr("onclick", "showOrgComp(); return false;");
}
function editInfo1(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,modal:true,height:500,width:600,open:function(event,ui){$(".ui-dialog-titlebar-close").children().hide();},title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
	$("#addDiv").removeClass('hide')
}
function addInfo(id,title,savefn,cancelfn){
	 $("#"+id).removeClass('hide').dialog({
	    	resizable:false,
	    	height:768,
	    	width:1024,
	    	modal:true,
	    	open:function(event,ui){$(".ui-dialog-titlebar-close").children().hide();},
	    	title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",
	     title_html:true,
	     buttons:[{
	     	html: "<i class='icon-ok bigger-110'></i>&nbsp;保存为草稿",
	     	"class":"btn btn-primary btn-xs",
	     	click:function(){if(typeof(savefn) == 'function'){savefn.call(this,0);}}},
	     	{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存并提交",
	     	 "class":"btn btn-primary btn-xs",
	     	 click:function(){if(typeof(savefn) == 'function'){savefn.call(this,1);}}},
	     	 {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",
	     	 click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);
	     	 }
	     	}
	     	}]
	     });
		$("#addDiv").removeClass('hide');
		$("#emptyOrg").removeClass('hide');
		$("#orgInput").attr("readonly", true);
		$("#dateRender").attr("readonly", true);
		$("#description").attr("readonly", false);
		$("#mdAddInput").attr("readonly", false);
		$("#emptyOrg").addClass('lrspace3 aBtnNoTD') ;
		$("#orgInput").attr("onclick", "showOrgComp(); return false;");
}
function checkInfo(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({
    	resizable:false,
    	height:768,
    	width:1024,
    	modal:true,
    	title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"审核")+"</h4></div>",
     title_html:true,
     buttons:[{
     	html: "<i class='icon-ok bigger-110'></i>&nbsp;通过",
     	"class":"btn btn-primary btn-xs",
     	click:function(){if(typeof(savefn) == 'function'){savefn.call(this,2);}}},
     	{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;不通过",
     	 "class":"btn btn-primary btn-xs",
     	 click:function(){if(typeof(savefn) == 'function'){savefn.call(this,4);}}},
     	 {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",
     	 click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);
     	 }
     	}
     	}]
     });
	$("#addDiv").addClass('hide')
}
function getSelectValue(key,list){
	var _value="";
	$.each(list,function(i,e){
		if(e.key==key){
			_value= e.text;
			return false;
		}
	});
	return _value;
}
function setDetialList(){
	JY.Ajax.doRequest(null,jypath +'/scm/purorder/findLackDetail',null,function(data){
		var items=data.obj.items;
		var array = new Array();
		var list=new Array();
		var nums = $("#orderTable input[name='items']").map(function(){return $(this).val();}).get().join(",");
		if(JY.Object.notNull(nums)){
			var numList=nums.split(",");
			for(var i=0;i<numList.length;i++){
				var num=numList[i];
				var beforeId=$("#item"+num).attr("beforeId");
				if(JY.Object.notEmpty(beforeId) !=""){
					list.push(beforeId);
				}
			}
		}
		for(var i=0;i<items.length;i++){
			var item=items[i];
			if(list.indexOf(item.beforeId)==-1){
				array.push(item);
			}
		}
		setItems(array,0);
		getTotNum();
	});
}
function setForm(data,isHide){
	var l=data.obj;
	var isForm="";
	if(1==isHide){
		isForm="#splitForm"
		$(isForm+" input[name$='orgName']").prop("value",JY.Object.notEmpty(l.orgName));
	}else{
		isForm="#orderForm"
		$(isForm+" input[name$='orgId']").prop("value",JY.Object.notEmpty(l.orgId));
		$(isForm+" input[name$='orgName']").prop("value",JY.Object.notEmpty(l.orgName));
		$("#orgInput").prop("value",JY.Object.notEmpty(l.orgName));
	}
	$(isForm+" input[name$='id']").val(JY.Object.notEmpty(l.id));
	$(isForm+" input[name$='orderNo']").val(JY.Object.notEmpty(l.orderNo));
	$(isForm+" input[name$='totalNum']").val(JY.Object.notEmpty(l.totalNum));
	$(isForm+" input[name$='weight']").val(JY.Object.notEmpty(l.weight));
	$(isForm+" input[name$='totalFee']").val(JY.Object.notEmpty(l.totalFee));
	$(isForm+" input[name$='arrivalDate']").val(new Date(l.arrivalDate).Format("yyyy/MM/dd"));
	$(isForm+" textarea[name$='description']").val(JY.Object.notEmpty(l.description));
	$(isForm+" span[name='createName']").text(JY.Object.notEmpty(l.createName));
	$(isForm+" span[name='checkName']").text(JY.Object.notEmpty(l.checkName));
	$(isForm+" span[name='updateName']").text(JY.Object.notEmpty(l.updateName));
	$(isForm+" span[name='createTime']").text(JY.Object.notEmpty(l.createTime)==""?"":new Date(l.createTime).Format("yyyy/MM/dd hh:mm:ss"));
	$(isForm+" span[name='updateTime']").text(JY.Object.notEmpty(l.updateTime)==""?"":new Date(l.updateTime).Format("yyyy/MM/dd hh:mm:ss"));
	$(isForm+" span[name='checkTime']").text(JY.Object.notEmpty(l.checkTime)==""?"":new Date(l.checkTime).Format("yyyy/MM/dd hh:mm:ss"));
	var items=l.items;
	setItems(items,isHide);
}
function setItems(items,isHide){
	if(items!=null){
		var html="";
		for(var i=0;i<items.length;i++){
			var item=items[i];
			html+="<tr id='temtr"+itemNum+"' >";
			html+="<td class='center'>"+ getSelectValue(item.mdCode,mdCodes) +"</td>";
			html+="<td style='display:none'>"+item.mdtlCode+"</td>";
			if(0==isHide){
				html+="<td style='padding:0px;'> <select id='gMaterial"+itemNum+"' jyValidate='required' style='width:100%;'  onchange= upInput("+itemNum+",'gMaterial')>"+options(gMaterials,item.gMaterial)+"</select></td>";
				html+="<td style='padding:0px;'> <select id='gWeight"+itemNum+"' jyValidate='required' style='width:100%;'  onchange= upInput("+itemNum+",'gWeight')>"+options(gWeights,item.gWeight)+"</select></td>";
				html+="<td style='padding:0px;'> <select id='dWeight"+itemNum+"'  style='width:100%;'  onchange= upInput("+itemNum+",'dWeight')>"+options(dWeights,item.dWeight)+"</select></td>";
				html+="<td style='padding:0px;'> <select id='dClarity"+itemNum+"'  style='width:100%;'  onchange= upInput("+itemNum+",'dClarity')>"+options(dClaritys,item.dClarity)+"</select></td>";
				html+="<td style='padding:0px;'> <select id='dColor"+itemNum+"' style='width:100%;'  onchange= upInput("+itemNum+",'dColor')>"+options(dColors,item.dColor)+"</select></td>";
//				html+="<td style='padding:0px;'><select id='feeType"+itemNum+"' style='width:100%;'  onchange= upInput("+itemNum+",'feeType')>"+options(feeTypes,item.feeType)+"</select>"+"</td>";
//				html+="<td style='padding:0px;'><input type='text'  id='weight"+itemNum+"' jyValidate='required' style='width:100%;height:30px;' onchange=upInput("+itemNum+",'weight') value='"+JY.Object.notEmpty(item.weight)+"'>"+"</td>";
				html+="<td style='padding:0px;'>"+"<input type='text'  id='numbers"+itemNum+"' jyValidate='required' style='width:100%;height:30px;' onchange=upInput("+itemNum+",'numbers') value='"+item.numbers+"'></td>";
			}else{
				
				html+="<td>"+getSelectValue(item.gMaterial,gMaterials)+"</td>";
				html+="<td>"+getSelectValue(item.gWeight,gWeights)+"</td>";
				html+="<td>"+getSelectValue(item.dWeight,dWeights)+"</td>";
				html+="<td>"+getSelectValue(item.dClarity,dClaritys)+"</td>";
				html+="<td>"+getSelectValue(item.dColor,dColors)+"</td>";
				html+="<td>"+getSelectValue(item.cut,cuts)+"</td>";
				html+="<td>"+JY.Object.notEmpty(item.circel)+"</td>";
//				html+="<td>"+JY.Object.notEmpty(item.weight)+"</td>";
				html+="<td>"+JY.Object.notEmpty(item.numbers)+"</td>";
			}
//			html+="<td style='display:none'>"+getSelectValue(item.feeType,feeTypes)+"</td>";
			html+="<td style='display:none'>"+item.basicCost+"</td>";
			html+="<td style='display:none'>"+item.additionCost+"</td>";
			html+="<td style='display:none'>"+item.otherCost+"</td>";
			html+="<td style='display:none'>"+JY.Object.notEmpty(item.unitprice)+"</td>";
			html+="<td style='display:none'>"+JY.Object.notEmpty(item.totalFee)+"</td>";
			if(1==isHide){
				html+="<td><a style='cursor:pointer;' onclick='deliveryClick(&apos;"+JY.Object.notEmpty(item.id)+"&apos;)'>"+JY.Object.notEmpty(item.okNum)+"</a></td>"; //JY.Object.notEmpty(item.okNum)+"
				html+="<td>"+JY.Object.notEmpty(item.mateNum)+"</td>";
				html+="<td>"+JY.Object.notEmpty(item.stockNum)+"</td>";
				if(item.status==1){
					html+="<td class='center hidden-480'><span class='label label-sm label-success'>正常</span></td>";
	       		}else if(item.status==2){
	       			html+="<td class='center hidden-480'><span class='label label-sm label-success'>处理中</span></td>";
	       		}else{
	       			html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>无效</span></td>";
	       		} 
				html+="<td>"+JY.Object.notEmpty(item.description)+"</td>"
				html+="<td>";
				if(item.status!=2){
					html+="<button class='dropbtn' onclick='splitAll(&apos;"+itemNum+"&apos;)' title='匹配' href='#'>匹配</button>";		
				}
			}else if (0==isHide){
				html+="<td>"+JY.Object.notEmpty(item.description)+"</td>";
				html+="<td>";
				html+="<a class='aBtnNoTD' onclick='editDetail(&apos;"+itemNum+"&apos;)' title='编辑' href='#'><i class='icon-edit color-blue bigger-120'></i></a>";
				html+="<a class='aBtnNoTD' onclick='delDetail(&apos;"+itemNum+"&apos;)' title='删除' href='#'><i class='icon-remove-sign color-red bigger-120'></i></a>";
			}else{
				html+="<td>"+JY.Object.notEmpty(item.description)+"</td>";
				html+="<td>";
			}
			html+="<input type='hidden' name='items'id='item"+itemNum+"' value='"+itemNum+"' mdCode='"+JY.Object.notEmpty(item.mdCode)+"' mdtlCode='"+JY.Object.notEmpty(item.mdtlCode)+"' gMaterial='"
			+JY.Object.notEmpty(item.gMaterial)+"' gWeight='"+JY.Object.notEmpty(item.gWeight)+"' cut='"+JY.Object.notEmpty(item.cut)+"' circel='"+JY.Object.notEmpty(item.circel)+"' dWeight='"+JY.Object.notEmpty(item.dWeight)
			+"' dClarity='"+JY.Object.notEmpty(item.dClarity)+"' dColor='"+JY.Object.notEmpty(item.dColor)+"' weight='"+JY.Object.notEmpty(item.weight)+"' numbers='"
			+JY.Object.notEmpty(item.numbers)+"' feeType='"+JY.Object.notEmpty(item.feeType)+"' basicCost='"+JY.Object.notEmpty(item.basicCost)+"' status='"+JY.Object.notEmpty(item.status)+"' additionCost='"
			+JY.Object.notEmpty(item.additionCost)+"' otherCost='"+JY.Object.notEmpty(item.otherCost)+"' unitprice='"+JY.Object.notEmpty(item.unitprice)+"' totalFee='"+JY.Object.notEmpty(item.totalFee)
			+"' ids='"+JY.Object.notEmpty(item.id)+"' description='"+JY.Object.notEmpty(item.description)+"'okNum='"+JY.Object.notEmpty(item.okNum)+"'list='"+item.list.toString()
			+"' lackNum='"+JY.Object.notEmpty(item.lackNum)+"' mateNum='"+JY.Object.notEmpty(item.mateNum)+"'beforeId='"+JY.Object.notEmpty(item.beforeId)+"'stockNum='"+JY.Object.notEmpty(item.stockNum)+"' >";			
			html+="</td>";
			html+="</tr>";
			itemNum++;
		}
		if(1==isHide){
			$("#splitTable1").append(html);
		}else{
			$("#orderTable").append(html);
		}
		initFoot(isHide);
	}
}
/*
 * 查看
 */
function find(id){
	//加载dialog
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/scm/purorder/find',{id:id},function(data){ 
		setForm(data,2);
		viewInfo("orderDiv",null,768,1028,null,null);
	});
}
/*
 * 修改
 */
function edit(id){
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/scm/purorder/find',{id:id},function(data){
	    setForm(data,0);   
	    addInfo("orderDiv","修改",function(type){
	    	if(JY.Validate.form("orderForm")){
				 var that =$(this);
				 var id=$("#orderForm input[name$='id']").val();
				 var orderNo=$("#orderForm input[name$='orderNo']").val();
				 var totalNum=$("#orderForm input[name$='totalNum']").val();
//				 var weight=$("#orderForm input[name$='weight']").val();
//				 var totalFee=$("#orderForm input[name$='totalFee']").val();
				 var weight="";
				 var totalFee="";
				 var arrivalDate=new Date($("#orderForm input[name$='arrivalDate']").val());
				 var description=$("#orderForm textarea[name$='description']").val();
				 var orgId=$("#orderForm input[name$='orgId']").val();
				 var orderType=$("#orderForm input[name$='orderType']").val();
				 setItemArry("#orderTable");
				 var params ={id:id,orderNo:orderNo,totalNum:totalNum,weight:weight,totalFee:totalFee,arrivalDate:arrivalDate,description:description,orgId:orgId,orderType:orderType,status:type,items:itemArry};								 	 
				 $.ajax({type:'POST',url:jypath+'/scm/purorder/update',data:JSON.stringify(params),dataType:'json',contentType:"application/json",success:function(data,textStatus){  			        	 
					 	if(data.res==1){
			        		that.dialog("close");      
			        		JY.Model.info(data.resMsg,function(){search();});
			        	}else{
			        		 JY.Model.error(data.resMsg);
			        	}     	
			         }
			     });
	    	}
	    });
	});
}
/*
 * 删除
 */
function del(id){
	JY.Model.confirm("确认删除吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/purorder/del',{id:id},function(data){
			JY.Model.info(data.resMsg,function(){search();});
		});
	});
}
/*
 * 审核
 */
function check(id){
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/scm/purorder/find',{id:id},function(data){ 
		setForm(data,2);
		checkInfo("orderDiv","订单审核",function(type){
			var that =$(this);
			JY.Model.confirm(type=="2"?"确认审核通过吗？":"确认审核不通过吗？",function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/purorder/check',{id:id,status:type},function(data){
					if(data.res==1){
						that.dialog("close");  
						JY.Model.info(data.resMsg,function(){search();});
					}else{
						JY.Model.error(data.resMsg);
					}
				});
			});
		});
	});
}
/**
 *提交 
 */
function submit(id){
	JY.Model.confirm("确认提交吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/purorder/check',{id:id,status:1},function(data){
			JY.Model.info(data.resMsg,function(){search();});
		});
	});
}
/**
 *反审
 */
function uCheck(id){
	JY.Model.confirm("确认反审核吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/purorder/check',{id:id,status:0},function(data){
			JY.Model.info(data.resMsg,function(){search();});
		});
	});
}
/**
 *拆分 
 */
function split(id){
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/scm/purorder/findSplit',{id:id},function(data){
		var l= data.obj;
		if(l.orgId==curUser.orgId){
			JY.Model.info("不能拆分该单位订单");
			return;
		}
	    setForm(data,1);   
	    viewInfo("splitDiv","拆分",768,1028,null,function(type){
	    	var that =$(this);
			JY.Model.confirm("确认拆分吗？",function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/purorder/split',{id:id,label:type},function(data){
					if(data.res==1){
						that.dialog("close");  
						JY.Model.info(data.resMsg,function(){search();});
					}else{
						JY.Model.error(data.resMsg);
					}
				});
			});
	    	
	    });
	});
}

/**
 * 悬浮层 start
 */
var splitsArry=new Array();
var itemArry =new Array();
var itemNum=1;
function cleanForm(){
	itemArry =new Array();
	itemNum=1;
	JY.Tags.cleanForm("orderForm");
	JY.Tags.cleanForm("itemDiv");
	$("#orderTable tbody").empty();
	$("#grid-table tbody").empty();
	$("#splitTable1 tbody").empty();
	$("#orderDiv2 span").text("");
}
function cleanBaseForm(){
	JY.Tags.cleanForm("baseForm");
	$("#baseForm input[name$='orgId']").val("");
	setBaseData();
}
function setBaseData(){
	var date=new Date;
	var year=date.getFullYear(); 
	$("#dateRender1").val(year+"/1/1")
	$("#dateRender2").val(year+"/12/31")
}
function cleanItemForm(){
	JY.Tags.cleanForm("itemDiv");
	$("#splitTable2 tbody").empty();
	$("#splitTable3 tbody").empty();
	$("#moTable tbody").empty();
	$("#moTable1 tbody").empty();
	$("#lblCheckbox").prop("checked", false)
	JY.Tags.cleanForm("orderDiv");
	getselect(gMaterials,"gMaterialSelect1");
	getselect(gWeights,"gWeightSelect1");
	getselect(dClaritys,"dClaritySelect1");
	getselect(dColors,"dColorSelect1");
	getselect(gWeights,"weightSelect1");
	getselect(dWeights,"dWeightSelect1");
	getselect(feeTypes,"feeTypeSelect1");
}
function cleanItemForm1(){
	JY.Tags.cleanForm("orderDiv1");
	getselect(gMaterials,"gMaterialSelect1");
	getselect(gWeights,"gWeightSelect1");
	getselect(dClaritys,"dClaritySelect1");
	getselect(dColors,"dColorSelect1");
	getselect(gWeights,"weightSelect1");
	getselect(dWeights,"dWeightSelect1");
	getselect(feeTypes,"feeTypeSelect1");
}
function search(){
	$("#searchBtn").trigger("click");
}
function setItemArry(forms){
	itemArry =new Array();
	var nums = $(forms+" input[name='items']").map(function(){return $(this).val();}).get().join(",");
	if(JY.Object.notNull(nums)){
		var numList=nums.split(",");
		for(var i=0;i<numList.length;i++){
			var num=numList[i];
			var mdCode=$("#item"+num).attr("mdCode");
			var mdtlCode=$("#item"+num).attr("mdtlCode");
			var gMaterial=$("#item"+num).attr("gMaterial");
			var gWeight=$("#item"+num).attr("gWeight");
			var dWeight=$("#item"+num).attr("dWeight");
			var dClarity=$("#item"+num).attr("dClarity");
			var dColor=$("#item"+num).attr("dColor");
			var weight=$("#item"+num).attr("weight");
			var numbers=$("#item"+num).attr("numbers");
			var feeType=$("#item"+num).attr("feeType");
			var basicCost=$("#item"+num).attr("basicCost");
			var additionCost=$("#item"+num).attr("additionCost");
			var otherCost=$("#item"+num).attr("otherCost");
			var unitprice=$("#item"+num).attr("unitprice");
			var totalFee=$("#item"+num).attr("totalFee");
			var status=$("#item"+num).attr("status");
			var ids=$("#item"+num).attr("ids");
			var description=$("#item"+num).attr("description");
			var beforeId=$("#item"+num).attr("beforeId");
			var circel=$("#item"+num).attr("circel");
			var cut=$("#item"+num).attr("cut");
			var item={mdCode:mdCode,mdtlCode:mdtlCode,gMaterial:gMaterial,gWeight:gWeight,dWeight:dWeight,dClarity:dClarity,dColor:dColor,
					weight:weight,numbers:numbers,feeType:feeType,basicCost:basicCost,additionCost:additionCost,otherCost:otherCost,unitprice:unitprice,
					totalFee:totalFee,status:status,id:ids,description:description,beforeId:beforeId,circel:circel,cut:cut};
			
			itemArry.push(item);
		}
	}	
}
/*******组件：选择组织机构 start*******/
var preisShow=false;//窗口是否显示
function showOrgComp() {
	if(preisShow){
		hideOrgComp();
	}else{
		var obj = $("#orgInput");
		var offpos = $("#orgInput").position();
		$("#orgContentList").css({left:offpos.left+"px",top:offpos.top+obj.heith+"px"}).slideDown("fast");	
		preisShow=true;
	}
}
function hideOrgComp(){
	$("#orgContentList").fadeOut("fast");
	preisShow=false;
}
function emptyOrgComp(){
	$("#orgInput").prop("value","");
	$("#orderForm input[name$='orgId']").prop("value",'');
	$("#orderForm input[name$='orgName']").prop("value",'');
}
function initOrgSelectData(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getOrgTree',null,function(data){
		//设置
		$.fn.zTree.init($("#selectOrgTree"),{view:{dblClickExpand:false,selectedMulti:false,nameIsHTML:true},data:{simpleData:{enable: true}},callback:{onClick:clickPreOrg}},data.obj);
		var treeObj = $.fn.zTree.getZTreeObj("selectOrgTree");
		var nodes = treeObj.getNodes();
		treeObj.expandNode(nodes[0],true,false,false,false);
	});
	
}
function clickPreOrg(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("selectOrgTree"),
	nodes = zTree.getSelectedNodes(),v ="",n ="";	
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";//获取name值
		n += nodes[i].id + ",";	//获取id值
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (n.length > 0 ) n = n.substring(0, n.length-1);
	$("#orgInput").prop("value",v);
	$("#orderForm input[name$='orgId']").prop("value",n);
	$("#orderForm input[name$='orgName']").prop("value",v);
	//因为单选选择后直接关闭，如果多选请另外写关闭方法
	hideOrgComp();
}
/*******组件：选择组织机构 end*******/

/*******组件：选择组织机构 start*******/
function initOrgBaseData(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getOrgTree',{status:"1"},function(data){
		var setting={rootId:'baseOrgList',displayId:'baseOrg',data:data.obj,clickFn:function(node){
			$("#baseForm input[name$='orgId']").val(node.id);
		},isExpand:true,dataFormat:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}};
		$.jy.dropTree.init(setting);
	});
	
}
/*******组件：选择组织机构 end*******/


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
function print(){
	 var id = $("#orderForm input[name='id']").val();
	 var orderNo=$("#orderForm input[name='orderNo']").val();
	 $("#printDiv").load(jypath +'/scm/purorder/print?id='+id+'&type='+1,function(){
		 LODOP=getLodop();  
		 LODOP.PRINT_INIT("打印控件功能演示");
		 LODOP.ADD_PRINT_BARCODE(16,78,166,21,"128B",orderNo);
		 LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
		 LODOP.ADD_PRINT_HTM(0,0,"100%","100%",document.getElementById("testPrintdiv").innerHTML);
		 LODOP.PREVIEW();
	 })
}