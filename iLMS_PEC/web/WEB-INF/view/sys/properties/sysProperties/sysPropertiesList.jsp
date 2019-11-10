
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>系统属性</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search" href="javascript:;">
								<span>搜索</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add" href="javascript:;" onclick="openDetail('','add')">
								<span>添加</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove" href="javascript:;" action="/sys/properties/sysProperties/remove">
								<span>删除</span>
							</a>
							
							<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
							
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
								<li><span>参数名:</span><input class="inputText" type="text" name="Q^name^SL"></li>
								<li><span>别名:</span><input class="inputText" type="text" name="Q^alias^SL"></li>
								<li><span>分组:</span><input class="inputText" type="text" name="Q^group_^SL"></li></br>
								<li>
									<span>创建时间从:</span><input  name="Q^createTime^DL"  class="inputText date" />
								</li>
								<li>
									<span>至:</span><input  name="Q^createTime^DG" class="inputText date" />
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
		
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑系统属性" : action == "add" ? "添加系统属性" : "查看系统属性";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="sysProperties" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson",
			idField : "id",
			striped : true,
			columns : [ [
			{field : 'name',sortName : "name",title : '参数名',width : 250,align : 'center',sortable : 'true'}, 
			{field : 'alias',sortName : "alias",title : '别名',width : 250,align : 'center',sortable : 'true'}, 
			{field : 'group',sortName : "group_",title : '分组',width : 250,align : 'center',sortable : 'true'}, 
			{field : 'encrypt',sortName : "encrypt",title : '是否加密',align : 'center',sortable : 'true',formatter : function(value, row, index) {
				var arr = [ {'key' : 1,'value' : '加密','css' : 'red'}, {'key' : 0,'value' : '明文','css' : 'green'} ];
				return Object.formatItemValue(arr,value);
			}}, 
			{field : 'value',sortName : "value",title : '参数值',width : 250,align : 'center',sortable : 'true',
				formatter : function(value, row, index) {
					return row.encrypt==1?"已加密":value;
				}
			}, 
			{field : 'createTime',sortName : "createTime",title : '创建时间',width : 250,align : 'center',sortable : 'true'
			,formatter:dateTimeFormatter}, 
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>";
					result += "<a class='btn btn-default fa fa-eye' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/sys/properties/sysProperties/remove?id=" + row.id + "' herf='#'>删除</a>";
					return result;
				}
			} ] ]
		}));
	}
</script>
