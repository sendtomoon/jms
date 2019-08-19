<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
<%@include file="../../common/includeBaseSet.jsp" %>
<%@include file="../../common/includeSystemSet.jsp" %>
<script src="${jypath}/static/js/bootstrap/bootstrap3-typeahead.min.js"></script>
</head>
<body><!-- <input onkeyup="this.value=this.value.replace(/D/g,'')" type="number" min="1" max="2" placeholder="页码" class="choseJPage"> -->
	<div class="page-content">
		<div class="row-fluid">
			<div class="col-xs-12">
					<div id="rightDiv" >
						<form id="baseForm" class="form-inline" method="POST" onsubmit="return false;">
							<div class="widget-main customBtn">	
								<input  type="text"  name="code" placeholder="这里输入条码" class="input-large">&nbsp;&nbsp;
								<span id="selectisValid">类型：<select  data-placeholder="类型" name="type" class="isSelect75"></select></span>&nbsp;&nbsp;
								<!-- <button class="btn btn-warning  btn-xs" id="searchBtn" title="过滤" type="button" onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button>		 -->
								<button id='searchBtn' class="btn btn-success  btn-xs" title="过滤" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>
							</div>	
							<input type='hidden' class='pageNum' name='pageNum' value='1'/>
							<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
						</form>
						<div class="col-sm-4">
							<div class="dataTables_info customBtn" >
								<c:forEach var="pbtn" items="${permitBtn}">
									<button  id="${pbtn.btnId}"type="button" class="btn btn-success btn-xs" style="border-radius:5px;">${pbtn.name}</button>
									<%-- <a href="#" title="${pbtn.name}" id="${pbtn.btnId}" class="lrspace3" ><i class='${pbtn.icon} bigger-220'></i></a> --%>
								</c:forEach>
							</div>
						</div>
						<table id="baseTable" class="table table-striped table-bordered table-hover" >
							<thead>
								<tr>
									<th style="width:1%" class="center">
										<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
									</th>
									<th style="width:3%" class="center">序号</th>
									<th style="width:10%" class='center hidden-480'>条码</th>	
									<th style="width:12%" class="center">物料名称</th>	
									<th style="width:10%"  class='center hidden-480'>仓库</th>
									<th style="width:10%" class="center">仓位</th>
									<th style="width:9%" class="center">计价方式</th>
									<th style="width:9%" class="center">颜色</th>
									<th style="width:8%" class="center">净度</th>
									<th style="width:8%" class="center">切工</th>
									<th style="width:9%" class="center">剩余数量</th>
									<th style="width:8%" class="center">剩余重量</th>
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
			</div>
		</div>
	</div>
	
	
	<div id="matinventory" class="tab-pane hide" style="width: 720px;" >
	  <!--  <table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
			<tr>
				<td style="text-align: right;">&ensp;物料名称：</td>
				<td style="text-align: left;"><input type="text"  name="name"></td>
				<td style="text-align: right;">&ensp;条码：</td>
				<td style="text-align: left;"><input type="text"  name="code"></td>
			</tr>
			<tr>
				<td style="text-align: right;">&ensp;仓库：</td>
				<td style="text-align: left;"><input type="text" name="warehouseId"></td>
				<td style="text-align: right;">&ensp;仓位：</td>
				<td style="text-align: left;"><input type="text" name="locationId"></td>
			</tr>
			<tr>
				<td style="text-align: right;">&ensp;数量：</td>
				<td style="text-align: left;"><input type="text" name="num"/></td>
				<td style="text-align: right;">&ensp;重量：</td>
				<td style="text-align: left;"><input type="text" name="weight"></td>
			</tr>
			<tr>
				<td style="text-align: right;">&ensp;单价(元)：</td>
				<td style="text-align: left;"><input type="text"  name="price"></td>
				<td style="text-align: right;">&ensp;采购单价(元)：</td>
				<td style="text-align: left;"><input type="text"  name="purcost"></td>
			</tr>
			<tr>
				<td style="text-align: right;">&ensp;销售价(元)：</td>
				<td style="text-align: left;"><input type="text" name="saleprice"></td>
				<td style="text-align: right;">&ensp;类型：</td>
				<td style="text-align: left;"><input type="text" name="type"></td>
			</tr>
			<tr>
				<td style="text-align: right;">&ensp;剩余数量：</td>
				<td style="text-align: left;"><input type="text" name="availNum"></td>
				<td style="text-align: right;">&ensp;剩余重量：</td>
				<td style="text-align: left;"><input type="text" name="availWeight"></td>
			</tr>
			<tr>
				<td style="text-align: right;">&ensp;创建人：</td>
				<td style="text-align: left;"><input type="text" name="createUser"></td>
				<td style="text-align: right;">&ensp;创建时间：</td>
				<td style="text-align: left;"><input type="text" name="createTime"></td>
			</tr>
			<tr>
				<td style="text-align: right;">&ensp;修改人：</td>
				<td style="text-align: left;"><input type="text" name="updateUser"></td>
				<td style="text-align: right;">&ensp;修改时间：</td>
				<td style="text-align: left;"><input type="text" name="updateTime"></td>
			</tr>
			<tr>
				<td style="text-align: right;">&ensp;备注：</td>
				<td colspan="3" style="text-align: left;">
				<textarea rows="2" cols="69" jyValidate="lenrange" maxl="50" name="remarks" multiline="true"></textarea></td>
			</tr>
		</table> -->
		
		
		<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
			<tr>
				<td style="text-align: right;">&ensp;物料名称：</td>
				<td style="text-align: left;"><input type="text"  name="name"></td>
				<td style="text-align: right;">&ensp;条码：</td>
				<td style="text-align: left;"><input type="text"  name="code"></td>
				<td style="text-align: right;">&ensp;仓库：</td>
				<td style="text-align: left;"><input type="text" name="warehouseId"></td>
			</tr>
			<tr>
				<td style="text-align: right;">&ensp;仓位：</td>
				<td style="text-align: left;"><input type="text" name="locationId"></td>
				<td style="text-align: right;">&ensp;剩余数量：</td>
				<td style="text-align: left;"><input type="text" name="availNum"></td>
				<td style="text-align: right;">&ensp;剩余重量：</td>
				<td style="text-align: left;"><input type="text" name="availWeight"></td>
			</tr>
			<tr>
				<!-- <td style="text-align: right;">&ensp;计费方式：</td>
				<td style="text-align: left;"><input type="text" name="feetype"></td> -->
				<td style="text-align: right;"><font color="red">*</font>计价方式：</td>
			    <td style="text-align: left;">
			    	<input id="feeType"  name="feetype" type="radio"  value="1"  />件 &nbsp;
			        <input id="feeType2" name="feetype" type="radio"   value="2" />克
			    </td>
				<td style="text-align: right;">&ensp;牌价(元)：</td>
				<td style="text-align: left;"><input type="text"  name="price"></td>
				<td style="text-align: right;">&ensp;采购单价(元)：</td>
				<td style="text-align: left;"><input type="text"  name="purcost"></td>
			</tr>
			<tr>
				<td style="text-align: right;">&ensp;销售价(元)：</td>
				<td style="text-align: left;"><input type="text" name="saleprice"></td>
				<td style="text-align: right;">&ensp;分类：</td>
				<td style="text-align: left;"><input type="text" name="cateId"></td>
				<td style="text-align: right;">&ensp;批次号：</td>
				<td style="text-align: left;"><input type="text" name="batchnum"></td>
			</tr>
			<tr>
				<td style="text-align: right;">&ensp;款号：</td>
				<td style="text-align: left;"><input type="text" name="moudleCode"></td>
				<td style="text-align: right;">&ensp;台宽比：</td>
				<td style="text-align: left;"><input type="text" name="pwidth"></td>
				<td style="text-align: right;">&ensp;尺寸：</td>
				<td style="text-align: left;"><input type="text" name="materialSize"></td>
			</tr>
			<tr>
				<td style="text-align: right;">&ensp;证书号：</td>
				<td style="text-align: left;"><input type="text" name="cerNum"></td>
				<td style="text-align: right;">&ensp;全深比：</td>
				<td style="text-align: left;"><input type="text" name="pdeep"></td>
				<td style="text-align: right;">&ensp;类型：</td>
				<td style="text-align: left;"><input type="text" name="type"></td>
			</tr>
			<tr>
				<td style="text-align: right;">&ensp;颜色：</td>
				<td style="text-align: left;"><input type="text" name="color"></td>
				<td style="text-align: right;">&ensp;切工：</td>
				<td style="text-align: left;"><input type="text" name="cut"></td>
				<td style="text-align: right;">&ensp;净度：</td>
				<td style="text-align: left;"><input type="text" name="clartity"></td>
			</tr>
			<tr>
				<td style="text-align: right;">&ensp;荧光：</td>
				<td style="text-align: left;"><input type="text" name="fluoreScence"></td>
				<td style="text-align: right;">&ensp;石形：</td>
				<td style="text-align: left;"><input type="text" name="stoneShape"></td>
				<td style="text-align: right;">&ensp;对称性：</td>
				<td style="text-align: left;"><input type="text" name="symmety"></td>
			</tr>
			<tr>
				<td style="text-align: right;">&ensp;抛光：</td>
				<td style="text-align: left;"><input type="text" name="polish"></td>
			</tr>
		</table>
</div>
	
<script src="${jypath}/static/js/scm/materialin/matinventory.js"></script>
</body>