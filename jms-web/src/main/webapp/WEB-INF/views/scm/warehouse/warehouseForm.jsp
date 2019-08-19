<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 岗位-->
<div id="warDiv" class="hide">
		<form id="warForm" method="POST" onsubmit="return false;" >
		<table cellspacing="0" cellpadding="4px;" border="0" class="customTable" >
				<tr>
					<td align="right">
						<font color="red">*</font>状态：
					</td>
					<td>
						<label class="inline isValidCheckbox">
								<input type="checkbox" checked="checked" sh-isValid="" role="checkbox" class="FormElement ace ace-switch ace-switch-5" />	
								<span  class="lbl"></span>
								<!-- cb-isValid和Yes和No选择框配套使用-->
								<input type="hidden" hi-isValid=""  name="status" value="1" />
						</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<font color="red">*</font>仓库名称：
					</td>
					<td>
					    <input type="hidden" name="id" >
						<input type="text" jyValidate="required"  maxlength="16" name="name" class="FormElement ui-widget-content ui-corner-all" >
					</td>
				</tr>
				<tr>
					<td align="right">
						<font color="red">*</font>仓库代码：
					</td>
					<td>
						<input type="text" jyValidate="required"  maxlength="16" name="code" class="FormElement ui-widget-content ui-corner-all" placeholder="如：001">
					</td>
				</tr>
				<tr>
					<td align="right">
						<font color="red">*</font>所属单位：
					</td>
					<td>
						<input id="orgInput" type="text" readonly value="" class="FormElement ui-widget-content ui-corner-all" onclick="showOrgComp(); return false;"/>
						<input type="hidden" name="orgId">
						<input type="hidden" name="orgName">
						<a href="#" title="清空" onclick="emptyOrgComp(); return false;" class="lrspace3 aBtnNoTD" data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a>
						<div id='orgContentList' class="menuContent ztreeMC" style="display: none; position: absolute;">
							<ul id="selectOrgTree" class="ztree preOrgTree"></ul>
						</div>
					</td>
				</tr>
				<tr>
					<td align="right">
						<font color="red">*</font>区域代码：
					</td>
					<td>
						<input type="text" jyValidate="required"  maxlength="16" name="distcode" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					<td align="right" >
						<font color="red">*</font>地址：
					</td>
					<td colspan="2">
						<span id="selectProvince">
							<select  id="provincees" style="width: 120px;" name="province" onchange="chg(this);" jyValidate="required">
								<option value="">请选择省份</option>
							</select>
						</span>
						<span id="selectCity">
							<select  id="cityes" style="width: 120px;" name="city" onchange="chg2(this);" jyValidate="required">
								<option value="">请选择市/区</option>
							</select>
						</span>
						<span id="selectCounty">
							<select  id="countyes" style="width: 120px;" name="county" jyValidate="required">
								<option value="">请选择市/县</option>
							</select>
						</span>
					</td>
				</tr>
					
				<tr>
					<td align="right">
					</td>
					<td colspan="3">
						<textarea rows="1" cols="50" name="address" multiline="true"  jyValidate="required"></textarea>
					</td>
				</tr>
				<tr>
					<td align="right">
						<font color="red">*</font>联系电话：
					</td>
					<td>
						<input type="text" jyValidate="required,mobile"  maxlength="11" name="directornm" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					<td align="right">
						<font color="red">*</font>负责人：
					</td>
					<td>
						<input type="text" jyValidate="required"  maxlength="16" name="director" class="FormElement ui-widget-content ui-corner-all"></td>
					</td>
				</tr>
				<tr>
					<td align="right">
						&ensp;邮编：
					</td>
					<td>
						<input type="text"  maxlength="16" name="zipcode" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					<td align="right">
						&ensp;描述：
					</td>
					<td  colspan="3">
						<textarea rows="3" cols="50" name="description" multiline="true"></textarea>
					</td>
				</tr>
			</table>
		</form>
</div>



<!-- 库存 -->
<div id="warehousingDiv" class="hide">
		<form id="warLoForm" method="POST" onsubmit="return false;" >
			<table class="customTable">
				<tbody>
					<tr>
						<td>&nbsp;<font color="red">*</font>请选择仓库：
							<span>
									<input type="hidden" name="warehouseidOld" id="warehouseidOld"/>
									<select  id="selectWarehouse" style="width: 120px;" name="warehouseId" jyValidate="required" onchange="chgWarehouseLocation($(this))">
									</select>
							</span>
						</td>
					</tr>
					<tr style="margin-top: 80px">
						<td>&nbsp;<font color="red">*</font>请选择仓位：
							<span>
									<select  id="selectWarehouseLocation" style="width: 120px;" name="warehouseLocation"  jyValidate="required">
									
									</select>
							</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
</div>
