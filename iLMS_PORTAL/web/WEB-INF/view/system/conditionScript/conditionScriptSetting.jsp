<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/ngEdit.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/util/Dialogs.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/conditionScript/conditionScriptService.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/conditionScript/settingController.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/conditionScriptDialog.js"></script>
<script type="text/javascript">
	var defId = "${param.defId}";//流程id
	var nodeId = "${param.nodeId}";//任务节点
	var type = "${param.type}";//1:条件脚本，2人员脚本
	var calc = window.passConf;
	function getResult() {
		var scope = $("body").scope();
		var script=scope.getScript();
		var desc=scope.getDesc();
		var id = scope.prop.id;
		var params = scope.prop.params;
		var data={scriptId:id,params:params,script:script,desc:desc}
		return data;
	}

	var targetObj;
	var varTree;

	$(function() {
		var scope = $("body").scope();
		varTree = new ZtreeCreator('varTree', "${ctx}/flow/node/varTree").setDataKey({
			name : 'desc'
		}).setCallback({
			onClick : setVariable
		}).makeCombTree("tempTree").initZtree({
			defId : '${param.defId}',
			nodeId : '${param.nodeId}',
			flowKey:'${param.flowKey}',
			includeBpmConstants : true
		}, 1);

		$(".varTree").bind("click", varTree.showMenu);

	});
	function showFlowMenu(obj, index) {
		targetObj = $(obj);
		varTree.showMenu(targetObj);
	}

	function setVariable(event, treeId, node) {
		var keyStr = node.name;
		var description=node.desc;
		var path = node.path;
		var parentNode = node.getParentNode();
		var parentNode2 = node.getParentNode();
		var boDefAlias = parentNode2.boDefAlias;
		while(parentNode2 && !parentNode2.boDefAlias){
			parentNode2 = parentNode2.getParentNode();
			if(!parentNode2){
				break;
			}
			boDefAlias = parentNode2.boDefAlias;
		}
		
		// 子表情况做提示
		 if (node.nodeType == 'sub'){
			keyStr = boDefAlias+".getSubByKey('"+node.name+"') /* 获取子表,return List<BoData> */";
		 }// 主表bo
		 else if(parentNode.nodeType == 'main'){
			keyStr = boDefAlias+'.getValByKey("'+node.name+'") /*数据类型：'+node.dataType+'*/';
		}else if(parentNode.nodeType == 'sub'){
			var mainTableName = boDefAlias;
			keyStr = mainTableName+'.getSubByKey("'+parentNode.name+'") /*获取子表数据 ，返回数据：return List<BoData> 。子表字段：'+node.name+', 请根据实际情况处理子表数据的获取*/';
		}else if(node.nodeType == 'var'){
			keyStr =node.name;
		}else return ;

		varTree.hideMenu();

		var scope = $("body").scope();
		scope.$apply(function() {
			scope.currentEditParam.value = keyStr;
			scope.currentEditParam.description = description;
		});

		varTree.hideMenu();
	}
	
	function gridOnclick(){
		var data = $("#listFrame")[0].contentWindow.getResult(); 
		if(data && data.length>0){
			var scope = $("body").scope();
			scope.callBack(data);
		}
	}
</script>
<style>
input {
	list-style: outside none none;
	border: 0px none;
	height: 28px;
}
</style>
</head>
<body ng-app="app" ng-controller="SettingController">
	
		<div style="width:480px;height: 100%;float:left;overflow: hidden;" >
			<iframe id="listFrame" src="${ctx}/form/customDialog/customDialogShowList?dialog_alias_=${param.type==2?'userScriptSelector':'conditionScriptSelector'}" frameborder="no" style="width:480px;height:100%;overflow: hidden;" ></iframe>
			<div id="tempTree"></div>
		</div>
	
		<div style="width:585px;height: 100%;float:left;padding-left: 10px;" >
			<div ng-if="prop.params">
				<table class="table-list" cellspacing="0" style="padding: 0px;margin: 0px;">
					<tr>
						<th colspan="4">当前所选脚本</th>
					</tr>
					<tr>
						<th>脚本别名</th>
						<td ng-bind="prop.methodName"></td>
						<th>脚本描述</th>
						<td ng-bind="prop.methodDesc"></td>
					</tr>
				</table>
			</div>
			<div style="margin-top: 5px;">
				<table class="table-list" cellspacing="0" style="padding: 0px;margin: 0px;">
					<tr>
						<th>参数名称</th>
						<th>参数类型</th>
						<th>参数说明</th>
						<th width="55%">参数值</th>
					</tr>
					<tr ng-repeat="param in prop.params">
						<td>{{param.paraName}}</td>
						<td>{{param.paraType}}</td>
						<td>{{param.paraDesc}}</td>
						<td>
							<select class="inputText" ng-model="param.valueType" ng-change="valueTypeChange(param.valueType,$index)" ng-options="m.value as m.key for m in valueTypeList" style="width: 100px;">
							</select> 
							<input ng-if="param.valueType==0" ng-model="param.value" ng-click="setActiveParam(param)" type="text" class="inputText" onClick="showFlowMenu(this)" placeholder="点击选择变量" /> 
							<span ng-if="param.valueType==1" style="width: 60%"> 
								<input id='input{{param.paraName}}' class="inputText" 
									ht-selector-bind="[{'name':'param.value'}]" ng-model="param.value" /> 
								<a href="javascript:void(0)" ng-if="param.paraCt !='' "  parms="{paraName:'{{param.paraName}}','id':'{{param.paraCt}}','paraCtBindKey':'{{param.paraCtBindKey}}','multi':{{param.multiSelect}}}" 
									onClick="showDialogSelector(this)"><i class="fa fa-search"></i>选择</a>
							</span>
						</td>
					</tr>
				</table>
			</div>
			<div style="clear: both;"></div>
		</div>
		<div style="clear: both;"></div>
</body>
</html>
<script>
	function showDialogSelector(obj) {
		var target = eval('(' + $(obj).attr("parms") + ')');
		var paraCt = target.id;
		var paraCtBindKey = target.paraCtBindKey;
		var index = paraCt.indexOf(":");
		var alias = paraCt.substring(index + 1);
		var selectorType = paraCt.substring(0, index);
		

		if (selectorType == "cusdg") {
			CustomDialog.openCustomDialog(alias, function(data, dialog) {
				if (!data) {
					dialog.dialog('close');
					return false;
				}
				
				setValue(target.paraName, paraCtBindKey,data);
				dialog.dialog('close');
			},{
				selectNum:target.multi?-1:1
			});
		} else if (selectorType == "selector") {
			var selector = eval(alias);
			selector({
				selectNum : target.multi?-1:1,
				callBack : function(data, dialog) {
					if (!data)  return ;
					setValue(target.paraName, paraCtBindKey,data);
				}
			});
		}
	}

	function setValue(paraName,key,aryData) {
		var scope = $("body").scope();
		var params = scope.prop.params;
		for(var i=0;i<params.length;i++){
			var param=params[i];
			if (param.paraName != paraName ) continue;
			
			setParam(scope,param,key,aryData)
		}
	}
	
	function setParam(scope,param,key,aryData){
		var isString=param.paraType == "java.lang.String" ;
		var vals="";
		var descriptions="";
		for(var i=0;i<aryData.length;i++){
			var obj=aryData[i];
			val=obj[key];
			tmp=obj["name"]?obj["name"]:obj[key] ;
			vals+=(i==0)?val:"," +val;
			descriptions+=(i==0)?tmp:"," +tmp  ;
		}
		scope.$apply(function() {
			param.value =isString ? "\""+ vals +"\"" :vals;
			param.description= descriptions;
		});
	}
	
	
</script>