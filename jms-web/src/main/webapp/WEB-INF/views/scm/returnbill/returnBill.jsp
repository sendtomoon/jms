<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html lang="en">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head>
<%@include file="../../common/includeBaseSet.jsp"%>
<%@include file="../../common/includeSystemSet.jsp"%>
<script src="${jypath}/static/js/bootstrap/bootstrap3-typeahead.min.js"></script>
<script language="javascript" src="${jypath}/static/js/lodop/LodopFuncs.js"></script>
</head>
<body>
	<div class="page-content">
		<div class="row-fluid">
			<div class="col-xs-12">
				<div id="rightDiv">
					<form id="returnBillForm" class="form-inline" method="POST" onsubmit="return false;">
						<div class="widget-main customBtn">
							<table>
								<tr>
									<td><span>退厂单号：<input type="text" name="returnNo"
											placeholder="请输入关键字" class="input-large" /></span>&nbsp;&nbsp;</td>
									<td><span>入库通知单：<input type="text" name="noticeNo"
											placeholder="请输入关键字" class="input-large" /></span>&nbsp;&nbsp;</td>
									<td><span>供应商：<input type="text" name="supplierName"
											placeholder="请输入关键字" class="input-large" /></span>&nbsp;&nbsp;</td>
<!-- 									<td><span id="selectReturnBillStatus"><label></label>：<select -->
<!-- 											data-placeholder="状态" name="status" class="isSelect75"></select></span></td> -->
									<td>
										<button id='searchBtn' class="btn btn-success  btn-xs" title="过滤" type="button" style="border-radius: 5px; " onclick="getbaseList(1)">搜索</button>
<!-- 									<button class="btn btn-warning  btn-xs" id="searchBtn" -->
<!-- 											title="过滤" type="button" onclick="getbaseList(1)"> -->
<!-- 											<i class="icon-search bigger-110 icon-only"></i> -->
<!-- 										</button>&nbsp;&nbsp; -->
										</td>
								</tr>
							</table>
						</div>
						<input type='hidden' class='pageNum' name='pageNum' value='1' />
						<input type='hidden' class='pageSize' name='pageSize' value='10' />
					</form>
				</div>
				<div class="row">
					<div style="z-index: 1;">
						<div class="dataTables_info customBtn"
							style="z-index: 1; margin-left: 30px;">
							<c:forEach var="pbtn" items="${permitBtn}">
								<button id="${pbtn.btnId}" type="button"
									class="btn btn-success btn-xs"
									style="border-radius: 5px; margin-bottom: 5px;">${pbtn.name}</button>
							</c:forEach>
						</div>
					</div>
				</div>
				<table id="returnBillList" align="center"
					class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th style="width: 3%" class="center"><label><input
									type="checkbox" class="ace"><span class="lbl"></span></label></th>
							<th style="width: 3%" class="center hidden-480">序号</th>
							<th style="width: 10%" class="center hidden-480">退厂单号</th>
							<th style="width: 10%" class="center hidden-480">入库通知单</th>
							<th style="width: 5%" class="center hidden-480">退货数量</th>
							<th style="width: 5%" class="center hidden-480">退货重量</th>
							<th style="width: 5%" class="center hidden-480">基础工费</th>
							<th style="width: 5%" class="center hidden-480">附加工费</th>
							<th style="width: 5%" class="center hidden-480">其他工费</th>
							<th style="width: 5%" class="center hidden-480">采购成本</th>
							<th style="width: 10%" class="center hidden-480">供应商</th>
							<th style="width: 5%" class="center hidden-480">创建人</th>
							<th style="width: 5%" class="center hidden-480">创建时间</th>
<!-- 							<th style="width: 10%" class="center hidden-480">备注</th> -->
<!-- 							<th style="width: 5%" class="center hidden-480">状态</th> -->
<!-- 							<th style="width: 10%" class="center hidden-480">审核时间</th> -->
<!-- 							<th style="width: 5%" class="center hidden-480">审核人</th> -->
<!-- 							<th style="width: 10%" class="center hidden-480">审核时间</th> -->
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

			<%@include file="../../common/dialog.jsp"%>
			<div id="baseDiv" class="hide" style="position: static;">
				<form id="returnBillBaseForm" class="form-inline" method="POST" onsubmit="return false;">
					<div id="baseInfoDiv" class="row">
						<div id="titleInfo" class="widget-main">
							<span id="returnBillNumber"
								style="width: 125px; margin-left: 20px;">退厂单号：</span> <input
								id="inputReturnBillNumber" type="text"></input> <span
								id="inBill" style="width: 125px; margin-left: 20px;">入库通知单：</span>
							<input id="inputNoticeNumber" type="text"></input> <span
								id="returnOrg" style="width: 125px; margin-left: 20px;">退货单位：</span>
							<input id="inputReturnOrg" type="text"></input> <span
								id="supplier" style="width: 125px; margin-left: 20px;">供应商：</span>
							<input id="inputSupplierName" type="text"></input>
							<!--<div id="refuseCause" style="margin-left:20px;margin-top: 20px;"><span>拒单理由：<input id="refuseReason" type="text" style="width: 800px;"/></span></div>-->
						</div>
					</div>
				</form>
				<div
					style="height: 300px; overflow-y: scroll; border: 1px solid #ddd; width: 100%">
					<table id="returnBillDetailList" cellspacing="0" cellpadding="0"
						border="0" class="table table-striped table-bordered table-hover"
						style="width: 99.9%">
						<thead>
							<tr>
								<th style="width: 3%" class="formdetail">序号</th>
								<th style="width: 7%" class="formdetail">条码</th>
								<th style="width: 8%" class="formdetail">名称</th>
								<th style="width: 6%" class="formdetail">材质</th>
								<th style="width: 5%" class="formdetail">退货数量</th>
								<th style="width: 5%" class="formdetail">退货重量</th>
								<th style="width: 5%" class="formdetail">金重</th>
								<th style="width: 5%" class="formdetail">石重</th>
								<th style="width: 5%" class="formdetail">基础工费</th>
								<th style="width: 5%" class="formdetail">附加工费</th>
								<th style="width: 5%" class="formdetail">其它工费</th>
								<th style="width: 5%" class="formdetail">采购成本</th>
							</tr>
						</thead>
						<tbody></tbody>
						<tfoot></tfoot>
					</table>
				</div>
				<div id="returnBillBaseInfo">

					<div id="bottomReason" class="widget-main">
						<div>
							<table>
<!-- 								<div id="beizhuhehehe"> -->
<!-- 									<tr> -->
<!-- 										<td><span>备注：</span></td> -->
<!-- 										<td><textarea id="returnBillRemarks" rows="1" cols="35" -->
<!-- 												disable="disabled" -->
<!-- 												style="margin: 1px; height: 28px; width: 970px;" -->
<!-- 												id="remarkInput"></textarea></td> -->
<!-- 									</tr> -->
<!-- 								</div> -->
							</table>
						</div>
					</div>
					<div class="widget-main"
						style="margin-top: -7px; margin-bottom: 10px;">
						<span>创建人：</span><span id="consoleManInput"></span> <span
							style="margin-left: 10px;">创建时间：</span><span id="makeTimeInput"></span>
					</div>
<!-- 					<div class="widget-main" -->
<!-- 						style="margin-top: -7px; margin-bottom: 10px;"> -->
<!-- 						<span>审核人：</span><span id="checkUserInput"></span> <span -->
<!-- 							style="margin-left: 10px;">审核时间：</span><span id="checkTimeInput"></span> -->
<!-- 					</div> -->
				</div>
			</div>


			<div id="addReturnBillDiv" class="hide" style="position: static;">
			     <input type='hidden' id="savereturnbillid" value=""/>
				<form id="addReturnBillForm" class="form-inline" method="POST" onsubmit="return false;">
				
				
				<table id="addBaseInfoDiv" cellspacing="0" cellpadding="4px;" border="0" class="customTable">
<!-- 				<tr style="display:none"> -->
<!-- 					<td colspan="2" class="ui-state-error"><input type="hidden" name="id"></td> -->
<!-- 				</tr> -->
				<tr class="FormData">
					<td class="Captiontd"><font color="red">*</font>退厂单号：</td>
					<td class="Datatd"><input type="text" maxlength="36"
						name="returnNo"
						class="FormElement ui-widget-content ui-corner-all" readonly
						placeholder="自动生存"/ ></td>
					<td class="Captiontd"><font color="red">*</font>入库通知单：</td>
					<td class="Datatd"><input type="text" maxlength="36"
						jyValidate="required" name="noticeNo" class="FormElement ui-widget-content ui-corner-all" onchange="subcodeTwo()"  jyValidate="required"/></td>
						
					<td class="Captiontd"><font color="red">*</font>退货单位：</td>
					<td class="Datatd">
						<input type="text" id="orgName" maxlength="36" name="orgName" class="FormElement ui-widget-content ui-corner-all"readonly/ >
						<input type="hidden" id="orgId" name="orgId" / >
					</td>
				<td class="Captiontd"><font color="red">*</font>供应商：</td>
					<td class="Datatd"><input type="text" maxlength="36"
						name="supplierName"
						class="FormElement ui-widget-content ui-corner-all"readonly/ >
						<input type="hidden" name="surpplyId" id="supplierId"/>
					</td>
				</tr>
				</table>
				<div class="btnClass" style="margin-bottom: 10px;">
				</a> <a class="lrspace3 aBtnNotd" title="删除" href="#"
					onclick="del()"> <i
					class="icon-trash color-red bigger-180"></i>
				</a>
				条码：<input type="text" id="enteryNo" name="enteryNo" placeholder="请输入产品条码" class="input-large" onchange="subcodeTwo()">
			</div>
<!-- 					<div id="addBaseInfoDiv" class="row"> -->
<!-- 						<div style="margin-left: 30px;margin-bottom: 5px;"> -->
<!-- 						<span>关联QC单号：<input id="QC" type="text" placeholder="如没有请勿输入"/></span> -->
<!-- 						<span id="inBill" style="width: 125px; margin-left: 20px;">入库通知单：</span> -->
<!-- 						<input id="addReturnBillNoticeNo" type="text"/>  -->
<!-- 						<button class="btn btn-warning  btn-xs" id="searchNoticeBtn" title="过滤" type="button" onclick="subcodeTwo()"> -->
<!-- 							<i class="icon-search bigger-110 icon-only"></i></button> -->
<!-- 						</div> -->
<!-- 						<div style="margin-left: 30px;margin-bottom: 5px;"> -->
<!-- 						<span id="supplier" style="width: 125px;">供应商：</span> -->
<!-- 						<input id="addReturnBillSupplierName" readonly type="text"></input> -->
<!-- 						</div> -->
<!-- 						<div style="margin-left: 30px;margin-bottom: 5px;"> -->
<!-- 						<span>备注：<input id="addRBRemarks" type="text" style="width:600px;"/></span> -->
<!-- 						</div>				 -->
<!-- 						<div style="margin-left: 30px;margin-bottom: 5px;"> -->
<!-- 							<a class="lrspace3 aBtnNoTD" title="删除多个" href="#" onclick="delRowOfAdd()"> -->
<!-- 							<i class="icon-trash color-red bigger-180"></i></a>  -->
<!-- 							<span style="margin-left: 10px;">条码： -->
<!-- 							<input type="text" id="addReturnBillProductCode" placeholder="请输入产品条码" class="input-large" onchange="findXiangqian()" /></span> -->
<!-- 						</div>  -->
<!-- 						</div> -->
				</form>
				<div style="height: 300px; overflow-y: scroll; border: 1px solid #ddd; width: 100%">
					<table id="addRturnBillDetailList" cellspacing="0" cellpadding="0"
						border="0" class="table table-striped table-bordered table-hover"
						style="width: 99.9%;text-align:center;">
						<thead>
							<tr>
							    <th style="width: 5%" class="center"><input id="selectAddAllCheckBox" type="checkbox"></th>
								<th style="width: 4%" class="center">序号</th>
								<th style="width: 7%" class="center">条码</th>
								<th style="width: 9%" class="center">名称</th>
								<th style="width: 9%" class="center">材质</th>
								<th style="width: 5%" class="center">退货数量</th>
								<th style="width: 5%" class="center">退货重量</th>
								<th style="width: 5%" class="center">金重</th>
								<th style="width: 5%" class="center">石重</th>
								<th style="width: 7%" class="center">基础工费</th>
								<th style="width: 7%" class="center">附加工费</th>
								<th style="width: 7%" class="center">其它工费</th>
								<th style="width: 7%" class="center">采购成本</th>
							</tr>
						</thead>
						<tbody></tbody>
						<tfoot></tfoot>
					</table>
				</div>
				
				<div class="col-xs-12" id="returnNote" style="margin-top: 10px; margin-left: -12px;">
				<table>
					<tr>
						<td>备&nbsp;注：</td>
						<td colspan="11"><textarea rows="1" cols="135" name="remarks" multiline="true"></textarea></td></tr>
					
<!-- 					<tr id="causesDiv" class="hide"> -->
<!-- 						<td>驳回原因：</td> -->
<!-- 						<td colspan="11"><textarea rows="1" cols="135" disabled="disabled" name="causes" multiline="true"></textarea></td> -->
<!-- 					</tr> -->
					<tr>
						<td width="40px">创建人:</td>
						<td width="70px"><span id="createUser"></span></td>
						<td width="65px">创建时间:</td>
						<td width="13%"><span id="createTime"></span></td>
<!-- 						<td width="50px">修改人:</td> -->
<!-- 						<td width="70px"><span id="updateUser"></span></td> -->
<!-- 						<td width="65px">修改时间:</td> -->
<!-- 						<td width="13%"><span id="updateTime"></span></td> -->
<!-- 						<td width="50px">审核人:</td> -->
<!-- 						<td width="70px"><span id="checkUser"></span></td> -->
<!-- 						<td width="65px">审核时间:</td> -->
<!-- 						<td width="13%"><span id="checkTime"></span></td> -->
					</tr>
				</table>
			</div>
			</div>


		</div>
	</div>
	</div>
	<script type="text/javascript"
		src="${jypath}/static/js/scm/returnbill/returnbill.js"></script>

	<div id="returnbillPrintInfoDiv" class="hide"></div>
</body>
</html>