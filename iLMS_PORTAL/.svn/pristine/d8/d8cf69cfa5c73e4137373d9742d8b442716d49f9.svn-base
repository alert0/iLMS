<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/common/util/chineseToPinyin.js"></script>
</style>
<!-- ng js的代码 -->
<script type="text/javascript">
	var app = angular.module('dseApp', [ 'base', 'formDirective' ]);
	app.controller("DSEController", function($scope, $http, $jsonToFormData) {
		$scope.prop = {};//初始化
		$scope.prop.dbType = "mysql";
		$scope.prop.initOnStart = true;
		$scope.prop.enabled = true;

		var id = "${param.id}";

		//获取数据源池，新建才可以选择
		if (id == "") {
			var params = {};
			var url = '${ctx}/system/sysDataSourceDef/getAll.ht';
			$http.post(url, params, {
				transformRequest : $jsonToFormData
			}).success(function(data, status, headers, config) {
				$scope.sysDataSourceDefs = data;
			});
		}

		//如果为编辑模式，先获取对象
		if (id != "") {
			var params = {
				id : id
			};
			var url = '${ctx}/system/sysDataSource/getById.ht';

			$http.post(url, params, {
				transformRequest : $jsonToFormData
			}).success(function(data, status, headers, config) {
				$scope.prop = data;
				$scope.prop.settingJson = JSON.parse(data.settingJson);
			});
		}

		//连接测试
		$scope.checkConnection = function() {
			if (!$scope.form.$valid) {
				$.topCall.error("请正确填写数据源配置");
				return;
			}

			var url = '${ctx}/system/sysDataSource/checkConnection.ht';
			$http.post(url, $scope.prop).success(function(data, status, headers, config) {
				if (data.result == 1) {
					$.topCall.success(data.message);
				} else {
					$.topCall.error(data.message);
				}
			});
		}

		//数据源配置别名跟这里的别名一致
		$scope.changeAlias = function() {
			for (var i = 0; i < $scope.prop.settingJson.length; i++) {
				var attr = $scope.prop.settingJson[i];
				if (attr.name.toLowerCase().indexOf("alias") != -1) {
					attr.value = $scope.prop.alias;
				}
			}
		}

		//改变了数据池id，那么需要输入的属性也变了
		$scope.changeDsId = function(dsId) {
			for (var i = 0; i < $scope.sysDataSourceDefs.length; i++) {
				var def = $scope.sysDataSourceDefs[i];
				if (def.id != dsId) continue;
				$scope.prop.settingJson = JSON.parse(def.settingJson);
				$scope.prop.classPath = def.classPath;
				$scope.prop.initMethod = def.initMethod;
				$scope.prop.closeMethod = def.closeMethod;
				
				//处理配置的初始化值
				$($scope.prop.settingJson).each(function(){
					this.value=this["default"];
				});
			}
		}

		$scope.changeDbType = function() {
			for ( var i in $scope.dbTypeList) {
				var d = $scope.dbTypeList[i];
				if (d.value != $scope.prop.dbType)
					continue;
				for (var i = 0; i < $scope.prop.settingJson.length; i++) {
					var attr = $scope.prop.settingJson[i];
					if (attr.name.toLowerCase().indexOf("url") != -1) {
						attr.value = d.url;
					} else if (attr.name.toLowerCase().indexOf("driver") != -1) {
						attr.value = d.driverName;
					}
				}
			}
		}

		//数据库类型数组
		$scope.dbTypeList = [ {
			value : 'mysql',
			driverName : 'com.mysql.jdbc.Driver',
			url : 'jdbc:mysql://主机:3306/数据库名?useUnicode=true&characterEncoding=utf-8'
		}, {
			value : 'oracle',
			driverName : 'oracle.jdbc.OracleDriver',
			url : 'jdbc:oracle:thin:@主机:1521:数据库实例'
		}, {
			value : 'mssql',
			driverName : 'com.microsoft.sqlserver.jdbc.SQLServerDriver',
			url : 'jdbc:sqlserver://主机:1433;databaseName=数据库名;'
		},{
			value : 'mssql2008',
			driverName : 'com.microsoft.sqlserver.jdbc.SQLServerDriver',
			url : 'jdbc:sqlserver://主机:1433;databaseName=数据库名;'
		} ];

		//普通是否数组
		$scope.yesOrNoList = [ {
			key : '是',
			value : true
		}, {
			key : "否",
			value : false
		} ];
		
		$scope.$on("afterSaveEvent",function(event,data){
			if(data.r){
				window.location.reload();
			}else{
				HT.window.refreshParentGrid();
				HT.window.closeEdit(true,'grid');
			}
		});

	});
</script>
</head>
<body ng-app="dseApp" ng-controller="DSEController">
	<div id="tb" class="toolbar-panel">
		<div class="buttons">
			<a href="javaScript:void(0)" class="btn btn-sm btn-primary fa-save" ng-model="prop" ht-save="save">
				<span>保存</span>
			</a>
			<a href="javascript:HT.window.closeEdit(true,'grid')" class="btn btn-sm btn-primary fa-back">
				<span>返回</span>
			</a>
			<a href="javaScript:void(0)" class="btn btn-sm btn-primary fa-desktop" ng-click="checkConnection();">
				<span>测试连接</span>
			</a>
		</div>
	</div>
	<form name="form">
		<table class="table-form" cellspacing="0">
			<tr>
				<th>名称:</th>
				<td>
					<input type="text" ng-model="prop.name" class="inputText" ht-validate="{required:true}" />
				</td>
			</tr>
			<tr>
				<th>别名(唯一):</th>
				<td>
					<input type="text" ht-pinyin="prop.name" ng-model="prop.alias" ng-change="changeAlias();" class="inputText" ht-validate="{required:true}" <c:if test="${param.id!=null}">disabled</c:if> />
				</td>
			</tr>
			<tr>
				<th>数据源类型:</th>
				<td>
					<select class="inputText" ng-model="prop.dbType" ng-change="changeDbType();" ng-options="m.value as m.value for m in dbTypeList">
					</select>
				</td>
			</tr>
			<tr>
				<th>初始化容器:</th>
				<td>
					<select class="inputText" ng-model="prop.initOnStart" ng-options="m.value as m.key for m in yesOrNoList">
					</select>
				</td>
			</tr>
			<tr>
				<th>是否生效:</th>
				<td>
					<select class="inputText" ng-model="prop.enabled" ng-options="m.value as m.key for m in yesOrNoList">
					</select>
				</td>
			</tr>

			<c:if test="${param.id==null}">
				<tr>
					<th>数据源:</th>

					<td>
						<select class="inputText" ng-model="dsId" ng-change="changeDsId(dsId);changeDbType();changeAlias();" ng-options="m.id as m.name for m in sysDataSourceDefs">
							<option value="">请选择</option>
						</select>
					</td>
				</tr>
			</c:if>

			<tr ng-repeat="attr in prop.settingJson">
				<th>
					<span ng-class="{red:attr.baseAttr==1}">{{attr.comment}}</span>
				</th>

				<td ng-if="attr.name.indexOf('alias')==-1">
					<input ng-if="attr.baseAttr=='1'" ng-model="attr.value" type="text" class="inputText" ht-validate="{required:true}" style="width: 600px" />
					<input ng-if="attr.baseAttr=='0'" ng-model="attr.value" type="text" class="inputText" style="width: 600px" />
					({{attr.type}})
				</td>
				<td ng-if="attr.name.indexOf('alias')!=-1">
					<input ng-if="attr.baseAttr=='1'" ng-model="attr.value" type="text" class="inputText" ht-validate="{required:true}" style="width: 600px" disabled="disabled" />
					<input ng-if="attr.baseAttr=='0'" ng-model="attr.value" type="text" class="inputText" style="width: 600px" disabled="disabled" />
					({{attr.type}})
				</td>
			</tr>
		</table>
	</form>
</body>
</html>