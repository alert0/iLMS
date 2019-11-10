<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>角色管理</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" >
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<!-- 顶部按钮 -->
				<div class="buttons">
				   <a class="btn btn-sm btn-primary fa-search" href="javascript:;">
						<span>搜索</span>
					</a>
					<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
						<span>重置</span>
					</a>
					
					<a class="btn btn-sm btn-primary fa-add" href="javascript:;" onclick="openDetail('','add')">
						<span>添加</span>
					</a>
					<a class="btn btn-sm btn-primary fa-remove" href="javascript:;" action="/org/role/remove">
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
					<ul >
						<li><span>角色名称:</span><input class="inputText" type="text" name="Q^name_^SL"></li>
						<li><span>角色别名:</span><input class="inputText" type="text" name="Q^alias_^SL"></li>
						<li><span>状态:</span><select class="inputText" name="Q^enabled_^SL">
						   <option value="">全部</option>
						  <option value="0">禁用</option>
						   <option value="1">启用</option>
						</select>
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
		var title = action == "edit" ? "编辑角色" : action == "add" ? "添加角色" : "查看角色";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="role" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	function openUserRoleList(roleId,roleName){
		var url=__ctx+"/org/role/roleUserList?roleId="+roleId;
		HT.window.openEdit(url, "为【"+roleName+"】角色分配用户", "edit", 'grid', 800, 600, null, null, null, false);
	}
	
	function assignRoleRes(roleId){
		 var url=__ctx +"/base/base/resRole/sysResourceAssign?roleId="+ roleId;
		 var def = {
			      title : "角色资源分配",
			      width : 400,
			      height : 600,
			      modal : true,
			      resizable : true,
			      buttons : [ {
			        text : '确定',
			        handler : function(e) {
			          var win = dialog.innerWin;// 获取自定义对话框
			      	  var result=win.getResult();
			      	  saveResRole(result);
			          dialog.dialog('close');
			        }
			      }, {
			        text : '关闭',
			        handler : function() {
			          dialog.dialog('close');
			        }
			      } ]
			    };  

			    dialog = $.topCall.dialog({
			      src : url,
			      base : def
			    });
	}
	
	function saveResRole(data){
		var url=__ctx +"/base/base/resRole/save";
		$.post(url,data, function(result){
			var result=new com.hotent.form.ResultMessage(result);
			if(result.isSuccess()){
				$.topCall.success("分配资源成功!");
			}
			else{
				$.topCall.errorStack("分配资源失败!", result.getCause());
			}
		})
	}
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson",
			idField : "id",
			sortName : 'id_',
			sortOrder : 'desc',
			columns : [ [
		    {field : 'id',sortName : "id_",title : '名称',checkbox : true,width : 250,align : 'center',sortable : 'true'},
			{field : 'name',sortName : "name_",title : '角色名称',width : 250,align : 'center',sortable : 'true'
			}, 
			{field : 'alias',sortName : "alias_",title : '角色别名',width : 250,align : 'center',sortable : 'true'
			}, 
			{field : 'enabled',sortName : "enabled_",title : '状态',width : 250,align : 'center',sortable : 'true'
				,formatter:function(value,row,index){
					var arr = [ { 'key' : '1','value' : '启用','css' : 'green'}, 
					            {'key' : '0', 'value' : '禁用','css' : 'red'}];
					return Object.formatItemValue(arr,value);
				}
			}, 
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick=\"openDetail('" + row.id + "','edit')\" herf='javascript:void(0)'>编辑</a>";
					result += "<a class='btn btn-default fa fa-detail' onClick='openDetail(\"" + row.id + "\",\"get\")' herf='javascript:void(0)'>明细</a>";
					result += "<a class='btn btn-default fa fa-wrench' onClick='assignRoleRes(\"" + row.id + "\")' herf='javascript:void(0)'>资源分配</a>";
					result += "<a class='btn btn-default fa fa-users' onClick=\"openUserRoleList('" + row.id + "','" + row.name + "')\" herf='javascript:void(0)'>分配用户</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/org/role/remove?id=" + row.id + "' herf='javascript:void(0)'>删除</a>";
					return result;
				}
			} ] ]
		} ));
	}
</script>
