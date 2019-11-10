<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<%@include file="/commons/include/zTree.jsp"%>
		<script type="text/javascript" src="${ctx}/js/common/customform/directiveTpl.js"></script>
		<script type="text/javascript" src="${ctx}/js/business/meeting/meetingroom/meetingroomGetController.js"></script>
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
						<th>会议室名称:</th>
						<td> {{data.name }}</td>
					</tr>
					<tr>								
						<th>是否需要审批:</th>
						<td ng-if="data.needPending ==0">否</td>
						<td ng-if="data.needPending !=0">是</td>
					</tr>
			<!-- 		<tr>								
						<th>审批人id:</th>
						<td> {{data.pendingUserId }}</td>
					</tr> -->
					<tr>								
						<th>审批人姓名:</th>
						<td> {{data.pendingUserName }}</td>
					</tr>
					<tr>								
						<th>支持的服务:</th>
						<td> 
						
						<input class="inputText" isonlyshow='true' ht-diccheckbox="data.supportService" dickey="hyszcfw" type="hidden" ng-model="data.supportService"   ht-validate="{required:false,maxlength:600}"  />
						</td>
					</tr>
					<tr>								
						<th>会议室地址:</th>
						<td> {{data.location }}</td>
					</tr>
					<tr>								
						<th>会议室容量:</th>
						<td> {{data.capacity }}</td>
					</tr>
					<tr>								
						<th>会议室备注:</th>
						<td> {{data.memo }}</td>
					</tr>
		</table>
		
		
		</form>
	</body>
</html>