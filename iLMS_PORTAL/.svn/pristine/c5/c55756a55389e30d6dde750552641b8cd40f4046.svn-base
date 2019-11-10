<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
	</head>
	<body class="easyui-layout" style="overflow:auto;">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel col-md-13 ">
			<div class="buttons">
				<a class="btn btn-primary fa-back" href="${returnUrl}" ><span>返回</span></a>
			</div>
		</div>
		<table class="table-form"   cellspacing="0"  >
					<tr>								
						<th><span>对应表单ID:</span></th>
						<td>${bpmFormHistory.formId}</td>
					</tr>
					<tr>								
						<th><span>表单名称:</span></th>
						<td>${bpmFormHistory.name}</td>
					</tr>
					<tr>								
						<th><span>表单描述:</span></th>
						<td>${bpmFormHistory.desc}</td>
					</tr>
					<tr>								
						<th><span>表单设计（HTML代码）:</span></th>
						<td>${bpmFormHistory.formHtml}</td>
					</tr>
					<tr>								
						<th><span>创建人ID:</span></th>
						<td>${bpmFormHistory.createUserId}</td>
					</tr>
					<tr>								
						<th><span>创建人Name:</span></th>
						<td>${bpmFormHistory.createUserName}</td>
					</tr>
					<tr>								
						<th><span>创建时间:</span></th>
						<td><fmt:formatDate value="${bpmFormHistory.createTime}" /></td>		
					</tr>
		</table>
	</body>
</html>