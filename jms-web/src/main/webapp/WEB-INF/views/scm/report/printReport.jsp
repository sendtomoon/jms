<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
</head>
<body>
	<div id="testPrintdiv">
	<center><strong><font style='font-family:"微软雅黑";font-size:16px;'>质检单</font></strong></center>
	<p></p>
		<table border=0 cellSpacing=0 cellPadding=0 width="100%">
			<tbody>
				<tr>
					
				</tr>
				<tr>
					<td width="43%"><font>质检单号：<span>${object.reportNo}</span></font></td>
					<td width="33%"><font>入库通知单：<span>${object.entryNo}</span></font></td>
					<td width="33%"><font>供应商：<span>${object.supplierName}</span></font></td>
				</tr>
				<tr>
					<td><font>质检人：<span>${object.qcUserName}</span></font><font></font></td>
					<td><font>质检数量：<span>${object.qcNumber}</span></font></td>
					<td><font>质检重量：<span>${object.qcWeight}</span></font></td>
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
					<th>不合格数量</th>
					<th>不合格重量</th>
					<th>问题原因</th>
					<th>说明</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${list}" varStatus="status">
				<tr>
					<td>${status.index+1 }</td>
					<td>${list.code}</td>
					<td>${list.name}</td>
					<td align="right">${list.goldtype}</td>
					<td align="right">${list.ngNumber}</td>
					<td align="right">${list.ngWeight}</td>
					<td align="right">${list.qcFaqName}</td>
					<td align="right">${list.qcFaqDesc}</td>
				</tr>
				</c:forEach> 
			</tbody>
			<tfoot>
				 <tr>
					<td colspan="4">
						合计:
					</td>
					<td align="right">${object.qcNgNumber}</td>
					<td align="right">${object.qcNgWeight}g</td>
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