var app = angular.module('app', [ 'formDirective', 'formDefModel', 'arrayToolService' ]);
app.controller("ctrl", [ '$scope', 'baseService', 'ArrayToolService', 'formDefService', function($scope, baseService, ArrayTool, formDefService) {
	$scope.ArrayTool = ArrayTool;

	$scope.init = function() {
		$scope.field = window.passConf;

		// 获取自定义对话框列表
		baseService.postForm(__ctx + "/form/customDialog/getAll", {}).then(function(data) {
			$(data).each(function() {
				try {
					this.resultfield = JSON.parse(this.resultfield);
				} catch (e) {console.info(e)}
			});
			$scope.customDialogs = data;// 自定义对话框列表
			// 初始化当前对话框
			if ($scope.field.controlType != "customdialog")
				return;
			$($scope.customDialogs).each(function() {
				if (this.alias == $scope.field.controlContent.alias) {
					$scope.customDialog = this;// 当前选择的自定义对话框
				}
			});
		});
	};

	// 控件类型换做的js准备
	$scope.$watch('field.controlType', function(newValue, oldValue, scope) {
		if (oldValue == newValue)
			return;
		$($scope.controlTypes).each(function() {
			if (this.id == newValue) {
				$scope.field.controlTypeDesc = this.name;
			}
		});
		
		if (newValue == "select") {
			$scope.field.controlContent = [];
		} else if (newValue == "customdialog") {
			$scope.field.controlContent = {};
		}
	});

	// 自定义对话框别名更改
	$scope.$watch('field.controlContent.alias', function(newValue, oldValue, scope) {
		if (oldValue == newValue) return;
		
		$($scope.customDialogs).each(function() {
			if (this.alias == newValue) {
				$scope.customDialog = this;// 当前选择的自定义对话框
				$scope.field.controlContent.resultField = this.resultfield[0].field;
			}
		});
	});

	$scope.controlTypes = [ {
		id : "onetext",
		name : "单行文本框"
	}, {
		id : "select",
		name : "下拉框"
	}, {
		id : "customdialog",
		name : "自定义对话框"
	}, {
		id : "date",
		name : "日期选择器"
	} ];
} ]);