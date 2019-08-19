$(function(){
	initOrgSelectDataOut();
	setBaseData();
	getbaseList(1);
	JY.Dict.setSelect("selectisValid","POS_EARNEST_STATUS",2,"全部");
	/*增加回车事件*/
	$("#earnestBaseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	$("#dateRender1").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd',
		changeYear: true,
		changeMonth:true,
		defaultViewDate:new Date(),
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
	$("#dateRender3").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd',
		changeYear: true,
		changeMonth:true,
		minDate:new Date(),
		onSelect:function(dateText, inst) { }
	});
	$("#dateRender4").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd',
		changeYear: true,
		changeMonth:true,
		minDate:new Date(),
		onSelect:function(dateText, inst) { }
	});
	$("#dateRender5").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd',
		changeYear: true,
		changeMonth:true,
		minDate:new Date(),
		onSelect:function(dateText, inst) { }
	});
	
	/*1：先填写公共的原料入库*/
	$('#add').on('click', function(e) {
		e.preventDefault();
		cleanAdd();
		baseInfo({id:"earnsdtDiv",title:"新增定金单",height:"520",width:"824",savefn:function(){
			if(JY.Validate.form("addForm")){
				 var that =$(this);
				 JY.Ajax.doRequest("addForm",jypath +'/pos/earnestOrder/add',null,function(data){
					 JY.Model.confirm("是否进行付款？",function(){
						paymentLeng=1;
						cleanPay()
						$("#paymentsListForm").find("#paymentsOrderId").val(data.obj.id);
						$("#paymentsListForm").find("#paymentsOrderNo").val(data.obj.orderNo);
						$("#paymentsListForm").find("#paymentsType").val(2);
						$("#receivable").text(data.obj.amount);
						$("#owed").text(data.obj.amount);
						editInfo1 ("paymentsListDiv","付款",function(){
							if(JY.Validate.form("paymentsListDiv")){
								var that =$(this);
								var owed=$("#owed").text();
								setPpaymentArry();
								if(owed!=0){
									JY.Model.info("实收金额不等于应收金额");
									return false;
								}
								var params={list:paymentArry,type:2,status:1};
								 $.ajax({type:'POST',url:jypath+'/pos/payments/add',data:JSON.stringify(params),dataType:'json',contentType:"application/json",success:function(data,textStatus){  			        	 
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
				    that.dialog("close");
				    search();
				 });
			 }	
		}})	
	});
	
	//退定金
	$('#refund').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#earnestBaseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val()); 
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{	
			refund(chks[0])
		}
	});
	
	//修改
	$('#update').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#earnestBaseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val()); 
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{	
			update(chks[0])
		}
	});
	
	//删除
	$('#del').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#earnestBaseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val()); 
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{	
			JY.Model.confirm("确认删除吗？",function(){	
				JY.Ajax.doRequest(null,jypath +'/pos/earnestOrder/del',{cheks:chks.toString()},function(data){
					JY.Model.info(data.resMsg,function(){
						search();
					});
				});
			});
		}
	});
	//付款
	$('#pay').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#earnestBaseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val()); 
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{	
			pay(chks[0])
		}
	});
})
//付款
function pay(id){
	JY.Ajax.doRequest(null,jypath +'/pos/earnestOrder/view',{id:id},function(data){
		cleanPay()
		paymentLeng=1;
		$("#paymentsListForm").find("#paymentsOrderId").val(data.obj.earnestOrder.id);
		$("#paymentsListForm").find("#paymentsOrderNo").val(data.obj.earnestOrder.orderNo);
		$("#paymentsListForm").find("#paymentsType").val(2);
		$("#receivable").text(data.obj.earnestOrder.amount);
		editInfo1 ("paymentsListDiv","付款",function(){
			var that =$(this);
			var owed=$("#owed").text();
			setPpaymentArry();
			if(owed!=0){
				JY.Model.info("实收金额大于应收金额");
				return false;
			}
			var params={list:paymentArry,type:2,status:1};
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
	});
}

/*刷新列表页*/
function search(){
	$("#searchBtn").trigger("click");
}

function refund(id){
	cleanRefund();
	JY.Ajax.doRequest(null,jypath +'/pos/earnestOrder/refundView',{id:id},function(data){
		/*setBaseForm(data);*/
		$("#moneyForm input[name$='id']").val(JY.Object.notEmpty(data.obj.earnestOrder.id));
		$("#moneyForm input[name$='originalNo']").val(JY.Object.notEmpty(data.obj.earnestOrder.orderNo));
		var html=comm(data.obj.payList);
		$("#addMoney tbody").append(html);
		$("#fontOne").text(data.obj.earnestOrder.amount);
		baseInfo({id:"baseDiv",title:"新增定金单退款",height:"720",width:"1024",savefn:function(){
			if(JY.Validate.form("moneyForm")){
				 var that =$(this);
				 JY.Ajax.doRequest("moneyForm",jypath +'/pos/earnestOrder/refund',null,function(data){
					 JY.Model.confirm("是否进行付款？",function(){	
						cleanPay()
					 	paymentLeng=1;
					 	$("#paymentsListForm").find("#paymentsOrderId").val(data.obj.earnestOrder.id);
						$("#paymentsListForm").find("#paymentsOrderNo").val(data.obj.earnestOrder.orderNo);
						$("#paymentsListForm").find("#paymentsType").val(2);
						$("#receivable").text(data.obj.earnestOrder.amount);
						$("#owed").text(data.obj.earnestOrder.amount);
						editInfo1 ("paymentsListDiv","付款",function(){
							if(JY.Validate.form("paymentsListDiv")){
								var that =$(this);
								var owed=$("#owed").text();
								setPpaymentArry();
								if(owed!=0){
									JY.Model.info("实收金额不等于应收金额");
									return false;
								}
								var params={list:paymentArry,type:2,status:1};
								 $.ajax({type:'POST',url:jypath+'/pos/payments/add',data:JSON.stringify(params),dataType:'json',contentType:"application/json",success:function(data,textStatus){  			        	 
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
				    that.dialog("close");
				    search();
				 });
			 }	
		}})	
	});
}

function cleanPay(){
	JY.Tags.cleanForm("paymentsListForm");
	$("#paymentsTable tbody").html("");
	$("#receivable").text(0);
	$("#owed").text(0);
}

function update(id){
	JY.Ajax.doRequest(null,jypath +'/pos/earnestOrder/view',{id:id},function(data){
		console.log(data)
		if(data.obj.earnestOrder.type==1){
			 JY.Tools.populateForm("addForm",data.obj.earnestOrder);
			 $("#addForm textarea[name$='note']").val(JY.Object.notEmpty(data.obj.earnestOrder.note));
			 $("#addForm input[name$='validDate']").val(JY.Object.notEmpty(new Date(data.obj.earnestOrder.validDate).Format("yyyy/MM/dd")));
			 baseInfo({id:"earnsdtDiv",title:"修改定金单",height:"520",width:"824",savefn:function(){
					if(JY.Validate.form("addForm")){
						 var that =$(this);
						 JY.Ajax.doRequest("addForm",jypath +'/pos/earnestOrder/update',null,function(data){
							 JY.Model.confirm("是否进行付款？",function(){
								paymentLeng=1;
								cleanPay()
								$("#paymentsListForm").find("#paymentsOrderId").val(data.obj.id);
								$("#paymentsListForm").find("#paymentsOrderNo").val(data.obj.orderNo);
								$("#paymentsListForm").find("#paymentsType").val(2);
								$("#receivable").text(data.obj.amount);
								$("#owed").text(data.obj.amount);
								editInfo1 ("paymentsListDiv","付款",function(){
									if(JY.Validate.form("paymentsListDiv")){
										var that =$(this);
										var owed=$("#owed").text();
										setPpaymentArry();
										if(owed!=0){
											JY.Model.info("实收金额不等于应收金额");
											return false;
										}
										var params={list:paymentArry,type:2,status:1};
										 $.ajax({type:'POST',url:jypath+'/pos/payments/add',data:JSON.stringify(params),dataType:'json',contentType:"application/json",success:function(data,textStatus){  			        	 
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
						    that.dialog("close");
						    search();
						 });
					 }	
				}})	
		}else{
			JY.Tools.populateForm("moneyForm",data.obj.earnestOrder);
			$("#moneyForm textarea[name$='note']").val(JY.Object.notEmpty(data.obj.earnestOrder.note));
			var html=comm(data.obj.payList);
			$("#addMoney tbody").append(html);
			$("#fontOne").text(data.obj.earnestOrder.amount);
			baseInfo({id:"baseDiv",title:"修改定金单退款",height:"720",width:"1024",savefn:function(){
				if(JY.Validate.form("moneyForm")){
					 var that =$(this);
					 JY.Ajax.doRequest("moneyForm",jypath +'/pos/earnestOrder/update',null,function(data){
						 JY.Model.confirm("是否进行付款？",function(){	
							cleanPay()
						 	paymentLeng=1;
						 	$("#paymentsListForm").find("#paymentsOrderId").val(data.obj.id);
							$("#paymentsListForm").find("#paymentsOrderNo").val(data.obj.orderNo);
							$("#paymentsListForm").find("#paymentsType").val(2);
							$("#receivable").text(data.obj.amount);
							$("#owed").text(data.obj.amount);
							editInfo1 ("paymentsListDiv","付款",function(){
								if(JY.Validate.form("paymentsListDiv")){
									var that =$(this);
									var owed=$("#owed").text();
									setPpaymentArry();
									if(owed!=0){
										JY.Model.info("实收金额不等于应收金额");
										return false;
									}
									var params={list:paymentArry,type:2,status:1};
									 $.ajax({type:'POST',url:jypath+'/pos/payments/add',data:JSON.stringify(params),dataType:'json',contentType:"application/json",success:function(data,textStatus){  			        	 
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
					    that.dialog("close");
					    search();
					 });
				 }	
			}})	
			
		}
		/*cleanPay()
		paymentLeng=1;
		$("#paymentsListForm").find("#paymentsOrderId").val(data.obj.id);
		$("#paymentsListForm").find("#paymentsOrderNo").val(data.obj.orderNo);
		$("#paymentsListForm").find("#paymentsType").val(2);
		$("#receivable").text(data.obj.amount);
		$("#owed").text(data.obj.amount);
		editInfo1 ("paymentsListDiv","付款",function(){
			if(JY.Validate.form("paymentsListDiv")){
				var that =$(this);
				var owed=$("#owed").text();
				setPpaymentArry();
				if(owed!=0){
					JY.Model.info("实收金额不等于应收金额");
					return false;
				}
				var params={list:paymentArry,type:2,status:1};
				 $.ajax({type:'POST',url:jypath+'/pos/payments/add',data:JSON.stringify(params),dataType:'json',contentType:"application/json",success:function(data,textStatus){  			        	 
					 	if(data.res==1){
			        		that.dialog("close");      
			        		JY.Model.info(data.resMsg,function(){search();});
			        	}else{
			        		 JY.Model.error(data.resMsg);
			        	}     	
			         }
			     });
			}
		});*/
	});
}



function editInfo1(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,modal:true,height:570,width:720,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
}


function findOrg(type,selectId){
	JY.Ajax.doRequest(null,jypath +'/scm/report/findOrg',null,function(data){
		var opts = "";
		if(type==1){
			 $.each(data.obj.list, function(n, v) {
		        	if(v.key==data.obj.userId){
		        		 opts += "<option value='" + v.key + "' selected='selected'>" + v.value + "</option>";
		        	}else{
		        		 opts += "<option value='" + v.key + "'>" + v.value + "</option>";
		        	}
		     });
		}else{
			 $.each(data.obj.list, function(n, v) {
		        	if(v.key==selectId){
		        		 opts += "<option value='" + v.key + "' selected='selected'>" + v.value + "</option>";
		        	}else{
		        		 opts += "<option value='" + v.key + "'>" + v.value + "</option>";
		        	}
		     });
		}
        $("#selectUser select").append(opts);
	});
}

/*列表*/
function getbaseList(init){
	if(init==1)$("#earnestBaseForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("earnestBaseForm",jypath +'/pos/earnestOrder/findByPage',null,function(data){
		 	$("#earnestBaseTable tbody").empty();
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
            		 html+="<td class='center hidden-480'><a style='cursor:pointer;' onclick='view(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.orderNo)+"</a></td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.vipCode)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.customer)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.createUser)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.orgName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.createTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
            		 if(l.type==0) {html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>退款单</span></td>";}
            		 else if(l.type==1)  { html+="<td class='center hidden-480'><span class='label label-sm  label-success'>定金单</span></td>";}  
            		 if(l.status==0) {html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>不可用</span></td>";}
            		 else if(l.status==1)  { html+="<td class='center hidden-480'><span class='label label-sm  label-success'>可用</span></td>";}   
            		 else if(l.status==2)  { html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已使用</span></td>";}
            		 else{html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>待付款</span></td>";}
            		 html+="</tr>";		 
            	 } 
        		 $("#earnestBaseTable tbody").append(html);
        		 JY.Page.setPage("earnestBaseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='14' class='center'>没有相关数据</td></tr>";
        		$("#earnestBaseTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
    		 JY.Model.loadingClose();
	 });
}
function baseInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
    	buttons:[{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
    	         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}

function viewInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,
		buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;打印预览","class":"btn btn-primary btn-xs",click:function(){print()}},
		         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
}

function view(id){
	JY.Ajax.doRequest(null,jypath +'/pos/earnestOrder/find',{id:id},function(data){
		if(data.obj.earnestOrder.type==1){
			cleanAdd();
			setBaseForm(data.obj.earnestOrder);
			var html=comm(data.obj.payList);
			$("#tableOne tbody").append(html);
			$('#viewForm').find('input,select,textarea').attr('disabled','disabled');
			viewInfo({id:"viewDiv",title:"查看定金单",height:"720",width:"1024",savefn:function(){}})	
		}else{
			cleanRefund()
			setForm(data.obj.earnestOrder);
			//定金付款记录
			var html=comm(data.obj.payList);
			$("#tableTwo tbody").append(html);
			//定金退款记录
			var html=comm(data.obj.payLists);
			$("#tableThere tbody").append(html);
			$('#refundForm').find('input,select,textarea').attr('disabled','disabled');
			viewInfo({id:"refundDiv",title:"查看定金单退款",height:"720",width:"1024",savefn:function(){}})	
			
		}
	});
}
/*清空表单弹出列表数据数据*/
function cleanAdd(){
	 JY.Tags.cleanForm("viewForm");
	 $("#tableOne tbody").html("");
	 JY.Tags.cleanForm("addForm");
	 $('#viewForm').find('input,select,textarea').removeAttr('disabled');
}
function cleanRefund(){
	 $("#tableTwo tbody").html("");
	 $("#tableThere tbody").html("");
	 $("#addMoney tbody").html("");
	 JY.Tags.cleanForm("refundForm");
	 $('#refundForm').find('input,select,textarea').removeAttr('disabled');
}



/*设置表单值*/
function setBaseForm(data){
	 var p=data;
	 $("#fontTwo").text(p.amount);
	 JY.Tools.populateForm("viewForm",p);
	 $("#viewForm textarea[name$='note']").val(JY.Object.notEmpty(p.note));
}
function setForm(data){
	 var p=data;
	 $("#fontThere").text(p.amount);
	 $("#fontFour").text(p.amount);
	 JY.Tools.populateForm("refundForm",p);
	 $("#refundForm textarea[name$='note']").val(JY.Object.notEmpty(p.note));
}

function comm(data){
	 var html="";
	 var total=0;
	 for(var i=0;i<data.length;i++){ 
		 html+="<tr>";
		 html+="<td class='center hidden-480'>"+(i+1)+"</td>";
		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(data[i].payment)+"</td>";
		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(data[i].bank)+"</td>";
		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(data[i].cardno)+"</td>";
		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(data[i].rate)+"</td>";
		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(data[i].amount)+"</td>";
		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(data[i].note)+"</td>";
		 html+="</tr>";
		 total+=JY.Object.notNumber(data[i].amount);
	 }
	 html+="<tr>";
	 html+="<td class='center hidden-480'>合计</td>";
	 html+="<td class='center hidden-480'></td>";
	 html+="<td class='center hidden-480'></td>";
	 html+="<td class='center hidden-480'></td>";
	 html+="<td class='center hidden-480'></td>";
	 html+="<td class='center hidden-480'>"+total+"</td>";
	 html+="<td class='center hidden-480'></td>";
	 html+="</tr>";
	 return html;
}
/*******组件：选择组织机构 start*******/
var preisShowOut=false;//窗口是否显示
function showOrgCompOut() {
	if(preisShowOut){
		hideOrgCompOut();
	}else{
		var obj = $("#orgInputOut");
		var offpos = $("#orgInputOut").position();
		$("#orgContentListOut").css({left:offpos.left+"px",top:offpos.top+obj.heith+"px"}).slideDown("fast");	
		preisShowOut=true;
	}
}
function hideOrgCompOut(){
	$("#orgContentListOut").fadeOut("fast");
	preisShowOut=false;
}
function emptyOrgCompOut(){
	$("#earnestBaseForm input[name$='orgId']").val("");
	$("#orgInputOut").prop("value","");
}
function initOrgSelectDataOut(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getOrgTree',null,function(data){
		//设置
		$.fn.zTree.init($("#selectOrgTreeOut"),{view:{dblClickExpand:false,selectedMulti:false,nameIsHTML:true},data:{simpleData:{enable: true}},callback:{onClick:clickPreOrgOut}},data.obj);
	});
}

function clickPreOrgOut(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("selectOrgTreeOut"),
	nodes = zTree.getSelectedNodes(),v ="",n ="";	
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";//获取name值
		n += nodes[i].id + ",";	//获取id值
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (n.length > 0 ) n = n.substring(0, n.length-1);
	$("#orgInputOut").prop("value",v);
	$("#earnestBaseForm input[name$='orgId']").prop("value",n);
	$("#earnestBaseForm input[name$='outorgName']").prop("value",v);
	//因为单选选择后直接关闭，如果多选请另外写关闭方法
	hideOrgCompOut();
}
/*******组件：选择组织机构 end*******/
function cleanBaseForm(){
	JY.Tags.cleanForm("earnestBaseForm");
	$("#earnestBaseForm input[name$='orgId']").val("");
	setBaseData();
}

function setBaseData(){
	var date=new Date;
	var year=date.getFullYear(); 
	$("#dateRender1").val(year+"/1/1")
	$("#dateRender2").val(year+"/12/31")
}

function isVip(type){
	var cardNo='';
	var mobile='';
	if(type==1){
		cardNo=$("#addForm input[name$='vipCode']").val();
	}else{
		mobile=$("#addForm input[name$='phone']").val();
	}
	$("#addForm input[name$='vipCode']").val("");
	JY.Ajax.doRequest(null,jypath +'/pos/earnestOrder/isVip',{cardNo:cardNo,mobile:mobile},function(data){
		/*if(data!=null){
			alert(1)
		}*/
		$("#addForm input[name$='customer']").val(data.obj.name);
		$("#addForm input[name$='phone']").val(data.obj.mobile);
		$("#addForm input[name$='vipCode']").val(data.obj.cardNo);
	});
	
}
