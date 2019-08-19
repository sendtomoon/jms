<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
<%@include file="../../common/includeBaseSet.jsp" %>
<script language="javascript" src="${jypath}/static/js/lodop/LodopFuncs.js"></script>
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
				<td width="43%"><font>入库通知单：<span>RKCN2017030300111</span></font></td>
				<td width="33%"><font>采购单号：<span>CGCN2017030300222</span></font></td>
				<td><font>收货人：<span>张武泳</span></font><font></font></td>
			</tr>
			<tr>
				<td><font>拨入单位：<span>深圳金一</span></font></td>
				<td><font>供应商：<span>金一智造</span></font></td>
				
				
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
					<th>金重</th>
					<th>石重</th>
					<th>基础工费</th>
					<th>附加工费</th>
					<th>其它工费</th>
					<th>采购成本</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td>
					<td>千足金钻戒女戒</td>
					<td>千足金</td>
					<td align="right">1</td>
					<td align="right">4</td>
					<td align="right">4</td>
					<td align="right">3</td>
					<td align="right">1</td>
					<td align="right">50</td>
					<td align="right">25</td>
					<td align="right">25</td>
					<td align="right">2000</td>
				</tr>
				<tr>
					<td>2</td>
					<td>千足金钻戒女戒</td>
					<td>千足金</td>
					<td align="right">1</td>
					<td align="right">4</td>
					<td align="right">4</td>
					<td align="right">3</td>
					<td align="right">1</td>
					<td align="right">50</td>
					<td align="right">25</td>
					<td align="right">25</td>
					<td align="right">1000</td>
				</tr>
				<tr>
					<td>3</td>
					<td>千足金钻戒耳钉</td>
					<td>千足金</td>
					<td align="right">1</td>
					<td align="right">3</td>
					<td align="right">3</td>
					<td align="right">2</td>
					<td align="right">1</td>
					<td align="right">30</td>
					<td align="right">10</td>
					<td align="right">10</td>
					<td align="right">500</td>
				</tr>
				<tr>
					<td>4</td>
					<td>千足金钻戒耳钉</td>
					<td>千足金</td>
					<td align="right">1</td>
					<td align="right">3</td>
					<td align="right">3</td>
					<td align="right">2</td>
					<td align="right">1</td>
					<td align="right">30</td>
					<td align="right">10</td>
					<td align="right">10</td>
					<td align="right">500</td>
				</tr>
				<tr>
					<td>5</td>
					<td>千足金钻戒耳钉</td>
					<td>千足金</td>
					<td align="right">1</td>
					<td align="right">3</td>
					<td align="right">3</td>
					<td align="right">2</td>
					<td align="right">1</td>
					<td align="right">30</td>
					<td align="right">10</td>
					<td align="right">10</td>
					<td align="right">500</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="3">
						合计:
					</td>
					<td align="right">5</td>
					<td align="right">17g</td>
					<td align="right">17g</td>
					<td align="right">12g</td>
					<td align="right">5g</td>
					<td align="right">￥190</td>
					<td align="right">￥80</td>
					<td align="right">￥80</td>
					<td align="right">￥4500</td>
				</tr>
			</tfoot>
		</table>
	</div>
	<p>备注：</p>
	<p></p>

	<table border=0 cellSpacing=0 cellPadding=0 width="100%">
		<tbody>
			<tr>
				<td width="43%"><font>创建人：<span>张武泳</span></font></td>
				<td width="33%"><font>修改人：<span>文龙</span></font></td>
				<td><font>审核人：<span>黄锋华</span></font></td>
			</tr>
			<tr>
				<td><font>创建时间：<span>2017-03-01</span></font></td>
				<td><font>修改时间：<span>2017-03-02</span></font><font></font></td>
				<td><font>审核时间：<span>2017-03-03</span></font></td>
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