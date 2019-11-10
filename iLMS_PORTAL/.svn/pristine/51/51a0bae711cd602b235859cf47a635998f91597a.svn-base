var  app = angular.module("approveFlow", ['flowServiceModule','formDirective']);
app.controller('taskCtrl', ['$scope','flowService',function($scope,flowService){
	
	$scope.mobiscroll_setting={ 
	        theme: 'ios', 
	        lang: 'zh',
	        display: 'bottom',
	        max: new Date(2030, 1, 1,23,59),
	        min: new Date(1941, 1, 0,0),
	        defaultValue:""
	 };
	
	//获取流程定义ID
	var taskId=HtUtil.getParameter("taskId");
	var defId=HtUtil.getParameter("defId");
	var runId=HtUtil.getParameter("runId");
	
	/**
	 * 添加子表行
	 */
	$scope.add=function(path){
		flowService.addRow($scope,path);
		return false;
	};
	/**弹出框编辑子表行*/
	$scope.editRow=function(tableName,index){
		flowService.editRow($scope,tableName,index);
		return false;
	};
	/**将弹出框子表清空**/
	$scope.cleansubTempData=function(tableName){
		$scope.subTempData[tableName] ={};
	}
	/**将弹出框子表数据填充至子表**/ 
	$scope.pushTempDataToForm=function(tableName){
		if($(".ng-invalid","#"+tableName+"_editDialog").length>0){
			$.alert("提示信息","当前子表表单未校验通过！");
			return;
		} 
		
		var tempData =$.extend({},$scope.subTempData[tableName]);
		var index = tempData.$index;
		if(!index)index=0;
		$scope.data.sub[tableName].rows[index]=tempData;
		$('#'+tableName+'_editDialog').modal('close');
	}
	
	/**
	 * 删除行
	 */
	$scope.remove=function(path,idx){
		flowService.removeRow($scope,path,idx);
	};
	
	/**
	 * 初始化任务表单。
	 */
	$scope.initTaskForm=function(){
		var json=HtUtil.getJSON("form_" +defId);
		var defer=null;
		if(json==null){
			defer=flowService.getTaskForm(taskId);
		}
		else{
			defer=flowService.getTaskForm(taskId,json.formKey,json.version);
		}
		defer.then(function(data){
			flowService.handForm($scope,data,defId);
		},
		function(status){
//			console.info(status);
		});
	};
	
	
	//初始化表单。
	$scope.initTaskForm();
	
	
	/**
	 * 返回结果。
	 */
	var confirmFn=function(opinion){
		var action = $scope.action;
		if (!opinion) {
			$.alert("请填写审批意见！");
			return;
		}
		
		var def=flowService.approveFlow(taskId,action,opinion,$scope.data);
		def.then(function(data){ 
			if(data.result==1){
				$.alert($scope.actionMsg+"操作成功!",function(){
					location.href=__ctx+"/mobile/bpm/myWaitMatters.html";
				});
			}
			else{
				$.alert(data.cause,$scope.actionMsg+"操作失败");
			}
		},function(status){
			
		});
	}
	
	/**
	 * 协办处理。
	 */
	var transferFn=function(opinion){
		var action = $scope.action;
		var def = flowService.startTrans(taskId,action,opinion,$scope.userIds);
		def.then(function(data){ 
			if(data.result==1){
				$.alert($scope.actionMsg+"操作成功!",function(){
					location.href=__ctx+"/mobile/bpm/myWaitMatters.html";
				});
			}
			else{
				$.alert(data.cause,$scope.actionMsg+"操作失败");
			}
		},function(status){
			
		});
	}
	
	$scope.approveFlow=function(action,text){
		if($scope.custForm.$invalid){
			$.alert("表单未校验通过！");
			return;
		}
		//关闭侧栏
		
		if(!text)text="提交";

		var tempOpinion=$("[ng-model='htBpmOpinion']:not(:hidden)").val();
		if (!tempOpinion) {
			tempOpinion = text;
		}
		
		$scope.action = action; 
		$scope.actionMsg = text;
		$.prompt("请输入审批意见","确认提交吗?",confirmFn);
		//处理表单意见回填
		var opinion = $scope.data['__form_opinion'];
		if(opinion){
			setTimeout(function(){
				var oldOpinion = '';
				for(var p in opinion){ 
					oldOpinion = opinion[p];
					break;
				 }
				$('.modal-text-input').val(oldOpinion);
			},500);
		}
		
		//表单意见关联
		$("[ng-model='htBpmOpinion']:not(:hidden)").each(function(i){
			var path=$(this).parent().parent().attr("ht-bpm-opinion");
			var aryPath= path.split("\.");
			var opinionName=aryPath[2];
			var obj={};
			obj[opinionName]=tempOpinion;
			$scope.data.__form_opinion=obj;
		});
		
		//默认意见
		$(".modal-text-input").val(tempOpinion);
	}
	
	$scope.startTrans=function(action,text){
		var action = $scope.action;
		$scope.actionMsg = text;
		UserDialog({
			title : "选择协办人员",
			isSingle : false,
			callBack : function(data) {
				var arrId=[];
				var arrText=[];
				$.each(data,function(index,item){
					arrId.push(item.id);
					arrText.push(item.name);
				})
				$scope.userIds = arrId.toString();
				var userNames = arrText.toString();
				$.prompt("请输入协办通知内容", "确认向以下用户发起协办吗?<br>["+userNames+"]",transferFn);
				$(".modal-text-input").val("请协助处理任务");
			} 
		});
	}
	
	
	/**
	 * 显示审批历史。
	 */
	$scope.showOpinion=function(){
		flowService.showOpinion(runId);
	}
	
	/**
	 * 手机流程图
	 */
	$scope.showFlowPic=function(){
		var url=__ctx +"/bpm/bpmImage?taskId="+taskId;
		var conf={};
		conf.title="流程图"
		conf.url=url;
		createPopupDialog(conf,'flowApproveDialog');
	}
	
}]);