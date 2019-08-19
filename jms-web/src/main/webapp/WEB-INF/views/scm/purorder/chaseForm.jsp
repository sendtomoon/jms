<%@ page contentType="text/html;charset=UTF-8"%>
<div id="pOrderDiv" class="hide" onsubmit="return false;">
		<table cellspacing="0" cellpadding="4px;" border="0"
			class="customTable">
			<tr style="display: none">
				<td colspan="2" class="ui-state-error"><input type="hidden"
					name="id"> <input type="hidden" name="orgId"> <input
					type="hidden" name="orderType1" value='1'> <input
					type="hidden" name="franchiseeId"> <input type="hidden"
					name="franchiseeName"> <input type="hidden" name="orgId">
				</td>
			</tr>
			<tr class="FormData">
				<td class="CaptionTD"><font color="red">*</font>订单编号：</td>
				<td class="DataTD"><input type="text" readonly
					placeholder="自动生成" maxlength="36" name="orderNo"
					class="FormElement ui-widget-content ui-corner-all" /></td>
				<td class="CaptionTD"><font color="red">*</font>要求到货日期：</td>
				<td class="DataTD"><input type="text" name="arrivalDate"
					readonly jyValidate="required" /></td>
				<td class="CaptionTD"><font color="red">*</font>货品总数：</td>
				<td class="DataTD"><input type="text" jyValidate="required"
					readonly maxlength="25" name="totalNum"
					class="FormElement ui-widget-content ui-corner-all" /></td>
			</tr>
			
		</table>
		<br />
		<form id="pBaseForm" class="form-inline" method="POST"
			onsubmit="return false;">
			<input type='hidden' class='pageNum' name='pageNum' value='1' /> <input
				type='hidden' class='pageSize' name='pageSize' value='10' /> <input
				type='hidden' name='orderType' value='1' /> <input type='hidden'
				name='pId' />
		</form>
		<div class="col-xs-12">
			<div style="height: 420px; overflow-x: auto; border: 1px solid #ddd">
				<table id="pBaseTable"
					class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th style="width: 3%" class="center"><label><input
									type="checkbox" class="ace"><span class="lbl"></span></label>
							</th>
							<th style="width: 8%" class="center "></i>序号</th>
							<th style="width: 14%" class="center">订单编号</th>
							<th style="width: 10%" class="center ">货品总数</th>
							<th style="width: 10%" class="center ">货品总重</th>
							<th style="width: 10%" class="center ">订单总价</th>
							<th style="width: 13%" class="center ">要求到货日期</th>
							<th style="width: 10%" class="center ">标签</th>
							<th style="width: 8%" class="center">状态</th>
							<th style="width: 12%" class="center">操作</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
		<div class="col-sm-8">
			<!--设置分页位置-->
			<div id="pageing1" class="dataTables_paginate paging_bootstrap">
				<ul class="pagination"></ul>
			</div>
		</div>
		<br />
		<div class="col-xs-12">
			<table border="0" style="margin-top: 8px; width: 100%">
				<tr>
					<td class="CaptionTD" width="70px">备&nbsp;注:</td>
					<td class="DataTD" colspan="5"><textarea rows="1"
							maxlength="200" id="description" name="description"
							multiline="true" style="width: 100%"></textarea></td>
				</tr>
				<tr>
					<td>创建人:</td>
					<td width="25%"><span id="createName" name="createName"></span>
					</td>
					<td class="right">修改人:</td>
					<td width="25%"><span id="updateName" name="updateName"></span>
					</td>
					<td class="right">审核人:</td>
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
</div>
<div id="orderDiv" class="hide">
		<form id="orderForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr style="display:none">
					<td colspan="2" class="ui-state-error">
						<input type="hidden" name="id" >
						<input type="hidden" name="orgId" > 
						<input type="hidden" name="orderType" value='1' >
						<input type="hidden" name="franchiseeId" > 
						<input type="hidden" name="franchiseeName" >
						<input type="hidden" name="orgId" > 
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>订单编号：</td>
					<td class="DataTD">
						<input type="text" readonly placeholder="自动生成" maxlength="36" name="orderNo"  class="FormElement ui-widget-content ui-corner-all"/>
					</td>
					<td class="CaptionTD"><font color="red">*</font>要求到货日期：</td>
					<td class="DataTD">
						<input type="text" id="dateRender" name="arrivalDate" readonly jyValidate="required" />
					</td>					
					<td class="CaptionTD"><font color="red">*</font>货品总数：</td>
					<td class="DataTD">
						<input type="text" jyValidate="required" readonly  maxlength="25" name="totalNum"   class="FormElement ui-widget-content ui-corner-all"/>
					</td>
				</tr>
				<tr id="opeTr" style="display: none">
				<td class="CaptionTD"><font color="red">*</font>经办人：</td>
				<td class="DataTD"><input type="hidden" name="operatorId"
					id="operatorId"> <input type="text" jyValidate="" readonly
					maxlength="25" name="operatorName" id="operatorName"
					class="FormElement ui-widget-content ui-corner-all" />
					<button id='searchUserBtn' class="btn btn-warning  btn-xs" title="选人"
						type="button" onclick="userClike()">
						<i class="icon-user bigger-110 icon-only"></i>
					</button>
				</td>
			</tr>
			</table>
			<br/>
			<div id="addDiv">
<!-- 				<a id="addRow" class="lrspace3 aBtnNoTD" title="增加" href="#"> -->
<!-- 					<i class="icon-plus-sign color-green bigger-220"></i> -->
<!-- 				</a> -->
<!-- 				<a id="addRowList" class="lrspace3 aBtnNoTD" title="匹配" href="#"> -->
<!-- 					<i class="icon-plus-sign color-green bigger-220"></i> -->
<!-- 				</a> -->
				<div class="row">
					<form id="importForm" method="POST" onsubmit="return false;">
						&ensp; &ensp;&ensp; &ensp;&ensp;
						货类：<select name="cate" id="cate" class="isSelect75"></select>
						首饰类别：<select name="group" id="group" class="isSelect75"></select>
						<button id="addRowList" type="button" class="btn btn-success btn-xs" style="border-radius:5px" >导入明细(待补货)</button>
					</from>
				</div>
				<div class="row">
					&ensp; &ensp;&ensp; &ensp;<button id="addRow" type="button" class="btn btn-success btn-xs" style="border-radius:5px">新增明细</button>
				</div>
			</div>
			<div class="col-xs-12">
				<div style="height: 420px; overflow-x: auto;border:1px solid #ddd" >
					<table id="itemsTable" cellspacing="0" cellpadding="0" border="0" class="table table-striped table-bordered table-hover">
						<thead>
						<tr>
							<th style="width:5%"  class="center">款号</th>
 							<th style="width:6%"  class="center">材质</th>	 
							<th style="width:6%"  class="center">金重范围</th>
							<th style="width:4%"  class="center">圈口</th>
							<th style="width:6%"  class="center">主钻石重</th>
							<th style="width:4%"  class="center">钻石净度</th>
							<th style="width:4%"  class="center">钻石颜色</th>
							<th style="width:4%"  class="center">切工</th>
<!-- 							<th style="width:4%;display:none"  class="center">重量</th> -->

							<th style="width:4%"  class="center">数量</th>
<!-- 							<th style="width:6%"  class="center">计费类型</th> -->


							<th style="width:8%"  class="center">供应商</th>
							<th style="width:6%"  class="center">工厂款号</th>
							<th style="width:4%"  class="center">基本工费</th>
							<th style="width:4%"  class="center">附加工费</th>
							<th style="width:4%"  class="center">其他工费</th>
<!-- 							<th style="width:6%;display:none"  class="center">单价</th> -->
<!-- 							<th style="width:6%;display:none"  class="center">合计金额</th> -->
							<th style="width:4%"  class="center">库存</th>
							<th style="width:7%"  class="center">操作</th>
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
						<textarea rows="1" maxlength="200" id="description" name="description" multiline="true" style="width:100%"></textarea>
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
<div id="itemDiv" class="hide">
	<form id="itemForm" method="POST" onsubmit="return false;">
		<table cellspacing="0" cellpadding="0" border="0" class="customTable">
			<tbody>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>款号：</td>
					<td class="DataTD">&nbsp;
						<input type="text" maxlength="16" jyValidate="required" id="mdInput" autocomplete="off" name="mdCode" class="FormElement ui-widget-content ui-corner-all">
						<input type="hidden" name="mdCodeId" >
						<input type="hidden" name="ids" >
						<input type="hidden" name="status" value="1" />
					</td>
					<td class="CaptionTD">工厂款号：</td>
					<td class="DataTD">&nbsp;
						<input type="text" maxlength="32"  id="queryMouCode" name="mdtlCodeName" autocomplete="off" class="FormElement ui-widget-content ui-corner-all">
						<input type="hidden" name="mdtlCode" >
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>供应商：</td>
					<td class="DataTD">&nbsp;
						<input type="text" id ="inputName" maxlength="16" jyValidate="required" autocomplete="off" name="franchiseeName" class="FormElement ui-widget-content ui-corner-all">
						<input type="hidden" name="franchiseeId" />
						
					</td>
					<td class="CaptionTD">材质：</td>
					<td class="DataTD">&nbsp;
						<select id="gMaterialSelect"  name="gMaterial" class="isSelect157"></select>
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">金重范围：</td>
					<td class="DataTD">&nbsp;
						<select id="gWeightSelect"   name="gWeight" class="isSelect157"></select>
					</td>
					<td class="CaptionTD">钻石主石重：</td>
					<td class="DataTD">&nbsp;
						<select id="dWeightSelect" name="dWeight" class="isSelect157"></select>
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">钻石净度：</td>
					<td class="DataTD">&nbsp;
						<select id="dClaritySelect" name="dClarity" class="isSelect157"></select>
					</td>
					<td class="CaptionTD">钻石颜色：</td>
					<td class="DataTD">&nbsp;
						<select id="dColorSelect" name="dColor" class="isSelect157"></select>
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">切工：</td>
					<td class="DataTD">&nbsp;
						<select id="dCutSelect" name="cut" class="isSelect157"></select>
					</td>
					<td class="CaptionTD">圈口：</td>
					<td class="DataTD">&nbsp;
						<input type="text" maxlength="32" name="circel" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>重量：</td>
					<td class="DataTD">&nbsp;
<!--  						<select id="weightSelect"  jyValidate="required"  name="weight" class="isSelect157"></select> -->
						<input type="text" maxlength="32" name="weight" class="FormElement ui-widget-content ui-corner-all">
					</td>
					<td class="CaptionTD"><font color="red">*</font>件数：</td>
					<td class="DataTD">&nbsp;
						<input type="text" maxlength="16" jyValidate="required" name="numbers" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>计价方式：</td>
					<td class="DataTD">&nbsp;
<!-- 						<select id="feeTypeSelect" name="feeType" class="isSelect157"></select> -->
						<label><input id="feeType1" name="feeType" type="radio" checked="true" value="1" />克 </label> <label><input id="feeType2" name="feeType" type="radio" value="2" />件</label>
					</td>
					
					<td class="CaptionTD">基本工费：</td>
					<td class="DataTD">&nbsp;
						<input type="text" maxlength="32" name="basicCost" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">附加工费：</td>
					<td class="DataTD">&nbsp;
						<input type="text" maxlength="16" name="additionCost" class="FormElement ui-widget-content ui-corner-all">
					</td>
					<td class="CaptionTD">其他工费：</td>
					<td class="DataTD">&nbsp;
						<input type="text" maxlength="32" name="otherCost" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">合计金额：</td>
					<td class="DataTD">&nbsp;
						<input type="text" name="totalFee" min='1' max='9' class="FormElement ui-widget-content ui-corner-all ">
					</td>
					<td class="CaptionTD"><font color="red">*</font>单价：</td>
					<td class="DataTD">&nbsp;
						<input type="text" name="unitprice" min='1' max='9' jyValidate="required"  class="FormElement ui-widget-content ui-corner-all ">
					</td> 
				</tr>	
				<tr class="FormData">
					<td class="CaptionTD">描述：</td>
					<td class="DataTD" colspan="3">&nbsp;
						<textarea rows="2" cols="10" maxlength="200" name="description" multiline="true" class="FormElement ui-widget-content ui-corner-all isSelect157" width="100%" ></textarea> 
					</td>
				</tr>		
			</tbody>
		</table>
	</form>
</div>
