var app = angular.module('app', [ 'formDirective', 'arrayToolService', 'ui.codemirror' ]);
app.controller("ctrl", [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayTool) {
	$scope.ArrayTool = ArrayTool;

	$scope.init = function() {
		$scope.data = {};
		$scope.data.filterType = 1;
		$scope.parentScope = window.parentWindow.getScope();
		$scope.metafields = $scope.parentScope.jsonObject.fields;
		$scope.colPrefix = $scope.parentScope.jsonObject.colPrefix;
		$scope.data.filterInitSql = '';//初始化时的脚本
		$scope.isEditabled = true;
		if($scope.parentScope.editFilter){
			$scope.isEditabled = false;
			$scope.data.filter = $scope.parentScope.editFilter.condition;
			$scope.data.filterType = $scope.parentScope.editFilter.type;
			$scope.data.name = $scope.parentScope.editFilter.name;
			$scope.data.key = $scope.parentScope.editFilter.key;
		}
		
		// 获取对应的常量
		baseService.postForm(__ctx + "/system/util/getBean", {
			beanId : "queryViewComVarList"
		}).then(function(data) {
			$scope.comVarList = data;
		});

	};

	$scope.clickVar = function() {
		$scope.$broadcast('CodeMirror', function(CodeMirror) {
			CodeMirror.replaceSelection($scope.selectVar);
		});
	};
	
	//当改变的脚本类型是初始化的脚本类型时，将过滤脚本设置为初始化时的脚本，否则置空
	$scope.changeFilterType = function(){
		if($scope.data.filterType == $scope.data.filterInitType){
			$scope.data.filter = $scope.data.filterInitSql;
		}else{
			if($scope.data.filterType > 1){
				$scope.data.filter = " ";
			}
		}
	}

	//保存表单时，校验表单是否通过，未通过弹出提示信息。主要是处理当未通过验证的表单不在当前显示的tab时，用户点保存按钮保存不了，也无任何提示。
	$scope.saveFormValid = function(){
		if (!$scope.form.$valid){
			$.topCall.error("表单验证未通过，请检查修改，确认后再提交！");
			return false;
		};
		//key不能重复
		if($scope.isEditabled){
			for(var i=0;i<$scope.parentScope.filterFields.length;i++){
				if($scope.parentScope.filterFields[i].key == $scope.data.key){
					$.topCall.error("Key已被使用，请重新填写！");
					$scope.$apply(function() {
						$scope.data.key = "";
					});
					return false;
				}
			}
		}
		// 处理过滤条件
		if ($scope.data.filterType == 1 && filterInitPoint) {
			$scope.data.filter = getData();
		}
		return true;
	}
	
} ]);