var app = angular.module("app", [ 'base', 'formDirective', 'ui.codemirror' ]);
app.controller('ctrl', [ '$scope', 'baseService','$rootScope', function($scope, baseService,$rootScope) {
	
	$scope.init = function(params) {
		$scope.data={};
		$scope.editingRight={};//在编辑中的权限
	};
	
	$scope.insetCode = function(str) {
		$scope.$broadcast('CodeMirror', function(CodeMirror) {
			CodeMirror.replaceSelection(str);
		});
	};
	
	/**
	 * 编辑的对象改变了
	 */
	$scope.editingChange = function(tableName){
		if($scope.data[tableName]==null){
			var json = {};
			json.nodeId=__param.nodeId;
			json.parentDefKey=__param.parentDefKey;
			json.rightType="curUser";
			json.tableName=tableName;
			json.script="return sql;";
			$scope.data[tableName]=json;
		}
		$scope.editingRight = $scope.data[tableName];
	};
	
	$scope.selectScript = function(){
		new ScriptSelector(function(script){
			$scope.insetCode(script);
		 }).show();
	};
	
	/**
	 * 清除权限
	 */
	$scope.clean = function(){
		baseService.post("saveSub?defId="+__param.defId+"&nodeId="+__param.nodeId+"&parentDefKey="+__param.parentDefKey,{}).then(function(data){
			if (data.result == 1) {
				$.topCall.success("清除成功",function(){
					window.location.reload();
				});
			} else {
				if (data.cause) {
					$.topCall.errorStack(data.message, data.cause, "错误信息");
				} else {
					$.topCall.error(data.message);
				}
			}
		});
	};
	
	$scope.$on("beforeSaveEvent",function(event,data){
		if(jQuery.isEmptyObject($scope.data)){
			$.topCall.error("请至少配置一个子表权限");
			data.pass=false;
		}
	});
} ]);