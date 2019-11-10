<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html ng-app="BpmImageService">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务流程图</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/bpm/service/bpmImageService.js"></script>
<style type="text/css">
div.icon {
	border: 1px solid #868686;
	line-height: 10px;
	width: 10px;
	height: 10px;
	float: left;
	overflow: hidden;
	margin-top: 3px;
}

.target span {
	margin: 0 10px 0 3px;
	font-size: 12px;
	font-weight: bold;
	float: left;
	vertical-align: middle;
	white-space: nowrap;
}
div.qtip{
	min-width:280px;
}
</style>
</head>
<body>
	<div class="container-fluid" style="_height:380px;min-height:380px;">
		<div class="row" style="margin: 10px 0 20px 10px;">
			<div class="target">
				<div class="icon" style="background: #FF0000;  "></div>
				<span>待审批</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #F89800;"></div>
				<span>提交</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #FFE76E;"></div>
				<span>重新提交</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #00FF00;"></div>
				<span>同意</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #C33A1F;"></div>
				<span>挂起</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #0000FF;"></div>
				<span>反对</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #8A0902;"></div>
				<span>驳回</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #FFA500;"></div>
				<span>驳回到发起人</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #023B62;"></div>
				<span>撤销</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #F23B62;"></div>
				<span>撤销到发起人</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #338848;"></div>
				<span>会签通过</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #82B7D7;"></div>
				<span>会签不通过</span>
			</div>
			<br/>
			<div class="target">
				<div class="icon" style="background: #EEAF97;"></div>
				<span>人工终止</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #CCCCCC;"></div>
				<span>驳回取消</span>
			</div>
			<div class="target">
				<div class="icon" style="background: #4A4A4A;"></div>
				<span>完成</span>
			</div>
			<c:if test="${parentInstId!=null&&parentInstId!=0}">
			<div class="target">
				
				<a style="cursor:pointer;" href="javascript:void(0);" onclick="showFlowMap('${parentInstId}')"><i class='fa fa-map'></i>查看主流程图</a>
			</div>
			</c:if>
		</div>
		<div class="row" style="margin: 10px">
			<div style="margin:10px;position:relative;background:url('${ctx}/bpm/bpmImage?taskId=${taskId}') no-repeat;width:${bpmDefLayout.width}px;height:${bpmDefLayout.height}px;">
				<c:forEach var="layout" items="${bpmDefLayout.listLayout}">
					<c:choose>
						<c:when test="${layout.nodeType == 'CALLACTIVITY'}">
						   <div class="flowNode"  onclick="showFlowMap('${instId}','${layout.nodeId}','${layout.nodeType}','subFlow')"  ht-bpm-image="{instId:'${instId}',nodeId:'${layout.nodeId}',nodeType:'${layout.nodeType}'}" style="position:absolute;left:${layout.x}px;top:${layout.y}px;width:${layout.width}px;height:${layout.height}px;"></div>
						</c:when>
						<c:otherwise>
							<div class="flowNode" ht-bpm-image="{instId:'${instId}',nodeId:'${layout.nodeId}',nodeType:'${layout.nodeType}'}" style="position:absolute;left:${layout.x}px;top:${layout.y}px;width:${layout.width}px;height:${layout.height}px;"></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>
<script>
//显示指定流程实例的轨迹图
function showFlowMap(instId,nodeId,nodeType,type) {
	var url= __ctx+"/flow/instance/instanceFlowImage?type="+type+"&id="+instId+"&nodeId="+nodeId+'&from=task';
	var title=type=="subFlow"?"查看子流程":"查看主流程";
	var def = {title:title,width:800,height:550, modal:true,resizable:true,iconCls: 'fa fa-table'};
 
	$.topCall.dialog({
		src:url,
		base:def
	});
}
</script>