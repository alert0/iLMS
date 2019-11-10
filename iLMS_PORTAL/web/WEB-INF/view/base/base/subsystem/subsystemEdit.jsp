<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/base/base/subsystem/subsystemEditController.js"></script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-sm btn-primary fa-save" ng-model="data" ht-save="save"><span>保存</span></a>
					<a class="btn btn-sm btn-primary fa-back" onClick="HT.window.closeEdit();"><span>返回</span></a>
				</div>
			</div>
			<form name="form" method="post" ht-load="getJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
						<tr>								
							<th>系统名称:</th>
							<td>
								<input class="inputText" type="text" ng-model="data.name"   ht-validate="{maxlength:150}"  />
							</td>								
						</tr>
						<tr>								
							<th>系统别名:</th>
							<td>
								<input class="inputText" type="text" ng-model="data.alias"  ht-validate="{required:true,maxlength:150}"  />
							</td>								
						</tr>
						<tr>								
							<th>logo地址:</th>
							<td>
								<input class="inputText" type="text" ng-model="data.logo"   ht-validate="{required:false,maxlength:150}"  />
							</td>								
						</tr>
						<tr>								
							<th>是否可用:</th>
							<td>
								<span  ht-boolean="1/0" text="是否可用" ng-model="data.enabled" ht-tip title="是否可用"></span>
							
							</td>								
						</tr>
						<tr>								
							<th>主页地址:</th>
							<td>
								<input class="inputText" type="text" ng-model="data.homeUrl"   ht-validate="{required:true,maxlength:300}"  />
							</td>								
						</tr>
						<tr>								
							<th>基础地址:</th>
							<td>
								<input class="inputText" type="text" ng-model="data.baseUrl"   ht-validate="{required:true,maxlength:150}"  />
							</td>								
						</tr>
					
						<tr>								
							<th>备注:</th>
							<td>
								<textarea class="inputText" type="text" ng-model="data.memo"   ht-validate="{maxlength:600}"  ></textarea>
							</td>								
						</tr>
						
				</table>
				
				
			</form>
		
	</body>
</html>