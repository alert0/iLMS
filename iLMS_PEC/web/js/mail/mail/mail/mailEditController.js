
var app = angular.module('app', ['formDirective','arrayToolService']);
app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
	$scope.ArrayTool=ArrayTool;
	
	var obj={
	}
	//树
	var linkmanTree;
	var menu;
	var focusFiled='';
	$scope.data={};
	$scope.$on("afterLoadEvent",function(ev,data){
		$scope.loadTree("initTree");
	});
	
	//加载树
	$scope.loadTree = function(condition){
		var setting = {
			data: {
				key : {
					name: "lINKADDRESS"
				},
				simpleData: {
					enable: true,
					idKey: "lINKID"
				}
			},
			view:{
				showIcon:false
			},
			callback:{
				onClick: zTreeOnLeftClick,
				onRightClick : zTreeOnRightClick
			}
		};
		$.post(__ctx +"/mail/mail/mailLinkman/getMailLinkmanData",{condition:condition},function(result){
			for(var i=0;i<result.length;i++){
				result[i].iconSkin="none";
			}
			linkmanTree= $.fn.zTree.init($("#linkmanTree"), setting, result);}
		);
		$("input[address='true']").focus(function(){
			focusFiled=$(this).attr('ng-model').split(".")[1];
		}).blur(function(){
			setTimeout(function(){
				focusFiled='';
			}, 200);
			
		});
	};
	/**
	 * 树右击事件
	 */
	function zTreeOnRightClick(e, treeId, treeNode) {
		if (treeNode) {
			linkmanTree.selectNode(treeNode);
			curSelectNode=treeNode;
			var isfolder = treeNode.isFolder;
			var h = $(window).height();
			var w = $(window).width();
			var menuWidth = 120;
			var menuHeight = 75;
			var menu = null;
			if (treeNode != null) {
				menu = $('#orgMenu');
			}
			var x = e.pageX, y = e.pageY;
			if (e.pageY + menuHeight > h) {
				y = e.pageY - menuHeight;
			}
			if (e.pageX + menuWidth > w) {
				x = e.pageX - menuWidth;
			}
			menu.menu('show', {
				left : x,
				top : y
			});
		}
	};
	
	$scope.delNode=function() {
		var treeNode=getSelectNode();
		var params = "id=" + treeNode.lINKID;
		$.post(__ctx +"/mail/mail/mailLinkman/remove", params, function(data) {
			var json =JSON.parse(data);
			if(json.result=='1'){
				linkmanTree.removeNode(treeNode);
				$.topCall.success(json.message);
			}else{
				$.topCall.warn(json.message);
			}
		});
	};
	function getSelectNode(){
		linkmanTree = $.fn.zTree.getZTreeObj("linkmanTree");
		var nodes = linkmanTree.getSelectedNodes();
		var treeNode = nodes[0];
		return treeNode;
	}
	//左击
	function zTreeOnLeftClick(event, treeId, treeNode){
		if(focusFiled==''){
			$.topCall.toast("提示信息","请选择要填入的地址");
			 return;
		}
		var address=treeNode.lINKADDRESS;
		address=address.substring(address.indexOf('(')+1,address.indexOf(')'));
		if(!$scope.data[focusFiled] || $scope.data[focusFiled]==''){
			$scope.data[focusFiled]=address;
		}else{
			var arrtemp=$scope.data[focusFiled].split(',');
			for(var i=0;i<arrtemp.length;i++){
				if(arrtemp[i]==address){
					 break;
				}else{
					if(i==arrtemp.length-1){
						$scope.data[focusFiled]+=","+address;
					}
				}
			}
		}
		$scope.$apply();
	};
	
	
	$scope.addRow=function(classVar){
		$scope.data[classVar +"List"].push(angular.copy(obj[classVar]))
	}
    //重 置
	$scope.reset=function() {
		$scope.data='';
	}
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
	
	$scope.sendMail = function(action){
		if(!$scope.data.senderAddress){
			$.topCall.error("请选择发件人!");
			return;
		}
		if(!$scope.data.receiverAddresses){
			$.topCall.error("请填写收件人邮箱地址!");
			return;
		}
		if(!$scope.data.subject){
			$.topCall.error("请填写邮件主题!");
			return;
		}
		if(!$scope.data.content){
			$.topCall.error("请填写邮件内容!");
			return;
		}
		$scope.submit(action);
	}
	
	$scope.submit = function(action){
		//$.topCall.progress("正在发送,请您耐心等待...");
		var msg = action==2?"发送":"保存";
		$scope.data.type = action;
		$scope.data.isReply = 0;
		var defer = baseService.post("send", $scope.data);
		defer.then(function(data){
			//$.topCall.closeProgress();
			if (data.result==1) {
			    $.topCall.success("邮件"+msg+"成功!",  function(r){
						HT.window.closeEdit(true,'grid');
					},"提示信息"
			    );
			} else {
				$.topCall.errorStack("邮件"+msg+"失败",data.message);
			}
		})
	}
	
	$scope.reset = function(){
		$("form")[0].reset(); 
		$scope.data.senderAddress = "";
		$scope.data.fileIds = "";
		$scope.data.content = "";
	}
	

	 $scope.selectLinkMan=function(object){
			var callBack = function(data, dialog) {
				dialog.dialog("close");
				$scope.data[object] =$scope.data[object] ||'';
				$scope.data[object+'List']=$scope.data[object+'List']||[];
				$scope.$apply(function() {
					$(data).each(function(index) {
						if($scope.data[object]==''){
							$scope.data[object]+= this.email;
							$scope.data[object+'List'].push(this);
						}else{
							var arr=$scope.data[object].split(',');
							var str=$scope.data[object].substr($scope.data[object].length-1,1);
							for(var i=0;i<arr.length;i++){
								if(arr[i]==this.email){
									break;
								}else{
									if(str==','){
										$scope.data[object]+= this.email;
									}else{
										$scope.data[object] += ','+this.email;
									}
									$scope.data[object+'List'].push(this);
								}
							}
								
						}
					});
				});
			};
			var conf = {
				initData : $scope.data[object+'List'],
				selectNum : ""
			};
			CustomDialog.openCustomDialog("xzyjfsr", callBack, conf);
	}

}]);