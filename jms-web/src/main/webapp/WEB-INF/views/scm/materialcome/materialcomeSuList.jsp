<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
<%@include file="../../common/includeBaseSet.jsp" %>
<%@include file="../../common/includeSystemSet.jsp" %>
<script language="javascript" src="${jypath}/static/js/lodop/LodopFuncs.js"></script>
<script src="${jypath}/static/js/bootstrap/bootstrap3-typeahead.min.js"></script>
<link rel="stylesheet" type="text/css" href="${jypath}/static/plugins/webuploader/css/webuploader.css" />
<link rel="stylesheet" type="text/css" href="${jypath}/static/plugins/webuploader/image-upload/style.css" />
<link rel="stylesheet" href="${jypath}/static/plugins/zTree/3.5/zTreeStyle.css" />
<script type="text/javascript" src="${jypath}/static/plugins/webuploader/js/webuploader.nolog.min.js"></script> 
<script type="text/javascript" src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>
<script language="javascript" src="${jypath}/static/js/lodop/LodopFuncs.js"></script>
</head>
<body>


	<div class="page-content">
		<div class="row-fluid">
			<div class="col-xs-12">
				<div id="rightDiv" >
					<form id="materialcomeBaseForm" class="form-inline" method="POST" onsubmit="return false;">
						<input type="hidden" name="flag" id="flag" value="${flag}"/>
						<div class="widget-main customBtn">	
							<span>入库通知单：</span><input  type="text"  name="noticeNo" placeholder="请输入关键词" style="width:150px;">&nbsp;&nbsp;
							<span>采购单号：</span><input type="text" name="purchaseNo" placeholder="请输入关键字" style="width:150px;" >&nbsp;&nbsp;
							<span id="selectMaterialcomeStatus">状态：<select  data-placeholder="状态" name="status" class="isSelect75"></select></span>&nbsp;&nbsp;
							<button id='searchBtn' class="btn btn-success  btn-xs" title="过滤" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>
<!-- 							<button class="btn btn-warning  btn-xs" id="searchBtn" title="过滤" type="button" onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button>		 -->
						</div>	
						<input type='hidden' class='pageNum' name='pageNum' value='1'/>
						<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
					</form>
						<div>
							<div class="dataTables_info customBtn" >
								<c:forEach var="pbtn" items="${permitBtn}">
<%-- 									<button  id="${pbtn.btnId}"type="button" class="btn btn-success btn-xs" style="border-radius:5px;">${pbtn.name}</button> --%>
										<a href="#" id="${pbtn.btnId}" type="button" class="btn btn-success btn-xs" style="border-radius:5px;">${pbtn.name}</a>
<%-- 									<a href="#" title="${pbtn.name}" id="${pbtn.btnId}" class="lrspace3" ><i class='${pbtn.icon} bigger-220'></i></a> --%>
								</c:forEach>
<!-- 								<a href="#" title="打印" id="testPrint" class="lrspace3" ><i class='icon-print bigger-220'></i></a> -->
<!-- <a class="lrspace3 aBtnNotd" title="打印" href="#" -->
<!-- 					id="print"> <i -->
<!-- 					class="icon-trash color-red bigger-180"></i> -->
<!-- 				</a> -->
							</div>
						</div>
					<table id="materialcomeTable" class="table table-striped table-bordered table-hover" >
						<thead>
							<tr>
								<th style="width:1%" class="center">
									<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
								</th>
								<th style="width:3%" class="center">序号</th>
								<th style="width:6%" class='center'>入库通知单</th>		
								<th style="width:6%"  class='center'>采购单号</th>
								<th style="width:6%" class="center">单位名称</th>
								<th style="width:3%" class="center">数量</th>
								<th style="width:3%" class="center">重量</th>
								<th style="width:5%" class="center">基础工费</th>
								<th style="width:5%" class="center">附加工费</th>
								<th style="width:5%" class="center">其它工费</th>
								<th style="width:5%" class="center">采购成本</th>
								<th style="width:5%" class="center">供应商</th>
								<th style="width:5%" class="center">经办人</th>
								<th style="width:5%" class="center">收货人</th>
								<th style="width:3%" class="center">状态</th>
								<th style="width:5%" class="center">创建人</th>
								<th style="width:10%" class="center">创建时间</th>
								<th style="width:5%" class="center">审核人</th>
								<th style="width:10%" class="center">审核时间</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>  
					<div class="row">
						<div class="col-sm-13">
							<!--设置分页位置-->
							<div id="pageing" class="dataTables_paginate paging_bootstrap">
								<ul class="pagination"></ul>
							</div>
						</div>
				   </div>
				<%@include file="../../common/dialog.jsp" %>
				<%@include file="../materialcome/materialcomeSuForm.jsp" %>
			</div>
		</div>
	</div>
<script src="${jypath}/static/js/system/jy/jy.component.js"></script>
<script src="${jypath}/static/js/scm/materialcome/materialcomeSu.js"></script>
<script src="${jypath}/static/js/jquery/jquery.form.js"></script>
</body>