<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<style>
th{
	width:15%;
}
td{
	width:35%;
}
</style>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/mail/mail/mailSetting/mailSettingGetController.js"></script>
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
						<th>用户名称:</th>
						<td>
							{{data.nickName}}
						</td>
						<th>邮件类型:</th>
						<td>
							{{data.mailType}}
						</td>										
					</tr>
					<tr>								
						<th>邮箱地址:</th>
						<td>
							{{data.mailAddress}}
						</td>	
						<th>是否默认</th>
						<td>
							<input type="checkbox" disabled="disabled" ng-model="data.isDefault" />
						</td>								
					</tr>
					<tr>								
						<th>smtp主机:</th>
						<td>
							{{data.smtpHost}}
						</td>	
						<th>smtp端口:</th>
						<td>
							{{data.smtpPort}}
						</td>									
					</tr>
					<tr ng-if="data.mailType!='imap'">								
						<th>pop主机:</th>
						<td>
							{{data.popHost}}
						</td>	
						<th>pop端口:</th>
						<td>
							{{data.popPort}}
						</td>								
					</tr>
					<tr ng-if="data.mailType=='imap'">								
						<th>imap主机:</th>
						<td>
							{{data.imapHost}}
						</td>	
						<th>imap端口:</th>
						<td>
							{{data.imapPort}}
						</td>								
					</tr>
					<tr>								
						<th>使用SSL认证:</th>
						<td>
							<input type="checkbox" disabled="disabled" ng-model="data.SSL" />
						</td>
						<th>需要身份验证:</th>
						<td>
							<input type="checkbox" disabled="disabled" ng-model="data.validate" />
						</td>
					</tr>
					<tr>								
						<th>删除远程邮件:</th>
						<td>
							<input type="checkbox" disabled="disabled" ng-model="data.isDeleteRemote" />
						</td>	
						<th>下载附件:</th>
						<td>
							<input type="checkbox" disabled="disabled" ng-model="data.isHandleAttach" />
						</td>								
					</tr>
		</table>
		
		
		</form>
	</body>
</html>