$(function(){
	getbaseList();
	JY.Dict.setSelect("typeForm","SCM_DATA_MATERIALTYPE",1);
	JY.Dict.setSelect("selectisValid","SCM_ORDER_STATUS",2,"全部");
	warehouseForm();
	
	
/*	2：不相同的信息
	$('#addBtn').on('click', function(e) {
		e.preventDefault();
		cleanForm();
		addInfo({id:"moveDiv",title:"移库信息",height:"520",width:"400",savefn:function(e){
			var that =$(this);
			var html="";		    		
			if(JY.Validate.form("transferForm")){
				var code=$("#transferForm input[name$='code']").val();
				JY.Ajax.doRequest(null,jypath +'/scm/proTransfer/findByCode',{code:code},function(data){
					setForm(data);
					html+="<tr>";
					 var remarks= $("#proTransferForm textarea[name$='remarks']").val();
	        		 if(remarks ==""){remarks="无";}
	        		 alert($(".customTable input[name$='aaa']").val())
	        		 html+="<td class='center'><label> <input  type='checkbox' name='ids' value='"+data.obj.code+"' class='ace' /><span class='lbl'></span></label></td>";
	    			 html+="<td style='padding:0px;'><input jyValidate='required' type='text' name='code' readonly value='"+data.obj.code+"' style='width:100%;height:30px;border:none;'/><input type='hidden' name='remarks' value='"+remarks+"'/><input type='hidden' name='id' value='0'/></td>";
	    			 html+="<td style='padding:0px;'><input jyValidate='required' type='text' name='num' onkeyup='JY.Validate.limitNum(this)' value='"+$("#transferForm input[name$='num']").val()+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
	        		 html+="<td style='padding:0px;'><input jyValidate='required' type='text' name='weight' onkeyup='JY.Validate.limitDouble(this)' value='"+$("#transferForm input[name$='weight']").val()+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
	        		 html+="<td style='padding:0px;'><input jyValidate='required' type='text' name='diffWeight' onkeyup='JY.Validate.limitDouble(this)' value='"+$("#transferForm input[name$='diffWeight']").val()+"' style='width:100%;height:30px;border:1px solid #ccc;'/></td>";
	        		 html+="<td style='padding:0px;'><input readonly type='text' name='outWarehouseIdName' value='"+data.obj.outWarehouseIdName+"' style='width:100%;height:30px;border:1px solid #ccc;'/><input type='hidden' name='outWarehouseId' value='"+data.obj.outWarehouseId+"'/></td>";
	    			 html+="<td style='padding:0px;'><input readonly type='text' name='outLocationIdName'  value='"+data.obj.outLocationIdName+"' style='width:100%;height:30px;border:1px solid #ccc;'/><input type='hidden' name='outLocationId' value='"+data.obj.outLocationId+"'/></td>";
	    			 html+="<td style='padding:0px;'><select id='ow_"+data.obj.code+"' jyValidate='required' type='text'  name='inWarehouseId' value='' style='width:100%;height:30px;border:1px solid #ccc;' "+"onchange=chgWarehouseLocation('ow_"+data.obj.code+"','ol_"+data.obj.code+"')></select></td>";
	    			 html+="<td style='padding:0px;'><select id='ol_"+data.obj.code+"' jyValidate='required' type='text'  name='inLocationId' value='' style='width:100%;height:30px;border:1px solid #ccc;'></select></td>";
	    			 html+="</tr>";	
		    		 $("#transferAdd tbody").append(html);
		    		 warehouseForm('ow_'+data.obj.code);
		    		 chgWarehouseLocation('selectWarehouse','ol_'+data.obj.code,$("#transferForm select[name$='inLocationId']").val());
		    		 $("#ow_"+data.obj.code).val($("#transferForm select[name$='inWarehouseId']").val());
		    		 if(e==0){
		    			 that.dialog("close");
		    		 }
				});
			}
		}})	
	});*/
	//---------------------	
	//新增移库
	$('#move').on('click', function(e) {
		 //通知浏览器不要执行与事件关联的默认动作		
		 e.preventDefault();
		 clean();
		 baseInfo({id:"transferDiv",title:"新增移库",height:"720",width:"1024",savefn:function(type){
			if(JY.Validate.form("transferDetailForm") && $('#transferAdd tbody tr').length>0){
				var that =$(this);
				var json_data="";
		 	    var last_index=$('#transferAdd tbody tr').length-1;
		 	    $('#transferAdd tbody tr').each(function(element,index){
			 	    var data='{"code":"'+$(this).find("input[name=code]").val()+'","description":"'+$(this).find("input[name=description]").val()+'"}';	
			 	    if($(this).index()==last_index){
			        	json_data+=data;
			        }else{
			        	json_data+=data+',';
			        }
			    });
			    json_data='['+json_data+']';
			    $.ajax({type:"POST",url:jypath+"/scm/proTransfer/move",
			    	data: "t="+json_data+"&status="+type+"&remarks="+$('#transferDetailForm').find("textarea[name=remarksTwo]").val()+"&inWarehouseId="+$('#selectWarehouse').val()+"&inLocationId="+$('#selectWarehouseLocation').val(),dataType:"text",success:function(data,textStatus){ 
					   var json_obj=eval('('+data+')');
					   if(json_obj.res==1){
			        		that.dialog("close");      
			        		JY.Model.info(json_obj.resMsg,function(){search();});
			        	}else{
			        		 JY.Model.error(json_obj.resMsg);
			        	}     	
			         }
			    });
			}	
		}});
	});
	
	
	
/*	var moudtlMap = {};
	$("#queryCode").typeahead({
        source: function (query, process) {
        	$.ajax({
				type:'POST',
				url:jypath+'/scm/purstorage/out/findSetCode',
				data:{code:query},
				dataType:'json',
				success:function(result,textStatus){
					moudtlMap = {};
					var list= [];
					 $.each(result.obj, function (index, ele) {
						 moudtlMap[ele.code] = ele.id;
						 list.push(ele.code);
                     });
					process(list);
				}
			});
        	
        },
        items: 20,
        afterSelect: function (value) {
        	$.ajax({
        		type:'POST',
        		url:jypath +'/scm/proTransfer/findByCode',
        		data:{code:value},
        		dataType:'json',
        		success:function(data,textStatus){
        			JY.Tools.populateForm("transferForm",data.obj);
        			setForm(data);
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
    });*/
	
	
	
	//查看
	$('#view').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#transferTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{
			check(chks[0])
		}	
	});
	
	//修改
	$('#edit').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#transferTable input[name="ids"]:checked').each(function(){    
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
	
	//审核
	$('#auditing').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#transferTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{
			auditing(chks[0])
		}	
	});
	
	//删除
	$('#del').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#transferTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			del(chks);
		}	
	});

});




/*仓位*/
function chgWarehouseLocation(obj,val){
	var va = obj;
	var selectWarehouse = $('#selectWarehouseLocation');
	JY.Ajax.doRequest(null,jypath +'/scm/warehousing/selectWarehousingLocation',{id:va},function(data){
	    var obj=data.obj;
	    selectWarehouse.html("");
	    selectWarehouse.append('<option value="">请选择</option>');
		for (var i = 0; i < obj.length; i++) {
			selectWarehouse.append('<option value="'+obj[i].key+'">'+obj[i].value+'</option>');
        }
		if(JY.Object.notEmpty(val).length>0){
			 $('#selectWarehouseLocation').val(JY.Object.notEmpty(val));
		}
	});
}

/*表单添默认值 */
function warehouseForm(){
	$('#selectWarehouse').html("");
	$('#selectWarehouse').append('<option value="">请选择</option>');
	JY.Ajax.doRequest(null,jypath +'/scm/warehousing/selectWarehousing',null,function(data){
	    var obj=data.obj.list;
		for (var i = 0; i < obj.length; i++) {
			$('#selectWarehouse').append('<option value="'+obj[i].key+'">'+obj[i].value+'</option>');
        }
	});
}

function subcode(){
	var code=$("input[id='enteryno']").val().trim();
	if(code.length != 0 && !code.match(/^\s+$/g)){
		JY.Ajax.doRequest(null,jypath +'/scm/proTransfer/findByCode',{code:code},function(data){
			if(JY.Object.notEmpty(data.resMsg).length>0){
				$('#enteryno').tips({
			         msg: data.resMsg,
			         bg: '#FF2D2D',
			         time: 1
			     });
			}else{
				commCode(data.obj);
			}
		});
		$("input[id='enteryno']").val("");
	}
}


function commCode(obj){
	 var list=[];
	 $("#transferAdd input[name$='code']").each(function(){  
			list.push($(this).val());
	});
	if(list.indexOf(obj.code)==-1){
		 var html="";
		 html+="<tr>";
		 var remarks= $("#transferForm textarea[name$='remarks']").val();
		 if(remarks ==""){remarks="无";}
		 html+="<td class='center'><label> <input  type='checkbox' name='ids' value='"+JY.Object.notEmpty(obj.code)+"' class='ace' /><span class='lbl'></span></label></td>";
		 html+="<td style='padding:1px;'><input class='center' jyValidate='required' type='text' name='code' readonly value='"+JY.Object.notEmpty(obj.code)+"' style='width:100%;height:30px;border:none;'/><input type='hidden' name='id' value='"+JY.Object.notEmpty(obj.id)+"'/></td>";
		 html+="<td style='padding:1px;'><input class='center' type='text' name='name' readonly value='"+JY.Object.notEmpty(obj.name)+"' style='width:100%;height:30px;border:none;'/></td>";html+="<td style='padding:1px;'><input class='center' jyValidate='required' type='text' name='num' readonly value='"+JY.Object.notNumber(obj.num)+"' style='width:100%;height:30px;border:none;'/></td>";
		 html+="<td style='padding:1px;'><input class='center' jyValidate='required' type='text' name='weight' readonly value='"+parseFloat(JY.Object.notNumber(obj.weight)).toFixed(4)+"' style='width:100%;height:30px;border:none;'/></td>";
		 html+="<td style='padding:1px;'><input class='center' readonly type='text' name='outWarehouseIdName' value='"+JY.Object.notEmpty(obj.outWarehouseIdName)+"' style='width:100%;height:30px;border:none;'/></td>";
		 html+="<td style='padding:1px;'><input class='center' readonly type='text' name='outLocationIdName'  value='"+JY.Object.notEmpty(obj.outLocationIdName)+"' style='width:100%;height:30px;border:none;'/></td>";
		 html+="<td style='padding:1px;'><input class='center' type='text' name='description' value='"+JY.Object.notEmpty(obj.description)+"' style='width:100%;height:30px;border:none;'/></td>";
		 html+="</tr>";	
		 $("#transferAdd tbody").append(html);
		 commFoot();
	}else{
		$('#enteryno').tips({
	         msg: "条码已存在!",
	         bg: '#FF2D2D',
	         time: 1
	    });
	}
}

/*底部合计*/
function commFoot(){
	 $("#transferAdd tfoot").html("");
     var num=0,weight=0;
	 $('#transferAdd').find('input[name="num"]').each(function(element,index){
		 if($(this).val()!=''){num+=parseFloat($(this).val());}
	 });
	 $('#transferAdd').find('input[name="weight"]').each(function(element,index){
		 if($(this).val()!=''){ weight+=parseFloat($(this).val());}
	 });
     var foot="";
	 foot+="<tr>";
	 foot+="<td class='center'>合计</td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+num+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly  value='"+parseFloat(weight).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="</tr>";	
	 $("#transferAdd tfoot").append(foot);
}



/*function transfer(code){
	JY.Ajax.doRequest(null,jypath +'/scm/proTransfer/findByCode',{code:code},function(data){
		setForm(data)
	});
}*/

function delMaterialin(){
	$('#transferAdd input[name="ids"]:checked').each(function(){  
		$(this).parent().parent().parent().remove();
	});  
}


function getbaseList(){
	JY.Model.loading();
	JY.Ajax.doRequest("transferListForm",jypath +'/scm/proTransfer/findByPage',null,function(data){
		 $("#transferTable tbody").empty();
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
            		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
            		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
            		 html+="<td class='center hidden-480'><a style='cursor:pointer;' onclick='check(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.transferNo)+"</a></td>";
            		 if(l.type=='0') html+="<td class='center hidden-480'><span class='label label-sm label-success'>移库</span></td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.inWarehouseId)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.inLocationId)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.num)+"</td>";
            		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notEmpty(l.weight)).toFixed(4)+"</td>";
            		 if(l.status=='0'){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>草稿</span></td>";
            		 }else if(l.status=='1'){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>待审核</span></td>";
            		 }else if(l.status=='2'){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已审核</span></td>";
            		 }else if(l.status=='3'){
            			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>已完成</span></td>";
            		 }else if(l.status=='4'){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已拒绝</span></td>";
            		 }else if(l.status=='9'){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已删除</span></td>";
            		 }
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.createUser)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.createTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.checkUser)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.checkTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
            		 html+="</tr>";		 
            	 } 
        		 $("#transferTable tbody").append(html);
        		 JY.Page.setPage("transferListForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		 html+="<tr><td colspan='13' class='center'>没有相关数据</td></tr>";
        		 $("#transferTable tbody").append(html);
        		 $("#pageing ul").empty();//清空分页
        	 }	
 	 
    	 JY.Model.loadingClose();
	 });
}

//保存和修改
function baseInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
    	buttons:[{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存为草稿","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
    	         {html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存并提交","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},
    	         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}

	
//加载查看页面
function viewInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
}
//加载修改页面
function editInfo(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,height:700,width:1050,modal:true,
		title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",
		title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",
			click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'>" +
					"</i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function')
					{cancelfn.call(this);}}}]});
}


function checkInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
		buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,3,"您确认审核通过吗");}}},
		        {html: "<i class='icon-remove bigger-110'></i>&nbsp;不通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,4,"您确认审核不通过吗");}}},
		        {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
	});
}



function search(){
	$("#searchBtn").trigger("click");
}


function addInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({
    	resizable:false,
    	height:attr.height,
    	width:attr.width,
    	modal:true,
    	title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",
     title_html:true,
     buttons:[{
     	html: "<i class='icon-ok bigger-110'></i>&nbsp;继续添加",
     	"class":"btn btn-primary btn-xs",
     	click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},
     	{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存",
     	 "class":"btn btn-primary btn-xs",
     	 click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
     	 {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",
     	 click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);
     	 }
     	}
     	}]
     });
}

/*删除移库*/
function del(cheks){
	$("#jyConfirm").children().children().find(".causesDiv").remove();
	JY.Model.confirm("确认删除吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/proTransfer/del',{cheks:cheks.toString()},function(data){
			JY.Model.info(data.resMsg,function(){search();});
		});
	});
}

//set赋值
/*function setForm(data){
	var l=data.obj;
	$("#transferForm input[name$='transferId']").val(JY.Object.notEmpty(l.transferId));
	$("#transferForm input[name$='transferNo']").val(JY.Object.notEmpty(l.transferNo));
	$("#transferForm input[name$='code']").val(JY.Object.notEmpty(l.code));
	$("#transferForm input[name$='num']").val(JY.Object.notEmpty(l.num));
	$("#transferForm input[name$='weight']").val(JY.Object.notEmpty(l.weight));
	$("#transferForm input[name$='diffWeight']").val(JY.Object.notEmpty(l.diffWeight));
	$("#transferForm input[name$='outWarehouseId']").val(JY.Object.notEmpty(l.outWarehouseId));
	$("#transferForm input[name$='outLocationId']").val(JY.Object.notEmpty(l.outLocationId));
	$("#transferForm input[name$='description']").val(JY.Object.notEmpty(l.description));
	$("#transferDetailForm input[name$='checkUser']").val(JY.Object.notEmpty(l.checkUser));
	$("#transferDetailForm input[name$='checKtime']").val(JY.Object.notEmpty(l.checKtime));
	$("#transferDetailForm input[name$='updateUser']").val(JY.Object.notEmpty(l.updateUser));
	$("#transferDetailForm input[name$='updateTime']").val(JY.Object.notEmpty(l.updateTime));
	$("#transferDetailForm input[name$='createuser']").val(JY.Object.notEmpty(l.createuser));
	$("#transferDetailForm input[name$='createTime']").val(JY.Object.notEmpty(l.createTime));
	$("#transferDetailForm input[name$='description']").val(JY.Object.notEmpty(l.description));
	$("#transferDetailForm input[name$='remarks']").val(JY.Object.notEmpty(l.remarks));
	$("#transferDetailForm input[name$='transferNo']").val(JY.Object.notEmpty(l.transferNo));
	$("#ScmaterialForm #type").val(JY.Object.notEmpty(l.type));
	$("#transferDetailForm input[name$='status']").val(JY.Object.notEmpty(l.status));
}*/


/*删除*/
function delMaterialin(){
	var chks =[];    
	$('#transferAdd input[name="ids"]:checked').each(function(){ 
		chks.push($(this).val());    
	});     
	if(chks.length==0) {
		JY.Model.info("您没有选择任何内容!"); 
	}else{
		$("#jyConfirm").children().children().find(".causesDiv").remove();
		JY.Model.confirm("确认要删除选中的数据吗?",function(){	
			$('#transferAdd input[name="ids"]:checked').each(function(){  
				$(this).parent().parent().parent().remove();
			}); 
		});		
	}	
}

function check(id){
	clean();
	$('.btnClass').hide();
	JY.Ajax.doRequest(null,jypath +'/scm/proTransfer/view',{id:id},function(data){
		setFormTransfer(data);
		 $('#transferDiv').find('input,select,textarea').attr('disabled','disabled');
		if(data.obj.list.status=="4"){
			$("#causesDiv").show();
		}
		viewInfo({id:"transferDiv",title:"查看移库单",height:"700",width:"1024"})
	});
}


function auditing(id){
	clean();
	$('.btnClass').hide();
	JY.Ajax.doRequest(null,jypath +'/scm/proTransfer/view',{id:id,flag:"2"},function(data){
		setFormTransfer(data);
		$('#transferDiv').find('input,select,textarea').attr('disabled','disabled');
		checkInfo({id:"transferDiv",title:"查看移库单",height:"700",width:"1024",savefn:function(checkType,checkTitle){
			/*var that =$(this);
			JY.Model.confirm("确认审核吗？",function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/proTransfer/auditing',{id:id,status:e},function(data){
					that.dialog("close");   
					JY.Model.info(data.resMsg,function(){search();});
				});
			});*/
			var that =$(this);
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
				JY.Ajax.doRequest(null,jypath +'/scm/proTransfer/auditing',{id:id,status:checkType,description:causes,remarks:$("#transferDetailForm textarea[name='remarksTwo']").val()},function(data){
					that.dialog("close");
					model2.dialog("close");
					JY.Model.info(data.resMsg,function(){search();});
				});
			},function(){},flag)
		}});
	});
}

function edit(id){
	clean();
	JY.Ajax.doRequest(null,jypath +'/scm/proTransfer/view',{id:id,flag:"1"},function(data){
		setFormTransfer(data);
		if(data.obj.list.status=="4"){
			$("#causesDiv").show();
		}
		baseInfo({id:"transferDiv",title:"修改移库单",height:"720",width:"1024",savefn:function(type){
			if(JY.Validate.form("transferDetailForm") && $('#transferAdd tbody tr').length>0){
				var that =$(this);
				var json_data="";
		 	    var last_index=$('#transferAdd tbody tr').length-1;
		 	    $('#transferAdd tbody tr').each(function(element,index){
			 	    var data='{"code":"'+$(this).find("input[name=code]").val()+'","description":"'+$(this).find("input[name=description]").val()+'","id":"'+$(this).find("input[name=id]").val()+'"}';	
			 	    if($(this).index()==last_index){
			        	json_data+=data;
			        }else{
			        	json_data+=data+',';
			        }
			    });
			    json_data='['+json_data+']';
			    $.ajax({type:"POST",url:jypath+"/scm/proTransfer/edit",
			    	data: "t="+json_data+"&status="+type+"&id="+$('#transferId').val()+"&remarks="+$('#transferDetailForm').find("textarea[name=remarksTwo]").val()+"&inWarehouseId="+$('#selectWarehouse').val()+"&inLocationId="+$('#selectWarehouseLocation').val(),dataType:"text",success:function(data,textStatus){ 
					   var json_obj=eval('('+data+')');
					   if(json_obj.res==1){
			        		that.dialog("close");      
			        		JY.Model.info(json_obj.resMsg,function(){search();});
			        	}else{
			        		 JY.Model.error(json_obj.resMsg);
			        	}     	
			         }
			    });
			}	
		}});
	});
}

/*设置表单值*/
function setFormTransfer(data){
	 JY.Tools.populateForm("transferDetailForm",data.obj.list);
	 chgWarehouseLocation(data.obj.list.inWarehouseId,data.obj.list.inLocationId);
	 $('#selectWarehouse').val(JY.Object.notEmpty(data.obj.list.inWarehouseId));
	 $("#transferDetailForm span[id$='createUser']").text(JY.Object.notEmpty(data.obj.list.createName));
	 if(JY.Object.notEmpty(data.obj.list.createTime)){
		 $("#transferDetailForm span[id$='createTime']").text(JY.Object.notEmpty(new Date(data.obj.list.createTime).Format("yyyy/MM/dd hh:mm:ss")));
	 }
	 $("#transferDetailForm span[id$='updateUser']").text(JY.Object.notEmpty(data.obj.list.updateName));
	 if(JY.Object.notEmpty(data.obj.list.updateTime)){
		 $("#transferDetailForm span[id$='updateTime']").text(JY.Object.notEmpty(new Date(data.obj.list.updateTime).Format("yyyy/MM/dd hh:mm:ss")));
	 }
	 $("#transferDetailForm span[id$='checkUser']").text(JY.Object.notEmpty(data.obj.list.checkName));
	 if(JY.Object.notEmpty(data.obj.list.checkTime)){
		 $("#transferDetailForm span[id$='checkTime']").text(JY.Object.notEmpty(new Date(data.obj.list.checkTime).Format("yyyy/MM/dd hh:mm:ss")));
	 }
	 $("#transferDetailForm input[name$='transferNo']").val(JY.Object.notEmpty(data.obj.list.transferNo));
	 if(data.obj.list.type=="0"){
		 $("#transferDetailForm input[name$='typeName']").val("移库");
	 }
	 $("#transferDetailForm input[name$='type']").val(JY.Object.notEmpty(data.obj.list.type));
	 $("#transferDetailForm textarea[name$='remarksTwo']").val(JY.Object.notEmpty(data.obj.list.remarks));
	 $("#transferDetailForm textarea[name$='description']").val(JY.Object.notEmpty(data.obj.list.description));
	 var results=data.obj.details;
	 if(results!=null&&results.length>0){
		 for(var i = 0;i<results.length;i++){
			 commCode(results[i]);
		 }
	 }	 
}


/*清空表单弹出列表数据数据*/
function clean(){
	 $('.btnClass').show();
	 $("#causesDiv").hide();
	 $('#transferDiv').find('input,select,textarea').removeAttr('disabled','disabled');
	 $("#transferAdd tbody").html("");
	 $("#transferAdd tfoot").html("");
	 $('#selectWarehouse').val("");
	 $('#selectWarehouseLocation').html("");
	 $("#transferDetailForm span[id$='createUser']").text("");
	 $("#transferDetailForm span[id$='createTime']").text("");
	 $("#transferDetailForm span[id$='updateUser']").text("");
	 $("#transferDetailForm span[id$='updateTime']").text("");
	 $("#transferDetailForm span[id$='checkUser']").text("");
	 $("#transferDetailForm span[id$='checkTime']").text("");
	 JY.Tags.cleanForm("transferDetailForm");
}

/*//清空页面
function cleanForm(){
	
	$('.btnClass').show();
	$("#transferDetailForm #causesDiv").addClass("hide");
	$('#transferDetailForm').find('input,select,textarea').removeAttr('disabled','disabled');
	JY.Tags.cleanForm("transferForm");
	JY.Tags.cleanForm("baseForm");
	$("#transferForm input[name='num']").val("");
	$("#transferDivForm input[id='status']").val("0");
	$("#transferAdd tfoot").html("");
	JY.Tools.formDisabled("transferForm",false,function(){
		$("#transferForm .icon-remove").show();
		$("#transferForm .ui-datepicker-trigger").show();
	});
}*/
