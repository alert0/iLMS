<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
	</head>
	<body class="easyui-layout">
		<c:choose>
			<c:when test="${sysType == null}">
				<div class="alert alert-warning"style="display: block;">
						请在左边树的节点右键进行操作
				</div>
			</c:when>
			<c:otherwise>
				<div class="scoll-panel">
					<div class="gray-div">
						<div id="tb" style="height:auto;">
						    <div style="margin-bottom:5px" class="datagrid-toolbar">
						        <a href="${returnUrl}" class="easyui-linkbutton" iconCls="icon-return" plain="true">返回</a>
						    </div>
						</div>
						<table class="table-form" cellspacing="0">
							<tr>								
								<th><span>所属分类组业务主键:</span></th>
								<td>${sysType.typeGroupKey}</td>								
							</tr>
							<tr>								
								<th><span>分类名称:</span></th>
								<td>${sysType.name}</td>								
							</tr>
							<tr>								
								<th><span>节点的分类Key:</span></th>
								<td>${sysType.typeKey}</td>								
							</tr>
							<tr>								
								<th><span>flat 平行；tree 树形:</span></th>
								<td>${sysType.struType}</td>								
							</tr>
							<tr>								
								<th><span>父节点:</span></th>
								<td>${sysType.parentId}</td>								
							</tr>
							<tr>								
								<th><span>层次:</span></th>
								<td>${sysType.depth}</td>								
							</tr>
							<tr>								
								<th><span>路径:</span></th>
								<td>${sysType.path}</td>								
							</tr>
							<tr>								
								<th><span>是否叶子节点。Y=是；N=否:</span></th>
								<td>${sysType.isLeaf}</td>								
							</tr>
							<tr>								
								<th><span>所属人ID:</span></th>
								<td>${sysType.ownerId}</td>								
							</tr>
							<tr>								
								<th><span>序号:</span></th>
								<td>${sysType.sn}</td>								
							</tr>
							<tr>								
								<th><span>创建人ID:</span></th>
								<td>${sysType.createBy}</td>								
							</tr>
							<tr>								
								<th><span>创建时间:</span></th>
								<td>${sysType.createTime}</td>								
							</tr>
							<tr>								
								<th><span>创建者所属组织ID:</span></th>
								<td>${sysType.createOrgId}</td>								
							</tr>
							<tr>								
								<th><span>更新人ID:</span></th>
								<td>${sysType.updateBy}</td>								
							</tr>
							<tr>								
								<th><span>更新时间:</span></th>
								<td>${sysType.updateTime}</td>								
							</tr>
						</table>
					</div>
				</div>
		</c:otherwise>
	</c:choose>
	</body>
</html>