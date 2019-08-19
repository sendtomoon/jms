$(function() {
	getbaseList(1);
	JY.Dict.setSelect("selectHandoverStatus", "POS_HANDOVER_STATUS", 2,"全部");
	$('#addBtn').on('click', function(e) {
		cleanForm();
		getAddHandoverDetail();
	 });
	
	$("#dateBegin").datepicker({dateFormat: "yy/mm/dd"});  
	$("#dateEnd").datepicker({dateFormat: "yy/mm/dd"});  
	
	$("#delBtn").on('click',function(e){	
		e.preventDefault();
		var chks =[];    
		$('#handoverList input[name="ids"]:checked').each(function(){
			chks.push($(this).val());    
		});
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			JY.Model.confirm("确认要删除选中的数据吗?",function(){	
				JY.Ajax.doRequest(null,jypath + '/pos/handover/delete',{"orderNo":chks.toString()},function(data){
			    	JY.Model.info(data.resMsg,function(){getbaseList(1);});
			    });
			});	
		}
	});
	
//	selectOrg();
	
});

function cleanBaseForm(){
	JY.Tags.cleanForm("handoverBillFrom");
	$("#dateBegin").val("2017/01/01");  
	$("#dateEnd").val("2017/12/31");
}


//function selectOrg(){
//	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getOrgTree',{},function(data){
//		$.jy.dropTree.init({
//			rootId:"handoverOrg",
//			displayId:"handoverOrgName",
//			data:data.obj,
//			dataFormat:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'},
//			isExpand:true,
//			resetFn:function(){
//				$("#handoverBillFrom #handoverOrgName").val('');
//				$("#handoverBillFrom #handoverOrgId").val('');
//			},
//			clickFn:function(node){
//				$("#handoverBillFrom #handoverOrgId").val(node.id);
//			}
//		});
//		//clearSelect("selectStatus",$("#optionArr").val());
//	});
//}



function save(int){
	if(JY.Validate.form("handoverForm")==false){
		return false;
	}
	if($("#receiverSelect").val()=="qxz"){
		JY.Model.error("请选择接班人！");
		return false;
	}
	if($("#receiver").val() == $("#hander").val()){
		JY.Model.error("交接班不能为同一人！");
		return false;
	}
		
	JY.Ajax.doRequest(null,jypath + '/pos/handover/checkPassword',{"user":$("#receiverSelect").find("option:selected").attr("name"),"passwd":$("#receivePasswd").val()},function(data) {
		if(data.resMsg == "false"){
			JY.Model.info("密码错误！");
		}else{
			var addData = "[{'status':'"+int+"','hander':'"+$("#hander").attr("name").trim()+"','handNum':'"+$("#handNum").val().trim()+"','handWt':'"+$("#handWt").val().trim()+"'," +
			"'handAmt':'"+$("#handAmt").val().trim()+"','receiver':'"+$("#receiverSelect").val()+"','receiveNum':'"+$("#receiveNum").val().trim()+"'," +
					"'receiveWt':'"+$("#receiveWt").val().trim()+"','receiveAmt':'"+$("#receiveAmt").val().trim()+"','difference':'"+$("#reason").val()+"'}]";
	JY.Ajax.doRequest(null,jypath + '/pos/handover/addHandoverDetail',{"addData":addData},function(data) {
		JY.Model.info(data.resMsg);
		$("#handoverForm").dialog("close");
	});
	getbaseList(1);
		}
	});
}

function setDisabled(){
	$("#hander").attr("disabled",false);
	 $("#handNum").attr("disabled",false);
	 $("#handWt").attr("disabled",false);
	 $("#handAmt").attr("disabled",false);
	 $("#receiveNum").attr("disabled",false);
	 $("#receiveWt").attr("disabled",false);
	 $("#receiveAmt").attr("disabled",false);
	 $("#reason").attr("disabled",false);
	 $("#receiver").attr("disabled",false);
	 $("#handoverTime").attr("disabled",false);
	 $("#orgName").attr("disabled",false);
}

function setabled(){
	$("#hander").attr("disabled",true);
	 $("#handNum").attr("disabled",true);
	 $("#handWt").attr("disabled",true);
	 $("#handAmt").attr("disabled",true);
	 $("#receiveNum").attr("disabled",true);
	 $("#receiveWt").attr("disabled",true);
	 $("#receiveAmt").attr("disabled",true);
	 $("#reason").attr("disabled",true);
	 $("#receiver").attr("disabled",true);
	 $("#handoverTime").attr("disabled",true);
	 $("#orgName").attr("disabled",true);
}

function view(no){
		cleanForm();
		JY.Model.loading();
		 JY.Ajax.doRequest(null,jypath + '/pos/handover/find',{"orderNo":no},function(data) {
			 var list = data.obj;
			 if(list[0].status == "0"){
				 $("#hander").val(list[0].handerName);
				 $("#handNum").val(list[0].handNum);
				 $("#handWt").val(list[0].handWt);
				 $("#handAmt").val(list[0].handAmt);
				 $("#receiveNum").val(list[0].receiveNum);
				 $("#receiveWt").val(list[0].receiveWt);
				 $("#receiveAmt").val(list[0].receiveAmt);
				 $("#reason").val(list[0].difference);
				 $("#receiver").val(list[0].receiverName);
				 $("#handoverTime").val(list[0].createTime);
				 $("#orgName").val(list[0].orgName);
				 $("#orderNoHidden").val(no);
				 JY.Ajax.doRequest(null,jypath + '/pos/handover/findForReceiverList',null,function(data) {
					 var list = data.obj;
					     for(var i = 0; i < list.length; i++){
					    	 $("#receiver").append("<option value='"+list[i].id+"'>"+list[i].receiver+"</option>");
						 }
				 });
				 setDisabled();
				 waitHandover(0);
			 }else if(list[0].status == "1"){
				 setabled();
				 $("#hander").val(list[0].handerName);
				 $("#handNum").val(list[0].handNum);
				 $("#handWt").val(list[0].handWt);
				 $("#handAmt").val(list[0].handAmt);
				 $("#receiveNum").val(list[0].receiveNum);
				 $("#receiveWt").val(list[0].receiveWt);
				 $("#receiveAmt").val(list[0].receiveAmt);
				 $("#reason").val(list[0].difference);
				 $("#receiver").val(list[0].receiverName);
				 $("#handoverTime").val(list[0].createTime);
				 $("#orgName").val(list[0].orgName);
				 $("#receiver").empty();
				 $("#orderNoHidden").val(no);
				 $("#receiver").append("<option value='"+list[0].receiver+"'>"+list[0].receiverName+"</option>");
				 waitHandover(1);
			 }else{
				 $("#hander_view").val(list[0].handerName);
				 $("#handNum_view").val(list[0].handNum);
				 $("#handWt_view").val(list[0].handWt);
				 $("#handAmt_view").val(list[0].handAmt);
				 $("#receiveNum_view").val(list[0].receiveNum);
				 $("#receiveWt_view").val(list[0].receiveWt);
				 $("#receiveAmt_view").val(list[0].receiveAmt);
				 $("#reason_view").val(list[0].difference);
				 $("#receiver_view").val(list[0].receiverName);
				 var mydate = new Date(list[0].createTime).Format("yyyy-MM-dd hh:mm:ss");
				 $("#handoverTime_view").val(mydate);
				 $("#orgName_view").val(list[0].orgName);
				 viewInfo({id:"viewdiv",title:"查看交接单",height:"500",width:"650"}); 
			 }   
		 });          
}
function viewInfo(attr) {
	$("#" + attr.id).removeClass('hide').dialog(
			{
				resizable : false,
				height : attr.height,
				width : attr.width,
				modal : true,
				title : "<div class='widget-header'><h4 class='smaller'>"
						+ (JY.Object.notNull(attr.title) ? attr.title : "查看")
						+ "</h4></div>",
				title_html : true,
				buttons : [{
					         html : "<i></i>&nbsp;关闭",
					         "class" : "btn btn-primary",
					         click : function() {$(this).dialog("close");getbaseList(1);}}]});
}

function modifyStatus(no,status){
	JY.Ajax.doRequest(null,jypath + '/pos/handover/checkPassword',{"passwd":$("#receivePasswd").val()},function(data) {
		if(data.resMsg == "false"){
			JY.Model.info("密码错误！");
			return false;
		}
	});
	JY.Ajax.doRequest(null,jypath + '/pos/handover/modifyStatus',{"orderNo":no,"status":status},function(data) {
		JY.Model.info(data.resMsg);
		$("#handoverForm").dialog("close");
	});
	getbaseList(1);
}

function saveCG(no,status){
	if(JY.Validate.form("handoverForm")==false){
		return false;
	}
	if($("#receiver").val() == $("#hander").val()){
		JY.Model.error("交接班不能为同一人！");
		return false;
	}
	if(status == 2 || status == 3){
		if(!JY.Object.notNull($("#receivePasswd").val())){
			JY.Model.error("请输入密码！");
			return false;
		}
	JY.Ajax.doRequest(null,jypath + '/pos/handover/checkPassword',{"passwd":$("#receivePasswd").val()},function(data) {
		if(data.resMsg == "false"){
			JY.Model.info("密码错误！");
			return false;
		}
	});
	}
	
	var addData = "[{'orderNo':"+$("#orderNoHidden").val()+"','status':'"+int+"','hander':'"+$("#hander").attr("name").trim()+"','handNum':'"+$("#handNum").val().trim()+"','handWt':'"+$("#handWt").val().trim()+"'," +
			"'handAmt':'"+$("#handAmt").val().trim()+"','receiver':'"+$("#receiver").val().trim()+"','receiveNum':'"+$("#receiveNum").val().trim()+"'," +
					"'receiveWt':'"+$("#receiveWt").val().trim()+"','receiveAmt':'"+$("#receiveAmt").val().trim()+"','difference':'"+$("#reason").val()+"'}]";
	JY.Ajax.doRequest(null,jypath + '/pos/handover/updateHandoverDetail',{"addData":addData},function(data) {
		JY.Model.info(data.resMsg);
		$("#handoverForm").dialog("close");
	});
	getbaseList(1);
}

function waitHandover(status){
	JY.Ajax.doRequest(null,jypath + '/pos/handover/getAddHandoverDetail',null,function(data) {
		var list = data.obj;
		var html="";
		if(list != null && list.length > 0){
			for(var i=0;i<list.length;i++){
				var l = list[i];
			    html+="<tr>";
			    html+="<td class='center hidden-480'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' value='"+JY.Object.notEmpty(l.add_cangwei)+"'/></td>";
			    html+="<td class='center hidden-480'><input type='text' style='width:100%;height:30px;border:none; readonly class='center' value='"+JY.Object.notEmpty(l.add_Typecode)+"'/></td>";
			    html+="<td class='center hidden-480'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' value='"+JY.Object.notEmpty(l.add_Typename)+"'/></td>";
			    html+="<td class='center hidden-480'><input name='add_num' type='text' style='width:100%;height:30px;border:none;' readonly class='center' value='"+JY.Object.notEmpty(l.add_num).toFixed(2)+"'/></td>";
			    html+="<td class='center hidden-480'><input name='add_wt' type='text' style='width:100%;height:30px;border:none;' readonly class='center' value='"+JY.Object.notEmpty(l.add_wt).toFixed(4)+"'/></td>";
			    html+="<td class='center hidden-480'><input name='add_amt' type='text' style='width:100%;height:30px;border:none;' readonly class='center' value='"+JY.Object.notEmpty(l.add_amt).toFixed(2)+"'/></td>";
			}
			$("#addHandoverDetailList tbody").append(html);
			var mydate = new Date().Format("yyyy-MM-dd hh:mm:ss");
			$("#handoverTime").val(mydate);
			$("#orgName").val(list[0].orgName);
		}else{
			  html+="<tr><td colspan='6' class='center'>没有相关数据</td></tr>";
			  $("#addHandoverDetailList tbody").append(html);
			  $("#pageing ul").empty();//清空分页
		   }
	});
	 foot();
	 if(status==1){
	     waitInfo({id:"handoverForm",title:"确认交接",height:"700",width:"650"});
	 }else{
		 caogaoInfo({id:"handoverForm",title:"编辑交接单",height:"700",width:"650"});
	 }
}

function waitInfo(attr) {
	$("#" + attr.id).removeClass('hide').dialog(
			{
				resizable : false,
				height : attr.height,
				width : attr.width,
				modal : true,
				title : "<div class='widget-header'><h4 class='smaller'>"
						+ (JY.Object.notNull(attr.title) ? attr.title : "查看")
						+ "</h4></div>",
				title_html : true,
				buttons : [ 
					          {
					              html : "<i></i>&nbsp;确认交接",
					              "class" : "btn btn-primary",
					              click : function() {modifyStatus($("#orderNoHidden").val(),2);}
					          },
					          {
							              html : "<i></i>&nbsp;拒绝交接",
							              "class" : "btn btn-primary",
							              click : function() {modifyStatus($("#orderNoHidden").val(),3);}
							          	} ,
					          {
							       html : "<i></i>&nbsp;关闭",
							       "class" : "btn btn-primary",
							       click : function() {$(this).dialog("close");}
							          	}
					          ]
			});
	getbaseList(1);
}

function caogaoInfo(attr) {
	$("#" + attr.id).removeClass('hide').dialog(
			{
				resizable : false,
				height : attr.height,
				width : attr.width,
				modal : true,
				title : "<div class='widget-header'><h4 class='smaller'>"
						+ (JY.Object.notNull(attr.title) ? attr.title : "查看")
						+ "</h4></div>",
				title_html : true,
				buttons : [ 
					          {
					              html : "<i></i>&nbsp;确认交接",
					              "class" : "btn btn-primary",
					              click : function() {saveCG($("#orderNoHidden").val(),2);}
					          },
					          {
					              html : "<i></i>&nbsp;取消",
					              "class" : "btn btn-primary",
					              click : function() {saveCG($("#orderNoHidden").val(),0);}
					          	}
					          ]
			});
	getbaseList(1);
}


function addInfo(attr) {
	$("#" + attr.id).removeClass('hide').dialog(
			{
				resizable : false,
				height : attr.height,
				width : attr.width,
				modal : true,
				title : "<div class='widget-header'><h4 class='smaller'>"
						+ (JY.Object.notNull(attr.title) ? attr.title : "查看")
						+ "</h4></div>",
				title_html : true,
				buttons : [ 
					          {
					              html : "<i></i>&nbsp;确认交接",
					              "class" : "btn btn-primary",
					              click : function() {save(2);}
					          },
					          {
					              html : "<i></i>&nbsp;取消",
					              "class" : "btn btn-primary",
					              click : function() {$(this).dialog('close');}
					          	}
					          ]
			});
	getbaseList(1);
}

function cleanForm(){
	$("#handNum").val("");
	$("#handWt").val("");
	$("#handAmt").val("");
	$("#receiveNum").val("");
	$("#receiveWt").val("");
	$("#receiveAmt").val("");
	$("#addHandoverDetailList tbody").empty();
	$("#hander_view").val("");
	 $("#handNum_view").val("");
	 $("#handWt_view").val("");
	 $("#handAmt_view").val("");
	 $("#receiveNum_view").val("");
	 $("#receiveWt_view").val("");
	 $("#receiveAmt_view").val("");
	 $("#reason_view").val("");
	 $("#receiver_view").val("");
	 $("#handoverTime_view").val("");
	 $("#orgName_view").val("");
}

function getAddHandoverDetail(){
	JY.Model.loading();
	$("#receivePasswd").val("");
	JY.Ajax.doRequest(null,jypath + '/pos/handover/getAddHandoverDetail',null,function(data) {
		var list = data.obj;
		var html="";
		if(list != null && list.length > 0){
			for(var i=0;i<list.length;i++){
				var l = list[i];
			    html+="<tr>";
			    html+="<td class='center hidden-480'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' value='"+JY.Object.notEmpty(l.add_cangwei)+"'/></td>";
			    html+="<td class='center hidden-480'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' value='"+JY.Object.notEmpty(l.add_Typecode)+"'/></td>";
			    html+="<td class='center hidden-480'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' value='"+JY.Object.notEmpty(l.add_Typename)+"'/></td>";
			    html+="<td class='center hidden-480'><input name='add_num' type='text' style='width:100%;height:30px;border:none;' readonly class='center' value='"+JY.Object.notEmpty(l.add_num).toFixed(2)+"'/></td>";
			    html+="<td class='center hidden-480'><input name='add_wt' type='text' style='width:100%;height:30px;border:none;' readonly class='center' value='"+JY.Object.notEmpty(l.add_wt).toFixed(4)+"'/></td>";
			    html+="<td class='center hidden-480'><input name='add_amt' type='text' style='width:100%;height:30px;border:none;' readonly class='center' value='"+JY.Object.notEmpty(l.add_amt).toFixed(2)+"'/></td>";
			}
			$("#addHandoverDetailList tbody").append(html);
			var mydate = new Date().Format("yyyy-MM-dd hh:mm:ss");
			$("#handoverTime").val(mydate);
			$("#orgName").val(list[0].orgName);
		}else{
			  html+="<tr><td colspan='6' class='center'>没有相关数据</td></tr>";
			  $("#addHandoverDetailList tbody").append(html);
		   }
	});
	 JY.Ajax.doRequest(null,jypath + '/pos/handover/findForHander',null,function(data) {
		 var list = data.obj;
		 $("#hander").empty();
		 $("#hander").val(list[0].hander);
		 $("#hander").attr("name",list[0].id);
	 });
	 JY.Ajax.doRequest(null,jypath + '/pos/handover/findForReceiverList',null,function(data) {
		 var list = data.obj;
		 $("#receiverSelect").empty();
		 $("#receiverSelect").append("<option value='qxz'>请选择</option>");
		     for(var i = 0; i < list.length; i++){
		    	 $("#receiverSelect").append("<option value='"+list[i].id+"' name='"+list[i].loginName+"'>"+list[i].receiver+"</option>");
			 }
	 });
	 foot();
	 addInfo({id:"handoverForm",title:"新增交接单",height:"700",width:"650"});
	 $("#receivePasswd").val("");
}

function foot(){
	 $("#sumTable tbody").empty();
		JY.Ajax.doRequest(null,jypath + '/pos/handover/findSum',null,function(data) {
			 var list = data.obj;
			   var sum="";
				sum+="<tr>";
				sum+="<td class='center hidden-480'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' value='合计'/></td>";
				sum+="<td class='center hidden-480'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' value='"+list[0].add_num.toFixed(2)+"'/></td>";
				sum+="<td class='center hidden-480'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' value='"+list[0].add_wt.toFixed(4)+"'/></td>";
				sum+="<td class='center hidden-480'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' value='"+list[0].add_amt.toFixed(2)+"'/></td>";
				 $("#sumTable tbody").append(sum);
		 });
		
}

function getbaseList(init){
	$("#handoverList tbody").empty("");
	JY.Model.loading();
	JY.Ajax.doRequest("handoverBillFrom",jypath + '/pos/handover/findByPage',null,function(data) {
		var obj=data.obj;
    	var list=data.obj;
    	var results=list.results;
    	var permitBtn=obj.permitBtn;
		var pageNum = list.pageNum, pageSize = list.pageSize, totalRecord = list.totalRecord;
		var html = "";
		if(results != null && results.length > 0) {
		  var leng = (pageNum - 1) * pageSize;// 计算序号
	        for(var i = 0; i < results.length; i++) {
			  var l = results[i];
			  html+="<tr>";
			  html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.orderNo+"' class='ace'/> <span class='lbl'></span></label></td>";
			  html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
			  html+="<td class='center hidden-480'><a name='orderNo' style='cursor:pointer;' onclick='view(&apos;"+JY.Object.notEmpty(l.orderNo)+"&apos;)'>"+JY.Object.notEmpty(l.orderNo)+"</a></td>";
			  html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.orgName)+"</td>";
			  html+="<td class='center hidden-480'>"+JY.Object.notEmpty(new Date(l.createTime).Format("yyyy/MM/dd hh:mm:ss"))+"</td>";
			  if(l.status==0) {html+="<td class='center hidden-480'><span class='label label-info arrowed-right arrowed-in'>草稿</span></td>";}
     		 else if(l.status==1)  { html+="<td class='center hidden-480'><span class='label label-info arrowed-right arrowed-in'>待交接</span></td>";}
     		 else if(l.status==2)  { html+="<td class='center hidden-480'><span class='label label-info arrowed-right arrowed-in'>已交接</span></td>";}
     		 else if(l.status==3)  { html+="<td class='center hidden-480'><span class='label label-info arrowed-right arrowed-in'>拒绝交接</span></td>";}
			  html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.handerName)+"</td>";
			  html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.receiverName)+"</td>";
			  html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.handNum)+"</td>";
			  html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.receiveNum)+"</td>";
			  html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.handWt)+"</td>";
			  html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.receiveWt)+"</td>";
			  html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.handAmt)+"</td>";
			  html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.receiveAmt)+"</td>";
			  html+="</tr>";
	        }
	        $("#handoverList tbody").append(html);
			JY.Page.setPage("handoverBillFrom","pageing",pageSize,pageNum,totalRecord,"getbaseList(1)");
	    }else{
		  html+="<tr><td colspan='14' class='center'>没有相关数据</td></tr>";
		  $("#handoverList tbody").append(html);
		  $("#pageing ul").empty();//清空分页
	   }	
	});
	JY.Model.loadingClose();
}









