
var app = angular.module('app', ['formDirective','arrayToolService', 'ui.codemirror']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	$scope.data = {};
	$scope.data.colType = 0;
	$scope.data.showEffect = 0;
	$scope.data.dataMode = 0;
	$scope.data.isPublic = 0;
	$scope.data.supportRefesh = 0;
	$scope.data.needPage = 0;
	
	var obj={
	}
	
	$scope.addRow=function(classVar){
		$scope.data[classVar +"List"].push(angular.copy(obj[classVar]))
	}
	
	
	
	
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
	
	// 保存前 base64编码
	$scope.$on("beforeSaveEvent",function(ev,data){
		$scope.data.templateHtml = $.base64.encode($scope.data.templateHtml_temp,"utf-8");
	});
	
	
	// base64解码
	$scope.$on("afterLoadEvent",function(ev,data){
		data.templateHtml_temp = $.base64.decode(data.templateHtml,"utf-8");
	});
	
	
	$scope.showSetParamDialog = function() {
		var url = __ctx + '/portal/sysIndexColumn/sysIndexColumn/sysIndexColumnSetParam';
		$.topCall.dialog({
			src : url,
			base : {
				title : "参数设置",
				width : 820,
				height : 480,
				modal : true,
				resizable : true,
				passConf : {
					dataParam : $scope.data.dataParam,
					callBack : function(data){
						$scope.$apply(function() {
							$scope.data.dataParam = data;
						});
					}
				}
			}
		});
	}
	
	$scope.selectQuery = function() {
		var callBack = function(data, dialog) {
			dialog.dialog('close');
			$scope.$apply(function() {
				if(data.length>0){
					$scope.data.dataFrom = data[0].ALIAS_;
				}
			});
		};

		CustomDialog.openCustomDialog("customQuerySelector", callBack);
	};
	
}]);