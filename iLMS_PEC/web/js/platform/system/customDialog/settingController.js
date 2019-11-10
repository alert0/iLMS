var app = angular.module('app',['base','CustomQueryService','arrayToolService','CustomDialogService','formDirective']);
app.controller("Controller",['$scope','baseService','CustomQuery','ArrayToolService','CustomDialog',function($scope,BaseService,CustomQuery,ArrayToolService,CustomDialog){
	$scope.ArrayTool=ArrayToolService;
	
	var params = {};
	
    //初始化页面传来的信息
	$scope.prop=CloneUtil.deep(window.passConf.prop);
	$scope.exitCustomDialogList=[];
	$scope.exitSelectorList=[];
    //获取系统已有自定义对话框列表
    CustomDialog.getAll(function(data){
    	for(var i=0;i<data.length;i++){
    		var cd = data[i];
    		cd.id=cd.id;
    		cd.displayfield=JSON.parse(cd.displayfield);
    		cd.resultfield=JSON.parse(cd.resultfield);
    		for(var j=0;j<cd.resultfield.length;j++)
    		{
    			cd.resultfield[j].field=cd.resultfield[j].comment;
    		}
    		cd.sortfield=JSON.parse(cd.sortfield);
    		cd.conditionfield=JSON.parse(cd.conditionfield);
    		$scope.exitCustomDialogList.push(cd);
    	}
	});
    
    //获取系统已有的选择器
    CustomDialog.getSelectorAll(function(data){
     
    	for(var i=0;i<data.length;i++){
    		var cd ={};
    		cd.id=data[i].id;
    		cd.resultfield=[];
    		cd.name=data[i].name;
    		cd.alias=data[i].alias;
    		var groupField=JSON.parse(data[i].groupField);
    		for(var j=0;j<groupField.length;j++)
    		{
    			var item={field:groupField[j].name,comment:groupField[j].key};
    			cd.resultfield.push(item);
    		}
    		//$scope.exitCustomDialogList.push(cd);
    		$scope.exitSelectorList.push(cd); 
    	}
	});
    
    if($scope.prop.sqlBuildType==null) $scope.prop.sqlBuildType=0;
    $scope.prop.sqlBuildType=$scope.prop.sqlBuildType+"";
    
	var params = {dsalias:$scope.prop.dsalias,isTable:$scope.prop.isTable,objName:$scope.prop.objName};
	CustomQuery.getTable(params,function(data){
		$scope.table=data.table;
	});
	
    $scope.addColumns=function(){
    	if($scope.prop.displayfield==null&&$scope.prop.style=='0'){
    		$scope.prop.displayfield=[];
    	}
    	if($scope.prop.displayfield==null&&$scope.prop.style=='1'){
    		$scope.prop.displayfield={};
    	}
    	if($scope.prop.conditionfield==null){
    		$scope.prop.conditionfield=[];
    	}
    	if($scope.prop.resultfield==null){
    		$scope.prop.resultfield=[];
    	}
    	if($scope.prop.sortfield==null){
    		$scope.prop.sortfield=[];
    	}
    	
    	//检查是否选择了列
    	for(var i in $scope.table.columnList){
			var c = $scope.table.columnList[i];
			if(c.selected=='yes') break;
			if(i==$scope.table.columnList.length-1){
				$.topCall.warn('请选择左边的列');
				return;
			}
    	}
    	
    	//加到显示字段
    	if($('.tabs-selected').find('.tabs-title').html()=='显示字段'){
    		for(var i in $scope.table.columnList){
				var c = $scope.table.columnList[i];
				if(c.selected!='yes') continue;
				var column = {};
				column.field=c.fieldName;
				column.comment=c.comment;
				column.nameKey="0";
				if($scope.prop.style=='0'){
					$scope.prop.displayfield.push(column);
				}else if($scope.prop.style=='1'){
					eval("$scope."+$scope.activeModelStr+"='"+c.fieldName+"'");
				}
			}
    	}
    	
    	//加到条件字段的条件选择
    	if($('.tabs-selected').find('.tabs-title').html()=='条件字段'){
    		for(var i in $scope.table.columnList){
				var c = $scope.table.columnList[i];
				
				if(c.selected!='yes') continue;
				var column = {};
				column.field=c.fieldName;
				column.comment=c.comment;
				column.condition='EQ';
				column.dbType=c.columnType;
				column.defaultType='2';
				column.defaultValue='';
				
				$scope.prop.conditionfield.push(column);
			}
    		//去除重复项
    		$scope.prop.conditionfield.unique(function(a,b){
    			return a.field==b.field;
    		});
    	}
    	
    	//加到返回字段
    	if($('.tabs-selected').find('.tabs-title').html()=='返回字段'){
    		for(var i in $scope.table.columnList){
				var c = $scope.table.columnList[i];
				
				if(c.selected!='yes') continue;
				var column = {};
				column.field=c.fieldName;
				column.comment=c.fieldName;
				column.idKey="0";
				column.AggFuncOp='';//合计函数运算符
				
				$scope.prop.resultfield.push(column);
			}
    		//去除重复项
    		$scope.prop.resultfield.unique(function(a,b){
    			return a.field==b.field;
    		});
    	}
    	
    	//加到排序字段
    	if($('.tabs-selected').find('.tabs-title').html()=='排序字段'){
    		for(var i in $scope.table.columnList){
				var c = $scope.table.columnList[i];
				
				if(c.selected!='yes') continue;
				var column = {};
				column.field=c.fieldName;
				column.sortType="asc";
				
				$scope.prop.sortfield.push(column);
			}
    		//去除重复项
    		$scope.prop.sortfield.unique(function(a,b){
    			return a.field==b.field;
    		});
    	}
	}
    
    //改变组件类型，如果是自定义对话框，需要初始化这个json
    $scope.changeCt=function(column){
    	if(column.controllerType==0||column.controllerType==2){
    		column.customDialogCt={};
    	}else{
    		delete column.customDialogCt;
    	}
    }
    
    $scope.changeCustomDiaologCt=function(column){
    	var theData=$scope.exitCustomDialogList;
    	if(column.controllerType==2){
    		theData=$scope.exitSelectorList;
		}
    	for(var i=0;i<theData.length;i++){
    		var cd = theData[i];
        	if(cd.id==column.customDialogCt.id){
        		column.customDialogCt.resultfield=cd.resultfield;
        		column.customDialogCt.width=cd.width;
        		column.customDialogCt.height=cd.height;
        		column.customDialogCt.name=cd.name;
        		column.customDialogCt.alias=cd.alias;
//        		console.info(column.customDialogCt);
        		return;
    		}
    		
    	}
    }
    
  	//保存按钮
    $scope.save=function(){
    	for(var i in $scope.prop.conditionfield){
    		var column = $scope.prop.conditionfield[i];
			if(column.dbType=='date'&&column.condition=='BETWEEN'){
				column.defaultValue+="|"+column.endDate;
				delete column.endDate;
			}
    	}
    	if($scope.prop.sortfield&&$scope.prop.sortfield.length>3){
    		$.topCall.error("您添加的排序字段太多，最多只能添加3个排序字段！");
    		return ;
    	}
    	window.passConf.parentScope.prop=CloneUtil.deep($scope.prop);
    	window.selfDialog.dialog('close');
	}
    
    $scope.activeModelStr=null;
    $scope.setActiveModel=function(modelName){
    	$scope.activeModelStr=modelName;
    };
  	
    //选择自定义sql常用脚本
    $scope.selectScript_diySql = function(){
    	new ScriptSelector(function(script) {
    		$scope.$apply(function(){
    			if(!$scope.prop.diySql) $scope.prop.diySql="";
    			$scope.prop.diySql+=script;
    		}); 
    	}).show();
    };
    
    //选择条件字段时脚本sql常用脚本
    $scope.selectScript_column = function(column){
    	new ScriptSelector(function(script) {
    		$scope.$apply(function(){
    			if(!column.defaultValue) column.defaultValue="";
    			column.defaultValue+=script;
    		}); 
    	}).show();
    };
    
    //选择回填相关的nameKey
    $scope.selectNameKey = function(column){
    	$($scope.prop.displayfield).each(function(){
    		this.nameKey="0";
    	});
    	column.nameKey="1";
    };
    
    //选择回填相关的idKey
    $scope.selectIdKey = function(column){
    	$($scope.prop.resultfield).each(function(){
    		this.idKey="0";
    	});
    	column.idKey="1";
    };
    
	 $scope.selectVar = function(){
    	if($scope.customVar){
    		if(!$scope.prop.diySql){
    			$scope.prop.diySql = "";
    		}
    		$scope.prop.diySql += " " +  $scope.customVar;
    	}
    }

    
  	//运算条件数组-number
	$scope.number_opList=[
		{
			key:'等于',
			value:'EQ'
		},
		{
			key:"大于等于",
			value:'GE'
		},
		{
			key:"大于",
			value:'GT'
		},
		{
			key:"小于",
			value:'LT'
		},
		{
			key:"小于等于",
			value:'LE'
		},
		{
			key:"in",
			value:'IN'
		}
	];
  	
	//运算条件数组-varchar
	$scope.string_opList=[
		{
			key:'等于',
			value:'EQ'
		},
		{
			key:"like",
			value:'LK'
		},
		{
			key:"likeEnd",
			value:'LFK'
		},
		{
			key:"in",
			value:'IN'
		}
	];
	
	//运算条件数组-日期
	$scope.date_opList=[
		{
			key:'等于',
			value:'EQ'
		},
		{
			key:"between",
			value:'BETWEEN'
		},
		{
			key:"大于等于",
			value:'GE'
		},
		{
			key:"小于等于",
			value:'LE'
		}
	];
    
	//值来源数组_列表
	$scope.value_sourceList_list=[
		{
			key:'参数传入',
			value:'1'
		},
		{
			key:"固定值",
			value:'2'
		},
		{
			key:"脚本",
			value:'3'
		},
		{
			key:"动态传入",
			value:'4'
		},
		{
			key:"可选条件",
			value:'7'
		}
	];
	
	//值来源数组_树
	$scope.value_sourceList_tree=[
		{
			key:"固定值",
			value:'2'
		},
		{
			key:"脚本",
			value:'3'
		},
		{
			key:"动态传入",
			value:'4'
		}
	];
	
	//排序字段升序还是降序
	$scope.sort_typeList=[
		{
			key:'升序',
			value:'asc'
		},
		{
			key:'降序',
			value:'desc'
		}
	];
	
	//控制器的类型
	$scope.param_ctList=[
		{
			key:'自定义对话框',
			value:'0'
		},
		{
			key:'单行文本框',
			value:'1'
		},
		{
			key:'控件选择器',
			value:'2'
		}
	];
}]);