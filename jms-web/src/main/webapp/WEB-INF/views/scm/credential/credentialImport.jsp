<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<style>
	#credentialUpload td{
		font-size:14px;
		border:1px solid;
		padding:15px;
		
	}
</style>
<%@include file="../../common/includeBaseSet.jsp" %>
<%@include file="../../common/includeSystemSet.jsp" %>
<%@include file="../../common/dialog.jsp" %>
</head>
<body>
	<div>
		<table id="credentialUpload" class="table table-striped table-bordered table-hover">
			<tr>
				<td>检测编号</td>
				<td>批次号</td>
				<td>证书类型</td>
				<td>宝石名称</td>
				<td>饰品类型</td>
				<td>形状</td>
				<td>标注</td>
				<td>标石重</td>
				<td>总质量</td>
				<td>称重备注</td>
				<td>色级</td>
				<td>净度</td>
				<td>台宽比</td>
				<td>亭深比</td>
				<td>厂商号</td>
				<td>金额</td>
			</tr>
			<c:forEach items="${result}" var="l">
				<tr>
					<td>${l.detectionid}</td>
					<td>${l.batchid}</td>
					<td>${l.certificatetype}</td>
					<td>${l.gemname}</td>
					<td>${l.ornamenttype}</td>
					<td>${l.form}</td>
					<td>${l.label}</td>
					<td>${l.weight}</td>
					<td>${l.quality}</td>
					<td>${l.remarks}</td>
					<td>${l.color}</td>
					<td>${l.neatness}</td>
					<td>${l.width}</td>
					<td>${l.depth}</td>
					<td>${l.code}</td>
					<td>${l.money}</td>
				</tr>
			</c:forEach>
		</table>
		<input type="hidden" id="id"  value="${batchid}"/>
		<input type="button" value="返回"  onclick="window.history.go(-1)"/>
		<input type="button" id="uploadResult" value="导入证书信息" />
	</div>
<script type="text/javascript">
	$(function(){
		$("#uploadResult").on('click',function(){
				var id= $("#id").val();
			     JY.Model.confirm("是否导入数据",function(){
			    	 JY.Ajax.doRequest(null,jypath +'/scm/credential/batchCredential/'+id,null,function(data){
			    		 JY.Model.info(data.resMsg,function(){window.history.go(-1);});
					    });
			     });
						
			});
		});
	
</script>
</body>
</html>