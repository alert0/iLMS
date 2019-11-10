<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>维度管理</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search" href="#">
								<span>搜索</span>
							</a>
							<a href="#" class="btn btn-sm btn-primary fa-rotate-left">
								<span>重置</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add" href="#" onclick="openDetail('','add')">
								<span>添加</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove" href="#" action="/org/sysDemension/remove">
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
							<ul>
								<li><span>维度名称:</span><input class="inputText" type="text" name="Q^DEM_NAME_^SL"></li>
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
		
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑组织维度" : action == "add" ? "添加组织维度" : "查看组织维度";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="sysDemension" + action;
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
			columns : [ [
			{field : 'id',sortName : "ID_",checkbox : true},   
			{field : 'demName',sortName : "DEM_NAME_",title : '维度名称',width : 300,align : 'center',sortable : 'true'},
			{field : 'demCode',sortName : "DEM_Code_",title : '维度别名',width : 150,align : 'center',sortable : 'true'}, 
			{field : 'isDefault',sortName : "IS_DEFAULT_",title : '是否默认',width : 100,align : 'center',sortable : 'true',
				formatter:function(value,row,index){ 
					var arr = [ { 'key' : '1','value' : '是'}, 
					            {'key' : '0', 'value' : '否'}];
					return Object.formatItemValue(arr,value);
				}
			},  
			{field : 'demDesc',sortName : "DEM_DESC",title : '描述',width : 550,align : 'center',sortable : 'true'
			
			
			}, 
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "";
					result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>";
					result += "<a class='btn btn-default fa fa-detail' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
					if(row.isDefault==0){
						result += "<a class='btn btn-default fa fa-thumbs-o-up userEdit' onclick='setDefault(" + row.id + ")' herf='javascript:void(0)'>设置为默认</a>"
					}
					if(row.id != 1){
						result += "<a class='rowInLine btn btn-default fa fa-remove' action='/org/sysDemension/remove?id=" + row.id + "' herf='#'>删除</a>";
					}
					return result;
				}
			} ] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
		}));
	}
		
	function setDefault(id){
		$.topCall.confirm("提示信息","确认将该维度设置为默认维度吗？",function(rtn){
			if(!rtn) return;
			$.post(__ctx +"/org/sysDemension/setDefault",{id:id},function(data){
				refreshTargetGrid("grid");
			})
		});
	}
</script>
