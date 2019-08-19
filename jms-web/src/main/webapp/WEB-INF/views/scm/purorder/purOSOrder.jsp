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
					<div id="rightDiv">
						<input type='hidden' id="stateYes" value='${yes}'/>
						<input type='hidden' id="stateValue" value='${state}'/>
						<form id="purOSOrderBaseForm" class="form-inline" method="POST" onsubmit="return false;">
							<div class="widget-main customBtn">	
								<table>
									<tr>
										<td><span>出库单号：<input type="text"  name="outBoundNo" placeholder="输入出库单号" class="input-large" style="width: 150px;"></span>&nbsp;&nbsp;</td>
										<td><span>订单号：<input  type="text"  name="orderNum" placeholder="输入订单号" class="input-large" style="width: 150px;"></span>&nbsp;&nbsp;</td>
										<td>拨入单位：</td>
										<td>
											<div id="queryinOrg" style="position:relative;z-index:99">
													<input id="queryinOrgName" type="text" placeholder="请选择拨入单位" jyValidate="required" readonly />
													<input id="queryinOrgId" type="hidden" name="inOrgId"/>
											</div>
										</td>
										<td>拨出单位：</td>
										<td><div id="queryoutOrg" style="position:relative;z-index:99">
													<input id="queryoutOrgName" type="text" jyValidate="required" placeholder="请选择拨出单位" readonly />
													<input id="queryoutOrgId" type="hidden" name="outOrgId"/>
											</div>
										</td>
										<td>
												时间：<input type="text" id="dateBegin" name="createTime"/>-	<input type="text" id="dateEnd" name="createTimeEnd"/>
										</td>
										<td><span id="selectisValid">状态：<select  data-placeholder="状态" name="status" class="isSelect75"></select></span>&nbsp;&nbsp;</td>
										<td>
<!-- 											<button class="btn btn-warning  btn-xs" id="searchBtn" title="过滤" type="button" onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button> -->
											<button id='searchBtn' class="btn btn-success  btn-xs" title="过滤" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>
											<button id='searchBtn' class="btn btn-success  btn-xs" title="重置" type="button" style="border-radius: 5px; " onclick="cleanBaseForm()">重置</button>		
										</td>	
									</tr>
								</table>	
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
						<table id="purOSOrderTable" class="table table-striped table-bordered table-hover" >
							<thead>
								<tr>
									<th style="width:2%" class="center">
										<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
									</th>
									<th style="width:3%" class="center">序号</th>
									<th style="width:10%" class="center">出库单号</th>	
									<th style="width:10%" class='center hidden-480'>订单单号</th>	
									<th style="width:10%"  class='center hidden-480'>拨入单位</th>
									<th style="width:6%"  class='center hidden-480'>数量</th>
									<th style="width:6%"  class='center hidden-480'>重量</th>
									<th style="width:6%" class="center">成本</th><!-- 
									<th style="width:6%" class="center">工费</th>
									<th style="width:5%" class="center">挂签费</th> -->
									<th style="width:6%" class="center">批发价</th>
									<th style="width:6%" class="center">牌价</th>
									<th style="width:5%" class="center">出库类型</th>
									<th style="width:5%" class="center">状态</th>
									<th style="width:3%" class="center">标记</th>
									<th style="width:5%" class="center">拨出单位</th>
									<th style="width:5%" class="center">创建时间</th>
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
					
				<div id="baseDiv" class="hide">
					<div class="col-xs-12">
						<form id="baseForm" method="POST" onsubmit="return false;" >
							<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
								<tr style="display:none">
									<td colspan="2" class="ui-state-error"><input type="hidden" name="id"></td>
								</tr>
								<tr class="FormData">
									<td class="CaptionTD" >出库单号：</td>
									<td class="DataTD" >
										<input type="text"  maxlength="36"  name="outBoundNo" class="FormElement ui-widget-content ui-corner-all" placeholder="系统自动生成" readonly />
									</td>
									<td class="CaptionTD"><font color="red">*</font>拨出单位：</td>
									<td class="DataTD">
										<div id="outOrg" style="position:relative;z-index:99">
											<input id="outOrgName" type="text" jyValidate="required" placeholder="请选择拨出单位" readonly />
											<input id="outOrgId" type="hidden"/>
										</div>
									</td>
									<td class="CaptionTD"><font color="red">*</font>结价类型：</td>
									<td class="DataTD"><span id="balancetype"><select id="balancetype" name="balancetype" style="width: 150px;"></select></span></td>
									<td class="CaptionTD"><font color="red">*</font>系数：</td>
									<td class="DataTD">
										<input type="text" jyValidate="required" maxlength="36"  name="ratio" class="FormElement ui-widget-content ui-corner-all" placeholder="不能为0" style="width: 60px;" onkeyup="JY.Validate.limitDouble(this)"/>
									</td>
								</tr>
								<tr class="FormData">
									<td class="CaptionTD"><font color="red">*</font>出库类型：</td>
									<td class="DataTD"><span id="selectType"><select id="outType" jyValidate="required" name="type" style="width: 150px;" onchange="selectType($(this).val())"></select></span></td>
									<td class="CaptionTD" name="typeOne"><font color="red">*</font>订单单号：</td>
									<td class="DataTD" name="typeOne" >
										<input id="orderNum" type="text" maxlength="36"  name="orderNum" class="FormElement ui-widget-content ui-corner-all" placeholder="输入关联订单单号" onchange="selectInOrgId()"/>
									</td>
									<td class="CaptionTD" name="typeOne"><font color="red">*</font>拨入单位：</td>
									<td class="DataTD" name="typeOne">
										<div id="inOrg" style="position:relative;z-index:99">
											<input id="inOrgName" type="text" placeholder="请选择拨入单位" readonly />
											<input id="inOrgId" type="hidden"/>
										</div>
									</td>
									<td class="CaptionTD" name="typeTwo"><font color="red">*</font>拨入供应商：</td>
									<td class="DataTD" name="typeTwo">
										<input id="queryFranchisee" name="franchiseeName" type="text" data-provide="typeahead" autocomplete="off" placeholder="请输入供应商检索"> 
										<input type="hidden" name="supplierId">
									</td> 
									<td class="CaptionTD"><font color="red">*</font>仓库：</td>
									<td class="DataTD">
										<select id="selectWarehouse" style="width: 120px;" name="warehouseid" jyValidate="required"></select>
									</td>
								</tr>
							</table>
							
							<br/>
							<div class="btnClass" style="margin-bottom:10px;">
								<!-- <a id="addBtn" class="lrspace3 aBtnNoTD" title="增加" href="#" >
									<i class="icon-plus-sign color-green bigger-180"></i>
								</a> -->
								<a class="lrspace3 aBtnNoTD" title="删除多个" href="#" onclick="delpurOSOrder()" >
									<i class="icon-trash color-red bigger-180"></i>
								</a>
								条码：<input type="text" id="enteryno" placeholder="请输入产品条码" class="input-large" onchange="subcode2()">
								入库单号：<input type="text" id="productinNo" placeholder="输入入库单号批量导入" class="input-large" value="RKCN2017022100002" onchange="subcode()">
							</div>
							
							<div class="col-xs-12" style="height: 380px;overflow-y: scroll;border: 1px solid #ddd">
								<table id="purOSOrderAdd" cellspacing="0" cellpadding="0" class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th style="width:3%"  class="center"><label><input type="checkbox" class="ace" ><span class="lbl"></span></label></th>
											<th style="width:8%"  class="center">名称</th>
											<th style="width:8%"  class="center">条码</th>
											<th style="width:6%"  class="center">证书号</th>
											<th style="width:6%"  class="center">说明</th>
											<th style="width:6%"  class="center">计价方式</th>
											<th style="width:6%"  class="center">数量</th>
											<th style="width:6%"  class="center">重量</th>	
											<th style="width:6%"  class="center">牌价</th>
											<th style="width:6%"  class="center">成本</th>
											<th style="width:6%"  class="center">批发价</th>
											<!-- <th style="width:6%"  class="center">牌价</th> -->
										</tr>
									</thead>
									<tbody>
									</tbody>
									<tfoot>
								</table>
							</div>
							<div class="col-xs-12" style="margin-top: 10px;margin-left: -12px;">
								<table>
									<tr>
										<td>备&nbsp;&nbsp;注：</td>
										<td colspan="11"><textarea rows="1" cols="125" name="remarks" multiline="true"></textarea></td>
									</tr>
									<tr id="causesDiv">
									<td>驳回原因：</td>
										<td colspan="11"><textarea rows="1" cols="125" disabled="disabled" name="description" multiline="true"></textarea></td>
									</tr>
									<tr>
										<td width="40px" >创建人:</td>
										<td width="70px" ><span id="createUser"></span></td>
										<td width="65px" >创建时间:</td>
										<td width="13%" ><span id="createTime"></span></td>
										<td width="50px" >修改人:</td>
										<td width="70px" ><span id="updateUser"></span></td>
										<td width="65px" >修改时间:</td>
										<td width="13%" ><span id="updateTime"></span></td>
										<td width="50px" >审核人:</td>
										<td width="70px" ><span id="checkUser"></span></td>
										<td width="65px" >审核时间:</td>
										<td width="13%" ><span id="checkTime"></span></td>
									</tr>
								</table>
							</div>
						</form>
					</div>
				</div>
				
			
				<div id="purOSOrderDiv" class="hide">
						<form id="purOSOrderForm" method="POST" onsubmit="return false;"  enctype="multipart/form-data">
							<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
								<tr class="FormData">
									<td class="CaptionTD"><font color="red">*</font>条码：</td>
									<td class="DataTD"><input id="queryCode" type="text"  maxlength="16" name="code" class="FormElement ui-widget-content ui-corner-all" autocomplete="off" jyValidate="required"><input type="hidden" name="outBoundNo"> <input type="hidden" name="id"></td>
								</tr>
								<tr class="FormData">
									<td class="CaptionTD"><font color="red">*</font>名称：</td>
									<td class="DataTD"><input  type="text"   maxlength="16" name="name" class="FormElement ui-widget-content ui-corner-all" readonly>
								</tr>	
								
								<tr class="FormData">
									<td class="CaptionTD"><font color="red">*</font>证书号：</td>
									<td class="DataTD"><input id="queryCode" type="text"   maxlength="16" name="cerno" class="FormElement ui-widget-content ui-corner-all" autocomplete="off" readonly></td>
								</tr>
								<tr class="FormData">
									<td class="CaptionTD"><font color="red">*</font>说明：</td>
									<td class="DataTD"><input  type="text"   maxlength="16" name="proRemarks" class="FormElement ui-widget-content ui-corner-all" readonly>
								</tr>	
								
								
								<tr class="FormData">
									<td class="CaptionTD"><font color="red">*</font>计价方式：</td>
									<td class="DataTD"><input  type="text"   maxlength="16" name="feeType" class="FormElement ui-widget-content ui-corner-all" readonly>
								</tr>	
								
								<tr class="FormData">
								<td class="CaptionTD"><font color="red">*</font>数量：</td>
									<td class="DataTD"><input type="number"  maxlength="16" name="num"  min="1"  class="FormElement ui-widget-content ui-corner-all"></td>
								</tr>
								<tr class="FormData">
									<td class="CaptionTD"><font color="red">*</font>重量：</td>
									<td class="DataTD"><input type="text"  maxlength="16" name="weight" class="FormElement ui-widget-content ui-corner-all">
									</td>
								</tr>
								
								
								
								<tr class="FormData">
									<td class="CaptionTD"><font color="red">*</font>单价：</td>
									<td class="DataTD"><input  type="text"   maxlength="16" name="unitPrice" class="FormElement ui-widget-content ui-corner-all" readonly>
								</tr>	
								
								<tr class="FormData">
									<td class="CaptionTD"><font color="red">*</font>成本：</td>
									<td class="DataTD">
										<input type="text" name="costing"  class="FormElement ui-widget-content ui-corner-all" readonly/>
									</td>
								</tr>
								<tr class="FormData">
									<td class="CaptionTD">
										<font color="red">*</font>批发价 ：</td>
									<td class="DataTD"><input name="pradeprice"  type="text"  class="FormElement ui-widget-content ui-corner-all" readonly/>
									</td>
								</tr>
								 <tr class="FormData">
									<td class="CaptionTD"><font color="red">*</font>牌价：</td>
									<td class="DataTD"><input type="text" name="price"  class="FormElement ui-widget-content ui-corner-all" readonly/></td>
								</tr>
							</table>
						</form>
					</div>
				</div>
				
			</div>
		</div>
	</div>
<div id="printDiv" class="hide">

</div>
<div id="purCheckDiv" class="hide">
	<form id="purOSOrderCheckForm" class="form-inline" method="POST" onsubmit="return false;">
		<table id="purCheckTable" class="table table-striped table-bordered table-hover" >
			<thead>
				<tr>
					<th style="width:2%" class="center">
						
					</th>
					<th style="width:3%" class="center">序号</th>
					<th style="width:10%" class="center">出库单号</th>	
					<th style="width:10%" class='center hidden-480'>采购单号</th>	
					<th style="width:13%"  class='center hidden-480'>收货单位</th>
					<th style="width:12%"  class='center hidden-480'>发货单位</th>
					<th style="width:15%"  class='center hidden-480'>出库类型</th>
					<th style="width:10%" class="center">发货时间</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>  
	</form>
</div>

<script src="${jypath}/static/js/system/jy/jy.component.js"></script>
<script src="${jypath}/static/js/scm/purorder/purOSOrder.js"></script>


<!-- 出库配送 -->
<script src="${jypath}/static/js/scm/distribution/logisticsInfo.js"></script>
<%@include file="../distribution/logisticsInfoForm.jsp" %>
<!-- 引入分页js -->  
<script  src="${jypath}/static/js/system/jy/pagging.js"></script> 
</body>