var app = angular.module('app', ['formDirective']);
app.controller("ctrl", [ '$scope', 'baseService', function($scope, baseService) {
	$scope.closeFrame = function(){
		var index = top.layer.getFrameIndex(window.name);  
		top.layer.close(index);  
	}
}]);