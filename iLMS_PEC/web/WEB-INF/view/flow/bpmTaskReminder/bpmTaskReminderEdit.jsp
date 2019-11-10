<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/bpm/bpmTaskReminder/bpmTaskReminderEditController.js"></script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary btn-sm fa-save" ng-model="data" ht-save="save"><span>保存</span></a>
					<a class="btn btn-primary btn-sm fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
				</div>
			</div>
			<form name="form" method="post" ht-load="getJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th><span>催办任务ID:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.taskId"   ht-validate="{required:false,maxlength:192}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>催办名称:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.name"   ht-validate="{required:false,maxlength:192}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>相对时间:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.relDate" ht-date  ht-validate="{required:false}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>到期执行动作:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.dueAction"   ht-validate="{required:false,maxlength:120}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>调用指定方法:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.dueScript"   ht-validate="{required:false,maxlength:765}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>到期日期:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.dueDate" ht-date  ht-validate="{required:false}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>期间是否发送催办:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.isSendMsg"   ht-validate="{required:false,number:true,maxIntLen:10}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>发送催办消息开始时间:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.msgSendDate" ht-date  ht-validate="{required:false}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>发送消息间隔:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.msgInterval"   ht-validate="{required:false,number:true,maxIntLen:10}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>发送次数:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.msgCount"   ht-validate="{required:false,number:true,maxIntLen:10}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>消息类型  inner,msg,email 等:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.msgType"   ht-validate="{required:false,maxlength:120}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>富文本内容:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.htmlMsg"   ht-validate="{required:false,maxlength:765}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>普通文本内容:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.plainMsg"   ht-validate="{required:false,maxlength:765}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>预警配置（预警名称，triggerDate，change2level）:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.warningset"   ht-validate="{required:false,maxlength:765}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>触发时间(每次触发后更新触发时间):</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.triggerDate" ht-date  ht-validate="{required:false}"  />
								</td>								
							</tr>
				</table>
				
				
			</form>
		
	</body>
</html>