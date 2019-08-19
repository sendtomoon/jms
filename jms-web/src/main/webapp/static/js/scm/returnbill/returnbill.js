var qcFaqId = [];
$(function() {
	JY.Dict.setSelect("selectReturnBillStatus", "SCM_RETURNBILL_STATUS", 2,"全部");
	qcFaqId=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_QC_PROBLEM",qcFaqId);
	getbaseList(1);
	 
	 
	 $("#returnBillForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 getbaseList(1);
		 } 
	});
	 
	 $('#viewBtn').on('click', function(e) {
		 if(checkChooseSingle()==false){
				return;
			}
		 view(chk.toString());
	 });
	 
});

function search(){
	$("#searchBtn").trigger("click");
}

$("#delReturnBillBtn").on('click',function(e){
	//通知浏览器不要执行与事件关联的默认动作		
	e.preventDefault();
	var chks =[];    
	$('#returnBillList input[name="ids"]:checked').each(function(){
		chks.push($(this).val());    
	});
	console.log(chks.toString());
	if(chks.length==0) {
		JY.Model.info("您没有选择任何内容!"); 
	}else{
		JY.Model.confirm("确认要删除选中的数据吗?",function(){	
			JY.Ajax.doRequest(null,jypath + '/scm/returnbill/delete',{chks:chks.toString()},function(data){
		    	JY.Model.info(data.resMsg,function(){getbaseList(1);});
		    });
		});	
	}
});

$('#addReturnBill').on('click', function(e){
	//通知浏览器不要执行与事件关联的默认动作		
	e.preventDefault();
	flushDiv();
	addReturnBill();
});

$('#modifyBtn').on('click', function(e){
	//通知浏览器不要执行与事件关联的默认动作		
	e.preventDefault();
	flushDiv();
	modifyReturnBill();
});

function getbaseList(init) {
	if(init==1)$("#returnBillForm .pageNum").val(1);
	cleanForm();
	JY.Model.loading();
	JY.Ajax.doRequest("returnBillForm",jypath + '/scm/returnbill/findByPage',null,function(data) {
						$("#returnBillList tbody").empty();
						var obj=data.obj;
			        	var list=data.obj;
			        	var results=list.results;
			        	var permitBtn=obj.permitBtn;
						var pageNum = list.pageNum, pageSize = list.pageSize, totalRecord = list.totalRecord;
						var html = "";
						if (results != null && results.length > 0) {
							var leng = (pageNum - 1) * pageSize;// 计算序号
							for (var i = 0; i < results.length; i++) {
								var l = results[i];
//								var statusCH;
//								if((l.status)==2){
//									statusCH = "审核通过";
//								}
//								if((l.status)==1){
//									statusCH = "已拒绝";
//								}
//								if((l.status)==0){
//									statusCH = "待审核";
//								}
//								if((l.status)==3){
//									statusCH = "已删除";
//								}
//								if((l.status)==4){
//									statusCH = "草稿";
//								}
								 html+="<tr>";
				        		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace'/> <span class='lbl'></span></label></td>";
				        		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
				        		 html+="<td class='center hidden-480'><a name=\"returnno\" style='cursor:pointer;' onclick='view(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.returnNo)+"</a></td>";
				        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.noticeNo)+"</td>";
				        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(parseFloat(l.totalNum).toFixed(4))+"</td>";
				        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(parseFloat(l.totalWt).toFixed(4))+"</td>";
				        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(parseFloat(l.basicCost).toFixed(4))+"</td>";
				        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(parseFloat(l.addCost).toFixed(4))+"</td>";
				        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(parseFloat(l.otherCost).toFixed(4))+"</td>";
				        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(parseFloat(l.purCost).toFixed(4))+"</td>";
				        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.supplierName)+"</td>";
//				        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.remarks)+"</td>";
				        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.createUser)+"</td>";
				        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(new Date(l.createTime).Format("yyyy/MM/dd hh:mm:ss"))+"</td>"; 
//				        		 if(l.status==0) {html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>待审核</span></td>";}
//			            		 else if(l.status==3)  { html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已删除</span></td>";}
//			            		 else if(l.status==2)  { html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已审核</span></td>";}
//			            		 else if(l.status==1)  { html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已拒绝</span></td>";}
//			            		 else if(l.status==4)  { html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>草稿</span></td>";}
//				        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.checkUser)+"</td>";
//				        		 html+="<td class='center hidden-480'>"+checkDate(new Date(l.checkTime).Format("yyyy/MM/dd hh:mm:ss"))+"</td>";
				        		 html+="</tr>";	
							}
							$("#returnBillList tbody").append(html);
							JY.Page.setPage("returnBillForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
						}else{
			        		html+="<tr><td colspan='16' class='center'>没有相关数据</td></tr>";
			        		$("#returnBillList tbody").append(html);
			        		$("#pageing ul").empty();//清空分页
			        		
			        	 }	
						JY.Model.loadingClose();
					});
}

function checkDate(String){
	if(String == "1970/01/01 08:00:00"){
		return "";
	}else{
		return String;
	}
}

function cleanForm(){
	JY.Tags.cleanForm("returnBillBaseForm");
}

function flushDiv(){
	$("#returnBillDetailList tbody").empty();//清除页面
	$("#delSpan").remove();
	$("#inputReturnBillNumber").attr("enable",true);
}

//添加明细
function saveRB(status,that){
	var QCNO;
	if($('#QC').val()=="" || $('#QC').val()==null){
		var QCNO = "无单号";
	}else{
		var QCNO = $('#QC').val();
	}
	var noticeNo = $("#addReturnBillForm input[name='noticeNo']").val();
	var orgId = $("#addReturnBillForm input[name='orgId']").val();
	var supplierId = $("#addReturnBillForm input[name='surpplyId']").val();
	var enteryNo = JY.Object.notEmpty($("#addReturnBillForm input[name='code']").val());
	var counts = $("#addRturnBillDetailList input[name='counts']").val();
	var unqualifyWts = $("#addRturnBillDetailList input[name='unqualifyWts']").val();
	var basicCostSum = $("#addRturnBillDetailList input[name='basicCostSum']").val();
	var addCostSum = $("#addRturnBillDetailList input[name='addCostSum']").val();
	var otherCostSum = $("#addRturnBillDetailList input[name='otherCostSum']").val();
	var purCostSum = $("#addRturnBillDetailList input[name='purCostSum']").val();
	var dataRB = '{"status":"'+status+'","enteryNo":"'+enteryNo+'","basicCost":"'+basicCostSum+'","addCost":"'+addCostSum+'","otherCost":"'+otherCostSum+'","purCost":"'+purCostSum+'","noticeNo":"'+noticeNo+'","supplierId":"'+supplierId+'","orgId":"'+orgId+'","totalNum":"'+counts+'","totalWt":"'+unqualifyWts+'","remarks":"'+JY.Object.notEmpty($('#addRBRemarks').val())+'"}';
	dataRB='['+dataRB+']';
	
	var json_data="";
    var last_index=$('#addRturnBillDetailList tbody tr').length-1;
    $('#addRturnBillDetailList tbody tr').each(function(element,index){
    var data='{"code":"'+$(this).find("input[name=code]").val()+'","name":"'+$(this).find("input[name=name]").val()+'","goldType":"'+$(this).find("input[name=goldType]").val()+'","goldName":"'+$(this).find("input[name=goldName]").val()+'","unqualifyNum":"'+$(this).find("input[name=fyNum]").val()+'","unqualifyWt":"'+$(this).find("input[name=fyWt]").val()+'","goldWeight":"'+$(this).find("input[name=goldWeight]").val()+'","stoneWeight":"'+$(this).find("input[name=stoneWeight]").val()+'","basicCost":"'+$(this).find("input[name=basicCost]").val()+'","addCost":"'+$(this).find("input[name=addCost]").val()+'","otherCost":"'+$(this).find("input[name=purCost]").val()+'","purCost":"'+$(this).find("input[name=purCost]").val()+'","goodsId":"'+$(this).find("input[name='goodsId']").val()+'","causeId":"'+$(this).find("select[name=qcFaqIdDetail]").val()+'"}';	
    if($(this).index()==last_index){
	json_data+=data;
       }else{
	      json_data+=data+',';
        }
    });
    json_data='['+json_data+']';
    
    console.log(dataRB);
    console.log(json_data);
    
	JY.Ajax.doRequest(null,jypath +'/scm/returnbill/saveReturnBill/',{rbdata:dataRB.toString(),rbddata:json_data.toString()},function(data){
		that.dialog("close");
					JY.Model.info(data.resMsg,function(){search();});
			});
}


function saveModifyRB(status,that){
	var noticeNo = $("#addReturnBillForm input[name='noticeNo']").val();
	var orgId = $("#addReturnBillForm input[name='orgId']").val();
	var enteryNo = JY.Object.notEmpty($("#addReturnBillForm input[name='enteryNo']").val());
	var counts = $("#addRturnBillDetailList input[name='counts']").val();
	var unqualifyWts = $("#addRturnBillDetailList input[name='unqualifyWts']").val();
	var basicCostSum = $("#addRturnBillDetailList input[name='basicCostSum']").val();
	var addCostSum = $("#addRturnBillDetailList input[name='addCostSum']").val();
	var otherCostSum = $("#addRturnBillDetailList input[name='otherCostSum']").val();
	var purCostSum = $("#addRturnBillDetailList input[name='purCostSum']").val();
	var returnNo = $("#addRturnBillDetailList input[name='returnNo']").val();
    var dataRB = '{"status":"'+status+'","id":"'+$("#savereturnbillid").val()+'","enteryNo":"'+enteryNo+'","basicCost":"'+basicCostSum+'","addCost":"'+addCostSum+'","otherCost":"'+otherCostSum+'","purCost":"'+purCostSum+'","noticeNo":"'+noticeNo+'","orgId":"'+orgId+'","totalNum":"'+counts+'","totalWt":"'+unqualifyWts+'","remarks":"'+JY.Object.notEmpty($("#returnNote textarea[name='remarks']").val())+'"}';
//	var dataRB = '{"status":"'+status+'","noticeNo":"'+$('#addReturnBillNoticeNo').val()+'","id":"'+$("#savereturnbillid").val()+'","remarks":"'+$('#addRBRemarks').val()+'"}';
	dataRB='['+dataRB+']';
	
	var json_data="";
    var last_index=$('#addRturnBillDetailList tbody tr').length-1;
    $('#addRturnBillDetailList tbody tr').each(function(element,index){
//    var data='{"code":"'+$(this).find("input[name=code]").val()+'","name":"'+$(this).find("input[name=name]").val()+'","unqualifyNum":"'+$(this).find("input[name=unqualifyNum]").val()+'","unqualifyWt":"'+$(this).find("input[name=unqualifyWt]").val()+'","goodsId":"'+$(this).find("input[type=checkbox]").attr("name")+'","causeId":"'+$(this).find("select[name=qcFaqIdDetail]").val()+'"}';	
    
    var data='{"code":"'+$(this).find("input[name=code]").val()+'","name":"'+$(this).find("input[name=name]").val()+'","goldType":"'+$(this).find("input[name=goldType]").val()+'","goldName":"'+$(this).find("input[name=goldName]").val()+'","unqualifyNum":"'+$(this).find("input[name=fyNum]").val()+'","unqualifyWt":"'+$(this).find("input[name=fyWt]").val()+'","goldWeight":"'+$(this).find("input[name=goldWeight]").val()+'","stoneWeight":"'+$(this).find("input[name=stoneWeight]").val()+'","basicCost":"'+$(this).find("input[name=basicCost]").val()+'","addCost":"'+$(this).find("input[name=addCost]").val()+'","otherCost":"'+$(this).find("input[name=purCost]").val()+'","purCost":"'+$(this).find("input[name=purCost]").val()+'","goodsId":"'+$(this).find("input[type=checkbox]").attr("name")+'","causeId":"'+$(this).find("select[name=qcFaqIdDetail]").val()+'"}';
    
    if($(this).index()==last_index){
	json_data+=data;
       }else{
	      json_data+=data+',';
        }
    });
    json_data='['+json_data+']';
    
    console.log(dataRB);
    console.log(json_data);
    
	JY.Ajax.doRequest(null,jypath +'/scm/returnbill/saveModifyReturnBill/',{rbdata:dataRB.toString(),rbddata:json_data.toString()},function(data){
				if(data.resMsg == "0"){
					JY.Model.info("修改失败！");
				}else{
					JY.Model.info(data.resMsg);
				}
				that.dialog("close");
				JY.Model.info(data.resMsg,function(){search();});
			});
}


/*新增退厂单*/
function addReturnBill(){
	cleanReturnForm();
	$("#addRturnBillDetailList tbody").empty();//清除table
	addInfo({id:"addReturnBillDiv",title:"新增退厂单",height:"600",width:"1050",savefn:function(type){
		var that = $(this);
		saveRB(type,that);
	}});
	addTableFoot();
}

/*修改退厂单*/
function modifyReturnBill(){
	var chk=null;  
	chk=[];
	$('#returnBillList input[name="ids"]:checked').each(function(){
		chk.push($(this).val());
	});     
	if(chk.length==0) {
		JY.Model.info("您没有选择任何内容!"); 
		return false;
	}
	if(chk.length>1){
		JY.Model.info("您只能选中一张单!"); 
		return false;
	}
	cleanReturnForm();
	$("#addRturnBillDetailList tbody").empty();//清除table
	
	$("#savereturnbillid").val(chk.toString());
	JY.Ajax.doRequest(null,jypath +'/scm/returnbill/queryForModify/',{id:chk.toString()},function(data){
		var list = data.obj.list;
		$("#QC").val(list[0].QCNO);
		
		$("#addReturnBillForm input[name='returnNo']").val(list[0].returnNo);
		$("#addReturnBillForm input[name='noticeNo']").val(list[0].noticeNo);
		$("#addReturnBillForm input[name='orgName']").val(list[0].orgName);
		$("#addReturnBillForm input[name='supplierName']").val(list[0].supplierName);
		$("#addReturnBillForm input[name='orgId']").val(list[0].orgId);
		$("#addReturnBillForm input[name='enteryNo']").val(list[0].code);
		$("#returnNote #createUser").text(list[0].createUser);
		$("#returnNote #createTime").text(list[0].cTime);
		if(list[0].checkUser){
			$("#returnNote #checkUser").text(list[0].checkUser);
		}
		if(list[0].checkTime){
			$("#returnNote #checkTime").text(list[0].chTime);
		}
		$("#returnNote textarea[name='remarks']").val(list[0].remarks);
	//	$("#QC").attr("disabled",true);
		$("#addReturnBillNoticeNo").attr("disabled",true);
		var html = "";
		for(var i=0;i<list.length;i++){
			var l = list[i];
			html+="<tr>";
			html+="<td class='center'><input id='checkbox' type='checkbox' name=\""+list.goodsId+"\"></td>";
			html+="<td class='center'>"+(i+1)+"</td>";
			html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' class='center' readonly name='code' value='"+JY.Object.notEmpty(l.code)+"'><input type='hidden' name='unitPrice' id='unitPrice"+i+"' value='"+JY.Object.notEmpty((l.purCost/l.unqualifyWt))+"'/>" +
					"<input type='hidden' id='shiNum"+i+"' value='"+JY.Object.notEmpty(l.unqualifyNum)+"'/><input type='hidden' id='shiWt"+i+"' value='"+JY.Object.notEmpty(l.unqualifyWt)+"'/></td>"
			html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' class='center' readonly name='name' value='"+JY.Object.notEmpty(l.name)+"'></td>";
			html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' class='center' readonly name='goldName' value='"+JY.Object.notEmpty(l.goldName)+"'><input type='hidden' name='goldType' value='"+JY.Object.notEmpty(l.goldType)+"'></td>";
			html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:1px solid #ccc;' class='center' name='unqualifyNum' id='unqualifyNum"+i+"' onBlur='addNum(\""+"unqualifyNum"+"\","+i+")' value='"+JY.Object.notEmpty(l.unqualifyNum)+"'><input type='hidden' id='fyNum"+i+"' name='fyNum' value='"+JY.Object.notEmpty(l.unqualifyNum)+"'/></td>"
			html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:1px solid #ccc;' class='center' name='unqualifyWt' id='unqualifyWt"+i+"' onBlur='addNum(\""+"unqualifyWt"+"\","+i+")' value='"+JY.Object.notEmpty(l.unqualifyWt.toFixed(4))+"'><input type='hidden' id='fyWt"+i+"' name='fyWt' value='"+JY.Object.notEmpty(l.unqualifyWt.toFixed(4))+"'/></td>";
			html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' name='goldWeight' value='"+JY.Object.notEmpty(l.goldWeight)+"'></td>";
			html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' name='stoneWeight' value='"+JY.Object.notEmpty(l.stoneWeight)+"'></td>";
			html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' name='basicCost' value='"+JY.Object.notEmpty(l.basicCost.toFixed(4))+"'></td>";
			html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' name='addCost' value='"+l.addCost.toFixed(4)+"'></td>";
			html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' name='otherCost' value='"+l.otherCost.toFixed(4)+"'></td>";
			html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' name='purCost' id='purCost"+i+"' value='"+l.purCost.toFixed(4)+"'></td>";
	    	html+="</tr>";
			
			
	//		html+="<td class=\"center\"><input id=\"checkbox\" type=\"checkbox\" name=\""+list[i].goodsId+"\"></td>";
	//    	html+="<td class=\"center\"><input class='center' type='text' readonly  value='"+(i+1)+"' style='width:100%;height:30px;border:none;'/></td>";
	//    	html+="<td class=\"center\"><input name=\"code\" class='center' type='text' readonly  value='"+(list[i].code)+"' style='width:100%;height:30px;border:none;'/></td>";
	//    	html+="<td class=\"center\"><input name=\"name\" class='center' type='text' readonly  value='"+(list[i].name)+"' style='width:100%;height:30px;border:none;'/></td>";
	//    	html+="<td class=\"center\"><input name=\"goldName\" class='center' type='text' readonly  value='"+(list[i].goldName)+"' style='width:100%;height:30px;border:none;'/></td>";    		 
	//  		html+="<td class=\"center\"><input name=\"unqualifyNum\" class='center' type='text' onchange=\"addTableFoot()\" value='"+list[i].unqualifyNum+"' style='width:100%;height:30px;border:none;'/></td>";
	//    	html+="<td class=\"center\"><input name=\"unqualifyWt\" class='center' type='text' onchange=\"addTableFoot()\" value='"+list[i].unqualifyWt+"' style='width:100%;height:30px;border:none;'/></td>";  		 
	//    	html+="<td class=\"center\"><select data-placeholder='状态' name='qcFaqIdDetail' class='isSelect145' jyValidate='required' "+options(qcFaqId, JY.Object.notEmpty(list[i].qcFaqId))+"></select></td>";
	//    	html+="</tr>";
		}
			$("#addRturnBillDetailList tbody").append(html);
			addTableFoot();
			modifyInfo({id:"addReturnBillDiv",title:"修改退厂单",height:"600",width:"1050",savefn:function(type){
				var that = $(this);
				saveModifyRB(type,that);
			}});
	});
	
}


function delRowOfAdd(){
		var chks =[];    
		$('#addRturnBillDetailList input[id="checkbox"]:checked').each(function(){ 
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			JY.Model.confirm("确认要删除选中的数据吗?",function(){	
				$('#addRturnBillDetailList input[id="checkbox"]:checked').each(function(){  
					$(this).parent().parent().remove();
					addTableFoot();
				}); 
			});	
		}	
}


function findXiangqian(){
	var code = $("#addReturnBillProductCode").val();
	JY.Ajax.doRequest(null,jypath +'/scm/returnbill/queryCode/',{code:code},function(data){
		var obj = data.obj;
		var list = obj.list;
		if(list.length == 0 || list == null){
			JY.Model.info("查找不到数据!"); 
			return;
		}
		if($("#addReturnBillSupplierName").val() != "" && list[0].supplierName != $("#addReturnBillSupplierName").val()){
			JY.Model.info("不同供应商不能放在同一张单内!"); 
			return;
		}
		if($("#addReturnBillNoticeNo").val() != "" && list[0].noticeNo != $("#addReturnBillNoticeNo").val()){
			JY.Model.info("不同入库通知单同一张单内!"); 
			return;
		}
		var html = "";
		$("#addReturnBillSupplierName").val(list[0].supplierName);
		$("#addReturnBillNoticeNo").val(list[0].noticeNo);
		for (var i = 0; i < list.length; i++) {
			html+="<tr>";
			html+="<td class=\"center\"><input id=\"checkbox\" type=\"checkbox\" name=\""+list[i].goodsId+"\"></td>";
        	html+="<td class=\"center\"><input class='center' type='text' readonly  value='"+(i+1)+"' style='width:100%;height:30px;border:none;'/></td>";
        	html+="<td class=\"center\"><input name=\"code\" class='center' type='text' readonly  value='"+(list[i].code)+"' style='width:100%;height:30px;border:none;'/></td>";
        	html+="<td class=\"center\"><input name=\"name\" class='center' type='text' readonly  value='"+(list[i].name)+"' style='width:100%;height:30px;border:none;'/></td>";
        	html+="<td class=\"center\"><input name=\"goldName\" class='center' type='text' readonly  value='"+(list[i].goldName)+"' style='width:100%;height:30px;border:none;'/></td>";    		 
      		html+="<td class=\"center\"><input name=\"unqualifyNum\" class='center' type='text' onchange=\"addTableFoot()\" value='"+"1"+"' style='width:100%;height:30px;border:none;'/></td>";
        	html+="<td class=\"center\"><input name=\"unqualifyWt\" class='center' type='text' onchange=\"addTableFoot()\" value='"+list[i].unqualifyWt+"' style='width:100%;height:30px;border:none;'/></td>";  		 
        	html+="<td class=\"center\"><select data-placeholder='状态' name='qcFaqIdDetail' class='isSelect145' jyValidate='required' "+options(qcFaqId, JY.Object.notEmpty(list[i].qcFaqId))+"></select></td>";
        	html+="</tr>";
		}
		$("#addRturnBillDetailList tbody").append(html);
		$("input[id='code']").val("");
		addTableFoot();
		$("#addReturnBillProductCode").val();
	});
}


/*查找商品*/
function subcodeTwo(){
//	JY.Model.confirm("该操作会导致列表被更新，确认继续吗?",function(){
//		cleanReturnForm();
		$("#addRturnBillDetailList tbody").html("");//清除table
		$("#addRturnBillDetailList tfoot").html("");//清除table
		var noticeNo = $("#addReturnBillForm input[name='noticeNo']").val().trim();
		var enteryNo = $("#addReturnBillForm input[name='enteryNo']").val().trim();
		if(JY.Object.notNull(noticeNo)){
			JY.Ajax.doRequest(null,jypath +'/scm/returnbill/queryNotice/',{noticeNo:noticeNo,enteryNo:enteryNo},function(data){
				var obj = data.obj;
				var list = obj.list;
//				if(list.length == 0 || list == null){
//					JY.Model.info("查找不到信息!"); 
//					return;
//				}
				if(list&&list.length > 0){
					$("#addReturnBillForm input[name='orgName']").val(list[0].orgName);
					$("#addReturnBillForm input[name='orgId']").val(list[0].orgId);
					var html = "";
					$("#addReturnBillForm input[name='supplierName']").val(list[0].supplierName);
					$("#addReturnBillForm input[name='surpplyId']").val(list[0].supplierId);
					for (var i = 0; i < list.length; i++) {
						
						var addCost = list[i].addCost?list[i].addCost:0.0000;
						var otherCost = list[i].otherCost?list[i].otherCost:0.0000;
						var purCost = list[i].purCost?list[i].purCost:0.0000;
						
						html+="<tr>";
						html+="<td class='center'><input id='checkbox' type='checkbox' name=\""+list[i].goodsId+"\"></td>";
						html+="<td class='center'>"+(i+1)+"</td>";
						html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' class='center' readonly name='code' value='"+JY.Object.notEmpty(list[i].code)+"'><input type='hidden' name='unitPrice' id='unitPrice"+i+"' value='"+JY.Object.notEmpty((list[i].purCost/list[i].unqualifyWt).toFixed(4))+"'/>" +
								"<input type='hidden' id='shiNum"+i+"' value='"+JY.Object.notEmpty(list[i].unqualifyNum)+"'/><input type='hidden' id='shiWt"+i+"' value='"+JY.Object.notEmpty(list[i].unqualifyWt)+"'/><input type='hidden' name='goodsId' value='"+list[i].goodsId+"'></td>"
						html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' class='center' readonly name='name' value='"+JY.Object.notEmpty(list[i].name)+"'></td>";
						html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' class='center' readonly name='goldName' value='"+JY.Object.notEmpty(list[i].goldName)+"'><input type='hidden' name='goldType' value='"+JY.Object.notEmpty(list[i].goldType)+"'></td>";
						html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:1px solid #ccc;' class='center' name='unqualifyNum' id='unqualifyNum"+i+"' onBlur='addNum(\""+"unqualifyNum"+"\","+i+")' value='"+JY.Object.notEmpty(list[i].unqualifyNum)+"'><input type='hidden' name='fyNum' id='fyNum"+i+"' value='0'/></td>"
						html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:1px solid #ccc;' class='center' name='unqualifyWt' id='unqualifyWt"+i+"' onBlur='addNum(\""+"unqualifyWt"+"\","+i+")' value='"+JY.Object.notEmpty(list[i].unqualifyWt.toFixed(4))+"'><input type='hidden' name='fyWt' id='fyWt"+i+"' value='0'/></td>";
						html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' name='goldWeight' value='"+JY.Object.notEmpty(list[i].goldWeight.toFixed(4))+"'></td>";
						html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' name='stoneWeight' value='"+JY.Object.notEmpty(list[i].stoneWeight.toFixed(4))+"'></td>";
						html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' name='basicCost' value='"+JY.Object.notEmpty(list[i].basicCost.toFixed(4))+"'></td>";
						html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' name='addCost' value='"+addCost+"'></td>";
						html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' name='otherCost' value='"+otherCost+"'></td>";
						html+="<td class='center' style='padding:1px;'><input type='text' style='width:100%;height:30px;border:none;' readonly class='center' name='purCost' id='purCost"+i+"' value='"+purCost+"'></td>";
						html+="</tr>";
					}
					$("#addRturnBillDetailList tbody").append(html);
					addTableFoot();	
				}
			});
		}else{
			JY.Model.info("条码有误!"); 
		}
		$("#addRturnBillDetailList tbody").empty();//清除table
//	});
	
}

function addNum(name,i){
	var counts=0,unqualifyWts=0 ,purCostSum=0;
	if(name=="unqualifyWt"){
		if($("#shiWt"+i).val()<$("#unqualifyWt"+i).val()){
			$("#unqualifyWt"+i).tips({
                msg: "不能大于退货重量！",
                bg: '#FF2D2D',
                time: 1
            });
			$("#unqualifyWt"+i).val($("#shiWt"+i).val());
		}else{
			$("#fyWt"+i).val(($("#shiWt"+i).val()-$("#unqualifyWt"+i).val()).toFixed(4));
		}
		var purCost = $("#unqualifyWt"+i).val()*$("#unitPrice"+i).val();
		$("#purCost"+i).val(purCost.toFixed(4));
	}
	if(name=="unqualifyNum"){
		if($("#shiNum"+i).val()<$("#unqualifyNum"+i).val()){
			$("#unqualifyNum"+i).tips({
                msg: "不能大于退货数量！",
                bg: '#FF2D2D',
                time: 1
            });
			$("#unqualifyNum"+i).val($("#shiNum"+i).val());
		}else{
			$("#fyNum"+i).val(($("#shiNum"+i).val()-$("#unqualifyNum"+i).val()).toFixed(4));
		}
	}
	 $("#addRturnBillDetailList").find('input[name="unqualifyNum"]').each(function(element,index){
	 		if($(this).val()!=''){counts+=parseFloat($(this).val());}});
	 $("#addRturnBillDetailList").find('input[name="unqualifyWt"]').each(function(element,index){
	 		if($(this).val()!=''){unqualifyWts+=parseFloat($(this).val());}});
	 $("#addRturnBillDetailList").find('input[name="purCost"]').each(function(element,index){
	 		if($(this).val()!=''){purCostSum+=parseFloat($(this).val());}});
	 $("#addRturnBillDetailList input[name='counts']").val(counts);
	 $("#addRturnBillDetailList input[name='unqualifyWts']").val(unqualifyWts.toFixed(4));
	 $("#addRturnBillDetailList input[name='purCostSum']").val(purCostSum.toFixed(4));
}

function addTableFoot(){
	 $("#addRturnBillDetailList tfoot").html("");
	 var counts=0,unqualifyWts=0,goldWeightSum=0,stoneWeightSum=0,basicCostSum=0,addCostSum=0,otherCostSum=0,purCostSum=0;
	 $("#addRturnBillDetailList").find('input[name="unqualifyNum"]').each(function(element,index){
	 		if($(this).val()!=''){counts+=parseFloat($(this).val());}});
	 $("#addRturnBillDetailList").find('input[name="unqualifyWt"]').each(function(element,index){
	 		if($(this).val()!=''){unqualifyWts+=parseFloat($(this).val());}});
    $("#addRturnBillDetailList").find('input[name="goldWeight"]').each(function(element,index){
		if($(this).val()!=''){goldWeightSum+=parseFloat($(this).val());}});
    $("#addRturnBillDetailList").find('input[name="stoneWeight"]').each(function(element,index){
		if($(this).val()!=''){stoneWeightSum+=parseFloat($(this).val());}});
    $("#addRturnBillDetailList").find('input[name="basicCost"]').each(function(element,index){
		 if($(this).val()!=''){basicCostSum+=parseFloat($(this).val());}});
    $("#addRturnBillDetailList").find('input[name="addCost"]').each(function(element,index){
		if($(this).val()!=''){addCostSum+=parseFloat($(this).val());}});
    $("#addRturnBillDetailList").find('input[name="otherCost"]').each(function(element,index){
		if($(this).val()!=''){otherCostSum+=parseFloat($(this).val());}});
    $("#addRturnBillDetailList").find('input[name="purCost"]').each(function(element,index){
 		if($(this).val()!=''){purCostSum+=parseFloat($(this).val());}});
    
		var foot="";
		 foot+="<tr>";
		 foot+="<td class='count'>合计</td>";
		 foot+="<td></td>";
		 foot+="<td></td>";
		 foot+="<td></td>";
		 foot+="<td></td>";
		 foot+="<td class='center' style='padding:1px;'><input name='counts' class='center' type='text' value='"+counts+"' style='width:100%;height:30px;border:none;' readonly /></td>";
		 foot+="<td class='center' style='padding:1px;'><input name='unqualifyWts' class='center' type='text' value='"+unqualifyWts.toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
		 foot+="<td class='center' style='padding:1px;'><input class='center' type='text' value='"+goldWeightSum.toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
		 foot+="<td class='center' style='padding:1px;'><input class='center' type='text' value='"+stoneWeightSum.toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
		 foot+="<td class='center' style='padding:1px;'><input class='center' name='basicCostSum' type='text' value='"+basicCostSum.toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
		 foot+="<td class='center' style='padding:1px;'><input class='center' name='addCostSum' type='text' value='"+addCostSum.toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
		 foot+="<td class='center' style='padding:1px;'><input class='center' name='otherCostSum' type='text' value='"+otherCostSum.toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
		 foot+="<td class='center' style='padding:1px;'><input name='purCostSum' class='center' type='text' value='"+purCostSum.toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
		 foot+="</tr>";	
		 $("#addRturnBillDetailList tfoot").append(foot);
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

function detailViewCommon(data){
	flushDiv();
	$("#qqq").remove();
	$("#inputReturnBillNumber").attr("disabled",true);
	$("#inputNoticeNumber").attr("disabled",true);
	$("#inputReturnOrg").attr("disabled",true);
	$("#inputSupplierName").attr("disabled",true);
	$("#refuseReason").attr("disabled",true);
	$("#returnBillRemarks").attr("disabled",true);
		var obj = data.obj;
		var list = obj.list;
		var html = "";
		$('#refuseCause').show();
		if (list != null && list.length > 0) {
			var l1 = list[0];
 			$("#inputReturnBillNumber").val(l1.returnNo);
			$("#inputNoticeNumber").val(l1.noticeNo);
			$("#inputReturnOrg").val(l1.orgName);
			$("#inputSupplierName").val(l1.supplierName);
			$("#remarkInput").val(l1.remarks);			
			$("#consoleManInput").text(l1.createName);
			$("#makeTimeInput").text(l1.cTime);
			$("#returnBillRemarks").val(l1.remarks);
			if(l1.checkName){
				$("#checkUserInput").text(l1.checkName)
			}
			if(l1.chTime){
				$("#checkTimeInput").text(l1.chTime)
			}
			$("#refuseReason").val(l1.rejectCause);
			if(l1.status=="1"){
				$('#beizhuhehehe').append("<span id=\"qqq\"><tr><td><span>拒单原因：</span></td><td><textarea id=\"refuseReason\" rows=\"1\" cols=\"35\" disable=\"disabled\" style=\"margin: 1px; height: 28px; width: 950px;\"></textarea></td></tr></span>");
			}
			for (var i = 0; i < list.length; i++) {
				var l = list[i];
				 html+="<tr>";
        		 html+="<td class='formdetail'><input class='center' type='text' name='id' readonly  value='"+(i+1)+"' style='width:100%;height:30px;border:none;'/></td>";
        		 html+="<td class='formdetail'><input class='center' type='text' name='code' readonly  value='"+JY.Object.notEmpty(l.enteryNo)+"' style='width:100%;height:30px;border:none;'/></td>";
        		 html+="<td class='formdetail'><input class='center' type='text' name='name' readonly  value='"+JY.Object.notEmpty(l.name)+"' style='width:100%;height:30px;border:none;'/></td>";
        		 html+="<td class='formdetail'><input class='center' type='text' name='goldType' readonly  value='"+JY.Object.notEmpty(l.goldName)+"' style='width:100%;height:30px;border:none;'/></td>";    		 
        		 html+="<td class='formdetail'><input class='center' type='text' name='unqualifyNum' readonly  value='"+parseFloat(JY.Object.notEmpty(l.unqualifyNum)).toFixed(4)+"' style='width:100%;height:30px;border:none;'/></td>";
        		 html+="<td class='formdetail'><input class='center' type='text' name='unqualifyWt' readonly  value='"+parseFloat(JY.Object.notEmpty(l.unqualifyWt)).toFixed(4)+"' style='width:100%;height:30px;border:none;'/></td>";
        		 html+="<td class='formdetail'><input class='center' type='text' name='goldWeight' readonly  value='"+parseFloat(empty(l.goldWeight)).toFixed(4)+"' style='width:100%;height:30px;border:none;'/></td>";
        		 html+="<td class='formdetail'><input class='center' type='text' name='stoneWeight' readonly  value='"+parseFloat(empty(l.stoneWeight)).toFixed(4)+"' style='width:100%;height:30px;border:none;'/></td>";
        		 html+="<td class='formdetail'><input class='center' type='text' name='basicCost' readonly  value='"+parseFloat(JY.Object.notEmpty(l.basicCost)).toFixed(4)+"' style='width:100%;height:30px;border:none;'/></td>";
        		 html+="<td class='formdetail'><input class='center' type='text' name='addCost' readonly  value='"+parseFloat(JY.Object.notEmpty(l.addCost)).toFixed(4)+"' style='width:100%;height:30px;border:none;'/></td>";
        		 html+="<td class='formdetail'><input class='center' type='text' name='otherCost' readonly  value='"+parseFloat(JY.Object.notEmpty(l.otherCost)).toFixed(4)+"' style='width:100%;height:30px;border:none;'/></td>";
        		 html+="<td class='formdetail'><input class='center' type='text' name='purCost' readonly  value='"+parseFloat(JY.Object.notEmpty(l.purCost)).toFixed(4)+"' style='width:100%;height:30px;border:none;'/></td>";        		 html+="</tr>";	
			}
			$("#returnBillDetailList tbody").append(html);
			commFoot();
		}
}

function empty(obj){
        //判断某对象不为空..返回obj 否则 ""
        if (obj === null)
            return "0.0000";
        else if (obj === undefined)
            return "0.0000";
        else if (obj === "undefined")
            return "0.0000";
        else if (obj === "")
            return "0.0000";
        else if (obj === "[]")
            return "0.0000";
        else if (obj === "{}")
            return "0.0000";
        else if (obj === "null")
        	return "0.0000";
        else
            return obj;
}

function view(returnBillId){
//	detailViewCommon("/scm/returnbill/find/",returnBillId);
	JY.Ajax.doRequest(null,jypath +"/scm/returnbill/find/" + returnBillId,{},function(data){
//	JY.Ajax.doRequest(null,jypath +"/scm/returnbill/check/",chk.toString(),function(data){
		detailViewCommon(data);
		viewInfo({id:"baseDiv",title:"查看退厂单明细",height:"550",width:"1150"} );
	})
}


function commFoot(){
	 $("#returnBillDetailList tfoot").html("");
	 var unqualifyNum=0.0000,unqualifyWt=0,goldWeight=0,fgoldWeight=0,
	 stoneWeight=0, basicCost=0,addCost=0.0000, otherCost=0, purCost=0;
	 $('#returnBillDetailList').find('input[name="unqualifyNum"]').each(function(element,index){
		 if($(this).val()!=''){unqualifyNum+=parseFloat($(this).val());}
	 });
	 $('#returnBillDetailList').find('input[name="unqualifyWt"]').each(function(element,index){
		 if($(this).val()!=''){unqualifyWt+=parseFloat($(this).val());}
	 });
	 $('#returnBillDetailList').find('input[name="goldWeight"]').each(function(element,index){
		 if($(this).val()!=''){goldWeight+=parseFloat($(this).val());}
	 });
	 $('#returnBillDetailList').find('input[name="fgoldWeight"]').each(function(element,index){
		 if($(this).val()!=''){fgoldWeight+=parseFloat($(this).val());}
	 });
	 $('#returnBillDetailList').find('input[name="stoneWeight"]').each(function(element,index){
		 if($(this).val()!=''){stoneWeight+=parseFloat($(this).val());}
	 });
	 $('#returnBillDetailList').find('input[name="basicCost"]').each(function(element,index){
		 if($(this).val()!=''){basicCost+=parseFloat($(this).val());}
	 });
	 $('#returnBillDetailList').find('input[name="addCost"]').each(function(element,index){
		 if($(this).val()!=''){addCost+=parseFloat($(this).val());}
	 });
	 $('#returnBillDetailList').find('input[name="otherCost"]').each(function(element,index){
		 if($(this).val()!=''){otherCost+=parseFloat($(this).val());}
	 });
	 $('#returnBillDetailList').find('input[name="purCost"]').each(function(element,index){
		 if($(this).val()!=''){purCost+=parseFloat($(this).val());}
	 });

	 var foot="";
	 foot+="<tr>";
	 foot+="<td class='count'>合计</td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+JY.Object.notEmpty(parseFloat(unqualifyNum).toFixed(4))+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+JY.Object.notEmpty(parseFloat(unqualifyWt).toFixed(4))+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+JY.Object.notEmpty(parseFloat(goldWeight).toFixed(4))+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+JY.Object.notEmpty(parseFloat(stoneWeight).toFixed(4))+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+JY.Object.notEmpty(parseFloat(basicCost).toFixed(4))+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+JY.Object.notEmpty(parseFloat(addCost).toFixed(4))+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+JY.Object.notEmpty(parseFloat(otherCost).toFixed(4))+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+JY.Object.notEmpty(parseFloat(purCost).toFixed(4))+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="</tr>";	
	 $("#returnBillDetailList tfoot").append(foot);
}

function viewInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({
		resizable:false,
		height:attr.height,
		width:attr.width,
		modal:true,
		title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",
		title_html:true,
		buttons:[
			{html: "<i class='icon-ok bigger-110'></i>&nbsp;打印预览","class":"btn btn-primary btn-xs",click:function(){print()}},
			{
			html:"<i class='icon-remove bigger-110'></i>&nbsp;退出",
			"class":"btn btn-xs",
			click:function(){
				$(this).dialog("close");
				}
		}]
	});
}


function addInfo(attr){
	$("#addReturnBillNoticeNo").val();
	$("#"+attr.id).removeClass('hide').dialog({
		resizable:false,
		height:attr.height,
		width:attr.width,
		modal:true,
		title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",
		title_html:true,
		buttons:[
			{
				html:"<i class='icon-ok bigger-110'></i>&nbsp;保存为草稿",
				"class":"btn btn-primary",
				click:function(){
					if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,4);}
//					saveRB(4);
					}
			},
			{
				html:"<i class='icon-ok bigger-110'></i>&nbsp;提交",
				"class":"btn btn-primary",
				click:function(){
					if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}
//					saveRB(0);
					}
			},
			{
			html:"<i class='icon-remove bigger-110'></i>&nbsp;退出",
			"class":"btn btn-xs",
			click:function(){
				$(this).dialog("close");
				getbaseList(1);
			}}
				
		]
	});
}


function modifyInfo(attr){
	$("#addReturnBillNoticeNo").val();
	$("#"+attr.id).removeClass('hide').dialog({
		resizable:false,
		height:attr.height,
		width:attr.width,
		modal:true,
		title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",
		title_html:true,
		buttons:[
			{
				html:"<i class='icon-ok bigger-110'></i>&nbsp;保存为草稿",
				"class":"btn btn-primary",
				click:function(){
//					saveModifyRB(4);
//					$(this).dialog("close");
					if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,4);}
					}
			},
			{
				html:"<i class='icon-ok bigger-110'></i>&nbsp;保存并提交",
				"class":"btn btn-primary",
				click:function(){
//					saveModifyRB(0);
//					$(this).dialog("close");
					if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}
					}
			},
			{
			html:"<i class='icon-remove bigger-110'></i>&nbsp;退出",
			"class":"btn btn-xs",
			click:function(){
				$(this).dialog("close");
			}}
				
		]
	});
}

function checkInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({
		resizable:false,
		height:attr.height,
		width:attr.width,
		modal:true,
		title:"<div class='widget-header'><h4 class='smaller'>"+
		(JY.Object.notNull(attr.title)?attr.title:"审核退厂单")+"</h4></div>",
		title_html:true,buttons:[{html:"<i class='icon-ok bigger-110'></i>&nbsp;通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,2,"您确认审核通过吗");}}},
	                             {html:"<i class='icon-remove bigger-110'></i>&nbsp;不通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,3,"您确认审核不通过吗");}}},
                                 {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
}

$('#addBtn').on('click',function(e){
	saveRB();
});



var chk = null;
function checkChooseSingle(){
	chk =[];    
	$('#returnBillList input[name="ids"]:checked').each(function(){
		chk.push($(this).val());    
	});     
	if(chk.length==0) {
		JY.Model.info("您没有选择任何内容!"); 
		return false;
	}
	if(chk.length>1){
		JY.Model.info("您只能选中一张单!"); 
		return false;
	}
	return chk;
}


$('#checkButton').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		$('#refuseCause').show();
		if(checkChooseSingle()==false){
			return;
		}
		cleanReturnForm();
//		detailViewCommon("/scm/returnbill/check/",chk.toString());
//		JY.Ajax.doRequest(null,jypath +"/scm/returnbill/check/",chk.toString(),function(data){
			JY.Ajax.doRequest(null,jypath +"/scm/returnbill/check/" + chk.toString(),{},function(data){
			detailViewCommon(data);
			checkInfo({id:"baseDiv",
				title:"审核单据",
				height:"600",
				width:"1100",
				savefn:function(checkType,checkTitle){
					var model1 =$(this);
					var html="";
					$("#jyConfirm").children().children().find(".causesDiv").remove();
					if(checkType==3){
						$("#jyConfirm").children().children().append('<div class="causesDiv"><form id="causesForm"><br><div style="float:left;font-size:13px;"><span style="float:left">驳回原因</span><br><input type="text" jyValidate="required" id="causesTxt" placeholder="请输入原因" style="width: 265px;"></div></form></div>');
					}
					var flag = false;
					if(checkType==3){
						flag = true;
					}
					JY.Model.confirm(checkTitle,function(){
						var model2 = $(this);
						var rejectCause = $("#causesTxt").val();
						var noticeNo = $("#inputNoticeNumber").val();
						if(checkType==3&&!JY.Validate.form("causesForm")){
							return false;
						}
						JY.Ajax.doRequest(null,jypath +'/scm/returnbill/modifyStatus/',{id:chk.toString(),status:checkType,rejectCause:rejectCause,noticeNo:noticeNo},function(data){
							model1.dialog("close");
							model2.dialog("close");
							JY.Model.info(data.resMsg,function(){getbaseList(1);});
						});
					},function(){},flag)}});
		});
});


function print(){
	 var id = $("#inputReturnBillNumber").val();
	 var noticeNo = $("#inputNoticeNumber").val();
	 $("#returnbillPrintInfoDiv").load(jypath +'/scm/returnbill/print?id='+id,function(){
		 LODOP=getLodop();  
		 LODOP.PRINT_INIT("退厂单打印");
		 LODOP.ADD_PRINT_BARCODE(16,98,176,21,"128B",noticeNo);
		 LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0); 	
		 LODOP.ADD_PRINT_HTM(0,0,"100%","100%",document.getElementById("returnBillPrintDiv").innerHTML);
		 LODOP.PREVIEW();
	 })
}


function cleanReturnForm(){
	JY.Tags.cleanForm("addReturnBillForm");
	$("#materialnoteDiv span").text("");
	$("#materialnoteDiv textarea").val("");
}






















