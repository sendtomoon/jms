<%@ page contentType="text/html;charset=UTF-8"%>
<div id="materialcomeDiv" style="overflow-y: hidden;" class="hide">
	<div class="col-xs-12">
		<form id="materialcomeForm" method="POST" onsubmit="return false;" >
			<table id="materialcomeTab" cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"><input type="hidden" name="id"></td>
				</tr>
				<tr class="FormData">
					<td class="Captiontd"><font color="red">*</font>入库通知单：</td>
					<td class="Datatd"><input type="text" maxlength="36"
						name="noticeNo"
						class="FormElement ui-widget-content ui-corner-all" readonly
						placeholder="自动生成"/ ></td>
					<td class="Captiontd"><font color="red">*</font>采购单号：</td>
					<td class="Datatd"><input type="text" maxlength="36"
						jyValidate="required" name="purchaseNo"
						class="FormElement ui-widget-content ui-corner-all" onchange="subPur($(this).val())"  jyValidate="required"/></td>
					<td class="Captiontd"><font color="red">*</font>拨入单位：</td>
					<td class="Datatd">
						<input type="text" id="orgName" maxlength="36" name="orgName" class="FormElement ui-widget-content ui-corner-all"readonly/ >
						<input type="hidden" id="orgId" name="orgId" / >
					</td>
					<td class="Captiontd"><font color="red">*</font>供应商：</td>
					<td class="Datatd"><input type="text" maxlength="36"
						name="surpplyName"
						class="FormElement ui-widget-content ui-corner-all"readonly/ >
						<input type="hidden" name="surpplyId" id="surpplyId"/>
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>收货人：</td>
					<td class="DataTD">
						<select id="selectReceiver" style="width: 155px;" name="receiverId" jyValidate="required" onchange="change(this.value)">
						<option value="">请选择</option>
						</select>
					</td>
				</tr>
			</table>
			<div class="btnClass" style="margin-bottom: 10px;">
				<a id="addBtn" class="lrspace3 aBtnNotd" title="增加" href="#"
					onclick="addMaterialcome()"> <i
					class="icon-plus-sign color-green bigger-180"></i>
				</a> <a class="lrspace3 aBtnNotd" title="删除" href="#"
					onclick="delMaterialcome()"> <i
					class="icon-trash color-red bigger-180"></i>
				</a>
			</div>
<!-- 			<a href="#" title="预览打印" id="print" class="lrspace3" ><i class='icon-print bigger-220'></i></a> -->
			<div style="height: 380px; overflow-y: scroll; border: 1px solid #ddd; width: 100%">
				<table id="materialcomeAdd" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th style="width:3%" class="center" id="checkId">
								<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
							</th>
							<th style="width:10%"  class="center">名称</th>
							<th style="width:7%"  class="center">材质</th>
							<th style="width:4%"  class="center">数量</th>
							<th style="width:4%"  class="center">重量</th>
							<th style="width:4%"  class="center">金重</th>
							<th style="width:4%"  class="center">石重</th>
							<th style="width:5%"  class="center">基础工费</th>
							<th style="width:5%"  class="center">附加工费</th>
							<th style="width:5%"  class="center">其它工费</th>
							<th style="width:5%"  class="center">采购成本</th>
						</tr>
					</thead>
					<tbody></tbody>
					<tfoot></tfoot>
				</table>
			</div>
			
			<div class="col-xs-12" id="materialnoteDiv" style="margin-top: 10px; margin-left: -12px;">
				<table>
					<tr>
						<td>备&nbsp;注：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td colspan="11"><textarea rows="1" cols="135" name="note" multiline="true"></textarea></td></tr>
					
					<tr id="causesDiv" class="hide">
						<td>驳回原因：</td>
						<td colspan="11"><textarea rows="1" cols="135" disabled="disabled" name="causes" multiline="true"></textarea></td>
					</tr>
					<tr>
						<td width="40px">创建人:</td>
						<td width="70px"><span id="createUser"></span></td>
						<td width="65px">创建时间:</td>
						<td width="13%"><span id="createTime"></span></td>
						<td width="50px">修改人:</td>
						<td width="70px"><span id="updateUser"></span></td>
						<td width="65px">修改时间:</td>
						<td width="13%"><span id="updateTime"></span></td>
						<td width="50px">审核人:</td>
						<td width="70px"><span id="checkUser"></span></td>
						<td width="65px">审核时间:</td>
						<td width="13%"><span id="checkTime"></span></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>

<div id="materialaddDiv" class="hide">
	<form id="materialaddForm" method="POST" onsubmit="return false;"
		enctype="multipart/form-data">
		<table cellspacing="0" cellpadding="4px;" border="0"
			class="customtable">
			<tr style="display:none">
					<td colspan="2" class="ui-state-error"><input type="hidden" id="flag" value="${flag}"></td>
				</tr>
			<tr class="FormData">
				<td class="Captiontd"><font color="red">*</font>名称：</td>
				<td class="Datatd"><input type="text" jyValidate="required"
					maxlength="16" name="name"
					class="FormElement ui-widget-content ui-corner-all"
					autocomplete="off"><input type="hidden" name="enteryno">
					<input type="hidden" name="id"></td>
			</tr>
			<tr class="FormData">
				<td><font color="red">*</font>材质：</td>
				<td style="text-align: left;"><span id="selectGoldType"><select
						id="goldType" name="goldType" style="width: 180px;"></select></span></td>

			</tr>
			<tr class="FormData">
				<td class="Captiontd"><font color="red">*</font>数量：</td>
				<td class="Datatd">
				<input type="text" jyValidate="required" maxlength="16" name="count" onkeyup="JY.Validate.limitNum(this)" disabled="disabled" class="FormElement ui-widget-content ui-corner-all"></td>
			</tr>
			<tr class="FormData">
				<td class="Captiontd">总重：</td>
				<td class="Datatd"><input type="text" onkeyup="JY.Validate.limitAmtNum(this)" maxlength="16" name="requireWt" class="FormElement ui-widget-content ui-corner-all"></td>
			</tr>
			<tr class="FormData">
				<td class="Captiontd">金重：</td>
				<td class="Datatd"><input name="goldWt" type="text" onkeyup="JY.Validate.limitAmtNum(this)" class="FormElement ui-widget-content ui-corner-all"/></td>
			</tr>
			<tr class="FormData">
				<td class="Captiontd">石重：</td>
				<td class="Datatd"><input type="text" name="stoneWt" onkeyup="JY.Validate.limitAmtNum(this)" class="FormElement ui-widget-content ui-corner-all"/></td>
			</tr>
			<tr class="FormData">
				<td><font color="red">*</font>石单位：</td>
				<td style="text-align: left;"><span id="selectStoneUnit"><select
						id="stoneUnit" jyValidate="required" name="stoneUnit" style="width: 180px;"></select></span></td>

			</tr>
			<tr class="FormData">
				<td class="Captiontd">基础工费：</td>
				<td class="Datatd"><input type="text" name="basicCost" onkeyup="JY.Validate.limitAmtNum(this)" class="FormElement ui-widget-content ui-corner-all" /></td>
			</tr>
			<tr class="FormData">
				<td class="Captiontd">附加工费：</td>
				<td class="Datatd"><input type="text" name="addCost" onkeyup="JY.Validate.limitAmtNum(this)" class="FormElement ui-widget-content ui-corner-all"/></td>
			</tr>
			<tr class="FormData">
				<td class="Captiontd">其它工费：</td>
				<td class="Datatd"><input type="text" name="otherCost" onkeyup="JY.Validate.limitAmtNum(this)" class="FormElement ui-widget-content ui-corner-all"/></td>
			</tr>
			<tr class="FormData">
				<td class="Captiontd"><font color="red">*</font>采购成本：</td>
				<td class="Datatd"><input type="text" name="costPrice" onkeyup="JY.Validate.limitAmtNum(this)" jyValidate="required" class="FormElement ui-widget-content ui-corner-all"/></td>
			</tr>
		</table>
	</form>
</div>


<div id="excel" class="hide">
	<form id="excelForm" method="POST"  action="${jypath}/scm/materialcome/toUploads">
	
 		<table>
 			<tr style="display:none">
					<td colspan="2" class="ui-state-error"><input type="hidden" name="flag" value="${flag}"></td>
				</tr>
			<tr> 
 				<td style="text-align: right;">选择Excel文件：</td> 
 					<td style="text-align: right;"><input type="text" name="importName" id='textfield' /></td> 
 				<td style="text-align: right;"> 
 					<div style="position:relative;"> 
 						<input type="button" value="浏览...."/> 
 						<input type='file' name="file" id="fileUpload" style="opacity:0;position:absolute;top:0;left:0;width:20px;height:20px;cursor:pointer;" onchange="document.getElementById('textfield').value=this.value"> 
 						<input type="submit" value="导入"/> 
 					</div> 
 				</td>
 			</tr>
 		</table> 
 		</form>
 </div>
</div>
</div>

<div id="credentialByInfoDiv" class="hide">

</div>