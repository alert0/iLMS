<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<%@include file="/commons/include/codeMirror.jsp"%>
<link rel="stylesheet" href="${ctx}/js/system/query/dataRights.css">
<script type="text/javascript" src="${ctx}/js/system/query/queryViewEditController.js"></script>
<script type="text/javascript" src="${ctx}/js/system/query/bpmNodeRule.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery.linkdiv.js"></script>

<script type="text/javascript">
	var filterInitPoint = false;//过滤条件的中的组件是否初始化的标记
	var filterInitType = null;
	var filterInitSql = null;
	$(function() {
		$('#tab').tabs({
			tabPosition : 'top',
			onSelect : function(title, index) {
				var scope=AngularUtil.getScope();
				
				if (index == 3&&!filterInitPoint) {
					filterInitPoint=true;
					
					//得到焦点间接修复codeMirror的在tab切换中的初始化问题
					scope.$broadcast('CodeMirror', function(CodeMirror) {
						CodeMirror.replaceSelection(" ");
					});
					
					//要切换到条件脚本才初始化，不然会导致元素的坐标读取错误的问题
					var data=scope.data;
					data.filterInitType = data.filterType;
					data.filterInitSql = data.filter;
					if (__param.id&&scope.data.filterType == 1) {
						data.filter = JSON.parse(data.filter?data.filter:"{}");
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
				}
			}
		});
	});

	function getRuleDiv(t) {
		return $("#ruleDiv");
	};
	
</script>
</head>
<body ng-controller="ctrl" ng-init="init()">
	<!-- 顶部按钮 -->
	<div class="toolbar-panel">
		<div class="buttons">
			<a class="btn btn-primary btn-sm fa-save" ng-model="data" ng-click="saveFormValid()" ht-save="${ctx}/system/query/queryView/save">
				<span>保存</span>
			</a>
			<a class="btn btn-primary btn-sm fa-back" onClick="HT.window.closeEdit(true,'grid');">
				<span>返回</span>
			</a>
			<a class="btn btn-primary btn-sm fa-repeat" ng-click="initDefaultData()">
				<span>重置字段</span>
			</a>
			<a ng-if="data.id" class="btn btn-primary btn-sm fa-edit" href="${ctx}/system/query/queryViewTempEdit?id=${param.id}&sqlAlias=${param.sqlAlias}">
				<span>编辑模板</span>
			</a>
		</div>
	</div>
	<form name="form" method="post" ht-load="${ctx}/system/query/queryView/getJson?id=${param.id}" ng-model="data">
		<div id="tab">
			<div title="基本信息">
				<table class="table-form" cellspacing="0">
					<tr>
						<th>名字</th>
						<td>
							<input class="inputText" type="text" ng-model="data.name" ht-validate="{required:true}" />
						</td>
						<th>别名</th>
						<td>
							<input class="inputText" type="text" ng-model="data.alias" ht-validate="{required:true,varirule:true,isexist:'${ctx}/system/query/queryView/isExist?id=${param.id}&sqlAlias=${param.sqlAlias}'}" ht-pinyin="data.name" />
						</td>
					</tr>
					<tr>
						<th>是否分页</th>
						<td>
							<span ht-boolean="1/0" text="是/否" ng-model="data.needPage"></span>
							<select class="inputText" ng-model="data.pageSize" ng-show="data.needPage==1">
								<option value="5">5</option>
								<option value="10">10</option>
								<option value="15">15</option>
								<option value="20">20</option>
								<option value="50">50</option>
							</select>
						</td>
						<th>是否初始查询</th>
						<td>
							<span ht-boolean="1/0" text="是/否" ng-model="data.initQuery" />
						</td>
					</tr>
					<tr>
						<th>是否显示行号</th>
						<td>
							<span ht-boolean="1/0" text="是/否" ng-model="data.showRowsNum"></span>
						</td>
						<th>
							数据模板
							<a href="javaScript:void(0)" style="text-decoration: none;" title='添加更多数据模板，请到自定义表单模板中添加类型为"业务数据模板"的模板' class="fa fa-exclamation-circle ht-help" ht-tip> </a>
						</th>
						<td>
							<select class="inputText" ng-model="data.templateAlias" ng-options="m.alias as m.templateName for m in tempList" ht-validate="{required:true}">
								<option value="">请选择</option>
							</select>
						</td>
					</tr>
					<tr ng-if="data.id">
						<th>重新生成模板</th>
						<td colspan="3">
							<span ht-boolean="1/0" text="是/否" ng-model="data.rebuildTemp"></span>
						</td>
					</tr>
				</table>
			</div>
			<div title="显示字段">
				<table class="table-list" cellspacing="0">
					<tr>
						<th>序号</th>
						<th>列名</th>
						<th>注释</th>
						<th>支持排序</th>
						<th>排序方向</th>
						<th>默认排序</th>
						<th>是否冻结</th>
						<th>是否隐藏</th>
						<th>对齐方式</th>
						<th>
							宽度(px)
							<a href="javaScript:void(0)" style="text-decoration: none;" title="为0表示自适应" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
						</th>
						<th>统计类型</th>
						<th>统计模板</th>
					</tr>
					<tr ng-repeat="item in data.shows track by $index">
						<td>{{$index+1}}</td>
						<td>{{item.fieldName}}</td>
						<td>
							<input class="inputText" type="text" ng-model="item.fieldDesc" ht-validate="{required:true,maxlength:48}" />
						</td>
						<td>
							<span ht-boolean="1/0" text="是/否" ng-model="item.sortable" ng-if="item.isVirtual!=1"></span>
						</td>
						<td>
							<select ng-show="item.defaultSort==1" class="inputText" ng-model="item.sortSeq">
								<option value="asc">asc</option>
								<option value="desc">desc</option>
							</select>
						</td>
						<td>
							<span ht-boolean="1/0" text="是/否" ng-model="item.defaultSort" ng-if="item.isVirtual!=1" ng-change="defaultSortChange(item)"></span>
						</td>
						<td>
							<span ht-boolean="1/0" text="是/否" ng-model="item.frozen"></span>
						</td>
						<td>
							<span ht-boolean="1/0" text="是/否" ng-model="item.hidden"></span>
						</td>
						<td>
							<select class="inputText" ng-model="item.align">
								<option value="left">居左</option>
								<option value="right">居右</option>
								<option value="center">居中</option>
							</select>
						</td>
						<td>
							<input class="inputText" type="text" ng-model="item.width" ht-validate="{required:true,minvalue:0,digits:true}" />
						</td>
						<td>
							<select ng-model="item.summaryType" class="inputText" style="width: 60px;">
								<option value="">请选择</option>
								<option value="count">计数</option>
								<option value="sum">求和</option>
								<option value="max">最大</option>
								<option value="min">最小</option>
							</select>
						</td>
						<td>
							<textarea ng-show="item.summaryType" ng-model="item.summaryTemplate" rows="3" cols="25"></textarea>
						</td>
					</tr>
				</table>
			</div>
			<div title="条件字段">
				<table class="table-list" cellspacing="0">
					<tr>
						<th>列名</th>
						<th>描述</th>
						<th>数据类型</th>
						<th>控件类型</th>
						<th>操作符</th>
						<th>值来源</th>
						<th>是否隐藏</th>
						<th>管理</th>
					</tr>
					<tr ng-repeat="item in data.conditions track by $index">
						<td>{{item.fieldName}}</td>
						<td>
							<input class="inputText" type="text" ng-model="item.fieldDesc" ht-validate="{required:true}" />
						</td>
						<td>{{item.dataType}}</td>
						<td>{{item.controlTypeDesc}}</td>
						<td>
							<select ng-model="item.operate" class="inputText">
								<option value="EQ">等于</option>
								<option value="NE">不等于</option>
								<!-- 字符串特有 -->
								<option ng-show="item.dataType=='varchar'" value="LK">相似</option>
								<option ng-show="item.dataType=='varchar'" value="LFK">左相似</option>
								<option ng-show="item.dataType=='varchar'" value="RHK">右相似</option>
								<!-- 数字和日期特有 -->
								<option ng-show="item.dataType!='varchar'" value="LT">小于</option>
								<option ng-show="item.dataType!='varchar'" value="LE">小于等于</option>
								<option ng-show="item.dataType!='varchar'" value="GT">大于</option>
								<option ng-show="item.dataType!='varchar'" value="GE">大于等于</option>
								<option ng-show="item.dataType!='varchar'" value="BETWEEN">在...之间</option>
							</select>
						</td>
						<td>
							<select ng-model="item.valueFrom" class="inputText">
								<option value="1">表单输入</option>
								<option value="2">动态传入</option>
							</select>
						</td>
						<td>
							<span ng-show="item.valueFrom==1">
							<span ht-boolean="1/0" text="是/否" ng-model="item.hidden"></span>
							</span>
						</td>
						<td>
							<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.up($index,data.conditions)" class="btn btn-sm btn-default fa-chevron-up"></a>
							<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.down($index,data.conditions)" class="btn btn-sm btn-default fa-chevron-down"></a>
						</td>
					</tr>
				</table>
			</div>
			<div title="过滤功能">
				<table class="table-form" cellspacing="0">
					<tr>
						<th style="width: 2%">脚本类型</th>
						<th>
							<select ng-model="data.filterType" class="inputText" style="float: left;" ng-change="changeFilterType();">
								<option value="1">条件脚本</option>
								<option value="2">SQL</option>
								<option value="3">追加SQL</option>
							</select>
						</th>
					</tr>
					<tr ng-show="data.filterType==1">
						<td colspan="2">
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
									<c:forEach var="f" items="${sqldef.metafields}">
										<option value="${f.fieldName}" datefmt="${f.dateFormat}" ctltype="${f.controlType}" ftype="${f.dataType}" >${f.fieldDesc}</option>
									</c:forEach>
									<!-- <option value="{{f.fieldName}}" datefmt="{{f.dateFormat}}" ctltype="{{f.controlType}}" ftype="{{f.dataType}}" ng-repeat="f in sqldef.metafields track by $index">{{f.fieldDesc}}</option> -->
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
						<th style="width: 2%">常用变量</th>
						<td style="float: left;">
							<select class="inputText" ng-model="selectVar" ng-change="clickVar(selectVar)">
								<option value="">--请选择--</option>
								<optgroup label="SQL字段"></optgroup>
								<option value="{{f.fieldName}}" ng-repeat="f in sqldef.metafields |filter:{isVirtual:'0'} track by $index">{{f.fieldDesc}}</option>
								<optgroup label="常用变量"></optgroup>
								<option ng-repeat="f in comVarList track by $index" value="[{{f.alias}}]">{{f.title}}</option>
								<optgroup label="条件字段"></optgroup>
								<option ng-repeat="f in data.conditions track by $index" value="param.{{f.fieldName}}">{{f.fieldDesc}}</option>
								<optgroup label="jqGrid参数"></optgroup>
								<option value="param.sortField">排序字段</option>
								<option value="param.orderSeq">排序方向</option>
								<option value="param.pageSize">页面大小</option>
								<option value="param.page">当前页面</option>
							</select>
						</td>
					</tr>

					<tr ng-if="data.filterType!=1">
						<td colspan="2">
							<textarea ui-codemirror ng-model="data.filter" style="width: 90%" rows="20" cols="20"></textarea>
						</td>
					</tr>
				</table>
			</div>
			<div title="功能按钮">
				<table class="table-list" cellspacing="0">
					<tr>
						<th>名称</th>
						<th>类型</th>
						<th>URL路径</th>
						<th>隐藏</th>
						<th>操作</th>
					</tr>
					<tr ng-repeat="item in data.buttons track by $index">
						<td>{{item.name}}</td>
						<td>{{item.inRow=='0'?'页头':'行内'}}</td>
						<td>{{item.urlPath}}</td>
						<td>
							<span ht-boolean="1/0" text="是/否" ng-model="item.hidden"></span>
						</td>
						<td>
							<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.up($index,data.buttons)" class="btn btn-sm btn-default fa-chevron-up"></a>
							<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.down($index,data.buttons)" class="btn btn-sm btn-default fa-chevron-down"></a>
						</td>
					</tr>
				</table>
			</div>
			<div title="分组设置">
				<table class="table-form" cellspacing="0">
					<tr>
						<th>启用分组：</th>
						<td>
							<span ht-boolean="1/0" text="是/否" ng-model="data.supportGroup"></span>
						</td>
					</tr>
					<tr ng-show="data.supportGroup==1">
						<th>字段列表</th>
						<td>
							<span ng-repeat="item in data.shows track by $index ">
								<span ht-boolean="1/0" text="{{item.fieldDesc}}/{{item.fieldDesc}}" ng-model="item.group" ng-change="groupChange(item)"></span>
							</span>
						</td>
					</tr>
					<tr ng-show="data.supportGroup==1">
						<td colspan="2">
							<table class="table-list" cellspacing="0">
								<tr>
									<th style="width:5%;">序号</th>
									<th style="width:10%;">名称</th>
									<th style="width:8%;">合计</th>
									<th style="width:7%;">是否显示</th>
									<th style="width:15%;">排序规则</th>
									<th style="width:45%;">
										分组表头模版
										<a href="javaScript:void(0)" style="text-decoration: none;" title="这个显示分组表头模版。示例:<b> 国家: {0} {1} </b>" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
									</th>
									<th style="width:10%;">管理</th>
								</tr>
								<tr ng-repeat="field in data.groupSetting.groupField track by $index ">
									<td>{{$index+1}}</td>
									<td>{{field}}</td>
									<td>
										<span ht-boolean text="是/否" ng-model="data.groupSetting.groupSummary[$index]"></span>
									</td>
									<td>
										<span ht-boolean text="是/否" ng-model="data.groupSetting.groupColumnShow[$index]"></span>
									</td>
									<td>
										<select ng-model="data.groupSetting.groupOrder[$index]" class="inputText">
											<option value="asc">升序</option>
											<option value="desc">降序</option>
										</select>
									</td>
									<td>
										<textarea style="width: 90%" rows="4" ng-model="data.groupSetting.groupText[$index]"></textarea>
									</td>
									<td>
										<a href="javascript:javaScript:void(0)" ng-click="groupUp($index)" class="btn btn-sm btn-default fa-chevron-up"></a>
										<a href="javascript:javaScript:void(0)" ng-click="groupDown($index)" class="btn btn-sm btn-default fa-chevron-down"></a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>