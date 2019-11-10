<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>xq_meetingroom_appointment</title>
<%@include file="/commons/include/list.jsp"%>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false" style="text-align: center;">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
		<!-- 				<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search" href="#">
								<span>搜索</span>
							</a>
							<a class="btn btn-sm btn-primary fa-add" href="#" onclick="openDetail('','add')">
								<span>添加</span>
							</a>
							<a class="btn btn-sm btn-primary fa-remove" href="#" action="/business/meeting/meeting/remove">
								<span>删除</span>
							</a>
						</div> -->
				<!-- 		<div class="tools">
							<a href="javascript:;" class="collapse">
								<i class="bigger-190 fa  fa-angle-double-up"></i>
							</a>
						</div> -->
					</div>
<!-- 					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul style="margin-left: -26px">
								<li><span>会议室id:</span><input class="inputText" type="text" name="Q^MEETINGROOM_ID_^SL"></li>
								<li><span>会议id:</span><input class="inputText" type="text" name="Q^MEETING_ID_^SL"></li>
								<li><span>会议名称:</span><input class="inputText" type="text" name="Q^MEETING_NAME_^SL"></li>
								<li><span>主持人姓名:</span><input class="inputText" type="text" name="Q^HOSTESS_NAME_^SL"></li>
								<li>
									<span>预约时间 从</span>:<input  name="Q^APPOINTMENT_TIME_^DL"  class="inputText date" />
								</li>
								<li>
									<span>至: </span><input  name="Q^APPOINTMENT_TIME_^DG" class="inputText date" />
								</li>
								<li><span>预约状态:</span><input class="inputText" type="text" name="Q^APPOINTMENT_STATUS_^SN"></li>
							</ul>
						</form>
					</div> -->
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
		var title = action == "edit" ? "编辑xq_meetingroom_appointment" : action == "add" ? "添加xq_meetingroom_appointment" : "查看xq_meetingroom_appointment";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="meeting" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	   function openDetail(id)
		{
		    var title="查看会议详情";
		    // HT.window.openEdit("${ctx}/flow/instance/instanceGet?id="+id,title, 'view', 'grid', 500, 500, null, null, id, true);
		    var url = __ctx + "/flow/instance/instanceGet?id="+id;
		    top.layer.open({  
		        type: 2,  
		        title: title,  
		        shadeClose: true,  
		        shade: 0.3,  
		        area : ['100%' , '100%'],  
		        content: [url, 'no']
		      });
		}
		function toTask(id) {
			var url= __ctx + '/flow/task/canLock?taskId=' + id
			$.get(url, function(rtn){
				//0:任务已经处理,1:可以锁定,2:不需要解锁 ,3:可以解锁，4,被其他人锁定,5:这种情况一般是管理员操作，所以不用出锁定按钮。
				switch(rtn){
					case 0:
						$.topCall.success("此任务已经被其他人处理完成!");
						break;
					case 1:
					case 2:
					case 3:
					case 5:
						$.openFullWindow(__ctx + '/flow/task/taskApprove?id=' + id);
						break;
					case 4:
						$.topCall.success("此任务已经被其他人锁定!");
						break;
					case 6:
						$.topCall.error("流程已经被禁止，请联系管理员！");
						break;
				}
			});
			
		}
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "pendingJson",
			idField : "id",
			sortName : 'ID_',
			sortOrder : 'desc',
			striped : true,
			columns : [ [
			{field : 'id',sortName : "ID_",checkbox : true},   
			{field : 'hymc',title :'会议名称',width : 250,align : 'center'
				 ,formatter:function(value,row,index){
				        return "<span class=\"cur\" onClick=\"toTask("+row.taskId+")\" >"+value+"</span>";
				  }
			
			}, 
			{field : 'zcr',title : '主持人',width : 250,align : 'center'
			
			
			}, 
			{field : 'hys',title : '会议室',width : 250,align : 'center'
			
			
			}, 
			/* {field : 'nodeId',title : '通知状态',width : 250,align : 'center'
			
			
			},  */
			{field : 'kssj',title : '开始时间',width : 250,align : 'center'
			,formatter:dateTimeFormatter
			
			}, 
			{field : 'jssj',title : '结束时间',width : 250,align : 'center'
			
			
			}, 
			{
				field : 'colManage',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					var /* result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",\"edit\")' herf='#'>编辑</a>"; */
					result = "<a class='btn btn-default fa fa-detail' onClick='toTask(" + row.taskId + ",\"get\")' herf='#'>明细</a>";
					/* result += "<a class='rowInLine btn btn-default fa fa-remove' action='/business/meeting/meeting/remove?id=" + row.id + "' herf='#'>删除</a>"; */
					return result;
				}
			} ] ],
			onLoadSuccess : function() {
				handGridLoadSuccess();
			}
		}));
	}
</script>
