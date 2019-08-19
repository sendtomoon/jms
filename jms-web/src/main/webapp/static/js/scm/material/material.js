$(function(){
	//字典
	JY.Dict.setSelect("typeForm","SCM_DATA_MATERIALTYPE",1);
	JY.Dict.setSelect("feeTypeForm","SCMWAGEMOD",1);
	JY.Dict.setSelect("colorForm","SCM_DATA_COLOR",1);
	JY.Dict.setSelect("cutForm","SCM_DATA_CUT",1);
	JY.Dict.setSelect("clartityForm","SCM_DATA_CLARITY",1);
	JY.Dict.setSelect("polishForm","SCM_DATA_POLISH",1);
	JY.Dict.setSelect("symmetyForm","SCM_DATA_SYMMETRY",1);
	JY.Dict.setSelect("fluoreScenceForm","SCM_DATA_FLUORESCENCE",1);
	JY.Dict.setSelect("pwidthForm","SCM_DATA_TableProportion ",1);
	JY.Dict.setSelect("pdeepForm","SCM_DATA_TotalDepthProportion ",1);
	JY.Dict.setSelect("stoneShapeForm","stoneShape",1);    
	initOrgSelectData();	
	//下拉框
	JY.Dict.setSelect("selectisValid","status",2,"全部");	
	//增加回车事件
	$("#MaterialauForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});
	setBaseData();
	getbaseList();
	
	$("#dateBegin").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd',
		changeYear: true,
		changeMonth:true,
		onSelect:function(dateText, inst) { }
	});
	$("#dateEnd").datepicker({
		showOn: "button",
		buttonImage: jypath+"/static/images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'yy/mm/dd',
		changeYear: true,
		changeMonth:true,
		onSelect:function(dateText, inst) { }
	});
	
	//自动补全
	var input={};
	$("#moudleCode").typeahead({
		source:function(query,process){
			$.ajax({	
				type:'POST',
				url:jypath+'/scm/material/findCode',
				data:{code:query},
				dataType:'json',
				success:function(data,textStatus){
						input={};
						var array=[];
						 $.each(data.obj, function (index, ele) {
							 input[ele.moudleCode] = ele.id;
	                         array.push(ele.moudleCode);
	                     });
						 process(array);
				}
			});
		},
	    items: 20,
	    afterSelect: function (item) {
	    	$("#ScmaterialForm input[name$='id']").val(input[item]);
	    },
	    delay: 500,
	    highlighter: function (item) {//item是用户输入
	        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')//这是将字符串安全格式化为正则表达式的源码
	            return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	                return '<strong style="color:red;">' + match + '</strong>'//将下拉列表中符合项中的LI的innerHTML相等部分加个strong标签
	            })
	        	        }
	    
		
	});
	//新加
	$('#addBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		cleanForm();	
		$("#ScmaterialForm input[name$='code']").prop("readonly",true);
		editInfo("MaterialDiv","新增",function(){
			if(JY.Validate.form("ScmaterialForm")){
				var that =$(this);
				JY.Ajax.doRequest("ScmaterialForm",jypath +'/scm/material/add',null,function(data){
					     that.dialog("close");      
					     JY.Model.info(data.resMsg,function(){search();});
				    });
			}	
		});
	});
	
	//查看
	$('#check').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#MaterialTable input[name="ids"]:checked').each(function(){    
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
		$('#MaterialTable input[name="ids"]:checked').each(function(){    
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
	
	//删除
	$('#del').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作		
		e.preventDefault();
		var chks =[];    
		$('#MaterialTable input[name="ids"]:checked').each(function(){    
			chks.push($(this).val()); 
		});     
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!"); 
		}else{	
			JY.Model.confirm("确认删除吗？",function(){	
				JY.Ajax.doRequest(null,jypath +'/scm/material/del',{cheks:chks.toString()},function(data){
						JY.Model.info(data.resMsg,function(){search();});
				});
			});
		}
	});
});
function cleanBaseForm(){
	JY.Tags.cleanForm("MateriaaulForm");
	$("#MateriaaulForm input[name$='orgId']").val("");
	setBaseData();
}
function setBaseData(){
	var date=new Date;
	var year=date.getFullYear(); 
	$("#dateBegin").val(year+"/1/1")
	$("#dateEnd").val(year+"/12/31")
}
//分页查询
function getbaseList(){
	JY.Model.loading();
	JY.Ajax.doRequest("MateriaaulForm",jypath +'/scm/material/findByPage',null,function(data){
		 $("#MaterialTable tbody").empty();
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
            		 html+="<td class='center'><a style='cursor:pointer;' onclick='check(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.code)+"</a></td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.name)+"</td>";
            		 if(l.type=='0') html+="<td class='center hidden-480'><span class='label label-sm label-success'>原料</span></td>";
            		 else if(l.type == '1') html+="<td class='center hidden-480'><span class='label label-sm label-success'>辅料</span></td>";
            		 else if(l.type=='3')html+="<td class='center hidden-480'><span class='label label-sm label-success'>易耗品</span></td>";  		 
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.moudleCode)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.color)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.clartity)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.cut)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(new Date(l.createTime).Format('yyyy-MM-dd hh:mm:ss'))+"</td>";
            		 if(l.status=='1'){
            			 html+="<td class='center hidden-480'><span class='label label-sm label-success'>有效</span></td>";
            		 }else if(l.status == '0'){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>失效</span></td>";
            		 }else if(l.status=='9'){
            			 html+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>删除</span></td>";
            		 }
            		 html+="</tr>";		 
            	 } 
        		 $("#MaterialTable tbody").append(html);
        		 JY.Page.setPage("MateriaaulForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
        		$("#MaterialTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
 	 
    	 JY.Model.loadingClose();
	 });
}


//查看按钮
function check(id){
	cleanForm();
		JY.Ajax.doRequest(null,jypath +'/scm/material/find',{id:id},function(data){ 
			viewInfo("MaterialDiv");
			setForm(data);
			JY.Tools.formDisabled("ScmaterialForm",true,function(){
				$("#ScmaterialForm .icon-remove").hide();
				$("#ScmaterialForm .ui-datepicker-trigger").hide();
			});
		});
}

//修改按钮
function edit(id){
	cleanForm();
	JY.Ajax.doRequest(null,jypath +'/scm/material/find',{id:id},function(data){ 
		setForm(data); 
	    $("#ScmaterialForm input[name$='code']").prop("readonly",true);
	    editInfo("MaterialDiv","修改",function(){
	    	if(JY.Validate.form("ScmaterialForm")){
				var that =$(this);
				JY.Ajax.doRequest("ScmaterialForm",jypath +'/scm/material/update',null,function(data){
				    that.dialog("close");
				    JY.Model.info(data.resMsg,function(){
				    	search();
				    });	
				});
			}	
	    });
	});
}
//清空页面
function cleanForm(){
	JY.Tags.cleanForm("ScmaterialForm");
	$("#ScmaterialForm input[name$='price']").val(0.0);
	JY.Tools.formDisabled("ScmaterialForm",false,function(){
		$("#ScmaterialForm .icon-remove").show();
		$("#ScmaterialForm .ui-datepicker-trigger").show();
	});
}

//加载查看页面
function viewInfo(id,title,fn){	
	$("#"+id).removeClass('hide').dialog({resizable:false,height:530,width:680,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(fn) == 'function'){fn.call(this);}}}]});
}
//加载修改页面
function editInfo(id,title,savefn,cancelfn){
	$("#"+id).removeClass('hide').dialog({resizable:false,height:530,width:680,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(title)?title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(savefn) == 'function'){savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(cancelfn) == 'function'){cancelfn.call(this);}}}]});
}






//set赋值
function setForm(data){
	var l=data.obj;
	$("#ScmaterialForm input[name$='id']").val(JY.Object.notEmpty(l.id));
	$("#ScmaterialForm input[name$='code']").val(JY.Object.notEmpty(l.code));
	$("#ScmaterialForm input[name$='name']").val(JY.Object.notEmpty(l.name));
	JY.Tags.isValid("ScmaterialForm",(JY.Object.notNull(l.status)?l.status:"1"));
	$("#ScmaterialForm #type").val(JY.Object.notEmpty(l.type));
	$("#ScmaterialForm input[name$='status']").val(JY.Object.notEmpty(l.status));
	$("#ScmaterialForm input[name$='moudleCode']").val(JY.Object.notEmpty(l.moudleCode));
	$("#ScmaterialForm input[name$='cateId']").val(JY.Object.notEmpty(l.cateId));
	if(data.obj.feeType=="1"){
		$("#ScmaterialForm #feeType").prop("checked",true);
	}else{
		$("#ScmaterialForm #feeType2").prop("checked",true);
	}
	$("#ScmaterialForm #color").val(JY.Object.notEmpty(l.color));
	$("#ScmaterialForm #cut").val(JY.Object.notEmpty(l.cut));
	$("#ScmaterialForm #clartity").val(JY.Object.notEmpty(l.clartity));
	$("#ScmaterialForm input[name$='cerNum']").val(JY.Object.notEmpty(l.cerNum));
	$("#ScmaterialForm #stoneShape").val(JY.Object.notEmpty(l.stoneShape));
	$("#ScmaterialForm #polish").val(JY.Object.notEmpty(l.polish));
	$("#ScmaterialForm #symmety").val(JY.Object.notEmpty(l.symmety));
	$("#ScmaterialForm #fluoreScence").val(JY.Object.notEmpty(l.fluoreScence));
	$("#ScmaterialForm input[name$='materialSize']").val(JY.Object.notEmpty(l.materialSize));
	$("#ScmaterialForm input[name$='batchNum']").val(JY.Object.notEmpty(l.batchNum));
	$("#ScmaterialForm input[name$='price']").val(JY.Object.notEmpty(l.price));
	$("#ScmaterialForm textarea[name$='remarks']").val(JY.Object.notEmpty(l.remarks));
	$("#ScmaterialForm textarea[name$='description;']").val(JY.Object.notEmpty(l.description));    
	$("#ScmaterialForm input[name$='pdeep']").val(JY.Object.notEmpty(l.pdeep)); 
	$("#ScmaterialForm input[name$='pwidth']").val(JY.Object.notEmpty(l.pwidth));
	$("#ScmaterialForm input[name$='cateId']").prop("value",JY.Object.notEmpty(l.cateId));
	$("#cateInput").prop("value",JY.Object.notEmpty(l.cateName));
}

function search(){
	$("#searchBtn").trigger("click");
}


/*******原材料组件：选择组织机构 start*******/
var preisShow=false;//窗口是否显示
function showOrgComp() {
	if(preisShow){
		hideOrgComp();
	}else{
		var obj = $("#cateInput");
		var offpos = $("#cateInput").position();
		$("#categoryContentListDiv").css({left:offpos.left+"px",top:offpos.top+obj.heith+"px"}).slideDown("fast");	
		preisShow=true;
	}
}
function hideOrgComp(){
	$("#categoryContentListDiv").fadeOut("fast");
	preisShow=false;
}
function emptyOrgComp(){
	$("#cateInput").prop("value","");
	$("#ScmaterialForm input[name$='pId']").prop("value","");
}
function initOrgSelectData(){
	JY.Ajax.doRequest(null,jypath +'/scm/moudle/getCgyTree',null,function(data){
		//设置
		$.fn.zTree.init($("#selectTree"),{view:{dblClickExpand:false,selectedMulti:false,nameIsHTML:true},data:{simpleData:{enable: true}},callback:{onClick:clickPreOrg}},data.obj);
	});
}
function clickPreOrg(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("selectTree"),
	nodes = zTree.getSelectedNodes(),v ="",n ="";	
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";//获取name值
		n += nodes[i].id + ",";	//获取id值
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (n.length > 0 ) n = n.substring(0, n.length-1);
	$("#cateInput").prop("value",v);
	$("#ScmaterialForm input[name$='cateId']").prop("value",n);
	$("#ScmaterialForm input[name$='cateName']").prop("value",v);
	//因为单选选择后直接关闭，如果多选请另外写关闭方法
	hideOrgComp();
}
/*******组件：选择组织机构 end*******/



