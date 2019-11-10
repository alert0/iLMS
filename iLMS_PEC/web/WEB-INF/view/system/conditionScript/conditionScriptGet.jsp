<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/edit.jsp"%>
</head>
<body>
	<div class="toolbar-panel">
		<div class="buttons" >
			<a href="javascript:;" onclick="HT.window.closeEdit();" class="btn btn-sm btn-primary fa-back"><span>返回</span>
			</a>
		</div>
	</div>
	<table class="table-form" cellspacing="0">
		<tr>
			<th width="200"><span>脚本所在类名:</span></th>
			<td>${conditionScript.className}</td>
		</tr>
		<tr>
			<th><span>类实例名:</span></th>
			<td>${conditionScript.classInsName}</td>
		</tr>
		<tr>
			<th><span>方法名称:</span></th>
			<td>${conditionScript.methodName}</td>
		</tr>
		<tr>
			<th><span>方法描述:</span></th>
			<td>${conditionScript.methodDesc}</td>
		</tr>
		<tr>
			<th><span>返回值类型:</span></th>
			<td>${conditionScript.returnType}</td>
		</tr>
		<tr>
			<th><span>参数信息:</span></th>
			<td>${conditionScript.argument}</td>
		</tr>
		<tr>
			<th><span>是否有效:</span></th>
			<td><c:if test="${conditionScript.enable eq 0}">否</c:if> <c:if
					test="${conditionScript.enable eq 1}">是</c:if>
			</td>
		</tr>
	</table>
</body>
</html>