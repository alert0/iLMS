<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/edit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/lib/javacode/codemirror.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/javacode/InitMirror.js"></script>
	</head>
	<body class="easyui-layout">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel">
			<div class="buttons">
				<button onclick="HT.window.closeEdit(true,'boDefGridList');" class="btn btn-primary btn-sm fa-close" ><span>关闭</span></button>
			</div>
		</div>
		<table cellspacing="0"  class="table-form">
					<tr >								
						<th><span>别名:</span></th>
						<td >${bpmFormTemplate.alias}</td>								
					</tr>
					<tr >								
						<th><span>模板名称:</span></th>
						<td>${bpmFormTemplate.templateName}</td>								
					</tr>
					<tr >								
						<th><span>模板类型:</span></th>
						<td>${bpmFormTemplate.templateType}</td>								
					</tr>
					<tr >								
						<th><span>模板内容:</span></th>
						<td><textarea id="html" name="html" codemirror="true" mirrorheight="300px" cols=100 rows=20 readonly>${fn:escapeXml(bpmFormTemplate.html)}</textarea></td>								
					</tr>
					<tr >								
						<th><span>模板描述:</span></th>
						<td>${bpmFormTemplate.templateDesc}</td>								
					</tr>
		</table>
	</body>
</html>