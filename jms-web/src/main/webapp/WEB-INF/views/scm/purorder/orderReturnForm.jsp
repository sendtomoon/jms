<%@ page contentType="text/html;charset=UTF-8"%>
<div id="returnDiv" class="hide">
		<form id="returnForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr style="display:none">
					<td colspan="2" class="ui-state-error">
						<input type="hidden" name="id" >
						<input type="hidden" name="orgId" > 
						<input type="hidden" name="dialinOrgId" > 
						<input type="hidden" name="dialinWarehouseId" > 
						<input type="hidden" name="dialoutOrgId" >
						<input type="hidden" name="dialoutWarehouseId" >
						
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">订单编号：</td>
					<td class="DataTD">
						<input type="text" readonly placeholder="自动生成" maxlength="36" name="returnNo"/>
					</td>
					<td class="CaptionTD">拨入机构：</td>
					<td class="DataTD">
						<input type="text" readonly  name="dialinOrgName"  />
					</td>					
					<td class="CaptionTD">拨入仓库：</td>
					<td class="DataTD">
						<input type="text" readonly  name="dialinWarehouseNaem"   />
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">拨出机构：</td>
					<td class="DataTD">
						<input type="text" readonly maxlength="36" name="dialoutOrgName"  />
					</td>
					<td class="CaptionTD">拨出仓库：</td>
					<td class="DataTD">
						<input type="text" readonly name="dialoutWarehouseName"   />
					</td>					
					<td class="CaptionTD"><font color="red">*</font>退货原因：</td>
					<td class="DataTD">
						<input type="text" name="returnCause"  jyValidate="required"  maxlength="25"   class="FormElement ui-widget-content ui-corner-all"/>
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">驳回原因：</td>
					<td class="DataTD">
						<input type="text" name="rejectInfo"  class="FormElement ui-widget-content ui-corner-all"/>
					</td>					
					
				</tr>
			</tr>
			</table>
			<br/>
			<div id="addDiv">
				<div class="row">
					<form id="importForm" method="POST" onsubmit="return false;">
						&ensp; &ensp;&ensp; &ensp;<input type="text" name="code" id= "importFormCode" />
					</from>
				</div>
			</div>
			<div class="col-xs-12">
				<div style="height: 380px; overflow-x: auto;border:1px solid #ddd" >
					<table id="itemsTable" cellspacing="0" cellpadding="0" border="0" class="table table-striped table-bordered table-hover">
						<thead>
						<tr>
							<th style="width:5%"  class="center">条码</th>
 							<th style="width:6%"  class="center">名称</th>	 
							<th style="width:4%"  class="center">数量</th>
							<th style="width:4%"  class="center">辅石数量/粒数</th>
							<th style="width:4%"  class="center">重量</th>
							<th style="width:4%"  class="center">单价</th>
							<th style="width:8%"  class="center">拨出单位</th>
							<th style="width:8%"  class="center">拨出仓库</th>
							<th style="width:8%"  class="center">备注</th>
							<th style="width:5%"  class="center">操作</th>
						</tr>
						</thead>
						<tbody></tbody>
						<tfoot></tfoot>
					</table>
				</div>
			</div>
			<br/>
			<div class="col-xs-12" >
				<table border="0"style="margin-top: 8px;width:100%">
				<tr>
					<td class="CaptionTD" width="70px">备&nbsp;注:</td>	
					<td class="DataTD" colspan="5">
						<textarea rows="1" maxlength="200" id="remarks" name="remarks" multiline="true" style="width:100%"></textarea>
					</td>
				</tr>
				<tr>
					<td >
						创建人:
					</td>
					<td width="25%" >
						<span id="createName" name="createName"></span>
					</td>
					<td   class="right">
						修改人:
					</td>
					<td width="25%">
						<span id="updateName" name="updateName"></span>
					</td>
					<td class="right">
						审核人:
					</td> 
					<td width="25%">
						<span id="checkName" name="checkName"></span>
					</td>	
				</tr>
				<tr>
					<td>
						创建时间:
					</td>
					<td >
						<span id="createTime" name="createTime"></span>
					</td>
					<td>
						修改时间:
					</td>
					<td>
						<span id="updateTime" name="updateTime"></span>
					</td>
					<td >
						审核时间:
					</td>
					<td>
						<span id="checkTime" name="checkTime"></span>
					</td>
				
				</tr>
			</table>
			</div>
		</form>
</div>

