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
							<form id="MateriaaulForm" class="form-inline" method="POST" onsubmit="return false;">
								<div class="row">
									<div class="widget-main">	
										&nbsp;&nbsp;名称：<input type="text" name="name"  placeholder="这里输入名称关键字" style="width:150px;"   class="input-large">
										&nbsp;&nbsp;条码：<input type="text" name="code"  placeholder="这里输入条码关键字" style="width:150px;"   class="input-large">
										&nbsp;&nbsp;款号：<input type="text" name="moudleCode"  placeholder="这里输入款号关键字" style="width:150px;"   class="input-large">
										时间：<input type="text" id="dateBegin" name="createTime" value="2017/1/1"/>-	<input type="text" id="dateEnd" name="createTimeEnd" value="2017/12/31"  />
										&nbsp;&nbsp;<span id="selectisValid">状态：<select   data-placeholder="状态" name="status" class=" isSelect75"></select></span>	
										<button id='searchBtn' class="btn btn-success  btn-xs" title="过滤" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>
										<button id='searchBtn' class="btn btn-success  btn-xs" title="重置" type="button" style="border-radius: 5px; " onclick="cleanBaseForm()">重置</button>	
<!-- 										<button id='searchBtn' class="btn btn-warning  btn-xs" title="过滤" type="button" onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button>									 -->
									</div>
								</div>
								<input type='hidden' class='pageNum' name='pageNum' value='1'/>
								<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
								<input type="hidden" name="id" />
							</form>
							<div class="col-sm-4">
							<div class="dataTables_info customBtn" >
									<c:forEach var="pbtn" items="${permitBtn}">
										<button  id="${pbtn.btnId}"type="button" class="btn btn-success btn-xs" style="border-radius:5px;">${pbtn.name}</button>
										<%-- <a href="#" title="${pbtn.name}" id="${pbtn.btnId}" class="lrspace3" ><i class='${pbtn.icon} bigger-220'></i></a> --%>
									</c:forEach>
								</div>
							</div>
							<table id="MaterialTable" class="table table-striped table-bordered table-hover" >
								<thead>
									<tr>
										<th style="width:1%" class="center">
											<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
										</th>
										<th style="width:3%" class="center hidden-480">序号</th>
										<th style="width:10%" class="center">条码</th>
										<th style="width:10%" class="center">名称</th>	
										<th style="width:8%" class='center hidden-480'>类别</th>		
										<th style="width:8%"  class='center hidden-480'>款号</th>
										<th style="width:8%" class='center hidden-480'>颜色</th>		
										<th style="width:8%"  class='center hidden-480'>净度</th>
										<th style="width:8%" class='center hidden-480'>切工</th>		
										<th style="width:10%" class="center hidden-480">创建时间</th>															
										<th style="width:10%" class="center">状态</th>
										
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
			<%@include file="materialForm.jsp" %>
			<%@include file="../../common/dialog.jsp" %>
			</div>
		</div>
	</div>

<script src="${jypath}/static/js/scm/material/material.js"></script>

</body>
</html>