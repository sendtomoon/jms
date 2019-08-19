<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
</head>
<body>
	<div id="testPrintdiv">
	<center><strong><font style='font-family:"微软雅黑";font-size:16px;'>入库单</font></strong></center>
	<p></p>
		<table border=0 cellSpacing=0 cellPadding=0 width="100%">
			<tbody>
				<tr>
					
				</tr>
				<tr>
					<td width="50%"><font>入库单号：<span>${object.enteryno}</span></font></td>
					<td width="50%"><font>订单单号：<span>${object.purno}</span></font></td>
				</tr>
				<tr>
					<td><font>仓库：<span>${object.warehouseName}</span></font><font></font></td>
					<td><font>仓位：<span>${object.locationName}</span></font></td>
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
					<th>计价方式</th>
					<th>数量</th>
					<th>重量</th>
					<th>称差</th>
					<th>牌价</th>
					<th>采购成本</th>
					<c:if test="${type==1 }">
					<th>财务成本</th>
					</c:if>
					<c:if test="${type==2 }">
					<th>销售价</th>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${list}" varStatus="status">
				<tr>
					<td>${status.index+1 }</td>
					<td>${list.name}</td>
					<td>${list.code}</td>
					<c:if test="${list.feeType==2 }">
					<td align="right">件</td>
					</c:if>
					<c:if test="${list.feeType==1 }">
						<td align="right">克</td>
					</c:if>
					<td align="right">${list.num}</td>
					<td align="right">${list.weight}</td>
					<td align="right">${list.diffweight}</td>
					<td align="right">${list.price}</td>
					<td align="right">${list.purcost}</td>
					<c:if test="${type==1 }">
					<td align="right">${list.finacost}</td>
					</c:if>
					<c:if test="${type==2 }">
					<td align="right">${list.saleprice}</td>
					</c:if>
				</tr>
				</c:forEach> 
			</tbody>
			<tfoot>
				 <tr>
					<td colspan="4">
						合计:
					</td>
					<td align="right">${object.totalNums}</td>
					<td align="right">${object.totalWeight}g</td>
					<td align="right">${object.diffweight}g</td>
					<td align="right">￥${object.totalprice}</td>
					<td align="right">￥${object.purcost}</td>
					<c:if test="${type==1 }">
					<td align="right">￥${object.finacost}</td>
					</c:if>
					<c:if test="${type==2 }">
					<td align="right">￥0</td>
					</c:if>
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