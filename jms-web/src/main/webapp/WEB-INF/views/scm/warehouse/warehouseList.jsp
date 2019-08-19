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
</head>
<body>
	<div class="page-content">
		<div class="row-fluid">
			<div class="col-xs-12">
				<div class="row">
					<div class="col-sm-3">
						<div class="widget-box"><ul id="orgTree" class="ztree orgTree"></ul></div>
						<div class="dataTables_info customBtn">
							<!-- 
							<a class="lrspace3" id="addPosBtn" title="新增职务" href="#"><i class="icon-user-md color-green bigger-240"></i></a>
							 -->
							<!-- <button  id="addPosBtn" type="button" class="btn btn-success btn-xs" style="border-radius:4px" >新增职务</button> -->
						</div>						
					</div>	
					<div class="col-sm-9">	
						<div id="rightDiv" >
							<div class="row page-header">
								<h1>&nbsp;<span id='orgName1'></span>&nbsp;<small> <i class="icon-double-angle-right"></i> <span id='orgName2'></span> </small></h1>
							</div>
							<form id="baseWarehouseForm" method="POST" onsubmit="return false;">
								<div class="widget-main customBtn">	
									<input  type="text"  name="name" placeholder="这里输入关键词" class="input-large">		
										&nbsp;&nbsp;<span id="selectisValid"><label></label>：<select  data-placeholder="状态" name="status" class="isSelect75"></select></span>			
										<button id='searchBtn' class="btn btn-success  btn-xs" title="过滤" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>																																			
<!-- 									&nbsp;&nbsp;<button class="btn btn-warning  btn-xs" id="searchBtn" title="过滤" type="button" onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button>		 -->
								</div>			
								<input type='hidden' class='pageNum' name='pageNum' value='1'/>
								<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
								<input type='hidden' id="menuId" name='menuId' value='${menuId}'/>
								<input type='hidden' id="menuPId" name='menuPId' value='${menuPId}'/>
								<input type='hidden' name='orgId' />
								<input type='hidden' name='orgName' />
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
									<th style="width:10%" class="center ">仓库名称</th>
									<th style="width:10%" class="center ">仓库代码</th>
									<th style="width:10%" class="center ">所属单位</th>
									<th style="width:10%" class="center ">区域代码</th>
									<th style="width:10%" class="center ">负责人</th>
									<th style="width:10%" class="center">仓库类型</th>
									<th style="width:10%" class="center">状态</th>
									<th style="width:10%" class="center ">默认仓库</th>
									<th style="width:10%" class="center">操作</th>
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
				</div>	
			<!-- #addorUpdateFrom -->
			<%@include file="warehouseForm.jsp" %>
			<!-- #dialog-confirm -->
			<%@include file="../../common/dialog.jsp" %>
			<!-- 仓位列表 -->
			<%@include file="../warehouse/warehouseLocationList.jsp" %>
			<!-- 仓位表单 -->
			<%@include file="../warehouse/warehouseLocationForm.jsp" %>
			</div>
		</div>
	</div>
<script src="${jypath}/static/js/scm/warehouse/warehouse.js"></script>
</body>
</html>