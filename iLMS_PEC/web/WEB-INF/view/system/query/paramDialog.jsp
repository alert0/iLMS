<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript">
	var app = angular.module("app", [ 'base', 'formDirective', 'arrayToolService' ]);
	app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayToolService) {

		$scope.init = function() {
			$scope.param = window.passConf;
		};

	} ]);

	function getResult() {
		var mapParam = {};
		var urlParam = "";
		$("[name]").each(function() {
			var val = $(this).val();
			var name = $(this).attr("name");
			if (!val) {
				return;
			}

			if (urlParam) {
				urlParam += "&";
			}
			urlParam += name + "=" + val;
			mapParam[$(this).attr("name")] = val;
		});
		return {
			url : urlParam,
			map : mapParam
		};
	}
</script>
</head>
<body ng-controller="ctrl" ng-init="init()">
	<table class="table-form" cellspacing="0">
		<tr ng-repeat="(key,val) in param">
			<th style="width:30%">{{val}}</th>
			<td>
				<input class="inputText" type="text" name="{{key}}">
			</td>
		</tr>
	</table>
</body>
</html>