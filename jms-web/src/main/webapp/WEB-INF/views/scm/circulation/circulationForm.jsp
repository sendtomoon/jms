<%@ page contentType="text/html;charset=UTF-8" %>
<div id="circulationDiv" class="hide">
	<div class="col-xs-12">
		<form id="circulationForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr style="display:none">
					<td colspan="2" class="ui-state-error"><input type="hidden" name="id"></td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">流转单号：</td>
					<td class="DataTD">
						<input type="text"  maxlength="36"  jyValidate="flowNo"  name="flowNo" class="FormElement ui-widget-content ui-corner-all" readonly placeholder="自动生成" />
					</td>
					<td class="CaptionTD" >入库通知单：</td>
					<td class="DataTD" >
						<input type="text" id="findnoticeNo"  maxlength="36"  name="noticeNo" class="FormElement ui-widget-content ui-corner-all" data-provide="typeahead" autocomplete="off" />
					</td>
					<td class="CaptionTD">发货部门：</td>
					<td class="DataTD">
						<div id="queryhandover" style="position:relative;z-index:99">
							<input id="queryhandoverorgName" type="text" name="handoverOrgName"  readonly />
							<input id="queryhandoverorgId" name="handoverOrgId" type="hidden"/>
						</div>
					</td>  
					<td class="CaptionTD">发货人：</td>
					<td class="DataTD">
						<select id="selectHandover" style="width: 120px;"  name="handoverId" jyValidate="required"></select>
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">收货部门：</td>
					<td class="DataTD">
						<input id="cirInput" type="text" readonly value="" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" onclick="showOrgComp(); return false;"/>
						<input type="hidden" name="pId">
						<input type="hidden" name="receiveOrgId">
						<input type="hidden" name="receiveOrgName">
						<a href="#" title="清空" onclick="emptyOrgComp(); return false;" class="lrspace3 aBtnNoTD" data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a>
						<div id='orgList' class="menuContent ztreeMC" style="display: none; position: absolute;">
							<ul id="selectOrgTree" class="ztree preOrgTree"></ul>
						</div>
					</td>
					<td class="CaptionTD">收货人：</td>
					<td class="DataTD">
						<select id="selectReceiver" style="width: 120px;" name="receiverId" jyValidate="required" ></select>
						
					</td>
				</tr>
			</table>
			<div class="btnClass" style="margin-bottom:10px;">
				<a class="lrspace3 aBtnNoTD" title="删除" href="#" onclick="delCirculation()" >
					<i class="icon-trash color-red bigger-180"></i>
				</a>
			</div>
			<div style="height: 380px;overflow-y: scroll;border:1px solid #ddd;width:100%">
				<table id="circulationAdd" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th style="width:1%" class="center">
								<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
							</th>
							<th style="width:6%"  class="center">条码</th>
							<th style="width:7%"  class="center">名称</th>
							<th style="width:4%"  class="center">材质</th>
							<th style="width:4%"  class="center">数量</th>
							<th style="width:4%"  class="center">重量</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
					<tfoot></tfoot>
				</table>
			</div>
			</br>
			<span>备注：<textarea id="note" rows="1" cols="125" name="note" multiline="true"></textarea></span>
		</form>
	</div>
</div>
<div id="printDiv" class="hide">

</div>