

var  flowServiceModule = angular.module('flowServiceModule',["base"]);

flowServiceModule.factory('flowService',['baseService',function(baseService){
	return {
		/**
		 * 获取发起表单界面
		 */
		getStartForm:function(defId,formKey,version,runId){
			var url=__ctx +"/mobile/bpm/getStartForm.ht";
			formKey=formKey || "";
			version=version || 0;
			var data={defId:defId,formKey:formKey,version:version,runId:runId};
			var def=baseService.postForm(url,data);
			return def;
		},
		
		/**
		 * 数据返回构建表单。
		 */
		handForm:function($scope,data,defId){
			if(!data.result){
				angular.element("#formHtml").html("获取表单数据出错了:" + data.msg);
				return;
			}
			var isGet=data.get;
			if(isGet){
				json={formKey:data.formKey,version:data.version};
				HtUtil.setJSON("form_" + defId,json);
			}
			//数据 增加几个换行
			$scope.formHtml=data.form.formHtml;
			$scope.permission=eval("("+data.permission+")");
			$scope.data=data.data;
			$scope.subject=data.form.name;
			$scope.buttons =data.buttons;
			$scope.opinionList = data.opinionList;
			$scope.prop = data.prop;
			$scope.isSignTask = data.nodeType == 'signTask';
			$scope.isTransStatus = data.taskStatus == 'TRANSFORMED';
			$scope.isApproveTask = !$scope.isSignTask&&!$scope.isTransStatus;
			
			this.nodeUserSetting($scope);
			
			//如果权限为必填，自动添加一行。
			this.initSubTableData($scope);

			//发布表单发布事件
			$scope.$broadcast('formInited');
		},
		
		/**
		 * 启动流程。
		 */
		startFlow:function(defId,json,runId,nodeUsers,isSendNodeUsers){
			var url=__ctx +"/flow/instance/start";
			var formJson = angular.toJson(json);
			var nodeUsersJson = angular.toJson(nodeUsers);
			var data={supportMobile:1,defId:defId,data:formJson,proInstId:runId,nodeUsers:nodeUsersJson}; 
			if(isSendNodeUsers){
				data.isSendNodeUsers = 1;
			}
			var def=baseService.postForm(url,data);
			return def;
		},
		
		/**
		 * 保存草稿
		 */
		saveDraft:function(defId,data,runId,businessKey){
			var url=__ctx +"/flow/instance/saveDraft.ht";
			var formJson = angular.toJson(data);
			var data={fromMobile:1,defId:defId,data:formJson,proInstId:runId,businessKey:businessKey,supportMobile:1};
			var def=baseService.postForm(url,data);
			return def;
		},
		/**
		 * 删除草稿
		 */
		delDraft:function(runId){
			var url=__ctx +"/office/initiatedProcess/removeDraft";
			var data={id:runId};
			var def=baseService.postForm(url,data);
			return def;
		},
		/**
		 * 在子表中添加行。
		 */
		addRow:function($scope,path){
			var arr = path.split(".");
			if(arr.length<2)alert("subtable path is error!")
			var subTableName = arr[1].replace("sub_","")
			var tempData = $scope.data[arr[0]].initData[subTableName];
			if(!tempData)tempData={};
			
			var ary = eval("$scope.data." + path); 
			if(!angular.isArray(ary)) ary = [];
			
			ary.push(angular.copy(tempData));
			eval("$scope.data." + path+"=ary");
			!$scope.$$phase && $scope.$digest();
			return true;
		},
		//弹出框处理
		editRow:function($scope,tableName,index){
			if(!$scope.subTempData)$scope.subTempData={};
			var tempTableData ;
			
			if(index===undefined){
				tempTableData =$.extend({}, $scope.data.sub[tableName].row);
				index=$scope.data.sub[tableName].rows.length;//如果没有index 默认为添加一条
			}else{
				tempTableData =$.extend({},$scope.data.sub[tableName].rows[index]);
			}
			tempTableData.$index =index;
			$scope.subTempData[tableName]=tempTableData; 
			$("#"+tableName+"_editDialog").modal();
			return false;
		},
		/**
		 * 单行子表数据初始化。子表必填 
		 */
		initSubTableData:function($scope){
			var initSubTable = []; 
			var data = $scope.data;
			var permission = $scope.permission;
			//子表必填
			for(var subTable in permission.table){
				if(permission.table[subTable].required){
					initSubTable.push(subTable);
				}
			}
			//单行数据直接初始化
			$("[type='subGroup'][initdata]").each(function(i,item){
				initSubTable.push($(item).attr("tablename"));
			});
			
			for(var i=0,subTable;subTable=initSubTable[i++];){
				for(var boCode in $scope.data){
					var initData =data[boCode].initData[subTable];
					if(initData &&(!data[boCode]["sub_"+subTable]||data[boCode]["sub_"+subTable].length==0)){
						data[boCode]["sub_"+subTable] = [];
						data[boCode]["sub_"+subTable].push($.extend({},initData));
					}
				}
			}
			!$scope.$$phase&&$scope.$digest();
		},
		/**
		 * 删除一行数据。
		 */
		removeRow:function($scope,path,idx){
			var ary = eval("($scope.data." + path + ")");
			if(ary&&ary.length>0){
				ary.splice(idx,1);
			}
		},
		/**
		 * 返回任务表单。
		 */
		getTaskForm:function(taskId,formKey,version){
			var url=__ctx +"/mobile/bpm/getTaskForm.ht";
			formKey=formKey || "";
			version=version || 0;
			var data={taskId:taskId,formKey:formKey,version:version};
			var def=baseService.postForm(url,data);
			return def;
		},
		/**
		 * 审批流程。
		 */
		approveFlow:function(taskId,actionName,opinion,data){
			var url=__ctx +"/flow/task/complete";
			var obj={taskId:taskId,actionName:actionName,
					opinion:opinion,data:angular.toJson(data),
					supportMobile:1};
			var def=baseService.postForm(url,obj);
			return def;
		},
		/**
		 * 任务流转。
		 */
		startTrans:function(taskId,actionName,opinion,data){
			
			var url=__ctx +"/flow/task/saveTrans";
			var obj = {
					taskId:taskId,
					opinion:opinion,
					receiverIds:data,
					notifyType:"inner",
					action:"back",
					decideType:"agree",
					voteType:"percent"
				};
			var def=baseService.postForm(url,obj);
			return def;
		},
		/**
		 * 获取流程实例表单。
		 */
		getInstForm:function(runId,formKey,version){
			var url=__ctx +"/mobile/bpm/getInstForm";
			formKey=formKey || "";
			version=version || 0;
			var data={runId:runId,formKey:formKey,version:version};
			var def=baseService.postForm(url,data);
			return def;
		},
		/**
		 * 显示审批历史。
		 */
		showOpinion:function(runId){
			var url=__ctx +"/mobile/bpm/opinions.html?runId="+runId;
			var conf={};
			conf.title="审批历史" ;
			conf.url=url;
			createPopupDialog(conf,'flowStartDialog');
		},
		
		/**
		 * 手机流程图
		 */
		showFlowPic:function(runId){
			var url=__ctx +"/bpm/bpmImage?runId="+runId;
			var conf={};
			conf.title="流程图"
			conf.url=url;
			createPopupDialog(conf,'flowApproveDialog');
		},
		/**
		 * 获取通知类型。
		 */
		getInformType:function(){
			var url=__ctx +"/platform/system/share/getInformType.ht";
			var def=baseService.get(url);
			return def;
		},
		/**
		 * 取消代理。
		 */
		cancelAgent:function(data){
			var url=__ctx +"/platform/bpm/bpmTaskExe/cancel.ht";
			var def=baseService.postForm(url,data);
			return def;
		},
		/**
		 * 
		 */
		nodeUserSetting:function($scope){
			if($scope.prop && $scope.prop.firstNodeUserAssign ){
				var url = __ctx + "/mobile/bpm/nodeUsers.ht?defId="+$scope.defId;
				baseService.get(url).then(function(data){
					$scope.nodeUsers = data;
				},function(){});
			}
		}

	};
}])