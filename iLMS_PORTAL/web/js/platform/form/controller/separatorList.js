var gangedApp = angular.module('app', [ 'formDirective','arrayToolService']);

gangedApp.controller('ctrl',['$scope','baseService','ArrayToolService',function($scope,BaseService,ArrayToolService){
	$scope.ArrayTool=ArrayToolService;
	$scope.separators = [];
	
	$scope.$on("afterLoadEvent",function(ev,data){
		
	});
	
	if(window.passConf){//初始化
		if(window.passConf.separators)
		$scope.separators = parseToJson(window.passConf.separators);
	}

	//添加分组
	$scope.addSeparator = function(){
		var separator = {};
		separator.key = "";
		separator.isOpen = true;
		separator.isShow = true;
		$scope.separators.push(separator);
	}
	//移除分组
	$scope.removeSeparator = function(separator){
		$scope.separators.remove(separator);
	}
	
}]);