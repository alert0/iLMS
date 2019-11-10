<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>自定义视图</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/common/util/dialogUtil.js"></script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false"
			style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-head">
					<!-- 顶部按钮 -->
					<div class="buttons">
						<a class="btn btn-sm btn-primary fa-search" href="javascript:;"> <span>搜索</span>
						</a> <a href="javaScript:void(0)"
							class="btn btn-sm btn-primary fa-rotate-left"> <span>重置</span>
						</a> <a class="btn btn-sm btn-primary fa-add" href="javascript:;"
							onclick="openDetail()"> <span>添加</span>
						</a> <a class="btn btn-sm btn-primary fa-remove" href="javascript:;"
							action="/system/query/queryView/remove"> <span>删除</span>
						</a> <a class="btn btn-sm btn-primary fa-back" href="querySqldefList">
							<span>返回</span>
						</a>
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"> <i
							class=" fa  fa-angle-double-up"></i>
						</a>
					</div>
				</div>
				<div class="toolbar-body">
					<form id="searchForm" class="search-form">
						<ul style="margin-left: -26px">
							<li><span>视图名称:</span> <input class="inputText" type="text"
								name="Q^NAME_^SL"></li>
							<li><span>视图别名:</span> <input class="inputText" type="text"
								name="Q^ALIAS_^SL"></li>
						</ul>
					</form>
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

	function openDetail(id) {
		var url = "queryView/edit?sqlAlias=${param.sqlAlias}";
		if (id) {
			url += '&id=' + id;
		}
		var title=id?"编辑自定义视图":"添加自定义视图";
		HT.window.openEdit(url, title, "edit", 'grid', 500, 500, null, null, id, true);
	}

	function preview(id) {
		var url = __ctx + '/system/query/queryView/getJson?id=' + id;
		$.ajax({
			url : url,
			type : 'POST',
			dataType : 'json',
			success : function(customDialog) {//检查有没有动态传入的字段
				var url = __ctx +"/system/query/queryView/${param.sqlAlias}/" + customDialog.alias;

				var list = JSON.parse(customDialog.conditions);
				var param={};
				$(list).each(function() {
					if (this.valueFrom == 2) {
						param[this.fieldName]=this.fieldDesc;
					}
				});

				if ($.isEmptyObject(param)) {//没有动态传入的字段
					window.open(url);
					return;
				}
				var u = __ctx +"/system/query/paramDialog";
				DialogUtil.openDialog(u, "参数对话框", param,function(data,dialog){
					dialog.dialog('close');
					window.open(url+"?"+data.url);
				},400,300);
			}
		});
	}

	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : "queryView/listJson?SQL_ALIAS_=${param.sqlAlias}",
			idField : "id",
			sortName : 'ID_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [ {
				field : 'id',
				sortName : "id_",
				checkbox : true,
				width : 250,
				align : 'center'
			}, {
				field : 'sqlAlias',
				sortName : "SQL_ALIAS_",
				title : 'sql别名',
				width : 250,
				align : 'center',
				sortable : 'true'

			}, {
				field : 'name',
				sortName : "NAME_",
				title : '视图名称',
				width : 250,
				align : 'center',
				sortable : 'true'

			}, {
				field : 'alias',
				sortName : "ALIAS_",
				title : '视图别名',
				width : 250,
				align : 'center',
				sortable : 'true'

			}, {
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ")'>编辑</a>";
					result += "<a class='btn btn-default fa fa-eye' onClick='preview(\"" + row.id + "\")'>预览</a>";
					return result;
				}
			} ] ]
		}));
	}
</script>
