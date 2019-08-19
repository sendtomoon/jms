<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
<%@include file="../../common/includeBaseSet.jsp" %>
<%@include file="../../common/includeSystemSet.jsp" %>
<script src="${jypath}/static/js/bootstrap/bootstrap3-typeahead.min.js"></script>
<script language="javascript" src="${jypath}/static/js/lodop/LodopFuncs.js"></script>
</head>
<body>
	<div class="page-content">
		<div class="row-fluid">
			<div class="col-xs-12">
					<div id="rightDiv">
						<form id="reportForm" class="form-inline" method="POST" onsubmit="return false;">
							<div class="widget-main customBtn">	
								<table>
									<tr>
										<td><span>质检单号：<input type="text"  name="reportNo" placeholder="请输入关键字" class="input-large" style="width: 150px;"></span>&nbsp;&nbsp;</td>
										<td><span>入库通知单：<input  type="text"  name="entryNo" placeholder="请输入关键字" class="input-large" style="width: 150px;"></span>&nbsp;&nbsp;</td>
										<td><span>供应商：<input  type="text"  name="supplierId" placeholder="请输入关键字" class="input-large" style="width: 150px;"></span>&nbsp;&nbsp;</td>
										<td><span id="selectisValid">状态：<select  data-placeholder="状态" name="status" class="isSelect75"></select></span>&nbsp;&nbsp;</td>
										<td>
											<!-- <button class="btn btn-warning  btn-xs" id="searchBtn" title="过滤" type="button" onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button> -->
											<button  id="searchBtn" class="btn btn-success  btn-xs" title="重置" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>
											<button id='searchBtn' class="btn btn-success  btn-xs" title="重置" type="button" style="border-radius: 5px; " onclick="cleanBaseForm()">重置</button>	
										</td>	
									</tr>
								</table>	
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
						<table id="reportTable" class="table table-striped table-bordered table-hover" >
							<thead>
								<tr>
									<th style="width:2%" class="center">
										<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
									</th>
									<th style="width:3%" class="center">序号</th>
									<th style="width:8%" class="center">质检单号</th>	
									<th style="width:8%" class='center hidden-480'>入库通知单</th>	
									<th style="width:6%"  class='center hidden-480'>质检人</th>
									<th style="width:5%"  class='center hidden-480'>质检数量</th>
									<th style="width:5%"  class='center hidden-480'>质检重量</th>
									<th style="width:5%" class="center">不合格数量</th> 
									<th style="width:5%" class="center">不合格重量</th>
									<th style="width:5%" class="center">供应商</th> 
									<th style="width:6%" class="center">备注</th>
									<th style="width:4%" class="center">状态</th>
									<th style="width:5%" class="center">创建人</th>
									<th style="width:8%" class="center">创建时间</th>
									<th style="width:5%" class="center">审核人</th>
									<th style="width:8%" class="center">审核时间</th>
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
				<%@include file="reportForm.jsp" %>
			</div>
		</div>
	</div>
	
	<div id="printDiv" class="hide">

    </div>
<script src="${jypath}/static/js/scm/report/report.js"></script>	
</body>