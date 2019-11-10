<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>子系统定义</title>
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
							<a class="btn btn-sm btn-primary fa-remove" href="javascript:;" action="/base/base/subsystem/remove">
								<span>删除</span>
							</a>
							<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left"><span>重置</span></a>
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
								<li><span>系统名称:</span><input class="inputText" type="text" name="Q^name_^SL"></li>
								<li><span>系统别名:</span><input class="inputText" type="text" name="Q^alias_^SL"></li>
								
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
		var title = action == "edit" ? "编辑子系统定义" : action == "add" ? "添加子系统定义" : "查看子系统定义";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="subsystem" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	function openSysRes(id){
		var url = __ctx +"/base/base/sysResource/sysResourceList?systemId=" + id;
		HT.window.openEdit(url, "资源管理", "", 'grid', 500, 500, null, null, id, true);
	}
	
	function setMaster(id,isDefault){
		var msg=isDefault?"确定设置默认吗?":"确定取消默认吗?";
		$.topCall.confirm("提示信息",msg,function(rtn){
			if(!rtn) return;
			var url=__ctx +"/base/base/subsystem/setDefault?systemId="+id;
			$.post(url,function(){
				refreshTargetGrid("grid");
			})
		});
	}

	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson",
			idField : "id",
			sortName : 'ID_',
			sortOrder : 'desc',
			columns : [ [
			{field : 'name',sortName : "name_",title : '系统名称',align : 'center',sortable : 'true',width:150}, 
			{field : 'alias',sortName : "alias_",title : '系统别名',align : 'center',sortable : 'true',width:100}, 
			{field : 'logo',sortName : "logo_",title : 'logo地址',align : 'center',sortable : 'true',width:200}, 
			{field : 'enabled',sortName : "enabled_",title : '是否可用 ',align : 'center',sortable : 'true',width:50,
				formatter : function(value, row, index) {
					var arr = [ {'key' : 0,'value' : '禁用','css' : 'red'}, {'key' : 1,'value' : '可用','css' : 'green'} ];
					return Object.formatItemValue(arr,value);
				}
			}, 
			{field : 'isDefault',sortName : "is_default_",title : '默认系统',align : 'center',sortable : 'true',width:50,
				formatter : function(value, row, index) {
					var arr = [ {'key' : 0,'value' : '否','css' : 'red'}, {'key' : 1,'value' : '是','css' : 'green'} ];
					return Object.formatItemValue(arr,value);
				}
			}, 
			{field : 'homeUrl',sortName : "home_url_",title : '主页地址',align : 'center',sortable : 'true',width:100},
			{field : 'creator',sortName : "creator_",title : '创建人',align : 'center',sortable : 'true',width:100}, 
			{field : 'createTime',sortName : "create_time_",title : '创建时间',align : 'center',sortable : 'true',width:80,formatter:dateTimeFormatter}, 
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' href='#'>编辑</a>";
					result += "<a class='btn btn-default fa fa-eye' onClick='openDetail(" + row.id + ",\"get\")' href='#'>明细</a>";
					result += "<a class='btn btn-default fa fa-remove' onClick='openSysRes("+row.id+")' >资源管理</a>";
					if(row.isDefault){
						result += "<a class='btn btn-default fa fa-level-down' onClick='setMaster(\""+row.id+"\",false)'  href='javascript:void(0)' >取消默认</a>";
					}
					else{
						result += "<a class='btn btn-default fa fa-level-up' onClick='setMaster(\""+row.id+"\",true)' href='javascript:void(0)'>设置默认</a>";
					}
					
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/base/base/subsystem/remove?id=" + row.id + "' herf='#'>删除</a>";
					return result;
				}
			} ] ]
		}));
	}
</script>
