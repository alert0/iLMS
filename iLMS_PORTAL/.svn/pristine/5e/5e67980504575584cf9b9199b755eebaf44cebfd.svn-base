<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/system/query/querySqldefEditController.js"></script>
<script type="text/javascript">
	$(function() {
		$('#tab').tabs({
			tabPosition : 'top'
		});
		if (!__param.id) {//新增模式下不能修改字段配置
			$('#tab').tabs('disableTab', 2);
		}
	});
	
	var initModel = '${initModel}';
</script>
</head>
<body ng-controller="ctrl" ng-init="init()">
	<!-- 顶部按钮 -->
	<div class="toolbar-panel">
		<div class="buttons">
			<a class="btn btn-sm  btn-primary fa-save" ng-click="save()">
				<span>保存</span>
			</a>
			<a class="btn btn-sm btn-primary fa-back" onClick="HT.window.closeEdit(true,'grid');">
				<span>返回</span>
			</a>
		</div>
	</div>
	<form name="form" method="post" ht-load="${ctx}/system/query/querySqldef/getJson?id=${param.id}" ng-model="data">
		<div id="tab">
			<div title="sql配置">
				<table class="table-form" cellspacing="0">
					<tr>
						<th>
							名称:
							<span class="required">*</span>
						</th>
						<td>
							<input class="inputText" type="text" ng-model="data.name" ht-validate="{required:true,maxlength:48}" />
						</td>
						<th>
							别名:
							<span class="required">*</span>
						</th>
						<td>
							<input ng-disabled="data.id" class="inputText" type="text" ng-model="data.alias" ht-validate="{required:true,maxlength:48}" ht-pinyin="data.name" />
						</td>
					</tr>
					<tr>
						<th>
							数据源:
							<span class="required">*</span>
						</th>
						<td colspan="3">
							<div ht-ds-selector="data.dsName" ng-disabled="data.id"></div>
						</td>
						<!-- <th>
							<span>是否支持tab:</span>
						</th>
						<td>
							<span ht-boolean="1/0" text="支持/不支持" ng-model="data.supportTab" />
						</td> -->
					</tr>
					<tr>
						<th>
							SQL语句:
							<span class="required">*</span>
						</th>
						<td colspan="3">
							<textarea ng-disabled="data.id" rows="12" style="width: 80%; resize: none" ng-model="data.sql" ht-validate="{required:true}"></textarea>
							<a class="btn btn-sm btn-primary fa-check" ng-click="checkSql()">
								<span>验证SQL</span>
							</a>
						</td>
					</tr>
				</table>
			</div>
			<div title="按钮配置">
				<table class="table-list" cellspacing="0">
					<tr>
						<td colspan="5">
							<a class="btn btn-sm btn-primary fa-add" ng-click="addButton()">
								<span>添加</span>
							</a>
						</td>
					</tr>
					<tr>
						<th>名字</th>
						<th>行内按钮</th>
						<th>事件类型</th>
						<th>
							url路径
							<a href="javaScript:void(0)" style="text-decoration: none;" title="URL路径可以使用列表的字段对象，例如：/platform/system/sysQueryView.ht?userid={USERID}&staus={STATUS},这里可以使用多个参数,每个参数使用大括号将字段名括起来。" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
						</th>
						<th>操作</th>
					</tr>
					<tr ng-repeat="item in data.buttonDef track by $index">
						<td>
							<input class="inputText" type="text" ng-model="item.name" ht-validate="{required:true}" ng-disabled="item.isDefault=='1'" />
						</td>
						<td>
							<span ht-boolean="1/0" text="是/否" ng-model="item.inRow" ng-disabled="item.isDefault=='1'" />
						</td>
						<td>
							<select class="inputText" ng-model="item.triggerType" ng-disabled="item.isDefault=='1'">
								<option value="onclick">onclick</option>
								<option value="href">href</option>
							</select>
						</td>
						<td>
							<input class="inputText" type="text" ng-model="item.urlPath" ng-disabled="item.isDefault=='1'" />
						</td>
						<td>
							<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.up($index,data.buttonDef)" class="btn btn-sm btn-default fa-chevron-up"></a>
							<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.down($index,data.buttonDef)" class="btn btn-sm btn-default fa-chevron-down"></a>
							<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.del($index,data.buttonDef)" class="btn btn-sm btn-default fa-delete" ng-if="item.isDefault!='1'"></a>
						</td>
					</tr>
				</table>
			</div>
			<div title="字段配置">
				<table class="table-list" cellspacing="0">
					<tr>
						<th>序号</th>
						<th>列名</th>
						<th>类型</th>
						<th>实际列名</th>
						<th>描叙</th>
						<th>
							宽度(px)
							<a href="javaScript:void(0)" style="text-decoration: none;" title="为0表示自适应" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
						</th>
						<th>控件类型</th>
						<th>
							URL
							<a href="javaScript:void(0)" style="text-decoration: none;" title="url 写法规则如下/platform/system/sysQueryView.ht?USERID={USERID},当前字段为USERID使用大括号括起来。" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
						</th>
						<th>
							显示
							<input type="checkbox" ng-model="selectAllShow" />
						</th>
						<th>
							查询
							<input type="checkbox" ng-model="selectAllSearch" />
						</th>
						<th>虚拟列</th>
						<th>排序</th>
						<th>管理</th>
					</tr>
					<tr ng-repeat="item in data.metafields track by $index">
						<th>{{$index+1}}</th>
						<td>{{item.name}}</td>
						<td>{{item.dataType}}</td>
						<td>
							<input class="inputText" type="text" ng-model="item.fieldName" ht-validate="{required:true,maxlength:48}" />
						</td>
						<td>
							<input class="inputText" type="text" ht-validate="{required:true,maxlength:98}" ng-model="item.fieldDesc" />
						</td>
						<td>
							<input class="inputText" type="text" ht-validate="{minvalue:0,digits:true}" ng-model="item.width" />
						</td>
						<td>{{item.controlTypeDesc}}</td>
						<td>
							<input class="inputText" type="text" ng-model="item.url" ht-validate="{maxlength:198}"/>
						</td>
						<td>
							<span ht-boolean="1/0" text="是/否" ng-model="item.isShow" ht-checked="selectAllShow" />
						</td>	
						<td>
							<span ht-boolean="1/0" text="是/否" ng-model="item.isSearch" ht-checked="selectAllSearch" ng-if="item.isVirtual==0"></span>
							<span ht-boolean="1/0" text="是/否" ng-model="item.isSearch" disabled ng-if="item.isVirtual==1"></span>
						</td>
						<td>
							<span style="color: red" ng-show="item.isVirtual==1">是</span>
							<span style="color: green" ng-show="item.isVirtual==0">否</span>
						</td>
						<td>
							<input class="inputText" placeholder="to" ng-model="turnToIndex" ng-keydown="changeSn($event)" style="width: 30px" ht-validate="{number:true}">
							<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.up($index,data.metafields)" class="btn btn-sm btn-default fa-chevron-up"></a>
							<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.down($index,data.metafields)" class="btn btn-sm btn-default fa-chevron-down"></a>
							<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.del($index,data.metafields)" class="btn btn-sm btn-default fa-delete" ng-if="item.isVirtual==1"></a>
						</td>
						<td>
							<button ng-show="item.isVirtual==0" type="button" class="btn" ng-class="{true:'btn-info',false:'inactive'}[item.controlType!='onetext']" ng-click="fieldDialog('K',item)">控</button>
							<button type="button" class="btn" ng-class="{true:'btn-warning',false:'inactive'}[(item.alarmSetting!=null&&item.alarmSetting.length>0)||(item.formater!=null&&item.formater!='')]" ng-click="fieldDialog('B',item)">报</button>
							<button ng-show="item.isVirtual==0" type="button" class="btn" ng-class="{true:'btn-success',false:'inactive'}[checkHasX(item)]" ng-click="fieldDialog('X',item)">虚</button>
							<button ng-show="item.isVirtual==1" type="button" class="btn btn-danger" ng-click="fieldDialog('P',item)">配</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>