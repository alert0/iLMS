<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/business/xqmessagetype/messageType/messageTypeGetController.js"></script>
		<script type="text/javascript" src="${ctx}/js/business/xqmessagetype/messageType/messageTypeEditController.js"></script>

	</head>
	<style>
.buttons>a.btn.btn-sm.btn-primary.fa.fa-add:hover {
    background-color: #33b388!important;
    border: 1px solid #33b388!important;
    cursor: pointer;
}

</style>
	<body class="easyui-layout" ng-controller="ctrl" ng-init="load('${param.id}')">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel">
			<div class="buttons">
				<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
			</div>
		</div>
		
		<form  method="post">
		<table class="table-form"   cellspacing="0">
								<tr>
						<th width="20%">分类名称:<span style='color:#f00;display:inline-block'> * </span></th>
						<td>
							<input type="text" style="width:300px;background: #fff;cursor: pointer;" class="form-control input-default" ng-model="data.classificationCode" ht-validate="{'required':true}" disabled="true" />
						</td>
					</tr>
					<tr>								
						<th>发布是否审批:</th>
						<td ng-if="data.ispending ==0">否</td>
						<td ng-if="data.ispending !=0">是</td>
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
						<th width="20%">发布授权:</th>
						<td>
							
							<table id="bpmDefUserTable"  class="table-list">
									<thead>
										<tr>
											<th width="30%" >权限分类</th>
											<th width="70%" >授权给</th>
										</tr> 
									</thead>
									<tr class="empty-div" ng-if="data.ownerNameJson.length==0">
										<td colspan="2" style="text-align: center;"> 
											没有授权的人员
										</td>
									</tr>
									<tbody ng-if="data.ownerNameJson.length>0">
										
										<tr  ng-repeat="item in data.ownerNameJson track by $index">
											<td> 
												{{item.title}}
											</td>
											<td> 
												{{item.name}}
											</td>
										</tr>
									</tbody>
							</table>
						</td>
					</tr>
					
		</table>
		
		
		</form>
	</body>
</html>