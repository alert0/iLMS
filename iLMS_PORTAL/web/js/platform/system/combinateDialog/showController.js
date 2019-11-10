var app = angular.module('app',['base','arrayToolService','CombinateDialogService']);
app.controller("ctrl",['$scope','baseService','ArrayToolService','CombinateDialog',function($scope,BaseService,ArrayToolService,CombinateDialog){
	$scope.ArrayTool=ArrayToolService;
	
	//页面用到的参数或临时变量
	$scope.param={};
	$scope.selectNum = -1;
	$scope.initData = "";
	if(__param){
		if(__param.selectNum)$scope.selectNum = __param.selectNum;
		if(__param.initData)$scope.initData = __param.initData;
	}
	
	//初始化
	CombinateDialog.detail({id:id},function(data){
		$scope.prop=data;
		$scope.prop.field=JSON.parse(data.field);
		
		$("#treeFrame").attr("src",__ctx+"/form/customDialog/customDialogShowTree?dialog_alias_="+data.treeDialog.alias);
		$("#listFrame").attr("src",__ctx+"/form/customDialog/customDialogShowList?dialog_alias_="+data.listDialog.alias+"&selectNum="+$scope.selectNum+"&initData="+$scope.initData);
		
		//改标题，暴力改元素
		$('.layout-panel-center').find(".panel-title").text(data.listDialogName);
		$('.layout-panel-west').find('.panel-title').text(data.treeDialogName);
	});
	
	$scope.treeDataChange=function(data){
		var d =data[data.length-1];//拿倒数第一条数据
		
		var urlParam="";
		var field=$scope.prop.field;
		for(var i=0;i<field.length;i++){
			var t = field[i].tree;
			var l = field[i].list;
			var val = d[t.comment];
			if(!val) continue;
			urlParam+="&"+l.field+"="+val;
		}
		
		$("#listFrame").attr("src",__ctx+"/form/customDialog/customDialogShowList?dialog_alias_="+$scope.prop.listDialog.alias+urlParam+"&selectNum="+$scope.selectNum+"&initData="+$scope.initData);
	}
}]);