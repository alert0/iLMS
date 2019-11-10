<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/sys/properties/sysProperties/sysPropertiesEditController.js"></script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-sm  btn-primary fa-save" ng-model="data" ht-save="save"><span>保存</span></a>
					<a class="btn btn-sm  btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
				</div>
			</div>
			<form name="form" method="post" ht-load="getJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th>参数名:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.name"   ht-validate="{required:true,maxlength:150}"  />
								</td>								
							</tr>
							<tr>								
								<th>别名:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.alias"   ht-validate="{required:true,maxlength:150}"  />
								</td>								
							</tr>
							<tr>								
								<th>分组:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.group"   ht-validate="{required:true,maxlength:150}"  />
									
									<select ng-model="data.category" ng-options="item for item in data.categorys" ng-change="changeGroup()">
										<option value="">-- 请选择 --</option>
									</select>
									
								</td>								
							</tr>
							<tr>								
								<th>参数值:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.value"   ht-validate="{required:true,maxlength:300}"  />
								</td>								
							</tr>
							<tr>								
								<th>加密存储:</th>
								<td>
									<span  ht-boolean="1/0" text="参数加密存储" ng-model="data.encrypt" ht-tip title="参数加密存储"></span>
								</td>								
							</tr>
							<tr>								
								<th>描述:</th>
								<td>
									<textarea rows="3" cols="80" ng-model="data.description"></textarea>
								</td>								
							</tr>
				</table>
				
				
			</form>
		
	</body>
</html>