var app = angular.module("app", [ 'base', 'formDirective', 'arrayToolService' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', '$rootScope',function($scope, baseService, ArrayToolService,$rootScope) {
	$scope.ArrayTool = ArrayToolService;
	
	$scope.userRule = {};
	$scope.userRule.nodeType=nodeType;
	$scope.userRule.calcs = [];
	$scope.conditionList=[];
	$scope.prop={};
	
	//要初始化
	if(window.passConf){
		$scope.userRule=window.passConf;
	}
	
	baseService.post(__ctx + "/system/util/getBean?beanId=nodeUserPluginList").then(function(data) {
		$scope.nodeUserPluginList = data;
	});
	
	$scope.addCalc = function() {
		$scope.userRule.calcs.push({
			pluginType : "cusers",
			extract : "no",
			logicCal : "or",
			source : "start",
			vars:"",
			description : "发起人",
			nodeType: nodeType
		});
	};
	
	/**
	 * 点击选择按钮处理，这里使用动态调用对话框。
	 * 选择器命名修改成：
	 * pluginType +Selector,参数为：calc 对象。
	 */
	$scope.selector = function(calc){
		eval(calc.pluginType+"Selector(calc);");
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
	
	$scope.preview = function(){
		var passConf = getUserRuleData();
		var callback = function(data, dialog) {
			dialog.dialog('close');
		};
		DialogUtil.openDialog(__ctx+"/flow/node/condition/preview", "预览", passConf, callback);
	};
	
	$scope.calcTypeChange = function(calc){
		calc.description="";
		if(calc.pluginType== "approver"){//流程实例审批人，在此处设置description
			calc.description="流程实例审批人";
		}
	};
	
	/**
	 * 以下代码是可以扩展部分。
	 */
	function cusersSelector(calc){
		var passConf = {};
		passConf.calc=CloneUtil.shallow(calc);
		var callback = function(data, dialog) {
			dialog.dialog('close');
			jQuery.extend(calc, data);
			!$rootScope.$$phase && $rootScope.$digest();
		};
		DialogUtil.openDialog(__ctx+"/flow/node/usercalc/cusersSelector?nodeId="+nodeId+"&defId="+defId, 
				"用户选择器", passConf, callback);
	}
	
	function scriptSelector(calc){
		var passConf = {};
		passConf.calc=CloneUtil.shallow(calc);
		var callback = function(data, dialog) {
			dialog.dialog('close');
			jQuery.extend(calc, data);
			!$rootScope.$$phase && $rootScope.$digest();
		};
		DialogUtil.openDialog(__ctx + "/flow/node/usercalc/scriptDialog?defId="+defId+"&nodeId="+nodeId+"&parentFlowKey="+parentFlowKey, "脚本对话框",passConf,
				callback,800,450);
	}
	
			
	function hrScriptSelector(calc){
		var passConf = {};
		passConf.calc=CloneUtil.shallow(calc);
		var callback = function(data, dialog) {
			dialog.dialog('close');
			calc.description = "[人员脚本]"+ data.desc;
			calc.scriptId = data.scriptId;
			calc.params = data.params;
			jQuery.extend(calc, data);
			!$rootScope.$$phase && $rootScope.$digest();
		};
		ConditionScript.showDialog({
			defId : defId,
			flowKey:flowKey,
			nodeId : nodeId,
			calc:calc,
			type:2
		}, callback);
	}
	
	function sameNodeSelector(calc){
		var passConf = {};
		passConf.calc=CloneUtil.shallow(calc);
		var callback = function(data, dialog) {
			dialog.dialog('close');
			jQuery.extend(calc, data);
			!$rootScope.$$phase && $rootScope.$digest();
		};
		DialogUtil.openDialog(__ctx+"/flow/node/condition/sameNodeSelector?defId="+defId+"&nodeId="+nodeId, "节点选择", passConf, callback);
	}
	
	
} ]);