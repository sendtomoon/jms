<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
<%@include file="../../common/includeBaseSet.jsp" %>
<%@include file="../../common/includeSystemSet.jsp" %>
<link rel="stylesheet" href="${jypath}/static/plugins/zTree/3.5/zTreeStyle.css" />
<script type="text/javascript" src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>

<!-- 自动补全 -->
<script src="${jypath}/static/js/bootstrap/bootstrap3-typeahead.min.js"></script>

<!-- 附件上传 -->
<link rel="stylesheet" type="text/css" href="${jypath}/static/plugins/webuploader/css/webuploader.css" />
<link rel="stylesheet" type="text/css" href="${jypath}/static/plugins/webuploader/image-upload/style.css" />
<script type="text/javascript" src="${jypath}/static/plugins/webuploader/js/webuploader.nolog.min.js"></script> 


</head>
<body>
	<div class="page-content">
		<div class="row-fluid">
			<div class="col-xs-12">
				<div class="">
					<div class="col-sm-3">
						<div class="widget-box"><ul id="categoryTree" class="ztree orgTree"></ul></div>
						
						<div class="dataTables_info customBtn">
							<!-- 
							<a class="lrspace3" id="addCgyBtn" title="增加类别" href="#"><i class="icon-home color-green bigger-240"></i></a>
							<a class="lrspace3" id="editCgyBtn" title="修改类别" href="#"><i class="icon-edit color-blue bigger-240"></i></a>
							<a class="lrspace3" id="delCgyBtn" title="删除类别" href="#"><i class="icon-remove-sign color-red bigger-240"></i></a>
							 -->
							<button  id="addCgyBtn" type="button" class="btn btn-success btn-xs" style="border-radius:4px" >增加类别</button>
							<button  id="editCgyBtn" type="button" class="btn btn-success btn-xs" style="border-radius:4px" >修改类别</button>
							<button  id="delCgyBtn" type="button" class="btn btn-success btn-xs" style="border-radius:4px" >删除类别</button>
						</div>	
						
					</div>	
					<div class="col-sm-9">	
						<div class="row page-header">
							<h1>&nbsp;<span id='orgName1'></span>&nbsp;<small> <i class="icon-double-angle-right"></i> <span id='orgName2'></span> </small></h1>
						</div>
						<form id="baseForm" class="form-inline" method="POST" onsubmit="return false;">
							<div class="widget-main customBtn">	
								<input  type="text"  name="keyWord" placeholder="这里输入关键词" class="input-large">
								&nbsp;&nbsp;<span id="selectisValid"><label></label>：<select name="status" data-placeholder="状态"  class="isSelect75"></select></span>														
<!-- 								&nbsp;&nbsp;<button id="searchBtn" class="btn btn-warning  btn-xs" title="过滤" type="button" onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button>		 -->
								<button id='searchBtn' class="btn btn-success  btn-xs" title="过滤" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>
							</div>			
							<input type='hidden' name='categoryid' />
							<input type='hidden' class='pageNum' name='pageNum' value='1'/>
							<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
							<input type='hidden' id="menuId" name='menuId' value='${menuId}'/>
							<input type='hidden' id="menuPId" name='menuPId' value='${menuPId}'/>
						</form>
						<c:forEach var="pbtn" items="${permitBtn}">
							<button  id="${pbtn.btnId}" type="button" class="btn btn-success btn-xs" style="border-radius:4px" >${pbtn.name}</button>
						</c:forEach>
						<table id="baseTable" class="table table-striped table-bordered table-hover" >
							<thead>
								<tr>
									<th style="width:3%" class="center">
										<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
									</th>
									<th style="width:7%"  class="center hidden-480">序号</th>
									<th style="width:15%" class="center">款号</th>
									<th style="width:15%" class="center">名称</th>
									<th style="width:10%"  class="center hidden-480">状态</th>
									<th style="width:33%" class="center hidden-480">描述</th>
									<th style="width:20%" class="center">操作</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
						<div class="row">
							<div class="col-sm-8 col-sm-offset-4">
								<!--设置分页位置-->
								<div id="pageing" class="dataTables_paginate paging_bootstrap">
									<ul class="pagination"></ul>
								</div>
							</div>
						</div>
					</div>	
				</div>	
			<!-- #addorUpdateFrom -->
			<%@include file="moudleForm.jsp" %>
			<%@include file="detailList.jsp" %>
			<%@include file="detailForm.jsp" %>
			<!-- #dialog-confirm -->
			<%@include file="../../common/dialog.jsp" %>
			</div>
		</div>
	</div>
<script src="${jypath}/static/js/scm/moudle/moudle.js"></script>
<!--  附件 -->
<script type="text/javascript" src="${jypath}/static/js/system/jy/attachmentUploadMore.js"></script>


</body>
</html>