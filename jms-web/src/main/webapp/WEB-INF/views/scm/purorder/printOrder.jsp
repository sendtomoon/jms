<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html >
<html lang="en">
<head>
</head>
<body>
	<div id="testPrintdiv">
	<center><strong><font style='font-family:"微软雅黑";font-size:16px;'><c:if test="${type==2}">采购单</c:if><c:if test="${type==1}">要货单</c:if></font></strong></center>
	<p></p>
			<table cellspacing="0" border="0" class="customTable"> 
				<tr >
					<td  width="40%">订单编号：<span>${object.orderNo}</span>	</td>
					<td  width="33%"><font>要求到货日期：<span><fmt:setLocale value="zh"/><fmt:formatDate value="${object.arrivalDate}" /></span></font>	
					</td>	
					<c:if test="${type==2 }">
						<td >经办人：
							<span>${object.operatorName}</span>	
						</td>
					</c:if>	
					<c:if test="${type==1 }">
						<td >要货单位：
							<span>${object.orgLongName}</span>	
						</td>			
					</c:if>
				</tr>
			</table>
			<table id="orderTable" border=1 cellSpacing=0 cellPadding=1 width="100%"
			style="border-collapse: collapse" bordercolor="#333333">
					<thead>
					<tr>
						<th >款号</th>
						<th >材质</th>	
						<th >金重范围</th>
						<th >主钻石重</th>
						<th >钻石净度</th>
						<th >钻石颜色</th>
						<th >主石切工</th>
						<th >圈口</th>
						<th >数量</th>
						<c:if test="${type==2 }">
							<th>供应商</th>
							<th>工厂款号</th>
							<th>基本工费</th>
							<th>附加工费</th>
							<th>其他工费</th>
						</c:if>
					</tr>
					</thead>
						<tbody>
							<c:forEach var="list" items="${list}" varStatus="status">
								<tr>
									<td>${list.mdCodeName }</td>
									<td>${list.gMaterial}</td>
									<td>${list.gWeight}</td>
									<td>${list.dWeight}</td>
									<td>${list.dClarity}</td>
									<td>${list.dColor}</td>
									<td>${list.cut}</td>
									<td>${list.circel}</td>
									<td>${list.numbers}</td>
									<c:if test="${type==2 }">
										<td>${list.franchiseeName}</td>
										<td>${list.mdtlCodeName}</td>
										<td>${list.basicCost}</td>
										<td>${list.additionCost}</td>
										<td>${list.otherCost}</td>
									</c:if>
								</tr>
							</c:forEach> 
						</tbody>
						<tfoot>
							<td>合计</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td>${object.totalNum}</td>
							<c:if test="${type==2 }">
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</c:if>
						</tfoot>
				</table>
		<p>备注：${object.description}</p> 
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