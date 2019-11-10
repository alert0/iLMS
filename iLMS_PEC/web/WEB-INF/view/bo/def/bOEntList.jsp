<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
<script type="text/javascript"
	src="${ctx}/js/platform/util/customUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/ztree/ztreeCreator.js"></script>
<script type="text/javascript"
	src="${ctx}/js/platform/system/dialog/updateTypeDialog.js"></script>
<script type="text/javascript">
function setTypeId(){
	new updateTypeDialog({categoryKey:'ENT_TYPE',tableName:'xbo_ent',typeIdKey:'package_id_',setting:{view:{selectedMulti:false}}}).show();
}
</script>

</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div
			data-options="region:'west',split:true,border:false,title:'业务实例分类'"
			style="width: 200px;">
			<div id="sysTypeTree" class="ztree"></div>
		</div>

		<div data-options="region:'center',border:false">
			<!-- 顶部查询操作 -->
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-head">
					<!-- 顶部按钮 -->
					<div class="buttons">
						<a class="btn btn-primary  btn-sm fa-search"
							href="javaScript:void(0)"> <span>搜索</span>
						</a> <a class="btn btn-primary btn-sm fa-add"
							href="javaScript:void(0)" onClick='openDetail()'> <span>添加</span>
						</a> <a class="btn btn-primary btn-sm fa-add"
							href="javaScript:void(0)" onClick='openExtDetail()'> <span>添加外部表</span>
						</a> <a class="btn btn-primary btn-sm fa-remove"
							href="javaScript:void(0)" action="/bo/def/bOEnt/remove"> <span>删除</span>
						</a> <a href="javaScript:void(0)"
							class="btn btn-sm btn-primary fa-rotate-left"> <span>重置</span>
						</a> <a href="javascript:;" class="btn btn-sm btn-primary fa-filter"
							onclick="setTypeId()"> 设置分类</a>
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
							<li class="mar-l-20"><span>实体名称:</span> <input
								class="inputText ipu-w-170" type="text" name="Q^name_^SL">
							</li>
							<li class="mar-l-20"><span>实体描述:</span> <input
								class="inputText ipu-w-170" type="text" name="Q^desc_^SL">
							</li>
							</br>

							<li class="mar-l-20"><span>生成表:</span> <select
								class="inputText" name="Q^is_create_table_^SL">
									<option value="">全部</option>
									<option value="1">生成</option>
									<option value="0">未生成</option>
							</select></li>
							<li class="mar-l-20"><span>可用:</span> <select
								class="inputText" name="Q^status_^SL">
									<option value="">全部</option>
									<option value="enabled">可用</option>
									<option value="forbidden">禁用</option>
							</select></li>
							<li class="mar-l-20"><span>外部表:</span> <select
								class="inputText" name="Q^is_external_^SL">
									<option value="">全部</option>
									<option value="0">自定义表</option>
									<option value="1">外部表</option>
							</select></li>
						</ul>
					</form>
				</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	var selpackageId = null;//选中的树节点
	$(function() {
		loadGrid();
		new SysTypeTree($('#sysTypeTree'), {
			typeKey : "ENT_TYPE",
			onClick : function(event, treeId, treeNode) {
				var param = {};
				if (treeNode.isRoot != 1) {
					param = {
						'Q^package_id_^L' : treeNode.id
					};
					selpackageId = treeNode.id;
				}
				$('.my-easyui-datagrid').datagrid('load', param);
			}
		});
	});
	function openDetail(id) {
		var title = "编辑实体";
		if(!id){
			title = "添加实体";
		}
		var action = "bOEntEdit";
		var url = action;
		if (id && id != "") {
			url += "?id=" + id;
		}
		if(selpackageId && selpackageId!=null){
			if (id && id != "") {
				url += "&packageId=" + selpackageId;
			}else{
				url += "?packageId=" + selpackageId;
			}
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
		
	}
	function openExtDetail(id) {
		var title = "添加外部表";
		if(id){
			title = "编辑外部表";
		}
		var action = "bOEntExtEdit";
		var url = action;
		if (id && id != "") {
			url += "?id=" + id;
		}else{
			if(selpackageId){
				url += "?packageId=" + selpackageId;
			}
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}

	function createTable(id) {
		$.ajax({
			url : __ctx + "/bo/def/bOEnt/createTable?id=" + id,
			type : 'POST',
			dataType : 'json',
			success : function(data) {
				if (data.result == 1) {
					$.topCall.success(data.message, function() {
						$('#grid').datagrid("reload");
					});
				} else {
					$.topCall.error(data.message);
				}
			}
		});
	}

	function setStatus(id, status) {
		$.ajax({
			url : __ctx + "/bo/def/bOEnt/setStatus?id=" + id + "&status=" + status,
			type : 'POST',
			dataType : 'json',
			success : function(data) {
				if (data.result == 1) {
					$.topCall.success(data.message, function() {
						$('#grid').datagrid("reload");
					});
				} else {
					$.topCall.error(data.message);
				}
			}
		});
	}
	
	function reload(entId){
		var url=__ctx + "/bo/def/bOEnt/reload?id=" + entId;
		$.post( url, function(data) {
				if(data.result==1){
					$.topCall.success("更新数据结构成功!", function() {
						$('#grid').datagrid("reload");
					});
				}
				else{
					$.topCall.error("刷新出错!",data.message);
				}
			}
		);
	}

	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions, {
			url : __ctx + "/bo/def/bOEnt/listJson",
			idField : "id",
			sortName : 'create_time_',
			sortOrder : 'desc',
			columns : [ [
				{field : 'id',sortName : "id_",checkbox : true,align : 'center'},
				{field : 'name',title : '实体名称',width : 250,align : 'center'},         
			    {field : 'desc',title : '实体描叙',width : 250,align : 'center'}, 
			    {field : 'isCreateTable',title : '是否生成表',align : 'center',
			    	formatter : function(value) {
					var arr = [ {'key' : '1','value' : '是','css' : 'green'}, 
					            {'key' : '0','value' : '否','css' : 'red'} ];
					return Object.formatItemValue(arr, value);
				}
				}, 
				{field : 'isExternal',title : '是否外部表',align : 'center',
				formatter : function(value) {
					var arr = [ {'key' : '1','value' : '是','css' : 'green'}, 
					            {'key' : '0','value' : '否','css' : 'red'} ];
					return Object.formatItemValue(arr, value);
				}
				}, 
				{field : 'status',title : '状态',align : 'center',
					formatter : function(value) {
						var arr = [ {'key' : 'enabled','value' : '可用','css' : 'green'}, 
						            {'key' : 'forbidden','value' : '禁用','css' : 'red'} ];
						return Object.formatItemValue(arr, value);
					}
				}, 
				{field : 'dsName',title : '数据源',align : 'center'}, 
				{field : 'tableName',title : '表名',align : 'center',
					formatter : function(value, row, index) {
						var result = "";
						if (row.isCreateTable == "1") {
							result = row.tableName;
						}
						return result;
					}
				}, 
				{field : 'colManage',title : '操作',align : 'center',
					formatter : function(value, row, index) {
						var result;
						if (row.isExternal == "1") {
							result = "<a class='btn btn-default fa fa-edit' onClick='openExtDetail(" + row.id + ")' herf='javaScript:void(0)'>编辑外部表</a>";
							
							result +="<a class='btn btn-default fa fa-refresh' onClick='reload(" + row.id + ")' herf='javaScript:void(0)'>刷新元数据</a>";
							
						} else {
							result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ")' herf='javaScript:void(0)'>编辑</a>";
						}
	
						if (row.editable) {
							if (row.status == "enabled") {
								result += "<a class='btn btn-default fa fa-lock' onClick='setStatus(" + row.id + ",\"forbidden\")' herf='javaScript:void(0)'>禁用</a>";
							} else {
								result += "<a class='btn btn-default fa fa-unlock' onClick='setStatus(" + row.id + ",\"enabled\")' herf='javaScript:void(0)'>可用</a>";
							}
						}
	
						if (row.isCreateTable == "0") {
							result += "<a class='btn btn-default fa fa-eject' onClick='createTable(" + row.id + ")' herf='javaScript:void(0)'>生成表</a>";
						}
						
						if(row.isExternal == "1" ||(row.isExternal == "0"&&row.isCreateTable == "1")){
							result += "<a class='btn btn-default fa fa-cloud' onClick='relation(" + row.id + ")' herf='javaScript:void(0)'>绑定关系</a>";
						}
						return result;
					}
				} ] ]
		}));
	}
	
	function relation(id) {
		var title ="实体的绑定关系";
		var url = "bODefRelation?type=boEnt&id="+id;
		HT.window.openEdit(url, title, "bODefRelation", 'grid', 100, 100, null, null, id, true);
	}
	
	//处理导入成功后，关闭页面时，刷新相关列表
	function reloadForImport (){
		$('.my-easyui-datagrid').datagrid('reload');
	}
</script>