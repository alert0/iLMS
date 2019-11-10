<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
<script type="text/javascript"
	src="${ctx}/js/common/customform/directiveTpl.js"></script>
<script type="text/javascript"
	src="${ctx}/js/platform/system/sysDataSource/sysDataSourceService.js"></script>
<script type="text/javascript"
	src="${ctx}/js/platform/bo/ent/bOEntExtEditController.js"></script>
<script type="text/javascript">
	var packageId = "${param.packageId}";
	var id = "${param.id}";
</script>
</head>
<body ng-controller="ctrl">
	<div>
		<!-- 顶部按钮 -->
		<div class="toolbar-panel" ng-show="isStepCreate">
			<div class="buttons">
				<a class="btn btn-primary btn-sm fa-save" ng-model="data"  config='{"afterSave":"afterSave"}'
					ht-save="bOEnt/save"> <span>保存</span>
				</a> <a class="btn btn-primary btn-sm fa-back"
					href="javascript:HT.window.closeEdit(true,'grid')"> <span>返回</span>
				</a>
			</div>
		</div>
		<form name="form" ht-load="bOEnt/getObject?id=${param.id}"
			ng-model="data">
			<table class="table-form" cellspacing="0">
				<tr>
					<th>分类:</th>
					<td style="width: 400px">
						<div type="text" ht-dic="data.packageId"
							url="${ctx }/system/sysType/getTypesByKey?typeKey=ENT_TYPE"
							key-name="id"></div>
					</td>
				</tr>
				<tr>
					<th><span>描述:</span> <span class="required">*</span></th>
					<td><input class="inputText" type="text" ng-model="data.desc"
						ht-validate="{required:true,maxlength:48}" /></td>
					<th><span>名称:</span> <span class="required">*</span></th>
					<td><input ng-if="!data.id" class="inputText" type="text"
						ng-model="data.name"
						ht-validate="{required:true,fields:true,isexist:'bOEnt/isExist'}"
						ht-pinyin="data.desc" /> <input ng-if="data.id" class="inputText"
						disabled="disabled" type="text" ng-model="data.name" /> 
						<input type="checkbox" ng-true-value="enabled" ng-false-value="forbidden"
						ng-model="data.status" ng-show="false"/></td>
				</tr>

				<tr>
					<th><span>数据源:</span></th>
					<td ng-if="!data.id">
						<div ht-ds-selector="data.dsName"></div>
					</td>
					<td ng-if="data.id">{{data.dsName}}</td>
					<th><span>表名:</span></th>
					<td><input ng-if="!data.id" class="inputText" type="text"
						ng-model="data.tableName" /> <span ng-if="data.id">{{data.tableName}}</span><a type="button"
						class="btn btn-primary fa fa-search" ng-click="getExternalTable()">查询</a>
					</td>
				</tr>
				<tr>
					<th ng-if="!data.id"><span>选择外部表:</span></th>
					<td ng-if="!data.id"><select class="inputText"
						style="width: 320px" ng-model="data.tableName"
						ng-options="m.name as (m.name+'('+m.comment+')') for m in externalTable"
						ng-change="tableChange()">
							<option value="">--请选择--</option>
					</select></td>
					<th><span>相关配置</span></th>
					<td>
						<table>
							<tr>
								<th>主键:</th>
								<td><input class="inputText" type="text"
									disabled="disabled" ng-model="data.pk"
									ht-validate="{required:true}" /></td>
							</tr>
							<tr>
								<th>外键:</th>
								<td>{{data.fk}} <select ng-model="data.fk"
									ng-options="m.name as m.desc for m in data.attributeList"
									class="inputText" ng-if="!data.id">
								</select>
								</td>
							</tr>
							<tr>
								<th>主键类型:</th>
								<td ng-init="data.pkType='varchar'"><input type="radio"
									name="pkType" value="varchar" ng-model="data.pkType" /> 字符串 <input
									type="radio" name="pkType" value="number"
									ng-model="data.pkType" /> 数字</td>
							</tr>
						</table>

					</td>
				</tr>

			</table>

			<table class="table-list" cellspacing="0">
				<thead>
					<tr>
						<td colspan="6">字段信息</td>
					</tr>
				</thead>
				<tr>
					<th>注释</th>
					<th>名称</th>
					<th>是否必填</th>
					<th>数据类型</th>
					<th>属性长度</th>
					<th>默认值</th>
				</tr>
				<tr ng-repeat="attr in data.attributeList">
					<td>{{attr.desc}}</td>
					<td>{{attr.name}}</td>
					<td>{{attr.isRequired==1 ? '是':'否'}}</td>
					<td>
						<div ng-switch="attr.dataType">
							<div ng-switch-when="varchar">字符串</div>
							<div ng-switch-when="number">数字型</div>
							<div ng-switch-when="date">日期型</div>
							<div ng-switch-when="clob">大文本</div>
						</div>
						<div ng-switch="attr.fcolumnType">
							<div ng-switch-when="datetime" ng-init="attr.format='yyyy-MM-dd HH:mm:ss'"></div>
							<div ng-switch-when="time" ng-init="attr.format='HH:mm:ss'"></div>
							<div ng-switch-when="date" ng-init="attr.format='yyyy-MM-dd'"></div>
							<div ng-switch-when="timestamp" ng-init="attr.format='yyyy-MM-dd HH:mm:ss'"></div>
						</div>
					</td>
					<td>{{attr.fcolumnType==null || attr.fcolumnType=='' ? attr.attrLength:attr.fcolumnType}}</td>
					<td><input  class="inputText" type="text" ng-model="attr.defaultValue"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>