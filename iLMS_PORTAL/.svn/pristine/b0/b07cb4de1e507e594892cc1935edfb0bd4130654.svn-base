<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/flow/defNodeBosController.js"></script>
<script type="text/javascript">
	var defId = "${param.defId}";
	var topDefKey = "${param.topDefKey}";
</script>
<style>
.tabs-panels,.panel-body{overflow:auto;}
</style>
</head>
<body ng-controller="ctrl">
	<div>
		<!-- 顶部按钮 -->
		<div class="toolbar-panel">
			<div class="buttons">
				<a class="btn btn-sm btn-primary fa-save" href="javaScript:void(0)" ng-click="save()">
					<span>保存</span>
				</a>
				<a class="btn btn-sm btn-primary fa-back" href="javaScript:window.location.reload()">
					<span>重置</span>
				</a>
				
			</div>
			<div class="buttons">
				<label><input class="ace" type="checkbox" name="isClear" ng-model="isClearForm" ></input>清空已配置的表单</label>
			</div>
		</div>
		<form name="form">
			<div class="easyui-tabs" style="text-align: center; height: 650px;">
				<!-- BO选定区  class="container" -->
				<div title="BO绑定">

					<table class="table-list" cellspacing="0">
						<thead>
							<tr>
								<td colspan="3">
									业务数据保存方式:
									<c:choose>
										<c:when test="${empty param.topDefKey}">
											<label class="radio-inline">
												<input type="radio" ng-model="data.bodef.boSaveMode" value="database">
												业务表
											</label>
											<label class="radio-inline">
												<input type="radio" ng-model="data.bodef.boSaveMode" value="boObject">
												实例表
											</label>
												<a class="btn btn-sm  btn-primary fa-add" ng-click="addBoDef()">
													<span>添加</span>
												</a>
										</c:when>
										<c:otherwise>
											<span ng-if="data.bodef.boSaveMode=='database'"><b>业务表</b></span>
											<span ng-if="data.bodef.boSaveMode=='boObject'"><b>实例表</b></span>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</thead>
						<tr>
							<th>描叙</th>
							<th>别名</th>
							<c:if test="${empty param.topDefKey}">
								<th>操作</th>
							</c:if>
						</tr>
						<tr ng-repeat="item in data.bodef.boDefs">
							<td>{{item.name}}</td>
							<td>{{item.alias}}</td>
							<c:if test="${empty param.topDefKey}">
								<td>
									<a href="javaScript:void(0)" ng-click="ArrayTool.del($index,data.bodef.boDefs)" class="btn btn-sm btn-default fa-times"></a>
								</td>
							</c:if>
						</tr>
					</table>
				</div>
				<!-- 节点设置区  class="container" -->
				<div title="节点设置">
					<table class="table-list" cellspacing="0">
						<thead>
							<td colspan="2">
								请选择节点：
								<select class="inputText" ng-model="selectedNode" ng-options="m as m.name for m in nodeDefList">
								</select>
								<a class="btn btn-primary fa-add" href="javaScript:void(0)" ng-click="editNodeSet()">
									<span>添加</span>
								</a>
								<a class="btn btn-primary fa-edit" href="javaScript:void(0)" ng-click="editNodeBoMapping()">
									<span>BO映射</span>
								</a>
							</td>
						</thead>
						<tr ng-repeat="(key, value) in nodeSetMap" ng-show="value.length>0">
							<th>{{getDesc(key)}}</th>
							<td>
								<table class="table-form" cellspacing="0">
									<thead>
										<th>序号</th>
										<th>描述</th>
										<th>操作</th>
									</thead>
									<tr ng-repeat="item in value">
										<td>{{$index+1}}</td>
										<td>{{item.description}}</td>
										<td>
											<a href="javaScript:void(0)" ng-click="editNodeSet(item,key)" class="btn btn-sm btn-default fa-edit"></a>
											<a href="javaScript:void(0)" ng-click="ArrayTool.del($index,value)" class="btn btn-sm btn-default fa-times"></a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</div>
</body>
</html>