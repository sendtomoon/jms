$(function(){
	//下拉框
	JY.Dict.setSelect("selectBillsStatus","POS_BILLING_STATUS",2,"全部");
	getbaseList(1);
})


/*查询列表页*/
function search(){
	$("#searchBtn").trigger("click");
}

function toFix(value){
	var zero = 0;
	return !value?zero:parseFloat(value);
}

/*我的订单列表*/
function getbaseList(init){
	$("#billsBaseForm input[name='orgId']").val(curUser.orgId);
	if(init==1)$("#billsBaseForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("billsBaseForm",jypath +'/crm/bills/findByPage',null,function(data){
		 $("#billsTable tbody").empty();
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
            		 html+="<td class='center'><label> <input type='checkbox' name='id' value='"+l.id+"' class='ace'/> <span class='lbl'></span></label></td>";
            		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
            		 html+="<td class='center hidden-480' ><a style='cursor:pointer;' onclick='find(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.orderNo)+"</a></td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.cardNo)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.payAmount)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.payIntegral)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.getIntegral)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.createTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
            		 if(l.types==0){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>销售订单</span></td>";
            		 }
            		 if(l.types==1){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>积分兑换订单</span></td>";
            		 }
            		 
            		 if(l.status==0){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>草稿</span></td>";
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
        		 $("#billsTable tbody").append(html);
        		 JY.Page.setPage("billsTable","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='19' class='center'>没有相关数据</td></tr>";
        		$("#billsTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
    		 JY.Model.loadingClose();
	 });
}

//删除我的订单
$('#delBtn').on('click', function(e) {
	//通知浏览器不要执行与事件关联的默认动作		
	e.preventDefault();
	var chks =[];    
	$('#billsTable input[name="id"]:checked').each(function(){    
		chks.push($(this).val());    
	});     
	if(chks.length==0) {
		JY.Model.info("您没有选择任何内容!"); 
	}else{
		JY.Model.confirm("确认要删除选中的数据吗?",function(){	
			JY.Ajax.doRequest(null,jypath +'/crm/bills/delete',{chks:chks.toString()},function(data){
				JY.Model.info(data.resMsg,function(){search();});
			});
		});		
	}		
});

//查看我的订单
$('#viewBtn').on('click', function(e) {
	var chks =[];    
	$('#billsTable input[name="id"]:checked').each(function(){
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

function find(id){
	JY.Ajax.doRequest(null,jypath +'/crm/bills/find',{id:id},function(data){
		setForm(data); 
		$("#btnDiv input[name='barCode']").hide();
		$("#materialnoteDiv").addClass("hide");
		$("#posbillTab input").attr("disabled","disabled");
		$("#posbillTab select").attr("disabled","disabled");
		$("#posbillTab textarea").attr("disabled","disabled");
		$("#offsetDiv").addClass("hide");
		$("#offsetDiv textarea[name='offsetcause']").attr("disabled","disabled");
		$("#offsetDiv textarea[name='offsetcause']").removeAttr("jyValidate","required");
		if(data.obj.status==4){
			$("#offsetDiv").removeClass("hide");
			$("#offsetDiv textarea[name='offsetcause']").val(data.obj.pos.offsetcause);
			viewInfo({id:"posbillDiv",title:"查看我的订单",height:"730",width:"1150"});
		}else{
			viewInfo({id:"posbillDiv",title:"查看我的订单",height:"700",width:"1150"});
		}
		
	});
}

function viewInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,
		buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
}


/*查看明细*/
function setForm(data){
	 $("#posbillAdd tbody").empty();
	 $("#posbillAdd tfoot").empty();
	 cleanForm();
	 var pos = data.obj;
	 $("#posbillTab input[name='cashier']").val(pos.cashier);
	 $("#posbillTab input[name='cashierName']").val(pos.cashierName);
	 $("#posbillTab input[name='customer']").val(pos.customer);
	 $("#posbillTab input[name='vipCode']").val(pos.vipCode);
	 $("#posbillTab input[name='assistant1']").val(pos.assistantName1);
	 $("#posbillTab input[name='leader1']").val(pos.leaderName1);
	 $("#posbillTab input[name='promrate1']").val(pos.promrate1);
	 $("#posbillTab input[name='assistant2']").val(pos.assistantName2);
	 $("#posbillTab input[name='leader2']").val(pos.leaderName2);
	 $("#posbillTab input[name='promrate2']").val(pos.promrate2);
	 $("#posbillTab input[name='saleTime']").val(JY.Date.Format(pos.saleTime,"yyyy-MM-dd"));
	 $("#posbillTab input[name='actualAmt']").val(toFix(pos.actualAmt));
	 $("#posbillTab input[name='earnest']").val(pos.earnest);
	 $("#posbillTab input[name='storeId']").val(pos.storeName);
	 $("#posbillTab textarea[name='note']").val(pos.notes);
	 var results=data.obj.posBillDetails;
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
			$("#posbillAdd input").attr("disabled","disabled");
			countFoot();
		 }
	 }	 
}

//合计
function countFoot(){
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
	 foot+="<td class='center'><span id='counts'>"+toFix(counts)+"</span></td>";
	 foot+="<td class='center'><span id='actualPrices'>"+toFix(actualPrices)+"</span></td>";
	 foot+="</tr>";
	 $("#posbillAdd tfoot").append(foot);
}


function cleanForm(){
	JY.Tags.cleanForm("posbillTab");
}