
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	
	
	
}]);

function getData(){
	var scope=AngularUtil.getScope();
	return angular.toJson(scope.data);
}

function isValid(){
	var scope=AngularUtil.getScope();
	return  scope.form.$valid;
}