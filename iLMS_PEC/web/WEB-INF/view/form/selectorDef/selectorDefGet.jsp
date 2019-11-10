<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/edit.jsp" %>
	</head>
	<body>
		<div class="toolbar-panel"  >
			<div class="buttons" style=" margin-left:10px;">
				<a href="javascript:;" onclick="HT.window.closeEdit();"  class="btn btn-primary btn-sm fa-back" ><span>关闭</span></a>
			</div>
		</div>
		<table class="table-form" cellspacing="0">
			<tr>
				<th ><span>名称:</span></th>
				<td>${bpmSelectorDef.name}</td>
			</tr>
			<tr>								
				<th><span>别名:</span></th>
				<td>${bpmSelectorDef.alias}</td>
			</tr>
			<tr>								
				<th><abbr title="对应的javascript方法">对应方法:</abbr></th>
				<td>${bpmSelectorDef.method}</td>								
			</tr>
			<tr>								
				<th><abbr title="将已选数据作为参数传递时的key">参数名:</abbr></th>
				<td>${bpmSelectorDef.confKey}</td>								
			</tr>
			<tr>								
				<th><span>组合字段:</span></th>
				<td>${bpmSelectorDef.groupField}</td>								
			</tr>
			<tr>								
				<th><span>按钮定义:</span></th>
				<td>${bpmSelectorDef.buttons}</td>								
			</tr>
			<tr>								
				<th><span>系统预定义:</span></th>
				<td>
					<c:if test="${bpmSelectorDef.flag eq 0}">否</c:if>
					<c:if test="${bpmSelectorDef.flag eq 1}">是</c:if>
				</td>								
			</tr>
		</table>
	</body>
</html>