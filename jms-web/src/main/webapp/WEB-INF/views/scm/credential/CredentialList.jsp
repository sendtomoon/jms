<%@ page contentType="text/html;charset=UTF-8" %>
	<div class="hide" id="CredentialDiv">
		<div class="row-fluid">
			<div class="col-xs-12">
						<div id="rightDiv" >
							<form id="CredentialBaseForm" class="form-inline" method="POST" onsubmit="return false;">
								<input type="hidden" name="accessorieId" />
								<input type="hidden" name="productId" />
							</form>
							<table id="CredentialbaseTable" class="table table-striped table-bordered table-hover" >
								<thead>
									<tr>
										<th style="width:8%" class="center hidden-480">序号</th>
										<th style="width:10%" class="center">产品编号</th>
										<th style="width:10%"  class='center hidden-480'>辅料编号</th>
										<th style="width:10%" class="center">证书编号</th>
										<th style="width:15%" class='center hidden-480'>证书名称</th>		
										<th style="width:17%" class="center">认证机构名称</th>
										<th style="width:13%" class="center">认证日期</th>
										<th style="width:10%" class="center">状态</th>
										<th style="width:7%" class="center">操作</th>
									</tr>
								</thead>
								<tbody></tbody>
						</table>  
						</div>
				<a id="CredentialAdd" class="lrspace3 aBtnNoTD" title="增加" href="#">
				<i class="icon-plus-sign color-green bigger-120"></i>
				</a>
			<%@include file="CredentialForm.jsp" %>
			</div>
		</div>
	</div>

