<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="app">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/ngEdit.jsp" %>
		<%@include file="/commons/include/zTree.jsp" %>
		<%@include file="/commons/include/codeMirror.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/flow/node/autoTaskWebServiceEdit.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/bpm/service/serviceSetService.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/system/dialog/scriptSelector.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/system/dialog/conditionScriptDialog.js"></script>
		
		<script type="text/javascript">
			var bpmPluginDefJson = '${bpmPluginDefJson}';
			var flowParam={'defId':'${defId}','nodeId':'${nodeId}',includeBpmConstants:true};
			
			
		</script>
	</head>
	<body class="easyui-layout" style="overflow-y: hidden" fit="true" ng-controller="webServiceController">
	    <div class="toolbar-panel col-md-13 ">
		 <a href="javascript:;" class="btn btn-primary btn-sm fa-save"  ng-click="save()">保存</a> 
		 <a href="javascript:;" class="btn btn-primary btn-sm fa-back" onclick="javascript:parent.selfDialog.dialog('close')">取消</a>
		 <a ng-hide="serviceSet.id | isEmpty" class="btn btn-primary fa fa-bug" ng-click="testServiceSet()">测试</a>
		</div>
		<div class="easyui-layout" style="width:100%;height:92%;">
			<form  name="myForm">
				 <table style="width:98%;margin:5px;" class="table-form">
				 	<tr>
						<th>名称</th>
						<td><input ng-model="nodeDefJson.name" readonly="readonly" ht-validate="{required:true}" class="inputText"><a class="btn btn-primary fa fa-search" ng-click="chooseServiceSet()">选择</a>
							</td>
						<th>别名</th>
						<td>{{serviceSet.alias}} </td>
					</tr>
					<tr>
						<th>wsdl地址</th>
						<td colspan="3">{{serviceSet.url}}</td>
					</tr>
					<tr>
						<th>方法名</th>
						<td>{{serviceSet.methodName}}</td>
						<th>名称空间</th>
						<td>{{serviceSet.namespace}}</td>
					</tr>
					
					<tr rowspan="{{params.length+1}}">
						<th>参数设置</th>
						<td colspan="3">
							<div ng-repeat="param in nodeDefJson.params" ng-switch="param.type" class="form-inline">
								<div class="form-group">
									<label>{{param.desc}} </label> 
									<select class="form-control" ng-model="param.bindType" placeholder="参数绑定类型" ht-validate="{required:true}">
									  <option value="">请选择参数类型</option>
									  <option value="var">流程变量</option>
									  <option value="bo">BO值</option>
									  <option value="script">脚本</option>
									</select>
									<select class="form-control" ng-model="param.bindValue" ng-options="(field.path+'.'+field.name) as field.desc group by field.tableName for field in boData" ng-if="param.bindType=='bo'"  ht-validate="{required:true}">
									<option value="">请选择BO值</option>
									</select>
									<select class="form-control" ng-model="param.bindValue" ng-options="var.name as var.desc for var in flowVar" ng-if="param.bindType=='var'"  ht-validate="{required:true}">
									<option value="">请选择流程变量</option>
									</select>
									<textarea placeholder="执行脚本" ng-model="param.bindValue" ng-if="param.bindType=='script'" class="form-control"  ht-validate="{required:true}"></textarea>
								</div>
							</div>
						</td>
					</tr>
					
					<tr>
						<th>输出处理<a class="fa bigger-120 fa-info-circle" ht-tip="{style:{width:'500px'}}" 
									title="1、返回值为JSONObject,list类型为jsonArray  data<br>
										2、可以从invokeResult 获取返回信息<br>
										3、可以通过pluginSession 获取节点信息或者设置流程变量<br>
										eg:pluginSession.getBpmDelegateExecution().setVariable(variableName,value);
									"></a></th>
						<td colspan="3" rowspan="3">
							<div>
									<a class="btn btn-primary btn-xs" ng-click="selectScript()">常用脚本</a>
									<a class="btn btn-primary btn-xs"  ng-click="selectConditionScript()">条件脚本</a>
									<a class="varTree btn btn-primary btn-xs" id="tempTree">可选变量</a>
							</div>
							<textarea ui-codemirror ng-model="nodeDefJson.outPutScript"></textarea>
						</td>
					</tr>
				</table>
				</form>
		</div>
	</body>
</html>