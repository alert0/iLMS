var app = angular.module('app', ['formDirective']);
app.controller("ctrl", [ '$scope', 'baseService', function($scope, baseService) {
	// 初始化值
	$scope.$on("afterLoadEvent",function(ev,data){
		if(data.content){
			$('#content').html(data.content);
		}
	});
}]);