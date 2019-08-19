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
				<div id="rightDiv" >
					<form id="InventoryDetailForm" class="form-inline" method="POST" onsubmit="return false;">
						<div class="row">
							<div class="widget-main">	
								&nbsp;&nbsp;编号：<input type="text" name="inventoryNo"  placeholder="这里输入编号" style="width:150px;"   class="input-large">
								<!-- 
								时间：<input type="text" id="dateBegin" name="createTime" value="2017/1/1"/>-<input type="text" id="dateEnd" name="createTimeEnd" value="2017/12/31"  />
								&nbsp;&nbsp;<span id="selectisValid">状态：<select   data-placeholder="状态" name="status" class=" isSelect75"></select></span>	
								-->
								<button id='searchBtn' class="btn btn-success  btn-xs" title="过滤" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>
<!-- 								<button id='searchBtn' class="btn btn-warning  btn-xs" title="过滤" type="button" onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button>									 -->
							</div>
						</div>
						<input type='hidden' class='pageNum' name='pageNum' value='1'/>
						<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
						<input type="hidden" name="id" />
					</form>
					<c:forEach var="pbtn" items="${permitBtn}">
						<button  id="${pbtn.btnId}"type="button" class="btn btn-success btn-xs" style="border-radius:4px" >${pbtn.name}</button>
					</c:forEach>
					<table id="InventoryDetailTable" class="table table-striped table-bordered table-hover" >
						<thead>
							<tr>
								<th style="width:1%" class="center">
									<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
								</th>
								<th style="width:10%" class="center">编号</th>
								<th style="width:8%"  class='center hidden-480'>货类</th>
								<th style="width:8%" class='center hidden-480'>货组</th>		
								<th style="width:8%"  class='center hidden-480'>盘点机构</th>
								<th style="width:8%" class='center hidden-480'>盘点仓库</th>		
								<th style="width:10%" class="center hidden-480">盘点仓位</th>															
								<th style="width:10%" class="center">状态</th>
								<th style="width:10%" class="center">创建人</th>
								<th style="width:8%" class='center hidden-480'>盘点时间</th>	
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
			<%@include file="../inventory/inventoryDetailForm.jsp" %>
			<%@include file="../../common/dialog.jsp" %>
			</div>
		</div>
	</div>
	
<script src="${jypath}/static/js/scm/inventory/inventoryDetail.js"></script>

</body>
</html>