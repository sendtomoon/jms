var goldType = [];
var itemNum = 0;
var selectedGoldType = [];

$(function () {
    JY.Dict.setSelect("selectisValid","POS_PRICEAPPLY_STATUS",2,"全部");

    goldType = gridSelect(jypath +'/backstage/dataDict/getDictSelect',"SCM_PRO_GOLD_TYPE",goldType);

    setBaseData();
    getbaseList(1);
    initOrg();

    // 更改查询条件按回车键触发查询按钮的点击事件
    $("#PriceApplyForm").keydown(function(e){
        keycode = e.which || e.keyCode;
        if (keycode == 13) {
            search();
        }
    });

    $("#dateBegin").datepicker({
        showOn: "button",
        buttonImage: jypath+"/static/images/calendar.gif",
        buttonImageOnly: true,
        dateFormat: 'yy/mm/dd',
        changeYear: true,
        changeMonth:true,
        onSelect:function(dateText, inst) { }
    });
    $("#dateEnd").datepicker({
        showOn: "button",
        buttonImage: jypath+"/static/images/calendar.gif",
        buttonImageOnly: true,
        dateFormat: 'yy/mm/dd',
        changeYear: true,
        changeMonth:true,
        onSelect:function(dateText, inst) { }
    });

    // 新增按钮事件
    $('#addBtn').on('click', function(e) {
        itemNum = 0;
        selectedGoldType = [];
        e.preventDefault();
        cleanAdd();
        $('#ScmPriceApplyForm').find('#productOrg_clear').show();
        $("#ScmPriceApplyForm .rejectBox").hide();
        editInfo({id:"ScmPriceApplyDiv",title:"新建金价申请",height:"690",width:"1024",savefn:function(type){
            var that =$(this);
            if(JY.Validate.form("ScmPriceApplyForm") && $('#ScmPriceApplyAdd tbody tr').length > 0) {
                var canFill = false;
                var jsonData = "";
                var trLastIndex = $('#ScmPriceApplyAdd tbody tr').length - 1;
                $('#ScmPriceApplyAdd tbody tr').each(function (index, element) {
                    var tmpGoldCode = $(this).find('select[name="goldCode"]').val();
                    if (tmpGoldCode == null || tmpGoldCode == '' || tmpGoldCode == undefined) {
                        canFill = false;
                        return false;
                    } else {
                        var tmpOldPrice = $(this).find('input[name="oldPrice"]').val();
                        if (tmpOldPrice == null || tmpOldPrice == '' || tmpOldPrice == undefined) {
                            tmpOldPrice = 0;
                        }
                        var data = '{"goldCode":"' + $(this).find('select[name="goldCode"]').val() + '", "oldPrice":' + tmpOldPrice
                            + ', "price":' + $(this).find('input[name="price"]').val() // + ', "otherPrice":' + $(this).find('input[name="otherPrice"]').val()
                            + ', "note":"' + $(this).find('input[name="note"]').val() + '"}';
                        if ($(this).index() === trLastIndex) {
                            canFill = true;
                            jsonData += data;
                        } else {
                            jsonData += data + ',';
                        }
                    }
                });
                if (canFill) {
                    jsonData = '[' + jsonData + ']';
                    JY.Ajax.doRequest(null,jypath +'/pos/priceApply/add',{myData: jsonData, type: type, note: $("#ScmPriceApplyForm textarea[name='note']").val(), orgId: $('#productOrgId').val()},function(data){
                        if (data.res == 1) {
                            JY.Model.info(data.resMsg);
                            that.dialog("close");
                            search();
                        } else {
                            JY.Model.error(data.resMsg);
                        }
                    });
                } else {
                    JY.Model.info("表格中存在未指定金类的行，请核对后重试！");
                }
            } else if (JY.Validate.form("ScmPriceApplyForm") && !($('#ScmPriceApplyAdd tbody tr').length > 0)) {
                JY.Model.info("金价申请明细不能为空，请核对后再试！");
            }
        }})
    });

    // 删除按钮
    $('#delBtn').on('click', function() {
        var chks = [];
        var values = [];
        var ids = [];
        $('#PriceApplyTable input[name="ids"]:checked').each(function(){
            chks.push($(this).val());
            values.push($(this).parent().parent().parent().find('span[name="statusValue"]').text());
            ids.push($(this).parent().parent().parent().find('input[name="ids"]').val());
        });
        if(chks.length == 0) {
            JY.Model.info("您没有选择任何内容!");
        } else {
            if (values.indexOf("待审核") != -1 || values.indexOf("已审核") != -1) {
                JY.Model.info("您只能删除状态为草稿或已拒绝的金价申请单!");
            } else {
                delByBtn(ids);
            }
        }
    });

    // 查看按钮
    $('#viewBtn').on('click', function(e) {
        var chks =[];
        $('#PriceApplyTable input[name="ids"]:checked').each(function(){
            chks.push($(this).val());
        });
        if(chks.length == 0) {
            JY.Model.info("您没有选择任何内容!");
        }else if(chks.length > 1){
            JY.Model.info("您只能选择一条内容!");
        } else {
            view(chks[0]);
        }
    });

    // 修改按钮
    $('#updateBtn').on('click', function(e) {
        var chks =[];
        var values = [];
        $('#PriceApplyTable input[name="ids"]:checked').each(function(){
            chks.push($(this).val());
            values.push($(this).parent().parent().parent().find('span[name="statusValue"]').text());
        });
        if(chks.length == 0) {
            JY.Model.info("您没有选择任何内容!");
        }else if(chks.length > 1){
            JY.Model.info("您只能选择一条内容!");
        } else {
            if (values.indexOf("待审核") != -1 || values.indexOf("已审核") != -1) {
                JY.Model.info("您只能修改状态为草稿或已拒绝的金价申请单!");
            } else {
                update(chks[0]);
            }
        }
    });

    // 审核按钮
    $('#verifyBtn').on('click', function() {
        var chks = [];
        var values = [];
        $('#PriceApplyTable input[name="ids"]:checked').each(function(){
            chks.push($(this).val());
            values.push($(this).parent().parent().parent().find('span[name="statusValue"]').text());
        });
        if(chks.length == 0) {
            JY.Model.info("您没有选择任何内容!");
        } else if (chks.length > 1){
            JY.Model.info("您只能选择一条内容!");
        } else {
            if (values.indexOf("草稿") != -1 || values.indexOf("已审核") != -1 || values.indexOf("已拒绝") != -1) {
                JY.Model.info("您只能审核状态为待审核的金价申请单!");
            } else {
                verify(chks[0]);
            }
        }
    });

});

// 更改查询条件按回车键触发查询按钮的点击事件
function search(){
    $("#searchBtn").trigger("click");
}

function cleanBaseForm(){
    JY.Tags.cleanForm("PriceApplyForm");
    setBaseData();
}
function setBaseData(){
    var date=new Date;
    var year=date.getFullYear();
    $("#dateBegin").val(year+"/1/1")
    $("#dateEnd").val(year+"/12/31")
}

function view(id){
    cleanAdd();
    $('#ScmPriceApplyForm').find('#productOrg_clear').hide();
    $("#ScmPriceApplyForm .rejectBox").show();
    $('#ScmPriceApplyForm').find('.btnClass').hide();
    JY.Ajax.doRequest(null,jypath +'/pos/priceApply/view',{id:id},function(data){
        setForm(data);
        $('#ScmPriceApplyForm').find('input,select,textarea').attr('disabled','disabled');
        viewInfo({id:"ScmPriceApplyDiv",title:"查看金价申请",height:"690",width:"1024"});
    });
}

function update(id){
    itemNum = 0;
    selectedGoldType = [];
    cleanAdd();
    $('#ScmPriceApplyForm').find('#productOrg_clear').show();
    $("#ScmPriceApplyForm .rejectBox").hide();
    $('#ScmPriceApplyForm').find('.btnClass').show();
    JY.Ajax.doRequest(null,jypath +'/pos/priceApply/view',{id:id},function(data){
        setForm(data);
        editInfo({id:"ScmPriceApplyDiv",title:"修改金价申请",height:"690",width:"1024",savefn:function(type){
            var that =$(this);
            if(JY.Validate.form("ScmPriceApplyForm") && $('#ScmPriceApplyAdd tbody tr').length > 0) {
                var canFill = false;
                var jsonData = "";
                var trLastIndex = $('#ScmPriceApplyAdd tbody tr').length - 1;
                $('#ScmPriceApplyAdd tbody tr').each(function (index, element) {
                    var tmpGoldCode = $(this).find('select[name="goldCode"]').val();
                    if (tmpGoldCode == null || tmpGoldCode == '' || tmpGoldCode == undefined) {
                        canFill = false;
                        return false;
                    } else {
                        var data = '{"goldCode":"' + $(this).find('select[name="goldCode"]').val() + '", "oldPrice":' + $(this).find('input[name="oldPrice"]').val()
                            + ', "price":' + $(this).find('input[name="price"]').val() // + ', "otherPrice":' + $(this).find('input[name="otherPrice"]').val()
                            + ', "note":"' + $(this).find('input[name="note"]').val() + '"}';
                        if ($(this).index() === trLastIndex) {
                            canFill = true;
                            jsonData += data;
                        } else {
                            jsonData += data + ',';
                        }
                    }
                });
                if (canFill) {
                    jsonData = '[' + jsonData + ']';
                    JY.Ajax.doRequest(null,jypath +'/pos/priceApply/update',{myData: jsonData, type: type, id: $("#ScmPriceApplyTab input[name='id']").val(), orderNo: $("#ScmPriceApplyTab input[name='orderNo']").val(), note: $("#ScmPriceApplyForm textarea[name='note']").val(), orgId: $("#ScmPriceApplyTab input[name='orgId']").val()},function(data){
                        if (data.res == 1) {
                            JY.Model.info(data.resMsg);
                            that.dialog("close");
                            search();
                        } else {
                            JY.Model.error(data.resMsg);
                        }
                    });
                } else {
                    JY.Model.info("表格中存在未指定金类的行，请核对后重试！");
                }
            } else if (JY.Validate.form("ScmPriceApplyForm") && !($('#ScmPriceApplyAdd tbody tr').length > 0)) {
                JY.Model.info("金价申请明细不能为空，请核对后再试！");
            }
        }});
    });
}

function verify(id){
    cleanAdd();
    $("#ScmPriceApplyForm .rejectBox").hide();
    $('#ScmPriceApplyForm').find('.btnClass').hide();
    JY.Ajax.doRequest(null,jypath +'/pos/priceApply/view',{id:id},function(data){
        setForm(data);
        $('#ScmPriceApplyForm').find('input,select,textarea').attr('disabled','disabled');
        $('#ScmPriceApplyForm').find('textarea[name="rejectcause"]').attr('disabled', false);
        verifyInfo({id:"ScmPriceApplyDiv",title:"审核金价申请",height:"690",width:"1024",savefn:function(type){
            var model1 =$(this);
            $("#jyConfirm").children().children().find(".causesDiv").remove();
            var flag = false;
            if(type == 0) {
                flag = true;
                $("#jyConfirm").children().children().append('<div class="causesDiv"><form id="causesForm"><br><div style="float:left;font-size:13px;"><span style="float:left">驳回原因</span><br><input type="text" jyValidate="required" id="causesTxt" placeholder="请输入原因" style="width: 265px;"></div></form></div>');
                JY.Model.confirm("确认要审核不通过吗?",function(){
                    var model2 = $(this);
                    var causes = $("#causesTxt").val();
                    if(type == 0 && !JY.Validate.form("causesForm")){
                        return false;
                    }
                    JY.Ajax.doRequest(null,jypath +'/pos/priceApply/verify',{myData: jsonData, status: type, id: $("#ScmPriceApplyTab input[name='id']").val(), orgId: $('#productOrgId').val(), orderNo: $("#ScmPriceApplyTab input[name='orderNo']").val(), rejectCause: causes},function(data){
                        model1.dialog("close");
                        model2.dialog("close");
                        JY.Model.info(data.resMsg,function(){search();});
                    });
                },function(){},flag);
            } else if (type == 2) {
                var jsonData = "";
                var trLastIndex = $('#ScmPriceApplyAdd tbody tr').length - 1;
                $('#ScmPriceApplyAdd tbody tr').each(function (index, element) {
                    var data = '{"goldCode":"' + $(this).find('select[name="goldCode"]').val() + '", "oldPrice":' + $(this).find('input[name="oldPrice"]').val()
                        + ', "price":' + $(this).find('input[name="price"]').val() // + ', "otherPrice":' + $(this).find('input[name="otherPrice"]').val()
                        + ', "note":"' + $(this).find('input[name="note"]').val() + '"}';
                    if ($(this).index() === trLastIndex) {
                        jsonData += data;
                    } else {
                        jsonData += data + ',';
                    }
                });
                jsonData = '[' + jsonData + ']';
                JY.Ajax.doRequest(null,jypath +'/pos/priceApply/verify',{myData: jsonData, status: type, id: $("#ScmPriceApplyTab input[name='id']").val(), orgId: $('#productOrgId').val(), orderNo: $("#ScmPriceApplyTab input[name='orderNo']").val()},function(data){
                    if (data.res == 1) {
                        JY.Model.info(data.resMsg);
                        model1.dialog("close");
                        search();
                    } else {
                        JY.Model.error(data.resMsg);
                    }
                });
            }
        }});
    });

}

function delByBtn(obj) {
    $("#jyConfirm").children().children().find(".causesDiv").remove();
    JY.Model.confirm("确认删除吗？",function(){
        JY.Ajax.doRequest(null,jypath +'/pos/priceApply/delete',{ids: obj.toString()},function(data){
            JY.Model.info(data.resMsg,function(){
                search();
            });
        });
    });
}

function viewInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"查看")+"</h4></div>",title_html:true,buttons:[{html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.fn) == 'function'){attr.fn.call(this);}}}]});
}

function editInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
        buttons:[{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存为草稿","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
            {html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;保存并提交","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,1);}}},
            {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}

function verifyInfo(attr){
    $("#"+attr.id).removeClass('hide').dialog({resizable:false,height:attr.height,width:attr.width,modal:true,title:"<div class='widget-header'><h4 class='smaller'>"+(JY.Object.notNull(attr.title)?attr.title:"修改")+"</h4></div>",title_html:true,
        buttons:[{html: "<i id='save_btn' class='icon-ok bigger-110'></i>&nbsp;通过","class":"btn btn-primary btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,2);}}},
            {html: "<i id='save_btn' class='icon-remove bigger-110'></i>&nbsp;不通过","class":"btn btn-xs",click:function(){if(typeof(attr.savefn) == 'function'){attr.savefn.call(this,0);}}},
            {html:"<i class='icon-remove bigger-110'></i>&nbsp;取消","class":"btn btn-xs",click:function(){$(this).dialog("close");if(typeof(attr.cancelfn) == 'function'){attr.cancelfn.call(this);}}}]
    });
}

function gridSelect(url,keys,selects){
    JY.Ajax.doRequest("",url,{ids:"1",keys:keys},function(data){
        var map=data.obj;
        var list=map["1"];
        $.each(list.items,function(i,e){
            var a ={key:e.value,text:e.name};
            selects[i]=a;
        });
    })
    return selects;
}

function options(obj,val){
    var str = "<option value=''>请选择</option>";

    for(var i=0;i<obj.length;i++){
        str += "<option value='" + obj[i].key + "'";
        if(val==obj[i].key){
            str+=" selected='true' ";
        }
        str+=">" + obj[i].text + "</option>";
    }
    return str;
}

function cleanAdd(){
    $('#ScmPriceApplyForm').find('.btnClass').show();
    $('#ScmPriceApplyForm').find('.ui-datepicker-trigger').show();
    $('#ScmPriceApplyForm').find('input,select,textarea').removeAttr('disabled');
    JY.Tags.cleanForm("ScmPriceApplyForm");
    $("#ScmPriceApplyForm input[name='orderNo']").val("");
    $("#ScmPriceApplyForm input[name='orgId']").val("");
    $("#ScmPriceApplyForm input[id='id']").val("");
    $("#ScmPriceApplyForm input[id='status']").val("");
    $("#ScmPriceApplyAdd tbody").html("");
    $("#ScmPriceApplyAdd tfoot").html("");
    $("#ScmPriceApplyForm span[id$='createUser']").text("");
    $("#ScmPriceApplyForm span[id$='createTime']").text("");
    $("#ScmPriceApplyForm span[id$='checkUser']").text("");
    $("#ScmPriceApplyForm span[id$='checkTime']").text("");
}

// 机构树初始化
function initOrg(){
    JY.Ajax.doRequest(null,jypath +'/pos/priceApply/getOrgTree',{},function(data){
        $.jy.dropTree.init({
            rootId:"productOrg",
            displayId:"productOrgName",
            data:data.obj,
            dataFormat:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'},
            isExpand:true,
            resetFn:function(){
                $("#ScmPriceApplyForm #productOrgName").val('');
                $("#ScmPriceApplyForm #productOrgId").val('');
            },
            clickFn:function(node){
                $("#ScmPriceApplyForm #productOrgId").val(node.id);
            }
        });
    });
}

function getbaseList(init) {

    if(init == 1) {
        $("#PriceApplyForm .pageNum").val(1);
    }
    JY.Model.loading();
    JY.Ajax.doRequest("PriceApplyForm",jypath +'/pos/priceApply/findByPage',null,function(data) {
        $("#PriceApplyTable tbody").empty();
        var obj = data.obj;
        var list = obj.list;
        var results = list.results;
        var permitBtn = obj.permitBtn;
        var pageNum = list.pageNum, pageSize = list.pageSize, totalRecord = list.totalRecord;
        var html = "";
        if(null != results && results.length > 0) {
            var leng = (pageNum-1) * pageSize;//计算序号
            for(var i = 0;i < results.length;i++) {
                var l = results[i];
                html += "<tr>";
                html += "<td class='center'><label> <input type='checkbox' name='ids' value='"+l.id+"' class='ace' /> <span class='lbl'></span></label></td>";
                html += "<td class='center hidden-480'>"+(i+leng+1)+"</td>";
                html += "<td class='center hidden-480'><a style='cursor:pointer;' onclick='view(&apos;"+JY.Object.notEmpty(l.id)+"&apos;)'>"+JY.Object.notEmpty(l.orderNo)+"</a></td>";
                html += "<td class='center hidden-480'>"+JY.Object.notEmpty(l.createName)+"</td>";
                html += "<td class='center hidden-480'>"+JY.Object.notEmpty(l.orgName)+"</td>";
                html += "<td class='center hidden-480'>"+JY.Object.notEmpty(new Date(l.createTime).Format('yyyy-MM-dd'))+"</td>";
                if (l.status == 0) {
                    html += "<td class='center hidden-480'><span name='statusValue' class='label label-sm arrowed-in'>草稿</span></td>";
                } else if (l.status == 1) {
                    html += "<td class='center hidden-480'><span name='statusValue' class='label label-sm arrowed-in'>待审核</span></td>";
                } else if (l.status == 2) {
                    html += "<td class='center hidden-480'><span name='statusValue' class='label label-sm label-success'>已审核</span></td>";
                } else if (l.status == 3) {
                    html += "<td class='center hidden-480'><span name='statusValue' class='label label-sm arrowed-in'>已拒绝</span></td>";
                }
                html += "<td class='center hidden-480'>"+JY.Object.notEmpty(l.checkName)+"</td>";
                html += "<td class='center hidden-480'>"+JY.Object.notEmpty(l.checkOrgName)+"</td>";
                if (!JY.Object.notNull(l.checkTime)) {
                    html += "<td class='center hidden-480'></td>";
                } else {
                    html += "<td class='center hidden-480'>"+JY.Object.notEmpty(new Date(l.checkTime).Format('yyyy-MM-dd'))+"</td>";
                }
                html += "</tr>";
            }
            $("#PriceApplyTable tbody").append(html);
            JY.Page.setPage("PriceApplyForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        }else{
            html += "<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
            $("#PriceApplyTable tbody").append(html);
            $("#pageing ul").empty();//清空分页
        }
        JY.Model.loadingClose();
    });

}

function addDetail() {
    var canFill = false;
    var trLastIndex = $('#ScmPriceApplyAdd tbody tr').length - 1;
    if (trLastIndex >= 0) {
        $('#ScmPriceApplyAdd tbody tr').each(function (index, element) {
            var tmpGoldCode = $(this).find('select[name="goldCode"]').val();
            if (tmpGoldCode == null || tmpGoldCode == '' || tmpGoldCode == undefined) {
                canFill = false;
                return false;
            } else {
                if ($(this).index() === trLastIndex) {
                    canFill = true;
                }
            }
        });
        if (canFill) {
            var html = "";
            html += "<tr>";
            html += "<td class='center'><label> <input type='checkbox' name='detailId' class='ace' /><span class='lbl'></span></label></td>";
            html += "<td style='padding:1px;'><select name='goldCode' jyValidate='required' id='goldType"+itemNum+"' onchange=upInput("+itemNum+",'goldType') style='width:100%;'>"+options(goldType)+"</select></td>";
            html += "<td style='padding:1px;'><input class='center' type='text' name='oldPrice' readonly onkeyup='JY.Validate.limitDouble(this)' style='width:100%;height:30px;border:none;' /></td>";
            html += "<td style='padding:1px;'><input class='center' type='text' name='price' id='price"+itemNum+"' onchange=chg("+itemNum+",'price') jyValidate='required' onkeyup='JY.Validate.limitDouble(this)' style='width:100%;height:30px;border:none;'/></td>";
            // html += "<td style='padding:1px;'><input class='center' type='text' name='otherPrice' onkeyup='JY.Validate.limitDouble(this)' style='width:100%;height:30px;border:none;'/></td>";
            html += "<td style='padding:1px;'><input class='center' type='text' name='note' style='width:100%;height:30px;border:none;' /></td>";
            html += "</tr>";
            $("#ScmPriceApplyAdd tbody").append(html);
            itemNum++;
        } else {
            JY.Model.info("表格中存在未指定金类的行，请核对后重试！");
        }
    } else {
        var html = "";
        html += "<tr>";
        html += "<td class='center'><label> <input type='checkbox' name='detailId' class='ace' /><span class='lbl'></span></label></td>";
        html += "<td style='padding:1px;'><select name='goldCode' jyValidate='required' id='goldType"+itemNum+"' onchange=upInput("+itemNum+",'goldType') style='width:100%;'>"+options(goldType)+"</select></td>";
        html += "<td style='padding:1px;'><input class='center' type='text' name='oldPrice' readonly onkeyup='JY.Validate.limitDouble(this)' style='width:100%;height:30px;border:none;' /></td>";
        html += "<td style='padding:1px;'><input class='center' type='text' name='price' id='price"+itemNum+"' onchange=chg("+itemNum+",'price') jyValidate='required' onkeyup='JY.Validate.limitDouble(this)' style='width:100%;height:30px;border:none;'/></td>";
        // html += "<td style='padding:1px;'><input class='center' type='text' name='otherPrice' onkeyup='JY.Validate.limitDouble(this)' style='width:100%;height:30px;border:none;'/></td>";
        html += "<td style='padding:1px;'><input class='center' type='text' name='note' style='width:100%;height:30px;border:none;' /></td>";
        html += "</tr>";
        $("#ScmPriceApplyAdd tbody").append(html);
        itemNum++;
    }
    selectedGoldType = [];
    $("#ScmPriceApplyAdd tbody").find("select[name='goldCode']").each(function (index, element) {
        selectedGoldType.push($(this).val());
    });
}

function delDetail() {
    var chks =[];
    $('#ScmPriceApplyAdd input[name="detailId"]:checked').each(function(){
        chks.push($(this).val());
    });
    if(chks.length==0) {
        JY.Model.info("您没有选择任何内容!");
    }else{
        JY.Model.confirm("确认要删除选中的数据吗?",function(){
            $('#ScmPriceApplyAdd input[name="detailId"]:checked').each(function(){
                $(this).parent().parent().parent().remove();
                codeArray = [];
            });
            $('#ScmPriceApplyAdd input[type="checkbox"]').each(function(){
                $(this).attr('checked', false);
            });
        });
    }
}

function setForm(data){
    itemNum = 0;
    commForm(data);
    commCode(data.obj.details);
}

function commForm(data){
    $("#ScmPriceApplyTab input[name='id']").val(JY.Object.notEmpty(data.obj.applies.id));
    $("#ScmPriceApplyTab input[name='status']").val(JY.Object.notEmpty(data.obj.applies.status));
    $("#ScmPriceApplyTab input[name='orderNo']").val(JY.Object.notEmpty(data.obj.applies.orderNo));
    $("#ScmPriceApplyTab input[name='orgId']").val(JY.Object.notEmpty(data.obj.applies.orgId));
    $("#ScmPriceApplyTab input[name='orgName']").val(JY.Object.notEmpty(data.obj.applies.orgName));
    $.jy.dropTree.checkNode("productOrg","productOrgName",data.obj.applies.orgId);
    $("#ScmInventoryForm input[id='productOrgId']").val(data.obj.applies.orgId);

    $("#ScmPriceApplyForm textarea[name='note']").prop("value",JY.Object.notEmpty(data.obj.applies.note));
    $("#ScmPriceApplyForm textarea[name='rejectcause']").prop("value",JY.Object.notEmpty(data.obj.applies.rejectCause));
    $("#ScmPriceApplyForm span[id='createUser']").text(JY.Object.notEmpty(data.obj.applies.createName));
    if(JY.Object.notEmpty(data.obj.applies.createTime)){
        $("#ScmPriceApplyForm span[id='createTime']").text(JY.Object.notEmpty(new Date(data.obj.applies.createTime).Format("yyyy/MM/dd hh:mm:ss")));
    }
    $("#ScmPriceApplyForm span[id='checkUser']").text(JY.Object.notEmpty(data.obj.applies.checkName));
    if(JY.Object.notEmpty(data.obj.applies.checkTime)){
        $("#ScmPriceApplyForm span[id='checkTime']").text(JY.Object.notEmpty(new Date(data.obj.applies.checkTime).Format("yyyy/MM/dd hh:mm:ss")));
    }
}

function commCode(obj){
    var list=[];
    var html="";
    if(obj!=null){
        for(var i=0;i<obj.length;i++){
            $("#ScmPriceApplyAdd input[name$='detailId']").each(function(){
                list.push($(this).val());
            });
            if(list.indexOf(obj[i].id) == -1){
                html += "<tr>";
                html += "<td class='center'><label> <input type='checkbox' name='detailId' class='ace' value='" + JY.Object.notEmpty(obj[i].id) + "' /><span class='lbl'></span></label></td>";
                html += "<td style='padding:1px;'><select name='goldCode' jyValidate='required' id='goldType"+itemNum+"'  style='width:100%;' onchange=upInput("+itemNum+",'goldType') >"+options(goldType, JY.Object.notEmpty(obj[i].goldCode))+"</select></td>";
                html += "<td style='padding:1px;'><input class='center' type='text' name='oldPrice' readonly onkeyup='JY.Validate.limitDouble(this)' value='" + JY.Object.notNumber(obj[i].oldPrice) + "' style='width:100%;height:30px;border:none;' /></td>";
                html += "<td style='padding:1px;'><input class='center' type='text' name='price' jyValidate='required' id='price"+itemNum+"' onchange=chg("+itemNum+",'price') onkeyup='JY.Validate.limitDouble(this)' value='" + JY.Object.notNumber(obj[i].price) + "' style='width:100%;height:30px;border:none;'/></td>";
                // html += "<td style='padding:1px;'><input class='center' type='text' name='otherPrice' jyValidate='required' onkeyup='JY.Validate.limitDouble(this)' value='" + JY.Object.notNumber(obj[i].otherPrice) + "' style='width:100%;height:30px;border:none;'/></td>";
                html += "<td style='padding:1px;'><input class='center' type='text' name='note' value='" + JY.Object.notEmpty(obj[i].note) + "' style='width:100%;height:30px;border:none;' /></td>";
                html += "</tr>";
                itemNum++;
            }else{
                JY.Model.info("金类条码已存在，请核对后再试!");
            }
        }
        $("#ScmPriceApplyAdd tbody").append(html);
    }
}

function upInput(num, name){
    var selectedVal = $("#"+name+num).val();
    if (selectedGoldType.indexOf(selectedVal) != -1 && selectedGoldType.length >= 1){
        JY.Model.info("该金类已存在，请重新选择!");
        $("#"+name+num).val('');
    } else {
        JY.Ajax.doRequest(null,jypath +'/pos/priceApply/getOldPrice',{code: selectedVal},function(data){
            if (data.obj == null) {
                $("#"+name+num).parent().parent().find('input[name="oldPrice"]').val('');
            } else {
                $("#"+name+num).parent().parent().find('input[name="oldPrice"]').val(data.obj.price);
            }
        });
    }
    selectedGoldType = [];
    $("#ScmPriceApplyAdd tbody").find("select[name='goldCode']").each(function (index, element) {
        selectedGoldType.push($(this).val());
    });

}

function chg(num, name) {
    var value = $("#" + name + num).val();
     var tmpOldPrice = $("#"+name+num).parent().parent().find('input[name="oldPrice"]').val();
     if (tmpOldPrice == null || tmpOldPrice == '' || tmpOldPrice == undefined) {
         $("#"+name+num).parent().parent().find('input[name="oldPrice"]').val(value);
     }
}
