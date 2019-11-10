<%@page import="com.hotent.base.core.web.RequestUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/list.jsp"%>
<%
	String id = RequestUtil.getString(request, "id");
	String taskKey = RequestUtil.getString(request,"taskKey");
	String url = RequestUtil.getPrePage(request);
%>
<script type="text/javascript">
	$(function() {
		var tabs = $("div.tabs-panels");
		tabs.height(tabs.height() - 63);
	});
</script>
</head>
<body class="easyui-layout">
	<!-- 顶部按钮 -->
	<div class="toolbar-panel col-md-13 ">
		<div class="buttons">
			<button onclick="HT.window.closeEdit();top.layer.close(top.layer.getFrameIndex(window.name));" class="btn btn-primary btn-sm fa-close">
				<span>关闭</span>
			</button>
		</div>
	</div>
	<div id="bpmProcessInstanceTab" class="easyui-tabs" data-options="fit:true,border:false" style="padding-left: 5px;">
		<div title="流程信息 " style="padding: 10px;" data-options="iconCls:'bigger-120 fa fa-cog'" iframe="true" href="${ctx}/flow/instance/instanceInfo?id=<%=id%>"></div>
		<div title="流程图" style="padding: 10px;" data-options="iconCls:'bigger-120 fa fa-picture-o'" iframe="true" href="${ctx}/flow/instance/instanceFlowImage?id=<%=id%>" ></div>
		<div title="审批历史" style="padding: 10px;" data-options="iconCls:'bigger-120 fa fa-bars'" iframe="true" href="${ctx}/flow/instance/instanceFlowOpinions?instId=<%=id%>">${ctx}/flow/instance/instanceFlowImage?id=<%=id%></div>
		<div title="流程表单" style="padding: 0 5px 0 0;" data-options="iconCls:'bigger-120 fa fa-newspaper-o'" iframe="true" href="${ctx}/flow/instance/instanceFlowForm?id=<%=id%>&taskKey=<%=taskKey%>">
		</div>
	</div>
</body>
</html>