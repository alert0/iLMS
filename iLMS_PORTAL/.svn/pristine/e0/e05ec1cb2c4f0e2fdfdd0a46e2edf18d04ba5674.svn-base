<%@page import="com.hotent.bpmx.api.constant.NodeType"%>
<%@page import="com.hotent.bpmx.api.constant.ScriptType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>流程事件脚本编辑</title>
<%@include file="/commons/include/edit.jsp"%>
<%@include file="/commons/include/zTree.jsp" %>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/scriptSelector.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/conditionScriptDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/javacode/codemirror.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/javacode/InitMirror.js"></script>
<script type="text/javascript">
	function handFlowVars(obj, txtId) {
		var val = $(obj).val();
		InitMirror.editor.insertCode(val);
	}

	function selectConditionScript() {
		ConditionScript.showDialog({
			defId : '${defId}',
			nodeId : '${bpmNodeDef.nodeId}'
		}, function(data) {
			var script="return " + data.script +";";
			InitMirror.editor.insertCode(script);
		});
	}

	function saveScript() {
		InitMirror.editor.save();
		var eventScriptArray = [];
		$("textarea[name='script']").each(function() {
			var scriptType = $(this).prev().val();
			eventScriptArray.push({
				scriptType : scriptType,
				content : $(this).val()
			});
		});
		param = {
			defId : '${defId}',
			nodeId : '${bpmNodeDef.nodeId}',
			eventScriptArray : JSON.stringify(eventScriptArray)
		};

		var url = __ctx + "/flow/node/eventScriptSave";
		$.post(url, param, function(data) {
			var resultMessage = new com.hotent.form.ResultMessage(data);
			if (resultMessage.isSuccess()) {
				window.passConf(eventScriptArray);
				$.topCall.confirm("提示 信息", "节点事件脚本配置成功，是否继续", function(i) {
					if (!i) {
						window.selfDialog.dialog('close');
					}
				});
			} else {
				$.topCall.error("节点事件脚本配置失败!");
			}
		});
	}

	var varTree;
	$(function() {
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
		
		$(document).on("click","ul.tabs li",getCurrentEditor);

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
	function selectScript(){
		new ScriptSelector(function(script){
			InitMirror.editor.insertCode(script);
		 }).show();
	}
	
	function getCurrentEditor(){
		var id = $(".panel:visible").find("[codemirror='true']").attr("id");
		InitMirror.editor =  InitMirror.getById(id);
		InitMirror.editor.replaceSelection(""); 
	}
	
	
</script>
<style type="text/css">
html {
	overflow-y: hidden;
}
</style>
</head>
<body>
	<div class="toolbar-panel">
		<div class="buttons">
			<a href="javascript:void(0);" class="btn btn-primary  fa-save" onclick="saveScript()">保存</a>
			<a href="javascript:void(0);" class="btn btn-primary fa-close" onclick="javascript:window.selfDialog.dialog('close')">取消</a>
		</div>
	</div>
	<div id="tempTree"></div>
	<c:set value="<%=NodeType.START%>" var="start"></c:set>
	<c:set value="<%=NodeType.END%>" var="end"></c:set>
	<c:set value="<%=NodeType.USERTASK%>" var="userTask"></c:set>
	<c:set value="<%=NodeType.SIGNTASK%>" var="singTask"></c:set>

	<c:set value="<%=ScriptType.CREATE%>" var="create"></c:set>
	<c:set value="<%=ScriptType.END%>" var="endd"></c:set>
	<c:set value="<%=ScriptType.COMPLETE%>" var="complete"></c:set>
	<c:set value="<%=ScriptType.START%>" var="startt"></c:set>
	
	<div id="userTab" class="easyui-tabs" style="height: 380px">
		<c:if test="${bpmNodeDef.type.key eq 'start'}">
			<div tabid="startEvent" title="开始事件">
				<table class="table-form">
					<tr>
						<th >
							<span>说明:</span>
						</th>
						<td>
							该脚本在流程启动时执行，用户可以使用
							<span class="red">execution</span>
							做操作。 例如设置流程变量:execution.setVariable("total", 100);
						</td>
					</tr>
					<tr>
						<th >
							<span>脚本:</span>
						</th>
						<td>
							<div>
								<a class="btn btn-primary btn-xs" onclick="selectScript()">常用脚本</a>
								<a class="btn btn-primary btn-xs" onclick="selectConditionScript()">条件脚本</a>
								<a class="varTree btn btn-primary btn-xs">可选变量</a>

							</div>
							<div style="border: 1px solid #D3D3D3;">
								<input type="hidden" value="start" name="scriptType">
								<textarea id="startScript" name="script" codemirror="true" mirrorheight="277px" rows="10" cols="80">${eventScriptMap[startt]}</textarea>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</c:if>
		<c:if test="${bpmNodeDef.type.key eq 'end'}">
			<div tabid="endEvent" title="结束事件">
				<table class="table-form">
					<tr>
						<th>
							<span>脚本描述:</span>
						</th>
						<td>
							该脚本在
							<span class="red">流程结束</span>
							时执行，用户可以使用
							<span class="red">execution</span>
							做操作。 例如设置流程变量:execution.setVariable("total", 100);
						</td>
					</tr>
					<tr>
						<th >
							<span>脚本:</span>
						</th>
						<td>
							<div>
								<a class="btn btn-primary btn-xs" onclick="selectScript()">常用脚本</a>
								<a class="btn btn-primary btn-xs" onclick="selectConditionScript()">条件脚本</a>
								<a class="varTree btn btn-primary btn-xs">可选变量</a>
							</div>
							<input type="hidden" value="end">
							<textarea id="endScript" name="script" codemirror="true" mirrorheight="2770px" rows="10" cols="80">${eventScriptMap[endd]}</textarea>
						</td>
					</tr>
				</table>
			</div>
		</c:if>
		<c:if test="${bpmNodeDef.type.key eq 'userTask' || bpmNodeDef.type.key eq 'signTask'}">
			<div title="前置脚本">
				<div class="table-form">
					<table>
						<tr>
							<th width="20%">
								<span>脚本描述</span>
							</th>
							<td>
								该事件在
								<span class="red">启动该任务</span>
								时执行，用户可以使用
								<span class="red">task</span>
								做操作。 例如设置流程变量:task.setVariable("total", 100);
								<input type="hidden" name="scriptType" value="1" />
							</td>
						</tr>
						<tr>
							<th width="20%">
								<span>脚本:</span>
							</th>
							<td>
								<div>
									<a class="btn btn-primary btn-xs" title="常用脚本" id="asdf" onclick="selectScript()">常用脚本</a>
									<a class="btn btn-primary btn-xs" title="条件脚本" id="asdf" onclick="selectConditionScript()">条件脚本</a>
									<a class="varTree btn btn-primary btn-xs">可选变量</a>
								</div>
								<input type="hidden" value="create">
								<textarea id="preScript" name="script" codemirror="true" mirrorheight="290px" rows="10" cols="80">${eventScriptMap[create]}</textarea>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div title="后置脚本">
				<div class="table-form">
					<table>
						<tr>
							<th width="20%">
								<span>脚本描述</span>
							</th>
							<td>
								该事件在
								<span class="red">任务完成</span>
								时执行，用户可以使用
								<span class="red">task</span>
								做操作。 例如设置流程变量:task.setVariable("total", 100);
								<input type="hidden" name="scriptType" value="2" />
							</td>
						</tr>
						<tr>
							<th width="20%">
								<span>脚本:</span>
							</th>
							<td>
								<div>
									<a class="btn btn-primary btn-xs" onclick="selectScript()">常用脚本</a>
									<a class="btn btn-primary btn-xs" onclick="selectConditionScript()">条件脚本</a>
									<a class="varTree btn btn-primary btn-xs">可选变量</a>
								</div>
								<input type="hidden" value="complete">
								<textarea id="afterScript" name="script" codemirror="true" mirrorheight="290px" rows="10" cols="80">${eventScriptMap[complete]}</textarea>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</c:if>
	</div>
	<div>
		<span class="red">提示：在使用流程常用脚本、变量的时候，请注意光标是否位于脚本编辑框！</span>
	</div>
</body>
</html>
