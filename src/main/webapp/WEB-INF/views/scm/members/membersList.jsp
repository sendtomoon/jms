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
							<form id="baseForm" class="form-inline" method="POST" onsubmit="return false;">
								<div class="row">
									<div class="widget-main">	
										&nbsp;&nbsp;名称：<input type="text" name="name" placeholder="这里输入关键词"   class="input-large"/>
										&nbsp;&nbsp;手机：<input type="text" name="mobile" placeholder="请输入手机号" />
										&nbsp;&nbsp;卡号：<input type="text" name="cardNo" placeholder="请输入卡号" />
										<button id='searchBtn' class="btn btn-success  btn-xs" title="过滤" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>
									</div>
								</div>
								<input type='hidden' class='pageNum' name='pageNum' value='1'/>
								<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
								<input type="hidden" name="id" />
							</form>
							<c:forEach var="pbtn" items="${permitBtn}">
								<button  id="${pbtn.btnId}" type="button" class="btn btn-success btn-xs" style="border-radius:4px" >${pbtn.name}</button>
							</c:forEach>
							<table id="baseTable" class="table table-striped table-bordered table-hover" >
								<thead>
									<tr>
										<th style="width:1%" class="center">
											<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
										</th>
										<th style="width:3%" class="center hidden-480">序号</th>
										<th style="width:10%" class="center">卡号</th>
										<th style="width:10%" class="center">昵称</th>
										<th style="width:15%" class='center'>名称</th>		
										<th style="width:4%" class='center'>性别</th>
										<th style="width:10%" class="center">手机</th>
										<th style="width:10%" class="center">邮箱</th>
										<th style="width:6%" class="center">生日</th>
										<th style="width:10%" class="center">注册时间</th>
										<th style="width:6%" class="center">是否锁定</th>
										<th style="width:6%" class="center">是否激活</th>
										<th style="width:6%" class="center">是否限制</th>
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
			<%@include file="membersForm.jsp" %>
			<%@include file="../../common/dialog.jsp" %>
			</div>
		</div>
	</div>

<script src="${jypath}/static/js/scm/members/members.js"></script>

</body>
</html>