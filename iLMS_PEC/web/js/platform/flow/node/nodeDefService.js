angular.module('nodeDefModel', ['base'])
.service('nodeDefService', ['$rootScope','baseService','$compile', function($rootScope,baseService,$compile) {
    var service = {
    	pluginConf: [
    	              {name:'会签节点规则',key:"sign-config",supprotType:'signTask'},
			          {name:'设置跳转规则',key:"node-rules",supprotType:'userTask,signTask'},
			          {name:'事件脚本设置',key:"event-script",supprotType:'userTask,signTask,start,end'},
			          {name:'分支条件设置',key:"exclusive-gateway",supprotType:'inclusiveGateway,exclusiveGateway'},
			          {name:'自动任务设置',key:"auto-service",supprotType:'serviceTask'},
			          {name:'催办设置',key:"task-reminder",supprotType:'userTask'},
			          {name:'子流程设置',key:"call-activity",supprotType:'callActivity'},
					],
    		
		//选择用户
		addNodeUserCondition:function(scope,nodeType, nodeId, i,name){
    			var nodeJsonArray = scope.nodeUserMap[nodeId];
    			var userRule;
    			if (i != undefined) {
    				userRule = angular.copy(nodeJsonArray[i]);
    				userRule.nodeType = nodeType;
    			}
    			var dialog;
    			var title ="【"+name+"】节点人员设置";
    			var def = { passConf :userRule , title :title, width : 950, height : 580, modal : true, resizable : true, iconCls :'fa fa-table', buttonsAlign : 'center',
    				buttons : [ { text : '确定', iconCls : 'fa fa-check-circle',
    					handler : function(e) {
    						var win = dialog.innerWin;
    						var data = win.getUserRuleData();
    						if (!data||data=='validateError') return;
    						dialog.dialog('close');
    						scope.$apply(function() {
    							if (i != undefined) {
    								nodeJsonArray[i] = data;
    							} else if (nodeJsonArray) {
    								nodeJsonArray.push(data);
    							} else {
    								var userRules = [];
    								userRules.push(data);
    								scope.nodeUserMap[nodeId] = userRules;
    							}
    						});
    					}
    				}, {
    					text : '取消', iconCls : 'fa fa-times-circle',
    					handler : function() {
    						dialog.dialog('close');
    					}
    				}]
    			};
    			var topDefKey= scope.topDefKey;
    			var url = __ctx + '/flow/node/condition/conditionEdit?defId=' + defId + '&nodeId=' + nodeId + "&nodeType=" + nodeType+"&parentFlowKey="+topDefKey;
    			dialog = $.topCall.dialog({
    				src : url,
    				base : def
    			});
    		},
    		editBtns: function(nodeId,scope){
    			var changeBtnData = function(data){
    				scope.$apply(function(){
    					scope.nodeBtnMap[nodeId] = data;
    				})
    			}
    			
    			var url=__ctx+'/flow/def/nodeBtnSet?defId='+defId+'&nodeId='+nodeId;
				showDialog({url:url,width:970,height:545,title:"设置按钮"+nodeId,passConf:changeBtnData});
    		},
    		setRestFul: function(scope,nodeId,nodeName){
    			var confData =  [];
    			confData.nodeId = nodeId;
    			try {
    				if(nodeId==null){
    					confData.restful = scope.bpmDefSetting.globalRestfuls;
    					confData.nodes = scope.nodes;
    				}else{
    					confData.restful = scope.nodeRestfulMap[nodeId];
    				}
				} catch (e) {}
    			var dialog;
    			var title = "全局事件设置";
    			if(nodeId){
    				title = "节点【"+nodeName+"】事件设置";
    			}
    			confData
    			var def = { passConf :confData , title :title, width : 950, height : 580, modal : true, resizable : true, iconCls :'fa fa-table', buttonsAlign : 'center',
    				buttons : [ { text : '确定', iconCls : 'fa fa-check-circle',
    					handler : function(e) {
    						var win = dialog.innerWin;
    						var data = win.getRestfulData();
    						if (!data||data=='validateError') return;
    						dialog.dialog('close');
    						scope.$apply(function() {
    							if(nodeId){
    								scope.nodeRestfulMap[nodeId] = data;
    							}else{
    								scope.bpmDefSetting.globalRestfuls = data;
    							}
    						});
    					}
    				}, {
    					text : '取消', iconCls : 'fa fa-times-circle',
    					handler : function() {
    						dialog.dialog('close');
    					}
    				}]
    			};
    			var url = __ctx + '/flow/plugins/restfulEdit';
    			if(nodeId){
    				url+='?nodeId=' + nodeId;
    			}
    			dialog = $.topCall.dialog({
    				src : url,
    				base : def
    			});
    		}
        }
    return service;
}])
/***
 * type :globle,instance,node
 */
.directive('bpmForm', ["baseService",function(baseService) {
	return {
    	restrict : 'AE',
    	scope:{
    		bpmForm:"=",
    		mobileForm:"="
    	},
    	link: function(scope, element, attrs,ctrl){
    		scope.title = attrs.title;
    		var type = attrs.type;
    		var nodeTyped='';
    		if(attrs.nodeid)  scope.nodeMsg=eval('('+attrs.nodeid+')');
    		scope.$on ('nodeSetUpdate', function() {//监听节点保存时间，更新按钮状态
    			if(!scope.nodeMsg) return;//不是节点表单不做判断
    			scope.bpmForm.formValue=scope.bpmForm.formValue || '';
    			if((scope.nodeMsg.type=='userTask' || scope.nodeMsg.type=='signTask') && scope.bpmForm.formValue != ''  ){
    				scope.showAuthorizeSubData = true;
        		}else{
        			scope.showAuthorizeSubData = false;
        		}
    	    });
    		scope.showAuthorizeSubData = false;
    		if(type=="node" && scope.bpmForm.formValue && (scope.nodeMsg.type=='userTask' || scope.nodeMsg.type=='signTask')){
    			scope.showAuthorizeSubData = true;
    		}
    		
    		//解决主流程设置外部子流程时，只选手机表单，重新进入后子流程中选的手机表单没显示问题
    		if(typeof(scope.bpmForm) == "undefined" ){
    			scope.bpmForm={"type":"INNER"};
    		}
    		
    		
    		//选择表单
    		scope.selectForm = function(isPc){
    			var topDefKey= scope.$parent.topDefKey;
    			
    			var formType=isPc?"pc":"mobile";
    			BpmFormDialog({defId:defId,formType:formType,
    				topDefKey:topDefKey,
    				callback:function(data){
    				var formStr = isPc?"bpmForm":"mobileForm";
    				scope.$apply(function(){
    					if(!scope[formStr])scope[formStr]={};
    					scope[formStr].type = 'INNER';
    					scope[formStr].name = data.name;
        				scope[formStr].formValue = data.formKey;	
    				})
    			 }
    			}).show();
    		}
    		//授权
    		scope.authorize = function(){
    			if(!scope.bpmForm.formValue){
    				 $.topCall.alert('提示信息','请先设置表单!');
    				 return;
    			}
    			var url= __ctx + '/form/rights/rightsDialog?&flowKey='+flowKey+"&parentFlowKey="+topDefKey+"&formKey="+scope.bpmForm.formValue;
    			if(type=='node') url+="&nodeId="+scope.bpmForm.nodeId;
    			if(type=='instance') url+="&instId=true";
    			
    			var width = width || 800;
    			var height = height || 600;
    			var dialog;
    			var def = { title : "授权页面", width : width, height : height, modal : true, resizable : true};
    			dialog = $.topCall.dialog({
    				src : url,
    				base : def
    			});
    		}
    		
    		// 子表数据授权
    		scope.authorizeSubData = function(){
    			var url= __ctx + '/form/rights/subRightsDialog?flowKey='+flowKey+'&nodeId='+scope.bpmForm.nodeId+'&defId='+defId+'&parentDefKey='+topDefKey;
    			
    			var width = width || 800;
    			var height = height || 600;
    			var dialog;
    			var def = {
    				title : "子表数据授权页面",
    				width : width,
    				height : height,
    				modal : true,
    				resizable : true,
    			};
    			dialog = $.topCall.dialog({
    				src : url,
    				base : def
    			});
    		}
    		
    		//清除表单和已设置该节点的表单权限
    		scope.clearForm = function(form){
    			var nodeId = form.nodeId||"";
    			var parentFlowKey = topDefKey;
    			var param = {"flowKey":flowKey, "nodeId": nodeId,"parentFlowKey":parentFlowKey};
    			baseService.post(__ctx+ "/form/rights/remove",param).then(function(data){
    				if(data.result){
    					$.topCall.success(data.message);
    					form.name = "";
    					form.formValue ="";
    					scope.showAuthorizeSubData = false;
    				}else{
    					$.topCall.error(data.message);
    				}
    			});
				
    		}
    		
    		//点击所选手机表单，打开手机表单的预览界面
    		scope.openMobile = function(formKey){
    			var url = __ctx+'/form/form/preview?formKey='+formKey;
    			window.open(url);
    		}
    	},
    	template:'<div class="well">\
    			  <div class="form-group form-inline">\
				    <label class="control-label col-sm-3">{{title}}</label>\
				    <select class="form-control" ng-model="bpmForm.type" ht-validate="{required:true}">\
						<option value="INNER">在线表单</option>\
						<option value="FRAME">URL表单</option> \
					</select>\
				  </div>\
				  <div class="form-group form-inline" ng-if="bpmForm.type==\'INNER\'">\
				    <label class="control-label col-sm-3 ">PC端:</label>\
    					<div class="right-operation">\
						<a href="'+__ctx+'/form/form/preview?formKey={{bpmForm.formValue}}" target="_blank">{{bpmForm.name}}</a>\
						<span ng-if="!bpmForm.name" class="operation_text">请选择</span>\
						<a href="javascript:void(0);" class="btn btn-sm btn-info fa-search-plus" ng-click="selectForm(true)"></a>\
						<a href="javascript:void(0);" ng-if="bpmForm.name" class="btn btn-sm btn-info fa-undo" ng-click="clearForm(bpmForm)"></a>\
						<a href="javascript:void(0);" class="btn btn-sm btn-info fa-tachometer" ng-click="authorize()">授权</a><br/><br/>\
						<a href="javascript:void(0);" class="btn btn-sm btn-info fa-tachometer" ng-click="authorizeSubData()" ng-if="showAuthorizeSubData" >子表数据授权</a>\
						</div>\
				  </div>\
				  <div ng-if="bpmForm.type==\'FRAME\'" class="form-inline">\
						<label class="control-label col-sm-3">PC端URL:</label><input ng-model="bpmForm.formValue" class="form-control">\
				  </div>\
				  <div>\
					  <div class="form-group form-inline" ng-if="bpmForm.type==\'INNER\'">\
					    <label class="control-label col-sm-3">手机端:</label>\
					    <span ng-if="!mobileForm.name" class="operation_text">请选择</span>\
					    <div class="right-operation">\
						<a ng-click="openMobile(mobileForm.formValue)">{{mobileForm.name}}</a>\
						<a href="javascript:void(0);" class="btn btn-sm btn-info fa-search-plus" ng-click="selectForm(false)"></a>\
						<a href="javascript:void(0);" ng-if="mobileForm.name" class="btn btn-sm btn-info fa-undo" ng-click="clearForm(mobileForm)"></a>\
						<div>\
					  </div>\
				  </div></div>' 
				// <div ng-if="bpmForm.type==\'FRAME\' " class="form-inline">\
				//	<label class="control-label col-sm-3">手机端URL:</label><input ng-model="mobileForm.formValue" class="form-control">\
				// </div>\
	}}])
//dynamicDirective="{name:'attr',directive1:'attr'}"
.directive('dynamicDirective', function($compile) {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var directives = attrs.dynamicDirective;
			if(!directives)return;
			var directiveArr = directives.split(",");
			
			var str = "<div ";
			for(var i=0,d;d=directiveArr[i++];){
				str=str+d.split(":")[0]+"="+d.split(":")[1]+" ";
			}
			str +="></div>";
//			console.info(str);
			element.removeAttr("dynamic-directive")
			element.removeClass("ng-binding");
			element.html(str);
            $compile(element)(scope);
		}
	};
})
//节点规则
.directive('bpmNodeRules', ['baseService', function(baseService) {
	return {
		restrict : 'A',
		scope:true,
		link : function(scope, element, attrs) {
			var ruleData = {};
			var currentNodeId = "";
			
			scope.$parent.$watch("edittingNodeId",function(newVal,oldVal){
				if(newVal){
					currentNodeId = scope.edittingNodeId;
					if(ruleData[currentNodeId]){
						scope.ruleList =ruleData[currentNodeId];
					}else {
						loadData();
					}
				}
			})
			var loadData = function(){
				baseService.postForm(__ctx+"/flow/node/ruleListJson",{definitionId:defId,nodeId:currentNodeId}).then(function(data){
					scope.ruleList =data.rows;
					ruleData[currentNodeId] = data.rows;
				});
			}
			scope.editNodeRules = function(){
				var topDefKey= scope.$parent.topDefKey;
				var url=__ctx+'/flow/node/ruleEdit?definitionId='+defId+'&nodeId='+currentNodeId+"&parentFlowKey="+topDefKey;
				showDialog({url:url,width:840,height:545,title:"跳转规则设置　"+currentNodeId,passConf:loadData});
			}
		},
		template:'<div>\
					<div class="table-nest">\
						<div class="btn btn-danger btn-sm fa fa-edit pull-right" ng-click="editNodeRules()">设置跳转规则</div>\
					</div>\
					<div class="alert alert-info show-grid" role="alert" ng-if="!ruleList||ruleList.length==0">尚未配置节点规则</div>\
					<div class="well  show-grid" ng-repeat="rule in ruleList">\
			      		<span class="control-label">【{{rule.ruleName}}】<div class="btn btn-sm">跳转至:{{rule.targetNode}}</div></span>\
					</div>\
				<div>',
		replace:true
	};
}])
//节点事件脚本
.directive('bpmEventScript', ['baseService', function(baseService) {
	return {
		restrict : 'A',
		scope:true,
		link : function(scope, element, attrs) {
			var ruleData = {};
			
			scope.$parent.$watch("edittingNodeId",function(newVal,oldVal){
				if(newVal) loadData();
			})
			var loadData = function(){
				scope.scriptMap =scope.nodeScriptMap[scope.edittingNodeId];
			}
			var setData = function(data){
				var scriptMap = {};
				for(var i=0,d;d=data[i++];){
					scriptMap[d.scriptType] = d.content;
				}
				scope.$apply(function(){
					scope.nodeScriptMap[scope.edittingNodeId] =scriptMap;
					scope.scriptMap = scriptMap;
				});
			}
			scope.editEventScript = function(){
				var currentNodeId = scope.edittingNodeId;
				var topDefKey= scope.$parent.topDefKey;
				var url=__ctx+'/flow/node/eventScriptEdit?defId='+defId+'&nodeId='+currentNodeId+"&parentFlowKey="+topDefKey;
				showDialog({url:url,width:840,height:515,title:"事件脚本设置　"+currentNodeId,passConf:setData});
			}
		},
		template:'<div>\
					<div class="btn btn-danger btn-sm fa fa-edit pull-right" ng-click="editEventScript()">设置节点脚本</div><br>\
				<div ng-if="edittingNodeType==\'start\'">\
					<label class="control-label col-sm-3">开始事件:</label>\
					<div class="alert alert-info show-grid" role="alert" ng-if="!scriptMap.start">尚未配置开始脚本</div>\
					<div class="well row show-grid" ng-if="scriptMap.start">\
						<code>{{scriptMap.start}}</code>\
					</div>\
				</div>\
				<div ng-if="edittingNodeType==\'end\'">\
					<label class="control-label col-sm-3">结束事件:</label>\
					<div class="alert alert-info show-grid" role="alert" ng-if="!scriptMap.end">尚未配置结束脚本</div>\
					<div class="well row show-grid" ng-if="scriptMap.end">\
						<code>{{scriptMap.end}}</code>\
					</div>\
				</div>\
				<div ng-if="edittingNodeType==\'userTask\'||edittingNodeType==\'signTask\'" style="width:100%">\
					<label class="control-label">前置事件:</label>\
					<div class="alert alert-info show-grid" role="alert" ng-if="!scriptMap.create">尚未配置前置脚本</div>\
					<div class="well show-grid" ng-if="scriptMap.create">\
						<code>{{scriptMap.create}}</code>\
					</div>\
				</div>\
				<div ng-if="edittingNodeType==\'userTask\'||edittingNodeType==\'signTask\'">\
					<label class="control-label">后置事件:</label>\
					<div class="alert alert-info show-grid" role="alert" ng-if="!scriptMap.complete">尚未配置后置脚本</div>\
					<div class="well row show-grid" ng-if="scriptMap.complete">\
						<code>{{scriptMap.complete}}</code>\
					</div>\
				</div>\
				<div>',
		replace:true
	};
}])
//同步网关
.directive('bpmExclusiveGateway', ['baseService', function(baseService) {
	return {
		restrict : 'A',
		scope:true,
		link : function(scope, element, attrs) {
			scope.data = {};
			
			scope.$parent.$watch("edittingNodeId",function(newVal,oldVal){
				if(newVal){
					if(scope.data[newVal]){
						scope.scirptMap = scope.data[newVal].scriptMap;
						scope.outComeNodes = scope.data[newVal].outComes;
					}
					else loadData();
				}
			})
			var loadData = function(){
				baseService.postForm(__ctx+'/flow/node/getNodeOutcomes?defId='+defId+'&nodeId='+scope.edittingNodeId,{}).then(function(data){
					scope.scirptMap = data.scriptMap;
					scope.outComeNodes = data.outComes;
					scope.data[scope.edittingNodeId] = data;
				})
			}
			scope.editNodeRules = function(){
				var topDefKey= scope.$parent.topDefKey;
				var currentNodeId = scope.edittingNodeId;
				var url=__ctx+'/flow/node/branchConditionEdit?defId='+defId+'&nodeId='+currentNodeId+"&parentFlowKey="+topDefKey;
				showDialog({url:url,width:840,height:515,title:"分支条件设置　"+currentNodeId,passConf:loadData});
			}
		},
		template:'<div>\
				<div class="btn btn-danger btn-sm fa fa-edit pull-right" ng-click="editNodeRules()">设置分支条件</div>\
				<div class="well row show-grid" ng-repeat="node in outComeNodes">\
			      	<span class="control-label">目标节点：{{node.nodeId}}({{node.nodeName}})</span><br><br>\
		  			<code style="word-wrap: break-word;word-break: break-all;">{{scirptMap[node.nodeId]||\'【尚未设置分支条件】\'}}</code>\
				</div>\
				<div>',
		replace:true
	};
}])
//自动任务
.directive('bpmAutoService', ['baseService', function(baseService) {
	return {
		restrict : 'A',
		scope:true,
		link : function(scope, element, attrs) {
			var currentNodeId =""; 
			var dataMap ={};
			scope.$parent.$watch("edittingNodeId",function(newVal,oldVal){
				if(newVal){
					currentNodeId = scope.edittingNodeId;
					if(dataMap[newVal]){
						scope.scirptMap = dataMap[newVal].data;
					}
					else loadData();
				}
			})
			
			var loadData = function(){
				baseService.postForm(__ctx+'/flow/node/getNodeAutoTask?defId='+defId+'&nodeId='+currentNodeId,{}).then(function(data){
					scope.autoTask = data
					dataMap[currentNodeId] = data;
				})
			}
			
			scope.editAutoService = function(){
				var currentNodeId = scope.edittingNodeId;
				var url=__ctx+'/flow/node/autoTaskManager?defId='+defId+'&nodeId='+currentNodeId;
				showDialog({url:url,width:840,height:620,title:"自动任务节点　"+currentNodeId,passConf:loadData});
			}
		},
		template:'<div>\
				<div class="btn btn-danger btn-sm fa fa-edit pull-right" ng-click="editAutoService()">设置自动任务</div>\
				<div class="alert alert-info row show-grid" role="alert" ng-if="!autoTask">尚未配置自动任务</div>\
				<div class="well row show-grid" ng-if="autoTask.title==\'webService\'">\
			      	<h3 class="control-label">WebService 节点</h3>\
		  			<div>webService方法：{{autoTask.name}}</div>\
				</div>\
				<div class="well row show-grid" ng-if="autoTask.title==\'消息节点\'">\
		      		<h3 class="control-label">自动消息节点</h3>\
				</div>\
				<div class="well row show-grid" ng-if="autoTask.title==\'脚本\'">\
			  		<h3 class="control-label">脚本节点</h3><br><br>\
					<code>{{autoTask.script}}</code>\
				</div>\
				<div>',
		replace:true
	};
}])
//会签
.directive('bpmSignConfig', ['baseService', function(baseService) {
	return {
		restrict : 'A',
		scope:true,
		link : function(scope, element, attrs) {
			var currentNodeId = "";
			var dataMap = {};
			scope.$parent.$watch("edittingNodeId",function(newVal,oldVal){
				if(newVal){
					currentNodeId = scope.edittingNodeId;
					if(dataMap[newVal]){
						scope.privilegeMap = dataMap[currentNodeId].privilegeList;
						scope.signRule = dataMap[currentNodeId].signRule;
					}
					else loadData();
				}
			})
			
			var loadData = function(){
				baseService.postForm(__ctx+'/flow/node/getSignConfig?defId='+defId+'&nodeId='+currentNodeId,{}).then(function(data){
					scope.privilegeMap = data.privilegeList;
					scope.signRule = data.signRule;
					dataMap[currentNodeId] = data;
				})
			}
			
			scope.editSignConfig = function(){
				var currentNodeId = scope.edittingNodeId;
				var url=__ctx+'/flow/node/signConfigEdit?defId='+defId+'&nodeId='+currentNodeId;
				showDialog({url:url,width:840,height:620,title:"会签节点规则定义　"+currentNodeId,passConf:loadData});
			}
		},
		template:'<div>\
				<div class="btn btn-danger btn-sm fa fa-edit pull-right" ng-click="editSignConfig()">设置会签规则</div>\
				<div class="alert alert-danger row show-grid" role="alert" ng-if="!signRule">尚未配置会签规则</div>\
				<div class="well row show-grid form-inline" ng-if="signRule">\
			      	<label class="control-label">{{signRule.decideType==\'agree\'?\'同意\':\'反对\'}}票\
					达到 {{signRule.voteAmount}}{{signRule.voteType==\'amount\'?\'张\':\'%\'}}则{{signRule.decideType==\'agree\'?\'同意\':\'反对\'}} \
					({{signRule.followMode==\'wait\'?\'等待所有人完成投票\':\'达到设定直接处理\'}})</label><br><br>\
					<div ng-repeat="(key,privilege) in privilegeMap">\
			      		<label class="control-label col-sm-4" ng-if="key==\'all\'">所有特权:</label>\
						<label class="control-label col-sm-4" ng-if="key==\'direct\'">直接处理:</label>\
						<label class="control-label col-sm-4" ng-if="key==\'oneticket\'">一票特权:</label>\
						<label class="control-label col-sm-4" ng-if="key==\'allowAddSign\'">允许增加会签:</label>\
		  				<span ng-repeat="nodeCondition in privilege">{{$index+1}}、{{nodeCondition.description}};</span><br><br>\
					</div>\
				</div>\
				<div>', 
		replace:true
	};
}])
//催办设置
.directive('bpmTaskReminder', ['baseService', function(baseService){
	return {
		restrict : 'A',
		scope:true,
		link : function(scope, element, attrs) {
			var currentNodeId =""; 
			var dataMap ={};
			scope.$parent.$watch("edittingNodeId",function(newVal,oldVal){
				if(newVal){
					currentNodeId = scope.edittingNodeId;
					if(dataMap[newVal]){
						scope.taskReminderList = dataMap[newVal].data;
					}
					else loadData();
				}
			})
			
			var loadData = function(){
				baseService.postForm(__ctx+'/flow/plugins/remindersJson?defId='+defId+'&nodeId='+currentNodeId,{}).then(function(data){
					scope.taskReminderList = data.reminders;
					dataMap[currentNodeId] = data.reminders;
				})
			}
			scope.editTaskReminder = function(){
				var currentNodeId = scope.edittingNodeId;
				var topDefKey= scope.$parent.topDefKey;
				var url=__ctx+'/flow/plugins/remindersEdit?defId='+defId+'&nodeId='+currentNodeId+"&parentFlowKey="+topDefKey;
				showDialog({url:url,width:1000,height:700,title:"催办设置　"+currentNodeId});
			}
		},
		template:'<div>\
				<div class="btn btn-danger btn-sm fa fa-edit pull-right" ng-click="editTaskReminder()">设置催办</div>\
				<div class="alert alert-info show-grid" role="alert" ng-if="!taskReminderList||taskReminderList.length==0">尚未配置催办任务</div>\
				<div class="well show-grid" ng-repeat="taskReminder in taskReminderList">\
			      	<span class="control-label">{{taskReminder.name}}<div>查看更多信息请编辑</div>\
		  			</span>\
				</div>\
			<div>',
		replace:true
	};
}])
//子流程设置 
.directive('bpmCallActivity', ['baseService', function(baseService) {
	return {
		restrict : 'A',
		scope:true,
		link : function(scope, element, attrs) {
			
			scope.editCallActivity = function(){
				if(!topDefKey){
					topDefKey=flowKey;
				}
				var currentNodeId = scope.edittingNodeId;
				var url=__ctx+'/flow/def/subFlowDetail?defId='+defId+'&nodeId='+currentNodeId 
					+"&topDefKey="+ topDefKey ;
				var h = $(top).height() - 65;
				var w =$(top).width() - 5;  
				showDialog({url:url,width:w,height:h,title:"子流程设置"+currentNodeId}); 
			}
		},
		template:'<div>\
					<div class="btn btn-danger btn-sm fa fa-edit pull-right" ng-click="editCallActivity()">设置子流程设置</div>\
				<div>',
		replace:true
	};
}])
function showDialog(param) {
		var baseConfig = { passConf : "", title : '', width : 450, height : 300, modal : true, resizable : true };
		$.extend(baseConfig, param);
		$.topCall.dialog({
			src : baseConfig.url,
			base : baseConfig
		});
	}
