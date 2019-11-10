<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/base/base/sysResource/sysResourceEditController.js"></script>
		<script>
		 $(function() {
			if(parent.parent.getTemplateResourceUrl){
				var scope = AngularUtil.getScope();
				setTimeout(function(){
					scope.$apply(function(){
						scope.data.defaultUrl = parent.parent.getTemplateResourceUrl();
						scope.data.enableMenu = "1";
						scope.data.hasChildren = "0";
						scope.isUrlDisabled = true;
					});
				},200);
			}
		 });
		</script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-sm btn-primary fa-save" ng-model="data" ht-save="save" config='{"afterSave":"afterSave"}'><span>保存</span></a>
					<a class="btn btn-sm  btn-primary fa-back" onClick="window.parent.parent.location.reload();"><span>返回</span></a>
				</div>
			</div>
			<form name="form" method="post" ht-load="getJson?id=${param.id}&parentId=${param.parentId}&systemId=${param.systemId}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							
							<tr>								
								<th>资源名:</th>
								<td>
									<input class="inputText"  type="text" ng-model="data.name"   ht-validate="{required:true,maxlength:150}"  />
								</td>								
							</tr>
							<tr>								
								<th>别名:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.alias"   ht-validate="{required:true,maxlength:150}"  />
								</td>								
							</tr>
							<tr>								
								<th>默认地址:</th>
								<td>
									<input class="inputText input-width-80" type="text" ng-model="data.defaultUrl" ht-validate="{required:false,maxlength:150}"  ng-disabled="isUrlDisabled" />
								</td>								
							</tr>
							<tr>								
								<th>显示到菜单:</th>
								<td>
									<input type="radio"  ng-model="data.enableMenu"  value="1" />是
									<input type="radio"  ng-model="data.enableMenu"  value="0" />否
								</td>								
							</tr>
							<tr>								
								<th>是否有子节点:</th>
								<td>
									<input type="radio"  ng-model="data.hasChildren"  ng-value="1" />是
									<input type="radio"  ng-model="data.hasChildren"  ng-value="0" />否
								</td>								
							</tr>
							<tr style="display: none;">								
								<th>默认展开:</th>
								<td>
									<input type="radio"  ng-model="data.opened"  value="1" />是
									<input type="radio"  ng-model="data.opened"  value="0" />否
								</td>								
							</tr>
							<tr>								
								<th>图标:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.icon"   ht-validate="{required:false,maxlength:150}" />
								</td>								
							</tr>
							<tr>								
								<th>打开新窗口:</th>
								<td>
									<input type="radio"  ng-model="data.newWindow"  value="1" />是
									<input type="radio"  ng-model="data.newWindow"  value="0" />否
								</td>								
							</tr>
							<tr>								
								<th>排序:</th>
								<td>
									<input class="inputText input-width-5" type="text"  ng-model="data.sn"   ht-validate="{required:false,number:true}"  />
								</td>								
							</tr>
				</table>		
				
				
				<table id="relResourcesTable" class="table-list"  cellspacing="1" >
					<thead>
						<tr>
							<td colspan="4">
								<a ng-click="add()" class="btn btn-sm btn-primary fa fa-plus"><span>添加</span></a>
							</td>
						</tr>
					</thead>
					<tr >
						<th style="text-align: center;width: 60px;">默认</th>
						<th width="25%">名称</th>
						<th width="40%">URL</th>
						<th >管理</th>
					</tr>
					<tr ng-repeat="relResource in data.relResources">
						<td style="text-align: center;width: 60px;"><input type="radio" name="relUrl" ng-model="relResource.id"  ng-value="relResource.id" ng-click="setDefaultUrl(relResource.resUrl)" /></td>
						<td><input class="inputText" type="text" ng-model="relResource.name"  ht-validate="{required:true}" /></td>
						<td><input class="inputText" type="text" ng-model="relResource.resUrl"  ht-validate="{required:true}" /></td>
						<td style="text-align: center;">
							<a ng-click="data.relResources.remove(relResource)" class="link delete">
								<span>删除</span>
							</a>
						</td>
					</tr>
				</table>	
			</form>
			
		
	</body>
</html>