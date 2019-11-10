<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/edit.jsp" %>
		<script type="text/javascript">
			var id="${param.id}";
		</script>
		<script type="text/javascript" src="${ctx}/js/platform/system/scriptEdit/scriptEdit.js"></script>
		<script type="text/javascript">
		$(function(){
			var type="${param.type}";
			if(type!="")$("#type").val(type);
			var frm = $('#conditionScriptForm').form();
			$("a.btn.fa-save").click(function() {
				frm.ajaxForm({success:showResponse});
				if (frm.valid()) {
					save('conditionScriptForm');
				}
			});
			$("select[name='className']").change(classNameChange).trigger("change");
			$("select[name='methodName']").change(methodChange);
		});
		
		//类名选择事件
		function classNameChange(){
			var className = $(this).val();
			if(!className)return;
			
			var match = /^.*\.(\w*)$/.exec(className),
				name = '';
			if(match!=null){
				name = match[1];
			}
			if(!name)return;
			name = name.toLowerCase().split("",1) + name.slice(1);
			$("input[name='classInsName']").val(name);
			var url = __ctx + '/system/conditionScript/getMethodsByClassName?className='+className+"&id=${param.id}&type=${param.type}";
			getMethods(url);
		};

		function showResponse(responseText) {
			var resultMessage=new com.hotent.form.ResultMessage(responseText);
			if(resultMessage.isSuccess()){
				$.topCall.confirm("温馨提示",resultMessage.getMessage()+',是否继续操作',function(r){
					if(r){
						window.location.reload(true);
					}else{
						HT.window.refreshParentGrid();
						HT.window.closeEdit(true,'grid');
					}
				});
			}else{
				$.topCall.error(resultMessage.getMessage());
			}
		}
		
		function closeUserGridList(){
			HT.window.closeEdit(true,'userGridList');
			HT.window.refreshParentGrid();
		}
		
		</script>
	</head>
	<body>
		<div class="toolbar-panel">
			<div class="buttons" >
				<a href="javascript:;" class="btn btn-primary btn-sm fa-save" ><span>保存</span></a>
				<a href="javascript:;" onclick="closeUserGridList();" class="btn btn-primary btn-sm fa-back ">
	                <span>关闭</span>
	            </a>
			</div>
		</div>
		
		<form id="conditionScriptForm" action="save" method="post">
			<input type="hidden" name="id" value="${conditionScript.id}" />
			<input type="hidden" id="type" name="type" value="${conditionScript.type}" />
			<textarea class="hidden" name="argument">${conditionScript.argument}</textarea>
			<textarea class="hidden" name="controlBindSourceJson">${controlBindSourceJson}</textarea>
			<input type="hidden" id="methodName" value="${conditionScript.methodName}"/>
			<table class="table-form" cellspacing="0">
				<tr>								
					<th>脚 本 所 在 类 名:</th>
					<td>
						<select name="className" class="inputText input-width-50">
							<c:forEach items="${implClasses}" var="implClass">
								<option value="${implClass.name}"<c:if test="${conditionScript.className eq implClass.name}"> selected="selected"</c:if>>${implClass.name}</option>
							</c:forEach>
						</select>
					</td>								
				</tr>
				<tr>								
					<th>类实例名:</th>
					<td>
						<input type="text" class="inputText" readonly value="${conditionScript.classInsName}" name="classInsName" />
					</td>								
				</tr>
				<tr>								
					<th>方法名称:</th>
					<td>
						<select class="inputText" name="methodName"></select>
					</td>								
				</tr>
				<tr>								
					<th>方法描述:</th>
					<td>
						<input type="text" class="inputText input-width-50" name="methodDesc" value="${conditionScript.methodDesc}"/>
					</td>								
				</tr>
				<tr>								
					<th>返回值类型:</th>
					<td>
						<input type="text" class="inputText" readonly name="returnType" value="${conditionScript.returnType}"/>
					</td>								
				</tr>
				<tr>								
					<th>参数信息:</th>
					<td id="paraInfo"></td>								
				</tr>
				 
				<tr>								
					<th>是否有效:</th>
					<td>
						<select name="enable" class="inputText input-width-20">
							<option value="0"<c:if test="${conditionScript.enable eq 0}"> selected="selected"</c:if>>否</option>
							<option value="1"<c:if test="${conditionScript.enable eq 1}"> selected="selected"</c:if>>是</option>
						</select>
					</td>								
				</tr>
			</table>
		</form>
		<div style="display: none;">
			<div id="para-txt">
				<table class="table-list" cellpadding="0" cellspacing="0" border="0">
					<thead>
						<tr>
							<th >参数信息</th>
							<th >参数类型</th>
							<th >参数说明</th>
							<th >选择器</th>
							<th >绑定字段</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><span name="paraName"></span></td>
							<td><span name="paraType"></span></td>
							<td><input type="text" name="paraDesc" class="inputText"/></td>
							<td>
									<select name="paraCt" onChange="paraCtChange(this)" class="inputText">
										 ${optionXml}
									</select>
							</td>
							<td>
								<select name="paraCtBindField"    class="inputText">
								</select>
								<label class="checkbox-inline"><input type="checkbox" value="1" name="multiSelect" >多选</label>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
	</div>
    
	</body>
</html>