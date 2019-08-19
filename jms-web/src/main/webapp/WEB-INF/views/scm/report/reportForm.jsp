<%@ page contentType="text/html;charset=UTF-8"%>
<div id="baseDiv" class="hide">
	<div class="col-xs-12">
		<form id="baseForm" method="POST" onsubmit="return false;" enctype="multipart/form-data">
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"><input type="hidden" name="id"></td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD" >质检单号：</td>
					<td class="DataTD" >
						<input type="text"  maxlength="36"  name="reportNo" class="FormElement ui-widget-content ui-corner-all" placeholder="系统自动生成" readonly />
					</td>
					<td class="CaptionTD"><font color="red">*</font>入库通知单：</td>
					<td class="DataTD" >
						<input type="text"  maxlength="36"  id="entryNo" name="entryNo" class="FormElement ui-widget-content ui-corner-all" onchange="subcode($(this).val())" jyValidate="required"/>
					</td>
					<td class="CaptionTD" ><font color="red">*</font>供应商：</td>
					<td class="DataTD" >
						<input type="text"  maxlength="36"  name="supplierId" class="FormElement ui-widget-content ui-corner-all" readonly jyValidate="required"/>
					</td>
					<td class="CaptionTD" ><font color="red">*</font>质检人：</td>
					<td class="DataTD" >
						<span id="selectUser"><select  data-placeholder="质检人" name="qcUserId" class="isSelect95" jyValidate="required"></select></span>&nbsp;&nbsp;
						<!-- <input type="text"  maxlength="36"  name="qcUserName" class="FormElement ui-widget-content ui-corner-all" readonly/> -->
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD" ><font color="red">*</font>质检数量：</td>
					<td class="DataTD" >
						<input type="text"  maxlength="10"  name="qcNumber" id="qcNumber" class="FormElement ui-widget-content ui-corner-all" jyValidate="required" onkeyup='JY.Validate.limitNum(this)' onchange="changeQcNumber($(this).val())"/>
					</td>
					<td class="CaptionTD" ><font color="red">*</font>质检重量：</td>
					<td class="DataTD" >
						<input type="text"  maxlength="10"  name="qcWeight" id="qcWeight" class="FormElement ui-widget-content ui-corner-all" jyValidate="required" onkeyup='JY.Validate.limitDouble(this)' onchange="changeQcWeight($(this).val())"/>
					</td>
				</tr>
			</table>
			
			<br/>
			<div class="btnClass" style="margin-bottom:10px;">
				<!-- <a id="addBtn" class="lrspace3 aBtnNoTD" title="增加" href="#" >
					<i class="icon-plus-sign color-green bigger-180"></i>
				</a> -->
				<a class="lrspace3 aBtnNoTD" title="删除多个" href="#" onclick="delReport()" >
					<i class="icon-trash color-red bigger-180"></i>
				</a>
				条码：<input type="text" id="enteryno" placeholder="请输入产品条码" class="input-large" onchange="subcodeTwo()">
			</div>
			<div class="col-xs-12" style="height: 380px;overflow-y: scroll;border: 1px solid #ddd">
				<table id="reportAdd" cellspacing="0" cellpadding="0" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th style="width:3%"  class="center"><label><input type="checkbox" class="ace" ><span class="lbl"></span></label></th>
							<th style="width:8%"  class="center">条码</th>
							<th style="width:8%"  class="center">名称</th>
							<th style="width:6%"  class="center">材质</th>
							<th style="width:6%"  class="center">不合格数量</th>
							<th style="width:6%"  class="center">不合格重量</th>
							<th style="width:6%"  class="center">问题原因</th>	
							<th style="width:6%"  class="center">说明</th>
						</tr>
					</thead>
					<tbody></tbody>
					<tfoot></tfoot>
				</table>
			</div>
			<div class="col-xs-12" style="margin-top: 10px;margin-left: -12px;">
				<table>
					<tr>
						<td>附&nbsp;&nbsp;件：</td>
						<td colspan="3">
							<input type="file" name="file"></input>
						</td>
						<td colspan="8">
							<span id="file"></span>
						</td>
					</tr>
					<tr id="causesDiv" class="hide" >
							<td>驳回原因:</td>
							<td colspan="11"><textarea rows="1" cols="125" disabled="disabled" name="rejectinfo" multiline="true"></textarea></td>
						</tr>
					<tr>
						<td>备&nbsp;&nbsp;注：</td>
						<td colspan="11"><textarea rows="1" cols="125" name="remarks" multiline="true"></textarea></td>
					</tr>
					<tr>
						<td width="40px" >创建人:</td>
						<td width="70px" ><span id="createUser"></span></td>
						<td width="65px" >创建时间:</td>
						<td width="13%" ><span id="createTime"></span></td>
						<td width="50px" >修改人:</td>
						<td width="70px" ><span id="updateUser"></span></td>
						<td width="65px" >修改时间:</td>
						<td width="13%" ><span id="updateTime"></span></td>
						<td width="50px" >审核人:</td>
						<td width="70px" ><span id="checkUser"></span></td>
						<td width="65px" >审核时间:</td>
						<td width="13%" ><span id="checkTime"></span></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>