<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>设置分支条件</title>
<%@include file="/commons/include/edit.jsp"%>
<%@include file="/commons/include/zTree.jsp" %>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/conditionScriptDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/javacode/codemirror.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/javacode/InitMirror.js"></script>
<style type="text/css">
body {
	overflow-x: hidden; overflow-y: auto;
}
</style>
<script type="text/javascript">
    $(function(){
	    $("a[name='signResult']").click(function(){
	    addToTextarea($(this).attr("result"));
	    });
		varTree = new ZtreeCreator('varTree', "${ctx}/flow/node/varTree").setDataKey({
			name : 'desc'
		}).setCallback({
			onClick : setVariable
		}).makeCombTree("tempTree").initZtree({
			defId : '${defId}',
			nodeId : '${bpmNodeDef.nodeId}',
			parentFlowKey:'${parentFlowKey}',
			includeBpmConstants : true
		}, 1);

		$(".varTree").bind("click", varTree.showMenu);
    });
    function setVariable(event, treeId, node) {
		var keyStr = node.name;
		var parentNode = node.getParentNode();
		var parentNode2 = node.getParentNode();
		var boDefAlias = parentNode2.boDefAlias;
		while(parentNode2 && !parentNode2.boDefAlias){
			parentNode2 = parentNode2.getParentNode();
			if(!parentNode2){
				break;
			}
			boDefAlias = parentNode2.boDefAlias;
		}
		
		// 子表情况做提示
		 if (node.nodeType == 'sub'){
			keyStr = boDefAlias+".getSubByKey('"+node.name+"') /* 获取子表,return List<BoData> */";
		 }// 主表bo
		 else if(parentNode.nodeType == 'main'){
			keyStr = boDefAlias+'.getValByKey("'+node.name+'") /*数据类型：'+node.dataType+'*/';
		}else if(parentNode.nodeType == 'sub'){
			var mainTableName = boDefAlias;
			keyStr = mainTableName+'.getSubByKey("'+parentNode.name+'") /*获取子表数据 ，返回数据：return List<BoData> 。子表字段：'+node.name+', 请根据实际情况处理子表数据的获取*/';
		}else if(node.nodeType == 'var'){
			keyStr =node.name;
		}else return ;
		varTree.hideMenu();
		InitMirror.editor.insertCode(keyStr);
	}
    function handFlowVars(obj, txtId){
		var val = $(obj).val();
		InitMirror.editor.insertCode(val);
    }

    function selectScript(){
		ConditionScript.showDialog(
		{
		    defId : '${defId}', nodeId : '${bpmNodeDef.nodeId}'
		}, function(data){
			InitMirror.editor.insertCode(data.script);
		});
    }
    //将条件表达式追加到脚本输入框内
    function addToTextarea(str){
		InitMirror.editor.insertCode(str);
    };
    function handFlowVars(obj){
		addToTextarea($(obj).val());
    };
    function saveCondition(){
		InitMirror.save();
		var condition = {};
		$("textarea[name='condition']").each(function(){
		    var conditionValue = $(this).val();
		    var nodeId = $(this).prev().val();
		    condition[nodeId] = conditionValue;
		});
	
		var url = __ctx + "/flow/node/branchConditionSave";
		var paras ={ defId : '${defId}', nodeId : '${bpmNodeDef.nodeId}', condition : JSON.stringify(condition)};
		$.post(url, paras, function(data){
		    var resultMessage = new com.hotent.form.ResultMessage(data);
		    if (resultMessage.isSuccess()) {
				$.topCall.confirm("提示 信息", "分支条件设置成功，是否继续?", function(result){
					window.passConf();
				    if (result) {
				    } else {
					window.selfDialog.dialog('close')
				    }
				});
		    } else {
				$.topCall.error("分支条件设置失败!");
		    }
		});
    };
</script>
</head>
<body>
	<div class="toolbar-panel">
		<div class="buttons">
			<button class="btn btn-primary btn-sm fa-save" onclick="saveCondition()">保存</button>
			<button class="btn btn-primary btn-sm fa-close" onclick="javascript:window.selfDialog.dialog('close')">取消</button>
		</div>
	</div>
	<div id="tempTree"></div>
	<table class="table-form" >
		<tr>
			<th>
				<span>条件表达式</span>
			</th>
			<td>
				<div style="margin: 8px 0;">
					<a class="btn btn-primary btn-xs" title="条件脚本" onclick="selectScript()">条件脚本</a> 
					<a class="varTree btn btn-primary btn-xs">可选变量</a>
				 
				</div>
				<c:forEach items="${bpmNodeDef.incomeNodes}" var="inNode">
					<div style="padding: 4px;">
						<c:choose>
							<c:when test="${inNode.type=='SIGNTASK'}">
								<a href="javascript:void(0);" name="signResult" result='signResult_${inNode.nodeId}.equals("agree")'>[${inNode.name}]投票通过</a>
								<a href="javascript:void(0);" name="signResult" result='signResult_${inNode.nodeId}.equals("oppose")'>[${inNode.name}]投票不通过</a>
								</br>
							</c:when>
							<c:otherwise>
								<a href="javascript:void(0);" name="signResult" result='taskCmd.getActionName().equals("agree")'>[${inNode.name}]-通过</a>
								<a href="javascript:void(0);" name="signResult" result='taskCmd.getActionName().equals("oppose")'>[${inNode.name}]-反对</a>
							</c:otherwise>
						</c:choose>

						<br> 选择流程变量的时候，请注意光标位置。
					</div>
				</c:forEach>
			</td>
		</tr>
		<c:forEach items="${bpmNodeDef.outcomeNodes}" var="outNode">
			<tr>
				<th width="20%">
					<span>${outNode.name}</span>
				</th>
				<td>
					<input type="hidden" name="task" value="${outNode.nodeId}" />
					<textarea id="${outNode.nodeId}" codemirror="true" mirrorheight="110px" name="condition" rows="3" cols="20" class="inputText">${bpmNodeDef.conditions[outNode.nodeId]}</textarea>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
