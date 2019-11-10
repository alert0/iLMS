<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<%@include file="/commons/include/zTree.jsp"%>
		<script type="text/javascript" src="${ctx}/js/common/customform/directiveTpl.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/system/sysUserParams/sysUserParamsEditController.js"></script>
	</head>
	<body ng-controller="ctrl" >
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-sm btn-primary fa-save" ng-model="data" ng-click="save()"><span>保存</span></a>
				</div>
			</div>
			<form name="form" method="post">
				<table class="table-form"  cellspacing="0" >
						<tr ng-repeat="sysParam in sysParams track by $index">								
							<th>{{sysParam.name}}:</th>
							<td>
								<div ng-if="sysParam.ctlType=='input'">
									<input  class="inputText" type="text" ng-model="sysParam.value"   ht-validate="{required:false,maxlength:150}"  />
								</div>
								<div ng-if="sysParam.ctlType=='dic'" class="inputText">
									<div ht-dic="sysParam.value" dickey="{{sysParam.json.dicItem}}" desc="字段一" type="text" ng-model="sysParam.value"  ht-validate="{required:false}"></div>
								</div>
								<div ng-if="sysParam.ctlType=='select'">
									<select class="inputText" ng-model="sysParam.value" ng-options="selectItem.value as selectItem.key for selectItem in sysParam.json " ht-validate="{required:false,maxlength:150}" ></select>
								</div>
								<div ng-if="sysParam.ctlType=='checkbox'">
									<label ng-repeat="checkboxItem in sysParam.json"><input type="checkbox" name="checkbox{{sysParam.alias}}"  ht-checkbox ng-model="sysParam.value"  value="{{checkboxItem.key}}"/>{{checkboxItem.key}}   </label>
								</div>
								<div ng-if="sysParam.ctlType=='radio'">
									<label ng-repeat="radioItem in sysParam.json"><input type="radio" name="radio{{sysParam.alias}}" ng-model="sysParam.value"  value="{{radioItem.key}}"/>{{radioItem.key}}   </label>
								</div>
								<div ng-if="sysParam.ctlType=='date'">
									<input  class="inputText date" type="text" ht-date ng-model="sysParam.value"  ht-validate="{required:false,maxlength:150}" />
								</div>
								<div ng-if="sysParam.ctlType=='number'">
									<input  class="inputText" type="text" ng-model="sysParam.value"   ht-validate="{required:false,maxlength:150,number:true}"  />
								</div>
								<div ng-if="sysParam.ctlType=='customdialog'">
									<input class="inputText" type="text" ng-model="sysParam.value" disabled="disabled"  ht-validate="{required:false,maxlength:150}"  />
                					<a class="btn btn-primary btn-xs" ng-click="showCustomDialog(sysParam,sysParam.json.alias,sysParam.json.resultField,sysParam.json.isSingle)">选择</a>
								</div>
							</td>								
						</tr>
				</table>
				
				
			</form>
		
	</body>
</html>