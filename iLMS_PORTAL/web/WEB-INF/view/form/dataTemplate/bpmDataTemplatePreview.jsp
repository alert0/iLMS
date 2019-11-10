<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<%@include file="/commons/include/zTree.jsp"%>
		<f:link href="list.css"></f:link>
		<f:link href="rowOps.css"></f:link>
		<f:link href="ht.datagrid.css"></f:link>
		<link rel="stylesheet" href="${ctx}/js/lib/select2/css/select2.min.css"></link>
		<script type="text/javascript" src="${ctx}/js/common/form/form.js"></script>
		<script type="text/javascript" src="${ctx}/js/common/util/json2.js"></script>
		<!-- grid -->
		<script type="text/javascript" src="${ctx}/js/common/base/jquery.common.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/base/jquery.rowOps.js"></script>
		<script type="text/javascript" src="${ctx}/js/common/base/jquery.easyui.package.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/base/jquery.easyui.adapt.js"></script>
		<script type="text/javascript" src="${ctx}/js/common/customform/directiveTpl.js"></script>
		<script type="text/javascript" src="${ctx}/js/common/customform/FormDirective.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/select2/js/select2.full.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/select2/js/i18n/zh-CN.js"></script>
		
		<script type="text/javascript" src="${ctx}/js/lib/jqGrid/jquery.peity.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/jqGrid/i18n/grid.locale-cn.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/jqGrid/jquery.jqGrid.src.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/form/dataTemplate/bpmDataTemplatePreviewController.js"></script>
		<style>
			ul>li.select2-selection__choice span.select2-selection__choice__remove{
				min-width:8px;
			}
			.search-form ul>li.select2-search{
				margin-top:0px; 
			}
			span.select2-container--default{
				width:auto !important;
				min-width:160px;
				max-width: none;
			}
		</style>
	</head>
	<body ng-controller="ctrl">
		${html}
	</body>
</html>