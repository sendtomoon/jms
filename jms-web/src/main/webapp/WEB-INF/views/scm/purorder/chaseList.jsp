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

<script src="${jypath}/static/js/bootstrap/bootstrap3-typeahead.min.js"></script>

<script language="javascript" src="${jypath}/static/js/lodop/LodopFuncs.js"></script>


</head>
<body>
<div class="page-content">
		<div class="row-fluid">	
			<div class="col-xs-12">
				<div id="rightDiv" >
					<form id="baseForm" class="form-inline" method="POST" onsubmit="return false;">
						<div class="row">
							<div class="widget-main">	
								&ensp;&nbsp;&ensp;&nbsp;订单编号：<input type="text" name="orderNo" placeholder="这里输入关键词"   class="input-large">
								&nbsp;<span id="selectStoreStatus">订单状态：<select  data-placeholder="状态" name="status" class="isSelect75"></select></span>		
								时间：<input type="text" id="dateRender1" name="arrivalDate" value=""/>-	<input type="text" id="dateRender2" name="arrivalDateEnd" value=""  />
								<button id='searchBtn' class="btn btn-success  btn-xs" title="过滤" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>
								<button id='searchBtn' class="btn btn-success  btn-xs" title="重置" type="button" style="border-radius: 5px; " onclick="cleanBaseForm()">重置</button>								
							</div>				
						</div>
						<input type='hidden' class='pageNum' name='pageNum' value='1'/>
						<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
						<input type='hidden' name='orderType' value='${orderType}'/>
						<input type='hidden' id="userName" value="${userName}">
					</form>
					<div class="col-xs-12">
						<div >
							<div class="dataTables_info customBtn" style="z-index:1;" >
								<c:forEach var="pbtn" items="${permitBtn}">
<%-- 									<a href="#" title="${pbtn.name}" id="${pbtn.btnId}" class="lrspace3" ><i class='${pbtn.icon} bigger-120'>${pbtn.name}</i></a> --%>
									<button  id="${pbtn.btnId}"type="button" class="btn btn-success btn-xs" style="border-radius:4px;" >${pbtn.name}</button>
								</c:forEach>
<!-- 								<button  id="btnId"type="button" class="btn btn-success btn-xs" style="border-radius:4px;" >付款</button> -->
							</div>
						</div>
					</div>
					<div class="col-xs-12">
					<table id="baseTable" class="table table-striped table-bordered table-hover" >
						<thead>
							<tr>
								<th style="width:3%" class="center">
									<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
								</th>
								<th style="width:5%"  class="center hidden-480">序号</th>
								<th style="width:8%" class="center">订单编号</th>
								<th style="width:8%" class="center">要货单位</th>
								<th style="width:5%" class="center hidden-480">货品总数</th>
								<th style="width:5%" class="center hidden-480">到货率</th>
<!-- 								<th style="width:10%" class="center hidden-480"><i class="icon-home"></i>货品总重</th> -->
<!-- 								<th style="width:10%"  class="center hidden-480"><i class="glyphicon glyphicon-tag"></i>订单总价</th> -->
								<th style="width:10%"  class="center hidden-480">要求到货日期</th>
<!-- 								<th style="width:5%" class="center hidden-480">标签</th> -->
								<th style="width:5%" class="center">状态</th>
								<th style="width:5%" class="center hidden-480">经办人</th>
								<th style="width:5%" class="center hidden-480">供应商</th>
								<th style="width:5%" class="center">审核人</th>
								<th style="width:10%" class="center">审核时间</th>
								<th style="width:5%" class="center">创建人</th>
								<th style="width:10%" class="center">创建时间</th>
<!-- 								<th style="width:10%" class="center">操作</th> -->
							</tr>
						</thead>
						<tbody></tbody>
					</table>
					</div>
					<div class="row">
						<div class="col-sm-4">
<!-- 							<div class="dataTables_info customBtn" > -->
<%-- 								<c:forEach var="pbtn" items="${permitBtn}"> --%>
<%-- 									<a href="#" title="${pbtn.name}" id="${pbtn.btnId}" class="lrspace3" ><i class='${pbtn.icon} bigger-220'></i></a> --%>
<%-- 								</c:forEach> --%>
<!-- 							</div> -->
						</div>
						<div class="col-sm-8">
							<!--设置分页位置-->
							<div id="pageing" class="dataTables_paginate paging_bootstrap">
								<ul class="pagination"></ul>
							</div>
						</div>
					</div>
				</div>
			<!-- #addorUpdateFrom -->
			<%@include file="chaseForm.jsp" %>
			<%@include file="../../system/account/pickUser.jsp" %>
<%-- 			<%@include file="../../pos/payments/paymentsList.jsp" %> --%>
			<!-- #dialog-confirm -->
			<%@include file="../../common/dialog.jsp" %>
			</div>
		</div>
	</div>	
<script src="${jypath}/static/js/scm/purorder/chase.js"></script>	
<script src="${jypath}/static/js/system/account/pickUser.js"></script>	
<div id="printDiv" class="hide">

</div>
</body>
</html>