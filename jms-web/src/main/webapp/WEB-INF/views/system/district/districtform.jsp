<%@ page contentType="text/html;charset=UTF-8" %>
<div id="auDiv" class="hide">
		<form id="auForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr>
					<td>
						&ensp;状态：&nbsp;&nbsp;&nbsp;
						<label class="inline isValidCheckbox">
							<input type="checkbox" checked="checked"  sh-isValid="" role="checkbox" class="FormElement ace ace-switch ace-switch-5" />	
							<span  class="lbl"></span>
							<!-- cb-isValid和Yes和No选择框配套使用-->
							<input type="hidden" hi-isValid="" name="status" value="1" />
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font><span align="right">编号：</span>
						<input type="text" jyValidate="required"  maxlength="16" name="id" class="FormElement ui-widget-content ui-corner-all" >
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font><span align="right">父级：</span>
						<input id="orgInput" jyValidate="required" type="text" readonly value="" class="FormElement ui-widget-content ui-corner-all" onclick="showOrgComp(); return false;"/>
							<input type="hidden" name="pid"  value="0">
							<a href="#" title="清空" onclick="emptyOrgComp(); return false;" class="lrspace3 aBtnNoTD" data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a>
							<div id='orgContentList' class="menuContent ztreeMC"  style="display: none; position: absolute;">
								<ul id="selectdistrictorgTree" class="ztree preOrgTree"></ul>
							</div>
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font><span align="right">排序：</span>
						<input type="text" jyValidate="required" maxlength="16" name="sort" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font><span align="right">名称：</span>
						<input type="text" jyValidate="required" maxlength="16" name="name" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					<td ><span style="margin-left: 8px;">描述:</span>
						<span style="margin-left: 5px;"><input type="text"  maxlength="32" name="descrition" class="FormElement ui-widget-content ui-corner-all"></span>
					</td>
				</tr>
				
			</table>	
		</form>
</div>
