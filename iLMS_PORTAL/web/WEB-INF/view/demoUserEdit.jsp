<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" >
		var app = angular.module('app', ['formDirective','arrayToolService']);
		app.controller("ctrl", [ '$scope', function($scope) {
			
			
		}]);
		
		
		
		</script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary fa-save" ng-model="data" ht-save="save"><span>保存</span></a>
					<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
				</div>
			</div>
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