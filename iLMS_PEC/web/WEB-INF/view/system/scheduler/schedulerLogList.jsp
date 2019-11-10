<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>任务执行日志查询</title>
<%@include file="/commons/include/list.jsp"%>
<script>
	var trigName = '${trigName}';
	var jobName = '${jobName}';
	$(function() {
		loadGrid();
	});
		
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  "listJson",
			queryParams:{
				jobName:jobName,
				trigName:trigName
			},
			idField : "id",
			sortName : 'id_',
			sortOrder : 'desc',
			columns : [ [
			{field : 'id',sortName : "id_",checkbox : true,align : 'center'},
			{field : 'jobName',sortName : "job_name_",title : '执行名称',align : 'center',sortable : 'true'}, 
			{field : 'trigName',sortName : "trig_name_",title : '触发名称',align : 'center',sortable : 'true'}, 
			{field : 'startTime',sortName : "start_time_",title : '开始时间',width : 150,align : 'center',sortable : 'true'
				,formatter:dateTimeFormatter}, 
			{field : 'endTime',sortName : "end_time_",title : '结束时间',width : 150,align : 'center',sortable : 'true'
			,formatter:dateTimeFormatter}, 
			{field : 'content',sortName : "content_",title : '日志内容',width : 250,align : 'center',sortable : 'true'}, 
			{field : 'state',sortName : "state_",title : '状态',width : 50, align : 'center',sortable : 'true',
				formatter : function(value, row, index) {
					var arr = [ {'key' : "0",'value' : '失败','css' : 'red'}, 
					            {'key' : '1','value' : '成功','css' : 'green'} ];
					return Object.formatItemValue(arr,value);
				}}, 
			{field : 'runTime',sortName : "run_time_",title : '运行时间持续时间',align : 'center',sortable : 'true'}] ],
		} ));
	}
	
	
	function comeBack(){
		var backUrl = "schedulerJobList";
		if(trigName){
			var param = {
				jobName : jobName
			}
			backUrl = "${ctx}/system/scheduler/getTriggersByJob";
			skipJsp(backUrl,param);
			return;
		}
		window.location.href = backUrl; 
	}
</script>
</head>
<body >
	<div class="easyui-layout" fit="true" scroll="no">
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-search" href="javascript:;">
								<span>搜索</span>
							</a>
							<a class="btn  btn-sm  btn-primary fa-remove" href="javascript:;" action="/system/scheduler/delJobLog">
								<span>删除</span>
							</a>
							<a onclick="comeBack()" class="btn  btn-sm  btn-primary fa-back">
								<span>返回</span>
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
								<c:if test="${empty param.trigName}">
									<li>
										<span>触发名称:</span>
										<input type="text" class="inputText" name="Q^trig_Name_^SL">
									</li>
								</c:if>
								<li>
									<span>时间从:</span>
									<input type="text" class="inputText date" name="Q^start_time_^DL">
								</li>
								<li>
									<span>至:</span>
									<input type="text" class="inputText date" name="Q^end_time_^DG">
								</li>
							</ul>
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>

</body>
</html>


