<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<style>
	#productUpload th{
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
	<table id="productUpload" class="table table-striped table-bordered table-hover" >
						<thead>
							<tr >
								<th>
									<label><input type="checkbox" class="ace" ><span >序号</span></label>
								</th>
								<th>订单号</th>
								<th>入库通知单号</th>
								<th>时间</th>
								<th >名称</th>
								<th >原编号</th>
								<th>证书号</th>
								<th>说明</th>
								<th>备注</th>
								<th>厂家</th>
								<th>款号</th>
								<th>系列1</th>
								<th>系列2</th>
								<th>圈口</th>
								<th>工费成本</th>
								<th>销售工费</th>
								<th>批发工费</th>
								<th>超镶工费</th>
								<th>配件费</th>
								<th>其他工费</th>
								<th>证书费</th>
								<th>增值</th>
								<th>金价</th>
								<th>总重</th>
								<th>金重</th>
								<th>损耗</th>
								<th>销售损耗</th>
								<th>金值</th>
								<th>金类</th>
								<th>货类</th>
								<th>货组</th>
								<th>中央仓</th>
								<th>位置</th>
								<th>公司</th>
								<th>基价</th>
								<th>财务成本</th>
								<th>成本</th>
								<th>牌面价</th>
								<th>倍率</th>
								<th>标签类别</th>
								<th>主石代码</th>
								<th>主石名称</th>
								<th>石形</th>
								<th>主石重</th>
								<th>主石数</th>
								<th>主石值</th>
								<th>镶工</th>
								<th>净度</th>
								<th>颜色</th>
								<th>切工</th>
								<th>石证书</th>
								<th>石包号</th>
								<th>副石编码1</th>
								<th>副石名称1</th>
								<th>副石重1</th>
								<th>副石数1</th>
								<th>副石值1</th>
								<th>副石镶工1</th>
								<th>副石证书1</th>
								<th>副石包号1</th>
								<th>副石编码2</th>
								<th>副石名称2</th>
								<th>副石重2</th>
								<th>副石数2</th>
								<th>副石值2</th>
								<th>副石镶工2</th>
								<th>副石证书2</th>
								<th>副石包号2</th>
								<th>副石编码3</th>
								<th>副石名称3</th>
								<th>副石重3</th>
								<th>副石数3</th>
								<th>副石值3</th>
								<th>副石镶工3</th>
								<th>副石证书3</th>
								<th>副石包号3</th>
								<th>副石编码4</th>
								<th>副石名称4</th>
								<th>副石重4</th>
								<th>副石数4</th>
								<th>副石值4</th>
								<th>副石镶工4</th>
								<th>副石证书4</th>
								<th>副石包号4</th>
							</tr>
					</thead>
					<tbody></tbody>
		</table>
			<input type="button" value="返回"  onclick="window.history.go(-1)"/>
			<input type="button" id="import" value="导入" />
			<input type="button" id="importStock" value="导入商品库存" />
</div>
<div id="productStock" class="hide">
	<form id="productStockForm" method="POST" onsubmit="return false;">
		<table cellspacing="0" cellpadding="4px;" border="0"
			class="customTable">
			<tr class="FormData">
				<td class="CaptionTD">仓库：</td>
				<td class="DataTD">
					<select id="selectWarehouse" style="width: 120px;" name="warehouseId" jyValidate="required" onchange="change(this.value)">
						<option value="">请选择仓库</option>
					</select>
				</td>
				<td class="CaptionTD">仓位</td>
				<td class="DataTD">
					<select id="selectLocation" style="width: 120px;" name="locationId"  jyValidate="required">
						<option value="">请选择仓位</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="CaptionTD">入账类型</td>
				<td class="DataTD"><span id="selFeeType"><select id="feeType" name="feeType" style="width:120px;"></select></span></td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</form>
</div>
	
<script>
	(function(){
		JY.Dict.setSelect("selFeeType","SCM_DISCRIPTION");
		$.ajax({
    		type:'POST',
    		url:jypath +'/scm/product/queryProductUpload',
    		data:{},
    		dataType:'json',
    		success:function(data,textStatus){
    			JY.Model.loading();
	        	 $("#productUpload tbody").empty();
	        	 var obj=data.obj;
	        	 var html="";
	       		 if(obj!=null&&obj.length>0){
	           		 for(var i = 0;i<obj.length;i++){
	               		 var l=obj[i];
	               		 html+="<tr>";
	               		 html+="<td class='center hidden-480'>"+(i+1)+"</td>";
	               		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.purchasenum)+"</td>";
	               		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.noticeno)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(new Date(l.createtime).Format('yyyy-MM-dd hh:mm:ss'))+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.name)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.primarycode)+"</td>";
	               		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.procertificate)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.description)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.remarks)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.franchiseecode)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.moucode)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.series)+"</td>";
	               		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.finecolumn)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.circel)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.wagebasic)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.wagese)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.wholesale)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.wageew)+"</td>";
	               		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.wagecw)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.wageow)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.costcer)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.costadd)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.goldcost)+"</td>";
	               		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.totalweight)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.goldweight)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.goldcostlose)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.goldselllose)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.goldvalue)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.goldtype)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.cateid)+"</td>";
	               		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.catejewelryid)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.warehouseid)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.locationid)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.orgid)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.pricesuggest)+"</td>";
	               		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.costfin)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.prime)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.price)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.multiplying)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.labeltype)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonecode)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonename)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stoneshape)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stoneweight)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonecount)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.purcal)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.jeweler)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.clarity)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.color)+"</td>";
	               		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.cut)+"</td>";
	               		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.certificate)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonepkgno)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonecode1)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonename1)+"</td>";
	               		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.stoneweight1)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonecount1)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.purcal1)+"</td>";
	               	   	 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.jeweler1)+"</td>";
	               	  	 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.certificate1)+"</td>";
	               	     html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonepkgno1)+"</td>";
	               	     html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonecode2)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonename2)+"</td>";
	               		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.stoneweight2)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonecount2)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.purcal2)+"</td>";
	               	   	 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.jeweler2)+"</td>";
	               	     html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.certificate2)+"</td>";
	               	   	 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonepkgno2)+"</td>";
	               	     html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonecode3)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonename3)+"</td>";
	               		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.stoneweight3)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonecount3)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.purcal3)+"</td>";
	               	   	 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.jeweler3)+"</td>";
	               	  	 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.certificate3)+"</td>";
	               	   	 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonepkgno3)+"</td>";
	               	     html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonecode4)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonename4)+"</td>";
	               		 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.stoneweight4)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonecount4)+"</td>";
	               		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.purcal4)+"</td>";
	               	   	 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.jeweler4)+"</td>";
	               	 	 html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.certificate4)+"</td>";
	               	   	 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.stonepkgno4)+"</td>";
	               		 html+="</tr>";
	           		 }
	           		 $("#productUpload tbody").append(html);
	       		 }else{
	       			html+="<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
	           		$("#productUpload tbody").append(html);
	       		 }
	       		 JY.Model.loadingClose();		
    		}
    	});
		loads();
	})();
	$("#import").on('click',function(){ 	
	     JY.Model.confirm("是否导入数据",function(){
	    	 JY.Ajax.doRequest(null,jypath +'/scm/product/batchImport',null,function(data){
	    		 JY.Model.info(data.resMsg,function(){window.history.go(-1);});
			    });
	     });
				
	});
	$("#importStock").on('click',function(){
		$("#productStockForm #selectWarehouse").val('');
		$("#productStockForm #selectLocation").val('');
		stockInfo({
			id:"productStock",
			title:"选择仓库仓位",
			height:"220",
			width:"500",
			savefn:function(){
				debugger
				var Warehouse=$("#productStockForm #selectWarehouse").val();
				var Location=$("#productStockForm #selectLocation").val();
				var feeType=$("#productStockForm #feeType").val();
				JY.Ajax.doRequest(null,jypath +'/scm/product/batchStock',{warehouseId:Warehouse,locationId:Location,feeType:feeType},function(data){
					JY.Model.info(data.resMsg,function(){window.history.go(-1);});
				});
			}
		});
	});
	function stockInfo(attr){
		$("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,buttons:[{html: "<i class='icon-ok bigger-110'></i>&nbsp;保存","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this);}}},{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]});
	}
	var provinceEle = document.getElementById("selectWarehouse");
    var cityEle = document.getElementById("selectLocation");

	function loads(){
		JY.Ajax.doRequest(null,jypath +'/scm/warehousing/warehousAll',null,function(data){
			 var obj=data.obj;
			 cityEle.options.length = 1;
	    	 for (var i = 0; i < obj.length; i++) {
	              op = new Option(obj[i].value, obj[i].key);
	             //添加
	             provinceEle.options.add(op);
	         }
		});
	}
	function change(obj){
			 cityEle.options.length = 1;
			JY.Ajax.doRequest(null,jypath +'/scm/warehousing/selectWarehousingLocation',{id:obj},function(data){
				var cs=data.obj;
			    for (var i = 0; i < cs.length; i++) {
			        var op = new Option(cs[i].value,cs[i].key);
			        cityEle.options.add(op);
			    }
			});

	}

</script>
</body>
</html>