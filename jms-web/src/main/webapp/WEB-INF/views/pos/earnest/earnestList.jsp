<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
<%@include file="../../common/includeBaseSet.jsp" %>
<%@include file="../../common/includeSystemSet.jsp" %>
<script src="${jypath}/static/js/bootstrap/bootstrap3-typeahead.min.js"></script>


<link rel="stylesheet" href="${jypath}/static/plugins/zTree/3.5/zTreeStyle.css" />
<script type="text/javascript" src="${jypath}/static/js/system/jy/jy.component.js"></script>
<script src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.min.js"></script>
</head>
<body>
	<div class="page-content">
		<div class="row-fluid">
			<div class="col-xs-12">
				<div id="rightDiv" >
					<form id="earnestBaseForm" class="form-inline" method="POST" onsubmit="return false;">
						<div class="widget-main customBtn">	
							<span>定金单号：<input type="text"  name="orderNo" placeholder="输入定金单号" class="input-large" style="width: 150px;"></span>&nbsp;&nbsp;
							创建机构：<input id="orgInputOut"type="text" readonly jyValidate="required" value=""class="FormElement ui-widget-content ui-corner-all"onclick="showOrgCompOut(); return false;" /> <a href="#"title="清空" id="emptyOrgOut"onclick="emptyOrgCompOut(); return false;"class="lrspace3 aBtnNoTD" data-toggle="modal"><iclass='icon-remove bigger-120 red'></i></a>
							<div id='orgContentListOut' class="menuContent ztreeMC" style="display: none; position: absolute; z-index: 5555;"><ul id="selectOrgTreeOut" class="ztree preOrgTree"></ul></div>
							<input type="hidden" name="orgId"> <input type="hidden" name="outorgName"> 
							时间：<input type="text"id="dateRender1" name="createTime" readonly/>-<input type="text" id="dateRender2" name="updateTime" readonly/>
							<span>会员卡号：<input  type="text"  name="vipCode" placeholder="输入会员卡号" class="input-large" style="width: 150px;"></span>&nbsp;
							<span>客户姓名：<input  type="text"  name="customer" placeholder="输入客户姓名" class="input-large" style="width: 150px;"></span>&nbsp;
							<span id="selectisValid">状态：<select data-placeholder="状态" name="status" class="isSelect85"></select></span>&nbsp;
							<button id='searchBtn' class="btn btn-success  btn-xs" title="过滤" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>
							<button id='searchBtn' class="btn btn-success  btn-xs" title="重置" type="button" style="border-radius: 5px; " onclick="cleanBaseForm()">重置</button>
						</div>	
						<input type='hidden' class='pageNum' name='pageNum' value='1'/>
						<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
					</form>
					<div class="col-sm-4">
						<div class="dataTables_info customBtn" >
							<c:forEach var="pbtn" items="${permitBtn}">
								<button  id="${pbtn.btnId}"type="button" class="btn btn-success btn-xs" style="border-radius:5px;">${pbtn.name}</button>
								<%-- <a href="#" title="${pbtn.name}" id="${pbtn.btnId}" class="lrspace3" ><i class='${pbtn.icon} bigger-220'></i></a> --%>
							</c:forEach>
						</div>
					</div>
					<table id="earnestBaseTable" class="table table-striped table-bordered table-hover" >
						<thead>
							<tr>
								<th style="width:1%" class="center">
									<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
								</th>
								<th style="width:3%" class="center">序号</th>
								<th style="width:10%" class="center">定金单</th>
								<th style="width:6%"  class='center' >会员卡号</th>
								<th style="width:6%" class="center">客户姓名</th>
								<th style="width:10%" class="center">创建人</th>
								<th style="width:8%" class='center'>创建机构</th>	
								<th style="width:6%" class="center">创建时间</th>
								<th style="width:6%" class="center hidePrice">单类型</th>
								<th style="width:6%" class="center">状态</th>	
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
			</div>
		</div>
	</div>
	<%@include file="../../common/dialog.jsp" %>
	<%@include file="../earnest/earnestForm.jsp" %>
	<%@include file="../../pos/payments/paymentsList.jsp" %>
	<script src="${jypath}/static/js/pos/earnest/earnest.js"></script>	
</body>
</html>