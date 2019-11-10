<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
<f:link href="edit.css"></f:link>
<script type="text/javascript">
var isimport = false;
$(function() {
	
	$.post(__ctx+"/org/sysDemension/listJson",function(result){
		var demList = result.rows;
		var optionStr = "<option value=''>----请选择---</option>";
		for(var i=0;i<demList.length;i++){
			var val = demList[i].id;
			var name =demList[i].demName;
			optionStr += "<option value='"+val+"'  >"+name+"</option>"
		}
		$("#demId").find("option").remove();
		$("#demId").append(optionStr);
	});
	
	var frm = $('#importForm').form();
	$(".fa-save").click(function() {
		var file = $("#file").val();
		var demId = $("#demId").val();
		var preCode = $("#preCode").val();
		if(demId == ""){
			$.topCall.warn("请选组织维度");
			return;
		}
		if (file == "") {
			$.topCall.warn("请选择文件");
			return;
		}
		frm.ajaxForm({
			success : showResponse
		});
		$.topCall.progress();
		$('#importForm').attr("action","importUser");
		$('#importForm').submit();
	});
});

function showResponse(responseText) {
	var resultMessage = new com.hotent.form.ResultMessage(responseText);
	var msg = JSON.parse(resultMessage.getMessage());
	var showMsg = msg['log']==null||msg['log']==''?msg['console']:msg['log'];
	showMsg = showMsg==null||showMsg==''?"用户导入成功！":showMsg;
	if (resultMessage.isSuccess()) {
		$.topCall.closeProgress();
		$.topCall.success(showMsg, function() {
			isimport = true;
			closeCallBack();
		});
	}else if (resultMessage.getResult() == 0) {
		$.topCall.closeProgress();
		$.topCall.error(showMsg);
	} else {
		$.topCall.closeProgress();
		$.topCall.confirm("提示",showMsg,
			function(r) {
				if (!r) return;
				$('#importForm').attr("action","importUser");
				$('#importForm').submit();
			}
		);
	}
}

//处理导入成功后，关闭页面时，刷新相关列表
function closeCallBack(){
	selfDialog.dialog('close');
	if(isimport){
		parentWindow.loadGrid()
	}
}
</script>
</head>
<body >
	<!-- 顶部按钮 -->
	<div class="toolbar-panel">
		<div class="buttons">
			<a class="btn btn-sm btn-primary fa-save" ><span>导入</span></a> 
			<a href="#" class="btn btn-default btn-sm fa-close" onclick="closeCallBack()">
				<span>关闭</span>
			</a>
		</div>
	</div>
	<form style="margin-top: 10px;" id="importForm" action="importUser" method="post" enctype="multipart/form-data">
		<table class="table-form" cellspacing="0">
			<tr>
					<th>
						<span>组织维度:</span>
					</th>
					<td>
						<select class="inputText input-wh-9"   name="demId" id="demId" validate="{required:true}" >
							<option></option>
						</select>
					</td>
			</tr>
			<tr>
				<th>
					<span>选择文件:</span>
				</th>
				<td>
					<input type="file" size="40" name="file" id="file" accept=".xls,.xlsx" class="inputText input-wh-9" />
				</td>
			</tr>
			<tr>
				<th colspan="2" style="text-align: left;padding: 8px;">
					<span style="color: red;">导入须知:</span>
					<span>
						导入数据需按模板指定格式进行导入：1、姓名、帐号为必填字段，组织名称列以“/”开头，下级组织同样用“/”分隔；2、岗位和职务导入规则：一一对应导入，多个用;号隔开，如果没有职务，则不导入岗位，如果有职务，则对应顺序以岗位为准（第一个岗位对应第一个职务，以此类推）(如果岗位有多个，职务只有一个时，导入同一个职务中)；
						3、邮箱格式必须符合标准，将不导入不符合规范的邮箱数据；3、导入后提示信息只说明错误信息和未导入的信息，导入的数据日志记录在WEB-INF/logs下的x5.log中；4、“编码前缀”说明：编码前缀用于生成组织编码的前缀，区分不同组织维度（使得相同的组织架构可以导入到多个不同的组织维度下），不同组织维度导入时需要输入不同的前缀编码，
						相同的组织维度，输入相同的组织维度编码。
					</span>
				</th>
			</tr>
		</table>
	</form>
</body>
</html>