<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.hotent.base.core.web.RequestUtil"%>
<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="java.util.Map"%>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f"   uri="http://www.jee-soft.cn/functions" %>
<!DOCTYPE html>
<html ng-app="app">
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=EDGE">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<f:link href="base.css"></f:link>
<f:link href="list.css"></f:link>
<f:link href="bootstrap.min.css"></f:link>
<f:link href="font-awesome.min.css"></f:link>
<link rel="stylesheet" href="${ctx}/js/lib/jqGrid/ui.jqgrid.css">
<link rel="stylesheet" href="${ctx}/js/lib/jqGrid/style.min.css">

<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/angular/angular.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jquery/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/js/system/query/queryViewShow.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jqGrid/jquery.peity.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jqGrid/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jqGrid/jquery.jqGrid.src.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery.base64.js"></script>

<script type="text/javascript" src="${ctx}/js/common/service/BaseService.js"></script>
<script type="text/javascript" src="${ctx}/js/common/service/FormService.js"></script>
<script type="text/javascript" src="${ctx}/js/common/form/FormDirective.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/customDialog/showListController.js"></script>
<script type="text/javascript" src="${ctx}/js/common/util/util.js"></script>
<script type="text/javascript">
var __ctx='${ctx}';
<%
Map map = RequestUtil.getParameterValueMap(request, true, false);
Object json = JSON.toJSON(map);
%>
var __param=<%=json%>;
</script>

<script type="text/javascript">
	$(function(){
		$("#panelbody").height($("customBody").height()-$("#panelTop").height()-5);
		setTimeout(function(){
			$("#searchForm input[type='text']").each(function(){
				$(this).keydown( function(e) {
				    var key = window.event?e.keyCode:e.which;
				    if(key.toString() == "13"){
				    	$('a.fa-search').click();
				        return false;
				    }
				});
			});
		},500);
	})
	
	jQuery.extend($.jgrid.defaults,{
		styleUI:"Bootstrap",
		emptyrecords: "没有任何数据",
		rowNum:true,recordtext:"共{2}条",
		viewrecords:true}
	);
	
	var jqGridParam = {
		url : "getListData?dialog_alias_=${param.dialog_alias_}",
		postData:__param,
		datatype : "json",
		prmNames : {
			page : "page",
			rows : "pageSize",
			sort : "sortField",
			order : "orderSeq",
		},
		mtype : "post",
		viewrecords : true
	};

	function getResult() {
		var list = [];
		var scope = AngularUtil.getScope();
		if (scope.customDialog.selectNum == 1) {//单选
			scope.selectedList=[];
			if(typeof(scope.activeRow) != "undefined"){
				scope.selectedList.push(scope.activeRow);
			}
		}
		
		$(scope.selectedList).each(function() {
			var temp = this;//因为temp中会包含显示字段的信息，所以要根据resultfield来只获取返回字段的信息
			
			var json = {};
			$(scope.customDialog.resultfield).each(function() {
				json[this.comment] = temp[this.field.toUpperCase()];
			});
			list.push(json);
		});
		return list;
	}
</script>

<style type="text/css">
	.input-width{
		width: 100px;
	}
	table.creteria-table{
		border: 1px lightgray solid;
	    border-radius: 5px;
	    padding: 4px;
	    border-collapse: separate;
	    margin-bottom:5px;
	}
</style>

</head>
<body ng-controller="ctrl" ng-init="init()" id="customBody" ng-model="customDialog" style="overflow: hidden;" >
	<!-- 顶部查询操作 -->
	<div id="panelTop" class="toolbar-search ">
			<div class="toolbar-head">
				<!-- 顶部按钮 -->
				<div class="buttons">
					<a class="btn btn-primary btn-sm fa-search" id="btnSearch" href="javaScript:void(0)">
						<span>搜索</span>
					</a>                                                       
					<a href="javaScript:void(0)" id="btnResetCondition" class="btn btn-sm btn-primary fa-rotate-left">
						<span>重置</span>
					</a>
					<a href="javaScript:void(0)" ng-if="dynamicCondition&&dynamicCondition.length>0" ng-click="addCreteria()" class="btn btn-sm btn-info fa-add">
						<span>添加查询条件</span>
					</a>
				</div>
			</div>
			<!-- 查询字段 -->
			<div class="toolbar-body">
				<form id="searchForm" class="search-form">
					<ul>
						<li ng-repeat="item in customDialog.conditionfield |filter: {defaultType:'1'}">
							<span>{{item.comment}}:</span>
							<input ng-if="item.controllerType=='1'" class="inputText input-width" type="text" ng-model="item.value">
						</li>
					</ul>
					<div style="margin:0 10px;">
						<table class="creteria-table" ng-repeat="creteria in creteriaList">
							<tbody>
							<tr>
								<td width="150">
									<select style="width:120px;" ng-model="creteria.field" ng-change="switchDynamic(creteria)" 
											ng-options="dynamic.field as dynamic.comment for dynamic in dynamicCondition">
									</select>
								</td>
								<td width="120">
									<select style="width:80px;" ng-model="creteria.condition"
											ng-options="condition.value as condition.key for condition in creteria.conditionOptions">
									</select>
								</td>
								<td width="180">
									<input class="form-control" ng-model="creteria.value" style="height:25px;" type="text"/>
								</td>
								<td width="60" align="center">
									<a class="btn btn-danger btn-sm fa fa-delete" ng-click="removeCreteria(creteria)" style="border-radius:10px;" title="移除"></a>
								</td>
							</tr>
							</tbody>
						</table>
					</div>
				</form>
			</div>
	</div>
	<div id="panelbody" >
		<div style="float: left; margin-left: 0px;">
			<table id="gridList"></table>
			<div id="pagerNav"></div>
		</div>
		<div id="divSelected" ng-if="customDialog.selectNum!=1" style="float: right; width: 20%; overflow:auto; margin-right: 2px;">
			<table style="float: right;" class="table-list" cellspacing="0">
				<tr>
					<th>{{customDialog.displayfield[0].comment}}
					</th>
					<th>
						操作
						<a href="javascript:javaScript:void(0)" ng-click="clear()" class="btn btn-sm fa-close"></a>
					</th>
				</tr>
				<tr ng-repeat="item in selectedList track by $index">
					<td>{{item[customDialog.displayfield[0].field.toUpperCase()]}}</td>
					<td>
						<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.del($index,selectedList)" class="btn btn-sm btn-default fa-delete"></a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>