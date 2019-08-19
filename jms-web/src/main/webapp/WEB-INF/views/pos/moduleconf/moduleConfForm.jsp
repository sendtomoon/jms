<%@ page contentType="text/html;charset=UTF-8" %>
<div id="ModuleConfDiv" class="hide">
	<form id="ModuleConfForm" method="POST" onsubmit="return false;" >
	  <table cellspacing="0" cellpadding="4px;" border="0"class="customTable">
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>状态：</td>
			<td class="DataTD"><label class="inline isValidCheckbox">
					<input type="checkbox" checked="checked"  sh-isValid=""role="checkbox" class="FormElement ace ace-switch ace-switch-5" />
					<span class="lbl"></span> <!-- cb-status和Yes和No选择框配套使用--> <input type="hidden" hi-isValid="" name="status" value="1" />
			</label></td>
		</tr>
		<tr class="FormData">
			<input type="hidden" name="id">
			<td class="CaptionTD"><font color="red">*</font>系统代码：</td>
			<td class="DataTD"><input type="text" jyValidate="required"  name="sysCode"class="FormElement ui-widget-content ui-corner-all"></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>系统名称：</td>
			<td class="DataTD"><input type="text" jyValidate="required"  name="sysName" class="FormElement ui-widget-content ui-corner-all"></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>模块代码：</td>
			<td class="DataTD"><input  type="text" jyValidate="required"  name="moduleCode"  maxlength="18" /></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>模块名称：</td>
			<td class="DataTD"><input name="moduleName" type="text"  jyValidate="required" class="FormElement ui-widget-content ui-corner-all" /></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD">备注：</td>
			<td  class="DataTD" colspan="3"><textarea rows="2" maxlength="59" cols="19"   name="remarks" ></textarea></td>
		</tr>
	</table>
	</form>
</div>
