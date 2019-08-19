<%@ page contentType="text/html;charset=UTF-8" %>
<div id="membersDiv" class="hide">
	<form id="membersForm" method="POST" onsubmit="return false;" >
	  <table cellspacing="0" cellpadding="4px;" border="0"class="customTable">
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>卡号：</td>
			<td class="DataTD"><input type="text" jyValidate="required" maxlength="32" id="formCardNo" name="cardNo" class="FormElement ui-widget-content ui-corner-all"/></td>
			<td class="CaptionTD"><font color="red">*</font>名称：</td>
			<td class="DataTD" ><input type="text" jyValidate="required" maxlength="32" name="name" class="FormElement ui-widget-content ui-corner-all"/></td>
		</tr>
		<tr class="FormData">
			<input type="hidden" name="id"/>
			<td class="CaptionTD"><font color="red">*</font>昵称：</td>
			<td class="DataTD"><input type="text" jyValidate="required" maxlength="16" name="nickName"class="FormElement ui-widget-content ui-corner-all"/></td>
			<td class="CaptionTD">性别：</td>
			<td class="DataTD">
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
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>手机号码：</td>
			<td class="DataTD"><input type="text" jyValidate="required phone" maxlength="32" id="formMobile" name="mobile" class="FormElement ui-widget-content ui-corner-all"/></td>
			<td class="CaptionTD">电子邮箱：</td>
			<td class="DataTD"><input name="email" type="text" jyValidate="email" maxlength="20" class="FormElement ui-widget-content ui-corner-all" /></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD">生日：</td>
			<td class="DataTD"><input  type="text"   name="birthday" jyValidate="dou" maxlength="4" /></td>
			<td class="CaptionTD">注册时间：</td>
			<td class="DataTD">
				<input name="regTime1" type="text" readonly class="FormElement ui-widget-content ui-corner-all" />
			</td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>注册类型：</td>
			<td class="DataTD">
<!-- 				<input name="regType" type="text" maxlength="30"  class="FormElement ui-widget-content ui-corner-all" /> -->
				<select id="formRegType" name="regType" class="isSelect157"></select>
			</td>
			<td class="CaptionTD">注册单位：</td>
			<td class="DataTD"><input type="text" readonly maxlength="12" name="regOrg"/></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD">注册用户：</td>
			<td class="DataTD"><input name="regUser" readonly type="text"  maxlength="16" class="FormElement ui-widget-content ui-corner-all" /></td>
			<td class="CaptionTD">是否锁定：</td>
			<td class="DataTD">
<!-- 				<input type="text" name="flagLock" maxlength="16" jyValidate="required" /> -->
				<label id="flagLockLabel" class="inline">
					<input name="flagLock" id="flagLock_0" type="radio" class="ace" value="0"/>
					<span class="lbl middle"> 否</span>
				</label>
				&nbsp;&nbsp;
				<label class="inline">
					<input name="flagLock" id="flagLock_1" type="radio" class="ace" value="1"/>
					<span class="lbl middle"> 是</span>
				</label>
			</td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD">是否激活：</td>
			<td class="DataTD">
<!-- 				<input name="flagActive" type="text" maxlength="18" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" /> -->
				<label id="flagActiveLabel" class="inline">
					<input name="flagActive" id="flagActive_0" type="radio" class="ace" value="0"/>
					<span class="lbl middle"> 否</span>
				</label>
				&nbsp;&nbsp;
				<label class="inline">
					<input name="flagActive" id="flagActive_1" type="radio" class="ace" value="1"/>
					<span class="lbl middle"> 是</span>
				</label>
			</td>
			<td class="CaptionTD">是否限制：</td>
			<td class="DataTD">
<!-- 			<input name="flagLimit" type="text" maxlength="16" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" /> -->
				<label id="flagLimitLabel" class="inline">
					<input name="flagLimit" id="flagLimit_0" type="radio" class="ace" value="0"/>
					<span class="lbl middle"> 否</span>
				</label>
				&nbsp;&nbsp;
				<label class="inline">
					<input name="flagLimit" id="flagLimit_1" type="radio" class="ace" value="1"/>
					<span class="lbl middle"> 是</span>
				</label>
			</td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD">地址：</td>
			<td class="DataTD"><input name="address" type="text" maxlength="16"  class="FormElement ui-widget-content ui-corner-all" /></td>
			<td class="CaptionTD"><font color="red">*</font>会员级别：</td>
			<td class="DataTD">
<!-- 				<input name="grade" type="text" maxlength="16"  class="FormElement ui-widget-content ui-corner-all" /> -->
				<select id="formGrade" name="grade" jyValidate="required" class="isSelect157"></select>
			</td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD">微信：</td>
			<td class="DataTD"><input name="openId" type="text" maxlength="16" class="FormElement ui-widget-content ui-corner-all" /></td>
			<td class="CaptionTD">最后登录时间：</td>
			<td class="DataTD">
				<input name="lastLoginTime1" type="text" readonly class="FormElement ui-widget-content ui-corner-all" />
<!-- 				<label name ="lastLoginTime"></label> -->
			</td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD">省份：</td>
			<td class="DataTD">
<!-- 				<input name="province" id="province" type="text" maxlength="16" class="FormElement ui-widget-content ui-corner-all" /> -->
				<select id="province" name="province" class="isSelect157"></select>
			</td>
			<td class="CaptionTD">推荐人手机：</td>
			<td class="DataTD">
				<input name="reference" type="text" class="FormElement ui-widget-content ui-corner-all" />
<!-- 				<label name ="lastLoginTime"></label> -->
			</td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD">备注</td>
			<td  class="DataTD" colspan="3"><textarea rows="2" maxlength="59" cols="68"  name="remark" ></textarea></td>
		</tr>
	</table>
	</form>
</div>
<div id="membersPwdDiv" class="hide">
	<form id="membersPwdForm" method="POST" onsubmit="return false;" >
		<table cellspacing="0" cellpadding="4px;" border="0"class="customTable">
		<tr class="FormData">
			<td class="CaptionTD">原密码：</td>
			<td class="DataTD">
			<input type="hidden" name="id"/>
				<input name="pwd" type="password" jyValidate="required" autocomplete="off" maxlength="16" class="FormElement ui-widget-content ui-corner-all" />
			</td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD">新密码：</td>
			<td class="DataTD">
				<input name="pwdNew" type="password" jyValidate="required" autocomplete="off" maxlength="16" class="FormElement ui-widget-content ui-corner-all" />
			</td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD">确认密码：</td>
			<td class="DataTD">
				<input name="pwdNew1" type="password" jyValidate="required" autocomplete="off" maxlength="16" class="FormElement ui-widget-content ui-corner-all" />
			</td>
		</tr>
		</table>
	</form>
</div>
