<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
<%@include file="../../common/includeBaseSet.jsp" %>
<%@include file="../../common/includeSystemSet.jsp" %>
<link rel="stylesheet" href="${jypath}/static/plugins/zTree/3.5/zTreeStyle.css" />
<script src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.min.js"></script>
</head>
<body>
<div class="page-content">
	<div class="row-fluid">	
		<div class="col-xs-12">
			<div class="">	
				<div class="col-sm-3">
					<div class="widget-box">
						<ul id="districtorgTree" class="ztree orgTree"></ul>
					</div>
				</div>
				<div class="col-sm-9">
				<form id="districtListForm" class="form-inline" method="POST" onsubmit="return false;">
					<div class="row">
						<div class="widget-main" style="margin-left: 18px;">	
							<span id="selectisValid"><label>状态</label>：<select  data-placeholder="状态" name="status" class=" isSelect75"></select></span>													
							&nbsp;&nbsp;<button id='searchBtn' class="btn btn-warning  btn-xs" title="过滤" type="button" onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button>
						</div>
				
					</div>
					<input type='hidden' class='pageNum' name='pageNum' value='1'/>
					<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
					<input type="hidden" name="id" />
					<input type="hidden" name="name" />
					<input type="hidden" name="_pid" />
				</form>
					<table id="baseTable" class="table table-striped table-bordered table-hover" >
					<thead>
						<tr>
							<th style="width:1%" class="center">
								<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
							</th>
							<th style="width:3%" class="center hidden-480">序号</th>
							<th style="width:3%" class="center">编号</th>
							<th style="width:3%" class="center">父级编号</th>
							<th style="width:5%" class='center hidden-480'>名称</th>		
							<th style="width:7%"  class='center hidden-480'>描述</th>
							<th style="width:3%" class="center">状态</th>
							<th style="width:5%" class="center">操作</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
					<div class="row">
						<div class="col-sm-4">
							<div class="dataTables_info customBtn" >
								<c:forEach var="pbtn" items="${permitBtn}">
									<a href="#" title="${pbtn.name}" id="${pbtn.btnId}" class="lrspace3" ><i class='${pbtn.icon} bigger-220'></i></a>
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
			<!-- #addorUpdateFrom -->
			<%@include file="districtform.jsp" %>
			<!-- #dialog-confirm -->
			<%@include file="../../common/dialog.jsp" %>	
		</div>
	</div>
</div>	
<script src="${jypath}/static/js/system/district/district.js"></script>
	
</body>
</html>