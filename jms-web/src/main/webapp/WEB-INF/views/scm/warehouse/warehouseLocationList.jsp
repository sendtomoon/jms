<%@ page contentType="text/html;charset=UTF-8"%>
<div id="locationDiv" class="hide">
	<div id="btnLocation">
		<button  id="addBtnLoca" type="button" class="btn btn-success btn-xs" style="border-radius:4px" ><input type="hidden" name="warehouse" id="warehouse">增加</button>
		<button  id="delBtnLoca" type="button" class="btn btn-success btn-xs" style="border-radius:4px" >删除</button>
	</div>
	<table id="locationTable" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th style="width: 3%" class="center">
					<label><input type="checkbox" class="ace"><span class="lbl"></span></label>
				</th>
				<th style="width: 7%" class="center hidden-480">序号</th>
				<th style="width: 10%" class="center ">仓位名称</th>
				<th style="width: 10%" class="center ">仓位代码</th>
				<th style="width: 10%" class="center ">所属仓库</th>
				<th style="width: 10%" class="center ">仓位类型</th>
				<th style="width: 10%" class="center">状态</th>
				<th style="width: 10%" class="center ">默认仓位</th>
				<th style="width: 10%" class="center">操作</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
</div>