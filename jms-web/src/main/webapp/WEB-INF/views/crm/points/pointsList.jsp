<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
<%@include file="../../common/includeBaseSet.jsp" %>
<%@include file="../../common/includeSystemSet.jsp" %>
</head>
<body>
	<div class="page-content">
		<div class="row-fluid">
			<div class="col-xs-12">
				<div id="rightDiv" >
					<form id="pointsBaseForm" class="form-inline" method="POST" onsubmit="return false;">
						<div class="widget-main customBtn">	
							<span>会员名：<input type="text"  name="memberName" placeholder="输入会员名" class="input-large" style="width: 150px;"></span>&nbsp;&nbsp;
							<span>会员卡号：<input  type="text"  name="cardNo" placeholder="输入会员卡号" class="input-large" style="width: 150px;"></span>&nbsp;
							<span>会员手机：<input  type="text"  name="mobile" placeholder="输入会员手机" class="input-large" style="width: 150px;"></span>&nbsp;
							<span id="selectisValid">状态：<select data-placeholder="状态" name="type" class="isSelect85"></select></span>&nbsp;
							时间：<input type="text"id="dateRender1" name="createTime" readonly/>-<input type="text" id="dateRender2" name="updateTime" readonly/>
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
					<table id="pointsBaseTable" class="table table-striped table-bordered table-hover" >
						<thead>
							<tr>
								<th style="width:1%" class="center">
									<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
								</th>
								<th style="width:3%" class="center">序号</th>
								<th style="width:6%" class="center">会员名</th>
								<th style="width:6%" class="center">系统</th>
								<th style="width:6%" class="center">模块</th>
								<th style="width:6%" class="center">积分来源</th>
								<th style="width:6%" class="center">明细类型</th>
								<th style="width:6%" class="center">积分</th>
								<th style="width:6%" class="center">创建时间</th>
								<th style="width:8%" class='center'>创建人</th>	
								<th style="width:8%" class='center'>创建ip</th>	
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
	<script src="${jypath}/static/js/crm/points/points.js"></script>	
</body>
</html>