
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	$scope.isUrlDisabled = false;//url是否可以编辑
	var controllerScope = $('div[ng-controller="listController"]').scope();
	
	$scope.add = function() {
		var obj={'name': '','resUrl': ''};
		$scope.data.relResources.push(obj);
	};
	
	$scope.setDefaultUrl = function(url) {
		$scope.data.defaultUrl = url;
	};
	
	$scope.remove = function(item) {
		var aryUrl=$scope.data.relResources;
		for(var i=0;i<aryUrl.length;i++){
			var obj=aryUrl[i];
			if(obj==item){
				ArrayTool.del(i);
			}
		}
	};
	
	$scope.afterSave=function(data){
		$.topCall.success(data.message,function(){
			var obj=data.postData;
			var rtn=angular.fromJson(data.cause);
			//更新
			if(obj.id){
				parent.updateNode(obj.name);
			}
			else{
				parent.appendNode(rtn.id,obj.alias,obj.name,obj.hasChildren);
			}
			if($scope.isUrlDisabled){
				window.parent.parent.location.reload();
			}
		})  
	};	
}]);