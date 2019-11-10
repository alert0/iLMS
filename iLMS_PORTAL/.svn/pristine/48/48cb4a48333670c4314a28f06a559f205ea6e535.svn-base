<%@page import="com.hotent.base.core.web.RequestUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<% String taskTurnId = RequestUtil.getString(request, "taskTurnId"); %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
	</head>
	<body  class="easyui-layout">
		
		<div data-options="region:'center',border:false" style="text-align:center;" >
				<div id="grid" class="my-easyui-datagrid"></div>
				 
		</div>
	</body>
</html>
<script>
$(function() {
 
	loadGrid();

});
function loadGrid() {
	$('#grid').datagrid($.extend({
		url : __ctx + "/office/receivedProcess/taskTurnAssigns?taskTurnId=<%=taskTurnId%>",
		sortName : 'create_time_',
		sortOrder : 'desc',
		 frozenColumns: [[
		         {field : 'fromUser',sortName : "fromUser",title : '转办人',width : 80,align : 'center'}, 
		       	 {field : 'receiver',sortName : "receiver",title : '接收人',width : 80,align : 'center'},
		         {field : 'createTime',sortName : "createTime",title : '转办时间',width : 200,align : 'center',formatter:dateTimeFormatter},
		 		   {field : 'comment',sortName : "comment",title : '转办原因',width : 200,align : 'center'}
		         ]],
		columns : [ [
		
		 
            ] ]
	}, $defaultOptions));
}
</script>

