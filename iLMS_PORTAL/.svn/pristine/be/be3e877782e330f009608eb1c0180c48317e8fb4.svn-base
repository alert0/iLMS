<%@page import="com.hotent.bpmx.api.constant.ScriptType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>脚本节点</title>
<%@include file="/commons/include/list.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/conditionScriptDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/javacode/codemirror.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/scriptSelector.js"></script>

<script type="text/javascript">
	var scriptJson = '${bpmPluginDefJson}';
	var varTree;
	$(function(){
		if(scriptJson) {
			scriptJson = parseToJson(scriptJson);
		}else scriptJson = {"script":"return ;"};
		$("#script").val(scriptJson.script);
		
		varTree = new ZtreeCreator('varTree',"${ctx}/flow/node/varTree") 
			.setDataKey({name:'desc'})
	     	.setCallback({onClick:setVariable})
	     	.makeCombTree("showTreeBtn") 
	     	.initZtree({defId:'${defId}',nodeId:'${nodeId}',includeBpmConstants:true},1); 
		
	});
	
	function setVariable(event, treeId, node){
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
	
	function saveScript() {
		InitMirror.save();
		scriptJson.pluginType = '${pluginType}';
		scriptJson.script = $("#script").val();
		param = {
					defId:'${defId}',nodeId:'${nodeId}',
					jsonStr:JSON.stringify(scriptJson)
				};
		var url = __ctx + "/flow/node/autoTaskPluginSave"
		
		$.post(url, param,function(data){
			var resultMessage=new com.hotent.form.ResultMessage(data);
        	if(resultMessage.isSuccess()){
        		$.topCall.success("脚本节点配置成功!"); 
        		window.parent.passConf();
        	}else{
        		$.topCall.error(resultMessage.getMessage(),resultMessage.getCause());
        	}
		});
	}
	
	function selectConditionScript(){
		   ConditionScript.showDialog({defId:'${defId}',nodeId:'${bpmNodeDef.nodeId}'},function(data){ 
			 InitMirror.editor.insertCode(data);
		  }); 
		}
	
	function selectScript(){
		new ScriptSelector(function(script){
			InitMirror.editor.insertCode(script);
		 }).show();
	}
</script>
<script type="text/javascript" src="${ctx}/js/lib/javacode/InitMirror.js"></script>
</head>
<body>
	<div class="toolbar-panel col-md-13 ">
		<div class="buttons">
			 <a href="javascript:;" class="btn btn-primary btn-sm fa-save" onclick="saveScript()">1保存</a>
			 <a href="javascript:;" class="btn btn-primary  btn-sm fa-back"  onclick="javasrcipt:parent.selfDialog.dialog('close');">取消</a>
		</div> 
	</div>
    <table class="table-form">
		<tr>
			<th width="20%"><span>脚本描述</span> </th>
			<td>
				这个在脚本任务触发时执行，用户可以使用<span class="red">execution</span>做操作。
				 例如设置流程变量:execution.setVariable("total", 100);
			</td>
		</tr>
		<tr>
			<th width="20%"><span>脚本:</span></th>
			<td><div>
				 <a class="btn btn-primary btn-xs" onclick="selectScript()">常用脚本</a>
				 <a class="btn btn-primary btn-xs" onclick="selectConditionScript()">条件脚本</a>
				 <a id="showTreeBtn" class="btn btn-primary btn-xs">可选变量</a> 
				 </div>
				 <textarea name="script" id="script" codemirror="true" mirrorheight="200px" rows="10" cols="80" ></textarea>
			</td>
		</tr>
    </table> 
</body>
</html>
						