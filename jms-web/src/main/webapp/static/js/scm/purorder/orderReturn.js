$(function () {
	JY.Dict.setSelect("selectStoreStatus","SCM_ORDER_STATUS",2,"全部");
	getbaseList();
	
	$('#addBtn').on('click', function(e) {
		$("#addDiv").removeClass('hide');
		cleanForm();
		addInfo("returnDiv","新增",function(type){
			e.preventDefault();
			if(JY.Validate.form("returnForm")){
				var that =$(this);
				var returnCause=$("#returnForm input[name$='returnCause']").val();
				var rejectInfo=$("#returnForm input[name$='rejectInfo']").val();
				setItemArry("#itemsTable");
				if(!itemArry.length>0){
					JY.Model.info("您没有输入需要退货的商品!"); 
					return;
				}
				var params ={returnCause:returnCause,rejectInfo:rejectInfo,status:type,orderType:1,list:itemArry};
				$.ajax({type:'POST',url:jypath+'/scm/purreturn/add',data:JSON.stringify(params),dataType:'json',contentType:"application/json",success:function(data,textStatus){  			        	 
		        	if(data.res==1){
		        		that.dialog("close");      
		        		JY.Model.info(data.resMsg,function(){search();});
		        	}else{
		        		 Y.Model.error(data.resMsg);
		        	}     	
		        }});
			}	
		});
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
			JY.Ajax.doRequest(null,jypath +'/scm/purreturn/find',{id:chks[0]},function(data){
				$("#returnForm input[name='id']").val(data.obj.id);
				$("#returnForm input[name='returnNo']").val(data.obj.returnNo);
				print();
			});
		}
	});
	$('#findBtn').on('click', function(e) {
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
	$('#editBtn').on('click', function(e) {
		e.preventDefault();
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
	$('#delBtn').on('click', function(e) {
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
			del(chks[0]);
		}
	});
	$("#importFormCode").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 findCode();
		 } 
	});
});
function findCode(){
	var code=$("#importFormCode").val();
	var list=[];
	if(JY.Object.notEmpty(code)==""){
		return
	}
	JY.Ajax.doRequest(null,jypath +'/scm/product/find',{code:code,orgId:curUser.orgId,status:"B"},function(data){
		var l=data.obj;
		$("#itemsTable input[name$='code']").each(function(){  
			list.push($(this).val());
		});
		if(list.indexOf(l.code)!=-1){
			return;
		}
		var html="<tr id='temtr"+itemNum+"' >";
		html+="<td style='padding:0px;'>"+"<input type='text' readonly  name='code' id='code"+itemNum+"' jyValidate='required' style='width:100%;height:30px;'value='"+JY.Object.notEmpty(l.code)+"' /> </td>";
		html+="<td style='padding:0px;'>"+"<input type='text' readonly  id='name"+itemNum+"' jyValidate='required' style='width:100%;height:30px;'value='"+JY.Object.notEmpty(l.name)+"' />"+"</td>";
		html+="<td style='padding:0px;'>"+"<input type='text' readonly  id='count"+itemNum+"' jyValidate='required' style='width:100%;height:30px;'value='"+JY.Object.notEmpty(l.count)+"' />"+"</td>";
		html+="<td style='padding:0px;'>"+"<input type='text' readonly  id='count2"+itemNum+"' style='width:100%;height:30px;'value='"+JY.Object.notEmpty()+"' />"+"</td>";
		html+="<td style='padding:0px;'>"+"<input type='text' readonly  id='weight"+itemNum+"' jyValidate='required' style='width:100%;height:30px;'value='"+JY.Object.notEmpty(l.totalWeight)+"' />"+"</td>";
		html+="<td style='padding:0px;'>"+"<input type='text' readonly  id='price"+itemNum+"' jyValidate='required' style='width:100%;height:30px;'value='"+JY.Object.notEmpty(l.price)+"' />"+"</td>";
		html+="<td style='padding:0px;'>"+"<input type='text' readonly  id='dialoutOrgName"+itemNum+"' jyValidate='required' style='width:100%;height:30px;'value='"+JY.Object.notEmpty(l.orgName)+"' />"+"</td>";
		html+="<td style='padding:0px;'>"+"<input type='text' readonly  id='dialoutWarehouseName"+itemNum+"' jyValidate='required' style='width:100%;height:30px;'value='"+JY.Object.notEmpty(l.warehouseName)+"' />"+"</td>";
		html+="<td style='padding:0px;'>"+"<input type='text' id='remarks"+itemNum+"' jyValidate='required' style='width:100%;height:30px;' value=''/>";
		html+="<td style='padding:3px;'>"+"<button class='dropbtn' id='dropbtn' onclick='delDetail(&apos;"+itemNum+"&apos;)'>删除</button>"+"</td>";
		html+="<input type='hidden' id='dialoutOrgId"+itemNum+"' value='"+l.orgId+"'/>";
		html+="<input type='hidden' id='dialoutWarehouseId"+itemNum+"' value='"+l.warehouseId+"' />";
		html+="<input type='hidden' name='items' value='"+itemNum+"' />";
		html+="</tr>";
		itemNum++;
		$("#itemsTable").append(html);
		$("#importFormCode").val("");
	});
}
function find(id){
	//加载dialog
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/scm/purreturn/find',{id:id},function(data){ 
		setForm(data); 
		viewInfo("returnDiv");
	});
}
function edit(id){
	cleanForm();
	$("#addDiv").removeClass('hide');
	JY.Ajax.doRequest(null,jypath +'/scm/purreturn/find',{id:id},function(data){
		var l = data.obj;
		if(l.status!=0&&l.status!=4){
			JY.Model.info("只有草稿状态订单才能修改");
			return;
		}
	    setForm(data,1);   
	    addInfo("returnDiv","修改",function(type){
	    	if(JY.Validate.form("returnForm")){
	    		var that =$(this);
				var id=$("#returnForm input[name$='id']").val();
				var returnNo=$("#returnForm input[name$='returnNo']").val();
				var returnCause=$("#returnForm input[name$='returnCause']").val();
				var rejectInfo=$("#returnForm input[name$='rejectInfo']").val();
				setItemArry("#itemsTable");
				if(!itemArry.length>0){
					 JY.Model.info("您没有输入需要退货的商品!"); 
					 return;
				}
				var params ={id:id,returnNo:returnNo,returnCause:returnCause,rejectInfo:rejectInfo,status:type,list:itemArry};								 	 
				$.ajax({type:'POST',url:jypath+'/scm/purreturn/update',data:JSON.stringify(params),dataType:'json',contentType:"application/json",success:function(data,textStatus){  			        	 
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
function check(id){
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/scm/purreturn/find',{id:id},function(data){ 
		var l = data.obj;
		if(l.status!=1){
			JY.Model.info("只有待审核状态订单才能审核");
			return;
		}
		setForm(data);  
		checkInfo("returnDiv","订单审核",function(type){
			var that =$(this);
			if(type=="4"){
				JY.Model.confirm(type=="2"?"确认审核通过吗？":"确认审核不通过吗？",function(){	
					var rejectInfo=$("#returnForm input[name$='rejectInfo']").val();
					if(JY.Object.notEmpty(rejectInfo)==""){
						JY.Model.info("请填写驳回原因!"); 
						return 
					}
					JY.Ajax.doRequest(null,jypath +'/scm/purreturn/check',{id:id,status:type,rejectInfo:rejectInfo},function(data){
						JY.Model.info(data.resMsg,function(){search(); that.dialog("close"); });
					});
				});
			}else{
				if(JY.Validate.form("returnForm")){
					JY.Model.confirm(type=="2"?"确认审核通过吗？":"确认审核不通过吗？",function(){	
						JY.Ajax.doRequest(null,jypath +'/scm/purreturn/check',{id:id,status:type},function(data){
							JY.Model.info(data.resMsg,function(){search(); that.dialog("close"); });
						});
					});
				}
			}
		});
	});
}

function search(){
	$("#searchBtn").trigger("click");
}
function del(id){
	JY.Model.confirm("确认删除吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/purreturn/find',{id:id},function(data){ 
			var l = data.obj;
			if(l.status!=0 && l.status!=4){
				JY.Model.info("该状态不能删除");
				return;
			}
			JY.Ajax.doRequest(null,jypath +'/scm/purreturn/del',{id:id},function(data){
				JY.Model.info(data.resMsg,function(){search();});
			});
		});
	});
}
function setForm(data,insert){
	var l=data.obj;
	var isForm="";
	isForm="#returnForm"
		$(isForm+" input[name$='orgId']").prop("value",JY.Object.notEmpty(l.orgId));
		$(isForm+" input[name$='orgName']").prop("value",JY.Object.notEmpty(l.orgName));
		$(isForm+" input[name$='id']").val(JY.Object.notEmpty(l.id));
		$(isForm+" input[name$='returnNo']").val(JY.Object.notEmpty(l.returnNo));
		$(isForm+" input[name$='dialinOrgId']").val(JY.Object.notEmpty(l.dialinOrgId));
		$(isForm+" input[name$='dialinWarehouseId']").val(JY.Object.notEmpty(l.dialinWarehouseId));
		$(isForm+" input[name$='dialoutOrgId']").val(JY.Object.notEmpty(l.dialoutOrgId));
		$(isForm+" input[name$='dialoutWarehouseId']").val(JY.Object.notEmpty(l.dialoutWarehouseId));
		$(isForm+" input[name$='dialinOrgName']").val(JY.Object.notEmpty(l.dialinOrgName));
		$(isForm+" input[name$='dialoutOrgName']").val(JY.Object.notEmpty(l.dialoutOrgName));
		$(isForm+" input[name$='dialinWarehouseNaem']").val(JY.Object.notEmpty(l.dialinWarehouseNaem));
		$(isForm+" input[name$='dialoutWarehouseName']").val(JY.Object.notEmpty(l.dialoutWarehouseName));
		$(isForm+" input[name$='returnCause']").val(JY.Object.notEmpty(l.returnCause));
		$(isForm+" input[name$='rejectInfo']").val(JY.Object.notEmpty(l.rejectInfo));
//		$(isForm+" textarea[name$='description']").val(JY.Object.notEmpty(l.description));
		$(isForm+" span[name='createName']").text(JY.Object.notEmpty(l.createName));
		$(isForm+" span[name='checkName']").text(JY.Object.notEmpty(l.checkName));
		$(isForm+" span[name='updateName']").text(JY.Object.notEmpty(l.updateName));
		$(isForm+" span[name='createTime']").text(JY.Object.notEmpty(l.createTime)==""?"":new Date(l.createTime).Format("yyyy/MM/dd hh:mm:ss"));
		$(isForm+" span[name='updateTime']").text(JY.Object.notEmpty(l.updateTime)==""?"":new Date(l.updateTime).Format("yyyy/MM/dd hh:mm:ss"));
		$(isForm+" span[name='checkTime']").text(JY.Object.notEmpty(l.checkTime)==""?"":new Date(l.checkTime).Format("yyyy/MM/dd hh:mm:ss"));
		
	var items=l.list;
	setItems(items,insert);
}
function setItems(items,insert){
	var html="";
	for(var i=0;i<items.length;i++){
		var l=items[i];
		debugger
		html+="<tr id='temtr"+itemNum+"' >";
		html+="<td style='padding:0px;'>"+"<input type='text' readonly  name='code' id='code"+itemNum+"' jyValidate='required' style='width:100%;height:30px;'value='"+JY.Object.notEmpty(l.code)+"' />"+"</td>";
		html+="<td style='padding:0px;'>"+"<input type='text' readonly  id='name"+itemNum+"' jyValidate='required' style='width:100%;height:30px;'value='"+JY.Object.notEmpty(l.name)+"' />"+"</td>";
		html+="<td style='padding:0px;'>"+"<input type='text' readonly  id='count"+itemNum+"' jyValidate='required' style='width:100%;height:30px;'value='"+JY.Object.notEmpty(l.count)+"' />"+"</td>";
		html+="<td style='padding:0px;'>"+"<input type='text' readonly  id='count2"+itemNum+"' jyValidate='required' style='width:100%;height:30px;'value='"+JY.Object.notEmpty(l.count2)+"' />"+"</td>";
		html+="<td style='padding:0px;'>"+"<input type='text' readonly  id='weight"+itemNum+"' jyValidate='required' style='width:100%;height:30px;'value='"+JY.Object.notEmpty(l.weight)+"' />"+"</td>";
		html+="<td style='padding:0px;'>"+"<input type='text' readonly  id='price"+itemNum+"' jyValidate='required' style='width:100%;height:30px;'value='"+JY.Object.notEmpty(l.price)+"' />"+"</td>";
		html+="<td style='padding:0px;'>"+"<input type='text' readonly  id='dialoutOrgName"+itemNum+"' jyValidate='required' style='width:100%;height:30px;'value='"+JY.Object.notEmpty(l.dialoutOrgName)+"' />"+"</td>";
		html+="<td style='padding:0px;'>"+"<input type='text' readonly  id='dialoutWarehouseName"+itemNum+"' jyValidate='required' style='width:100%;height:30px;'value='"+JY.Object.notEmpty(l.dialoutWarehouseName)+"' />"+"</td>";
		html+="<td style='padding:0px;'>"+"<input type='text' readonly  id='remarks"+itemNum+"' jyValidate='required' style='width:100%;height:30px;'value='"+JY.Object.notEmpty(l.remarks)+"'/>"+"</td>";
		html+="<td style='padding:3px;'>";
		if(insert=="1"){
			html+=" <button class='dropbtn' id='dropbtn' onclick='delDetail(&apos;"+itemNum+"&apos;)'>删除</button> ";
		}
		html+="<input type='hidden' name='items' value='"+itemNum+"' />";
		html+="<input type='hidden' id='dialoutOrgId"+itemNum+"' value='"+l.dialoutOrgId+"'/>";
		html+="<input type='hidden' id='dialoutWarehouseId"+itemNum+"' value='"+l.dialoutWarehouseId+"' />";
		html+="</td>";
		html+="</tr>";
		itemNum++;
	}
	$("#itemsTable").append(html);
}
function delDetail(num){
	e.preventDefault();
	$("#temtr"+num).remove();
}
function getbaseList(init){
	if(init==1)$("#baseForm .pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("baseForm",jypath +'/scm/purreturn/findByPage',null,function(data){
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
            		 var statusStr
            		 if(l.status==1)statusStr="待审核";else if(l.status==2)statusStr="已审核";else if(l.status==3)statusStr="已完成"; else if(l.status==4)statusStr="已拒绝";else if(l.status==9)statusStr="已删除"; else statusStr="草稿";
            		 html+="<tr>";
            		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
            		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
            		 html+="<td class='center'><a style='cursor:pointer;' onclick='find(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.returnNo)+"</a></td>";
            		 html+="<td class='left'>"+JY.Object.notEmpty(l.dialoutWarehouseName)+"</td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.dialoutOrgName)+"</td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.dialinOrgName)+"</td>"
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.dialinWarehouseNaem)+"</td>"
            		 html+="<td class='center hidden-480'>"+statusStr+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.checkName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.checkTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.createName)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.createTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
            		 html+="</tr>";		 
            	 } 
        		 $("#baseTable tbody").append(html);
        		 JY.Page.setPage("baseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='12' class='center'>没有相关数据</td></tr>";
        		$("#baseTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
 	 
    		 JY.Model.loadingClose();
	 });
}
var itemArry =new Array();
var itemNum=1;
function setItemArry(forms){
	itemArry =new Array();
	debugger
	var nums = $(forms+" input[name='items']").map(function(){return $(this).val();}).get().join(",");
	if(JY.Object.notNull(nums)){
		var numList=nums.split(",");
		for(var i=0;i<numList.length;i++){
			var num=numList[i];
			var id=$("#id"+num).val();
			var code=$("#code"+num).val();
			var name=$("#name"+num).val();
			var count=$("#count"+num).val();
			var count2=$("#count2"+num).val();
			var weight=$("#weight"+num).val();
			var price=$("#price"+num).val();
			var dialoutOrgId=$("#dialoutOrgId"+num).val();
			var dialoutWarehouseId=$("#dialoutWarehouseId"+num).val();
			var remarks=$("#remarks"+num).val();
			var item={id:id,code:code,name:name,count:count,count2:count2,weight:weight,price:price,dialoutOrgId:dialoutOrgId,dialoutWarehouseId:dialoutWarehouseId,remarks:remarks};
			itemArry.push(item);
		}
	}	
}
function cleanForm(){
	itemArry =new Array();
	itemNum=1;
	JY.Tags.cleanForm("returnForm");
	$("#itemsTable tbody").empty();
	$("#returnDiv span").text("");
}
function viewInfo(id,title,fn){	
	$("#"+id).removeClass('hide').dialog({resizable:false,modal:true,height:768,width:1024,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"查看")+"</h4></div>",title_html:true,
		buttons:[
		         {html: "<i class='icon-ok bigger-110'></i>&nbsp;打印预览","class":"btn btn-primary btn-xs",click:function(){print()}},
		         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(fn) == 'function'){fn.call(this);}}}
		]});
	$("#addDiv").addClass('hide') ;
	JY.Tools.formDisabled("returnForm",true);
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
	 JY.Tools.formDisabled("returnForm",false);
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
function print(){
	 var id = $("#returnForm input[name='id']").val();
	 var returnNo=$("#returnForm input[name='returnNo']").val();
	 $("#printDiv").load(jypath +'/scm/purreturn/print?id='+id,function(){
		 LODOP=getLodop();  
		 LODOP.PRINT_INIT("打印控件功能演示");
		 LODOP.ADD_PRINT_BARCODE(16,78,166,21,"128B",returnNo);
		 LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
		 LODOP.ADD_PRINT_HTM(0,0,"100%","100%",document.getElementById("testPrintdiv").innerHTML);
		 LODOP.PREVIEW();
	 })
}