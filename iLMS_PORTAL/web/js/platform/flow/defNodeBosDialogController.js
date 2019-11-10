var  app = angular.module("app", ['base','formDirective','arrayToolService','ui.codemirror' ]);
app.controller('ctrl', ['$scope','baseService','ArrayToolService',function($scope,baseService,ArrayToolService){
	$scope.ArrayTool = ArrayToolService;
	$scope.data={};
	$scope.data.beforeShow = "";
	$scope.data.whenSave="";
	var data=window.passConf.data;
	//初始化数据
	if(data){
		$scope.data = data;
	}
	
	$scope.selectScript = function(){
		new ScriptSelector(function(script) {
			scope.$apply(function() {
				insetCode(script);
			});
		}).show();
	};
	
	$scope.selectIdentity = function() {
		var callBack = function(data, dialog) {
			dialog.dialog('close');
			if(!data||data.length<1)
				return;
			var str='scriptImpl.getNextNo("'+data[0].alias+'")';
			scope.$apply(function() {
				insetCode(str);
			});
		};
		
		CustomDialog.openCustomDialog("identitySelector",callBack);
	};
	
	function insetCode(script){
		var activeTab = $('#tabs').tabs('getSelected');//当前活动tab
		scope.CodeMirrorBroadcast = "afterScript";
		if (activeTab.panel("options").id == "beforeShow") {
			scope.CodeMirrorBroadcast = "preScript";
		}
		scope.$broadcast(scope.CodeMirrorBroadcast, function(CodeMirror) {
			CodeMirror.replaceSelection(script);
		});
	}
}]);