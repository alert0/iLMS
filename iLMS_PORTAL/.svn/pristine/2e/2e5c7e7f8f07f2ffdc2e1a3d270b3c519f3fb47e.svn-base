<%@page import="com.hotent.bpmx.api.constant.NodeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/edit.jsp" %> 
		<%@include file="/commons/include/zTree.jsp" %> 
		<script type="text/javascript" src="${ctx}/js/platform/system/dialog/scriptSelector.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/system/dialog/conditionScriptDialog.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/javacode/codemirror.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/javacode/InitMirror.js"></script>
		<script type="text/javascript">
		var varTree;
		$(function() {
		 	varTree = new ZtreeCreator('varTreeObj',"${ctx}/flow/node/varTree")
				.setDataKey({name:'desc'})
		     	.setCallback({onClick:setVariable})
		     	.setChildKey()
		     	.makeCombTree("varTree") 
		     	.initZtree({defId:'${processDifinitionId}',nodeId:'${nodeDef.nodeId}',parentFlowKey:'${parentFlowKey}',includeBpmConstants:true},1); 
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
		
		function addRule(){
			var  frm =$('#ruleFrom').form();
			if (frm.valid()){
				var rule = {ruleName:$("#ruleName").val(),
							targetNode:	$("#targetNode").val(),
							condition: InitMirror.editor.getCode() /* $("#condition").val() */
							};    
				var records  = $('#nodeRuleList').datagrid('getRows'); 
				for(var i=0;i<records.length;i++){
					if(records[i]['ruleName']==rule['ruleName']){
						 $('#nodeRuleList').datagrid('deleteRow',i);
					}
				}
				$('#nodeRuleList').datagrid('appendRow',rule);
				$("#ruleName").val("");
				$("#targetNode").val("");
				InitMirror.editor.setCode("return true;"); 
			}
		}
		function selectConditionScript(){
			   ConditionScript.showDialog({defId:'${processDifinitionId}',nodeId:'${bpmNodeDef.nodeId}'},function(data){ 
				   var script="return " + data.script +";";
				 InitMirror.editor.insertCode(script);
			  }); 
			}
		
		function selectScript(){
			new ScriptSelector(function(script){
				InitMirror.editor.insertCode(script);
			 }).show();
		}	
		
		function deleteSelect(id){
			var records  = $('#nodeRuleList').datagrid('getRows'); 
			for(var i=0;i<records.length;i++){
				if(records[i]['ruleName']==id){
					 $('#nodeRuleList').datagrid('deleteRow',i);
				}
			}
		}
		function saveRules(){
			var rules = $('#nodeRuleList').datagrid('getRows');
			if(!rules ||rules.length==0){
				$.topCall.confirm("提示信息",'目前条件列表为空，是否确定清空条件',function(r){
					if(r){ 
						save(rules);
					}else{
					    return; 
					}
				});
			}
			else save(rules);
		}
		function save(rules){
			rules = JSON.stringify(rules);
			var data ={ 
				'rules' : rules, 
				nodeId : '${nodeDef.nodeId}',
			    processDifinitionId : '${processDifinitionId}'
			}
			$.post("${ctx}/flow/node/ruleSave",data,function(responseText){
				var resultMessage=new com.hotent.form.ResultMessage(responseText);
				if(resultMessage.isSuccess()){
					$.topCall.confirm("提示信息",resultMessage.getMessage()+',是否继续操作',function(r){
						window.passConf();
						if(r){ 
						}else{
						    window.selfDialog.dialog('close');
						}
					});
				}else{
					$.topCall.error(resultMessage.getMessage(),resultMessage.getCause());
				}
		   }); 
		}
		// 点击标题展示改条条件
		function showCondition(index){
			var records  = $('#nodeRuleList').datagrid('getRows');
			var rowData =records[index];
			var hasTS = false ; // 
			for(var i=0;i<records.length;i++){
				if(records[i]['ruleName'] == $("#ruleName").val() 
						&& records[i]['targetNode'] == $("#targetNode").val()
							&&	records[i]['condition'] == InitMirror.editor.getCode() ){
					hasTS = true;
				}
			} 
			if(!hasTS && $("#ruleName").val() && $("#targetNode").val() && InitMirror.editor.getCode()){
				
				$.topCall.confirm("提示", "将覆盖”"+$("#ruleName").val()+"“修改的数据",function(r){ 
					if(r){
						$("#ruleName").val(rowData['ruleName']);
						$("#targetNode").val(rowData['targetNode']);
						InitMirror.editor.setCode(rowData['condition']); 
						//$("#condition").val(rowData['condition']);
					}else {return; }
				}); 
			}else{
			$("#ruleName").val(rowData['ruleName']);
			$("#targetNode").val(rowData['targetNode']);
			InitMirror.editor.setCode(rowData['condition']); 
			//$("#condition").val(rowData['condition']);
			}
		}
		</script>
	</head>
	<body  class="easyui-layout">
		<div data-options="region:'center',border:false" >
			<div class="toolbar-panel">
			<div class="buttons">
				 <a href="javascript:void(0)" class="btn btn-primary fa-save" onclick="saveRules()">保存</a>
				 <a href="javascript:void(0)" class="btn btn-primary fa-close" onclick="window.selfDialog.dialog('close')">取消</a>
			</div>
			</div>
			
			<form id="ruleFrom">
				<table class="table-form" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<th width="20%">当前节点名称:</th>
						<td>${nodeDef.name}</td>
					</tr>
					<tr>
						<th width="20%">规则名称: <span class="required">*</span></th>
						<td><input type="text" id="ruleName" name="ruleName" size="40" value="${bpmNodeRule.ruleName}" class="inputText" validate="{required:true,maxlength:120}"/></td>
					</tr>
					<tr>
						<th width="20%">跳转节点名称:</th>
						<td>
							<select name="targetNode" id="targetNode" validate="{required:true,maxlength:120}" class="inputText must-error">
								<option value="">请选择</option>
								<c:forEach items="${nodeDefList}" var="node">
									<c:if test="${nodeDefinition.type eq type}">
									<option value="${node.nodeId}">${node.name}</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th width="20%">规则表达式*:<span class="required">*</span></th>
						<td>
							<a class="btn btn-primary btn-xs" onclick="selectScript()">常用脚本</a>
							<a class="btn btn-primary btn-xs" onclick="selectConditionScript()">条件脚本</a>
						    <a id="varTree" class="btn btn-primary btn-xs">可选变量</a>
							<textarea codemirror="true" mirrorheight="200px"  id="condition" rows="12" cols="55" name="conditionCode"  validate="{required:true}">return true;</textarea>
							<div style="margin:8px 0;">这个脚本需要使用返回语句(return)返回布尔值，返回true流程将跳转到指定的节点。</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div align="center">
								<a onclick="addRule()" class="btn btn-primary fa-arrow-right">添加至条件列表</a>
							</div>
						</td>
					</tr> 
				</table>
			</form>	
		</div>
		 <div data-options="region:'east',border:false"  style="text-align:center;width:280px;" >
				<table id="nodeRuleList" class="easyui-datagrid"  data-options="fitColumns:false,singleSelect:true" fit=true 
				url="${ctx}/flow/node/ruleListJson?definitionId=${processDifinitionId}&nodeId=${nodeDef.nodeId}">
				    <thead>
					    <tr>
							<th data-options="field:'ruleName',width:120" formatter=titleFormatter>规则名称</th>
							<th data-options="field:'targetNode',width:115" formatter=titleFormatter>目标节点</th>
							<th data-options="field:'condition',width:45,formatter:manager"> 管理 </th> 
					    </tr>
				    </thead>
			    </table> 
		</div>
	</body>
	<script type="text/javascript">
	function titleFormatter(value,row,index){ 
		return '<a onclick="javascript:showCondition('+index+')" class="btn btn-link">'+value+' </a>' ;
	}
	function manager(value,row,index){ 
		return '<a onclick="deleteSelect(\''+row["ruleName"]+'\')" class="btn-default btn fa-delete"> </a>' ;
	}
	</script> 
</html>
