<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>用户表</title>
<%@include file="/commons/include/list.jsp"%>
<script>
var orgId="${param.orgId}";
$(function(){
});
function setMaster(userId){
	var data=Object.toAjaxJson(__ctx + "/org/user/","setMaster",{userId:userId,orgId:orgId});
		$.topCall.alert("提示",data.message);
		HT.window.refreshTargetGrid("grid");
}
</script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<!-- 顶部按钮 -->
				<div class="buttons">
					<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
						<span>搜索</span>
					</a>
					<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
						<span>重置</span>
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
						<li><span>姓名:</span><input class="inputText" type="text" name="Q^fullname_^SL"></li>
						<li><span>账号:</span><input class="inputText" type="text" name="Q^account_^SL"></li>
						<li><span>状态:</span>
						<select class="inputText" name="Q^status_^SL">
						    <option value="">全部</option>
						  <option value="0">禁用</option>
						   <option value="1">正常</option>
						</select>
						 </li>
					</ul>
				</form>
			</div>
		</div>
		<div id="grid" class="my-easyui-datagrid" ></div>
	</div>
</body>
</html>
<script>
	var noSwtich=${cookie.origSwitch==null  };


	$(function() {
		loadGrid();
	});
		
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑用户" : action == "add" ? "添加用户" : "查看用户";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="user" + action+"?orgId="+orgId;
		if(!$.isEmpty(id)){
			url+='&id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 400, 300, null, null, id, true);
	}
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson?orgId="+orgId,
			idField : "id",
			sortName : 'id_',
			sortOrder : 'desc',
			columns : [ [
			{field : 'id',sortName : "id_",checkbox : true},     
			{field : 'fullname',sortName : "fullname_",title : '姓名',width : 130,align : 'center',sortable : 'true'}, 
			{field : 'account',sortName : "account_",title : '账号',width : 130,align : 'center',sortable : 'true'},
			{field : 'email',sortName : "email_",title : '邮箱',width : 130,align : 'center',sortable : 'true'},
			{field : 'mobile',sortName : "mobile_",title : '手机号码',width : 130,align : 'center',sortable : 'true'}, 
			{field : 'weixin',sortName : "weixin_",title : '微信号',width : 130,align : 'center'}, 
			{field : 'sex',sortName : "sex_",title : '性别',width :80,align : 'center',sortable : 'true'}, 
			{field : 'status',sortName : "status_",title :'状态',width :80,align :'center',sortable: 'true',
				formatter:function(value,row,index){ return value==0?"禁用":"正常";}
			}, 
			{field : 'createTime',sortName : "create_time_",title : '创建时间',width : 150,align : 'center',sortable : 'true',formatter:dateTimeFormatter}, 
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-detail' onClick='openDetail(" + row.id + ",\"get\");' herf='javascript:void(0)'>明细</a>";
					return result;
				}
			} ] ]
		}));
	}
</script>
