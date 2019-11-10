<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/system/query/queryViewShowExport.js"></script>
<script type="text/javascript">
	
</script>
</head>
<body ng-controller="ctrl" ng-init="init()">
	<!-- 顶部按钮 -->
	<div class="toolbar-panel">
		<div class="buttons">
			<a class="btn btn-primary fa-sign-out" ng-click="exportData()">
				<span>导出</span>
			</a>
			<a class="btn btn-primary fa-back" onClick="javascript:window.selfDialog.dialog('close')">
				<span>返回</span>
			</a>
		</div>
	</div>
	<form name="form" method="post" ht-load="queryView/getJson?alias=${param.alias}&sqlAlias=${param.sqlAlias}" ng-model="queryView">
		<table class="table-form" cellspacing="0">
			<tr>
				<th>
					<span>导出类型</span>
				</th>
				<td>
					<select class="inputText" ng-model="param.getType">
						<option value="page">当前页数据</option>
						<option value="all">全部数据</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span>导出排序</span>
				</th>
				<td>
					<table class="table table-bordered table-hover" cellspacing="0">
						<tr>
							<th>序号</th>
							<th>字段名称</th>
							<th>字段描述</th>
							<th>
								是否导出
								<input type="checkbox" ng-model="selAllExp" />
							</th>
							<th>排序</th>
						</tr>
						<tr ng-repeat="item in queryView.shows |filter: {hidden:'0'}">
							<td>{{$index+1}}</td>
							<td>{{item.fieldName}}</td>
							<td>{{item.fieldDesc}}</td>
							<td>
								<input type="checkbox" ht-checkbox ng-model="param.expField" value="{{item.fieldName}}" ht-checked="selAllExp"/>
							</td>
							<td>
								<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.up($index,queryView.shows)" class="btn btn-sm btn-default fa-chevron-up"></a>
								<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.down($index,queryView.shows)" class="btn btn-sm btn-default fa-chevron-down"></a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>