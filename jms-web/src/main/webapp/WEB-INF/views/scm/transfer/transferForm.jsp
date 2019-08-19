<%@ page contentType="text/html;charset=UTF-8" %>
<div id="move-transferDiv" class="hide">
   <div class="col-xs-12">
<form id="move-transferForm" method="POST" onsubmit="return false;" >
  <div id="transferDiv" class="hide">
	<div class="col-xs-12">
		<!-- <form id="transferDivForm" method="POST" onsubmit="return false;" > -->
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"><input type="hidden" name="id"></td>
				</tr>
			</table>
			<table>
				<tr class="FormData">
					<td class="CaptionTD" ><font color="red">*</font>移库单号：</td>
					<td class="DataTD"><input type="text"   name="transferNo" readonly  placeholder="系统自动生成" ></td>
				    <td class="CaptionTD"><font color="red">*</font>类型：</td>
			        <td class="DataTD"><input type="text"  name="typeName" readonly  placeholder="移库" ><input type="hidden" name="type" value="1"/></td></span>
				</tr>
				<tr class="FormData">
					
				</tr> 
				
			</table>
			
			
			<table>
			</table>
			<br/>
			<div class="btnClass" style="margin-bottom:10px;">
				<a id=addBtn class="lrspace3 aBtnNoTD" title="增加" href="#" >
					<i class="icon-plus-sign color-green bigger-180"></i>
				</a>
				<a class="lrspace3 aBtnNoTD" title="删除多个" href="#" onclick="delMaterialin()" >
					<i class="icon-trash color-red bigger-180"></i>
				</a>
				<input type="text" id="enteryno" placeholder="这里输入条码" class="input-large" onblur="subcode()">
			</div>
			
			<div style="height: 380px;overflow-y: scroll;border:1px solid #ddd;width:100%">
				<table id="transferAdd" cellspacing="0" cellpadding="0" border="0" class="table table-striped table-bordered table-hover" style="width:99.9%">
					<thead>
						<tr>
							<th style="width:2%"  class="center"><label><input type="checkbox" class="ace" ><span class="lbl"></span></label></th>
							<th style="width:8%"  class="center">条码</th>
							<th style="width:6%"  class="center">数量</th>
							<th style="width:6%"  class="center">重量</th>	
							<th style="width:6%"  class="center">称差</th>
							<th style="width:6%"  class="center">拨出仓库</th>
							<th style="width:6%"  class="center">拨出仓位</th>
							<th style="width:6%"  class="center">拨入仓库</th>
							<th style="width:6%"  class="center">拨入仓位</th>
						</tr>
						
						
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
				<div class="col-xs-12" style="margin-top: 10px;margin-left: -12px;">
				<table>
					<tr>
						<td>备&nbsp;注：</td>
						<td colspan="11"><textarea rows="1" cols="125" name="remarksTwo" multiline="true"></textarea></td>
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
		
		<!-- </form> -->
	</div>
  </div>
 </form>
 </div>
<div id="moveDiv" class="hide">
		<form id="transferForm" method="POST" onsubmit="return false;"  enctype="multipart/form-data">
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr class="FormData">
					<td class="CaptionTD"><font color="red"  >*</font>条码：</td>
					<td class="DataTD"><input type="text" jyValidate="required" id="queryCode" maxlength="16" name="code" class="FormElement ui-widget-content ui-corner-all"> <input type="hidden" name="enteryno"> <input type="hidden" name="id"></td>
				</tr>	
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>数量：</td>
					<td class="DataTD"><input type="number" jyValidate="required" onkeyup="JY.Validate.limitNum(this)"  maxlength="16" name="num"  min="1"  class="FormElement ui-widget-content ui-corner-all"/></td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>重量：</td>
					<td class="DataTD"><input type="text" jyValidate="required" maxlength="16" name="aaa" class="FormElement ui-widget-content ui-corner-all" onkeyup="JY.Validate.limitDouble(this)"/>
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>称差：</td>
					<td class="DataTD">
						<input type="text" name="diffWeight"  jyValidate="required" class="FormElement ui-widget-content ui-corner-all" onkeyup="JY.Validate.limitDouble(this)"/>
					</td>
				</tr>	
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>拨出仓库：</td>
					<td class="DataTD"><input type="text" readonly maxlength="16" name="outWarehouseIdName" class="FormElement ui-widget-content ui-corner-all"><input type="hidden" name="outWarehouseId">
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>拨出仓位：</td>
					<td class="DataTD">
						<input type="text"  readonly maxlength="16" name="outLocationIdName" class="FormElement ui-widget-content ui-corner-all"><input type="hidden" name="outLocationId">
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>拨入仓库：</td>
					<td class="DataTD">
                        <span><select  id="selectWarehouse" style="width: 155px;" name="inWarehouseId" jyValidate="required" onchange="chgWarehouseLocation('selectWarehouse','selectWarehouseLocation')"></select></span>					
					</td>
				</tr>
 				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>拨入仓位：</td>
					<td class="DataTD">
                        <span><select  id="selectWarehouseLocation" style="width: 155px;" name="inLocationId"  jyValidate="required"></select></span>					
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