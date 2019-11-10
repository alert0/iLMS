
var bpmModel = angular.module('BpmService', ['base','formDirective'])
bpmModel.filter('clearValue', function() {
	return function(obj) {
		for(var k in obj){
			obj[k] =  "";
		}
		return obj;
	};
})
.factory('bpm', ['$rootScope','baseService', function($rootScope,baseService) { 
	var defId = null,
		taskId = null,
		proInstId = null;
	    params = null,
	    hideOpinion = null;
		
	return {
		init : function(optionStr){
			var option = parseToJson(optionStr);
			defId = option.defId;
			taskId = option.taskId;
			proInstId = option.proInstId;
			params = option.params || "";
			var sendParam="";
			//if(params !="") sendParam=baseService.decode(params);
			if(taskId){
				$.topCall.progress();
				var url=__ctx + "/flow/task/taskDetail?taskId=" + taskId;
				var defer=baseService.get(url);
				defer.then(function(data,status){
						$.topCall.closeProgress();
						if(!data) return;
						
						if(data.result=="formEmpty"){
							$.topCall.error("还没有设置表单,请先设置表单!");
							return;
						}
						 if(sendParam !="" && sendParam.indexOf('data'>-1)){
							 var fileArr=sendParam.split(":")[0].split(".");
							 data.data[fileArr[1]][fileArr[2]]=sendParam.split(":")[1];
						 }
						data.result&&($rootScope.$broadcast('html:update',data));
						!data.result&&($.topCall.error(data.message));
					}
					,function(status){
						$.topCall.error("加载失败");
					}
				);
			}
			else if(defId){
				$.topCall.progress();
				var defer=baseService.postForm(__ctx + "/flow/instance/getFormAndBO",{defId:defId,proInstId:proInstId});
				defer.then(function(data,status){
					$.topCall.closeProgress();
					
					if(data.result=="formEmpty"){
						$.topCall.error("还没有设置表单,请先设置表单!");
						return;
					}
					 if(sendParam !="" && sendParam.indexOf('data'>-1)){
						 var fileArr=sendParam.split(":")[0].split(".");
						 data.data[fileArr[1]][fileArr[2]]=sendParam.split(":")[1];
					 }
					$rootScope.$broadcast('html:update', data);
				},function(status){
					$.topCall.error("加载失败");
				});
				
			}
		},
		isHideOpinion:function(){
			if(hideOpinion== null){
				hideOpinion = $("[ng-model='htBpmOpinion']:not(:hidden)").length>0 ;
			}
			return hideOpinion;
		},	
		isCreateInstance : function(){
			return $.isEmpty(taskId);
		},
		isDraft:function(){
			return !$.isEmpty(proInstId);
		},
		getDefId:function(){
			return defId;
		},
		getTaskId:function(){
			return taskId;
		},
		getProInstId:function(){
			return proInstId;
		},
		getOpinionFLag:function(){
			return "__form_opinion";
		},
		getParams:function() {
			return params;
		},
		
		excBeforScript:function(scope,el_){
			//执行前置js
			var btn__ =scope.buttons[$(el_).attr("alias")];
			
			var beforeScript = btn__.beforeScript||"return true";
			var script = "var tempFunction = function(scope){ "+beforeScript+"};"
			var result =  eval(script+"tempFunction(scope);");
			
			window.curent_btn_after_script_ = btn__.afterScript||"";
			if(result.hasOwnProperty("done")){
				return result;
			}
			
			if(result===false) return false;
			return true;
		},
		handlerSuccess :function(data){
			$.topCall.closeProgress();
			//执行后置js
			var script = "var tempFunction = function(data){ "+window.curent_btn_after_script_+"};"
			var afterScriptPassed =  eval(script+"tempFunction(data);");
			this.afterScript = "";
			
			if(data.result==1){
		        $.topCall.success(data.message, function() {
		        	if(afterScriptPassed!== false) window.close();
		        });
		        //如果是从我的草稿那里启动，则更新我的草稿列表
		        if(window.opener.reloadMyDraft){
		        	window.opener.reloadMyDraft();
		        }
		    }else {
		    	$.topCall.error(data.message,data.cause); 
		    }
		},
		handFail:function(code){
			$.topCall.closeProgress();
			$.topCall.error("错误消息:"+code);
		}
	};
}])
.directive('htBpmManager', ['bpm','$filter', function(bpm,$filter){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			bpm.init(attrs.htBpmManager);
			//添加子表记录
			scope.add = function(path){
				var arr = path.split(".");
				if(arr.length<2){
					alert("subtable path is error!")
				}
				var subTableName = arr[1].replace("sub_","");
				var tempData = scope.data[arr[0]].initData[subTableName];
				
				if(!tempData)tempData={};
				var ary = eval("(scope.data." + path + ")"); 
				var newItem = angular.copy(tempData);
				if(ary){
					ary.push(newItem);
				}
				else{
					var rows=[];
					rows.push(newItem);
					eval("(scope.data." + path + "=rows)")
				}
			};
			
			var flag=0;
			//删除子表记录
			scope.remove = function(path,index){
				if(flag>0) return ;
				var ary = eval("(scope.data." + path + ")");
				if(ary&&ary.length>0){
					ary.splice(index,1);
				}
				flag=1;
				window.setTimeout(function(){flag=0;},50);
			};
			
			scope.$on("html:update",function(event,data){
				scope.form = data.form;
				//如果是内部表单的情况才处理数据。
				if(data.form.type=="INNER"){
					scope.data =data.data;
					scope.permission = parseToJson(data.permission);
					scope.opinionList = data.opinionList;
					// 初始化表单
					window.setTimeout(scope.initSubTableData,100);
				}
				// 启动流程时， 既没有选择跳过第一个节点， 并且跳转类型设置了 jumpType： free/select  选择路径跳转或自由跳转
				if( !isFirstNodeUserAssign && data.jumpType ){
					// 启动流程时，可以选择路径跳转
					scope.canSelectNode = true;
				}
				
			});
			
			/*响应office控件事件。
			 data : 格式
			 {action:"",scope:scope,params:{}}
			 action: 动作处理类型
			 scope : 当前scope对象
			 params :其他上下文数据
			*/
			scope.$on("office:saved",function(event,data){
				var action=data.action;
				var scope=data.scope;
				//流程启动 和保存草稿
				if(bpm.isCreateInstance()) {
					toSubmitForm(scope,action);
				}
				//任务处理
				else{
					switch(action){
						//保存草稿
						case "saveDraft":
							toSaveTaskForm(scope);
							break;
						//处理任务完成
						default:
							var json=data.params;
							var opinion=json.opinion;
							taskComplete(action,opinion,scope);
							break;
					}
				}
			});
			
			//初始化子表数据
			scope.initSubTableData = function(data,permission){
				var permission = scope.permission;
				
				var initSubTable = [];
				for(var subTable in permission.table){
					if(permission.table[subTable].required){
						initSubTable.push(subTable);
					}
				}
				
				$("[type='subGroup'][initdata]").each(function(i,item){
					initSubTable.push($(item).attr("tablename"));
				});
				var data = scope.data;
				for(var i=0,subTable;subTable=initSubTable[i++];){
					for(var boCode in data){ 
						var initData =data[boCode].initData[subTable];
						if(initData &&(!data[boCode]["sub_"+subTable]||data[boCode]["sub_"+subTable].length==0)){
							data[boCode]["sub_"+subTable] = [];
							data[boCode]["sub_"+subTable].push($.extend({},initData));
						}
					}
				}
				!scope.$$phase&&scope.$digest(); 
			}
		},
		template: '<div>\
					<div ng-show="form.type==\'INNER\'" ht-bind-html="form.formHtml"></div>\
					<div ng-show="form.type==\'FRAME\'" ht-frame-form ng-model="form"></div>\
				   </div>'
	};
}])
.directive('htBpmStartFlow', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			element.bind("click",function(){
				//固定的检验脚本。
				if(window.validForm){
					var rtn= window.validForm(scope);
					if(!rtn) return;
				}
				//前置脚本执行结果为false 则不继续
				var scriptResult = bpm.excBeforScript(scope,this);
				if(scriptResult===false) return;
				
				if(scriptResult.hasOwnProperty("done")){
					scriptResult.done(function(){
						submitForm(scope,"startFlow");
					}).fail(function(){
						return;
					});
				}else{
					submitForm(scope,"startFlow");
				}
			});
		},
		template:'<a class="btn btn-success fa fa-send">启动</a>',
		replace:true
	};
}])
.directive('htBpmSaveDraft', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			
			element.bind('click',function(){
				//如果执行后置脚本出现异常 则返回
				if(bpm.excBeforScript(scope,this)===false) return;
				//保存启动时的数据。
				if(bpm.isCreateInstance()) {
					//启动时的保存
					submitForm(scope,"saveDraft");
				}
				else{
					saveTaskForm(scope);
				}
			});
		},
		template:'<a class="btn btn-primary fa fa-clipboard">保存</a>',
		replace:true
	};
}])
.directive('htBpmAgree', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			//打开任务办理界面
			element.bind('click',function(){
				if(window.validForm){
					var rtn= window.validForm(scope);
					if(!rtn) return;
				}
				//前置脚本
				if(bpm.excBeforScript(scope,this)===false) return;
				
				handleTask("agree","同意",scope);
			});
		},
		template:'<a href="#" class="btn btn-success fa fa-check-square-o">同意</a> ',
		replace:true
	};
}])
.directive('htBpmOppose', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			//打开任务办理界面
			element.bind('click',function(){
				if(window.validForm){
					var rtn= window.validForm(scope);
					if(!rtn) return;
				}
				//前置脚本 
				if(bpm.excBeforScript(scope,this)===false) return;
				
				handleTask("oppose","反对",scope);
				
			});
		},
		template:'<a href="#" class="btn btn-primary fa fa-close">反对</a> ',
		replace:true
	};
}])
.directive('htBpmAbandon', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			//打开任务办理界面
			element.bind('click',function(){
				if(window.validForm){
					var rtn= window.validForm(scope);
					if(!rtn) return;
				}
				//前置脚本
				if(bpm.excBeforScript(scope,this)===false) return;
				
				handleTask("abandon","弃权",scope);
			});
		},
		template:'<a href="#" class="btn btn-danger fa fa-hand-o-up">弃权</a> ',
		replace:true
	};
}])
.directive('htBpmCommu', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			//打开沟通界面
			element.bind('click',function(){
				
				//前置脚本
				if(bpm.excBeforScript(scope,this)===false) return;
				
    			var def = {title:'沟通反馈',width:600, modal:true,resizable:true,iconCls: 'fa fa-table'};
    			def.passConf = scope.data;
    			$.topCall.dialog({
    				src:__ctx + '/flow/task/taskToFeedBack?taskId=' + bpm.getTaskId(),
    				base:def
    			});
			});
		},
		template:'<a href="#" class="btn btn-primary fa fa-comment-o">沟通反馈</a> ',
		replace:true
	};
}])
.directive('htBpmStartCommu', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			//打开发起沟通界面
			element.bind('click',function(){
				//前置脚本
				if(bpm.excBeforScript(scope,this)===false) return;
				
    			var def = {title:'发起沟通',width:600, modal:true,resizable:true,iconCls: 'fa fa-table'};
    			def.passConf = scope.data;
    			$.topCall.dialog({
    				src:__ctx + '/flow/task/taskToCommu?taskId=' + bpm.getTaskId(),
    				base:def
    			});
			});
		},
		template:'<a href="#" class="btn btn-primary fa fa-comment-o">发起沟通</a> ',
		replace:true
	};
}])
.directive('htBpmReject', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			//打开驳回界面
			element.bind('click',function(){
				//前置脚本
				if(bpm.excBeforScript(scope,this)===false) return;
				
    			var def = {title:'驳回',width:600, modal:true,resizable:true,iconCls: 'icon-commu'};
    			def.passConf = scope.data;
    			$.topCall.dialog({
    				src:__ctx + '/flow/task/taskToReject?taskId=' + bpm.getTaskId()+"&backModel=reject",
    				base:def
    			});
			});  
		},
		template:'<a href="#" class="btn btn-danger fa fa-lastfm">驳回</a> ',
		replace:true
	};
}])
.directive('htBpmBackTostart', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			//打开驳回界面
			element.bind('click',function(){
				//前置脚本
				if(bpm.excBeforScript(scope,this)===false) return;
				
    			var def = {title:'驳回到发起人',width:600, modal:true,resizable:true,iconCls: 'icon-commu'};
    			def.passConf =scope.data;
    			$.topCall.dialog({
    				src:__ctx + '/flow/task/taskToReject?taskId=' + bpm.getTaskId()+'&backModel=backToStart',
    				base:def
    			});
			});  
		},
		template:'<a href="#" class="btn btn-danger fa fa-lastfm">驳回到发起人</a> ',
		replace:true
	};
}])
.directive('htBpmStartTrans', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			//打开流转界面
			element.bind('click',function(){
				//前置脚本
				if(bpm.excBeforScript(scope,this)===false) return;
				
    			var def = {title:'流程任务流转',width:700,height:500, modal:true,resizable:true,iconCls: 'fa fa-table'};
    			def.passConf = scope.data;
    			$.topCall.dialog({
    				src:__ctx + '/flow/task/taskToTrans?taskId=' + bpm.getTaskId(),
    				base:def
    			});
			});
		},
		template:'<a href="#" class="btn btn-primary fa fa-ioxhost">流转</a> ',
		replace:true
	};
}])
.directive('htBpmAgreeTrans', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			//打开 流转任务的同意界面
		    element.bind('click',function(){
		    	//前置脚本
				if(bpm.excBeforScript(scope,this)===false) return;
				
				handleTask("agreeTrans","流转同意",scope);
			});
		},
		template:'<a href="#" class="btn btn-success fa fa-ioxhost">同意</a> ',
		replace:true
	};
}])
//
.directive('htBpmOpposeTrans', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
		   //打开 流转任务的反对界面
		  //打开任务办理界面
			element.bind('click',function(){
				//前置脚本
				if(bpm.excBeforScript(scope,this)===false) return;
				
				handleTask("opposeTrans","流转反对",scope);
			});
		},
		template:'<a href="#" class="btn btn-primary fa fa-ioxhost">反对</a> ',
		replace:true
	};
}])
.directive('htBpmPrint', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			//打开 打印界面
		    element.bind('click',function(){
		     $(".buttons").parent().hide();
		     $(this).closest('body').printArea();
		     $(".buttons").parent().show();
		    });
		},
		template:'<a href="#" class="btn btn-primary fa fa-print">打印</a> ',
		replace:true
	};
}])
.directive('htBpmEndProcess', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			//打开 终止流程的界面
			element.bind('click',function(){
				//前置脚本
				if(bpm.excBeforScript(scope,this)===false) return;
				
    			var def = {title:'终止流程',width:600, height:260, modal:true,resizable:true,iconCls: 'fa fa-table'};
    			$.topCall.dialog({
    				src:__ctx + '/flow/task/taskToEndProcess?taskId=' + bpm.getTaskId(),
    				base:def
    			});
			});
		},
		template:'<a href="#" class="btn btn-danger fa fa-ioxhost">终止流程</a> ',
		replace:true
	};
}])
.directive('htBpmDelegate', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			//打开 转交的界面
			element.bind('click',function(){
    			var def = {title:'流程任务转办',width:600, height:300, modal:true,resizable:true,iconCls: 'fa fa-table'};
    			def.passConf = scope.data;
    			$.topCall.dialog({
    				src:__ctx + '/flow/task/taskToDelegate?taskId=' + bpm.getTaskId(),
    				base:def
    			});
			});
		},
		template:'<a href="#" class="btn btn-primary fa fa-share">转办</a> ',
		replace:true
	};
}])
.directive('htBpmAddSign', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			//打开 加签的界面
			element.bind('click',function(){
				if(bpm.excBeforScript(scope,this)===false) return;
				
    			var def = {title:'添加会签任务',width:600, height:300, modal:true,resizable:true,iconCls: 'fa fa-table'};
    			def.passConf = scope.data;
    			$.topCall.dialog({
    				src:__ctx + '/flow/task/taskToAddSignTask?taskId=' + bpm.getTaskId(),
    				base:def
    			});
			});
		},
		template:'<a href="#" class="btn btn-primary fa fa-group">加签</a> ',
		replace:true
	};
}])
.directive('htBpmSuspendProcess', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			//打开 打印界面
		},
		template:'<a href="#" class="btn btn-primary fa fa-print">禁用流程</a> ',
		replace:true
	};
}])
.directive('htBpmRecoverProcess', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			//打开 打印界面
		},
		template:'<a href="#" class="btn btn-primary fa fa-print">恢复流程</a> ',
		replace:true
	};
}])
/**
 * 解锁与释放指令。
 */
.directive('htBpmLockUnlock', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			var alias=element.attr("alias");
			var btn=scope.buttons[alias];
			scope.clsLockName='fa-print';
			scope.btnLockText=btn.name;
			scope.clsLockName='fa-unlock';
			if(btn.name=='锁定'){
				scope.clsLockName='fa-lock';
			}
			//打开任务办理界面
			element.bind('click',function(){
				//前置脚本
				if(bpm.excBeforScript(scope,this)===false) return;
				
				var url=__ctx + '/flow/task/lockUnlock?taskId=' + bpm.getTaskId();
				$.get(url,function(rtn){
					
					//0:任务已经处理,1:可以锁定,2:不需要解锁 ,3:可以解锁，4,被其他人锁定,5 管理员
					var msg="";
					switch(rtn){
						case 0:
							msg="该任务可能由其他人进行了处理!";
							break;
						case 1:
							msg="任务已经被锁定!";
							break;
						case 3:
							msg="任务已经被释放!";
							break;
						case 4:
							msg="任务已经其他人锁定了!";
							break;
					}
					
					$.topCall.success(msg,function(){
						if(window.opener){
		            		window.opener.refreshTargetGrid("grid");
		            	}
						window.location.reload();
					});
				})
			});
		},
		template:'<a href="#" class="btn btn-success fa {{clsLockName}}">{{btnLockText}}</a>',
		replace:true
	};
}])
/**
 * 抄送
 */
.directive('htBpmInstanceTrans', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			var alias=element.attr("alias");
			var btn=scope.buttons[alias];
			scope.clsName='fa-detail';
			scope.btnText=btn.name;
			
			//打开抄送界面
			element.bind('click',function(){
				var title="流程抄送";
				var url=__ctx+"/flow/instance/instanceToCopyTo?taskId="+bpm.getTaskId()+"&copyToType=0";
				 HT.window.openEdit(url,title, 'view', 'grid', 500, 350, null, null, bpm.getTaskId(), false);
			});
		},
		template:'<a href="#" class="btn btn-success fa {{clsName}}">{{btnText}}</a>',
		replace:true
	};
}])
/**
 * 任务延期操作
 */
.directive('htBpmTaskDelay', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			var alias=element.attr("alias");
			var btn=scope.buttons[alias];
			scope.clsName='fa-detail';
			scope.btnText=btn.name;
			
			//打开抄送界面
			element.bind('click',function(){
				var title="任务延期设置";
				var url=__ctx+"/flow/task/bpmTaskDueTime/bpmTaskDueTimeEdit?taskId="+bpm.getTaskId();
				 HT.window.openEdit(url,title, 'view', 'grid', 600, 410, null, null, bpm.getTaskId(), false);
			});
		},
		template:'<a href="#" class="btn btn-success fa {{clsName}}">{{btnText}}</a>',
		replace:true
	};
}])
.directive('htBpmButtons', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"EA",
		controller:function($scope, $element){
			$scope.buttons = [];
			
			$scope.$on("html:update",function(event,data){
				$scope.buttonList = data.buttons; 
				$scope.buttons = {};
				// 转换下格式
				for(var i=0,button;button=$scope.buttonList[i++];){
					$scope.buttons[button.alias]=button;
				} 
				//按钮排序
				window.setTimeout(function(){
					for(var i=0,button;button=$scope.buttonList[i++];){
						var key = button.alias;
						
						var directiveStr =key.replace(/[A-Z]/g, function(word){
							return "-" + word.substring(0,1).toLowerCase()+word.substring(1);} 
						); 
						directiveStr = directiveStr.replace("back-to-start","back-tostart");
						var btn = $("[ht-bpm-"+directiveStr+"]",$element);
						
						//自定义按钮处理
						if(btn.length==0 && "rejectToAnyNode,backTostart".indexOf(key)== -1){
							btn = $('<a href="#" class="btn btn-primary fa fa-'+key+'" >'+button.name+'</a>');
							btn.bind('click',function(){
								//自定义按钮执行前置脚本 
								var btnScript = $(this).data("btn_script_");
								var scope = AngularUtil.getScope(scope);
								scope.bpm=bpm;
								scope.basePath = __basePath||"";
								var script = "var tempFunction = function(scope){ "+ btnScript +"};"
								var result =  eval(script+"tempFunction(scope);"); 
							});
						}
						btn.text(button.name);
						btn.attr("alias",key);
						btn.data("btn_script_",button.beforeScript);
						$element.append(btn);
					}
				},5)
			});
			
        	$scope.decideButton = function(taskAction){
        		if($scope.buttons &&$scope.buttons[taskAction]) return true;
        		
        		return false
        	};
        },
		template:'<span class="ht-bpm-buttons">'+
					'<div ng-if="decideButton(\'startFlow\')" ht-bpm-start-flow></div>'+//启动流程
					'<div ng-if="decideButton(\'agree\')" ht-bpm-agree></div>'+//同意
					'<div ng-if="decideButton(\'oppose\')" ht-bpm-oppose></div>'+//反对
					'<div ng-if="decideButton(\'abandon\')" ht-bpm-abandon></div>'+
					'<div ng-if="decideButton(\'saveDraft\')" ht-bpm-save-draft></div>'+
					'<div ng-if="decideButton(\'reject\')" ht-bpm-reject></div>'+
					'<div ng-if="decideButton(\'backToStart\')" ht-bpm-back-tostart></div>'+
					'<div ng-if="decideButton(\'endProcess\')" ht-bpm-end-process></div>'+
					'<div ng-if="decideButton(\'delegate\')" ht-bpm-delegate></div>'+
					'<div ng-if="decideButton(\'addSign\')" ht-bpm-add-sign></div>'+
					'<div ng-if="decideButton(\'startCommu\')" ht-bpm-start-commu></div>'+
					'<div ng-if="decideButton(\'commu\')" ht-bpm-commu></div>'+
					'<div ng-if="decideButton(\'startTrans\')" ht-bpm-start-trans></div>'+
					'<a   ng-if="decideButton(\'flowImage\')" ht-bpm-flow-image class="btn btn-primary fa fa-newspaper-o">流程图</a>'+
					'<a   ng-if="decideButton(\'approvalHistory\')" ht-bpm-approval-history class="btn btn-primary fa  fa-bars"> 审批历史</a>'+
					'<div ng-if="decideButton(\'opposeTrans\')" ht-bpm-oppose-trans></div>'+
					'<div ng-if="decideButton(\'agreeTrans\')" ht-bpm-agree-trans></div>'+
					'<div ng-if="decideButton(\'suspendProcess\')" ht-bpm-suspend-process></div>'+//挂起流程
					'<div ng-if="decideButton(\'recoverProcess\')" ht-bpm-recover-process></div>'+//恢复流程
					'<div ng-if="decideButton(\'lockUnlock\')" ht-bpm-lock-unlock alias="lockUnlock"></div>'+//锁定或恢复锁定
					'<div ng-if="decideButton(\'print\')" ht-bpm-print></div>'+//打印
					'<div ng-if="decideButton(\'instanceTrans\')" ht-bpm-instance-trans alias="instanceTrans" ></div>'+//抄送
					'<div ng-if="decideButton(\'taskDelay\')" ht-bpm-task-delay alias="taskDelay" ></div>'+//任务延期
				 '</span>',
		replace:true
	};
}])
/**
 * IFRAME方式加载表单。
 * ng-model：需要绑定表单的地址。
 * <div ng-show="form.type==\'FRAME\'" ht-inner-form ng-model="form"></div>
 */
.directive('htFrameForm', ['bpm','baseService', function(bpm,baseService){
	return {
		restrict:"A",
		require : '?ngModel', 
		link:function(scope, element, attrs,ngModel){
			if(!ngModel) return;
			
			ngModel.$render = function () {
				if(!ngModel.$modelValue) return ;
				if(ngModel.$modelValue.type!="FRAME") return;
				
				var url=ngModel.$modelValue.formValue;
				url=url.startWith("http")?url :__ctx +url;
				try {
					url = url + "&params=" + bpm.getParams();
				} catch (e) {}
				var frameObj=$("<iframe id='frmFrame' src="+url +" onload='iframeHeight(this)' style='width:100%;border:none;'></iframe>")
				//将表单加载到系统中
				element.append(frameObj);
			}
		}
	};
}]);

/**
 * 提交表单这个方法兼顾了 office控件提交的逻辑。
 * 如果为在线表单，则提交调用office控件提交方法，完成处理后
 * 通过 监听 office:saved 事件，在这个事件监听器中进行处理。
 * @param scope
 * @param action
 */
function submitForm(scope,action){
	//不是在线表单
	if(scope.form.type!="INNER"){
		toSubmitForm(scope,action);
	}
	else{
		//保存office 控件
		OfficePlugin.submit(action);
	}
}

/**
 * 提交表单。
 * @param bpm
 * @param scope
 * @param startFlow		true:启动流程,false:保存草稿
 */
function toSubmitForm(scope,type){
	var bpm=CustForm.getService("bpm");
	var baseService=CustForm.getService("baseService");
	
	var formType=scope.form.type;
	//{isValid:true,frmType:"表单类型",data:"表单数据"}

	var form=getFormData(scope,bpm);
	
	var startFlow=type=="startFlow";
 
	//启动流程时才校验表单。  因某些实体有字段在数据库是必填。保存草稿如果不校验，会导致数据库异常，故保存草稿也修改为校验表单
	//if(startFlow){
		if(!form.isValid){
			selectErrorTab();
			var error = form.error.customValidate;
			var errorMsg = ""
			if(errorMsg != null){
				for(var i = 0; i<error.length; i++){
					errorMsg += error[i].$error;
					if(error.length > 1 && i < error.length-1){
						errorMsg += ",";
					}
				}
			}
			$.topCall.error("表单校验失败",errorMsg);
			return ; 
		}
	//}

	
	var jsonData = {defId:bpm.getDefId(),formType:form.frmType,  data:form.data};
	//通过草稿启动流程时，传入草稿的对应的实例ID
	if(bpm.isDraft()){
		jsonData.proInstId = bpm.getProInstId();
	}
	var action=(type=="startFlow")?  "start" : "saveDraft";
	var url=__ctx +  "/flow/instance/" + action;
	
	//发起时是否弹出窗口选择人员
	if(startFlow &&isFirstNodeUserAssign ){
		//弹出节点人员设置窗口
		DialogUtil.openDialog(__ctx +  "/flow/instance/sendNodeUsers?defId="+bpm.getDefId(), "选择节点人员", {defId:bpm.getDefId()}, function(data, dialog){
			if(data.length>0){
				jsonData.nodeUsers=JSON.stringify(data);
				jsonData.isSendNodeUsers=1;
				$.topCall.progress();
				baseService.postForm(url,jsonData).then(bpm.handlerSuccess,bpm.handFail);
				dialog.dialog('close');
			}
		}, 500, 350)
	}else if(scope.canSelectNode){
		//弹出节点人员设置窗口
		DialogUtil.openDialog(__ctx +  "/flow/instance/selectDestination?defId="+bpm.getDefId(), "选择跳转路径", {defId:bpm.getDefId()}, function(data, dialog){
			if(data.length>0 && data[0].nodeId ){
				jsonData.nodeUsers= JSON.stringify(data);
				jsonData.destination = data[0].nodeId;
				jsonData.isSendNodeUsers=1;
			}
			$.topCall.progress();
			baseService.postForm(url,jsonData).then(bpm.handlerSuccess,bpm.handFail);
			dialog.dialog('close');
		}, 500, 350)
	}else{
		//启动流程
		$.topCall.progress();
		baseService.postForm(url,jsonData).then(bpm.handlerSuccess,bpm.handFail);
	}
	
}

/**
 * 任务对话框保存。
 * @param scope
 */
function saveTaskForm(scope){
	//保存IFRAME。
	if(scope.form.type!="INNER"){
		toSaveTaskForm(scope);
	}
	else{
		//保存office 控件,真正保存通过事件处理器。
		OfficePlugin.submit("saveDraft");
	}
	
}

function toSaveTaskForm(scope){
	var bpm=CustForm.getService("bpm");
	var baseService=CustForm.getService("baseService");
	
	//{isValid:true,frmType:"表单类型",data:"表单数据"}

	var form=getFormData(scope,bpm);
	$.topCall.progress();
	
	var taskId = bpm.getTaskId();
	
	var jsonData ={data:form.data,taskId:taskId,formType:form.frmType};
	
	var url=__ctx +  "/flow/task/saveDraft" ;
	
	baseService.postForm(url,jsonData).then(bpm.handlerSuccess,bpm.handFail);
}

function handleTask(actionName,opinion,scope){
	
	if(scope.form.type!="INNER"){
		taskComplete(actionName,opinion,scope);
	}
	else{
		var params={opinion:opinion};
		//保存office 控件,真正保存通过事件处理器。
		OfficePlugin.submit(actionName,params);
	}
}

function taskComplete(actionName,opinion,scope){
	var bpm=CustForm.getService("bpm");	
	var form=getFormData(scope,bpm);
	//启动流程时才校验表单。
	if(actionName=="agree"){
		if(!form.isValid){
			selectErrorTab();
			var error = form.error.customValidate;
			var errorMsg = ""
			if(errorMsg != null){
				for(var i = 0; i<error.length; i++){
					errorMsg += error[i].$error;
					if(error.length > 1 && i < error.length-1){
						errorMsg += ",";
					}
				}
			}
			$.topCall.error("表单校验失败",errorMsg);
			return ; 
		}
	}
	
	if(bpm.isHideOpinion()){
		opinion=$("[ng-model='htBpmOpinion']:not(:hidden)").val();
	}
	
	// 是否弹窗
	if(scope.isPopWin){
		completeTaskDialog(opinion,actionName,scope,bpm);
	}else{
		completeTask(bpm,actionName,opinion,scope);
	}
}

/**
 * 审批任务。
 * @param taskId
 * @param actionName
 * @param opinion
 * @param formData
 */
function completeTask(bpm,actionName,opinion,scope){
	//{isValid:true,frmType:"表单类型",data:"表单数据"}
	var form=getFormData(scope,bpm);
	if(!form.isValid){
		selectErrorTab();
		$.topCall.error("表单校验失败","");
		return ; 
	}
	
	var taskId = bpm.getTaskId();
	 var jsonData ={data:form.data,actionName:actionName,opinion:opinion,taskId:taskId,formType:form.frmType};
	 $.post(__ctx + "/flow/task/complete",jsonData,function(data){
		 	$.topCall.closeProgress();
			var resultMessage=new com.hotent.form.ResultMessage(data);	
			
		 	//执行节点后置脚本
		 	data = JSON.parse(data);
		 	var script = "var tempFunction = function(data){ "+window.parent.curent_btn_after_script_+"};"
			var afterScriptPassed =  eval(script+"tempFunction(data);");
		 
			if(resultMessage.isSuccess()) {
             $.topCall.success(resultMessage.getMessage(), function() {
            	if(window.opener){
            		window.opener.refreshTargetGrid("grid");
            		if(afterScriptPassed !== false)window.parent.close(); 
            	}
             });
         }else {
         	$.topCall.error(resultMessage.getMessage(),resultMessage.getCause()); 
         }
	});
}

/**
 * 删除无效的bo数据。
 * @param data
 */
function tidyData(data){
	var json={};
	$("[ng-model^='data.']").each(function(){
		var temp=$(this).attr("ng-model");
		var bocode=temp.split(".")[1];
		json[bocode]=true;
	});
	
	for(var key in data){
		var rtn=isBoCodeExist(key,json);
		if(!rtn){
			delete data[key];
		}
	}
	

	
}

/**
 * 判断 bo的数据是否存在在界面的bo数据中。
 * @param key
 * @param json
 * @returns {Boolean}
 */
function isBoCodeExist(key,json){
	if("__form_opinion"==key)return true;
	var rtn=false;
	for(var temp in json){
		if(key==temp){
			rtn=true;
		}
	}
	return rtn;
}

/**
 * 处理表单有意见框但是意见框没有值的情况，添加一个空的意见数据。
 */
function handleOpionion(scope,bpm){
	//表单上不存在意见框
	if(!bpm.isHideOpinion()) return;
	
	if(scope.data.__form_opinion) return false;
	
	$("[ng-model='htBpmOpinion']:not(:hidden)").each(function(i){
		var path=$(this).parent().attr("ht-bpm-opinion");
		var aryPath= path.split("\.");
		var opinionName=aryPath[2];
		var obj={};
		obj[opinionName]="";
		scope.data.__form_opinion=obj;
		return false;
	});
}


/**
 * 统一获取表单数据。
 * @param scope
 * @returns {isValid:true,frmType:"表单类型",data:"表单数据"}
 */
function getFormData(scope,bpm){
	var rtnObj={isValid:true};
	var frmType=scope.form.type;
	
	if(frmType=='INNER'){
		if(!scope.custForm.$valid){
			rtnObj.error = scope.custForm.$error
			rtnObj.isValid=false;
		}
		//清除表单中没有使用的bo数据。
		tidyData(scope.data);
		//处理表单意见没有填写时加上意见数据。
		handleOpionion(scope,bpm);
		
		rtnObj.data=angular.toJson(scope.data);
		rtnObj.frmType="inner";
	}
	//
	else if(frmType=='FRAME'){
		var iframeObj=document.getElementById("frmFrame").contentWindow;
		var canSumit=false;
		try{
			canSumit=iframeObj.isValid();
		}
		catch(e){
			canSumit=true;
		}
		if(!canSumit){
			rtnObj.isValid=false;
		}
		rtnObj.data=iframeObj.getData();
		rtnObj.frmType="urlForm";
	}
	
	
	return rtnObj;
	
}

function completeTaskDialog(opinion,actionName,scope,bpm){
	
	var hasFormOpinion = bpm.isHideOpinion();
	//{isValid:true,frmType:"表单类型",data:"表单数据"}
	var form=getFormData(scope,bpm);
	if(!form.isValid){
		selectErrorTab();
		$.topCall.error("表单校验失败","");
		return ; 
	}
	
	var def = {title:"审批任务",width:600, modal:true,resizable:true,iconCls: 'fa fa-table'};
	
	
	def.passConf = {
		data : form.data,
		hasFormOpinion : hasFormOpinion,
		bpmFormId : scope.form.formKey,
		opinion :opinion 
	};

	$.topCall.dialog({
		src:__ctx + '/flow/task/taskToAgree?actionName='+actionName+'&taskId=' + bpm.getTaskId()+'&urlForm=' + (scope.buttons[actionName].urlForm||""),
		base:def
	});
}

/**
 * iframe高度沾满浏览器高度。
 * @param obj
 */
function iframeHeight(obj){
	var height=document.documentElement.clientHeight  || document.body.clientHeight ;
	$(obj).height(height-40);
}

/**
 * tab页面有校验不通过的，切换到相应的tab页来显示
 */
function selectErrorTab(){
	var panelArr = $(".tabs-panels").children();
	for(var idx = 0 ; idx < panelArr.length; idx++){
		if($(".ng-invalid-custom-validate",$(panelArr[idx])).length){
			$('#formTab').tabs('select', idx);
			break;
		}
	}
}

