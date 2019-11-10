angular.module("bpmSelectorApp",["base",'formDirective'])
.controller("selectorCtrl",['$scope','$http',function($scope,$http){
	$scope.def = {groupField:[]};
	
	if(selectorDefId){
		$http.post(__ctx + "/form/selectorDef/selectorData",{id:selectorDefId})
			 .success(function(data){
				 if(data){
					 $scope.def = data;
					 $scope.def.groupField = eval("("+ $scope.def.groupField +")");
					 $scope.history = angular.copy($scope.def);
				 }
			 });
	}
	
	$scope.save = function(){
		var obj = angular.copy($scope.def);
		obj.groupField = angular.toJson(obj.groupField);
		$http.post(__ctx + "/form/selectorDef/save",obj)
			 .success(function(data){
				 if(data.result){
					 $.topCall.confirm("温馨提示",data.message+',是否继续操作',function(r){
						if(r){
							window.location.reload(true);
						}else{
							HT.window.refreshParentGrid();
							HT.window.closeEdit(true,'selectorDef');
						}
					 });
				 }
				 else{
					 $.topCall.error(data.message);
				 }
			 });
	}
	
	/**
	 * 关闭
	 */
	$scope.close = function(){
		HT.window.closeEdit(true,'selectorDef');
		if($scope.isUnchanged){
			HT.window.refreshParentGrid();
		}
	}
	
	/**
	 * 是否有修改
	 */
	$scope.isUnchanged = function(){
		return $scope.history&&angular.equals($scope.history,$scope.def);
	}
	
	$scope.addItem = function(){
		$scope.def.groupField.push({});
	}
	
	$scope.removeItem = function(item){
		$scope.def.groupField.remove(item);
	}
	
	$scope.removeAll = function(){
		$scope.def.groupField = [];
	}
}]);