<%@ page contentType="text/html;charset=UTF-8" %>
<div id="auDiv" class="hide" style="width:500px ">
		<form id="auForm" method="POST" onsubmit="return false;" enctype="multipart/form-data">
			<table cellspacing="0" cellpadding="4px;" border="0" class="customTable">
				<tr class="FormData"  rowspan="2">
					<td class="CaptionTD"><font color="red">*</font>证书图片(多张)：</td>
					<td class="DataTD" colspan="3">
						<input type="hidden" id="imgId" name="imgId" value=''/>
						<div id="wrapper">
							<div id="container" style="width: 400px">
								<p>点击按钮选择照片</p>
								<div id="uploader">
									<div class="queueList">
										<div id="dndArea" class="placeholder">
											<div id="filePicker"></div>
											<p>或将照片拖到这里</p>
										</div>
									</div>
									<div class="statusBar">
										<!-- <div class="progress">
											<span class="text">0%</span> <span class="percentage"></span>
										</div> -->
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
		</form>	   
</div>