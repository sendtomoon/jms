<%@ page contentType="text/html;charset=UTF-8"%>
<div id="handoverForm" class="hide" style="position: static;" >
<div id="cuohuodiv">
    <div>
    <form>
        <table>
            <tr>
                <td><span>交接时间：<input id="handoverTime" type="text" class="input-large" disabled="disabled"/>&nbsp;&nbsp;</span></td>
                <td><span>门店：<input id="orgName" type="text" class="input-large" disabled="disabled"/></span></td>
            </tr>
        </table>
        </form>
    </div>
    <div style="height: 140px; overflow-y: scroll; border: 1px solid #ddd; width: 100%;margin-top:15px;">
    <table id="addHandoverDetailList" class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
        <thead>
			<tr>
			    <th>仓位</th>
			    <th>货类编号</th>
			    <th>货类名称</th>
			    <th>件数</th>
			    <th>重量</th>
			    <th>金额</th>
		    </tr>
		</thead>
		<tbody></tbody>  
    </table>
    </div>
    </div>
    <input type="hidden" value="" id="orderNoHidden">
    <div style="height: 75px; border: 1px solid #ddd; width: 100%;margin-top:15px;">
    <table id="sumTable" class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
    <thead>
			<tr>
			    <th></th>
			    <th>件数</th>
			    <th>重量</th>
			    <th>金额</th>
		    </tr>
		</thead>
    <tbody></tbody>
    </table>
    </div>
    <div id="handoverdiv">
    <div id="bothside" style="margin-top:30px;">
    <form id="handoverForm">
        <table class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
            <tr>
                <td>交班人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="hander" type="text" name="" disabled="disabled"/></td>
                <td>接班人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select id="receiverSelect"/></td>
            </tr>
            <tr>
                <td><font color="red">*</font>商品数量：<input id="handNum" type="text" jyValidate="required" onkeyup="JY.Validate.limitDouble(this)"/></td>
                <td><font color="red">*</font>商品数量：<input id="receiveNum" type="text" jyValidate="required" onkeyup="JY.Validate.limitDouble(this)"/></td>
            </tr>
            <tr>
                <td></font>商品重量：<input id="handWt" type="text" onkeyup="JY.Validate.limitDouble(this)"/></td>
                <td></font>商品重量：<input id="receiveWt" type="text" onkeyup="JY.Validate.limitDouble(this)"/></td>
            </tr>
            <tr>
                <td></font>金额：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="handAmt" type="text" onkeyup="JY.Validate.limitDouble(this)"/></td>
                <td></font>金额：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="receiveAmt" type="text" onkeyup="JY.Validate.limitDouble(this)"/></td>
            </tr>
            <tr>
                <td></td>
                <td><font color="red">*</font>交接密码：&nbsp;&nbsp;<input id="receivePasswd" type="password" jyValidate="required" autocomplete="off"/></td>
            </tr>
        </table>
        </form>
    </div>
    <div style="margin-top:15px;">
    <table>
    <tr>
    <td>差异原因：</td>
    <td><textarea id="reason"  rows="3" cols="72"></textarea></td>
    </tr>
    </table>
    </div>
    </div>
</div>
<div id="viewdiv" class="hide">

    <div style="margin-top:30px;">
        <table class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
           <tr>
                <td><span>交接时间：<input id="handoverTime_view" type="text" class="input-large" disabled="disabled"/>&nbsp;&nbsp;</span></td>
                <td><span>门店：<input id="orgName_view" type="text" class="input-large" disabled="disabled"/></span></td>
            </tr>
            <tr>
                <td>交班人：&nbsp;&nbsp;&nbsp;<input id="hander_view" type="text" name="" disabled="disabled"/></td>
                <td>接班人：&nbsp;&nbsp;&nbsp;<input id="receiver_view" type="text" disabled="disabled"/></td>
            </tr>
            <tr>
                <td>商品数量：<input id="handNum_view" type="text" disabled="disabled"/></td>
                <td>商品数量：<input id="receiveNum_view" type="text" disabled="disabled"/></td>
            </tr>
            <tr>
                <td>商品重量：<input id="handWt_view" type="text" disabled="disabled"/></td>
                <td>商品重量：<input id="receiveWt_view" type="text" disabled="disabled"/></td>
            </tr>
            <tr>
                <td>金额：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="handAmt_view" type="text" disabled="disabled"/></td>
                <td>金额：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="receiveAmt_view" type="text" disabled="disabled"/></td>
            </tr>
        </table>
    </div>
    <div style="margin-top:15px;">
    <table>
    <tr>
    <td>差异原因：</td>
    <td><textarea id="reason_view" rows="3" cols="72"  disabled="disabled"></textarea></td>
    </tr>
    </table>
    </div>

</div>










