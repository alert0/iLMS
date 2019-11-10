var ReplyApp = angular.module('replyApp',['baseServices','MessageReplyService']);
ReplyApp.controller("MessageReplyController",['$scope','BaseService','MsgReply',function($scope,BaseService,MsgReply){
	$scope.messageReply={};//回复消息
	$scope.sysMessage={};//消息

	if(messageId!=""&&messageId!=0){
		MsgReply.getMsgData({messageId:messageId},function(data){
			$scope.sysMessage=data.sysMessage;
		})
	}
	
	
}]);