var feeTypes=[];
var mdCodes=[];
var dColors=[];
var dClaritys=[];
var gMaterials=[];
var gWeights=[];
var dWeights=[];
var groups=[];
var cates=[];
var cuts=[];
$(function () {
	//下拉框
	JY.Dict.setSelect("selectStoreStatus","SCM_ORDER_STATUS",2,"全部");
	
	feeTypes=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"feeType",feeTypes);
	dColors=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_DATA_COLOR",dColors);
	dClaritys=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_DATA_CLARITY",dClaritys);
	gMaterials=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_PRO_GOLD_TYPE",gMaterials);
	gWeights=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_ATTR_GOLDWEIGHT",gWeights);
	dWeights=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"stoneSize",dWeights);
	groups=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_PRO_CATE_JEWELRY",groups);
	cates=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_PRO_CATE",cates);
	cuts=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_DATA_CUT",cuts);
	
	mdCodes=gridSelect1(jypath +'/scm/moudle/select',mdCodes);
	
	setBaseData();
	getbaseList();
	//增加回车事件
	$("#storeBaseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	//新加g
	$('#addBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		cleanForm();
		var userName=$("#userName").val();
		getselect(cates,"cate");
		getselect(groups,"group");
		$("#addDiv").removeClass('hide');
//		$("#orderForm input[name$='createName']").val(userName);
		setOrderForm();
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
			JY.Model.confirm("确认要审核选中的数据吗?",function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/purorder/checkBatch',{chks:chks.toString()},function(data){
					JY.Model.info(data.resMsg,function(){search();});
				});
			});		
		}	
	});
//	$('#btnId').on('click', function(e) {
//		getPaymentsList();
//		paymentLeng=1;
//		editInfo1 ("paymentsListDiv","付款",function(){
//			var that =$(this);
//			setPpaymentArry();
//			debugger
//			var owed=$("#owed").text();
//			if(owed<0){
//				JY.Model.info("实收金额大于应收金额");
//				return false;
//			}
//			var params={list:paymentArry};
//			 $.ajax({type:'POST',url:jypath+'/pos/payments/add',data:JSON.stringify(params),dataType:'json',contentType:"application/json",success:function(data,textStatus){  			        	 
//				 	if(data.res==1){
//		        		that.dialog("close");      
//		        		JY.Model.info(data.resMsg,function(){search();});
//		        	}else{
//		        		 JY.Model.error(data.resMsg);
//		        	}     	
//		         }
//		     });
//		});
//	});
	
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
	//自动补全
	var input={};
	$("#inputName").typeahead({
		source:function(longName,process){
			$.ajax({
				type:'POST',
				url:jypath+'/scm/franchisee/findLongNamePage',
				data:{longName:longName},
				dataType:'json',
				success:function(data,textStatus){
						input={};
						var array=[];
						 $.each(data.obj, function (index, ele) {
							 input[ele.longName] = ele.id;
	                         array.push(ele.longName);
	                     });
						 process(array);
				}
			});
		},
        items: 10,
        afterSelect: function (item) {
        	$("#itemForm input[name$='franchiseeId']").val(input[item]);
        	$("#itemForm input[name$='mdtlCodeName']").val("");
        	$("#itemForm input[name$='mdtlCode']").val("");
        },
        delay: 500,
        highlighter: function (item) {//item是用户输入
	        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')//这是将字符串安全格式化为正则表达式的源码
	            return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	                return '<strong style="color:red;">' + match + '</strong>'//将下拉列表中符合项中的LI的innerHTML相等部分加个strong标签
	            })
	        	        }
        
		
	});
	var mdInput={};
	$("#mdInput").typeahead({
		source:function(code,process){
			$.ajax({
				type:'POST',
				url:jypath+'/scm/moudle/findScmMoudleByCode',
				data:{code:code},
				dataType:'json',
				success:function(data,textStatus){
					mdInput={};
					var array=[];
					$.each(data.obj, function (index, ele) {
						mdInput[ele.code] = ele.id;
						array.push(ele.code);
					});
					process(array);
				}
			});
		},
        items: 10,
        afterSelect: function (item) {
        	$("#itemForm input[name$='mdCodeId']").val(mdInput[item]);
        },
        delay: 500,
        highlighter: function (item) {//item是用户输入
	        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')//这是将字符串安全格式化为正则表达式的源码
	        return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	        	return '<strong style="color:red;">' + match + '</strong>'//将下拉列表中符合项中的LI的innerHTML相等部分加个strong标签
	        })
	   }
	});
	$("#addRow").on('click', function(e) {
		cleanItemForm();
		$("#addDiv").removeClass('hide');
		$("#itemDiv input[name$='mdCode']").attr("readonly", false);
		$("#itemDiv input[name$='mdtlCodeName']").attr("readonly", false);
		var franchiseeId= $("#orderForm input[name$='franchiseeId']").val();
		if(franchiseeId!=null&&franchiseeId!=""){
			var franchiseeName= $("#orderForm input[name$='franchiseeName']").val();
			$("#itemDiv input[name$='franchiseeName']").attr("readonly", true);
			$("#itemDiv input[name$='franchiseeId']").val(franchiseeId);
			$("#itemDiv input[name$='franchiseeName']").val(franchiseeName);
		}else{
			$("#itemDiv input[name$='franchiseeName']").attr("readonly", false);
		}
		
		editInfo1 ("itemDiv","新增",function(){
			var mdCode=$("#itemForm input[name$='mdCode']").val();
			var mdCodeId=$("#itemForm input[name$='mdCodeId']").val();
			var mdtlCode=$("#itemForm input[name$='mdtlCode']").val();
			var mdtlCodeName=$("#itemForm input[name$='mdtlCodeName']").val();
			var gMaterial=$("#gMaterialSelect option:selected").val();
			var gWeight=$("#gWeightSelect option:selected").val();
			var dWeight=$("#dWeightSelect option:selected").val();
			var dClarity=$("#dClaritySelect option:selected").val();
			var dColor=$("#dColorSelect option:selected").val();
//			var weight=$("#weightSelect option:selected").val();
			var cut=$("#dCutSelect option:selected").val();
			var circel=$("#itemForm input[name$='circel']").val();
			var weight=$("#itemForm input[name$='weight']").val();
			var numbers=$("#itemForm input[name$='numbers']").val();
			var feeType=$("#feeTypeSelect option:selected").val();
			var basicCost=$("#itemForm input[name$='basicCost']").val();
			var additionCost=$("#itemForm input[name$='additionCost']").val();
			var otherCost=$("#itemForm input[name$='otherCost']").val();
			var unitprice=$("#itemForm input[name$='unitprice']").val();
			var totalFee=$("#itemForm input[name$='totalFee']").val();
			var status=$("#itemForm input[name$='status']").val();
			var description=$("#itemForm textarea[name$='description']").val();
			var status=$("#itemForm input[name$='status']").val();
			var franchiseeName=$("#itemForm input[name$='franchiseeName']").val();
			var franchiseeId=$("#itemForm input[name$='franchiseeId']").val();
			var stockNum=0;
			if(JY.Validate.form("itemForm")){
				JY.Ajax.doRequest(null,jypath +'/scm/product/findProductNumByMoCode',{mouCode:mdCode},function(data){
					stockNum=data.obj;
				});
				var html="";
				html+="<tr id='temtr"+itemNum+"' >";
				 html+="<td>"+ mdCode+"</td>";
				 html+="<td>"+getSelectValue(gMaterial,gMaterials)+"</td>";
				 html+="<td>"+getSelectValue(gWeight,gWeights)+"</td>";
				 html+="<td>"+JY.Object.notEmpty(circel)+"</td>"
				 html+="<td>"+getSelectValue(dWeight,dWeights)+"</td>";
				 html+="<td>"+getSelectValue(dClarity,dClaritys)+"</td>";
				 html+="<td>"+getSelectValue(dColor,dColors)+"</td>";
				 html+="<td>"+getSelectValue(cut,cuts)+"</td>";
//					html+="<td style='display:none'>"+JY.Object.notNumber(weight)+"</td>";
				 html+="<td>"+JY.Object.notNumber(numbers)+"</td>";
//					html+="<td>"+getSelectValue(feeType,feeTypes)+"</td>";
				 html+="<td>"+JY.Object.notEmpty(franchiseeName)+"</td>";
				 html+="<td>"+mdtlCodeName+"</td>";
				 html+="<td >"+JY.Object.notNumber(basicCost)+"</td>";
				 html+="<td>"+JY.Object.notNumber(additionCost)+"</td>";
				 html+="<td>"+JY.Object.notNumber(otherCost)+"</td>";
				 html+="<td>"+stockNum+"</td>";
				html+="<td>";
				html+="<div class='dropdown'>";
				html+="<button class='dropbtn'>操作</button>";
				html+="<div class='dropdown-content'>";
				html+="<a onclick='editDetail(&apos;"+itemNum+"&apos;)' title='编辑' href='#'>编辑</a>";	
//				html+="<a onclick='splitDetail(&apos;"+itemNum+"&apos;)' title='拆开' href='#'>拆开</a>";	
				html+="<a onclick='delDetail(&apos;"+itemNum+"&apos;)' title='删除' href='#'>删除</a>";
				html+="</div></div>";
				html+="<input type='hidden' name='items'id='item"+itemNum+"' value='"+itemNum+"'mdCode='"+mdCodeId+"' mdtlCode='"+mdtlCode+"' gMaterial='"+gMaterial+"' gWeight='"+gWeight+"' dWeight='"+dWeight
				+"' dClarity='"+dClarity+"' dColor='"+dColor+"' weight='"+weight+"' numbers='"+numbers+"' feeType='"+feeType+"' mdtlCodeName='"+mdtlCodeName+"' basicCost='"+basicCost
				+"' additionCost='"+additionCost+"' otherCost='"+otherCost+"' unitprice='"+unitprice+"' totalFee='"+totalFee+"' status='1' ids='' franchiseeId='"+franchiseeId
				+"'franchiseeName='"+franchiseeName+"' description='"+description+"'  beforeId='' circel='"+circel+"'cut='"+cut+"'>";		
				html+="</td>";
				html+="</tr>";
				itemNum++;
				$("#itemsTable").append(html);
				getTotNum();
				initFoot();
				$(this).dialog("close");
			 }
		});
	});
	/**
	 * 匹配方法
	 */
	$("#addRowList").on('click', function(e) {
		setDetialList();
	});
	
	var CodeMap={};
	/**
	 * 工厂款号自动补全
	 */
	$("#queryMouCode").typeahead({
        source: function (query, process) {
        	var supplierCode;
        	var mdCodeId;
        	if($("#itemForm input[name$='franchiseeName']").val()!=""){
        		supplierCode=$("#itemForm input[name$='franchiseeId']").val();
        	}
        	if($("#itemForm input[name$='mdCode']").val()!=""){
        		mdCodeId=$("#itemForm input[name$='mdCodeId']").val();
        	}
        	$.ajax({
				type:'POST',
				url:jypath+'/scm/moudle/detail/queryModCode',
				data:{suppmouCode:query,supplierCode:supplierCode,moudleid:mdCodeId},
				dataType:'json',
				success:function(result,textStatus){ 
					CodeMap = {};
					var arr= [];
					 $.each(result, function (index, ele) {
						 CodeMap[ele.suppmouCode] = ele.id;
						 arr.push(ele.suppmouCode);
                     });
					process(arr);
				}
			});
        	
        },
        items: 10,
        afterSelect: function (value) {
        	var moudtlCode= CodeMap[value];
        	$("#itemForm input[name$='mdtlCode']").val(moudtlCode);
        	JY.Ajax.doRequest(null,jypath +'/scm/moudle/detail/find',{id:moudtlCode},function(data){
        		l=data.obj;
        		$("#itemForm input[name$='franchiseeName']").val(l.supplierName);
        		$("#itemForm input[name$='franchiseeId']").val(l.supplierCode);
        		$("#itemForm input[name$='basicCost']").val(l.laborCost);
        		$("#itemForm input[name$='otherCost']").val(l.addLaborCost);
        		var read=$("#itemForm input[name$='mdCode']").attr("readonly");
        		if(!read=="readonly"||typeof(read) == "undefined"){
        			$("#itemForm input[name$='mdCodeId']").val(l.moudleid);
        			$("#itemForm input[name$='mdCode']").val(getSelectValue(l.moudleid,mdCodes));
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
	$('#findsBtn').on('click', function(e) {
		$("#addDiv").addClass('hide') 
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
	$('#editsBtn').on('click', function(e) {
		$("#addDiv").addClass('hide') 
		var chks =[];    
		$('#baseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{
			edit(chks[0]);
		}
	});
	$('#checkBtn').on('click', function(e) {
		$("#addDiv").addClass('hide') 
		var chks =[];    
		$('#baseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{
			check(chks[0]);
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
	$('#uCheckBtn').on('click', function(e) {
		$("#addDiv").addClass('hide') 
		var chks =[];    
		$('#baseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{
			uCheck(chks[0]);
		}
	});
});
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
function getselect(selects,opts){
	$("#"+opts).empty();
	var op="<option value=''>" +"请选择" + "</option>";
	$.each(selects,function(i, e) {
		op+="<option value='" + e.key + "'>" + e.text + "</option>"
    });
	$("#"+opts).append(op);
}
function setDetialList(){
	var group= $("#group").val();
	var cate= $("#cate").val();
	var term= $("#term").val();
	var orderType= $("#baseForm input[name='orderType']").val();
	debugger
	if(orderType=="1"){
		orderType="0";
	}else{
		orderType="2";
	}
	JY.Ajax.doRequest(null,jypath +'/scm/purorder/findLackDetail',{group:group,cate:cate,term:term,orderType:orderType},function(data){
		var items=data.obj.items;
		if(items.length>0){
		var array = new Array();
		var list=new Array();
		var nums = $("#itemsTable input[name='items']").map(function(){return $(this).val();}).get().join(",");
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
		if(data.resMsg!=""){
			JY.Model.info(data.resMsg);
		}
		setItems(array);
		getTotNum();
		initFoot();
		}else{
			JY.Model.info("没有找到相关数据");
		}
	});
}
function setOrderForm(){
//	JY.Ajax.doRequest(null,jypath +'/scm/purchase/findChase',null,function(data){
//		setForm(data,1);
		var userName=$("#userName").val();
		$("#orderForm span[name='createName']").text(userName);
		addInfo("orderDiv","新增",function(type){
			if(JY.Validate.form("orderForm")){
				 var that =$(this);
				 var id=$("#orderForm input[name$='id']").val();
				 var orderNo=$("#orderForm input[name$='orderNo']").val();
				 var totalNum=$("#orderForm input[name$='totalNum']").val();
				 var weight=$("#orderForm input[name$='weight']").val();
				 var totalFee=$("#orderForm input[name$='totalFee']").val();
				 var arrivalDate=new Date($("#orderForm input[name$='arrivalDate']").val());
				 var orderType=$("#baseForm input[name$='orderType']").val();
				 var orgId=$("#orderForm input[name$='orgId']").val();
				 var description=$("#orderForm textarea[name$='description']").val();
				 var p= setItemArry("#itemsTable");
				 if (p){
					 return;
				 }
				 var params ={id:id,orderNo:orderNo,totalNum:totalNum,weight:weight,totalFee:totalFee,arrivalDate:arrivalDate,description:description,orderType:orderType,orgId:orgId,status:type,items:itemArry};								 	 
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
//	});
	
}

function search(){
	$("#searchBtn").trigger("click");
}

/**组装并加载table数据列表**/
function getbaseList(init){
	if(init==1)$("#baseForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("baseForm",jypath +'/scm/purchase/findByPage',null,function(data){
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
            		 if(l.status==1)statusStr="待审核";else if(l.status==2)statusStr="已审核";else if(l.status==3)statusStr="已完成"; else if(l.status==4)statusStr="已拒绝";else if(l.status==9)statusStr="已删除"; else statusStr="草稿";
            		 html+="<tr>";
            		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
            		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
            		 html+="<td class='center'><a style='cursor:pointer;' onclick='find(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.orderNo)+"</a></td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.orgName)+"</td>"
            		 html+="<td class='left'>"+JY.Object.notEmpty(l.totalNum)+"</td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.collect)+"</td>";
//            		 html+="<td class='left hidden-480' >"+JY.Object.notEmpty(l.weight)+"</td>";
//            		 html+="<td class='left hidden-480'>"+JY.Object.notEmpty(l.totalFee)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.arrivalDate,"yyyy-MM-dd")+"</td>";
//            		 html+="<td class='center hidden-480'>"+labelStr+"</td>";
            		 html+="<td class='center hidden-480'>"+statusStr+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.operatorName)+"</td>"
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.franchiseeName)+"</td>"
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.checkName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.checkTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.createName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.createTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
//            		 if(l.status==1){
//            			 html+=JY.Tags.setFunction1(l.id,permitBtn,'edit,find,del,submit,uCheck,dowBut,edits,');
//            		 }else if(l.status==2){
//            			 html+=JY.Tags.setFunction1(l.id,permitBtn,'check,edit,find,del,submit,edits,');
//            		 }else if(l.status==3){
//            			 html+=JY.Tags.setFunction1(l.id,permitBtn,'check,edit,find,del,uCheck,submit,dowBut,edits,');
//            		 }else if(l.status==0||l.status==4){
//            			 html+=JY.Tags.setFunction1(l.id,permitBtn,'edit,find,check,uCheck,dowBut,');
//            		 }else{
//            			 html+=JY.Tags.setFunction(l.id,permitBtn);
//            		 }
            		 html+="</tr>";		 
            	 } 
        		 $("#baseTable tbody").append(html);
        		 JY.Page.setPage("baseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='14' class='center'>没有相关数据</td></tr>";
        		$("#baseTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
 	 
    		 JY.Model.loadingClose();
	 });
}

function viewInfo(id,title,fn){	
	$("#"+id).removeClass('hide').dialog({resizable:false,modal:true,height:768,width:1024,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"查看")+"</h4></div>",title_html:true,
		buttons:[
		         {html: "<i class='icon-ok bigger-110'></i>&nbsp;打印预览","class":"btn btn-primary btn-xs",click:function(){print()}},
		         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(fn) == 'function'){fn.call(this);}}}
		]});
	$("#addDiv").addClass('hide') ;
	JY.Tools.formReadonly("orderForm",true);
	$("#opeTr").show(); 
	$("#searchUserBtn").hide();
//	$("#operatorName").attr("jyValidate","");
}
function editInfo(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,modal:true,height:768,width:1024,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
//	JY.Tools.formReadonly("orderForm",false);
	$("#description").attr("readonly", false);
	$("#opeTr").hide();
	$("#operatorName").attr("jyValidate","");

}
function addInfo(id,title,savefn,cancelfn){
	 $("#"+id).removeClass('hide').dialog({
	    	resizable:false,
	    	height:768,
	    	width:1024,
	    	modal:true,
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
//	$("#addDiv").removeClass('hide');
	$("#description").attr("readonly", false);
	$("#opeTr").hide();
	$("#operatorName").attr("jyValidate","");
}
function editInfo1(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,modal:true,height:570,width:720,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
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
var itemNum;
function setForm(data,insert){
	var l=data.obj;
	var isForm="";
	isForm="#orderForm"
	if(insert!=1 ||typeof(insert) == "undefined"){
		$(isForm+" input[name$='orgId']").prop("value",JY.Object.notEmpty(l.orgId));
		$(isForm+" input[name$='orgName']").prop("value",JY.Object.notEmpty(l.orgName));
		$("#orgInput").prop("value",JY.Object.notEmpty(l.orgName));
		$(isForm+" input[name$='id']").val(JY.Object.notEmpty(l.id));
		$(isForm+" input[name$='orderNo']").val(JY.Object.notEmpty(l.orderNo));
		$(isForm+" input[name$='totalNum']").val(JY.Object.notEmpty(l.totalNum));
		$(isForm+" input[name$='weight']").val(JY.Object.notEmpty(l.weight));
		$(isForm+" input[name$='totalFee']").val(JY.Object.notEmpty(l.totalFee));
		$(isForm+" input[name$='arrivalDate']").val(new Date(l.arrivalDate).Format("yyyy/MM/dd"));
		$(isForm+" textarea[name$='description']").val(JY.Object.notEmpty(l.description));
		$(isForm+" input[name$='franchiseeId']").val(JY.Object.notEmpty(l.franchiseeId));
		$(isForm+" input[name$='franchiseeName']").val(JY.Object.notEmpty(l.franchiseeName));
		$(isForm+" span[name='createName']").text(JY.Object.notEmpty(l.createName));
		$(isForm+" span[name='checkName']").text(JY.Object.notEmpty(l.checkName));
		$(isForm+" span[name='updateName']").text(JY.Object.notEmpty(l.updateName));
		$(isForm+" span[name='createTime']").text(JY.Object.notEmpty(l.createTime)==""?"":new Date(l.createTime).Format("yyyy/MM/dd hh:mm:ss"));
		$(isForm+" span[name='updateTime']").text(JY.Object.notEmpty(l.updateTime)==""?"":new Date(l.updateTime).Format("yyyy/MM/dd hh:mm:ss"));
		$(isForm+" span[name='checkTime']").text(JY.Object.notEmpty(l.checkTime)==""?"":new Date(l.checkTime).Format("yyyy/MM/dd hh:mm:ss"));
		
	}
	if(insert==2){
		$("#operatorName").val(l.operatorName);
		$("#operatorId").val(l.operatorId);
	}
	var items=l.items;
	setItems(items,insert);
}
function setForm1(data){
	var l=data.obj;
	var isForm="";
	isForm="#pOrderDiv"
		$(isForm+" input[name$='orgId']").prop("value",JY.Object.notEmpty(l.orgId));
		$(isForm+" input[name$='orgName']").prop("value",JY.Object.notEmpty(l.orgName));
		$("#orgInput").prop("value",JY.Object.notEmpty(l.orgName));
		$(isForm+" input[name$='id']").val(JY.Object.notEmpty(l.id));
		$(isForm+" input[name$='orderNo']").val(JY.Object.notEmpty(l.orderNo));
		$(isForm+" input[name$='totalNum']").val(JY.Object.notEmpty(l.totalNum));
		$(isForm+" input[name$='weight']").val(JY.Object.notEmpty(l.weight));
		$(isForm+" input[name$='totalFee']").val(JY.Object.notEmpty(l.totalFee));
		$(isForm+" input[name$='arrivalDate']").val(new Date(l.arrivalDate).Format("yyyy/MM/dd"));
		$(isForm+" textarea[name$='description']").val(JY.Object.notEmpty(l.description));
		$(isForm+" input[name$='franchiseeId']").val(JY.Object.notEmpty(l.franchiseeId));
		$(isForm+" input[name$='franchiseeName']").val(JY.Object.notEmpty(l.franchiseeName));
		$(isForm+" span[name='createName']").text(JY.Object.notEmpty(l.createName));
		$(isForm+" span[name='checkName']").text(JY.Object.notEmpty(l.checkName));
		$(isForm+" span[name='updateName']").text(JY.Object.notEmpty(l.updateName));
		$(isForm+" span[name='createTime']").text(JY.Object.notEmpty(l.createTime)==""?"":new Date(l.createTime).Format("yyyy/MM/dd hh:mm:ss"));
		$(isForm+" span[name='updateTime']").text(JY.Object.notEmpty(l.updateTime)==""?"":new Date(l.updateTime).Format("yyyy/MM/dd hh:mm:ss"));
		$(isForm+" span[name='checkTime']").text(JY.Object.notEmpty(l.checkTime)==""?"":new Date(l.checkTime).Format("yyyy/MM/dd hh:mm:ss"));
		
}
function setItems(items,insert){
	var html="";
	for(var i=0;i<items.length;i++){
		var item=items[i];
		html+="<tr id='temtr"+itemNum+"' >";
		html+="<td>"+ getSelectValue(item.mdCode,mdCodes) +"</td>";
		html+="<td>"+getSelectValue(item.gMaterial,gMaterials)+"</td>";
		html+="<td>"+getSelectValue(item.gWeight,gWeights)+"</td>";
		html+="<td>"+JY.Object.notEmpty(item.circel)+"</td>"
		html+="<td>"+getSelectValue(item.dWeight,dWeights)+"</td>";
		html+="<td>"+getSelectValue(item.dClarity,dClaritys)+"</td>";
		html+="<td>"+getSelectValue(item.dColor,dColors)+"</td>";
		html+="<td>"+getSelectValue(item.cut,cuts)+"</td>";
//		html+="<td style='display:none'>"+JY.Object.notNumber(item.weight)+"</td>";
		html+="<td>"+JY.Object.notNumber(item.numbers)+"</td>";
//		html+="<td>"+getSelectValue(item.feeType,feeTypes)+"</td>";
		html+="<td>"+JY.Object.notEmpty(item.franchiseeName)+"</td>";
		html+="<td>"+JY.Object.notEmpty(item.mdtlCodeName)+"</td>";
		html+="<td >"+JY.Object.notNumber(item.basicCost)+"</td>";
		html+="<td>"+JY.Object.notNumber(item.additionCost)+"</td>";
		html+="<td>"+JY.Object.notNumber(item.otherCost)+"</td>";
//		html+="<td style='display:none'>"+JY.Object.notEmpty(item.unitprice)+"</td>";
//		html+="<td style='display:none'>"+JY.Object.notEmpty(item.totalFee)+"</td>";
		html+="<td>"+JY.Object.notNumber(item.stockNum)+"</td>";		
		html+="<td>";
		if(insert!=2 ||typeof(insert) == "undefined"){
			html+="<div class='dropdown'>";
			html+="<button class='dropbtn'>操作</button>";
			html+="<div class='dropdown-content'>";
			html+="<a onclick='editDetail(&apos;"+itemNum+"&apos;)' title='编辑' href='#'>编辑</a>";	
			if(insert!=3){
				html+="<a onclick='splitDetail(&apos;"+itemNum+"&apos;)' title='拆开' href='#'>拆开</a>";	
			}
			html+="<a onclick='delDetail(&apos;"+itemNum+"&apos;)' title='删除' href='#'>删除</a>";
			html+="</div></div>";
		}
		html+="<input type='hidden' name='items'id='item"+itemNum+"' value='"+itemNum+"'mdCode='"+JY.Object.notEmpty(item.mdCode)+"' mdtlCode='"+JY.Object.notEmpty(item.mdtlCode)
		+"' mdtlCodeName='"+JY.Object.notEmpty(item.mdtlCodeName)+"' gMaterial='"+JY.Object.notEmpty(item.gMaterial)+"' gWeight='"+JY.Object.notEmpty(item.gWeight)+"' dWeight='"
		+JY.Object.notEmpty(item.dWeight) +"' dClarity='"+JY.Object.notEmpty(item.dClarity)+"' dColor='"+JY.Object.notEmpty(item.dColor)+"' weight='"+JY.Object.notEmpty(item.weight)
		+"' numbers='"+JY.Object.notNumber(item.numbers)+"' feeType='"+JY.Object.notEmpty(item.feeType)+"'franchiseeName='"+JY.Object.notEmpty(item.franchiseeName)+"' basicCost='"
		+JY.Object.notNumber(item.basicCost) +"' additionCost='"+JY.Object.notEmpty(item.additionCost)+"' otherCost='"+JY.Object.notEmpty(item.otherCost)+"' unitprice='"
		+JY.Object.notNumber(+item.unitprice) +"' totalFee='"+JY.Object.notEmpty(item.totalFee)+"' status='1' ids='"+JY.Object.notEmpty(item.id)+"' franchiseeId='"
		+JY.Object.notEmpty(item.franchiseeId)+"' description='"+JY.Object.notEmpty(item.description)+"' beforeId='"+JY.Object.notEmpty(item.beforeId)
		+"'circel='"+JY.Object.notEmpty(item.circel)+"'cut='"+JY.Object.notEmpty(item.cut)+"' stockNum='"+JY.Object.notNumber(item.stockNum)+"' >";		
		html+="</td>";
		html+="</tr>";
		itemNum++;
	}
	$("#itemsTable").append(html);
	getTotNum();
	initFoot();
}
function getTotNum(){
	var nums = $("#itemsTable input[name='items']").map(function(){return $(this).val();}).get().join(",");
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
	initFoot();
}
function splitDetail(num){
	editItemsTable(num,1)
}
function editDetail(num){
	editItemsTable(num,0)
}
function editItemsTable(num,isAddRow){
	cleanItemForm();
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
	var franchiseeId=$("#item"+num).attr("franchiseeId");
	var franchiseeName=$("#item"+num).attr("franchiseeName");
	var beforeId=$("#item"+num).attr("beforeId");
	var cut=$("#item"+num).attr("cut");
	var circel=$("#item"+num).attr("circel");
	var stockNum=$("#item"+num).attr("stockNum");
	$("#itemDiv input[name$='mdCode']").val(getSelectValue(mdCode,mdCodes));
	$("#itemDiv input[name$='mdCodeId']").val(mdCode);
	$("#itemDiv input[name$='mdCode']").attr("readonly", true);
	$("#itemDiv input[name$='mdtlCode']").val(JY.Object.notEmpty(mdtlCode));
	if((JY.Object.notEmpty(mdtlCode)=="" && JY.Object.notEmpty(franchiseeId)=="")||isAddRow==1){
		$("#itemDiv input[name$='mdtlCodeName']").attr("readonly", false);
	}else{
		$("#itemDiv input[name$='mdtlCodeName']").attr("readonly", true);
	}
	$("#itemDiv input[name$='mdtlCodeName']").val(JY.Object.notEmpty(mdtlCodeName));
	$("#gMaterialSelect").val(gMaterial);
	$("#gWeightSelect").val(gWeight);
	$("#dWeightSelect").val(dWeight);
	$("#dClaritySelect").val(dClarity);
	$("#dColorSelect").val(dColor);
//	$("#weightSelect").val(weight);
	$("#dCutSelect").val(cut);
	$("#itemDiv input[name$='circel']").val(circel)
	$("#itemDiv input[name$='numbers']").val(numbers1);
	$("#itemDiv input[name$='weight']").val(weight);
//	$("#feeTypeSelect").val(feeType);
	if(feeType=="1"){
		$("#feeType1").prop("checked",true);
	}else{
		$("#feeType2").prop("checked",true);
	}
	$("#itemDiv input[name$='basicCost']").val(JY.Object.notEmpty(basicCost));
	$("#itemDiv input[name$='additionCost']").val(JY.Object.notEmpty(additionCost));
	$("#itemDiv input[name$='otherCost']").val(JY.Object.notEmpty(otherCost));
	$("#itemDiv input[name$='unitprice']").val(JY.Object.notEmpty(unitprice));
	$("#itemDiv input[name$='totalFee']").val(JY.Object.notEmpty(totalFee));
//	$("#itemDiv input[name$='status']").val(status);
	$("#itemDiv textarea[name$='description']").val(JY.Object.notEmpty(description));
	$("#itemDiv input[name$='franchiseeId']").val(JY.Object.notEmpty(franchiseeId));
	$("#itemDiv input[name$='franchiseeName']").val(JY.Object.notEmpty(franchiseeName));
	if(JY.Object.notEmpty(franchiseeName)==""||isAddRow==1){
		$("#itemDiv input[name$='franchiseeName']").attr("readonly", false);
	}else{
		$("#itemDiv input[name$='franchiseeName']").attr("readonly", true);
	}
	editInfo1 ("itemDiv",isAddRow==1?"拆开":"修改明细",function(){
		var mdtlCode=$("#itemForm input[name$='mdtlCode']").val();
		var mdtlCodeName=$("#itemForm input[name$='mdtlCodeName']").val();
		var gMaterial=$("#gMaterialSelect option:selected").val();
		var gWeight=$("#gWeightSelect option:selected").val();
		var dWeight=$("#dWeightSelect option:selected").val();
		var dClarity=$("#dClaritySelect option:selected").val();
		var dColor=$("#dColorSelect option:selected").val();
		var cut=$("#dCutSelect option:selected").val();
		var circel=$("#itemForm input[name$='circel']").val()
//		var weight=$("#weightSelect option:selected").val();
		var weight=$("#itemForm input[name$='weight']").val();
		var numbers=$("#itemForm input[name$='numbers']").val();
//		var feeType=$("#feeTypeSelect option:selected").val();
		var feeType=$("#itemForm input[name$='feeType']:checked").val();
		var basicCost=$("#itemForm input[name$='basicCost']").val();
		var additionCost=$("#itemForm input[name$='additionCost']").val();
		var otherCost=$("#itemForm input[name$='otherCost']").val();
		var unitprice=$("#itemForm input[name$='unitprice']").val();
		var totalFee=$("#itemForm input[name$='totalFee']").val();
		var status=$("#itemForm input[name$='status']").val();
		var description=$("#itemForm textarea[name$='description']").val();
//		var status=$("#itemForm input[name$='status']").val();
		var franchiseeName=$("#itemForm input[name$='franchiseeName']").val();
		var franchiseeId=$("#itemForm input[name$='franchiseeId']").val();
		
		if(JY.Validate.form("itemDiv")){
//			 $("#item"+num).attr("mdCode",mdCode);
			if(isAddRow==0){
				$("#item"+num).attr("mdtlCode",mdtlCode);
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
				$("#item"+num).attr("status",status);
				$("#item"+num).attr("description",description);
				$("#item"+num).attr("franchiseeName",franchiseeName);
				$("#item"+num).attr("franchiseeId",franchiseeId);
				$("#item"+num).attr("circel",circel);
				$("#item"+num).attr("cut",cut);
//				$("#item"+num).attr("feeType",feeType);
				
				$("#temtr"+num).find("td").eq(1).html(getSelectValue(gMaterial,gMaterials));
				$("#temtr"+num).find("td").eq(2).html(getSelectValue(gWeight,gWeights));
				$("#temtr"+num).find("td").eq(3).html(circel);
				$("#temtr"+num).find("td").eq(4).html(getSelectValue(dWeight,dWeights));
				$("#temtr"+num).find("td").eq(5).html(getSelectValue(dClarity,dClaritys));
				$("#temtr"+num).find("td").eq(6).html(getSelectValue(dColor,dColors));
				$("#temtr"+num).find("td").eq(7).html(getSelectValue(cut,cuts));
				$("#temtr"+num).find("td").eq(8).html(numbers);
				$("#temtr"+num).find("td").eq(9).html(franchiseeName);
				$("#temtr"+num).find("td").eq(10).html(mdtlCodeName);
				$("#temtr"+num).find("td").eq(11).html(basicCost);
				$("#temtr"+num).find("td").eq(12).html(additionCost);
				$("#temtr"+num).find("td").eq(13).html(otherCost);
			}else{
				 var html="";
				 html+="<tr id='temtr"+itemNum+"' >";
				 html+="<td>"+ getSelectValue(mdCode,mdCodes) +"</td>";
				 html+="<td>"+getSelectValue(gMaterial,gMaterials)+"</td>";
				 html+="<td>"+getSelectValue(gWeight,gWeights)+"</td>";
				 html+="<td>"+JY.Object.notEmpty(circel)+"</td>"
				 html+="<td>"+getSelectValue(dWeight,dWeights)+"</td>";
				 html+="<td>"+getSelectValue(dClarity,dClaritys)+"</td>";
				 html+="<td>"+getSelectValue(dColor,dColors)+"</td>";
				 html+="<td>"+getSelectValue(cut,cuts)+"</td>";
//					html+="<td style='display:none'>"+JY.Object.notEmpty(weight)+"</td>";
				 html+="<td>"+JY.Object.notNumber(numbers)+"</td>";
//					html+="<td>"+getSelectValue(feeType,feeTypes)+"</td>";
				 html+="<td>"+JY.Object.notEmpty(franchiseeName)+"</td>";
				 html+="<td>"+mdtlCodeName+"</td>";
				 html+="<td >"+JY.Object.notNumber(basicCost)+"</td>";
				 html+="<td>"+JY.Object.notNumber(additionCost)+"</td>";
				 html+="<td>"+JY.Object.notNumber(otherCost)+"</td>";
//					html+="<td style='display:none'>"+JY.Object.notEmpty(unitprice)+"</td>";
//					html+="<td style='display:none'>"+JY.Object.notEmpty(totalFee)+"</td>";
				html+="<td>"+JY.Object.notNumber(stockNum)+"</td>";
				html+="<td>";
				html+="<div class='dropdown'>";
				html+="<button class='dropbtn'>操作</button>";
				html+="<div class='dropdown-content'>";
				html+="<a  onclick='editDetail(&apos;"+itemNum+"&apos;)' title='编辑' href='#'>编辑</a>";	
//					 html+="<a onclick='splitDetail(&apos;"+itemNum+"&apos;)' title='拆开' href='#'>拆开</a>";	
				html+="<a onclick='delDetail(&apos;"+itemNum+"&apos;)' title='删除' href='#'>删除</a>";
				html+="</div></div>";
				html+="<input type='hidden' name='items'id='item"+itemNum+"' value='"+itemNum+"'mdCode='"+JY.Object.notEmpty(mdCode)+"' mdtlCode='"+JY.Object.notEmpty(mdtlCode)
				 +"' mdtlCodeName='"+JY.Object.notEmpty(mdtlCodeName)+"' gMaterial='"+JY.Object.notEmpty(gMaterial)+"' gWeight='"+JY.Object.notEmpty(gWeight)+"' dWeight='"
				 +JY.Object.notEmpty(dWeight) +"' dClarity='"+JY.Object.notEmpty(dClarity)+"' dColor='"+JY.Object.notEmpty(dColor)+"' weight='"+JY.Object.notEmpty(weight)
				 +"' numbers='"+JY.Object.notEmpty(numbers)+"' feeType='"+JY.Object.notEmpty(feeType)+"'franchiseeName='"+JY.Object.notEmpty(franchiseeName)+"' basicCost='"
				 +JY.Object.notEmpty(basicCost) +"' additionCost='"+JY.Object.notEmpty(additionCost)+"' otherCost='"+JY.Object.notEmpty(otherCost)+"' unitprice='"
				 +JY.Object.notEmpty(+unitprice) +"' totalFee='"+JY.Object.notEmpty(totalFee)+"' status='1' ids='"+"' franchiseeId='"
				 +JY.Object.notEmpty(franchiseeId)+"' description='"+JY.Object.notEmpty(description)+"' beforeId='"+JY.Object.notEmpty(beforeId)
				 +"'circel='"+JY.Object.notEmpty(circel)+"'cut='"+JY.Object.notEmpty(cut)+"' >";		
				 html+="</td>";
				 html+="</tr>";
				 itemNum++;
				 $("#itemsTable").append(html);
			}
			initFoot();
			getTotNum();
			$(this).dialog("close");
		 }
	});
}
/*
 * 查看
 */
function finds(id){
	//加载dialog
	cleanForm();
	$("#opeTr").hide();
	JY.Ajax.doRequest(null,jypath +'/scm/purorder/find',{id:id},function(data){ 
		setForm1(data); 
		viewInfo("pOrderDiv");
	});
	$("#pBaseForm input[name$='pId']").val(id);
	findView();
}
/*
 * 修改
 */
function edits(id){
	cleanForm();
	$("#opeTr").hide();
	$("#pBaseForm input[name$='pId']").val(id);
	JY.Ajax.doRequest(null,jypath +'/scm/purorder/find',{id:id},function(data){ 
		var l =data.obj;
		if(l.status!="0"){
			JY.Model.info("只有草稿状态的订单才能进行编辑");
			return;
		}
		setForm1(data); 
		addInfo("pOrderDiv","编辑",function(type){
	    		var that =$(this);
				JY.Ajax.doRequest(null,jypath +'/scm/purorder/check',{id:id,status:type},function(data){
					JY.Model.info(data.resMsg,function(){search();findView(1); that.dialog("close"); });
				});
		});
		findView();
	});
//	viewInfo("pOrderDiv");
}
function findView(isEdit){
	JY.Model.loading();
	JY.Ajax.doRequest("pBaseForm",jypath +'/scm/purchase/findByPage',null,function(data){
		 $("#pBaseTable tbody").empty();
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
            		 if(l.status==1)statusStr="待审核";else if(l.status==2)statusStr="已审核";else if(l.status==3)statusStr="已完成"; else if(l.status==9)statusStr="已删除"; else statusStr="草稿";
            		 html+="<tr>";
            		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
            		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.orderNo)+"</td>";
            		 html+="<td class='left'>"+JY.Object.notEmpty(l.totalNum)+"</td>";
            		 html+="<td class='left hidden-480' >"+JY.Object.notEmpty(l.weight)+"</td>";
            		 html+="<td class='left hidden-480'>"+JY.Object.notEmpty(l.totalFee)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.arrivalDate,"yyyy-MM-dd")+"</td>";
            		 html+="<td class='center hidden-480'>"+labelStr+"</td>";
            		 html+="<td class='center hidden-480'>"+statusStr+"</td>";
            		 if(isEdit==1){
            			 if(l.status==1){
            				 html+=JY.Tags.setFunction1(l.id,permitBtn,'edits,finds,check,edit,del,split,uCheck,submit,dowBut,');
            			 }else if(l.status==2){
            				 html+=JY.Tags.setFunction1(l.id,permitBtn,'edits,finds,check,edit,del,split,uCheck,submit,dowBut,');
            			 }else if(l.status==3){
            				 html+=JY.Tags.setFunction1(l.id,permitBtn,'edits,finds,check,edit,del,split,uCheck,submit,dowBut,');
            			 }else if(l.status==0){
            				 html+=JY.Tags.setFunction1(l.id,permitBtn,'edits,finds,check,del,split,uCheck,submit,dowBut,');
            			 }
            		 }else{
            			 html+=JY.Tags.setFunction1(l.id,permitBtn,'edits,finds,check,edit,del,uCheck,submit,dowBut,');
            		 }
            		 html+="</tr>";		 
            	 } 
        		 $("#pBaseTable tbody").append(html);
        		 JY.Page.setPage("baseForm","pageing1",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
        		$("#pBaseTable tbody").append(html);
        		$("#pageing1 ul").empty();//清空分页
        	 }	
    		 JY.Model.loadingClose();
	 });
}
function edit(id){
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/scm/purorder/find',{id:id},function(data){
		var l = data.obj;
		if(l.status!=0&&l.status!=4){
			JY.Model.info("只有草稿状态订单才能修改");
			return;
		}
	    setForm(data,3);   
	    addInfo("orderDiv","修改",function(type){
	    	if(JY.Validate.form("orderForm")){
	    		var that =$(this);
				var id=$("#orderForm input[name$='id']").val();
				var orderNo=$("#orderForm input[name$='orderNo']").val();
				var totalNum=$("#orderForm input[name$='totalNum']").val();
				var weight=$("#orderForm input[name$='weight']").val();
				var totalFee=$("#orderForm input[name$='totalFee']").val();
				var arrivalDate=new Date($("#orderForm input[name$='arrivalDate']").val());
				var orderType=$("#orderForm input[name$='orderType']").val();
				var description=$("#orderForm textarea[name$='description']").val();
				var orgId=$("#orderForm input[name$='orgId']").val();
				setItemArry("#itemsTable");
				var params ={id:id,orderNo:orderNo,totalNum:totalNum,weight:weight,totalFee:totalFee,arrivalDate:arrivalDate,description:description,orderType:orderType,orgId:orgId,status:type,items:itemArry};								 	 
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

function dowBut(id){
	JY.Model.confirm("确认下单吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/purorder/dowOrder',{id:id,label:1},function(data){
			JY.Model.info(data.resMsg,function(){search();findView(1);});
		});
	});
}
/*
 * 删除
 */
function del(id){
	JY.Model.confirm("确认删除吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/purorder/del',{id:id},function(data){
			JY.Model.info(data.resMsg,function(){search();findView(1);});
		});
	});
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
function find(id){
	//加载dialog
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/scm/purorder/find',{id:id},function(data){ 
		setForm(data,2); 
		viewInfo("orderDiv");
	});
}
/*
 * 审核     jyValidate="required" 
 */
function check(id){
	cleanForm();
	$("#operatorName").attr("jyValidate","required");
	$("#opeTr").show(); 
	$("#searchUserBtn").show(); 
//	$("#pBaseForm input[name$='pId']").val(id);
	JY.Ajax.doRequest(null,jypath +'/scm/purorder/find',{id:id},function(data){ 
		var l = data.obj;
		if(l.status!=1){
			JY.Model.info("只有待审核状态订单才能审核");
			return;
		}
		setForm(data,2);  
		checkInfo("orderDiv","订单审核",function(type){
			var that =$(this);
			if(type=="4"){
				JY.Model.confirm(type=="2"?"确认审核通过吗？":"确认审核不通过吗？",function(){	
					JY.Ajax.doRequest(null,jypath +'/scm/purorder/check',{id:id,status:type},function(data){
						JY.Model.info(data.resMsg,function(){search();findView(1); that.dialog("close"); });
					});
				});
			}else{
				if(JY.Validate.form("orderForm")){
					JY.Model.confirm(type=="2"?"确认审核通过吗？":"确认审核不通过吗？",function(){	
						var operatorId=$("#operatorId").val();
						var operatorName=$("#operatorName").val();
						JY.Ajax.doRequest(null,jypath +'/scm/purorder/check',{id:id,status:type,operatorId:operatorId,operatorName:operatorName},function(data){
							JY.Model.info(data.resMsg,function(){search();findView(1); that.dialog("close"); });
						});
					});
				}
			}
		});
		findView(1);
	});
}
function userClike(){
	$("#pickUserName").val("");
	$("#pickUserId").val("");
	pickUserInfo("pickUserDiv","选人",function(){
		var that =$(this);
		var pickUserId=$("#pickUserId").val();
		var pickUserName=$("#pickUserName").val();
		$("#operatorId").val(pickUserId);
		$("#operatorName").val(pickUserName);
		that.dialog("close");
	})
	loadOrgTree();
}
function submit(id){
	JY.Model.confirm("确认提交吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/purorder/check',{id:id,status:1},function(data){
			JY.Model.info(data.resMsg,function(){search();findView(1);});
		});
	});
}
function uCheck(id){
	JY.Ajax.doRequest(null,jypath +'/scm/purorder/find',{id:id},function(data){
		var l =data.obj;
		if(l.status!=2){
			JY.Model.info("只有<已审核>状态订单才能反审核");
			return;
		}
		JY.Model.confirm("确认反审核吗？",function(){	
			JY.Ajax.doRequest(null,jypath +'/scm/purorder/check',{id:id,status:0},function(data){
				JY.Model.info(data.resMsg,function(){search();findView(1);});
			});
		});
	});
}
function cleanForm(){
	itemArry =new Array();
	itemNum=1;
	JY.Tags.cleanForm("orderForm");
	$("#orderForm span").text("");
	$("#itemsTable tbody").empty();
	$("#itemsTable tfoot").empty();
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
var itemArry;
function cleanItemForm(){
	JY.Tags.cleanForm("itemDiv");
	getselect(gMaterials,"gMaterialSelect");
	getselect(gWeights,"gWeightSelect");
	getselect(dClaritys,"dClaritySelect");
	getselect(dColors,"dColorSelect");
	getselect(cuts,"dCutSelect");
	getselect(gWeights,"weightSelect");
	getselect(dWeights,"dWeightSelect");
	getselect(feeTypes,"feeTypeSelect");
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
			var franchiseeId=$("#item"+num).attr("franchiseeId");
			var description=$("#item"+num).attr("description");
			var beforeId=$("#item"+num).attr("beforeId");
			var circel=$("#item"+num).attr("circel");
			var cut=$("#item"+num).attr("cut");
			if(mdCode==""||franchiseeId==""||numbers==""){
				editDetail(num);
				return true;
			}
			var item={mdCode:mdCode,mdtlCode:mdtlCode,gMaterial:gMaterial,gWeight:gWeight,dWeight:dWeight,dClarity:dClarity,dColor:dColor,
					weight:weight,numbers:numbers,feeType:feeType,basicCost:basicCost,additionCost:additionCost,otherCost:otherCost,unitprice:unitprice,
					totalFee:totalFee,status:status,id:ids,franchiseeId:franchiseeId,description:description,beforeId:beforeId,circel:circel,cut:cut};
			itemArry.push(item);
		}
	}	
}
function initFoot(isHide){
	var list="";
	$("#itemsTable tfoot").empty();
	list=$("#itemsTable input[name='items']").map(function(){return $(this).val();}).get().join(",");
	var totWeight=0;
	var totNumbers=0;
	var totBasicCost=0;
	var totAdditionCost=0
	var totOtherCost=0;
	if(list!=null&&list!=""){
		var chks=list.split(",");
		for(var i=0;i<chks.length;i++){
			totWeight+=parseFloat($("#item"+chks[i]).attr("weight"))>0?parseFloat($("#item"+chks[i]).attr("weight")):0;
			totNumbers+=parseInt($("#item"+chks[i]).attr("numbers"))>0?parseInt($("#item"+chks[i]).attr("numbers")):0;
			totBasicCost+=parseInt($("#item"+chks[i]).attr("basicCost"))>0?parseInt($("#item"+chks[i]).attr("basicCost")):0;
			totAdditionCost+=parseInt($("#item"+chks[i]).attr("additionCost"))>0?parseInt($("#item"+chks[i]).attr("additionCost")):0;
			totOtherCost+=parseInt($("#item"+chks[i]).attr("otherCost"))>0?parseInt($("#item"+chks[i]).attr("otherCost")):0;
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
	html+="<td >"+totNumbers+"</td>";
	html+="<td >"+"</td>";
	html+="<td >"+"</td>";
	html+="<td>"+totBasicCost+"</td>";
	html+="<td>"+totAdditionCost+"</td>";
	html+="<td>"+totOtherCost+"</td>";
	html+="<td></td>";
	html+="<td></td></tr>";
	$("#itemsTable tfoot").append(html);
}

function print(){
	 var id = $("#orderForm input[name='id']").val();
	 var orderNo=$("#orderForm input[name='orderNo']").val();
	 $("#printDiv").load(jypath +'/scm/purorder/print?id='+id+'&type='+2,function(){
		 LODOP=getLodop();  
		 LODOP.PRINT_INIT("打印控件功能演示");
		 LODOP.ADD_PRINT_BARCODE(16,78,166,21,"128B",orderNo);
		 LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
		 LODOP.ADD_PRINT_HTM(0,0,"100%","100%",document.getElementById("testPrintdiv").innerHTML);
		 LODOP.PREVIEW();
	 })
}