<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/system/customQuery/customQueryService.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/customDialog/customDialogService.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/customDialog/settingController.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/scriptSelector.js"></script>
</head>
<body class="easyui-layout" ng-app="app" ng-controller="Controller">
	<div region="north" style="height: 45px">
		<div class="toolbar-panel col-md-13 ">
			<div class="buttons" style="margin-left: 10px;">
				<a href="javaScript:void(0)" ng-click="save();" class="btn btn-primary btn-sm fa-save">保存</a>
				<a href="javaScript:void(0)" onClick="window.selfDialog.dialog('close');" class="btn btn-sm btn-default fa-close">关闭</a>
			</div>
		</div>
	</div>
	<div region="west" title="获取字段信息" style="width: 380px;overflow: auto;" collapsible="false">
		<table class="table-list">
			<tr>
				<th width="5%">
					<input type="checkbox" ng-model="selectAll" />
				</th>
				<th>字段</th>
				<th width="35%">注解</th>
			</tr>
			<tr ng-repeat="column in table.columnList">
				<td>
					<input type="checkbox" ng-model="column.selected" ht-checked="selectAll" ng-true-value="yes" ng-false-value="no" />
				</td>
				<td ng-click="clickColumn(column)">{{column.fieldName}}</td>
				<td ng-click="clickColumn(column)">
					<input type="text" class="inputText" ng-model="column.comment" />
				</td>
			</tr>
		</table>
	</div>
	<div region="center" style="padding-left: 5px; padding-top: 200px;">
		<a href="javaScript:void(0)" ng-click="addColumns()" class="btn btn-info fa-arrow-right"></a>
	</div>
	<div region="east" title="字段设置" style="width: 580px;overflow:auto;" collapsible="false">
		<div id="tt1" class="easyui-tabs">
			<div title="显示字段">
				<table class="table-list">
					<thead ng-if="prop.style=='0'">
						<tr>
							<th>字段名</th>
							<th>显示名</th>
							<th width="25%">管理</th>
						</tr>
					</thead>
					<tbody ng-if="prop.style=='0'">
						<tr ng-repeat="column in prop.displayfield">
							<td>
								{{column.field}}
								<a ng-if="$index==0" href="javaScript:void(0)" style="text-decoration: none;color: red;" title="第一个字段为回显的显示字段" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
							</td>
							<td>
								<input type="text" class="inputText" ng-model="column.comment" />
							</td>
							<td>
								<a href="javaScript:void(0)" ng-click="ArrayTool.up($index,prop.displayfield)" class="btn btn-sm btn-default fa-chevron-up"></a>
								<a href="javaScript:void(0)" ng-click="ArrayTool.down($index,prop.displayfield)" class="btn btn-sm btn-default fa-chevron-down"></a>
								<a href="javaScript:void(0)" ng-click="ArrayTool.del($index,prop.displayfield)" class="btn btn-sm btn-default fa-times"></a>
							</td>
						</tr>
					</tbody>
					<tbody ng-if="prop.style=='1'">
						<tr>
							<th>
								<span>ID:</span>
							</th>
							<td>
								<input type="text" ng-model="prop.displayfield.id" ng-click="setActiveModel('prop.displayfield.id')" class="inputText" />
							</td>
						</tr>
						<tr>
							<th>
								<span>父ID:</span>
							</th>
							<td>
								<input type="text" ng-model="prop.displayfield.pid" ng-click="setActiveModel('prop.displayfield.pid')" class="inputText" />
							</td>
						</tr>
						<tr>
							<th>
								<span>父ID初始值:</span>
							</th>
							<td>
								<textarea ng-model="prop.displayfield.pvalue"></textarea>
								<label class="checkbox-inline"><input type="checkbox" ng-model="prop.displayfield.isScript" ng-true-value="true" ng-false-value="false" checked="checked" />
								脚本</label>
							</td>
						</tr>
						<tr>
							<th>
								<span>显示名称:</span>
							</th>
							<td>
								<input type="text" ng-model="prop.displayfield.displayName" ng-click="setActiveModel('prop.displayfield.displayName')" class="inputText" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div title="条件字段">
				<table class="table-list">
					<tr>
						<th>字段名</th>
						<th>显示名</th>
						<th>条件</th>
						<th>值来源</th>
						<th>默认值</th>
						<th width="25%">管理</th>
					</tr>
					<tr ng-repeat="column in prop.conditionfield" ng-if="column.defaultType!='5'">
						<td>{{column.field}}</td>
						<td>
							<input type="text" class="inputText" style="width: 65px" ng-model="column.comment" />
						</td>
						<td ng-if="column.dbType=='number'||column.dbType=='int'">
							<select ng-if="column.defaultType!='7'" ng-model="column.condition" ng-options="m.value as m.key for m in number_opList"></select>
						</td>
						<td ng-if="column.dbType=='varchar'||column.dbType=='clob'">
							<select ng-if="column.defaultType!='7'" ng-model="column.condition" ng-options="m.value as m.key for m in string_opList"></select>
						</td>
						<td ng-if="column.dbType=='date'">
							<select ng-if="column.defaultType!='7'" ng-model="column.condition" ng-options="m.value as m.key for m in date_opList"></select>
						</td>
						<td>
							<select ng-if="prop.style!='1'" ng-model="column.defaultType" ng-options="m.value as m.key for m in value_sourceList_list"></select>
							<select ng-if="prop.style=='1'" ng-model="column.defaultType" ng-options="m.value as m.key for m in value_sourceList_tree"></select>
						</td>
						<td>
							<select style="width: 120px" ng-if="column.defaultType=='1'&&column.dbType!='date'" ng-change="changeCt(column);" ng-model="column.controllerType" ng-options="m.value as m.key for m in param_ctList"></select>

							<select style="width: 120px" ng-model="column.customDialogCt.id" ng-change="changeCustomDiaologCt(column);" ng-if="column.defaultType=='1'&&column.dbType!='date'&&column.controllerType==0" ng-options="m.id as m.name for m in exitCustomDialogList"></select>
							<select style="width: 120px" ng-model="column.customDialogCt.id" ng-change="changeCustomDiaologCt(column);" ng-if="column.defaultType=='1'&&column.dbType!='date'&&column.controllerType==2" ng-options="m.id as m.name for m in exitSelectorList"></select>
							<select style="width: 120px" ng-model="column.customDialogCt.returnfield" ng-if="column.defaultType=='1'&&column.dbType!='date'&&column.controllerType!=1&&column.customDialogCt.id!=null" ng-options="m.comment as m.field for m in column.customDialogCt.resultfield">
								<option value="">选择返回字段</option>
							</select>
							<i ng-show="column.defaultType=='3'" class="bigger-150 fa fa-question ht-help" ht-tip title="<ul >
								<li>自定义SQL
																<ul>
																	<li>if(map.get('ACTDEFID')!=null){</li>
																	<li>return 'select * from where ACTDEFID like '%'+map.get('ACTDEFID')+'%'' ;</li>
																	<li>}</li>
																	<li>其中的map为系统所封装的一个参数</li>
																	<li>
																		在脚本中使用map.get('ACTDEFID')可以获取表单提交时所携带的ACTDEFID参数值，
																	</li>
																</ul>
															</li>
							</ul>"></i>
							<a class="btn btn-primary btn-xs" ng-show="column.defaultType=='3'" ng-click="selectScript_column(column)">常用脚本</a>
							<textarea ng-model="column.defaultValue" ng-if="(column.defaultType=='2'&&column.dbType!='date')||column.defaultType=='3'" rows="5" cols="20"></textarea>
							<input ng-model="column.defaultValue" ng-if="column.defaultType=='2'&&column.dbType=='date'" validate="{date:true}" type="text" class="inputText wdateTime" ht-date="wdateTime" name="date" />
							<span ng-if="column.defaultType=='2'&&column.dbType=='date'&&column.condition=='BETWEEN'">
								至
								<input ng-model="column.endDate" name="date" validate="{date:true}" type="text" class="inputText wdateTime" ht-date="wdateTime" />
							</span>
						</td>

						<td>
							<a href="javaScript:void(0)" ng-click="ArrayTool.up($index,prop.conditionfield)" class="btn btn-sm   btn-default fa-chevron-up"></a>
							<a href="javaScript:void(0)" ng-click="ArrayTool.down($index,prop.conditionfield)" class="btn btn-sm btn-default fa-chevron-down"></a>
							<a href="javaScript:void(0)" ng-click="ArrayTool.del($index,prop.conditionfield)" class="btn btn-sm btn-default fa-times"></a>
						</td>
					</tr>
				</table>
			</div>
			<div title="返回字段">
				<table class="table-list">
					<tr>
						<th>
							<abbr ht-tip title="字段列名">字段名称</abbr>
						</th>
						<th>
							<abbr ht-tip title="对话框返回数据时的字段KEY">返回名称</abbr>
						</th>
						<th width="25%">
							<span>管理</span>
						</th>
					</tr>
					<tr ng-repeat="column in prop.resultfield">
						<td>
							{{column.field}}
						</td>
						<td>
							<input type="text" class="inputText" ng-model="column.comment" ht-validate="{variable:true}" />
						</td>
						<td>
							<a href="javaScript:void(0)" ng-click="ArrayTool.up($index,prop.resultfield)" class="btn btn-sm btn-default fa-chevron-up"></a>
							<a href="javaScript:void(0)" ng-click="ArrayTool.down($index,prop.resultfield)" class="btn btn-sm btn-default fa-chevron-down"></a>
							<a href="javaScript:void(0)" ng-click="ArrayTool.del($index,prop.resultfield)" class="btn btn-sm btn-default fa-times"></a>
						</td>
					</tr>
				</table>
			</div>
			<div title="排序字段">
				<table class="table-list">
					<tr>
						<th>字段</th>
						<th>排序类型</th>
						<th width="25%">管理</th>
					</tr>
					<tr ng-repeat="column in prop.sortfield">
						<td>{{column.field}}</td>
						<td>
							<select ng-model="column.sortType" ng-options="m.value as m.key for m in sort_typeList"></select>
						</td>
						<td>
							<a href="javaScript:void(0)" ng-click="ArrayTool.up($index,prop.sortfield)" class="btn btn-sm btn-default fa-chevron-up"></a>
							<a href="javaScript:void(0)" ng-click="ArrayTool.down($index,prop.sortfield)" class="btn btn-sm btn-default fa-chevron-down"></a>
							<a href="javaScript:void(0)" ng-click="ArrayTool.del($index,prop.sortfield)" class="btn btn-sm btn-default fa-times"></a>
						</td>
					</tr>
				</table>
			</div>
			<div title="自定义SQL" style="margin-left: 10px;">
				<div class="checkbox">
					<label>
						<input type="checkbox" ng-model="prop.sqlBuildType" ng-true-value="1" ng-false-value="0" />
						自定义SQL
						<i class="bigger-150 fa fa-question ht-help" ht-tip title="<ul >
								<li>自定义SQL
																<ul>
																	<li>if(map.get('ACTDEFID')!=null){</li>
																	<li>return 'select * from where ACTDEFID like '%'+map.get('ACTDEFID')+'%'' ;</li>
																	<li>}</li>
																	<li>其中的map为系统所封装的一个参数</li>
																	<li>
																		在脚本中使用map.get('ACTDEFID')可以获取表单提交时所携带的ACTDEFID参数值，
																		脚本应拼接并返回任意的可执行的sql语句;同时，sql语句字段返回应该包含我们定义的
																		返回字段，和显示字段，通常使用select * from的方式保证需求字段都在这个查询里面
																	</li>
																</ul>
															</li>
							</ul>"></i>
					</label>
					<a class="btn btn-primary btn-xs" ng-click="selectScript_diySql()">常用脚本</a>
					<select style="width: 120px" ng-model="customVar" ng-change="selectVar()">
						<option value="">可选变量</option>
						<option ng-repeat="con in prop.conditionfield"  ng-if="con.defaultType == 1 || con.defaultType == 4"  ng-value="con.field" >{{con.comment}}</option>
					</select>
				</div>
				<textarea ng-model="prop.diySql" rows="10" cols="77"></textarea>
			</div>
		</div>
		<script type="text/javascript">
			$('#tt1').tabs({
				tabPosition : 'top'
			});
		</script>
	</div>
</body>
</html>