<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/mail/mail/mailLinkman/mailLinkmanGetController.js"></script>
	</head>
	<body class="easyui-layout" ng-controller="ctrl">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel">
			<div class="buttons">
				<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
			</div>
		</div>
		<form name="form" ht-load="getJson?id=${param.id}"   ng-model="data">
		<table class="table-form"   cellspacing="0">
					<tr>								
						<th>用户ID:</th>
						<td> {{data.USERID }}</td>
					</tr>
					<tr>								
						<th>邮件ID:</th>
						<td> {{data.MAILID }}</td>
					</tr>
					<tr>								
						<th>送送时间:</th>
						<td> {{data.SENDTIME | date :'yyyy-MM-dd'}}</td>
					</tr>
					<tr>								
						<th>联系人名称:</th>
						<td> {{data.LINKNAME }}</td>
					</tr>
					<tr>								
						<th>联系人地址:</th>
						<td> {{data.LINKADDRESS }}</td>
					</tr>
					<tr>								
						<th>发送次数:</th>
						<td> {{data.SENDTIMES }}</td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>