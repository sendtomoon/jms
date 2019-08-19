var qcFaqId = [];
var dataPrice=0;
$(function(){
	getbaseList(1)
	JY.Dict.setSelect("selectisValid","SCM_ORDER_STATUS",2,"全部");
	qcFaqId=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_QC_PROBLEM",qcFaqId);
	/*添加采购出库单*/
	$('#add').on('click', function(e) {
		e.preventDefault();
		cleanForm();findOrg(1);
		baseInfo({id:"baseDiv",title:"质检单增加",height:"730",width:"1024",savefn:function(types){
			var that =$(this);
			var result=total();
			if(JY.Validate.form("baseForm") && $('#reportAdd tbody tr').length>0 && result==true){
				var json_data="";
		 	    var last_index=$('#reportAdd tbody tr').length-1;
		 	    $('#reportAdd tbody tr').each(function(element,index){
		 	    var data='{"ngNumber":"'+$(this).find("input[name=ngNumberDetail]").val()+'","ngWeight":"'+$(this).find("input[name=ngWeightDetail]").val()+'","qcFaqId":"'+$(this).find("select[name=qcFaqIdDetail]").val()+'","qcFaqDesc":"'+$(this).find("input[name=qcFaqDescDetail]").val()+'","prodId":"'+$(this).find("input[name=prodIdDetail]").val()+'","suppmouCode":"'+$(this).find("input[name=suppmouCodeDetail]").val()+'"}';	
		 	    if($(this).index()==last_index){
		        	json_data+=data;
		        }else{
		        	json_data+=data+',';
		        }
			    });
			    json_data='['+json_data+']';
			    var jsonData = new FormData($("#baseForm")[0]); 
                jsonData.append('myData', json_data);  
                jsonData.append('status', types);
			    $.ajax({type:'POST',url:jypath+"/scm/report/add",data: jsonData,async: true,cache: false,contentType: false,processData: false,success:function(data,textStatus){  	
			    	var json_obj=eval('('+data+')');
					if(json_obj.res==1){
			        	that.dialog("close");      
			        	JY.Model.info(json_obj.resMsg,function(){search();});
			        }else{
			        	JY.Model.error(json_obj.resMsg);
			        } 
                }});
			}
		}})	
	});
	
	
	
	//查看
	$('#view').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#reportTable input[name="ids"]:checked').each(function(){    
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
	
	
	//批量删除   
	$('#del').on('click', function(e) {
		$("#jyConfirm").children().children().find(".causesDiv").remove();
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#reportTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			JY.Model.confirm("确认要删除选中的数据吗?",function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/report/delete',{cheks:chks.toString()},function(data){
					JY.Model.info(data.resMsg,function(){search();});
				});
			});		
		}		
	});
	
	//审核
	$('#aduit').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#reportTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{
			aduit(chks[0])
		}	
	});
	
	//修改
	$('#edit').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#reportTable input[name="ids"]:checked').each(function(){    
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
	
	$("#entryNo").typeahead({
        source: function (query, process) {
        	$.ajax({
				type:'POST',
				url:jypath+'/scm/circulation/selectNoticeno',
				data:{noticeno:query},
				dataType:'json',
				success:function(result,textStatus){ 
					var arr= [];
					 $.each(result.obj, function (index, ele) {
						 arr.push(ele.noticeNo);
                     });
					process(arr);
				}
			});
        	
        },
        items: 20,
        afterSelect: function (value) {
        	//
        },
        delay: 500,
        highlighter: function (item) {//item是用户输入
	        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')//这是将字符串安全格式化为正则表达式的源码
	            return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	                return '<strong style="color:red;">' + match + '</strong>'//将下拉列表中符合项中的LI的innerHTML相等部分加个strong标签
	            })
	    }
    });
	
	
})


function total(){
	var totalNum=$('#reportAdd').find('input[name="totalNum"]').val();
	var num=$('#qcNumber').val();
	var totalWeight=$('#reportAdd').find('input[name="totalWeight"]').val();
	var weight=$('#qcWeight').val();
	if(parseFloat(num)<parseFloat(totalNum)){
		$('#qcNumber').tips({
             msg: "质检数量不能小于总数量!",
             bg: '#FF2D2D',
             time: 1
         });
		return false;
	}
	if(parseFloat(weight)<parseFloat(totalWeight)){
		$('#qcWeight').tips({
             msg: "质检重量不能小于总重量!",
             bg: '#FF2D2D',
             time: 1
         });
		return false;
	}
	return true;
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


/*刷新列表页*/
function search(){
	$("#searchBtn").trigger("click");
}

/*修改出库*/
function edit(id){
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/scm/report/find',{id:id,flag:1},function(data){
		if(data.obj.report.status=="4"){
			$("#baseForm #causesDiv").removeClass("hide");
		}
		setBaseForm(data);
		baseInfo({id:"baseDiv",title:"质检单修改",height:"730",width:"1024",savefn:function(types){
			var that =$(this);
			var result=total();
			if(JY.Validate.form("baseForm") && $('#reportAdd tbody tr').length>0 && result==true){
				var json_data="";
		 	    var last_index=$('#reportAdd tbody tr').length-1;
		 	    $('#reportAdd tbody tr').each(function(element,index){
		 	    var data='{"id":"'+$(this).find("input[name=idDetail]").val()+'","ngNumber":"'+$(this).find("input[name=ngNumberDetail]").val()+'","ngWeight":"'+$(this).find("input[name=ngWeightDetail]").val()+'","qcFaqId":"'+$(this).find("select[name=qcFaqIdDetail]").val()+'","qcFaqDesc":"'+$(this).find("input[name=qcFaqDescDetail]").val()+'","prodId":"'+$(this).find("input[name=prodIdDetail]").val()+'","suppmouCode":"'+$(this).find("input[name=suppmouCodeDetail]").val()+'"}';	
		 	    if($(this).index()==last_index){
		        	json_data+=data;
		        }else{
		        	json_data+=data+',';
		        }
			    });
			    json_data='['+json_data+']';
			    var jsonData = new FormData($("#baseForm")[0]); 
			    jsonData.append('status', types);
                jsonData.append('myData', json_data);  
			    $.ajax({type:'POST',url:jypath+"/scm/report/update",data: jsonData,async: true,cache: false,contentType: false,processData: false,success:function(data,textStatus){  	
			    	var json_obj=eval('('+data+')');
					if(json_obj.res==1){
			        	that.dialog("close");      
			        	JY.Model.info(json_obj.resMsg,function(){search();});
			        }else{
			        	JY.Model.error(json_obj.resMsg);
			        } 
                }});
			}
		}})	
	});
}

/*审核*/
function aduit(id){
	cleanForm();
	$('#baseForm').find('.btnClass').hide();
	JY.Ajax.doRequest(null,jypath +'/scm/report/find',{id:id,flag:2},function(data){
		setBaseForm(data);
		$('#baseForm').find('input,select,textarea').attr('disabled','disabled');
		checkInfo({id:"baseDiv",title:"质检单审核",height:"730",width:"1024",savefn:function(checkType,checkTitle){
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
				JY.Ajax.doRequest(null,jypath +'/scm/report/aduit',{id:id,status:checkType,rejectinfo:causes},function(data){
					model1.dialog("close");
					model2.dialog("close"); 
					var json_obj=data;
					if(json_obj.res==1 && checkType==2){
			        	//JY.Model.info(json_obj.resMsg,function(){
			        		if(!JY.Object.notNull(json_obj.flag)){
//								JY.Model.confirm("是否立即生成退厂单?",function(){	
									JY.Ajax.doRequest(null,jypath +'/scm/returnbill/newReturnBill/'+json_obj.obj,{},function(data){
										JY.Model.info(data.resMsg);
										search();
									});
//								});	
							}
			        		search();
			        	//});
			        }else{
			        	JY.Model.info(json_obj.resMsg);
			        	search();
			        } 
				});
			},function(){},flag)
		}});
	});
}


/*出库明细*/
function view(id){
	cleanForm();
	$('#baseForm').find('.btnClass').hide();
	JY.Ajax.doRequest(null,jypath +'/scm/report/find',{id:id},function(data){
		if(data.obj.report.status=="4"){
			$("#baseForm #causesDiv").removeClass("hide");
		}
		setBaseForm(data);
		viewInfo({id:"baseDiv",title:"质检单查看",height:"730",width:"1024"})
		$('#baseForm').find('input,select,textarea').attr('disabled','disabled');
	});
	
}

/*设置表单值*/
function setBaseForm(data){
	 var p=data.obj.report;
	 JY.Tools.populateForm("baseForm",p);
	 findOrg(2,p.qcUserId);
	// var opts="<option>" + p.qcUserName + "</option>";
	 $('#baseForm').find('select[name="qcUserId"]').attr("value",p.qcUserName);
	 $("#baseForm textarea[name$='remarks']").val(JY.Object.notEmpty(p.remarks));
	 $("#baseForm textarea[name$='rejectinfo']").val(JY.Object.notEmpty(p.rejectinfo));
	 document.getElementById("qcNumber").setAttribute('qcNumber',p.num);
		document.getElementById("qcWeight").setAttribute('qcWeight',p.weight);
	 if(JY.Object.notEmpty(p.attachment)){
		 $("#file").append("<a href='"+JY.Object.notEmpty(p.attachment.path)+"?filename="+JY.Object.notEmpty(p.attachment.name)+"'>"+JY.Object.notEmpty(p.attachment.name)+"</a>")
	 }
	 $("#baseForm span[id$='createUser']").text(JY.Object.notEmpty(p.createName));
	 if(JY.Object.notEmpty(p.createTime)){
		 $("#baseForm span[id$='createTime']").text(JY.Date.Format(p.createTime,"yyyy/MM/dd hh:mm:ss"));
	 }	
	 $("#baseForm span[id$='updateUser']").text(JY.Object.notEmpty(p.updateName));
	 if(JY.Object.notEmpty(p.updateTime)){
		 $("#baseForm span[id$='updateTime']").text(JY.Date.Format(p.updateTime,"yyyy/MM/dd hh:mm:ss"));
	 }
	 $("#baseForm span[id$='checkUser']").text(JY.Object.notEmpty(p.checkName));
	 if(JY.Object.notEmpty(p.checkTime)){
		 $("#baseForm span[id$='checkTime']").text(JY.Date.Format(p.checkTime,"yyyy/MM/dd hh:mm:ss"));
	 }
	 commCode(data.obj.details,data.obj.report.flag)
}

/*搜索条码或入库单号公共数据*/
function commCode(obj,type){
	var list=[];
	var html="";
	if(obj!=null){
		for(var i=0;i<obj.length;i++){ 
			$("#reportAdd input[name$='prodIdDetail']").each(function(){  
				list.push($(this).val());
			});
			if(list.indexOf(obj[i].prodId)==-1){
				 html+="<tr>";
				 html+="<td class='center'><label> <input type='checkbox' name='idsDetail' value='"+JY.Object.notEmpty(obj[i].id)+"' class='ace' /><span class='lbl'></span></label></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='codeDetail' readonly  value='"+JY.Object.notEmpty(obj[i].code)+"' style='width:100%;height:30px;border:none;' readonly /><input type='hidden' name='prodIdDetail' value='"+JY.Object.notEmpty(obj[i].prodId)+"'><input type='hidden' name='suppmouCodeDetail' value='"+JY.Object.notEmpty(obj[i].suppmouCode)+"'></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='nameDetail' readonly value='"+JY.Object.notEmpty(obj[i].name)+"' style='width:100%;height:30px;border:none;'/><input type='hidden' name='idDetail' value='"+JY.Object.notEmpty(obj[i].id)+"'/></td>";
				 html+="<td style='padding:1px;'><input class='center' type='text' name='goldtypeDetail' readonly  value='"+JY.Object.notEmpty(obj[i].goldtype)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
				 if(type==0){
					 html+="<td style='padding:1px;'><input class='center' type='text' name='ngNumberDetail' jyValidate='required' onkeyup='JY.Validate.limitNum(this)' onchange='changeNum("+obj[i].tolNgNumber+",$(this))'  value='"+JY.Object.notNumber(obj[i].ngNumber)+"' style='width:100%;height:30px;border:none;'/></td>";
					 html+="<td style='padding:1px;'><input class='center' type='text' name='ngWeightDetail' jyValidate='required' onkeyup='JY.Validate.limitDouble(this)' onchange='changeWeight("+obj[i].tolNgWeight+",$(this))' value='"+parseFloat(JY.Object.notNumber(obj[i].ngWeight)).toFixed(4)+"' style='width:100%;height:30px;border:none;'/></td>";
				 }else{
					 html+="<td style='padding:1px;'><input class='center' type='text' name='ngNumberDetail' jyValidate='required' onkeyup='JY.Validate.limitNum(this)' readonly  value='"+JY.Object.notNumber(obj[i].ngNumber)+"' style='width:100%;height:30px;border:none;'/></td>";
					 html+="<td style='padding:1px;'><input class='center' type='text' name='ngWeightDetail' jyValidate='required'  onkeyup='JY.Validate.limitDouble(this)' readonly value='"+parseFloat(JY.Object.notNumber(obj[i].ngWeight)).toFixed(4)+"' style='width:100%;height:30px;border:none;'/></td>";
				 }
				 html+="<td style='padding:1px;' class='center'><select id='"+JY.Object.notEmpty(obj[i].prodId)+" data-placeholder='状态' name='qcFaqIdDetail' class='isSelect145' jyValidate='required' "+options(qcFaqId, JY.Object.notEmpty(obj[i].qcFaqId))+"></select></td>";
				 /*html+="<td style='padding:1px;'><input class='center' type='text' name='qcFaqIdDetail' jyValidate='required' value='"+JY.Object.notEmpty(obj[i].qcFaqId)+"' style='width:100%;height:30px;border:none;'/></td>";*/
				 html+="<td style='padding:1px;'><input class='center' type='text' name='qcFaqDescDetail' value='"+JY.Object.notEmpty(obj[i].qcFaqDesc)+"' style='width:100%;height:30px;border:none;'/></td>";
				 html+="</tr>";	
			}else{
				$('#enteryno').tips({
			         msg: "数据已存在!",
			         bg: '#FF2D2D',
			         time: 1
			    });
			}	
		}
	    $("#reportAdd tbody").append(html);
	    commFoot();
	}
}


function changeNum(totalNum,num){
	var number=num.parent().parent().find('input[name="ngNumberDetail"]').val();
	if(totalNum<parseInt(number)){
		 num.tips({
             msg: "数量不能超过最大值!",
             bg: '#FF2D2D',
             time: 1
         });
		num.parent().parent().find('input[name="ngNumberDetail"]').val(totalNum);
	}
	commFoot();
}

function changeWeight(totalWeight,weight){
	var number=weight.parent().parent().find('input[name="ngWeightDetail"]').val();
	if(totalWeight<parseFloat(number)){
		weight.tips({
            msg: "重量不能超过最大值!",
            bg: '#FF2D2D',
            time: 1
        });
		weight.parent().parent().find('input[name="ngWeightDetail"]').val(parseFloat(JY.Object.notNumber(totalWeight)).toFixed(4));
	}
	commFoot();
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


/*质检列表页*/
function getbaseList(init){
	if(init==1)$("#reportForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("reportForm",jypath +'/scm/report/findByPage',null,function(data){
		 $("#reportTable tbody").empty();
        	 var obj=data.obj;
        	 var list=data.obj;
        	 var results=list.results;
        	 var permitBtn=obj.permitBtn;
         	 var pageNum=list.pageNum,pageSize=list.pageSize,totalRecord=list.totalRecord;
        	 var html="";
    		 if(results!=null&&results.length>0){
        		 var leng=(pageNum-1)*pageSize;//计算序号
        		 for(var i = 0;i<results.length;i++){
        			 var l=results[i];
            		 html+="<tr>";
            		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
            		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
            		 html+="<td class='center hidden-480'><a style='cursor:pointer;' onclick='view(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.reportNo)+"</a></td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.entryNo)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.qcUserName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notNumber(l.qcNumber)+"</td>";
            		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notNumber(l.qcWeight)).toFixed(4)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notNumber(l.qcNgNumber)+"</td>";
            		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notNumber(l.qcNgWeight)).toFixed(4)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notNumber(l.supplierId)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.remarks)+"</td>";
            		 if(l.status==1) {html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>待审核</span></td>";}
            		 else if(l.status==3)  { html+="<td class='center hidden-480'><span class='label label-sm  label-success'>已完成</span></td>";}   
            		 else if(l.status==0)  { html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>草稿</span></td>";}
            		 else if(l.status==2)  { html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已审核</span></td>";}
            		 else if(l.status==4)  { html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已拒绝</span></td>";}
            		 else { html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已删除</span></td>";}
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.createName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.createTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.checkName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.checkTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
            		 html+="</tr>";		 
            	 } 
        		 $("#reportTable tbody").append(html);
        		 JY.Page.setPage("reportForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='16' class='center'>没有相关数据</td></tr>";
        		$("#reportTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
    		 JY.Model.loadingClose();
	 });
}

function cleanForm(){
	$('#baseForm').find('.btnClass').show();
	$("#baseForm #causesDiv").addClass("hide");
	$('#baseForm').find('input,select,textarea').removeAttr('disabled','disabled');
	JY.Tags.cleanForm("baseForm");
	$('#baseForm').find('select[name="qcUserId"]').empty();
	$("#reportAdd tbody").empty();
	$("#file").html("");
	$("#baseForm span[id$='createUser']").text('');
	$("#baseForm span[id$='createTime']").text('');
	$("#baseForm span[id$='updateUser']").text('');
	$("#baseForm span[id$='updateTime']").text('');
	$("#baseForm span[id$='checkUser']").text('');
	$("#baseForm span[id$='checkTime']").text('');
}



/*底部合计*/
function commFoot(){
	 $("#reportAdd tfoot").html("");
     var num=0,weight=0;
	 $('#reportAdd').find('input[name="ngNumberDetail"]').each(function(element,index){
		 if($(this).val()!=''){num+=parseFloat($(this).val());}
	 });
	 $('#reportAdd').find('input[name="ngWeightDetail"]').each(function(element,index){
		 if($(this).val()!=''){ weight+=parseFloat($(this).val());}
	 });
     var foot="";
	 foot+="<tr>";
	 foot+="<td class='center'>合计</td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly name='totalNum'  value='"+num+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  name='totalWeight' value='"+parseFloat(weight).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="</tr>";	
	 $("#reportAdd tfoot").append(foot);
}


var patrn=/^RN[0-1][0-9]{13}$/;
function subcode(nol){
	var no=nol.trim();
	//cleanForm();
	$("#reportAdd tbody").empty();
	$("#reportAdd tfoot").empty();
	$('#baseForm').find('input[name="supplierId"]').val("");
	$('#baseForm').find('input[name="qcUserName"]').val("");
	if(no.length!=0 && patrn.exec(no)){
		var patrn1=/^RN1[0-9]{13}$/,flag="";
		JY.Ajax.doRequest(null,jypath +'/scm/report/queryCode',{entryNo:no,flag:(patrn1.exec(no)?flag="1":flag)},function(data){
			if(JY.Object.notEmpty(data.resMsg).length>0){
				$('#entryNo').tips({
			         msg: data.resMsg,
			         bg: '#FF2D2D',
			         time: 1
			     });
			}else{
				commCode(data.obj.details,data.obj.report.flag);
				$('#baseForm').find('input[name="supplierId"]').val(JY.Object.notEmpty(data.obj.report.supplierName));
				$('#baseForm').find('input[name="qcUserName"]').val(JY.Object.notEmpty(data.obj.report.qcUserName));
				document.getElementById("qcNumber").setAttribute('qcNumber',data.obj.report.qcNumber);
				document.getElementById("qcWeight").setAttribute('qcWeight',data.obj.report.qcWeight);
			}
		});
	}
	/*else{
		JY.Model.info("请填写正确的入库通知单!"); 
	}*/
}

function changeQcNumber(qcNumber){
	//总数量
	var number=$('#qcNumber').attr('qcnumber');
	//质检数量不得总数量
	if(parseInt(qcNumber)>parseInt(number)){
		$('#qcNumber').tips({
             msg: "质检数量不能超过最大值!",
             bg: '#FF2D2D',
             time: 1
         });
		 $('#baseForm').find('input[name="qcNumber"]').val(number);
	}
}

function changeQcWeight(qcWeight){
	//总重
	var weight=$('#qcWeight').attr('qcWeight');
	//质检重量不得总重
	if(parseFloat(qcWeight)>parseFloat(weight)){
		$('#qcWeight').tips({
             msg: "质检重量不能超过最大值!",
             bg: '#FF2D2D',
             time: 1
         });
		$('#baseForm').find('input[name="qcWeight"]').val(weight);
		
	}
}

function subcodeTwo(){
	var no=$("input[id='enteryno']").val().trim();
	var entryNo=$('#baseForm').find('input[name="entryNo"]').val().trim();
	if(JY.Object.notNull(no) && JY.Object.notNull(entryNo)){
		JY.Ajax.doRequest(null,jypath +'/scm/report/queryCodeTwo',{entryNo:entryNo,code:no},function(data){
			if(JY.Object.notEmpty(data.resMsg).length>0){
				$('#enteryno').tips({
			         msg: data.resMsg,
			         bg: '#FF2D2D',
			         time: 1
			     });
			}else{
				commCode(data.obj.details,data.obj.report.flag);
			}
		});
	}
	$("input[id='enteryno']").val("");
}

/*删除*/
function delReport(){
	var chks =[];    
	$("#jyConfirm").children().children().find(".causesDiv").remove();
	$('#reportAdd input[name="idsDetail"]:checked').each(function(){ 
		chks.push($(this).val());    
	});     
	if(chks.length==0) {
		JY.Model.info("您没有选择任何内容!"); 
	}else{
		JY.Model.confirm("确认要删除选中的数据吗?",function(){	
			$('#reportAdd input[name="idsDetail"]:checked').each(function(){  
				$(this).parent().parent().parent().remove();
				commFoot();
			}); 
		});		
	}	
}



function baseInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({
    	resizable:false,
    	height:attr.height,
    	width:attr.width,
    	modal:true,
    	title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",
     title_html:true,
     buttons:[
     	{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存为草稿",
        	 "class":"btn btn-primary btn-xs",
        	 click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
     	{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存并提交",
     	 "class":"btn btn-primary btn-xs",
     	 click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},
     	 {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",
     	 click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);
     	 }
     	}
     	}]
     });
}



function viewInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,
		buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;打印预览","class":"btn btn-primary btn-xs",click:function(){print()}},
		         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
}

function checkInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
		buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,2,"您确认审核通过吗");}}},
		        {html: "<i class='icon-remove bigger-110'></i>&nbsp;不通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,4,"您确认审核不通过吗");}}},
		        {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
	});
}


function print(){
	 var id = $("#baseForm input[name='id']").val();
	 var reportNo = $("#baseForm input[name='reportNo']").val();
	 $("#printDiv").load(jypath +'/scm/report/print?id='+id,function(){
		 LODOP=getLodop();  
		 if(LODOP){
			 LODOP.PRINT_INIT("打印控件功能演示");
			 LODOP.ADD_PRINT_BARCODE(16,78,166,21,"128B",reportNo);
			 LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
			 LODOP.ADD_PRINT_HTM(0,0,"100%","100%",document.getElementById("testPrintdiv").innerHTML);
			 LODOP.PREVIEW();
		 }
	 })

}

function cleanBaseForm(){
	JY.Tags.cleanForm("reportForm");
}