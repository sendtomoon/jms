<%@ page contentType="text/html;charset=UTF-8" %>
<div id="earnsdtDiv" align="center" class="hide">
	<form id="addForm" method="POST" onsubmit="return false;" enctype="multipart/form-data">
		<table cellspacing="0" cellpadding="0" border="0" class="customTable" style="width: 720px">
			<tbody>
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"><input type="hidden" name="id" ></td>
				</tr>
				<tr class="FormData">
					<td  class="CaptionTD"><font color="red">*</font>定金单号：</td>
					<td class="DataTD">&nbsp;
						<input id="preOrg" type="text" readonly value=""  class="FormElement ui-widget-content ui-corner-all" name="orderNo"/>
					</td>
					<td  class="CaptionTD"><font color="red">*</font>订单金额：</td>
					<td class="DataTD">&nbsp;
						<input id="preOrg" type="text"  value="" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" name="amount" maxlength="10" onkeyup='JY.Validate.limitDouble(this)'/>
					</td>
				</tr>
				<tr class="FormData" >
					<td class="CaptionTD">会员卡号：</td>
					<td class="DataTD">&nbsp;
						<input id="preOrg" type="text"  value=""  class="FormElement ui-widget-content ui-corner-all" name="vipCode" maxlength="35" onchange="isVip(1)"/>
					</td>
					<td class="CaptionTD">客户姓名：</td>
					<td class="DataTD">&nbsp;
					<input type="text"  maxlength="25" name="customer" class="FormElement ui-widget-content ui-corner-all"></td>
				</tr>
				 <tr class="FormData">
					<td class="CaptionTD">客户电话：</td>
					<td class="DataTD">&nbsp;
					<input type="text"  jyvalidate="mobile" name="phone" onchange="isVip(2)" class="FormElement ui-widget-content ui-corner-all" ></td>
					<td class="CaptionTD"><font color="red">*</font>有效日期：</td>
					<td class="DataTD">&nbsp;<input type="text" id="dateRender4" name="validDate" class="FormElement ui-widget-content ui-corner-all" jyValidate="required" readonly="readonly"/></td>
				</tr> 
				<tr class="FormData">
					<td class="CaptionTD">备注：</td>
					<td colspan="3">&nbsp;
						<textarea rows="10" cols="75"  name="note" multiline="true" maxlength="250"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div> 

 <div id="baseDiv" class="hide">
	<div class="col-xs-12">
		<form id="moneyForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"></td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD" ><font color="red">*</font>定金退单号：</td>
					<td class="DataTD" >
						<input type="text"  maxlength="36"  name="orderNo" class="FormElement ui-widget-content ui-corner-all"  readonly placeholder="自动生成"/ >
					</td>
					<td class="CaptionTD"><font color="red">*</font>定金单号：</td>
					<td class="DataTD">
						<input type="hidden" name="id">
						<input type="text"  maxlength="36"  name="originalNo" class="FormElement ui-widget-content ui-corner-all"  readonly/ >
					</td>
				</tr>
			</table>
			<br/>
			<div class="col-xs-12" style="height: 380px;overflow-y: scroll;border: 1px solid #ddd">
				<br/>定金款额：<span id="fontOne"></span>元<p/>
				<table id="addMoney" cellspacing="0" cellpadding="0" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<!-- <th style="width:2%"  class="center"><label><input type="checkbox" class="ace" ><span class="lbl"></span></label></th> -->
							<th style="width:2%"  class="center">序号</th>
							<th style="width:6%"  class="center">收款方式</th>
							<th style="width:6%"  class="center">银行/币种/面额</th>
							<th style="width:7%"  class="center">支票号/卡号/赠券数量</th>
							<th style="width:4%"  class="center">汇率</th>
							<th style="width:4%"  class="center">人民币金额</th>
							<th style="width:4%"  class="center">备注</th>
						</tr>
					</thead>
					<tbody></tbody>
					<tfoot></tfoot>
				</table>
			</div>
			<div class="col-xs-12" style="margin-top: 10px;margin-left: -12px;">
				<table>
					<tr>
						<td>备&nbsp;&nbsp;注：</td>
						<td colspan="11"><textarea rows="8" cols="125" name="note" multiline="true"></textarea></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>





 <div id="viewDiv" class="hide">
	<div class="col-xs-12">
		<form id="viewForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"></td>
				</tr>
				<tr class="FormData">
					<td  class="CaptionTD"><font color="red">*</font>定金单号：</td>
					<td class="DataTD">&nbsp;
						<input id="preOrg" type="text" readonly value=""  class="FormElement ui-widget-content ui-corner-all" name="orderNo"/>
					</td>
					<td  class="CaptionTD"><font color="red">*</font>订单金额：</td>
					<td class="DataTD">&nbsp;
						<input id="preOrg" type="text"  value="" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" name="amount" maxlength="10" onkeyup='JY.Validate.limitDouble(this)'/>
					</td>
				</tr>
				<tr class="FormData" >
					<td class="CaptionTD">会员卡号：</td>
					<td class="DataTD">&nbsp;
						<input id="preOrg" type="text"  value=""  class="FormElement ui-widget-content ui-corner-all" name="vipCode" maxlength="35"/>
					</td>
					<td class="CaptionTD">客户姓名：</td>
					<td class="DataTD">&nbsp;
					<input type="text"  maxlength="25" name="customer" class="FormElement ui-widget-content ui-corner-all"></td>
				</tr>
				 <tr class="FormData">
					<td class="CaptionTD">客户电话：</td>
					<td class="DataTD">&nbsp;
					<input type="text"  name="phone" class="FormElement ui-widget-content ui-corner-all" jyvalidate="mobile"></td>
					<td class="CaptionTD">收银员：</td>
					<td class="DataTD">&nbsp;
					<input type="text" name="cashierName" class="FormElement ui-widget-content ui-corner-all"></td>
				</tr> 
					<td class="CaptionTD"><font color="red">*</font>有效日期：</td>
					<td class="DataTD">&nbsp;
					<input type="text" id="dateRender5" name="validDate" class="FormElement ui-widget-content ui-corner-all" jyValidate="required" readonly="readonly"/></td>
				</tr>
			</table>
			<br/>
			<div class="col-xs-12" style="height: 300px;overflow-y: scroll;border: 1px solid #ddd">
				<br/>定金款额：<span id="fontTwo"></span>元<p/>
				<table id="tableOne" cellspacing="0" cellpadding="0" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<!-- <th style="width:2%"  class="center"><label><input type="checkbox" class="ace" ><span class="lbl"></span></label></th> -->
							<th style="width:2%"  class="center">序号</th>
							<th style="width:6%"  class="center">收款方式</th>
							<th style="width:6%"  class="center">银行/币种/面额</th>
							<th style="width:7%"  class="center">支票号/卡号/赠券数量</th>
							<th style="width:4%"  class="center">汇率</th>
							<th style="width:4%"  class="center">人民币金额</th>
							<th style="width:4%"  class="center">备注</th>
						</tr>
					</thead>
					<tbody></tbody>
					<tfoot></tfoot>
				</table>
			</div>
			<div class="col-xs-12" style="margin-top: 10px;margin-left: -12px;">
				<table>
					<tr>
						<td>备&nbsp;&nbsp;注：</td>
						<td colspan="11"><textarea rows="8" cols="125" name="note" multiline="true"></textarea></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>


 <div id="refundDiv" class="hide">
	<div class="col-xs-12">
		<form id="refundForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"></td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD" ><font color="red">*</font>定金退单号：</td>
					<td class="DataTD" >
						<input type="text"  maxlength="36"  name="orderNo" class="FormElement ui-widget-content ui-corner-all"  readonly placeholder="自动生成"/ >
					</td>
					<td class="CaptionTD"><font color="red">*</font>定金单号：</td>
					<td class="DataTD">
						<input type="hidden" name="id">
						<input type="text"  maxlength="36"  name="originalNo" class="FormElement ui-widget-content ui-corner-all"  readonly/ >
					</td>
				</tr>
				<tr class="FormData" >
					<td class="CaptionTD">会员卡号：</td>
					<td class="DataTD">
						<input id="preOrg" type="text"  value=""  class="FormElement ui-widget-content ui-corner-all" name="vipCode" maxlength="35"/>
					</td>
					<td  class="CaptionTD"><font color="red">*</font>订单金额：</td>
					<td class="DataTD">
						<input id="preOrg" type="text"  value="" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" name="amount" maxlength="10" onkeyup='JY.Validate.limitDouble(this)'/>
					</td>
				</tr>
				<tr class="FormData">
				 	<td class="CaptionTD">客户姓名：</td>
					<td class="DataTD">
					<input type="text"  maxlength="25" name="customer" class="FormElement ui-widget-content ui-corner-all"></td>
					<td class="CaptionTD">客户电话：</td>
					<td class="DataTD">
					<input type="text"  maxlength="11" name="phone" class="FormElement ui-widget-content ui-corner-all" jyvalidate="mobile"></td>
				</tr> 
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>有效日期：</td>
					<td class="DataTD"><input type="text" id="dateRender3" name="validDate" class="FormElement ui-widget-content ui-corner-all" jyValidate="required" readonly="readonly"/></td>
					<td class="CaptionTD">收银员：</td>
					<td class="DataTD"><input type="text" name="cashierName" class="FormElement ui-widget-content ui-corner-all"></td>
				</tr>
			</table>
			<br/>
			<div class="col-xs-12" style="height: 200px;overflow-y: scroll;border: 1px solid #ddd">
				<br/>定金款额：<span id="fontThere"></span>元<p/>
				<table id="tableTwo" cellspacing="0" cellpadding="0" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<!-- <th style="width:2%"  class="center"><label><input type="checkbox" class="ace" ><span class="lbl"></span></label></th> -->
							<th style="width:2%"  class="center">序号</th>
							<th style="width:6%"  class="center">收款方式</th>
							<th style="width:6%"  class="center">银行/币种/面额</th>
							<th style="width:7%"  class="center">支票号/卡号/赠券数量</th>
							<th style="width:4%"  class="center">汇率</th>
							<th style="width:4%"  class="center">人民币金额</th>
							<th style="width:4%"  class="center">备注</th>
						</tr>
					</thead>
					<tbody></tbody>
					<tfoot></tfoot>
				</table>
			</div>
			<div class="col-xs-12" style="height: 200px;overflow-y: scroll;border: 1px solid #ddd;margin-top: 4px">
				<br/>退款金额：<span id="fontFour"></span>元<p/>
				<table id="tableThere" cellspacing="0" cellpadding="0" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<!-- <th style="width:2%"  class="center"><label><input type="checkbox" class="ace" ><span class="lbl"></span></label></th> -->
							<th style="width:2%"  class="center">序号</th>
							<th style="width:6%"  class="center">收款方式</th>
							<th style="width:6%"  class="center">银行/币种/面额</th>
							<th style="width:7%"  class="center">支票号/卡号/赠券数量</th>
							<th style="width:4%"  class="center">汇率</th>
							<th style="width:4%"  class="center">人民币金额</th>
							<th style="width:4%"  class="center">备注</th>
						</tr>
					</thead>
					<tbody></tbody>
					<tfoot></tfoot>
				</table>
			</div>
			<div class="col-xs-12" style="margin-top: 10px;margin-left: -12px;">
				<table>
					<tr>
						<td>备&nbsp;&nbsp;注：</td>
						<td colspan="11"><textarea rows="5" cols="123" name="note" multiline="true"></textarea></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>





