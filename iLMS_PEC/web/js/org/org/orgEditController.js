
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	$scope.authId = authId;
	$scope.authOrgId = authOrgId;
	if(!$scope.data){
		$scope.data = {parentOrgName:parentOrgName,parentId:parentId,isAdd:true,demId:demId};
	}
	
	$scope.save = function(){
		if (!$scope.form.$valid) return;
		var rtn = baseService.post("save", $scope.data);
		rtn.then(function(data) {
			if (data.result == 1){
				$.topCall.toast("提示信息",$scope.data.isAdd? "添加成功！":"保存成功！");
				if($scope.authId){//分级组织管理
					var param = {"authId":$scope.authId,"orgId":$scope.authOrgId};
					window.parent.refreshNode(param);
				}else{
					window.parent.refreshNode($scope.data.demId);
				}
				if($scope.data.isAdd){
					$scope.data = {parentOrgName:parentOrgName,parentId:parentId,isAdd:true,demId:demId,authId:authId};
				}
			}else{
				$.topCall.error(data.message, data.cause);
			}
		})
	}
 
	
	$scope.addRow=function(classVar){
		$scope.data[classVar +"List"].push(angular.copy(obj[classVar]))
	}
	
}]);