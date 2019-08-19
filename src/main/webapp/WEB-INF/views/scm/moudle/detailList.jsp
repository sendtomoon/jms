<%@ page contentType="text/html;charset=UTF-8" %>
<div id="detailDiv" class="hide">
	<div class="page-content">
		<div class="row-fluid">
			<div class="col-xs-12">
						<div id="rightDiv" >
							<form id="baseForm1" method="POST" onsubmit="return false;">
								<div class="widget-main customBtn">	
									<input  type="text"  name="keyWord" placeholder="这里输入关键词" class="input-large">																										
									&nbsp;&nbsp;<button class="btn btn-warning  btn-xs" id="searchBtn1" title="过滤" type="button" onclick="getbaseList1(1)"><i class="icon-search bigger-110 icon-only"></i></button>	
<%-- 									<c:if test="${moudleid!=null}"> --%>
<!-- 										&nbsp;&nbsp;<button class="btn btn-warning  btn-xs" id="outhBtn" title="款号" type="button" onclick="out()">款号</button>	 -->
<%-- 									</c:if>	 --%>
								</div>	
								<input type='hidden' class='pageNum' name='pageNum' value='1'/>
								<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
								<input type='hidden' name='moudleid' value='${moudleid}'/>		
								<input type='hidden' name='categoryid' value='${categoryid}'/>	
								<input type='hidden' id="menuPId" name='menuPId' value='${menuPId}'/>	
							</form>
							<table id="baseTable1" class="table table-striped table-bordered table-hover" >
								<thead>
									<tr>
										<th style="width:3%" class="center">
										<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
									</th>
									<th style="width:7%"  class="center hidden-480">序号</th>
									<th style="width:9%" class="center ">名称</th>
									<th style="width:9%" class="center ">供应商</th>
									<th style="width:9%" class="center ">供应商款号</th>
									<th style="width:9%" class="center">工费</th>
									<th style="width:9%" class="center">附加工费</th>
									<th style="width:9%" class="center">销售工费</th>
									<th style="width:9%" class="center">销售损耗(百分比)</th>
									<th style="width:9%" class="center">主要工厂</th>
									<th style="width:9%" class="center">状态</th>
									<th style="width:9%" class="center">操作</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
							<div class="row">
								<div class="col-sm-4">
									<div id="detailAdd">
										<div class="dataTables_info customBtn" >
											<a id="addBtnDetail" class="lrspace3 aBtnNoTD" title="增加" href="#">
												<input type="hidden" name="warehouse" id="warehouse">
												<i class="icon-plus-sign color-green bigger-220"></i>
											</a>
											<a id="delBtnDetail" class="lrspace3 aBtnNoTD" title="删除多个" href="#">
												<i class="icon-trash color-red bigger-220"></i>
											</a>
	<%-- 										<c:forEach var="pbtn" items="${permitBtn}"> --%>
	<%-- 											<a href="#" title="${pbtn.name}" id="${pbtn.btnId}" class="lrspace3" ><i class='${pbtn.icon} bigger-220'></i></a> --%>
	<%-- 										</c:forEach> --%>
										</div>
									</div>
								</div>
								<div class="col-sm-8">
									<!--设置分页位置-->
									<div id="pageing1" class="dataTables_paginate paging_bootstrap">
										<ul class="pagination"></ul>
									</div>
								</div>
							</div>
						</div>
			</div>
		</div>
	</div>
</div>