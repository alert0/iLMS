<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/oa/demo/demoUser/demoUserEditController.js"></script>
	</head>
	<body ng-controller="ctrl">
			<form name="form" method="post" ht-load="getJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th><span>用户名:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.userName"   ht-validate="{required:false,maxlength:300}"  />
								</td>								
							</tr>
							<tr>								
								<th><span>地址:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="data.address"   ht-validate="{required:false,maxlength:300}"  />
								</td>								
							</tr>
				</table>
			</form>
		
	</body>
</html>