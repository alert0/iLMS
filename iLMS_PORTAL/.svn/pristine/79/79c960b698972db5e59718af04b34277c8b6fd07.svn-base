
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	$scope.sysParams = [];
	var data={
	}
	
	$scope.addRow=function(classVar){
		$scope.data[classVar +"List"].push(angular.copy(obj[classVar]));
	}
	
	$scope.$on("beforeSaveEvent",function(){
		$scope.data = [];
		angular.forEach($scope.sysParams,function(obj, idx){
			$scope.data.push({alias:obj.alias,value:obj.value});
		});
	});
	
	$scope.$on("afterSaveEvent",function(event,data){
		HT.window.refreshParentGrid();
		if(data.r){
		  window.location.reload();
		}else{
			HT.window.closeEdit();
		}
	});
	
	$scope.save = function(){
		if (!$scope.form.$valid) return;
		$scope.data = [];
		angular.forEach($scope.sysParams,function(obj, idx){
			$scope.data.push({alias:obj.alias,value:obj.value});
		});
		var rtn = baseService.post($scope.saveParams, $scope.data);
		rtn.then(function(data) {
			if (data.result == 1){
				$.topCall.success(data.message);
			}else{
				$.topCall.error(data.message, data.cause);
			}
		})
	}
	
	
	var type = $.getParameter("type"), id = $.getParameter("id"), url = "";
	$scope.saveParams = "saveParams?id="+id;
	if(type == 1){
		url = __ctx+"/system/sysUserParams/getByUserId?id="+id;
	}else if(type ==2){
		url = __ctx+"/system/sysOrgParams/getByOrgId?id="+id;
		$scope.saveParams = __ctx+"/system/sysOrgParams/saveParams?id="+id;
	}
	
	
	baseService.get(__ctx+"/system/sysParams/getByType?type="+type).then(function(data){
		if(data){
			$scope.sysParams = data;
			angular.forEach($scope.sysParams,function(obj, idx){
				if(obj.ctlType=="select"||obj.ctlType=="radio"||obj.ctlType=="checkbox"||obj.ctlType=="dic"||obj.ctlType=="customdialog"){
					obj.json = angular.fromJson(obj.json);
				}
			});
			getUserOrgParams();
		}
	});
	
	$scope.showCustomDialog = function(obj,alias,resultField,isSingle) {
		CustomDialog.openCustomDialog(alias, function(data, dialog) {
			dialog.dialog('close');
			var retVal = [];
			for(var i=0;i<data.length;i++){
				retVal.push(data[i][resultField]);
			}
			obj.value= retVal.join(",");
			AngularUtil.setData($scope);
		},{selectNum:isSingle?1:-1});
	};
	
	function getUserOrgParams(){
		baseService.get(url).then(function(data){
			$scope.data = data;
			angular.forEach($scope.data,function(obj, idx){
				angular.forEach($scope.sysParams,function(obj2, idx2){
					if(obj.alias == obj2.alias){
						obj2.value = obj.value;
					}
				});
			});
		});
	}
}]);