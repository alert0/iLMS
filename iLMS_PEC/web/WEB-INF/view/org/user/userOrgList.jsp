<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>用户的组织列表</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/util/customUtil.js"></script>
<script>
var userId="${param.userId}";
$(function(){
});
 
</script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
								<span>搜索</span>
							</a>
<!-- 							<a class="btn btn-sm btn-primary fa-add" id="add" href="javascript:void(0)" onclick="openDetail('','selectAdd')"> -->
<!-- 								<span>加到组织</span> -->
<!-- 							</a> -->
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
								<li><span>组织名称:</span><input class="inputText" type="text" name="Q^org.name_^SL"></li>
							</ul>
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid" ></div>
		</div>
	</div>
</body>
</html>
<script>
	$(function() {
		loadGrid();
	});

	function removeRow(id) {
		$.topCall.confirm('温馨提示', '确定删除？', function(r){
			if(!r)return false;
			var param = {
					userId : id,
					orgId:orgId
				}
				$.post(__ctx+"/org/orgUser/removeByOrgIdUserId", param, function(data) {
					var result = new com.hotent.form.ResultMessage(data)
					if (result.isSuccess()) {//成功
						$.topCall.success(result.getMessage());
						$('.my-easyui-datagrid').datagrid('reload');
					} else {
						$.topCall.error(result.getMessage());
					}
				});
		});
	}
	
	function loadGrid() {
		$('#grid').datagrid($.extend({
			url :  __ctx + "/org/user/listUserOrgJson?userId="+userId,
			idField : "id",
			sortName : 'id_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			   {field : 'orgName',sortName : "org_name_",title : '组织名称',width : 500,align : 'center'}
			] ],
		}, $defaultOptions));
	}
</script>
