var app = angular.module("app", [ 'base', 'formDirective', 'arrayToolService', 'ui.codemirror' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayToolService) {
	$scope.init = function() {
		$scope.data = window.passConf.calc;
		// 初始化
		if (window.passConf && window.passConf.code) {
			$scope.code = window.passConf.code;
			$scope.description = window.passConf.description;
		}
		if ($scope.data && $scope.data.script) {
			$scope.code = $scope.data.script;
			$scope.description =$scope.data.description;
		}
		$scope.varTree = new ZtreeCreator('varTree', __ctx + "/flow/node/varTree").setDataKey({
			name : 'desc'
		}).setCallback({
			onClick : $scope.setVariable
		}).makeCombTree("tempTree").initZtree({
			defId : defId,
			nodeId : nodeId,
			flowKey:flowKey,
			parentFlowKey:parentFlowKey,
			includeBpmConstants : true
		}, 1);
	};

	$scope.setVariable = function(event, treeId, node) {
		var keyStr = node.name;
		var parentNode = node.getParentNode();

		// 子表情况做提示
		if (node.nodeType == 'sub') {
			keyStr = "/* " + parentNode.name + ".getSubByKey('" + node.name + "')  获取子表,return List<BoData> */";
		}// 主表bo
		else if (parentNode.nodeType == 'main') {
			keyStr = parentNode.path + '.getValByKey("' + node.name + '") /*数据类型：' + node.dataType + '*/';
		} else if (parentNode.nodeType == 'sub') {
			var mainTableName = parentNode.getParentNode().name;
			keyStr = "/* " + mainTableName + ".getSubByKey('" + parentNode.name + "')  获取子表数据 ，返回数据：return List<BoData> 子表字段：”" + node.name + "“ 请根据实际情况处理子表数据的获取*/";
		} else if (node.nodeType == 'var') {
			keyStr = node.name;
		} else
			return;
		$scope.varTree.hideMenu();
		$scope.insetCode(keyStr);
	};

	$scope.insetCode = function(str) {
	
		$scope.$broadcast('CodeMirror', function(CodeMirror) {
			CodeMirror.replaceSelection(str);
		});
	};

	$scope.selectConditionScript = function(type) {
		ConditionScript.showDialog({
			defId : defId,
			flowKey:flowKey,
			nodeId : nodeId,
			type:type
		}, function(data) {
			$scope.insetCode(data.script);
		});
	};

	$scope.selectHrScript = function() {
		ConditionScript.showDialog({
			defId : defId,
			flowKey:flowKey,
			nodeId : nodeId,
			type:2
		}, function(data) {
			$scope.$apply(function(){
				$scope.code =data.script;
				$scope.description =data.desc||"[人员脚本]";
			});

		});
	};
	
	$scope.selectScript = function() {
		new ScriptSelector(function(script) {
			$scope.insetCode(script);
		}).show();
	};

	$scope.selectIdentity = function() {
		var callBack = function(data, dialog){
			dialog.dialog('close');
			if(!data||data.length<1)
				return;
			$scope.insetCode('scriptImpl.getSeqNo("'+data[0].alias+'");');
		};
		CustomDialog.openCustomDialog("identitySelector",callBack);
	};
} ]);