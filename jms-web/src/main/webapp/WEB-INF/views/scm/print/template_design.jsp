<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
<%@include file="../../common/includeBaseSet.jsp" %>
<%@include file="../../common/includeSystemSet.jsp" %>
<%@include file="../../common/dialog.jsp" %>
<link rel="stylesheet" href="${jypath}/static/css/system/jquery/jquery-labelauty.css">
<style>
ul { list-style-type: none;}
li { display: inline-block;}
li { margin: 10px 0;}
input.labelauty + label { font: 12px "Microsoft Yahei";}
</style>

<script language="javascript" src="${jypath}/static/js/lodop/LodopFuncs.js"></script>
<script language="javascript" src="${jypath}/static/js/scm/print/designTemplate.js"></script>


</head>
<body>
<div class="page-content">
<object id="MYLODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0 >
<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object> 
<div class="tabbable">
				<ul class="nav nav-tabs" id="myTab">
					<li class="active">
						<a data-toggle="tab" href="#createTemplate"> <i class="icon-book"></i>新增模版</a>
					</li>
					<li>
						<a data-toggle="tab" href="#updateTemplate"> <i class="icon-cog"></i>调整模版</a>
					</li>
					<!-- <li>
						<a data-toggle="tab" href="#templateList"> <i class="green icon-home bigger-110"></i>模版列表</a>
					</li>
					 -->
				</ul>

				<div  class="tab-content" style="height:680px;">
					<div id="createTemplate" class="tab-pane in active" >
					<div>
					<p><strong>模版数据源如下：</strong><span style="color:red;">(提示：点击"设计新的模版"按钮会弹出设计模版的窗口,设计完成后,请关闭窗口并"获取程序代码")</span></p>
 
					<ul class="dowebok">
						<li><input type="checkbox" class="mycheckbox" name="datasource" data-labelauty="商品名称" checked value="K_NAME"></li>
						<li><input type="checkbox" class="mycheckbox" name="datasource" data-labelauty="条码" checked value="K_BARCODE"></li>
						<li><input type="checkbox" class="mycheckbox" name="datasource" data-labelauty="牌价" checked value="K_PRICE"></li>
						<li><input type="checkbox" class="mycheckbox" name="datasource" data-labelauty="主石" checked value="K_MSTONE"></li>
						<li><input type="checkbox" class="mycheckbox" name="datasource" data-labelauty="辅石" checked value="K_ASTONE"></li>
						<li><input type="checkbox" class="mycheckbox" name="datasource" data-labelauty="金重" checked value="K_GOLD"></li>
						<li><input type="checkbox" class="mycheckbox" name="datasource" data-labelauty="圈口" checked value="K_SIZE"></li><br>
						<li><input type="checkbox" class="mycheckbox" name="datasource" data-labelauty="颜色/净度/切工" checked value="K_FSF"></li>
						<li><input type="checkbox" class="mycheckbox" name="datasource" data-labelauty="商品证书号" checked value="K_CERT"></li>
						<li><input type="checkbox" class="mycheckbox" name="datasource" data-labelauty="销售工费" value="K_WAGESE"></li>
						<li><input type="checkbox" class="mycheckbox" name="datasource" data-labelauty="执行标准" checked value="K_NORM"></li>
						<li><input type="checkbox" class="mycheckbox" name="datasource" data-labelauty="合格品" checked value="K_PASS"></li>
					</ul>
					</div>
						<p>
						宽：<input type="text" id="tempWidth" value="80"/>mm &nbsp; 高：<input type="text" id="tempHeight" value="17"/>mm
						<a href="javascript:;"><input type="button" value="设计新的模版" onclick="designTemplate()"/></a></p>
						<p><input type="button" class="btn btn-primary" value="获取程序代码" onclick="javascript:getProgramCode();"/>&nbsp;&nbsp;
						<input type="button" class="btn btn-primary" value="预览设计" id="previewDesign" style="display:none"  onclick="previewDesign()" />  </p>
						<textarea rows="20" id="designCode" cols="102" placeholder="(此区域显示您设计的模版Code。)"  readonly></textarea><br>
						<br/>
						<form id="createTemplateForm" style="display:none">
						<label>模版名称：</label><input type="text" name="name" jyValidate="required,lenrange" maxl="200">	&nbsp;&nbsp;	<label>模版说明：</label><textarea name="note" rows="1" cols="52" jyValidate="required,lenrange" maxl="500"></textarea>	
						<input name="template" id="template" type="hidden" />	
						<input type="hidden" name="type" value="L"/>
						<br><br><input type="button" class="btn btn-primary" value="保存模版" id="saveDesignTemplate"  /> 
						</form>
					</div>
					
					<div id="updateTemplate" class="tab-pane" >
						<p>
							<select id="templateSelector">
								
							</select>
							<input type="button" value="调整模版" id="designSelectTemplate"  onclick="designSelectedTemplate()"/><span style="color:red;">(提示：点击此按钮会弹出设计模版的窗口,设计完成后,请关闭窗口并"点击获得调整后的模版代码")</span>
						</p>
						<br>
						
						<input type="button" class="btn btn-primary" value="点击获得调整后的模版代码" onclick="javascript:getProgram();"/><br>  
						<textarea rows="20" id="mycode" cols="102" placeholder="(请选择您需要的模版进行设计！此区域显示调整后的模版Code。)"  readonly></textarea><br>
						<br/><input type="button" class="btn btn-primary" value="用调整后的代码执行打印预览" id="mypreview" style="display:none"  onclick="prn_Preview()" /> 
						<input type="button" class="btn btn-primary" value="保存模版" id="saveTemplate" style="display:none" /> 
					</div>
					
					<div id="templateList" class="tab-pane in active" >
						
					</div>
			    </div>
			    
			    
			 </div>



<br>


<p></p>
<br/>

</div>
<script src="${jypath}/static/js/jquery/jquery-labelauty.js"></script>
<script type="text/javascript">
	$('.mycheckbox').labelauty();
</script>
</body>
</html>