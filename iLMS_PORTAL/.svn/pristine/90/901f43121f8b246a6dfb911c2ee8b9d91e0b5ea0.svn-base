<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript">
	$(function() {
		loadGrid();
	});

	function delJob(name) {
		var url = '${ctx}/system/scheduler/delJob';
		var param = {
			jobName : name
		}
		$.topCall.confirm('提示信息', '确认删除吗？', function(r) {
			if (!r) return;
			$.post(url,param, function(responseText) {
				var resultMessage = new com.hotent.form.ResultMessage(responseText);
				if (resultMessage.isSuccess()) {
					$.topCall.success(resultMessage.getMessage(),function(){
						window.location.reload();
					});
				} else {
					$.topCall.error(resultMessage.getMessage(),'错误提示');
				}
			});
		});
	}
	function changeStart(isStandby) {
		var url = '${ctx}/system/scheduler/changeStart';
		var param = {
			isStandby : isStandby
		}
		$.post(url,param, function(responseText) {
			var resultMessage = new com.hotent.form.ResultMessage(responseText);
			if (resultMessage.isSuccess()) {
				$.topCall.success(resultMessage.getMessage(), function(){
					window.location.reload(true);
				});
			} else {
				$.topCall.error(resultMessage.getMessage(),"错误提示"); 
			}
		});
	}
	function triggersJob(jobName) {
		var url = '${ctx}/system/scheduler/getTriggersByJob';
		var param = {
			jobName : jobName
		}
		skipJsp(url,param);
	}
	
	function executeJob(jobName) {
		var param = {
			jobName : jobName
		}
		$.post("executeJob", param, function(data) {
			var result = new com.hotent.form.ResultMessage(data)
			if (result.isSuccess()) {//成功
				$.topCall.success(result.getMessage());
			} else {
				$.topCall.error(result.getMessage());
			}
		});
	}
	function lookLog(jobName) {
		var url = '${ctx}/system/scheduler/schedulerLogList';
		var param = {
			jobName : jobName
		}
		skipJsp(url,param);
	}
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptionsNoPage,{
			url :  "schedulerJobJson",
			columns : [ [
			{field : 'name',title : '任务名称',width : 250,align : 'center'}, 
			{field : 'jobClass',title : '任务类',width : 250,align : 'center',sortable : 'true'},
		    {field : 'description',title : '描述',width : 250,align : 'center',sortable : 'true'},
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa-detail' onClick='triggersJob(\""+row.name+"\")' href='javascript:void(0)'>计划列表</a>" +
							"<a class='btn btn-default fa-play-circle' onClick='executeJob(\""+row.name+"\")' herf='javascript:void(0)'>执行</a>"+
							"<a href='javascript:void(0)' onClick='lookLog(\""+row.name+"\")' class='btn btn-default fa-calendar-o'>日志</a>" +
							"<a onClick='delJob(\""+row.name+"\");' herf='javascript:void(0)' class='btn btn-default fa-remove'>删除</a>" ;
					return result;
				}
			} ] ]
		} ));
	}
	
	
</script>

</head>
<div class="easyui-layout" fit="true" >
		<!-- 顶部按钮 -->
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
					<!-- 顶部按钮 -->
					<div class="buttons">
					   <a class="btn  btn-sm btn-primary fa-add" href="schedulerJobAdd">
						<span>添加</span>
					</a>
					<c:if test="${isStandby==true}">
						<a class="btn btn-sm  btn-primary fa-send" href="javascript:changeStart('${isStandby}')">
							<span>启动任务</span>
						</a>
					</c:if>
					<c:if test="${isStandby==false}">
						<a class="btn btn-sm  btn-primary fa-add" href="javascript:changeStart('${isStandby}')">
							<span>停止</span>
						</a>
					</c:if>
				</div>
				
			</div>
		</div>
		
		<div id="grid" class="my-easyui-datagrid"></div>
	</div>
</body>