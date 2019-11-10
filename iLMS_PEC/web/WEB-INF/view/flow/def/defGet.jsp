<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/edit.jsp"%>
<script type="text/javascript">
	$(function() {
		setContainerWidth();
	});

	function setContainerWidth() {
		var w = $(".scoll-panel").width();
		$("#divContainer").width(w - 20);
	}
</script>
<style type="text/css">
	body {font-size:12px;}
	.nav .active a{ padding:0 !important;}
	
</style>
</head>
<body class="panel-noscroll" style="border:1px solid #959DA4">
<div class="toolbar-panel" ng-show="isStepCreate">
	<div class="buttons">
		<a class="btn btn-primary btn-sm fa-back" href="javascript:window.close()">
			<span>返回</span>
		</a>
	</div>
</div>
	<table class="table-form" cellspacing="0">
		<tr>
			<th><span>流程名称:</span></th>
			<td>${bpmDefinition.name}</td>
			<th><span>流程业务主键:</span></th>
			<td>${bpmDefinition.defKey}</td>
		</tr>
		<tr>
			<th><span>流程描述:</span></th>
			<td>${bpmDefinition.desc}</td>
			<th><span>创建时间:</span></th>
			<td ><fmt:formatDate value="${bpmDefinition.createTime}"
					pattern="yyyy-MM-dd HH:mm:ss" />
					
					</td>
		</tr>
		<tr>
		<th><span>流程状态:</span></th>
			<td>
			
				<c:choose>
					<c:when test="${bpmDefinition.status=='draft'}">
						草稿
					</c:when>
					<c:when test="${bpmDefinition.status=='deploy'}">
						发布
					</c:when>
					<c:when test="${bpmDefinition.status=='forbidden'}">
						禁用
					</c:when>
					<c:when test="${bpmDefinition.status=='forbidden_instance'}">
						禁用实例
					</c:when>
				</c:choose>
			</td>
		<th>更新时间：</th>
		<td><fmt:formatDate value="${bpmDefinition.updateTime}"
					pattern="yyyy-MM-dd HH:mm:ss" />
		</td>
		</tr>
		<tr>
			<th><span>BPMN流程定义ID:</span></th>
			<td>${bpmDefinition.bpmnDefId}</td>
			
			<th><span>BPMN流程发布ID:</span></th>
			<td>${bpmDefinition.bpmnDeployId}</td>
			
		</tr>
		<tr>
			<th><span>版本 - 是否主版本:</span></th>
			<td>
				<c:choose>
					<c:when test="${bpmDefinition.isMain=='Y'}">
						是
					</c:when>
					<c:otherwise>
						否
					</c:otherwise>
				</c:choose>
			</td>
			<th><span>测试状态:</span></th>
			<td>
				<c:choose>
					<c:when test="${bpmDefinition.testStatus=='run'}">
						正式
					</c:when>
					<c:otherwise>
						测试
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<th><span>版本 - 当前版本号:</span></th>
			<td>${bpmDefinition.version}</td>
			<th><span>版本 - 主版本流程ID:</span></th>
			<td>${bpmDefinition.mainDefId}</td>
		</tr>
		<tr>
			<th><span>版本 - 变更理由:</span></th>
			<td>${bpmDefinition.reason}</td>
			<th><span>流程定义ID:</span></th>
			<td>${bpmDefinition.defId}</td>
		</tr>
	</table>
</body>
</html>