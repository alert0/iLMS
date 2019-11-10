<%@page import="com.hotent.bpmx.api.constant.NodeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript">
	var parentFlowKey = "${param.parentFlowKey}";
</script>
<script type="text/javascript" src="${ctx}/js/platform/flow/node/condition/conditionEditController.js"></script>
<f:link href="link-div-default.css"></f:link>
<script type="text/javascript" src="${ctx}/js/platform/flow/bpmNodeRule.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery.linkdiv.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/conditionScript/conditionScriptService.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/conditionScriptDialog.js"></script>

<script type="text/javascript">
	var flowKey = '${param.flowKey}';
	var defId = '${param.defId}';
	var nodeId = '${param.nodeId}';
	var nodeType = "${param.nodeType}";
	
	$(function() {
		var data=[];
		if(window.passConf&&window.passConf.condition){//初始化
			if(typeof(window.passConf.condition)=="string"){
				data = JSON.parse(window.passConf.condition);
			}else{
				data = CloneUtil.list(window.passConf.condition);
			}
		}
		$("#ruleDiv").linkdiv({
			data : data,
			updateContent : updateContent,
			rule2json : rule2json
		});
	});
	
	function getUserRuleData() {
		var scope = AngularUtil.getScope();
		var r={};
		if(!scope.userRule.calcs||scope.userRule.calcs.length==0){
			$.topCall.warn("请设置人员！","温馨提示");
			return "validateError";
		}
		if(!validateData(scope.userRule.calcs)){
			$.topCall.warn("存在无效的人员设置！","温馨提示");
			return "validateError";
		}
		var conditionJson = JSON.stringify($("#ruleDiv").linkdiv("getData"));
		r.condition=conditionJson;
		r.conditionMode="";
		r.name="";
		r.nodeType=nodeType;
		r.parentFlowKey=parentFlowKey;
		r.calcs=CloneUtil.list(scope.userRule.calcs);
		//拼装描述
		r.description="";
		$(r.calcs).each(function(){
			if(r.description){
				if(this.logicCal=="or"){
					r.description+="(或)";
				}else if(this.logicCal=="and"){
					r.description+="(与)";
				}else{
					r.description+="(非)";
				}
			}
			r.description+=this.description;
		});
		
		return r;
	};
	
	function validateData(calcs){
		var isTrue = true;
		for(var i=0;i<calcs.length;i++){
			switch(calcs[i].pluginType){
				case "script":
			 	case "hrScript": if(!calcs[i].script)isTrue=false; break;
	        	case "sameNode": if(!calcs[i].nodeId)isTrue=false; break;
	        	case "cusers": if(!calcs[i].description)isTrue=false; break;
			 }
		}
		return isTrue;
	}
	
	function getRuleDiv(t) {
		return $("#ruleDiv");
	};
</script>
</head>
<body ng-controller="ctrl">
	<div>
		<fieldset class="scheduler-border">
			<legend class="toolbar-title" style="margin-bottom:0;">
				<span>规则设置</span>
			</legend>
			<div style="height: auto; margin-top:5px; margin-left:3px;">
				<div class="datagrid-toolbar" style="height: 36px; text-align: left;">
					<a onclick="addDiv(1)" class="btn btn-sm btn-primary fa-add ">添加规则 </a>
					<a onclick="addDiv(2)" class="btn btn-sm btn-primary fa-add">添加脚本</a>
					<a onclick="assembleDiv()" class="btn btn-sm btn-primary fa-sign-in">组合规则</a>
					<a onclick="splitDiv()" class="btn btn-sm btn-primary fa-sign-out">拆分规则</a>
					<a onclick="removeDiv()" class="btn btn-sm btn-primary fa-remove">删除</a>
				</div>
				<div>
					<div id="ruleDiv" style="margin: 5px 0 0 0;"></div>
				</div>
			</div>
		</fieldset>
		<fieldset class="scheduler-border">
			<legend class="toolbar-title" style="margin-bottom:0;">
				<span>人员设置</span>
			</legend>
			<div class="datagrid-toolbar" style="text-align: left; padding:5px;">
				<a class="btn btn-primary btn-sm fa-add" ng-click="addCalc()">添加</a>
				<a ng-click="preview()" class="btn btn-primary btn-sm fa-youtube-play">预览</a>
			</div>
			<table class="table-list">
				<thead>
					<tr >
						<th width="200">用户类型</th>
						<th width="360">用户来自</th>
						<th>抽取用户</th>
						<th width="70">运算类型</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="calc in userRule.calcs track by $index">
						<td>
							<select ng-model="calc.pluginType" ng-options="m.type as m.title for m in nodeUserPluginList" class="inputText" ng-change="calcTypeChange(calc)">
							</select>
						</td>
						<td>
							<a ng-show="calc.pluginType!='approver'" href="javascript:javaScript:void(0)" class="btn btn-xs btn-primary" ng-click="selector(calc)">选择</a>
							<span>{{calc.description}}</span>
						</td>
						<td>
							<select ng-if="userRule.nodeType=='userTask'" ng-model="calc.extract" class="inputText">
								<option value="no">不抽取</option>
								<option value="extract">抽取</option>
							</select>
							<select ng-if="userRule.nodeType=='signTask'" ng-model="calc.extract">
								<option value="no">不抽取</option>
								<option value="extract">抽取</option>
								<option value="secondExtract">延迟抽取</option>
								<option value="usergroup">用户组合</option>
							</select>
						</td>
						<td>
							<select ng-if="!$first" ng-model="calc.logicCal">
								<option value='or'>或</option>
								<option value='and'>与</option>
								<option value='exclude'>排除</option>
							</select>
						</td>
						<td>
							<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.up($index,userRule.calcs)" class="btn btn-sm btn-default fa-chevron-up"></a>
							<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.down($index,userRule.calcs)" class="btn btn-sm btn-default fa-chevron-down"></a>
							<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.del($index,userRule.calcs)" class="btn btn-sm btn-default fa-delete"></a>
						</td>
					</tr>
				</tbody>
			</table>
			<br>
		</fieldset>
	</div>
</body>
</html>


