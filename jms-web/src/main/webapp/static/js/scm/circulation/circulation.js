$(function () {
	JY.Dict.setSelect("selectCirculationStatus","SCM_CIRCULATION_STATUS",2,"全部");
	
	//增加回车事件
	$("#circulationBaseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
			 
		 } 
	});
	$("#findnoticeNo").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 subNoticeno();
			 search();
		 } 
	});
	$("#orgList").mouseleave(function(){
		  hideOrgComp();
	});
	getbaseList(1);
	initOrgSelectData();
	loadOrgTree();
	
	$("#findnoticeNo").typeahead({
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
        	subNoticeno();
        },
        delay: 500,
        highlighter: function (item) {//item是用户输入
	        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')//这是将字符串安全格式化为正则表达式的源码
	            return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	                return '<strong style="color:red;">' + match + '</strong>'//将下拉列表中符合项中的LI的innerHTML相等部分加个strong标签
	            })
	    }
    });
	$("#delBatch").on('click',function(){
		var chks =[];    
		$('#circulationTable input[name="ids"]:checked').each(function(){ 
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			JY.Model.confirm("确认要删除选中的数据吗?",function(){
				JY.Ajax.doRequest(null,jypath +'/scm/circulation/delBatch',{chks:chks.toString()},function(data){
					JY.Model.info(data.resMsg,function(){
						search();
					});	
				});
			});
		}	
	});
	
	$("#addBtn").on('click',function(){
		 cleanForm();
		 loadOrgTree();
		addInfo({
			id:"circulationDiv",title:"新增流转单",height:"700",width:"1024",savefn:function(){
				var that =$(this);
				if(JY.Validate.form("circulationForm")){
					var json_data="";
					var a=$("#circulationForm input[name='noticeNo']").val();
					var b=a.replace(/(^\s+)|(\s+$)/g,"");
					$("#circulationForm input[name='noticeNo']").val(b);
			 	    var last_index=$('#circulationAdd tbody tr').length-1;
			 	    $('#circulationAdd tbody tr').each(function(element,index){
			 	    var data='{"prodid":"'+$(this).find("input[name=prodid]").val()+'","code":"'+$(this).find("input[name=code]").val()+'","name":"'+$(this).find("input[name=name]").val()+'","goldtype":"'+$(this).find("input[name=goldtype]").val()+'","weight":"'+$(this).find("input[name=weight]").val()+'","count":"'+$(this).find("input[name=count]").val()+'","type":"'+$(this).find("input[name=type]").val()+'","handid":"'+$(this).find("input[name=handid]").val()+'","remainwt":"'+$(this).find("input[name=weight]").val()+'","remaincount":"'+$(this).find("input[name=count]").val()+'","cid":"'+$(this).find("input[name=cid]").val()+'"}';	
			 	    if($(this).index()==last_index){
			        	json_data+=data;
			        }else{
			        	json_data+=data+',';
			        }
				    });
				    json_data='['+json_data+']';
				    var jsonData = new FormData($("#circulationForm")[0]); 
	                jsonData.append('data', json_data);  
				    $.ajax({type:'POST',url:jypath+"/scm/circulation/add",data: jsonData,async: true,cache: false,contentType: false,processData: false,success:function(data,textStatus){  	
				    	var json_obj=eval('('+data+')');
						if(json_obj.res==1){
				        	that.dialog("close");      
				        	JY.Model.info(json_obj.resMsg,function(){search();});
				        }else{
				        	JY.Model.error(json_obj.resMsg);
				        } 
	                }});
				}
			
			}
		});
	});
	//查看
	$('#view').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#circulationTable input[name="ids"]:checked').each(function(){    
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
	$('#receive').on('click',function(){
		//通知浏览器不要执行与事件关联的默认动作		
		var chks =[];    
		$('#circulationTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val()); 
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{
			JY.Ajax.doRequest(null,jypath +'/scm/circulation/find',{id:chks[0]},function(data){
				if(data.obj.list.receiverId==curUser.id){
					receive(chks[0]);
				}else{
					JY.Model.info("请选中的收货人来接收!");
				}
				
			});
			
		}
	});
	$('#edit').on('click',function(){
		//通知浏览器不要执行与事件关联的默认动作		
		var chks =[];    
		$('#circulationTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val()); 
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{	
			JY.Ajax.doRequest(null,jypath +'/scm/circulation/find',{id:chks[0]},function(data){
				if(data.obj.list.handoverId==curUser.id){
					edit(chks[0]);
				}else{
					JY.Model.info("必须是创建人，才能修改!");
				}
				
			});
		}
	});
});

function getbaseList(init){
	if(init==1)$("#circulationBaseForm .pageNum").val(1);
	JY.Model.loading();
	JY.Ajax.doRequest("circulationBaseForm",jypath +'/scm/circulation/dataFilter_findByPage',null,function(data){
		 $("#circulationTable tbody").empty();
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
        		 html+="<td class='center hidden-480'><a style='cursor:pointer;' onclick='view(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.flowNo)+"</a></td>";
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.noticeNo)+"</td>";
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.handoverOrgName)+"</td>";
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.handoverName)+"</td>";
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.receiveOrgName)+"</td>";
        		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.receiver)+"</td>";
        		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notNumber(l.handoverCount)).toFixed(4)+"</td>";
        		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notNumber(l.handoverWt)).toFixed(4)+"</td>";
//        		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notNumber(l.surplusCount)).toFixed(4)+"</td>";
//        		 html+="<td class='center hidden-480'>"+parseFloat(JY.Object.notNumber(l.surplusWt)).toFixed(4)+"</td>";
        		 if(l.status=="1"){
        			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>已接收</span></td>";
        		 }else if(l.status=="0"){
        			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>未接收</span></td>";
        		 }else if(l.status=="9"){
        			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>已删除</span></td>";
        		 }
        		 html+="</tr>";	
        		// $("#circulationTable tbody").append(html);
        		 JY.Page.setPage("productBaseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
    		 	}	
    		 }else{
        		html+="<tr><td colspan='13' class='center'>没有相关数据</td></tr>";
        		//$("#circulationTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
		     $("#circulationTable tbody").append(html);
    	 JY.Model.loadingClose();
		 
	});
}
function addInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
    	buttons:[
    	         {html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
    	         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}

/*******组件：选择组织机构 start*******/
var preisShow=false;//窗口是否显示
function showOrgComp() {
	if(preisShow){
		hideOrgComp();
	}else{
		var obj = $("#cirInput");
		var offpos = $("#cirInput").position();
		$("#orgList").css({left:offpos.left+"px",top:offpos.top+obj.heith+"px"}).slideDown("fast");	
		preisShow=true;
	}
	
}
function hideOrgComp(){
	$("#orgList").fadeOut("fast");
	preisShow=false;
}
function emptyOrgComp(){
	$("#cirInput").prop("value","");
	$("#circulationForm input[name$='pId']").prop("value","");
}
function initOrgSelectData(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/selectOrgTreeE4',null,function(data){
		//设置
		$.fn.zTree.init($("#selectOrgTree"),{view:{dblClickExpand:false,selectedMulti:false,nameIsHTML:true},data:{simpleData:{enable: true}},callback:{onClick:clickPreOrg}},data.obj);
	});
}
function clickPreOrg(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("selectOrgTree"),
	nodes = zTree.getSelectedNodes(),v ="",n ="";	
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";//获取name值
		n += nodes[i].id + ",";	//获取id值
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (n.length > 0 ) n = n.substring(0, n.length-1);
	$("#cirInput").prop("value",v);
	//$("#storeInfoForm input[name$='pId']").prop("value",n);
	$("#circulationForm input[name$='receiveOrgId']").prop("value",n);
	$("#circulationForm input[name$='receiveOrgName']").prop("value",v);
	JY.Ajax.doRequest(null,jypath +'/scm/circulation/getByOrg',{orgid:$("#circulationForm input[name$='receiveOrgId']").val()},function(data){
	     $("#selectReceiver").empty();
	     var defaultOpts = '<option value="">请选择</option>';
	     $.each(data.obj, function(i, v) {
            defaultOpts += '<option value="' + v.value + '">' + v.key + "</option>"
        });
	     $("#selectReceiver").append(defaultOpts);
	});
	//因为单选选择后直接关闭，如果多选请另外写关闭方法
	hideOrgComp();
}
/*******组件：选择组织机构 end*******/
function loadOrgTree(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getPreOrgTree',null,function(data){
		$.jy.dropTree.init({
			rootId:"queryhandover",
			displayId:"queryhandoverorgName",
			data:data.obj,
			dataFormat:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'},
			isExpand:true,
			resetFn:function(){
				$("#circulationDiv #queryhandoverorgName").val('');
				$("#circulationDiv #queryhandoverorgId").val('');
			},
			clickFn:function(node){
				$("#circulationDiv #queryhandoverorgId").val(node.id);
			}
		});
		$.jy.dropTree.checkNode("queryhandover","queryhandoverorgName",curUser.orgId,function(){
			$("#circulationDiv #queryhandoverorgId").val(curUser.orgId);
			$("#circulationDiv #queryhandoverorgName").prop('disabled','disabled');
			 $("#circulationForm #queryhandover .icon-remove").hide();
		});
		JY.Ajax.doRequest(null,jypath +'/scm/circulation/getByOrg',{orgid:$("#circulationForm #queryhandoverorgId").val()},function(data){
		     $("#selectHandover").empty();
		     var defaultOpts = '<option value="">请选择</option>';
		     $.each(data.obj, function(i, v) {
	           defaultOpts += '<option value="' + v.value + '">' + v.key + "</option>"
	       });
		     $("#selectHandover").append(defaultOpts);
		     $("#circulationForm #selectHandover").prop('value',curUser.id);
		     $("#circulationDiv #selectHandover").prop('disabled','disabled');
		});
	});
	
}

function subNoticeno(){
	var noticeNo=$("#circulationForm input[name='noticeNo']").val();
	var result=noticeNo.replace(/(^\s+)|(\s+$)/g,"");
	if(result.length!=0){
		JY.Ajax.doRequest(null,jypath +'/scm/circulation/queryCirculationVO',{noticeno:result},function(data){
			commNoticeno(data.obj);
		});
	}
}

function commNoticeno(obj){
	var list=obj.vo;
	var html="";
	$("#circulationAdd tbody").empty();
	for(var i = 0;i<list.length;i++){
		 html+="<tr>";
		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+JY.Object.notEmpty(list[i].cid)+"' class='ace' /><span class='lbl'></span></label></td>";
		 html+="<td style='padding:1px;'><input class='center' type='text' name='code' readonly  value='"+JY.Object.notEmpty(list[i].code)+"' style='width:100%;height:30px;border:none;' readonly /></td><input type='hidden' name='prodid' value='"+JY.Object.notEmpty(list[i].prodid)+"'><input type='hidden' name='type' value='"+JY.Object.notEmpty(obj.type)+"'><input type='hidden' name='cid' value='"+JY.Object.notEmpty(list[i].cid)+"' /><input type='hidden' name='handid' value='"+JY.Object.notEmpty(list[i].handid)+"' />";
		 html+="<td style='padding:1px;'><input class='center' type='text' name='name' readonly  value='"+JY.Object.notEmpty(list[i].name)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
		 html+="<td style='padding:1px;'><input class='center' type='text' name='goldtype' readonly  value='"+JY.Object.notEmpty(list[i].goldName)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
		 if(obj.type=="1"){
			 html+="<td style='padding:1px;'><input class='center'  jyValidate='required' type='text' name='count' value='"+JY.Object.notNumber(list[i].count)+"' style='width:100%;height:30px;border:none; ' readonly /></td>";
			 html+="<td style='padding:1px;'><input class='center' jyValidate='required' type='text' name='weight' value='"+JY.Object.notNumber(list[i].weight)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
		 }else{
			 html+="<td style='padding:1px;'><input class='center' onkeyup='JY.Validate.limitNum(this)' jyValidate='required' type='text' name='count' value='"+JY.Object.notNumber(list[i].remaincount)+"' style='width:100%;height:30px;border:none; ' onchange=commCount("+JY.Object.notNumber(list[i].count)+",$(this)) /></td>";
			 html+="<td style='padding:1px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' jyValidate='required' type='text' name='weight' value='"+parseFloat(JY.Object.notNumber(list[i].remainwt)).toFixed(4)+"' style='width:100%;height:30px;border:none;  ' onchange=commWeight("+JY.Object.notNumber(list[i].weight)+",$(this)) /></td>";
		 }
		 html+="</tr>"
	}
	$("#circulationAdd tbody").append(html);
	if(list!=null||list!=""){
		commFootNoticeno();
	}
	
}
function commCount(totalCount,count){
	var number=count.parent().parent().find('input[name="count"]').val();
	if(totalCount<parseInt(number)){
		 count.tips({
             msg: "数量不能超过最大值!",
             bg: '#FF2D2D',
             time: 1
         });
		count.parent().parent().find('input[name="count"]').val(totalCount);
	}
	commFootNoticeno();
}
function commWeight(totalWeight,weight){
	var number=weight.parent().parent().find('input[name="weight"]').val();
	if(totalWeight<parseInt(number)){
		weight.tips({
            msg: "重量不能超过最大值!",
            bg: '#FF2D2D',
            time: 1
        });
		weight.parent().parent().find('input[name="weight"]').val(totalWeight);
	}
	commFootNoticeno();
}
function commFootNoticeno(){
	 $("#circulationAdd tfoot").html("");
     var count=0,weight=0.0;
	 $('#circulationAdd').find('input[name="weight"]').each(function(element,index){
		 if($(this).val()!=''){weight+=parseFloat($(this).val());}
	 });
	 $('#circulationAdd').find('input[name="count"]').each(function(element,index){
		 if($(this).val()!=''){ count+=parseFloat($(this).val());}
	 });
     var foot="";
     var foot="";
	 foot+="<tr>"; 
	 foot+="<td class='center'>合计</td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly   style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly name='totalCount' value='"+count+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="<td style='padding:1px;'><input class='center' type='text'  readonly name='totalWeight' value='"+parseFloat(weight).toFixed(4)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
	 foot+="</tr>";	
	 $("#circulationAdd tfoot").append(foot);
}
/*刷新列表页*/
function search(){
	$("#searchBtn").trigger("click");
}
/*删除*/
function delCirculation(){
	var chks =[];    
	$('#circulationAdd input[name="ids"]:checked').each(function(){ 
		chks.push($(this).val());    
	});     
	if(chks.length==0) {
		JY.Model.info("您没有选择任何内容!"); 
	}else{
		JY.Model.confirm("确认要删除选中的数据吗?",function(){	
			$('#circulationAdd input[name="ids"]:checked').each(function(){  
				$(this).parent().parent().parent().remove();
					JY.Ajax.doRequest(null,jypath +'/scm/circulation/modify',{cid:$(this).val()},function(data){
						JY.Model.info(data.resMsg,function(){
							search();
						});	
				});
				commFootNoticeno();
			}); 
		});		
	}
}
function viewInfo(attr){
	  $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
	    	buttons:[
	    	         {html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;打印预览","class":"btn btn-primary btn-xs",click:function(){print()}},
	    	         {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
	    });
}

function view(id){
	cleanForm();
	 $("#circulationForm #selectHandover").prop('value',curUser.id);
	 $("#circulationDiv #selectHandover").prop('disabled','disabled');
	$('#circulationForm').find('.btnClass').hide();
	JY.Ajax.doRequest(null,jypath +'/scm/circulation/find',{id:id},function(data){
		setBaseForm(data);
		$("#circulationForm .icon-remove").hide();
		viewInfo({id:"circulationDiv",title:"流转查看",height:"700",width:"1024"});
		$('#circulationForm').find('input,select,textarea').attr('disabled','disabled');
	});
	
}
function cleanForm(){
	$('#circulationForm').find('.btnClass').show();
	$('#circulationForm').find('input,select,textarea').removeAttr('disabled','disabled');
	JY.Tags.cleanForm("circulationForm");
	$("#circulationAdd tbody").empty();
	$("#circulationForm .icon-remove").show();
  
}
/*设置表单值*/
function setBaseForm(data){
	var p=data.obj.list;
	JY.Tools.populateForm("circulationForm",p);
	$("#circulationForm #cirInput").prop('value',p.receiveorgName);
	$("#circulationForm #note").prop('value',p.note);
	JY.Ajax.doRequest(null,jypath +'/scm/circulation/getByOrg',{orgid:p.receiveOrgId},function(data){
	     $("#selectReceiver").empty();
	     var defaultOpts = '<option value="">请选择</option>';
	     $.each(data.obj, function(i, v) {
           defaultOpts += '<option value="' + v.value + '">' + v.key + "</option>"
       });
	     $("#selectReceiver").append(defaultOpts);
	     $("#selectReceiver").prop("value",p.receiverId);
	}
);
	JY.Ajax.doRequest(null,jypath +'/scm/circulation/getByOrg',{orgid:p.handoverOrgId},function(data){
	     $("#selectHandover").empty();
	     var defaultOpts = '<option value="">请选择</option>';
	     $.each(data.obj, function(i, v) {
          defaultOpts += '<option value="' + v.value + '">' + v.key + "</option>"
      });
	     $("#selectHandover").append(defaultOpts);
	     $("#selectHandover").prop("value",p.handoverId);
	}
);
	commCode(data.obj);
}
function commCode(obj){
	var list=obj.vo;
	var html="";
	$("#circulationAdd tbody").empty();
	for(var i = 0;i<list.length;i++){
		 html+="<tr>";
		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+JY.Object.notEmpty(list[i].cid)+"' class='ace' /><span class='lbl'></span></label></td>";
		 html+="<td style='padding:1px;'><input class='center' type='text' name='code' readonly  value='"+JY.Object.notEmpty(list[i].code)+"' style='width:100%;height:30px;border:none;' readonly /></td><input type='hidden' name='prodid' value='"+JY.Object.notEmpty(list[i].prodid)+"'><input type='hidden' name='type' value='"+JY.Object.notEmpty(obj.type)+"'><input type='hidden' name='cid' value='"+JY.Object.notEmpty(list[i].cid)+"' /><input type='hidden' name='handid' value='"+JY.Object.notEmpty(list[i].handid)+"' />";
		 html+="<td style='padding:1px;'><input class='center' type='text' name='name' readonly  value='"+JY.Object.notEmpty(list[i].name)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
		 html+="<td style='padding:1px;'><input class='center' type='text' name='goldtype' readonly  value='"+JY.Object.notEmpty(list[i].goldName)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
		 if(obj.type=="1"){
			 html+="<td style='padding:1px;'><input class='center' onkeyup='JY.Validate.limitNum(this)' type='text' jyValidate='required' name='count' value='"+JY.Object.notNumber(list[i].count)+"' style='width:100%;height:30px;border:none; ' readonly /></td>";
			 html+="<td style='padding:1px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' type='text' jyValidate='required' name='weight' value='"+JY.Object.notNumber(list[i].weight)+"' style='width:100%;height:30px;border:none;' readonly /></td>";
		 }else{
			 html+="<td style='padding:1px;'><input class='center' onkeyup='JY.Validate.limitNum(this)' type='text'  jyValidate='required' name='count' value='"+JY.Object.notNumber(list[i].count)+"' style='width:100%;height:30px;border:none; ' onchange=commCount("+JY.Object.notNumber(list[i].count)+",$(this)) /></td>";
			 html+="<td style='padding:1px;'><input class='center' onkeyup='JY.Validate.limitAmtNum(this)' type='text'  jyValidate='required' name='weight' value='"+parseFloat(JY.Object.notNumber(list[i].weight)).toFixed(4)+"' style='width:100%;height:30px;border:none; ' onchange=commWeight("+JY.Object.notNumber(list[i].weight)+",$(this)) /></td>";
		 }
		 html+="</tr>"
	}
	$("#circulationAdd tbody").append(html);
	if(list!=null||list!=""){
		commFootNoticeno();
	}
}

function receive(id){
	cleanForm();
	 $("#circulationForm #selectHandover").prop('value',curUser.id);
	 $("#circulationDiv #selectHandover").prop('disabled','disabled');
	$("#circulationForm .icon-remove").hide();
	$('#circulationForm').find('.btnClass').hide();
	JY.Ajax.doRequest(null,jypath +'/scm/circulation/find',{id:id,flag:2},function(data){
		setBaseForm(data);
		checkInfo({id:"circulationDiv",title:"流转接收",height:"700",width:"1024",savefn:function(e){
			var that=$(this);
			var receId=$("#circulationForm #selectReceiver").find("option:selected").val();
			JY.Ajax.doRequest(null,jypath +'/scm/circulation/receive',{id:id,receiverId:receId},function(data){
				 JY.Model.info(data.resMsg,function(){search();});
				 that.dialog("close");
			});
			}
		});
		$('#circulationForm').find('input,select,textarea').attr('disabled','disabled');
	});
}
function checkInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
		buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;接收","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,2,"您确认审核通过吗");}}},
		        {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
	});
}
function edit(id){
	cleanForm();
	 $("#circulationForm #selectHandover").prop('value',curUser.id);
	 $("#circulationDiv #selectHandover").prop('disabled','disabled');
	$("#circulationForm #queryhandover .icon-remove").hide();
	$("#circulationDiv #queryhandoverorgName").prop('disabled','disabled');
	JY.Ajax.doRequest(null,jypath +'/scm/circulation/find',{id:id,flag:1},function(data){
		setBaseForm(data);
		addInfo({id:"circulationDiv",title:"流转修改",height:"700",width:"1024",savefn:function(e){
			var that =$(this);
			if(JY.Validate.form("circulationForm")){
				var json_data="";
		 	    var last_index=$('#circulationAdd tbody tr').length-1;
		 	    $('#circulationAdd tbody tr').each(function(element,index){
		 	    var data='{"prodid":"'+$(this).find("input[name=prodid]").val()+'","code":"'+$(this).find("input[name=code]").val()+'","name":"'+$(this).find("input[name=name]").val()+'","goldtype":"'+$(this).find("input[name=goldtype]").val()+'","weight":"'+$(this).find("input[name=weight]").val()+'","count":"'+$(this).find("input[name=count]").val()+'","type":"'+$(this).find("input[name=type]").val()+'","cid":"'+$(this).find("input[name=cid]").val()+'","handid":"'+$(this).find("input[name=handid]").val()+'"}';	
		 	    if($(this).index()==last_index){
		        	json_data+=data;
		        }else{
		        	json_data+=data+',';
		        }
			    });
			    json_data='['+json_data+']';
			    var jsonData = new FormData($("#circulationForm")[0]); 
                jsonData.append('data', json_data);  
			    $.ajax({type:'POST',url:jypath+"/scm/circulation/update",data: jsonData,async: true,cache: false,contentType: false,processData: false,success:function(data,textStatus){  	
			    	var json_obj=eval('('+data+')');
					if(json_obj.res==1){
			        	that.dialog("close");      
			        	JY.Model.info(json_obj.resMsg,function(){search();});
			        }else{
			        	JY.Model.error(json_obj.resMsg);
			        } 
                }});
			}
		
		}
		});
	});
}
function print(){
	 var id = $("#circulationForm input[name='id']").val();
	 var reportNo = $("#circulationForm input[name='noticeNo']").val();
	 $("#printDiv").load(jypath +'/scm/circulation/print?id='+id,function(){
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
