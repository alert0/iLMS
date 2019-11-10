
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	$scope.organList = [];
	
	$scope.init = function() {
		baseService.post(__ctx +"/org/sysOrgan/listJson").then(function(data) {
			if(data&&data.rows.length>0){
				$scope.organList = data.rows;
			}
		});
	}
	
	$scope.addRow=function(classVar){
		$scope.data[classVar +"List"].push(angular.copy(obj[classVar]))
	}
	
	$scope.$on("beforeSaveEvent", function(event, data) {// 提交前做数据整理
		try {
			event.currentScope.data.demName = event.currentScope.data.demName.replace(/\s/g,'');
		} catch (e) {}
	});
	
	// 保存成功
	$scope.$on("afterSaveEvent",function(ev,data){
		if(data.r){
			window.location.reload();
		}
		else{
			HT.window.refreshParentGrid();
			HT.window.closeEdit(true,'grid');
		}
	});
	
}]);