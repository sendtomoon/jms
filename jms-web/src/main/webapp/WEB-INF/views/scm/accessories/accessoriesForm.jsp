<%@ page contentType="text/html;charset=UTF-8"%>
<div id="accessoriesauDiv" class="hide">
<form id="accessoriesDataForm" method="POST" onsubmit="return false;">
	<table cellspacing="0" cellpadding="4px;" border="0"
		class="customTable">
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>状态：</td>
			<td class="DataTD"><label class="inline isValidCheckbox">
					<input type="checkbox" checked="checked"  sh-isValid=""
					role="checkbox" class="FormElement ace ace-switch ace-switch-5" />
					<span class="lbl"></span> <!-- cb-status和Yes和No选择框配套使用--> <input
					type="hidden" hi-isValid="" name="status" value="1" />
			</label></td>
			<td class="CaptionTD"><font color="red">*</font>主石标志：</td>
			<td class="DataTD"><label class="inline isValidCheckbox2">
					<input type="checkbox" checked="checked" 
					sh-isValid="" role="checkbox"
					class="FormElement ace ace-switch ace-switch-10" /> <span
					class="lbl"></span> <!-- cb-stoneFlag和是和否选择框配套使用--> <input
					type="hidden" hi-isValid="" name="stoneFlag" value="0" />
			</label></td>
		</tr>
		<tr class="FormData">
			<input type="hidden" name="id">
			<input type="hidden" name="productId">
			<td class="CaptionTD"><font color="red">*</font>顺序号：</td>
			<td class="DataTD"><input type="text" name="sort"
				jyValidate="dou" /></td>
			<td class="CaptionTD">石单位：</td>
			<td class="DataTD"><span id="selectStoneUnit"><select  id="stoneUnit" name="stoneUnit" style="width:157px;"></select></span></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>石代码：</td>
			<td class="DataTD"><input type="text" jyValidate="required"
				maxlength="16" name="stoneCode"
				class="FormElement ui-widget-content ui-corner-all"></td>
			<td class="CaptionTD"><font color="red">*</font>石名称：</td>
			<td class="DataTD"><input name="stoneName" type="text"
				jyValidate="required"
				class="FormElement ui-widget-content ui-corner-all" /></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>石重：</td>
			<td class="DataTD"><input  type="text" onkeyup="JY.Validate.limitAmtNum(this)" jyValidate="required"  name="stoneWeight"  /></td>
			<td class="CaptionTD"><font color="red">*</font>石数：</td>
			<td class="DataTD"><input name="stoneCount" type="text"
				onkeyup="JY.Validate.limitNum(this)" jyValidate="dou" class="FormElement ui-widget-content ui-corner-all" />
			</td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>采购单价（元）：</td>
			<td class="DataTD"><input name="purPrice" type="text"
				onkeyup="JY.Validate.limitAmtNum(this)" jyValidate="dou" class="FormElement ui-widget-content ui-corner-all" /></td>
			<td class="CaptionTD"><font color="red">*</font>采购价值（元）：</td>
			<td class="DataTD"><input type="text" name="purcal"
				onkeyup="JY.Validate.limitAmtNum(this)" jyValidate="dou" /></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>成本单价（元）：</td>
			<td class="DataTD"><input name="costPrice" type="text"
				onkeyup="JY.Validate.limitAmtNum(this)" jyValidate="dou" class="FormElement ui-widget-content ui-corner-all" /></td>
			<td class="CaptionTD"><font color="red">*</font>成本价值（元）：</td>
			<td class="DataTD"><input type="text" name="costCal"
				onkeyup="JY.Validate.limitAmtNum(this)" jyValidate="dou" /></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>镶石工费（元）：</td>
			<td class="DataTD"><input name="jeweler" type="text"
				onkeyup="JY.Validate.limitAmtNum(this)" jyValidate="dou" class="FormElement ui-widget-content ui-corner-all" /></td>
			<td class="CaptionTD">计算方式：</td>
			<td class="DataTD"><span id="selectFormula"><select  id="formula" name="formula" style="width:157px;"></select></span></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD">石形：</td>
			<td class="DataTD"><span id="selectShape"><select  id="stoneShape" name="stoneShape" style="width:157px;"></select></span></td>
			<td class="CaptionTD">石重区间：</td>
			<td class="DataTD"><span id="selectstoneWeightarea"><select  id="stoneWeightarea" name="stoneWeightarea" style="width:157px;"></select></span></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD">净度：</td>
			<td class="DataTD"><span id="selectClarity"><select  id="clarity" name="clarity" style="width:157px;"></select></span></td>
			<td class="CaptionTD">颜色：</td>
			<td class="DataTD"><span id="selectColor"><select id="color" name="color" style="width:157px;"></select></span></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD">切工：</td>
			<td class="DataTD"><span id="selectCut"><select  id="cut" name="cut" style="width:157px;"></select></span></td>
			<td class="CaptionTD">石包号：</td>
			<td class="DataTD"><input type="text" name="stonePkgno" /></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD">描述：</td>
			<td class="DataTD" colspan="3"><textarea id="description" rows="2" cols="75"  name="description" ></textarea></td>
		</tr>
	</table>
</form>
</div>


