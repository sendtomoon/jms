(function($) {
	/*当domReady的时候开始初始化*/
	$(function() {
		var $wrap = $('#uploader'),
		/* 图片容器*/
		$queue = $('<ul class="filelist"></ul>').appendTo(
				$wrap.find('.queueList')),
		/*状态栏，包括进度和控制按钮*/
		$statusBar = $wrap.find('.statusBar'),
		/*文件总体选择信息。*/
		$info = $statusBar.find('.info'),
		/* 上传按钮*/
		$upload = $wrap.find('.uploadBtn'),
		/* 没选择文件之前的内容。*/
		$placeHolder = $wrap.find('.placeholder'),
		/*添加的文件数量*/
		fileCount = 0,
		/* 添加的文件总大小*/
		fileSize = 0
		/* 优化retina, 在retina下这个值是2*/
				ratio = window.devicePixelRatio || 1,
				/* 缩略图大小*/
				thumbnailWidth = 110 * ratio,
				thumbnailHeight = 110 * ratio,
				 /*可能有pedding, ready, uploading, confirm, done.*/
				state = 'pedding',
				/*所有文件的进度信息，key为file id*/
				percentages = {},
				/*判断浏览器是否支持图片的base64*/
				isSupportBase64 = (function() {
					var data = new Image();
					var support = true;
					data.onload = data.onerror = function() {
						if (this.width != 1 || this.height != 1) {
							support = false;
						}
					}
					data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
					return support;
				})(),
				/* 检测是否已经安装flash，检测flash的版本*/
				flashVersion = (function() {
					var version;
					try {
						version = navigator.plugins['Shockwave Flash'];
						version = version.description;
					} catch (ex) {
						try {
							version = new ActiveXObject(
									'ShockwaveFlash.ShockwaveFlash')
									.GetVariable('$version');
						} catch (ex2) {
							version = '0.0';
						}
					}
					version = version.match(/\d+/g);
					return parseFloat(version[0] + '.' + version[1], 10);
				})(),
				supportTransition = (function() {
					var s = document.createElement('p').style, r = 'transition' in s
							|| 'WebkitTransition' in s
							|| 'MozTransition' in s
							|| 'msTransition' in s || 'OTransition' in s;
					s = null;
					return r;
				})(),
				/* WebUploader实例*/
				uploader;
		if (!WebUploader.Uploader.support('flash') && WebUploader.browser.ie) {
			/* flash 安装了但是版本过低。*/
			if (flashVersion) {
				(function(container) {
					window['expressinstallcallback'] = function(state) {
						switch (state) {
						case 'Download.Cancelled':
							JY.Model.info('您取消了更新！')
							break;
						case 'Download.Failed':
							JY.Model.info('安装失败')
							break;
						default:
							JY.Model.info('安装已成功，请刷新！');
							break;
						}
						delete window['expressinstallcallback'];
					};
					var swf = './expressInstall.swf';
					/* insert flash object*/
					var html = '<object type="application/'
							+ 'x-shockwave-flash" data="' + swf + '" ';
					if (WebUploader.browser.ie) {
						html += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" ';
					}
					html += 'width="100%" height="100%" style="outline:0">'
							+ '<param name="movie" value="'
							+ swf
							+ '" />'
							+ '<param name="wmode" value="transparent" />'
							+ '<param name="allowscriptaccess" value="always" />'
							+ '</object>';
					container.html(html);
				})($wrap);
				/* 压根就没有安转。*/
			} else {
				$wrap
						.html('<a href="http://www.adobe.com/go/getflashplayer" target="_blank" border="0"><img alt="get flash player" src="http://www.adobe.com/macromedia/style_guide/images/160x41_Get_Flash_Player.jpg" /></a>');
			}
			return;
		} else if (!WebUploader.Uploader.support()) {
			JY.Model.info('Web Uploader 不支持您的浏览器！');
			return;
		}

		/* 实例化*/
		uploader = WebUploader.create({
			pick : {
				id : '#filePicker',
				label : '点击选择图片'
			},
			formData : {
				uid : 123
			},
			dnd : '#dndArea',
			paste : '#uploader',
			swf : jypath + '/static/plugins/webuploader/js/Uploader.swf',
			chunked : false,
			chunkSize : 512 * 1024,
			server : jypath + '/backstage/accessory/upload',
			/* runtimeOrder: 'flash',*/
			accept : {
				title : 'Images',
				extensions : 'jpg,jpeg,png',
				mimeTypes: 'image/jpg,image/jpeg,image/png' 
			},

			/* 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。*/
			disableGlobalDnd : true,
			fileNumLimit : 300,
			fileSizeLimit : 200 * 1024 * 1024, /* 200 M*/
			fileSingleSizeLimit : 50 * 1024 * 1024
		/* 50 M*/
		});

		/* 拖拽时不接受 js, txt 文件。*/
		uploader.on('dndAccept', function(items) {
			var denied = false, len = items.length, i = 0,
			/* 修改js类型*/
			unAllowed = 'text/plain;application/javascript ';

			for (; i < len; i++) {
				/* 如果在列表里面*/
				if (~unAllowed.indexOf(items[i].type)) {
					denied = true;
					break;
				}
			}

			return !denied;
		});

		uploader.on('dialogOpen', function() {
			console.log('here');
		});

		/* 添加“添加文件”的按钮，*/
		uploader.addButton({
			id : '#filePicker2',
			label : '点击选择图片'
		});

		uploader.on('ready', function() {
			window.uploader = uploader;
		});

		/* 当有文件添加进来时执行，负责view的创建*/
		function addFile(file) {
			var $li = $('<li id="' + file.id + '">' + '<p class="title">'
					+ file.name + '</p>' + '<p class="imgWrap"></p>' + ''
					+ '</li>'),

			$btns = $(
					'<div class="file-panel">'
							+ '<span class="cancel">删除</span>'
							+ '<span class="rotateRight">向右旋转</span>'
							+ '<span class="rotateLeft">向左旋转</span></div>')
					.appendTo($li), $wrap = $li.find('p.imgWrap'), $info = $('<p class="error"></p>'), showError = function(
					code) {
				switch (code) {
				case 'exceed_size':
					text = '文件大小超出';
					break;

				case 'interrupt':
					text = '上传暂停';
					break;

				default:
					text = '上传失败，请重试';
					break;
				}

				$info.text(text).appendTo($li);
			};

			if (file.getStatus() === 'invalid') {
				showError(file.statusText);
			} else {
				/* @todo lazyload*/
				$wrap.text('预览中');
				uploader.makeThumb(file, function(error, src) {
					var img;
					if (error) {
						$wrap.text('不能预览');
						return;
					}
					if (isSupportBase64) {
						img = $('<img src="' + src + '">');
						$wrap.empty().append(img);
					} else {
						$wrap.text("预览出错");
					}
				}, thumbnailWidth, thumbnailHeight);

				percentages[file.id] = [ file.size, 0 ];
				file.rotation = 0;
			}

			file.on('statuschange', function(cur, prev) {
				if (prev === 'queued') {
					$li.off('mouseenter mouseleave');
					$btns.remove();
				}

				/* 成功*/
				if (cur === 'error' || cur === 'invalid') {
					console.log(file.statusText);
					showError(file.statusText);
					percentages[file.id][1] = 1;
				} else if (cur === 'interrupt') {
					showError('interrupt');
				} else if (cur === 'queued') {
					$info.remove();
					percentages[file.id][1] = 0;
				} else if (cur === 'complete') {
					$li.append('<span class="success"></span>');
				}

				$li.removeClass('state-' + prev).addClass('state-' + cur);
			});

			$li.on('mouseenter', function() {
				$btns.stop().animate({
					height : 30
				});
			});

			$li.on('mouseleave', function() {
				$btns.stop().animate({
					height : 0
				});
			});

			$btns.on('click', 'span', function() {
				var index = $(this).index(), deg;
				switch (index) {
				case 0:
					uploader.removeFile(file);
					return;
				case 1:
					file.rotation += 90;
					break;
				case 2:
					file.rotation -= 90;
					break;
				}
				if (supportTransition) {
					deg = 'rotate(' + file.rotation + 'deg)';
					$wrap.css({
						'-webkit-transform' : deg,
						'-mos-transform' : deg,
						'-o-transform' : deg,
						'transform' : deg
					});
				} else {
					$wrap.css('filter',
							'progid:DXImageTransform.Microsoft.BasicImage(rotation='
									+ (~~((file.rotation / 90) % 4 + 4) % 4)
									+ ')');
				}
			});

			$li.appendTo($queue);
		}

		/* 负责view的销毁*/
		function removeFile(file) {
			var $li = $('#' + file.id);

			delete percentages[file.id];
			$li.off().find('.file-panel').off().end().remove();
		}

		function setState(val) {
			var file, stats;
			if (val === state) {
				return;
			}
			$upload.removeClass('state-' + state);
			$upload.addClass('state-' + val);
			state = val;
			switch (state) {
			case 'pedding':
				$placeHolder.addClass('element-invisible');
				$('#filePicker2').removeClass('element-invisible');
				$queue.show();
				$statusBar.removeClass('element-invisible');
				uploader.refresh();
				break;

			case 'ready':
				$placeHolder.addClass('element-invisible');
				$('#filePicker2').removeClass('element-invisible');
				$queue.show();
				$statusBar.removeClass('element-invisible');
				uploader.refresh();
				break;

			case 'uploading':
				$('#filePicker2').addClass('element-invisible');
				$upload.text('暂停上传');
				break;

			case 'paused':
				$upload.text('继续上传');
				break;

			case 'confirm':
				$('#filePicker2').removeClass('element-invisible');
				$upload.text('开始上传');

				stats = uploader.getStats();
				if (stats.successNum && !stats.uploadFailNum) {
					setState('finish');
					return;
				}
				break;
			case 'finish':
				stats = uploader.getStats();
				if (stats.successNum) {
					console.log('上传成功');
				} else {
					/* 没有成功的图片，重设*/
					state = 'done';
					location.reload();
				}
				break;
			}
		}
		uploader.onFileQueued = function(file) {
			fileCount++;
			fileSize += file.size;

			if (fileCount === 1) {
				$placeHolder.addClass('element-invisible');
				$statusBar.show();
			}

			addFile(file);
			setState('ready');
		};

		uploader.onFileDequeued = function(file) {
			fileCount--;
			fileSize -= file.size;

			if (!fileCount) {
				setState('pedding');
			}

			removeFile(file);

		};

		uploader.on('all', function(type) {
			var stats;
			switch (type) {
			case 'uploadFinished':
				setState('confirm');
				break;

			case 'startUpload':
				setState('uploading');
				break;

			case 'stopUpload':
				setState('paused');
				break;

			}
		});
		uploader.onError = function(code) {
			if (code == 'Q_EXCEED_NUM_LIMIT') {
				JY.Model.info('文件数量超出');
			}else if (code == 'Q_EXCEED_SIZE_LIMIT') {
				JY.Model.info('文件总大小超出');
			}else if (code == 'Q_TYPE_DENIED') {
				JY.Model.info('文件类型错误');
			}else if(code == 'F_DUPLICATE'){
				JY.Model.info('不能选择相同图片');
			}
		}
		uploader.on('uploadSuccess', function(file, response) {
			var fileId=$('#' + file.id);
			if (response.res == '1') {
				var json_obj = eval('(' + response.resMsg + ')');
				var str = $('#imgId').val();
				if (json_obj.status) {
					if (str == "") {
						str += response.resMsg;
					} else {
						str += "," + response.resMsg;
					}
				} else {
					fileId.append('<p class="error">上传失败，请重试</p>');
					fileId.remove();
				}
				$('#imgId').val(str);
			} else {
				fileId.append('<p class="error">上传失败，请重试</p>');
				fileId.find('.success').remove();
			}
		});
		$upload.on('click', function() {
			if ($(this).hasClass('disabled')) {
				return false;
			}

			if (state === 'ready') {
				uploader.upload();
			} else if (state === 'paused') {
				uploader.upload();
			} else if (state === 'uploading') {
				uploader.stop();
			}
		});

		$info.on('click', '.retry', function() {
			uploader.retry();
		});

		$info.on('click', '.ignore', function() {
			JY.Model.info('todo');
		});

		$upload.addClass('state-' + state);
		
		 $('body').delegate('.files .imgname','click',function(){
			 /*查看页面*/
			 $("#img").removeClass('hide').dialog(
						{
							resizable : false,
							height : "500",
							width :"400",
							modal : true,
							title : "<div class='widget-header'><h4 class='smaller'>"
									+ "查看"
									+ "</h4></div>",
							title_html : true,
							buttons : [ {
								html : "<i class='icon-remove bigger-110'></i>&nbsp;取消",
								"class" : "btn btn-xs",
								click : function() {
									$(this).dialog("close");
								}
							} ]
						
						});
			  var src=$(this).data('img');
			  $('#img').html("");
		  	  if($('#imgs'+$(this).data("num")).length==0){
		  	    $('#img').append('<p id="imgs'+$(this).data("num")+'" style="width:380px;height:360px; align="center";border:1px #ddd solid;"><img style="width:100%;height:100%;object-fit: cover;" src="'+src+'"/></p>')
		  	  }else{
		  	  	$('#imgs'+$(this).data("num")).remove();
		  	  }
		   })
		  
	});

})(jQuery);

function move_dom(dom,moveTo){//dom是插件的jquery对象(页面只放一个，并且设置displa:none;),moveTo需要放置的位置的jquery对象;
	var clone_dom=$(dom);
	$(dom).show();
	$(dom).remove();
	$(moveTo).append(clone_dom);
	$('#uploader .uploadBtn').on('click', function() {
		if ($(this).hasClass('disabled')) {
			return false;
		}
		if (state === 'ready') {
			uploader.upload();
		} else if (state === 'paused') {
			uploader.upload();
		} else if (state === 'uploading') {
			uploader.stop();
		}
	});

}

var $wrap = $('#uploader'),
/* 图片容器*/
$queue = $('<ul class="filelist" id="files"></ul>').appendTo(
		$wrap.find('.queueList')),
/* 状态栏，包括进度和控制按钮*/
$statusBar = $wrap.find('.statusBar'),
/* 文件总体选择信息。*/
$info = $statusBar.find('.info'),
/* 上传按钮*/
/*$upload = $wrap.find('.uploadBtn'),*/
/* 没选择文件之前的内容。*/
$placeHolder = $wrap.find('.placeholder')

function setImg(data) {
	window.uploader = uploader;
	var jsonLen = 1;
	if (jsonLen != 0) {
		fileCount = jsonLen;
		$placeHolder.addClass('element-invisible');
		$statusBar.show();
		/* 显示在页面上*/
		$.each(data, function(i, n) {
			var obj = {}, statusMap = {};
			obj.id = n.id;
			obj.name = n.name;
			obj.filename = n.path;
			obj.getStatus = function() {
				return '';
			};
			obj.statusText = '';
			obj.size = n.filelen;
			obj.version = WebUploader.Base.version;
			obj.source = this;
			obj.setStatus = function(status, text) {
				var prevStatus = statusMap[this.id];
				typeof text !== 'undefined' && (this.statusText = text);
				if (status !== prevStatus) {
					statusMap[this.id] = status;
					/** 文件状态变化 */
					uploader.trigger('statuschange', status, prevStatus);
				}
			};
			editFile(obj);
		});
		WebUploader.Base.idSuffix = jsonLen;
	}
}

function editFile(file) {
	var $li = $('<li id="' + file.id + '">' + '<p class="title">' + file.name
			+ '</p>' + '<p class="imgWrap"></p>' + '' + '</li>'),

	$btns = $(
			'<div class="file-panel">'
					+ '<span class="cancel" onclick="delImg(\'' + file.id
					+ '\')">删除</span>' + '</div>').appendTo($li), $wrap = $li
			.find('p.imgWrap'), $info = $('<p class="error"></p>');

	if (file.getStatus() === 'invalid') {
		showError(file.statusText);
	} else {
		/* @todo lazyload*/
		$wrap.empty();
		$wrap.text('不能预览');
		var img;
		img = $('<img src="'+ file.filename
				+ '" style="height:100%;">');
		$wrap.empty().append(img);
		file.rotation = 0;
	}

	

	$li.on('mouseenter', function() {
		$btns.stop().animate({
			height : 30
		});
	});

	$li.on('mouseleave', function() {
		$btns.stop().animate({
			height : 0
		});
	});

	$btns.on('click', 'span', function() {
		var index = $(this).index(), deg;
		switch (index) {
		case 0:
			uploader.removeFile(file);
			return;

		case 1:
			file.rotation += 90;
			break;

		case 2:
			file.rotation -= 90;
			break;
		}
		if (supportTransition) {
			deg = 'rotate(' + file.rotation + 'deg)';
			$wrap.css({
				'-webkit-transform' : deg,
				'-mos-transform' : deg,
				'-o-transform' : deg,
				'transform' : deg
			});
		} else {
			$wrap.css('filter',
					'progid:DXImageTransform.Microsoft.BasicImage(rotation='
							+ (~~((file.rotation / 90) % 4 + 4) % 4) + ')');
		}
	});
	$li.append('<span class="success"></span>');
	$li.appendTo($queue);
}


var wrapper=$('#wrapper');

var queue = $('<ul class="files"></ul>').appendTo(wrapper);
var $div=$('<div id="bigImg" class="hide"></div>');
function checkImg(file) {
/*	$("#bigImg").attr("display","none");*/
	/* 显示在页面上*/
	$.each(file, function(i, n) {
		var $li = $('<li><a class="imgname" href="javascript:;" data-num="'+n.id+'" data-img="'+n.path+'" style="color: blue;">'+n.name+'</a></li>');
		$li.appendTo(queue);
	});
	$div.appendTo(queue);
}


function checkImgs(id){
	$('#container').hide();
	JY.Ajax.doRequest("",jypath + '/backstage/accessory/findImgList',{busnessid:id},function(data){
		imgList=data.obj;
		checkImg(imgList);
		var els = $('.file-panel');
		$.each(els, function() {
		    $(els).hide();
	    });
		
		
		$statusBar.hide();
	});
}

function cleanImgs() {
	/* 移除所有缩略图并将上传文件移出上传序列*/
	for (var i = 0; i < uploader.getFiles().length; i++) {
		/*将图片从上传序列移除*/
		uploader.removeFile(uploader.getFiles()[i]);
		uploader.removeFile(uploader.getFiles()[i], true);
		delete uploader.getFiles()[i];
		/* 将图片从缩略图容器移除*/
		var $li = $('#' + uploader.getFiles()[i].id);
		$li.off().remove();
	}
	/* 重置uploader，目前只重置了文件队列*/
	uploader.reset();
	$("input[name$='imgId']").val('');
	$('#files').html("");
	$('.files').html("");
	$('#bigImgs').html("");
	$placeHolder.addClass('element-invisible');
	$('#filePicker2').removeClass('element-invisible');
	$('#container').show();
	$queue.show();
	$statusBar.show();
	$statusBar.removeClass('element-invisible');
	uploader.refresh();
}

/* 删除按钮*/
function delImg(id) {
	JY.Ajax.doRequest(null, jypath + '/backstage/accessory/delImgs', {
		id : id
	});
}

function findImgList(id){
	JY.Ajax.doRequest("",jypath + '/backstage/accessory/findImgList',{busnessid:id},function(data){
		imgList=data.obj;
		setImg(imgList);
	});
}