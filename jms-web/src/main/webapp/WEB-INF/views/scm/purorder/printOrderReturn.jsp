<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html >
<html lang="en">
<head>
</head>
<body>
	<div id="testPrintdiv">
	<center><strong><font style='font-family:"微软雅黑";font-size:16px;'>退货单</font></strong></center>
	<p></p>
			<table cellspacing="0" border="0" class="customTable"> 
				<tr >
					<td  width="40%">订单编号：<span>${object.returnNo}</span>	</td>
					<td  width="33%"><font>拨入机构：<span>${object.dialinOrgName}</span></font>	
					</td>	
					<td >拨入仓库：
						<span>${object.dialinWarehouseNaem}</span>	
					</td>
				</tr>
				<tr>
					<td >拨出机构：
						<span>${object.dialoutOrgName}</span>	
					</td>	
					<td >拨出仓库：
						<span>${object.dialoutWarehouseName}</span>	
					</td>
					<td >退货原因：
						<span>${object.returnCause}</span>	
					</td>
				</tr>		
			</table>
			<table id="orderTable" border=1 cellSpacing=0 cellPadding=1 width="100%"
			style="border-collapse: collapse" bordercolor="#333333">
				<thead>
					<th style="width:5%"  class="center">条码</th>
 					<th style="width:6%"  class="center">名称</th>	 
					<th style="width:4%"  class="center">数量</th>
					<th style="width:4%"  class="center">辅石数量/粒数</th>
					<th style="width:4%"  class="center">重量</th>
					<th style="width:4%"  class="center">单价</th>
					<th style="width:8%"  class="center">拨出单位</th>
					<th style="width:8%"  class="center">拨出仓库</th>
					<th style="width:8%"  class="center">备注</th>
				</thead>
				<tbody>
					<c:forEach var="list" items="${list}" varStatus="status">
						<tr>
							<td>${list.code }</td>
							<td>${list.name}</td>
							<td>${list.count}</td>
							<td>${list.count2}</td>
							<td>${list.weight}</td>
							<td>${list.price}</td>
							<td>${list.dialoutOrgName}</td>
							<td>${list.dialoutWarehouseName}</td>
							<td>${list.remarks}</td>
						</tr>
					</c:forEach> 
				</tbody>
			</table>
		<p>备注：${object.remarks}</p> 
		<p></p>
		<table border=0 cellSpacing=0 cellPadding=0 width="100%">
			<tbody>
			    <tr>
					<td width="33%"><font>创建人：<span>${object.createName}</span></font></td>
					<td width="33%"><font>修改人：<span>${object.updateName}</span></font></td>
					<td><font>审核人：<span>${object.checkName}</span></font></td>
				</tr>
				<tr>
					<td><font>创建时间：<span><fmt:setLocale value="zh"/> <fmt:formatDate value="${object.createTime}" type="both"/></span></font></td>
					<td><font>修改时间：<span><fmt:setLocale value="zh"/> <fmt:formatDate value="${object.updateTime}" type="both"/></span></font></td>
					<td><font>审核时间：<span><fmt:setLocale value="zh"/> <fmt:formatDate value="${object.checkTime}" type="both"/></span></font></td>
				</tr> 
			</tbody>
		</table>
	</div>
</body>