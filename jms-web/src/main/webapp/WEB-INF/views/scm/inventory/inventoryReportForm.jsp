<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 编辑新增要货 -->
<div id="reportDiv" class="hide">
		<form id="reportForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" border="0" class="customTable">
				<tr style="display:none">
					<td  class="ui-state-error"><input type="hidden" name="id" > <input type="hidden" name="inventoryId" ></td>
					
				</tr>
				<tr class="FormData">
					<td class="CaptionTD" >计划编号：</td>
					<td class="DataTD">
						<input type="text" readonly  maxlength="36" name="inventoryNo"  class="FormElement ui-widget-content ui-corner-all"/>
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD" >差异说明：</td>
					<td  class="DataTD"><textarea rows="4" maxlength="200" cols="63"   name="content" ></textarea></td>
				</tr>
			</table>
<!-- 		</form> -->
		<div class="col-xs-12" >
			<div style="height: 430px; overflow-x: auto;border:1px solid #ddd" >
				<form id="reoprtBaseForm" class="form-inline" method="POST" onsubmit="return false;">
					<input type="hidden" name="inventoryId" >
				</form>
				<table id="reoprtTable" cellspacing="0" cellpadding="0" border="0"  class="table table-striped table-bordered table-hover">
					<thead>
					<tr>
						<th style="width:15%"  class="center">条码</th>
						<th style="width:15%"  class="center">名称</th>
						<th style="width:10%"  class="center">数量</th>
						<th style="width:10%"  class="center">总重</th>
						<th style="width:10%"  class="center">盘点数量</th>
						<th style="width:10%"  class="center">盘点总重</th>
						<th style="width:15%"  class="center">是否差异</th>
						<th style="width:15%"  class="center">差异原因</th>
					</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
			<div class="row">
				<div class="col-sm-4">
								
				</div>
				<div class="col-sm-8">
					<!--设置分页位置-->
					<div id="pageing1" class="dataTables_paginate paging_bootstrap">
						<ul class="pagination"></ul>
					</div>
				</div>
			</div>
		</div>
		<div id = "orderDiv2" class="col-xs-12" >
			<table border="0"style="margin-top: 8px;width:100%">
				<tr>
					<td class="CaptionTD" width="70px">备&nbsp;注:</td>	
					<td class="DataTD" colspan="5">
						<textarea rows="1" maxlength="200" id="description" name="description" multiline="true" style="width:100%"></textarea>
					</td>
				</tr>
				<tr>
					<td >
						创建人:
					</td>
					<td width="25%">
						<span id="createName" name="createName"></span>
					</td>
					<td   >
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
					<td  >
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

