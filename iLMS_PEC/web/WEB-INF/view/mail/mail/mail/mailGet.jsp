<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/mail/mail/mail/mailGetController.js"></script>
	</head>
	<body class="easyui-layout" ng-controller="ctrl">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel">
			<div class="buttons">
				<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
			</div>
		</div>
		<form name="form" ht-load="getJson?id=${param.id}" ng-model="data">
			<table class="table-form"   cellspacing="0">
					<tr>								
						<th>主题:</th>
						<td> {{data.subject }}</td>
					</tr>
					<tr>								
						<th>发件人:</th>
						<td> {{outMail.senderName}}({{data.senderAddress }})</td>
					</tr>
					<tr>								
						<th>收件人地址:</th>
						<td>{{data.receiverName }}({{data.receiverAddresses }})</td>
					</tr>
					<tr>								
						<th>日期:</th>
						<td>{{data.sendDate | date :'yyyy-MM-dd HH:mm:ss'}}</td>
					</tr>
					
			</table>
			<div style="border:solid 1px #7babcf;margin:2px;padding:4px;height: 430px;overflow: auto;">
				<h2 ng-if="data.mailAttachments">附件（{{data.mailAttachments.length}}个）</h2>
				<div  ng-repeat="file in data.mailAttachments track by $index" style="font-size:15px;">
					<font color="green">{{file.fileName}}</font>
					<a href="downLoadAttach?fileId={{file.id}}" id='downLoadAttach'>下载此附件</a>
	   			</div>
				<hr>
				<br>
				<div id="content" style="width: 100%;padding: 5px;margin-bottom: 15px;">
					
				</div>
				<!-- <p>{{data.content}}</p> -->
			</div>
		</form>
	</body>
</html>