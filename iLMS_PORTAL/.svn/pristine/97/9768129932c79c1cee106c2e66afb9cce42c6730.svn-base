<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>组织岗位</title>
<%@include file="/commons/include/list.jsp"%>
<script>
var orgId="${param.orgId}";
var authId = "${param.authId}";
var authOrgId = "${param.authOrgId}";
var posPerms = "";
var userPerms = "";
$(function(){
	if(authId){
		$.post(__ctx+"/org/orgAuth/getJson", {"id":authId}, function(data) {
			var result = data;
			posPerms = result.posPerms;
			if(posPerms.indexOf("add") == -1){
				$("a.posAdd").hide();
			}
			if(posPerms.indexOf("delete") == -1){
				$("a.posDel").hide();
			}
			if(posPerms.indexOf("edit") == -1  ){
				$("a.posEdit").hide();
			}
			
			userPerms = result.userPerms;
		});	
	}
});
</script>
</head>
<body>
	<div class="easyui-layout" fit="true" >
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
								<span>搜索</span>
							</a>
							<a href="javascript:void(0)" class="btn btn-sm btn-primary fa-rotate-left">
								<span>重置</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add posAdd " href="javascript:void(0)" onclick="openDetail('','add')">
								<span>添加</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove posDel" href="javascript:void(0)" action="/org/orgRel/remove">
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
								<li><span>岗位名称:</span><input class="inputText" type="text" name="Q^rel_name_^SL"></li>
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
		var title = action == "edit" ? "编辑组织岗位" : action == "add" ? "添加组织岗位" : "查看组织岗位";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		 
		var url="orgRel" + action+"?orgId="+orgId;
		if(!$.isEmpty(id)){
			url+='&id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 300, null, null, id, false);
	}
	function openPostUser(relId,orgId,name){
		var url=__ctx+"/org/orgRel/userOrgRelList?relId="+relId+"&orgId="+orgId;
		HT.window.openEdit(url, "为【"+name+"】岗位分配用户", "edit", 'grid', 800, 600, null, null, null, false);
	}
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson?orgId="+orgId,
			idField : "id",
			sortName : 'orgrel.id_',
			sortOrder : 'desc',
			columns : [ [
		    {field : 'id',sortName : "id_",title : '名称',checkbox : true,width : 250,align : 'center',sortable : 'true'},
			{field : 'orgName',sortName : "sys_org.name_",title : '部门名称',width :160,align : 'center',sortable : 'true'}, 
			{field : 'relName',sortName : "rel_name_",title : '岗位名称',width : 150,align : 'center',sortable : 'true'}, 
			{field : 'relCode',sortName : "rel_code_",title : '岗位编码',width : 150,align : 'center',sortable : 'true'}, 
			{field : 'jobName',sortName : "sys_org_reldef.name_",title : '职务名称',width : 150,align : 'center',sortable : 'true'}, 
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "";
					if(authId){//分级管理员
						if(posPerms.indexOf("edit") > -1){
							result = "<a class='btn btn-default fa fa-edit posEdit' onClick='openDetail(" + row.id + ",\"edit\")' href='javascript:void(0)'>编辑</a>";
						}
						result += "<a class='btn btn-default fa fa-detail' onClick='openDetail(" + row.id + ",\"get\")' herf='javascript:void(0)'>明细</a>";
						if(userPerms.indexOf("add") > -1){
							result += "<a class='btn btn-default fa fa-users' onClick=\"openPostUser(" + row.id + ",'" + row.orgId + "','" + row.relName + "')\" herf='javascript:void(0)'>分配人员</a>";
						}
						if(posPerms.indexOf("delete") >-1){
							result += "<a class='rowInLine btn btn-default fa fa-remove posDel' action='/org/orgRel/remove?id=" + row.id + "' herf='javascript:void(0)'>删除</a>";
						}
					}else{
						result = "<a class='btn btn-default fa fa-edit posEdit' onClick='openDetail(" + row.id + ",\"edit\")' href='javascript:void(0)'>编辑</a>";
						result += "<a class='btn btn-default fa fa-detail' onClick='openDetail(" + row.id + ",\"get\")' herf='javascript:void(0)'>明细</a>";
						result += "<a class='btn btn-default fa fa-users' onClick=\"openPostUser(" + row.id + ",'" + row.orgId + "','" + row.relName + "')\" herf='javascript:void(0)'>分配人员</a>";
						result += "<a class='rowInLine btn btn-default fa fa-remove posDel' action='/org/orgRel/remove?id=" + row.id + "' herf='javascript:void(0)'>删除</a>";
					}
					return result;
				}
			} ] ]
		}));
	}
</script>
