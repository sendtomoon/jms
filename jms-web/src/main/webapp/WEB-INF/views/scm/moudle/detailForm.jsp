<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 岗位-->
<div id="moDiv" class="hide">
		<form id="moForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="0" border="0" class="customTable">
				<tbody>
					<tr style="display:none">
						<td colspan="2" class="ui-state-error"><input type="hidden" name="id" ><input type="hidden" name="moudleid" ></td>
					</tr>
					<tr>
						<td class="CaptionTD"><font color="red">*</font>状态：</td>
						<td class="DataTD">
							<label class="inline isValidCheckbox">
								<input type="checkbox" checked="checked" sh-isValid="" role="checkbox" class="FormElement ace ace-switch ace-switch-5" />	
								<span  class="lbl"></span>
								<!-- cb-isValid和Yes和No选择框配套使用-->
								<input type="hidden" hi-isValid=""  name="status" value="1" />
							</label>
						</td>
						<td class="CaptionTD"><font color="red">*</font>供应商：</td>
						<td class="DataTD">
<!-- 						<input type="text" jyValidate="required"  maxlength="25" name="supplierCode" class="FormElement ui-widget-content ui-corner-all"></td> -->
							<input id="inputName" type="text" jyValidate="required" name="supplierName" placeholder="请输入供应商" style="width:157px;"  data-provide="typeahead" autocomplete="off">
							<a title="重置" onclick="clearSupplier(); return false;"><i class='icon-remove bigger-120 red'></i></a>
							<input type="hidden" name="supplierCode"/>
						</td>
					</tr>
					<tr class="FormData">
						<td class="CaptionTD"><font color="red">*</font>供应商款号：</td>
						<td class="DataTD">
							<input type="text" jyValidate="required"  maxlength="25" name="suppmouCode" class="FormElement ui-widget-content ui-corner-all">
						</td>
						<td class="CaptionTD"><font color="red">*</font>工费：</td>
						<td class="DataTD">
							<input type="text" jyValidate="required,dou"  maxlength="8" name="laborCost" class="FormElement ui-widget-content ui-corner-all">
						</td>
					</tr>
					<tr class="FormData">
						<td class="CaptionTD"><font color="red">*</font>附加工费：</td>
						<td class="DataTD">
							<input type="text" jyValidate="required,dou"  maxlength="8" name="addLaborCost" class="FormElement ui-widget-content ui-corner-all">
						</td>
						<td class="CaptionTD"><font color="red">*</font>销售工费：</td>
						<td class="DataTD">
							<input type="text" jyValidate="required,dou"  maxlength="8" name="saleLaborCost" class="FormElement ui-widget-content ui-corner-all">
						</td>
					</tr>
					<tr class="FormData">
						<td class="CaptionTD"><font color="red">*</font>主要工厂：</td>
						<td class="DataTD">
<!-- 							<input type="text" jyValidate="required"  maxlength="25" name="majorFlag" class="FormElement ui-widget-content ui-corner-all"> -->
							<label class="inline isValidCheckbox1">
								<input type="checkbox" checked="checked" sh-isValid="" role="checkbox" class="FormElement ace ace-switch ace-switch-10" />	
								<span  class="lbl"></span>
								<input type="hidden" hi-isValid=""  name="majorFlag" value="0" />
							</label>
						</td>
						<td class="CaptionTD"><font color="red">*</font>销售损耗：</td> 
						<td class="DataTD">
							<input type="text" jyValidate="required,dou"  maxlength="8" name="saleLossRate" class="FormElement ui-widget-content ui-corner-all">
						</td>
					</tr>
					<tr class="FormData">
						<td class="CaptionTD">图片：</td> 
						<td class="DataTD" colspan="3">
							<div id ="detailDiv1">
<%-- 								<%@include file="img.jsp" %> --%>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
</div>