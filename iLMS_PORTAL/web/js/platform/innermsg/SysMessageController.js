var MSGApp = angular.module('msgApp',['baseServices','SysMessageService','MessageTypeService']);
MSGApp.controller("SysMessageController",['$scope','BaseService','SysMessage','MsgType',function($scope,BaseService,SysMessage,MsgType){
	$scope.sysMessage={};//初始化
	//$scope.sysMessage.content='<p>测试数据的话附件</p><p style="line-height: 16px;"><img style="vertical-align: middle; margin-right: 2px;" src="http://localhost:8085/x5/js/lib/ueditor/dialogs/attachment/fileTypeImages/icon_doc.gif"/><a style="font-size:12px; color:#0066cc;" href="/x5D:/work1/trunk/x5-bpmx-platform/src/main/webapp/js/lib/ueditor/jsp/upload/file/20150120/1421718668771003751.docx" title="1421718668771003751.docx">1421718668771003751.docx</a></p>'
	$scope.displayMsgType=[];//可见的消息类型
	$scope.replyMsgType=[];//是否可以回复的消息类型
	
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
	});
	
	//消息类型改变时控制是否回复的可见
	$scope.changeMsgType=function(index){
		var replyAry=$scope.replyMsgType;
		var messageType = $scope.sysMessage.messageType;
		var isCanReply = false;
		if(messageType=="bulletin"){
			$("#canReplyTr").hide();
			$("#receiverNameTr").hide();
			$("#receiverOrgNameTr").hide();
			$scope.sysMessage.isPublic=1;
			$scope.sysMessage.canReply=0;
			return;
		}
		$("#receiverNameTr").show();
		$("#receiverOrgNameTr").show();
		for(var i in replyAry){
			var reply=replyAry[i];
			if(messageType==reply.value){
				$("#canReplyTr").show();
				isCanReply=true;
			}
		}
		if(!isCanReply){
			$("#canReplyTr").hide();
		}
		$scope.sysMessage.isPublic=0;
		$scope.sysMessage.canReply=0;
		
	}
	
	$scope.showUserDialog = function(index){
		new UserSelectDialog({type:"1",callback:function(userIds,fullnames,dialog){
			$scope.sysMessage.receiverId=userIds.join(",");
			$scope.sysMessage.receiverName=fullnames.join(",");
			$scope.$digest();
		}}).show();
	}
	
	$scope.reSetUser = function(index){
		$scope.sysMessage.receiverId="";
		$scope.sysMessage.receiverName="";
	}
	
	$scope.showOrgDialog = function(index){
		new GroupSelectDialog({single:false,type:'1',dimKey:'key',callback:function(groupIds,names,dialog){
			$scope.sysMessage.receiverOrgId=groupIds.join(",");
			$scope.sysMessage.receiverOrgName=names.join(",");	
			$scope.$digest();
		}}).show();
		
	}
	
	$scope.reSetOrg = function(index){
		$scope.sysMessage.receiverOrgId="";
		$scope.sysMessage.receiverOrgName="";
	}
	
	
	
	
	$scope.save = function(){
		var content = ue.getContent();
		if(!$scope.sysMessage.subject||$scope.sysMessage.subject==""){
			$.topCall.error("请填写消息标题!");
		}else if(!$scope.sysMessage.messageType||$scope.sysMessage.messageType==""){
			$.topCall.error("请选择消息类型!");
		}else if($scope.sysMessage.isPublic!=1 && (!$scope.sysMessage.receiverOrgId||$scope.sysMessage.receiverOrgId=="") && (!$scope.sysMessage.receiverId||$scope.sysMessage.receiverId=="")){
			$.topCall.error("请选择收信人或者收信组织!");
		}else if(!content && content==""){
			$.topCall.error("请填写消息内容!");
		}else{
			$scope.sysMessage.content = content;
			SysMessage.saveData({sysMessage : JSON2.stringify($scope.sysMessage)},function(data){
				if(data.result==1){
					$.topCall.success("消息发送成功!",function(result){
							window.location.reload(true);
					},"温馨提示");
				}else{
					$.topCall.error(data.message);
				}
			})
		}
		
	}
	
	/*//普通是否数组
	$scope.replySelect=[
		{
			key:'个人消息',
			value:'a'
		},
		{
			key:"公告消息",
			value:'b'
		},
		{
			key:"流程消息",
			value:'c'
		},
		{
			key:"系统消息",
			value:'d'
		}
	];*/
	
}]);