<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自定义对话框列表</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/common/util/dialogUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/util/customUtil.js"></script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-primary  btn-sm fa-search" href="javaScript:void(0)">
								<span>搜索</span>
							</a>
							<a class="btn btn-primary btn-sm fa-add" href="javaScript:void(0)" onClick='openDetail("","add")'>
								<span>添加</span>
							</a>
							<a class="btn btn-primary btn-sm fa-remove" href="javaScript:void(0)" action="/form/customDialog/remove">
								<span>删除</span>
							</a>
							<a href="javaScript:void(0)" class="btn btn-sm btn-primary fa-rotate-left">
								<span>重置</span>
							</a>
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse">
								<i class="fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<li class="mar-l-20">
									<span>名称:</span>
									<input class="inputText " type="text" name="Q^name_^SL">
								</li>
								<li class="mar-l-20">
									<span>别名:</span>
									<input class="inputText" type="text" name="Q^alias_^SL">
								</li>
							</ul>
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
	</div>
</body>
</html>
<script type="text/javascript">
	$(function() {
		loadGrid();
	});
	function openHelp(id) {
		var dialog;
		var def = {
			passConf : null,
			title : '帮助文档',
			width : 900,
			height : 450,
			modal : true,
			resizable : true,
			iconCls : 'icon-collapse',
			buttons : [ {
				text : '关闭',
				handler : function() {
					dialog.dialog('close');
				}
			} ]
		};
		dialog = $.topCall.dialog({
			src : __ctx + '/form/customDialog/customDialogHelp?id=' + id,
			base : def
		});
	}

	function openDetail(id, action) {
		var title = action == "customDialogEdit" ? "编辑自定义对话框" : action == "add" ? "添加自定义对话框" : "查看自定义对话框";
		action = action == "add" ? "customDialogEdit" : action;
		var url = action;
		if (id && id != "") {
			url += "?id=" + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : __ctx + "/form/customDialog/listJson",
			idField : "id",
			sortName : 'id_',
			sortOrder : 'desc',
			columns : [ [ 
			    {
					field : 'id',
					sortName : "id_",
					checkbox : true,
					align : 'center'
				}, {
					field : 'name',
					sortName : "name_",
					title : '名称',
					width : 250,
					align : 'left',
					sortable : 'true'
				}, {
					field : 'alias',
					sortName : "alias_",
					title : '别名',
					width : 250,
					align : 'center',
					sortable : 'true'
				} ,
				{
					field : 'style',
					sortName : "style_",
					title : '显示样式',
					align : 'center',
					sortable : 'true',
					formatter : function(value) {
						if (value == 0) {
							return "列表";
						} else {
							return "树形";
						}
					}
				}, {
					field : 'system',
					sortName : "system_",
					title : '系统默认',
					align : 'center',
					sortable : 'true',
					formatter : function(value) {
						var arr = [ {'key' : true,'value' : '是','css' : 'green'}, 
						            {'key' : false,'value' : '否','css' : 'red'} ];
						return Object.formatItemValue(arr, value);
					}
			}, {
				field : 'objName',
				sortName : "obj_name_",
				title : '对象名称',
				width : 100,
				align : 'left',
				sortable : 'true'
			}, {
				field : 'dsalias',
				sortName : "dsalias_",
				title : '数据源别名',
				width : 100,
				align : 'center',
				sortable : 'true'
			}, {
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-eye' onClick='preview(\"" + row.alias + "\")' herf='javaScript:void(0)'>预览</a>";
					result += "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"customDialogEdit\")' herf='javaScript:void(0)'>编辑</a>";
					if (!row.system)
						result += "<a class='rowInLine btn btn-default fa fa-remove' action='/form/customDialog/remove?id=" + row.id + "' herf='javaScript:void(0)'>删除</a>";
					result += "<a class='btn btn-default fa fa-help' onClick='openHelp(" + row.id + ")' herf='javaScript:void(0)'>帮助</a>";
					return result;
				}
			} ] ]
		}));
	}

	function preview(alias) {
		var url = __ctx + '/form/customDialog/getByAlias?alias=' + alias;
		$.ajax({
			url : url,
			type : 'POST',
			dataType : 'json',
			success : function(customDialog) {
				var list = JSON.parse(customDialog.conditionfield);
				var param = {};
				$(list).each(function() {
					if (this.defaultType == "4") {
						param[this.field] = this.comment;
					}
				});

				if ($.isEmptyObject(param)) {//没有动态传入的字段
					CustomDialog.openCustomDialog(alias);
					return;
				}
				var u = __ctx + "/system/query/paramDialog";
				DialogUtil.openDialog(u, "参数对话框", param, function(data, dialog) {
					dialog.dialog('close');
					CustomDialog.openCustomDialog(alias, null, {
						param : data.map
					});
				}, 400, 300);
			}
		});

	}
</script>