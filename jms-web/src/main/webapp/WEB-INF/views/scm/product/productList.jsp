<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
<%@include file="../../common/includeBaseSet.jsp" %>
<%@include file="../../common/includeSystemSet.jsp" %>
<script language="javascript" src="${jypath}/static/js/lodop/LodopFuncs.js"></script>
<script src="${jypath}/static/js/bootstrap/bootstrap3-typeahead.min.js"></script>
<link rel="stylesheet" type="text/css" href="${jypath}/static/plugins/webuploader/css/webuploader.css" />
<link rel="stylesheet" type="text/css" href="${jypath}/static/plugins/webuploader/image-upload/style.css" />
<link rel="stylesheet" href="${jypath}/static/plugins/zTree/3.5/zTreeStyle.css" />
<script type="text/javascript" src="${jypath}/static/plugins/webuploader/js/webuploader.nolog.min.js"></script> 
<script type="text/javascript" src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>
</head>
<body>
<div class="page-content">
		<div class="row-fluid">	
			<div class="col-xs-12">
					<input type='hidden' id="stateFlag" value='${flag}'/>
					<input type='hidden' id="stateValue" value='${state}'/>
					<form id="productBaseForm" class="form-inline" method="POST" onsubmit="return false;">
					<div class="row">
						<div class="widget-main">	
							<span id="selGoldType" style="width:125px; margin-left: 20px;">金类：<select  name="goldType" style="width:125px;"></select></span>
							<span id="selProdCate">货类：<select jyValidate="required"  name="cateId" style="width:125px;"></select></span>
							<span id="selJewelryType">首饰类别：<select jyValidate="required"  name="cateJewelryId" style="width:125px;"></select></span>
							<span>条码：</span><input id="selCode" type="text" name="code" placeholder="请输入条码" style="width:150px;" />
							<c:if test="${flag!='DEFAULT'}"><span>入库单：</span><input type="text" name="inWarehouseNum" placeholder="请输入入库单" style="width:150px;" /></c:if>
							<span>供应商：</span><input id="findFranchisee" type="text" name="franchiseeName" placeholder="请输入供应商" style="width:150px;"  data-provide="typeahead" autocomplete="off"/>
							<input type="hidden" name="franchiseeId" />
							  <div id="productOrg" style="margin-left:20px;float:left;">
								组织机构：</span><input id="productOrgName" type="text" jyValidate="required" placeholder="请选组织机构" readonly />
								<input id="productOrgId" name="orgId" type="hidden"/>
							</div>
<%-- 							<c:if test="${flag!='false'}"> --%>
							<span id="selectProductStatus">状态：<select  data-placeholder="状态" id="selectStatus" name="status" class="isSelect75"></select></span>
<%-- 							</c:if>													 --%>
<!-- 							<label>时间</label>：<input type="text" id="dateBegin" name="createTime" value="2016/1/1"/>-<input type="text" id="dateEnd" name="createTimeEnd" value="2016/12/31"  />	 -->
							<button id='searchBtn' class="btn btn-success  btn-xs" title="过滤" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>
							<button id='searchBtn' class="btn btn-success  btn-xs" title="重置" type="button" style="border-radius: 5px; " onclick="cleanBaseForm()">重置</button>		
<!-- 							<button id='searchBtn' class="btn btn-warning  btn-xs" title="过滤" type="button" onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button> -->
						</div>				
					</div>
					<input type='hidden' class='pageNum' name='pageNum' value='1'/>
					<input type='hidden' class='pageSize'  name='pageSize' value='10'/> 
					<input  name="stockStatus" type="hidden" value='${stockStatus}'/>
					<input  name="skStatus" type="hidden" value='${skStatus}'/>
					<input  name="marking" type="hidden" value='${marking}'/>
					<input  id="optionArr" type="hidden" value='${optionArr}'/>
					</form>
					<div class="row">
						<div class="dataTables_info customBtn" style="z-index:1;margin-left: 30px;" >
							<c:forEach var="pbtn" items="${permitBtn}">
								<button  id="${pbtn.btnId}"type="button" class="btn btn-success btn-xs" style="border-radius:5px;">${pbtn.name}</button>
								<%-- <a href="#" title="${pbtn.name}" id="${pbtn.btnId}" class="lrspace3" ><i class='${pbtn.icon} bigger-220'></i></a> --%>
							</c:forEach>
						</div>
					</div>
					<table id="productList" class="table table-striped table-bordered table-hover" >
						<thead>
							<tr>
								<th style="width:5%" class="center">
									<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
								</th>
								<th style="width:5%"  class="center hidden-480">序号</th>
								<th style="width:10%" class="center hidden-480">条码</th>
<%-- 								<c:if test="${flag!='DEFAULT'}"><th style="width:10%" class="center hidden-480">入库单</th></c:if> --%>
								<th style="width:10%" class="center hidden-480">产品名称</th>
								<th style="width:7%" class="center hidden-480">重量</th>
								<th style="width:8%" class="center hidden-480">圈口</th>
								<th style="width:8%" class="center hidden-480">所属机构</th>
								<th style="width:15%" class="center hidden-480">供应商名称</th>
								<th style="width:10%" class="center hidden-480">创建时间</th>
								<th style="width:5%"  class="center hidden-480">状态</th>
								<th style="width:10%" class="center">操作</th>
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
			<%@include file="productForm.jsp" %>
			
			<!-- 证书列表 -->
			<%@include file="../credential/CredentialList.jsp" %>
			<%@include file="../accessories/accessoriesList.jsp" %>
			<!-- #dialog-confirm -->
			<%@include file="../../common/dialog.jsp" %>
			<%@include file="../warehouse/warehousingForm.jsp" %>
			<!-- 商品入库 -->	
			</div>
		</div>
	</div>	
<script src="${jypath}/static/js/system/jy/jy.component.js"></script>
<script src="${jypath}/static/js/scm/product/product.js"></script>
<script src="${jypath}/static/js/scm/accessories/accessories.js"></script>
<script src="${jypath}/static/js/scm/credential/credential.js"></script>
<script src="${jypath}/static/js/system/jy/uploadCommOne.js"></script>
<script src="${jypath}/static/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${jypath}/static/js/system/jy/attachmentUploadMore.js"></script>
<!-- 商品入库js -->
<script type="text/javascript" src="${jypath}/static/js/scm/warehouse/warehousing.js"></script>
</body>
</html>