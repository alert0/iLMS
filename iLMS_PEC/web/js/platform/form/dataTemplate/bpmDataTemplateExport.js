var app = angular.module('app', [ 'formDirective', 'arrayToolService' ]);
app.controller("ctrl", [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayTool) {
	$scope.ArrayTool = ArrayTool;

	$scope.init = function() {
		$scope.selAllExp=true;
		$scope.param = window.passConf;
		$scope.param.getType = "page";
	};

	$scope.exportData = function() {
		if(!$scope.param.expField){
			alert("请选择至少一个字段");
			return;
		}
		
		var url = __ctx + '/form/dataTemplate/export?formKey=' + __param.formKey;
		var frm = new com.hotent.form.Form();
		frm.creatForm("frmExport", url);
		$.each($scope.param,function(key,val){
			frm.addFormEl(key, val);
		});
		frm.submit();
	};

	$scope.$on("afterLoadEvent", function(event, data) {
		$scope.data.displayField = JSON.parse(data.displayField);
	});
} ]);