<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/edit.jsp"%>
<script type="text/javascript" src="${ctx }/js/platform/system/scheduler/JobDialog.js"></script>
 
<script type="text/javascript">
function addjob() {
	addRow();
}
$(function() {
	var frm = $('#jobForm').form();
	$("a.btn.fa-save").click(function() {
		if (frm.valid()) {
			frm.ajaxForm({
				success : showResponse
			});
			var str = setParameterXml();
			$("#parameterJson").val(str);
			$('#jobForm').submit();
		}
	});
});
function showResponse(responseText) {
	var resultMessage=new com.hotent.form.ResultMessage(responseText);
	if(resultMessage.isSuccess()){
		$.topCall.message({base:{type:'confirm',title:'温馨提示',msg:resultMessage.getMessage()+',是否继续操作',fn:function(r){
			if(r){
				window.location.reload(true);
			}else{
				window.location.href = "${ctx}/system/scheduler/schedulerJobList";
			}
		}}});
	}else{
		$.topCall.error("错误提示",resultMessage.getMessage()); 
	}
}

function isExist(type){
	var id = type=="jobName"?'name':'className';
	var name = $('#'+id).val();
	if(name){
		$.ajax({
			url:"isExist",
			data:{'type':type,'name':name},
			dataType:'json',
			async:false,
			success:function(data){
				if(data==true){
					var msg = type=="jobName"?'任务名称已经存在，请重新填写！':'任务列表中已添加该任务类记录，不能多次添加同一任务类！';
					$.topCall.error("错误提示",msg); 
				}
			}
		})
	}
}
</script>
</head>
<body class="easyui-layout">
		<div data-options="region:'north',border:false" style="height:auto;">
			<div class="toolbar-panel">
				<div class="buttons">
					<a href="javaScript:void(0)" class="btn btn-sm btn-primary fa-save">
						<span>保存</span>
					</a>
					<a href="schedulerJobList" class="btn btn-sm btn-primary fa-back">
						<span>返回</span>
					</a>
				</div>
			</div>
			<form id="jobForm" method="post" action="addJob">
				<input type="hidden" name="id" value="${calendarShift.id}" />
				<table class="table-form" cellspacing="0">
					<tr>
						<th>
							任务名:<span class="required">*</span>
						</th>
						<td>
							<input type="text" id="name" name="name" onblur="isExist('jobName')" class="inputText input-width-50" validate="{required:true,maxlength:120}" />
						</td>
					</tr>
					<tr>
						<th>任务类:</th>
						<td>
							<input type="text" id="className" name="className" onblur="isExist('className')" value="" class="inputText input-width-50" />
							<a href="javaScript:void(0)" class="btn btn-sm btn-info" title="验证任务类名是否正确" onclick="validClass();">验证</a>
							<input id="parameterJson" name="parameterJson" type="hidden" />
							例如：com.hotent.mini.job.MyJob
						</td>
					</tr>
					<tr>
						<th>任务描述:</th>
						<td>
							<textarea id="description" name="description" class="inputText input-width-50" rows="5"></textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>	
		
		<div data-options="region:'center',border:false">
			<div id="columnsDiv" title="任务参数" style="height: 100%;overflow: scroll;">
				
				<table cellpadding="0" cellspacing="1" class="table-list">
					<thead>
						<tr>
							<td colspan="4">
								对象属性管理：
								<a class="btn btn-sm btn-primary fa fa-add" href="javaScript:void(0)" onclick="addRow();">添加</a>
							</td>
						</tr>
					</thead>
					<tr>
						<th >参数名</th>
						<th >参数类型</th>
						<th >参数值</th>
						<th >删除</th>
					</tr>
					<tbody id="trContainer">
					</tbody>
				</table>
			</div>
		</div>
</body>
</html>