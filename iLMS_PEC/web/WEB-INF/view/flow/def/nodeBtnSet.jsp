<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="btnApp">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/flow/defNodeBtnCtrl.js"></script>
	</head>

	<body ng-controller="btnController" class="easyui-layout">
			<div data-options="region:'center',title:'按钮列表'" style="height: 100%;overflow:auto;" id="divContainer">
				<!----顶部工具栏---->
				<div class="redact-toolbar">
					<div class="buttons"> 	
						<a ng-click="addButton()" class="btn btn-primary btn-sm fa-add"><span>新建</span></a>
						<a ng-click="getButtons(0)" class="btn btn-primary btn-sm  fa-recycle"><span>初始化按钮</span></a>
						
						<a class="btn btn-primary btn-sm  fa-save" ng-click="saveButtons()"><span>保存</span></a>
						<a href="javascript:;" onclick="window.selfDialog.dialog('close')" class="btn btn-primary btn-sm fa-close"><span>关闭</span></a>
				    </div>
				</div>
				<!-- -工具栏end- -->
				<div class="react-occupied"></div>
				<table class="table-list" >
				  <thead>
			        <tr><th>序号</th> <th>alias</th> <th>名字</th> <th>排序</th> <th>操作</th> </tr>
			      </thead>
			      <tbody>
			        <tr ng-repeat="btn in buttonList">
			          <th>{{$index+1}}</th>
			          <td ng-click="changeEditing($index)">{{btn.alias}}</td>
			          <td ng-click="changeEditing($index)">{{btn.name}}</td>
					  <td>				        
					 		<input class="inputText" placeholder="to" ng-model="turnToIndex" ng-blur="ArrayTool.turnTo(this,buttonList)" style="width:30px" ht-validate="{number:true}"> 
							<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.up($index,buttonList)" class="btn btn-sm btn-default fa-chevron-up"></a>
							<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.down($index,buttonList)" class="btn btn-sm btn-default fa-chevron-down"></a>
					  </td>
			          <td>
				          	<a ng-click="del($index)" class="btn btn-danger btn-sm fa-delete">删除</a>
			          </td>
			        </tr>
			        </tbody>
				</table>
				
			</div>
			<div data-options="region:'east',title:'按钮编辑',split:true" style="width:350px;padding-top:0px !important;">
			<form name="myForm">
				<table class="table-form" cellspacing="0"  ng-if="btn">
					<tr>
						<th style="width:30%;">按钮名称: </th>
						<td>
							<input  ng-model="btn.name" class="inputText" ht-validate="{required:true}" />
						</td>
					</tr>
					<tr>
						<th>动作: </th>
						<td> <span ng-if="btn.status!=2">{{btn.alias}}</span>
							<span ng-show="btn.status==2">
								<input ng-model="btn.alias" class="inputText" ng-blur="changeAlias()" ht-validate="{required:true,varirule:true}"/>
							</span>
							<span ng-show="btn.status==2 ">
								<select ng-model="btn.alias" ng-options="m.alias as m.name for m in buttonNoInitList" ng-change="changeButtonType(btn)" style="width:160px; margin-top:10px;">
									<option value="">--请选择--</option>
								</select>
							</span>
						</td>
					</tr>
					<tr ng-if="btn.supportScript">
						<th>前置脚本:<span class="fa bigger-120 fa-info-circle" ht-tip title="javascript脚本,在提交前做些处理<br>，参数：scope<br>返回:true/false。<br>返回false时不做提交动作."></span> </th>
						<td><textarea ng-model="btn.beforeScript" cols="30" rows="5" class="inputText" ></textarea> 
						</td>
					</tr>
					<tr ng-if="btn.supportScript">
						<th>后置脚本:<span class="fa bigger-120 fa-info-circle" ht-tip title="javascript脚本,在返回后前做些处理<br>参数:data(responseData)<br>返回:true/false<br>返回false时可以控制不关闭当前窗口。<br>非系统预定义按钮忽略后置脚本"></span> </th>
						<td><textarea  ng-model="btn.afterScript" cols="30" rows="5" class="inputText"></textarea>
						</td>
					</tr>
					<tr >
						<th>groovy脚本:<span class="fa  fa-info-circle" ht-tip title="java脚本，控制按钮是否显示，返回 false不显示，返回 true显示。<br>上下文变量：<br>1.流程变量<br>2.BO数据<br>返回:true/false。"></span> </th>
						<td><textarea  ng-model="btn.groovyScript" cols="30" rows="5" class="inputText"></textarea>
						</td>
					</tr>
					<tr>
						<th>urlForm:<span class="fa  fa-info-circle" ht-tip title="urlForm表单,在审批时用于保存表单的信息， 表单要用saveForm方法提交表单， 返回一个defer对象， 成功则提交流程， 不成功，则不提交 , example: var defer = $.Deferred();function saveForm(){<br/>// 保存表单成功<br/>defer.resolve();<br/>HT.window.closeEdit();<br/>// 保存失败<br/>// defer.reject();<br/>return defer;}"></span> </th>
						<td><input  ng-model="btn.urlForm" class="inputText"></input></td>
					</tr>
				</table>
			</form>
			</div>
	</body>
</html>