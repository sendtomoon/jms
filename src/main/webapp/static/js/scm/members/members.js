var grades=[],regTypes=[],provinces=[];
$(function(){
	grades=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"MEMBERS_GRADE",grades);
	regTypes=gridSelect(jypath +'/backstage/dataDict/getDictSelect',"MEMBERS_REGTYPE",regTypes);
	provinces=gridSelect1(jypath +'/scm/franchisee/get',provinces);
	//加载列表
	getbaseList();
	
	//新加
	$('#addBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		cleanForm();	
		JY.Tools.formDisabled("membersForm",false);
		editInfo("membersDiv","新增",function(){
			if(JY.Validate.form("membersForm")){
				var that =$(this);
					JY.Ajax.doRequest("membersForm",jypath +'/scm/members/add',null,function(data){
					     that.dialog("close");      
					     JY.Model.info(data.resMsg,function(){search();});
				    });
			}	
		});
	});
	//批量删除
	$('#delBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#baseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});  
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			JY.Model.confirm("确认要删除选中的数据吗?",function(){
				JY.Ajax.doRequest(null,jypath +'/scm/members/del',{chks:chks.toString()},function(data){
					   JY.Model.info(data.resMsg,function(){
							search();
					   });	
				});
			});
		}		
	});
	$('#findBtn').on('click', function(e) {
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
	$('#pwdBtn').on('click', function(e) {
		var chks =[];    
		$('#baseTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else if(chks.length>1){
			JY.Model.info("请选择一条内容!"); 
		}else{
			editPwd(chks[0]);
		}
	});
});

//分页查询
function getbaseList(){
	JY.Model.loading();
	JY.Ajax.doRequest("baseForm",jypath +'/scm/members/findByPage',null,function(data){
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
            		 html+="<tr>";
            		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
            		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
            		 html+="<td class='center hidden-480'><a style='cursor:pointer;' onclick='find(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.cardNo)+"</a></td>";
            		 html+="<td class='center'>"+JY.Object.notEmpty(l.nickName)+"</td>";
            		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.name)+"</td>";
            		 if(l.sex=="0"){
            			 html+="<td class='center hidden-480'>男</td>";
            		 }else if(l.sex=="1"){
            			 html+="<td class='center hidden-480'>女</td>";
            		 }else{
            			 html+="<td class='center hidden-480'>未知</td>";
            		 }
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.mobile)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.email)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.birthday)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Date.Format(l.regTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
            		 if(l.flagLock=="1"){
            			 html+="<td class='center hidden-480'>是</td>";
            		 }else{
            			 html+="<td class='center hidden-480'>否</td>";
            		 }
            		 if(l.flagActive=="1"){
            			 html+="<td class='center hidden-480'>是</td>";
            		 }else{
            			 html+="<td class='center hidden-480'>否</td>";
            		 }
            		 if(l.flagLimit=="1"){
            			 html+="<td class='center hidden-480'>是</td>";
            		 }else{
            			 html+="<td class='center hidden-480'>否</td>";
            		 }
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

//删除按钮
function del(id){
	JY.Ajax.doRequest(null,jypath +'/scm/members/del',{id:id},function(data){
		JY.Model.confirm("确认删除吗？",function(){	
			JY.Model.info(data.resMsg,function(){
				search();
				
			});
		});
		
	});
	
}

//加载查看页面
function viewInfo(id,title,fn){	
	JY.Tools.formDisabled("membersForm",true);
	$("#"+id).removeClass('hide').dialog({resizable:false,height:550,width:680,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(fn) == 'function'){fn.call(this);}}}]});
}
//加载修改页面
function editInfo(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,height:550,width:680,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
}
//修改按钮
function edit(id){
	cleanForm();
	JY.Tools.formDisabled("membersForm",false);
	 $("#formCardNo").attr("disabled", true);
	 $("#formMobile").attr("disabled", true);
	JY.Ajax.doRequest(null,jypath +'/scm/members/find',{id:id},function(data){
	    setForm(data); 
	    editInfo("membersDiv","修改",function(){
	    	if(JY.Validate.form("membersForm")){
				var that =$(this);
				JY.Ajax.doRequest("membersForm",jypath +'/scm/members/update',null,function(data){
				    that.dialog("close");
				    JY.Model.info(data.resMsg,function(){
				    	search();
				    });	
				});
			}	
	    });
	});
}
function editPwd(id){
	$("#membersPwdForm input[type$='password']").val("");
	$("#membersPwdForm input[name$='id']").val("");
	JY.Model.edit("membersPwdDiv","修改密码",function(){
		 if(JY.Validate.form("membersPwdForm")){
			 var that =$(this);
			 $("#membersPwdForm input[name$='id']").val(JY.Object.notEmpty(id));
			 var pwdNew=$("#membersPwdForm input[name$='pwdNew']").val();
			 var pwdNew1=$("#membersPwdForm input[name$='pwdNew1']").val();
			 if(pwdNew!=pwdNew1){
				 JY.Model.info("新密码与确认密码不一致");
				 return;
			 }
			 JY.Ajax.doRequest("membersPwdForm",jypath +'/scm/members/updatePwd',null,function(data){
					 that.dialog("close"); 
	        		 JY.Model.info(data.resMsg,function(){search();});
			 }); 
		 }	
	});
}
function find(id){
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/scm/members/find',{id:id},function(data){
	    setForm(data); 
	    viewInfo("membersDiv");
	});
}
//清空
function cleanForm(){
	JY.Tags.cleanForm("membersForm");
	getselect(grades,"formGrade");
	getselect(regTypes,"formRegType");
	getselect(provinces,"province");
}
//set赋值
function setForm(data){
	var l=data.obj;
	$("#membersForm input[name$='id']").val(JY.Object.notEmpty(l.id));
	$("#membersForm input[name$='cardNo']").val(JY.Object.notEmpty(l.cardNo));
	$("#membersForm input[name$='name']").val(JY.Object.notEmpty(l.name));
	$("#membersForm input[name$='nickName']").val(JY.Object.notEmpty(l.nickName));
	if(l.sex=='0') $("#membersForm input[name$='sex'][value=0]").attr("checked",'checked');
	if(l.sex=='1') $("#membersForm input[name$='sex'][value=1]").attr("checked",'checked');
	$("#membersForm input[name$='mobile']").val(JY.Object.notEmpty(l.mobile));
	$("#membersForm input[name$='email']").val(JY.Object.notEmpty(l.email));
	$("#membersForm input[name$='birthday']").val(JY.Object.notEmpty(l.birthday));
	$("#membersForm input[name$='regTime1']").val(JY.Date.Format(l.regTime,"yyyy-MM-dd hh:mm:ss"));
	$("#formRegType").val(JY.Object.notEmpty(l.regType));
	$("#membersForm input[name$='regOrg']").val(JY.Object.notEmpty(l.regOrgName));
	$("#membersForm input[name$='regUser']").val(JY.Object.notEmpty(l.regUserName));
	if(l.flagLock=='0') $("#membersForm input[name$='flagLock'][value=0]").attr("checked",'checked');
	if(l.flagLock=='1') $("#membersForm input[name$='flagLock'][value=1]").attr("checked",'checked');
	if(l.flagActive=='0') $("#membersForm input[name$='flagActive'][value=0]").attr("checked",'checked');
	if(l.flagActive=='1') $("#membersForm input[name$='flagActive'][value=1]").attr("checked",'checked');
	if(l.flagLimit=='0') $("#membersForm input[name$='flagLimit'][value=0]").attr("checked",'checked');
	if(l.flagLimit=='1') $("#membersForm input[name$='flagLimit'][value=1]").attr("checked",'checked');
	$("#membersForm input[name$='address']").val(JY.Object.notEmpty(l.address));
	$("#formGrade").val(JY.Object.notEmpty(l.grade));
	$("#province").val(JY.Object.notEmpty(l.province));
	$("#membersForm input[name$='reference']").val(JY.Object.notEmpty(l.reference));
	$("#membersForm input[name$='openId']").val(JY.Object.notEmpty(l.openId));
	$("#membersForm textarea[name$='remark']").val(JY.Object.notEmpty(l.remark));
	$("#membersForm input[name$='lastLoginTime1']").val(JY.Date.Format(l.lastLoginTime,"yyyy-MM-dd hh:mm:ss"));
	
}
function search(){
	$("#searchBtn").trigger("click");
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
function getselect(selects,opts){
	$("#"+opts).empty();
	var op="";
	var op="<option value=''>" +"请选择" + "</option>";
	$.each(selects,function(i, e) {
		op+="<option value='" + e.key + "'>" + e.text + "</option>"
    });
	$("#"+opts).append(op);
}
function gridSelect1(url,selects){
	
	$.ajax({
        type: 'POST',
        url: url,
        data: {},
        dataType: 'json',
        success: function(data, textStatus) {
        	$.each(data.obj,function(i,e){
    			var a ={key:e.id,text:e.paramValue};
    			selects[i]=a;
    		});
               
        }
    })
	return selects;
}