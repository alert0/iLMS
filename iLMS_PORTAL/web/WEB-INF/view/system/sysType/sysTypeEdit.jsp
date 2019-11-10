<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/edit.jsp"%>
<script type="text/javascript" src="${ctx}/js/common/util/chineseToPinyin.js"></script>
<script type="text/javascript">
	var isAdd = "${isAdd}";
	$(function() {
		var frm = $('#sysTypeForm');
		$("a.btn.fa-save").click(function() {
			if($("#sysTypeForm").form().valid()){
				frm.ajaxForm({
					success : showResponse
				});
				$('#sysTypeForm').submit();
			}
		});

		$("#name").blur(function() {
			if ($("#typeKey").val())
				return;
			var obj = $(this);
			var str = ChineseToPinyin({
				Chinese : obj.val()
			});
			$("#typeKey").val(str);
		});
	});

	function showResponse(responseText) {
		var rtn = new com.hotent.form.ResultMessage(responseText);
		if (rtn.isSuccess()) {
			//在对话框中
			if(window.parentWindow){
				window.parentWindow.refreshNode();
			}else{
				parent.refreshNode();
			}
			
			$.topCall.confirm('提示', rtn.getMessage(), function(rtn) {
				if (rtn) {
					var id = $('[name="id"]').val();
					if(!id){
						$("#typeKey").val("");
						$("#name").val("");
					} 
				}  
				else{
					if(window.selfDialog){
						window.selfDialog.dialog('close');
					}
				}
			});
		} else {
			$.topCall.error(rtn.getMessage(), rtn.getCause());
		}
	}
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" fit="true">
	<!-- 顶部标题 -->
	<c:if test="${!isDict}">
		<div class="panel-header" style="width:100%;">
		<c:if test="${!isAdd}">
			<div class="panel-title">编辑分类</div>
		</c:if>
		<c:if test="${isAdd}">
			<div class="panel-title">添加分类</div>
		</c:if>
	</div>
	</c:if>
	<!-- 顶部按钮 -->
	<div class="toolbar-panel col-md-13 ">
		<div class="buttons">
			<a role="button" class="btn btn-primary  btn-sm fa-save">
				<span>保存</span>
			</a>
			<c:choose>
				<c:when test="${fn:contains(returnUrl,'platform/org/user/list.ht')}">
					<a href="${returnUrl}" class="btn btn-primary btn-sm fa-back">
						<span>返回</span>
					</a>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<form id="sysTypeForm" action="save.ht" method="post">
		<table class="table-form" cellspacing="0">
			<%-- <c:choose>
				<c:when test="${isPriNode==0}">
					<th>
						<span>类型:</span>
					</th>
					<td>普通节点</td>
				</c:when>
				<c:otherwise>
					<th>
						<span>类型:</span>
					</th>
					<td>私有节点</td>
				</c:otherwise>
			</c:choose> --%>

			<c:if test="${isAdd}">
				<tr>
					<th>
						<span>父节点:</span>
					</th>
					<td>${parentName}</td>
				</tr>
			</c:if>
			<tr>
				<th>
					<span>分类名称:</span>
					<span class="required">*</span>
				</th>
				<td>
					<input type="text" id="name" name="name" value="${sysType.name}" class="inputText" validate="{required:true,maxlength:30}" />
				</td>
			</tr>
			<tr>
				<th>
					<span>分类Key:</span>
					<span class="required">*</span>
				</th>
				<td>
					<input type="text" id="typeKey" name="typeKey" value="${sysType.typeKey}" class="inputText" validate="{required:true,maxlength:30}" />
				</td>
			</tr>
			<c:if test="${isDict}">
				<tr>
					<th>
						<span>字典项类型:</span>
					</th>
					<td>
						<label class="radio-inline">
							<input <c:if test="${not empty sysType.id}">disabled="disabled"</c:if> type="radio" name="struType" value="0" <c:if test="${sysType.struType==0}">checked="checked"</c:if> />
							平铺
						</label>
						<label class="radio-inline">
							<input <c:if test="${not empty sysType.id}">disabled="disabled"</c:if> type="radio" name="struType" value="1" <c:if test="${sysType.struType==1}">checked="checked"</c:if> />
							树形
						</label>
					</td>
				</tr>
			</c:if>
			<input type="hidden" id="isRoot" name="isRoot" value="${isRoot}" />
			<input type="hidden" id="parentId" name="parentId" value="${parentId}" />
			<input type="hidden" id="isPriNode" name="isPriNode" value="${isPriNode}" />
			<input type="hidden" id="path" name="path" value="${sysType.path}" />

			<input type="hidden" id="ownerId" name="ownerId" value="${sysType.ownerId}" />
			<input type="hidden" name="id" value="${sysType.id}" />
			<input type="hidden" id="typeGroupKey" name="typeGroupKey" value="${sysType.typeGroupKey}" />
			<input type="hidden" name="struType" value="${sysType.struType}" />
		</table>
	</form>
</body>
</html>