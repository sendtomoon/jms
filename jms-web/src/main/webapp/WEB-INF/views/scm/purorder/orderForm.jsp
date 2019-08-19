<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 编辑新增要货 -->
<div id="orderDiv" class="hide">
		<form id="orderForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" border="0" class="customTable">
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"><input type="hidden" name="id" ></td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD" ><font color="red">*</font>订单编号：</td>
					<td class="DataTD">
						<input type="text" readonly placeholder="自动生成"   maxlength="36" name="orderNo"  class="FormElement ui-widget-content ui-corner-all"/>
					</td>
					<td class="CaptionTD"><font color="red">*</font>要货单位：</td>
					<td class="DataTD">
						<input id="orgInput" type="text" readonly jyValidate="required" value="" class="FormElement ui-widget-content ui-corner-all" onclick="showOrgComp(); return false;"/>
						<input type="hidden" name="orderType" value="0" >
						<input type="hidden" name="orgId">
						<input type="hidden" name="orgName">
						<a href="#" title="清空" id="emptyOrg" onclick="emptyOrgComp(); return false;" class="lrspace3 aBtnNoTD" data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a>
						<div id='orgContentList' class="menuContent ztreeMC" style="display: none; position: absolute;z-index:5555;">
							<ul id="selectOrgTree" class="ztree preOrgTree"></ul>
						</div>
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
<!-- 				<tr class="FormData"> -->
<!-- 					<td class="CaptionTD"><font color="red">*</font>货品总价：</td> -->
<!-- 					<td class="DataTD"> -->
<!-- 						<input type="text" jyValidate="required"  maxlength="25" name="totalFee"   class="FormElement ui-widget-content ui-corner-all"/> -->
<!-- 					</td> -->
<!-- 					<td class="CaptionTD"><font color="red">*</font>货品总重：</td> -->
<!-- 					<td class="DataTD"> -->
<!-- 						<input type="text" jyValidate="required"  maxlength="25" name="weight"   class="FormElement ui-widget-content ui-corner-all"/> -->
<!-- 					</td> -->
<!-- 					<td class="CaptionTD"><font color="red">*</font>要求到货日期：</td> -->
<!-- 					<td class="DataTD"> -->
<!-- 							<input type="text" id="dateRender" name="arrivalDate" readonly jyValidate="required" /> -->
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td class="CaptionTD">创建人：</td> -->
<!-- 					<td class="DataTD"> -->
<!-- 						<input type="text" readonly maxlength="36" id="createName" name="createName"  class="FormElement ui-widget-content ui-corner-all isSelect157"/> -->
<!-- 					</td> -->
<!-- 					<td class="CaptionTD">描述：</td> -->
<!-- 					<td class="DataTD"> -->
<!-- 						<textarea rows="3" cols="10" maxlength="200" name="description" multiline="true"  class="FormElement ui-widget-content ui-corner-all isSelect157" width="100%" ></textarea> -->
<!-- 					</td> -->
<!-- 					<td class="CaptionTD"></td> -->
<!-- 					<td class="DataTD"> -->
<!-- 					</td> -->
<!-- 				</tr> -->
				
			</table>
<!-- 		</form> -->
		<div id="addDiv">
			&ensp;
<!-- 			<input type="text" maxlength="16" placeholder="自动生成" id="mdAddInput" class="FormElement ui-widget-content ui-corner-all"> -->
<!-- 			<button id="addRow" type="button" class="btn btn-success" style="border-radius:5px">新增明细</button> -->
			<button id="addRowList" type="button" class="btn btn-success" style="border-radius:5px">导入明细(待补货)</button>
		</div>
		<div class="col-xs-12" >
			
			<div style="height: 480px; overflow-x: auto;border:1px solid #ddd" >
				<table id="orderTable" cellspacing="0" cellpadding="0" border="0"  class="table table-striped table-bordered table-hover">
					<thead>
					<tr>
						<th style="width:6%"  class="center">款号</th>
						<th style="width:6%;display:none"  class="center">款号</th>
						<th style="width:6%"  class="center">材质</th>	
						<th style="width:6%"  class="center">金重范围</th>
						<th style="width:6%"  class="center">主钻石重</th>
						<th style="width:4%"  class="center">钻石净度</th>
						<th style="width:4%"  class="center">钻石颜色</th>
						<th style="width:4%"  class="center">主石切工</th>
						<th style="width:4%"  class="center">圈口</th>
<!-- 						<th style="width:4%"  class="center">重量</th> -->
						<th style="width:4%"  class="center">数量</th>
						<th style="width:6%;display:none"  class="center">计费类型</th>
						<th style="width:6%;display:none"  class="center">基本工费</th>
						<th style="width:6%;display:none"  class="center">附加工费</th>
						<th style="width:6%;display:none"  class="center">其他工费</th>
						<th style="width:6%;display:none"  class="center">单价</th>
						<th style="width:6%;display:none"  class="center">合计金额</th>
						<th style="width:5%" class="center">备注</th>
						<th style="width:4%"  class="center">操作</th>
					</tr>
					</thead>
						<tbody></tbody>
						<tfoot></tfoot>
				</table>
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

<!-- 新增款式选择 -->
<div id="selectMoDiv" class="hide">
	<div class="col-xs-12">
		<div class="col-xs-3">
			<div class="row">
				<div class="widget-main"><span style="font-size: 16px; font-weight: bold; padding-left: 12px;">已选择商品</span></div>
			</div>
			<div  style="height: 450px; overflow-x: auto;">
			<table id="moTable" cellspacing="0" cellpadding="0" border="0" class="table table-striped table-bordered table-hover">
				<thead>
					<th style="width:3%" class="center">
						<label><input type="checkbox" class="ace" id="lblCheckbox" ><span class="lbl"></span></label>
					</th>
					<th style="width:6%"  class="center">款号代码</th>
				</thead>
			</table>
			<a id="moAttrDel" class="lrspace3 aBtnNoTD" title="删除" href="#">
				<i class='icon-remove-sign color-red bigger-220'></i>
			</a>
			</div>
		</div>
		<div class="col-xs-9">
			<div class="row">
				<form id="moForm" class="form-inline" method="POST" onsubmit="return false;">
					<div class="widget-main">	
						&ensp;&nbsp;
						&ensp;&nbsp;饰品： <span><select name="jewelry" id="jewelry" class="isSelect75"></select></span>
						&ensp;&nbsp;款号：<input type='text'  name="code" style="width:100px"/>
						<input type='hidden'  name='status' value="1"/>
						<button id='searchBtn1' class="btn btn-warning  btn-xs" title="过滤" type="button" onclick="getItem2()"><i class="icon-search bigger-110 icon-only"></i></button>
						<input type='hidden' class='pageNum' name='pageNum' value='1'/>
						<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
					</div>
				</form>				
			</div>
			<table id="moTable1" cellspacing="0" cellpadding="0" border="0" class="table table-striped table-bordered table-hover">
				<thead>
					<th style="width:3%" class="center">
						<label><input type="checkbox" class="ace" id="lblCheckbox" ><span class="lbl"></span></label>
					</th>
					<th style="width:20%"  class="center">款号名称</th>
					<th style="width:20%"  class="center">款号</th>	
					<th style="width:15%"  class="center">状态</th>
					<th style="width:30%"  class="center">描述</th>
				</thead>
			</table>
			<div class="col-xs-2">
				<a id="moAttrAdd" class="lrspace3 aBtnNoTD" title="增加" href="#">
					<i class="icon-plus-sign color-green bigger-220"></i>
				</a>
			</div>
			<div class="col-xs-10">
				<div id="pageing2" class="dataTables_paginate paging_bootstrap">
					<ul class="pagination"></ul>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 修改要货明细 -->
<div id="orderDiv1" class="hide">
	<form id="orderForm1" method="POST" onsubmit="return false;">
		<table cellspacing="0" cellpadding="0" border="0" class="customTable">
			<tbody>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>款号：</td>
					<td class="DataTD">&nbsp;
						<input type="text" maxlength="16" jyValidate="required" id="mdInput" name="mdCode" autocomplete="off" class="FormElement ui-widget-content ui-corner-all">
						<input type="hidden" name="mdCodeId" >
						<input type="hidden" name="ids" >
						<input type="hidden" name="status" value="1" />
					</td>
					<td class="CaptionTD">单价：</td>
					<td class="DataTD">&nbsp;
						<input type="text" name="unitprice" min='1' max='9'  class="FormElement ui-widget-content ui-corner-all ">
					</td>
<!-- 					<td class="CaptionTD">工厂款号：</td> -->
<!-- 					<td class="DataTD">&nbsp; -->
<!-- 						<input type="text" maxlength="32"  id="queryMouCode" name="mdtlCodeName" class="FormElement ui-widget-content ui-corner-all"> -->
<!-- 						<input type="hidden" name="mdtlCode" > -->
<!-- 					</td> -->
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>材质：</td>
					<td class="DataTD">&nbsp;
						<select id="gMaterialSelect1" jyValidate="required" name="gMaterial" class="isSelect157"></select>
					</td>
					<td class="CaptionTD"><font color="red">*</font>金重范围：</td>
					<td class="DataTD">&nbsp;
						<select id="gWeightSelect1" jyValidate="required" name="gWeight" class="isSelect157"></select>
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">钻石主石重：</td>
					<td class="DataTD">&nbsp;
						<select id="dWeightSelect1" name="dWeight" class="isSelect157"></select>
					</td>
					<td class="CaptionTD">钻石净度：</td>
					<td class="DataTD">&nbsp;
						<select id="dClaritySelect1"  name="dClarity" class="isSelect157"></select>
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">钻石颜色：</td>
					<td class="DataTD">&nbsp;
						<select id="dColorSelect1"  name="dColor" class="isSelect157"></select>
					</td>
					<td class="CaptionTD"><font color="red">*</font>重量：</td>
					<td class="DataTD">&nbsp;
<!-- 						<select id="weightSelect1" jyValidate="required" name="weight" class="isSelect157"></select> -->
						<input type="text" maxlength="32" name="weight" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>件数：</td>
					<td class="DataTD">&nbsp;
						<input type="text" maxlength="16" jyValidate="required" name="numbers" class="FormElement ui-widget-content ui-corner-all">
					</td>
					<td class="CaptionTD"><font color="red">*</font>计费类型：</td>
					<td class="DataTD">&nbsp;
						<select id="feeTypeSelect1" jyValidate="required" name="feeType" class="isSelect157"></select>
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">基本工费：</td>
					<td class="DataTD">&nbsp;
						<input type="text" maxlength="32" name="basicCost" class="FormElement ui-widget-content ui-corner-all">
					</td>
					<td class="CaptionTD">附加工费：</td>
					<td class="DataTD">&nbsp;
						<input type="text" maxlength="16" name="additionCost" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">其他工费：</td>
					<td class="DataTD">&nbsp;
						<input type="text" maxlength="32"  name="otherCost" class="FormElement ui-widget-content ui-corner-all">
					</td>
					<td class="CaptionTD">合计金额：</td>
					<td class="DataTD">&nbsp;
						<input type="text" name="totalFee" min='1' max='9'  class="FormElement ui-widget-content ui-corner-all ">
					</td>
				</tr>
				<tr class="FormData">
					 
					<td class="CaptionTD">描述：</td>
					<td class="DataTD" rowspan="3">&nbsp;
						<textarea rows="2" cols="10" maxlength="200" name="description" multiline="true" class="FormElement ui-widget-content ui-corner-all isSelect157" width="100%" ></textarea> 
					</td>
				</tr>		
			</tbody>
		</table>
	</form>
</div>
<div id="ZBTOrderDiv" class="hide">
	<div class="col-xs-12">
			<table id="orderZBTTable" cellspacing="0" cellpadding="0" border="0" class="table table-striped table-bordered table-hover">
				<thead>
					<th style="width:3%" class="center">
						<label><input type="checkbox" class="ace" id="lblCheckbox" ><span class="lbl"></span></label>
					</th>
					<th style="width:25%"  class="center">原始订单号</th>
					<th style="width:25%"  class="center">要求到货日期</th>	
					<th style="width:10%"  class="center">总数</th>
					<th style="width:25%"  class="center">要货人</th>
				</thead>
			</table>
			<div class="col-xs-10">
				<div id="pageingZBT" class="dataTables_paginate paging_bootstrap">
					<ul class="pagination"></ul>
				</div>
			</div>
	</div>
</div>