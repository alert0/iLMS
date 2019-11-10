<%@page import="com.hotent.bpmx.api.constant.ScriptType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>消息节点</title>
<%@include file="/commons/include/ngEdit.jsp" %>
<%@include file="/commons/include/zTree.jsp" %> 
<script type="text/javascript">
	window.UEDITOR_HOME_URL = "${ctx}/js/lib/ueditor/";
</script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/platform/bpm/def/bpmdef.udeitor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/plugins/autoMsgflowVar.js"></script>

<script type="text/javascript">
	var defId = "${defId}";
	var varTree;
	$(function(){
		varTree = new ZtreeCreator('varTreeObj',__ctx+"/flow/node/varTree")
		.setDataKey({name:'desc'})
	 	.setChildKey()
	 	.makeCombTree("varTree")
	 	.initZtree({defId:defId,nodeId:'${nodeId}',includeBpmConstants:true,removeSub:true},1);
	});


	var app = angular.module('messageNodeApp',['base','formDirective']); 
	
	
	app.controller("messageNodeController",function($scope){
		
		var ue = UE.getEditor('content');
		
		var bpmPluginDefJson = '${bpmPluginDefJson}';
		if(bpmPluginDefJson){
			$scope.nodeMessage = eval('(' + bpmPluginDefJson + ')');
		}else{
			$scope.nodeMessage={"htmlSetting":{"msgType":"","content":""},"plainTextSetting":{"msgType":"","content":""}};
		}
		// 保存数据
		$scope.save= function(){
			$scope.nodeMessage.pluginType = '${pluginType}';
			if(!$scope.nodeMessage.htmlSetting)$scope.nodeMessage.htmlSetting={};
			$scope.nodeMessage.htmlSetting.content = ue.getContent(); 
			param = {
						defId:'${defId}',nodeId:'${nodeId}',
						jsonStr:JSON.stringify($scope.nodeMessage)
					};
			var url = __ctx + "/flow/node/autoTaskPluginSave";
			
			$.post(url, param,function(data){
				var resultMessage=new com.hotent.form.ResultMessage(data);
	        	if(resultMessage.isSuccess()){
	        		$.topCall.success("节点配置成功!");
	        		window.parent.passConf();
	        	}else{
	        		$.topCall.error(resultMessage.getMessage(),resultMessage.getCause()); 
	        	}
			});
		}
		
		//用户规则选择
		$scope.addUserCondition=function(setting,index){
			var ruleList = eval('$scope.nodeMessage.'+setting+'.ruleList'); 
			var userRule;
			if(index){
				userRule = ruleList[index];
			}
			
			var dialog;
			var def = {
			        passConf:userRule,title:'节点人员条件配置',width:870,height:580, modal:true,resizable:true,iconCls: 'icon-collapse',
			        buttonsAlign:'center',
			        buttons:[{
						text:'确定',iconCls:'fa fa-check-circle',
						handler:function(e){
								var win=dialog.innerWin;   				
								var data = win.getUserRuleData();
								if(!data) return;
								$scope.$apply(function(){
									if(index != undefined){
										ruleList[index] = data;
									}else if(ruleList && ruleList.length > 0){
										ruleList.push(data);
									}else {
										var userRules = [];
										userRules.push(data);
										eval('$scope.nodeMessage.'+setting+'.ruleList=userRules;');
									}
								});
								dialog.window('close');
							}
					},{
						text:'取消',iconCls:'fa fa-times-circle',
						handler:function(){dialog.dialog('close');}
					}]
			};
			dialog = $.topCall.dialog({
				src:'${ctx}/flow/node/condition/conditionEdit?defId=${defId}',
				base:def
			});
			}
		
		///删除行
		$scope.deleteAttr=function(setting,index){
			 var aa = eval('$scope.nodeMessage.'+setting+'.ruleList');
			 removeObjFromArr(aa,index);
			}
	});
	
	
</script>
<style type="text/css">
	.tt-inner {
	display: inline-block;
	line-height: 12px;
	padding-top: 5px;
	}
</style>
</head>
<body ng-app="messageNodeApp" >
<div ng-controller="messageNodeController" id="messageNode">
	<div class="toolbar-panel col-md-13 ">
				 <a href="javascript:;" class="btn btn-primary btn-sm fa-save"  ng-click="save()">保存</a>
				 <a href="javascript:;" class="btn btn-primary btn-sm fa-back" onclick="javascript:parent.selfDialog.dialog('close')">取消</a>
	</div>
	<table class="table-form" style="width: 100%">
		<tr>
			<th width="20%"><span>外部数据类</span> </td>
			<td><input type="text" ng-model="nodeMessage.externalClass" style="border: 1px solid #CCCCCC;height: 25px; width: 320px" class="inputText">
			<a href="javascript:;" style="text-decoration: none;" title="
				该类必须实现IExternalData接口，程序会为你提供流程的一些信息<br>
				该实现类，通过已知流程信息，提供数据 map(String,Object)<br>
				系统会通过map中的key匹配文本消息中$&#123;key}的动态数据。
			" class="fa fa-exclamation-circle" ht-tip>　</a>
			</td>
		</tr>
	</table>
	<div id="noticeTab"  data-options="tabPosition:'top'" class="easyui-tabs" easy-tabs>
	<div title="富文本消息" 
		style="width:auto;height:auto;"> 
    <table class="table" style="width: 100%">
		<tr>
			<th width="20%"><span>通知类型</span> </th>
			<td>
				<input type="checkbox" ht-checkbox ng-model="nodeMessage.htmlSetting.msgType" value="inner" id="message_"><label for="message_" class="normal_label">站内消息</label>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="checkbox" ht-checkbox ng-model="nodeMessage.htmlSetting.msgType" value="mail" id="email_"><label for="email_" class="normal_label">邮件</label>
			</td>
		</tr>
		<tr>
			<th width="20%"><span>接收人配置:</span></th>
			<td style="padding-top: 0px !important;">
				<div class="table" style="min-height:120px">
					<table class="table" style="text-align:left;">
						<thead>
						<tr>
							<th width="60px">序号</th>
							<th>条件</th>           
							<th width="60px">批次</th>
							<th width="120px">操作</th>
						</tr>
						</thead>
						<tr ng-repeat="userRule in nodeMessage.htmlSetting.ruleList">
						<td>{{$index+1}}</td>
						<td>{{userRule.description}} </td>
						<td><input type="text" size="5" ng-model="userRule.groupNo"> </td> 
						<td>
							<a ng-click="addUserCondition('htmlSetting',$index)" class="btn-sm btn-success">修改</a>
							<a ng-click="deleteAttr('htmlSetting',$index)" class="btn-sm btn-danger">删除</a>
						</td>
						</tr> 
						<tr><td style="text-align: left;" colspan="4"> 
						<a class="btn-sm btn-primary" href="javascript:;" ng-click="addUserCondition('htmlSetting')" >新增</a>
						 </td></tr>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<th width="20%"><span>标题:</span></th>
			<td>
				<input class="inputText" type="text" ng-model="nodeMessage.htmlSetting.subject" style="border: 1px solid #CCCCCC;height: 25px; width: 320px">
			</td>
		</tr>
		<tr>
			<th width="20%">
				<span>文本:</span>
				<div id="varTree"></div>
			</th>
			<td>
				<textarea id="content" style="height:274px;width:613px;"
				ng-model="nodeMessage.htmlSetting.content"></textarea> 
				<!--  <div id="content" ht-editor="getContentTxt" config="editorConfig" ng-model="nodeMessage.htmlSetting.content" style="height:274px;width:613px;" ></div>  -->
			</td>
		</tr>
    </table> 
    </div>
    <div title="
    	普通消息
    	"  style="width:auto;height:auto;">
    <table class="table-form" style="width: 100%">
		<tr>
			<th width="20%"><span>通知类型</span> </th>
			<td>
				<input type="checkbox" ht-checkbox ng-model="nodeMessage.plainTextSetting.msgType" value="sms" id="duanxin"><label for="duanxin" class="normal_label">短信</label>
			</td> 
		</tr>
		<tr>
			<th width="20%"><span>接收人配置:</span></th>
			<td>
			<div style="min-height:120px">
					<table style="text-align:left;" class="table-list">
						<tr>
							<th width="60px">序号</th>
							<th>条件</th>           
							<th width="60px">批次</th>
							<th width="120px">操作</th>
						</tr>
						<tr ng-repeat="userRule in nodeMessage.plainTextSetting.ruleList">
							<td>{{$index+1}}</td>
							<td>{{userRule.description}} </td>
							<td><input type="text" size="5" ng-model="userRule.groupNo" > </td> 
							<td>
								<a ng-click="addUserCondition('plainTextSetting',$index)" >修改</a>
								<a ng-click="deleteAttr('plainTextSetting',$index)" >删除</a>
							</td>
						</tr> 
						<tr>
							<td style="text-align: left;" colspan="4"> 
								<a class="btn-sm btn-primary" href="javascript:;" ng-click="addUserCondition('plainTextSetting')" >新增</a>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<th width="20%"><span>文本:</span></th>
			<td>
				<textarea ng-model="nodeMessage.plainTextSetting.content" style="height:188px;width:543px;" ></textarea> 
			</td>
		</tr>
    </table>  
    </div>
    </div>
    </div>
</body>
</html>
						