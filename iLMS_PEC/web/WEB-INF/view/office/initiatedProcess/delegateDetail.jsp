<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<%@include file="/commons/include/edit.jsp"%>
<title>任务转办说明</title>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div style="overflow-y:auto; " data-options="region:'center',border:false">
			<div style="margin-top: 20px;">
				<c:forEach items="${turnAssignList }" var="turnAssign">
					转办关系：${turnAssign.fromUser }于<fmt:formatDate value="${turnAssign.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />将任务转交于${turnAssign.receiver }！<br/>
					转办理由：${turnAssign.comment }<br/><br/>
				</c:forEach>
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
</body>
</html>