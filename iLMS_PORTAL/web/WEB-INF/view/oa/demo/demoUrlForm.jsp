<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" >
		var app = angular.module('app', ['formDirective','arrayToolService']);
		app.controller("ctrl", [ '$scope', function($scope) {
			
		}]);
		
		
		var defer = $.Deferred();
		
		function saveForm(){
		 	// 保存表单成功
			defer.resolve();
			HT.window.closeEdit();
			// 保存失败
			//defer.reject("保存失败")
			return defer;
		}
		
		</script>
	</head>
	<body ng-controller="ctrl">
			<form name="form" method="post" ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th><span>输入金额:</span></th>
								<td>
									<input class="inputText" type="text" ng-model="jinge"   />
								</td>								
							</tr>
							<tr>								
								<th><span>大写:</span></th>
								<td>
									{{jinge | cnCapital}}
								</td>								
							</tr>
				</table>
				
				
			</form>
		
	</body>
</html>