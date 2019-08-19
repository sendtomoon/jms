<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html >
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
</head>
<body>
	<div id="returnBillPrintDiv">
	<center><strong><font style='font-family:"微软雅黑";font-size:16px;'>退厂单</font></strong></center>
	<p></p>
		<table border=0 cellSpacing=0 cellPadding=0 width="100%">
			<tbody>
				<tr>
				</tr>
				<tr>
					<td width="33%"><font>退厂单号：<span>${object.returnNo}</span></font></td>
					<td width="43%"><font>入库通知单：<span>${object.noticeNo}</span></font></td>
					<td><font>供应商：<span>${object.supplierName}</span></font></td>
				<tr>
			</tbody>
		</table>
	<br>
	<div>
		<table border=1 cellSpacing=0 cellPadding=1 width="100%"
			style="border-collapse: collapse" bordercolor="#333333">
			<thead>
				<tr>
					<th>序号</th>
					<th>条码</th>
					<th>名称</th>
					<th>材质</th>
					<th>数量</th>
					<th>重量</th>
					<th>金重</th>
					<th>石重</th>
					<th>基础工费</th>
					<th>附加工费</th>
					<th>其它工费</th>
					<th>采购成本</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${list}" varStatus="status">
				<tr>
					<td>${status.index+1}</td>
					<td align="right">${list.code}</td>
					<td>${list.name}</td>
					<td>${list.goldName}</td>
					<td align="right">${list.unqualifyNum}</td>
					<td align="right">${list.unqualifyWt}</td>
					<td align="right">${list.fgoldWeight}</td>
					<td align="right">${list.stoneWeight}</td>
					<td align="right">${list.basicCost}</td>
					<td align="right">${list.addCost}</td>
					<td align="right">${list.otherCost}</td>
					<td align="right">${list.purCost}</td>
				</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="4">
						合计:
					</td>
					
					<td align="right">${object.totalNum}g</td>
					<td align="right">${object.totalWt}g</td>
					<td align="right">${object.fgoldWeight}g</td>
					<td align="right">${object.stoneWeight}g</td>
					<td align="right">￥${object.basicCost}</td>
					<td align="right">￥${object.addCost}</td>
					<td align="right">￥${object.otherCost}</td>
					<td align="right">￥${object.purCost}</td>
				
				</tr>
			</tfoot>
		</table>
	</div>
<%-- 	<p>备注：${object.remarks}</p> --%>
	<p></p>

	<table border=0 cellSpacing=0 cellPadding=0 width="100%">
		<tbody>
			<tr>
				<td width="43%"><font>创建人：<span>${object.createUser}</span></font></td>
<%-- 				<td><font>审核人：<span>${object.checkUser}</span></font></td> --%>
			</tr>
			<tr>
				<td><font>创建时间：<span>${object.cTime}</span></font></td>
<%-- 				<td><font>审核时间：<span>${object.chTime}</span></font></td> --%>
			</tr>
		</tbody>
	</table>
	<p></p>
	<br/>
	<br/>
	<br/>
	<p>质检：_________&nbsp;&nbsp;&nbsp;退货：_________</p>
</div>
</body>