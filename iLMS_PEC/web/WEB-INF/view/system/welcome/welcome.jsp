<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<link rel="stylesheet" href="${ctx }/js/lib/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" href="${ctx }/js/lib/easyui/themes/bootstrap/portal.css">
<script type="text/javascript" src="${ctx}/js/lib/easyui/plugins/jquery.portal.js"></script>
<script type="text/javascript" src="${ctx}/js/system/welcome/welcomeBulletin.js"></script>
<script type="text/javascript" src="${ctx}/js/system/welcome/welcomeBulletinController.js"></script>
<style type="text/css">
.portalul{
	padding: 5px;
}
.portalul li{
	height: 22px;
	line-height: 22px;
}
</style>
</head>
	<body ng-controller="ctrl" ng-init="init()">
		<div class="easyui-layout" fit="true" scroll="no">
			<div id="portalsDiv" region="center" border="false">
			
				<div style="width: 98%;height:335px;border: none;">
					<div id="welcomePortals1" style="width: 95%;">
						<div style="width:45%;">
							
						</div>
						<div style="width:45%;">
							
						</div>
						
					</div>
				</div>
				
				<div style="width: 98%;height:335px;border: none;">	
					<div id="welcomePortals2" style="width: 95%;">
						<div style="width:80%;">
					
						</div>
					</div>
				</div>
				<div id="pendingTool" >
					<a href="javascript:void(0)" title="刷新" class="icon-refresh" ng-click="refreshBulletins('pending',false)"></a>
					<a href="javascript:void(0)" title="更多" class="icon-viewmore" onclick="parentAddTab('pending')"></a>
				</div>
				<div id="messageTool" >
					<a href="javascript:void(0)" title="刷新" class="icon-refresh" ng-click="refreshBulletins('message',false)"></a>
					<a href="javascript:void(0)" title="更多" class="icon-viewmore" onclick="parentAddTab('message')"></a>
				</div>
				<div id="addressTool" >
					<a href="javascript:void(0)" title="刷新" class="icon-refresh" ng-click="refreshBulletins('address',false)"></a>
					<a href="javascript:void(0)" title="更多" class="icon-viewmore" onclick="parentAddTab('address')"></a>
				</div>
				<div style="clear: both;"></div>
			</div>	
		</div>
	</body>
</html>
