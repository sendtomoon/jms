var goldTypes = [];
var cou = 0;
$(function(){
	//下拉框
	goldTypes=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_PRO_GOLD_TYPE",goldTypes);
	JY.Dict.setSelect("selectMaterialcomeStatus","SCM_MATERIALCOME_STATUS",2,"全部");
	JY.Dict.setSelect("selectGoldType","SCM_PRO_GOLD_TYPE",1);
	
//	function printMaterialcome(){} 
	
	getReceiver();
	getbaseList(1);
	var moudtlMap = {};
	
	//下载导入模版
	$('#downloadTemplate').on('click',function(){
		$(this).prop("href",jypath+"/scm/common/downloads?name="+encodeURI('素金来货导入模版.xls'));
	});
	
	$('#importBtn').on('click',function(e){
		 importInfo({
				id:"excel",
				title:"来料",
				height:"200",
				width:"600"});
		});
	
	function importInfo(attr){
		$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
	};
	
	$("#excelForm").ajaxForm({
        dataType:'json',
        beforeSend: function() {
            if($("#fileUpload").val()==""){
            	JY.Model.info("请上传文件！")
            	return false;
            }
        },
        success: function(data) {
        	JY.Model.info(data.resMsg);
        	if(data.obj==1){
        		JY.Model.info(data.resMsg,function(){
        			$("#excel").addClass('hide').dialog("close");
        			search();
        		});
        	}
        },
        resetForm : true,
        error:function(){
        	JY.Model.info(data.resMsg);
        }
    });
})

//拨入单位
//function loadOrgTree(){
//	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getPreOrgTree',null,function(data){
//		$.jy.dropTree.init({
//			rootId:"queryhandover",
//			displayId:"queryhandoverorgName",
//			data:data.obj,
//			dataFormat:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'},
//			isExpand:true,
//			resetFn:function(){
//				$("#queryOrg #orgName").val('');
//				$("#queryOrg #orgId").val('');
//			},
//			clickFn:function(node){
//				$("#queryOrg #orgId").val(node.id);
//			}
//		});
//		$.jy.dropTree.checkNode("queryOrg","orgName",curUser.orgId,function(){
//			$("#queryOrg #orgId").val(curUser.orgId);
//			$("#queryOrg #orgName").prop('disabled','disabled');
//			 $("materialcomeForm#queryOrg .icon-remove").hide();
//		});
//	});
//	
//}

function setOrg(){
	JY.Ajax.doRequest(null,jypath +'/scm/materialcome/getOrg',null,function(data){
		$("#materialcomeTab input[name='orgName']").val(data.obj.orgName);
		$("#materialcomeTab input[name='orgId']").val(data.obj.orgId);
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

/*计算称差，牌价等等的总和*/
function addNum(){
	 var counts=0,sumRequireWt=0,sumActualWt=0,sumBasicCost=0,sumAddCost=0,sumOtherCost=0,sumCostPrice=0;
	 $('#materialcomeAdd').find('input[name="count"]').each(function(element,index){
		 if($(this).val()!=''){counts+=parseFloat($(this).val());}
	 });
	 $('#materialcomeAdd ').find('input[name="requireWt"]').each(function(element,index){
		 if($(this).val()!=''){ sumRequireWt+=parseFloat($(this).val());}
	 });
	 $('#materialcomeAdd').find('input[name="actualWt"]').each(function(element,index){
		 if($(this).val()!=''){sumActualWt+=parseFloat($(this).val());}
	 });
	 $('#materialcomeAdd').find('input[name="basicCost"]').each(function(element,index){
		 if($(this).val()!=''){ sumBasicCost+=parseFloat($(this).val());}
	 });
	 $('#materialcomeAdd').find('input[name="addCost"]').each(function(element,index){
		 if($(this).val()!=''){sumAddCost+=parseFloat($(this).val());}
	 });
	 $('#materialcomeAdd').find('input[name="otherCost"]').each(function(element,index){
		 if($(this).val()!=''){sumOtherCost+=parseFloat($(this).val());}
	 });
	 
	 $('#materialcomeAdd').find('input[name="costPrice"]').each(function(element,index){
		 if($(this).val()!=''){sumCostPrice+=parseFloat($(this).val());}
	 });
	 $("#materialcomeAdd  span[id='counts']").text(counts);
	 $("#materialcomeAdd  span[id='sumRequireWt']").text(sumRequireWt.toFixed(4));
	 $("#materialcomeAdd  span[id='sumActualWt']").text(sumActualWt.toFixed(4));
	 $("#materialcomeAdd  span[id='sumBasicCost']").text(sumBasicCost.toFixed(4));
	 $("#materialcomeAdd  span[id='sumAddCost']").text(sumAddCost.toFixed(4));
	 $("#materialcomeAdd  span[id='sumOtherCost']").text(sumOtherCost.toFixed(4));
	 $("#materialcomeAdd  span[id='sumCostPrice']").text(sumCostPrice.toFixed(4));
}

/*刷新列表页*/
function search(){
	$("#searchBtn").trigger("click");
}

function toFix(value){
	var zero = 0;
	return !value?zero.toFixed(4):value.toFixed(4);
}


/*来料列表*/
function getbaseList(init){
	if(init==1)$("#materialcomeBaseForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("materialcomeBaseForm",jypath +'/scm/materialcome/findByPage',null,function(data){
		 $("#materialcomeTable tbody").empty();
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
            		 html+="<td class='center hidden-480' ><input type='hidden' value='"+l.noticeNo+"'/><a style='cursor:pointer;' onclick='find(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.noticeNo)+"</a></td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.purchaseNo)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.orgName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.count)+"</td>";
            		 html+="<td class='center hidden-480'>"+toFix(l.requireWt)+"</td>";
            		 html+="<td class='center hidden-480'>"+toFix(l.basicCost)+"</td>";
            		 html+="<td class='center hidden-480'>"+toFix(l.addCost)+"</td>";
            		 html+="<td class='center hidden-480'>"+toFix(l.otherCost)+"</td>";
            		 html+="<td class='center hidden-480'>"+toFix(l.costPrice)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.surpplyName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.operator)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.receiverName)+"</td>";
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
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已拒绝</span></td>";
            		 }
            		 if(l.status==9){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已删除</span></td>";
            		 }
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.createUser)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.createTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.checkUser)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.checkTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
            		 html+="</tr>";		 
            	 } 
        		 $("#materialcomeTable tbody").append(html);
        		 JY.Page.setPage("materialcomeTable","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='19' class='center'>没有相关数据</td></tr>";
        		$("#materialcomeTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
    		 JY.Model.loadingClose();
	 });
}

function subPur(value){
	if(value){
		JY.Ajax.doRequest(null,jypath +'/scm/materialcome/findPurchaseNo',{purchaseNo:value.trim()},function(data){
			if(data.obj.material){
				$("#materialcomeForm input[name='surpplyName']").val(data.obj.material.surpplyName);
				$("#materialcomeForm input[name='surpplyId']").val(data.obj.material.surpplyId);
			}else{
				$("#materialcomeForm input[name='surpplyName']").val("");
				$("#materialcomeForm input[name='surpplyId']").val("");
			}
			
		});
	}
}

//删除来料
$('#delBtn').on('click', function(e) {
	$("#jyConfirm").children().children().find(".causesDiv").remove();
	//通知浏览器不要执行与事件关联的默认动作		
	e.preventDefault();
	var chks =[];    
	$('#materialcomeTable input[name="ids"]:checked').each(function(){    
		chks.push($(this).val());    
	});     
	if(chks.length==0) {
		JY.Model.info("您没有选择任何内容!"); 
	}else{
		JY.Model.confirm("确认要删除选中的数据吗?",function(){	
			JY.Ajax.doRequest(null,jypath +'/scm/materialcome/delete',{chks:chks.toString()},function(data){
				JY.Model.info(data.resMsg,function(){search();});
			});
		});		
	}		
});

//查看来料
$('#viewBtn').on('click', function(e) {
	var chks =[];    
	$('#materialcomeTable input[name="ids"]:checked').each(function(){    
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
	$('#materialcomeTable input[name="ids"]:checked').each(function(){    
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

//审核来料
$('#aduitBtn').on('click', function(e) {
	var chks =[];    
	$('#materialcomeTable input[name="ids"]:checked').each(function(){    
		chks.push($(this).val());    
	}); 
	if(chks.length==0){
		JY.Model.info("您没有选择任何内容!"); 
	}else if(chks.length>1){
		JY.Model.info("请选择一条内容!"); 
	}else{
		check(chks[0]);
	}
});

//填充审核来料
function check(id){
	$("#materialcomeForm #causesDiv").addClass("hide");
	$("#selectReceiver").attr("disabled","disabled");
	$("#materialcomeTab input[name='purchaseNo']").attr("disabled","disabled");
	$('#materialcomeForm').find('.btnClass').hide();
	$("textarea[name='note']").attr("disabled","disabled");
	//加载dialog
	cleanMaterialcomeForm();
	JY.Ajax.doRequest(null,jypath +'/scm/materialcome/find',{id:id,flag:2},function(data){ 
		setForm(data); 
		checkInfo({id:"materialcomeDiv",title:"审核入库通知单",height:"700",width:"1100",savefn:function(checkType,checkTitle){
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
				var causes = $("#causesTxt").val();
				if(checkType==3&&!JY.Validate.form("causesForm")){
					return false;
				}
				JY.Ajax.doRequest(null,jypath +'/scm/materialcome/aduit',{id:id,status:checkType,causes:causes,note:$("#materialcomeDiv textarea[name='note']").val()},function(data){
					model1.dialog("close");
					model2.dialog("close");
					JY.Model.info(data.resMsg,function(){search();});
				});
			},function(){},flag)
		}});
	});
}

//填充修改来料
function update(id){
	//加载dialog
	$('#materialcomeForm').find('.btnClass').show();
	$("#materialcomeForm #causesDiv").addClass("hide");
	$("#materialcomeTab input[name='purchaseNo']").removeAttr("disabled","disabled");
	$("#materialcomeTab select[name='receiverId']").removeAttr("disabled","disabled");
	$('#materialcomeDiv').find('textarea[name="note"]').removeAttr('disabled','disabled');
	cleanMaterialcomeForm();
	JY.Ajax.doRequest(null,jypath +'/scm/materialcome/find',{id:id,flag:1},function(data){ 
		if(data.obj.list[0].status=="3"){
			$("#materialcomeForm #causesDiv").removeClass("hide");
		}
		setUpdateForm(data)
		baseInfo({id:"materialcomeDiv",title:"修改入库通知单",height:"720",width:"1100",savefn:function(type){
			var that =$(this);
			if(JY.Validate.form("materialcomeForm")){
			var jsonData="";
			var last_index=$('#materialcomeAdd tbody tr').length-1;
			var counts=0,sumRequireWt=0;
			counts = $(this).find("#counts").text();
			sumActualWt = $(this).find("#sumActualWt").text();
			
			var sumBasicCost = $(this).find("#sumBasicCost").text();
			var sumAddCost = $(this).find("#sumAddCost").text();
			var sumOtherCost = $(this).find("#sumOtherCost").text();
			
			$('#materialcomeAdd tbody tr').each(function(element,index){
				data='{"id":"'+id+'","detailId":"'+JY.Object.notEmpty($(this).find("input[name=detailId]").val())+'","sumBasicCost":"'+sumBasicCost+'","sumAddCost":"'+sumAddCost+'","sumOtherCost":"'+sumOtherCost+'","totalCount":"'+counts+'","totalWt":"'+sumActualWt+'","noticeId":"'+JY.Object.notEmpty($(this).find("input[name=noticeId]").val())+'","goldType":"'+$(this).find("select[name=goldType]").val()+'","goldTypeName":"'+$(this).find("select[name=goldType] option:selected").text()+'","count":"'+$(this).find("input[name=count]").val()+'","name":"'+$(this).find("input[name=name]").val()+'","requireWt":"'+$(this).find("input[name=requireWt]").val()+'","actualWt":"'+$(this).find("input[name=actualWt]").val()+'","remainCount":"'+$(this).find("input[name=count]").val()+'","remainWt":"'+$(this).find("input[name=actualWt]").val()+'","goldWt":"0","stoneWt":"0","basicCost":"'+$(this).find("input[name=basicCost]").val()+'","addCost":"'+$(this).find("input[name=addCost]").val()+'","otherCost":"'+$(this).find("input[name=otherCost]").val()+'","costPrice":"'+$(this).find("input[name=costPrice]").val()+'","note":"'+JY.Object.notEmpty($("#materialcomeForm textarea[name='note']").val())+'","purchaseNo":"'+$("#materialcomeTab").find("input[name=purchaseNo]").val()+'","receiverId":"'+$("#selectReceiver option:selected").val()+'","receiverName":"'+$("#selectReceiver option:selected").text()+'","status":"'+type+'"}';	
				if($(this).index()==last_index){
	 	    		jsonData+=data;
		        }else{
		        	jsonData+=data+',';
		        };
		    });
			jsonData='['+jsonData+']';
			JY.Ajax.doRequest(null,jypath +'/scm/materialcome/update',{data:jsonData.toString()},function(data){
	        	if(data.res==1){
	        		that.dialog("close");
	        		JY.Model.info(data.resMsg,function(){search();});
	        	}else{
	        		 JY.Model.error(data.resMsg); 
	        	}     	
				JY.Model.info(data.resMsg,function(){search();});
			});
		}}});
	});
}


//获取收货人
function getReceiver(){
	var receiver = document.getElementById("selectReceiver");
	JY.Ajax.doRequest(null,jypath +'/scm/materialcome/receiverAll',null,function(data){
		var obj=data.obj;
    	 for (var i = 0; i < obj.length; i++) {
              var op = new Option(obj[i].value, obj[i].key);
             //添加
              receiver.options.add(op);
         }
});
	
}


//添加来料
$('#addBtn').on('click', function(e) {
	setOrg();
	$("#materialcomeForm #causesDiv").addClass("hide");
	$("#materialcomeTab input[name='purchaseNo']").removeAttr("disabled","disabled");
	$("#materialcomeTab select[name='receiverId']").removeAttr("disabled","disabled");
	$('#materialcomeForm').find('.btnClass').show();
	$("#materialcomeForm input").removeAttr("disabled","disabled");
	cleanMaterialcomeForm();
	$("#materialcomeAdd tbody").empty();
	$("#materialcomeAdd tfoot").empty();
	$("#materialcomeForm #selectReceiver").prop("value",curUser.id);
	baseInfo({id:"materialcomeDiv",title:"新增入库通知单",height:"700",width:"1100",savefn:function(e){
		var that =$(this);
		if(JY.Validate.form("materialcomeForm")){
			var json_data="";
			var totalCount=0,totalWt = 0;
			var count = $(this).find("input[name=count]").val();
			var wt = $(this).find("input[name=actualWt]").val();
	 	    var last_index=$('#materialcomeAdd tbody tr').length-1;
	 	   var sumBasicCost = $(this).find("#sumBasicCost").text();
			var sumAddCost = $(this).find("#sumAddCost").text();
			var sumOtherCost = $(this).find("#sumOtherCost").text();
	 	    $('#materialcomeAdd tbody tr').each(function(element,index){
	 	    	totalCount = $("#counts").text();
	 	    	totalWt = $("#sumActualWt").text();
	 	    	var data='{"id":"'+$(this).find("input[name=ids]").val()+'","sumBasicCost":"'+sumBasicCost+'","sumAddCost":"'+sumAddCost+'","sumOtherCost":"'+sumOtherCost+'","totalCount":"'+totalCount+'","totalWt":"'+totalWt+'","name":"'+$(this).find("input[name=name]").val()+'","goldType":"'+$(this).find("select[name=goldType]").val()+'","goldTypeName":"'+$(this).find("select[name=goldType] option:selected").text()+'","count":"'+$(this).find("input[name=count]").val()+'","remainCount":"'+$(this).find("input[name=count]").val()+'","requireWt":"'+$(this).find("input[name=requireWt]").val()+'","actualWt":"'+$(this).find("input[name=actualWt]").val()+'","remainWt":"'+$(this).find("input[name=actualWt]").val()+'","goldWt":"0","stoneWt":"0","basicCost":"'+$(this).find("input[name=basicCost]").val()+'","addCost":"'+$(this).find("input[name=addCost]").val()+'","otherCost":"'+$(this).find("input[name=otherCost]").val()+'","costPrice":"'+$(this).find("input[name=costPrice]").val()+'","note":"'+$("#materialcomeForm textarea[name='note']").val()+'","purchaseNo":"'+$("#materialcomeTab").find("input[name=purchaseNo]").val()+'","receiverId":"'+$("#selectReceiver option:selected").val()+'","receiverName":"'+$("#selectReceiver option:selected").text()+'","status":"'+e+'","flag":"'+$("#flag").val()+'"}';	
	 	    	if($(this).index()==last_index){
		        	json_data+=data;
		        }else{
		        	json_data+=data+',';
		        };
		    });
	        json_data='['+json_data+']';
	        JY.Ajax.doRequest(null,jypath +'/scm/materialcome/insert',{data:json_data.toString()},function(data){
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

//添加明细
function addMaterialcome(e){
	JY.Tags.cleanForm("materialaddDiv");
	var flag = $("#flag").val();
	if(flag==0){
		$("#materialaddDiv input[name='count']").removeAttr("disabled","disabled");
	}else if(flag==1){
		$("#materialaddDiv input[name='count']").val(1);
		$("#materialaddDiv input[name='count']").attr("disabled","disabled");
	}
	addInfo({id:"materialaddDiv",title:"添加商品信息",height:"480",width:"400",savefn:function(e){
		var that =$(this);
		if(JY.Validate.form("materialaddForm")){
		var num=1;
		var html = "";
		var requireWt = parseFloat($("#materialaddForm input[name='requireWt']").val()).toFixed(4);
		var actualWt = parseFloat($("#materialaddForm input[name='actualWt']").val()).toFixed(4);
		var stoneWt = parseFloat(!$("#materialaddForm input[name='stoneWt']").val()?0:$("#materialaddForm input[name='stoneWt']").val()).toFixed(4);
		var basicCost = parseFloat(!$("#materialaddForm input[name='basicCost']").val()?0:$("#materialaddForm input[name='basicCost']").val()).toFixed(4);
		var addCost = parseFloat(!$("#materialaddForm input[name='addCost']").val()?0:$("#materialaddForm input[name='addCost']").val()).toFixed(4);
		var otherCost = parseFloat(!$("#materialaddForm input[name='otherCost']").val()?0:$("#materialaddForm input[name='otherCost']").val()).toFixed(4);
		var costPrice = parseFloat($("#materialaddForm input[name='costPrice']").val()).toFixed(4);
		
		var basicCosts = actualWt*basicCost;
		var addCosts = actualWt*addCost;
		var otherCosts = actualWt*otherCost;
		
		 html+="<tr>";
		 html+="<td class='center'><label> <input type='checkbox' name='ids' class='ace' /><span class='lbl'></span></label></td>";
		 html+="<td class='center hidden-480'>"+$('#materialaddForm input[name="name"]').val()+"<input type='hidden' name='name'  value='"+$('#materialaddForm input[name="name"]').val()+"'></td>";
		 html+="<td style='padding:0px;'> <select id='goldType"+num+"'name='goldType' jyValidate='required' style='width:100%;'"+options(goldTypes, $("#materialaddForm #goldType option:selected").val())+"</select></td>";
		 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitNum(this)' jyValidate='required' type='text' onBlur='addNum()' name='count' value='"+$('#materialaddForm input[name="count"]').val()+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
		 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' onBlur='addNum()' name='requireWt' value='"+requireWt+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
		 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' onBlur='addNum()' name='actualWt' value='"+actualWt+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
		 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' type='text' onBlur='addCo($(this).val(),\""+"basicCost"+"\","+cou+","+actualWt+",\""+"BasicCost"+"\")' name='basicCost' value='"+basicCost+"' style='width:100%;height:30px;border:1px solid #ccc;'/><input type='hidden' id='basicCosts"+cou+"' name='basicCosts' value='"+basicCosts+"'/></td>";
		 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' type='text' onBlur='addCo($(this).val(),\""+"addCost"+"\","+cou+","+actualWt+",\""+"AddCost"+"\")' name='addCost' value='"+addCost+"' style='width:100%;height:30px;border:1px solid #ccc;'/><input type='hidden' id='addCosts"+cou+"' name='addCosts' value='"+addCosts+"'/></td>";
		 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' type='text' onBlur='addCo($(this).val(),\""+"otherCost"+"\","+cou+","+actualWt+",\""+"OtherCost"+"\")' name='otherCost' value='"+otherCost+"' style='width:100%;height:30px;border:1px solid #ccc;'/><input type='hidden' id='otherCosts"+cou+"' name='otherCosts' value='"+otherCosts+"'/></td>";
		 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' onBlur='addNum()' name='costPrice' value='"+costPrice+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
		 html+="</tr>";
		 $("#materialcomeAdd tbody").append(html);
		 if(e==0){
			 that.dialog("close");
		 }
//		 chgGoldType($("#goldType").val());
		 num++;
		 countFoot();
	}}})	
}

//删除明细
function delMaterialcome(){
	$("#jyConfirm").children().children().find(".causesDiv").remove();
	var chks =[];    
	$('#materialcomeAdd input[name="ids"]:checked').each(function(){ 
		chks.push($(this).val());    
	});     
	if(chks.length==0) {
		JY.Model.info("您没有选择任何内容!"); 
	}else{
		JY.Model.confirm("确认要删除选中的数据吗?",function(){	
			$('#materialcomeAdd input[name="ids"]:checked').each(function(){  
				$(this).parent().parent().parent().remove();
				countFoot();
			}); 
		});		
	}	
}

function cleanMaterialcomeForm(){
	JY.Tags.cleanForm("materialcomeDiv");
	JY.Tags.cleanForm("materialcomeForm");
	$("#materialnoteDiv span").text("");
	$("#materialnoteDiv textarea").val("");
}

function find(id){
	//加载dialog
	$("#materialcomeForm #causesDiv").addClass("hide");
	$("#selectReceiver").attr("disabled","disabled");
	$("#materialcomeTab input[name='purchaseNo']").attr("disabled","disabled");
	$('#materialcomeForm').find('.btnClass').hide();
	cleanMaterialcomeForm();
	$('#materialcomeDiv').find('textarea[name="note"]').attr('disabled','disabled');
	JY.Ajax.doRequest(null,jypath +'/scm/materialcome/find',{id:id,flag:0},function(data){ 
		if(data.obj.list[0].status=="3"){
			$("#materialcomeForm #causesDiv").removeClass("hide");
		}
		setForm(data); 
		viewInfo({id:"materialcomeDiv",title:"查看入库通知单",height:"700",width:"1100"});
	});
}


function viewInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,
		buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;打印预览","class":"btn btn-primary btn-xs",click:function(){print()}},
		         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
}

function addInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
    	buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;继续添加","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},
    	         {html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
    	         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}

function checkInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
		buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,2,"您确认审核通过吗");}}},
		        {html: "<i class='icon-remove bigger-110'></i>&nbsp;不通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,3,"您确认审核不通过吗");}}},
		        {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
	});
}

function baseInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
    	buttons:[{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存为草稿","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
    	         {html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存并提交","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},
    	         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}

function commForm(data){
	$("#materialcomeForm input[name='id']").val(data.obj.list[0].noticeId);
	$("#materialcomeForm input[name='noticeNo']").val(data.obj.list[0].noticeNo);
	$("#materialcomeForm input[name='purchaseNo']").val(data.obj.list[0].purchaseNo);
	$("#materialcomeForm input[name='orgName']").val(data.obj.list[0].orgName);
	$("#materialcomeForm input[name='surpplyName']").val(data.obj.list[0].surpplyName);
	$("#materialcomeForm #surpplyId").val(data.obj.list[0].surpplyId);
	$("#materialcomeForm #selectReceiver").prop("value",data.obj.list[0].receiverId);
	if(!$("#materialcomeForm #selectReceiver").val()){
		var re = document.getElementById("selectReceiver");
		var op = new Option(data.obj.list[0].receiverName, data.obj.list[0].receiverId);
		re.options.add(op);
		$("#materialcomeForm #selectReceiver").val(data.obj.list[0].receiverId);
	}
	if(JY.Object.notEmpty(data.obj.list[0].note)){
		$("#materialcomeForm textarea[name='note']").val(data.obj.list[0].note);
	}
	if(JY.Object.notEmpty(data.obj.list[0].causes)){
		$("#materialcomeForm textarea[name='causes']").val(data.obj.list[0].causes);
	}
	$("#materialcomeForm span[id='createUser']").text(data.obj.list[0].createUser);
	if(JY.Object.notEmpty(data.obj.list[0].createTime)){
		$("#materialcomeForm span[id='createTime']").text(JY.Object.notEmpty(new Date(data.obj.list[0].createTime).Format("yyyy/MM/dd hh:mm:ss")));
	}
	if(JY.Object.notEmpty(data.obj.list[0].updateUser)){
		$("#materialcomeForm span[id='updateUser']").text(data.obj.list[0].updateUser);
	}
	if(JY.Object.notEmpty(data.obj.list[0].updateTime)){
		$("#materialcomeForm span[id='updateTime']").text(JY.Object.notEmpty(new Date(data.obj.list[0].updateTime).Format("yyyy/MM/dd hh:mm:ss")));
	}
	
	if(JY.Object.notEmpty(data.obj.list[0].checkUser)){
		$("#materialcomeForm span[id='checkUser']").text(data.obj.list[0].checkUser);
	}
	if(JY.Object.notEmpty(data.obj.list[0].checkTime)){
		$("#materialcomeForm span[id='checkTime']").text(JY.Object.notEmpty(new Date(data.obj.list[0].checkTime).Format("yyyy/MM/dd hh:mm:ss")));
	}
}

/*查看明细*/
function setForm(data){
	var flag = $("#flag").val();
	 commForm(data);
	 $("#materialcomeAdd tbody").empty();
	 $("#materialcomeAdd tfoot").empty();
	 var counts=0,sumRequireWt=0,sumActualWt=0,sumStoneWt=0,sumBasicCost=0,sumAddCost=0,sumOtherCost=0,sumCostPrice=0;
	 var results=data.obj.list;
	 if(results!=null&&results.length>0){
		 var html="";
		 for(var i = 0;i<results.length;i++){
			 var l=results[i];
			 html+="<tr>";
			 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /><span class='lbl'></span></label></td>";
			 html+="<td class='center hidden-480'>"+l.name+"</td>";
			 html+="<td class='center hidden-480'>"+l.goldTypeName+"</td>";
			 html+="<td class='center hidden-480'>"+l.count+"</td>";
			 html+="<td class='center hidden-480'>"+l.requireWt.toFixed(4)+"</td>";
			 html+="<td class='center hidden-480'>"+l.actualWt.toFixed(4)+"</td>";
			 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.basicCost.toFixed(4))+"</td>";
			 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.addCost.toFixed(4))+"</td>";
			 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.otherCost.toFixed(4))+"</td>";
			 html+="<td class='center hidden-480'>"+l.costPrice.toFixed(4)+"</td>";
			 html+="</tr>";
			 counts += l.count;
			 sumRequireWt += l.requireWt;
			 sumActualWt += l.actualWt;
			 sumBasicCost += (l.basicCost*l.actualWt);
			 sumAddCost += l.addCost*l.actualWt;
			 sumOtherCost += l.otherCost*l.actualWt;
			 sumCostPrice += l.costPrice;
		 }
		 html+="<tr>";
		 html+="<td class='center'>合计</td>";
		 html+="<td class='center'></td>";
		 html+="<td class='center'></td>";
		 html+="<td class='center'>"+counts+"</td>";
		 html+="<td class='center'>"+sumRequireWt.toFixed(4)+"</td>";
		 html+="<td class='center'>"+sumActualWt.toFixed(4)+"</td>";
		 html+="<td class='center'>"+sumBasicCost.toFixed(4)+"</td>";
		 html+="<td class='center'>"+sumAddCost.toFixed(4)+"</td>";
		 html+="<td class='center'>"+sumOtherCost.toFixed(4)+"</td>";
		 html+="<td class='center'>"+sumCostPrice.toFixed(4)+"</td>";
		 html+="</tr>";
		 $("#materialcomeAdd tfoot").append(html);
		 $("#materialcomeForm input").attr("disabled","disabled");
	 }	 
	 
//	 $('#addBtn').on('click', function(e) {
	 
	 
}


/*修改明细*/
function setUpdateForm(data){
	 var flag = $("#flag").val();
	 commForm(data);
	 var jsonData =""; 
	 $("#materialcomeAdd tbody").empty();
	 $("#materialcomeAdd tfoot").empty();
	 var counts=0,sumRequireWt=0,sumActualWt=0,sumBasicCost=0,sumAddCost=0,sumOtherCost=0,sumCostPrice=0;
	 var results=data.obj.list;
	 if(results!=null&&results.length>0){
		 var html="";
		 cou = results.length;
		 for(var i = 0;i<results.length;i++){
			 var l=results[i];
			 html+="<tr>";
			 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /><span class='lbl'></span></label></td>";
			 html+="<td class='center hidden-480'>"+l.name+"</td>";
			 html+="<td style='padding:0px;'> <select id='goldType"+(i+1)+"'name='goldType' jyValidate='required' style='width:100%;'>"+options(goldTypes,l.goldType)+"</select></td>";
			 if(flag==0){
				 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitNum(this)' jyValidate='required' type='text' onBlur='addNum()' name='count' value='"+l.count+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
			 }
			 if(flag==1){
				 html+="<td class='center hidden-480'>"+l.count+"<input type='hidden' name='count' value='"+l.count+"'/></td>";
			 }
			 html+="<td class='center hidden-480 hide'><input type='hidden' name='noticeId' value='"+l.noticeId+"'/></td>";
			 html+="<td class='center hidden-480 hide'><input type='hidden' name='detailId' value='"+l.id+"'/></td>"
			 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' onBlur='addNum()' name='requireWt' value='"+l.requireWt.toFixed(4)+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
			 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' onBlur='addNum()' name='actualWt' value='"+l.actualWt.toFixed(4)+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
			 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' type='text' onBlur='addCo($(this).val(),\""+"basicCost"+"\","+i+","+l.actualWt+",\""+"BasicCost"+"\")' name='basicCost' value='"+JY.Object.notEmpty(l.basicCost.toFixed(4))+"' style='width:100%;height:30px;border:1px solid #ccc;'/><input type='hidden' id='basicCosts"+i+"' name='basicCosts' value='"+l.basicCost*l.actualWt+"'/></td>";
			 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' type='text' onBlur='addCo($(this).val(),\""+"addCost"+"\","+i+","+l.actualWt+",\""+"AddCost"+"\")' name='addCost' value='"+JY.Object.notEmpty(l.addCost.toFixed(4))+"' style='width:100%;height:30px;border:1px solid #ccc;'/><input type='hidden' id='addCosts"+i+"' name='addCosts' value='"+l.addCost*l.actualWt+"'/></td>";
			 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' type='text' onBlur='addCo($(this).val(),\""+"otherCost"+"\","+i+","+l.actualWt+",\""+"OtherCost"+"\")' name='otherCost'  value='"+JY.Object.notEmpty(l.otherCost.toFixed(4))+"' style='width:100%;height:30px;border:1px solid #ccc;'/><input type='hidden' id='otherCosts"+i+"' name='otherCosts' value='"+l.otherCost*l.actualWt+"'/></td>";
			 html+="<td style='padding:0px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' onBlur='addNum()' name='costPrice' value='"+l.costPrice.toFixed(4)+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
			 html+="</tr>";
		 }
		 $("#materialcomeAdd tbody").append(html);
		 countFoot();
		 $("#materialcomeForm input").removeAttr("disabled","disabled");
	 }	 
	 return jsonData;
	 
}


function addCo(cost,name,i,wt,names){
	var cou=0;
	if(name=='basicCost'){
		$("#basicCosts"+i).val(cost*wt);
	}
	if(name=='addCost'){
		$("#addCosts"+i).val(cost*wt);
	}
	if(name=='otherCost'){
		$("#otherCosts"+i).val(cost*wt);
	}
	$('#materialcomeAdd').find('input[name="'+name+'s"]').each(function(element,index){
		 if($(this).val()!=''){cou+=parseFloat($(this).val());}
	 });
	$("#materialcomeAdd span[id='sum"+names+"']").text(cou.toFixed(4));
}

//合计
function countFoot(){
	var flag = $("#flag").val();
	var foot = "";
	$('#materialcomeAdd tfoot').html("");
	var counts=0,sumRequireWt=0,sumActualWt=0,sumGoldWt=0,sumStoneWt=0,sumBasicCost=0,sumAddCost=0,sumOtherCost=0,sumCostPrice=0;
	$('#materialcomeAdd').find('input[name="count"]').each(function(element,index){
		 if($(this).val()!=''){counts+=parseFloat($(this).val());}
	 });
	 $('#materialcomeAdd ').find('input[name="requireWt"]').each(function(element,index){
		 if($(this).val()!=''){ sumRequireWt+=parseFloat($(this).val());}
	 });
	 $('#materialcomeAdd').find('input[name="actualWt"]').each(function(element,index){
		 if($(this).val()!=''){sumActualWt+=parseFloat($(this).val());}
	 });
//	 $('#materialcomeAdd').find('input[name="basicCost"]').each(function(element,index){
//		 if($(this).val()!=''){ sumBasicCost+=parseFloat($(this).val());}
//	 });
//	 $('#materialcomeAdd').find('input[name="addCost"]').each(function(element,index){
//		 if($(this).val()!=''){sumAddCost+=parseFloat($(this).val());}
//	 });
//	 $('#materialcomeAdd').find('input[name="otherCost"]').each(function(element,index){
//		 if($(this).val()!=''){sumOtherCost+=parseFloat($(this).val());}
//	 });
	 
	 $('#materialcomeAdd').find('input[name="costPrice"]').each(function(element,index){
		 if($(this).val()!=''){sumCostPrice+=parseFloat($(this).val());}
	 });
	 
	 $('#materialcomeAdd').find('input[name="basicCosts"]').each(function(element,index){
		 if($(this).val()!=''){sumBasicCost+=parseFloat($(this).val());}
	 });
	 
	 $('#materialcomeAdd').find('input[name="addCosts"]').each(function(element,index){
		 if($(this).val()!=''){sumAddCost+=parseFloat($(this).val());}
	 });
	 
	 $('#materialcomeAdd').find('input[name="otherCosts"]').each(function(element,index){
		 if($(this).val()!=''){sumOtherCost+=parseFloat($(this).val());}
	 });
	 
	 foot+="<tr>";
	 foot+="<td class='center'>合计</td>";
	 foot+="<td class='center'></td>";
	 foot+="<td class='center'></td>";
	 foot+="<td class='center'><span id='counts'>"+counts+"</span></td>";
	 foot+="<td class='center'><span id='sumRequireWt'>"+sumRequireWt.toFixed(4)+"</span></td>";
	 foot+="<td class='center'><span id='sumActualWt'>"+sumActualWt.toFixed(4)+"</span></td>";
	 foot+="<td class='center'><span id='sumBasicCost'>"+sumBasicCost.toFixed(4)+"</span></td>";
	 foot+="<td class='center'><span id='sumAddCost'>"+sumAddCost.toFixed(4)+"</span></td>";
	 foot+="<td class='center'><span id='sumOtherCost'>"+sumOtherCost.toFixed(4)+"</span></td>";
	 foot+="<td class='center'><span id='sumCostPrice'>"+sumCostPrice.toFixed(4)+"</span></td>";
	 foot+="</tr>";
	 $("#materialcomeAdd tfoot").append(foot);
}


//function checkSum(name){
//	var goldWt = $("#materialaddForm input[name='goldWt']").val()?$("#materialaddForm input[name='goldWt']").val():0;
//	var stoneWt = $("#materialaddForm input[name='stoneWt']").val()?$("#materialaddForm input[name='stoneWt']").val():0;
//	var actualWt = $("#materialaddForm input[name='actualWt']").val()?$("#materialaddForm input[name='actualWt']").val():0;
//	var name = $("#materialaddForm input[name='"+name+"']");
//	var sum = parseFloat(goldWt)+parseFloat(stoneWt);
//	if(sum>parseFloat(actualWt)){
//		name.tips({
//            msg: "金重加石重不能超过实重!",
//            bg: '#FF2D2D',
//            time: 1
//        });
//		$("#materialaddForm input[name='actualWt']").val(sum);
//	}
//}
//
//function changeSum(that){
//	var actualWt = that.parent().parent().find("input").eq(5).val()?that.parent().parent().find("input").eq(5).val():0;
//	var goldWt = that.parent().parent().find("input").eq(6).val()?that.parent().parent().find("input").eq(6).val():0;
//	var stoneWt = that.parent().parent().find("input").eq(7).val()?that.parent().parent().find("input").eq(7).val():0;
//	var sum = parseFloat(goldWt)+parseFloat(stoneWt);
//	if(sum>parseFloat(actualWt)){
//		that.tips({
//            msg: "金重加石重不能超过实重!",
//            bg: '#FF2D2D',
//            time: 1
//        });
//		that.parent().parent().find("input").eq(5).val(sum);
//	}
//	
//}

$('#printBtn').on('click', function(e) {
	var chks =[];    
	$('#materialcomeTable input[name="ids"]:checked').each(function(){    
		chks.push($(this).val());    
	}); 
	if(chks.length==0){
		JY.Model.info("您没有选择任何内容!"); 
	}else if(chks.length>1){
		JY.Model.info("请选择一条内容!"); 
	}else{
		var id =$('#materialcomeTable input[name="ids"]:checked').val();
		if(id){
			$("#credentialByInfoDiv").load(jypath +'/scm/materialcome/printSu?id='+id,function(){
				 var noticeNo = $("#credentialByInfoDiv #testPrintdiv #noticeNoSpan").text();
				 if(LODOP){
					 LODOP=getLodop();  
					 LODOP.PRINT_INIT("打印控件功能演示");
					 LODOP.ADD_PRINT_BARCODE(16,98,176,21,"128B",noticeNo);
					 LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
					 LODOP.ADD_PRINT_HTM(0,0,"100%","100%",document.getElementById("testPrintdiv").innerHTML);
					 LODOP.PREVIEW();
				 }
			 })
		}
	}
	
})

function print(){
	 var id = $("#materialcomeForm input[name='id']").val();
	 var noticeNo = $("#materialcomeForm input[name='noticeNo']").val();
	 $("#credentialByInfoDiv").load(jypath +'/scm/materialcome/printSu?id='+id,function(){
		 LODOP=getLodop();  
		 if(LODOP){
			 LODOP.PRINT_INIT("打印控件功能演示");
			 LODOP.ADD_PRINT_BARCODE(16,98,176,21,"128B",noticeNo);
			 LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
			 LODOP.ADD_PRINT_HTM(0,0,"100%","100%",document.getElementById("testPrintdiv").innerHTML);
			 LODOP.PREVIEW();
		 }
	 })

}



