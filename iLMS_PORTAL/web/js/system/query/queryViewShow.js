$(function(){
　　    //重置处理查询
	var $obj = $(".toolbar-search a.btn.fa-rotate-left");
	$obj.unbind('click');
	$obj.click(function() {
		$("#searchForm").each( function(){
			this.reset();
		});
		//点击重置后重新查找一次
		search();
	});
	
}); 

function dealNullUrl(type){
  var msgtext = "您还没有给该按钮添加url！";
  if(type==1){
	  msgtext = "您还没有给该按钮添加事件！";
  }
  $.topCall.message({base:{type:'alert',title:'错误提示',msg:msgtext,icon:'error'}});
}

function resizeJqGrid() {
	var objSize = $.getPageSize();
	var panelHeight = $("#panelTop").height();
	$("#gridList").jqGrid('setGridWidth', objSize.width-2 )
	.jqGrid('setGridHeight', objSize.height - panelHeight -128);
}

function search() {
	var scope = $("#searchForm");
	var postData = $("#gridList").jqGrid("getGridParam", "postData");
	$("#searchForm").find("[name]").each(function() {
		var obj = $(this);
		var val = obj.val() ? obj.val() : "";
		var key = obj.attr("name");
		postData[key] = val;
	});
	$("#gridList").jqGrid('setGridParam', {
		search : true
	}).trigger("reloadGrid"); // 重新载入
}

function exports() {
	var url = __ctx + '/system/query/queryViewShowExport?alias=' + alias + "&sqlAlias=" + sqlAlias;
	var postData = $("#gridList").jqGrid("getGridParam", "postData");
	var dialog;
	var def = {
		passConf : jQuery.extend({
			search : true,
		}, postData),
		title : "导出设置",
		width : "800",
		height : "600",
		modal : true,
		resizable : true,
	};
	dialog = $.topCall.dialog({
		src : url,
		base : def
	});
}

function showCustomDialog(obj, alias, resultField) {
	CustomDialog.openCustomDialog(alias, function(data, dialog) {
		dialog.dialog('close');
		if (data.length > 0) {
			$(obj).prev().val(data[0][resultField]);
		} else {
			$(obj).prev().val("");
		}
	});
}

/**
 * 替换格式如类似格式的URL.
 * var url="/list.ht?id={id}&name={name}"
 * @param url
 * @param rowObject
 * @returns
 */
function replaceUrl(url,rowObject){
	var myregexp = /\{(.*?)\}/g;
	var match = myregexp.exec(url);
	while (match != null) {
		url=url.replace(match[0],rowObject[match[1]])
		match = myregexp.exec(url);
	}
	return url;
}