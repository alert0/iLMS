<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html ng-app="systemApp">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/ngEdit.jsp" %>
		<%@include file="/commons/include/zTree.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/bpm/service/serviceSetEdit.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/bpm/service/serviceSetService.js"></script>
		<style>
			.param-div{
				border:1px dashed #fff;
				margin-bottom:5px;
				padding:5px;
			}
			.param-span{
				float:right;
				display:none;
				cursor:pointer;
				margin-right:10px;
			}
			.param-span:hover{
				color:red;
			}
			.param-div:hover{
				border-color:#3E68B2;
			}
			.param-div:hover span{
				display:inline-block;
			}
		</style>
		<script type="text/javascript">
			var serviceSetId = '${serviceSetId}',
				returnUrl = '${returnUrl}';
			function getScope(){
				return angular.element($("#serviceSetEditDiv")).scope();
			}
		
			function addParam(){
				getScope().addUpdateParam();
			}
			
			$(function(){
				//console.info(window.parentWindow);
			})
		</script>
	</head>
	<body class="easyui-layout" style="overflow-y: hidden" fit="true">
			<div id="serviceSetEditDiv" ng-controller="serviceSetEditCtrl">
			    <div  class="toolbar-panel">
			    	<a href="javascript:;" class="btn btn-primary  btn-sm fa-save" ng-click="save()"><span>保存</span></a>
			    	<a class="btn btn-primary fa-back btn-sm" href="javascript:HT.window.closeEdit(true,'grid')"> 关闭 </a>
			    </div>
				<div class="easyui-layout" style="width:100%;height:92%;">
					<div class="easyui-accordion" data-options="region:'west'" style="width: 300px; height: 100%;">
						<div title="服务接口" style="overflow: auto; padding: 10px;">
					      <input type="text" ng-model="url" autocomplete="off" placeholder="输入服务wsdl地址" class="inputText">
				          <button class="btn btn-default" ng-click="parse()" type="button">解析</button>
					    	<span ht-ztree="services" child-key="soapBindingOperationInfos" callback="{onDblClick:onOperationDbClick,onClick:onSingleClick}"></span>
					    </div>
					    
						<div title="参数列表" style="padding: 10px;" data-options="tools:[{iconCls:'fa fa-lg fa-add',title:'添加参数',handler:function(){addParam()}}],selected:${!empty serviceSetId}">
							<ul class="list-unstyled">
								<li ng-repeat="param in params track by param.name">
									<div class="param-div">
										<span ng-show="param.type=='string'" class="label label-primary">字符串</span>
										<span ng-show="param.type=='number'" class="label label-success">数字</span>
										<span ng-show="param.type=='date'" class="label label-info">日期</span>
										<label>{{param.name}}({{param.desc}})</label>
										<span class="fa fa-trash-o fa-lg param-span" title="删除" ng-click="removeParam(param)"></span>
										<span class="fa fa-edit fa-lg param-span" title="修改" ng-click="addUpdateParam(param)"></span>
									</div>
								</li>
							</ul>
						</div>
					</div>
					<div data-options="region:'center'" title="参数设置">
					<form name="myForm">
						 <div ng-hide="serviceSet | isEmpty">
							 <table style="width:98%;margin:5px;" class="table-form">
							 	<tr>
									<td width="100">名称</td>
									<td colspan="3">
										 <input type="text" class="form-control" ng-model="serviceSet.name" ht-validate="{required:true}">
									</td>
								</tr>
							 	<tr>
									<td width="100">别名</td>
									<td colspan="3">
										 <input type="text" class="form-control" ng-model="serviceSet.alias" ht-validate="{required:true}">
									</td>
								</tr>
								<tr>
									<td>wsdl地址</td>
									<td colspan="3">{{serviceSet.url}}</td>
								</tr>
								<tr>
									<td>方法名</td>
									<td>{{serviceSet.methodName}}</td>
									<td>名称空间</td>
									<td>{{serviceSet.namespace}}</td>
								</tr>
								<tr>
									<td>方法地址</td>
									<td colspan="3">{{serviceSet.address}}</td>
								</tr>
								<tr>
									<td rowspan="2">输入参数</td>
									<td colspan="3">
										<ul ht-ztree="serviceSet.inputSet" set-checkbox callback="{onClick:onInputParamClick,beforeCheck:beforeInputParamCheck}"></ul>
									</td>
								</tr>
								<tr>
								<td colspan="3">
									<div class="form-horizontal" ng-hide="input.bind | isEmpty">
										<div class="form-group">
											<label class="col-sm-2 control-label">参数名称</label>
											<div class="col-sm-2">
												<p class="form-control-static">{{input.key}}</p>
											</div>
											<label class="col-sm-2 control-label">参数类型</label>
											<div class="col-sm-2">
												<p class="form-control-static">{{input.type}}</p>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">绑定类型</label>
											<div class="col-sm-4">
												<select ng-model="input.bind.type" class="form-control">
													<option value="1">固定值</option>
													<option value="2">参数</option>
													<option value="3">脚本</option>
												</select>
											</div>
										</div>
										<div class="form-group" ng-if="input.bind.type==1">
											<label class="col-sm-2 control-label">固定值</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" ng-model="input.bind.value" ht-validate="{required:true}"/>
											</div>
										</div>
										<div class="form-group" ng-show="input.bind.type==2">
											<label class="col-sm-2 control-label">参数</label>
											<div class="col-sm-5">
												<select class="form-control" ng-model="input.bind.value">
													<option ng-repeat="param in params"
													        value="{{param.name}}">{{param.name}}({{param.desc}})</option>
												</select>
											</div>
										</div>
										<div class="form-group" ng-show="input.bind.type==3">
											<label class="col-sm-2 control-label">脚本</label>
											<div class="col-sm-5">
												<textarea class="form-control" ng-model="input.bind.value" row="3"></textarea>
											</div>
										</div>
									</div>
								</td>
							</tr>
							</table>
						</div>
						</form>
					</div>
				</div>
			</div>
	</body>
</html>