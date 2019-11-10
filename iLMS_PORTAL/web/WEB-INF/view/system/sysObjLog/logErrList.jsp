<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>系统出错日志</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" style="" fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-head">
					<!-- 顶部按钮 -->
					<div class="buttons">
						<a class="btn btn-sm btn-primary fa-search" href="javascript:;">
							<span>搜索</span>
						</a>
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse">
							<i class="bigger-190 fa  fa-angle-double-up"></i>
						</a>
					</div>
				</div>
				<div class="toolbar-body">
					<form id="searchForm" class="search-form">
						<ul >
							<li><span>操作人:</span><input class="inputText" type="text" name="Q^OPERATOR_^SL"></li>
							<li>
								<span>创建时间 从</span>:<input  name="Q^CREATE_TIME_^DL"  class="inputText date" />
							</li>
							<li>
								<span>至: </span><input  name="Q^CREATE_TIME_^DG" class="inputText date" />
							</li>
						</ul>
					</form>
				</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
	</div>
</body>
</html>
<script>
	$(function() {
		loadGrid();
	});
		
	function showDetail(id) {
		var url="logErrGet?id=" + id;
		HT.window.openEdit(url, "日志明细", "", 'grid', 500, 500, null, null, id, true);
	}
	
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "logErrListJson",
			idField : "id",
			sortName : 'ID_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'account',sortName : "ACCOUNT_",title : '帐号',width : 50,align : 'center',sortable : 'true'
			}, 
			{field : 'ip',sortName : "IP_",title : '内容',width : 100,align : 'center',sortable : 'true'
			}, 
			{field : 'createTime',sortName : "CREATE_TIME_",title : '创建时间',width : 50,align : 'center',sortable : 'true'
			,formatter:dateTimeFormatter}, 
			{field : 'url',sortName : "url_",title : '地址',width : 250,align : 'left',sortable : 'true'
			
			}, 
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					return  "<a class='btn btn-default fa fa-remove' onClick='showDetail(\"" + row.id + "\")' herf='#'>明细</a>";
				}
			} ] ]
		}));
	}
</script>
