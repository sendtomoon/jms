<%@ page contentType="text/html;charset=UTF-8"%>
<div id="ScmPriceApplyDiv" class="hide">
	<div class="col-xs-12">
		<form id="ScmPriceApplyForm" method="POST" onsubmit="return false;" >
			<table id="ScmPriceApplyTab" cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"><input type="hidden" name="id"></td>
				</tr>
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"><input type="hidden" name="status"></td>
				</tr>
                <tr style="display:none">
                    <td colspan="2" class="ui-state-error"><input type="hidden" name="orgId"></td>
                </tr>
                <tr class="FormData">
                    <td class="CaptionTD">申请单号：</td>
                    <td class="DataTD"><input type="text" maxlength="36"
                                              name="orderNo"
                                              class="FormElement ui-widget-content ui-corner-all" readonly
                                              placeholder="自动生成">
                    </td>
                    <td class="CaptionTD">创建机构：</td>
                    <td class="DataTD">
                        <input type="text" maxlength="36"
                               name="orgName"
                               class="FormElement ui-widget-content ui-corner-all" readonly
                               placeholder="自动关联">
                    </td>
                    <td class="CaptionTD"><font color="red">*</font>调整机构：</td>
                    <td class="DataTD">
						<div id="productOrg">
							<input id="productOrgName" type="text" jyValidate="required" placeholder="请选组织机构" readonly />
							<input id="productOrgId" name="" type="hidden"/>
						</div>
					</td>
                </tr>
			</table>
			<!--  -->
			<div class="btnClass" style="margin-bottom: 10px;">
				<a class="lrspace3 aBtnNotd" title="新增" href="#" onclick="addDetail()">
					<i class="icon-plus-sign color-green bigger-180"></i>
				</a>
				<a class="lrspace3 aBtnNotd" title="删除" href="#" onclick="delDetail()">
					<i class="icon-trash color-red bigger-180"></i>
				</a>
			</div>
			<div style="height: 380px; overflow-y: scroll; border: 1px solid #ddd; width: 100%; margin-top: 10px;">
				<table id="ScmPriceApplyAdd" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th style="width:5%" class="center" id="checkId">
								<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
							</th>
							<th style="width:19%"  class="center">金类</th>
							<th style="width:19%"  class="center">原金价</th>
							<th style="width:19%"  class="center">现金价</th>
                            <%--
							<th style="width:19%"  class="center">其他品牌金价</th>
							--%>
							<th style="width:19%"  class="center">备注</th>
						</tr>
					</thead>
					<tbody></tbody>
					<tfoot></tfoot>
				</table>
			</div>
			<div class="col-xs-12" style="margin-top: 10px; margin-left: -12px;">
				<table>
					<tr>
						<td style="display: inline-block; width: 80px;padding-top: 6px;">备&nbsp;注：</td>
						<td colspan="11"><textarea rows="1" cols="120" name="note" multiline="true"></textarea></td>
					</tr>
                    <tr class="rejectBox">
                        <td style="display: inline-block; width: 80px;padding-top: 6px;">拒绝原因：</td>
                        <td colspan="11"><textarea rows="1" cols="120" name="rejectcause" multiline="true"></textarea></td>
                    </tr>
					<tr>
						<td width="40px">创建人:</td>
						<td width="70px"><span id="createUser"></span></td>
						<td width="65px">创建时间:</td>
						<td width="13%"><span id="createTime"></span></td>
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