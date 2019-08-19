<%@ page contentType="text/html;charset=UTF-8" %>
<div id="storeDiv" class="hide">
		<form id="storeInfoForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr>
					<td>
						<font color="red">*</font>门店代码：
					</td>
					<td>
						<input type="hidden" name="id">
						<input type="text" jyValidate="required,ennum"  maxlength="20" name="code" class="FormElement ui-widget-content ui-corner-all">
					</td>
					
					<td>
						<font color="red">*</font>门店性质：
					</td>
					<td>
						<span id="selectStoreProp"><select id="storeProp" jyValidate="required" name="perpoty" style="width:157px;"></select></span>
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font>门店简称：
					</td>
					<td>
						<input type="text" jyValidate="required,lenrange" maxl="30" name="name" cls="FormElement ui-widget-content ui-corner-all">
					</td>
					<td>
						&ensp;门店类型：
					</td>
					<td>
						<span id="selectStoreType"><select id="storeType" name="type" style="width:157px;"></select></span>
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font>门店全称：
					</td>
					<td>
						<input type="text" jyValidate="required,lenrange"  maxl="50" name="longName" class="FormElement ui-widget-content ui-corner-all">
					</td>
					<td>
						<font color="red">*</font>负责人：
					</td>
					<td>
						<input type="text" jyValidate="required,lenrange"  maxl="20" name="director" cls="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font>负责人电话：
					</td>
					<td>
						<input type="text" jyValidate="required,mobile"  maxlength="11" name="directorNm" class="FormElement ui-widget-content ui-corner-all">
					</td>
					<td>
						<font color="red">*</font>联系人：
					</td>
					<td>
						<input type="text" jyValidate="required,lenrange"  maxl="20" name="contractor" cls="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font>联系人电话：
					</td>
					<td>
						<input type="text" jyValidate="required,mobile"  maxlength="11" name="contractorNm" class="FormElement ui-widget-content ui-corner-all">
					</td>
					<td>
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
					<td>
						<font color="red">*</font>组织机构：
					</td>
					<td>
						<input id="orgInput" type="text" readonly value="" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" onclick="showOrgComp(); return false;"/>
						<input type="hidden" name="pId">
						<input type="hidden" name="orgId">
						<input type="hidden" name="orgName">
						<a href="#" title="清空" onclick="emptyOrgComp(); return false;" class="lrspace3 aBtnNoTD" data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a>
						<div id='orgContentList' class="menuContent ztreeMC" style="display: none; position: absolute;">
							<ul id="selectOrgTree" class="ztree preOrgTree"></ul>
						</div>
					</td>
					<td>
						<font color="red">*</font>区域代码
					</td>
					<td>
						<div id="disList">
							<input id="disInput" type="text" readonly />
							<input type="hidden" name="distCode" >
						</div>
<!-- 						<input id="disInput"  type="text" readonly value="" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" /> -->
<!-- 						<input type="hidden" name=""distCode"" > 	 -->
<!-- 						<a ref="#" id="empt" title="清空" class="lrspace3 aBtnNoTD" data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a> -->
<!-- 						<div id='disList' class="menuContent ztreeMC"  style="display: none; position: absolute;"> -->
<!-- 							<ul id="disTree" class="ztree preOrgTree"></ul> -->
<!-- 						</div> -->
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font>门店地址：
					</td>
					<td colspan="3">
						<textarea rows="2" cols="65" jyValidate="required,lenrange" maxl="120" name="address" multiline="true"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font>通讯地址：
					</td>
					<td colspan="3">
						<textarea rows="2" cols="65" jyValidate="required,lenrange" maxl="120" name="postAddress" multiline="true"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						&ensp;描述：
					</td>
					<td colspan="3">
						<textarea rows="2" cols="65" jyValidate="lenrange" maxl="100" name="description" multiline="true"></textarea>
					</td>
				</tr>
				
				
			</table>
		</form>
</div>
