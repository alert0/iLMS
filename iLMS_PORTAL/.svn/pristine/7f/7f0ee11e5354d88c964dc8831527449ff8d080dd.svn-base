<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<f:link href="formEdit.css"></f:link>
		<f:link href="component.css"></f:link>
		<script type="text/javascript" src="${ctx}/js/platform/system/sysParams/sysParamsEditController.js"></script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary btn-sm fa-save" ng-model="data" ht-save="save"><span>保存</span></a>
					<a class="btn btn-primary btn-sm fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
				</div>
			</div>
			<form name="form" method="post"  ht-load="<c:if test='${!empty param.id}'>getJson?id=${param.id}</c:if>"  ht-load="<c:if test='${!empty param.id}'>getJson?id=${param.id}</c:if>"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th>参数名:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.name"   ht-validate="{required:true,maxlength:300}"  />
								</td>								
							</tr>
							<tr>								
								<th>参数key:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.alias"   ht-validate="{required:true,varirule:true,maxlength:150}"
									<c:if test='${!empty param.id}'>readonly=true</c:if>  />
								</td>								
							</tr>
							<tr>								
								<th>参数类型:</th>
								<td>
									<select class="inputText" ng-model="data.type"  ht-validate="{required:true,maxlength:150}">
										<option value="1">用户参数</option>
										<option value="2">组织参数</option>
									</select>
								</td>								
							</tr>
							<tr>								
								<th>数据来源:</th>
								<td>
									<select class="inputText" ng-model="data.ctlType"  ht-validate="{required:true,maxlength:150}">
										<option value="input">手动录入</option>
										<option value="dic">数据字典</option>
										<option value="select">下拉框</option>
										<option value="checkbox">复选框</option>
										<option value="radio">单选按钮</option>
										<option value="date">日期</option>
										<option value="number">数字</option>
										<option value="customdialog">自定义对话框</option>
									</select>
								</td>								
							</tr>
							<tr ng-if="data.ctlType=='dic'">								
								<th>自定义选择项:</th>
								<td>
									<input  class="inputText" ht-category="DIC" ng-model="data.json.dicItem" />
								</td>								
							</tr>
							<tr ng-if="data.ctlType=='select'||data.ctlType=='checkbox'||data.ctlType=='radio'">								
								<th>自定义选择项:</th>
								<td>
									<a class="btn btn-primary btn-sm" style="margin-bottom:5px;" href="javascript:;" ng-click="addItem()" >添加项</a>
									<table>
										<tr ng-repeat="item in data.json track by $index">
											<th>选项：</th>
											<td>
												<input class="inputText" ng-model="item.key" ht-validate="{required:true,maxlength:150}"  ></input>
											</td>
											<th>值：</th>
											<td>
												<input class="inputText" ng-model="item.value" ht-validate="{required:true,maxlength:150}"></input>
											</td>
											<th colspan="2"  align="left">
												<a href="javaScript:void(0)" ng-click="ArrayTool.up($index,data.json)" class="btn btn-sm btn-default fa-chevron-up"></a>
												<a href="javaScript:void(0)" ng-click="ArrayTool.down($index,data.json)" class="btn btn-sm btn-default fa-chevron-down"></a>
												<a href="javaScript:void(0)" ng-click="ArrayTool.del($index,data.json)" class="btn btn-sm btn-default fa-delete"></a>
											</th>
										</tr>
									</table>
								</td>								
							</tr>
							
							<tr ng-if="data.ctlType=='customdialog'">
								<th>自定义对话框</th>
								<td>
									<select ht-validate="{required:true}" ng-model="data.json.alias" ng-options="m.alias as m.name for m in customDialogs" class="inputText">
									</select>
								</td>
								<th>返回字段</th>
								<td>
									<select ht-validate="{required:true}" ng-model="data.json.resultField" ng-options="m.comment as m.comment for m in customDialog.resultfield" class="inputText">
									</select>
									<span>
										<input class="radio-hide" ng-model="data.json.isSingle" type="checkbox" name="selectorIsSingle" vlaue="false" id="selectorIsSingle" />
										<label for="selectorIsSingle" style="margin-bottom:5px;" class="btn label-sm">是否单选</label>
									</span>
								</td>
							</tr>
				</table>
				
				
			</form>
		
	</body>
</html>