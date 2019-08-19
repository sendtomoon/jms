<%@ page contentType="text/html;charset=UTF-8" %>
<div id="attributedictbaseDiv" class="hide">
		<form id="attributedictbaseForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr>
					
					<td>
						<font color="red">*</font>属性名称：
					</td>
					<td>
						<input type="hidden" name="id">
						<input type="text" jyValidate="required"  maxlength="50" name="name" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font>属性编码：
					</td>
					<td>
						<input type="text" jyValidate="required,ennum_,lenrange" maxl="20" name="code" cls="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					
					<td>
						<font color="red">*</font>数据类型：
					</td>
					<td>
						<select id="type" name="type" jyValidate="required" style="width:157px;">
						    <option value="">请选择...</option>
							<option value="string">字符</option>
							<option value="number">数值</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font>属性长度：
					</td>
					<td>
						<input type="number" jyValidate="required,lenrange" maxl="4" name="length" cls="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font>属性排序：
					</td>
					<td>
						<input type="number" jyValidate="required"  maxlength="3" name="sort" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					<td>
						&ensp;字典使用：
					</td>
					<td>
						 <input id="selectDict" name="dictName" type="text" data-provide="typeahead" autocomplete="off" placeholder="请输入字典名称">
						 <input type="hidden" name="dictId" />
						<a title="重置" onclick="clearSupplier(); return false;"><i class='icon-remove bigger-120 red'></i></a>
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font>允许为空：
					</td>
					<td>
						<label class="inline isValidCheckbox1">
							<input type="checkbox" checked="checked" id="nullable" sh-isValid="" role="checkbox" class="FormElement ace ace-switch ace-switch-10" />	
							<span  class="lbl"></span>
							<input type="hidden" hi-isValid=""  name="nullable" value="1" />
						</label>
					</td>
				</tr>
				
				<tr>
					<td>
						<font color="red">*</font>检索标签：
					</td>
					<td>
						<label class="inline isValidCheckbox2">
							<input type="checkbox" checked="checked" id="filtertag" sh-isValid="" role="checkbox" class="FormElement ace ace-switch ace-switch-10" />	
							<span  class="lbl"></span>
							<input type="hidden" hi-isValid=""  name="filtertag" value="1" />
						</label>
					</td>
				</tr>
				
				<tr>
					<td>
						&ensp;状  态：
					</td>
					<td>
						<label class="inline isValidCheckbox3">
							<input type="checkbox" checked="checked" id="status" sh-isValid="" role="checkbox" class="FormElement ace ace-switch ace-switch-12" />	
							<span  class="lbl"></span>
							<input type="hidden" hi-isValid=""  name="status" value="1" />
						</label>
					</td>
				</tr>
			</table>
		</form>
</div>
