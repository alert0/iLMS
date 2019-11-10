function rowToJson(row){
	var json ={};
	for(var i=0;i<row.length;i++){
		var r = row[i];
		try{
			eval('json.'+r.key+'="'+r.value+'"');
		}catch(e){
			eval('json.'+r.key+'='+r.value+'');
		}
	}
	return json;
}
function jsonToRow(json){
	var row=[];
	for(var k in json){
		row.push({"key":k,"value":json[k]});
	}
	return row;
}

var zNodes;
var zTree;
// 初始化树的配置文件
function initTreeSetting(prop) {
	// setting异步加载的设置
	var setting = {
		async : {
			enable : true, // 表示异步加载生效
			url : __ctx + "/form/customDialog/getTreeData?id=" + prop.id, // 异步加载时访问的页面
			autoParam : [ prop.displayfield.id + "=idKey"], // 异步加载时自动提交的父节点属性的参数
			type : 'post',
			dataType : 'json'
		},
		check: {
			enable: (prop.selectNum!=1),
			chkboxType : { "Y" : "", "N" : "" }
		},
		data : { // 用pId来标识父子节点的关系
			key : {name: prop.displayfield.displayName},
			simpleData : {
				enable : true,
				idKey : prop.displayfield.id+"",
				pIdKey : prop.displayfield.pid+"",
				rootPid : prop.displayfield.pvalue+""
			}
		},
		callback : { // 回调函数
			onClick : zTreeOnClick, // 单击鼠标事件
			asyncSuccess : zTreeOnAsyncSuccess,
			beforeCheck: zTreeBeforeCheck
		}
	};
	return setting;
}
// 单击时获取zTree节点的Id,和value的值
function zTreeOnClick(event, treeId, treeNode, clickFlag) {
	var scope =$("body").scope();
	if(scope.prop.selectNum==1){
		scope.selectedList=[];
		scope.addToSelectList(treeNode);
	}else{
		var treeObj = $.fn.zTree.getZTreeObj("ztree");
		var nodes = treeObj.getSelectedNodes();
		for (var i=0, l=nodes.length; i < l; i++) {
			//调用checked改变方法
			if(zTreeBeforeCheck(treeId, treeNode)){
				treeObj.checkNode(nodes[i], !treeNode.checked, true);
			}
		}
	}
	
	//这里触发组合对话框的事件CombinateDialog/show.jsp
	if(window.parent.treeClick){
		window.parent.treeClick(getScope().getTreeResult());
	}
};
function zTreeBeforeCheck(treeId, treeNode) {
	var scope =$("body").scope();
	if(!treeNode.checked){
		if(scope.addToSelectList(treeNode)){
			return true;
		}
		return false;
	}else{
		scope.ArrayTool.remove(treeNode,scope.selectedList);
		return true;
	}
};
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
}

// 初始化加载节点
function initTreeRoot(prop) {
	$.ajax({
		url : __ctx + "/form/customDialog/getTreeData?id=" + prop.id,
		type : 'post',
		dataType : 'json',
		async : false,
		data : {
			'isRoot' : '1'
		},
		success : function(data) {
			zNodes = data;
		}
	});
};
// 初始化整棵树
function initTree(prop) {
	initTreeRoot(prop);
	var setting=initTreeSetting(prop)
	zTree=$.fn.zTree.init($("#ztree"),setting,zNodes);
}

var app = angular.module('app',['base','CustomDialogService','arrayToolService']);
app.controller("Controller",['$scope','CustomDialog','ArrayToolService',function($scope,CustomDialog,ArrayToolService){
	$scope.ArrayTool=ArrayToolService;
	
    CustomDialog.detail({id:id},function(data){
    	$scope.prop=data;
    	$scope.prop.conditionfield=JSON.parse(data.conditionfield);
    	$scope.prop.displayfield=JSON.parse(data.displayfield);
    	$scope.prop.resultfield=JSON.parse(data.resultfield);
    	if(selectNum&&!isNaN(selectNum)){
    		$scope.prop.selectNum=selectNum;
    	}
    	
    	//检查是否需要显示搜索字段
    	if($scope.prop.conditionfield.length>0){
    		$scope.showSearch=true;
	    	for(var i=0;c=$scope.prop.conditionfield[i];i++){
	    		if(c.defaultType=='1'){
	    			break;
	    		}
	    		if(i==$scope.prop.conditionfield.length-1){
	    			$scope.showSearch=false;
	    		}
	    	}
    	}else{
    		$scope.showSearch=false;
    	}
    	
    	if(data.style==1){
    		initTree($scope.prop);
    		$("#cc").css("display","none");
    	}else{
    		$scope.nameKeyIndex=0;//显示字段index默认是第一个
	    	$scope.nameKeyColumn=null;
	    	$($scope.prop.displayfield).each(function(index){
	    		if(this.nameKey=="1"){
	    			$scope.nameKeyIndex=index;
	    			$scope.nameKeyColumn=this;
	    		}
	    	});
	    	
	    	$scope.idKeyColumn=null;
	    	$($scope.prop.resultfield).each(function(index){
	    		if(this.idKey=="1"){
	    			$scope.idKeyColumn=this;
	    		}
	    	});
    		
    		//单选就不要显示已选列
    		if($scope.prop.selectNum==1){
    			$('#cc').layout('remove','east');
    		}
    		$scope.search();
    		
    		initData();
    	}
	});
    
    //异步查询结果
    $scope.search=function(pageNo){
    	pageNo=(pageNo==null)?1:pageNo;
    	
    	var queryParam = {};
    	//获取参数输入
    	for(var i in $scope.prop.conditionfield){
    		var column = $scope.prop.conditionfield[i];

    		if(column.defaultType!='1'||column.value==null||column.value==''){
    			continue;
    		}
    		
    		queryParam[column.field] =column.value;
    		if(column.condition=='BETWEEN'&&column.endDate!=null){
    			queryParam[column.field]+="|"+column.endDate;
    		}
    	}
    	
    	//开始加入页面url传入的参数
    	var strs =paramValueString.split('|');
    	for(var i=0;i<strs.length;i++){
    		var str = strs[i];
    		if(str.split(":")[0].length<=0){
    			continue;
    		}
    		queryParam[str.split(":")[0]] = str.split(":")[1];
    	}
    	
		var params = {
			queryParam:JSON.stringify(queryParam),
			id:$scope.prop.id,
			page:pageNo,
		};
		CustomDialog.search(params,function(data){
			 $scope.page=data;
		});
	}
    
    //字段展现控件
    $scope.showCt=function(column){
    	var type=column.controllerType;
    	//人员选择器(单选)
    	if(type=='2'){
    		 
    		var selector=eval(column.customDialogCt.alias);
    		selector({single:true,callBack:function(data,dialog){
    			var names=[];
				for(var i=0;i<data.length;i++){
					var t = data[i];
					 eval("names.push("+"t."+column.customDialogCt.returnfield+");");    
				}
				handleCtResult(names,column);    			 
    		 
    	    }
    		});

    		var single=true;

    		new UserSelectDialog({single:single,type:'1',callback:function(ids,names,records,dialog){
    			handleCtResult(names, column);
    		}}).show();
    	}
    	//自定义对话框
    	else if(type=='0'){
    		var parm={alias:column.customDialogCt.alias,callBack:function(data,dialog){
    			var names=[];
				for(var i=0;i<data.length;i++){
					var t = data[i];
					 eval("names.push("+"t."+column.customDialogCt.returnfield+");");    
				}
				handleCtResult(names,column);
				dialog.dialog('close');
    		}};
    		CustomDialog.customDialog(parm);
    	}
    }
    
    //检查选择数的界限
    $scope.checkSelectNum=function(){
    	if($scope.prop.selectNum==-1) return true;//-1代表多选
    	if($scope.prop.selectNum==1){//单选
    		$scope.selectedList=[];//初始化
    	}
    	if($scope.selectedList.length<$scope.prop.selectNum){
			return true;
		}
    	return false;
    }
    
    //添加一条信息到已选队列
    $scope.addToSelectList=function(column,radioId){
    	var radio=null;
    	if(radioId!=null){
    		radio=$('#radio'+radioId)[0];
    	}
    	
    	if( $scope.selectedList==null){//初始化
    		 $scope.selectedList =[];//已选数组
    	}
    	
		if(checkExist(column)){//判断是否已存在
			if(radio!=null){
				radio.checked='true';
			}
    		return true;
    	}
    	if($scope.checkSelectNum()){
    		$scope.selectedList.push(column);
    		if(radio!=null){
				radio.checked='true';
			}
    		return true;
    	}else{
    		alert("选择上限已到("+$scope.prop.selectNum+"),请删除后再选择");
    		return false;
    	}
    }
    
    //把当前页面的所有信息添加到已选队列
    $scope.addPageToSelectList=function(){
    	for(var i=0;i<$scope.page.rows.length;i++){
    		var row=$scope.page.rows[i];
    		$scope.addToSelectList(row);
    	}
    }
    
    //返回被选列的json信息
    $scope.getResult=function(){
    	var result=[];
    	for(var i=0;$scope.selectedList!=null&&i<$scope.selectedList.length;i++){
    		var row = $scope.selectedList[i];
			result.push(rowToJson(row.resultRow));
    	}
    	return result;
    }
    
    //返回树选择的节点
    $scope.getTreeResult=function(){
		var aryField=$scope.prop.resultfield;
		var aryRtn=new Array();
		var nodes = $scope.selectedList;
		if(nodes.length<1){
			return -1;	
		}
		
		for(var i=0;i<nodes.length;i++){
			var obj=new Object();
			var node=nodes[i];
			for(var j=0;j<aryField.length;j++)	{
				var field=aryField[j].comment;
				obj[field]=node[field];
				if(node[field]==null){
					obj[field]="";
				}
			}
			aryRtn.push(obj);
		}
		return aryRtn;
    }
    
    //监听分页输入数字的回车事件
    $scope.pageChangePress=function(event,num){
    	if(event.keyCode==13){
    		$scope.search(num);
    	}
    }
    
    //处理控件的返回结果
    function handleCtResult(names,column){
    	if(names!=null&&names.length>0){
			var str="";
			for(var i=0;i<names.length;i++){
				str+=names[i];
				if(i!=names.length-1){
					str+=","
				}
			}
			$scope.$apply(function(){
                column.value=str;
            });
		}
    }
    
    //处理列表中的回填
    function initData(){
    	//将其翻译成row对象。。这个对象太多余了。。本人作为开发者很惭愧。。。但是要改起来更麻烦，先留这吧，我会回来重构的！！
    	if(!window.passConf) return;
    	$(window.passConf).each(function(){
    		var column={};
    		column.resultRow=jsonToRow(this);
        	var displayRow=jsonToRow(this);
        	column.displayRow=displayRow;
        	
        	$scope.addToSelectList(column);
    	});
    }
    
    /**
	 * 检查已存在
	 */
	function checkExist(column){
		var b=$.inArray(column, $scope.selectedList)!=-1;
		if(b||!$scope.idKeyColumn) return b;
		var columnRJson=rowToJson(column.resultRow);
		for(var i=0;i<$scope.selectedList.length;i++){
			var rJson = rowToJson($scope.selectedList[i].resultRow);
			if(columnRJson[$scope.idKeyColumn.comment]&&columnRJson[$scope.idKeyColumn.comment]==rJson[$scope.idKeyColumn.comment]){
				b=true;
			}
		}
		return b;
	}
}]);