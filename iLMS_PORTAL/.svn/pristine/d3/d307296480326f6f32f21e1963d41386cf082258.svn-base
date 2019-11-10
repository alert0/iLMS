<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/portal/sysLayoutSetting/sysLayoutSettingEditController.js"></script>
<script type="text/javascript">
	var id = "${param.id}";
	$(function() {
		
	});
</script>
</head>
<body ng-controller="ctrl">
	<!-- 顶部按钮 -->
	<div class="toolbar-panel">
		<div class="buttons">
			<a class="btn btn-sm btn-primary fa-save" ng-model="data" ht-save="save"><span>保存</span></a> <a class="btn btn-sm btn-primary fa-back" onClick="HT.window.closeEdit(false);"><span>返回</span></a>
		</div>
	</div>
	<div id="tabUser" class="easyui-tabs" style="width: 100%;overflow-y:auto;">
		<div title="布局设置信息" style="padding: 10px ">
			<form name="form" method="post" ht-load="getByLayout?layoutId=${param.id}" ng-model="data">
				<table class="table-form" cellspacing="0">
					<tr>
						<th>网站LOGO(宽:296,高:40):</th>
						<td>
							<div>
								<img ng-if="data.logo == null || data.logo == ''" id="logoImg" src="${ctx}/css/image/logo.svg" style="width: 296px;height: 40px;">
								<img ng-if="data.logo != null && data.logo != ''" id="logoImg" ng-src="${ctx}{{data.logo}}" style="width: 296px;height: 40px;">
							</div>
							<div style="background: none; height: 24px; display:inline-block;">
								<a class="btn btn-sm btn-primary" href="javascript:;" ng-click="addPic();"><i class="fa fa-file-image-o"></i>上传照片</a>
								<a class="btn btn-sm btn-primary" href="javascript:;" ng-click="delPic();"><i class="fa fa-remove"></i>删除照片</a>
							</div>
						</td>
					</tr>
				</table>
			<input type="hidden" ng-model="data.logo"/>
			<input type="hidden" ng-model="data.layoutId"/>
			</form>
		</div>
		<div id="userRoleListTab" title="首页工具设置" style="padding: 0px;">
			<iframe id="userRoleList" src="${ctx}/portal/sysLayoutTools/sysLayoutTools/toolsList?layoutId=${param.id}" frameborder="no" width="100%" height="97%"></iframe>
		</div>
	</div>
</body>
</html>