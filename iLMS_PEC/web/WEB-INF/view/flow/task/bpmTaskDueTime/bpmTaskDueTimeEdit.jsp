<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/flow/task/bpmTaskDueTime/bpmTaskDueTimeEditController.js"></script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary fa-save" ng-disable="saveBtnDisable"  ng-model="data" ht-save="save"><span>保存</span></a>
					<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
				</div>
			</div>
			<form name="form" method="post" ht-load="getByTaskId?taskId=${param.taskId}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<!--
							<tr>								
								<th>流程实例id:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.instId"   ht-validate="{required:false,maxlength:192}"  />
								</td>								
							</tr>
							<tr>								
								<th>任务id:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.taskId"   ht-validate="{required:false,maxlength:192}"  />
								</td>								
							</tr> -->
							<tr>								
								<th width="15%">时间计算方式:</th>
								<td width="35%">
									<span ng-switch="data.dateType">
										<span ng-switch-when="worktime">工作日</span>
										<span ng-switch-default>日历日</span>
									</span>
								</td>	
								
								<th width="15%">已用时间:</th>
								<td width="35%">
								<!--	<span ht-times ng-model="data.dueTime" permission="r" ></span> -->
									<div class="progress progress-striped">
	              		 			  	<div class="progress-bar" ng-class="className"  style="width:{{percent}}">
	              		 			  		<span style="color:black" >{{percent}}</span>
	              		 			  	</div>
	              		 			</div> 
								</td>							
							</tr>
							
							<tr>								
								<th>任务开始时间:</th>
								<td>
								{{ data.startTime | date :'yyyy-MM-dd HH:mm:ss'  }}
								</td>	
								
								<th>到期时间:</th>
								<td>
									{{ data.expirationDate  | date :'yyyy-MM-dd  HH:mm:ss'  }}
								</td>							
							</tr>
							<tr>
								<th>增加任务时间:</th>
								<td colspan="3" >
									<span ht-times ng-model="data.addDueTime"></span>
								</td>	
							</tr>
							<tr>
								<th>预计到期时间:</th>
								<td colspan="3" >
									{{ data.expirationDate2  | date :'yyyy-MM-dd  HH:mm:ss'  }}
								</td>	
							</tr>
							
						
							<tr>	
								<th>附件:</th>
								<td colspan="3">
									<div class="inputText" type="text" ht-upload="data.fileId" permission="w"/>
								</td>								
							</tr>
							<tr>
								<th>备注:</th>
								<td colspan="3">
									<textarea cols=60 rows=4  ng-model="data.remark"   ht-validate="{required:false}"  />
								</td>	
							</tr>
							
				</table>
				
				
			</form>
		
	</body>
</html>