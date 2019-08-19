<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
<%@include file="../../common/includeBaseSet.jsp" %>
<%@include file="../../common/includeSystemSet.jsp" %>
<script src="${jypath}/static/js/bootstrap/bootstrap3-typeahead.min.js"></script>
</head>
<body>
	<div class="page-content">
		<div class="row-fluid">
			<div class="col-xs-12">
						<div id="rightDiv" >
							<form id="FranchiseBaseForm" class="form-inline" method="POST" onsubmit="return false;">
								<div class="row">
									<div class="widget-main">	
										&nbsp;&nbsp;<input type="text" name="name" placeholder="这里输入关键词"   class="input-large">
										&nbsp;&nbsp;<span id="selectisValid"><label>状态</label>：<select   data-placeholder="状态" name="status" class=" isSelect75"></select></span>	
										&nbsp;&nbsp;<span><label>供应商</label>：</span><input id="inputName" type="text" name="longName" placeholder="请输入供应商" style="width:125px;"  data-provide="typeahead" autocomplete="off">
										<input type="hidden" name="id"/>
										<button id='searchBtn' class="btn btn-success  btn-xs" title="过滤" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>
<!-- 										<button id='searchBtn' class="btn btn-warning  btn-xs" title="过滤" type="button" onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button>									 -->
										
									</div>
								</div>
								<input type='hidden' class='pageNum' name='pageNum' value='1'/>
								<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
								<input type="hidden" name="id" />
							</form>
							<c:forEach var="pbtn" items="${permitBtn}">
								<button  id="${pbtn.btnId}" type="button" class="btn btn-success btn-xs" style="border-radius:4px" >${pbtn.name}</button>
							</c:forEach>
							<table id="FranchisebaseTable" class="table table-striped table-bordered table-hover" >
								<thead>
									<tr>
										<th style="width:1%" class="center">
											<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
										</th>
										<th style="width:3%" class="center hidden-480">序号</th>
										<th style="width:10%" class="center">代码</th>
										<th style="width:10%" class="center">简称</th>
										<th style="width:15%" class='center hidden-480'>名称</th>		
										<th style="width:10%"  class='center hidden-480'>法人名称</th>
										<th style="width:10%" class="center">营业执照</th>
										<th style="width:17%" class="center">邮箱</th>
										<th style="width:10%" class="center">状态</th>
										<th style="width:15%" class="center">操作</th>
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
			<%@include file="FranchiseeForm.jsp" %>
			<%@include file="../../common/dialog.jsp" %>
			</div>
		</div>
	</div>

<script src="${jypath}/static/js/scm/franchisee/franchisee.js"></script>

</body>
</html>