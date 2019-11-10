<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript">
	var jobName="${jobName}";

	function delTrigger(name){
		$.topCall.confirm('提示信息', '确认删除吗？', function(r) {
			if (!r) return;
			var url = '${ctx}/system/scheduler/delTrigger';
			var param = {
				name : name
			}
			$.post(url,param,function(responseText) {
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
	
	function triggerAdd() {
		var url = '${ctx}/system/scheduler/schedulerTriggerAdd';
		var param = {
			jobName : jobName
		}
		skipJsp(url,param);
	}
	function lookLog(trigName) {
		var url = '${ctx}/system/scheduler/schedulerLogList';
		var param = {
			trigName:trigName,
			jobName : jobName
		}
		skipJsp(url,param);
	}
	function banAndPlay(name) {
		var url = '${ctx}/system/scheduler/toggleTriggerRun';
		var param = {
			name : name
		}
		$.post(url,param,function(responseText) {
			var resultMessage = new com.hotent.form.ResultMessage(responseText);
			if (resultMessage.isSuccess()) {
					window.location.reload();
			} else {
				$.topCall.error(resultMessage.getMessage(),'错误提示');
			}
		});
	}
	
	function loadGrid() {
		var url="getTriggersJsonByJob";
		$('#grid').datagrid($.extend($defaultOptionsNoPage,{
			url :  url,
			queryParams:{
				jobName:jobName
			},
			columns : [ [
			{field : 'jobName',title : '任务名称',width : 250,align : 'center'}, 
			{field : 'triggerName',title : '计划名称',width : 250,align : 'center',sortable : 'true'},
		    {field : 'description',title : '描述',width : 250,align : 'center',sortable : 'true'},
		    {field : 'state',title : '状态',width : 250,align : 'center',sortable : 'true',
		    	formatter : function(value, row, index) {
		    		var arr = [ { 'key' : 'NORMAL','value' : '启用','css' : 'green'}, 
					            {'key' : 'PAUSED', 'value' : '禁用','css' : 'red'}, 
					            {'key' : 'ERROR','value' : '执行出错','css' : 'red'}, 
					            {'key' : 'COMPLETE','value' : '已完成','css' : 'green'} ,
					            {'key' : 'BLOCKED','value' : '正在执行','css' : 'green'} ,
					            {'key' : 'NONE','value' : '未启动','css' : 'red'}];
					return Object.formatItemValue(arr,value);
		    	}
		    },
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var state=row.state;
					var triggerName=row.triggerName;
					var result="";
					if(state == "NORMAL"){
						result+='<a href="javaScript:void(0)" onclick="banAndPlay(\''+triggerName+'\')" class="btn btn-default fa-ban">禁用</a>';
					}
					if(state == "PAUSED"){
						result+='<a href="javaScript:void(0)" onclick="banAndPlay(\''+triggerName+'\')" class="btn btn-default fa-play-circle">启用</a>';
					}
					
					result+="<a class='btn btn-default fa-delete' onclick='delTrigger(\""+triggerName+"\")' href='javaScript:void(0)'>删除</a>";
					result+='<a class="btn btn-default fa-calendar-o" href="javaScript:void(0)" onclick="lookLog(\''+triggerName+'\')">日志</a>';
					
					
					return result;
				}
			} ] ]
		} ));
	}
	
	$(function(){
		loadGrid();
	})
</script>
</head>
<body>
	<div class="easyui-layout" style="position: fixed !important;" fit="true" scroll="no">
		<!-- 顶部按钮 -->
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-box border">
				<div class="toolbar-head">
					<div class="buttons">
						<a class="btn btn-primary btn-sm fa-add" href="javaScript:void(0)" onclick="triggerAdd()">
							<span>添加</span>
						</a>
						<a class="btn btn-primary btn-sm fa-back" href="schedulerJobList">
							<span>返回</span>
						</a>
					</div>
				</div>
			</div>
		</div>
		
		<div id="grid" class="my-easyui-datagrid"></div>
	</div>
</body>


</html>