var app = angular.module('app', [ 'base', 'CustomQueryService', 'arrayToolService', 'formDirective' ]);
app.controller("Controller", [ '$scope', 'baseService', 'CustomQuery', 'ArrayToolService', function($scope, BaseService, CustomQuery, ArrayToolService) {
	$scope.ArrayTool = ArrayToolService;
	var params = {};

	// 初始化页面传来的信息
	$scope.prop = CloneUtil.deep(window.passConf.prop);

	// 处理between
	for ( var i in $scope.prop.conditionfield) {
		var column = $scope.prop.conditionfield[i];
		if (column.condition == 'BETWEEN') {
			var strs = column.defaultValue.split("|");
			column.defaultValue = strs[0];
			column.endDate = strs[1];
		}
	}
	$scope.prop.sqlBuildType = $scope.prop.sqlBuildType + "";

	var params = {
		dsalias : $scope.prop.dsalias,
		isTable : $scope.prop.isTable,
		objName : $scope.prop.objName
	};
	CustomQuery.getTable(params, function(data) {
		$scope.table = data.table;
	});

	$scope.addColumns = function() {
		if ($scope.prop.conditionfield == null) {
			$scope.prop.conditionfield = [];
		}
		if ($scope.prop.resultfield == null) {
			$scope.prop.resultfield = [];
		}
		if ($scope.prop.sortfield == null) {
			$scope.prop.sortfield = [];
		}

		// 检查是否选择了列
		for ( var i in $scope.table.columnList) {
			var c = $scope.table.columnList[i];
			if (c.selected == 'yes')
				break;
			if (i == $scope.table.columnList.length - 1) {
				$.topCall.warn('请选择左边的列');
				return;
			}
		}

		// 加到条件字段的条件选择
		if ($('.tabs-selected').find('.tabs-title').html() == '条件字段') {
			for ( var i in $scope.table.columnList) {
				var c = $scope.table.columnList[i];

				if (c.selected != 'yes')
					continue;
				var column = {};
				column.field = c.fieldName;
				column.comment = c.comment;
				column.condition = 'EQ';
				column.dbType = c.columnType;
				column.defaultType = '1';
				column.defaultValue = '';

				$scope.prop.conditionfield.push(column);
			}
			// 去除重复项
			$scope.prop.conditionfield.unique(function(a, b) {
				return a.field == b.field;
			});
		}

		// 加到返回字段
		if ($('.tabs-selected').find('.tabs-title').html() == '返回字段') {
			for ( var i in $scope.table.columnList) {
				var c = $scope.table.columnList[i];

				if (c.selected != 'yes')
					continue;
				var column = {};
				column.field = c.fieldName;
				column.comment = c.fieldName;
				column.AggFuncOp = '';// 合计函数运算符

				$scope.prop.resultfield.push(column);
			}
			// 去除重复项
			$scope.prop.resultfield.unique(function(a, b) {
				return a.field == b.field;
			});
		}

		// 加到排序字段
		if ($('.tabs-selected').find('.tabs-title').html() == '排序字段') {
			for ( var i in $scope.table.columnList) {
				var c = $scope.table.columnList[i];

				if (c.selected != 'yes')
					continue;
				var column = {};
				column.field = c.fieldName;
				column.sortType = "asc";

				$scope.prop.sortfield.push(column);
			}
			// 去除重复项
			$scope.prop.sortfield.unique(function(a, b) {
				return a.field == b.field;
			});
		}

	}

	// 选择条件字段时脚本sql常用脚本
	$scope.selectScript_column = function(column) {
		new ScriptSelector(function(script) {
			$scope.$apply(function() {
				if (!column.defaultValue)
					column.defaultValue = "";
				column.defaultValue += script;
			});
		}).show();
	};

	// 选择自定义sql常用脚本
	$scope.selectScript_diySql = function() {
		new ScriptSelector(function(script) {
			$scope.$apply(function() {
				if (!$scope.prop.diySql)
					$scope.prop.diySql = "";
				$scope.prop.diySql += script;
			});
		}).show();
	};
	
	 $scope.selectVar = function(){
    	if($scope.customVar){
    		if(!$scope.prop.diySql){
    			$scope.prop.diySql = "";
    		}
    		$scope.prop.diySql += " " +  $scope.customVar;
    	}
    }

	// 保存按钮
	$scope.save = function() {
		for ( var i in $scope.prop.conditionfield) {
			var column = $scope.prop.conditionfield[i];
			if (column.dbType == 'date' && column.condition == 'BETWEEN') {
				column.defaultValue += "|" + column.endDate;
				delete column.endDate;
			}
		}

		window.passConf.parentScope.prop = CloneUtil.deep($scope.prop);
		window.selfDialog.dialog('close');
	}

	//运算条件数组-number
	$scope.number_opList=[
		{
			key:'等于',
			value:'EQ'
		},
		{
			key:"大于等于",
			value:'GE'
		},
		{
			key:"大于",
			value:'GT'
		},
		{
			key:"小于",
			value:'LT'
		},
		{
			key:"小于等于",
			value:'LE'
		},
		{
			key:"in",
			value:'IN'
		}
	];
  	
	//运算条件数组-varchar
	$scope.string_opList=[
		{
			key:'等于',
			value:'EQ'
		},
		{
			key:"like",
			value:'LK'
		},
		{
			key:"likeEnd",
			value:'LFK'
		},
		{
			key:"in",
			value:'IN'
		}
	];
	
	//运算条件数组-日期
	$scope.date_opList=[
		{
			key:'等于',
			value:'EQ'
		},
		{
			key:"between",
			value:'BETWEEN'
		},
		{
			key:"大于等于",
			value:'GE'
		},
		{
			key:"小于等于",
			value:'LE'
		}
	];

	// 值来源数组
	$scope.value_sourceList = [ {
		key : '参数传入',
		value : '1'
	}, {
		key : "固定值",
		value : '2'
	}, {
		key : "脚本",
		value : '3'
	} ];

	// 返回字段合计函数数组
	$scope.agg_func_opList = [ {
		key : '',
		value : ''
	}, {
		key : 'sum',
		value : 'sum'
	}, {
		key : "max",
		value : 'max'
	}, {
		key : "min",
		value : 'min'
	} ];

	// 排序字段升序还是降序
	$scope.sort_typeList = [ {
		key : '升序',
		value : 'asc'
	}, {
		key : '降序',
		value : 'desc'
	} ];
} ]);