<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>首页布局</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<style>
	 .toolbar-head>.buttons.buttons_right {
    float: right;
    }
    .toolbar-body>.title{
    	    color: #067E53;
    font-size: 12px;
    padding: 10px 15px 10px 32px;
    left: 0;
    background: url(../images/other/search_title.png) no-repeat scroll 15px center #BADFC9;
    width: 100%;
    }

</style>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
		<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-head">
					<!-- 顶部按钮 -->
					<div class="buttons">
						<a class="btn btn-sm btn-primary fa-search apval-item" href="javascript:void(0);"><span>搜索</span></a> 
						<a href="#" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
					    <a class="btn btn-sm btn-primary fa-add" href="#" onclick="openDetail('','add')">
							<span>添加</span>
						</a>
						<a class="btn btn-sm btn-primary fa-remove" href="#" action="/portal/sysIndexLayout/sysIndexLayout/remove">
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
								<li><span>布局名称:</span><input class="inputText" type="text" name="Q^NAME^SL"></li>
								<li><span>布局描述:</span><input class="inputText" type="text" name="Q^MEMO^SL"></li>
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
	var title = action == "edit" ? "编辑布局" : action == "add" ? "添加布局" : "查看布局";
	action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
	var url="sysIndexLayout" + action;
	if(!$.isEmpty(id)){
		url+='?id=' + id;
	}
	update();
	HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
}

function update(){
	
}


function loadGrid() {
	$('#grid').datagrid($.extend($defaultOptions,{
		url :  "listJson",
		idField : "id",
		sortName : 'sn',
		sortOrder : 'asc',
		striped : true,
		columns : [ [
		{field : 'id',sortName : "id",checkbox : true},   
		{field : 'name',sortName : "name",title : '布局名称',width : 250,align : 'center',sortable : 'true'
		
		}, 
		{field : 'memo',sortName : "memo",title : '布局描述',width : 250,align : 'center',sortable : 'true'
		}, 
		
		{
			field : 'colManage',
			title : '管理',
			align : 'center',
			 formatter : function(value, row, index) {
				var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='javascript:void(0)'>编辑</a>";
				result += "<a class='btn btn-default fa fa-detail' onClick='openDetail(" + row.id + ",\"get\")' herf='javascript:void(0)'>明细</a>";
				result += "<a class='rowInLine btn btn-default fa fa-remove' action='/portal/sysIndexLayout/sysIndexLayout/remove?id=" + row.id + "' herf='#'>删除</a>";
				return result;
			} 
		} ] ]
		/* onLoadSuccess : function() {
			handGridLoadSuccess();
		} */
	}));
} 
</script>
