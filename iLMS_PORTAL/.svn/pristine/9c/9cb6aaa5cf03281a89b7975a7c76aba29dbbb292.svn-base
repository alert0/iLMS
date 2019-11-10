<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>业务数据模板</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-box border">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search" href="javascript:;">
								<span>搜索</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add" href="javascript:;" onclick="openDetail('','add')">
								<span>添加</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove" href="javascript:;" action="/form/dataTemplate/bpmDataTemplate/remove">
								<span>删除</span>
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
							<ul style="margin-left: -26px">
								<li><span>自定义表ID:</span><input class="inputText" type="text" name="Q^TABLE_ID_^SL"></li>
								<li><span>自定义表单key:</span><input class="inputText" type="text" name="Q^FORM_KEY_^SL"></li>
								<li><span>名称:</span><input class="inputText" type="text" name="Q^NAME_^SL"></li>
								<li><span>别名:</span><input class="inputText" type="text" name="Q^ALIAS_^SL"></li>
								<li><span>样式:</span><input class="inputText" type="text" name="Q^STYLE_^SN"></li>
								<li><span>是否需要分页:</span><input class="inputText" type="text" name="Q^NEED_PAGE_^SN"></li>
								<li><span>分页大小:</span><input class="inputText" type="text" name="Q^PAGE_SIZE_^SN"></li>
								<li><span>数据模板别名:</span><input class="inputText" type="text" name="Q^TEMPLATE_ALIAS_^SL"></li>
								<li><span>数据模板代码:</span><input class="inputText" type="text" name="Q^TEMPLATE_HTML_^SL"></li>
								<li><span>显示字段:</span><input class="inputText" type="text" name="Q^DISPLAY_FIELD_^SL"></li>
								<li><span>排序字段:</span><input class="inputText" type="text" name="Q^SORT_FIELD_^SL"></li>
								<li><span>条件字段:</span><input class="inputText" type="text" name="Q^CONDITION_FIELD_^SL"></li>
								<li><span>管理字段:</span><input class="inputText" type="text" name="Q^MANAGE_FIELD_^SL"></li>
								<li><span>过滤条件:</span><input class="inputText" type="text" name="Q^FILTER_FIELD_^SL"></li>
								<li><span>变量字段:</span><input class="inputText" type="text" name="Q^VAR_FIELD_^SL"></li>
								<li><span>过滤类型（1.建立条件,2.脚本条件）:</span><input class="inputText" type="text" name="Q^FILTER_TYPE_^SN"></li>
								<li><span>数据来源:</span><input class="inputText" type="text" name="Q^SOURCE_^SN"></li>
								<li><span>流程定义ID:</span><input class="inputText" type="text" name="Q^DEF_ID_^SL"></li>
								<li><span>是否查询:</span><input class="inputText" type="text" name="Q^IS_QUERY_^SN"></li>
								<li><span>是否过滤:</span><input class="inputText" type="text" name="Q^IS_FILTER_^SN"></li>
								<li><span>导出字段:</span><input class="inputText" type="text" name="Q^EXPORT_FIELD_^SL"></li>
								<li><span>打印字段:</span><input class="inputText" type="text" name="Q^PRINT_FIELD_^SL"></li>
							</ul>
						</form>
					</div>
				</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>
</body>
</html>
<script>
	$(function() {
		loadGrid();
	});
		
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑业务数据模板" : action == "add" ? "添加业务数据模板" : "查看业务数据模板";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="bpmDataTemplate" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson",
			idField : "id",
			sortName : 'ID_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'tableId',sortName : "TABLE_ID_",title : '自定义表ID',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'formKey',sortName : "FORM_KEY_",title : '自定义表单key',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'name',sortName : "NAME_",title : '名称',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'alias',sortName : "ALIAS_",title : '别名',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'style',sortName : "STYLE_",title : '样式',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'needPage',sortName : "NEED_PAGE_",title : '是否需要分页',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'pageSize',sortName : "PAGE_SIZE_",title : '分页大小',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'templateAlias',sortName : "TEMPLATE_ALIAS_",title : '数据模板别名',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'templateHtml',sortName : "TEMPLATE_HTML_",title : '数据模板代码',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'displayField',sortName : "DISPLAY_FIELD_",title : '显示字段',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'sortField',sortName : "SORT_FIELD_",title : '排序字段',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'conditionField',sortName : "CONDITION_FIELD_",title : '条件字段',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'manageField',sortName : "MANAGE_FIELD_",title : '管理字段',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'filterField',sortName : "FILTER_FIELD_",title : '过滤条件',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'varField',sortName : "VAR_FIELD_",title : '变量字段',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'filterType',sortName : "FILTER_TYPE_",title : '过滤类型（1.建立条件,2.脚本条件）',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'source',sortName : "SOURCE_",title : '数据来源',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'defId',sortName : "DEF_ID_",title : '流程定义ID',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'isQuery',sortName : "IS_QUERY_",title : '是否查询',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'isFilter',sortName : "IS_FILTER_",title : '是否过滤',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'exportField',sortName : "EXPORT_FIELD_",title : '导出字段',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'printField',sortName : "PRINT_FIELD_",title : '打印字段',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>";
					result += "<a class='btn btn-default fa fa-remove' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/form/dataTemplate/bpmDataTemplate/remove?id=" + row.id + "' herf='#'>删除</a>";
					return result;
				}
			} ] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
		}));
	}
</script>
