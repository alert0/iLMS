<%@page import="com.hotent.base.core.web.RequestUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  String formId = RequestUtil.getString(request,"formId"); %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
	</head>
	<script>
		var formId = window.passConf.formId;
		var setHtml = window.passConf.setHtml;
		function getRowData(id){
			var arrs = $('#formHistoryDialog').datagrid('getRows');
			var row ;
			$(arrs).each(function(){
				if(id==this['id'])row = this;
				});
			return row;
		}
		
		function restore(id){
			setHtml(getRowData(id).formHtml);
		}
		function preview(id){
			window.onbeforeunload  =  null ;
			var rowData =getRowData(id);

			var frm=new com.hotent.form.Form();
			frm.creatForm("bpmPreview",__ctx+"/form/form/preview");
			frm.addFormEl("id",rowData.formId);
			frm.addFormEl("isHistory",true);
			frm.addFormEl("formHtml",rowData.formHtml);
			frm.setTarget("_blank");
			frm.setMethod("post");
			frm.submit();
			frm.clearFormEl();
		};
	</script>
	<body  class="easyui-layout" fit="true"   scroll="no">
		<div data-options="region:'center',border:false"  >
			<!-- 顶部查询操作 -->
		    
			<table id="formHistoryDialog" idField="id" class="easyui-datagrid my-easyui-datagrid" data-options="singleSelect:true,fitColumns:true,checkOnSelect:true,pagination:true,onLoadSuccess:onLoadSuccess" fit="true"
			 url="${ctx}/form/history/listJson?Q^form_id_^S=<%=formId%>"  >
			    <thead>
				    <tr>
						<th field="id" checkbox="true" class="pk"  title="ID"></th> 
						<th field="name" sortable="true" sortName="name_" width="250px">表单名称</th>
						<th field="createUserName" sortable="true" sortName="create_user_name_" width="100px">创建人</th>
						<th field="createTime" sortable="true" sortName="create_time_" formatter=dateTimeFormatter width="160px">创建时间</th>
						<th field="createUserId" formatter=manager>管理</th>
				    </tr>
			    </thead>
		    </table>
		  </div>
	</body>
	<script type="text/javascript">
		function manager(value,row,index){ 
			var str =''; 
				str+= '<a onclick="preview(\''+row.id+'\')" class="btn btn-default fa-detail btn-sm">预览 </a>' ;
				str+= '<a onclick="restore(\''+row.id+'\')" class="btn btn-default fa-detail btn-sm">恢复</a>' ;
				str+= '<a  class="rowInLine  btn btn-default fa-remove btn-sm" action="/form/history/remove?id='+row.id+'">删除 </a>' ;
			 return str;
		}
	
		function onLoadSuccess(){
			handGridLoadSuccess();
		}
			
	</script>
</html>
 