<%@ page contentType="text/html;charset=UTF-8"%>

<div id="auCgyDiv" class="hide">
	<form id="auCgyForm" method="POST" onsubmit="return false;">
		<table cellspacing="0" cellpadding="0" border="0" class="customTable">
			<tbody>
				<tr style="display: none">
					<td colspan="2" class="ui-state-error"><input type="hidden"
						name="id"></td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>状态：</td>
					<td class="DataTD">&nbsp; <label
						class="inline isValidCheckbox"> <input type="checkbox"
							checked="checked" sh-isValid="" role="checkbox"
							class="FormElement ace ace-switch ace-switch-5" /> <span
							class="lbl"></span> <!-- cb-isValid和Yes和No选择框配套使用--> <input
							type="hidden" hi-isValid="" name="status" value="1" />
					</label>
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>上级：</td>
					<td class="datatd">&nbsp; <input id="pre" type="text" readonly
						value="" class="FormElement ui-widget-content ui-corner-all"
						onclick="showPre(); return false;" /> <input type="hidden"
						name="pid" value="0"> <a href="#" title="清空"
						onclick="emptyPre(); return false;" class="lrspace3 aBtnNoTD"
						data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a>
						<div id='preContent' class="menuContent ztreeMC"
							style="display: none; position: absolute;">
							<ul id="preTree" class="ztree preOrgTree"></ul>
						</div>
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>名称：</td>
					<td class="DataTD">&nbsp; <input type="text"
						jyValidate="required" maxlength="25" name="name"
						class="FormElement ui-widget-content ui-corner-all"></td>
				</tr>
				<tr class="FormData">
					<td>
					代码：
					</td>
					<td class="DataTD">&nbsp; <input type="text" jyValidate="required"  maxlength="25" name="code"
						class="FormElement ui-widget-content ui-corner-all"></td>
				</tr>
				<tr class="FormData" style="display: none">
					<td class="CaptionTD"><font color="red">*</font>机构：</td>
					<td class="DataTD">&nbsp; <input id="preOrg" type="text"
						readonly value=""
						class="FormElement ui-widget-content ui-corner-all"
						onclick="showPreOrg(); return false;" /> <input type="hidden"
						name="orgid"> <a href="#" title="清空"
						onclick="emptyPreOrg(); return false;" class="lrspace3 aBtnNoTD"
						data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a>
						<div id='preOrgContent' class="menuContent ztreeMC"
							style="display: none; position: absolute;">
							<ul id="preOrgTree" class="ztree preOrgTree"></ul>
						</div>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>排序：</td>
					<td class="DataTD">&nbsp; <input type="text"
						jyValidate="required" maxlength="25" name="sort"
						class="FormElement ui-widget-content ui-corner-all"></td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>类别：</td>
					<td class="DataTD">&nbsp; <span id="selectType1"> <select
							id="type1" name="type" jyValidate="required"></select></span>
					</td>
				</tr>

				<tr class="FormData">
					<td class="CaptionTD">描述：</td>
					<td class="DataTD">&nbsp; <textarea rows="2" cols="10"
							maxlength="100" name="description" multiline="true"
							class="FormElement ui-widget-content ui-corner-all isSelect147"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>


<div id="auDiv" class="hide">
	<form id="auForm" method="POST" onsubmit="return false;">
		<table cellspacing="0" cellpadding="0" border="0" class="customTable"
			style="width: 100%">
			<tbody>
				<tr style="display: none">
					<td colspan="2" class="ui-state-error"><input type="hidden"
						name="id"> <input type="hidden" name="categoryid"></td>
				</tr>
				<tr class="FormData">

					<td class="CaptionTD"><font color="red">*</font>状态：</td>
					<td class="DataTD"><label class="inline isValidCheckbox1">
							<input type="checkbox" checked="checked" sh-isValid=""
							role="checkbox" class="FormElement ace ace-switch ace-switch-5" />
							<span class="lbl"></span> <!-- 	cb-isValid和Yes和No选择框配套使用 --> <input
							type="hidden" hi-isValid="" name="status" value="1" />
					</label></td>

					<td class="CaptionTD"><font color="red">*</font>名称：</td>
					<td class="DataTD"><input type="text" jyValidate="required"
						maxlength="25" name="name"
						class="FormElement ui-widget-content ui-corner-all"></td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>款号：</td>
					<td class="DataTD"><input type="text" readonly placeholder="自动生成" 
						maxlength="25" name="code"
						class="FormElement ui-widget-content ui-corner-all"></td>

					<td class="CaptionTD"><font color="red">*</font>类型：</td>
					<td class="DataTD"><span id="selectType"><select
							id="type" name="type" jyValidate="required"></select></span> 
					</td>
				</tr>

				<tr class="FormData">
					<td class="CaptionTD">描述：</td>
					<td class="DataTD" colspan="3"><textarea rows="2" cols="10"
							maxlength="100" name="description" multiline="true"
							style="width: 410px"
							class="FormElement ui-widget-content ui-corner-all"></textarea></td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">图片：</td>
					<td class="DataTD" colspan="3">
						<div id="moudleDiv">
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<%@include file="img.jsp"%>
<div id="img"></div>