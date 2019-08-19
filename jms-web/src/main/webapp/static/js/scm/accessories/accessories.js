$(function(){

	JY.Dict.setSelect("selectFormula","SCMWAGEMOD",1);
	JY.Dict.setSelect("selectShape","stoneShape",1);
	JY.Dict.setSelect("selectClarity","SCM_DATA_CLARITY",1);
	JY.Dict.setSelect("selectColor","SCM_DATA_COLOR",1);
	JY.Dict.setSelect("selectCut","SCM_DATA_CUT",1);
	JY.Dict.setSelect("selectStoneUnit","SCM_DATA_STONEUNIT",1);
	JY.Dict.setSelect("selectstoneWeightarea","stoneSize",1);
	//增加回车事件
	$("#accessoriesBaseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search2();
		 } 
	});
	//新增按钮
	$("#accessoriesAdd").tooltip({hide:{effect:"slideDown",delay:250}});
	
	//新增
	$('#accessoriesAdd').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		cleanForm2();
		var productId = $("#accessoriesBaseForm input[name$='productId']").val();
		$("#accessoriesDataForm input[name$='productId']").prop("value",productId);
		editInfo2("accessoriesauDiv","新增辅件",function(){
			if(JY.Validate.form("accessoriesDataForm")){
				var that =$(this);
					JY.Ajax.doRequest("accessoriesDataForm",jypath +'/scm/accessories/add',null,function(data){
					     that.dialog("close");      
					     JY.Model.info(data.resMsg,function(){
					    	 //加载列表
						    	viewAccessories($("#accessoriesBaseForm input[name$=productId]").val());
					     });
				    });
			}	
		});
	});

});

//加载辅料页面，加载列表
function viewAccessories(productId){
	//获取商品ID
	$("#accessoriesBaseForm input[name$=productId]").val(productId);
	//加载辅料页面
	$("#accessoriesDiv").removeClass('hide').dialog({resizable:false,height:500,width:1024,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+"商品辅料"+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(fn) == 'function'){fn.call(this);}}}]});
	JY.Model.loading();
	JY.Ajax.doRequest(null,jypath +'/scm/accessories/findByproductId',{productId:productId},function(data){
		$("#accessoriesbaseTable tbody").empty();
       	 var obj=data.obj;
       	 var html="";
   		 if(obj!=null&&obj.length>0){
       		 for(var i = 0;i<obj.length;i++){
           		 var l=obj[i];
           		 html+="<tr>";
           		 html+="<td class='center hidden-480'>"+(i+1)+"</td>";
           		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stoneCode)+"</td>";
           		 html+="<td class='center'>"+JY.Object.notEmpty(l.stoneName)+"</td>";
           		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.stoneWeight)+"</td>";
           		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stoneCount)+"</td>";
           		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.purPrice)+"</td>";
           		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.costPrice)+"</td>";
           		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.jeweler)+"</td>";
           		 if(l.stoneFlag==1){
           			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>是</span></td>";
           		 } else{
           			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>否</span></td>";
           		 }
           		 if(l.status==1){ 
           			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>有效</span></td>";
           		 }else if(l.status == 0){
           			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>无效</span></td>";
           			
           		 }else if(l.status == 9){
           			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>删除</span></td>";
           		 }
           		 	 html+="<td>";
					 html+="<div class='inline position-relative'>";
					 html+="<button class='btn btn-minier btn-primary dropdown-toggle' data-toggle='dropdown'><i class='icon-cog icon-only bigger-110 '></i></button>";
					 html+="<ul class='dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close'>";	
					 html+="<li><a class='aBtnNoTD' onclick='check(&apos;"+l.id+"&apos;)' title='查看' href='#'>查看</a></li>";
					 html+="<li><a class='aBtnNoTD' onclick='edit2(&apos;"+l.id+"&apos;)' title='修改' href='#'>修改</i></a></li>";
					 html+="<li><a class='aBtnNoTD' onclick='del2(&apos;"+l.id+"&apos;)' title='删除' href='#'>删除</a></li>";
					 html+="</ul></div>";	
					 html+="</td>";
					 html+="</tr>";
					 
           	 } 
       		 $("#accessoriesbaseTable tbody").append(html);
       	 }else{
       		html+="<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
       		$("#accessoriesbaseTable tbody").append(html);
       	 }	
   		 JY.Model.loadingClose();
	 });

}
//查看按钮
function check(id){
	cleanForm2();
	var productId = $("#accessoriesBaseForm input[name$='productId']").val();
	$("#accessoriesDataForm input[name$='productId']").prop("value",productId);
	$("#accessoriesDataForm input[name$='id']").prop("readonly",true);
		JY.Ajax.doRequest(null,jypath +'/scm/accessories/find',{id:id},function(data){ 
			check2("accessoriesauDiv");
			setForm2(data);
			JY.Tools.formDisabled("accessoriesDataForm",true,function(){
				$("#accessoriesDataForm .icon-remove").hide();
				$("#accessoriesDataForm .ui-datepicker-trigger").hide();
			});
		});
}	

//加载修改页面
function editInfo2(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,height:600,width:800,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
}

//加载查看页面
function check2(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,height:600,width:800,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
}
//修改按钮
function edit2(id,_positive){
	cleanForm2();
	JY.Ajax.doRequest(null,jypath +'/scm/accessories/find',{id:id},function(data){
		setForm2(data); 
		var productId = data.obj.productId;
	    editInfo2("accessoriesauDiv","修改辅件",function(){
	    	if(JY.Validate.form("accessoriesDataForm")){
				var that =$(this);
				JY.Ajax.doRequest("accessoriesDataForm",jypath +'/scm/accessories/update',null,function(data){
				    that.dialog("close");
				    JY.Model.info(data.resMsg,function(){
				    	 //加载列表
				    	if(_positive){
				    		getAccessories(productId,_positive);
				    	}else{
				    		viewAccessories($("#accessoriesBaseForm input[name$=productId]").val());
				    	}
				    });	
				});
			}	
	    });
	});
}
//删除按钮
function del2(id,_positive){
	JY.Ajax.doRequest(null,jypath +'/scm/accessories/del',{id:id},function(data){
		JY.Model.confirm("确认删除吗？",function(){	
			JY.Model.info(data.resMsg,function(){
				if(flag){
					getAccessories(id,_positive);
		    	}else{
				 //加载列表
		    	viewAccessories($("#accessoriesBaseForm input[name$=productId]").val());
		    	}
			});
		});
		
	});
	
}
//清空
function cleanForm2(){
	JY.Tags.cleanForm("accessoriesDataForm");
	JY.Tags.isValid("accessoriesDataForm",1);
	JY.Tags.isValid2("accessoriesDataForm",0);
	JY.Tools.formDisabled("accessoriesDataForm",false,function(){
		$("#accessoriesDataForm .icon-remove").show();
		$("#accessoriesDataForm .ui-datepicker-trigger").show();
	});
}

//set赋值
function setForm2(data){
	var l=data.obj;
	JY.Tools.populateForm("accessoriesDataForm",data.obj);
	$("#accessoriesDataForm #stoneShape").val(l.stoneShape);
	$("#description").val(l.description);
	JY.Tags.isValid("accessoriesDataForm",(JY.Object.notNull(l.status)?l.status:"0"));
	JY.Tags.isValid2("accessoriesDataForm",(JY.Object.notNull(l.stoneFlag)?l.stoneFlag:"0"));
	
}
function search2(){ 
	$("#searchBtn").trigger("click");
}