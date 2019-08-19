/**
 * @version jy.component.js v1.0
 * @creator lisongbai
 * @createdate 2016-12-19 
 */
;(function($){
      $.jy={
    		 dropTree:{
    			 /**render a tree*/
    			 init:function(setting){
	    	    	  var domId = setting.rootId;
	    	    	  var inputId = setting.displayId;
	    	    	  var resetFn = setting.resetFn;
	    	    	  var clickFn = setting.clickFn;
	    	    	  var data = setting.data;
	    	    	  var _defaultFormat = {enable:true,idKey:"id",pIdKey:"pid",rootPId:'0'};
	    	    	  var _expand = setting.isExpand;
	    	    	  $.extend(_defaultFormat,setting.dataFormat);
	    	    	  var _treePanelHtml = '<a title="清空" id="'+domId+'_clear"><i class="icon-remove bigger-120 red"></i></a><div id="'+domId+'_drop" class="menuContent ztreeMC" style="display: none; position: absolute;"><ul id="'+domId+'_tree" class="ztree preOrgTree"></ul></div>';
	    	    		$("#"+domId).append(_treePanelHtml);
	    	    		/**binding the Click event on clearBUTTON*/
	    	    		$("#"+domId+"_clear").on('click', function(e) {
	    	    			e.preventDefault();
	    	    			$("#"+domId+" #"+inputId).prop("value","");
	    	    			$("#" + domId + " input[type$='hidden']").val("");
	    	    			if (typeof (resetFn) == 'function') resetFn.call(this);
	    	    		});
	    	    		/**binding the Click event on displayINPUT*/
	    	    		$("#"+domId+" #"+inputId).on('click', function(e) {
	    	    			e.preventDefault();
	    	    			var that = $(this);
	    	    			var offpos = that.position();
	    	    			$("#"+domId+"_drop").css({left:offpos.left+"px",top:offpos.top+that.heith+"px"}).slideDown("fast");	
	    	    		});
	    	    		/**binding the Mouseleave event on topDIV*/
	    	    		$("#"+domId).on('mouseleave', function(e) {
	    	    			e.preventDefault();
	    	    			$("#"+domId+"_drop").fadeOut("fast");
	    	    		});
	    	    		
	    	    		/**init ztree*/
	    	    		$.fn.zTree.init($("#"+domId+"_tree"), {
	    	    			view : {
	    	    				dblClickExpand : false,
	    	    				selectedMulti : false,
	    	    				nameIsHTML : true
	    	    			},
	    	    			data : {
	    	    				simpleData:_defaultFormat
	    	    			},
	    	    			callback : {
	    	    				onClick : function(event, treeId, treeNode){
	    	    					$("#"+domId+" #"+inputId).prop("value",treeNode.name);
	    	    					$("#"+domId+"_drop").fadeOut("fast");
	    	    					if (typeof (clickFn) == 'function') clickFn.call(this,treeNode);
	    	    				}
	    	    			}
	    	    		}, data);
	    	    		/**switch:to expand the root node*/
	    	    		if(_expand){
	    	    			var _tree = $.fn.zTree.getZTreeObj(domId+"_tree");
	    	    			var _nodes = _tree.getNodes();
	    	    			if(_nodes.length>0)_tree.expandNode(_nodes[0],true,false,false,false);
	    	    		}
	    	      },
	    	      /**to check a node by id*/
	    	      checkNode:function(rootId,disaplayId,nodeId,fn){
	    	    	  var _ztree = $.fn.zTree.getZTreeObj(rootId+"_tree");
	    	    	  var _node = _ztree.getNodeByParam("id", nodeId, null);
	    	    	  _ztree.selectNode(_node,false);
	    	    	  $("#"+rootId+" #"+disaplayId).val(_node.name);
	    	    	  if (typeof (fn) == 'function') fn.call(this);
	    	      },
	    	      restStyle:function(rootId){
	    	    	  $("#"+rootId).find("#"+rootId+"_clear").remove();
	    	    	  $("#"+rootId).find("#"+rootId+"_drop").remove();
	    	      }
    		 }
      		 //TODO to extend others components
    	      
      }
    	  
})(jQuery);