<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<!DOCTYPE html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/bpm/bpmTaskReminder/bpmTaskReminderGetController.js"></script>
	</head>
	<body class="easyui-layout" ng-controller="ctrl">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel">
			<div class="buttons">
				<a class="btn btn-primary btn-sm fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
			</div>
		</div>
		<form name="form" ht-load="getJson?id=${param.id}"   ng-model="data">
		<table class="table-form"   cellspacing="0">
					<tr>								
						<th><span>催办任务ID:</span></th>
						<td> {{data.taskId }}</td>
					</tr>
					<tr>								
						<th><span>催办名称:</span></th>
						<td> {{data.name }}</td>
					</tr>
					<tr>								
						<th><span>相对时间:</span></th>
						<td> {{data.relDate | date :'yyyy-MM-dd HH:mm:ss'}}</td>
					</tr>
					<tr>								
						<th><span>到期执行动作:</span></th>
						<td> {{data.dueAction }}</td>
					</tr>
					<tr>								
						<th><span>调用指定方法:</span></th>
						<td> {{data.dueScript }}</td>
					</tr>
					<tr>								
						<th><span>到期日期:</span></th>
						<td> {{data.dueDate | date :'yyyy-MM-dd HH:mm:ss'}}</td>
					</tr>
					<tr>								
						<th><span>期间是否发送催办:</span></th>
						<td> {{data.isSendMsg }}</td>
					</tr>
					<tr>								
						<th><span>发送催办消息开始时间:</span></th>
						<td> {{data.msgSendDate | date :'yyyy-MM-dd HH:mm:ss'}}</td>
					</tr>
					<tr>								
						<th><span>发送消息间隔:</span></th>
						<td> {{data.msgInterval }}</td>
					</tr>
					<tr>								
						<th><span>发送次数:</span></th>
						<td> {{data.msgCount }}</td>
					</tr>
					<tr>								
						<th><span>消息类型  inner,msg,email 等:</span></th>
						<td> {{data.msgType }}</td>
					</tr>
					<tr>								
						<th><span>富文本内容:</span></th>
						<td> {{data.htmlMsg }}</td>
					</tr>
					<tr>								
						<th><span>普通文本内容:</span></th>
						<td> {{data.plainMsg }}</td>
					</tr>
					<tr>								
						<th><span>预警配置（预警名称，triggerDate，change2level）:</span></th>
						<td> {{data.warningset }}</td>
					</tr>
					<tr>								
						<th><span>触发时间(每次触发后更新触发时间):</span></th>
						<td> {{data.triggerDate | date :'yyyy-MM-dd HH:mm:ss'}}</td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>