var app = angular.module('app', [ 'formDirective', 'arrayToolService' ]);
app.controller("ctrl", [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayTool) {
	$scope.ArrayTool = ArrayTool;

	$scope.init = function() {
		$scope.field = window.passConf;
	};

	$scope.addSetting = function() {
		if(!$scope.field.alarmSetting){
			$scope.field.alarmSetting = [];
		}
		
		var json = {};
		json.condition = [ {
			op : '=='
		} ];
		$scope.field.alarmSetting.push(json);
	};
	
} ]);