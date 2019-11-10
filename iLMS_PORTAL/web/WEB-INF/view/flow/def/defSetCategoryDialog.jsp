<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
	</head>
	<body  class="easyui-layout">
		
		<div data-options="region:'center',border:false"  >
			<form id="frmDel">
				<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<th style="width:120px; font-weight:normal;">设置分类:</th>
						<td>
							 <select name="groupTypeId" class="easyui-combotree"  style="width:200px;" 
					data-options="url:'${ctx}/system/sysType/getByGroupKey?groupKey=FLOW_TYPE',method:'get',required:true,cascadeCheck:false,panelHeight:100"></select>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>