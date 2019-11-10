<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<title>流程复制</title>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/flow/defCopyCtrl.js"></script>
	</head>
	<body ng-controller="ctrl">
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary btn-sm fa-save" ng-model="data" ht-save="copyDef"><span>保存</span></a>
					<a class="btn btn-primary btn-sm fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
				</div>
			</div>
			<form name="form" method="post" ng-model="data">
				<table class="table-form"  cellspacing="0" >
					<tr>								
						<th>流程名称:</th>
						<td>
							${param.name }
						</td>
							<th>复制后的流程名称:</th>
						<td>
							<input class="inputText" type="text" ng-model="data.name" ht-validate="{required:true,maxlength:300}"  />
						</td>									
					</tr>
					<tr>								
						<th>流程key:</th>
						<td>
							${param.defKey }
						</td>
						<th>复制后的流程key:</th>
						<td>
							<input class="inputText" type="text" ng-model="data.defKey"  ht-pinyin="data.name" ht-validate="{required:true,maxlength:300,flowkeyrule:true}"  />
						</td>									
					</tr>
				</table>
					<input type="hidden" ng-model="data.defId"/>
			</form>
	</body>
</html>