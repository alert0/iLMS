<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>组织架构</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div class="easyui-tabs" data-options="fit:true,border:false">
				<div title="组织简介" iframe="true" href="${ctx}/org/org/orgGet?id=${param.id}&demId=${param.demId}"></div>
				<div title="组织人员" iframe="true" href="${ctx}/org/org/orgUserList?orgId=${param.id}&demId=${param.demId}"></div>
				<div title="组织岗位" iframe="true" href="${ctx}/org/orgRel/orgRelList?orgId=${param.id}"></div>
				<div title="组织参数" iframe="true" href="${ctx}/system/sysUserParams/sysUserParamsEdit?type=2&id=${param.id}"></div>
				<div title="组织分级管理设置" iframe="true" href="${ctx}/org/orgAuth/orgAuthList?demId=${param.demId}&orgId=${param.id}"></div>
			</div>
		</div>
	</div>
</body>
</html>
