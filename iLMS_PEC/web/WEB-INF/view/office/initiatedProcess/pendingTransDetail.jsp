<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<%@include file="/commons/include/edit.jsp"%>
<title>任务流转说明</title>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false">
			<div style="margin-top: 20px;">
				${record.creator }于<fmt:formatDate value="${record.transTime }" pattern="yyyy-MM-dd HH:mm:ss" />将任务流转给您！
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
</body>
</html>