<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/system/persistence/sysObjLog/sysObjLogEditController.js"></script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary fa-save" ng-model="data" ht-save="save"><span>保存</span></a>
					<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
				</div>
			</div>
			<form name="form" method="post" ht-load="getJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th>操作者ID:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.operatorId"   ht-validate="{required:false,maxlength:54}"  />
								</td>								
							</tr>
							<tr>								
								<th>操作人:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.operator"   ht-validate="{required:false,maxlength:765}"  />
								</td>								
							</tr>
							<tr>								
								<th>创建时间:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.createTime" ht-date  ht-validate="{required:false}"  />
								</td>								
							</tr>
							<tr>								
								<th>名称:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.name"   ht-validate="{required:false,maxlength:765}"  />
								</td>								
							</tr>
							<tr>								
								<th>内容:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.content"   ht-validate="{required:false}"  />
								</td>								
							</tr>
							<tr>								
								<th>分类:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.objType"   ht-validate="{required:false,maxlength:765}"  />
								</td>								
							</tr>
							<tr>								
								<th>参数:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.param"   ht-validate="{required:false,maxlength:765}"  />
								</td>								
							</tr>
				</table>
				
				
			</form>
		
	</body>
</html>