/**
 * 打印模版设计js  (2017/02/21 by lisongbai)
 */
$(function(){
	    /**
	     * 数据源checkbox渲染
	     */
		/**
		 * 下拉框查询
		 */
		JY.Ajax.doRequest(null,jypath +'/scm/print/selectTemplate',{type:"L"},function(data){
		     $("#templateSelector").empty();
		     var defaultOpts = '<option value="">请选择模版</option>';
		     $.each(data.obj, function(i, v) {
                 defaultOpts += '<option value="' + v.value + '">' + v.key + "</option>"
             });
		     $("#templateSelector").append(defaultOpts);
		});
		
		/**
		 * 修改模版
		 */
		$("#saveTemplate").on('click',function(){
			var templateCode = document.getElementById('saveTemplate').getAttribute("template-data");
			if(JY.Object.notNull(templateCode)){
				JY.Model.confirm("请确保您已预览或打印<br>测试过,确认保存吗？",function(){
					JY.Ajax.doRequest(null,jypath +'/scm/print/updateTemplate',{id:$("#templateSelector").val(),template:templateCode},function(data){
					    if(data.res=='1'){					    	
							JY.Model.info(data.resMsg);
					    }else{
					    	JY.Model.error(data.resMsg);
					    }
					});
				});
			}else{
				JY.Model.error("模版代码为空！");
			}
		});
		/**
		 * 新增模版
		 */
		$("#saveDesignTemplate").on('click',function(){
			var templateCode = $('#createTemplateForm input[name="template"]').val();
			if(JY.Object.notNull(templateCode)){
				if(JY.Validate.form("createTemplateForm")){
						JY.Model.confirm("请确保您已预览或打印<br>测试过,确认保存吗？",function(){
							JY.Ajax.doRequest("createTemplateForm",jypath +'/scm/print/addTemplate',null,function(data){
							    if(data.res=='1'){					    	
									JY.Model.info(data.resMsg);
							    }else{
							    	JY.Model.error(data.resMsg);
							    }
							});
					});
				}
			}else{
				JY.Model.error("模版代码为空！");
			}
		});
		
		/**
		 * 监控下拉框变化
		 */
		$("#templateSelector").on('change',function(){
			var selector = $(this);
			selector.val(this.value);
		});
		
	});

	var LODOP ;
	/**
	 * 开启创建模版设计的程序
	 */
	function designTemplate(){
		var unit = "mm",str="";
		var width = $("#tempWidth").val();
		var height = $("#tempHeight").val();
		var datasource = getDatasource();
		var template={
				"K_NAME":'LODOP.ADD_PRINT_TEXTA("K_NAME","2mm","2mm","20mm","4mm","商品名称xxx");',
				"K_MSTONE":'LODOP.ADD_PRINT_TEXTA("K_MSTONE","2mm","2mm","20mm","4mm","D 0.235ct/1");',
				"K_FSF":'LODOP.ADD_PRINT_TEXTA("K_FSF","2mm","2mm","20mm","4mm","FG/SI/FF");',
				"K_SIZE":'LODOP.ADD_PRINT_TEXTA("K_SIZE","2mm","2mm","20mm","4mm","16#");',
				"K_GOLD":'LODOP.ADD_PRINT_TEXTA("K_GOLD","2mm","2mm","20mm","4mm","G 10g");',
				"K_ASTONE":'LODOP.ADD_PRINT_TEXTA("K_ASTONE","2mm","2mm","20mm","4mm","d 0.018ct/3");',
				"K_PRICE":'LODOP.ADD_PRINT_TEXTA("K_PRICE","2mm","2mm","20mm","4mm","￥1000");',
				"K_CERT":'LODOP.ADD_PRINT_TEXTA("K_CERT","2mm","2mm","25mm","4mm","证书号:1234567");',
				"K_BARCODE":'LODOP.ADD_PRINT_BARCODEA("K_BARCODE","4mm","4mm","23.71mm","3.92mm","128B","JY0CN00001");LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);LODOP.ADD_PRINT_TEXTA("K_CODENUM","4mm","4mm","25mm","5mm","JY0CN00001");',
				"K_NORM":'LODOP.ADD_PRINT_TEXT("4mm","3mm","20mm","5mm","执行标准QB/T 2062\\r\\n Q/JY 001")',
				"K_PASS":'LODOP.ADD_PRINT_TEXT("4mm","4mm","20mm","5mm","合格品");',
				"K_WAGESE":'LODOP.ADD_PRINT_TEXTA("K_WAGESE","2mm","4mm","20mm","5mm","￥99.0");'
		}
		if(width=="" || height==""){
			alert("请指定模版宽、高");
			return;
		}
		if(datasource.length==0){
			alert("请选择数据源！");
			return;
		}
		LODOP = getLodop();
		LODOP.PRINT_INITA("0mm","0mm",width+unit,height+unit,"打印标签");
		LODOP.SET_PRINT_PAGESIZE(1,width+unit,height+unit,"");
		//根据所选择的数据源加载对应的code
		for(var i=0;i<datasource.length;i++){
			str += template[datasource[i]] +'\r\n';
		}
		eval(str);//执行代码
		LODOP.PRINT_DESIGN();//启用设计模版程序
	}
    
    /**
     * 获取程序代码
     */
    function getProgram() {	
		LODOP=getLodop(document.getElementById('MYLODOP')); 
		if (LODOP.CVERSION){
			LODOP.On_Return=function(TaskID,Value){
				document.getElementById('mycode').value=Value;
				document.getElementById('saveTemplate').setAttribute("template-data",assembleTemplate(Value));
			};	
		}
		document.getElementById('mycode').value=LODOP.GET_VALUE("ProgramCodes",0);
		//to convert the array to string, and save code of the current template.
		document.getElementById('saveTemplate').setAttribute("template-data",assembleTemplate(LODOP.GET_VALUE("ProgramCodes",0)));
		$("#mypreview").show();
		$("#saveTemplate").show();
	}
    /**
     * 预览调整后模版
     */
	function prn_Preview() {
		LODOP=getLodop(document.getElementById('MYLODOP')); 
		eval(document.getElementById('mycode').value); 
		LODOP.PREVIEW();
	}
	/**
	 * 组装模版code
	 */
	function assembleTemplate(target){
		var arr = target.split('\r\n');
		for(var i = 0 ;i<arr.length-1;i++){
			var ele = arr[i];
			var _key = ele.substring(ele.indexOf('(')+1,ele.indexOf(','));
			var _value = ele.substring(ele.lastIndexOf(',')+1,ele.indexOf(')'))
			switch(_key)
			{
			case '"K_NAME"':
				arr[i] = arr[i].replace(_value,"data.name");
			  break;
			case '"K_MSTONE"':
				arr[i] = arr[i].replace(_value,'"D "+data.stoneWeightM+"ct/"+data.stoneCountM');
				break;
			case '"K_FSF"':
				arr[i] = arr[i].replace(_value,'data.color+"/"+data.clarity+"/"+data.cut');
			  break;
			case '"K_SIZE"':
				arr[i] = arr[i].replace(_value,'data.circel+"#"');
				break;
			case '"K_GOLD"':
				arr[i] = arr[i].replace(_value,'"G "+data.goldWeight+"g"');
				break;
			case '"K_ASTONE"':
				arr[i] = arr[i].replace(_value,'"d "+data.stoneWeightA+"ct/"+data.stoneCountA');
				break;
			case '"K_PRICE"':
				arr[i] = arr[i].replace(_value,'"￥"+data.price+"元"');
				break;
			case '"K_CERT"':
				arr[i] = arr[i].replace(_value,'"证书号："+data.cerNum');
				break;
			case '"K_BARCODE"':
				arr[i] = arr[i].replace(_value,'data.code');
				break;
			case '"K_CODENUM"':
				arr[i] = arr[i].replace(_value,'data.code');
				break;
			case '"K_WAGESE"':
				arr[i] = arr[i].replace(_value,'"￥"+data.wageSe+"元"');
				break;
			}
			
		}
		return arr.join("\r\n");
	}
	/**
	 * 调整模版：打开程序界面
	 */
	function designSelectedTemplate(){
		var data={
				name:"金Au750钻石耳钉",
				stoneWeightM:"0.235",
				stoneCountM:"1",
				stoneWeightA:"0.018",
				stoneCountA:"6",
				color:"FG",
				clarity:"SI",
				cut:"FF",
				goldWeight:"10",
				circel:"16",
				price:"2630",
				cerNum:"S-ZH30747",
				code:"JY0CN00112",
				wageSe:"100.00"
		}
		var templateId = $("#templateSelector").val();
		if(!JY.Object.notNull(templateId)){			
			$("#templateSelector").tips({
                side: 1,
                msg: "请选择打印模版！",
                bg: '#FF2D2D',
                time: 1
            });
		}else{
			JY.Ajax.doRequest(null,jypath +'/scm/print/template/'+templateId,null,function(result){
				LODOP = getLodop();
				eval(result.obj.template);
				LODOP.PRINT_DESIGN();
			});
			
		}
	}
	/**
	 * 获取页面上选中的数据源
	 */
	function getDatasource(){
		var len = $("input[name='datasource']:checked").length;
		var chk =[];
		for(var i=0;i<len;i++){
			var obj = $("input[name='datasource']:checked")[i];
			chk.push(obj.value);
		}
		return chk;
	}
	
	/**
	 * 创建模版时：获取设计的模版code
	 */
	function getProgramCode() {	
		LODOP=getLodop(document.getElementById('MYLODOP')); 
		if (LODOP.CVERSION){
			LODOP.On_Return=function(TaskID,Value){
				document.getElementById('designCode').value=Value;
				document.getElementById('template').value = assembleTemplate(Value);
			};	
		}
		document.getElementById('designCode').value=LODOP.GET_VALUE("ProgramCodes",0);
		//to convert the array to string, and save code of the current template.
		document.getElementById('template').value = assembleTemplate(LODOP.GET_VALUE("ProgramCodes",0));
		$("#createTemplateForm").show();
		$("#previewDesign").show();
		
	}
    /**
     * 创建模版：预览设计的模版
     */
	function previewDesign() {
		LODOP=getLodop(document.getElementById('MYLODOP')); 
		eval(document.getElementById('designCode').value); 
		LODOP.PREVIEW();
	}
	