<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/bpm/bpmReminderHistory/bpmReminderHistoryEditController.js"></script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary fa-save" ng-model="data" ht-save="save"><span>保存</span></a>
					<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
				</div>
			</div>
			<form name="form" method="post" ht-load="getJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th><span>流程实例ID:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.instId"   ht-validate="{required:false,maxlength:765}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>流程实例标题:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.isntName"   ht-validate="{required:false,maxlength:765}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>节点名称:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.nodeName"   ht-validate="{required:false,maxlength:765}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>节点ID:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.nodeId"   ht-validate="{required:false,maxlength:765}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>执行时间:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.executeDate" ht-date  ht-validate="{required:false}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>执行类型:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.remindType"   ht-validate="{required:false,maxlength:765}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>user_id_:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.userId"   ht-validate="{required:false,maxlength:765}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>说明:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.note"   ht-validate="{required:false}"  />
								</td>								
							</tr>
				</table>
				
				
			</form>
		
	</body>
</html>