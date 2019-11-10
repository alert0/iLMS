<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/portal/sysIndexTools/sysIndexTools/sysIndexToolsEditController.js"></script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary fa-save" ng-model="data" ht-save="save"><span>保存</span></a>
					<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit(true,'grid');"><span>返回</span></a>
				</div>
			</div>
			<form name="form" method="post" ht-load="getJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th>名称:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.name"   ht-validate="{required:true,maxlength:150}"  />
								</td>							
							</tr>
							<tr>								
								<th>图标:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.icon"   ht-validate="{maxlength:600}"  />
								</td>								
							</tr>
							<tr>								
								<th>字体样式:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.fontStyle"   ht-validate="{maxlength:600}"  />
								</td>								
							</tr>
							<tr>								
								<th>类型:</th>
								<td>
									<select class="inputText" ng-model="data.type" ht-validate="{required:true}"  >
									  <option value="统计栏目" ng-selected="!data.type||data.type=='统计栏目'">统计栏目</option>
									  <option value="快捷工具">快捷工具</option>
									  <option value="常用功能">常用功能</option>
									</select>
									<!-- <input type="text" ng-model="data.typeName" style="display:none;"/>
									<input ng-model="data.type" class="easyui-combotree-me " ht-validate="{required:true}" /> -->
								</td>								
							</tr>
							<tr>								
								<th>链接:</th>
								<td>
									<input class="inputText input-width-80" type="text" ng-model="data.url"   ht-validate="{required:true,maxlength:600}"  />
								</td>								
							</tr>
							<tr>								
								<th>统计模式</th>
								<td>
									<label class="radio-inline"><input type="radio" ng-model="data.countMode" value="0"/>不统计</label>
									<label class="radio-inline"><input type="radio" ng-model="data.countMode" value="1" ng-checked="!data.countMode||data.countMode==1"/>Service方法</label>
									<label class="radio-inline"><input type="radio" ng-model="data.countMode" value="2" />自定义查询</label>
								</td>								
							</tr>
							<tr id="counting" ng-show="data.countMode!=0">								
								<th>统计算法:</th>
								<td>
									<div style="width: auto;float: left;">
										<input class="inputText" type="text" ng-model="data.counting"   ht-validate="{required:false,maxlength:300}"  />
									</div>
									<div ng-if="data.countMode==1" style="width: auto;float: left;padding-left: 5px;">
										<span class="pull-left btn btn-info btn-sm" ng-click="showSetParamDialog()">参数设置</span>
										<a href="#" style="text-decoration: none;" title="填写格式:1、service+“.”+方法名,service为spring的service名称。如果为webservice方法，格式为：方法路径+“?”+方法名，而且必须添加参数：namespace（名称空间）。（注意：路径中不能接参数，需点击“设置参数”按钮添加参数）" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
									</div>
									<div ng-if="data.countMode==2" style="width: auto;float: left;padding-left: 5px;">
										<span class="pull-left btn btn-info btn-sm" ng-click="selectQuery()">选 择...</span>
									</div>
									<div style="clear: both;"></div>
								</td>								
							</tr>
							<tr  ng-show="data.countMode!=0">								
								<th>统计数字样式:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.numberStyle"   ht-validate="{maxlength:600}"  />
								</td>								
							</tr>
				</table>
				
				
			</form>
		
	</body>
</html>