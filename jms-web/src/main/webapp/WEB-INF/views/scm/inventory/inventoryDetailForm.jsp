<%@ page contentType="text/html;charset=UTF-8"%>
<div id="ScmInventoryDetailDiv" class="hide">
	<div class="col-xs-12">
		<form id="ScmInventoryDetailForm" method="POST" onsubmit="return false;" >
			<div class="btnClass" style="margin-bottom: 10px;">
				<a class="lrspace3 aBtnNotd" title="删除" href="#" onclick="delInventoryDetail()">
					<i class="icon-trash color-red bigger-180"></i>
				</a>
				<input id='inventoryId' type="hidden"  name="inventoryId"  />
				<input id='inventoryNo' type="hidden"  name="inventoryNo" />
				<input id='orgId' type="hidden"  name="orgId" />
				<input id='whouseId' type="hidden"  name="whouseId" />
				<input id='locationId' type="hidden"  name="locationId" />
				&nbsp;&nbsp;条码：<input type="text" id="enteryno" placeholder="这里输入条码" autofocus class="input-large" onchange="subCode()">
				<a id="listBtn" class="lrspace3 aBtnNoTD" title="列出其他未盘点商品" href="#" >
					<i class="icon-list-alt color-green bigger-180"></i>
				</a>
			</div>
			<div  style="height: 380px; overflow-y: scroll; border: 1px solid #ddd; width: 100%">
				<table id="ScmInventoryDetailTable" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th style="width:4%" class="center" id="checkId">
								<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
							</th>
							<th style="width:16%"  class="center">条码</th>
							<th style="width:16%"  class="center">商品名称</th>
							<th style="width:10%"  class="center">数量</th>
							<th style="width:10%"  class="center">重量</th>
							<th style="width:10%"  class="center">盘点数量</th>
							<th style="width:10%"  class="center">盘点重量</th>
							<th style="width:12%"  class="center">是否差异</th>
							<th style="width:12%"  class="center">差异原因</th>
						</tr>
					</thead>
					<tbody></tbody>
					<tfoot></tfoot>
				</table>
			</div>
		</form>
	</div>
</div>

<div id="ScmInventoryDetailAddDiv" class="hide">
	<form id="ScmInventoryDetailAddForm" method="POST" onsubmit="return false;"
		enctype="multipart/form-data">
		<table cellspacing="0" cellpadding="4px;" border="0"
			class="customtable">
			<tr class="FormData">
				<td class="Captiontd"><font color="red">*</font>商品条码：</td>
				<td class="Datatd">
					<input type="text" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" />
				</td>
			</tr>
			<tr class="FormData">
				<td><font color="red">*</font>商品名称：</td>
				<td style="text-align: left;">
					<input type="text" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" />
				</td>
			</tr>
			<tr class="FormData">
				<td class="Captiontd"><font color="red">*</font>数量：</td>
				<td class="Datatd">
					<input type="text" jyValidate="required" maxlength="16" name="count" onkeyup="JY.Validate.limitAmtNum(this)" class="FormElement ui-widget-content ui-corner-all">
				</td>
			</tr>
			<tr class="FormData">
				<td class="Captiontd"><font color="red">*</font>总重：</td>
				<td class="Datatd">
					<input type="text" jyValidate="required" onkeyup="JY.Validate.limitAmtNum(this)" maxlength="16" name="requireWt" class="FormElement ui-widget-content ui-corner-all">
				</td>
			</tr>
			<tr class="FormData">
				<td class="Captiontd"><font color="red">*</font>盘点数量：</td>
				<td class="Datatd">
					<input type="text" name="actualWt" onkeyup="JY.Validate.limitAmtNum(this)" jyValidate="required" class="FormElement ui-widget-content ui-corner-all"/>
				</td>
			</tr>
			<tr class="FormData">
				<td class="Captiontd"><font color="red">*</font>盘点总重：</td>
				<td class="Datatd">
					<input name="goldWt" type="text" onkeyup="JY.Validate.limitAmtNum(this)" onkeyup="JY.Validate.limitAmtNum(this)" class="FormElement ui-widget-content ui-corner-all"/>
				</td>
			</tr>
			<tr class="FormData">
				<td class="Captiontd">是否差异：</td>
				<td class="Datatd">
					<span id="selectDiffType"><select id="detailIsDiff" name="detailIsDiff" style="width:125px;"></select></span>
				</td>
			</tr>
			<tr class="FormData">
				<td class="Captiontd">差异原因：</td>
				<td class="Datatd"><textarea name="addCost" class="FormElement ui-widget-content ui-corner-all"/></textarea></td>
			</tr>
		</table>
	</form>
</div>