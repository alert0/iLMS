<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/edit.jsp"%>
	</head>
	<body class="easyui-layout">
		<div class="toolbar-panel col-md-13 ">
			<div class="buttons" style="margin-left:5px;">
				<a  onclick="HT.window.closeEdit(true,'grid')"; href="javascript:;" class="btn btn-primary btn-sm fa-back" ><span>返回</span></a>
			</div>
		</div>
		
		<table class="table-form" cellspacing="0">
					<tr>								
						<th><span>标题:</span></th>
						<td>${bpmAgentSetting.subject}</th>								
					</tr>
					<tr>								
						<th><span>授权人ID:</span></th>
						<td>${bpmAgentSetting.authId}</td>								
					</tr>
					<tr>								
						<th><span>授权人姓名:</span></th>
						<td>${bpmAgentSetting.authName}</td>								
					</tr>
					<tr>								
						<th><span>开始生效时间:</span></th>
						<td><fmt:formatDate value="${bpmAgentSetting.startDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>								
					</tr>
					<tr>								
						<th><span>结束日期:</span></th>
						<td><fmt:formatDate value="${bpmAgentSetting.endDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>								
					</tr>
					<tr>								
						<th><span>是否有效:</span></th>
						<td>${bpmAgentSetting.isEnabled}</td>								
					</tr>
					<tr>								
						<th><span>代理人ID:</span></th>
						<td>${bpmAgentSetting.agentId}</td>								
					</tr>
					<tr>								
						<th><span>代理人:</span></th>
						<td>${bpmAgentSetting.agent}</td>								
					</tr>
					<tr>								
						<th><span>流程定义KEY:</span></th>
						<td>
							<c:if test="${bpmAgentSetting.type eq 2}">
								<c:forEach items="${bpmAgentSetting.defList}" var="defList">
									${defList.flowKey}&nbsp&nbsp
								</c:forEach>	
							</c:if>
							<c:if test="${bpmAgentSetting.type ne 2}">
								${bpmAgentSetting.flowKey}	
							</c:if>
						</td>								
					</tr>
					<tr>								
						<th><span>代理类型(1,全权代理,2,部分代理,3.条件代理):</span></th>
						<td>
							<c:if test="${bpmAgentSetting.type == 1}">全权代理</c:if>
							<c:if test="${bpmAgentSetting==null or bpmAgentSetting.type == 2}">部分代理</c:if>
							<c:if test="${bpmAgentSetting.type == 3}">条件代理</c:if>
						</td>								
					</tr>
					<tr>								
						<th><span>创建人ID:</span></th>
						<td>${bpmAgentSetting.createBy}</td>								
					</tr>
					<tr>								
						<th><span>创建时间:</span></th>
						<td><fmt:formatDate value="${bpmAgentSetting.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>								
					</tr>
					<tr>								
						<th><span>创建者所属组织ID:</span></th>
						<td>${bpmAgentSetting.createOrgId}</td>								
					</tr>
					<tr>								
						<th><span>更新人ID:</span></th>
						<td>${bpmAgentSetting.updateBy}</td>								
					</tr>
					<tr>								
						<th><span>更新时间:</span></th>
						<td><fmt:formatDate value="${bpmAgentSetting.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>								
					</tr>
		</table>
	</body>
</html>