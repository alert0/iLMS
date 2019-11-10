<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="omrDialogApp" class="c-white inline-block w100">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
<script type="text/javascript">
var omrDialogApp = angular.module('omrDialogApp', [ 'baseServices' ]);
omrDialogApp.controller('omrDialogCtrl',['$scope','BaseService',function($scope,BaseService){
	
	$scope.returnData=function(){
		return $scope.menuRight;
	};
	//全选
	$scope.selectAllRight=function(){
		$('[ng-model]').each(function(){
			setValToScope($(this),true);
		})
	};
	//反选
	$scope.reverseSelectRight=function(){
		$('[ng-model]').each(function(index){
			setValToScope($(this),!getValByScope($(this)));
		});
	};
	//放弃选择的
	$scope.abandonSelectRight=function(){
		$('[ng-model]').each(function(){
			setValToScope($(this),false);
		})
	};
}]);

</script>
</head>
<body ng-controller="omrDialogCtrl">
<div class="panel">
	<form id="selectOfficeMenu" action="">
		<div class="panel-detail">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td colspan="4">
					    	<a class="link search" href="javascript:;" ng-click="selectAllRight()">全选</a>  || <a class="link search" href="javascript:;" ng-click="reverseSelectRight()">反选</a> || <a class="link search" href="javascript:;" ng-click="abandonSelectRight()">弃选</a>
					</td>
				</tr>
				<tr>
					<th width=100>文件:</td>
					<td>
						<input type="checkbox" id="wjRight"  ng-model="menuRight.wjRight"/>
					</td>
					
					<th width=100>留痕:</td>
					<td><input type="checkbox" id="lhRight"  ng-model="menuRight.lhRight"/></td>
				</tr>
				<tr>	
					<th width=100>不留痕:</td>
					<td><input type="checkbox" id="blhRight"  ng-model="menuRight.blhRight"/></td>
					
					<th width=100>清除痕迹:</td>
					<td><input type="checkbox" id="qchjRight"  ng-model="menuRight.qchjRight"/></td>
				</tr>
				<tr>	
					<th width=100>模版套红:</td>
					<td><input type="checkbox" id="mbthRight"  ng-model="menuRight.mbthRight"/></td>
					
					<th width=100>选择模版:</td>
					<td><input type="checkbox" id="xzmbRight"  ng-model="menuRight.xzmbRight"/></td>
				</tr>
				<tr>	
					<th width=100>手写签名:</td>
					<td><input type="checkbox" id="sxqmRight"  ng-model="menuRight.sxqmRight"/></td>
					
					<th width=100>盖章:</td>
					<td><input type="checkbox" id="gzRight"  ng-model="menuRight.gzRight"/></td>
				</tr>
				<tr>	
					<th width=100>全屏:</td>
					<td><input type="checkbox" id="qpRight"  ng-model="menuRight.qpRight"/></td>
					
					<th width=100>转成PDF:</td>
					<td><input type="checkbox" id="zcpdfRight"  ng-model="menuRight.zcpdfRight"/></td>
				</tr>
				<tr>	
					<th width=100>Ekey盖章:</td>
					<td><input type="checkbox" id="ekeygzRight"  ng-model="menuRight.ekeygzRight"/></td>
					
					<th width=100>PDF盖章:</td>
					<td><input type="checkbox" id="pdfgzRight"  ng-model="menuRight.pdfgzRight"/></td>
				</tr>
			</table>
		</div>		
	</form>
</div>
</body>
</html>