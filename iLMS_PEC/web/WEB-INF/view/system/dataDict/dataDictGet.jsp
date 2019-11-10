<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
	</head>
	<body class="easyui-layout">
		<div class="scoll-panel">
			<div class="gray-div">
				<div id="tb" style="height:auto;">
				    <div style="margin-bottom:5px" class="datagrid-toolbar">
				        <a href="${returnUrl}" class="easyui-linkbutton" iconCls="icon-return" plain="true">返回</a>
				    </div>
				</div>
				<div class="form-table">
					<table cellspacing="0">
								<tr>								
									<td class="label"><span>类型ID:</span></td>
									<td>${dataDict.typeId}</td>								
								</tr>
								<tr>								
									<td class="label"><span>字典值代码,在同一个字典中值不能重复:</span></td>
									<td>${dataDict.key}</td>								
								</tr>
								<tr>								
									<td class="label"><span>字典值名称:</span></td>
									<td>${dataDict.name}</td>								
								</tr>
								<tr>								
									<td class="label"><span>父ID:</span></td>
									<td>${dataDict.parentId}</td>								
								</tr>
					</table>
				</div>
			</div>
		</div>
	</body>
</html>