<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/lib/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/lib/ueditor/ueditor.all.min.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel col-md-13 ">
			<div class="buttons">
				<a class="btn btn-primary fa-back" href="${returnUrl}"><span>返回</span></a>
			</div>
		</div>
		<div style="height:90%">
			<div id="sysMessageTab" class="easyui-tabs" data-options="fit:true,border:false" style="padding-left:5px;">
			    <div title="信息详情"  iframe="true"  href="${ctx}/platform/innermsg/messageRead/get.ht?id=${id}"></div>
			<c:if test="${isPublic}!=1&&${canReply==1}">
			    <div title="已回复信息人员列表" iframe="true" href="${ctx}/platform/innermsg/sysMessage/replyDetail.ht?messageId=${id}"></div>
			</c:if>
			</div>
		</div>
	</div>
	

</body>
</html>