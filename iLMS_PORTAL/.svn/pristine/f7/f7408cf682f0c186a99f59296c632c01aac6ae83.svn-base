var TEMPLATEApp = angular.module('templateApp',['base','TemplateService','formDirective']);
TEMPLATEApp.controller("TemplateController",['$scope','baseService','Template',function($scope,BaseService,Template){
	$scope.prop={};//初始化
	$scope.ueditorReady = false;
		
	InitMirror.initFinishCallBack = function(editor){
		if(id!=""){
			Template.detail({id:id},function(data){
				$scope.prop.template=data;
				//编辑器准备好了直接设置值。
		    	if($scope.ueditorReady){
		    		ue.setContent($scope.prop.template.html);
		    	}
		    	
		    	InitMirror.getById("subject").insertCode($scope.prop.template.subject);
		    	InitMirror.getById("plain").insertCode($scope.prop.template.plain);
			});
		}
	}
		
		
	
	
	
	
	  $scope.$watch("ueditorReady",function(newVal,oldVal){
	    	if(newVal!==oldVal){
	    		if(newVal&&$scope.prop.template&&$scope.prop.template.html){
	    			ue.setContent($scope.prop.template.html);
	    		}
	    	}
	    },false);
	
	
	
	
	$scope.changeType = function(){
		$scope.prop.template.key = $scope.prop.template.typeKey+"-";
	}
	
	$scope.save = function(){
		InitMirror.save();
		$scope.prop.template.plain=$("#plain").val();
		$scope.prop.template.subject=$("#subject").val();
		if (!$scope.myForm.$valid){ 
			$.topCall.error("表单校验失败！");
			return;
		}
		$scope.prop.template.html=ue.getContent();
		if(!$scope.prop.template.isDefault){
			$scope.prop.template.isDefault=0;
		}
		Template.saveData({template : JSON2.stringify($scope.prop.template)},function(data){
			if(data.result==1){
				$.topCall.confirm("温馨提示",data.message+',是否继续操作',function(result){
					if(result){
						window.location.reload(true);
					}else{
						HT.window.closeEdit(true,'grid');
					}
				});
			}else{
				$.topCall.error(data.message);
			}
		})
	}
	
	
	//普通是否数组
	$scope.typeSelect=[
		{value:'taskCreate',key:'任务创建通知'},
		{value:'bpmCommuSend',key:'任务沟通'},
		{value:'bpmCommuFeedBack',key:'通知沟通人'},
		{value:'bpmnTaskTrans',key:'任务流转默认'},
		{value:'bpmHandTo',key:'任务转交通知'},
		{value:'addSignTask',key:'加签通知'},
		{value:'taskComplete',key:'任务完成通知'},
		{value:'taskBack',key:'任务驳回通知'},
		{value:'processEnd',key:'流程结束通知'},
		{value:'bpmnApproval',key:'审批提醒'},
		{value:'bpmnBack',key:'驳回提醒'},
		{value:'bpmnRecover',key:'撤销提醒'},
		{value:'bpmnAgent',key:'代理任务审批'},
		{value:'bpmnDelegate',key:'通知被代理人'},
		{value:'bpmEndProcess',key:'终止流程'},
		{value:'bpmTransCancel',key:'撤销流转'},
		{value:'copyTo',key:'流程实例抄送'}
	];
	
}]);