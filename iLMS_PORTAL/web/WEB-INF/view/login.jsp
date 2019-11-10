<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f"   uri="http://www.jee-soft.cn/functions" %>
<%@page import="com.hotent.sys.util.SysPropertyUtil"%>
<%
  String  WechatCode=SysPropertyUtil.getByAlias("WechatCode");
%>
<!DOCTYPE html>
<html ng-app="loginApp">
<head>
<title>广汽新能源i-LMS系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8; IE=9; IE=EDGE;">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<f:link href="font-awesome.min.css"></f:link>
<link rel="icon" href="${ctx}/favicon.ico" type="image/x-icon" />
<link href="${ctx}/js/lib/buttons/buttons.min.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/login.min.css"></link>
</head>
<body ng-controller="loginCtrl">
	<div>
		<div class="login-div" ng-class="myCssVar">
			<div class="login-panel">
				<div class="column-div">
					<div class="title">&nbsp;</div>
					<div class="description">&nbsp;</div>
				</div>
				<div class="column-div">
					<span class="input input--hoshi">
						<input class="input__field input__field--hoshi" type="text" id="account" ng-model="user.account" tabindex="0">
						<label class="input__label input__label--hoshi input__label--hoshi-color-3" for="account">
							<span class="input__label-content input__label-content--hoshi">账号</span>
						</label>
					</span>
					<span class="input input--hoshi">
						<input class="input__field input__field--hoshi" type="password" id="password" ng-model="user.password" tabindex="0">
						<label class="input__label input__label--hoshi input__label--hoshi-color-3" for="password">
							<span class="input__label-content input__label-content--hoshi">密码</span>
						</label>
					</span>
					
				</div>
				
			<%-- 	<div class="column-div">
					<label><input type="checkbox" id="txtRememberMe"/><f:message code="login.remember.me"/></label>
					<a class="forgot-pwd" href="#">忘记密码?</a>
				</div> --%>
				<div class="column-div">
					<button class="submit-btn" ng-disabled="!user.account || !user.password" ng-click="login()">
						登录
					</button>
				</div>
				<br/>
				<div id="divMessage" style="color:red;"></div>
			</div>
		</div>
	</div>
	<!-- js -->
	<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/angular/angular.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/angular/animate.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery.cookie.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/buttons/buttons.min.js"></script>
	<script type="text/javascript">
		var __ctx = '${ctx}';
	</script>
	<script type="text/javascript" src="${ctx}/js/login.min.js"></script>
</body>
</html>