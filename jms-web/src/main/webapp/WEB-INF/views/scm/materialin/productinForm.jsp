<%@ page contentType="text/html;charset=UTF-8" %>
<div id="baseDiv" class="hide">
	<div class="col-xs-12">
		<form id="baseForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"><input type="hidden" name="id"></td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD" >入库单号：</td>
					<td class="DataTD" >
						<input type="text"  maxlength="36"  name="enteryno" class="FormElement ui-widget-content ui-corner-all"  readonly placeholder="自动生成"/ >
					</td>
					<td class="CaptionTD"><font color="red">*</font>订单单号：</td>
					<td class="DataTD">
						<input type="text" id="purno"  maxlength="36"  jyValidate="required"  name="purno" class="FormElement ui-widget-content ui-corner-all" />
					</td>
					<td class="CaptionTD"><font color="red">*</font>仓库：</td>
					<td class="DataTD">
						<select id="selectWarehouse" style="width: 157px;" name="warehouseId" jyValidate="required" onchange="chgWarehouseLocation($(this).val(),'selectWarehouseLocation')"></select>
					</td>
					<td class="CaptionTD"><font color="red">*</font>仓位：</td>
					<td class="DataTD">
						<select id="selectWarehouseLocation" style="width: 157px;" name="locationId"  jyValidate="required"></select>
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">入账类型：</td>
					<td class="DataTD"><span id="selFeeType"><select id="feeType" name="feeType" style="width:157px;"></select></span></td>
				</tr>
			</table>
			<!-- <table>
				<tr class="FormData">
				 	<td class="CaptionTD">采购成本：</td>
					<td class="DataTD"><input style="width:60px" type="text" id="purcosts" class="FormElement ui-widget-content ui-corner-all" readonly/></td>
				 	<td class="CaptionTD">核价成本：</td>
					<td class="DataTD"><input style="width:60px" type="text" id="checkcosts"  class="FormElement ui-widget-content ui-corner-all" readonly/></td>
					<td class="CaptionTD">财务成本：</td>
					<td class="DataTD"><input style="width:60px" type="text" id="finacosts"  class="FormElement ui-widget-content ui-corner-all" readonly/></td>
					<td class="CaptionTD">牌价合计：</td>
					<td class="DataTD"><input style="width:60px" type="text" id="totalprices"  class="FormElement ui-widget-content ui-corner-all" readonly/></td>
					<td class="CaptionTD">称差重量：</td>
					<td class="DataTD"><input style="width:60px" type="text" id="diffweights" class="FormElement ui-widget-content ui-corner-all" readonly/></td>
					<td class="CaptionTD">数量总计：</td>
					<td class="DataTD"><input style="width:60px" type="text" id="totalNum"  class="FormElement ui-widget-content ui-corner-all" readonly/></td>
					<td class="CaptionTD">件数总计：</td>
					<td class="DataTD"><input style="width:60px" type="text" id="totalCount"  class="FormElement ui-widget-content ui-corner-all" readonly/></td>
				</tr>  
			</table> -->
			<br/>
			<div class="btnClass" style="margin-bottom:10px;">
				<!-- <a id="addBtn" class="lrspace3 aBtnNoTD" title="增加" href="#" >
					<i class="icon-plus-sign color-green bigger-180"></i>
				</a> -->
				<a class="lrspace3 aBtnNoTD" title="删除多个" href="#" onclick="delMaterialin()" >
					<i class="icon-trash color-red bigger-180"></i>
				</a>
				<input type="text" id="enteryno" placeholder="这里输入条码" class="input-large" onchange="subcode()">
			</div>
			<div class="col-xs-12" style="height: 380px;overflow-y: scroll;border: 1px solid #ddd">
				<table id="materialinAdd" cellspacing="0" cellpadding="0" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th style="width:2%"  class="center"><label><input type="checkbox" class="ace" ><span class="lbl"></span></label></th>
							<th style="width:7%"  class="center">条码</th>
							<th style="width:6%"  class="center">名称</th>
							<th style="width:4%"  class="center">计价方式</th>
							<th style="width:4%"  class="center">数量</th>
							<th style="width:4%"  class="center">重量</th>
							<th style="width:4%"  class="center">称差</th>
							<th style="width:4%"  class="center hidePrice">采购成本</th>
							<th style="width:4%"  class="center hidePrice">核价成本</th> 
							<th style="width:4%"  class="center hidePrice">财务成本</th>
							<th style="width:4%"  class="center">牌价</th>
						</tr>
					</thead>
					<tbody></tbody>
					<tfoot></tfoot>
				</table>
			</div>
			
		
			<div class="col-xs-12" style="margin-top: 10px;margin-left: -12px;">
				<table>
					<tr>
						<td>备&nbsp;&nbsp;注：</td>
						<td colspan="11"><textarea rows="1" cols="125" name="remarksTwo" multiline="true"></textarea></td>
					</tr>
					<tr id="causesDiv">
						<td>驳回原因：</td>
						<td colspan="11"><textarea rows="1" cols="125" disabled="disabled" name="description" multiline="true"></textarea></td>
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


<div id="materialinDiv" class="hide">
	<form id="materialinForm" method="POST" onsubmit="return false;"  enctype="multipart/form-data">
		<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
			<tr class="FormData">
				<td class="CaptionTD"><font color="red">*</font>条码：</td>
				<td class="DataTD"><input id="findCode" type="text" jyValidate="required"  maxlength="16" name="code" class="FormElement ui-widget-content ui-corner-all"  autocomplete="off"><input type="hidden" name="enteryno"> <input type="hidden" name="id"></td>
			</tr>	
			<tr class="FormData">
				<td style="text-align: right;">&ensp;名称：</td>
				<td style="text-align: left;"><input type="text"  name="name" class="FormElement ui-widget-content ui-corner-all" readonly="readonly"> </td>
			</tr>
			<tr class="FormData">
				<td style="text-align: right;">&ensp;计价方式：</td>
				<td style="text-align: left;"><input type="text"  name="feeType" class="FormElement ui-widget-content ui-corner-all" readonly="readonly"> </td>
			</tr>
			<tr class="FormData">
			<td class="CaptionTD"><font color="red">*</font>数量：</td>
				<td class="DataTD"><input type="number" jyValidate="required" onkeyup="JY.Validate.limitNum(this)"  maxlength="16" name="num"  min="1"  class="FormElement ui-widget-content ui-corner-all" value="1" readonly="readonly"></td>
			</tr>
			<tr class="FormData">
				<td class="CaptionTD">
					<font color="red">*</font>重量：</td>
				<td class="DataTD"><input type="text" jyValidate="required" maxlength="16" name="weight" class="FormElement ui-widget-content ui-corner-all" onkeyup="JY.Validate.limitDouble(this)" readonly="readonly">
				</td>
			</tr>
			<tr class="FormData">
				<td class="CaptionTD">
					<font color="red">*</font>称差：</td>
				<td class="DataTD">
					<input type="text" name="diffweight" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" onkeyup="this.value=this.value.replace(/^[^0-9\-]*([\d\.\-?]*(?:\d{0,2})?).*$/g, '$1')"/>
				</td>
			</tr>
			<tr class="FormData">
				<td class="CaptionTD">
					<font color="red">*</font>单价：</td>
				<td class="DataTD"><input name="price"  type="text" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" onkeyup="JY.Validate.limitDouble(this)"/>
				</td>
			</tr>
			 <tr class="FormData">
				<input name=cerofficeName  type="hidden"  />
				<td class="CaptionTD"><font color="red">*</font>采购成本：</td>
				<td class="DataTD"><input type="text" name="purcost" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" onkeyup="JY.Validate.limitDouble(this)"/></td>
			</tr>
			<tr class="FormData">
				<td class="CaptionTD">
					<font color="red">*</font>销售价：</td>
				<td class="DataTD">
					<input type="text" name="saleprice" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" onkeyup="JY.Validate.limitDouble(this)"/>
				</td>
			</tr>
			<tr class="FormData">
				<td class="CaptionTD">
					<font color="red">*</font>财务成本：</td>
				<td class="DataTD">
					<input type="text" name="finacost" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" onkeyup="JY.Validate.limitDouble(this)"/>
				</td>
			</tr>
			<tr class="FormData">
				<td class="CaptionTD">备注：</td>
				<td class="DataTD">
					<textarea rows="2" cols="30" name="remarks" multiline="true"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>
</div>
<div id="printDiv" class="hide">

</div>