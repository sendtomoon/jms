<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
<%@include file="../../common/includeBaseSet.jsp" %>
<%@include file="../../common/includeCommon.jsp" %>
<link rel="stylesheet" href="${jypath}/static/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" />
<script src="${jypath}/static/plugins/ligerUI/js/ligerui.all.js"></script>
<script src="${jypath}/static/js/scm/demo/gridDemo.js"></script>

</head>
<body>
<br/>
<span style="font-size:24px;">【legerGrid aip:】<a href="http://api.ligerui.com/" target="_blank">http://api.ligerui.com/</a></span>
<br/>
<br/>
<a class="l-button" style="width:100px;float:left; margin-left:10px;" id="addRow">添加行</a>
<a class="l-button" style="width:120px;float:left; margin-left:6px;" id="delRows">删除行</a>
<div class="l-clear"></div>
<div id="gridDemo1" style="margin-top:10px;margin-bottom: 10px;"></div>	

<a class="l-button" style="width:100px;float:left; margin-left:10px;" id="saveGrid">保存</a> 


</body>
</html>