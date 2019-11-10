<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<%@include file="/commons/include/zTree.jsp"%>
		<script type="text/javascript" src="${ctx}/js/common/customform/directiveTpl.js"></script>
		<script type="text/javascript" src="${ctx}/js/business/meeting/meetingroom/meetingroomEditController.js"></script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary fa-save" ng-model="data" ng-click="save()"><span>保存</span></a>
					<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit(true,'grid');"><span>返回</span></a>
				</div>
			</div>
			<form name="form" method="post" ht-load="getJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th>会议室名称:<span class="required">*</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.name"   ht-validate="{required:true,maxlength:192,isexist:'isExist?id=${param.id}'}"  />
								</td>								
							</tr>
							<tr>								
								<th>是否需要审批:</th>
								<td>
								<label>
								  <input type="radio"  ng-model="data.needPending"  value="1" lablename="单选第二变量" validate="{}" ng-checked="data.needPending!='0' "/>
								 是
								</label
								<label>
								  <input type="radio"   ng-model="data.needPending"  value="0" lablename="单选第二变量" validate="{}" ng-checked="data.needPending=='0' " />
								否
								</label>
								</td>								
							</tr>
							<tr ng-if="data.needPending!='0'">								
								<th>审批人姓名:</th>
								<td>
								 <input class="inputText" type="text" ng-model="data.pendingUserName" readonly="readonly"  ht-validate="{required:true,maxlength:192}"  />
									<input class="inputText" type="hidden" ng-model="data.pendingUserId"   ht-validate="{required:true,maxlength:192}"  />
									<a class="btn btn-primary btn-mini"  ng-click="userDialog()">
										<span>选择</span>
									</a>
								</td>
							</tr>
							<tr>								
								<th>支持的服务:</th>
								<td>
									<input class="inputText" ht-diccheckbox="data.supportService" dickey="hyszcfw" type="hidden" ng-model="data.supportService"   ht-validate="{required:false,maxlength:600}"  />
								</td>								
							</tr>
							<tr>								
								<th>会议室地址:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.location"   ht-validate="{required:true,maxlength:900}"  />
								</td>								
							</tr>
							<tr>								
								<th>会议室容量:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.capacity"   ht-validate="{required:false,number:true,maxIntLen:10}"  />
								</td>								
							</tr>
							<tr>								
								<th>会议室备注:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.memo"   ht-validate="{required:false}"  />
								</td>								
							</tr>
				</table>
				
				
			</form>
		
	</body>
</html>