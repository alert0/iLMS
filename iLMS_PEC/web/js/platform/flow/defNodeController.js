var nodeApp = angular.module('nodeApp', [ 'formDirective', 'arrayToolService', 'base' ]);

nodeApp.controller("defNodeController", [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayToolService) {
	$scope.ArrayTool = ArrayToolService;
	$scope.nodeConditions = {};
	$scope.nodeConditions.data = [];
	$scope.params = {};

	// 如果id不为空，获取初始化数据,利用发请求的方式
	/**
	 * {defId:'${bpmDefinition.defId}',parentFlowKey:'${parentFlowKey}'};
	 */
	$scope.init = function(params) {
		$scope.params = params;
		var url = __ctx + '/flow/node/getNodesJson';
		var defer = baseService.postForm(url, params);
		defer.then(function(data) {
			for (var i = 0; i < data.length; i++) {
				$scope.nodeConditions.data[i]
			}
			$scope.nodeConditions = data;
		});
	}

	$scope.saveAllNodeJson = function() {
		var url = __ctx + '/flow/node/saveAllUserCondition';
		var success = true;
		var i = 0;
		for (nodeName in $scope.nodeConditions) {
			
			$.each($scope.nodeConditions[nodeName], function(i, node) {
				groupNo = i + 1;
			});
			
		}
		var nodesJson = angular.toJson($scope.nodeConditions);
		$scope.params.nodesJson = nodesJson;
		var params = $scope.params;

		var defer = baseService.postForm(url, params);

		defer.then(function(data) {
			if (data.result == 1) {
				$.topCall.success("全部节点人员条件配置成功!");
			} else {
				$.topCall.error(data.message);
			}
		});
	}

	// 保存节点的 人员配置json
	$scope.saveNodeJson = function(nodeId) {
		var json = $scope.nodeConditions[nodeId];
		var isRepeat = false;
		var success = true;
		$.each(json, function(i, node) {
			groupNo = i + 1;
		});
		

		var nodeJson = angular.toJson(json);
		var url = __ctx + '/flow/node/nodeUserConditionSave';

		var params = $scope.params;
		params.nodeJson = nodeJson;
		params.nodeId = nodeId;

		var defer = baseService.postForm(url, params);

		defer.then(function(data) {
			if (data.result == 1) {
				$.topCall.success(nodeId + "节点人员条件配置成功!");
			} else {
				$.topCall.error(nodeId + "节点人员条件配置失败!");
			}
		});
	}

	// 修改某节点的某批次的json
	$scope.addNodeUserCondition = function(nodeType, nodeId, i) {
		var nodeJsonArray = $scope.nodeConditions[nodeId];
		var userRule;
		if (i != undefined) {
			userRule = angular.copy(nodeJsonArray[i]);
			userRule.nodeType = nodeType;
		}
		var dialog;
		var def = {
			passConf :userRule ,
			title : '节点人员条件',
			width : 950,
			height : 580,
			modal : true,
			resizable : true,
			iconCls : 'icon-collapse',
			buttonsAlign : 'center',
			buttons : [ {
				text : '确定',
				iconCls : 'fa fa-check-circle',
				handler : function(e) {
					dialog.dialog('close');
					var win = dialog.innerWin;
					var data = win.getUserRuleData();
					if (!data)
						return;
					$scope.$apply(function() {
						if (i != undefined) {
							nodeJsonArray[i] = data;
						} else if (nodeJsonArray && nodeJsonArray.length > 0) {
							nodeJsonArray.push(data);
						} else {
							var userRules = [];
							userRules.push(data);
							$scope.nodeConditions[nodeId] = userRules;
						}
					});
				}
			}, {
				text : '取消',
				iconCls : 'fa fa-times-circle',
				handler : function() {
					dialog.dialog('close');
				}
			} ]
		};
		var url = __ctx + '/flow/node/condition/conditionEdit?defId=' + $scope.params.defId + '&nodeId=' + nodeId + "&nodeType=" + nodeType;
		dialog = $.topCall.dialog({
			src : url,
			base : def
		});
	}

	$scope.deleteAttr = function(nodeId, index) {
		var obj = $scope.nodeConditions[nodeId];
		removeObjFromArr(obj, index);
	}
} ]);