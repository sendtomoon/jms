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
								<form id="attributedictListForm" class="form-inline" method="POST" onsubmit="return false;">
									<div class="row">
										<div class="widget-main">	
											&ensp;&nbsp;<input type="text" name="name" placeholder="这里输入关键词"   class="input-large">													
											&nbsp;&nbsp;<span id="selectisValid"><label>状态</label>：<select  data-placeholder="状态" name="status" class=" isSelect75"></select></span>	
											<button id='searchBtn' class="btn btn-success  btn-xs" title="过滤" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>
<!-- 											<button id='searchBtn' class="btn btn-warning  btn-xs" title="过滤" type="button" onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button> -->
										</div>				
									</div>
									<input type='hidden' class='pageNum' name='pageNum' value='1'/>
									<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
								</form>
								<c:forEach var="pbtn" items="${permitBtn}">
									<button  id="${pbtn.btnId}" type="button" class="btn btn-success btn-xs" style="border-radius:4px" >${pbtn.name}</button>
								</c:forEach>
								<table id="attributedictTable" cellspacing="0" cellpadding="0" border="0" class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th id="itemformSort" style="width:5%"  class="center">序号</th>
											<th style="width:15%"  class="center">名字</th>
											<th style="width:10%"  class="center">编码</th>	
											<th style="width:10%"  class="center">类型</th>
											<th style="width:10%"  class="center">长度</th>
											<th style="width:10%"  class="center">空值</th>
											<th style="width:10%"  class="center">字典</th>
											<th style="width:10%"  class="center">检索</th>
											<th style="width:10%"  class="center">状态</th>
											<th style="width:10%"  class="center">操作</th>
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
			<%@include file="attributedictForm.jsp" %>
			<%@include file="../../common/dialog.jsp" %>
			</div>
		</div>
	</div>

<script src="${jypath}/static/js/scm/attributedict/attributedict.js"></script>	

</body>
</html>