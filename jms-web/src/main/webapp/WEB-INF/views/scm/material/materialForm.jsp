<%@ page contentType="text/html;charset=UTF-8" %>
<div id="MaterialDiv" class="hide">
	<form id="ScmaterialForm" method="POST" onsubmit="return false;" >
	  <table cellspacing="0" cellpadding="4px;" border="0"class="customTable">
		<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>状态：</td>
			<td class="DataTD"><label class="inline isValidCheckbox">
					<input type="checkbox" checked="checked"  sh-isValid=""role="checkbox" class="FormElement ace ace-switch ace-switch-5" />
					<span class="lbl"></span> <!-- cb-status和Yes和No选择框配套使用--> <input type="hidden" hi-isValid="" name="status" value="1" />
		</label></td>
			<td class="CaptionTD"><font color="red">*</font>名称：</td>
			<td class="DataTD" ><input type="text" jyValidate="required" maxlength="64" name="name" class="FormElement ui-widget-content ui-corner-all"></td>
		</tr>
		<tr class="FormData">
		<input type="hidden" name="id">
		<td class="CaptionTD"><font color="red">*</font>条码：</td>
		<td class="DataTD"><input type="text"   name="code" placeholder="系统自动生成" >
		</td>                    
        <td class="CaptionTD"><font color="red">*</font>分类：</td>
		<td  class="DataTD">
		    <input id="cateInput" type="text" readonly value="" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" onclick="showOrgComp(); return false;"/>		
		    <input type="hidden" name="cateId">
		    <input type="hidden" name="cateName">
		    <a href="#" title="清空" onclick="emptyOrgComp(); return false;" class="lrspace3 aBtnNoTD" data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a>
		    <div id='categoryContentListDiv' class="menuContent ztreeMC" style="display: none; position: absolute;">
		    <ul id="selectTree" class="ztree preOrgTree"></ul>
		</div>
		</td>
		</tr>
		 		
  		</tr>      
		<tr class="FormData">
            <td class="CaptionTD"><font color="red">*</font>批次号：</td>
			<td class="DataTD"><input name="batchNum" jyValidate="required" type="text" maxlength="30" /></td>          
			<td class="CaptionTD"><font color="red"></font>款号：</td>
			<td class="DataTD"><input type="text" id="moudleCode" maxlength="36" name="moudleCode" class="FormElement ui-widget-content ui-corner-all" placeholder="请输入Code检索"></td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red"></font>台宽比：</td>
			<td class="DataTD"><input name="pwidth" type="text" maxlength="20" class="FormElement ui-widget-content ui-corner-all" placeholder="如：70%"/></td>
            <td class="CaptionTD"><font color="red"></font>尺寸：</td>
			<td class="DataTD"><input name="materialSize" type="text" maxlength="20" class="FormElement ui-widget-content ui-corner-all" /></td>

		</tr>
		 <tr class="FormData">
			<td class="CaptionTD"><font color="red"></font>亭深比：</td>
			<td class="DataTD"><input name="pdeep" type="text" maxlength="20"  class="FormElement ui-widget-content ui-corner-all" /></td>
			<td class="CaptionTD"><font color="red">*</font>类型：</td>
			<td class="DataTD"><span id="typeForm"><select  id="type" name="type" style="width:157px;"></select></span>
		</tr>
			<tr class="FormData">
		    <td class="CaptionTD"><font color="red">*</font>计价方式：</td>
			<td class="DataTD"><input id="feeType"  name="feeType" type="radio"  value="1"  />件 &nbsp;
			                   <input id="feeType2" name="feeType" type="radio"   value="2" />克</td>
			<td class="CaptionTD"><font color="red"></font>颜色：</td>
			<td class="DataTD"><span id="colorForm"><select  id="color" name="color" style="width:157px;"></select></span>
			</td>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red"></font>切工：</td>
			<td class="DataTD"><span id="cutForm"><select  id="cut" name="cut" style="width:157px;"></select></span>
			<td class="CaptionTD"><font color="red"></font>净度：</td>
			<td class="DataTD"><span id="clartityForm"><select  id="clartity" name="clartity" style="width:157px;"></select></span>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red"></font>荧光：</td>
			<td class="DataTD"><span id="fluoreScenceForm"><select  id="fluoreScence" name="fluoreScence" style="width:157px;"></select></span>
			<td class="CaptionTD"><font color="red"></font>石形：</td>
			<td class="DataTD"><span id="stoneShapeForm"><select  id="stoneShape" name="stoneShape" style="width:157px;"></select></span>

		</tr>    
		<tr class="FormData"> 
            <td class="CaptionTD"><font color="red"></font>对称性：</td>
			<td class="DataTD"><span id="symmetyForm"><select  id="symmety" name="symmety" style="width:157px;"></select></span>
			<td class="CaptionTD"><font color="red"></font>抛光：</td>
			<td class="DataTD"><span id="polishForm"><select  id="polish" name="polish" style="width:157px;"></select></span>
		</tr>
		<tr class="FormData">
			<td class="CaptionTD"><font color="red"></font>证书号：</td>
			<td class="DataTD"><input name="cerNum" type="text" maxlength="30" class="FormElement ui-widget-content ui-corner-all" /></td>			
  			<!-- <td class="CaptionTD"><font color="red"></font>单价：</td>
			<td class="DataTD"><input name="price" type="text"  maxlength="16" onkeyup="JY.limitAmtNumMinusSign(this)" /></td>  -->
		</tr>
		<tr class="FormData">
			<td class="CaptionTD">备注：</td>
			<td  class="DataTD" colspan="3"><textarea rows="2" maxlength="200" cols="63"   name="remarks" ></textarea></td>
		</tr>		
	</table>
	</form>
</div>
