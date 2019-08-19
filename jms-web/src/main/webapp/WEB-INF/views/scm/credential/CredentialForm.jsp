<%@ page contentType="text/html;charset=UTF-8" %>
<div class="hide" id="credentialByInfoDiv">
	<div class="row-fluid" >
		<div class="col-xs-12">
			<div id="rightDiv">
				<table id="credentialByInfoTable"
					class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th style="width: 3%" class="center hidden-480">序号</th>
							<th style="width: 6%" class="center">名称</th>
							<th style="width: 6%" class='center hidden-480'>证书名称</th>
							<th style="width: 6%" class="center">类型</th>
							<th style="width: 1%" class="center">操作</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
</div>
	<div id="CredentialauDiv" class="hide">
			<form id="CredentialauForm" method="POST" onsubmit="return false;"  enctype="multipart/form-data">
				<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
					<tr class="FormData">
					    <td class="CaptionTD"><font color="red">*</font>状态：</td>
							<td class="DataTD"><label class="inline isValidCheckbox">
								<input type="checkbox" checked="checked"  sh-isValid="" role="checkbox" class="FormElement ace ace-switch ace-switch-5"/>	
								<span  class="lbl"></span>
								<!-- cb-isValid和Yes和No选择框配套使用 -->
								<input type="hidden" hi-isValid="" name="status" value="1" />
								<input type="hidden"  name="id" >
							</label>
						</td>
					</tr>
					<tr class="FormData">
						<input type="hidden" jyValidate="ennum"  maxlength="16" name="productId" class="FormElement ui-widget-content ui-corner-all" >
						<input type="hidden"  maxlength="16" name="accessorieId" class="FormElement ui-widget-content ui-corner-all" >
						<td class="CaptionTD">
							<font color="red">*</font>证书代码：</td>
						<td class="DataTD"><input type="text" jyValidate="ennum" maxlength="16" name="cerNo" class="FormElement ui-widget-content ui-corner-all">
						</td>
					</tr>
					<tr class="FormData">
						<td class="CaptionTD">
							<font color="red">*</font>证书类型：</td>
						<td class="DataTD"><input name="cerName"  type="text" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" />
						</td>
					</tr>
					 <tr class="FormData">
						<input name=cerofficeName  type="hidden"  />
						<td class="CaptionTD">认证机构名称：</td>
						<td class="DataTD"><span id="selectcerName"><select jyValidate="required" id="cerofficeId" name="cerofficeId" style="width:157px;"></select></span></td>
					</tr>
					<tr class="FormData">
						<td class="CaptionTD">
							认证日期：</td>
						<td class="DataTD">
							<input type="text" id="cerDate" name="cerDate" readonly jyValidate="required"/>
						</td>
					</tr>
					<tr class="FormData"  rowspan="2">
						<td class="CaptionTD"><font color="red">*</font>证书图片：</td>
						<td class="DataTD" colspan="3">
							<p class="smallimg" style="width:100px;height:100px;padding:10px;border:1px #ddd solid;position:relative;float: left;margin-right:10px;">
							<input style="width:100%;height:100%;position:absolute;top:0px;left:0px;z-index:9;opacity:0;filter:alpha(opacity=0);" type="file" onchange="file_img($(this))" name="file"  />
							<img class="imgone" style="width:100%;height:100%;position:absolute;top:0px;left:0px;z-index:0;object-fit:cover;" src="${jypath}/static/images/no_img.jpg"/></p>
						</td>
					</tr>
				</table>	
			</form>
	</div>
<div id="excelCredential" class="hide">
	<form id="excelCredentialForm" method="POST"  action="${jypath}/scm/credential/credentialImport">
 		<table>
			<tr> 
 				<td style="text-align: right;">选择Excel文件：</td> 
 					<td style="text-align: right;"><input type="text" id='inputfield' /></td> 
 				<td style="text-align: right;"> 
 					<div style="position:relative;"> 
 						<input type="button" value="浏览...."/> 
 						<input type='file' name="file" id="Credentialfile" style="opacity:0;position:absolute;top:0;left:0;width:60px;height:30px;cursor:pointer;" onchange="document.getElementById('inputfield').value=this.value"> 
 						<input type="submit" value="导入"/> 
 					</div> 
 				</td>
 			</tr>
 		</table> 
 </div> 
