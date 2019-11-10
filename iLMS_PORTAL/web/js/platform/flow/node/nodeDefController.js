var defApp = angular.module('bpmDefSetting',['base','formDirective','arrayToolService','nodeDefModel']);


defApp.controller("defCtrl",['$scope','baseService','ArrayToolService','nodeDefService',function(scope,baseService,ArrayToolService,nodeDefService){
	scope.ArrayTool = ArrayToolService;
	
	var url=__ctx+"/flow/node/getDefSetting?defId="+defId+"&topDefKey="+topDefKey;
	
	/**
	 * 记录父流程定义KEY和节点ID。
	 */
	scope.topDefKey=topDefKey;
	
	
	baseService.get(url).then(function(data){
		if(!data.bpmDefSetting.globalForm)data.bpmDefSetting.globalForm ={type:'INNER',formType:'pc'};
		if(!data.bpmDefSetting.globalMobileForm)data.bpmDefSetting.globalMobileForm ={type:'INNER',formType:'mobile'};
		if(!data.bpmDefSetting.instForm)data.bpmDefSetting.instForm ={type:'INNER',formType:'pc'};
		if(!data.bpmDefSetting.instMobileForm)data.bpmDefSetting.instMobileForm ={type:'INNER',formType:'mobile'};
		
		scope.bpmDefSetting = data.bpmDefSetting;
		scope.nodes = data.nodes;
		scope.nodeUserMap = data.nodeUserMap;
		scope.nodeBtnMap = data.nodeBtnMap;
		scope.nodeScriptMap = data.nodeScriptMap;
		scope.nodeRestfulMap = data.nodeRestfulMap;
		
		scope.isEditAllNode_forms = false;
		scope.isEditAllNode_btns = false;
		scope.isEditAllNode_nodeUser = false;
		scope.pluginList = nodeDefService.pluginConf;
		
		$('.collapse').collapse();
	});
	
	/**
	 * 保存设定。
	 */
	scope.save = function(){
     
		$.topCall.progress();
		var tempJson = angular.copy(scope.bpmDefSetting);
		delete tempJson.nodeForms;
		
		tempJson.parentDefKey=topDefKey;
		
		var param ={bpmDefSetting: angular.toJson(tempJson),
					nodeUserMap:angular.toJson(scope.nodeUserMap),
					nodeRestfulMap:angular.toJson(scope.nodeRestfulMap),
					defId:defId,
					topDefKey:topDefKey
		};
		baseService.postForm(__ctx+"/flow/node/saveDefConf",param).then(function(data){
			var rtn=data.result?'success':'error';
			$.topCall[rtn](data.message);
			$.topCall.closeProgress();
			scope.$broadcast ('nodeSetUpdate');
		},function(code){$.topCall.error("出现异常!code:"+code)})
	}
	
	/**
	 * 编辑所有的节点。
	 */
	scope.editAllNodes = function(name){
		if(scope["isEditAllNode_"+name]){
			scope["isEditAllNode_"+name] = false;
			$(".collapse.in").not(".node."+name).collapse('hide');
			//$(".collapse").not(".globalSetting").collapse('show');
		}else {
			$(".collapse.in").not(".node."+name).collapse('hide');
			scope["isEditAllNode_"+name] = true;
		}
	}
	
	scope.toEditNode = function(nodeId,nodeName,nodeType){
		scope.edittingNodeId = nodeId;
		scope.edittingNodeName = nodeName;
		scope.edittingTitle = '('+nodeName+'-'+nodeId+')';
		scope.edittingNodeType = nodeType;
		
		scope.isEditAllNode_forms = false;
		scope.isEditAllNode_btns = false;
		scope.isEditAllNode_nodeUser = false;
		scope.isEditAllNode_properties = false;
		scope.isEditAllNode_restful = false;
		$(".collapse.in").not(".node").not(".plugin").collapse('hide'); 
		$(".collapse.node").collapse('show'); 
		window.setTimeout(function(){$(".collapse.plugin").collapse('show');},100)
		
		if("signTask,userTask,start".indexOf(nodeType)== -1){
			$("#pluginstab").click();
		}
	}
	
	scope.checkHandler = function(handler){
		if(!handler) return;
		baseService.get(__ctx+"/flow/node/validHandler?handler="+handler).then(function(data){
			if(data.result!='0'){
				$.topCall.error("接口"+handler+"有误，原因："+data.msg);
			}
		})
	}
	//选择驳回节点
	scope.selectBackNode = function(value,currentNodeId){
		//弹出节点选择窗口
		DialogUtil.openDialog(__ctx+"/flow/node/condition/sameNodeSelector?defId="+defId, "选择节点", {calc:{nodeId:value,description:""}}, function(data, dialog){
			    scope.$apply(function(){
			    	  scope.bpmDefSetting.nodePropertieMap[currentNodeId].backNode=data.nodeId;
			    });
				dialog.dialog('close');
		 
		}, 500, 350)
	
	}
	
	//修改某节点的按钮
	scope.editBtns = function(nodeId){
		nodeDefService.editBtns(nodeId,scope);
	}
	scope.noStart = function(value){
		if(value.type!='start')return true;
		return false;
	}
	
	scope.initBackMode=function(prop){
		if(!prop.backMode) prop.backMode='direct';
	}
	
	scope.initBackUserMode=function(prop){
		if(!prop.backUserMode) prop.backUserMode='history';
	}
	
	/**
	 * 修改某节点的某批次的json
	 */ 
	scope.addNodeUserCondition = function(nodeType, nodeId, i,name) {
		nodeDefService.addNodeUserCondition(scope,nodeType, nodeId, i,name)
	}
	
	/**
	 * 批量勾选:驳回后直接返回
	 */ 
	scope.toBackModeall = function($event){
		var backMode = "normal";
		if($event.target.checked){
			backMode = "direct";
		}
		var nodes = scope.nodes;
		if(nodes!=null){
			for(var i=0;i<nodes.length;i++){
				try {
					scope.bpmDefSetting.nodePropertieMap[nodes[i].nodeId].backMode = backMode;
				} catch (e) {}
				
			}
		}
	}
	
	scope.setRestFul = function(nodeId,nodeName){
		nodeDefService.setRestFul(scope,nodeId,nodeName);
	}
	
}]);
