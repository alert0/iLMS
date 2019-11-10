<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>类别列表</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body class="easyui-layout">

	<div data-options="region:'center',border:false" style="text-align: center;">
		<div id="tb" style="padding: 8px; height: auto;">
			<div style="margin-bottom: 5px">
				<a href="sysTypeEdit" class="easyui-linkbutton link" iconCls="icon-add" plain="true">添加</a> <a href="javascript:;" action="edit" class="easyui-linkbutton link" iconCls="icon-edit" plain="true">编辑</a> <a href="javascript:;" action="sysTypeGet" class="easyui-linkbutton link " iconCls="icon-detail" plain="true">明细</a> <a
					href="javascript:;" class="easyui-linkbutton link " action="remove" iconCls="icon-remove" plain="true">移除</a> <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
			</div>
			<div class="foldBox searchBox" id="searchBox">
				<div class="content">
					<form id="searchForm">
						<ul class="row">
							<li><span>所属分类组业务主键:</span><input type="text" name="Q^type_group_key_^SL"></li>
							<li><span>分类名称:</span><input type="text" name="Q^name_^SL"></li>
							<li><span>节点的分类Key:</span><input type="text" name="Q^type_key_^SL"></li>
							<li><span>flat 平行；tree 树形:</span><input type="text" name="Q^stru_type_^SL"></li>
							<li><span>父节点:</span><input type="text" name="Q^parent_id_^SL"></li>
							<li><span>层次:</span><input type="text" name="Q^depth_^SL"></li>
							<li><span>路径:</span><input type="text" name="Q^path_^SL"></li>
							<li><span>是否叶子节点。Y=是；N=否:</span><input type="text" name="Q^is_leaf_^SL"></li>
							<li><span>所属人ID:</span><input type="text" name="Q^owner_id_^SL"></li>
							<li><span>序号:</span><input type="text" name="Q^sn_^SL"></li>
							<li><span>创建人ID:</span><input type="text" name="Q^create_by_^SL"></li>
							<li><span>创建时间 从</span>:<input name="Q^create_time_^DL" class="inputText date" /> <span>至: </span><input name="Q^create_time_^DG" class="inputText date" /></li>
							<li><span>创建者所属组织ID:</span><input type="text" name="Q^create_org_id_^SL"></li>
							<li><span>更新人ID:</span><input type="text" name="Q^update_by_^SL"></li>
							<li><span>更新时间 从</span>:<input name="Q^update_time_^DL" class="inputText date" /> <span>至: </span><input name="Q^update_time_^DG" class="inputText date" /></li>
						</ul>
					</form>
				</div>
			</div>
		</div>
		<table id="userGridList" class="easyui-datagrid" data-options="fitColumns:false,checkOnSelect:true,pagination:true,toolbar:'#tb'" fit="true" url="${ctx}/system/sysType/listJson">
			<thead>
				<tr>
					<th field="id" checkbox="true" class="pk"></th>
					<th field="typeGroupKey" sortable="true" sortName="type_group_key_">所属分类组业务主键</th>
					<th field="name" sortable="true" sortName="name_">分类名称</th>
					<th field="typeKey" sortable="true" sortName="type_key_">节点的分类Key</th>
					<th field="struType" sortable="true" sortName="stru_type_">flat 平行；tree 树形</th>
					<th field="parentId" sortable="true" sortName="parent_id_">父节点</th>
					<th field="depth" sortable="true" sortName="depth_">层次</th>
					<th field="path" sortable="true" sortName="path_">路径</th>
					<th field="isLeaf" sortable="true" sortName="is_leaf_">是否叶子节点。Y=是；N=否</th>
					<th field="ownerId" sortable="true" sortName="owner_id_">所属人ID</th>
					<th field="sn" sortable="true" sortName="sn_">序号</th>
					<th field="createBy" sortable="true" sortName="create_by_">创建人ID</th>
					<th field="createTime" sortable="true" sortName="create_time_">创建时间</th>
					<th field="createOrgId" sortable="true" sortName="create_org_id_">创建者所属组织ID</th>
					<th field="updateBy" sortable="true" sortName="update_by_">更新人ID</th>
					<th field="updateTime" sortable="true" sortName="update_time_">更新时间</th>
					<th manager=true width="40">
						<f:a alias="pk" href="sysTypeGet?id={id}" css="link icon-back">详细</f:a>
						<f:a alias="pk" href="javascript:;" action="sysTypeEdit?id={id}" css="link icon-edit">编辑</f:a>
						<f:a alias="pk" action="remove?id={id}" css="link icon-remove">删除</f:a>
					</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>