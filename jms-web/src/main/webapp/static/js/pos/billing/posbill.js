var goldTypes = [];
var stores = [];

var assistants1 = [];
var assistants2 = [];
var leaders1 = [];
var leaders2 = [];
var num = 0;
$(function(){
	//下拉框
	$("#posbillBaseForm input[name='orgId']").val(curUser.orgId);
	goldTypes=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_PRO_GOLD_TYPE",goldTypes);
	JY.Dict.setSelect("selectPosbillStatus","POS_BILLING_STATUS",2,"全部");
	JY.Dict.setSelect("selectGoldType","SCM_PRO_GOLD_TYPE",1);
	
	getbaseList(1);
})

function getSelect(url,selects,id){
	$("#"+id).html("");
	var select = document.getElementById(id);
	JY.Ajax.doRequest("",url,null,function(data){
		var obj=data.obj;
		for (var i = 0; i < obj.length; i++) {
            var op = new Option(obj[i].value, obj[i].key);
            //添加
            select.options.add(op);
        }
		if(id!="selectStore"){
			$("#"+id).val("");
		}
	})
	return selects;
}


function setOrg(){
	JY.Ajax.doRequest(null,jypath +'/pos/billing/getOrg',null,function(data){
		$("#posbillTab input[name='cashierName']").val(data.obj.cashierName);
		$("#posbillTab input[name='cashier']").val(data.obj.cashier);
		$("#posbillTab input[name='saleTime']").val(JY.Date.Format(data.obj.saleTime,"yyyy-MM-dd"));
		$("#posbillTab select[name='storeId']").val(data.obj.storeId);
	})
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

/*刷新列表页*/
function search(){
	$("#searchBtn").trigger("click");
}

function toFix(value){
	var zero = 0;
	return !value?zero.toFixed(4):parseFloat(value).toFixed(4);
}

/*销售列表*/
function getbaseList(init){
	if(init==1)$("#posbillBaseForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("posbillBaseForm",jypath +'/pos/billing/findByPage',null,function(data){
		 $("#posbillTable tbody").empty();
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
            		 html+="<td class='center hidden-480' ><input type='hidden' value='"+l.billNo+"'/><a style='cursor:pointer;' onclick='find(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.billNo)+"</a></td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.customer)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.vipCode)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.assistantName1)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.cashierName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.actualAmt)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.saleTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
            		 if(l.bussiType==0){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>销退</span></td>";
            		 }
            		 if(l.bussiType==1){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>销售</span></td>";
            		 }
            		 if(l.bussiType==2){
            			 html+="<td class='center hidden-480'><span class='label label-sm  arrowed-in'>销换</span></td>";
            		 }
            		 
            		 if(l.status==0){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>草稿</span></td>";
            		 }
            		 if(l.status==1){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>待审核</span></td>";
            		 }
            		 if(l.status==2){
            			 html+="<td class='center hidden-480'><span class='label label-sm  label-success'>已审核</span></td>";
            		 }
            		 if(l.status==3){
            			 html+="<td class='center hidden-480'><span class='label label-sm  label-success'>待付款</span></td>";
            		 }
            		 if(l.status==4){
            			 html+="<td class='center hidden-480'><span class='label label-sm  arrowed-in'>已红冲</span></td>";
            		 }
            		 if(l.status==9){
            			 html+="<td class='center hidden-480'><span class='label label-sm  arrowed-in'>已付款</span></td>";
            		 }
            		 html+="</tr>";		 
            	 } 
        		 $("#posbillTable tbody").append(html);
        		 JY.Page.setPage("posbillTable","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='19' class='center'>没有相关数据</td></tr>";
        		$("#posbillTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
    		 JY.Model.loadingClose();
	 });
}


$("#materialnoteDiv input[name='type']").on('click', function(e) {
	if(!$("#materialnoteDiv input[name='type']:checked").val()){
		$("#materialnoteDiv input[name='type']").prop("checked",false);
		$("#exchangeTab").addClass("hide");
		$("#oldGoldTab").addClass("hide");
		$("#posbillDiv").css("height","550px");
		$("#posbillDiv").parent().css("top","102px");
		$('#materialnoteDiv input').each(function(element,index){
			if($(this).is(".required")){
				$(this).removeAttr("jyValidate","required");
			}
		})
	}else{
		$("#materialnoteDiv input[name='type']").prop("checked",false);
		$(this).prop("checked",true);
	}
	var check = $("#materialnoteDiv input[name='type']:checked").val();
	if(check=="3"||check=="4"||check=="5"){
		$("#posbillDiv").css("height","600px");
		$("#posbillDiv").parent().css("top","50px")
		JY.Tags.cleanForm("oldGoldTab");
		$("#oldGoldTab").removeClass("hide");
		$("#exchangeTab").addClass("hide");
		$('#oldGoldTab input').each(function(element,index){
			if($(this).is(".required")){
				$(this).attr("jyValidate","required");
			}
		})
	}else if(check=="2"){
		$("#posbillDiv").css("height","650px");
		$("#posbillDiv").parent().css("top","30px")
		JY.Tags.cleanForm("exchangeTab");
		$("#oldGoldTab").addClass("hide");
		$("#exchangeTab").removeClass("hide");
		$('#exchangeTab input').each(function(element,index){
			if($(this).is(".required")){
				$(this).attr("jyValidate","required");
			}
		})
	}
})


function subProduct(value){
	var flag = true;
	if(value){
		$('#posbillAdd tbody tr').each(function(element,index){
			if(value.trim()==$(this).find("span[name='barCode']").text()){
				$("#btnDiv input[name='barCode']").tips({
		            msg: "该商品已存在!",
		            bg: '#FF2D2D',
		            time: 1
		        });
				flag = false;
				return flag;
			}
		})
		if(flag){
			JY.Ajax.doRequest(null,jypath +'/pos/billing/findProduct',{barCode:value.trim()},function(data){
				if(data.obj.posBillDetail){
					$("#posbillAddDiv").removeClass("hide");
					var l = data.obj.posBillDetail;
					var html = "";
					html+="<tr>";
					html+="<td class='center'><label> <input type='checkbox' name='ids' class='ace' /><span class='lbl'></span></label><input type='hidden' id='oiginalPrice"+num+"' value='"+toFix(l.price)+"'><input type='hidden' name='type' value='1'/><input type='hidden' name='weight' value='"+toFix(l.weight)+"'/><input type='hidden' name='actualWt' value='"+JY.Object.notEmpty(l.actualWt)+"'/><input type='hidden' name='remarks' value='"+JY.Object.notEmpty(l.remarks)+"'/><input type='hidden' name='note' value='"+JY.Object.notEmpty(l.note)+"'/><input type='hidden' name='coin' value='100'/></td>";
					html+="<td class='center hidden-480'><span name='barCode'>"+l.barCode+"</span></td>";
					html+="<td class='center hidden-480'><span name='name'>"+l.name+"</span></td>";
					html+="<td class='center hidden-480'><span name='price'>"+toFix(l.price)+"</span></td>";
					html+="<td style='padding:0px;'><input class='center' id='saleCost"+num+"' onBlur='addNum("+num+")' onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' name='saleCost' value='"+toFix(l.saleCost)+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
					html+="<td style='padding:0px;'><input class='center' id='promCoin"+num+"' onBlur='addNum("+num+")' onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' name='promCoin' value='"+toFix(l.promCoin)+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
					html+="<td style='padding:0px;'><input class='center' id='saleProm"+num+"' onBlur='addNum("+num+")' onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' name='saleProm' value='"+toFix(l.saleProm)+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
					html+="<td class='center'><label> <input type='checkbox' name='gift' class='ace' /><span class='lbl'></span></label></td>";
					html+="<td style='padding:0px;'><input class='center' onBlur='addNum("+num+")' id='count"+num+"' jyValidate='required' type='text' name='count' value='1' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
					html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' onBlur='addNum("+num+",\""+"actualPrice"+"\")' jyValidate='required' type='text' id='actualPrice"+num+"' name='actualPrice' value='"+toFix(l.price+l.saleCost-l.promCoin-l.saleProm)+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
					html+="</tr>";
					$("#posbillAdd tbody").append(html);
					countFoot();
					num++;
				}else{
					$("#btnDiv input[name='barCode']").tips({
			            msg: "该商品不存在!",
			            bg: '#FF2D2D',
			            time: 1
			        });
					return false;
				}
			});
		}
	}
}

function addNum(num,id){
	if(!id){
		var saleCost = $("#posbillAdd #saleCost"+num).val();
		var promCoin = $("#posbillAdd #promCoin"+num).val();
		var saleProm = $("#posbillAdd #saleProm"+num).val();
		var count = $("#posbillAdd #count"+num).val();
		var oiginalPrice = $("#posbillAdd #oiginalPrice"+num).val();
		var price = (oiginalPrice*count)-(saleCost*count+promCoin*count+saleProm*count);
		
		$("#posbillAdd #actualPrice"+num).val(price);
	}
	countFoot();
}

$("#exchangeTab input[name='zhekou']").change(function(){
	var oldPrice = $("#exchangeTab input[name='oldPrice']").val();
	var zhekou = $("#exchangeTab input[name='zhekou']").val();
	if(zhekou>1){
		$(this).tips({
            msg: "折扣不能超过1！",
            bg: '#FF2D2D',
            time: 1
        });
		$(this).val(1);
		return false;
	}
	$("#exchangeTab input[name='actualPrice']").val(oldPrice*zhekou);
})

function subPos(value){
	var flag = true;
	if(value){
		$('#posbillAdd tbody tr').each(function(element,index){
			if($(this).find("input[name='type']").val()==2&&value.trim()==$(this).find("span[name='barCode']").text()){
				$("#exchangeTab input[name='barCode']").tips({
		            msg: "旧货商品已存在!",
		            bg: '#FF2D2D',
		            time: 1
		        });
				$("#exchangeTab input[name='price']").val("");
				$("#exchangeTab input[name='saleDate']").val("");
				$("#exchangeTab input[name='oldPrice']").val("");
				flag = false;
				return flag;
			}
		})
		if(flag){
			JY.Ajax.doRequest(null,jypath +'/pos/billing/getDetailByCode',{barCode:value.trim()},function(data){
				var l = data.obj.detail;
				if(l){
					$("#exchangeTab input[name='price']").val(l.price);
					$("#exchangeTab input[name='saleDate']").val(JY.Date.Format(l.saleDate,"yyyy-MM-dd"));
					$("#exchangeTab input[name='oldPrice']").val(l.actualPrice);
					$("#exchangeTab input[name='actualPrice']").val(l.actualPrice);
					$("#exchangeTab input[name='zhekou']").val(1);
					$("#exchangeTab input[name='weight']").val(l.actualWt);
				}else{
					$("#exchangeTab input[name='price']").val("");
					$("#exchangeTab input[name='saleDate']").val("");
					$("#exchangeTab input[name='oldPrice']").val("");
					$("#exchangeTab input[name='actualPrice']").val("");
					$("#exchangeTab input[name='zhekou']").val("");
					$("#exchangeTab input[name='actualWt']").val("");
					
					$("#exchangeTab input[name='barCode']").tips({
			            msg: "旧货商品不存在!",
			            bg: '#FF2D2D',
			            time: 1
			        });
				}
			});
		}
	}
}

//删除来料
$('#delBtn').on('click', function(e) {
	//通知浏览器不要执行与事件关联的默认动作		
	e.preventDefault();
	var chks =[];    
	$('#posbillTable input[name="ids"]:checked').each(function(){    
		chks.push($(this).val());    
	});     
	if(chks.length==0) {
		JY.Model.info("您没有选择任何内容!"); 
	}else{
		JY.Model.confirm("确认要删除选中的数据吗?",function(){	
			JY.Ajax.doRequest(null,jypath +'/pos/billing/deletePosBill',{chks:chks.toString()},function(data){
				JY.Model.info(data.resMsg,function(){search();});
			});
		});		
	}		
});

//查看来料
$('#viewBtn').on('click', function(e) {
	var chks =[];    
	$('#posbillTable input[name="ids"]:checked').each(function(){    
		chks.push($(this).val());    
	}); 
	if(chks.length==0){
		JY.Model.info("您没有选择任何内容!"); 
	}else if(chks.length>1){
		JY.Model.info("请选择一条内容!"); 
	}else{
		find(chks[0]);
	}
});

//修改来料
$('#updateBtn').on('click', function(e) {
	var chks =[];    
	$('#posbillTable input[name="ids"]:checked').each(function(){    
		chks.push($(this).val());    
	}); 
	if(chks.length==0){
		JY.Model.info("您没有选择任何内容!"); 
	}else if(chks.length>1){
		JY.Model.info("请选择一条内容!"); 
	}else{
		update(chks[0]);
	}
});

function editInfo1(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,modal:true,height:570,width:720,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this,1);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
}

//付款
function payMoney(id){
	JY.Ajax.doRequest(null,jypath +'/pos/billing/find',{id:id},function(data){
		cleanPay();
		var pos = data.obj.pos;
		paymentLeng=1;
		$("#paymentsListForm").find("#paymentsOrderId").val(pos.id);
		$("#paymentsListForm").find("#paymentsOrderNo").val(pos.billNo);
		$("#paymentsListForm").find("#paymentsType").val(1);
		$("#receivable").text(pos.actualAmt);
//		editInfo1 ("paymentsListDiv","付款",function(e){
//			var that =$(this);
//			var owed=$("#owed").text();
//			setPpaymentArry();
//			if(owed!=0){
//				JY.Model.info("实收金额不等于应收金额");
//				return false;
//			}
//			var params={list:paymentArry,type:1,status:e};
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
		if(pos.status==3){
			paymentLeng=1;
			$("#paymentsListForm").find("#paymentsOrderId").val(pos.id);
			$("#paymentsListForm").find("#paymentsOrderNo").val(pos.billNo);
			$("#paymentsListForm").find("#paymentsType").val(1);
			$("#receivable").text(pos.actualAmt);
			editInfo1 ("paymentsListDiv","付款",function(e){
				var that =$(this);
				var owed=$("#owed").text();
				setPpaymentArry();
				if(owed!=0){
					JY.Model.info("实收金额不等于应收金额");
					return false;
				}
				var params={list:paymentArry,type:1,status:e};
				 $.ajax({type:'POST',url:jypath+'/pos/payments/add',data:JSON.stringify(params),dataType:'json',contentType:"application/json",success:function(data,textStatus){  			        	 
					 	if(data.res==1){
			        		that.dialog("close");      
			        		JY.Model.info(data.resMsg,function(){search();});
			        	}else{
			        		 JY.Model.error(data.resMsg);
			        	}     	
			         }
			     });
			});
		}else{
			JY.Model.error("只有待付款状态才能付款！");
		}
	});
}

function cleanPay(){
	JY.Tags.cleanForm("paymentsListForm");
	$("#paymentsTable tbody").html("");
	$("#receivable").text(0);
	$("#owed").text(0);
}

function countWight(){
	var goldUllage = $("#oldGoldTab input[name='goldUllage']").val();
	var weight = $("#oldGoldTab input[name='weight']").val();
	
	if(goldUllage>=100){
		$("#oldGoldTab input[name='goldUllage']").tips({
	          msg: "耗损不能大于或等于100%!",
	          bg: '#FF2D2D',
	          time: 1
         });
		$("#oldGoldTab input[name='goldUllage']").val("");
		}
	if(goldUllage&&weight){
		 $("#oldGoldTab input[name='actualWt']").val((100-goldUllage)*weight/100);
	}
}

//加入旧金
$('#addOldGold').on('click', function(e) {
	if(JY.Validate.form("oldGoldTab")){
		var type = $("#materialnoteDiv input[name='type']:checked").val();
		var goldType = $("#oldGoldTab select[name='goldType']").val();
		var goldPrice = $("#oldGoldTab input[name='goldPrice']").val();
		var goldUllage = $("#oldGoldTab input[name='goldUllage']").val();
		var weight = $("#oldGoldTab input[name='weight']").val();
		var actualWt = $("#oldGoldTab input[name='actualWt']").val();
		var saleCost = $("#oldGoldTab input[name='saleCost']").val();
		var actualPrice = -(goldPrice*actualWt-saleCost*actualWt);
		var actualCost = saleCost*actualWt;
		var name = "";
		if(type=="3"){
			name="本号回收旧金";
		}
		if(type=="4"){
			name="外号回收旧金";
		}
		if(type=="5"){
			name="截号回收旧金";
		}
		var zong = 0;
		$('#posbillAdd').find('input[name="actualPrice"]').each(function(element,index){
			 if($(this).val()!=''){zong+=parseFloat($(this).val());}
		 });
		
		var jian = zong+actualPrice;
		if(jian<=0){
			JY.Model.info("金额不能小于等于0!"); 
			return false;
		}
		
		if(actualPrice>0){
			JY.Model.info("旧金金额不能小于等于0!"); 
			return false;
		}
		$("#posbillAddDiv").removeClass("hide");
		var html = "";
		html+="<tr>";
		html+="<td class='center'><label> <input type='checkbox' name='ids' class='ace' /><span class='lbl'></span></label><input type='hidden' name='type' value='"+type+"'/><input type='hidden' name='weight' value='"+weight+"'/><input type='hidden' name='actualWt' value='"+actualWt+"'/><input type='hidden' name='goldPrice' value='"+goldPrice+"'/><input type='hidden' name='goldUllage' value='"+goldUllage+"'/><input type='hidden' name='goldType' value='"+goldType+"'/></td>";
		html+="<td class='center hidden-480'><span name='barCode'></span></td>";
		html+="<td class='center hidden-480'><span name='name'>"+name+"</span></td>";
		html+="<td class='center hidden-480'><span name='price'>"+toFix(goldPrice)+"</span></td>";
		html+="<td style='padding:0px;'><input readonly class='center' onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' name='saleCost' value='"+toFix(actualCost)+"' style='width:100%;height:38px;border:none;'/></td>";
		html+="<td style='padding:0px;'><input readonly class='center' onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' name='promCoin' value='0' style='width:100%;height:38px;border:none;'/></td>";
		html+="<td style='padding:0px;'><input readonly class='center' onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' name='saleProm' value='0' style='width:100%;height:38px;border:none;'/></td>";
		html+="<td class='center'><label> <input readonly type='checkbox' name='gift' class='ace' /><span class='lbl'></span></label></td>";
		html+="<td style='padding:0px;'><input readonly class='center' jyValidate='required' type='text' name='count' value='1' style='width:100%;height:38px;border:none;'/></td>";
		html+="<td style='padding:0px;'><input readonly class='center' onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' name='actualPrice' value='"+toFix(actualPrice)+"' style='width:100%;height:38px;border:none;'/></td>";
		html+="</tr>";
		$("#posbillAdd tbody").append(html);
		countFoot();
	}
});

function changeGoldType(value){
	if(value){
		JY.Ajax.doRequest(null,jypath +'/pos/billing/getGoldPrice',{goldType:value},function(data){
			if(data.obj){
				var l = data.obj;
				$("#oldGoldTab input[name='goldPrice']").val(l.goldPrice);
			}
			$("#oldGoldTab input[name='goldUllage']").val(0);
		});
	}
}
//付款
$('#pay').on('click', function(e) {
	e.preventDefault();
	var chks =[];    
	$('#posbillTable input[name="ids"]:checked').each(function(){    
		chks.push($(this).val()); 
	});     
	if(chks.length==0) {
		JY.Model.info("您没有选择任何内容!"); 
	}else if(chks.length>1){
		JY.Model.info("请选择一条内容!"); 
	}else{	
		payMoney(chks[0])
	}
})

//以旧换新销售
$('#exchange').on('click', function(e) {
	if(JY.Validate.form("exchangeTab")){
		var type = $("#materialnoteDiv input[name='type']:checked").val();
		var barCode = $("#exchangeTab input[name='barCode']").val(); 
		var price = toFix($("#exchangeTab input[name='price']").val()); 
		var oldPrice = toFix($("#exchangeTab input[name='oldPrice']").val());
		var zhekou = $("#exchangeTab input[name='zhekou']").val();
		var actualPrice = toFix($("#exchangeTab input[name='actualPrice']").val());
		var weight = $("#exchangeTab input[name='weight']").val();
		var poundage = toFix($("#exchangeTab input[name='poundage']").val());
		var certUllage = toFix($("#exchangeTab input[name='certUllage']").val());
		var goldUllage = toFix($("#exchangeTab input[name='goldUllage']").val());
		var stoneUllage = toFix($("#exchangeTab input[name='stoneUllage']").val());
		var note = $("#exchangeTab textarea[name='note']").val();
		var barCode = $("#materialnoteDiv input[name='barCode']").val();
		actualPrice = -(actualPrice-poundage-certUllage-goldUllage-stoneUllage);
		var name = "以旧换新";
		var zong = 0;
		$('#posbillAdd').find('input[name="actualPrice"]').each(function(element,index){
			 if($(this).val()!=''){zong+=parseFloat($(this).val());}
		 });
		var jian = zong+actualPrice;
		if(jian<=0){
			JY.Model.info("金额不能小于等于0!"); 
			return false;
		}
		
		var html = "";
		html+="<tr>";
		html+="<td class='center'><label> <input type='checkbox' name='ids' class='ace' /><span class='lbl'></span></label><input type='hidden' name='type' value='"+type+"'/><input type='hidden' name='barCode' value='"+barCode+"'/><input type='hidden' name='actualWt' value='"+JY.Object.notEmpty(weight)+"'/><input type='hidden' name='poundage' value='"+JY.Object.notEmpty(poundage)+"'/><input type='hidden' name='certUllage' value='"+JY.Object.notEmpty(certUllage)+"'/><input type='hidden' name='goldUllage' value='"+JY.Object.notEmpty(goldUllage)+"'/><input type='hidden' name='stoneUllage' value='"+JY.Object.notEmpty(stoneUllage)+"'/><input type='hidden' name='note' value='"+JY.Object.notEmpty(note)+"'/></td>";
		html+="<td class='center hidden-480'><span name='barCode'>"+barCode+"</span></td>";
		html+="<td class='center hidden-480'><span name='name'>"+name+"</span></td>";
		html+="<td class='center hidden-480'><span name='price'>"+toFix(price)+"</span></td>";
		html+="<td class='formdetail'><input class='center' readonly onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' name='saleCost' value='"+toFix(poundage)+"' style='width:100%;height:30px;border:none;'/></td>";
		html+="<td class='formdetail'><input class='center' readonly onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' name='promCoin' value='0' style='width:100%;height:30px;border:none;'/></td>";
		html+="<td class='formdetail'><input class='center' readonly onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' name='saleProm' value='0' style='width:100%;height:30px;border:none;'/></td>";
		html+="<td class='center'><label> <input type='checkbox' name='gift' class='ace' /><span class='lbl'></span></label></td>";
		html+="<td class='formdetail'><input class='center' readonly jyValidate='required' type='text' name='count' value='1' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
		html+="<td class='formdetail'><input class='center' readonly onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' name='actualPrice' value='"+toFix(actualPrice)+"' style='width:100%;height:30px;border:none;'/></td>";
		html+="</tr>";
		$("#posbillAdd tbody").append(html);
		countFoot();
	}
});

function cleanForm(){
	JY.Tags.cleanForm("posbillTab");
	JY.Tags.cleanForm("oldGoldTab");
	JY.Tags.cleanForm("exchangeTab");
	
	$("#oldGoldTab").addClass("hide");
	$("#exchangeTab").addClass("hide");
}

//销售开单
$('#addBtn').on('click', function(e) {
	cleanForm();
	setOrg();
	assistants1=getSelect(jypath +'/pos/billing/getAssistant',assistants1,"selectAssistant1");
	assistants2=getSelect(jypath +'/pos/billing/getAssistant',assistants2,"selectAssistant2");
	leaders1=getSelect(jypath +'/pos/billing/getAssistant',leaders1,"selectLeader1");
	leaders2=getSelect(jypath +'/pos/billing/getAssistant',leaders2,"selectLeader2");
	stores=getSelect(jypath +'/pos/billing/getStore',stores,"selectStore");
	$("#selectLeader1").val("");
	$("#materialnoteDiv input[name='type']").prop("checked",false);
	$("#btnDiv input[name='barCode']").val("");
	$("#btnDiv input[name='barCode']").show();
	$("#selectStore").val(curUser.orgId);
	$("#posbillTab input[name='promrate1']").val(1);
	$("#posbillTab input").removeAttr("disabled","disabled");
	$("#posbillTab select").removeAttr("disabled","disabled");
	$("#posbillTab textarea").removeAttr("disabled","disabled");
	$("#posbillTab input[name='cashierName']").attr("disabled","disabled");
	$("#posbillTab input[name='saleTime']").attr("disabled","disabled");
	$(".btnClass").removeClass("hide");
	$("#materialnoteDiv").removeClass("hide");
	$("#offsetDiv textarea[name='offsetcause']").removeAttr("jyValidate","required");
	$("#offsetDiv").addClass("hide");
	
	$("#posbillAdd tbody").empty();
	$("#posbillAdd tfoot").empty();
	$("#posbillAddDiv").css("height","300px");
	baseInfo({id:"posbillDiv",title:"销售开单",height:"650",width:"1150",savefn:function(e){
		var that =$(this);
		if(JY.Validate.form("posbillForm")){
			
			if($("#posbillTab input[name='actualAmt']").val()!=parseFloat($("#posbillAdd #actualPrices").text())){
				$("#posbillTab input[name='actualAmt']").tips({
		            msg: "收款金额和金额不同!",
		            bg: '#FF2D2D',
		            time: 1
		        });
				return false;
			}
			
			var json_data="",bills="";
			var totalCount=0,totalWt = 0,getIntegral=0,payIntegral=0;
	 	    var last_index=$('#posbillAdd tbody tr').length-1;
	 	    var cashier = $("#posbillTab input[name='cashier']").val();
	 	    var customer = $("#posbillTab input[name='customer']").val();
	 	    var vipCode = $("#posbillTab input[name='vipCode']").val();
	 	    var assistant1 = $("#posbillTab select[name='assistant1']").val();
	 	    var promrate1 = $("#posbillTab input[name='promrate1']").val();
	 	    var leader1 = $("#posbillTab select[name='leader1']").val();
	 	    var saleTime = $("#posbillTab input[name='saleTime']").val();
	 	    var assistant2 = !$("#posbillTab select[name='assistant2']").val()?"":$("#posbillTab select[name='assistant2']").val();
	 	    var promrate2 = $("#posbillTab input[name='promrate2']").val();
	 	    var leader2 = !$("#posbillTab select[name='leader2']").val()?"":$("#posbillTab select[name='leader2']").val();
	 	    var actualAmt = $("#posbillTab input[name='actualAmt']").val();
	 	    var earnest = $("#posbillTab input[name='earnest']").val();
	 	    var barCode = $("#btnDiv input[name='barCode']").val();
	 	    var note = !$("#posbillTab textarea[name='note']").val()?"":$("#posbillTab textarea[name='note']").val();
	 	    var notes = !$("#posbillTab textarea[name='note']").val()?"":$("#posbillTab textarea[name='note']").val();
	 	    var storeId = $("#posbillTab select[name='storeId']").val();
	 	    var saleAmt = $("#posbillAdd #actualPrices").text();
	 	    var orgCode = curUser.orgRootCode;
	 	    if(saleAmt<=0){
	 	    	JY.Model.info("总额不能小于等于0!"); 
	 	    	return false;
	 	    }
	 	    var bussiType = "1";
	 	    var status = e;
	 	    
	 	    var data='{"cashier":"'+cashier+'","orgCode":"'+orgCode+'","customer":"'+customer+'","bussiType":"'+bussiType+'","saleAmt":"'+saleAmt+'","vipCode":"'+vipCode+'","assistant1":"'+assistant1+'","promrate1":"'+promrate1+'","leader1":"'+leader1+'","saleTime":"'+saleTime+'","assistant2":"'+assistant2+'","promrate2":"'+promrate2+'","leader2":"'+leader2+'","actualAmt":"'+actualAmt+'","earnest":"'+earnest+'","notes":"'+note+'","storeId":"'+storeId+'","status":"'+status+'"}';
	 	    posbill='['+data+']';
	 	    var gift = 0;
	 	    $('#posbillAdd tbody tr').each(function(element,index){
	 	    	var barCode = $(this).find("span[name='barCode']").text();
	 	    	var name = $(this).find("span[name='name']").text();
	 	    	var price = $(this).find("span[name='price']").text();
	 	    	var saleCost = toFix($(this).find("input[name='saleCost']").val());
	 	    	var promCoin = toFix($(this).find("input[name='promCoin']").val());
	 	    	var saleProm = toFix($(this).find("input[name='saleProm']").val());
	 	    	if($(this).find('input[name="gift"]').is(':checked')){
	 	    		gift = 1;
	 	    	}
	 	    	var count = $(this).find("input[name='count']").val();
	 	    	var actualPrice = toFix($(this).find("input[name='actualPrice']").val());
	 	    	
	 	    	var type = $(this).find("input[name='type']").val();
	 	    	var saleTime = $("#posbillTab input[name='saleTime']").val();
	 	    	var saleCost = toFix($(this).find("input[name='saleCost']").val());
	 	    	var goldPrice = toFix($(this).find("input[name='goldPrice']").val());
	 	    	var goldUllage = toFix($(this).find("input[name='goldUllage']").val());
	 	    	var stoneUllage = toFix($(this).find("input[name='stoneUllage']").val());
	 	    	var certUllage = toFix($(this).find("input[name='certUllage']").val());
	 	    	var poundage = toFix($(this).find("input[name='poundage']").val());
	 	    	var remarks = !$(this).find("textarea[name='remarks']").val()?"":$(this).find("span[name='remarks']").val();
	 	    	var note = !$(this).find("textarea[name='note']").val()?"":$(this).find("textarea[name='note']").val();
	 	    	var goldType = !$(this).find("input[name='goldType']").val()?"":$(this).find("span[name='goldType']").val();
	 	    	var weight = $(this).find("input[name='weight']").val();
	 	    	var actualWt = $(this).find("input[name='actualWt']").val();
	 	    	var coin = $(this).find("input[name='coin']").val();
	 	    	var coinChange = 1;
	 	    	
	 	    	var detail = '{"barCode":"'+barCode+'","weight":"'+weight+'","actualWt":"'+actualWt+'","coin":"'+coin+'","coinChange":"'+coinChange+'","name":"'+name+'","price":"'+price+'","promCoin":"'+promCoin+'","saleProm":"'+saleProm+'","gift":"'+gift+'","count":"'+count+'","actualPrice":"'+actualPrice+'","type":"'+type+'","saleTime":"'+saleTime+'","saleCost":"'+saleCost+'","goldPrice":"'+goldPrice+'","goldUllage":"'+goldUllage+'","stoneUllage":"'+stoneUllage+'","certUllage":"'+certUllage+'","poundage":"'+poundage+'","remarks":"'+remarks+'","note":"'+note+'","goldType":"'+goldType+'"}';
	 	    	
	 	    	if(vipCode){
	 	    		getIntegral+=coin;
	 	    		payIntegral+=coinChange;
	 	    	}
	 	    	
	 	    	if($(this).index()==last_index){
		        	json_data+=detail;
		        }else{
		        	json_data+=detail+',';
		        };
		    });
	 	    
	 	   json_data='['+json_data+']';
	        
	 	   if(vipCode){
	 		  var bill='{"getIntegral":"'+getIntegral+'","payIntegral":"'+payIntegral+'","cardNo":"'+vipCode+'","types":"0","payAmount":"'+saleAmt+'","status":"'+status+'","note":"'+notes+'"}';
		 	  bills='['+bill+']';
	 	   }
	 	   
	        JY.Ajax.doRequest(null,jypath +'/pos/billing/insert',{posbill:posbill.toString(),detail:json_data.toString(),bills:bills.toString()},function(data){
	        	if(data.res==1){
	        		that.dialog("close");      
	        		JY.Model.info(data.resMsg,function(){search();});
	        	}else{
	        		 JY.Model.error(data.resMsg);
	        	}     	
				JY.Model.info(data.resMsg,function(){search();});
			}); 
		}
	}})	
});

//销售冲单
$('#editBtn').on('click', function(e) {
	var chks =[];    
	$('#posbillTable input[name="ids"]:checked').each(function(){    
		chks.push($(this).val());    
	}); 
	if(chks.length==0){
		JY.Model.info("您没有选择任何内容!"); 
	}else if(chks.length>1){
		JY.Model.info("请选择一条内容!"); 
	}else{
		var id = chks[0];
		cleanForm();
		setOrg();
		$("#materialnoteDiv input[name='type']").prop("checked",false);
		$("#btnDiv input[name='barCode']").val("");
		$("#btnDiv input[name='barCode']").show();
//		$("#selectStore").val(curUser.orgId);
		$("#posbillTab input[name='promrate1']").val(1);
		$("#posbillTab input").removeAttr("disabled","disabled");
		$("#posbillTab select").removeAttr("disabled","disabled");
		$("#posbillTab textarea").removeAttr("disabled","disabled");
		$("#posbillTab input[name='cashierName']").attr("disabled","disabled");
		$("#posbillTab input[name='saleTime']").attr("disabled","disabled");
		$("#offsetDiv textarea[name='offsetcause']").removeAttr("disabled","disabled");
		$("#offsetDiv textarea[name='offsetcause']").attr("jyValidate","required");
		$(".btnClass").removeClass("hide");
		$("#materialnoteDiv").removeClass("hide");
		$("#offsetDiv").removeClass("hide");
		$("#posbillAdd tbody").empty();
		$("#posbillAdd tfoot").empty();
		$("#posbillAddDiv").css("height","400px");
		JY.Ajax.doRequest(null,jypath +'/pos/billing/find',{id:id},function(data){ 
			if(data.obj.pos.status!=9){
				JY.Model.info("只有已付款状态才能冲单!"); 
				return false;
			}else if(data.obj.pos.originalNo){
				JY.Model.info("该单已经红冲!"); 
				return false;
			}else{
				setForm(data); 
				$("#btnDiv input[name='barCode']").hide();
				$("#materialnoteDiv").addClass("hide");
				$("#posbillTab input").attr("disabled","disabled")
				$("#posbillTab select").attr("disabled","disabled")
				$("#posbillTab textarea").attr("disabled","disabled")
				$("#posbillAddDiv").css("height","440px");
				
				baseInfo({id:"posbillDiv",title:"冲单",height:"750",width:"1150",savefn:function(e){
					var offsetcause = $("#offsetDiv textarea[name='offsetcause']").val();
					var that =$(this);
			        JY.Ajax.doRequest(null,jypath +'/pos/billing/offset',{id:id,offsetcause:offsetcause},function(data){
			        	if(data.res==1){
			        		that.dialog("close");      
			        		JY.Model.info(data.resMsg,function(){search();});
			        	}else{
			        		 JY.Model.error(data.resMsg);
			        	}     	
						JY.Model.info(data.resMsg,function(){search();});
					}); 
				}})	
			}
		});
	}
});



//定金单
function subPur(value){
	var flag = true;
	if(value){
		$('#posbillAdd tbody tr').each(function(element,index){
			
			if(value.trim()==$(this).find("input[name='earnest']").val()){
				$("#posbillTab input[name='earnest']").tips({
		            msg: "该定金单已存在!",
		            bg: '#FF2D2D',
		            time: 1
		        });
				flag = false;
				return flag;
			}
		})
		if(flag){
			JY.Ajax.doRequest(null,jypath +'/pos/billing/getEarnest',{earnest:value.trim()},function(data){
				var l = data.obj.posbill;
				if(l){
					$("#posbillAddDiv").removeClass("hide");
					var html = "";
					html+="<tr>";
					html+="<td class='center'><label> <input type='checkbox' name='ids' class='ace' /><span class='lbl'></span></label><input type='hidden' name='type' value='6'/><input type='hidden' name='earnest' value='"+value.trim()+"'/></td>";
					html+="<td></td>";
					html+="<td class='center hidden-480'><span name='name'>定金</span></td>";
					html+="<td class='center hidden-480'><span name='price'>"+toFix(l.actualAmt)+"</span></td>";
					html+="<td class='formdetail'><input class='center' readonly onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' name='saleCost' value='"+toFix(l.poundage)+"' style='width:100%;height:30px;border:none;'/></td>";
					html+="<td class='formdetail'><input class='center' readonly onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' name='promCoin' value='0' style='width:100%;height:30px;border:none;'/></td>";
					html+="<td class='formdetail'><input class='center' readonly onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' name='saleProm' value='0' style='width:100%;height:30px;border:none;'/></td>";
					html+="<td class='center'><label> <input type='checkbox' name='gift' class='ace' /><span class='lbl'></span></label></td>";
					html+="<td class='formdetail'><input class='center' readonly jyValidate='required' type='text' name='count' value='1' style='width:100%;height:30px;border:none;'/></td>";
					html+="<td class='formdetail'><input class='center' readonly onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' name='actualPrice' value='"+-toFix(l.actualAmt)+"' style='width:100%;height:30px;border:none;'/></td>";
					html+="</tr>";
					$("#posbillAdd tbody").append(html);
					countFoot();
				}else{
					$("#posbillTab input[name='earnest']").tips({
			            msg: "该定金单不存在!",
			            bg: '#FF2D2D',
			            time: 1
			        });
					return false;
				}
			});
		}
		
	}
}

//删除明细
function delposbill(){
	$("#jyConfirm").children().children().find(".causesDiv").remove();
	var chks =[];    
	$('#posbillAdd input[name="ids"]:checked').each(function(){ 
		chks.push($(this).val());    
	});     
	if(chks.length==0) {
		JY.Model.info("您没有选择任何内容!");
	}else{
		JY.Model.confirm("确认要删除选中的数据吗?",function(){	
			$('#posbillAdd input[name="ids"]:checked').each(function(){  
				$(this).parent().parent().parent().remove();
				countFoot();
			}); 
		});		
	}	
}

function cleanposbillForm(){
	JY.Tags.cleanForm("posbillDiv");
	JY.Tags.cleanForm("posbillForm");
	$("#materialnoteDiv span").text("");
	$("#materialnoteDiv textarea").val("");
}

function find(id){
	JY.Ajax.doRequest(null,jypath +'/pos/billing/find',{id:id},function(data){ 
		setForm(data); 
		$("#btnDiv input[name='barCode']").hide();
		$("#materialnoteDiv").addClass("hide");
		$("#posbillTab input").attr("disabled","disabled")
		$("#posbillTab select").attr("disabled","disabled")
		$("#posbillTab textarea").attr("disabled","disabled")
		$("#offsetDiv").addClass("hide");
		$("#posbillAddDiv").css("height","440px");
		$("#offsetDiv textarea[name='offsetcause']").attr("disabled","disabled");
		$("#offsetDiv textarea[name='offsetcause']").removeAttr("jyValidate","required");
		if(data.obj.pos.status==4){
			$("#offsetDiv").removeClass("hide");
			$("#offsetDiv textarea[name='offsetcause']").val(data.obj.pos.offsetcause);
			viewInfo({id:"posbillDiv",title:"查看销售",height:"730",width:"1150"});
		}else{
			viewInfo({id:"posbillDiv",title:"查看销售",height:"700",width:"1150"});
		}
		
	});
}

function viewInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,
		buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
}

function baseInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
    	buttons:[{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;提交","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,3);}}},
    	         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}

function addInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
    	buttons:[{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;提交","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},
    	         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}
//function setData(data){
//	$(("#posbillTab input")).each(function(){
//		var name = $(this).prop("name");
//		$(this).val(data.name);
//	})
//}

/*查看明细*/
function setForm(data){
	 $(".btnClass").addClass("hide");
	 $("#posbillAdd tbody").empty();
	 $("#posbillAdd tfoot").empty();
	 cleanForm();
	 var pos = data.obj.pos;
	 $("#posbillTab input[name='cashier']").val(pos.cashier);
	 $("#posbillTab input[name='cashierName']").val(pos.cashierName);
	 $("#posbillTab input[name='customer']").val(pos.customer);
	 $("#posbillTab input[name='vipCode']").val(pos.vipCode);
	 $("#posbillTab select[name='assistant1']").val(pos.assistant1);
	 $("#posbillTab select[name='leader1']").val(pos.leader1);
	 $("#posbillTab select[name='assistant2']").val(pos.assistant2);
	 $("#posbillTab select[name='leader2']").val(pos.leader2);
	 
	 if(!$("#posbillTab select[name='assistant1']").val()){
		var re = document.getElementById("selectAssistant1");
		var op = new Option(pos.assistantName1, pos.assistant1);
		re.options.add(op);
		$("#posbillTab select[name='assistant1']").val(pos.assistant1);
	}
	 if(!$("#posbillTab select[name='leader1']").val()){
			var re = document.getElementById("selectLeader1");
			var op = new Option(pos.leaderName1, pos.leader1);
			re.options.add(op);
			$("#posbillTab select[name='leader1']").val(pos.leader1);
		}
	 if(pos.assistant2){
		 if(!$("#posbillTab select[name='assistant2']").val()){
			var re = document.getElementById("selectAssistant2");
			var op = new Option(pos.assistantName2, pos.assistant2);
			re.options.add(op);
			$("#posbillTab select[name='assistant2']").val(pos.assistant2);
		}
	 }
	 if(pos.leader2){
		 if(!$("#posbillTab select[name='leader2']").val()){
				var re = document.getElementById("selectLeader2");
				var op = new Option(pos.leaderName2, pos.leader2);
				re.options.add(op);
				$("#posbillTab select[name='leader2']").val(pos.leader2);
			}
	 }
	 
	 
	 $("#posbillTab input[name='promrate1']").val(pos.promrate1);
	 $("#posbillTab input[name='saleTime']").val(JY.Date.Format(pos.saleTime,"yyyy-MM-dd"));
	 $("#posbillTab input[name='promrate2']").val(pos.promrate2);
	 $("#posbillTab input[name='actualAmt']").val(toFix(pos.actualAmt));
	 $("#posbillTab input[name='earnest']").val(pos.earnest);
	 $("#posbillTab textarea[name='note']").val(pos.notes);
	 $("#posbillTab select[name='storeId']").val(pos.storeId);
	 if(!$("#posbillTab select[name='storeId']").val()){
		var re = document.getElementById("selectStore");
		var op = new Option(pos.storeName, pos.storeId);
		re.options.add(op);
		$("#posbillTab select[name='storeId']").val(pos.storeId);
	}
	 $("#btnDiv input[name='barCode']").hide();
	 var results=data.obj.pos.posBillDetails;
	 if(results!=null&&results.length>0){
		 var html="";
		 for(var i = 0;i<results.length;i++){
			 var l=results[i];
			$("#posbillAddDiv").removeClass("hide");
			var html = "";
			html+="<tr>";
			html+="<td class='center'><label> <input type='checkbox' name='ids' class='ace' /><span class='lbl'></span></label><input type='hidden' name='type' value='1'/><input type='hidden' name='weight' value='"+l.weight+"'/><input type='hidden' name='actualWt' value='"+l.actualWt+"'/><input type='hidden' name='remarks' value='"+l.remarks+"'/><input type='hidden' name='note' value='"+l.note+"'/></td>";
			html+="<td class='center hidden-480'><span name='barCode'>"+JY.Object.notEmpty(l.barCode)+"</span></td>";
			html+="<td class='center hidden-480'><span name='name'>"+l.name+"</span></td>";
			html+="<td class='center hidden-480'><span name='price'>"+toFix(l.price)+"</span></td>";
			html+="<td class='center hidden-480'><span name='saleCost'>"+toFix(l.saleCost)+"</span></td>";
			html+="<td class='center hidden-480'><span name='promCoin'>"+toFix(l.promCoin)+"</span></td>";
			html+="<td class='center hidden-480'><span name='saleProm'>"+toFix(l.saleProm)+"</span></td>";
			html+="<td class='center'><label> <input type='checkbox' name='gift' class='ace' /><span class='lbl'></span></label></td>";
			html+="<td class='center hidden-480'><span name='count'>"+l.count+"</span></td>";
			html+="<td class='center hidden-480'><span name='actualPrice'>"+toFix(l.actualPrice)+"</span></td>";
			html+="</tr>";
			$("#posbillAdd tbody").append(html);
			countFootBy();
			$("#posbillAdd input").attr("disabled","disabled");
		 }
	 }	 
}


//合计
function countFootBy(){
	var foot = "";
	$('#posbillAdd tfoot').html("");
	var prices=0,saleCosts=0,promCoins=0,saleProms=0,counts=0,actualPrices=0;
	 $('#posbillAdd').find('span[name="price"]').each(function(element,index){
		 if($(this).text()!=''){prices+=parseFloat($(this).text());}
	 });
	 $('#posbillAdd').find('span[name="saleCost"]').each(function(element,index){
		 if($(this).text()){saleCosts+=parseFloat($(this).text());}
	 });
	 $('#posbillAdd').find('span[name="promCoin"]').each(function(element,index){
		 if($(this).text()!=''){promCoins+=parseFloat($(this).text());}
	 });
	 $('#posbillAdd').find('span[name="saleProm"]').each(function(element,index){
		 if($(this).text()!=''){saleProms+=parseFloat($(this).text());}
	 });
	 $('#posbillAdd').find('span[name="count"]').each(function(element,index){
		 if($(this).text()!=''){counts+=parseFloat($(this).text());}
	 });
	 
	 $('#posbillAdd').find('span[name="actualPrice"]').each(function(element,index){
		 if($(this).text()!=''){actualPrices+=parseFloat($(this).text());}
	 });
	 
	 foot+="<tr>";
	 foot+="<td class='center'>合计</td>";
	 foot+="<td class='center'></td>";
	 foot+="<td class='center'></td>";
	 foot+="<td class='center'><span id='prices'>"+toFix(prices)+"</span></td>";
	 foot+="<td class='center'><span id='saleCosts'>"+toFix(saleCosts)+"</span></td>";
	 foot+="<td class='center'><span id='promCoins'>"+toFix(promCoins)+"</span></td>";
	 foot+="<td class='center'><span id='saleProms'>"+toFix(saleProms)+"</span></td>";
	 foot+="<td class='center'></td>";
	 foot+="<td class='center'><span id='counts'>"+counts+"</span></td>";
	 foot+="<td class='center'><span id='actualPrices'>"+toFix(actualPrices)+"</span></td>";
	 foot+="</tr>";
	 $("#posbillAdd tfoot").append(foot);
}

/*修改明细*/
function setUpdateForm(data){
	 commForm(data);
	 var jsonData =""; 
	 $("#posbillAdd tbody").empty();
	 $("#posbillAdd tfoot").empty();
	 var counts=0,sumRequireWt=0,sumGoldWt=0,sumStoneWt=0,sumBasicCost=0,sumAddCost=0,sumOtherCost=0,sumCostPrice=0;
	 var results=data.obj.list;
	 if(results!=null&&results.length>0){
		 var html="";
		 for(var i = 0;i<results.length;i++){
			 var l=results[i];
			 html+="<tr>";
			 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /><span class='lbl'></span></label></td>";
			 html+="<td class='center hidden-480'>"+l.name+"</td>";
			 html+="<td style='padding:0px;'> <select id='goldType"+(i+1)+"'name='goldType' jyValidate='required' style='width:100%;'>"+options(goldTypes,l.goldType)+"</select></td>";
			 html+="<td class='center hidden-480'>"+l.count+"<input type='hidden' name='count' value='"+l.count+"'/></td>";
			 html+="<td class='center hidden-480 hide'><input type='hidden' name='noticeId' value='"+l.noticeId+"'/></td>";
			 html+="<td class='center hidden-480 hide'><input type='hidden' name='detailId' value='"+l.id+"'/></td>"
			 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' onBlur='addNum()' name='requireWt' value='"+l.requireWt.toFixed(4)+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
			 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' type='text' onBlur='addNum()' name='goldWt' value='"+JY.Object.notEmpty(l.goldWt.toFixed(4))+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
			 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' type='text' onBlur='addNum()' name='stoneWt' value='"+JY.Object.notEmpty(l.stoneWt.toFixed(4))+"' style='width:100%;height:30px;border:1px solid #ccc;'/><input type='hidden' name='stoneUnit' vlaue='"+JY.Object.notEmpty(l.stoneUnit)+"'/></td>";
			 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' type='text' onBlur='addNum()' name='basicCost' value='"+JY.Object.notEmpty(l.basicCost.toFixed(4))+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
			 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' type='text' onBlur='addNum()' name='addCost' value='"+JY.Object.notEmpty(l.addCost.toFixed(4))+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
			 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' type='text' onBlur='addNum()' name='otherCost'  value='"+JY.Object.notEmpty(l.otherCost.toFixed(4))+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
			 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' onBlur='addNum()' name='costPrice' value='"+l.costPrice.toFixed(4)+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
			 html+="</tr>";
		 }
		 $("#posbillAdd tbody").append(html);
		 countFoot();
		 $("#posbillForm input").removeAttr("disabled","disabled");
	 }	 
	 return jsonData;
	 
}


//合计
function countFoot(){
	var foot = "";
	$('#posbillAdd tfoot').html("");
	var prices=0,saleCosts=0,promCoins=0,saleProms=0,counts=0,actualPrices=0;
	 $('#posbillAdd').find('span[name="price"]').each(function(element,index){
		 if($(this).text()!=''){prices+=parseFloat($(this).text());}
	 });
	 $('#posbillAdd').find('input[name="saleCost"]').each(function(element,index){
		 if($(this).val()){saleCosts+=parseFloat($(this).val());}
	 });
	 $('#posbillAdd').find('input[name="promCoin"]').each(function(element,index){
		 if($(this).val()!=''){promCoins+=parseFloat($(this).val());}
	 });
	 $('#posbillAdd').find('input[name="saleProm"]').each(function(element,index){
		 if($(this).val()!=''){saleProms+=parseFloat($(this).val());}
	 });
	 $('#posbillAdd').find('input[name="count"]').each(function(element,index){
		 if($(this).val()!=''){counts+=parseFloat($(this).val());}
	 });
	 
	 $('#posbillAdd').find('input[name="actualPrice"]').each(function(element,index){
		 if($(this).val()!=''){actualPrices+=parseFloat($(this).val());}
	 });
	 
	 foot+="<tr>";
	 foot+="<td class='center'>合计</td>";
	 foot+="<td class='center'></td>";
	 foot+="<td class='center'></td>";
	 foot+="<td class='center'><span id='prices'>"+toFix(prices)+"</span></td>"; 
	 foot+="<td class='center'><span id='saleCosts'>"+toFix(saleCosts)+"</span></td>";
	 foot+="<td class='center'><span id='promCoins'>"+toFix(promCoins)+"</span></td>";
	 foot+="<td class='center'><span id='saleProms'>"+toFix(saleProms)+"</span></td>";
	 foot+="<td class='center'></td>";
	 foot+="<td class='center'><span id='counts'>"+counts+"</span></td>";
	 foot+="<td class='center'><span id='actualPrices'>"+toFix(actualPrices)+"</span></td>";
	 foot+="</tr>";
	 $("#posbillAdd tfoot").append(foot);
}

function subVip(value){
	var flag = true;
	if(value){
		JY.Ajax.doRequest(null,jypath +'/pos/billing/getVip',{cardNo:value.trim()},function(data){
			var l = data.obj;
			if(l){
				$("#posbillTab input[name='customer']").val(l.name);
			}else{
				$("#posbillTab input[name='vipCode']").val("");
				$("#posbillTab input[name='customer']").val("");
				JY.Model.confirm("未找到对应的会员信息，是否注册会员?",function(){
					addInfo({id:"membersDiv",title:"注册会员信息",height:"350",width:"680",savefn:function(e){
						var that =$(this);
						var cardNo = $(this).find("input[name='cardNo']").val();
						var name = $(this).find("input[name='name']").val();
						JY.Ajax.doRequest("membersForm",jypath +'/scm/members/add',null,function(data){  			        	 
						 	if(data.res==1){
						 		$("#posbillTab input[name='vipCode']").val(cardNo);
						 		$("#posbillTab input[name='customer']").val(name);
				        		that.dialog("close");      
				        		JY.Model.info(data.resMsg,function(){search();});
				        	}else{
				        		 JY.Model.error(data.resMsg);
				        	}     	
					     });
					}})
				})
			}
		});
	}
}

