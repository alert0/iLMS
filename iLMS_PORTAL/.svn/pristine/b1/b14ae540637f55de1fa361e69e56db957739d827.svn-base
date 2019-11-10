<%@page import="com.hotent.bpmx.api.constant.NodeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/bpm/agentSetting/dialogController.js"></script>
<f:link href="link-div-default.css"></f:link>
<script type="text/javascript" src="${ctx}/js/platform/flow/bpmNodeRule.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery.linkdiv.js"></script>

<script type="text/javascript">
	var flowKey = '${param.flowKey}';
	var defId = '${param.defId}';
	var nodeId = '';

	$(function() {
		initConditions(window.passConf);
		if($("#agentConditionForm [name='conditionItem']").length==0){
			addAgent();
		}
	});

	/**
	 * 初始化条件设置
	 */
	function initConditions(conditions) {
		for (var i = 0; i < conditions.length; i++) {
			cond = conditions[i];
			constructConditionItem(cond);
		}
	};

	/**
	 * 构造代理条件
	 */
	function constructConditionItem(cond) {
		var agentid = cond.agentId;
		var agent = cond.agentName;
		var condition = cond.condition;
		var memo = cond.memo;
		var temp = $("#divAgentTemplate [name='conditionItem']").clone();

		$('[name="agentid"]', temp).val(agentid);
		$('[name="agent"]', temp).val(agent);
		$("#agentConditionForm").append(temp);

		//初始化规则代码
		$("div[name='ruleDiv']", temp).linkdiv({
			data : condition,
			updateContent : updateContent,
			rule2json : rule2json
		});
	};

	function getRuleDiv(t) {
		var parent = $(t).closest("[name=conditionItem]");
		return $("[name='ruleDiv']", parent);
	};

	/**
	 * 添加一个代理条件
	 */
	function addAgent() {
		var temp = $("#divAgentTemplate [name='conditionItem']").clone();
		$('[name="agentid"]', temp).val("");
		$('[name="agent"]', temp).val("");
		$("#agentConditionForm").append(temp);
		$("div[name='ruleDiv']", temp).linkdiv({
			updateContent : updateContent,
			rule2json : rule2json
		});
	}

	function selectAgent(obj) {
		CustomDialog.openCustomDialog("userSelector",function(data, dialog) {
			dialog.dialog('close');
			$(obj).parent().find("[name=agentid]").val(data[0].id);
			$(obj).parent().find("[name=agent]").val(data[0].name);
		},{
			selectNum : "1"
		});
	}

	/**
	 * 删除一个代理条件
	 */
	function delAgent(obj) {
		var objFieldSet = $(obj).closest("[name='conditionItem']");
		objFieldSet.remove();
	}

	/**
	 * 获取代理条件设置
	 */
	function getConditions() {
		var conditions = [];
		var conditionItems = $("div[name='conditionItem']", $("#agentConditionForm"));
		conditionItems.each(function() {
			var item = $(this);
			var agentid = $("[name='agentid']", item).val();
			var agent = $("[name='agent']", item).val();
			var condition = getData(item);
			var memo = '';
			var cond = {
				agentid : agentid,
				agent : agent,
				condition : condition,
				memo : memo
			};
			conditions.push(cond);
		});
		return conditions;
	}

	function saveCondForm() {
		var conditions = getConditions();

		var status = 0;
		var msg = "";

		if (!conditions || conditions.length < 1) {
			status = -1;
			msg = '请设置至少一个以上 <B>代理人设置</B>!';
		}
		
		for (var i = 0; i < conditions.length; i++) {
			var cond = conditions[i];
			if (!cond.agentid) {
				status = -1;
				msg += String.format('第{0}个 <B>代理人设置</B> 没有指定代理人!<br/>', i + 1);
			}
			if (cond.condition.length<1) {
				status = -1;
				msg += String.format('第{0}个 <B>代理人设置</B> 未添加规则!<br/>', i + 1);
			}
		}

		if (status) {
			$.topCall.error(msg);
			return;
		}
		return conditions;
	}
</script>
</head>
<body ng-controller="ctrl" ng-init="init()">
	<!-- 顶部按钮 -->
	<div class="toolbar-panel col-md-13">
		<div class="buttons" style="margin-left: 5px;">
			<button href="javaScript:void(0)" onclick="addAgent()" class="btn btn-primary btn-sm fa-add">
				<span>添加条件</span>
			</button>
		</div>
	</div>

	<div class="panel-body">
		<form id="agentConditionForm" method="post" action="save.ht"></form>
	</div>

	<div style="display: none;" id="divAgentTemplate">
		<div name="conditionItem">
			<fieldset class="scheduler-border">
				<legend style="width: 150px; border-bottom: 0; margin-bottom: 5px;">
					<span>
						代理人设置
						<a href="javascript:javaScript:void(0)" class="fa fa-delete" onclick="delAgent(this)"></a>
					</span>
				</legend>
				<table>
					<tr>
						<th>代理人：</th>
						<td>
							<input type="hidden" name="agentid" />
							<input type="text" name="agent" class="inputText" disabled="disabled" />
							<a href="javaScript:void(0)" class="btn btn-sm btn-info  fa-search-plus" onclick="selectAgent(this)">
								<span>选择</span>
							</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div style="height: auto; margin-top: 5px">
								<div class="datagrid-toolbar" style="height: 36px; text-align: right;">
									<a onclick="addDiv(1,this)" class="btn btn-sm btn-primary fa-add ">添加规则 </a>
									<a onclick="addDiv(2,this)" class="btn btn-sm btn-primary fa-add">添加脚本</a>
									<a onclick="assembleDiv(this)" class="btn btn-sm btn-primary fa-sign-in">组合规则</a>
									<a onclick="splitDiv(this)" class="btn btn-sm btn-primary fa-sign-out">拆分规则</a>
									<a onclick="removeDiv(this)" class="btn btn-sm btn-primary fa-remove">删除</a>
								</div>
								<div>
									<div name="ruleDiv" style="margin: 5px 0 0 0;"></div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</fieldset>
		</div>
	</div>
</body>
</html>


