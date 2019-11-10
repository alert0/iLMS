<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<%@include file="/commons/include/codeMirror.jsp"%>
<script type="text/javascript" src="${ctx}/js/system/query/queryViewTempEditController.js"></script>
<script type="text/javascript">
	$(function(){
		$(".CodeMirror").css("height","600px");
	});
</script>
</head>
<body ng-controller="ctrl" ng-init="init()">
	<!-- 顶部按钮 -->
	<div class="toolbar-panel">
		<div class="buttons">
			<a class="btn btn-primary fa-save" ng-model="data" ht-save="queryView/save">
				<span>保存</span>
			</a>
			<a id="queryViewEditBack" class="btn btn-primary fa-back" href="queryViewEdit?id=${param.id}&sqlAlias=${param.sqlAlias}">
				<span>返回</span>
			</a>
		</div>
	</div>
	<form name="form" method="post" ht-load="queryView/getJson?id=${param.id}" ng-model="data">
		<table class="table-form" cellspacing="0">
			<th>模块</th>
			<td>
				<textarea ui-codemirror ng-model="data.template"></textarea>
			</td>
		</table>
	</form>
</body>
</html>