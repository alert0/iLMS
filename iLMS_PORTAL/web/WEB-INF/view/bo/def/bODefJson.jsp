<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript">
	var boCode = "${param.boCode}";

	var app = angular.module("app", [ 'base', 'formDirective', 'arrayToolService' ]);
	app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayToolService) {
		$scope.init = function() {
			baseService.post(__ctx + "/bo/def/bODef/getBoJson?boCode=" + boCode).then(function(data) {
				$scope.json = data;
			});
		};
	} ]);
</script>
</head>
<body ng-controller="ctrl" ng-init="init()">
	<pre>{{json | json}}</pre>
</body>
</html>