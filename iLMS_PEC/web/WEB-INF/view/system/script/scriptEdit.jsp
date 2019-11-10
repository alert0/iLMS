<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/edit.jsp"%>
<script type="text/javascript" src="${ctx}/js/common/util/dialogUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/javacode/codemirror.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/javacode/InitMirror.js"></script>

 
<script type="text/javascript">
    $(function(){
		var frm = $('#scriptForm').form();
		$(".fa-save").click(function(){
			InitMirror.save();
		    frm.ajaxForm({success : showResponse});
		    if (frm.valid()) {
			  $('#scriptForm').submit();
		    }
		});
    });

    function showResponse(responseText){
		var resultMessage = new com.hotent.form.ResultMessage(responseText);
		if (resultMessage.isSuccess()) {
		    $.topCall.confirm("温馨提示", resultMessage.getMessage() + ',是否继续操作', function(r){
		    	if(r){
					window.location.reload(true);
				}else{
					HT.window.refreshParentGrid();
					HT.window.closeEdit(true,'grid');
				}
		    });
		} else {
		    $.topCall.error(resultMessage.getMessage());
		}
    }
    function selectCategory(obj){
		var selObj = $(obj);
		$("#category").val(selObj.val());
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
			<button class="btn btn-sm btn-primary fa-save">
				<span>保存</span>
			</button>
			<a href="javascript:;" onclick="closeUserGridList();" class="btn btn-primary btn-sm fa-back ">
	                <span>关闭</span>
	            </a>
	           
	             <a href="javascript:void(0)" onclick="buildScript()" class="btn btn-primary btn-sm fa-cog ">
	                <span>测试脚本</span>
	            </a>
		</div>
	</div>

	<form id="scriptForm" action="save" method="post">
		<input type="hidden" name="id" value="${script.id}" />
		<table class="table-form" cellspacing="0">
			<tr>
				<th>名称:</th>
				<td>
					<input type="text" id="name" name="name" value="${script.name}" class="inputText" />
				</td>
			</tr>
			<tr>
				<th>脚本:</th>
				<td>
					<div >
						 <textarea mode="text/x-groovy" codemirror="true" mirrorheight="200px" id="script" rows="12" cols="55" name="script" validate="{required:true}">${fn:escapeXml(script.script)}</textarea>
					</div>
				</td>
			</tr>
			<tr>
				<th>脚本分类:</th>
				<td>
					<input id="category" name="category" value="${script.category}" class="inputText" /> <select onChange="selectCategory(this)" class="inputText">
						<option value="">全部</option>
						<c:forEach items="${categoryList}" var="catName">
							<option value="${catName}">${catName}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>备注:</th>
				<td>
					<textarea id="memo" name="memo" rows="5" cols="80" class="inputText input-width-50">${script.memo}</textarea>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
<script>
function preViewScript(){
	InitMirror.save();
	var script=$("#script").val();
	if(script==""){
		   $.topCall.error("请编写脚本内容");
		return false;
	}
	var parm={script:script,key:$("#name").val()};
	var data=Object.toAjaxJson(__ctx+"/system/script/executeScript", "", parm)
	 $.topCall.alert("执行结果","执行结果："+data.val);
	 
}
function buildScript(){
	DialogUtil.openDialog(__ctx+"/system/script/scriptTreeDesign", "脚本测试", null, function(data, dialog){
		InitMirror.save();
		var content=$("#script").val()+data;
		InitMirror.editor.setCode(content);
		dialog.dialog('close');
	}, 750, 450);
}
</script>