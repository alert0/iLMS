var app = angular.module('app', ['formDirective']);
app.controller("ctrl", [ '$scope', 'baseService', function($scope, baseService) {
	
	// base64解码
	$scope.$on("afterLoadEvent",function(ev,data){
		data.templateHtml = $.base64.decode(data.templateHtml,"utf-8");
	});
	
	
}]);