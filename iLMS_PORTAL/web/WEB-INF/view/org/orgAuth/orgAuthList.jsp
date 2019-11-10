<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>分级组织管理</title>
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
							</a>
								<a href="javascript:void(0)" class="btn btn-sm btn-primary fa-rotate-left">
								<span>重置</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add authAdd" href="#" onclick="openDetail('','add')">
								<span>添加</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove authDel" href="#" action="/org/orgAuth/remove">
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
								<li><span>组织管理员:</span><input class="inputText" type="text" name="Q^userName^SL"></li>
								<input class="inputText" type="hidden" value="${param.orgId }" name="orgId">
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
	//最上级祖先的相应操作权限
	var orgAuthPerms = "";
	var userPerms = "";
	var orgPerms = "";
	var posPerms = "";
	var isGrade = "${param.isGrade}";//是否为分级组织管理
	var authId = "${param.authId}";
	$(function() {
		loadGrid();
		if(authId){
			$.post(__ctx+"/org/orgAuth/getJson", {"id":authId}, function(data) {
				var result = data;
				orgAuthPerms = result.orgauthPerms;
				userPerms = result.userPerms;
				orgPerms = result.orgPerms;
				posPerms = result.posPerms;
				if(orgAuthPerms.indexOf("add") == -1){
					$("a.authAdd").hide();
				}
				if(orgAuthPerms.indexOf("delete") == -1){
					$("a.authDel").hide();
				}
				if(orgAuthPerms.indexOf("edit") == -1  ){
					$("a.userEdit").hide();
				}
			});	
		}
	});
		
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑组织分级管理" : action == "add" ? "添加组织分级管理" : "查看组织分级管理";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="orgAuth" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
			url += "&demId=${param.demId}&orgId=${param.orgId}&authId=${param.authId}&isGrade=${param.isGrade}";
		}else{
			url += "?demId=${param.demId}&orgId=${param.orgId}&authId=${param.authId}&isGrade=${param.isGrade}";
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson?orgId=${param.orgId}",
			idField : "id",
			sortName : 'ID_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'id',sortName : "ID_",checkbox : true},   
			{field : 'userName',sortName : "USER_NAME",title : '组织管理员',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'orgName',sortName : "ORGNAME",title : '所管理的组织名称',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'demName',sortName : "DEM_NAME",title : '组织所属维度',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'orgPerms',sortName : "ORG_PERMS_",title : '组织管理权限',width : 250,align : 'center',sortable : 'true'
				,formatter : function(value, row, index) {
					var result = "";
					//自身的权限
					if(value.indexOf("add") >-1){
						result += "增加   "
					}
					if(value.indexOf("delete") >-1){
						result += "删除   "
					}
					if(value.indexOf("edit") >-1){
						result += "修改     "
					}
					if(isGrade){
						//祖先的权限
						if(orgPerms.indexOf("add") < 0){
							result = result.replaceAll("增加   ","");
						}
						if(orgPerms.indexOf("delete") < 0){
							result = result.replaceAll("删除   ","");
						}
						if(orgPerms.indexOf("edit") < 0){
							result = result.replaceAll("修改     ","");
						}
					}
					return result;
				}
			
			}, 
			{field : 'userPerms',sortName : "USER_PERMS_",title : '用户管理权限',width : 250,align : 'center',sortable : 'true'
				,formatter : function(value, row, index) {
					var result = "";
					if(value.indexOf("add") >-1){
						result += "增加   "
					}
					if(value.indexOf("delete") >-1){
						result += "删除   "
					}
					if(value.indexOf("edit") >-1){
						result += "修改     "
					}
					if(isGrade){
						//祖先的权限
						if(userPerms.indexOf("add") < 0){
							result = result.replaceAll("增加   ","");
						}
						if(userPerms.indexOf("delete") < 0){
							result = result.replaceAll("删除  ","");
						}
						if(userPerms.indexOf("edit") < 0){
							result = result.replaceAll("修改     ","");
						}
					}
					return result;
				}
			
			}, 
			{field : 'posPerms',sortName : "POS_PERMS_",title : '岗位管理权限',width : 250,align : 'center',sortable : 'true'
				,formatter : function(value, row, index) {
					var result = "";
					if(value.indexOf("add") >-1){
						result += "增加   "
					}
					if(value.indexOf("delete") >-1){
						result += "删除   "
					}
					if(value.indexOf("edit") >-1){
						result += "修改     "
					}
					if(isGrade){
						//祖先的权限
						if(posPerms.indexOf("add") < 0){
							result = result.replaceAll("增加   ","");
						}
						if(posPerms.indexOf("delete") < 0){
							result = result.replaceAll("删除   ","");
						}
						if(posPerms.indexOf("edit") < 0){
							result = result.replaceAll("修改     ","");
						}
					}
					return result;
				}
			
			}, 
			{field : 'orgauthPerms',sortName : "ORGAUTH_PERMS_",title : '分级管理员权限',width : 250,align : 'center',sortable : 'true'
				,formatter : function(value, row, index) {
					var result = "";
					if(value.indexOf("add") >-1){
						result += "增加   "
					}
					if(value.indexOf("delete") >-1){
						result += "删除   "
					}
					if(value.indexOf("edit") >-1){
						result += "修改     "
					}
					if(isGrade){
						//祖先的权限
						if(orgAuthPerms.indexOf("add") < 0){
							result = result.replaceAll("增加   ","");
						}
						if(orgAuthPerms.indexOf("delete") < 0){
							result = result.replaceAll("删除   ","");
						}
						if(orgAuthPerms.indexOf("edit") < 0){
							result = result.replaceAll("修改     ","");
						}
					}
					return result;
				}
			
			}, 
			{field : 'layoutPerms',sortName : "LAYOUT_PERMS_",title : '布局管理权限',width : 250,align : 'center',sortable : 'true'
				,formatter : function(value, row, index) {
					var result = "";
					if(value.indexOf("add") >-1){
						result += "增加   "
					}
					if(value.indexOf("delete") >-1){
						result += "删除   "
					}
					if(value.indexOf("edit") >-1){
						result += "修改     "
					}
					if(isGrade){
						//祖先的权限
						if(orgAuthPerms.indexOf("add") < 0){
							result = result.replaceAll("增加   ","");
						}
						if(orgAuthPerms.indexOf("delete") < 0){
							result = result.replaceAll("删除   ","");
						}
						if(orgAuthPerms.indexOf("edit") < 0){
							result = result.replaceAll("修改     ","");
						}
					}
					return result;
				}
			
			},
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					var result = "";
					if(authId){
						if(orgAuthPerms.indexOf("edit") > -1  ){
							result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>";
						}
						result += "<a class='btn btn-default fa fa-detail' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
						if(orgAuthPerms.indexOf("delete") > -1){
							result += "<a class='rowInLine btn btn-default fa fa-remove' action='/org/orgAuth/remove?id=" + row.id + "' herf='#'>删除</a>";
						}
					}else{
						result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>";
						result += "<a class='btn btn-default fa fa-detail' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
						result += "<a class='rowInLine btn btn-default fa fa-remove' action='/org/orgAuth/remove?id=" + row.id + "' herf='#'>删除</a>";
					}
					return result;
				}
			} ] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
		}));
	}
</script>
