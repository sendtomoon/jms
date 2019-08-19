
function OrgData(orgInputId,formId,code){

	JY.Ajax.doRequest(null,jypath +'/backstage/district/getDistrictTree',{status:"1"},function(data){
		//设置
		$.fn.zTree.init($("#selectorgTree"),{view:{dblClickExpand:false,selectedMulti:false,nameIsHTML:true},
			data:{
				simpleData:{
					enable: true,
					idKey: "id",
					pIdKey: "pid",
					rootPId: '0'
				}},
			callback:{onClick:clickPreOrg}},data.obj);
		function clickPreOrg(event, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("selectorgTree"),
			nodes = zTree.getSelectedNodes(),v ="",n ="";	
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";//获取name值
				n += nodes[i].id + ",";	//获取id值
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (n.length > 0 ) n = n.substring(0, n.length-1);
			$("#"+orgInputId).prop("value",v);
			$("#"+formId+" input[name$="+code+"]").prop("value",n);
			//因为单选选择后直接关闭，如果多选请另外写关闭方法
			hideOrgComp();
		}
	});
	//下拉窗口是否显示
	$("#"+orgInputId).on('click',function(){
		$("#orgList").mouseleave(function(){
			  hideOrgComp();
		});
		var preisShow=false;
		if(preisShow){
			hideOrgComp();
		}else{
			var obj = $("#"+orgInputId);
			var offpos = $("#"+orgInputId).position();
			$("#orgList").css({left:offpos.left+"px",top:offpos.top+obj.heith+"px"}).slideDown("fast");	
			preisShow=true;
		}
	});
	$("#empt").on('click',function(){
		$("#"+orgInputId).prop("value","");
		$("#"+formId+" input[name$="+code+"]").prop("value","");
	});
}

//隐藏树控件
function hideOrgComp(orgContentId){
	$("#orgList").fadeOut("fast");
	preisShow=false;
}



