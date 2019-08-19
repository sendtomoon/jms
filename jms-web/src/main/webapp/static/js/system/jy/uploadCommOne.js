function file_img(it){
	   var this_name=it.val();
	   var length=this_name.length-3;
	   var ext_name=this_name.substr(length,3).toLowerCase();
	   if(ext_name!='jpg'&&ext_name!='png'&&ext_name!='gif'){
		   JY.Model.info('不能上传“除jpg、png、gif”格式以外的文件！');
		    it.val('');
			it.siblings('img').attr('src',jypath +'/static/images/no_img.jpg')
	   }else{
		    var f=it.get(0).files;  
			if(f==undefined){
				it.parent().append("<span style='margin-top:10px;font-size:12px;'></span>");
				it.parent().find('span').html("浏览器不支持预览<br><em style='font-size:10px;'>"+it.val()+"</em>");
			}else{
				var fileReader=new FileReader();
				fileReader.onload=function(){
			    it.siblings('img').attr('src',fileReader.result);
			}
			fileReader.readAsDataURL(f[0]);
			}
	 }
};