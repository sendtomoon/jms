<%@ page contentType="text/html;charset=UTF-8" %>
<div id="attrGroupDiv" class="hide">
		<form id="attrGroupForm" method="POST" onsubmit="return false;" >
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr>
					<td>
						<font color="red">*</font>名称：
					</td>
					<td>
						<input type="hidden" name="id">
						<input type="text" jyValidate="required"  name="name" class="FormElement ui-widget-content ui-corner-all">
					</td>
					<td>
						<font color="red">*</font>分类：
					</td>
						<td>
						<input id="cateGoryInput" type="text" readonly value="" jyValidate="required" class="FormElement ui-widget-content ui-corner-all" onclick="showOrgComp(); return false;"/>
						
						<input type="hidden" name="categoryId">
						<input type="hidden" name="categoryName">
						<a href="#" title="清空" onclick="emptyOrgComp(); return false;" class="lrspace3 aBtnNoTD" data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a>
						<div id='categoryContentList' class="menuContent ztreeMC" style="display: none; position: absolute;">
							<ul id="selectCategoryTree" class="ztree preOrgTree"></ul>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font>排序：
					</td>
					<td>
						<input type="number" jyValidate="required" name="sort" class="FormElement ui-widget-content ui-corner-all">
					</td>
					<td>
						&ensp;类型：
					</td>
					<td>
						<span id="selectAttrType"><select id="attrGroupType" name="type" style="width:157px;"></select></span>
					</td>
				</tr>
				<tr>
					<td>
						&ensp;描述：
					</td>
					<td colspan="3">
						<textarea rows="2" cols="80" jyValidate="lenrange" maxl="200" name="description" multiline="true" ></textarea>
					</td>
				</tr>
			</table>
		</form>
		<div class="hr hr-dotted"></div>		
		<div id="super">
<!-- 				<div id="left"> -->
<!-- 					<div class="head">已选择： -->
						
<!-- 					</div> -->
<!-- 					<div  class="pignumberchoice"></div> -->
<!-- 				</div> -->
				<div id="right">
					<div class="head">请选择属性：</div>
					<table id="chbox"></table>
				</div>
		</div>
		<br/><br/>
	
</div>
<style type="text/css">
 	#super{
 		border:0px solid;
 		width:100%;
 		height:400px;
 	}
/*  	#left{ */
/*  		border:1px solid #e3e3e3; */
/*  		width:10%; */
/*  		float: left; */
/*  		height:400px; */
/*  		overflow:auto; */
/*  	} */
 	#right{
 		border:1px solid #e3e3e3;
 		width:100%;
 		height:400px;
 		float: left;
 		overflow:auto;
 	}
 	.head{
 		padding:8px;
 		border:0px solid;
 		background-color: #eee;
 	}
</style>
