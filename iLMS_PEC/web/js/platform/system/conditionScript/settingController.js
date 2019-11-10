var app = angular.module('app',['base','arrayToolService','ConditionScriptService']);
app.controller("SettingController",['$scope','ArrayToolService','baseService','ConditionScript',function($scope,ArrayToolService,baseService,ConditionScript){	
	$scope.ArrayTool=ArrayToolService;
	$scope.prop={};
	if(calc&&calc.scriptId){
		baseService.get(__ctx+"/system/conditionScript/getObject?id="+calc.scriptId).then(function(data){
			if(data){
				$scope.prop.id=data.id;
				$scope.prop.methodDesc=data.methodDesc;
				$scope.prop.methodName=data.methodName;
				$scope.prop.params= calc.params;
				ConditionScript.getById($scope.prop.id,function(data){
					$scope.conditionScript=data;
				});
			}
		});
	}
	//打开人员脚本选择列表dialog
	$scope.showConditionScriptDialog=function(){
		if(type==2)	{
			CustomDialog.openCustomDialog("userScriptSelector",$scope.callBack);
		}
		else{
			CustomDialog.openCustomDialog("conditionScriptSelector",$scope.callBack);
		}
	}
	$scope.getScript=function(){
		if($scope.prop.id==null||$scope.prop.id==""){
			$.topCall.error("请选择脚本");
			return;
		}
		
		var script=""+$scope.conditionScript.classInsName+"."+$scope.conditionScript.methodName+"(";
		for(var i=0;p=$scope.prop.params[i];i++){
			script+=dealValue(p)+" ";
			if(i<$scope.prop.params.length-1){
				script+=",";
			}
		}
		script+=")";
		return script;
	}
	
	//处理固定值string类型参数的格式
	function dealValue(p){
		if(p.paraType=='java.lang.String'&&p.valueType=='1'){
			if(p.value&&p.value.indexOf("\"")!=0){
				p.value = p.value.indexOf('\'')==0?p.value.replace(/\'/g,'\"'):'\"'+p.value+'\"';
			}
		}
		return p.value;
	}
	
	//获取描述
	$scope.getDesc=function(){
		if($scope.prop.id==null||$scope.prop.id==""){
			$.topCall.error("请选择脚本");
			return;
		}
		
		var desc="【"+$scope.prop.methodDesc+"】参数:(";
		for(var i=0;p=$scope.prop.params[i];i++){
			var str=p.description || p.value;
			desc+=p.paraDesc+":"+str+" ";
			if(i<$scope.prop.params.length-1){
				desc+=",";
			}
		}
		desc+=")";
		return desc;
	}
	
	$scope.callBack = function(data,dialog){
		$scope.$apply(function(){
			if(data.length>0){
				$scope.prop.id=data[0].id_;
				$scope.prop.methodDesc=data[0].method_desc_;
				if(data[0].METHOD_NAME_){
					$scope.prop.methodName=data[0].METHOD_NAME_;
				}
				
				$scope.prop.params= data[0].argument_?eval("("+data[0].argument_+")"):{};
				
				ConditionScript.getById($scope.prop.id,function(data){
					$scope.conditionScript=data;
				});
			 
				$.each($scope.prop.params,function(){
					this.paraCt= this.paraCt || '';
					this.valueType="0";
				});
				 
			}else{
				$scope.prop.id="";
				$scope.prop.methodDesc="";
				$scope.prop.params=null;
			}
        });
		if(dialog){
			dialog.dialog('close');
		}
	};
	$scope.setActiveParam = function(param){
		$scope.currentEditParam = param;
		
	}
	//参数值类型数组
	$scope.valueTypeList=[
		{
			key:'变量',
			value:'0'
		},
		{
			key:"固定值",
			value:'1'
		}
	];
}]);