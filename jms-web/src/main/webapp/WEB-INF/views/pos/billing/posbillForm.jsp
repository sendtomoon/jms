<%@ page contentType="text/html;charset=UTF-8"%>
<head> 　　
	<style type="text/css"> 　　
	    .posBill{width: 100%;}
		.posBill .FormData > td{padding-bottom: 0px;padding-top: 0px;font-family: "Open Sans";}
		.posBill .CaptionTD{color: #666;font-size: 12px;text-align: right;}
	</style> 　　
</head>
<div id="posbillDiv" class="hide">
	<div class="col-xs-12">
		<form id="posbillForm" method="POST" onsubmit="return false;" class="posBill">
			<table id="posbillTab" cellspacing="0" cellpadding="4px;" border="0" >
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"><input type="hidden" name="id"></td>
				</tr>
				<tr class="FormData">
					<td class="Captiontd">收银员：</td>
					<td class="Datatd">
						<input type="text" maxlength="36" name="cashierName" class="FormElement ui-widget-content ui-corner-all" readonly>
						<input type="hidden" name="cashier"/>
					</td>
					<td class="Captiontd">VIP卡号：</td>
					<td class="Datatd">
						<input type="text" maxlength="36" name="vipCode" onchange="subVip($(this).val())" class="FormElement ui-widget-content ui-corner-all"/ >
					</td>
					<td class="Captiontd"><font color="red">*</font>顾客姓名：</td>
					<td class="Datatd">
						<input type="text" maxlength="36" jyValidate="required" name="customer" class="FormElement ui-widget-content ui-corner-all" jyValidate="required"/>
					</td>
					<td class="Captiontd">定金单号：</td>
					<td class="Datatd">
						<input type="text" maxlength="36" onchange="subPur($(this).val())" name="earnest" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				
				<tr class="FormData">
					<td class="Captiontd"><font color="red">*</font>营业员1：</td>
					<td class="Datatd">
						<select id="selectAssistant1" style="width: 157px;" name="assistant1" jyValidate="required">
						</select>
						<input type="text" style="width:30px" name="promrate1" jyValidate="required"/>
					</td>
					<td class="Captiontd"><font color="red">*</font>主管1：</td>
					<td class="Datatd">
						<select id="selectLeader1" style="width: 155px;" name="leader1" jyValidate="required">
						</select>
					</td>
					<td class="Captiontd"><font color="red">*</font>销售日期：</td>
					<td class="Datatd">
						<input type="text" maxlength="36" name="saleTime" class="FormElement ui-widget-content ui-corner-all"readonly/>
					</td>
					<td class="CaptionTD"><font color="red">*</font>业绩分店：</td>
					<td class="DataTD">
						<select id="selectStore" style="width: 155px;" name="storeId" jyValidate="required">
						</select>
					</td>
				</tr>
				
				<tr class="FormData">
					<td class="Captiontd">营业员2：</td>
					<td class="Datatd">
						<select id="selectAssistant2" style="width: 155px;" name="assistant2">
						</select>
						<input type="text" style="width:30px" name="promrate2"/>
					</td>
					<td class="Captiontd">主   管2：</td>
					<td class="Datatd">
						<select id="selectLeader2" style="width: 155px;" name="leader2">
						</select>
					</td>
					<td class="Captiontd"><font color="red">*</font>收款金额：</td>
					<td class="Datatd">
						<input type="text" maxlength="36" name="actualAmt" class="FormElement ui-widget-content ui-corner-all" jyValidate="required" />
					</td>
				</tr>
				<tr>
					<td >备注：</td>
					<td colspan="7">
<!-- 						<input type="text" name="note"/> -->
						<textarea rows="1" cols="130" name="note" multiline="true"></textarea>
					</td>
					
				</tr>
			</table>
			<div class="btnClass" id="btnDiv" style="margin-bottom: 10px;">
<!-- 				<a id="addBtn" class="lrspace3 aBtnNotd" title="增加" href="#" -->
<!-- 					onclick="addposbill()"> <i -->
<!-- 					class="icon-plus-sign color-green bigger-180"></i> -->
<!-- 				</a> -->
				<a class="lrspace3 aBtnNotd" title="删除" href="#"
					onclick="delposbill()"> <i
					class="icon-trash color-red bigger-180"></i>
				</a>
				商品条码：<input type="text" name="barCode" placeholder="请输入商品条码" class="input-large" onchange="subProduct($(this).val())">
			</div>
<!-- 			<a href="#" title="预览打印" id="print" class="lrspace3" ><i class='icon-print bigger-220'></i></a> -->
			<div id="posbillAddDiv" style="height: 300px; overflow-y: scroll; border: 1px solid #ddd; width: 100%">
				<table id="posbillAdd" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th style="width:3%" class="center" id="checkId">
								<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
							</th>
							<th style="width:7%"  class="center">商品条码</th>
							<th style="width:7%"  class="center">货品描述</th>
							<th style="width:4%"  class="center">牌面价</th>
							<th style="width:6%"  class="center">工费 元/件</th>
							<th style="width:4%"  class="center">积分优惠</th>
							<th style="width:4%"  class="center">价格优惠</th>
							<th style="width:5%"  class="center">赠品</th>
							<th style="width:5%"  class="center">件数</th>
							<th style="width:5%"  class="center">金额</th>
						</tr>
					</thead>
					<tbody></tbody>
					<tfoot></tfoot>
				</table>
			</div>
			</form>
			<div class="col-xs-12" id="materialnoteDiv" class="customTable" style="margin-top: 10px; margin-left: -12px;">
				<table>
					<tr>
						<td>
							回收旧金<label><input type="checkbox" name="type" value="3"/></label>本号
							<label><input type="checkbox" name="type" value="4"/></label>外号
							<label><input type="checkbox" name="type" value="5"/></label>截料
							<label><input type="checkbox" name="type" value="2"/></label>以旧换新
						</td></tr>
				</table>
				<table id="oldGoldTab" class="hide posBill">
					<tr class="FormData">
						<td class="Captiontd"><font color="red">*</font>金类：</td>
						<td class="Datatd">
							<span id="selectGoldType"><select style="width: 155px;" class="required" name="goldType" onchange="changeGoldType($(this).val())"/></span>
						</td>
						<td class="Captiontd"><font color="red">*</font>金价：</td>
						<td class="Datatd">
							<input type="text" maxlength="36" name="goldPrice" class="required" onkeyup='JY.Validate.limitAmtNum(this)' class="FormElement ui-widget-content ui-corner-all"/>
						</td>
						<td class="Captiontd"><font color="red">*</font>耗损：</td>
						<td class="Datatd">
							<input type="text" maxlength="36" onchange="countWight()" onkeyup='JY.Validate.limitAmtNum(this)'  class="required" name="goldUllage"/>%
						</td>
					</tr>
					
					<tr class="FormData">
						<td class="Captiontd">金重：</td>
						<td class="Datatd">
							<input type="text" maxlength="36" onchange="countWight()" onkeyup='JY.Validate.limitAmtNum(this)' class="required" name="weight" class="FormElement ui-widget-content ui-corner-all"/>g
							</select>
						</td>
						<td class="Captiontd"><font color="red">*</font>净重：</td>
						<td class="Datatd">
							<input type="text" maxlength="36" readonly name="actualWt" onkeyup='JY.Validate.limitAmtNum(this)' class="FormElement ui-widget-content ui-corner-all"/>g
						</td>
						<td class="Captiontd">工费：</td>
						<td class="Datatd">
							<input type="text" maxlength="36" name="saleCost" class="required" onkeyup='JY.Validate.limitAmtNum(this)' class="FormElement ui-widget-content ui-corner-all"/>元/g
						</td>
						<td>
							<a href="#" id="addOldGold" type="button" class="btn btn-success btn-xs" style="border-radius:5px;">加入旧金</a>
						</td>
					</tr>
					</table>
					
					<table id="exchangeTab" class="hide posBill">
						<tr class="FormData">
							<td class="Captiontd">旧货编码：</td>
							<td class="Datatd"> 
								<input type="text" maxlength="36" name="barCode" class="required" onchange="subPos($(this).val())"/>
							</td>
							<td class="Captiontd">牌面价：</td>
							<td class="Datatd">
								<input type="text" maxlength="36" name="price"readonly/>
							</td>
							<td class="Captiontd">销售日期：</td>
							<td class="Datatd">
								<input type="text" maxlength="36" name="saleDate" readonly/>
							</td>
							<td class="Captiontd">原始售价：</td>
							<td class="Datatd">
								<input type="text" maxlength="36" name="oldPrice" readonly/>
							</td>
						</tr>
						<tr class="FormData">
							<td class="Captiontd">折扣：</td>
							<td class="Datatd">
								<input type="text" maxlength="36" name="zhekou" onkeyup='JY.Validate.limitAmtNum(this)'/>
							</td>
							<td class="Captiontd">回收价：</td>
							<td class="Datatd">
								<input type="text" maxlength="36" class="required" name="actualPrice" onkeyup='JY.Validate.limitAmtNum(this)'/>元
							</td>
							<td class="Captiontd">回收重量：</td>
							<td class="Datatd">
								<input type="text" maxlength="36" class="required" name="weight" onkeyup='JY.Validate.limitAmtNum(this)'/>g
							</td>
							<td class="Captiontd">手续费：</td>
							<td class="Datatd">
								<input type="text" maxlength="36" class="required" name="poundage" onkeyup='JY.Validate.limitAmtNum(this)'/>元
							</td>
						</tr>
						<tr class="FormData">
							<td class="Captiontd">损： 证书</td>
							<td class="Datatd">
								<input type="text" maxlength="36" name="certUllage" onkeyup='JY.Validate.limitAmtNum(this)'/>元
							</td>
							<td class="Captiontd">金损：</td>
							<td class="Datatd">
								<input type="text"  name="goldUllage" onkeyup='JY.Validate.limitAmtNum(this)'/>元
							</td>
							<td class="Captiontd">石损：</td>
							<td class="Datatd">
								<input type="text" maxlength="36" name="stoneUllage" onkeyup='JY.Validate.limitAmtNum(this)'/>元
							</td>
						</tr>
						<tr>
							<td>备&nbsp;注：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td colspan="11"><textarea rows="1" cols="120" name="note" multiline="true"></textarea></td>
							<td>
								<a href="#" id="exchange" type="button" class="btn btn-success btn-xs" style="border-radius:5px;">确定以旧换新</a>
							</td>
						</tr>
					</table>
<!-- 					<tr> -->
<!-- 						<td width="40px">创建人:</td> -->
<!-- 						<td width="70px"><span id="createUser"></span></td> -->
<!-- 						<td width="65px">创建时间:</td> -->
<!-- 						<td width="13%"><span id="createTime"></span></td> -->
<!-- 						<td width="50px">修改人:</td> -->
<!-- 						<td width="70px"><span id="updateUser"></span></td> -->
<!-- 						<td width="65px">修改时间:</td> -->
<!-- 						<td width="13%"><span id="updateTime"></span></td> -->
<!-- 						<td width="50px">审核人:</td> -->
<!-- 						<td width="70px"><span id="checkUser"></span></td> -->
<!-- 						<td width="65px">审核时间:</td> -->
<!-- 						<td width="13%"><span id="checkTime"></span></td> -->
<!-- 					</tr> -->
			</div>
			
			<div class="col-xs-12" id="offsetDiv" class="hide" style="margin-top: 10px; margin-left: -12px;">
				<table>
					<tr>
						<td><font color="red">*</font>冲单原因：</td>
						<td colspan="11"><textarea rows="1" cols="135" name="offsetcause" multiline="true"></textarea></td></tr>
				</table>
			</div>
	</div>
</div>

</div>
</div>

<div id="membersDiv" class="hide">
	<form id="membersForm" method="POST" onsubmit="return false;" >
		<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
			<input type="hidden" name="regType" value="0"/>
			<input type="hidden" name="flagLock" value="0"/>
			<input type="hidden" name="flagActive" value="0"/>
			<input type="hidden" name="flagLimit" value="0"/>
			<input type="hidden" name="grade" value="1"/>
			<tr class="FormData">
				<td class="CaptionTD"><font color="red">*</font>卡号：</td>
				<td class="DataTD"><input type="text" jyValidate="required" maxlength="32" name="cardNo" class="FormElement ui-widget-content ui-corner-all"/></td>
				<td class="CaptionTD"><font color="red">*</font>名称：</td>
				<td class="DataTD" ><input type="text" jyValidate="required" maxlength="32" name="name" class="FormElement ui-widget-content ui-corner-all"/></td>
			</tr>
			<tr class="FormData">
				<input type="hidden" name="id"/>
				<td class="CaptionTD"><font color="red">*</font>昵称：</td>
				<td class="DataTD"><input type="text" jyValidate="required" maxlength="16" name="nickName"class="FormElement ui-widget-content ui-corner-all"/></td>
			
				<td class="CaptionTD"><font color="red">*</font>性别：</td>
				<td class="DataTD">
					<label id="sexLabel" class="inline">
						<input name="sex" id="sex_0" jyValidate="required" type="radio" class="ace" value="0"/>
						<span class="lbl middle"> 男</span>
					</label>
					&nbsp;&nbsp;
					<label class="inline">
						<input name="sex" id="sex_1" jyValidate="required" type="radio" class="ace" value="1"/>
						<span class="lbl middle"> 女</span>
					</label>
				</td>
			<tr class="FormData">
				<td class="CaptionTD"><font color="red">*</font>手机号码：</td>
				<td class="DataTD"><input type="text" jyValidate="required phone" maxlength="32" name="mobile" class="FormElement ui-widget-content ui-corner-all"/></td>
			</tr>
		</table>
	</form>
</div>
