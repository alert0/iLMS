<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<title>DemoUser</title>
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
							<a class="btn btn-sm btn-primary fa-remove" href="javascript:;" action="remove">
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
								<li><span>用户名:</span><input class="inputText" type="text" name="Q^userName^SL"></li>
								<li><span>地址:</span><input class="inputText" type="text" name="Q^address^SL"></li>
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
		var title = action == "edit" ? "编辑DemoUser" : action == "add" ? "添加DemoUser" : "查看DemoUser";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="demoUser" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	function remove(id) {
		var param = {
			id : id
		}
		$.post("remove", param, function(data) {
			var result = new com.hotent.form.ResultMessage(data)
			if (result.isSuccess()) {//成功
				$.topCall.success(result.getMessage());
				$('.my-easyui-datagrid').datagrid('reload');
			} else {
				$.topCall.error(result.getMessage());
			}
		});
	}
	
	function loadGrid() {
		$('#grid').datagrid($.extend({
			url :  "listJson",
			idField : "id",
			sortName : 'id',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'userName',sortName : "userName",title : '用户名',width : 250,align : 'center',sortable : 'true'
			
			}, 
			{field : 'address',sortName : "address",title : '地址',width : 250,align : 'center',sortable : 'true'
			
			}, 
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>";
					result += "<a class='btn btn-default fa fa-remove' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
					result += "<a class='btn btn-default fa fa-remove' onClick='remove(" + row.id + ")' herf='#'>删除</a>";
					return result;
				}
			} ] ],
		}, $defaultOptions));
	}
</script>
