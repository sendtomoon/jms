/*
 * JY工具组件
 */
//var JY = JY || {Object:{},Dict:{},Page:{},Tags:{},Model:{},Validate:{},Date:{},Url:{},Ajax:{}};
var JY = JY || {};
$(function() {
    //复选框
    $('table th input:checkbox').on('click', function() {
        var that = this;
        $(this).closest('table').find('tr > td:first-child input:checkbox').each(function() {
            this.checked = that.checked;
            $(this).closest('tr').toggleClass('selected');
        });
    });
    //class是isValidCheckbox的选择框Yes或No，value设为1或0
    $(".isValidCheckbox [sh-isValid]").bind('click', function() {
        $(".isValidCheckbox [hi-isValid]").val((this.checked) ? "1" : "0");
    });
    $(".isValidCheckbox1 [sh-isValid]").bind('click', function() {
        $(".isValidCheckbox1 [hi-isValid]").val((this.checked) ? "1" : "0");
    });
    $(".isValidCheckbox2 [sh-isValid]").bind('click', function() {
        $(".isValidCheckbox2 [hi-isValid]").val((this.checked) ? "1" : "0");
    });
    $(".isValidCheckbox3 [sh-isValid]").bind('click', function() {
        $(".isValidCheckbox3 [hi-isValid]").val((this.checked) ? "1" : "0");
    });
    //改写dialog是title可以使用html
    $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
        _title: function(title) {
            var $title = this.options.title || '&nbsp;';
            if (("title_html"in this.options) && (this.options.title_html == true))
                title.html($title);
            else
                title.text($title);
        }
    }));
});
JY = {
    Object: {
        notNull: function(obj) {
            //判断某对象不为空..返回true 否则 false
            if (obj === null)
                return false;
            else if (obj === undefined)
                return false;
            else if (obj === "undefined")
                return false;
            else if (obj === "")
                return false;
            else if (obj === "[]")
                return false;
            else if (obj === "{}")
                return false;
            else
                return true;
        },
        notEmpty: function(obj) {
            //判断某对象不为空..返回obj 否则 ""
            if (obj === null)
                return "";
            else if (obj === undefined)
                return "";
            else if (obj === "undefined")
                return "";
            else if (obj === "")
                return "";
            else if (obj === "[]")
                return "";
            else if (obj === "{}")
                return "";
            else if (obj === "null")
            	return "";
            else
                return obj;
        },
        notNumber:function(obj){
        	if (obj === null)
                return 0;
            else if (obj === undefined)
                return 0;
            else if (obj === "undefined")
                return 0;
            else if (obj === "")
                return 0;
            else if (obj === "[]")
                return 0;
            else if (obj === "{}")
                return 0;
            else if (obj === "null")
             	return 0;
            else
                return obj;
        },
        serialize: function(form) {
            var o = {};
            $.each(form.serializeArray(), function(index) {
                if (o[this['name']]) {
                    o[this['name']] = o[this['name']] + "," + this['value'];
                } else {
                    o[this['name']] = this['value'];
                }
            });
            return o;
        },
        formSerialize: function(form, exceptAttr) {
            var o = {};
            $.each(JY.Tools.switchForm2Array(form, exceptAttr), function(index) {
                if (o[this['name']]) {
                    o[this['name']] = o[this['name']] + "," + this['value'];
                } else {
                    o[this['name']] = this['value'];
                }
            });
            return o;
        },
        //组合变量传递keys,values,types形式// 转换JSON为字符串
        comVar: function(variables) {
            var keys = ""
              , values = ""
              , types = ""
              , vars = {};
            if (variables) {
                $.each(variables, function() {
                    if (keys != "") {
                        keys += ",";
                        values += ",";
                        types += ",";
                    }
                    keys += this.key;
                    values += this.value;
                    types += this.type;
                });
            }
            vars = {
                keys: keys,
                values: values,
                types: types
            };
            return vars;
        }
    },
    Dict: {
        //ids 对应id值(多个逗号分隔).keys 对应key值(多个逗号分隔).type(可选)1.请选择，2.自定义数组。默认不填.dfstr (可选)自定义数组
        setSelect: function(ids, keys, type, dfstr) {
        	var _fn = arguments[4];
            $.ajax({
                type: 'POST',
                url: jypath + '/backstage/dataDict/getDictSelect',
                data: {
                    ids: ids,
                    keys: keys
                },
                dataType: 'json',
                success: function(data, textStatus) {
                    if (data.res == 1) {
                        var map = data.obj;
                        var idss = ids.split(",");
                        var opts = ""
                          , name = "";
                        if (type == 1) {
                            for (var i = 0; i < idss.length; i++) {
                                name = map[idss[i]].name;
                                opts = "<option value=''>请选择</option>";
                                $.each(map[idss[i]].items, function(n, v) {
                                    opts += "<option value='" + v.value + "'>" + v.name + "</option>";
                                });
                                $("#" + idss[i] + " select").append(opts);
                                $("#" + idss[i]).trigger("liszt:updated");
                            }
                            ;
                        } else if (type == 2) {
                            var dfstrs = dfstr.split(",");
                            for (var i = 0; i < idss.length; i++) {
                                name = map[idss[i]].name;
                                $("#" + idss[i] + " label").html(name);
                                opts = "<option value=''>" + dfstrs + "</option>";
                                $.each(map[idss[i]].items, function(n, v) {
                                    opts += "<option value='" + v.value + "'>" + v.name + "</option>";
                                });
                                $("#" + idss[i] + " select").append(opts);
                            }
                            ;
                        } else {
                            for (var i = 0; i < idss.length; i++) {
                                var name = map[idss[i]].name;
                                $("#" + idss[i] + " label").html(name);
                                opts = "";
                                $.each(map[idss[i]].items, function(n, v) {
                                    opts += "<option value='" + v.value + "'>" + v.name + "</option>";
                                });
                                $("#" + idss[i] + " select").append(opts);
                            }
                        }
                    }
                    if(typeof(_fn) == 'function'){
                    	$("#" + ids + " select").change(function() {
                    		_fn.call(this);
                        });
                    }
                    //适应手机
                    if ("ontouchend"in document) {
                        $(".chosen-select").removeClass("chosen-select");
                    }//下拉框样式
                    else {
                        $(".chosen-select").chosen();
                        $(".chosen-select-deselect").chosen({
                            allow_single_deselect: true
                        });
                    }
                }
            });
        },
        selectRender: function(url, domId, type, str, fn) {
            $.ajax({
                type: 'POST',
                url: url,
                data: {},
                dataType: 'json',
                success: function(data, textStatus) {
                    var opts = ""
                      , name = "";
                    if (type == 1) {
                        opts = "<option value=''>请选择</option>";
                        $.each(data, function(n, v) {
                            opts += "<option value='" + v.key + "'>" + v.value + "</option>";
                        });
                        $("#" + domId + " select").append(opts);
                        $("#" + domId).trigger("liszt:updated");
                    } else if (type == 2) {
                        opts = "<option value=''>" + str + "</option>";
                        $.each(data, function(n, v) {
                            opts += "<option value='" + v.key + "'>" + v.value + "</option>";
                        });
                        $("#" + domId + " select").append(opts);
                    } else {
                        opts = "";
                        $.each(data, function(n, v) {
                            opts += "<option value='" + v.key + "'>" + v.value + "</option>";
                        });
                        $("#" + domId + " select").append(opts);
                    }
                    if (typeof (fn) == 'function') {
                        //回调
                        $("#" + domId + " select").change(function() {
                            fn.call(this);
                        })
                    }
                }
            });
        },
        selectData:function(param,param_2,param_3,code,code_2,code_3){//三级下拉联动 param,param_2,param_3传入省市县selectID, 查询修改时传入code,code_2,code_3省市区县id
			 $("#"+param).empty(); 
			   $("#"+param_2).empty(); 
			   $("#"+param_3).empty(); 
			   $.ajax({type:'POST',url:jypath+'/scm/franchisee/get',data:{},dataType:'json',success:function(data){
				   var obj=data.obj;op="<option value=''>请选择省份</option>"; $.each(obj,function(n,v){op+="<option value='"+v.id+"'>"+v.paramValue+"</option>";
				   $("#"+param_2).empty();
				   });
				   var $select = $("#"+param);
				   $select.append(op);
				   $select.val(JY.Object.notEmpty(code));
				   if(code!=null && code!=""){
					   //查询市
					   $.ajax({type:'POST',url:jypath+'/scm/franchisee/findByPid',data:{id:code},dataType:'json',success:function(data2){
						   var obj2=data2.obj;op2="<option value=''>请选择城市</option>"; $.each(obj2,function(n2,v2){op2+="<option value='"+v2.id+"'>"+v2.paramValue+"</option>";});
						   $("#"+param_2).append(op2);
						   $("#"+param_2).val(JY.Object.notEmpty(code_2));
					   	}});
					   if(code_2!=null && code_2!=""){
						   //查询区县
						   $.ajax({type:'POST',url:jypath+'/scm/franchisee/findByPid',data:{id:code_2},dataType:'json',success:function(data3){
							   $("#"+param_3).empty(); 
							   var obj3=data3.obj;op3="<option value=''>请选择县区</option>"; $.each(obj3,function(n3,v3){op3+="<option value='"+v3.id+"'>"+v3.paramValue+"</option>";});
							   $("#"+param_3).append(op3);
							   $("#"+param_3).val(JY.Object.notEmpty(code_3));
						   	}});
					   }
				   }
				   //根据省ID触发change事件查询市
				   $select.change(function(){
					   var options=$("#"+param+" option:selected");
					   $("#"+param_2).empty(); 
					   $("#"+param_3).empty(); 
					   $.ajax({type:'POST',url:jypath+'/scm/franchisee/findByPid',data:{id:options.val()},dataType:'json',success:function(data2){
						   $("#"+param_2).empty();
						   var obj2=data2.obj;op2="<option value=''>请选择城市</option>"; $.each(obj2,function(n2,v2){op2+="<option value='"+v2.id+"'>"+v2.paramValue+"</option>";});
						   $("#"+param_2).append(op2);
						   
					   	}});
					  
					   });
				 //根据市ID触发change事件查询市
				   $("#"+param_2).change(function(){
					   $("#"+param_3).empty(); 
					   var options3=$("#"+param_2+" option:selected");
					   $.ajax({type:'POST',url:jypath+'/scm/franchisee/findByPid',data:{id:options3.val()},dataType:'json',success:function(data3){
						   $("#"+param_3).empty(); 
						   var obj3=data3.obj;op3="<option value=''>请选择县区</option>"; $.each(obj3,function(n3,v3){op3+="<option value='"+v3.id+"'>"+v3.paramValue+"</option>";});
						   $("#"+param_3).append(op3);
					   	}});
				   	});
			   }});
	 }
    },
    Page: {
        //跳转分页
        jump: function(formId, num, JpFun) {
            $("#" + formId + " .pageNum").val(num);
            eval(JpFun + "()");
        },
        //设置分页单个显示数量
        setSize: function(formId, size, JpFun) {
            $("#" + formId + " .pageNum").val(1);
            $("#" + formId + " .pageSize").val(size);
            eval(JpFun + "()");
        },
        /*自定义跳转分页*/
        jumpCustom: function(formId, pageId, leng, JpFun) {
            var choseJPage = $("#" + pageId + " .choseJPage").val();
            if (typeof (choseJPage) == "undefined")
                return;
            else if (choseJPage == 0)
                choseJPage = 1;
            else if (choseJPage > leng)
                choseJPage = leng;
            $("#" + formId + " .pageNum").val(choseJPage);
            eval(JpFun + "()");
        },
        /*设置分页方法,formId 分页参数Form的Id,pageId 分页位置Id,pagesize 分页显示数量,pagenum 页码,totalCount 数据总数,fun 获得数据方法名*/
        setPage: function(formId, pageId, pagesize, pagenum, totalCount, fun) {
            if (totalCount > 0) {
                var pageul = $("#" + pageId + " ul")
                  , html = "";
                pageul.empty();
                var leng = parseInt((totalCount - 1) / pagesize) + 1;
                if (pagenum - 1 >= 1) {
                    html += "<li class='prev'><a onclick='JY.Page.jump(&apos;" + formId + "&apos;,1,&apos;" + fun + "&apos;)' href='#'>首页</a></li>";
                    html += "<li class='prev'><a onclick='JY.Page.jump(&apos;" + formId + "&apos;," + (pagenum - 1) + ",&apos;" + fun + "&apos;)' href='#'>上页</a></li>";
                } else {
                    html += "<li class='prev disabled'><a href='##'>首页</a></li>";
                    html += "<li class='prev disabled'><a href='##'>上页</a></li>";
                }
                var all = leng > 2 ? 2 : leng;
                //总显示个数,正常为all+1条,现在设2，显示为3条
                var start = 1;
                //all/2取整后的页数减去当前页数，判断是否为大于0
                var before = pagenum - parseInt(all / 2);
                if (before > 1)
                    start = before;
                var end = start + all;
                if (end > leng) {
                    end = leng;
                    start = leng > all ? (leng - all) : 1;
                }
                //现在设2,和显示对应
                if (pagenum > 2 && leng > 3) {
                    html += "<li class='' ><a href='#' onclick='JY.Page.jump(&apos;" + formId + "&apos;," + (pagenum - 2) + ",&apos;" + fun + "&apos;)' >..</a></li>";
                }
                for (var ii = start; ii <= end; ii++) {
                    var page = (parseInt(ii));
                    if (pagenum == page) {
                        html += "<li class='active' ><a href='#'>" + page + "</a></li>";
                    } else {
                        html += "<li><a onclick='JY.Page.jump(&apos;" + formId + "&apos;," + ii + ",&apos;" + fun + "&apos;)' href='#'>" + page + "</a></li>";
                    }
                }
                if (pagenum <= (leng - 2) && leng > 3) {
                    html += "<li class='' ><a href='#' onclick='JY.Page.jump(&apos;" + formId + "&apos;," + (pagenum + 2) + ",&apos;" + fun + "&apos;)' >..</a></li>";
                }
                if (pagenum + 1 <= leng) {
                    html += "<li class='next'><a onclick='JY.Page.jump(&apos;" + formId + "&apos;," + (pagenum + 1) + ",&apos;" + fun + "&apos;)' href='#'>下页</a></li>";
                    html += "<li class='next'><a onclick='JY.Page.jump(&apos;" + formId + "&apos;," + leng + ",&apos;" + fun + "&apos;)' href='#'>尾页</a></li>";
                } else {
                    html += "<li class='next disabled'><a href='##'>下页</a></li>";
                    html += "<li class='next disabled'><a href='##'>尾页</i></a></li>";
                }
                html += "<li class='disabled'><a href='##'>共" + leng + "页<font color='red'>" + totalCount + "</font>条</a></li>";
                html += "<li class='next'><input onkeyup='this.value=this.value.replace(/\D/g,&apos;&apos;)' type='number' min='1' max='" + leng + "'  placeholder='页码' class='choseJPage' ></li>";
                html += "<li ><a class='btn btn-mini btn-success' onclick='JY.Page.jumpCustom(&apos;" + formId + "&apos;,&apos;" + pageId + "&apos;," + leng + ",&apos;" + fun + "&apos;);' href='##'>跳转</a></li>";
                html += "<li class='disabled'><select onchange='JY.Page.setSize(&apos;" + formId + "&apos;,this.value,&apos;" + fun + "&apos;)' style='width:55px;float:left;' title='显示条数'>" + "<option value='5'  " + ((pagesize == 5) ? "selected='selected'" : "") + " >5</option>" + "<option value='10' " + ((pagesize == 10) ? "selected='selected'" : "") + " >10</option>" + "<option value='15' " + ((pagesize == 15) ? "selected='selected'" : "") + " >15</option>" + "</li>";
                pageul.append(html);
            }
        },
        /*简化版,设置分页方法,formId 分页参数Form的Id,pageId 分页位置Id,pagesize 分页显示数量,pagenum 页码,totalCount 数据总数,fun 获得数据方法名*/
        setSimPage: function(formId, pageId, pagesize, pagenum, totalCount, fun) {
            if (totalCount > 0) {
                var pageul = $("#" + pageId + " ul")
                  , html = "";
                pageul.empty();
                var leng = parseInt((totalCount - 1) / pagesize) + 1;
                if (pagenum - 1 >= 1) {
                    html += "<li class='prev'><a onclick='JY.Page.jump(&apos;" + formId + "&apos;,1,&apos;" + fun + "&apos;)' href='#'>首</a></li>";
                } else {
                    html += "<li class='prev disabled'><a href='##'>首</a></li>";
                }
                var all = leng > 2 ? 2 : leng;
                //总显示个数,正常为all+1条,现在设2，显示为3条
                var start = 1;
                //all/2取整后的页数减去当前页数，判断是否为大于0
                var before = pagenum - parseInt(all / 2);
                if (before > 1)
                    start = before;
                var end = start + all;
                if (end > leng) {
                    end = leng;
                    start = leng > all ? (leng - all) : 1;
                }
                //现在设2,和显示对应
                if (pagenum > 2 && leng > 3) {
                    html += "<li class='' ><a href='#' onclick='JY.Page.jump(&apos;" + formId + "&apos;," + (pagenum - 2) + ",&apos;" + fun + "&apos;)' >..</a></li>";
                }
                for (var ii = start; ii <= end; ii++) {
                    var page = (parseInt(ii));
                    if (pagenum == page) {
                        html += "<li class='active' ><a href='#'>" + page + "</a></li>";
                    } else {
                        html += "<li><a onclick='JY.Page.jump(&apos;" + formId + "&apos;," + ii + ",&apos;" + fun + "&apos;)' href='#'>" + page + "</a></li>";
                    }
                }
                if (pagenum <= (leng - 2) && leng > 3) {
                    html += "<li class='' ><a href='#' onclick='JY.Page.jump(&apos;" + formId + "&apos;," + (pagenum + 2) + ",&apos;" + fun + "&apos;)' >..</a></li>";
                }
                if (pagenum + 1 <= leng) {
                    html += "<li class='next'><a onclick='JY.Page.jump(&apos;" + formId + "&apos;," + leng + ",&apos;" + fun + "&apos;)' href='#'>尾</a></li>";
                } else {
                    html += "<li class='next disabled'><a href='##'>尾</i></a></li>";
                }
                html += "<li class='disabled'><a href='##'>共" + leng + "页</a></li>";
                pageul.append(html);
            }
        }
    },
    Tags: {
        //设置按钮用的方法,id 这行的id,pBtn 按钮组
        cleanForm: function(formId) {
            $("#" + formId + " input[type$='text']").val("");
            $("#" + formId + " textarea").val("");
            $("#" + formId + " select").val("");
        },
        setFunction: function(id, pBtn) {
            var h = "";
            if (pBtn != null && pBtn.length > 0) {
                h += "<td class='center'>";
                h += "<div class='dropdown'>";
                h += "<button class='dropbtn'>更多</button><div class='dropdown-content'>";
                for (var i = 0; i < pBtn.length; i++) {
                    h += "<a href='#' title='" + JY.Object.notEmpty(pBtn[i].name) + "' onclick='" + JY.Object.notEmpty(pBtn[i].btnFun) + "(&apos;" + id + "&apos;)'  >" + JY.Object.notEmpty(pBtn[i].name) + " </a>";
                }
                h += "</div></div>";
                h += "</td>";
            } else {
                h += "<td></td>";
            }
            return h;
        },
        setFunction1: function(id, pBtn,str) {
            var h = "";
            if (pBtn != null && pBtn.length > 0) {
                h += "<td class='center'>";
                h += "<div class='dropdown'>";
                h += "<button class='dropbtn'>更多</button><div class='dropdown-content'>";
                for (var i = 0; i < pBtn.length; i++) {
                	if(str.indexOf(pBtn[i].btnFun+",")==-1){
                		 h += "<a href='#' title='" + JY.Object.notEmpty(pBtn[i].name) + "' onclick='" + JY.Object.notEmpty(pBtn[i].btnFun) + "(&apos;" + id + "&apos;)'  >" + JY.Object.notEmpty(pBtn[i].name) + " </a>";
                	}
                }
                h += "</div></div>";
                h += "</td>";
            } else {
                h += "<td></td>";
            }
            return h;
        },
        setStatus: function(status){
        	var h="";
        	 if(status=="1"){
    			 h+="<td class='center hidden-480'><span class='label label-sm label-success'>有效</span></td>";
    		 }else{
    			 h+="<td class='center hidden-480'><span class='label label-sm arrowed-in'>无效</span></td>";
    		 } 
        	 return h;
        },
        setStatus1: function(status){
        	var h="";
        	 if(status=="1"){
    			 h+="<span class='label label-sm label-success'>有效</span>";
    		 }else{
    			 h+="<span class='label label-sm arrowed-in'>无效</span>";
    		 } 
        	 return h;
        },
        /*class是isValidCheckbox的选择框Yes或No，value设为1或0
			 *formId form的Id
			 */
        isValid: function(formId, val) {
            $("#" + formId + " .isValidCheckbox [hi-isValid]").val(val);
            if (val == 1) {
                $("#" + formId + " .isValidCheckbox [sh-isValid]").prop("checked", true);
            } else {
                $("#" + formId + " .isValidCheckbox [sh-isValid]").prop("checked", false);
            }
        },
        isValid1: function(formId, val) {
            $("#" + formId + " .isValidCheckbox1 [hi-isValid]").val(val);
            if (val == 1) {
                $("#" + formId + " .isValidCheckbox1 [sh-isValid]").prop("checked", true);
            } else {
                $("#" + formId + " .isValidCheckbox1 [sh-isValid]").prop("checked", false);
            }
        },
        isValid2: function(formId, val) {
            $("#" + formId + " .isValidCheckbox2 [hi-isValid]").val(val);
            if (val == 1) {
                $("#" + formId + " .isValidCheckbox2 [sh-isValid]").prop("checked", true);
            } else {
                $("#" + formId + " .isValidCheckbox2 [sh-isValid]").prop("checked", false);
            }
        },
        isValid3: function(formId, val) {
            $("#" + formId + " .isValidCheckbox3 [hi-isValid]").val(val);
            if (val == 1) {
                $("#" + formId + " .isValidCheckbox3 [sh-isValid]").prop("checked", true);
            } else {
                $("#" + formId + " .isValidCheckbox3 [sh-isValid]").prop("checked", false);
            }
        }
    },
    Model: {
        //loading框
        loading: function(Str) {
            if (JY.Object.notNull(Str))
                $("#jyLoadingStr").empty().append(Str);
            else
                $("#jyLoadingStr").empty().append("数据读取中...");
            $("#jyLoading").removeClass('hide').dialog({
                dialogClass: "loading-no-close",
                minHeight: 50,
                resizable: false,
                modal: true,
                show: {
                    effect: "fade"
                },
                hide: {
                    effect: "fade"
                }
            });
        },
        //关闭loading框
        loadingClose: function() {
            $("#jyLoading").dialog("close");
        },
        //Str 询问语句可以是html格式,fn  方法
        confirm: function(Str, fn,cfn,flag) {
            $("#jyConfirmStr").empty();
            if (JY.Object.notNull(Str))
                $("#jyConfirmStr").append(Str);
            else
                $("#jyConfirmStr").append("确认吗？");
            $("#jyConfirm").removeClass('hide').dialog({
                resizable: false,
                modal: true,
                title: "<div class='widget-header'><h4 class='font-bold'>询问</h4></div>",
                title_html: true,
                show: {
                    effect: "explode",
                    pieces: 9
                },
                hide: {
                    effect: "explode",
                    pieces: 9
                },
                buttons: [{
                    html: "<i class='icon-ok bigger-110'></i>&nbsp;确认",
                    "class": "btn btn-primary btn-xs",
                    click: function() {
                    	if(!flag){
                    		$(this).dialog("close");
                    	}
                        if (typeof (fn) == 'function') {
                            fn.call(this);
                        }
                    }
                }, {
                    html: "<i class='icon-remove bigger-110'></i>&nbsp;取消",
                    "class": "btn btn-xs",
                    click: function() {
                    	if (typeof (cfn) == 'function') {
                            cfn.call(this);
                        }
                        $(this).dialog("close");
                    }
                }]
            });
        },
        info: function(Str, fn) {
            if (JY.Object.notNull(Str)) {
                $("#jyInfoStr").empty().append(Str);
                $("#jyInfo").removeClass('hide').dialog({
                    resizable: false,
                    dialogClass: "title-no-close",
                    modal: true,
                    title: "<div class='widget-header'><h4 class='font-bold'>提示</h4></div>",
                    title_html: true,
                    show: {
                        effect: "explode",
                        pieces: 9
                    },
                    hide: {
                        effect: "explode",
                        pieces: 9
                    },
                    buttons: [{
                        html: "<i class='icon-ok bigger-110'></i>&nbsp;确认",
                        "class": "btn btn-primary btn-xs",
                        click: function() {
                            $(this).dialog("close");
                            if (typeof (fn) == 'function') {
                                fn.call(this);
                            }
                        }
                    }]
                });
            }
        },
        //Str 语句可以是html格式（如：<span>保存成功</span>）,fn 方法
        error: function(Str) {
            if (JY.Object.notNull(Str)) {
                $("#jyErrorStr").empty().append(Str);
                $("#jyError").removeClass('hide').dialog({
                    resizable: false,
                    dialogClass: "title-no-close",
                    modal: true,
                    title: "<div class='widget-header'><h4 class='font-bold red'>错误</h4></div>",
                    title_html: true,
                    show: {
                    	effect: "explode",
                        pieces: 9
                    },
                    hide: {
                        effect: "explode"
                    },
                    buttons: [{
                        html: "<i class='icon-ok bigger-110'></i>&nbsp;确认",
                        "class": "btn btn-primary btn-xs",
                        click: function() {
                            $(this).dialog("close");
                        }
                    }]
                });
            }
        },
        //divId,fn 方法
        check: function(id, title, fn) {
            $("#" + id).removeClass('hide').dialog({
                resizable: false,
                modal: true,
                title: "<div class='widget-header'><h4 class='smaller'>" + (JY.Object.notNull(title) ? title : "查看") + "</h4></div>",
                title_html: true,
                buttons: [{
                    html: "<i class='icon-remove bigger-110'></i>&nbsp;取消",
                    "class": "btn btn-xs",
                    click: function() {
                        $(this).dialog("close");
                        if (typeof (fn) == 'function') {
                            fn.call(this);
                        }
                    }
                }]
            });
        },
        edit: function(id, title, savefn, cancelfn) {
            $("#" + id).removeClass('hide').dialog({
                resizable: false,
                modal: true,
                title: "<div class='widget-header'><h4 class='smaller'>" + (JY.Object.notNull(title) ? title : "修改") + "</h4></div>",
                title_html: true,
                buttons: [{
                    html: "<i class='icon-ok bigger-110'></i>&nbsp;保存",
                    "class": "btn btn-primary btn-xs",
                    click: function() {
                        if (typeof (savefn) == 'function') {
                            savefn.call(this);
                        }
                    }
                }, {
                    html: "<i class='icon-remove bigger-110'></i>&nbsp;取消",
                    "class": "btn btn-xs",
                    click: function() {
                        $(this).dialog("close");
                        if (typeof (cancelfn) == 'function') {
                            cancelfn.call(this);
                        }
                    }
                }]
            });
        },
        //审核
        audit: function(id, title, savefn, rejectfn, cancelfn) {
            $("#" + id).removeClass('hide').dialog({
                resizable: false,
                modal: true,
                title: "<div class='widget-header'><h4 class='smaller'>" + (JY.Object.notNull(title) ? title : "审核") + "</h4></div>",
                title_html: true,
                buttons: [{
                    html: "<i class='icon-ok bigger-110'></i>&nbsp;同意",
                    "class": "btn btn-primary btn-xs",
                    click: function() {
                        if (typeof (savefn) == 'function') {
                            savefn.call(this);
                        }
                    }
                }, {
                    html: "<i class='icon-undo bigger-110'></i>&nbsp;驳回",
                    "class": "btn btn-danger btn-xs",
                    click: function() {
                        if (typeof (rejectfn) == 'function') {
                            rejectfn.call(this);
                        }
                    }
                }, {
                    html: "<i class='icon-remove bigger-110'></i>&nbsp;取消",
                    "class": "btn btn-xs",
                    click: function() {
                        $(this).dialog("close");
                        if (typeof (cancelfn) == 'function') {
                            cancelfn.call(this);
                        }
                    }
                }]
            });
        },
        //调整
        adjust: function(id, title, againfn, abandonfn, cancelfn) {
            $("#" + id).removeClass('hide').dialog({
                resizable: false,
                modal: true,
                title: "<div class='widget-header'><h4 class='smaller'>" + (JY.Object.notNull(title) ? title : "调整") + "</h4></div>",
                title_html: true,
                buttons: [{
                    html: "<i class='icon-ok bigger-110'></i>&nbsp;提交",
                    "class": "btn btn-primary btn-xs",
                    click: function() {
                        if (typeof (againfn) == 'function') {
                            againfn.call(this);
                        }
                    }
                }, {
                    html: "<i class='icon-flag-alt bigger-110'></i>&nbsp;放弃",
                    "class": "btn btn-danger btn-xs",
                    click: function() {
                        if (typeof (abandonfn) == 'function') {
                            abandonfn.call(this);
                        }
                    }
                }, {
                    html: "<i class='icon-remove bigger-110'></i>&nbsp;取消",
                    "class": "btn btn-xs",
                    click: function() {
                        $(this).dialog("close");
                        if (typeof (cancelfn) == 'function') {
                            cancelfn.call(this);
                        }
                    }
                }]
            });
        }
    },
    Validate: {
        /**modify by lisongbai**/
        //判断是否是英文数字,是返回true，不是返回false
        isEnNum: function(str) {
            if (/^[0-9a-zA-Z]+$/.test(str))
                return true;
            return false;
        },
        isEnNum_: function(str) {
            if (/^[0-9a-zA-Z_]+$/.test(str))
                return true;
            return false;
        },
        //判断是否是英文,是返回true，不是返回false
        isEn: function(str) {
            if (/^[A-Za-z]+$/.test(str))
                return true;
            return false;
        },
        //判断是否是中文,是返回true，不是返回false
        isCh: function(str) {
            if (/[\u4e00-\u9fa5]/.test(str))
                return true;
            return false;
        },
        //判断是否是电子邮箱,是返回true，不是返回false
        isEmail: function(email) {
            if (/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(email))
                return true;
            return false;
        },
        //  验证手机号码
        isPhone: function(phone) {
            if (/^(((13[0-9]{1})|(15[0-9]{1})|(14[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(phone))
                return true;
            return false;
        },
        //判断是否是日期,是返回true，不是返回false
        isDate: function(date) {
            if (date.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/))
                return true;
            return false;
        },
        //判断是否是日期时间,是返回true，不是返回false
        isDatetime: function(datetime) {
            if (datetime.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/))
                return true;
            return false;
        },
        //判断数值是否在范围内(不包含临界):min 最少值,max 最大值,是返回true，不是返回false
        numrange: function(v, min, max) {
            v = parseInt(v);
            min = parseInt(min);
            max = parseInt(max);
            if ((min < v) && (v < max))
                return true;
            return false;
        },
        //判断数值是否在范围内(包含临界),min 最少值,max 最大值,是返回true，不是返回false
        numrangeth: function(v, min, max) {
            v = parseInt(v);
            min = parseInt(min);
            max = parseInt(max);
            if ((min <= v) && (v <= max))
                return true;
            return false;
        },
        //判断长度是否在范围内,minl 最少值,maxl 最大值,是返回true，不是返回false
        checkLen: function(str, minl, maxl) {
            if (!JY.Object.notNull(minl))
                minl = 0;
            if (!JY.Object.notNull(maxl))
                return;
            var len = parseInt(JY.Tools.getStringBytes(str, JY.Tools.getPageCharset()));
            minl = parseInt(minl);
            maxl = parseInt(maxl);
            if ((minl <= len) && (len <= maxl))
                return true;
            return false;
        },
        //校验手机号规则
        isMobile: function(str) {
            if (/^(1(([35][0-9])|([4][57])|([7][0135678])|[8][0-9]))\d{8}$/.test(str))
                return true;
            return false;
        },
        //注册账号：以字母开头，数字和字母加下划线组合
        checkRegName: function(str) {
            if (/^[a-zA-Z][a-zA-Z0-9_]*$/.test(str))
                return true;
            return false;
        },
        //验证密码:数字、字母、字符至少包含两种，长度在8-16之间 
        checkPwd: function(pwd) {
            if (/^((?=.*\d)(?=.*\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{8,16}$/.test(pwd))
                return true;
            return false;
        },
        isDou: function(dou) {
            if (/^0$|^[1-9]\d{0,15}$|^[1-9]\d{0,15}\.{1}\d{1,4}$|^0\.{1}\d{1,4}$/.test(dou))
                return true;
            return false;
        },
        //表单验证fromId,使用方法 在表单必须使用jyValidate属性
        form: function(fromId, side) {
            var res = true;
            side = JY.Object.notNull(side) ? side : 1;
            $('#' + fromId + " input[jyValidate]").each(function() {
                if (res == false)
                    return;
                var that = $(this);
                var jyValidate = $(this).attr("jyValidate").split(",");
                $.each(jyValidate, function(n, v) {
                    if (res == false)
                        return;
                    if (v == 'required') {
                        if (!JY.Object.notNull(that.val())) {
                            that.tips({
                                side: side,
                                msg: "必要字段！",
                                bg: '#FF2D2D',
                                time: 1
                            });
                            that.focus();
                            res = false;
                        }
                    } else if (v == 'email') {
                        if (JY.Object.notNull(that.val())) {
                            if (!JY.Validate.isEmail(that.val())) {
                                that.tips({
                                    side: side,
                                    msg: "电子邮箱不正确！",
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    } else if (v == 'phone') {
                        if (JY.Object.notNull(that.val())) {
                            if (!JY.Validate.isPhone(that.val())) {
                                that.tips({
                                    side: side,
                                    msg: "手机号码不正确！",
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    } else if (v == 'date') {
                        if (JY.Object.notNull(that.val())) {
                            if (!JY.Validate.isDate(that.val())) {
                                that.tips({
                                    side: side,
                                    msg: "日期格式不正确！",
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    } else if (v == 'datetime') {
                        if (JY.Object.notNull(that.val())) {
                            if (!JY.Validate.isDatetime(that.val())) {
                                that.tips({
                                    side: side,
                                    msg: "日期时间格式不正确！",
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    } else if (v == 'numrange') {
                        if (JY.Object.notNull(that.val())) {
                            var min = that.attr("min");
                            var max = that.attr("max");
                            if (!JY.Validate.numrange(that.val(), min, max)) {
                                that.tips({
                                    side: side,
                                    msg: "数字范围：" + min + "~" + max,
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    } else if (v == 'numrangeth') {
                        if (JY.Object.notNull(that.val())) {
                            var min = that.attr("min");
                            var max = that.attr("max");
                            if (!JY.Validate.numrangeth(that.val(), min, max)) {
                                that.tips({
                                    side: side,
                                    msg: "数字范围：" + min + "~" + max,
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    } else if (v == 'en') {
                        if (JY.Object.notNull(that.val())) {
                            if (!JY.Validate.isEn(that.val())) {
                                that.tips({
                                    side: side,
                                    msg: "只能输入英文",
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    } else if (v == 'ch') {
                        if (JY.Object.notNull(that.val())) {
                            if (!JY.Validate.isCh(that.val())) {
                                that.tips({
                                    side: side,
                                    msg: "只能输入中文",
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    } else if (v == 'ennum') {
                        if (JY.Object.notNull(that.val())) {
                            if (!JY.Validate.isEnNum(that.val())) {
                                that.tips({
                                    side: side,
                                    msg: "只能输入英文或数字",
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    } else if (v == 'ennum_') {
                        if (JY.Object.notNull(that.val())) {
                            if (!JY.Validate.isEnNum_(that.val())) {
                                that.tips({
                                    side: side,
                                    msg: "只能输入英文数字或下划线",
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    } else if (v == 'mobile') {
                        if (JY.Object.notNull(that.val())) {
                            if (!JY.Validate.isMobile(that.val())) {
                                that.tips({
                                    side: side,
                                    msg: "手机号码格式不正确",
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    } else if (v == 'reg') {
                        if (JY.Object.notNull(that.val())) {
                            if (!JY.Validate.checkRegName(that.val())) {
                                that.tips({
                                    side: side,
                                    msg: "以字母开头，数字和字母加下划线组合",
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    } else if (v == 'password') {
                        if (JY.Object.notNull(that.val())) {
                            if (!JY.Validate.checkPwd(that.val())) {
                                that.tips({
                                    side: side,
                                    msg: "数字、字母、字符至少包含两种，长度在8-16之间 ",
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    } else if (v == 'lenrange') {
                        if (JY.Object.notNull(that.val())) {
                            var minl = that.attr("minl");
                            if (!JY.Object.notNull(minl))
                                minl = 0;
                            var maxl = that.attr("maxl");
                            if (!JY.Validate.checkLen(that.val(), minl, maxl)) {
                                that.tips({
                                    side: side,
                                    msg: "长度范围：" + minl + "~" + maxl + "字节",
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    } else if (v == 'dou') {
                        if (JY.Object.notNull(that.val())) {
                            if (!JY.Validate.isDou(that.val())) {
                                that.tips({
                                    side: side,
                                    msg: "只能输入数字",
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    }
                    //extend
                });
            });
            $('#' + fromId + " select[jyValidate]").each(function() {
                if (res == false)
                    return;
                var that = $(this);
                var jyValidate = $(this).attr("jyValidate").split(",");
                $.each(jyValidate, function(n, v) {
                    if (res == false)
                        return;
                    if (v == 'required') {
                        if (!JY.Object.notNull(that.val())) {
                            that.tips({
                                side: side,
                                msg: "必要字段！",
                                bg: '#FF2D2D',
                                time: 1
                            });
                            that.focus();
                            res = false;
                        }
                    }
                    //extend
                });
            });
            $('#' + fromId + " textarea[jyValidate]").each(function() {
                if (res == false)
                    return;
                var that = $(this);
                var jyValidate = $(this).attr("jyValidate").split(",");
                $.each(jyValidate, function(n, v) {
                    if (res == false)
                        return;
                    if (v == 'required') {
                        if (!JY.Object.notNull(that.val())) {
                            that.tips({
                                side: side,
                                msg: "必要字段！",
                                bg: '#FF2D2D',
                                time: 1
                            });
                            that.focus();
                            res = false;
                        }
                    } else if (v == 'en') {
                        if (JY.Object.notNull(that.val())) {
                            if (!JY.Validate.isEn(that.val())) {
                                that.tips({
                                    side: side,
                                    msg: "只能输入英文",
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    } else if (v == 'ch') {
                        if (JY.Object.notNull(that.val())) {
                            if (!JY.Validate.isCh(that.val())) {
                                that.tips({
                                    side: side,
                                    msg: "只能输入中文",
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    } else if (v == 'ennum') {
                        if (JY.Object.notNull(that.val())) {
                            if (!JY.Validate.isEnNum(that.val())) {
                                that.tips({
                                    side: side,
                                    msg: "只能输入英文或数字",
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    } else if (v == 'lenrange') {
                        if (JY.Object.notNull(that.val())) {
                            var minl = that.attr("minl");
                            if (!JY.Object.notNull(minl))
                                minl = 0;
                            var maxl = that.attr("maxl");
                            if (!JY.Validate.checkLen(that.val(), minl, maxl)) {
                                that.tips({
                                    side: side,
                                    msg: "长度范围：" + minl + "~" + maxl + "字节",
                                    bg: '#FF2D2D',
                                    time: 1
                                });
                                that.focus();
                                res = false;
                            }
                        }
                    }
                    //extend
                });
            });
            return res;
        },
        limitNum:function(obj){
        	obj.value = obj.value.replace(/[^\d]/g,''); 
        },
        limitAmtNum:function(obj){
        		var t = obj.value.charAt(0); 
        		obj.value = obj.value.replace(/[^\d\.]/g,''); 
        		obj.value = obj.value.replace(/^\./g,''); 
        		obj.value = obj.value.replace(/\.{2,}/g,'.'); 
        		obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');
        		if(t == '-') obj.value = '-'+obj.value;
        },
        limitDouble:function(obj){
        	obj.value = obj.value.replace(/^\D*([\d\.?]*(?:\d{0,2})?).*$/g, '$1'); 
        },
    	limitAmtNumMinusSign:function(obj){
    		var t = obj.value.charAt(0); 
    		obj.value = obj.value.replace(/[^\d\.]/g,''); 
    		obj.value = obj.value.replace(/^\./g,''); 
    		obj.value = obj.value.replace(/\.{2,}/g,'.'); 
    		obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');
        },
    },
    
  
    Date: {
        //时间格式化(默认),time 时间
        Default: function(time) {
            return JY.Object.notNull(time) ? (new Date(time).Format("yyyy-MM-dd  hh:mm:ss")) : " ";
        },
        //时间格式化,time 时间,fmt 格式
        Format: function(time, fmt) {
            return JY.Object.notNull(time) ? (new Date(time).Format(fmt)) : "";
        }
    },
    Url: {
        //获取url中的参数,name 参数名,当不存在返回空字符串
        getParam: function(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            //构造一个含有目标参数的正则表达式对象
            var r = window.location.search.substr(1).match(reg);
            //匹配目标参数
            if (r != null)
                return unescape(r[2]);
            return "";
            //返回参数值
        }
    },
    Ajax: {
        //异步请求, form表单ID,url请求路径,param参数对象,如：{a:'test',b:2},fn回调函数
        doRequest: function(form, url, param, fn) {
            var params = form || param || {};
            if (typeof form == 'string') {
                params = $.extend(param || {}, JY.Object.serialize($("#" + form)), {
                    menu: JY.Url.getParam("menu")
                });
            }
            $.ajax({
                type: 'POST',
                url: url,
                data: params,
                dataType: 'json',
                success: function(data, textStatus) {
                    if (data.res == 1) {
                        if (typeof (fn) == 'function') {
                            fn.call(this, data);
                        }
                    } else {
                        if (JY.Object.notNull(data.resMsg))
                            JY.Model.error(data.resMsg);
                    }
                },
                error: function() {
                    return;
                },
                beforeSend: function() {},
                complete: function() {}
            });
        },
        req: function(form, url, param, fn) {
            var params = form || param || {};
            if (typeof form == 'string') {
                params = $.extend(param || {}, JY.Object.serialize($("#" + form)), {
                    menu: JY.Url.getParam("menu")
                });
            }
            $.ajax({
                type: 'POST',
                url: url,
                data: params,
                dataType: 'json',
                success: function(data, textStatus) {
                    if (typeof (fn) == 'function') {
                        fn.call(this, data);
                    }
                },
                error: function() {
                    return;
                },
                beforeSend: function() {},
                complete: function() {}
            });
        }
    },
    Tools: {
        /**add by lisongbai**/
        getBrowser: function() {
            var oType = "";
            if (navigator.userAgent.indexOf("MSIE") != -1) {
                oType = "IE";
            } else if (navigator.userAgent.indexOf("Firefox") != -1) {
                oType = "FIREFOX";
            } else if (navigator.userAgent.indexOf("WebKit") != -1) {
                oType = "CHROME";
            }
            return oType;
        },
        getPageCharset: function() {
            var charSet = "";
            var oType = JY.Tools.getBrowser();
            switch (oType) {
            case "IE":
                charSet = document.charset;
                break;
            case "FIREFOX":
                charSet = document.characterSet;
                break;
            case "CHROME":
                charSet = document.characterSet;
                break;
            default:
                break;
            }
            return charSet;
        },
        getStringBytes: function(str, charset) {
            var bytesCount = 0;
            for (var i = 0; i < str.length; i++) {
                var ch = str.charAt(i);
                if (/^[\u4e00-\u9fa5]$/.test(ch)) {
                    if (charset == "UTF-8") {
                        bytesCount += 3;
                    } else {
                        bytesCount += 2;
                    }
                } else {
                    bytesCount += 1;
                }
            }
            return bytesCount;
        },
        switchForm2Array: function(form, exceptAttr) {
            var rselectTextarea = /^(?:select|textarea)/i;
            var rinput = /^(?:color|date|datetime|datetime-local|email|hidden|month|number|password|range|search|tel|text|time|url|week)$/i;
            var rCRLF = /\r?\n/g;
            return form.map(function() {
                return this.elements ? jQuery.makeArray(this.elements) : this;
            }).filter(function() {
                return !this.attributes[exceptAttr] && this.name && !this.disabled && (this.checked || rselectTextarea.test(this.nodeName) || rinput.test(this.type));
            }).map(function(i, elem) {
                var val = jQuery(this).val();
                return val == null ? null : jQuery.isArray(val) ? jQuery.map(val, function(val, i) {
                    return {
                        name: elem.name,
                        value: val.replace(rCRLF, "\r\n")
                    };
                }) : {
                    name: elem.name,
                    value: val.replace(rCRLF, "\r\n")
                };
            }).get();
        },
        getExtendsAttrValues: function(formId, attr) {
            var arr = [];
            $('#' + formId + ' input[' + attr + ']').each(function() {
                arr.push({
                    id: $(this).attr("id"),
                    attributeId: $(this).attr(attr),
                    value: $(this).val()
                });
            });
            $('#' + formId + ' select[' + attr + ']').each(function() {
                arr.push({
                    id: $(this).attr("id"),
                    attributeId: $(this).attr(attr),
                    value: $(this).val()
                });
            });
            return arr;
        },
        formReadonly: function(formId, boolean) {
            $.each($("#" + formId)[0], function(index, element) {
                $(element).attr("readonly", boolean);
            });
        },
        formDisabled: function(formId, boolean,fn) {
            $.each($("#" + formId)[0], function(index, element) {
                $(element).attr("disabled", boolean);
            });
            if (typeof (fn) == 'function') {            	
            	fn.call(this);
            }
        },
        populateForm:function(formId, data){  
        	for(var attr in data){  
        	    var formField = $("#" + attr);  
        	    if(!formField[0]){  
        	        formField = $("#" + formId).find("input[name=" + attr + "]");  
        	    }  
        	    if(formField){  
        	        if(formField.attr("type") == "radio"  
        	                || formField.attr("type") == "checkbox"){  
        	            for(var i = 0;i < formField.length;i++){  
        	                if(data[attr] != null){  
        	                    if($(formField.get(i)).attr("value") == data[attr].toString()){  
        	                        $(formField.get(i)).prop("checked", true);  
        	                    }  
        	                }  
        	            }  
        	        }else if(formField.length == 1 &&   
        	                    formField.get(0).nodeName.toLowerCase() == "select"){  
        	            if(data[attr]){  
        	                formField.find("option[value=" + data[attr] + "]").prop('selected', true);  
        	            }else{  
        	                formField.find("option:first").prop("selected", true);  
        	            }  
        	        }else{  
        	            formField.val(data[attr]);  
        	        }  
        	    }  
        	}  
        }
    },
    Module: {
        Field: {
            load: function(formId, busiParams, cols) {
                JY.Object.notNull(cols) ? cols : cols = 1;
                JY.Object.notNull(busiParams.type) ? busiParams.type : busiParams.type = "DEFAULT";
                $("#" + formId + " tr").remove("tr[tid=Generative]");
                JY.Ajax.doRequest(null, jypath + '/backstage/attribute/init', {
                    categoryId: busiParams.cid,
                    type:busiParams.type
                }, function(data) {
                    var html = '';
                    var td = '';
                    $.each(data.obj, function(i, v) {
                        if (i == 0)
                            html += '<tr class="FormData" tid="Generative">'
                        var jyValidTag = ' jyValidate="lenrange';
                        var jyValidTag2 = '';
                        var reqTag = '&ensp;';
                        if (JY.Object.notEmpty(v.nullable) == '0') {
                            jyValidTag += ',required';
                            jyValidTag2 = ' jyValidate="required"';
                            reqTag = '<font color="red">*</font>';
                        }
                        jyValidTag += '" ';
                        if (JY.Object.notEmpty(v.dictId)) {
                            $.ajax({
                                type: 'POST',
                                url: jypath + '/backstage/attribute/getDictItems',
                                data: {
                                    dictId: JY.Object.notEmpty(v.dictId)
                                },
                                dataType: 'json',
                                async: false,
                                success: function(result, textStatus) {
                                    var defaultOpts = '<option value="">选择</option>';
                                    var selectTag = '<select' + jyValidTag2 + ' name="' + v.code + '" attributeId="' + v.id + '">';
                                    $.each(result, function(i, v) {
                                        defaultOpts += '<option value="' + v.value + '">' + v.key + "</option>"
                                    });
                                    var selectHtml = selectTag + defaultOpts + '</select>';
                                    td = '<td class="CaptionTD">' + reqTag + JY.Object.notEmpty(v.name) + "：</td><td class='DataTD'>" + selectHtml + "</td>";
                                }
                            });
                        } else {
                            td = '<td class="CaptionTD">' + reqTag + JY.Object.notEmpty(v.name) + "：</td><td class='DataTD'><input type='text'" + jyValidTag + "attributeId='" + JY.Object.notEmpty(v.id) + "' name='" + JY.Object.notEmpty(v.code) + "' maxl='" + JY.Object.notEmpty(v.length) + "'  > </td>";
                        }
                        if ((i + 1) % cols == 0) {
                            td += '</tr>'
                        }
                        if (i != 0 && i % cols == 0) {
                            td = '<tr class="FormData" tid="Generative">' + td;
                        }
                        html += td;
                    });
                    html += '</table>'
                    $('#' + formId + ' tbody').append(html);
                    if (JY.Object.notNull(busiParams.bid)) {
                        $.ajax({
                            type: 'POST',
                            url: jypath + '/backstage/attribute/getValues',
                            data: {
                                businessId: busiParams.bid
                            },
                            dataType: 'json',
                            success: function(result, textStatus) {
                                $.each(result.obj, function(i, v) {
                                    var $input = $("#" + formId + " input[name='" + v.name + "']");
                                    if (JY.Object.notNull($input[0])) {
                                        $input.val(v.value);
                                        $input.attr("id", v.id);
                                    } else {
                                        var $select = $("#" + formId + " select[name='" + v.name + "']");
                                        if (JY.Object.notNull($select[0])) {
                                            $select.val(v.value);
                                            $select.attr("id", v.id);
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            },
            save: function(formId, url, callback) {
                var data = JY.Object.formSerialize($("#" + formId), "attributeId");
                var attrs = JY.Tools.getExtendsAttrValues(formId, "attributeId");
                data.attrValues = attrs;
                $.ajax({
                    type: 'POST',
                    url: url,
                    data: JSON.stringify(data),
                    dataType: 'json',
                    contentType: "application/json",
                    success: function(data, textStatus) {
                        if (typeof (callback) == 'function') {
                            callback.call(this,data);
                        }
                        JY.Model.info(data.resMsg);
                    }
                });
            }
        }
    }
};
/*
 * 对Date的扩展，将 Date 转化为指定格式的String
 *月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
 *年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
 *例子： 
 *(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
 *(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
 */
Date.prototype.Format = function(fmt) {
    //author: meizz 
    var o = {
        "M+": this.getMonth() + 1,
        //月份 
        "d+": this.getDate(),
        //日 
        "h+": this.getHours(),
        //小时 
        "m+": this.getMinutes(),
        //分 
        "s+": this.getSeconds(),
        //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3),
        //季度 
        "S": this.getMilliseconds()//毫秒 
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    }
    return fmt;
}
;
