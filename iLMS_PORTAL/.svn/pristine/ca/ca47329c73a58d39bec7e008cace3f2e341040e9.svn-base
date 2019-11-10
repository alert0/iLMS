<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>编辑 对象权限表</title>
	<%@include file="/commons/include/ngEdit.jsp"%>
	<%@include file="/commons/include/zTree.jsp"%>
	<script type="text/javascript" src="${ctx}/js/platform/system/sysTransDef/sysTransDefService.js"></script>
	<script type="text/javascript" src="${ctx}/js/platform/system/sysTransDef/sysTransDefSetController.js"></script>
	<script type="text/javascript">
		var id="${param.id}";
		function zTreeOnClick(event,treeId,treeNode) {
			var scope=getScope();
			id=treeNode.id;
			scope.excuteSelectSql();
		};
	</script>
</head>
<body class="easyui-layout" ng-app="app" ng-controller="SetController">
		<div data-options="region:'north',split:true" style="height:48px;">
				<div class="toolbar-head">
					设置被转移人：<input ng-model="param.author.name" type="text" readonly="true" class="inputText" validate="{required:true,maxlength:64}"/>
					<a class="btn fa-user btn-sm btn-primary" href="javascript:;" ng-click="selectUser(param.author,authorChange)">选择</a>
					
						转移到：<input ng-model="param.targetPerson.name" type="text" readonly="true" class="inputText" validate="{required:true,maxlength:64}"/>
					<a class="btn fa-user btn-sm btn-primary" href="javascript:;" ng-click="selectUser(param.targetPerson)">选择</a>
				</div>
		</div>
		
		<div data-options="region:'west',title:'权限转移管理',split:true" style="width:200px;" style="overflow:auto;float:left;width:100%">
			<ul id="glTypeTree" class="ztree"></ul>
		</div>
		
		<div data-options="region:'center',title:'权限明细'" >
				<div class="toolbar-head">
					
					<a class="fa fa-play-circle btn btn-sm btn-primary" ng-click="excuteUpdateSql()"><span></span>执行转移</a>
				</div>
				
				<div class="panel-body" style="overflow-y:scroll;height: 650px;">
					<table class="table table-bordered table-hover" cellpadding="0" cellspacing="0" border="0" type="main">
						<tr>
							<th width="2%"><input type="checkbox" ng-model="param.selectAll" ng-click="clickAll()"/></th>
							<th width="20%" ng-repeat="(id,value) in param.showList[0]">{{id}} </th>
						</tr>
						<tr ng-repeat="item in param.showList">
							<td><input ng-model="param.list[$index].selected" type="checkbox"/></td>
							<td height="30px" ng-repeat="(id,value) in item" ng-click="clickItem(param.list[$parent.$index])">{{value}}</td>
						</tr>
					</table>
				</div>
				
	     </div>
</body>
</html>
