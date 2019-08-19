<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
</head>
<body>
	<div id="testPrintdiv">
	<center><strong><font style='font-family:"微软雅黑";font-size:16px;'>入库通知单</font></strong></center>
	<p></p>
		<table border=0 cellSpacing=0 cellPadding=0 width="100%">
			<tbody>
				<tr>
					
				</tr>
				<tr>
					<td><font>入库通知单：<span id="noticeNoSpan">${object.noticeNo}</span></font></td>
					<td><font>采购单号：<span>${object.purchaseNo}</span></font></td>
					<td><font>收货人：<span>${object.receiverName}</span></font><font></font></td>
				</tr>
				<tr>
					<td><font>拨入单位：<span>${object.orgName}</span></font></td>
					<td><font>供应商：<span>${object.surpplyFullName}</span></font></td>
					
					
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
					<th>材质</th>
					<th>数量</th>
					<th>重量</th>
					<th>实重</th>
					<th>基础工费</th>
					<th>附加工费</th>
					<th>其它工费</th>
					<th>采购成本</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${list}" varStatus="status">
				<tr>
					<td>${status.index+1 }</td>
					<td>${list.name}</td>
					<td>${list.goldTypeName}</td>
					<td align="right">${list.count}</td>
					<td align="right">${list.requireWt}</td>
					<td align="right">${list.actualWt}</td>
					<td align="right">${list.basicCost}</td>
					<td align="right">${list.addCost}</td>
					<td align="right">${list.otherCost}</td>
					<td align="right">${list.costPrice}</td>
				</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="3">
						合计:
					</td>
					
					<td align="right">${object.count}</td>
					<td align="right">${object.requireWt}g</td>
					<td align="right">${object.actualWt}g</td>
					<td align="right">￥${object.basicCost}</td>
					<td align="right">￥${object.addCost}</td>
					<td align="right">￥${object.otherCost}</td>
					<td align="right">￥${object.costPrice}</td>
				
				</tr>
			</tfoot>
		</table>
	</div>
	<p>备注：${object.note}</p>
	<p></p>

	<table border=0 cellSpacing=0 cellPadding=0 width="100%">
		<tbody>
			<tr>
				<td width="43%"><font>创建人：<span>${object.createUser}</span></font></td>
				<td width="33%"><font>修改人：<span>${object.updateUser}</span></font></td>
				<td><font>审核人：<span>${object.checkUser}</span></font></td>
			</tr>
			<tr>
				<td><font>创建时间：<span>${object.crTime}</span></font></td>
				<td><font>修改时间：<span>${object.uTime}</span></font><font></font></td>
				<td><font>审核时间：<span>${object.cTime}</span></font></td>
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