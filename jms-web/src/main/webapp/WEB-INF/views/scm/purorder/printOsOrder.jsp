<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
</head>
<body>
	<div id="testPrintdiv">
	<center><strong><font style='font-family:"微软雅黑";font-size:16px;'>出库单</font></strong></center>
	<p></p>
		<table border=0 cellSpacing=0 cellPadding=0 width="100%">
			<tbody>
				<tr>
					
				</tr>
				<tr>
					<td width="33%"><font>出库单号：<span>${object.outBoundNo}</span></font></td>
					<td width="33%"><font>订单单号：<span>${object.orderNum}</span></font></td>
					<td><font>拨入单位：<span>${object.inOrgLongName}</span></font></td>
				</tr>
				<tr>
					<td><font>拨出单位：<span>${object.outOrgLongName}</span></font><font></font></td>
					<td><font>出库类型：<span>${object.typeName}</span></font></td>
				</tr>
			</tbody>
		</table>
	<br>
	<div>
		<table border=1 cellSpacing=0 cellPadding=1 width="100%"
			style="border-collapse: collapse" bordercolor="#333333">
			<thead>
				<tr>
					<th>序号</th>
					<th>名称</th>
					<th>条码</th>
					<th>证书号</th>
					<th>说明</th>
					<th>数量</th>
					<th>重量</th>
					<th>成本</th>
					<c:if test="${type==2 }">
					<th>工费</th>
					<th>挂签费</th>
					</c:if>
					<th>批发价</th>
					<th>牌价</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${list}" varStatus="status">
				<tr>
					<td>${status.index+1 }</td>
					<td>${list.name}</td>
					<td>${list.code}</td>
					<td align="right">${list.cerno}</td>
					<td align="right">${list.proRemarks}</td>
					<td align="right">${list.num}</td>
					<td align="right">${list.weight}</td>
					<td align="right">${list.costing}</td>
					<c:if test="${type==2 }">
					<td align="right">${list.wage}</td>
					<td align="right">${list.tageprice}</td>
					</c:if>
					<td align="right">${list.pradeprice}</td>
					<td align="right">${list.price}</td>
				</tr>
				</c:forEach> 
			</tbody>
			<tfoot>
				 <tr>
					<td colspan="5">
						合计:
					</td>
					<td align="right">${object.num}</td>
					<td align="right">${object.weight}g</td>
					<td align="right">￥${object.totalCosing}</td>
					<c:if test="${type==2 }">
					<td align="right">￥${object.totalWage}</td>
					<td align="right">￥${object.totalTageprice}</td>
					</c:if>
					<td align="right">￥${object.totalPradeprice}</td> 
					<td align="right">￥${object.totalPrice}</td> 
				</tr> 
			</tfoot>
		</table>
	</div>
 	<p>备注：${object.remarks}</p> 
	<p></p>

	<table border=0 cellSpacing=0 cellPadding=0 width="100%">
		<tbody>
		    <tr>
				<td width="43%"><font>创建人：<span>${object.createName}</span></font></td>
				<td width="33%"><font>修改人：<span>${object.updateName}</span></font></td>
				<td><font>审核人：<span>${object.checkName}</span></font></td>
			</tr>
			<tr>
				<td><font>创建时间：<span>${object.printCreate}</span></font></td>
				<td><font>修改时间：<span>${object.printUpdate}</span></font><font></font></td>
				<td><font>审核时间：<span>${object.printCheck}</span></font></td>
			</tr> 
		</tbody>
	</table>
	<p></p>
	<span style="font-family:'楷体';font-style:italic;font-siz:12px;">兹收到上述所列货品无误，已核对各项资料和数据。</span><br/>
	<span style="font-family:'楷体';font-style:italic;font-siz:12px;">结算重量以实重为准，产品质量问题3个工作日内更换合格产品。</span>
	<br/>
	<br/>
	<br/>
	<p>发货：_________&nbsp;&nbsp;&nbsp;采购：_________&nbsp;&nbsp;&nbsp;收货：_________&nbsp;&nbsp;&nbsp;</p>
</div>
</body>