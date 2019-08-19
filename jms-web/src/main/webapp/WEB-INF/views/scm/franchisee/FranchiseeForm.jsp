<%@ page contentType="text/html;charset=UTF-8" %>
<div id="FranchiseeauDiv" class="hide">
	<form id="FranchiseeauForm" method="POST" onsubmit="return false;" >
	  <table cellspacing="0" cellpadding="4px;" border="0"class="customTable">
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>状态：</td>
			<td class="DataTD"><label class="inline isValidCheckbox">
					<input type="checkbox" checked="checked"  sh-isValid=""role="checkbox" class="FormElement ace ace-switch ace-switch-5" />
					<span class="lbl"></span> <!-- cb-status和Yes和No选择框配套使用--> <input type="hidden" hi-isValid="" name="status" value="1" />
			</label></td>
			<td class="CaptionTD"><font color="red">*</font>名称：</td>
			<td class="DataTD" ><input type="text" jyValidate="required" maxlength="32" name="longName" class="FormElement ui-widget-content ui-corner-all"></td>
		</tr>
		<tr class="FormData">
			<input type="hidden" name="id">
			<td class="CaptionTD"><font color="red">*</font>简称：</td>
			<td class="DataTD"><input type="text" jyValidate="required" maxlength="16" name="name"class="FormElement ui-widget-content ui-corner-all"></td>
			<td class="CaptionTD"><font color="red">*</font>代码：</td>
			<td class="DataTD"><input type="text" name="code" maxlength="32" jyValidate="required" /></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>会计代码：</td>
			<td class="DataTD"><input type="text" jyValidate="required" maxlength="32" name="accountCode" class="FormElement ui-widget-content ui-corner-all"></td>
			<td class="CaptionTD"><font color="red">*</font>法人代表：</td>
			<td class="DataTD"><input name="legalName" type="text" jyValidate="required" maxlength="16" class="FormElement ui-widget-content ui-corner-all" /></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>营业执照：</td>
			<td class="DataTD"><input  type="text" jyValidate="ennum"  name="licenseNum"  maxlength="18" /></td>
			<td class="CaptionTD"><font color="red">*</font>税务登记号：</td>
			<td class="DataTD"><input name="taxNum" type="text" jyValidate="ennum"  maxlength="15" class="FormElement ui-widget-content ui-corner-all" />
			</td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>邮箱：</td>
			<td class="DataTD"><input name="email" type="text" maxlength="30" jyValidate="email" class="FormElement ui-widget-content ui-corner-all" /></td>
			<td class="CaptionTD"><font color="red">*</font>联系电话：</td>
			<td class="DataTD"><input type="text" maxlength="12" name="contactnum" jyValidate="phone" /></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>开户名称：</td>
			<td class="DataTD"><input name="accountName" type="text" jyValidate="required" maxlength="16" class="FormElement ui-widget-content ui-corner-all" /></td>
			<td class="CaptionTD"><font color="red">*</font>开户行：</td>
			<td class="DataTD"><input type="text" name="bankName" maxlength="16" jyValidate="required" /></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>账号：</td>
			<td class="DataTD"><input name="accountNum" type="text" maxlength="18" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" /></td>
			<td class="CaptionTD"><font color="red">*</font>联系人：</td>
			<td class="DataTD"><input name="contactor" type="text" maxlength="16" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" /></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD">地址：</td>
			<td colspan="3">
					<span id="selectProvince">
						<select  id="provincees" style="width: 120px;" name="province" jyValidate="required"></select>
					</span>
					<span id="selectCity">
						<select  id="cityes" style="width: 120px;" name="city"  jyValidate="required"></select>
					</span>
					<span id="selectCounty">
						<select  id="countyes" style="width: 120px;" name="county" jyValidate="required"></select>
					</span>
				</td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"></td>
			<td  class="DataTD" colspan="3"><textarea rows="2" maxlength="59" cols="68" jyValidate="required"  name="address" ></textarea></td>
		</tr>
	</table>
	</form>
</div>
