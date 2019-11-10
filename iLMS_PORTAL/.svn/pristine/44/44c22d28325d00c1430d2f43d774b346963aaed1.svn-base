<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>责任部门维护</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/index/bootstrap-dialog.min.js"></script>
<script>


</script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<!-- 顶部按钮 -->
				<div class="buttons">
					<a class="btn btn-sm btn-primary fa-search" >
						<span>搜索</span>
					</a>
					<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
						<span>重置</span>
					</a>
					<a class="btn btn-sm btn-primary fa-add" id="add"  onclick="openDetail('','add')">
						<span>添加</span>
					</a>
					 <a class="btn btn-sm btn-primary fa-remove"  action="/dpm/department/remove">
						<span>删除</span>
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
						<li><span>所属科室:</span>
						<input class="inputText" name="belongDep"></select>
						</li>
						<li><span>责任组编码:</span>
						<input class="inputText" name="depCode"></select>
						</li>
					    <li><span>责任组名称:</span>
					    <input class="inputText" type="text" name="depName"></li>
					</ul>
				</form>
			</div>
		</div>
		<div id="grid" class="my-easyui-datagrid" ></div>
	</div>
	<iframe id="downloadiframe" style="display:none;"></iframe>
</body>
</html>
<script>
	$(function() {
		loadGrid();
	});
		
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑" : action == "add" ? "添加" : "查看";
		action = action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url = __ctx + "/dpm/department/dpmDepartment" + action + "";
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 580, 350, null, null, id, true);
	}
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/dpm/department/curdlistJson",
			idField : "id",
			sortName : 'ID',
			sortOrder : 'desc',
			columns : [ [
			{field : 'id',sortName : "id",checkbox : true},     
			{field : 'depCode',sortName : "depCode",title : '责任组编码',width : 130,align : 'center',sortable : 'true'}, 
			{field : 'depName',sortName : "depName",title : '责任组名称',width : 130,align : 'center',sortable : 'true'},
			{field : 'belongDep',sortName : "belongDep",title : '所属科室',width : 130,align : 'center',sortable : 'true'},
			{field : 'depChecker',sortName : "depChecker",title : '部门审核人',width : 130,align : 'center',sortable : 'true'},
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(\""+ row.id +"\",\"edit\");' herf='javascript:void(0)'>编辑</a>";
					result += "<a class='btn btn-default fa fa-detail' onClick='openDetail(\"" + row.id + "\",\"get\");' herf='javascript:void(0)'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/dpm/department/remove?id=" + row.id + "' herf='javascript:void(0)'>删除</a>";
					
					return result;
				}
			} ] ]
		}));
	}
	
	
</script>
