<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/org/userUnder/userUnderEditController.js"></script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary fa-save" ng-model="data" ht-save="save"><span>保存</span></a>
					<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit(true,'grid');"><span>返回</span></a>
				</div>
			</div>
			<form name="form" method="post" ht-load="getJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th>用户id:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.userId"   ht-validate="{required:false,maxlength:192}"  />
								</td>								
							</tr>
							<tr>								
								<th>下属用户id:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.underUserId"   ht-validate="{required:false,maxlength:192}"  />
								</td>								
							</tr>
							<tr>								
								<th>下属用户名:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.underUserName"   ht-validate="{required:false,maxlength:300}"  />
								</td>								
							</tr>
				</table>
				
				
			</form>
		
	</body>
</html>