$(function(){
	/*下拉框*/
	JY.Dict.setSelect("selectcerName","cerofficeName",2,"请选择认证机构",function(){
		var _that = $(this);
		var displayName = _that.find("option:selected").text();
		$("#CredentialauForm input[name$='cerofficeName']").val(displayName);
	});
	/*增加回车事件*/
	$("#CredentialBaseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search3();
		 } 
	});
	//下载导入模版
	$('#downloadTemp').on('click',function(){		
		window.open("/jms/scm/common/downloads?name="+encodeURI('证书导入模板.xls'),'_blank');
	});
	/*新加*/
	$('#CredentialAdd').on('click', function(e) {
		e.preventDefault();
		cleanForm3();	
		/*通知浏览器不要执行与事件关联的默认动作*/		
		$("#CredentialauForm input[name$='id']").prop("readonly",false);
		/*赋值辅料文本*/
		var accessorieId = $("#CredentialBaseForm input[name$='accessorieId']").val();
		$("#CredentialauForm input[name$='accessorieId']").prop("value",accessorieId);
		/*赋值商品文本*/
		var productId=$("#CredentialBaseForm input[name$=productId]").val();
		$("#CredentialauForm input[name$='productId']").prop("value",productId);
		/*禁写商品，辅料编号*/
		$("#CredentialauForm input[name$='accessorieId']").prop("readonly",true);
		$("#CredentialauForm input[name$='productId']").prop("readonly",true);
		editInfo3("CredentialauDiv","新增证书",function(){
			if(JY.Validate.form("CredentialauForm")){
				var that =$(this);
				var file = new FormData($("#CredentialauForm")[0]);
				$.ajax({type:'POST',url:jypath +'/scm/credential/add',data: file,async: false,cache: false,contentType: false,processData: false,success:function(data,textStatus){  			        	 
					that.dialog("close");
					var json_obj=eval('('+data+')');
				    JY.Model.info(json_obj.resMsg,function(){search();});	        	 
				}
				});
			}	
		});
	});
	/*时间渲染*/
	$("#cerDate").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd ',
		changeYear: true,
		changeMonth:true,
		onSelect: function(dateText, inst) {
		}
	});
	$('#uploadBtn').on('click',function(){
		divDisabled("excelCredential",false);
		viewInfo3({
			id:"excelCredential",
			title:"证书",
			height:"200",
			width:"600"});
	});
	$("#excelCredentialForm").ajaxForm({
        dataType:'json',
        beforeSend: function() {
            if($("#Credentialfile").val()==""){
            	JY.Model.info("请上传文件！")
            	return false;
            }
        },
        success: function(data) {
        	JY.Model.info(data.resMsg);
        	if(data.res==1){
        		self.location.href = jypath+"/scm/credential/execlIndex/"+data.obj;
        	}
        },
        resetForm : true,
        error:function(data){
        	JY.Model.info(data.resMsg);
        }
    });
});


function divDisabled(divId,boolean){
	$("#"+divId+" input").each(function(){
	    $(this).attr("disabled", boolean)
	});
	$("#"+divId+" select").each(function(){
	    $(this).attr("disabled", boolean)
	});
	$("#"+divId+" textarea").each(function(){
	    $(this).attr("disabled", boolean)
	});
}
/*加载证书页面，加载证书列表*/
function infocheck(id){
				JY.Ajax.doRequest(null,jypath  +'/scm/credential/credentialByInfo',{id:id},function(data){
					$("#credentialByInfoTable tbody").empty();
			       	 var obj=data.obj;
			       	 var html="";
			   		 if(obj!=null&&obj.length>0){
			       		 for(var i = 0;i<obj.length;i++){
			           		 var l=obj[i];
			           		 html+="<tr>";
			           		 html+="<td class='center hidden-480'>"+(i+1)+"</td>";
			           		 if(l.flag=='1'){
			           			 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.name)+"</td>";
			           			 html+= JY.Object.notNull(l.cerName)?"<td class='center'><a style='cursor:pointer;' onclick='checkInfo(&apos;"+JY.Object.notEmpty(l.id)+"&apos;,&apos;"+JY.Object.notEmpty(l.flag)+"&apos;)'>"+JY.Object.notEmpty(l.cerName)+"</a></td>":"<td class='center'>无</td>";
			           			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>商品</span></td>";
			           		 } else{
			           			 html+="<td class='center hidden-480'><a style='cursor:pointer;' onclick='viewInfoAccessories(&apos;"+JY.Object.notEmpty(l.accid)+"&apos;)'>"+JY.Object.notEmpty(l.name)+"</a></td>";
			           			 html+= JY.Object.notNull(l.cerName)?"<td class='center'><a style='cursor:pointer;' onclick='checkInfo(&apos;"+JY.Object.notEmpty(l.accid)+"&apos;,&apos;"+JY.Object.notEmpty(l.flag)+"&apos;)'>"+JY.Object.notEmpty(l.cerName)+"</a></td>":"<td class='center'>无</td>"; 
			           			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>辅件</span></td>";
			           		 }
			           		 html+="<td class='center'>&nbsp;</td>";
							 html+="</tr>";
			           	 } 
			       		 $("#credentialByInfoTable tbody").append(html);
			       	 }else{
			       		html+="<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
			       		$("#credentialByInfoTable tbody").append(html);
			       	 }	
			   		 JY.Model.loadingClose();
				});
				viewInfo3({id:"credentialByInfoDiv",title:"证书列表",height:"650",width:"700"})
}

/*查看页面*/
function viewInfo3(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
}
/*编辑页面*/
function editInfo3(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
}

/*证书明细*/
function checkInfo(id,type){
	JY.Ajax.doRequest(null,jypath  +'/scm/credential/credentialcheckInfo',{id:id,type:type},function(data){
		 cleanForm3()
		 setForm3(data);
		 JY.Tools.formDisabled("CredentialauForm",true);
		 viewInfo3({id:"CredentialauDiv",title:"证书明细",height:"650",width:"500"})
	});
}

/*编辑证书列表*/
function editInfoList(id){
				JY.Ajax.doRequest(null,jypath  +'/scm/credential/credentialByInfo',{id:id},function(data){
					$("#credentialByInfoTable tbody").empty();
			       	 var obj=data.obj;
			       	 var html="";
			   		 if(obj!=null&&obj.length>0){
			       		 for(var i = 0;i<obj.length;i++){
			           		 var l=obj[i];
			           		 html+="<tr>";
			           		 html+="<td class='center hidden-480'>"+(i+1)+"</td>";
			           		 if(l.flag=='1'){
			           			html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.name)+"</td>";
			           			html+=JY.Object.notNull(l.cerName)?"<td class='center hidden-480'>"+JY.Object.notEmpty(l.cerName)+"</td>":"<td class='center'>无</td>";
			           			html+="<td class='center hidden-480'><span class='label label-sm label-success'>商品</span></td>";
			           			html+=JY.Object.notNull(l.cerName)?"<td><a class='aBtnNoTD dropbtn' onclick='editCredential(&apos;"+l.id+"&apos;,&apos;"+l.flag+"&apos;)' title='修改' href='#'>修改</a>" +
			           					 "</td>":"<td><a class='aBtnNoTD dropbtn' onclick='editCredential(&apos;"+l.id+"&apos;,&apos;"+l.flag+"&apos;)' title='添加' href='#'>添加</a></td>";
			           		 } else if(l.flag=="0"){
			           			html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.name)+"</td>";
			           			html+=JY.Object.notNull(l.cerName)?"<td class='center hidden-480'>"+JY.Object.notEmpty(l.cerName)+"</td>":"<td class='center'>无</td>";
			           			html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>辅件</span></td>"; 
			           			html+=JY.Object.notNull(l.cerName)?"<td><a class='aBtnNoTD dropbtn' onclick='editCredential(&apos;"+l.accid+"&apos;,&apos;"+l.flag+"&apos;)' title='修改' href='#'>修改</a>" +
			           					 "</td>":"<td><a class='aBtnNoTD dropbtn' onclick='editCredential(&apos;"+l.accid+"&apos;,&apos;"+l.flag+"&apos;)' title='添加' href='#'>添加</a></td>";
			           		 }
							 html+="</tr>";		
			           	 } 
			       		 $("#credentialByInfoTable tbody").append(html);
			       	 }else{
			       		html+="<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
			       		$("#credentialByInfoTable tbody").append(html);
			       	 }	
//			   		<a class='aBtnNoTD' onclick='deleteInfo(&apos;"+l.id+"&apos;,&apos;"+l.flag+"&apos;)' title='删除' href='#'><i class='icon-remove-sign color-red bigger-120'></i></a>
			   		 JY.Model.loadingClose();
				});
				viewInfo3({id:"credentialByInfoDiv",title:"证书列表",height:"650",width:"800"})
}


function viewCredential(id){
	editInfoList(id);
}



function editCredential(id,type,_positive){
	JY.Ajax.doRequest(null,jypath  +'/scm/credential/toEditCredential',{id:id,type:type},function(data){
		 cleanForm3();
		 setForm3(data);
		 var pid=data.obj.productId;
		 editInfo3({id:"CredentialauDiv",title:"编辑证书",height:"650",width:"450",savefn:function(){
		    if(JY.Validate.form("CredentialauForm")){
				var that =$(this);
				var file = new FormData($("#CredentialauForm")[0]);
				$.ajax({type:'POST',url:jypath +'/scm/credential/editCredential',data: file,async: true,cache: false,contentType: false,processData: false,success:function(data,textStatus){  			        	 
					var json_obj=eval('('+data+')');
				    if(json_obj.res=='1'){
						that.dialog("close");
						JY.Model.info(json_obj.resMsg,function(){
							if(_positive){
								getCredential(pid,_positive);
							}else{
								editInfoList(pid);
							}
						});	
					}else{
						JY.Model.info(json_obj.resMsg);	
					}
				}
			});
		   }	
	    }});
	});
}

function deleteInfo(id,type){
	JY.Ajax.doRequest(null,jypath  +'/scm/credential/toEditCredential',{id:id,type:type},function(data){
		var creid=data.obj.id;
		if(creid==null){
			JY.Model.info("信息不存在");
		}else{
			JY.Model.confirm("确认删除吗？",function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/credential/del',{id:creid},function(data){
					JY.Model.info(data.resMsg);
				});
			});
		}
	});
}

/*清空*/
function cleanForm3(){
	JY.Tags.cleanForm("CredentialauForm");
	JY.Tags.isValid("CredentialauForm",1);
	JY.Tools.formDisabled("CredentialauForm",false);
	$("#CredentialauForm .imgone").attr('src',jypath +"/static/images/no_img.jpg");
}

/*set赋值*/
function setForm3(data){
	var l=data.obj;
	$("#CredentialauForm input[name$='productId']").val(JY.Object.notEmpty(l.productId));
	$("#CredentialauForm input[name$='accessorieId']").val(JY.Object.notEmpty(l.accessorieId));
	JY.Tools.populateForm("CredentialauForm",l);
	$("#CredentialauForm input[name$='cerofficeName']").val(JY.Object.notEmpty(l.cerofficeName));
	$("#cerofficeId").val(JY.Object.notEmpty(l.cerofficeId));
	var date = JY.Object.notEmpty(l.cerDate).substring(0,10);
	$("#CredentialauForm input[name$='cerDate']").val(JY.Object.notEmpty(date));
	JY.Tags.isValid("CredentialauForm",(JY.Object.notNull(l.status)?l.status:"0"));
	if(JY.Object.notEmpty(l.filePath)){
		$("#CredentialauForm .imgone").attr('src',JY.Object.notEmpty(l.filePath));
	}else{
		$("#CredentialauForm .imgone").attr('src',jypath +"/static/images/no_img.jpg");
	}
}
function search3(){
	$("#searchBtn").trigger("click");
}

/*查看辅件明细*/
function viewInfoAccessories(id){
	$("#accessoriesauDiv").removeClass('hide').dialog(
			{
				resizable : false,
				height : 600,
				width : 800,
				modal : true,
				title : "<div class='widget-header'><h4 class='smaller'>查看辅件</h4></div>",
				title_html : true,
				buttons : [ {
					html : "<i class='icon-remove bigger-110'></i>&nbsp;取消",
					"class" : "btn btn-xs",
					 click : function() {
						$(this).dialog("close");
					}
				} ]
			});
	JY.Ajax.doRequest(null,jypath +'/scm/accessories/find',{id:id},function(data){ 
		setFormAccessories(data);
		JY.Tools.formDisabled("accessoriesDataForm",true,function(){
			$("#accessoriesDataForm .icon-remove").hide();
			$("#accessoriesDataForm .ui-datepicker-trigger").hide();
		});
	})
}

/*set辅件*/
function setFormAccessories(data){
	var l=data.obj;
	JY.Tools.populateForm("accessoriesDataForm",data.obj);
	$("#accessoriesDataForm #stoneShape").val(l.stoneShape);
	$("#description").val(l.description);
	JY.Tags.isValid("accessoriesDataForm",(JY.Object.notNull(l.status)?l.status:"0"));
	JY.Tags.isValid2("accessoriesDataForm",(JY.Object.notNull(l.stoneFlag)?l.stoneFlag:"0"));
		
}