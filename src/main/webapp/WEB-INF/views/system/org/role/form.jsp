<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 组织 -->
<div id="auOrgDiv" class="hide">
		<form id="auOrgForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="0" border="0" class="customTable">
				<tbody>
					<tr style="display:none">
						<td colspan="2" class="ui-state-error"><input type="hidden" name="id" ></td>
					</tr>
					 <tr class="FormData" >
						<td class="CaptionTD">状态：</td>
						<td class="DataTD">&nbsp;
							<label class="inline isValidCheckbox">
								<input type="checkbox" checked="checked" sh-status="" role="checkbox" class="FormElement ace ace-switch ace-switch-5" />	
								<span  class="lbl"></span>
						<!-- 	cb-isValid和Yes和No选择框配套使用 -->
								<input type="hidden" hi-status=""  name="status" value="1" />
							</label>
						</td>
					</tr>		 	
					<tr  class="FormData">
						<td  class="CaptionTD"><font color="red">*</font>上级组织：</td>
						<td class="DataTD">&nbsp;
							<input id="preOrg" type="text" readonly jyValidate="required" value="" class="FormElement ui-widget-content ui-corner-all" onclick="showPreOrg(); return false;"/>
							<input id="preId" type="hidden" name="pId" value="0" >
							<a href="#" title="清空" onclick="emptyPreOrg(); return false;" class="lrspace3 aBtnNoTD" data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a>
							<div id='preOrgContent' class="menuContent ztreeMC" style="display: none; position: absolute;">
								<ul id="preOrgTree" class="ztree preOrgTree"></ul>
							</div>
						</td>
					</tr>
					<tr class="FormData">
						<td class="CaptionTD"><font color="red">*</font>名称：</td>
						<td class="DataTD">&nbsp;
						<input type="text" jyValidate="required"  maxlength="25" name="name" class="FormElement ui-widget-content ui-corner-all"></td>
					</tr>
					<tr class="FormData">
						<td class="CaptionTD"><font color="red">*</font>全称：</td>
						<td class="DataTD">&nbsp;
						<input type="text" jyValidate="required"  maxlength="25" name="longName" class="FormElement ui-widget-content ui-corner-all"></td>
					</tr>
					<tr class="FormData">
						<td class="CaptionTD"><font color="red">*</font>代码：</td>
						<td class="DataTD">&nbsp;
						<input type="text" jyValidate="required"  maxlength="25" name="code" class="FormElement ui-widget-content ui-corner-all"></td>
					</tr>
					<tr class="FormData">
						<td class="CaptionTD"><font color="red">*</font>区域代码：</td>
						<td class="DataTD">&nbsp;
							<input id="orgInput"  type="text" readonly value="" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" />
							<input type="hidden" name="distcode" /> 	
							<a href="#" id="empt" title="清空" class="lrspace3 aBtnNoTD" data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a>
							<div id='orgList' class="menuContent ztreeMC"  style="display: none; position: absolute;">
								<ul id="selectorgTree" class="ztree preOrgTree"></ul>
							</div>
						</td>
					</tr>
					<tr class="FormData">
						<td class="CaptionTD"><font color="red">*</font>加盟商：</td>
						<td class="DataTD">&nbsp;
							<div>
							   <label class="checkbox-inline">
							      <input type="radio" name="orgType" id="optionsRadios1" value="0" checked> 否
							   </label>
							   <label class="checkbox-inline">
							      <input type="radio" name="orgType" id="optionsRadios2" value="1">是
							   </label>
							</div>
<!-- 							<span id="selectOrgType"><select id="orgType" name="orgType"></select></span> -->
<!-- 						<input type="text" jyValidate="required"  maxlength="25" name="orgType" class="FormElement ui-widget-content ui-corner-all"> -->
						</td>
					</tr>
					<tr class="FormData">
						<td class="CaptionTD"><font color="red">*</font>等级：</td>
						<td class="DataTD">&nbsp;
							<span id="selectOrgGrade"><select id="orgGrade" name="orgGrade"></select></span>
<!-- 						<input type="text" jyValidate="required"  maxlength="25" name="orgGrade" class="FormElement ui-widget-content ui-corner-all"> -->
						</td>
					</tr>
					<tr class="FormData">
						<td class="CaptionTD"><font color="red">*</font>排序：</td>
						<td class="DataTD">&nbsp;
						<input type="text" jyValidate="required"  maxlength="25" name="sort" class="FormElement ui-widget-content ui-corner-all"></td>
					</tr>
					<tr class="FormData">
						<td class="CaptionTD">描述：</td>
						<td class="DataTD">&nbsp;
						<textarea rows="2" cols="10" maxlength="100" name="description" multiline="true" class="FormElement ui-widget-content ui-corner-all isSelect147"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
</div>
<!-- 角色 -->
<div id="auDiv" class="hide">
		<form id="auForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="0" border="0" class="customTable">
				<tbody>
					<tr style="display:none">
						<td colspan="2" class="ui-state-error"><input type="hidden" name="id" ></td>
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
</div>
<!-- 授权界面 -->
<div id="authorityDiv" class="hide">
		<button title="选择显示层级" value='1' class="btn btn-xs btn-success"><i class="icon-desktop"></i></button>					
		<button title="选择显示层级" value='2' class="btn btn-xs"><i class="icon-th"></i></button>		
		<button title="选择显示层级" value='3' class="btn btn-xs"><i class="icon-user"></i></button>		
		<button title="选择显示层级" value='4' class="btn btn-xs"><i class="icon-bitbucket"></i></button>
		<button title="选择显示层级" value='5' class="btn btn-xs"><i class="icon-bar-chart"></i></button>
		<input type="hidden" name="layer" value="1" >
		<input type="hidden" name="auth" value="org" >
		<input type="hidden" name="roleId" value="" >
		<ul id="authorityTree" class="ztree authorityTree"></ul>
</div>
<script src="${jypath}/static/js/system/jy/jy.public.js"></script>