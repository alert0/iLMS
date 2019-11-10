<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>xq_message_type</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/common/util/dialogUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/bpmDefDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/util/rightsUtil.js"></script>
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
							<a class="btn btn-sm btn-primary fa-add" href="#" onclick="openDetail('','add')">
								<span>添加</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove" href="#" action="/business/xqmessagetype/messageType/remove">
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
								<li><span>分类名称:</span><input class="inputText" type="text" name="Q^CLASSIFICATION_CODE^SL"></li>
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
		var title = action == "edit" ? "编辑公告分类" : action == "add" ? "添加公告分类" : "查看公告分类";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="messageType" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson",
			idField : "ID",
			sortName : 'ID',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'ID',sortName : "ID",checkbox : true},   
			{field : 'CLASSIFICATION_CODE',sortName : "CLASSIFICATION_CODE",title : '分类名称',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'FULLNAME_',sortName : "FULLNAME_",title : '创建人',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'IS_PENDING',sortName : "IS_PENDING",title : '发布是否审批',width : 250,align : 'center',sortable : 'true'
				,formatter : function(value, row, index) {
					return value==1?'是':'否';
				}
			}, 
			{field : 'PENDING_USER_NAME',sortName : "PENDING_USER_NAME",title : '审批人姓名',width : 250,align : 'center',sortable : 'true'
			
			
			},
			{field : 'CREATE_TIME',sortName : "CREATE_TIME",title : '创建时间',width : 250,align : 'center',sortable : 'true'
			,formatter:dateTimeFormatter
			
			}, 
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.ID + ",\"edit\")' herf='javascript:void(0)'>编辑</a>";
					result += "<a class='btn btn-default fa fa-remove' onClick='openDetail(" + row.ID + ",\"get\")' herf='javascript:void(0)'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/business/xqmessagetype/messageType/remove?authorizeId=" + row.ID + "' herf='#'>删除</a>";
				
					return result;
				}
			} ] ],
			onLoadSuccess : function() {
				$('#grid').datagrid('unselectAll');
				handGridLoadSuccess();
			}
		}));
	}

</script>
