<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html lang="en">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head>
<%@include file="../../common/includeBaseSet.jsp"%>
<%@include file="../../common/includeSystemSet.jsp"%>
<script src="${jypath}/static/js/bootstrap/bootstrap3-typeahead.min.js"></script>

<link rel="stylesheet" href="${jypath}/static/plugins/zTree/3.5/zTreeStyle.css" />
<script type="text/javascript" src="${jypath}/static/js/system/jy/jy.component.js"></script>
<script src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.min.js"></script>
<link rel="stylesheet" type="text/css" href="${jypath}/static/plugins/webuploader/css/webuploader.css" />
<link rel="stylesheet" type="text/css" href="${jypath}/static/plugins/webuploader/image-upload/style.css" />
<link rel="stylesheet" href="${jypath}/static/plugins/zTree/3.5/zTreeStyle.css" />
<script type="text/javascript" src="${jypath}/static/plugins/webuploader/js/webuploader.nolog.min.js"></script> 
<script type="text/javascript" src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>

</head>
<body>
	<div class="row-fluid">
		<div class="col-xs-12">
		<div id="rightDiv">
		    <form id="handoverBillFrom" class="form-inline" method="POST" onsubmit="return false;">
		        <div class="widget-main">
		                    <span>交班单号：<input type="text" name="orderNo" placeholder="请输入关键字" /></span>
		                    <span>时间：<input type="text" id="dateBegin" name="startTime" value="2017/01/01" placeholder="请输入日期"/> — <input type="text" id="dateEnd" name="endTime" value="2017/12/31" placeholder="请输入日期"/></span>
		                    <!-- <span><div id="handoverOrg"  style="float:left;">创建机构： <input id="handoverOrgName" type="text" jyValidate="required" placeholder="请选组织机构" readonly/><input id="handoverOrgId" name="orgId" type="hidden"/></div></span> -->
		                    <span>交班人：<input type="text" name="handerName" placeholder="请输入关键字"/></span>
		                    <span>接班人：<input type="text" name="receiverName" placeholder="请输入关键字"/></span>
		                    <span id="selectHandoverStatus"><label></label>：<select data-placeholder="状态" name="status" class="isSelect75"></select></span>
		                    <span>
								<button id='searchBtn' class="btn btn-success  btn-xs" title="过滤" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>
							    <button id='searchBtn' class="btn btn-success  btn-xs" title="重置" type="button" style="border-radius: 5px; " onclick="cleanBaseForm()">重置</button>
							</span>
		        </div>
		        <input type='hidden' class='pageNum' name='pageNum' value='1' />
				<input type='hidden' class='pageSize' name='pageSize' value='10' />
		    </form>
		</div>
		<div class="row">
			<div style="z-index: 1;">
				<div class="dataTables_info customBtn" style="z-index: 1; margin-left: 30px;">
					<c:forEach var="pbtn" items="${permitBtn}">
					    <button id="${pbtn.btnId}" type="button" class="btn btn-success btn-xs" style="border-radius: 5px; margin-bottom: 5px;">${pbtn.name}</button>
					</c:forEach>
				</div>
			</div>
		</div>
		<table id="handoverList" align="center" class="table table-striped table-bordered table-hover">
			<thead>
			<tr>
			    <th style="width: 3%" class="center"><label><input type="checkbox" class="ace"><span class="lbl"></span></label></th>
			    <th style="width: 3%" class="center hidden-480">序号</th>
			    <th style="width: 5%" class="center hidden-480">交班单号</th>
			    <th style="width: 5%" class="center hidden-480">创建机构</th>
			    <th style="width: 5%" class="center hidden-480">创建时间</th>
			    <th style="width: 3%" class="center hidden-480">状态</th>
			    <th style="width: 3%" class="center hidden-480">交班人</th>
			    <th style="width: 3%" class="center hidden-480">接班人</th>
			    <th style="width: 3%" class="center hidden-480">交件数</th>
			    <th style="width: 3%" class="center hidden-480">接件数</th>
			    <th style="width: 3%" class="center hidden-480">交重量</th>
			    <th style="width: 3%" class="center hidden-480">接重量</th>
			    <th style="width: 3%" class="center hidden-480">交金额</th>
			    <th style="width: 3%" class="center hidden-480">接金额</th>
			</tr>
			</thead>
			<tbody></tbody>
		</table>
		<div class="row">
			<div class="col-sm-13">
				<div id="pageing" class="dataTables_paginate paging_bootstrap">
					<ul class="pagination"></ul>
				</div>
			</div>
		 </div>
	</div>
</div>
<%@include file="handoverForm.jsp" %>
<%@include file="../../common/dialog.jsp" %>
<script src="${jypath}/static/js/pos/handover/handover.js"></script>
</body>
</html>










