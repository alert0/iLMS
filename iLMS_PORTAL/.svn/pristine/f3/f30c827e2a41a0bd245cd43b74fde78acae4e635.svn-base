<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/base/base/resRole/resRoleEditController.js"></script>
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
								<th>系统ID:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.systemId"   ht-validate="{required:false,maxlength:150}"  />
								</td>								
							</tr>
							<tr>								
								<th>资源ID:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.resId"   ht-validate="{required:false,maxlength:150}"  />
								</td>								
							</tr>
							<tr>								
								<th>角色ID:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.roleId"   ht-validate="{required:false,maxlength:150}"  />
								</td>								
							</tr>
				</table>
				
				
			</form>
		
	</body>
</html>