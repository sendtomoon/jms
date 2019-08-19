<%@ page contentType="text/html;charset=UTF-8"%>
<div id="productDiv" class="hide">
	<div class="row">
		<div class="col-sm-6">
			<div class="tabbable">
				<ul class="nav nav-tabs" id="myTab">
					<li class="active">
						<a data-toggle="tab" href="#home"> <i class="green icon-home bigger-110"></i>基本信息</a>
					</li>
					<li>
						<a data-toggle="tab" href="#profile" id="profileCB"> <i class="icon-strikethrough "></i>成本信息</a>
					</li>
					<li>
						<a data-toggle="tab" href="#dropdown1" id="dropdownAccessories"> <i class="icon-cog "></i>辅件信息</a>
					</li>
					<li>
						<a data-toggle="tab" href="#dropdown2" id="dropdownCredential"> <i class="icon-book "></i>证书信息</a>
					</li>
				</ul>
				<form id="productInfoForm" method="POST" onsubmit="return false;">
				<div  class="tab-content" style="width: 780px;">
				
					<div id="home" class="tab-pane in active" >
							<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
								<tr>
									<input id="controlType"  name="controlType" type="hidden" value="0"/>
									<input  name="id" type="hidden"/>
									<input type="hidden"  maxl="20" name="description" maxl="500"/>
									<td style="text-align: right;"><font color="red">*</font>采购单号：</td>
									<td style="text-align: left;">
									<input id="queryMouCode" jyValidate="required,lenrange" maxl="20" name="purchaseNum" type="text" style="width: 150px;" />
									</td>
									<td style="text-align: right;">&ensp;条码：</td>
									<td style="text-align: left;"><input name="code" type="text" readonly placeholder="系统生成" style="width: 150px;" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">&ensp;入库通知单：</td>
									<td style="text-align: left;"><input id="noticeno" name="noticeno" type="text"  style="width: 150px;" />
									</td>
									<td style="text-align: right;">主石大小：</td>
									<td style="text-align: left;"><input type="text"  readonly="readonly" maxl="8" name="stonesize" style="width: 150px;" /></td>
									</td>
								</tr>
								<tr>
									<td style="text-align: right;">&ensp;标签类别：</td>
									<td style="text-align: left;">
										<span id="selectLabelType"><select id="labelType" name="labelType" style="width: 150px;"></select></span>
									</td>
									<td style="text-align: right;">&ensp;商品大类：</td>
									<td style="text-align: left;">
										<span id="selectCCategoryId"><select id="cCategoryId" name="cCategoryId" style="width: 150px;"></select></span>
									</td>
								</tr>
								<tr>
									<td style="text-align: right;">&ensp;款号：</td>
									<td style="text-align: left;"><input id="queryMoudleCode" name="mouCode" jyValidate="lenrange" maxl="36" type="text" data-provide="typeahead" autocomplete="off" placeholder="请输入Code检索" style="width: 150px;" /></td>
									<td style="text-align: right;">&ensp;工厂款号：</td>
									<td style="text-align: left;"><input id="queryMoudtlCode" name="moudtlCode" jyValidate="lenrange" maxl="36" type="text" data-provide="typeahead" autocomplete="off" placeholder="请输入Code检索" style="width: 150px;" /></td>
								</tr>
								<tr>
									<td style="text-align: right;"><font color="red">*</font>供应商：</td>
									<td style="text-align: left;"><input id="queryFranchisee" jyValidate="required" name="franchiseeName" type="text" data-provide="typeahead" autocomplete="off" placeholder="请输入供应商检索" style="width: 150px;" /> <input type="hidden" name="franchiseeId" /></td>
									<td style="text-align: right;">&ensp;销售大类：</td>
									<td style="text-align: left;"><span id="selectSCategoryId"><select id="sCategoryId" name="sCategoryId" value="0" style="width: 150px;"></select></span></td>
								</tr>
								<tr>
									<td style="text-align: right;">&ensp;金类：</td>
									<td style="text-align: left;"><span id="selectGoldType"><select id="queryGoldType" name="goldType" style="width: 150px;"></select></span></td>
									<td style="text-align: right;">&ensp;首饰处理方式：</td>
									<td style="text-align: left;"><span id="selectJewelryMode"><select id="jewelryMode" name="jewelryMode" style="width: 150px;"></select></span></td>
								</tr>
								<tr>
									<td style="text-align: right;"><font color="red">*</font>货类：</td>
									<td style="text-align: left;"><span id="selectProdCate"><select jyValidate="required" id="cateId" name="cateId" style="width: 150px;"></select></span></td>
									<td style="text-align: right;"><font color="red">*</font>首饰类别：</td>
									<td style="text-align: left;"><span id="selectJewelryType"><select jyValidate="required" id="cateJewelryId" name="cateJewelryId" style="width: 150px;"></select></span></td>
									</tr>
								<tr>
									<td style="text-align: right;">&ensp;名称：</td>
									<td style="text-align: left;"><input name="name" type="text" placeholder="自动组合生成" readonly style="width: 150px;" /></td>
									<td style="text-align: right;">&ensp;金重：</td>
									<td style="text-align: left;"><input type="text" jyValidate="lenrange" style="width: 150px;"  onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="goldWeight"/></td>

									</tr>
								<tr>
									<td style="text-align: right;">&ensp;实际重量：</td>
									<td style="text-align: left;"><input type="text" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="fGoldWeight" style="width: 150px;" /></td>
									<td style="text-align: right;">&ensp;总重量(g)：</td>
									<td style="text-align: left;"><input type="text" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" jyValidate="required,lenrange"  maxl="15" name="totalWeight" style="width: 150px;" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">&ensp;主石形状：</td>
									<td style="text-align: left;"><span id="selectStoneShape2"><select id="stoneShape" name="stoneShape" readonly="readonly" style="width: 150px;"></select></span></td>
									<td style="text-align: right;"><font color="red">*</font>件数：</td>
									<td style="text-align: left;"><input type="text" onkeyup="JY.Validate.limitNum(this)" jyValidate="required,lenrange" readonly="readonly" maxl="8" name="count" value="1" style="width: 150px;"/></td>
								</tr>

								<tr>
									<td style="text-align: right;">&ensp;长(mm)：</td>
									<td style="text-align: left;">
									<input type="text" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="length" style="width: 150px;" />
									</td>
									<td style="text-align: right;">&ensp;宽(mm)：</td>
									<td style="text-align: left;"><input type="text" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="width" style="width: 150px;" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">&ensp;高(mm)：</td>
									<td style="text-align: left;"><input type="text" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="height" style="width: 150px;" /></td>
									<td style="text-align: right;">&ensp;圈口(号)：</td>
									<td style="text-align: left;"><input type="text" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="10" name="circel" style="width: 150px;" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">&ensp;证书号：</td>
									<td style="text-align: left;"><input type="text" jyValidate="lenrange" maxl="36" name="cerNum" style="width: 150px;" /></td>
									<td style="text-align: right;">&ensp;证书费(元)：</td>
									<td style="text-align: left;"><input type="text" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="costCer" style="width: 150px;" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">&ensp;串号：</td>
									<td style="text-align: left;"><input type="text" jyValidate="lenrange" maxl="20" name="serialNo" style="width: 150px;" /></td>
									<td style="text-align: right;">&ensp;电子码：</td>
									<td style="text-align: left;"><input type="text" jyValidate="lenrange" maxl="20" name="epc" style="width: 150px;" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">&ensp;备注：</td>
									<td colspan="3" style="text-align: left;">
									<textarea rows="2" cols="69" jyValidate="lenrange" maxl="50" name="remarks" multiline="true"></textarea></td>
								</tr>
								<tr>
									<td style="text-align: right;">&ensp;商品图片：</td>
									<td colspan="3" style="text-align: left;">
										<input type="hidden" id="imgId" name="imgId" value='' />
										<div id="wrapper">
											<div id="container" style="margin-left: -12px; width: 510px;">
												<p>点击按钮选择照片</p>
												<div id="uploader">
													<div class="queueList">
														<div id="dndArea" class="placeholder">
															<div id="filePicker"></div>
															<p>或将照片拖到这里</p>
														</div>
													</div>
													<div class="statusBar">
														<div class="info"></div>
														<div class="btns">
															<div id="filePicker2"></div>
															<div class="uploadBtn">开始上传</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</table>
					</div>
					<div id="profile" class="tab-pane" style="width: 720px;">
						   <table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
								<tr>
									<td style="text-align: right;">&ensp;货品财务大类：</td>
									<td style="text-align: left;"><span id="selectFCategoryId"><select id="fCategoryId" name="fCategoryId" value="0" style="width: 150px;"></select></span></td>
									<td style="text-align: right;">&ensp;采购成本(元)：</td>
									<td style="text-align: left;"><input type="text" id="costPur" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="costPur" style="width: 150px;" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">&ensp;基础工费(元)：</td>
									<td style="text-align: left;"><input type="text" id="wageBasic" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="wageBasic" style="width: 150px;" /></td>
									<td style="text-align: right;">&ensp;附加工费(元)：</td>
									<td style="text-align: left;"><input type="text" id="wageAdd" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="wageAdd" style="width: 150px;" /></td>
								</tr>
								<tr>
									<td style="text-align: right;"><font color="red">*</font>工费计价方式：</td>
									<td style="text-align: left;"><label><input id="wageMode" name="wageMode" type="radio"  value="1" />克 </label> <label><input id="wageMode2" name="wageMode" type="radio" value="2" />件</label></td>
									<td style="text-align: right;">&ensp;销售工费(元)：</td>
									<td style="text-align: left;"><input type="text" id="wageSe" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="wageSe" style="width: 150px;" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">&ensp;配件费(元)：</td>
									<td style="text-align: left;"><input type="text" id="wageCw" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="wageCw" style="width: 150px;" /></td>
									<td style="text-align: right;">&ensp;成本金价(元)：</td>
									<td style="text-align: left;"><input type="text" id="goldCost" onkeyup="JY.Validate.limitAmtNum(this)" jyValidate="lenrange" maxl="15" name="goldCost" style="width: 150px;" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">&ensp;起版费(元)：</td>
									<td style="text-align: left;"><input type="text" id="wagePw" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="wagePw" style="width: 150px;" /></td>
									<td style="text-align: right;">&ensp;喷砂工费(元)：</td>
									<td style="text-align: left;"><input type="text" id="wageSw" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="wageSw" style="width: 150px;" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">&ensp;超镶工费(元)：</td>
									<td style="text-align: left;"><input type="text" id="wageEw" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="wageEw" style="width: 150px;" /></td>
									<td style="text-align: right;">&ensp;其它工费(元)：</td>
									<td style="text-align: left;"><input type="text" id="wageOw" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="wageOw" style="width: 150px;" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">&ensp;核价成本(元)：</td>
									<td style="text-align: left;"><input type="text" id="costChk" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="costChk" style="width: 150px;" /></td>
									<td style="text-align: right;">&ensp;牌价(元)：</td>
									<td style="text-align: left;"><input type="text"  jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="price" style="width: 150px;" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">&ensp;财务成本(元)：</td>
									<td style="text-align: left;"><input  id="costFin" type="text" jyValidate="lenrange" onkeyup="JY.Validate.limitAmtNum(this)" maxl="15" name="costFin" style="width: 150px;" /></td>
								</tr>
							</table>
					</div>
				</form>
					<div id="dropdown1" class="tab-pane">
							<table id="productAccessoriesTabe" class="table table-striped table-bordered table-hover" >
								<thead>
									<tr>
										<th style="width:6%" class="center hidden-480">序号</th>
										<th style="width:8%"  class='center hidden-480'>石代码</th>
										<th style="width:8%"  class='center hidden-480'>石名称</th>
										<th style="width:8%" class="center">石重</th>
										<th style="width:8%" class='center hidden-480'>石数</th>		
										<th style="width:8%" class="center">采购价值</th>
										<th style="width:8%" class="center">成本价值</th>
										<th style="width:8%" class="center">石单位</th>
										<th style="width:8%" class="center">主石标记</th>
										<th style="width:5%" class="center">状态</th>
										<th style="width:6%" class="center">操作</th>
									</tr>
								</thead>
								<tbody></tbody>
						</table>  
						<a id="productAccessoriesAdd" class="lrspace3 aBtnNoTD" title="增加" href="#">
							<i class="icon-plus-sign color-green bigger-120"></i>
						</a>
					</div>

					<div id="dropdown2" class="tab-pane">
						<table id="productCredentialTabe"class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th style="width: 3%" class="center hidden-480">序号</th>
									<th style="width: 6%" class="center">名称</th>
									<th style="width: 6%" class='center hidden-480'>证书类型</th>
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
	</div><!-- row -->
<div id="img"></div>

<div id="excel" class="hide">
	<form id="excelForm" method="POST"  action="${jypath}/scm/product/toUploads">
 		<table>
			<tr> 
 				<td style="text-align: right;">选择Excel文件：</td> 
 					<td style="text-align: right;"><input type="text" name="importName" id='textfield' /></td> 
 				<td style="text-align: right;"> 
 					<div style="position:relative;"> 
 						<input type="button" value="浏览...."/> 
 						<input type='file' name="file" id="fileUpload" style="opacity:0;position:absolute;top:0;left:0;width:20px;height:20px;cursor:pointer;" onchange="document.getElementById('textfield').value=this.value"> 
 						<input type="submit" value="导入"/> 
 					</div> 
 				</td>
 			</tr>
 		</table> 
 	</form>
 </div> 

