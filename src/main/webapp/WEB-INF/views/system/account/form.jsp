<%@ page contentType="text/html;charset=UTF-8" %>
<div id="auDiv" class="hide">
		<form id="auForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr>
					<td>
						&ensp;状态：
					</td>
					<td>
						<label class="inline isValidCheckbox">
							<input type="checkbox" checked="checked" sh-isValid="" role="checkbox" class="FormElement ace ace-switch ace-switch-12" />	
							<span  class="lbl"></span>
							<!-- cb-isValid和Yes和No选择框配套使用-->
							<input type="hidden" hi-isValid=""  name="isValid" value="1" />
						</label>
					</td>
					<td>
						<input type="hidden" name="id" >
						<input type="hidden" name="accountId" >
						<font color="red">*</font>登录名：
					</td>
					<td>
						<input type="text" jyValidate="required,reg,lenrange" maxl="20" name="loginName" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr style="margin-top: 20px;">
					<td>
						<font color="red">*</font>姓名：
					</td>
					<td>
						<input type="text" jyValidate="required,lenrange" maxl="30" name="name" class="FormElement ui-widget-content ui-corner-all">
					</td>
					<td>
						<font color="red">*</font>手机：
					</td>
					<td>
						<input type="text" jyValidate="required,mobile" maxlength="11" name="mobile" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font>工号：
					</td>
					<td>
						<input type="text" jyValidate="required,ennum" maxlength="10" name="userNo" class="FormElement ui-widget-content ui-corner-all">
					</td>
					<td>
						<font color="red">*</font>生日：</td>
					<td>
						<input type="text" id="dateRender" name="birthdaty" readonly jyValidate="required"/>
						
					</td>
				</tr>
				<tr>
					<td>
						公司：
					</td>
					<td>
						<input type="text" name="company" class="FormElement ui-widget-content ui-corner-all" placeholder="系统自动关联" readonly>
					</td>
					<td>
						<font color="red">*</font>性别：</td>
					<td>
						<label id="sexLabel" class="inline">
							<input name="sex" id="sex_0" type="radio" class="ace" value="0"/>
								<span class="lbl middle"> 男</span>
							</label>
							&nbsp;&nbsp;
							<label class="inline">
								<input name="sex" id="sex_1" type="radio" class="ace" value="1"/>
								<span class="lbl middle"> 女</span>
						</label>
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font>邮箱：
					</td>
					<td>
						<input type="text" jyValidate="required,email,lenrange"  maxl="30" name="email" class="FormElement ui-widget-content ui-corner-all">
					</td>
					
					<td>
						<font color="red">*</font>类型：
					</td>
					<td>
						<span id="selectUserType"><select jyValidate="required" id="userType" name="type" style="width:157px;"></select></span>
					</td>
				</tr>
				<tr>
					<td>
						&ensp;地址：</td>
						<td><input type="text" jyValidate="lenrange" maxl="60"  name="address" class="FormElement ui-widget-content ui-corner-all">
					</td>
					
					<td>
						<font color="red">*</font>等级：</td>
						<td><span id="selectGrade"><select jyValidate="required" id="grade" name="grade" style="width:157px;"></select></span>
					</td>
					
				</tr>
				<tr>
					<td>
						紧急联系人：</td>
						<td><input type="text" jyValidate="lenrange" maxl="20" name="emercontractor" class="FormElement ui-widget-content ui-corner-all">
					</td>
					
					<td>
						<font color="red">*</font>职级：</td>
						<td><span id="selectRank"><select jyValidate="required" id="rank" name="rank" style="width:157px;"></select></span>
					</td>
					
				</tr>
				<tr>
					<td>
					紧急联系手机：</td>
					<td><input type="text" jyValidate="mobile" maxlength="11" name="emercontractNm" class="FormElement ui-widget-content ui-corner-all">
					</td>
					<td>
						<font color="red">*</font>组织：</td>
						<td>	<input id="orgInput" type="text" readonly value="" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" onclick="showOrgComp(); return false;"/>
							<input type="hidden" name="pId" value="0" >
							<input type="hidden" name="orgId">
							<input type="hidden" name="orgName">
							<a href="#" title="清空" onclick="emptyOrgComp(); return false;" class="lrspace3 aBtnNoTD" data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a>
							<div id='orgContentList' class="menuContent ztreeMC" style="display: none; position: absolute;">
								<ul id="selectOrgTree" class="ztree preOrgTree"></ul>
							</div>
					</td>
					
				</tr>
				<tr>
					<td>
						描述：
					</td>
					<td colspan="3">
						<textarea rows="2" cols="66" jyValidate="lenrange" maxl="100" name="description" multiline="true" ></textarea>
					</td>
				
				</tr>
				
			</table>
		</form>
</div>
<div id="resetPwdDiv" class="hide">
	<form id="resetPwdFrom" method="POST" onsubmit="return false;" >
		<table cellspacing="0" cellpadding="0" border="0" class="customTable">
			<tbody>
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"><input type="hidden" name="accountId" ></td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">重置密码：</td>
					<td class="DataTD">&nbsp;
						<input type="password" jyValidate="required,password" name="pwd" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr> 
			</tbody>
		</table>
	</form>		
</div>

<div id="authRoles" class="hide">
	<ul id="userRoleTree" class="ztree" style="height:300px;"></ul>
</div>