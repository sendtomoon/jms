<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0" />
<%@include file="../common/includeBaseSet.jsp"%>
<script type="text/javascript" src="${jypath}/static/js/jquery/jquery.cookie.js" />
<link rel="stylesheet" href="${jypath}/static/css/system/bootstrap/bootstrap.min.css" />
<link rel="stylesheet" href="${jypath}/static/css/system/ace/font-awesome.min.css" />
<link rel="stylesheet" href="${jypath}/static/css/system/jquery/jquery-ui-1.10.3.full.min.css" type="text/css" />
<link rel="stylesheet" href="${jypath}/static/css/system/bootstrap/bootstrap-responsive.min.css" />
<!-- ace styles -->
<link rel="stylesheet" href="${jypath}/static/css/system/ace/ace.min.css" />
<link type="text/css" rel="stylesheet" href="${jypath}/static/css/system/ace/ace-rtl.min.css" />
<link type="text/css" rel="stylesheet" href="${jypath}/static/css/system/ace/ace-responsive.min.css" />
<link type="text/css" rel="stylesheet" href="${jypath}/static/css/system/ace/ace-skins.min.css" />
<link type="text/css" rel="stylesheet" href="${jypath}/static/plugins/tabs/css/tab-control.min.css" />
<link type="text/css" rel="stylesheet" href="${jypath}/static/css/system/system/index.css" />

</head>
<body id='indexBody'>
	<div class="navbar navbar-default" id="navbar" height="57px">
		<div class="navbar-container" id="navbar-container">
			<div class="navbar-header pull-left">
				<span class="navbar-brand">
				<img alt="" src="${jypath}/static/images/main/logo_title.png" width="248px" height="47px">
				</span>
			</div>
			<!-- /.navbar-header -->
			<!-- 头部右边 -->
			<%@include file="index/headerRight.jsp"%>
			<!-- 头部右边 end-->
		</div>
	</div>
	<div class="main-container" id="main-container">
		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span>
			</a>
			<div class="sidebar" id="sidebar">
				<div class="sidebar-shortcuts" id="sidebar-shortcuts"></div>
				<!--左边 导航 菜单 -->
				<ul class="nav nav-list" id="menu_li_id"></ul>
				<!-- /.nav-list -->
				<!--左边 导航 菜单  end-->
				<div class="sidebar-collapse" id="sidebar-collapse">
					<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
				</div>
			</div>
			<!-- 主要界面 右边 -->
			<div class="main-content" id="maincontent">
				<div style="margin: 0; padding-top: 5px; background-color: #fff;">
					<div class="tab-control">
						<div class="tab simple">
							<form>
								<input class="prev" type="button" />
								<input class="next" type="button" /> <input class="find" type="button" />
							</form>
							<ul class="tabul"></ul>
						</div>
						<!-- 标签查找 -->
						<div class="tab-find hidden ">
							<form>
								<input class="text" type="text" />
							</form>
							<ul></ul>
						</div>
						<!-- 主体 -->
						<div class="main"></div>
					</div>
				</div>
				<!-- /.main-content -->
				<!-- 设置栏 -->
				<%@include file="index/settings.jsp"%>
				<%@include file="../common/dialog.jsp"%>
			</div>
		</div>
	</div>
	<script src="${jypath}/static/js/ace/ace-extra.min.js" />
	<script src="${jypath}/static/js/bootstrap/bootstrap.min.js" />
	<script src="${jypath}/static/js/ace/typeahead-bs2.min.js" />
	<script src="${jypath}/static/js/ace/ace-elements.min.js" />
	<script src="${jypath}/static/js/ace/ace.min.js" />
	<script src="${jypath}/static/js/jquery/jquery-ui-1.10.3.full.min.js" />
	<script src="${jypath}/static/js/jquery/jquery.md5.js" />
	<script src="${jypath}/static/plugins/tabs/js/tab-control.min.js" />
	<script src="${jypath}/static/js/system/jy/jy.main.js" />
	<script src="${jypath}/static/js/system/index/index.js" />
</body>
</html>