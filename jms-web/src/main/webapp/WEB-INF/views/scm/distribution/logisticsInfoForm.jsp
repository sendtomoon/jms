<%@ page contentType="text/html;charset=UTF-8" %>
<div id="logisticsDiv" class="hide">
	<form id="logisticsForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr>
					<td style="text-align:right;">
						<font color="red">*</font>出库单号：
					</td>
					<td style="text-align:left;">
						<input type="hidden" name="id">
						<input type="hidden" name="stauts">
						<input type="hidden" name="bussnessId">
						<input type="text" jyValidate="required"  name="outboundNo" class="FormElement ui-widget-content ui-corner-all" readonly="readonly">
					</td>
					<td style="text-align:right;">
						<font color="red">*</font>收件人：
					</td>
					<td style="text-align:left;">
						<input type="text" jyValidate="required"  maxlength="15" name="recipient" class="FormElement ui-widget-content ui-corner-all" >
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">
						<font color="red">*</font>收件邮编：
					</td>
					<td style="text-align:left;">
						<input type="text" jyValidate="required"  maxlength="6" name="recPost" class="FormElement ui-widget-content ui-corner-all">
					</td>
					<td style="text-align:right;">  
						<font color="red">*</font>收件人电话：
					</td>
					<td style="text-align:left;">
						<input type="text" jyValidate="required,mobile"  maxlength="11" name="recPhone" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					<td align="right" >
						<font color="red">*</font>收件人地址：
					</td>
					<td colspan="2">
						<span id="selectRecProvince">
							<select  id="recProvince" style="width: 120px;" name="recProvince" jyValidate="required">
								<option value="">请选择省份</option>
							</select>
						</span>
						<span id="selectRecCity">
							<select  id="recCity" style="width: 120px;" name="recCity" jyValidate="required">
								<option value="">请选择市/区</option>
							</select>
						</span>
						<span id="selectRecCounty">
							<select  id="recCounty" style="width: 120px;" name="recCounty" jyValidate="required">
								<option value="">请选择市/县</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td align="right">
					</td>
					<td colspan="3">
						<textarea rows="1" cols="50" name="recAddress" jyValidate="required" multiline="true"  jyValidate="required"></textarea>
					</td>
				</tr>
					
				<tr>
					<!-- <td style="text-align:right;">
						<font color="red">*</font>物流状态：
					</td>
					<td style="text-align:left;">
						<label class="inline isValidCheckbox">
								<input type="checkbox" checked="checked" sh-isValid="" role="checkbox" class="FormElement ace ace-switch ace-switch-10" />	
								<span  class="lbl"></span>
								cb-isValid和Yes和No选择框配套使用
								<input type="hidden" hi-isValid=""  name="stauts" value="1" />
						</label>
					</td> -->
					<td style="text-align:right;">
						<font color="red">*</font>发件人：
					</td>
					<td style="text-align:left;">
						<input type="text" jyValidate="required"  maxlength="15" name="sender" class="FormElement ui-widget-content ui-corner-all" >
					</td>
					<td style="text-align:right;">
						<font color="red">*</font>发件人邮编：
					</td>
					<td style="text-align:left;">
						<input type="text" jyValidate="required"  maxlength="6" name="sendPost" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					
					<td style="text-align:right;">
						<font color="red">*</font>发件人电话：
					</td>
					<td style="text-align:left;">
						<input type="text" jyValidate="required,mobile"  maxlength="11" name="sendPhone" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					<td align="right" >
						<font color="red">*</font>发件人地址：
					</td>
					<td colspan="2">
						<span id="selectSecProvince">
							<select  id="senProvince" style="width: 120px;" name="senProvince" jyValidate="required">
								<option value="">请选择省份</option>
							</select>
						</span>
						<span id="selectSecCity">
							<select  id="senCity" style="width: 120px;" name="senCity" jyValidate="required">
								<option value="">请选择市/区</option>
							</select>
						</span>
						<span id="selectSecCounty">
							<select  id="senCounty" style="width: 120px;" name="senCounty" jyValidate="required">
								<option value="">请选择市/县</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td align="right">
					</td>
					<td colspan="3">
						<textarea rows="1" cols="50" name="sendAddress" multiline="true"  jyValidate="required"></textarea>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">
						<font color="red">*</font>快递公司：
					</td>
					<td style="text-align:left;">
						<span id="selectExpress"><select id="express" name="express" style="width: 180px;" jyValidate="required"></select></span>
					</td>
					<td style="text-align:right;">
						<font color="red">*</font>快递单号：
					</td>
					<td style="text-align:left;">
						<input type="text" jyValidate="required"  maxlength="30" name="expressNo" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">
						<font color="red">*</font>物流信息：
					</td>
				</tr>
			</table>
	</form>
</div>