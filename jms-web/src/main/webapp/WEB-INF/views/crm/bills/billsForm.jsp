<%@ page contentType="text/html;charset=UTF-8"%>
<head> 　　
	<style type="text/css"> 　　
	    .posBill{width: 100%;}
		.posBill .FormData > td{padding-bottom: 0px;padding-top: 0px;font-family: "Open Sans";}
		.posBill .CaptionTD{color: #666;font-size: 12px;text-align: right;}
	</style> 　　
</head>
<div id="posbillDiv" class="hide">
	<div class="col-xs-12">
		<form id="posbillForm" method="POST" onsubmit="return false;" class="posBill">
			<table id="posbillTab" cellspacing="0" cellpadding="4px;" border="0" >
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"><input type="hidden" name="id"></td>
				</tr>
				<tr class="FormData">
					<td class="Captiontd">收银员：</td>
					<td class="Datatd">
						<input type="text" maxlength="36" name="cashierName"  readonly>
						<input type="hidden" name="cashier"/>
					</td>
					<td class="Captiontd">VIP卡号：</td>
					<td class="Datatd">
						<input type="text" maxlength="36" name="vipCode" / >
					</td>
					<td class="Captiontd">顾客姓名：</td>
					<td class="Datatd">
						<input type="text" maxlength="36" name="customer"  />
					</td>
					<td class="Captiontd">定金单号：</td>
					<td class="Datatd">
						<input type="text" maxlength="36" name="earnest" >
					</td>
				</tr>
				
				<tr class="FormData">
					<td class="Captiontd">营业员1：</td>
					<td class="Datatd">
						<input type="text" maxlength="36" name="assistant1" >
						<input type="text" style="width:30px" name="promrate1" />
					</td>
					<td class="Captiontd">主管1：</td>
					<td class="Datatd">
						<input type="text" maxlength="36" name="leader1" >
					</td>
					<td class="Captiontd">销售日期：</td>
					<td class="Datatd">
						<input type="text" maxlength="36" name="saleTime" readonly/>
					</td>
					<td class="CaptionTD">业绩分店：</td>
					<td class="DataTD">
						<input type="text" maxlength="36" name="storeId"  readonly/>
					</td>
				</tr>
				<tr class="FormData">
					<td class="Captiontd">营业员2：</td>
					<td class="Datatd">
						<input type="text" maxlength="36" name="assistant2" >
						<input type="text" style="width:30px" name="promrate2"/>
					</td>
					<td class="Captiontd">主   管2：</td>
					<td class="Datatd">
						<input type="text" maxlength="36" name="leader2" >
						</select>
					</td>
					<td class="Captiontd">收款金额：</td>
					<td class="Datatd">
						<input type="text" maxlength="36" name="actualAmt"   />
					</td>
				</tr>
				<tr>
					<td >备注：</td>
					<td colspan="7">
<!-- 						<input type="text" name="note"/> -->
						<textarea rows="1" cols="130" name="note" multiline="true"></textarea>
					</td>
					
				</tr>
			</table>
			<div id="posbillAddDiv" style="height: 450px; overflow-y: scroll; border: 1px solid #ddd; width: 100%">
				<table id="posbillAdd" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th style="width:3%" class="center" id="checkId">
								<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
							</th>
							<th style="width:7%"  class="center">商品条码</th>
							<th style="width:7%"  class="center">货品描述</th>
							<th style="width:4%"  class="center">牌面价</th>
							<th style="width:6%"  class="center">工费 元/件</th>
							<th style="width:4%"  class="center">积分优惠</th>
							<th style="width:4%"  class="center">价格优惠</th>
							<th style="width:5%"  class="center">赠品</th>
							<th style="width:5%"  class="center">件数</th>
							<th style="width:5%"  class="center">金额</th>
						</tr>
					</thead>
					<tbody></tbody>
					<tfoot></tfoot>
				</table>
			</div>
			</form>
			<div class="col-xs-12" id="offsetDiv" class="hide" style="margin-top: 10px; margin-left: -12px;">
				<table>
					<tr>
						<td>冲单原因：</td>
						<td colspan="11"><textarea rows="1" cols="135" name="offsetcause" multiline="true"></textarea></td></tr>
				</table>
			</div>
	</div>
</div>

</div>
</div>
