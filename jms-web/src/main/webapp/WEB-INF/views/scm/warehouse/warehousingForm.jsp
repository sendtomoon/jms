<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 岗位-->
<div id="warehousingDiv" class="hide">
		<form id="warLoForm" method="POST" onsubmit="return false;" >
			<table class="customTable">
				<tbody>
					<tr>
						<td>&nbsp;<font color="red">*</font>请选择仓库：
							<span>
									<input type="hidden" name="productId" id="productId"/>
									<select  id="selectWarehouse" style="width: 120px;" name="warehouseId" jyValidate="required" onchange="chgWarehouseLocation($(this))">
									</select>
							</span>
						</td>
					</tr>
					<tr style="margin-top: 80px">
						<td>&nbsp;<font color="red">*</font>请选择仓位：
							<span>
									<select  id="selectWarehouseLocation" style="width: 120px;" name="warehouseLocation"  jyValidate="required">
									
									</select>
							</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
</div>
