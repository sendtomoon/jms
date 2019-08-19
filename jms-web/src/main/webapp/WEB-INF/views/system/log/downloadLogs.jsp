<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
<%@include file="../../common/includeBaseSet.jsp" %>
<%@include file="../../common/includeSystemSet.jsp" %>
<script>
$(function(){
	$('#downloadsLogs').on('click',function(){		
		$(this).prop("href",jypath+"/scm/common/logs/downloads?name=System_");
	});
});
		
</script>
</head>
<body>
	<div class="page-content">
		<div class="row-fluid">
		<br>
		<p>下载当日项目运行日志：</p>
		<hr>
			<a href="javascript:void(0)" title="下载今日运行日志" id="downloadsLogs" class="lrspace3">
			<i class="icon-download-alt color-blue bigger-220"></i></a>
			
			<%@include file="../../common/dialog.jsp" %>
			
		</div>
	</div>
</body>
</html>