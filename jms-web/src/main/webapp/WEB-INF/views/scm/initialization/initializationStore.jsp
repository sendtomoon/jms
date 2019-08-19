<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
<%@include file="../../common/includeBaseSet.jsp" %>
<%@include file="../../common/includeSystemSet.jsp" %>

<script src="${jypath}/static/js/bootstrap/bootstrap3-typeahead.min.js"></script>


<link rel="stylesheet" href="${jypath}/static/plugins/zTree/3.5/zTreeStyle.css" />
<script type="text/javascript" src="${jypath}/static/js/system/jy/jy.component.js"></script>
<script src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.min.js"></script>
</head>
<body>
	<br>
	<br>
	<div id="storeDiv" align="center" >
		<div class="row page-header">
			<h1>&nbsp;<span >门店信息</span>&nbsp; <i class="icon-double-angle-right"></i> <small><span >角色信息</span> &nbsp;<i class="icon-double-angle-right"></i> <span >用户信息</span>&nbsp;<i class="icon-double-angle-right"></i> <span >仓库信息</span></small></h1>
		</div>
		<form id="storeInfoForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable" style="width:720px">
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
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font>门店地址：
					</td>
					<td colspan="3">
						<textarea rows="2" cols="73"  jyValidate="required,lenrange" maxl="120" name="address" multiline="true" ></textarea>
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font>通讯地址：
					</td>
					<td colspan="3">
						<textarea rows="2" cols="73"  jyValidate="required,lenrange" maxl="120" name="postAddress" multiline="true" ></textarea>
					</td>
				</tr>
				<tr>
					<td>
						&ensp;描述：
					</td>
					<td colspan="3">
						<textarea rows="2" cols="73"   jyValidate="lenrange" maxl="100" name="description" multiline="true" ></textarea>
					</td>
				</tr>
			</table>
		</form>
		<button class="btn btn-primary btn-xs" onclick="backwardStore();"><i class='icon-backward bigger-110'></i>&nbsp;上一步</button>  <button class="btn btn-primary btn-xs" onclick="forwardStore();">下一步&nbsp;<i class='icon-forward bigger-110'></i></button>
	</div>
	<div  align="center" id="auRoleDiv" class="hide">
		<div class="row page-header">
			<h1>&nbsp;<small><span >门店信息</span>&nbsp; <i class="icon-double-angle-right"></i></small> <span >角色信息</span> &nbsp;<i class="icon-double-angle-right"></i> <small><span >用户信息</span>&nbsp;<i class="icon-double-angle-right"></i> <span >仓库信息</span></small></h1>
		</div>
		<form id="auRoleForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="0" border="0" class="customTable" style="width: 360px">
				<tbody>
					<tr style="display:none">
						<td colspan="2" class="ui-state-error"><input type="hidden" name="id" > <input type="hidden" name="orgId" ></td>
					</tr>
					<tr class="FormData" >
						<td class="CaptionTD">状态：</td>
						<td class="DataTD">&nbsp;
							<label class="inline isValidCheckbox">
								<input type="checkbox" checked="checked" sh-isValid="" role="checkbox" class="FormElement ace ace-switch ace-switch-5" />	
								<span  class="lbl"></span>
								<!-- cb-isValid和Yes和No选择框配套使用-->
								<input type="hidden" hi-isValid=""  name="isValid" value="1" />
							</label>
						</td>
					</tr>			
					<tr class="FormData">
						<td class="CaptionTD"><font color="red">*</font>角色名称：</td>
						<td class="DataTD">&nbsp;
						<input type="text" jyValidate="required"  maxlength="25" name="name" class="FormElement ui-widget-content ui-corner-all"></td>
					</tr>		
					<tr class="FormData">
						<td class="CaptionTD">角色描述：</td>
						<td class="DataTD">&nbsp;
						<textarea rows="2" cols="10" maxlength="100" name="description" multiline="true" class="FormElement ui-widget-content ui-corner-all isSelect147"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		<button class="btn btn-primary btn-xs" onclick="backwardRole()"><i class='icon-backward bigger-110'></i>&nbsp;上一步</button>  <button class="btn btn-primary btn-xs" onclick="forwardRole()">下一步&nbsp;<i class='icon-forward bigger-110'></i></button>
	</div>
	<div  align="center" id="auUserDiv" class="hide">
		<div class="row page-header">
			<h1>&nbsp;<small><span >门店信息</span>&nbsp; <i class="icon-double-angle-right"></i> <span >角色信息</span> &nbsp;<i class="icon-double-angle-right"></i> </small> <span >用户信息</span>&nbsp;<i class="icon-double-angle-right"></i> <small><span >仓库信息</span></small></h1>
		</div>
		<form id="auUserForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable" style="width:720px">
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
						<font color="red">*</font>职级：</td>
						<td><span id="selectRank"><select jyValidate="required" id="rank" name="rank" style="width:157px;"></select></span>
					</td>
				</tr>
				<tr>
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
					<td>
						<font color="red">*</font>生日：</td>
					<td>
						<input type="text" id="dateRender" name="birthdaty" readonly jyValidate="required"/>
						
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
					紧急联系手机：</td>
					<td><input type="text" jyValidate="mobile" maxlength="11" name="emercontractNm" class="FormElement ui-widget-content ui-corner-all">
					</td>
					<td>
					
				</tr>
				<tr>
						<font color="red">*</font>组织：</td>
						<td>	
							<input name="orgName" type="text" readonly value="" jyValidate="required" class="FormElement ui-widget-content ui-corner-all"/>
							<input type="hidden" name="pId" value="0" >
							<input type="hidden" name="orgId">
<!-- 							<input type="hidden" name="orgName"> -->
<!-- 							<a href="#" title="清空" onclick="emptyOrgComp(); return false;" class="lrspace3 aBtnNoTD" data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a> -->
<!-- 							<div id='orgContentList' class="menuContent ztreeMC" style="display: none; position: absolute;"> -->
<!-- 								<ul id="selectOrgTree" class="ztree preOrgTree"></ul> -->
<!-- 							</div> -->
					</td>
					<td>
						<font color="red">*</font>公司：
					</td>
					<td>
						<input type="text" jyValidate="required,lenrange" readonly maxl="50" name="companyName" class="FormElement ui-widget-content ui-corner-all">
						<input type="hidden" name="company" >
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
		<button class="btn btn-primary btn-xs" onclick="backwardUser()"><i class='icon-backward bigger-110'></i>&nbsp;上一步</button>  <button onclick="forwardUser()" class="btn btn-primary btn-xs">下一步&nbsp;<i class='icon-forward bigger-110'></i></button>
	</div>
	<div  align="center" id="warDiv" class="hide">
		<div class="row page-header">
			<h1>&nbsp;<small><span >门店信息</span>&nbsp; <i class="icon-double-angle-right"></i> <span >角色信息</span> &nbsp;<i class="icon-double-angle-right"></i> <span >用户信息</span>&nbsp;<i class="icon-double-angle-right"></i> </small><span >仓库信息</span></h1>
		</div>
		<form id="warForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable" style="width: 720px" >
				<tr>
					<td align="right">
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
					<td align="right">
						<font color="red">*</font>仓库名称：
					</td>
					<td>
					    <input type="hidden" name="id" >
						<input type="text" jyValidate="required"  maxlength="16" name="name" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					<td align="right">
						<font color="red">*</font>仓库代码：
					</td>
					<td>
						<input type="text" jyValidate="required"  maxlength="16" name="code" class="FormElement ui-widget-content ui-corner-all" placeholder="如：001">
					</td>
				</tr>
				<tr>
					<td align="right">
						<font color="red">*</font>所属单位：
					</td>
					<td>
						<input name="orgName" type="text" readonly value="" class="FormElement ui-widget-content ui-corner-all" onclick="showOrgComp(); return false;"/>
						<input type="hidden" name="orgId">
<!-- 						<input type="hidden" name="orgName"> -->
<!-- 						<a href="#" title="清空" onclick="emptyOrgComp(); return false;" class="lrspace3 aBtnNoTD" data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a> -->
<!-- 						<div id='orgContentList' class="menuContent ztreeMC" style="display: none; position: absolute;"> -->
<!-- 							<ul id="selectOrgTree" class="ztree preOrgTree"></ul> -->
<!-- 						</div> -->
					</td>
				</tr>
				<tr>
					<td align="right">
						<font color="red">*</font>区域代码：
					</td>
					<td>
						<input type="text" jyValidate="required"  maxlength="16" name="distcode" class="FormElement ui-widget-content ui-corner-all" readonly="readonly">
					</td>
					
				</tr>
				<tr>
					<td align="right" >
						<font color="red">*</font>地址：
					</td>
					<td colspan="2">
						<span id="selectProvince">
							<select  id="provincees" style="width: 120px;" name="province" onchange="chg(this);" jyValidate="required">
								<option value="">请选择省份</option>
							</select>
						</span>
						<span id="selectCity">
							<select  id="cityes" style="width: 120px;" name="city" onchange="chg2(this);" jyValidate="required">
								<option value="">请选择市/区</option>
							</select>
						</span>
						<span id="selectCounty">
							<select  id="countyes" style="width: 120px;" name="county" jyValidate="required">
								<option value="">请选择市/县</option>
							</select>
						</span>
					</td>
				</tr>
					
				<tr>
					<td align="right">
					</td>
					<td colspan="3">
						<textarea rows="1" cols="50" name="address" multiline="true"  jyValidate="required"></textarea>
					</td>
				</tr>
				<tr>
					<td align="right">
						<font color="red">*</font>联系电话：
					</td>
					<td>
						<input type="text" jyValidate="required,mobile"  maxlength="16" name="directornm" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					<td align="right">
						<font color="red">*</font>负责人：
					</td>
					<td>
						<input type="text" jyValidate="required"  maxlength="16" name="director" class="FormElement ui-widget-content ui-corner-all"></td>
					</td>
				</tr>
				<tr>
					<td align="right">
						&ensp;邮编：
					</td>
					<td>
						<input type="text"  maxlength="16" name="zipcode" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr>
					<td align="right">
						&ensp;描述：
					</td>
					<td  colspan="3">
						<textarea rows="3" cols="50" name="description" multiline="true"></textarea>
					</td>
				</tr>
			</table>
		</form>
		<button class="btn btn-primary btn-xs" onclick="backwardWar()"><i class='icon-backward bigger-110'></i>&nbsp;上一步</button>  <button class="btn btn-primary btn-xs" onclick="forwardWar()">完成&nbsp;<i class='icon-forward bigger-110'></i></button>
	</div>
<!-- 	<div  align="center" id="warLoDiv" class="hide"> -->
<!-- 		<form id="LocationForm" method="POST" onsubmit="return false;" > -->
<!-- 			<table cellspacing="0" cellpadding="0" border="0" class="customTable" style="width: 360px"> -->
<!-- 				<tbody> -->
<!-- 					<tr class="FormData"> -->
<!-- 						<td class="CaptionTD"><font color="red">*</font>状态：</td> -->
<!-- 						<td class="DataTD">&nbsp; -->
<!-- 							<label class="inline isValidCheckbox"> -->
<!-- 								<input type="checkbox" checked="checked" sh-isValid="" role="checkbox" class="FormElement ace ace-switch ace-switch-5" />	 -->
<!-- 								<span  class="lbl"></span> -->
<!-- 								cb-isValid和Yes和No选择框配套使用 -->
<!-- 								<input type="hidden" hi-isValid=""  name="status" value="1" /> -->
<!-- 								<input type="hidden" name="id" > -->
<!-- 							</label> -->
<!-- 						</td> -->
<!-- 					</tr>	 -->
<!-- 					<tr class="FormData"> -->
<!-- 						<td class="CaptionTD"><font color="red">*</font>仓位名称：</td> -->
<!-- 						<td class="DataTD">&nbsp; -->
<!-- 						<input type="text" jyValidate="required"  maxlength="16" name="name" class="FormElement ui-widget-content ui-corner-all" ></td> -->
<!-- 					</tr> -->
<!-- 					<tr class="FormData"> -->
<!-- 						<td class="CaptionTD"><font color="red">*</font>仓位代码：</td> -->
<!-- 						<td class="DataTD ">&nbsp; -->
<!-- 							<input type="text" jyValidate="required"  maxlength="16" name="code" class="FormElement ui-widget-content ui-corner-all" placeholder="如：001"> -->
<!-- 						</td> -->
<!-- 					</tr> -->
<!-- 					<tr class="FormData"> -->
<!-- 						<td class="CaptionTD"><font color="red">*</font>所属仓库：</td> -->
<!-- 						<td class="DataTD ">&nbsp; -->
<!-- 							<input type="text"  id="warehouseName" name="warehouseName" class="FormElement ui-widget-content ui-corner-all" readonly="readonly"> -->
<!-- 							<input type="hidden"  id="warehouseid" name="warehouseid"> -->
<!-- 						</td> -->
<!-- 					</tr> -->
<!-- 					<tr class="FormData"> -->
<!-- 						<td class="CaptionTD">排序：</td> -->
<!-- 						<td class="DataTD ">&nbsp; -->
<!-- 							<input type="text"  maxlength="16" name="sort" class="FormElement ui-widget-content ui-corner-all"> -->
<!-- 						</td> -->
<!-- 					</tr>						 -->
<!-- 					<tr class="FormData" > -->
<!-- 						<td class="CaptionTD">描述：</td> -->
<!-- 						<td class="DataTD">&nbsp; -->
<!-- 						<textarea rows="2" cols="30" maxlength="100" name="description" multiline="true" class="FormElement ui-widget-content ui-corner-all isSelect147" width="100%" ></textarea> -->
<!-- 						</td> -->
<!-- 					</tr> -->
<!-- 				</tbody> -->
<!-- 			</table> -->
<!-- 		</form> -->
<!-- 		<button class="btn btn-primary btn-xs" onclick="backwardWarLo()"><i class='icon-backward bigger-110'></i>&nbsp;上一步</button>  <button onclick="forwardWarLo()" class="btn btn-primary btn-xs">下一步&nbsp;<i class='icon-forward bigger-110'></i></button> -->
<!-- 	</div> -->
	<script src="${jypath}/static/js/scm/initialization/InitializationStore.js"></script>
</body>
</html>