<%@ page contentType="text/html;charset=UTF-8" %>
<div id="wrapper" style="display:none;">
	<input type="hidden" id="imgId" name="imgId" value='' />
	<div id="container" style="margin-left: -12px; width: 400px">
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