var paymentList=[];
var paymentLeng=1;
var coupons=[];
var banks=[];
var moneys=[],quicks=[];
$(function () {
	paymentList=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"POS_PAYMENT",paymentList);
	coupons=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"POS_PAYMENT_COUPON",coupons);
	banks=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"POS_PAYMENT_BANK",banks);
	moneys=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"POS_PAYMENT_MONEY",moneys);
	quicks=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"POS_PAYMENT_QUICK",quicks);
	
});

function onAddRow(){
	var html="<tr id ='paymentLeng"+paymentLeng+"'>";
	html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+paymentLeng+"' class='ace' /> <span class='lbl'></span></label></td>";
	html+="<td class='center'>"+paymentLeng+"<input type='hidden'  id='id"+paymentLeng+"' value=''/></td>";
	html+="<td style='padding:0px;'>"+"<select id='payment"+paymentLeng+"' jyValidate='required' style='width:100%;'  onchange= paymentSelect("+paymentLeng+")>"+options(paymentList)+"</select>"+"</td>";
	html+="<td style='padding:0px;'>"+"<select id='bank"+paymentLeng+"' jyValidate='required' style='width:100%;'  >"+options(moneys)+"</select>"+"</td>";
	html+="<td style='padding:0px;'>"+"<input type='text'  id='cardno"+paymentLeng+"' jyValidate='required' onchange= onCardno("+paymentLeng+")  style='width:100%;height:30px;'  value=''>"+"</td>";
	html+="<td style='padding:0px;'>"+"<input type='text'  id='rate"+paymentLeng+"' jyValidate='required' style='width:100%;height:30px;'  value='1'>"+"</td>";
	html+="<td style='padding:0px;'>"+"<input type='text'  id='amount"+paymentLeng+"' jyValidate='required' onchange= onAmount() style='width:100%;height:30px;'  value=''>"+"</td>";
	html+="<td style='padding:0px;'>"+"<input type='text'  id='note"+paymentLeng+"' style='width:100%;height:30px;'  value=''>"+"</td>";
	html+="</tr>";
	$("#paymentsTable").append(html);
	paymentLeng++;
}
function delRowList(){
	var chks =[];    
	$('#paymentsTable input[name="ids"]:checked').each(function(){  
		var id=$(this).val();
		var ids=$("#id"+id).val();
		if(JY.Object.notEmpty(ids)==""){
			$("#paymentLeng"+id).remove();
		}
//		if(JY.Object.notEmpty(ids)!=""){
//			chks.push(ids);    
//		}
	});    
	onAmount();
//	if(chks.length>0){
//		JY.Ajax.doRequest("",jypath +'/pos/payments/delBatch',{chks:chks.toString()},function(data){
//			if(data.res==1){
//				that.dialog("close");  
//				JY.Model.info(data.resMsg,function(){getPaymentsList();});
//			}else{
//				JY.Model.error(data.resMsg);
//			}
//		});
//	}
	
}
function getPaymentsList(){
	JY.Ajax.doRequest("paymentsListForm",jypath +'/pos/payments/findByPage',null,function(data){
		$("#paymentsTable tbody").empty();
		var html="";
		 var obj=data.obj;
    	 var list=obj.list;
    	 var results=list;
    	 if(results!=null&&results.length>0){
    		 for(var i = 0;i<results.length;i++){
        		 var l=results[i];
        		 html+="<tr id ='paymentLeng"+paymentLeng+"'>";
        		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+paymentLeng+"' class='ace' /> <span class='lbl'></span></label></td>";
        		 html+="<td class='center'>"+paymentLeng+"<input type='hidden'  id='id"+paymentLeng+"' value='"+l.id+"'/></td>";
        		 html+="<td style='padding:0px;'>"+"<select id='payment"+paymentLeng+"' disabled='disabled' jyValidate='required' style='width:100%;'  onchange= paymentSelect("+paymentLeng+")>"+options(paymentList,l.payment)+"</select>"+"</td>";
        		 if(l.payment=="1"){
        			 html+="<td style='padding:0px;'>"+"<select id='bank"+paymentLeng+"' disabled='disabled' jyValidate='required' style='width:100%;'  >"+options(moneys,l.currency)+"</select>"+"</td>";
        		 }else if(l.payment=="2"||l.payment=="5"){
        			 html+="<td style='padding:0px;'>"+"<select id='bank"+paymentLeng+"' disabled='disabled' jyValidate='required' style='width:100%;'  >"+options(banks,l.bank)+"</select>"+"</td>";
        		 }else if(l.payment=="3"){
        			 html+="<td style='padding:0px;'>"+"<select id='bank"+paymentLeng+"' disabled='disabled' jyValidate='required' style='width:100%;'  >"+options(coupons,l.denomination)+"</select>"+"</td>";
        		 }else if(l.payment=="4"){
        			 html+="<td style='padding:0px;'>"+"<select id='bank"+paymentLeng+"' disabled='disabled' jyValidate='required' style='width:100%;'  >"+options(quicks,l.bank)+"</select>"+"</td>";
        		 }else{
        			 html+="";
        		 }
        		 html+="<td style='padding:0px;'>"+"<input type='text' readonly id='cardno"+paymentLeng+"' jyValidate='required' onchange= onCardno("+paymentLeng+") style='width:100%;height:30px;'  value='"+JY.Object.notEmpty(l.cardno)+"'>"+"</td>";
        		 html+="<td style='padding:0px;'>"+"<input type='text' readonly id='rate"+paymentLeng+"' jyValidate='required' style='width:100%;height:30px;'  value='"+JY.Object.notEmpty(l.rate)+"'>"+"</td>";
        		 html+="<td style='padding:0px;'>"+"<input type='text' readonly id='amount"+paymentLeng+"' jyValidate='required'  onchange= onAmount() style='width:100%;height:30px;'  value='"+JY.Object.notEmpty(l.amount)+"'>"+"</td>";
        		 html+="<td style='padding:0px;'>"+"<input type='text' readonly id='note"+paymentLeng+"' style='width:100%;height:30px;'  value='"+JY.Object.notEmpty(l.note)+"'>"+"</td>";
        		 html+="</tr>";
        		 paymentLeng++;
    		 }
    	 }
    	 $("#paymentsTable").append(html);
	});
	onAmount();
}
function onAmount(){
	var nums = $("#paymentsTable input[name='ids']").map(function(){return $(this).val();}).get().join(",");
	var owed=0;
	if(JY.Object.notNull(nums)){
		var numList=nums.split(",");
		for(var i=0;i<numList.length;i++){
			var num=numList[i];
			var amount=$("#amount"+num).val();
			owed+=Number(amount);
		}
	}
	var receivable = $("#receivable").text();
	$("#owed").text(parseFloat(Number(receivable)-owed).toFixed(2));
}
function onCardno(num){
	var payment=$("#payment"+num).val();
	if(payment=="3"){
		var cardno=$("#cardno"+num).val();
		var bank=$("#bank"+num+" option:selected").val()
		$("#amount"+num).val(cardno*bank);
		onAmount();
	}
}
function paymentSelect(num){
	var payment=$("#payment"+num).val();
	if(payment=="1"){
		getselect11("bank"+num,moneys);
	}else if(payment=="2"||payment=="5"){
		getselect11("bank"+num,banks);
	}else if(payment=="3"){
		$("#amount"+num).attr("readonly", true);
		getselect11("bank"+num,coupons);
	}else if(payment=="4"){
		getselect11("bank"+num,quicks);
	}
	else {
		$("#bank"+num).empty();
	}
}
function getselect11(opts,selects){
	$("#"+opts).empty();
	var op="<option value=''>" +"请选择" + "</option>";
	$.each(selects,function(i, e) {
		op+="<option value='" + e.key + "'>" + e.text + "</option>"
    });
	$("#"+opts).append(op);
}
var paymentArry=new Array();
function setPpaymentArry(){
	paymentArry =new Array();
	var nums = $("#paymentsTable input[name='ids']").map(function(){return $(this).val();}).get().join(",");
	var orderId = $("#paymentsListForm").find("#paymentsOrderId").val();
	var orderNo = $("#paymentsListForm").find("#paymentsOrderNo").val();
	var type = $("#paymentsListForm").find("#paymentsType").val();
	if(JY.Object.notNull(nums)){
		var numList=nums.split(",");
		for(var i=0;i<numList.length;i++){
			var num=numList[i];
			var payment=$("#payment"+num+" option:selected").val();
			var bank="";
			var currency="";
			var denomination="";
			if(payment=="1"){
				currency=$("#bank"+num+" option:selected").val()
			}else if(payment=="2"||payment=="5"||payment=="4"){
				bank=$("#bank"+num+" option:selected").val()
			}else if(payment=="3"){
				denomination =$("#bank"+num+" option:selected").val()
			}
			var cardno=$("#cardno"+num).val();
			var rate=$("#rate"+num).val();
			var amount=$("#amount"+num).val();
			var note=$("#note"+num).val();
			var id=$("#id"+num).val();
			var payments={id:id,orderId:orderId,orderNo:orderNo,type:type,payment:payment,bank:bank,denomination:denomination,currency:currency,cardno:cardno,rate:rate,amount:amount,note:note};
			paymentArry.push(payments);
		}
	}
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