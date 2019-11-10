<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>自定义SQL查询列表</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search" href="javaScript:void(0)">
								<span>搜索</span>
							</a>
							<a href="javaScript:void(0)" class="btn btn-sm btn-primary fa-rotate-left">
								<span>重置</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add" href="javaScript:void(0)" onclick="openDetail('','add')">
								<span>添加</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove" href="javaScript:void(0)" action="/system/query/querySqldef/remove">
								<span>删除</span>
							</a>
							<a class="btn btn-sm btn-primary fa-sign-out" href="javascript:void(0)"><span>导出</span></a> 
							<a class="btn btn-sm btn-primary fa-sign-in" href="javascript:void(0)"><span>导入</span></a>
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse">
								<i class=" fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul >
								<li>
									<span>别名:</span>
									<input class="inputText" type="text" name="Q^ALIAS_^SL">
								</li>
								<li>
									<span>名字:</span>
									<input class="inputText" type="text" name="Q^NAME_^SL">
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
		
		$("a.btn.fa-sign-out").click(handExport);// 绑定导出
		$("a.btn.fa-sign-in").click(handImport);// 绑定导入
	});

	function openDetail(id, action) {
		var title = action == "edit" ? "编辑自定义SQL" : action == "add" ? "添加自定义SQL" : "查看自定义SQL";
		action = action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url = "querySqldef" + action;
		if (!$.isEmpty(id)) {
			url += '?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}

	function reload (){
		$('.my-easyui-datagrid').datagrid('reload');
	}
	
	function handImport(){
		var dialog;
		var def = {  passConf : reload, title : '导入查询', width : 600, height : 400, modal : true, resizable : true, iconCls : 'fa fa-sign-in'};
		dialog = $.topCall.dialog( {  src : __ctx + '/system/query/querySqlDefImport', base : def });
	}
	// 导出
	function handExport(){
		var ids= $.getSelectIds($("#grid"),'id');
		if (ids == null) {
		    $.topCall.error("请选择自定义SQL!");
		    return;
		}
		var frm = new com.hotent.form.Form();
		frm.creatForm("frmExport",__ctx + "/system/query/querySqldef/export");
		frm.addFormEl("ids", ids);
		frm.submit();
	}
	

	function viewList(alias) {
		window.location.href = "queryViewList?sqlAlias=" + alias;
	}

	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : "querySqldef/listJson",
			idField : "id",
			sortName : 'ID_',
			sortOrder : 'desc',
			columns : [ [ {
				field : 'id',
				sortName : "id_",
				checkbox : true
			},{
				field : 'alias',
				sortName : "ALIAS_",
				title : '别名',
				width : 250,
				align : 'left',
				sortable : 'true'
			}, {
				field : 'name',
				sortName : "NAME_",
				title : '名称',
				width : 250,
				align : 'left',
				sortable : 'true'
			}, {
				field : 'dsName',
				sortName : "DS_NAME_",
				title : '数据源',
				width : 250,
				align : 'center',
				sortable : 'true'
			}, {
				field : 'sql',
				sortName : "SQL_",
				title : 'SQL语句',
				width : 250,
				align : 'center',
				sortable : 'true',
				formatter : function(value) {
					if (value.length > 30) {
						return value.substring(0, 27) + "...";
					}
					return value;
				}
			}, {
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='javaScript:void(0)'>编辑</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/system/query/querySqldef/remove?id=" + row.id + "' herf='javaScript:void(0)'>删除</a>";
					result += "<a class='btn btn-default fa fa-detail' onClick='viewList(\"" + row.alias + "\")'>视图列表</a>";
					return result;
				}
			} ] ]
		}));
	}
	
	//处理导入成功后，关闭页面时，刷新相关列表
	function reloadForImport (){
		$('.my-easyui-datagrid').datagrid('reload');
	}
</script>
