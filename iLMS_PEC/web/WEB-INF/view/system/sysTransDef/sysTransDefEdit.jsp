<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/ngEdit.jsp"%>
	<title>编辑 对象权限表</title>
	<script type="text/javascript" src="${ctx}/js/platform/system/sysTransDef/sysTransDefService.js"></script>
	<script type="text/javascript" src="${ctx}/js/platform/system/sysTransDef/sysTransDefEditController.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/javacode/codemirror.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/javacode/InitMirror.js"></script>
	<script type="text/javascript">
		var id="${param.id}";
	</script>
</head>
<body  ng-app="app" ng-controller="EditController">
<form name="myForm">
	
		<div class="toolbar-panel">
			<div class="buttons">
				<a class="btn btn-sm btn-primary fa-save" ng-click="save()"><span></span>保存</a>
				<a class="btn btn-sm btn-primary fa-close" onclick="HT.window.closeEdit(true)"<span></span>关闭</a>
			</div>
		</div>
	
		<table class="table-form" cellpadding="0" >
			<tr>
				<th width="20%">名称: </th>
				<td><input ng-model="prop.name" type="text" class="inputText" ht-validate="{required:true,maxlength:64}"/></td>
			</tr>
			<tr>
				<th width="20%">是否启用: </th>
				<td>
					<select class="inputText" ng-model="prop.state" style="width: 70px">
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</td>
			</tr>
			<tr>
				<th width="20%">常用变量: </th>
				<td>
					<input ng-repeat="field in commonField" style="margin-left:5px;"type="button" value="{{field.key}}" ng-click="clickField(field)"/>
				</td>
			</tr>
			<tr>
				<th width="20%">SELECTSQL语句: </th>
				<td>
					<textarea id="selectSql" codemirror="true" rows="12" ht-validate="{required:true}"></textarea>
				</td>
			</tr>
			<tr>
				<th width="20%">UPDATESQL语句: </th>
				<td>
					<textarea id="updateSql" codemirror="true" rows="12" ht-validate="{required:true}"></textarea>
				</td>
			</tr>
			<tr>
				<th width="20%">日志内容模板: </th>
				<td>
					<textarea id="logContent" codemirror="true" rows="12" ht-validate="{required:true}"></textarea>
				</td>
			</tr>
		</table>
	
</form>
</body>
</html>
