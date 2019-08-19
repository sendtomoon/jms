var pageSize = 1;//每页信息条数  
var page = 1;  
//下一页  
function next(formId) { 
	var theTable = document.getElementById(formId);//Id选择对了就OK！   ${devCompanyInfos }是所有后台返回数据  
	//获取对应控件  
	var pageNum2 = document.getElementById("Text1");//当前页文本框  
	var numberRowsInTable = theTable.rows.length-2;//表格最大行数  
    if (pageCount(formId) <= 1) {  
    }else {  
      hideTable(formId);  
      currentRow = pageSize * page + 1; //下一页的第一行  
      maxRow = currentRow + pageSize;   //下一页的一行  
      if (maxRow > numberRowsInTable) maxRow = numberRowsInTable+1;//如果计算中下一页最后一行大于实际最后一行行号  
      for (var i = currentRow; i < maxRow; i++) {  
        theTable.rows[i].style.display = '';  
      }  
      page++;  
      pageNum2.value = page;  
      /*if (maxRow == numberRowsInTable) { //如果下一页的最后一行是表格的最后一行  
        nextNoLink(formId); //下一页 和尾页 不点击  
        lastNoLink(formId);   
      }  */
      showPage(formId);//更新page显示  
      if (page == pageCount(formId)) {//如果当前页是尾页  
        nextNoLink(formId);  
        lastNoLink(formId);  
      }  
      preLink(formId);  
      firstLink(formId);  
    }  
}  
//上一页  
function pre(formId) {  
	var theTable = document.getElementById(formId);//Id选择对了就OK！   ${devCompanyInfos }是所有后台返回数据  
	var pageNum2 = document.getElementById("Text1");//当前页文本框  
	var numberRowsInTable = theTable.rows.length-2;//表格最大行数  
  if (pageCount(formId) <= 1) {  
  }  
  else {         
      hideTable(formId);  
      page--;  
      pageNum2.value = page;  
      currentRow = pageSize * page + 1;//下一页的第一行  
      maxRow = currentRow - pageSize;//本页的第一行  
      if (currentRow > numberRowsInTable) currentRow = numberRowsInTable;//如果计算中本页的第一行小于实际首页的第一行行号，则更正  
      for (var i = maxRow; i < currentRow; i++) {  
        theTable.rows[i].style.display = '';  
      }  
      if (maxRow == 0) { preNoLink(formId); firstNoLink(formId); }  
      showPage(formId);//更新page显示  
      if (page == 1) {  
        firstNoLink(formId);  
        preNoLink(formId);  
      }  
      nextLink(formId);  
      lastLink(formId);  
    }  
  }  

//跳转页数
function jump(formId,pageId) {  
	page=pageId;
	var theTable = document.getElementById(formId);//Id选择对了就OK！   ${devCompanyInfos }是所有后台返回数据  
	var pageNum2 = document.getElementById("Text1");//当前页文本框  
	var numberRowsInTable = theTable.rows.length-2;//表格最大行数  
  if (pageCount(formId) <= 1) {  
  }  
  else {         
      hideTable(formId);  
      pageNum2.value = pageId;  
      currentRow = pageSize * pageId + 1;//下一页的第一行  
      maxRow = currentRow - pageSize;//本页的第一行  
      if (currentRow > numberRowsInTable) currentRow = numberRowsInTable;//如果计算中本页的第一行小于实际首页的第一行行号，则更正  
      for (var i = maxRow; i < currentRow; i++) {  
        theTable.rows[i].style.display = '';  
      }  
      if (maxRow == 0) { preNoLink(formId); firstNoLink(formId); }  
      showPage(formId);//更新page显示  
      firstLink(formId);  
      preLink(formId);
      nextLink(formId);  
      lastLink(formId);  
    }  
  }  



//第一页  
function first(formId) {  
	var theTable = document.getElementById(formId);//Id选择对了就OK！   ${devCompanyInfos }是所有后台返回数据  
	//获取对应控件  
	var pageNum2 = document.getElementById("Text1");//当前页文本框  
	var numberRowsInTable = theTable.rows.length-2;//表格最大行数  
  if (pageCount(formId) <= 1) {  
  }  
  else {  
    hideTable(formId);//隐藏所有行  
    page = 1;  
    pageNum2.value = page;  
    for (var i = 1; i < pageSize+1; i++) {//显示第一页的信息  
      theTable.rows[i].style.display = '';  
    }  
    showPage(formId);//设置当前页  
  
    firstNoLink(formId);  
    preNoLink(formId);  
    nextLink(formId);  
    lastLink(formId);  
  }  
}  
//最后一页  
function last(formId) {  
	var theTable = document.getElementById(formId);//Id选择对了就OK！   ${devCompanyInfos }是所有后台返回数据  
	//获取对应控件  
	var pageNum2 = document.getElementById("Text1");//当前页文本框  
	var numberRowsInTable = theTable.rows.length-2;//表格最大行数  
  if (pageCount(formId) <= 1) {  
  }  
  else {  
    hideTable(formId);//隐藏所有行  
    page = pageCount(formId);//将当前页设置为最大页数  
    pageNum2.value = page;  
    currentRow = pageSize * (page - 1)+1;//获取最后一页的第一行行号  
    for (var i = currentRow; i < numberRowsInTable+1; i++) {//显示表格中最后一页信息  
      theTable.rows[i].style.display = '';  
    }  
    showPage(formId);  
    lastNoLink(formId);  
    nextNoLink(formId);  
    preLink(formId);  
    firstLink(formId);  
  }  
}  
function hideTable(formId) {//隐藏表格内容  
	var theTable = document.getElementById(formId);//Id选择对了就OK！   ${devCompanyInfos }是所有后台返回数据  
	var numberRowsInTable = theTable.rows.length-2;//表格最大行数  
  for (var i = 0; i < numberRowsInTable+1; i++) {  
    theTable.rows[i].style.display = 'none';  
  }  
  theTable.rows[0].style.display = '';//标题栏显示  
}  
function showPage(formId) {//设置当前页数  
	var pageNum = document.getElementById("spanPageNum");//当前页  
	pageNum.innerHTML = page;  
}  
function inforCount(formId) {//设置总记录数  
	var theTable = document.getElementById(formId);//Id选择对了就OK！   ${devCompanyInfos }是所有后台返回数据  
	var numberRowsInTable = theTable.rows.length-2;//表格最大行数  
}  
//总共页数  
function pageCount(formId) {  
	var theTable = document.getElementById(formId);//Id选择对了就OK！   ${devCompanyInfos }是所有后台返回数据  
	var numberRowsInTable = theTable.rows.length-2;//表格最大行数  
  var count = 0;  
  if (numberRowsInTable % pageSize != 0) count = 1;  
  return parseInt(numberRowsInTable / pageSize) + count;  
}  
//显示链接 link方法将相应的文字变成可点击翻页的  nolink方法将对应的文字变成不可点击的  
function preLink(formId) { 
	var spanPre = document.getElementById("spanPre");//上一页  
	spanPre.innerHTML = "<a href='javascript:pre(&apos;" + formId + "&apos;);'>上页</a>"; 
}  

function preNoLink(formId){ 
	var spanPre = document.getElementById("spanPre");//上一页  
	spanPre.innerHTML = "上页"; 
}  

function nextLink(formId) { 
	var spanNext = document.getElementById("spanNext");//下一页  
	spanNext.innerHTML = "<a href='javascript:next(&apos;" + formId + "&apos;);'>下页</a>"; 
}  
function nextNoLink(formId){
	var spanNext = document.getElementById("spanNext");//下一页  
	spanNext.innerHTML = "下页";
}  
function firstLink(formId) { 
	var spanFirst = document.getElementById("spanFirst");
	spanFirst.innerHTML = "<a href='javascript:first(&apos;" + formId + "&apos;);'>首页</a>"; 
}  
function firstNoLink(formId){
	var spanFirst = document.getElementById("spanFirst");
	spanFirst.innerHTML = "首页";
}  
function lastLink(formId) { 
	var spanLast = document.getElementById("spanLast");//尾页  
	spanLast.innerHTML = "<a href='javascript:last(&apos;" + formId + "&apos;);'>尾页</a>"; 
}  
function lastNoLink(formId){ 
	var spanLast = document.getElementById("spanLast");//尾页  
	spanLast.innerHTML = "尾页";
}  

function hide(formId) { 
	page=1;
	var theTable = document.getElementById(formId);//Id选择对了就OK！   ${devCompanyInfos }是所有后台返回数据  
	if(theTable.rows.length>2){
		$('#contextPage').show();
	}
	$('#jumpPage').html("<input id='Text1' type='text' onkeyup='changepage(&apos;"+formId+"&apos;)' value='1'  class='choseJPage'/>");
	//获取对应控件  
	var totalPage = document.getElementById("spanTotalPage");//总页数  
	var pageNum2 = document.getElementById("Text1");//当前页文本框  
	var numberRowsInTable = theTable.rows.length-2;//表格最大行数  
    for (var i = pageSize + 1; i < numberRowsInTable+1 ; i++) {  
      theTable.rows[i].style.display = 'none';  
    }  
    theTable.rows[0].style.display = '';  
    inforCount(formId);  
    totalPage.innerHTML = pageCount(formId);  
    showPage(formId);  
    pageNum2.value = 1;
    //分页显示
    preNoLink(formId);
    firstNoLink(formId);
    if (pageCount(formId) > 1) {  
      nextLink(formId);  
      lastLink(formId);  
    } else{
      nextNoLink(formId);
      lastNoLink(formId);
    }
}  


function reomoveContext(formId){
	var theTable = document.getElementById(formId);
	for (var i = 0; i < theTable.rows.length ; i++) {  
	      theTable.rows[i].style.display = '';  
	} 
	hide(formId);
}


var txtValue = 1; 
//跳转页数
function changepage(formId) {
	var theTable = document.getElementById(formId);//Id选择对了就OK！   ${devCompanyInfos }是所有后台返回数据  
    if (txtValue != txtValue2) {  
		var txtValue2 = document.getElementById("Text1").value;  
	    if (txtValue2 > pageCount(formId)) {  
	    }  
	    else if (txtValue2 <= 0) {  
	    }  
	    else if (txtValue2 == 1) {  
	      first(formId);  
	    }  
	    else if (txtValue2 == pageCount(formId)) {  
	      last(formId);  
	    }  
	    else {  
	      jump(formId,txtValue2);
	    }  
	    txtValue = txtValue2;  
    }  
}  
