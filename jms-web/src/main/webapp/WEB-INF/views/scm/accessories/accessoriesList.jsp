<%@ page contentType="text/html;charset=UTF-8" %>
	<div class="hide" id="accessoriesDiv">
		<div class="row-fluid">
		
			<div class="col-xs-12">
			<button  id="accessoriesAdd" type="button" class="btn btn-success btn-xs" style="border-radius:4px" >增加</button>
						<div id="rightDiv" >
							<form id="accessoriesBaseForm" class="form-inline" method="POST" onsubmit="return false;">
								<input type="hidden" name="productId" />
							</form>
							<table id="accessoriesbaseTable" class="table table-striped table-bordered table-hover" >
								<thead>
									<tr>
										<th style="width:6%" class="center hidden-480">序号</th>
										<th style="width:8%"  class='center hidden-480'>石代码</th>
										<th style="width:8%"  class='center hidden-480'>石名称</th>
										<th style="width:8%" class="center">石重</th>
										<th style="width:8%" class='center hidden-480'>石数</th>		
										<th style="width:8%" class="center">采购单价</th>
										<th style="width:8%" class="center">成本单价</th>
										<th style="width:8%" class="center">镶石工费</th>
										<th style="width:8%" class="center">主石标记</th>
										<th style="width:5%" class="center">状态</th>
										<th style="width:3%" class="center">操作</th>
									</tr>
								</thead>
								<tbody></tbody>
						</table>  
						</div>
			<%@include file="accessoriesForm.jsp" %>
			</div>
		</div>
	</div>