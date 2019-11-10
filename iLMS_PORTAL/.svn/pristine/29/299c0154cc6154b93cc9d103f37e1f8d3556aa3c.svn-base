<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>组合对话框列表</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/util/customUtil.js"></script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false">
			<!-- 顶部查询操作 -->
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-box border">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-primary  btn-sm fa-search" href="javaScript:void(0)">
								<span>搜索</span>
							</a>
							<a class="btn btn-primary btn-sm fa-add" href="javaScript:void(0)" onClick='openDetail("","add")'>
								<span>添加</span>
							</a>
							<a class="btn btn-primary btn-sm fa-remove" href="javaScript:void(0)" action="/form/combinateDialog/remove">
								<span>删除</span>
							</a>
							<a href="javaScript:void(0)" class="btn btn-sm btn-primary fa-rotate-left">
								<span>重置</span>
							</a>
							
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse">
								<i class=" fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<li class="mar-l-20">
									<span>名称:</span>
									<input class="inputText ipu-w-170" type="text" name="Q^name_^SL">
								</li>
								<li class="mar-l-20">
									<span>别名:</span>
									<input class="inputText ipu-w-170" type="text" name="Q^alias_^SL">
								</li>
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
<script type="text/javascript">
	$(function() {
		loadGrid();
	});
	function openDetail(id, action) {
		var title = action == "combinateDialogEdit" ? "编辑组合对话框" : action == "add" ? "添加组合对话框" : "查看组合对话框";
		action = action == "add" ? "combinateDialogEdit" : action;
		var url = action;
		if (id && id != "") {
			url += "?id=" + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : __ctx + "/form/combinateDialog/listJson",
			idField:"id",
     		 sortName: 'id_',
	         sortOrder: 'desc',
			columns : [ [ 
				{
					field : 'id',
					sortName : "id_",
					checkbox : true,
					width : 250,
					align : 'center'
				}, {
					field : 'name',
					sortName : "name_",
					title : '名称',
					width : 200,
					align : 'center',
					sortable : 'true'
				},
			 
			 {
				field : 'alias',
				sortName : "alias_",
				title : '别名',
				width : 200,
				align : 'center',
				sortable : 'true'
			}, {
				field : 'width',
				title : '宽度',
				width : 250,
				align : 'center',
			}, {
				field : 'height',
				title : '高度',
				width : 250,
				align : 'center',
			}, {
				field : 'treeDialogName',
				title : '树对话框',
				width : 250,
				align : 'center',
			}, {
				field : 'listDialogName',
				title : '列对话框',
				width : 250,
				align : 'center',
			}, {
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-eye' onClick='CombinateDialog.open({alias:\"" + row.alias + "\"});' herf='javaScript:void(0)'>预览</a>";
					result += "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"combinateDialogEdit\")' herf='javaScript:void(0)'>编辑</a>";
					return result;
				}
			} ] ]
		}));
	}
</script>