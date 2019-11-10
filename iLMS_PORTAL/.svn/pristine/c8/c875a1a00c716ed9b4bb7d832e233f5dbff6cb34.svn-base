<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>工作日历定义</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/common/util/dialogUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/util/customUtil.js"></script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false">
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-head">
					<!-- 顶部按钮 -->
					<div class="buttons">
						<a class="btn btn-primary btn-sm fa-search" href="javascript:;"><span>搜索</span></a>
						<a class="btn btn-primary btn-sm fa-add" href="javaScript:void(0)" onClick="openDetail('','add')"><span>添加</span></a>
						<a class="btn btn-primary btn-sm fa-remove" href="javaScript:void(0)" action="/calendar/work/remove"><span>删除</span></a>
						<a href="javaScript:void(0)" class="btn btn-sm btn-primary fa-rotate-left">
							<span>重置</span>
						</a>
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"> <i
							class="fa  fa-angle-double-up"></i>
						</a>
					</div>
				</div>
				<div class="toolbar-body">
					<form id="searchForm" class="search-form">
						<ul>
							<li><span>日历名称:</span><input type="text" class="inputText"
								name="Q^name_^SL"></li>
						</ul>
					</form>
				</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>
	<script type="text/javascript">
		function loadGrid() {
			$('#grid').datagrid($.extend($defaultOptions,{
				url : __ctx + "/calendar/work/listJson",
				idField : "id",
				sortName : 'id_',
				sortOrder : 'name',
				columns : [ [ 
					{field : 'id',sortName : "id_",checkbox : true}, 
					{field : 'name',sortName : "name_",title : '名称',width : 150,align : 'left',sortable : 'true'},
				    {field : 'memo',sortName : "memo_",title : '描述',width : 150,align : 'left',sortable : 'true'},
					{field : 'isDefault',sortName : "is_default_",title : '是否默认',width : 100,align : 'center',sortable : 'true',
						formatter : function(value, row, index) {
							var arr = [ { 'key' : '1','value' : '是','css' : 'green'}, 
							            {'key' : '0','value' : '否','css' : 'red'} ];
							return Object.formatItemValue(arr,value);
						}
					}, 
					{field : 'colManage',title : '操作',width : 80,align : 'center',sortable : 'true',formatter : mangerFormater} 
					]]
			})); 
		}
		
		function openDetail(id, action) {
			var title = action == "edit" ? "编辑日历" : action == "add" ? "添加日历" : "查看日历";
			var url = "workEdit";
			if (id && id != "") {
				url += "?id=" + id;
			}
			HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
		}
		
		function mangerFormater(value, row, index){
			var result = "";
			if (row.isDefault != '1') {
				result += '<a class="btn btn-default fa fa-send" href="${ctx}/calendar/work//setDefault?id='+row.id+'">设为默认</a>';
			}
			result += '<a class="btn btn-default fa fa-send" href="javascript:;" onclick="openDetail(\''+row.id+'\',\'edit\')">编辑</a>';
			return result;
		}
		
		$(function(){
			loadGrid();
		});
	</script>
</body>
</html>