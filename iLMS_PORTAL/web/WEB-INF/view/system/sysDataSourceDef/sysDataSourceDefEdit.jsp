<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/ngEdit.jsp"%>
<!-- ng js的代码 -->
<script type="text/javascript">
	var app = angular.module('dspeApp', [ 'base', 'arrayToolService', 'formDirective' ]);

	app.controller("DSPController", [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayToolService) {
		$scope.ArrayTool = ArrayToolService;

		$scope.prop = {};//初始化prop
		$scope.prop.isSystem = false;
		$scope.prop.settingJson=[];
		
		var id = "${param.id}";
		//如果id不为空，获取初始化数据,利用发请求的方式
		if (id != "") {
			var params = {
				id : id
			};
			var url = '${ctx}/system/sysDataSourceDef/getById.ht';
			baseService.postForm(url, params).then(function(data) {
				$scope.prop = data;
				$scope.prop.settingJson = JSON.parse(data.settingJson);
			});
		}

		//获取字段信息
		$scope.getAttr = function(classPath) {
			var url = '${ctx}/system/sysDataSourceDef/getSetterFields.ht';
			var params = {
				classPath : classPath
			};
			baseService.postForm(url, params).then(function(data) {
				$scope.prop.settingJson = data;
			});
		}

		//删除字段
		$scope.deleteAttr = function(idx) {
			$scope.prop.settingJson.splice(idx, 1);
		}

		//删除字段
		$scope.addField = function() {
			var json = {};
			json.isAdd = true;
			$scope.prop.settingJson.push(json);
		}

		//是否系统默认的model数组
		$scope.isSystemList = [ {
			key : '是',
			value : true
		}, {
			key : "否",
			value : false
		} ];

		//baseAttr的model数组
		$scope.baseAttrList = [ {
			key : '是',
			value : '1'
		}, {
			key : "否",
			value : '0'
		} ];

	} ]);
</script>
</head>
<body ng-app="dspeApp" ng-controller="DSPController">
	<div id="tb" class="toolbar-panel">
		<div class="buttons">
			<a href="javaScript:void(0)" class="btn btn-sm  btn-primary fa-save" ng-model="prop" ht-save="save">
				<span>保存</span>
			</a>
			<a href="javascript:HT.window.closeEdit(true,'grid')" class="btn btn-sm btn-primary fa-back">
				<span>返回</span>
			</a>
		</div>
	</div>
	<form name="form">
		<table cellspacing="0" class="table-form">
			<tr>
				<th>数据源名称:</th>
				<td>
					<input type="text" ng-model="prop.name" class="inputText" ht-validate="{required:true}" />
				</td>
			</tr>
			<tr>
				<th>初始化方法:</th>
				<td>
					<input type="text" ng-model="prop.initMethod" class="inputText" ht-validate="{required:false}" />
				</td>
			</tr>
			<tr>
				<th>关闭方法:</th>
				<td>
					<input type="text" ng-model="prop.closeMethod" class="inputText" ht-validate="{required:false}" />
					(格式：classPath|method--》eg:org.logicalcobwebs.proxool.ProxoolFacade|shutDown)
				</td>
			</tr>
			<tr>
				<th>是否系统默认的:</th>
				<td>
					<select class="inputText" ng-model="prop.isSystem" ng-options="m.value as m.key for m in isSystemList">
					</select>
				</td>
			</tr>
			<tr>
				<th>数据源全路径:</th>
				<td>
					<input type="text" id="classPath" name="classPath" ng-model="prop.classPath" class="inputText" ht-validate="{required:false}" />
					<c:if test="${param.id==null}">
						<a href="javaScript:void(0)" class="btn btn-sm btn-primary fa-fax" ng-click="getAttr(prop.classPath)">
							<span>获取属性</span>
						</a>
					</c:if>
					<a href="javaScript:void(0)" class="btn btn-sm btn-primary fa-add" ng-click="addField()">
						<span>添加属性</span>
					</a>
				</td>
			</tr>
			<table class="table-form"  ng-if="prop.settingJson!=null">
				<tr>
					<th>名称</th>
					<th>描叙</th>
					<th>参数类型</th>
					<th>是否必填</th>
					<th>默认值</th>
					<th>管理</th>
				</tr>
				<tr ng-repeat="field in prop.settingJson">
					<td ng-show="!field.isAdd">{{field.name}}</td>
					<td ng-show="field.isAdd">
						<input type="text" ng-model="field.name" class="inputText" />
					</td>
					<td>
						<input type="text" value="{{field.comment}}" ng-model="field.comment" class="inputText" />
					</td>
					<td ng-show="!field.isAdd">{{field.type}}</td>
					<td ng-show="field.isAdd">
						<select ng-model="field.type" class="inputText">
							<option value="int">int</option>
							<option value="boolean">boolean</option>
							<option value="java.lang.String">java.lang.String</option>
						</select>
					</td>
					<td>
						<select class="inputText" ng-model="field.baseAttr" ng-options="m.value as m.key for m in baseAttrList">
						</select>
					</td>
					<td>
						<input type="text" ng-model="field.default" class="inputText" />
					</td>
					<td>
						<a href="javaScript:void(0)" ng-show="!field.baseAttr||field.baseAttr=='0'" class="btn fa-remove" ng-click="deleteAttr($index)">删除</a>
						<a href="javaScript:void(0)" class="btn fa-arrow-up" ng-click="ArrayTool.up($index,prop.settingJson)">上移</a>
						<a href="javaScript:void(0)" class="btn fa-arrow-down" ng-click="ArrayTool.down($index,prop.settingJson)">下移</a>
					</td>
				</tr>
			</table>
		</table>
	</form>
</body>
</html>