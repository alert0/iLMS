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
						<a class="btn btn-primary btn-sm fa-remove" href="javaScript:void(0)" action="/calendar/assign/remove"><span>删除</span></a>
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
							<li><span>分配日历名称:</span><input type="text" class="inputText"
								name="Q^calendar_name_^SL"></li>
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
				url : __ctx + "/calendar/assign/listJson",
				idField : "id",
				sortName : 'id_',
				sortOrder : 'name',
				columns : [ [ 
					{field : 'id',sortName : "id_",checkbox : true}, 
					{field : 'calendarName',sortName : "calendar_name_",title : '分配的日历名称',width : 150,align : 'left',sortable : 'true'},
					{field : 'assignType',sortName : "assign_type_",title : '被分配人类型',width : 100,align : 'center',sortable : 'true',
						formatter : function(value, row, index) {
							var arr = [ { 'key' : '1','value' : '用户','css' : 'red'}, 
							            {'key' : '2','value' : '组织','css' : 'green'} ];
							return Object.formatItemValue(arr,value);
						}
					}, 
				    {field : 'assignUserName',sortName : "assign_user_name",title : '被分配人名称',width : 150,align : 'left',sortable : 'true'},
					{field : 'colManage',title : '操作',width : 80,align : 'center',sortable : 'true',formatter : mangerFormater} 
					]]
			})); 
		}
		
		function mangerFormater(value, row, index){
			var result = '<a class="btn btn-default fa fa-send" href="javascript:;" onclick="openDetail(\''+row.id+'\',\'edit\')">编辑</a>';
			return result;
		}
		
		function openDetail(id, action) {
			var title = action == "edit" ? "编辑日历分配信息" : action == "add" ? "添加日历分配信息" : "查看日历分配信息";
			var url = "assignEdit";
			if (id && id != "") {
				url += "?id=" + id;
			}
			HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
		}
		
		$(function(){
			loadGrid();
		});
	</script>
</body>
</html>