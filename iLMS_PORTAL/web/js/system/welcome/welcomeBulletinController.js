var app = angular.module('app', [ 'formDirective', 'arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService', 'ArrayToolService','$compile', function($scope, baseService, ArrayTool,$compile) {
	$scope.ArrayTool = ArrayTool;

	$scope.init = function() {
		$scope.data = {};
		
		$scope.data.pendingList = null;//待办列表
		$scope.data.messageList = null;//通讯录列表
		$scope.data.addressList = null;//通讯录列表
		
		$scope.refreshBulletins('all',true);
	};
	
	$scope.refreshBulletins = function(type,isInit){
		if(type=='all' || type=='pending'){
			refreshPending(isInit);
		}
		if(type=='all' || type=='message'){
			refreshMessageList(isInit);
		}
		if(type=='all' || type=='address'){
			refreshAddressList(isInit);
		}
	}
	
	$scope.toTask = function(id) {
		var url= __ctx + '/flow/task/canLock?taskId=' + id
		$.get(url, function(rtn){
			//0:任务已经处理,1:可以锁定,2:不需要解锁 ,3:可以解锁，4,被其他人锁定,5:这种情况一般是管理员操作，所以不用出锁定按钮。
			switch(rtn){
				case 0:
					$.topCall.success("此任务已经被其他人处理完成!");
					break;
				case 1:
				case 2:
				case 3:
				case 5:
					$.openFullWindow(__ctx + '/flow/task/taskApprove?id=' + id);
					break;
				case 4:
					$.topCall.success("此任务已经被其他人锁定!");
					break;
			}
		});
	}
		
	
	// 获取代办信息
	function refreshPending(isInit){
		baseService.postForm(__ctx + "/office/receivedProcess/pendingJson", {}).then(function(data) {//receivedProcess/handledJson
			$scope.data.pendingList = data.rows;
			if(isInit){
				$compile($("#pendingDiv"))($scope);
			}
		});
	}
	
	// 获取内部消息
	function refreshMessageList(isInit){
		baseService.postForm(__ctx + "/innermsg/messageReceiver/listJson", {}).then(function(data) {
			$scope.data.messageList = data.rows;
			if(isInit){
				$compile($("#messageDiv"))($scope);
			}
		});
	}
	
	// 获取通讯录信息
	function refreshAddressList(isInit){
		baseService.postForm(__ctx + "/org/user/addressList", {page:1,rows:10}).then(function(data) {
			$scope.data.addressList = data.rows;
			if(isInit){
				$compile($("#addressDiv"))($scope);
			}
		});
	}
	
	$scope.openMsgDetail = function(id,isPublic,canReply){
		var url = __ctx +"/innermsg/messageRead/get?id="+id+"&isPublic="+isPublic+"&canReply="+canReply;
		var title = "查看详细信息";
		var option = {};
	 	HT.window.openEdit(url, title, "", 'grid', 500, 500, option, null, null, true);
	}
	
} ]);