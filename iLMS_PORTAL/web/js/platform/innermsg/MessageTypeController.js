var MsgTypeApp = angular.module('msgTypeApp',['base','MessageTypeService']);
MsgTypeApp.controller("MessageTypeController",['$scope','baseService','MsgType',function($scope,baseService,MsgType){
	$scope.displayMsgType=[];//可见的消息类型
	$scope.replyMsgType=[];//是否可以回复的消息类型
	$scope.allMsgType=[];//是否可以回复的消息类型
	
	
	//初始化消息类型
	MsgType.getMsgType(function(data){
			angular.forEach(data.disMsgType, function(key, value) {
					var obj={};
					obj.key=key;
					obj.value=value;
			       this.push(obj);
			     }, $scope.displayMsgType);
			angular.forEach(data.replyMsgType, function(key, value) {
				var obj={};
				obj.key=key;
				obj.value=value;
		       this.push(obj);
		     }, $scope.replyMsgType);
			angular.forEach(data.allMsgType, function(key, value) {
				var obj={};
				obj.key=key;
				obj.value=value;
				this.push(obj);
			}, $scope.allMsgType);
		
	});
	
	
	
}]);