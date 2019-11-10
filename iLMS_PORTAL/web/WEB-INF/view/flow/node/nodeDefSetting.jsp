<%@page import="com.hotent.bpmx.api.constant.NodeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="bpmDefSetting">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/flow/node/nodeDefController.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/flow/node/nodeDefService.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/flow/bpmFormDialog.js"></script>
		
		<script type="text/javascript">
			var defId='${bpmDefinition.defId}'; 
			var flowKey = '${bpmDefinition.defKey}';
			var topDefKey = '${topDefKey}';
		</script>
		<style type="text/css">
			.panel-body{padding: 15px!important; }
			danger:focus, .btn-danger:hover{background-color: #c12e2a!important;}
			.btn-success{margin:5px 0 0 5px;}
			.right-nest{float:left; width:240px;}
			.ml5{margin-left:5px;}
			.panel-heading{cursor:pointer;}
			.alert-info{width:100%;float:left;}
			.overflow-y{overflow-y:auto; height: 100%}
		</style>
		<script type="text/javascript">
		document.onkeydown = function(e){
			if (e.which==9){
				$("[data-toggle='tab'][aria-expanded=false]").click();
			}
		}
		</script>
	</head>
	<body ng-controller="defCtrl" class="easyui-layout">
	<!-- 流程图 -->
		<div data-options="region:'center',title:'流程图'" style="background:#eee;">
			<div id="divContainer" style="overflow-y: auto;height: 95%">
			<div style="margin-top:20px;position:relative;background:url('${ctx}/flow/def/image?defId=${bpmDefinition.defId}') no-repeat;width:${bpmDefLayout.width}px;height:${bpmDefLayout.height}px;">
				<c:forEach var="layout" items="${bpmDefLayout.listLayout}">
					<c:if test="${layout.nodeType != subProcess}">
					<div class="flowNode " nodeid="${layout.nodeId}" ng-click="toEditNode('${layout.nodeId}','${layout.name}','${layout.nodeType.key}')" 
						 ht-tip="{content:getMenuContent,alignX:'center',alignY:'bottom',offsetY:10}"
						 style="position:absolute;left:${layout.x}px;top:${layout.y}px;width:${layout.width}px;height:${layout.height}px;cursor:hand;text-align: center" 
						 name="${layout.name}" nodetype="${layout.nodeType.key}">
						 
						 <span class="badge" style="margin-top: 51px;" ng-if="'${layout.nodeId}'==edittingNodeId">正在编辑</span>
					</div>
					</c:if>  
				</c:forEach> 
			</div>
		</div>
	   </div>
	   	<!-- 节点配置 -->
	   <div data-options="region:'east',title:'流程配置{{edittingTitle}}',split:true" style="width:500px;padding-top:0px !important;" class="overflow-y">
		  	<div class="toolbar-panel-special">
		  		<div class="row">
		  			<div class="col-md-12">
		  				<ul class="nav nav-pills">
							  <li role="presentation" class="active">
							  	  <a href="#formUserConf" aria-controls="formUserConf" id="formUserTab" data-toggle="tab" aria-expanded="true" ht-tip title="TAB键切换">表单人员配置</a>
							  </li>
							  <li role="presentation">
							  	  <a href="#plugins" aria-controls="plugins" id="pluginstab" data-toggle="tab" aria-expanded="false">流程节点插件配置</a>
							  </li>
							  <li style="float:right;">
							  	<a href="javascript:void(0)" class="btn btn-danger fa fa-save" ng-click="save()">保存配置</a>
							  </li>
						</ul>
		  			</div>
		  			<!-- <div class="col-md-3">
			  			<a href="javascript:void(0)" class="btn btn-danger fa fa-save" ng-click="save()">保存配置</a>
		  			</div> -->
		  		</div>
			</div>
			<div class="tab-content">
				<div class="tab-pane active" id="formUserConf">
				   	<div class="panel-group">
			   	  	  <!-- 全局表单 -->
			   		  <div class="panel panel-primary" style="margin-top:52px;">
						  <div class="panel-heading"><div class="panel-title" data-toggle="collapse" href=".globalSetting">全局设置 </div></div>
						  <div class="panel-body collapse globalSetting in">
						        <div title="全局表单:" type="global" bpm-form="bpmDefSetting.globalForm" mobile-form="bpmDefSetting.globalMobileForm" ></div>
						        <div title="实例表单:" type="instance" bpm-form="bpmDefSetting.instForm" mobile-form="bpmDefSetting.instMobileForm"></div>
						  		<!-- 全局restful接口事件 -->
						  		<div class="well">
				    			  <div class="form-group form-inline">
								    <label class="control-label col-sm-3">全局事件：</label>
								   	<a href="javascript:void(0);" class="btn btn-sm btn-info fa-cog" ng-click="setRestFul(null,null)">设置接口事件</a>
								  </div>
								 </div>
						  </div>
					  </div>
					  <!-- 节点表单  -->
					  <div class="panel panel-primary">
						  <div class="panel-heading"><div class="panel-title" data-toggle="collapse" data-target=".node.forms" aria-expanded="false" aria-controls="nodeForm">节点表单</div></div>
						  <div class="panel-body collapse node forms in">
						  	 <div class=" table-nest">
						  	 	<span class="pull-left btn btn-info btn-sm" ng-click="editAllNodes('forms')">{{isEditAllNode_forms?'隐藏所有节点':'设置所有节点'}}</span>
						  	 </div>
						  	 <div ng-repeat="node in nodes" ng-if="node.nodeId == edittingNodeId || isEditAllNode_forms" class="table-ch">
						     	 <div title="{{node.nodeId}}" type="node" nodeId="{{node}}" bpm-form="bpmDefSetting.formMap[node.nodeId]" mobile-form="bpmDefSetting.mobileFormMap[node.nodeId]"></div>
						  	 </div>
						  </div>
					  </div>
					  <!-- 节点人员  -->
					  <div class="panel panel-primary">
						  <div class="panel-heading"><div class="panel-title" data-toggle="collapse" data-target=".node.nodeUser" aria-expanded="true" aria-controls="nodeUser">节点人员</div></div>
						  <div class="panel-body collapse node nodeUser in">
							  <div class=" table-nest">
							  	<span class="pull-left btn btn-info btn-sm" ng-click="editAllNodes('nodeUser')">{{isEditAllNode_nodeUser?'隐藏所有节点人员':'设置所有节点人员'}}</span>
							  
							  </div>
						  	  <div ng-repeat="node in nodes |filter:noStart"  class="table-ch" ng-if="node.nodeId == edittingNodeId || isEditAllNode_nodeUser" ng-init=" nodeUsers=nodeUserMap[node.nodeId]">
						  	 	<div class="btn-group" role="group" aria-label="...">
								  <button type="button" class="btn btn-link fa">{{node.name}}({{node.nodeId}})</button>
								  <button type="button" class="btn btn-default fa fa-add" ng-click="addNodeUserCondition(node.type,node.nodeId,null,node.name)"></button>
								</div>
								
								<div class="alert alert-danger show-grid" role="alert" ng-if="!nodeUsers||nodeUsers.length==0">尚未配置节点人员，请添加人员设置</div>
						     	<div ng-repeat="nodeCondition in nodeUsers" class="show-grid well" style="text-align: center;height: 100%;">
						     		<div class="col-xs-5 pull-left" style="line-height: 30px;">{{nodeCondition.description}}</div>
						     		<div class="col-xs-7 pull-right">
						     			<a ng-click="addNodeUserCondition(node.type,node.nodeId,$index,node.name)" class="btn btn-danger btn-sm fa-edit"></a>
										<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.up($index,nodeUsers)" class="btn btn-sm btn-default fa-chevron-up"></a>
										<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.down($index,nodeUsers)" class="btn btn-sm btn-default fa-chevron-down"></a>
										<a href="javascript:javaScript:void(0)" ng-click="ArrayTool.del($index,nodeUsers)" class="btn btn-sm btn-default fa-delete"></a>
						     		</div>
						     	</div>
						  	 </div>
						  </div>
					  </div>
					  
					  <!-- 节点属性 -->
					  <div class="panel panel-primary">
						  <div class="panel-heading"><div class="panel-title" data-toggle="collapse" data-target=".node.properties">节点属性</div></div>
						  <div class="panel-body collapse node properties in">
						  <div class=" table-nest">
						  	 <span class="pull-left btn btn-info btn-sm" class="table-ch" ng-click="editAllNodes('properties')">{{isEditAllNode_properties?'隐藏所有节点属性':'设置所有节点属性'}}</span>
						  </div>
						  	 <span ng-show="isEditAllNode_properties">
						  	 	 <div class="form-inline">
								   <div class="well">
								     <label class="form-inline">批量勾选:</label><br>
								     <label><input type="checkbox"  value="common" ng-model="jumpType_common"/>正常跳转</label>
									 <label><input type="checkbox"  value="free" ng-model="jumpType_free"/>自由跳转</label>
									 <label><input type="checkbox"  value="select" ng-model="jumpType_select"/>选择路径跳转</label><br>
									 <c:forEach items="${messageTypelist}" var="model">
									 <label><input type="checkbox" ng-model="notifyType_${model.type}" value="${model.type}"/>${model.title}</label>
									</c:forEach><br>
									 <label><input type="checkbox"  ng-model="backMode_all" ng-click="toBackModeall($event)"/>驳回后返回</label>
									 <label><input type="checkbox"  ng-model="skipExecutorEmpty_all"/>执行人为空时跳过</label>
									 </div>
								 </div>
						  	 </span>
						  	 <div ng-repeat="node in nodes" class="well" ng-if="node.nodeId == edittingNodeId || isEditAllNode_properties" ng-init="propertie=bpmDefSetting.nodePropertieMap[node.nodeId]">
								  <div class="form-inline"><label class="control-label">{{node.name}}({{node.nodeId}})</label></div>
								  <div class="form-inline">
								     <label class="control-label col-sm-4">跳转类型:</label>
								     <label><input type="checkbox" ht-checkbox ng-model="propertie.jumpType" value="common" ht-checked="jumpType_common"/>正常跳转</label>
									 <label><input type="checkbox" ht-checkbox ng-model="propertie.jumpType" value="free" ht-checked="jumpType_free"/>自由跳转</label>
									 <label><input type="checkbox" ht-checkbox ng-model="propertie.jumpType" value="select" ht-checked="jumpType_select"/>选择路径跳转</label>
								  </div>
								  <div class="form-inline">
								    <label class="control-label col-sm-4">通知类型:</label>
								    <c:forEach items="${messageTypelist}" var="model">
										<label><input type="checkbox" ht-checkbox ng-model="propertie.notifyType" ht-checked="notifyType_${model.type}" value="${model.type}"/>${model.title}</label>
									</c:forEach>
								  </div>
								  <div class="form-inline">
							    	<label class="control-label col-sm-4">属性设置:</label>
							    	<div class="right-nest" ng-init="initBackMode(propertie);">
							    		<span ht-boolean class="checkbox" text="执行人为空跳过" ng-model="propertie.skipExecutorEmpty" ht-checked="skipExecutorEmpty_all" ></span>
								 		<span ht-boolean class="checkbox" text="是否弹窗" ng-model="propertie.popWin"></span>
								 		<br>
								 		<label><input type="radio"  value="normal" ng-model="propertie.backMode">驳回后按流程图执行 </label>
  										<label><input type="radio" ng-disabled="node.type=='signTask'" value="direct" ng-model="propertie.backMode">驳回后直接返回</label>
								 	</div>
								  </div>
								  	
								   <div class="form-inline">
							    	<label class="control-label col-sm-4">驳回处理人模式:</label>
							    		<div class="right-nest"  ng-init="initBackUserMode(propertie);">
							    			<label><input type="radio"  value="history" ng-model="propertie.backUserMode"> 驳回历史处理人 </label>
							    			<label><input type="radio"  value="normal"  ng-model="propertie.backUserMode"> 驳回节点配置人</label>
								  		</div>
								  </div>
								  
								  <div class="form-inline">
							    	<label class="control-label col-sm-4">驳回节点:</label>
	                                    <input ng-model="propertie.backNode" class="form-control" style="width: 40%" placeholder="驳回节点" title="不为空时只能驳回到此节点" ht-tip="{position: { my: 'top left', at: 'bottom center'}}">
								     <span class="btn btn-info btn-sm ml5" ng-click="selectBackNode(propertie.backNode,node.nodeId)">选择节点</span>
								  </div>
								  
							
								  
								  <div class="form-inline ">
								    <label class="control-label col-sm-4">前置处理器:</label>
							    	<input ng-model="propertie.prevHandler" class="form-control" ng-blur="checkHandler(propertie.prevHandler)" style="width: 63%" placeholder="前置处理器" title="流程任务前置处理器，eg：userService.add" ht-tip="{position: { my: 'top left', at: 'bottom center'}}">
								  </div>
								  <div class="form-inline ">
								    <label class="control-label col-sm-4">后置处理器:</label>
							    	<input ng-model="propertie.postHandler" class="form-control" ng-blur="checkHandler(propertie.postHandler)" style="width: 63%" placeholder="后置处理器" title="流程任务后置处理器，eg：myService.sendMessage" ht-tip="{position: { my: 'top left', at: 'bottom center'}}">
								  </div>
								  <div class="form-inline">
								  	<label class="control-label col-sm-4" title="节点任务完成时间" ht-tip="{position: { my: 'top left', at: 'bottom center'}}">审批期限:</label>
								  	<div class="right-nest">
										  	<label class="radio-inline">
												<input type="radio"  value="worktime" ng-model="propertie.dateType">工作日
											</label>
											<label class="radio-inline">
									  			<input type="radio"  value="caltime" ng-model="propertie.dateType">日历日
									  		</label>
									  		<br/>
									  		<span ht-times="propertie.dueTime" ng-model="propertie.dueTime" ></span>
								  	</div>
								  </div>
						  	 </div>
						  </div>
					  </div>
					  
					  <!-- 节点按钮  -->
					  <div class="panel panel-primary">
						  <div class="panel-heading"><div class="panel-title" data-toggle="collapse" data-target=".node.btns" aria-expanded="true" aria-controls="nodeBtn">节点按钮</div></div>
						  <div class="panel-body collapse node btns in">
						  <div class=" table-nest">
						  	 <span class="pull-left btn btn-info" class="table-ch" ng-click="editAllNodes('btns')">{{isEditAllNode_btns?'隐藏所有按钮':'设置所有节点按钮'}}</span>
					  	  </div>
					  	  <div ng-repeat="(nodeId,nodeBtns) in nodeBtnMap" class="well" ng-if="nodeId == edittingNodeId || isEditAllNode_btns">
					  	  	 <button type="button" class="btn btn-link fa">{{nodeId}}</button><br>
				     		 <button ng-repeat="btn in nodeBtns" type="button" class="btn btn-sm btn-success">{{btn.name}}</button>
						  	 <button type="button" class="btn btn-sm btn-danger" ng-click="editBtns(nodeId)">编辑</button>
					  	  </div>
						  </div>
					  </div>
					  
					  <!-- 节点事件  -->
					 <div class="panel panel-primary">
					 	  <div class="panel-heading"><div class="panel-title" data-toggle="collapse" data-target=".node.restful">节点事件</div></div>
						  <div class="panel-body collapse node restful in">
							  <div class=" table-nest">
							  	 <span class="pull-left btn btn-info" class="table-ch" ng-click="editAllNodes('restful')">{{isEditAllNode_restful?'隐藏所有节点事件':'设置所有节点事件'}}</span>
						  	  </div>
						  	  <div ng-repeat="node in nodes |filter:noStart" style="line-height: 100%;" class="well" ng-if="node.nodeId == edittingNodeId || isEditAllNode_restful">
						  	  	 <div class="form-group form-inline">
							  	  	 <label class="control-label col-sm-7">{{node.name}}({{node.nodeId}})：</label>
									 <a href="javascript:void(0);" class="btn btn-sm btn-info fa-cog" ng-click="setRestFul(node.nodeId,node.name)">设置接口事件</a>
						  	  	  </div>
						  	  </div>
						  </div>
					  </div>
					  
				</div>
				</div>
				<div class="tab-pane overflow-y" id="plugins">
					<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
					<div class="mt55"></div>
						<div class="alert alert-danger show-grid" role="alert" ng-if="!edittingNodeId">请选择 一个节点</div>
						<div class="panel panel-primary" ng-repeat="plugin in pluginList" ng-if="plugin.supprotType.indexOf(edittingNodeType)!=-1">
							  <div class="panel-heading"><div class="panel-title" data-toggle="collapse" data-target=".plugin.{{plugin.key}}" aria-expanded="true">{{plugin.name}} </div></div>
							  <div class="panel-body collapse in plugin {{plugin.key}}">
							  	<div dynamic-directive="bpm-{{plugin.key}}:''">
							  		{{node.name}}{{plugin.key}}
							  	</div>
							  </div>
						</div>
					</div>
				</div>
			</div>
	   </div>
	</body>
</html>