$(function () {
	var flag = $("#stateFlag").val();
	if(flag=="true"){
		$("#selectProductStatus").hide();
	}else{
		JY.Dict.setSelect("selectProductStatus","SCM_PRODUCT_STATUS",2,"全部");
	}
	JY.Dict.setSelect("proControlType","SCMPROCONTROLTYP");
	JY.Dict.setSelect("selectProdCate","SCM_PRO_CATE",1);
	JY.Dict.setSelect("selectJewelryMode","SCM_PRO_PROCESS_MODE",1);
	JY.Dict.setSelect("selectGoldType","SCM_PRO_GOLD_TYPE",1);
	JY.Dict.setSelect("selectLabelType","SCM_PRO_LABEL_TYPE",1);
	JY.Dict.setSelect("selectStoneShape2","stoneShape",1);
	JY.Dict.setSelect("selectCCategoryId","SCM_PRODUCT_TYPE",1);
	JY.Dict.setSelect("selectFCategoryId","SCM_PRO_FINANCE_TYPE");
	JY.Dict.setSelect("selectSCategoryId","SCM_PRO_SALL_TYPE");
	JY.Dict.setSelect("selectJewelryType","SCM_PRO_CATE_JEWELRY",1);
	JY.Dict.setSelect("selGoldType","SCM_PRO_GOLD_TYPE",1);
	JY.Dict.setSelect("selProdCate","SCM_PRO_CATE",1);
	JY.Dict.setSelect("selJewelryType","SCM_PRO_CATE_JEWELRY",1);
	
	getbaseList(1);
	
	selectOrg();
	
	//增加回车事件
	$("#productBaseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	
	//下载导入模版
	$('#downloadTemplate').on('click',function(){		
		window.open("/jms/scm/common/downloads?name="+encodeURI('商品导入模版.xls'),'_blank');
	});
	
	/*-----------*/
		$('#importBtn').on('click',function(){
			importInfo({
				id:"excel",
				title:"商品",
				height:"200",
				width:"600"});
		});
	/*-----------*/
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
	        		self.location.href = jypath+"/scm/product/indexExcel";
	        	}
	        },
	        resetForm : true,
	        error:function(){
	        	JY.Model.info(data.resMsg);
	        }
	    });
		
	$('#queryGoldType,#cateId,#cateJewelryId,#jewelryMode').on('change',function(e){
		var _goldCate = $("#queryGoldType option:selected").val()==""?"":$("#queryGoldType option:selected").text();
		var _cateName = $("#cateId option:selected").val()==""?"":$("#cateId option:selected").text();
		var _groupName = $("#cateJewelryId option:selected").val()==""?"":$("#cateJewelryId option:selected").text();
		var _jewelryMode = $("#jewelryMode option:selected").val()==""?"":$("#jewelryMode option:selected").text();
		var _name = JY.Object.notEmpty(_goldCate) + JY.Object.notEmpty(_cateName) + JY.Object.notEmpty(_groupName)+ JY.Object.notEmpty(_jewelryMode);
		$("#productInfoForm input[name$='name']").val(_name);
	});
	
	
	$('#addBtn').on('click', function(e) {
		var _positive=true;
		$("#productInfoForm #jewelryMode").prop("value",1);
		$("#productInfoForm input[name$='id']").val('');
		$("#productInfoForm #wageMode2").prop("checked",true);
		//通知浏览器不要执行与事件关联的默认动作
		uploader.addButton({
			id : '#filePicker2',
			label : '点击选择图片'
		});
		cleanForm();
		/*禁止切换选项卡*/
		$('#dropdownAccessories').on('click', function(e) {
			
			return false;
		});
		$('#dropdownCredential').on('click', function(e) {
			return false;
		});
		pricing();
		
		e.preventDefault();
		addInfo({
				id:"productDiv",
				title:"新增商品",
				height:"700",
				width:"830",
				savefn:function(type){
					 if(JY.Validate.form("productInfoForm")){
						 var that =$(this);
						 var id=$("#productInfoForm input[name$='id']").val();
						 	if(!JY.Object.notEmpty(id)){
						 		 JY.Ajax.doRequest("productInfoForm",jypath +'/scm/product/add',{status:type},function(data){
									 $("#productInfoForm input[name$='id']").val(data.obj);
								     JY.Model.info(data.resMsg,function(){
								    	JY.Model.confirm("是否录入其他信息？",function(){
								    		$('#dropdownAccessories').unbind('click');
										 	 $('#dropdownCredential').unbind('click');
										 	 getCredential(data.obj,_positive);
								    	},function(){
								    		search();
								    		 that.dialog("close");
								    	});
								    	search();
								     });
								 });
						 	}else{
						 		JY.Ajax.doRequest("productInfoForm",jypath +'/scm/product/update',{status:type},function(data){
						 			JY.Model.info(data.resMsg,function(){
								    	JY.Model.confirm("是否录入其他信息？",function(){
								    		$('#dropdownAccessories').unbind('click');
										 	 $('#dropdownCredential').unbind('click');
										 	 getCredential(id,_positive);
								    	},function(){
								    		search();
								    		 that.dialog("close");
								    	});
								    	search();
								     });
						 		});
						 	}
						
					 }	
				}	
		});
	});
	/*添加辅件信息*/
	$('#productAccessoriesAdd').on('click', function(e) {
		var _positive=true;
		cleanForm2();
		var productId = $("#productInfoForm input[name$='id']").val();
		$("#accessoriesDataForm input[name$='productId']").prop("value",productId);
		editInfo({
			id:"accessoriesauDiv",
			title:"新增辅件",
			height:"620",
			width:"800",
			savefn:function(){
				 if(JY.Validate.form("accessoriesDataForm")){
					 var that =$(this);
					 JY.Ajax.doRequest("accessoriesDataForm",jypath +'/scm/accessories/add',null,function(data){
					     JY.Model.info(data.resMsg,function(){
					    	 getAccessories(productId,_positive);
					    	 getCredential(productId,_positive);
					    	 that.dialog("close");
					     });
					 });
				 }	
			}	
		});
	});
	//批量删除
	$('#delBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#productList input[name="ids"]:checked').each(function(){    
			chks.push($(this).val());    
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{
			JY.Model.confirm("确认要删除选中的数据吗?",function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/product/delBatch',{chks:chks.toString()},function(data){
					JY.Model.info(data.resMsg,function(){search();});
				});
			});		
		}		
	});
	
	var supplierMap={},CodeMap={},moudtlMap={},franchisee={};
	//款号检索
	$("#queryMoudleCode").typeahead({
        source: function (query, process) {
        	$.ajax({
				type:'POST',
				url:jypath+'/scm/product/queryMoudle/'+query,
				data:{},
				dataType:'json',
				success:function(result,textStatus){ 
					//CodeMap = {};
					var arr= [];
					 $.each(result, function (index, ele) {
						// CodeMap[ele.code] = ele.id;
						 arr.push(ele.code);
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
	
	//供应商检索
	$("#queryFranchisee").typeahead({
        source: function (query, process) {
        	$.ajax({
				type:'POST',
				url:jypath+'/scm/franchisee/findLongNamePage',
				data:{longName:query},
				dataType:'json',
				success:function(result,textStatus){ 
					supplierMap = {};
					var array= [];
					 $.each(result.obj, function (index, ele) {
						 supplierMap[ele.Name] = ele.id;
						 array.push(ele.Name);
                     });
					process(array);
				}
			});
        	
        },
        items: 20,
        afterSelect: function (item) {
        	$("#productInfoForm input[name$='franchiseeId']").val(supplierMap[item]);
        	$("#productInfoForm input[name$='modelCode']").removeAttr("readonly");
        },
        delay: 500,
        highlighter: function (item) {
	        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')
	            return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	                return '<strong style="color:red;">' + match + '</strong>';
	            })
	    }
    });
	
	$("#findFranchisee").typeahead({
        source: function (query, process) {
        	$.ajax({
				type:'POST',
				url:jypath+'/scm/franchisee/findLongNamePage',
				data:{longName:query},
				dataType:'json',
				success:function(result,textStatus){ 
					supplierMap = {};
					var array= [];
					 $.each(result.obj, function (index, ele) {
						 supplierMap[ele.longName] = ele.id;
						 array.push(ele.longName);
                     });
					process(array);
				}
			});
        	
        },
        items: 20,
        afterSelect: function (item) {
        	$("#productBaseForm input[name$='franchiseeId']").val(supplierMap[item]);
        	$("#productInfoForm input[name$='modelCode']").removeAttr("readonly");
        },
        delay: 500,
        highlighter: function (item) {
	        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')
	            return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	                return '<strong style="color:red;">' + match + '</strong>';
	            })
	    }
    });
	
	//工厂款号检索
	$("#queryMoudtlCode").typeahead({
        source: function (query, process) {
        	$.ajax({
				type:'POST',
				url:jypath+'/scm/product/queryModCode/'+query,
				data:{},
				dataType:'json',
				success:function(result,textStatus){ 
					moudtlMap = {};
					franchisee={};
					var list= [];
					 $.each(result, function (index, ele) {
						 moudtlMap[ele.suppmouCode] = ele.id;
						 list.push(ele.suppmouCode);
                     });
					process(list);
				}
			});
        	
        },
        items: 20,
        afterSelect: function (value) {
        	$.ajax({
        		type:'POST',
        		url:jypath+'/scm/product/queryMF',
        		data:{id:moudtlMap[value]},
        		dataType:'json',
        		success:function(data,textStatus){
        			$("#productInfoForm input[name$='mouCode']").val(data.obj.code);
        			$("#productInfoForm input[name$='franchiseeName']").val(data.obj.name);
        			$("#productInfoForm input[name$='franchiseeId']").val(data.obj.id);
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
    });
});

function search(){
	$("#searchBtn").trigger("click");
}

function clearSelect(id,options){
    //获取select dom对象
    var allSelect = document.getElementById(id);
    //获取select下拉框下所有的选项
    var optionArr = allSelect.getElementsByTagName("option");
    var len = optionArr.length;
    var j=0
    for(var i=0;i<len;i++){
    	if(options.indexOf(optionArr[i-j].innerText+",")>-1){
    		allSelect.remove(i-j);
    		j++
    	}
    }
};
/*辅件列表*/
var getAccessories=function(id,_positive){
	JY.Model.loading();
	JY.Ajax.doRequest(null,jypath +'/scm/accessories/findByproductId',{productId:id},function(data){
		$("#productAccessoriesTabe tbody").empty();
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
          		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.purcal)+"</td>";
          		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.costCal)+"</td>";
          		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stoneunitName)+"</td>";
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
          		 if(_positive){
          			 html+="<td>";
					 html+="<div class='inline position-relative'>";
					 html+="<button class='btn btn-minier btn-primary dropdown-toggle' data-toggle='dropdown'><i class='icon-cog icon-only bigger-110 '></i></button>";
					 html+="<ul class='dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close'>";	
					 html+="<li><a class='aBtnNoTD' onclick='edit2(&apos;"+l.id+"&apos;,&apos;"+_positive+"&apos;)' title='修改' href='#' style='margin: 3px'>修改</a></li>";
					 html+="<li><a class='aBtnNoTD' onclick='del2(&apos;"+l.id+"&apos;,&apos;"+_positive+"&apos;)' title='删除' href='#'>删除</a></li>";
					 html+="</ul></div>";	
					 html+="</td>";
					 html+="</tr>";
          		 }else{
          			html+="<td><a class='aBtnNoTD dropbtn' onclick='check(&apos;"+l.id+"&apos;)' title='查看' href='#'>查看</a></td>";
          		 }
          		 html+="</tr>";
          	 } 
      		 $("#productAccessoriesTabe tbody").append(html);
      	 }else{
      		html+="<tr><td colspan='11' class='center'>没有相关数据</td></tr>";
      		$("#productAccessoriesTabe tbody").append(html);
      	 }	
  		 JY.Model.loadingClose();
	});
}
/*证书列表*/
var getCredential = function(id,_positive){
	JY.Ajax.doRequest(null,jypath  +'/scm/credential/credentialByInfo',{id:id},function(data){
		$("#productCredentialTabe tbody").empty();
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
           			 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.name)+"</td>";
           			 html+= JY.Object.notNull(l.cerName)?"<td class='center'><a style='cursor:pointer;' onclick='checkInfo(&apos;"+JY.Object.notEmpty(l.accid)+"&apos;,&apos;"+JY.Object.notEmpty(l.flag)+"&apos;)'>"+JY.Object.notEmpty(l.cerName)+"</a></td>":"<td class='center'>无</td>";  
           			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>辅件</span></td>";
           		 }
           		 if(_positive){
           			html+="<td><a class='aBtnNoTD dropbtn' onclick='editCredential(&apos;"+l.accid+"&apos;,&apos;"+l.flag+"&apos;,&apos;"+_positive+"&apos;)' title='修改' href='#'>修改</a></td>"
           		 }else{
           			 if(l.flag=='1'){
           				html+=JY.Object.notNull(l.cerName)?"<td><a class='aBtnNoTD dropbtn' onclick='checkInfo(&apos;"+JY.Object.notEmpty(l.id)+"&apos;,&apos;"+JY.Object.notEmpty(l.flag)+"&apos;)' title='查看' href='#'>查看</a></td>":"<td>&nbsp;</td>";
           			 }else{
           				html+=JY.Object.notNull(l.cerName)?"<td><a class='aBtnNoTD dropbtn' onclick='checkInfo(&apos;"+JY.Object.notEmpty(l.accid)+"&apos;,&apos;"+JY.Object.notEmpty(l.flag)+"&apos;)' title='查看' href='#'>查看</a></td>":"<td>&nbsp;</td>";
           			 }
           		 }
				 html+="</tr>";
           	 } 
       		 $("#productCredentialTabe tbody").append(html);
       	 }else{
       		html+="<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
       		$("#productCredentialTabe tbody").append(html);
       	 }	
   		 JY.Model.loadingClose();
	});
}

/**组装并加载table数据列表**/
function getbaseList(init){
	if(init==1)$("#productBaseForm .pageNum").val(1);	
	var state = $('#stateValue').val();
	var marking = $("#productBaseForm input[name$='marking']").val();
	JY.Model.loading();
	JY.Ajax.doRequest("productBaseForm",jypath +'/scm/product/findByPage?state='+state+'&marking='+marking,null,function(data){
		 $("#productList tbody").empty();
        	 var obj=data.obj;
        	 var list=obj.list;
        	 var results=list.results;
        	 var permitBtn=obj.permitBtn;
         	 var pageNum=list.pageNum,pageSize=list.pageSize,totalRecord=list.totalRecord;
        	 var html="";
        	 var _flag = $("#stateFlag").val();
    		 if(results!=null&&results.length>0){
        		 var leng=(pageNum-1)*pageSize;//计算序号
        		 for(var i = 0;i<results.length;i++){
            		 var l=results[i];
	            			 html+="<tr>";
	                		 html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
	                		 html+="<td class='center hidden-480'>"+(i+leng+1)+"</td>";
	                		 html+="<td class='center hidden-480' ><a style='cursor:pointer;' onclick='view(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.code)+"</a></td>";
//	                		 if(_flag!="DEFAULT") html+="<td class='center'>"+JY.Object.notEmpty(l.inWarehouseNum)+"</td>";
	                		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.name)+"</td>";
	                		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.totalWeight)+"</td>";
	                		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.circel)+"</td>";
	                		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.orgSimpleName)+"</td>";
	                		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.franchiseeName)+"</td>";
	                		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(new Date(l.createTime).Format('yyyy-MM-dd hh:mm:ss'))+"</td>";
	                		 if(l.status=="0"){
	                			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>草稿</span></td>";
	                			 if(l.orgId!=curUser.orgId){
		                				html+=JY.Tags.setFunction1(l.id,permitBtn,"checkAuditing,");
		                			}else{
		                				html+=JY.Tags.setFunction1(l.id,permitBtn,"checkAuditing,");
		                			}
	                		 }else if(l.status=="A"){
	                			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>已审</span></td>";
	                			 if(l.orgId!=curUser.orgId){
		                				html+=JY.Tags.setFunction1(l.id,permitBtn,"checkAuditing,del,edit,checkAuditing,");
		                			}else{
		                				html+=JY.Tags.setFunction1(l.id,permitBtn,"checkAuditing,del,edit,");
		                			}
	                		 }else if(l.status=="B"){
	                			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>可售</span></td>";
	                			 if(l.orgId!=curUser.orgId){
		                				html+=JY.Tags.setFunction1(l.id,permitBtn,"checkAuditing,del,edit,checkAuditing,");
		                			}else{
		                				html+=JY.Tags.setFunction1(l.id,permitBtn,"checkAuditing,del,edit,");
		                			}		
	                		 }else if(l.status=="C"){
	                			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>锁定</span></td>";
	                			 if(l.orgId!=curUser.orgId){
		                				html+=JY.Tags.setFunction1(l.id,permitBtn,"checkAuditing,del,edit,checkAuditing,");
		                			}else{
		                				html+=JY.Tags.setFunction1(l.id,permitBtn,"checkAuditing,del,edit,");
		                			}
	                		 }else if(l.status=="D"){
	                			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>已售</span></td>";
	                			 if(l.orgId!=curUser.orgId){
		                				html+=JY.Tags.setFunction1(l.id,permitBtn,"checkAuditing,del,edit,checkAuditing,");
		                			}else{
		                				 html+=JY.Tags.setFunction1(l.id,permitBtn,"checkAuditing,del,edit,");
		                			}
	                			
	                		 }else if(l.status=="E"){
	                			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>销号</span></td>";
	                			 if(l.orgId!=curUser.orgId){
		                				html+=JY.Tags.setFunction1(l.id,permitBtn,"checkAuditing,del,edit,checkAuditing,");
		                			}else{
		                				html+=JY.Tags.setFunction1(l.id,permitBtn,"checkAuditing,del,edit,");
		                			}
	                		 }else if(l.status=="9") {
	                			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>删除</span></td>";
	                			 if(l.orgId!=curUser.orgId){
		                				html+=JY.Tags.setFunction1(l.id,permitBtn,"checkAuditing,del,edit,checkAuditing,");
		                			}else{
		                				html+=JY.Tags.setFunction1(l.id,permitBtn,"checkAuditing,del,edit,");
		                			}
	                		 }else if(l.status=="1"){
	                			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>待审</span></td>";
	                			 if(l.orgId!=curUser.orgId){
		                				html+=JY.Tags.setFunction1(l.id,permitBtn,"checkAuditing,del,edit,checkAuditing,");
		                			}else{
		                				html+=JY.Tags.setFunction1(l.id,permitBtn,"del,edit,");
		                			}
	                		 }else if(l.status=="F"){
	                			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>退厂</span></td>";
	                			 if(l.orgId!=curUser.orgId){
		                				html+=JY.Tags.setFunction1(l.id,permitBtn,"checkAuditing,del,edit,checkAuditing,");
		                			}else{
		                				html+=JY.Tags.setFunction1(l.id,permitBtn,"del,edit,checkAuditing,");
		                			}
	                		 }
	                	}
            		 html+="</tr>";	
        		 $("#productList tbody").append(html);
        		 JY.Page.setPage("productBaseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='13' class='center'>没有相关数据</td></tr>";
        		$("#productList tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
 	 
    	 JY.Model.loadingClose();
	 });
}
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
function viewInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
	divDisabled("home",true);
	divDisabled("productDiv",true);
}
function editInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
	divDisabled("home",false);
	divDisabled("productDiv",false);
}
function importInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
	divDisabled(attr.id,false);
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
	     	html: "<i class='icon-ok bigger-110'></i>&nbsp;保存为草稿",
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
	 divDisabled("home",false);
	 divDisabled("productDiv",false);
}
function AudingInfo(attr){
	$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"审核")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;不通过","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
}

function view(id){
	var _positive=false;
	cleanForm();
	checkImgs(id);
	document.getElementById("productAccessoriesAdd").style.display="none";
	pricing();
	JY.Ajax.doRequest(null,jypath +'/scm/product/find',{id:id},function(data){
		setForm(data);
		viewInfo({id:"productDiv",title:"查看商品",height:"700",width:"830"} );
	});
	getAccessories(id,_positive);
	getCredential(id,_positive);
}
function edit(id){
	var _positive=true;
	cleanForm();
	findImgList(id);
	pricing();
	JY.Ajax.doRequest(null,jypath +'/scm/product/find',{id:id},function(data){
		setForm(data);
		addInfo({
				id:"productDiv",
				title:"编辑商品",
				height:"700",
				width:"830",
				savefn:function(type){
					 if(JY.Validate.form("productInfoForm")){
						 var that =$(this);
						 JY.Ajax.doRequest("productInfoForm",jypath +'/scm/product/update',{status:type},function(data){
							 that.dialog("close");      
						     JY.Model.info(data.resMsg,function(){search();});
						 });
					 }	
				}	
		});
	});
	getAccessories(id,_positive);
	getCredential(id,_positive);
}

function del(id){
	JY.Model.confirm("确认删除吗？",function(){	
		JY.Ajax.doRequest(null,jypath +'/scm/product/delete',{id:id},function(data){
			JY.Model.info(data.resMsg,function(){search();});
		});
	});
}

function cleanForm(){
	JY.Tags.cleanForm("productInfoForm");
	JY.Tags.cleanForm("accessoriesDataForm");
	$("#accessoriesDataForm #stoneShape").val("");
	$("#productInfoForm input[name$='count']").val("1");
	//$("#productInfoForm select[name$='cCategoryId']").val("0");
	$("#productInfoForm select[name$='fCategoryId']").val("0");
	$("#productInfoForm select[name$='sCategoryId']").val("0");
	$("#productInfoForm select[name$='controlType']").val("0");
	$("#productAccessoriesTabe tbody").empty();
	$("#productCredentialTabe tbody").empty();
	cleanForm2();
	// 移除所有缩略图并将上传文件移出上传序列
	for (var i = 0; i < uploader.getFiles().length; i++) {
		// 将图片从上传序列移除
		uploader.removeFile(uploader.getFiles()[i]);
		uploader.removeFile(uploader.getFiles()[i], true);
		delete uploader.getFiles()[i];
		// 将图片从缩略图容器移除
		var $li = $('#' + uploader.getFiles()[i].id);
		$li.off().remove();
	}
	// 重置uploader，目前只重置了文件队列
	uploader.reset();
	$('#files').html("");
	cleanImgs();
	/*显示辅件添加按钮*/
	document.getElementById("productAccessoriesAdd").style.display="inline-block";
	/*切换选项卡*/
	 $('#dropdownAccessories').unbind('click');
 	 $('#dropdownCredential').unbind('click');
 	 /*显示第一个选项卡*/
	$('#myTab a:first').tab('show');
}

function setForm(data){
	JY.Tools.populateForm("productInfoForm",data.obj);
	$("#productInfoForm #stoneShape").prop("value",data.obj.stoneShape);
	$("#productInfoForm #queryGoldType").prop("value",data.obj.goldType);
	JY.Tags.isValid("productInfoForm",(data.obj.status=="9"?"0":"1"));
	$("#productInfoForm textarea[name$='remarks']").val(JY.Object.notEmpty(data.obj.remarks));
	if(data.obj.wageMode=="1"){
		$("#productInfoForm #wageMode2").prop("checked",true);
	}else{
		$("#productInfoForm #wageMode").prop("checked",true);
	}
	uploader.addButton({
		id : '#filePicker2',
		label : '点击选择图片' 
	});
	
}

function editPricing(id){
	var _positive=false;
	cleanForm();
	findImgList(id);
	document.getElementById("productAccessoriesAdd").style.display="none";
	JY.Ajax.doRequest(null,jypath +'/scm/product/find',{id:id},function(data){
		setForm(data);
		addInfo({
				id:"productDiv",
				title:"编辑商品",
				height:"700",
				width:"830",
				savefn:function(type){
					 if(JY.Validate.form("productInfoForm")){
						 var that =$(this);
						 JY.Ajax.doRequest("productInfoForm",jypath +'/scm/product/update',{status:type},function(data){
							 that.dialog("close");      
						     JY.Model.info(data.resMsg,function(){search();});
						 });
					 }	
				}	
		});
	});
	getAccessories(id,_positive);
	getCredential(id,_positive);
}

function pricing(){
	$.ajax({
		type:'POST',
		url:jypath +'/scm/product/query',
		data:{},
		dataType:'json',
		success:function(data,textStatus){ 
			if(data==1){
				document.getElementById("profileCB").style.display="inline-block";
			}else if(data==0){
				document.getElementById("profileCB").style.display="none";
			}
		}
	});
}

function checkAuditing(id){
	var _positive=false;
	cleanForm();
	checkImgs(id);
	document.getElementById("productAccessoriesAdd").style.display="none";
	pricing();
	JY.Ajax.doRequest(null,jypath +'/scm/product/find',{id:id},function(data){
		setForm(data);
		AudingInfo({id:"productDiv",title:"审核商品",height:"700",width:"830",
			savefn:function(){
				JY.Model.confirm("确认审核吗？",function(){
					JY.Ajax.doRequest(null,jypath +'/scm/product/Auditing',{id:id,status:"A"},function(data){
						JY.Model.info(data.resMsg,function(){
							$("#productDiv").dialog("close");
							search();
					});
				});
			});
			},
			cancelfn:function(){
				JY.Ajax.doRequest(null,jypath +'/scm/product/Auditing',{id:id,status:"0"},function(data){
					JY.Model.info(data.resMsg,function(){
						$("#productDiv").dialog("close");	
						search();
					});
				});
			}
		});
	});
	getAccessories(id,_positive);
	getCredential(id,_positive);

}

function selectOrg(){
	JY.Ajax.doRequest(null,jypath +'/backstage/org/role/getOrgTree',{},function(data){
		$.jy.dropTree.init({
			rootId:"productOrg",
			displayId:"productOrgName",
			data:data.obj,
			dataFormat:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'},
			isExpand:true,
			resetFn:function(){
				$("#productBaseForm #productOrgName").val('');
				$("#productBaseForm #productOrgId").val('');
			},
			clickFn:function(node){
				$("#productBaseForm #productOrgId").val(node.id);
			}
		});
		clearSelect("selectStatus",$("#optionArr").val());
	});
}

function findProinventory(id){
	var _positive=false;
	cleanForm();
	checkImgs(id);
	document.getElementById("productAccessoriesAdd").style.display="none";
	pricing();
	JY.Ajax.doRequest(null,jypath +'/scm/product/find',{id:id},function(data){
		setForm(data);
		viewInfo({id:"productDiv",title:"查看商品",height:"700",width:"830"});
	});
	getAccessories(id,_positive);
	getCredential(id,_positive);
}
function readonly(){
	$("#productInfoForm input[name='purchaseNum']").prop("readonly","readonly");
	$("#productInfoForm input[name='noticeno']").prop("readonly","readonly");
	$("#productInfoForm input[name='mouCode']").prop("readonly","readonly");
	$("#productInfoForm input[name='moudtlCode']").prop("readonly","readonly");
	$("#productInfoForm input[name='goldWeight']").prop("readonly","readonly");
	$("#productInfoForm input[name='fGoldWeight']").prop("readonly","readonly");
	$("#productInfoForm input[name='totalWeight']").prop("readonly","readonly");
	$("#productInfoForm input[name='length']").prop("readonly","readonly");
	$("#productInfoForm input[name='width']").prop("readonly","readonly");
	$("#productInfoForm input[name='height']").prop("readonly","readonly");
	
	$("#productInfoForm input[name='circel']").prop("readonly","readonly");
	$("#productInfoForm input[name='cerNum']").prop("readonly","readonly");
	$("#productInfoForm input[name='costCer']").prop("readonly","readonly");
	$("#productInfoForm input[name='serialNo']").prop("readonly","readonly");
	$("#productInfoForm input[name='epc']").prop("readonly","readonly");
	$("#productInfoForm textarea[name='remarks']").prop("readonly","readonly");
	$("#productInfoForm #labelType").prop("readonly","readonly");
	
	$("#productInfoForm #labelType").prop("readonly","readonly");
	$("#productInfoForm #cCategoryId").prop("readonly","readonly");
	$("#productInfoForm #sCategoryId").prop("readonly","readonly"); 
	$("#productInfoForm #queryGoldType").prop("readonly","readonly");
	$("#productInfoForm #jewelryMode").prop("readonly","readonly");
	$("#productInfoForm #cateId").prop("readonly","readonly");
	$("#productInfoForm #cateJewelryId").prop("readonly","readonly");
	$("#productInfoForm #queryFranchisee").prop("readonly","readonly");
	
}
function cleanBaseForm(){
	JY.Tags.cleanForm("productBaseForm");
	$("#productBaseForm input[name$='orgId']").val("");
}
