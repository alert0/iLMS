<%@page import="com.hotent.bpmx.api.constant.NodeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/include/edit.jsp"%>
<script type="text/javascript" src="${ctx}/js/common/util/chineseToPinyin.js"></script>
<script type="text/javascript">
	$(function() {
		$("a.link[iconCls='icon-save']").click(function() {
		});

		$("#name").blur(function() {
			if ($("#varKey").val())
				return;
			var obj = $(this);
			var str = ChineseToPinyin({
				Chinese : obj.val()
			});
			$("#varKey").val(str);
		});
	});

	function saveData() {
		var value = $("[name='dataType']").val();
		var defaultVal = $("[name='defaultVal']").val();
		if (defaultVal == "null") {
			$.topCall.alert("提示", "默认值不能为null");
			return false;
		}
		if (defaultVal != "" && value != "string" && isNaN(defaultVal)) {//剩下的都是数字了
			$.topCall.alert("提示", "默认值只能是数字");
			return false;
		}
		var frm = $('#bpmDefinitionForm').form();
		if (!frm.valid())
			return;
		frm.ajaxForm({
			success : showResponse,
			data : {
				isSave : true
			}
		});
		$('#bpmDefinitionForm').submit();
	}
	function showResponse(responseText) {
		var resultMessage = new com.hotent.form.ResultMessage(responseText);
		if (resultMessage.isSuccess()) {
			window.parentWindow.reload_();
			$.topCall.success(resultMessage.getMessage(), resultMessage.getMessage());
			window.selfDialog.dialog("close");
		} else {
			$.topCall.errorStack(resultMessage.getMessage(), resultMessage.getCause());
		}
	}
</script>
</head>
<body>
	<c:set value="<%=NodeType.USERTASK%>" var="userTask"></c:set>
	<c:set value="<%=NodeType.SIGNTASK%>" var="signTask"></c:set>
	<form id="bpmDefinitionForm" action="save" method="post">
		<table class="table-form" cellspacing="0">
			<tr>
				<th>
					<span>节点:</span>
					<span class="required">*</span>
				</th>
				<td>
					<select name="nodeId" class="inputText">
						<option value="">全局变量</option>
						<c:forEach items="${nodeDefList}" var="node">
							<c:if test="${node.type == userTask || node.type == userTask}">
								<option value="${node.nodeId}" <c:if test="${node.nodeId eq bpmVariableDef.nodeId }">selected="selected"</c:if>>${node.name}(${node.nodeId})</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span>变量名:</span>
					<span class="required">*</span>
				</th>
				<td>
					<input type="text" id="name" name="name" value="${bpmVariableDef.name}" class="inputText" validate="{required:true,maxlength:192}" />
				</td>
			</tr>
			<tr>
				<th>
					<span>变量Key:</span>
					<span class="required">*</span>
				</th>
				<td>
					<input type="text" id="varKey" name="varKey" value="${bpmVariableDef.varKey}" <c:if test="${!empty bpmVariableDef}">readonly="readonly"</c:if> class="inputText" validate="{required:true,maxlength:192}" />
					(唯一)
				</td>
			</tr>
			<tr>
				<th>
					<span>数据类型:</span>
				</th>
				<td>
					<select name="dataType" class="inputText">
						<option value="string" <c:if test="${bpmVariableDef.dataType eq 'string'}">selected="selected"</c:if>>字符串</option>
						<option value="int" <c:if test="${bpmVariableDef.dataType eq 'int'}">selected="selected"</c:if>>整形</option>
						<option value="float" <c:if test="${bpmVariableDef.dataType eq 'float'}">selected="selected"</c:if>>浮点型</option>
						<option value="double" <c:if test="${bpmVariableDef.dataType eq 'double'}">selected="selected"</c:if>>双精度</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span>是否必须:</span>
				</th>
				<td>
					<select id="isRequired" name="isRequired" class="inputText">
						<option value="true" <c:if test="${bpmVariableDef.required}">selected="selected"</c:if>>是</option>
						<option value="false" <c:if test="${!bpmVariableDef.required}">selected="selected"</c:if>>否</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span>缺省值:</span>
				</th>
				<td>
					<input type="text" id="defaultVal" name="defaultVal" value="${bpmVariableDef.defaultVal}" class="inputText" validate="{maxlength:192}" />
				</td>
			</tr>
			<tr>
				<th>
					<span>变量描述:</span>
				</th>
				<td>
					<input type="text" id="description" name="description" value="${bpmVariableDef.description}" class="inputText" validate="{maxlength:120}" />
					<input type="hidden" name="defId" value="${defId}">
					<input type="hidden" name="isAdd" value="<c:if test="${empty bpmVariableDef}">true</c:if>">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>