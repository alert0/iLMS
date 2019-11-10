
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	var id = $.getParameter("id");
	
	// 初始化值
	$scope.$on("afterLoadEvent",function(ev,data){
		if(!id){
			$scope.data.mailType = 'pop3';
			$scope.data.validate = 1;
			$scope.data.isHandleAttach = 1;
		}
	});
	
	var obj={
	}
	
	$scope.addRow=function(classVar){
		$scope.data[classVar +"List"].push(angular.copy(obj[classVar]))
	}
	
	$scope.$on("beforeSaveEvent",function(ev,data){
		if($scope.data.mailType=='pop3'){
			$scope.data.imapHost = '';
			$scope.data.imapPort = '';
		}else{
			$scope.data.popHost = '';
			$scope.data.popPort = '';
		}
	});
	
	
	// 保存成功
	$scope.$on("afterSaveEvent",function(ev,data){
		if(data.r){
			window.location.reload();
		}
		else{
			HT.window.refreshParentGrid();
			HT.window.closeEdit(true,'grid');
		}
	});
	
	$scope.showInfos = function(){
		if($scope.data.mailAddress!=''){
			var address= $scope.data.mailAddress;
			var type= $scope.data.mailType;
			var s=address.substring(address.indexOf('@')+1,address.length+1).trim();
			$scope.data.smtpHost = 'smtp.'+s;
			$scope.data.popHost = 'pop.'+s;
			if(type=='pop3'){
				if(s=='gmail.com'||s=='msn.com'||s=='live.cn'||s=='live.com'||s=='hotmail.com'){
					$scope.data.sSL = 0;
				}else{
					$scope.data.popPort = '110';
					$scope.data.smtpPort = '25';
				}
			}else{
				if(s=='gmail.com'||s=='msn.com'||s=='live.cn'||s=='live.com'||s=='hotmail.com'){
					$scope.data.sSL = 0;
				}else{
					$scope.data.popPort = '143';
					$scope.data.smtpPort = '25';
				}
				$scope.data.imapHost = 'imap'+'.'+s;
			}
		}
	}
	
	$scope.changeSSL = function(){
		if($scope.data.sSL=='1'){
			$scope.data.imapPort = '993';
			$scope.data.smtpPort = '465';
			$scope.data.popPort = '995';
		}else{
			$scope.data.imapPort = '143';
			$scope.data.smtpPort = '25';
			$scope.data.popPort = '110';
		}
	}
	
	$scope.testConnect = function(){
		if(!$scope.data.mailAddress && $scope.data.password==''){
			$.topCall.error("请先填写邮箱地址!");
    		$("#mailAddress").focus();
    		return false;
    	}
		if(!id && !$scope.data.password){
			$.topCall.error("请先填写邮箱密码!");
    		$("#password").focus();
    		return false;
    	}
		$.topCall.progress();
		$('#mailSetingForm').attr('action','test.ht?isEdit=1&id='+id);
		$('#mailSetingForm').ajaxSubmit(function(data){
			var obj=new com.hotent.form.ResultMessage(data);
			$.topCall.closeProgress();
			if(obj.isSuccess()){//成功
				$.topCall.success("连接成功！");
		    }else{//失败
		    	$.topCall.error("测试连接失败！error:"+obj.getMessage());
		    }
        });
	}
	
}]);