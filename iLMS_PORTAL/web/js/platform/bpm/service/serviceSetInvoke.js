var systemApp = angular.module('systemApp', [ 'base','ServiceSetService' ]);

systemApp.controller("serviceSetInvokeCtrl",['$scope','ServiceSet','ServiceParam',function($scope,ServiceSet,ServiceParam){
	$scope.params = [];
	$scope.serviceSetId;
	$scope.result = {};
	$scope.invoking = false;
	
	$scope.invoke = function(){
		var paramsStr = JSON.stringify($scope.params);
		$scope.invoking = true;
		$scope.result = {};
		ServiceSet.invokeService($scope.serviceSetId,paramsStr,function(data){
			$scope.invoking = false;
			$scope.result = data;
		});
	}
	
	$scope.initParams = function(serviceSetId){
		if(serviceSetId){
			$scope.serviceSetId = serviceSetId;
			ServiceParam.list(serviceSetId,function(data){
				if(data.result){
					$scope.params = data.params;
				}
			});
		}
	}
}]);