<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 岗位-->
<div id="warLoDiv" class="hide">
	<form id="LocationForm" method="POST" onsubmit="return false;" >
		<table cellspacing="0" cellpadding="0" border="0" class="customTable">
			<tbody>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>状态：</td>
					<td class="DataTD">&nbsp;
						<label class="inline isValidCheckbox">
							<input type="checkbox" checked="checked" sh-isValid="" role="checkbox" class="FormElement ace ace-switch ace-switch-5" />	
							<span  class="lbl"></span>
							<!-- cb-isValid和Yes和No选择框配套使用-->
							<input type="hidden" hi-isValid=""  name="status" value="1" />
							<input type="hidden" id="id" name="id" >
						</label>
					</td>
				</tr>	
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>仓位名称：</td>
					<td class="DataTD">&nbsp;
					<input type="text" jyValidate="required"  maxlength="16" name="name" class="FormElement ui-widget-content ui-corner-all" ></td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>仓位代码：</td>
					<td class="DataTD ">&nbsp;
						<input type="text" jyValidate="required"  maxlength="16" name="code" class="FormElement ui-widget-content ui-corner-all" placeholder="如：001">
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>所属仓库：</td>
					<td class="DataTD ">&nbsp;
						<input type="text"  id="warehouseName" name="warehouseName" class="FormElement ui-widget-content ui-corner-all" readonly="readonly">
						<input type="hidden"  id="warehouseid" name="warehouseid">
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>排序：</td>
					<td class="DataTD ">&nbsp;
						<input type="text" jyValidate="required" maxlength="16" name="sort" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>						
				<tr class="FormData" >
					<td class="CaptionTD">描述：</td>
					<td class="DataTD">&nbsp;
					<textarea rows="2" cols="30" maxlength="100" name="description" multiline="true" class="FormElement ui-widget-content ui-corner-all isSelect147" width="100%" ></textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
