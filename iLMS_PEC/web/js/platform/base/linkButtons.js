(function($) {

	// 初始
	$.init = function() {
		// 查询
		$.handlerSearch();
		// 重置查询
		$.handlerReSetSearch();

		// 关闭普通窗口
		$.handlerCloseWind();

		// 处理回车查询事件
		$.handleSearchKeypress();

		// 处理编辑
		$.handlerEditor();

		// 删除选中行
		$.handlerRemoveSelect();
	

		$.handlerCollapseExpand();

	};
	$.isShowProcessBar=false;
	// 查询
	$.search = function(obj) {
		if ($(obj).hasClass('disabled')) return;
		var params = $("#searchForm").serializeArray();
		var data = {};
		$(params).each(function() {
			var name = this.name, value = this.value, str = '{"' + name + '":"' + value + '"}';
			if (value != '') {
				var json = eval('(' + str + ')');
				data = $.extend(data, json);
			}
		});
		$('.my-easyui-datagrid').datagrid('load', data);
	};

	// 清空查询参数 重置查询
	$.clearSearch = function() {
		// $('#searchForm').form('clear');
		var inputs = $("#searchForm").find("input");
		$(inputs).each(function(i, k) {
			k.value = "";
		});
		inputs = $("#searchForm").find("select");
		$(inputs).each(function(i, k) {
			k.value = "";
			if($.isFunction($.fn.select2)){ // select2的清空
				var defaultvalue = null;
				if($(this)[0].multiple){
					defaultvalue = "";
				}
				$(this).val(defaultvalue).trigger("change");
			}
		})
		
		//根据指令解析出来的数据字典查询条件，虽然相应的input值已经置空，但是页面上显示的字典项没有还原到最初的状态
		var spans = $("#searchForm").find("span[placeholder='点击选择']");
		$(spans).each(function(i, k) {
			$(this).text("点击选择");
		});
		//$.reload();
	};

	// 重置查询
	$.reload = function() {
		$('.my-easyui-datagrid').datagrid('load', {});
	};

	$.getDatagrid = function(obj) {
		if(!obj) return $('.my-easyui-datagrid');
		return $(obj).parents('.datagrid').find('.my-easyui-datagrid').eq(0);
	}
	
	/**
	 * 返回选择的数据。
	 * obj：easyui 表格对象。
	 * field ：字段名
	 */
	$.getSelectIds=function(obj,field){
		var dategrid = obj.datagrid('getChecked');
		if (dategrid == null || dategrid.length < 1) {
		    return null;
		} else {
			var aryId=[];
			for (i = 0; i < dategrid.length; i++) {
				aryId.push(dategrid[i][field]);
			}
			return aryId.join(",");
		}
	}
	
	
	$.getDatagridCheckedId = function(obj, allowMultRecords) {
		var datagrid = $.getDatagrid(obj);
		idField = datagrid.datagrid('options').idField;
		records = datagrid.datagrid('getChecked'), rtn = null, idVal = "";
		if (records == null || records.length < 1)
			return rtn;
		if (records.length > 1) {
			if (!allowMultRecords)
				return records;
			$(records).each(function(i) {
				var obj = this;
				idVal += obj[idField] + ",";
			});
		} else {
			idVal = records[0][idField]
		}
		var rtn = '{"' + idField + '":"' + idVal + '"}';
		return eval('(' + rtn + ')');
	}

	$.getUrl = function(url, param) {
		
		var parseUrl = function(u, p) {
			if (u.indexOf('?') != -1)
				return '&' + p;
			else
				return '?' + p;
		}
		if ($.type(param) == "object") {
			$.each(param, function(i, n) {
				
				var p = i + "=" + n;
//				console.info(p);
				
				url += parseUrl(url, p);
			});
		} else {
			url = parseUrl(url, param);
		}
		return url;
	}

	// 处理查询
	$.handlerSearch = function() {
		var $obj = $(".toolbar-search a.btn.fa-search");
		$obj.unbind('click');
		$obj.click(function() {
			$.search(this);
		});
	};
	// 重置处理查询
	$.handlerReSetSearch = function() {
		var $obj = $(".toolbar-search a.btn.fa-rotate-left");
		$obj.unbind('click');
		$obj.click(function() {
			$.clearSearch(this);
		});
	};
	// 关闭普通窗口
	$.handlerCloseWind = function() {
		var $obj = $(".toolbar-search .fa-close");
		$obj.unbind('click');
		$obj.click(function() {
			HT.window.closeEdit();
		});
	};

	// 处理按键查询
	$.handleSearchKeypress = function() {
		$(".toolbar-search :input").keypress(function(e) {
			if (e.keyCode == 13) {// 回车
				$(this).closest('form').submit(function(e){//禁止表单自动提交，  防止出现只有一个输入导致页面刷新
		            e.preventDefault();  
		        });  
				$.search($("a.btn.search"));
			} else if (e.keyCode == 27) {// ESC
				var searchForm = $("#searchForm");
				if (searchForm)
					searchForm[0].reset();
			}
		});
	};
	// 编辑、明细
	$.handlerEditor = function() {
		var $obj = $(".toolbar-search a.btn.fa-edit, .toolbar-search a.btn.fa-detail");
		$obj.unbind('click');
		$obj.bind('click', function() {
			if ($(this).hasClass('disabled'))
				return false;
			var me = $(this), url = me.attr('action'), idVal = $.getDatagridCheckedId(this);
			if (idVal == null) {
				$.msgShow('提示信息', '请选择记录!');
				return false;
			}
			if ($.isArray(idVal)) {
				$.msgShow('提示信息', '已经选择了多项,请选择一项进行操作!');
				return false;
			}
			if (url == null || url == '') {
				$.msgShow('提示信息', '未找到配置参数[action]!');
				return false;
			}
			if(me.attr("fullwindow")){
				$.openFullWindow($.getUrl(url, idVal));
				return;
			}
			window.location.href = $.getUrl(url, idVal);
		});
	};

	// 删除记录
	$.romveRecord = function(conf) {
		var url=__ctx + conf.url ;
		
		$.topCall.confirm('提示信息', '确认删除吗？', function(r) {
			if(!r) return;
			
			$.post(url, function(responseText) {
				var resultMessage = new com.hotent.form.ResultMessage(responseText);
				if (resultMessage.isSuccess()) {
					var msg = resultMessage.getMessage();
					$.topCall.success( msg|| '删除成功！',function(){
						conf.datagrid.datagrid("clearSelections");// 清除选中的
						conf.datagrid.datagrid("reload");
					});
				} else {
					$.topCall.alert("错误提示",resultMessage.getMessage());
				}
			});
			
		});
	};

	function format(str, obj, pre) {
		if (!str) 	return '';
		$.each(obj, function(d, v) {
			if (pre) {
				d = pre + "." + d;
			}

			var objType = typeof (v);
			if (typeof (v) == "object" && !(v == null || v == '' || v == undefined)) {
				str = format(str, v, d);
			}

			str = str.replace(RegExp("\\{" + d + "\\}", "g"), v)

		});
		return str;
	}
	function convertCondition(condition, data) {
		if (!condition)
			return false;
		condition = format(condition, data);
		if (condition && eval(condition))
			return true;
		return false;
	}

	// 删除选中行
	$.handlerRemoveSelect = function() {
		var $obj = $(".toolbar-search  a.btn.fa-remove");
		$obj.unbind('click');
		$obj.bind('click', function() {
			
			if ($(this).hasClass('disabled'))
				return false;
			var me = $(this), url = me.attr('action');
				datagrid = me.parents('.datagrid').find('.my-easyui-datagrid').eq(0);
				options = datagrid.datagrid('options');
				idField = options.idField;
			

			if (datagrid == null || datagrid.length < 0)
				return false;
			var records = datagrid.datagrid('getChecked');
			if (records == null || records.length < 1) {
				$.msgShow('提示信息', '请选择记录!');
				return false;
			}

			if (url == null || url == '') {
				$.msgShow('提示信息', '未找到配置参数[action]!');
				return false;
			}

			var idVal = [];
			
			$(records).each(function(i, d) {
				var allowedRemove = convertCondition(options.allowedRemove, d);
				if (!allowedRemove)
					idVal.push(d[idField]);
			});
			if (idVal.length < 1) {
				$.msgShow('提示信息', '请选择有效的记录!');
				return false;
			}
			var param = '{"' + idField + '":"' + idVal.join(',') + '"}';
			param = eval('(' + param + ')');
			
			$.romveRecord({
				url : $.getUrl(url, param),
				datagrid : datagrid
			});

		});
	};

	$.handlerRemoveOne = function() {
		var $obj = $(".rowInLine.fa-remove");
		$obj.unbind('click');
		$obj.bind('click', function() {
			if ($(this).hasClass('disabled')) return false;
			
			var me = $(this), url = me.attr('action'), datagrid = me.parents('.datagrid').find('.my-easyui-datagrid').eq(0);
			$.romveRecord({
				url : url,
				datagrid : datagrid
			});
		});

	};

	$.handlerCollapseExpand = function() {
		var $obj = $('.toolbar-search .tools .collapse, .toolbar-search .tools .expand');
		$obj.unbind('click');
		// 收缩、展开
		$obj.bind("click", function() {
			var me = $(this);
			var el = me.parents(".toolbar-search").children(".toolbar-body");
			if (me.hasClass("collapse")) {
				me.attr("title", "展开");
				me.removeClass("collapse").addClass("expand");
				var i = me.children(".fa-angle-double-up");
				i.removeClass("fa-angle-double-up").addClass("fa-angle-double-down");
				me.parents('.toolbar-search').addClass("toolbar-border-bottom");
				el.slideUp(200);
			} else {
				me.attr("title", "收缩");
				me.removeClass("expand").addClass("collapse");
				var i = me.children(".fa-angle-double-down");
				i.removeClass("fa-angle-double-down").addClass("fa-angle-double-up");
				me.parents('.toolbar-search').removeClass("toolbar-border-bottom");
				el.slideDown(200);
			}
			// 重置
			setTimeout(function() {
				$('.my-easyui-datagrid').resize();
			}, 100);
		});
	};
	$.msgShow = function(title, msg) {
		$.topCall.show({
			title : title,
			msg : msg,
			timeout : 2000,
			showType : 'slide',
			style : {
				right : '',
				top : document.body.scrollTop + document.documentElement.scrollTop,
				bottom : ''
			}
		});
	};

	function befReqHanlder(settings){
		if(! $.isShowProcessBar) return;
		settings.uuId=Math.round(Math.random() * 10000)+Math.round(Math.random() * 10000);
		var html="<div id="+settings.uuId+"><div class='datagrid-mask' style='display:block;'></div><div class='datagrid-mask-msg' style='display: block;left: 50%;height: 40px;margin-left: -85.5px;line-height: 16.0227px;'>正在处理，请稍待。。。</div></div>";
		$('body').append(html);
		setTimeout(function() {
			$("#"+settings.uuId).remove();
		}, 10000);
	 }
	function afterReqHanlder(settings){
		if(! $.isShowProcessBar) return;
		$("#"+settings.uuId).remove();
	}
	$.ajaxSetup({ beforeSend:function(xhr,settings) {
		befReqHanlder(settings);
	   }
	});
	$(document).ajaxComplete(function( event, request, settings ) {
		 afterReqHanlder(settings);
	});

})(jQuery);
$(function() {
	$.init();

});

function handGridLoadSuccess(){
	$.initRowOps();
    reSizeView2();
    $.handlerRemoveOne();
}