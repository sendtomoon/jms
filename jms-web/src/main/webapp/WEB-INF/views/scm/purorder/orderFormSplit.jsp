<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 拆分页面 -->
<div id="splitDiv" class="hide" >
	<div>
		<form id="splitForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"><input type="hidden" name="id" ></td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD"><font color="red">*</font>订单编号：</td>
					<td class="DataTD">
						<input type="text" jyValidate="required" maxlength="36" readonly name="orderNo"  class="FormElement ui-widget-content ui-corner-all"/>
					</td>
					<td class="CaptionTD"><font color="red">*</font>要货单位：</td>
					<td class="DataTD">
						<input type="text" jyValidate="required"  maxlength="25" readonly name="orgName" class="FormElement ui-widget-content ui-corner-all"/>
					</td>
					<td class="CaptionTD"><font color="red">*</font>要求到货日期：</td>
					<td class="DataTD">
						<input type="text" name="arrivalDate" readonly jyValidate="required" />
					<td class="CaptionTD"><font color="red">*</font>货品总数：</td>
					<td class="DataTD">
						<input type="text" jyValidate="required" readonly  maxlength="25" name="totalNum"   class="FormElement ui-widget-content ui-corner-all"/>
					</td>
				</tr>
<!-- 				<tr class="FormData"> -->
<!-- 					<td class="CaptionTD"><font color="red">*</font>订单编号：</td> -->
<!-- 					<td class="DataTD"> -->
<!-- 						<input type="text" jyValidate="required"  maxlength="36" readonly name="orderNo" class="FormElement ui-widget-content ui-corner-all"/> -->
<!-- 					</td> -->
<!-- 					<td class="CaptionTD"><font color="red">*</font>货品总数：</td> -->
<!-- 					<td class="DataTD"> -->
<!-- 						<input type="text" jyValidate="required"  maxlength="25" readonly name="totalNum" class="FormElement ui-widget-content ui-corner-all"/> -->
<!-- 					</td> -->
<!-- 					<td class="CaptionTD"><font color="red">*</font>货品总重：</td> -->
<!-- 					<td class="DataTD"> -->
<!-- 						<input type="text" jyValidate="required"  maxlength="25" readonly name="weight" class="FormElement ui-widget-content ui-corner-all"/> -->
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 				<tr class="FormData"> -->
<!-- 					<td class="CaptionTD"><font color="red">*</font>货品总价：</td> -->
<!-- 					<td class="DataTD"> -->
<!-- 						<input type="text" jyValidate="required"  maxlength="25" readonly name="totalFee" class="FormElement ui-widget-content ui-corner-all"/> -->
<!-- 					</td> -->
<!-- 					<td class="CaptionTD"><font color="red">*</font>要货单位：</td> -->
<!-- 					<td class="DataTD"> -->
<!-- 						<input type="text" jyValidate="required"  maxlength="25" readonly name="orgName" class="FormElement ui-widget-content ui-corner-all"/> -->
<!-- 					</td> -->
<!-- 					<td class="CaptionTD"><font color="red">*</font>要求到货日期：</td> -->
<!-- 					<td class="DataTD"> -->
<!-- 						<input type="text" name="arrivalDate" readonly jyValidate="required" /> -->
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td class="CaptionTD">创建人：</td> -->
<!-- 					<td class="DataTD"> -->
<!-- 						<input type="text" readonly maxlength="36" name="createName" value=""  class="FormElement ui-widget-content ui-corner-all isSelect157"/> -->
<!-- 					</td> -->
<!-- 					<td class="CaptionTD">描述：</td> -->
<!-- 					<td class="DataTD"> -->
<!-- 						<textarea rows="3" cols="15" maxlength="300" name="description" readonly multiline="true" class="FormElement ui-widget-content ui-corner-all isSelect157" ></textarea> -->
<!-- 					</td> -->
<!-- 				</tr> -->
				
			</table>
		<br/>
			<div class="col-xs-12">
				<button id="mateBtn" type="button" class="btn btn-success btn-xs" onclick="mateClick()" style="border-radius:5px">自动匹配</button>
				<div style="height: 420px; overflow-x: auto;border:1px solid #ddd" >
					<table id="splitTable1" cellspacing="0" cellpadding="0" border="0" class="table table-striped table-bordered table-hover">
						<thead>
						<tr>
							<th style="width:8%"  class="center">款号</th>
							<th style="width:6%;display:none"  class="center">款号</th>
							<th style="width:8%"  class="center">材质</th>	
							<th style="width:6%"  class="center">金重范围</th>
							<th style="width:6%"  class="center">主钻石重</th>
							<th style="width:6%"  class="center">钻石净度</th>
							<th style="width:6%"  class="center">钻石颜色</th>
							<th style="width:4%"  class="center">主石切工</th>
							<th style="width:4%"  class="center">圈口</th>
<!-- 							<th style="width:6%" class="center">重量</th> -->
							<th style="width:5%"  class="center">数量</th>
							<th style="width:6%;display:none"  class="center">计费类型</th>
							<th style="width:6%;display:none"  class="center">基本工费</th>
							<th style="width:6%;display:none"  class="center">附加工费</th>
							<th style="width:6%;display:none"  class="center">其他工费</th>
							<th style="width:6%;display:none"  class="center">单价</th>
							<th style="width:6%;display:none"  class="center">合计金额</th>
							<th style="width:5%"  class="center">发货数</th>
							<th style="width:5%"  class="center">匹配数</th>
							<th style="width:4%"  class="center">库存</th>
							<th style="width:4%"  class="center">状态</th>
							<th style="width:4%"  class="center">备注</th>
							<th style="width:4%"  class="center">操作</th>
						</tr>
						</thead>
						<tbody></tbody>
						<tfoot></tfoot>
					</table>
				</div>
			</div>
			<div class="col-xs-12" >
				<table border="0"style="margin-top: 8px;width:100%"cellspacing="0" cellpadding="4px;" class="customTable">
					<tr>
						<td width='70px'>备&nbsp;注:</td>	
						<td colspan="5">
							<textarea rows="1" maxlength="200" name="description" multiline="true" style="width:100%"></textarea>
						</td>
					</tr>
					<tr>
					<td >
						创建人:
					</td>
					<td width="25%">
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
</div>
<!-- 拆分选商品页面 -->
<div id="itemDiv" class="hide">
	<div class="col-xs-12">
		
			<div class="row">
				<form id="splitItemForm" class="form-inline" method="POST" onsubmit="return false;">
					<div class="widget-main">	
						&ensp;&nbsp;&ensp;&nbsp;款号<input type='text'  id='mouCodeName' readonly="readonly" style="width:100px"/>
						&ensp;材质 <select id="gMaterialSelect" name="goldType" class="isSelect75"></select>
<!-- 						&ensp;金重 <select id="gWeightSelect" name="weightRange"  class="isSelect75"></select> -->
						&nbsp;圈口<input type='text'  id='circel' name="circel" style="width:40px"/>
<!-- 						&ensp;石重 <select id="dWeightSelect" name="stoneShapeRange" class="isSelect75"></select> -->
						&ensp;颜色 <select id="dColorSelect" name="color" class="isSelect55"></select>
						&ensp;净度 <select id="dClaritySelect" name="clarity" class="isSelect55"></select>
						&ensp;切工 <select id="dCutSelect" name="cut" class="isSelect55"></select>
						<button id='searchBtn1' class="btn btn-warning  btn-xs" title="过滤" type="button" onclick="getItem()"><i class="icon-search bigger-110 icon-only"></i></button>
					</div>
					<input type='hidden'  name='mouCode' />
					<input type='hidden'  name='status' value="B"/>
					<input type='hidden'  name='id'/>
					<input type='hidden' class='pageNum' name='pageNum' value='1'/>
					<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
				</form>				
			</div>
		<div class="col-xs-9">
<!-- 				<a id="itemAttrAdd" class="lrspace3 aBtnNoTD" title="增加" href="#"> -->
<!-- 					<i class="icon-plus-sign color-green bigger-120">增加</i> -->
<!-- 				</a> -->
			<button  id="itemAttrAdd"type="button" class="btn btn-success btn-xs" style="border-radius:5px" >增加</button>
			<table id="splitTable2" cellspacing="0" cellpadding="0" border="0" class="table table-striped table-bordered table-hover">
				<thead>
					<th style="width:3%" class="center">
						<label><input type="checkbox" class="ace" id="lblCheckbox" ><span class="lbl"></span></label>
					</th>
					<th style="width:8%"  class="center">商品名称</th>
					<th style="width:6%"  class="center">材质</th>	
					<th style="width:3%"  class="center">重量</th>
					<th style="width:3%"  class="center">圈口</th>
					<th style="width:3%"  class="center">主钻石重</th>
					<th style="width:3%"  class="center">颜色</th>
					<th style="width:3%"  class="center">净度</th>
					<th style="width:3%"  class="center">切工</th>
				</thead>
			</table>
			<div class="col-xs-10">
				<div id="pageing1" class="dataTables_paginate paging_bootstrap">
					<ul class="pagination"></ul>
				</div>
			</div>
		</div>
		<div class="col-xs-3">
			<button  id="itemAttrDel"type="button" class="btn btn-danger btn-xs" style="border-radius:5px" >删除</button>
<!-- 					<a id="itemAttrDel" class="lrspace3 aBtnNoTD" title="删除" href="#"> <i class='icon-remove-sign color-red bigger-120'>删除</i></a> -->
					<span style="font-size: 16px; font-weight: bold; padding-left: 12px;">已选择商品</span> 
			<div  style="height: 450px; overflow-x: auto;">
			<table id="splitTable3" cellspacing="0" cellpadding="0" border="0" class="table table-striped table-bordered table-hover">
				<thead>
					<th style="width:3%" class="center">
						<label><input type="checkbox" class="ace" id="lblCheckbox" ><span class="lbl"></span></label>
					</th>
					<th style="width:6%"  class="center">商品名称</th>
				</thead>
			</table>
			
			</div>
		</div>
	</div>
</div>
<div id="materialDiv" class="hide">
	<div class="col-xs-12">
		<div class="col-xs-4">
			<div class="row">
				<div class="widget-main"><span style="font-size: 16px; font-weight: bold; padding-left: 12px;">已选择商品</span></div>
			</div>
			<div  style="height: 450px; overflow-x: auto;">
			<table id="materialTable1" cellspacing="0" cellpadding="0" border="0" class="table table-striped table-bordered table-hover">
				<thead>
					<th style="width:3%" class="center">
						<label><input type="checkbox" class="ace" id="lblCheckbox" ><span class="lbl"></span></label>
					</th>
					<th style="width:6%"  class="center">条码</th>
					<th style="width:4%"  class="center">数量</th>
					<th style="width:4%"  class="center">重量</th>
				</thead>
			</table>
			<a id="materialDel" class="lrspace3 aBtnNoTD" title="删除" href="#">
				<i class='icon-remove-sign color-red bigger-220'></i>
			</a>
			</div>
		</div>
		<div class="col-xs-8">
			<div class="row">
				<form id="materialForm" class="form-inline" method="POST" onsubmit="return false;">
					<div class="widget-main">	
						&ensp;&nbsp;&ensp;&nbsp;款号：<input type='text'  id='mouCodeName1' readonly="readonly" style="width:100px"/>
						&ensp;&nbsp;名称：<input type='text'  id='name' name ="name" style="width:100px"/>
						<button id='searchBtn1' class="btn btn-warning  btn-xs" title="过滤" type="button" onclick="getMaterial()"><i class="icon-search bigger-110 icon-only"></i></button>
					</div>
					<input type='hidden'  name='moudleCode' />
<!-- 					<input type='hidden'  name='status' value="3"/> -->
					<input type='hidden'  name='id'/>
					<input type='hidden' class='pageNum' name='pageNum' value='1'/>
					<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
				</form>				
			</div>
			<table id="materialTable2" cellspacing="0" cellpadding="0" border="0" class="table table-striped table-bordered table-hover">
				<thead>
					<th style="width:3%" class="center">
						<label><input type="checkbox" class="ace" id="lblCheckbox" ><span class="lbl"></span></label>
					</th>
					<th style="width:6%"  class="center">商品名称</th>
					<th style="width:6%"  class="center">条码</th>	
					<th style="width:6%"  class="center">重量</th>
					<th style="width:6%"  class="center">数量</th>
					<th style="width:6%"  class="center">单价</th>
				</thead>
			</table>
			<div class="col-xs-2">
				<a id="materialAdd" class="lrspace3 aBtnNoTD" title="增加" href="#">
					<i class="icon-plus-sign color-green bigger-220"></i>
				</a>
			</div>
			<div class="col-xs-10">
				<div id="pageing1" class="dataTables_paginate paging_bootstrap">
					<ul class="pagination"></ul>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="deliveryDiv" class="hide">
	<table id="deliveryTable" cellspacing="0" cellpadding="0" border="0" class="table table-striped table-bordered table-hover">
		<thead>
			<th style="width:6%"  class="center">商品名称</th>
			<th style="width:6%"  class="center">材质</th>	
			<th style="width:3%"  class="center">重量</th>
			<th style="width:3%"  class="center">主钻石重</th>
			<th style="width:3%"  class="center">圈口</th>
			<th style="width:3%"  class="center">单价</th>
			<th style="width:8%"  class="center">出库单号</th>
			<th style="width:8%"  class="center">出库时间</th>
		</thead>
	</table>
	<div class="col-xs-12">
<!-- 		<div id="deliveryPageing" class="dataTables_paginate paging_bootstrap"> -->
<!-- 			<ul class="pagination"></ul> -->
<!-- 		</div> -->
	</div>
</div>