var app = angular.module('app', [ 'formDirective', 'arrayToolService' ]);
app.controller("ctrl", [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayTool) {
	$scope.ArrayTool = ArrayTool;

	$scope.init = function() {
		$scope.field = window.passConf;
		
		$scope.field.isVirtual=1;
		$scope.field.virtualFrom=$scope.field.fieldName;
		$scope.field.isSearch=0;
		
		$scope.field.fieldDesc="";
		$scope.field.fieldName="";
		$scope.field.resultFromType="script";
		
		$scope.field.controlTypeDesc="无";
		$scope.field.controlType="";
		
		$scope.field.alarmSetting=null;//清空报表设置
		$scope.field.formater="";
	};

} ]);