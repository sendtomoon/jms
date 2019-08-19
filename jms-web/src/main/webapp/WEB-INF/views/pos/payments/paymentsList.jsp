<%@ page contentType="text/html;charset=UTF-8"%>
<div id="paymentsListDiv" class="hide">
	<div class="col-xs-12">
		<div class="col-xs-8">
			<div id="operateDiv">
				<button type="button"  onclick="delRowList()" class="btn btn-success btn-xs" style="border-radius:4px;">删除选中行</button>
				<button type="button" onclick="onAddRow()" class="btn btn-success btn-xs" style="border-radius:4px;">新增一行</button>
			</div>
		</div>
		<form id="paymentsListForm" method="POST" class="form-inline" onsubmit="return false;">
			<input type="hidden" id="paymentsOrderNo"  name='orderNo' value='222'/>
			<input type="hidden" id="paymentsOrderId"  name='orderId' value='222'/>
			<input type="hidden" id="paymentsType"  name="type" value='2'/>
		</form>
		<div class="col-xs-12">
			<table  id="paymentsTable" cellspacing="0" cellpadding="0" border="0" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th style="width:3%" class="center">
							<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
						</th>
						<th style="width:5%"  class="center hidden-480">序号</th>
						<th style="width:8%" class="center">收款方式</th>
						<th style="width:8%" class="center">银行/币种/面额</th>
						<th style="width:8%" class="center">支票号/卡号/赠券数量</th>
						<th style="width:8%" class="center">汇率</th>
						<th style="width:8%" class="center">人民币金额</th>
						<th style="width:8%" class="center">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		<div class="col-xs-10">
		</div>
		<div class="col-xs-2">
			应收：<span id="receivable" name="receivable">20000</span> <br/>
			尚欠：<span id="owed" name="owed"></span>
		</div>
		<script src="${jypath}/static/js/pos/payments/payments.js"></script>
	</div>
	
	
</div>