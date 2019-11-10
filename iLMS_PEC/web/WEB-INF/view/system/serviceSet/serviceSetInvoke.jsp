<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html ng-app="systemApp">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/bpm/service/serviceSetInvoke.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/bpm/service/serviceSetService.js"></script>
		<script type="text/javascript">
			var serviceSetId;
			$(function(){
				serviceSetId = window.passConf;
				angular.element($("body")).scope().initParams(serviceSetId);
			});
		</script>
		<style>
			.param-div {
				margin-left: 5px;
			}
			.param-div > ul {
			  padding-left: 0;
			  list-style: none;
			}
			.param-div ul > li {
				margin:10px 0 0 10px;
			}
			.param-div li span {
				display: inline-block;
				margin-bottom: -5px;
				margin-right: 5px;
				font-weight: bold;
				text-align:right;
				overflow: hidden;
				white-space: nowrap;
				text-overflow: ellipsis;
				cursor:default;
			}
		</style>
	</head>
	<body style="overflow-y: hidden;" fit="true" ng-controller="serviceSetInvokeCtrl">
		<div class="easyui-layout" style="width: 95%; height: 545px; margin:10px;">
			<div data-options="region:'north'" style="height: 50px;padding:7px;">
				<a href="javascript:;" class="btn btn-primary btn-sm fa-phone" ng-click="invoke()"><span>调用</span></a>
				<i ng-show="invoking" class="fa fa-refresh fa-spin" style="margin-left:10px;"></i>
			</div>
			<div data-options="region:'west',split:true,title:'参数值',collapsible:false" style="width: 280px;" class="param-div">
				<ul>
					<li ng-repeat="param in params" ng-switch="param.type">
						<span ht-tip="{content:{text:param.name}}">{{param.name}}</span>
						<input type="text" ng-switch-when="string" class="inputText" ng-model="param.val"/>
						<input type="number" ng-switch-when="number" class="inputText" ng-model="param.val"/>
						<input type="text" ht-date-format="{'currentTime':true,'exp':'yyyy-MM-dd HH:mm:ss'}" 
							   ng-switch-when="date" class="inputText" ng-model="param.val"/>
					</li>
				</ul>
			</div>
			<div data-options="region:'center',title:'调用结果'" style="padding: 10px">
				<div ng-show="result.type==1" class="alert alert-info">
					<span class="label label-danger">调用失败</span>
					<textarea style="margin-top:10px;">{{result.message}}</textarea>
				</div>
				<div ng-show="result.type==2" class="alert alert-info">
					<span class="label label-warning">调用成功</span>
					<p style="margin-top:10px;">接口无返回值</p>
				</div>
				<div ng-show="result.type==3" class="alert alert-info">
					<span class="label label-success">调用成功</span>
					<textarea class="form-control" style="margin-top:10px;height: 350px">{{result.message | json}}</textarea>
				</div>
			</div>
		</div>
	</body>
</html>