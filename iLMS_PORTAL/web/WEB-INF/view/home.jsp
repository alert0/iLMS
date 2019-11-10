<%@page import="com.hotent.sys.util.ContextUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<!DOCTYPE html>
<html ng-app="app">
<head>
	<title>首页</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8; IE=9; IE=EDGE;">
	
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<f:link href="bootstrap.min.css"></f:link>
	<link rel="stylesheet" href="${ctx}/js/lib/easyui/themes/bootstrap/easyui.css">
	<f:link href="font-awesome.min.css"></f:link>
	<f:link href="base.css"></f:link>
	<f:link href="index.css"></f:link>
	<f:link href="icons.css"></f:link>
	<script type="text/javascript">
		var __ctx='${ctx}';
		var currentUserId = '<%=ContextUtil.getCurrentUserId()%>';
		var noRightsMsg = "${noRightsMsg}";
	</script>
	<script type="text/javascript" src="${ctx}/js/lib/angular/angular.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/common/util/util.js"></script>
	<script type="text/javascript" src="${ctx}/js/lib/easyui/lang/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/js/common/service/BaseService.js"></script>
	<script type="text/javascript" src='${ctx}/js/platform/system/layout/index.js'></script>
	
	<script id="recursion" type="text/ng-template">
    	<li ng-repeat="item in memus" >
        	<a ng-click="menuClick(item)" href="javascript:void(0)">
				<i class="sidebar-nav-item-icon fa fa-angle-down" ng-if="item.children.length && item.opened" ></i>
				<i class="sidebar-nav-item-icon fa fa-angle-right" ng-if="item.children.length && !item.opened" ></i>
				<span class="sidebar-nav-item fa {{item.icon}} "><i>{{item.name}}</i></span>
			</a>
        	<ul ng-if="item.children.length" class="{{item.opened==1?'active':'collapse'}}" 
				ng-include="'recursion'"  ng-init="memus=item.children"></ul>
    	</li>
	</script>
	
</head>
<body ng-controller="indexCtrl" class="easyui-layout" fit="true" scroll="no" >
	<div class="layout-north " region="north">
		<%@include file="/commons/include/header.jsp"%>
	</div>

	<div class="layout-west" region="west" style="overflow:auto;" title="　">
		<div class="sidebar-nav" id="sidebar_menu" >
			<ul id="menu" ng-include="'recursion'"></ul>
 		</div>
	</div>
	<div class="layout-center" id="mainPanel" region="center">
	    <div id="tabs" class="easyui-tabs" fit="true" border="false">
		 <c:if test="${not empty noRightsMsg}">
		 	<div title="未授权任何系统">
            	<div class="alert alert-danger" role="alert">${noRightsMsg }</div>
            </div>
          </c:if>
		</div>
	</div>
	<!--
	<div class="layout-south" region="south">
		<div class="footer">&copy;版权所有 广州宏天软件 2008-2015 粤ICP备09066681号</div>
	</div>
	-->
	<div class="hidden">
		<!--窗口-->
		<div id="win" title="win" style="width: 600px; height: 400px" data-options="modal:true"></div>
		<!--tabs右键菜单-->
		<div id="mm" class="easyui-menu" style="width: 150px; display: none;">
			<div id="tab-refresh" iconCls="fa fa-refresh">刷新</div>
			<div class="menu-sep"></div>
			<div id="tab-close-right" iconCls="fa fa-angle-double-right">关闭右侧标签页</div>
			<div id="tab-close-left" iconCls="fa fa-angle-double-left">关闭左侧标签页</div>
			<div class="menu-sep"></div>
			<div id="tab-close-other" iconCls="fa fa-times-circle-o">关闭其它标签页</div>
			<div id="tab-close-all" iconCls="fa fa-times-circle">关闭全部标签页</div>
		</div>
		<!--tabs右键菜单end-->
	</div>
</body>
</html>
