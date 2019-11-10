<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/get.jsp" %>
		<script type="text/javascript">
		$(function(){
			     $('#messageReadList').datagrid({
			        url:__ctx +'/platform/innermsg/sysMessage/getMessageRead.ht?messageId='+${param.messageId},
			      }); 
		});
		</script>
	</head>
	<body class="easyui-layout">
		<table  id="messageReadList" idField="id"  data-options="fitColumns:false,checkOnSelect:true,pagination:true,toolbar:'.toolbar-search'" fit="true" >
		    <thead>
			    <tr>
					<th data-options="width:200,align:'center'" field="receiver" sortable="false" sortName="receiver_" title="读信人" ></th>
					<th data-options="align:'center'" field="receiverTime" sortable="false" sortName="receiver_time_" title="读信时间" formatter="ht" dateFormat="YYYY-MM-DD HH:mm:ss"></th>
			    </tr>
		    </thead>
	    </table>
	</body>
</html>