<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="bpmDefApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>其他设置</title>
<%@include file="/commons/include/ngEdit.jsp"%>
<%@include file="/commons/include/zTree.jsp" %> 
<script type="text/javascript">
	window.UEDITOR_HOME_URL = "${ctx}/js/lib/ueditor/";
</script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/platform/bpm/def/bpmdef.udeitor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/plugins/startUser.js"></script>

<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/plugins/startTime.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/plugins/flowTitle.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/plugins/flowVar.js"></script>
<style type="text/css">
.btn.label-sm.inactive {
	width: 150px;
}
.btn.label-sm.active{
	width: 150px;
}
</style>

<script type="text/javascript">
	var defId = "${defId}";
	var varTree;
	
	$(function(){
		var defOtherPage = $("#defOtherPage",window.parent.document);
		if(defOtherPage && defOtherPage.length == 1){
			//调整其他设置页面的高度
			var h = defOtherPage.height();
			if(h > 50){
				defOtherPage.height(h-50);
			}
			var iframe = $("iframe",defOtherPage);
			if(iframe && iframe.length==1){
				iframe.height(h-50);
			}
		}
		varTree = new ZtreeCreator('varTreeObj',__ctx+"/flow/node/varTree")
		.setDataKey({name:'desc'})
     	.setChildKey()
     	.makeCombTree("varTree")
     	.initZtree({defId:defId,nodeId:'',includeBpmConstants:true,removeSub:true},1);
	});
	
	var app = angular.module('bpmDefApp', [ 'base', 'formDirective' ]);

	
	app.filter('statusFilter', function() {
		return function(str) {
			var out = "";
			switch (str) {
				case "draft":
					out = "草稿";
					break;
				case "deploy":
					out = "已发布";
					break;
				case "forbidden":
					out = "禁用";
					break;
				case "forbidden_instance":
					out = "禁用流程实例";
					break;
			}
			return out;
		}
	});

	app.controller("BpmDefPropController", [ '$scope', 'baseService', function($scope, baseService) {
		$scope.editorConfig = {
			toolbars : [['source']],
			initialFrameHeight:150,
			focus : true
		};

		var params = {
			defId : defId
		};
		var url = '${ctx}/flow/def/getOtherParam';
		$scope.prop = {
			notifyType : ''
		};

		baseService.postForm(url, params).then(function(data) {
			data.defId = defId;
			$scope.prop = data;
		});

		$scope.save = function() {
			var url = '${ctx}/flow/def/saveProp';
			baseService.post(url, $scope.prop).then(function(data) {
				if (data.result == 1) {
					$.topCall.success(data.message);
				} else {
					$.topCall.error(data.message);
				}
			});
		}

	} ]);
	
	function setEndNotify() {
		var url = '${ctx}/flow/plugins/procNotifyEdit?defId=${defId}';
		var dialog = $.topCall.dialog({
			src : url,
			base : {
				width : 800,
				height : 600,
				title : '办结抄送设置',
				modal : true
			}
		});
	}
</script>
		
</head>
<body class="easyui-layout panel-noscroll" style="border: 1px solid #959DA4;">
	<div data-options="region:'center',border:false" style="overflow: auto;" ng-controller="BpmDefPropController">
		<div class="toolbar-panel col-md-13">
			<div class="buttons" style="margin-left: 10px;">
				<a href="javascript:void(0);" ng-click="save()" class="btn btn-primary btn-sm fa-save">保存</a>
			</div>
		</div>
		<table class="table-form" cellspacing="0">
			<tr>
				<th>
					<span>标题规则:</span>
					<div id="varTree"></div>
				</th>
				<td>
					<input type="hidden" ng-model="prop.defId" />
					<div ht-editor="getContentTxt" config="editorConfig" ng-model="prop.subjectRule" style="width:80%;" ></div>
				</td>
			</tr>
			<tr>
				<th>
					<span>流程描述:</span>
				</th>
				<td>
					<textarea rows="3" style="width:64%;" ng-model="prop.description" class="inputText"></textarea>
				</td>
			</tr>
			<tr>
				<th>
					<span>通知类型:</span>
				</th>
				<td>
					<c:forEach items="${handlerList}" var="model">
						<label class="checkbox-inline">
							<input type="checkbox" ht-checkbox ng-model="prop.notifyType" value="${model.type}" <c:if test="${ model.isDefault}">checked='checked'</c:if> />${model.title}</label>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th>
					<span>测试通知类型:</span>
				</th>
				<td>
					<c:forEach items="${handlerList}" var="model">
						<label class="checkbox-inline">
							<input type="checkbox" ht-checkbox ng-model="prop.testNotifyType" value="${model.type}" <c:if test="${ model.isDefault}">checked='checked'</c:if> />${model.title}</label>
					</c:forEach>
				</td>
			</tr>
			<tr>		
				<th><span>参数设置</span></th>						
				<td colspan="1">
				    <div>
				    	<span  ht-boolean ng-disabled="prop.firstNodeUserAssign" text="跳过第一个节点" ng-model="prop.skipFirstNode" ht-tip title="流程启动时，是否允许直接跳过第一个节点。选中（背景为绿色）表示'是'"></span>
				    	<span  ht-boolean text="流程启动选择执行人" ng-model="prop.firstNodeUserAssign" ht-tip title="流程启动选择执行人。"></span>
				    	<!-- <span  ht-boolean text="审批表单使用主版本" ng-model="prop.useMainForm" ht-tip title="审批表单使用主版本"></span> -->
				    </div>
					 <div style="margin-top: 5px;">
					    <span  ht-boolean text="任务允许转办" ng-model="prop.allowTransTo" ht-tip title="任务是否允许转办"></span>
					    <span  ht-boolean text="允许执行人为空" ng-model="prop.allowExecutorEmpty" ht-tip title="任务执行人是否可以为空"></span>
					    <span  ht-boolean="run/test" text="是否正式" ng-model="prop.testStatus" ht-tip title="是否正式。测试状态，可以使用清除数据    选中（背景为绿色）表示'正式'，未选中（背景为灰色）表示'测试'"></span>
					 </div>
				</td>
			</tr>
			
			<tr>
				<th>
					<span>跳过类型:</span>
				</th>
				<td>
					<c:forEach items="${skipConditionList}" var="model">
						<label class="checkbox-inline">
							<input type="checkbox" ht-checkbox ng-model="prop.skipRules" value="${model.type}"  />${model.title}</label>
					</c:forEach>
				</td>
			</tr>
			
			<tr>
				<th>
					<span>办结抄送</span>
				</th>
				<td>
					<a class="btn  btn-info" onClick="setEndNotify()">设置</a>
					<a href="javascript:;" style="text-decoration: none;" title="如果允许流程结束时抄送给某批人，则需要进行设置。" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
				</td>
			</tr>

			
			<tr>
				<th>
					<span>状态:</span>
				</th>
				<td>
					<c:if test="${def.status=='draft' }">
						<label class="radio-inline">
							<input type="radio" ng-model="prop.status" value="draft" />
							草稿
						</label>
					</c:if>
					<label class="radio-inline">
						<input type="radio" ng-model="prop.status" value="deploy" />
						已发布
					</label>
					<c:if test="${def.status!='draft' }">
						<label class="radio-inline">
							<input type="radio" ng-model="prop.status" value="forbidden" />
							禁止
						</label>
						<label class="radio-inline">
							<input type="radio" ng-model="prop.status" value="forbidden_instance" />
							禁止实例
						</label>
					</c:if>
				</td>
			</tr>
			<tr>
				<th>
					<span>任务审批时表单版本<a ng-if="${not empty param.topDefKey}" href="javascript:;" style="text-decoration: none;" title="外部子流程取主流程的设置参数。" class="fa fa-exclamation-circle ht-help" ht-tip> </a>:</span>
				</th>
				<td>
					<label class="radio-inline">
						<input type="radio" ng-disabled="${not empty param.topDefKey}" ng-checked="!prop.useMainForm||prop.useMainForm==''" ng-model="prop.useMainForm" value="" />
						全局设置<a href="javascript:;" style="text-decoration: none;" title="全局设置：默认使用【系统属性管理】中参数“任务审批时使用表单主版本”的参数值。" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
					</label>
					<label class="radio-inline">
						<input type="radio" ng-disabled="${not empty param.topDefKey}" ng-model="prop.useMainForm" value="mainVersion" />
						表单主版本<a href="javascript:;" style="text-decoration: none;" title="表单主版本：任务审批时使用绑定表单的主版本。" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
					</label>
					<label class="radio-inline">
						<input type="radio" ng-disabled="${not empty param.topDefKey}" ng-model="prop.useMainForm" value="startVersion" />
						启动时版本<a href="javascript:;" style="text-decoration: none;" title="启动时版本：任务审批时使用流程启动时绑定的表单版本。" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
					</label>
				</td>
			</tr>
			<tr>
				<th>
					<span>审批期限:</span>
				</th>
				<td>
					<span>
						<label class="radio-inline">
							<input type="radio"  value="worktime" ng-model="prop.dateType">工作日
						</label>
						<label class="radio-inline">
				  			<input type="radio"  value="caltime" ng-model="prop.dateType">日历日
				  		</label>
				  		<span ht-times="prop.dueTime" ng-model="prop.dueTime"></span>
					</span>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>