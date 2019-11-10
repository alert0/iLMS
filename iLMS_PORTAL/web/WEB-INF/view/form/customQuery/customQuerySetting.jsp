<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/system/customQuery/customQueryService.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/customQuery/settingController.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/scriptSelector.js"></script>

</head>
<body class="easyui-layout" ng-app="app" ng-controller="Controller">
	<div region="north" style="height: 60px">
		<div class="toolbar-panel col-md-13 ">
			<div class="buttons" style="margin-left: 5px;">
				<a href="javascript:;" ng-click="save();" class="btn btn-primary btn-sm fa-save">保存</a> <a href="javascript:;" onClick="window.selfDialog.dialog('close');" class="btn btn-primary btn-sm fa-close">关闭</a>
			</div>
		</div>
	</div>
	<div region="west" title="获取字段信息" style="width: 400px;overflow: auto;" collapsible="false">


		<table class="table-list">
			<tr>
				<th width="5%">
					<input type="checkbox" ng-model="selectAll"/>
				</th>
				<th>字段</th>
				<th width="35%">注解</th>
			</tr>
			<tr ng-repeat="column in table.columnList">
				<td>
					<input type="checkbox" ng-model="column.selected" ng-true-value="yes" ng-false-value="no" ht-checked="selectAll" />
				</td>
				<td ng-click="clickColumn(column)">{{column.fieldName}}</td>
				<td ng-click="clickColumn(column)">
					<input type="text" class="inputText" ng-model="column.comment" />
				</td>
			</tr>
		</table>
	</div>
	<div region="center" style="padding-left: 5px; padding-top: 200px;">
		<a href="javascript:;" ng-click="addColumns()" class="btn btn-info fa-arrow-right"></a>

	</div>
	<div region="east" title="字段设置" style="width: 550px;" collapsible="false">
		<div id="tt1" class="easyui-tabs">
			<div title="条件字段">
				<table class="table-list" style="position: relative">
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
						<td>{{column.comment}}</td>
						<td ng-if="column.dbType=='number'||column.dbType=='int'">
							<select ng-model="column.condition" ng-options="m.value as m.key for m in number_opList"></select>
						</td>
						<td ng-if="column.dbType=='varchar'||column.dbType=='clob'">
							<select ng-model="column.condition" ng-options="m.value as m.key for m in string_opList"></select>
						</td>
						<td ng-if="column.dbType=='date'">
							<select ng-model="column.condition" ng-options="m.value as m.key for m in date_opList"></select>
						</td>
						<td>
							<select ng-model="column.defaultType" ng-options="m.value as m.key for m in value_sourceList"></select>
						</td>
						<td>
							<a class="btn btn-primary btn-xs" ng-show="column.defaultType=='3'" ng-click="selectScript_column(column)">常用脚本</a> <i ng-show="column.defaultType=='3'" class="bigger-150 fa fa-question ht-help" ht-tip
								title="<ul >
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
							<textarea rows="4" cols="20" class="inputText" ng-model="column.defaultValue" ng-if="(column.defaultType=='2'&&column.dbType!='date')||column.defaultType=='3'"></textarea>
							<input ng-model="column.defaultValue" ng-if="column.defaultType=='2'&&column.dbType=='date'" validate="{date:true}" type="text" class="inputText wdateTime" ht-date="wdateTime" name="date" /> <span ng-if="column.defaultType=='2'&&column.dbType=='date'&&column.condition=='BETWEEN'"> 至 <input
								ng-model="column.endDate" name="date" validate="{date:true}" type="text" class="inputText wdateTime" ht-date="wdateTime" />
						</td>
						<td>
							<a href="javascript:;" ng-click="ArrayTool.up($index,prop.conditionfield)" class="btn btn-default fa fa-chevron-up"></a> <a href="javascript:;" ng-click="ArrayTool.down($index,prop.conditionfield)" class="btn btn-default fa fa-chevron-down"></a> <a href="javascript:;" ng-click="ArrayTool.del($index,prop.conditionfield)"
								class="btn btn-default fa fa-times"></a>
						</td>
					</tr>
				</table>
			</div>
			<div title="返回字段">
				<table class="table-list" style="position: relative">
					<tr>
						<th>字段名</th>
						<th>显示名</th>
						<th width="25%">管理</th>
					</tr>
					<tr ng-repeat="column in prop.resultfield">
						<td>{{column.field}}</td>
						<td>
							<input type="text" class="inputText" ng-model="column.comment" />
						</td>
						<td>
							<a href="javascript:;" ng-click="ArrayTool.up($index,prop.resultfield)" class="btn btn-default fa fa-chevron-up"></a> <a href="javascript:;" ng-click="ArrayTool.down($index,prop.resultfield)" class="btn btn-default fa fa-chevron-down"></a> <a href="javascript:;" ng-click="ArrayTool.del($index,prop.resultfield)"
								class="btn btn-default fa fa-times"></a>
						</td>
					</tr>
				</table>
			</div>
			<div title="排序字段">
				<table class="table-list" style="position: relative">
					<tr>
						<th>字段名</th>
						<th>排序类型</th>
						<th width="25%">管理</th>
					</tr>
					<tr ng-repeat="column in prop.sortfield">
						<td>{{column.field}}</td>
						<td>
							<select ng-model="column.sortType" ng-options="m.value as m.key for m in sort_typeList"></select>
						</td>
						<td>
							<a href="javascript:;" ng-click="ArrayTool.up($index,prop.sortfield)" class="btn btn-default fa fa-chevron-up"></a> <a href="javascript:;" ng-click="ArrayTool.down($index,prop.sortfield)" class="btn btn-default fa fa-chevron-down"></a> <a href="javascript:;" ng-click="ArrayTool.del($index,prop.sortfield)"
								class="btn btn-default fa fa-times"></a>
						</td>
					</tr>
				</table>
			</div>
			<div title="DIY-SQL">
				  <input type="checkbox" ng-model="prop.sqlBuildType" ng-true-value="1" ng-false-value="0" checked="checked" /> 自定义sql </lable> 
				<i class="bigger-150 fa fa-question ht-help" ht-tip
					title="<ul >
								<li>自定义SQL
									<ul>
										<li>if(map.get('ACTDEFID')!=null){</li>
										<li>return 'select * from where ACTDEFID like '%'+map.get('ACTDEFID')+'%'' ;</li>
										<li>}</li>
										<li>其中的map为系统所封装的一个参数</li>
										<li>
											在脚本中使用map.get('ACTDEFID')可以获取表单提交时所携带的ACTDEFID参数值，
											脚本应拼接并返回任意的可执行的sql语句
										</li>
									</ul>
								</li>
							</ul>"></i>
					<a class="btn btn-primary btn-xs" ng-click="selectScript_diySql()">常用脚本</a> 
					<select style="width: 120px" ng-model="customVar" ng-change="selectVar()">
						<option value="">可选变量</option>
						<option ng-repeat="con in prop.conditionfield"  ng-if="con.defaultType == 1 || con.defaultType == 4" ng-value="con.field" >{{con.comment}}</option>
					</select>
					<textarea ng-model="prop.diySql" rows="10" cols="77"></textarea>
			</div>
		</div>

		<script type="text/javascript">
	    $('#tt1').tabs( {tabPosition : 'top' });
	</script>
	</div>
</body>
</html>