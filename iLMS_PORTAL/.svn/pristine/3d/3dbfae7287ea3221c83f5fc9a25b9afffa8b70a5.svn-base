<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/edit.jsp" %>
	</head>
	<body class="easyui-layout" style="overflow:auto;">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel">
			<div class="buttons">
				<a href="javascript:;" onclick="HT.window.closeEdit();"  class="btn btn-primary btn-sm fa-back" ><span>关闭</span></a>
			</div>
		</div>
		<table class="table-form"   cellspacing="0"  >
					<tr>								
						<th>流水号名称:</th>
						<td>${identity.name}</td>
					</tr>
					<tr>								
						<th>流水号别名:</th>
						<td>${identity.alias}</td>
					</tr>
					<tr>								
						<th>生成规则:</th>
						<td>${identity.regulation}</td>
					</tr>
					<tr>								
						<th>生成类型:</th>
						<td><c:if test="${identity.genType==0}">递增</c:if>
							<c:if test="${identity.genType==1}">每天生成</c:if>
						</td>
					</tr>
					<tr>								
						<th>长度:</th>
						<td>${identity.noLength}</td>
					</tr>
					<tr>								
						<th>初始值:</th>
						<td>${identity.initValue}</td>
					</tr>
					<tr>								
						<th>当前值:</th>
						<td>${identity.curValue}</td>		
					</tr>
					<tr>								
						<th>步长:</th>
						<td>${identity.step}</td>		
					</tr>
		</table>
	</body>
</html>