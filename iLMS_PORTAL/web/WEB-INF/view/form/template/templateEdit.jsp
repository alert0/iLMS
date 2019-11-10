<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/edit.jsp"%>

<link rel="stylesheet"
	href="${ctx}/js/common/directive/codemirror/lang/codemirror.css" />
<script type="text/javascript"
	src="${ctx}/js/common/directive/codemirror/lang/codemirror.js"></script>
<%-- <script type="text/javascript" src="${ctx}/js/common/directive/codemirror/lang/javascript.js"></script> --%>
<script type="text/javascript"
	src="${ctx}/js/common/directive/codemirror/lang/xml.js"></script>
<%-- <script type="text/javascript" src="${ctx}/js/lib/codemirror/mode/htmlembedded/htmlembedded.js"></script> --%>
<script type="text/javascript"
	src="${ctx}/js/lib/javacode/InitMirror.js"></script>

<script type="text/javascript"
	src="${ctx}/js/common/util/chineseToPinyin.js"></script>
<script type="text/javascript">
    var templateTypeVal='';
	$(function() {
		var frm = $('#bpmFormTemplateForm').form();
		$(".buttons>a.fa-save").click(function() {
			frm.ajaxForm({
				success : showResponse
			});
			if ($('#alias').isNgModelValid()&&frm.valid()) {
				InitMirror.save();
				$('[name="templateType"]').val(templateTypeVal);
				$('#bpmFormTemplateForm').submit();
			}
		});
		var templateType = '${bpmFormTemplate.templateType}';
		if(templateType){
			if(templateType=='macro'||templateType=='mobileMacro'){
				$('#macroType').css('display', '');
				$('#macro').css('display', 'none');
				templateTypeVal=templateType;
				$('[name="macrotemplateAlias"]').val(null);
			}else if(templateType=='dataTemplate'||templateType=='queryDataTemplate'){
				$('#macroType').css('display', 'none');
				$('#macro').css('display', 'none');
				$('[name="macrotemplateAlias"]').val(null);
			}
		}
		var htmldiv = $('#htmldiv');
		$(htmldiv).width(htmldiv.parent().width()*0.8);
    });

   

    function showResponse(responseText){
		var resultMessage = new com.hotent.form.ResultMessage(responseText);
		if (resultMessage.isSuccess()) {
		    $.topCall.confirm("温馨提示", resultMessage.getMessage() + ',是否继续操作', function(r){
			if (r) {
			    window.location.reload(true);
			} else {
				HT.window.closeEdit(true,'grid');
			}
		    });
		} else {
		    $.topCall.error(resultMessage.getMessage(), resultMessage.getCause());
		}
    }
    function show(templateType){
		if (templateType == 'main' || templateType == 'subTable') {
		    $('#macro').css('display', '');
		    $('#macroType').css('display', 'none');
		    templateTypeVal=templateType;
		} else if(templateType=='dataTemplate'||templateType=='queryDataTemplate'){
			$('[name="macrotemplateAlias"]').val(null);
			$('#macroType').css('display', 'none');
			$('#macro').css('display', 'none');
			 templateTypeVal=templateType;
		}else{
			$('#macro').css('display', 'none');
		    $('#macroType').css('display', '');
		    $('[name="macrotemplateAlias"]').val(null);
		    templateTypeVal=$('#macroTypeSet').val();
		}
    }

    function changeDesc(){
		$('#macroDesc').text($('#macroDescs').val($('#selectMacro').val()).find('option:selected').text());
	}
    function checkAliasIsExist(){
		var url = __ctx + "/form/template/checkAliasIsExist";
		var alias = $('#alias').val();
		var templateId =  $('#templateId').val();
		if (!alias) {
		    $('#alias').next("label.wrong").remove();
		    return;
		}
		if (templateId) {//如果编辑页面则不需要验证
		    $('#alias').next("label.wrong").remove();
		    return;
		}
		$.post(url,{alias : alias}, function(data){
			if (data) {
				$('#alias').qtipError("别名已经存在");
			}else
				$('#alias').qtipSuccess();
		});
    }
    function setAliasVal(obj){
		$('#alias').val($('#alias').val() || ChineseToPinyin(
		{
		    Chinese : $(obj).val()
		}));
			checkAliasIsExist();
	    }
    
    function setMacroType(){
    	 templateTypeVal=templateType=$('#macroTypeSet').val();
    }
</script>
</head>
<body>
	<div class="toolbar-panel">
		<div class="buttons">
			<a href="javascript:;" class="btn btn-sm btn-primary fa-save"><span>保存</span></a>
			<button onclick="HT.window.closeEdit(true,'grid');"
				class="btn btn-primary btn-sm fa-close">
				<span>关闭</span>
			</button>
		</div>
	</div>
	<form id="bpmFormTemplateForm" action="save" method="post">

		<table cellspacing="0" class=table-form>
			<tr>
				<th><span>模板名称:</span></th>
				<td><input type="text" id="templateName" name="templateName"
					value="${bpmFormTemplate.templateName}"
					class="inputText input-width-80"
					validate="{required:true,maxlength:600}" onBlur="setAliasVal(this)" />
				</td>
			</tr>
			<tr>
				<th><span>别名:</span></th>
				<td><input type="text" id="alias" name="alias"
					value="${bpmFormTemplate.alias}" class="inputText input-width-80"
					<c:if test="${not empty bpmFormTemplate.templateId}"  >readonly="readonly"</c:if>
					validate="{required:true,maxlength:150}"
					onBlur="checkAliasIsExist()" /></td>
			</tr>
			<tr>
				<th><span>模板描述:</span></th>
				<td><textarea id="templateDesc" name="templateDesc"
						class="inputText input-width-80">${bpmFormTemplate.templateDesc}</textarea>
				</td>
			</tr>
			<tr>
				<th><span>模板类型:</span></th>
				<td><label class="radio-inline"> <input type="radio" name="templateType"
						value="main" onClick="show('main')"
						<c:if test="${bpmFormTemplate.templateType=='main'||bpmFormTemplate.templateType==null }"> checked="checked"</c:if> />
						主表模板
				</label> <label class="radio-inline"> <input type="radio" name="templateType"
						value="subTable" onClick="show('subTable')"
						<c:if test="${bpmFormTemplate.templateType=='subTable' }"> checked="checked"</c:if> />
						子表模板
				</label> <label class="radio-inline"> <input type="radio" name="templateType" onClick="show('macro')"
						value="macro" <c:if test="${bpmFormTemplate.templateType=='macro'||bpmFormTemplate.templateType=='mobileMacro' }"> checked="checked"</c:if> />
						宏模板
				</label><label class="radio-inline"> <input type="radio" name="templateType" onClick="show('dataTemplate')"
						value="dataTemplate" <c:if test="${bpmFormTemplate.templateType=='dataTemplate'}"> checked="checked"</c:if> />
						业务数据模板
				</label><label class="radio-inline"> <input type="radio" name="templateType" onClick="show('queryDataTemplate')"
						value="queryDataTemplate" <c:if test="${bpmFormTemplate.templateType=='queryDataTemplate'}"> checked="checked"</c:if> />
						自定义sql查询模板
				</label></td>
			</tr>
			<tr id="macro">
				<th><span>控件宏:</span></th>
				<td><select name="macrotemplateAlias" id="selectMacro"
					class="inputText input-width-80">
						<c:forEach items="${macroTemplates}" var="template">
							<option value="${template.alias}"
								<c:if test="${bpmFormTemplate.macrotemplateAlias == template.alias}">selected</c:if>>${template.templateName}</option>
						</c:forEach>
				</select></td>
			</tr>
			
			<tr id="macroType" style="display: none;">
				<th><span>宏模板使用对象:</span></th>
				<td><select name="macroTypeSet" id="macroTypeSet"
					class="inputText input-width-80" onchange="setMacroType()">
						<option value="macro" <c:if test="${bpmFormTemplate.templateType == 'macro'}">selected</c:if>>PC端</option>
						<option value="mobileMacro" <c:if test="${bpmFormTemplate.templateType == 'mobileMacro'}">selected</c:if>>手机端</option>
				</select></td>
			</tr>

			<tr>
				<th><span>模板HTML:</span></th>
				<td>
					<div id="htmldiv" style="border: 1px solid #cccccc;">
						<!-- <input id="html" name="html" type="hidden" /> -->
						<textarea id="html" name="html" codemirror="true" mode="text/html" cols=80 rows=20
							mirrorwidth="400" mirrorheight="350px">${fn:escapeXml(bpmFormTemplate.html)}</textarea>
					</div>
				</td>
			</tr>
			<input type="hidden" id="templateId" name="templateId"
				value="${bpmFormTemplate.templateId}" />
		</table>

	</form>


	<select id="macroDescs" style="display: none">
		<c:forEach items="${macroTemplates}" var="template">
			<option value="${template.alias}">${template.templateDesc}</option>
		</c:forEach>
	</select>
</body>
</html>