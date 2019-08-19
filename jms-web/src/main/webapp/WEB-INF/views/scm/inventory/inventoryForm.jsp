<%@ page contentType="text/html;charset=UTF-8"%>
<div id="ScmInventoryDiv" class="hide">
	<div class="col-xs-12">
		<form id="ScmInventoryForm" method="POST" onsubmit="return false;" >
			<table id="ScmInventoryTab" cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"><input type="hidden" name="id"></td>
				</tr>
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"><input type="hidden" name="status"></td>
				</tr>
				<tr class="FormData">
					<td class="Captiontd">盘点单号：</td>
					<td class="Datatd"><input type="text" maxlength="36"
						name="inventoryNo"
						class="FormElement ui-widget-content ui-corner-all" readonly
						placeholder="自动生成"/ ></td>
					<td class="Captiontd"><font color="red">*</font>盘点机构：</td>
					<td class="Datatd">
						<div id="productOrg">
							<input id="productOrgName" type="text" jyValidate="required" placeholder="请选组织机构" readonly />
							<input id="productOrgId" name="orgId" type="hidden"/>
						</div>
					</td>
					<td class="Captiontd"><font color="red">*</font>盘点仓库：</td>
					<td class="Datatd">
						<select id="selectWarehouse" style="width: 120px;" name="warehouseId" jyValidate="required" onchange="changeWarehouse($(this).val(), 'selectWarehouseLocation')"></select>
					</td>
					<td class="Captiontd"><font color="red">*</font>盘点仓位：</td>
					<td class="Datatd">
						<select id="selectWarehouseLocation" style="width: 120px;" name="locationId"  jyValidate="required"></select>
					</td>
				</tr>
				<tr class="FormData">
					<td class="Captiontd">货类：</td>
					<td class="Datatd">
						<span id="selProdCate" ><select name="cateId" style="width:125px;"></select></span>
					</td>
					<td class="Captiontd">货组：</td>
					<td class="Datatd">
						<span id="selJewelryType"><select name="cateJewelryId" style="width:125px;"></select></span>
					</td>
					<td class="Captiontd">
						<button id="queryProductBtn" type="button" class="btn btn-success btn-md ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover" role="button" aria-disabled="false">检索</button>
					</td>
				</tr>
			</table>
			<!--  -->
			<div class="btnClass" style="margin-bottom: 10px;">
				<a class="lrspace3 aBtnNotd" title="删除" href="#" onclick="delInventoryDetail()">
					<i class="icon-trash color-red bigger-180"></i>
				</a>
			</div>
			
			<div style="height: 380px; overflow-y: scroll; border: 1px solid #ddd; width: 100%">
				<table id="ScmInventoryAdd" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th style="width:8%" class="center" id="checkId">
								<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
							</th>
							<th style="width:12%"  class="center">条码</th>
							<th style="width:16%"  class="center">名称</th>
							<th style="width:10%"  class="center">数量</th>
							<th style="width:10%"  class="center">总重</th>
							<th style="width:10%"  class="center">盘点数量</th>
							<th style="width:10%"  class="center">盘点总重</th>
							<th style="width:12%"  class="center">是否差异</th>
							<th style="width:12%"  class="center">差异原因</th>
						</tr>
					</thead>
					<tbody></tbody>
					<tfoot></tfoot>
				</table>
			</div>
			<div style="height: 30px; margin-top: 10px;">
				<font color="red">*</font>盘点执行日期：<input type="text" jyValidate="required" id="dateRender" name="executeTime" readonly />
			</div>
			<div class="col-xs-12" style="margin-top: 10px; margin-left: -12px;">
				<table>
					<tr>
						<td>备&nbsp;注：</td>
						<td colspan="11"><textarea rows="1" cols="120" name="note" multiline="true"></textarea></td>
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