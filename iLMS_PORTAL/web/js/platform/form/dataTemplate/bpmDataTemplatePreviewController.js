var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	
	$(".search-form select").select2({language: "zh-CN"});

	$scope.changeFilterKey = function(key){
		$('[name="filterKey"]').val(key);
		$('a.btn.fa-search').trigger('click');
		$('[name="filterKey"]').val(filterKey);
	}
	
}]);

$(function(){ 
	$('#searchForm').show();
});

function openDetail(thisEl,id, action) {
	var title = action == "edit" ? "编辑" : action=="get"? "查看":"添加";
	var url = $(thisEl).attr("action") + '&action='+action;
	if(!$.isEmpty(id)){
		url +='&id=' + id
	}
	HT.window.openEdit(url, title, action, 'grid', 600, 400, null, null, id, true);
}

function showCustomDialog(obj, alias, resultField) {
	CustomDialog.openCustomDialog(alias, function(data, dialog) {
		dialog.dialog('close');
		if (data.length > 0) {
			$(obj).prev().val(data[0][resultField]);
		} else {
			$(obj).prev().val("");
		}
	},{selectNum : "1"});
}

//导出
function exports(exportUrl){
	var postData = $('#grid').datagrid('options').queryParams;  
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
		src : exportUrl,
		base : def
	});
}

function startFlow(defId,boAlias, businessKey){
	var baseService = AngularUtil.getService("baseService");
	baseService.postForm(__ctx+"/flow/instance/startForm",{defId:defId,businessKey:businessKey,boAlias:boAlias}).then(function(data,msg){
		if(data.result==1){
			$.topCall.success(data.message);
			HT.window.closeEdit(true);
		}else{
			$.topCall.warn(data.message);
		}
	},function(data){
		$.topCall.warn(data.message);
	});
	
}