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
									<td class="label"><span>别名:</span></td>
									<td>${sysServiceSet.alias}</td>								
								</tr>
								<tr>								
									<td class="label"><span>wsdl地址:</span></td>
									<td>${sysServiceSet.url}</td>								
								</tr>
								<tr>								
									<td class="label"><span>接口调用地址:</span></td>
									<td>${sysServiceSet.address}</td>								
								</tr>
								<tr>								
									<td class="label"><span>名称空间:</span></td>
									<td>${sysServiceSet.namespace}</td>								
								</tr>
								<tr>								
									<td class="label"><span>构建soap的模式:</span></td>
									<td>${sysServiceSet.soapAction}</td>								
								</tr>
								<tr>								
									<td class="label"><span>输入参数设定:</span></td>
									<td>${sysServiceSet.inputSet}</td>								
								</tr>
					</table>
				</div>
			</div>
		</div>
	</body>
</html>