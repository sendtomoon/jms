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
<body><!-- <input onkeyup="this.value=this.value.replace(/D/g,'')" type="number" min="1" max="2" placeholder="页码" class="choseJPage"> -->
	<div class="page-content">
		<div class="row-fluid">
			<div class="col-xs-12">
				<div id="rightDiv" >
					<form id="materialinBaseForm" class="form-inline" method="POST" onsubmit="return false;">
						<div class="widget-main customBtn">	
							<input  type="text"  name="enteryno" placeholder="这里输入关键词" class="input-large">&nbsp;&nbsp;
							<span id="selectisValid">状态：<select  data-placeholder="状态" name="status" class="isSelect75"></select></span>&nbsp;&nbsp;
							<button id='searchBtn' class="btn btn-success  btn-xs" title="过滤" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>
<!-- 							<button class="btn btn-warning  btn-xs" id="searchBtn" title="过滤" type="button" onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button>		 -->
						</div>	
						<input type='hidden' class='pageNum' name='pageNum' value='1'/>
						<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
					</form>
					<div class="col-sm-4">
						<div class="dataTables_info customBtn" >
							<c:forEach var="pbtn" items="${permitBtn}">
								<%-- <a href="#" title="${pbtn.name}" id="${pbtn.btnId}" class="lrspace3" ><i class='${pbtn.icon} bigger-220'></i></a> --%>
								<button  id="${pbtn.btnId}"type="button" class="btn btn-success btn-xs" style="border-radius:5px;">${pbtn.name}</button>
							</c:forEach>
						</div>
					</div>
					<table id="materialinTable" class="table table-striped table-bordered table-hover" >
						<thead>
							<tr>
								<th style="width:1%" class="center">
									<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
								</th>
								<th style="width:3%" class="center">序号</th>
								<th style="width:10%" class="center">入库单号</th>
								<th style="width:10%" class='center'>单位名称</th>
								<th style="width:9%" class="center">入库数量</th>
								<th style="width:9%" class="center">入库件数</th>	
								<th style="width:9%" class="center">重量</th>	
								<th style="width:8%" class="center">牌价</th>
								<th style="width:8%"  class='center'>采购成本</th>
								<th style="width:8%"  class='center'>销售价</th>
								<th style="width:8%" class="center">状态</th>
								<th style="width:10%" class="center">入库时间</th>
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
				<%@include file="../materialin/materialinForm.jsp" %>
			</div>
		</div>
	</div>
	<div id="printDiv" class="hide">

    </div>
<script src="${jypath}/static/js/scm/materialin/materialin.js"></script>
</body>