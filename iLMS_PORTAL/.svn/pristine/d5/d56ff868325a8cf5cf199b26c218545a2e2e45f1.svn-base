var app = angular.module('app',['base','SysTransDefService','arrayToolService']);
app.controller("EditController",['$scope','baseService','SysTransDef','ArrayToolService',function($scope,baseService,SysTransDef,ArrayToolService){
	$scope.ArrayTool=ArrayToolService;
	
	//对象
	$scope.prop={};
	$scope.prop.state=1;
	
	//页面用到的参数或临时变量
	$scope.param={};
	
	//检查sql语句的合法性
	$scope.checkSqlValidity=function(sql){
		sql=sql.replace(/{authorId}/g,"1");//先给当前用户随便赋值
		SqlUtil.checkValidity(sql,"LOCAL",false);
	}
	
	$().ready(function (){
		//初始化
		if(id!=""){
			SysTransDef.detail({id:id},function(data){
				try {
					$scope.prop=data;
					//初始化脚本编辑器
					var i = 0;
					while(typeof InitMirror != 'undefined' && InitMirror.editor != null && i == 0){
						InitMirror.getById("selectSql").insertCode(data.selectSql);
						InitMirror.getById("updateSql").insertCode(data.updateSql);
						InitMirror.getById("logContent").insertCode(data.logContent);
						i++;
					}
				}catch (e) {}
			});
		}
	});
	
	
	//保存
	$scope.save=function(){
		if($scope.myForm.$invalid) return;
		
		$scope.prop.selectSql = InitMirror.getById("selectSql").getCode();
		$scope.prop.updateSql = InitMirror.getById("updateSql").getCode();
		$scope.prop.logContent = InitMirror.getById("logContent").getCode();
		
		SysTransDef.save($scope.prop,function(data){
			if (data.result == 1) {
				parent.relaod();
				$.topCall.confirm("温馨提示", data.message + ',是否继续操作', function(r) {
					if (r) {
						window.location.reload(true);
					} else {
						HT.window.closeEdit(true, 'grid');
					}
				});
			} else {
				$.topCall.error("操作结果","出错信息",data.message);
			}
			
		});
	}
	
	//点击常用变量
	$scope.clickField=function(field){
		InitMirror.editor.insertCode(field.value);
	}
	
	//设置常用字段
	$scope.commonField=[
	   {
		   key:"授权人ID",
		   value:"{authorId}"
	   },
	   {
		  key:"授权人名称",
		  value:"{authorName}"
	   },
	   {
		  key:"目的人ID",
		  value:"{targetPersonId}"
	   },
	   {
		   key:"目的人名称",
		   value:"{targetPersonName}"
	   },
	   {
		   key:"所选id列表','分割",
		   value:"{ids}"
	   },
	   {
		   key:"所选name列表','分割",
		   value:"{names}"
	   }
	];
}]);