$(function () {
	
	initDemo1();//init grid
	
	$("#addRow").on('click', function(e) {
		gridManager.addRow({
			id:"",
			chsName: "",
			enName: "",
			gender: "",
			age: "",
			birthday : "",
			education : "",
			school: "",
			description : "",
			status: "1"
	    });
	});
	
	$("#delRows").on('click', function(e) {
		var row = gridManager.getSelectedRow();
        if (!row) { alert('请选择行'); return; }
		gridManager.deleteSelectedRow();
	});
	
	$("#saveGrid").on('click', function(e) {
		var data = {};
		data.insertList = gridManager.getAdded();//new
		data.deleteList = gridManager.getDeleted();//delete
		data.updateList = gridManager.getUpdated();//update
		if(data.insertList.length>0 || data.updateList.length>0 || (JY.Object.notNull(data.deleteList) && data.deleteList.length>0)){
			$.ajax({
				type:'POST',
				url:jypath+'/scm/demo/batchSave',
				data:JSON.stringify(data),
				dataType:'json',
				contentType:"application/json;charset=utf-8",
				success:function(result,textStatus){ 
					//fresh grid
					JY.Model.info(result.message);
					gridManager.reload();
				}
			});
		}
	});
	
});


var gridManager,
eduData = [{ education: 0, text: '无' }, { education: 1, text: '小学'}
, { education: 2, text: '初中'}, { education: 3, text: '高中'}
, { education: 4, text: '大专'}, { education: 5, text: '本科'},
{ education: 6, text: '硕士'}, { education: 7, text: '博士'}];

function initDemo1(){
	gridManager = $("#gridDemo1").ligerGrid({
	    title:"表格操作示例",
        //width:900,
	    checkbox: true,
        columns:[
            {display:"id",name:"id",hide:true},
            {display:"中文名",name:"chsName",editor:{type:'text'}},
            {display:"英文名",name:"enName",editor: {type:'text'}},
            {display:"性别",name:"gender",editor: { type: 'select', data: [{ gender: 1, text: '男' }, { gender: 0, text: '女'}], valueField: 'gender' },
                render: function (item)
                {
                	return JY.Object.notNull(item.gender)?(parseInt(item.gender) == 1?"男":"女"):"";
                }},
            {display:"年龄",name:"age",editor:{type:'int'},
                	render:function(item){
                    	return JY.Object.notNull(item.age)?item.age:null;
                    }},
            {display:"出生日期",name:"birthday",type:'date',format:'yyyy/MM/dd', editor:{type:'date'},
            	render:function(item){
            	return JY.Date.Format(item.birthday,'yyyy/MM/dd');
            }},
            {display:"学历背景",name:"education",editor:{type:'select',data:eduData , valueField: 'education'},
            	render:function(item){
            		if(!JY.Object.notNull(item.education)){
            			return "";
            		}
            		var _value = "";
            		$.each(eduData,function(i,e){
            			if(e.education==item.education){
            				_value = e.text;
            				return false;
            			}
            		});
            		return _value;
            	}},
            {display:"毕业院校",name:"school",editor:{type:'text'}},
            {display:"描述",name:"description",editor:{type:'text'},width:400},
            {display:"状态",name:"status",editor:{type:'select',data:[{ status: 1, text: '正常' }, { status: 0, text: '禁用'}] , valueField: 'status'},
            	render:function(item){
            		return item.status == "1"?'<label style="color:green;">正常</label>':'<label style="color:red;">禁用</label>';
            	}},
        ],
        dataAction:"server",
        //数据请求地址
        url:jypath +'/scm/demo/findByPage',
        //数据书否分页，默认为true
        usePager:true,
        rownumbers:true,
        root:"results",
        record:"totalRecord",
        pageParmName: 'pageNum', 
        pagesizeParmName:"pageSize",
        enabledEdit:true,
        pageSizeOptions:[10,20,50,100]
    });
}
