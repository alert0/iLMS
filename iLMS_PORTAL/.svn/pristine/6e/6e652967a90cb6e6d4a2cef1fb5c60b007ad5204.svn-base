var app = angular.module('app', [ 'base', 'commonListService', 'CustomQueryService', 'SysDataSourceService','formDirective' ]);
app.controller("Controller", [ '$scope', 'baseService', 'CommonListService', 'CustomQuery', 'SysDataSource', function($scope, BaseService, CommonListService, CustomQuery, SysDataSource) {
	$scope.CommonList = CommonListService;

	$scope.prop = {};// 初始化
	$scope.prop.needPage = 1;
	$scope.prop.pageSize = 10;
	$scope.prop.isTable = 1;
	$scope.prop.dsalias = "LOCAL";// 本地数据源

	// 如果id不为空，获取初始化数据,利用发请求的方式
	if (id != "") {
		CustomQuery.detail({
			id : id
		}, function(data) {
			$scope.prop = data;
			if (!data.resultfield)
				data.resultfield = "[]";
			if (!data.sortfield)
				data.sortfield = "[]";
			if (!data.conditionfield)
				data.conditionfield = "[]";
			$scope.prop.resultfield = JSON.parse(data.resultfield);

			$scope.prop.sortfield = JSON.parse(data.sortfield);
			$scope.prop.conditionfield = JSON.parse(data.conditionfield);
		});
	}

	// 获取数据源池，新建才可以选择
	if (id == "") {
		$scope.prop.id = "";
		$scope.prop.sqlBuildType = "0";
		SysDataSource.getDataSourcesInBean(function(data) {
			$scope.dataSourcesInBean = data;
		});
	}

	// 获取表或视图列表
	$scope.getByDsObjectName = function() {
		var params = {
			dsalias : $scope.prop.dsalias,
			isTable : $scope.prop.isTable,
			objName : $scope.prop.objName
		};
		CustomQuery.getByDsObjectName(params, function(data) {
			$scope.tableOrViewList = data;
		});
	}

	$scope.showSettingDialog = function() {
		if ($scope.prop.objName == null) {
			$.topCall.error("请选择目标表或视图");
			return;
		}

		var title = "未命名";
		if ($scope.prop.name != null) {
			title = $scope.prop.name;
		}
		var url = __ctx + '/form/customQuery/customQuerySetting';
		$.topCall.dialog({
			src : url,
			base : {
				title : '' + title + "-设置列",
				width : 1020,
				height : 580,
				modal : true,
				resizable : true,
				passConf : {
					prop : $scope.prop,
					parentScope : getScope()
				}
			}
		});
	}

	$scope.save = function() {
		if (!$scope.form.$valid)
			return;
		if ($scope.prop.objName == null) {
			$.topCall.error("请选择目标表或视图");
			return;
		}
		CustomQuery.save($scope.prop, function(data) {
			if (data.result == 1) {
				$.topCall.confirm("温馨提示", data.message + ',是否继续操作', function(r) {
					if (r) {
						window.location.reload(true);
					} else {
						HT.window.closeEdit(true, 'grid');
					}
				});
			} else {
				$.topCall.error(data.message, data.cause);
			}
		});
	}

	// 视图和表选择数组
	$scope.isTableList = [ {
		key : '视图',
		value : 0
	}, {
		key : "表",
		value : 1
	} ];
} ]);