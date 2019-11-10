//nodeDefJson{alias,params,outPutScritp:outPutScritp}
//var flowParam={'defId':'${defId}','nodeId':'${nodeId}',includeBpmConstants:true};
var app = angular.module('app',['base','formDirective','ServiceSetService','ui.codemirror']); 
app.controller("webServiceController",['ServiceSet','ServiceParam','$scope',function(ServiceSet,ServiceParam,$scope){
	if(bpmPluginDefJson){
		$scope.nodeDefJson = eval('(' + bpmPluginDefJson + ')');
		$scope.alias = $scope.nodeDefJson.alias;
		$scope.nodeDefJson.params = eval('(' +$scope.nodeDefJson.params +')');
		ServiceSet.getByAlias($scope.alias,function(data){
			$scope.serviceSet = data.serviceSet;
		})
	}
	$scope.test="testess";
	$scope.save = function(){
		if(!$scope.myForm.$valid){
			$.topCall.error("表单校验不通过！");
			return;
		}
		$scope.nodeDefJson.pluginType = "webService";
		flowParam.jsonStr = JSON.stringify($scope.nodeDefJson);
		
		var url = __ctx + "/flow/node/autoTaskPluginSave";
		
		$.post(url,flowParam,function(data){
			var resultMessage=new com.hotent.form.ResultMessage(data);
        	if(resultMessage.isSuccess()){
        		$.topCall.success("webService配置成功!");
        		window.parent.passConf();
        	}else{
        		$.topCall.error(resultMessage.getMessage(),resultMessage.getCause()); 
        	}
		}); 
	};
	
	$scope.chooseServiceSet = function(){
		var dialog = {};
		var def = {
			passConf : '',
			title : '选择webService',
			width : 600,
			height : 500,
			modal : true,
			resizable : false,
			iconCls : 'icon-collapse',
			buttons : [
					{
						text : '确定',
						handler : function(e) {
							var record = dialog.innerWin.$("#userGridList").datagrid('getChecked');
							if(record.length == 1){
								$scope.$apply(function(){
									$scope.serviceSet =record[0];
								});
								$scope.nodeDefJson ={alias:$scope.serviceSet.alias,name:$scope.serviceSet.name}
								$scope.initParams($scope.serviceSet.id);
								dialog.dialog('close');
							}
						}
					}, {
						text : '取消',
						handler : function() {
							dialog.dialog('close');
						}
					} ]
		};

		dialog = $.topCall.dialog({
					src : __ctx+ '/system/serviceSet/serviceSetDialog',
					base : def
				});
		return this;
	}
	
	
	$scope.testServiceSet = function(){
		var dialog = {};
		var def = {
			passConf : $scope.serviceSet.id,
			title : '调用服务',
			width : 800,
			height : 600,
			modal : true,
			resizable : false,
			iconCls : 'icon-collapse'    				
		};

		$.topCall.dialog({src : __ctx+ '/system/serviceSet/serviceSetInvoke', base : def});
	}
	
	$scope.getFlowData = function(){
		$.post(__ctx+"/flow/node/varTree",flowParam,function(result){
			var boData=[];
			for (var i = 0,bo; bo=result[0].children[i++];) {
				for (var j = 0,field; field=bo.children[j++];){
					if(field.nodeType=="sub")continue;
					field.tableName =bo.desc;
					boData.push(field);
				}
			}
			$scope.flowVar = result[1].children;
			$scope.boData = boData;
			$scope.$digest();
		});
	}
	$scope.getFlowData();
	
	
	$scope.initParams = function(serviceSetId){
		if(serviceSetId){
			$scope.serviceSetId = serviceSetId;
			ServiceParam.list(serviceSetId,function(data){
				if(data.result){
					$scope.nodeDefJson.params = data.params;
				}
			});
		}
	}
	
	$scope.selectConditionScript = function() {
		ConditionScript.showDialog({ defId : flowParam.defId, nodeId : flowParam.nodeId }, 
			function(data) {
				$scope.insetCode(data); 
			}
		);
	}
	
	$scope.selectScript = function(){
		new ScriptSelector(function(script){
			$scope.insetCode(script);
		 }).show();
	}
	
	$scope.redoText = function(){
		$scope.$broadcast('CodeMirror',function(CodeMirror){
			CodeMirror.doc.redo()
		});
	}
	
	$scope.insetCode = function(str){
		$scope.$broadcast('CodeMirror',function(CodeMirror){
			CodeMirror.replaceSelection(str);
		});
	} 
	$scope.setVariable = function(event, treeId, node){
		var keyStr = node.name;
		var parentNode = node.getParentNode();
		
		// 子表情况做提示
		 if (node.nodeType == 'sub'){
			keyStr = "/* "+parentNode.name+".getSubByKey('"+node.name+"')  获取子表,return List<BoData> */";
		 }// 主表bo
		 else if(parentNode.nodeType == 'main'){
			keyStr = parentNode.path+'.getValByKey("'+node.name+'") /*数据类型：'+node.dataType+'*/';
		}else if(parentNode.nodeType == 'sub'){
			var mainTableName = parentNode.getParentNode().name;
			keyStr = "/* "+mainTableName+".getSubByKey('"+parentNode.name+"')  获取子表数据 ，返回数据：return List<BoData> 子表字段：”"+node.name+"“ 请根据实际情况处理子表数据的获取*/";
		}else if(node.nodeType == 'var'){
			keyStr =node.name;
		}else return ;
		 $scope.varTree.hideMenu();
		 $scope.insetCode(keyStr);
	}
	
	$scope.varTree = new ZtreeCreator('varTree', __ctx+"/flow/node/varTree")
	.setDataKey({ name :'desc'})
	.setCallback({onClick : $scope.setVariable})
	.makeCombTree("tempTree")
	.initZtree({defId:flowParam.defId, nodeId:flowParam.nodeId, includeBpmConstants:true}, 1);
	
}]);