<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<%@include file="/commons/include/codeMirror.jsp"%>
<link rel="stylesheet" href="${ctx}/js/system/query/dataRights.css">
<script type="text/javascript" src="${ctx}/js/platform/form/dataTemplate/bpmDataTemplateFilterController.js"></script>
<script type="text/javascript" src="${ctx}/js/system/query/bpmNodeRule.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery.linkdiv.js"></script>

<script type="text/javascript">
	var filterInitPoint = false;//过滤条件的中的组件是否初始化的标记
	var filterInitType = null;
	var filterInitSql = null;
	$(function() {
		var scope=AngularUtil.getScope();	
		filterInitPoint=true;
		//得到焦点间接修复codeMirror的在tab切换中的初始化问题
		scope.$broadcast('CodeMirror', function(CodeMirror) {
			CodeMirror.replaceSelection(" ");
		});
		
		//要切换到条件脚本才初始化，不然会导致元素的坐标读取错误的问题
		var data=scope.data;
		data.filterInitType = data.filterType;
		data.filterInitSql = data.filter;
		if (!scope.isEditabled&&scope.data.filterType == 1) {
			data.filter = data.filter?data.filter:JSON.parse("{}");
			$("#ruleDiv").linkdiv({
				data : data.filter,
				updateContent : updateContent,
				rule2json : rule2json
			});
		} else {
			$("#ruleDiv").linkdiv({
				data : {},
				updateContent : updateContent,
				rule2json : rule2json
			});
		}
	});

	function getRuleDiv(t) {
		return $("#ruleDiv");
	};
	
	function saveFilterForm(){
		var scope = AngularUtil.getScope();
		if(scope.saveFormValid()){
			if(scope.data.filter.length<1){
				$.topCall.warn('您还未添加任何过滤条件！');
				return false;
			}else if(scope.data.filterType != 1&&!scope.data.filter.trim()){//验证过滤条件为SQL或追加SQL时，脚本为必填
				$.topCall.error("请正确填写过滤条件中的SQL语句");
				return false;
			}
			return scope.data;
		}
		return false;
	}
	
</script>
</head>
<body ng-controller="ctrl" ng-init="init()">
	<form name="form" method="post" ht-load="${ctx}/system/query/queryView/getJson?id=${param.id}" ng-model="data">
		<table class="table-form" cellspacing="0">
			<tr>
				<th style="width: 80px;">脚本类型</th>
				<th>
					<select ng-model="data.filterType" class="inputText" style="float: left;" ng-change="changeFilterType();">
						<option value="1">条件脚本</option>
						<option value="2">SQL</option>
						<option value="3">追加SQL</option>
					</select>
				</th>
				<th style="width: 80px;">名称</th>
				<th>
					<span class="required">*</span>
					<input class="inputText" type="text" ng-model="data.name" ht-validate="{required:true,maxlength:48}" ng-disabled="!isEditabled" />
				</th>
				<th style="width: 80px;">Key</th>
				<th>
					<span class="required">*</span>
					<input class="inputText" ng-disabled="!isEditabled" type="text" ng-model="data.key" ht-validate="{required:true,varirule:true,fields:true}" ht-pinyin="data.name" />
				</th>
				<th></th>
			</tr>
			<tr ng-show="data.filterType==1">
				<td colspan="7">
					<div class="datagrid-toolbar" style="height: 36px; text-align: right;">
						<a onclick="addDiv(1)" class="btn btn-sm btn-primary fa-add ">添加规则 </a>
						<a onclick="assembleDiv()" class="btn btn-sm btn-primary fa-sign-in">组合规则</a>
						<a onclick="splitDiv()" class="btn btn-sm btn-primary fa-sign-out">拆分规则</a>
						<a onclick="removeDiv()" class="btn btn-sm btn-primary fa-remove">删除</a>
					</div>
					<div>
						<div id="ruleDiv" style="margin: 5px 0 0 0;"></div>
					</div>
					<div style="display: none;">
						<select id="flowVarsSelect" class="left margin-set ht-input" onchange="flowVarChange.apply(this)" name="flowVars">
							<option value="">--请选择--</option>
							<option value="{{colPrefix}}{{f.name}}" ftype="{{f.type}}" ctltype="{{f.ctrlType}}" ng-repeat="f in metafields track by $index">{{f.desc}}</option>
						</select>
						<span id="judgeCon-1" class="judge-condition">
							<select name="judgeCondition" class="inputText" style="width: 80px; height: 30px; margin-bottom: 10px;" onchange="judgeConditionChange.apply(this)">
								<option value="1">等于</option>
								<option value="2">不等于</option>
								<option value="3">大于</option>
								<option value="4">大于等于</option>
								<option value="5">小于</option>
								<option value="6">小于等于</option>
								<option value="7">等于变量</option>
								<option value="8">不等于变量</option>
							</select>
						</span>
						<span id="judgeCon-2" class="judge-condition">
							<select name="judgeCondition" class="inputText" style="width: 80px; height: 30px; margin-bottom: 10px;" onchange="judgeConditionChange.apply(this)">
								<option value="1">等于</option>
								<option value="3">等于(忽略大小写)</option>
								<option value="2">不等于</option>
								<option value="4">like</option>
								<option value="5">like左</option>
								<option value="6">like右</option>
								<option value="7">等于变量</option>
								<option value="8">不等于变量</option>
							</select>
						</span>
						<span id="normal-input" class="judge-value" type="1">
							<input class="inputText" name="judgeValue" type="text" style="width: 100px; margin-left: 5px; margin-bottom: 10px;">
						</span>
						<span id="date-span" class="judge-value" type="1">
							<input id="date-input" type="text" class="wdateTime inputText" style="width: 180px; margin-bottom: 10px;">
						</span>
						<span id="commonVar" class="judge-value" type="2">
							<select class="ht-input" style="width: 100px; margin-left: 5px; margin-bottom: 10px;">
								<c:forEach var="item" items="${comVarList}">
									<option value="[${item.alias}]">${item.title}</option>
								</c:forEach>
							</select>
						</span>
					</div>
				</td>
			</tr>

			<tr ng-show="data.filterType!=1">
				<th style="width: 80px;">常用变量</th>
				<td style="float: left;" colspan="6">
					<select class="inputText" ng-model="selectVar" ng-change="clickVar(selectVar)">
						<option value="">--请选择--</option>
						<optgroup label="SQL字段"></optgroup>
						<option value="{{colPrefix}}{{f.name}}" ng-repeat="f in metafields track by $index">{{f.desc}}</option>
						<optgroup label="常用变量"></optgroup>
						<option ng-repeat="f in comVarList track by $index" value="[{{f.alias}}]">{{f.title}}</option>
						<optgroup label="条件字段"></optgroup>
						<option value="{{colPrefix}}{{f.na}}" ng-repeat="f in parentScope.conditionFields track by $index">{{f.cm}}</option>
						<optgroup label="jqGrid参数"></optgroup>
						<option value="param.sortField">排序字段</option>
						<option value="param.orderSeq">排序方向</option>
						<option value="param.pageSize">页面大小</option>
						<option value="param.page">当前页面</option>
					</select>
				</td>
			</tr>

			<tr ng-if="data.filterType!=1">
				<td colspan="7">
					<textarea ui-codemirror ng-model="data.filter" style="width: 90%" rows="20" cols="20"></textarea>
				</td>
			</tr>
		</table>
			
	</form>
</body>
</html>