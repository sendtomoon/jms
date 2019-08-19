<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
</head>
<body>
	<div id="testPrintdiv">
	<center><strong><font style='font-family:"微软雅黑";font-size:16px;'>流转单</font></strong></center>
	<p></p>
		<table border=0 cellSpacing=0 cellPadding=0 width="100%">
			<tbody>
				<tr>
					
				</tr>
				<tr>
					<td width="43%"><font>流转单号：<span>${object.flowNo}</span></font></td>
					<td width="33%"><font>入库通知单：<span>${object.noticeNo}</span></font></td>
					<td width="33%"><font>发货部门：<span>${object.handoverOrgName}</span></font></td>
				</tr>
				<tr>
					<td><font>发货人：<span>${object.handoverName}</span></font><font></font></td>
					<td><font>收货部门：<span>${object.receiveOrgName}</span></font></td>
					<td><font>收货人：<span>${object.receiver}</span></font></td>
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
					<th>条码</th>
					<th>名称</th>
					<th>材质</th>
					<th>数量</th>
					<th>重量</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${list}" varStatus="status">
				<tr>
					<td>${status.index+1 }</td>
					<td>${list.code}</td>
					<td>${list.name}</td>
					<td align="right">${list.goldName}</td>
					<td align="right">${list.count}</td>
					<td align="right">${list.weight}</td>
				</tr>
				</c:forEach> 
			</tbody>
			<tfoot>
				 <tr>
					<td colspan="4">
						合计:
					</td>
					<td align="right">${list[0].totalCount}</td>
					<td align="right">${list[0].totalWeight}g</td>
				</tr> 
			</tfoot>
		</table>
	</div>
	<p>备注：${object.note}</p> 
	<p></p>
	<p></p>
	<span style="font-family:'楷体';font-style:italic;font-siz:12px;">兹收到上述所列货品无误，已核对各项资料和数据。</span><br/>
	<br/>
	<br/>
	<br/>
	<p>发货人：_________&nbsp;&nbsp;&nbsp;收货人：_________&nbsp;&nbsp;&nbsp;</p>
</div>
</body>