<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>组织人员参数</title>
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
							<a class="btn btn-sm btn-primary fa-remove" href="javascript:;" action="/system/sysParams/remove">
								<span>删除</span>
							</a>
							<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
								<span>重置</span>
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
							<ul>
								<li><span>参数名:</span><input class="inputText" type="text" name="Q^NAME_^SL"></li>
								<li><input class="inputText1" type="text" style="display:none;"></li>
								<li><span>参数类型:</span>
									<select class="inputText" type="text" name="Q^TYPE_^SL">
										<option value="">--请选择--</option>
										<option value="1">用户参数</option>
										<option value="2">组织参数</option>
									</select>
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
<script>
	$(function() {
		loadGrid();
	});
		
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑组织人员参数" : action == "add" ? "添加组织人员参数" : "查看组织人员参数";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="sysParams" + action;
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
			{field : 'id',sortName : "id_",checkbox : true},    
			{field : 'name',sortName : "NAME_",title : '参数名',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'alias',sortName : "ALIAS_",title : '参数key',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{
				field : 'type',
				sortName : "TYPE_",
				title : '参数类型',
				width : 250,
				align : 'center',
				sortable : 'true',
				formatter:function(value, row, index){
					if(value=="1"){
						return "用户参数";
					}
					return "组织参数";
				}
			}, 
			{
				field : 'ctlType',
				sortName : "CTL_TYPE_",
				title : '数据来源',
				width : 250,
				align : 'center',
				sortable : 'true',
				formatter:function(value, row, index){
					if(value=="input") return "手动录入";
					if(value=="dic") return "数据字典";
					if(value=="select") return "下拉框";
					if(value=="checkbox") return "复选框";
					if(value=="radio") return "单选按钮";
					if(value=="date") return "日期";
					if(value=="number") return "数字";
					if(value=="customdialog") return "自定义对话框";
				}
			},
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>";
					result += "<a class='btn btn-default fa fa-detail' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/system/sysParams/remove?id=" + row.id + "' herf='#'>删除</a>";
					return result;
				}
			} ] ]
		}));
	}
</script>
