var  app = angular.module("startFlow", ['flowServiceModule','formDirective','arrayToolService']);
app.controller('startFlowCtrl', ['$rootScope','$scope','flowService','ArrayToolService',function($rootScope,$scope,flowService,ArrayToolService){
	
	$scope.mobiscroll_setting={ 
        theme: 'ios', 
        lang: 'zh',
        display: 'bottom',
        max: new Date(2030, 1, 1,23,59),
        min: new Date(1941, 1, 0,0)
    };
	
	
	//获取流程定义ID
	var defId=HtUtil.getParameter("defId");
	var flowKey=HtUtil.getParameter("flowKey");
	var subject=decodeURIComponent(HtUtil.getParameter("subject"));
	var runId=HtUtil.getParameter("runId");
	$scope.runId = runId;
	$scope.defId = defId;
	$scope.isSendNodeUsers = 0;

	/**
	 * 显示流程图
	 */
	$scope.showFlowPic=function(){
		var url=__ctx +"/bpm/bpmImage?defId="+defId;
		var conf={};
		conf.title="流程图";
		conf.url=url;
		createPopupDialog(conf,'flowStartDialog');
	}
	
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
			Alert("提示信息","当前子表表单未校验通过！");
			return;
		} 
		var tempData =$.extend($scope.data.sub[tableName].rows[index],$scope.subTempData[tableName]);
		var index = tempData.$index;
		if(!index)index=0;
		$scope.data.sub[tableName].rows[index]=tempData;
		$('#'+tableName+'_editDialog').modal('close');
		//$rootScope.$digest();
	}
	
	/**
	 * 删除行
	 */
	$scope.remove=function(path,idx){
		flowService.removeRow($scope,path,idx);
	};
	/**
	 * 删除行
	 */
	$scope.removeTo=function(val,list){
		ArrayToolService.remove(val,list);
	};
	
	/**
	 * 启动流程
	 */
	$scope.startFlow=function(){
		var isRequired=false;
		var tabName=$('[type="subGroup"]').attr('tablename');
		if(tabName) isRequired=$scope.permission.table[tabName].required;
		if($scope.custForm.$invalid || (isRequired && $(".list-paddingleft-2").length<1)){
			$.alert("表单未校验通过！")
			return;
		}
		// [{nodeId:"userTask1",executors:[{id:"",type:"org,user,pos", name:""},{id:"",type:"org,user,pos",name:""}]}]
		if( $scope.prop.firstNodeUserAssign && !$scope.prop.allowExecutorEmpty ){
			var len = $scope.nodeUsers.length;
			for( var i=0;i<len;i++){
				if(!$scope.nodeUsers[i].executors || !$scope.nodeUsers[i].executors.length  ){
					$.alert("请设置任务执行人");
					return;
				}
			}
		
		}
		if($scope.flowSubmiting) return;
		$scope.flowSubmiting = true;
		var def=flowService.startFlow(defId,$scope.data,runId,$scope.nodeUsers,$scope.isSendNodeUsers);
		def.then(function(data){
			if(data.result==1){
				$.alert("启动流程成功!","提示信息",function(){
					location.href=runId?"myDraftList.html":"startFlowList.html";
				});
			}
			else{
				$scope.flowSubmiting = false;
				$.alert("启动失败!",data.message);
			}
		},function(status){
			$scope.flowSubmiting = false;
		})
	};
	
	/**
	 * 保存草稿
	 */
	$scope.saveDraft=function(){
		var def=flowService.saveDraft(defId,$scope.data,runId);
		def.then(function(data){
			if(data.result==1){
				$.alert("保存草稿成功!","提示信息",function(){
					location.href=runId?"myDraftList.html":"startFlowList.html";
				});
			}
			else{
				$.alert(data.message,"保存草稿失败");
			}
		},function(status){
			
		})
	};
	$scope.delDraft = function(){
		var def=flowService.delDraft(runId);
		def.then(function(data){
			if(data.result==1){
				$.alert("删除草稿成功!",function(){
					location.href="myDraftList.html";
				});
			}
			else{
				$.alert(data.message,"删除草稿失败:"); 
			}
		},function(status){
			
		})
	}
	
	/**
	 * 表单加载。
	 */
	$scope.init=function($scope,defId){
		var json=HtUtil.getJSON("form_" +defId);
		var defer=null;
		if(json==null){
			defer=flowService.getStartForm(defId,null,null,runId);
		}
		else{
			defer=flowService.getStartForm(defId,json.formKey,json.version,runId);
		}
		defer.then(function(data){
			flowService.handForm($scope,data,defId);
		},function(status){
//			console.info(status);
		});
	},
	//初始化表单。
	$scope.init($scope,defId);
	
	$scope.$on("formInited",function(evt,data){
	});
	
	$scope.selectUser = function(nodeUser){
		var dataBack = null;
		if(nodeUser.executors){
			dataBack = nodeUser.executors;
		}
		nodeUser.executors = [];
		UserDialog({
			initData:dataBack,
			callBack:function(data){
				$scope.isSendNodeUsers = 1;
				var len = data.length;
				for(var i=0;i<len;i++){
					if(!nodeUser.executors){
						nodeUser.executors = [];
					}
					nodeUser.executors.push({id:data[i].id,type:"user",name:data[i].name});
				}
				AngularUtil.setData($scope);
			}
		});
	}
	
}]);