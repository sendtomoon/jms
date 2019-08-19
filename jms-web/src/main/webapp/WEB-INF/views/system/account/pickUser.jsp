<%@ page contentType="text/html;charset=UTF-8"%>
<div id="pickUserDiv" class="hide">
	<div class="col-xs-12">
		<div class="col-sm-6">
			<div class="widget-box">
				<ul id="orgTree" class="ztree orgTree"></ul>
			</div>
		</div>
		<div class="col-sm-6">
			<form id="userForm" class="form-inline" method="POST"
				onsubmit="return false;">
				<div class="row">
					<div class="widget-main">
						&ensp;&nbsp;<input type="text" name="keyWord"
							placeholder="这里输入关键词" class="input-large">
						<button id='searchBtn' class="btn btn-warning  btn-xs" title="过滤"
							type="button" onclick="getUserList(1)">
							<i class="icon-search bigger-110 icon-only"></i>
						</button>
					</div>
				</div>
				<input type='hidden' class='pageNum' name='pageNum' value='1' /> <input
					type='hidden' class='pageSize' name='pageSize' value='10' /> <input
					type='hidden' name='orgId' /> <input type='hidden' name='_orgName' />
			</form>
			<table id="userTable"
				class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th style="width: 3%" class="center"><label></label></th>
						<th style="width: 10%" class="center ">用户名</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
			<div class="row">
				<div class="col-sm-12">
					<!--设置分页位置-->
					<div id="userPageing" class="dataTables_paginate paging_bootstrap">
						<ul class="pagination"></ul>
					</div>
				</div>
			</div>
	</div>
		选择人：
		<input type='text'  id='pickUserName' readonly="readonly"  class="input-large" />
		<input type='hidden'  id='pickUserId' value=""/>
</div>
