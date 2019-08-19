
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html lang="en">
<head>
<%@include file="../../common/includeBaseSet.jsp"%>
<%@include file="../../common/includeSystemSet.jsp"%>

<link rel="stylesheet"
	href="${jypath}/static/plugins/zTree/3.5/zTreeStyle.css" />
<script type="text/javascript"
	src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript"
	src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>


</head>
<body>
	<div class="page-content">
		<div class="row-fluid">
			<div class="col-xs-12">
				<div id="rightDiv">
					<form id="tradeHisForm" class="form-inline" method="POST"
						onsubmit="return false;">
						<div class="widget-main customBtn">
							<span>条码：</span><input type="text" name="code" placeholder="这里输入条码" class="input">&nbsp;
							<span>订单号：</span><input type="text" name="tradeorder" placeholder="这里输入订单号"
								class="input">&nbsp;&nbsp; 入库单位：<input id="orgInput"
								type="text" readonly jyValidate="required" value=""
								class="FormElement ui-widget-content ui-corner-all"
								onclick="showOrgComp(); return false;" /> <a href="#" title="清空"
								id="emptyOrg" onclick="emptyOrgComp(); return false;"
								class="lrspace3 aBtnNoTD" data-toggle="modal"><i
								class='icon-remove bigger-120 red'></i></a>
							<div id='orgContentList' class="menuContent ztreeMC"
								style="display: none; position: absolute; z-index: 5555;">
								<ul id="selectOrgTree" class="ztree preOrgTree"></ul>
							</div>
							<input type="hidden" name="inorgid"> <input type="hidden"
								name="inorgName"> 出库单位：<input id="orgInputOut"
								type="text" readonly jyValidate="required" value=""
								class="FormElement ui-widget-content ui-corner-all"
								onclick="showOrgCompOut(); return false;" /> <a href="#"
								title="清空" id="emptyOrgOut"
								onclick="emptyOrgCompOut(); return false;"
								class="lrspace3 aBtnNoTD" data-toggle="modal"><i
								class='icon-remove bigger-120 red'></i></a>
							<div id='orgContentListOut' class="menuContent ztreeMC"
								style="display: none; position: absolute; z-index: 5555;">
								<ul id="selectOrgTreeOut" class="ztree preOrgTree"></ul>
							</div>
							<input type="hidden" name="outorgid"> <input
								type="hidden" name="outorgName"> 时间：<input type="text"
								id="dateRender1" name="createTime"/>-<input
								type="text" id="dateRender2" name="updateTime"/>
							<span id="selectisValid">交易类型：<select
								data-placeholder="交易类型" name="type" class="isSelect85"></select></span>&nbsp;
							<button id='searchBtn' class="btn btn-success  btn-xs" title="过滤" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>
							<button id='searchBtn' class="btn btn-success  btn-xs" title="重置" type="button" style="border-radius: 5px; " onclick="cleanBaseForm()">重置</button>
						</div>
						<input type='hidden' class='pageNum' name='pageNum' value='1' /> <input
							type='hidden' class='pageSize' name='pageSize' value='10' />
					</form>
					<table id="tradeHisTable"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th style="width: 1%" class="center"><label><input
										type="checkbox" class="ace"><span class="lbl"></span></label>
								</th>
								<th style="width: 3%" class="center">序号</th>
								<th style="width: 8%" class="center">条码</th>
								<th style="width: 8%" class="center">商品名称</th>
								<th style="width: 6%" class='center'>交易类型</th>
								<th style="width: 8%" class='center'>订单号</th>
								<!-- <th style="width: 6%" class="center">数量</th>
								<th style="width: 6%" class="center">重量</th> -->
								<th style="width:5%" class="center">批发价</th>
								<th style="width:5%" class="center">采购成本</th>
								<th style="width:5%" class="center">核价成本</th>
								<th style="width:5%" class="center">财务成本</th>
								<th style="width: 8%" class="center">拨入单位</th>
								<!-- <th style="width: 8%" class="center">拨入仓库</th> -->
								<th style="width: 8%" class="center">拨出单位</th>
								<!-- <th style="width: 8%" class="center">拨出仓库</th> -->
								<th style="width: 8%" class="center">时间</th>
								<th style="width: 4%" class="center">操作</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
					<div class="row">
						<div class="col-sm-4">
							<div class="dataTables_info customBtn">
								<c:forEach var="pbtn" items="${permitBtn}">
									<a href="#" title="${pbtn.name}" id="${pbtn.btnId}"
										class="lrspace3"><i class='${pbtn.icon} bigger-220'></i></a>
								</c:forEach>
							</div>
						</div>
						<div class="col-sm-8">
							<!--设置分页位置-->
							<div id="pageing" class="dataTables_paginate paging_bootstrap">
								<ul class="pagination"></ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="tradeHisForm.jsp"%>
		<%@include file="../../common/dialog.jsp"%>
		<script src="${jypath}/static/js/scm/history/tradeHis.js"></script>
</body>