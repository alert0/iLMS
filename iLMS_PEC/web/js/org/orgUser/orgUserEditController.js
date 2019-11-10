
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	
	var data={
	}
	
	$scope.addRow=function(classVar){
		$scope.data[classVar +"List"].push(angular.copy(obj[classVar]))
	}
	
}]);