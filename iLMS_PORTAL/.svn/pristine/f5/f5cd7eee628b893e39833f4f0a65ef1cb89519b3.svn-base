<%@page import="com.hotent.bpmx.api.constant.ScriptType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>脚本弹框</title>
<%@include file="/commons/include/list.jsp" %>
<script type="text/javascript">
	var parentFlowKey = "${param.parentFlowKey}";
</script>
<script type="text/javascript" src="${ctx}/js/other/javacode/codemirror.js"></script>
<script type="text/javascript" src="${ctx}/js/other/javacode/InitMirror.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/conditionScriptDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/scriptSelector.js"></script>
<script type="text/javascript">
	var passConf = window.passConf;
	var scriptJson = passConf.json;
	if(!scriptJson) scriptJson = {ruleType:2};
	$(function(){
		if(scriptJson.script)
			$("#script").val(scriptJson.script);
		if(scriptJson.conDesc)
			$("#conDesc").val(scriptJson.conDesc);
		
     var url ='${ctx}/flow/node/varTree';
     var zTree = new ZtreeCreator('varTree',url)
     	.setDataKey({name:'description'})
     	.setCallback({onClick:setVariable})
     	.initZtree({defId:passConf.defId,nodeId:passConf.nodeId,parentFlowKey:parentFlowKey,includeBpmConstants:true},1);
	}); 
	
	
	function setVariable(event, treeId, node){
		var data ;
		if(node.fromType == 'boAttr'){
			if(node.dataType == 'string')
		  	    data = node.code+'.getString("'+node.name+'")';
			else if(node.dataType == 'number')
		  		data = node.code+'.getInt("'+node.name+'")';
			else if(node.dataType == 'date')
				data = node.code+'.getDate("'+node.name+'")';
			else data = node.code+'.get("'+node.name+'")';
		}else if (node.fromType == 'var'){
			 data =node.varKey;
		}else if(node.fromType == 'bpmConstants'){
			 data =node.name;
		}else return ;
		
		hideMenu()
		InitMirror.editor.insertCode(data);
	}
	function slectScript(){
		 ConditionScript.showDialog({defId:passConf.defId,nodeId:passConf.nodeId},function(data){ 
			InitMirror.editor.insertCode(data);		
		}); 
	}
	
	function getScript() {
		//InitMirror.save();
		scriptJson.script = $("#script").val();
		scriptJson.conDesc = $("#conDesc").val();
		if(!scriptJson.script) {
			$.topCall.warn("请填写脚本！");
			return false;  
		}
		if(!scriptJson.conDesc) scriptJson.conDesc = "脚本";
		return scriptJson;
	}
	function hideMenu(){
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "showTreeBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
	function showMenu(){
		var btnObj = $("#showTreeBtn");
		var btnOffset = $("#showTreeBtn").offset();
		$("#menuContent").css({left:btnOffset.left + "px", top:btnOffset.top + btnObj.outerHeight() + "px"}).slideDown("fast");
		$("body").bind("mousedown", onBodyDown);
	}
	function selectConditionScript(){
		   ConditionScript.showDialog({defId:'${defId}',type:2,nodeId:'${bpmNodeDef.nodeId}'},function(data){ 
			 InitMirror.editor.insertCode(data);
		  }); 
		}
	
	function selectScript(){
		new ScriptSelector(function(script){
			InitMirror.editor.insertCode(script);
		 }).show();
	}
	
</script>
</head>
<body>
    <table class="table-form">
   		 <tr>
			<th width="20%"><span>表单变量</span> </th>
			<td>
				<a class="btn btn-primary btn-xs" onclick="selectScript()">常用脚本</a>
			 	<a class="btn btn-primary btn-xs" onclick="selectConditionScript()">人员脚本</a>
				<a id="showTreeBtn" class="btn btn-primary btn-xs" onclick="showMenu()">可选变量</a> 
				<div id="menuContent" class="menuContent" style=" height: 260px;display:none;overflow-y:scroll; position:absolute;z-index: 9999;background-color:#F5F5F5">
				 	<ul id="varTree" class="ztree" style="margin-top:0; width:160px; height:260px;"></ul>
				</div>
			</td>
		</tr>
		<tr>
			<th width="20%"><span>脚本:</span></th>
			<td> 
				<textarea name="script" id="script" codemirror="true" mirrorheight="200px" rows="10" cols="80" ></textarea>
			</td>
		</tr>
		<tr>
			<th width="20%"><span>描述</span> </th>
			<td>
				<input class="inputText" style="width: 260px" id="conDesc">
			</td>
		</tr>
    </table> 
</body>
</html>
		