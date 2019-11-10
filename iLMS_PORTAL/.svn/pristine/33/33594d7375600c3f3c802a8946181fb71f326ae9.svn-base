var app = angular.module('remindersApp',['base','formDirective','arrayToolService','ui.codemirror']);
	app.controller("remindersController",['$scope', 'baseService', 'ArrayToolService', function(scope, baseService, ArrayTool){
		scope.ArrayTool = ArrayTool;
		//可以在系统属性中配置
		baseService.postForm(__ctx+"/flow/plugins/remindersJson",{defId:__param.defId,nodeId:__param.nodeId}).then(function(data){
			scope.warnSetting =data.warnSetting;
			scope.reminders = data.reminders;
		})
		scope.show = function(r){
			scope.reminder = r;
		}
		scope.addWarningSet = function(){
			if(!scope.reminder.warningSetList)scope.reminder.warningSetList=[{}];
			else scope.reminder.warningSetList.push({});
		}
		scope.tempReminder = {dateType:'caltime',relNodeEvent:'create',dueAction:'no-action',msgType:'inner',relNodeId:__param.nodeId,isAdd:true,dueTime:0,msgSendTime:0,msgInterval:0};
		scope.reminder = angular.copy(scope.tempReminder);
		
		scope.add = function(){
			if(scope.myForm.$invalid){
				$.topCall.warn("当前催办表单校验未通过！");
				return;
			}
			//新增数据
			if(scope.reminder.isAdd){
				delete scope.reminder.isAdd;
				if(!scope.reminders.reminderList)scope.reminders.reminderList =[];
				scope.reminders.reminderList.push(scope.reminder);
			}
			
			scope.reminder = angular.copy(scope.tempReminder);
		}
		scope.save = function(){
			var remindersJson = angular.toJson(scope.reminders);
			
			baseService.postForm(__ctx+"/flow/plugins/remindersSave",{defId:__param.defId,nodeId:__param.nodeId,remindersJson:remindersJson}).then(function(data){
				if(data=="1"){
					if(scope.reminders.reminderList.length==0){
						$.topCall.warn("已经清空催办设置。");
					}else{
						$.topCall.success(scope.reminders.reminderList.length+"条催办，已经保存成功！");
						scope.reminder = angular.copy(scope.tempReminder);
						scope.reminder.dueTime = 0;
					}
					
				}else{
					$.topCall.error("催办保存出现异常："+data);
				}
			})
		}
		
		scope.setVariable = function(event,treeId, node){
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
			//scope.varTree.hideMenu();
			scope.insetCode(keyStr)
			
		}
		
		scope.varTree = new ZtreeCreator('varTree', __ctx + "/flow/node/varTree")
							.setDataKey({ name : 'desc' }).setCallback({onClick :scope.setVariable})
							.makeCombTree("tempTree")
							.initZtree({ defId : __param.defId, nodeId : __param.nodeId, parentFlowKey : __param.parentFlowKey,includeBpmConstants : true }, 1);
		
		scope.selectScript = function(key){
			scope.CodeMirrorBroadcast = key;
			new ScriptSelector(function(script){
				scope.insetCode(script);
			 }).show();
		}
		
		//codeMirror 
		$(".varTree").bind("click", function(){
			var broadcast = $(this).attr("broadcast");
			scope.CodeMirrorBroadcast = broadcast;
			scope.varTree.showMenu($(this));
			$("#varTreeMenuContent").css("top",$("#varTreeMenuContent").css("top").split("p")[0]-32+"px")
		});
		scope.insetCode = function(str) {
			if(!scope.CodeMirrorBroadcast)scope.CodeMirrorBroadcast= "CodeMirror";
			scope.$broadcast(scope.CodeMirrorBroadcast, function(CodeMirror) {
				CodeMirror.replaceSelection(str);
			});
		};
		scope.editorConfig = {
				toolbars : [['source']],
				initialFrameHeight:150,
				focus : true
			};
	}]);
