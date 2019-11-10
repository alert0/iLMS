<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/edit.jsp" %>
	</head>
	<body class="easyui-layout">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel col-md-13 ">
			<div class="buttons">
				<button onclick="HT.window.closeEdit(true,'grid');" class="btn btn-primary btn-sm fa-back" ><span>返回</span></button>
			</div>
		</div>
		<table class="table-form">
			<tr id="trAppType">
	    			<th>常用语类型:</th>
	    			<td>
	    				<c:if test="${bpmApprovalItem.type == 1 }">
							系统全局
						</c:if>
						<c:if test="${bpmApprovalItem.type==4 }">
							个人常用语
						</c:if>
	    				<c:if test="${bpmApprovalItem.type==3}">
							流程定义
						</c:if>
						<c:if test="${bpmApprovalItem.type== 2 }">
							流程分类
						</c:if>
	    			</td>
	    		</tr>
			<tr>								
				<th>常用语:</th>
				<td>${bpmApprovalItem.expression}</td>
			</tr>
			<%-- <c:if test="${param.type=='flow' }">
				<tr>								
					<th><span>作用范围:</span></th>
					<td>${bpmApprovalItem.defKey}</td>
				</tr>
				<tr>								
					<th><span>作用对象:</span></th>
					<td>${bpmApprovalItem.typeId}</td>
				</tr>
			</c:if> --%>
		</table>
	</body>
</html>