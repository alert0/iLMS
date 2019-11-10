<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>xq_meetingroom</title>
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
							<a class="btn btn-sm btn-primary fa-remove" href="#" action="/business/meetingroom/meetingroom/remove">
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
								<li><span>会议室名称:</span><input class="inputText" type="text" name="Q^NAME_^SL"></li>
								<!-- <li><span>是否需要审批:</span><input class="inputText" type="text" name="Q^NEED_PENDING_^SN"></li> -->
								<!-- <li><span>审批人id:</span><input class="inputText" type="text" name="Q^PENDING_USER_ID_^SL"></li> -->
								<li><span>审批人姓名:</span><input class="inputText" type="text" name="Q^PENDING_USER_NAME_^SL"></li>
								<!-- <li><span>支持的服务:</span><input class="inputText" type="text" name="Q^SUPPORT_SERVICE_^SL"></li> -->
								<li><span>会议室地址:</span><input class="inputText" type="text" name="Q^LOCATION_^SL"></li>
								<li><span>会议室容量:</span><input class="inputText" type="text" name="Q^CAPACITY_^SL"></li>
								<!-- <li><span>会议室备注:</span><input class="inputText" type="text" name="Q^MEMO_^SL"></li> -->
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
		var title = action == "edit" ? "编辑会议室" : action == "add" ? "添加会议室" : "查看会议室";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="meetingroom" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : "listJson",
			idField : "id",
			sortName : 'ID_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'id',sortName : "ID_",checkbox : true},   
			{field : 'name',sortName : "NAME_",title : '会议室名称',width : 250,align : 'center',sortable : 'true'
			
			}, 
			{field : 'needPending',sortName : "NEED_PENDING_",title : '是否需要审批',width : 250,align : 'center',sortable : 'true'
				,formatter : function(value, row, index) {
					var result = "";
					if(value== 0){
						result = "否 ";
					}else{
						result = "是";
					}
					return result;
				}
			
			}, 
			/* {field : 'pendingUserId',sortName : "PENDING_USER_ID_",title : '审批人id',width : 250,align : 'center',sortable : 'true'
			
			
			},  */
			{field : 'pendingUserName',sortName : "PENDING_USER_NAME_",title : '审批人姓名',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			/* {field : 'supportService',sortName : "SUPPORT_SERVICE_",title : '支持的服务',width : 250,align : 'center',sortable : 'true'
			
			
			}, */ 
			{field : 'location',sortName : "LOCATION_",title : '会议室地址',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'capacity',sortName : "CAPACITY_",title : '会议室容量',width : 250,align : 'center',sortable : 'true'
			
			
			}, 
			{field : 'memo',sortName : "MEMO_",title : '会议室备注',width : 250,align : 'center',sortable : 'true'
				,formatter : function(value, row, index) {
					value = value|| '';
					return (value==''? '暂无备注' :value);
				}
			}, 
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>";
					result += "<a class='btn btn-default fa fa-detail ops_more' onClick='openDetail(" + row.id + ",\"get\")' herf='#'>明细</a>";
					result += "<a class='rowInLine btn btn-default fa fa-remove' action='/business/meetingroom/meetingroom/remove?id=" + row.id + "' herf='#'>删除</a>";
					result +="<a class='btn btn-default fa fa-eye' onClick='setAuth(" + row.id + ")' herf='#'>授权</a>";
					return result;
				}
			} ] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
		}));
	}
	
	 function setAuth(id){
			var ownerNameJson = getColumnRights(id);
			RightsUtil.openDialog("defaultObjectRightType",function(data, dialog){
				data = RightsUtil.rightsDataToArray(data);
				saveColumnRights(id,data);
				dialog.dialog('close');
			},ownerNameJson);
		}
	 
   function getColumnRights(id){
			var ownerNameJson = [];
			$.ajax({ 
		       type: "get", 
		       url: "getColumnRights?id="+ id, 
		       async:false,
		       dataType:'json',
		       success: function(data){ 
		    	   ownerNameJson = data;
		       } 
			});
			return ownerNameJson;
	}
   
	function saveColumnRights(id,ownerNameJson){
		var params = {
			id : id,
			rightsData : JSON.stringify(ownerNameJson),
		};
		$.ajax({
			   type: "POST",
			   url: __ctx + "/business/meetingroom/meetingroom/saveColumnRights",
			   async:true,
			   dataType:'json',
			   data: params,
			   success: function(data){
				   if(data.result==1){
					   $.topCall.alert("温馨提示",data.message);
					}else{
						$.topCall.error(data.message);
					}
			}
		}); 
	}
</script>
