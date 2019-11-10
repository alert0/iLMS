<%@page import="com.hotent.bpmx.api.constant.NodeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="bpmDefSetting" class="overflow-scroll-y">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript">
			var app = angular.module('bpmDefSetting',['base','formDirective']);
			
			var currentNodeId="";
			var defId='${bpmDefinition.defId}'; 
			
			$(function(){
				setContainerWidth();
			});
			
			app.controller("MainCtrl",function($scope){
				$scope.getMenuContent = function(){
					var me = $(this),
						nodeType = me.attr("nodetype"),
						menu = $("#"+nodeType);
					currentNodeId = me.attr("nodeid");
					
					if(menu&&menu.length>0){
						return menu[0].outerHTML;
					}else{
						return "尚未配置菜单";
					}
				}
			});
			
			function setContainerWidth(){
            	var w=$(".scoll-panel").width();
            	$("#divContainer").width(w-20);
            }
			
			function menuHandler(type){
				if(!type)return;
				switch(type){
					case "setNodeRules":
						var url='${ctx}/flow/node/ruleEdit?definitionId='+defId+'&nodeId='+currentNodeId;
						showDialog({url:url,width:840,height:545,title:"跳转规则设置　"+currentNodeId});
						break;
					case "setEventScript":
						var url='${ctx}/flow/node/eventScriptEdit?defId='+defId+'&nodeId='+currentNodeId;
						showDialog({url:url,width:840,height:515,title:"事件脚本设置　"+currentNodeId});
						break;
					case "setExclusiveGateway":
						var url='${ctx}/flow/node/branchConditionEdit?defId='+defId+'&nodeId='+currentNodeId;
						showDialog({url:url,width:840,height:515,title:"分支条件设置　"+currentNodeId});
						break;
					case "service":
						var url='${ctx}/flow/node/autoTaskManager?defId='+defId+'&nodeId='+currentNodeId;
						showDialog({url:url,width:840,height:620,title:"自动任务节点　"+currentNodeId});
						break;
					case "signConfig":
						var url='${ctx}/flow/node/signConfigEdit?defId='+defId+'&nodeId='+currentNodeId;
						showDialog({url:url,width:840,height:620,title:"会签节点规则定义　"+currentNodeId});
						break;
					case "setReminder":
						var url='${ctx}/flow/taskReminder/taskReminderEdit?procDefId='+defId+'&nodeId='+currentNodeId;
						showDialog({url:url,width:1000,height:700,title:"催办设置　"+currentNodeId});
						break;
					case "userSettings":
						var url='${ctx}/flow/node/userSettings?procDefId='+defId+'&nodeId='+currentNodeId;
						showDialog({url:url,width:1000,height:500,title:"用户设置　"+currentNodeId});
						break;
					case "setCallActivity": 
						var url='${ctx}/flow/def/subFlowDetail?defId='+defId+'&status=${bpmDefinition.status}'+'&nodeId='+currentNodeId;
						var h = $(top).height() - 65;
						var w =$(top).width() - 5;  
						showDialog({url:url,width:w,height:h,title:"子流程设置　"+currentNodeId}); 
						break;
					case "showSubFlowImage": 
						var url='${ctx}/flow/def/subFlowDetail?defId='+defId+'&status=${bpmDefinition.status}'+'&nodeId='+currentNodeId;
						var h = $(top).height() - 65;
						var w =$(top).width() - 5;  
						showDialog({url:url,width:w,height:h,title:"子流程设置　"+currentNodeId}); 
						break;
						
					showDialog({
						url : url,
						width : 250,
						height : 200,
						title : "全局插件设置",
						buttonsAlign:'center',
						buttons : [{
		                    text:'确定',
		                	iconCls:'btn btn-success fa-check-circle',
		                    handler:function(){
		                    	$scope.editingZdyz = dialog.innerWin.triggerScopeFun("getEditingZdyz");
		                    	if(!$scope.editingZdyz) return ;
		                    	$scope.saveEditingZdyz();
		                    	dialog.dialog('close');
		                    }
		                },{
		                    text:'取消',
		                	iconCls:'btn btn-default fa-times-circle',
		                    handler:function(){
		                    	dialog.dialog('close');
		                    }
		                }]
					});
					break;
				}
			}

			function showDialog(param) {
				var baseConfig = {
					passConf : "",
					title : '',
					width : 450,
					height : 300,
					modal : true,
					resizable : true
				};
				$.extend(baseConfig, param);
				$.topCall.dialog({
					src : baseConfig.url,
					base : baseConfig
				});
			}
		</script>
		<style type="text/css">
			.setting-item{
				height:22px;
				cursor:pointer;
				padding:2px;
				border:1px solid #fff;
				margin-top:3px;
				color:#3F1A00;
			}
			.setting-item:hover{
				border-color:#D3D3D3;
				background-color:#D3D3D3;
				color:#000000;
			}
			.setting-item span{
				margin-left:10px;
			}
			.setting-menu-line{
				position: absolute;
				left: 33px;
				top: 5px;
				font-size: 1px;
				border-left: 1px solid #ccc;
				border-right: 1px solid #fff;
				height:80%;
			}
			#divcontainer { border:1px solid #959DA4 !important;}
		</style>
	</head>
	<body ng-controller="MainCtrl"  class="easyui-layout">
		<div class="scoll-panel overflow-scroll-y" data-options="region:'center',border:false" >
			<div class="gray-div overflow-scroll-y" style="min-height: 500px;">
				<div class="hidden">
					<div id="userTask" style="width: 120px;">
						<div class="setting-menu-line"></div>
						<div class="setting-item" onClick="menuHandler('setNodeRules')">
							<i class="fa fa-ils"></i> <span>设置跳转规则</span>
						</div>
						<div class="setting-item" onClick="menuHandler('setEventScript')">
							<i class="fa fa-google-wallet"></i> <span>事件设置</span>
						</div>
						<div class="setting-item" onClick="menuHandler('setReminder')">
							<i class="fa fa-bell-o"></i> <span>催办设置</span>
						</div>
						
					</div>
	
					<div id="signTask" style="width: 120px;">
						<div class="setting-menu-line"></div>
						<div class="setting-item" onClick="menuHandler('setNodeRules')">
							<i class="fa fa-ils"></i> <span>设置跳转规则</span>
						</div>
						<div class="setting-item" onClick="menuHandler('signConfig')">
							<i class="fa fa-group"></i> <span>投票规则</span>
						</div>
						
					</div>
					<div id="callActivity" style="width: 120px;">
						<div class="setting-menu-line"></div>
						<div class="setting-item" onClick="menuHandler('setCallActivity')">
							<i class="fa fa-google-wallet"></i> <span>子流程设置</span>
						</div>
						<!-- <div class="setting-item" onClick="menuHandler('showSubFlowImage')">
							<i class="fa fa-google-wallet"></i> <span>子流程流程图</span>
						</div> -->
					</div>
					<div id="start" style="width: 120px;">
						<div class="setting-menu-line"></div>
						<div class="setting-item" onClick="menuHandler('setEventScript')">
							<i class="fa fa-google-wallet"></i> <span>事件设置</span>
						</div>
					</div>
					
					<div id="end" style="width: 120px;">
						<div class="setting-menu-line"></div>
						<div class="setting-item" onClick="menuHandler('setEventScript')">
							<i class="fa fa-google-wallet"></i> <span>事件设置</span>
						</div>
					</div>
	
					<div id="exclusivegateway" style="width: 120px;">
						<div class="setting-menu-line"></div>
						<div class="setting-item" onClick="menuHandler('setExclusiveGateway')">
							<i class="fa fa-random"></i> <span>设置分支条件</span>
						</div>
					</div>
					
					<div id="inclusiveGateway" style="width: 120px;"> 
						<div class="setting-menu-line"></div>
						<div class="setting-item" onClick="menuHandler('setExclusiveGateway')">
							<i class="fa fa-random"></i> <span>设置条件同步</span>
						</div>
					</div>
	
					<div id="serviceTask" style="width: 120px;">
						<div class="setting-menu-line"></div>
						<div class="setting-item" onClick="menuHandler('service')">
							<i class="fa fa-wrench"></i> <span>自动任务设置</span>
						</div>
					</div>
				</div>

				<c:set value="<%=NodeType.SUBPROCESS%>" var="subProcess"/>
			 	<!-- 节点消息配置 -->
				<div id="divContainer" style="overflow-x:auto; min-height: 400px;">
					<div style="margin-top:20px;position:relative;background:url('image?defId=${bpmDefinition.defId}') no-repeat;width:${bpmDefLayout.width}px;height:${bpmDefLayout.height}px;">
						<c:forEach var="layout" items="${bpmDefLayout.listLayout}">
							<c:if test="${layout.nodeType != subProcess}">
							<div class="flowNode" nodeid="${layout.nodeId}"
								 ht-tip="{content:getMenuContent,alignX:'center',alignY:'bottom',offsetY:10}"
								 style="position:absolute;left:${layout.x}px;top:${layout.y}px;width:${layout.width}px;height:${layout.height}px;" 
								 name="${layout.name}" nodetype="${layout.nodeType.key}">
								</div>
							</c:if>  
						</c:forEach> 
					</div>
				</div>
			</div>
		</div>
	</body>
</html>