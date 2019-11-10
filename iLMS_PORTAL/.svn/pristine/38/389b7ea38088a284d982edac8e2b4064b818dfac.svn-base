<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="formApp"  >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/bpmForm.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/system/customQuery/customQueryService.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/form/controller/formPreview.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/form/service/BpmFormService.js"></script>
<script type="text/javascript">
	var formType = '${bpmForm.formType}';
	var defId = '${bpmForm.defId}';
	var permission = '${permission}';
	var data ='${data}';
	
	function getFormData(){
		var scope = AngularUtil.getScope();
		return scope.data;
	}
	
	function setFormData(data){
		var scope = AngularUtil.getScope();
		debugger;
		scope.data = data;
		AngularUtil.setData(scope);
	}
</script>
</head>

<body  style="height: 100%">
	<div class="" style="height: 100%;">
		<div class="toolbar-panel">
			<div class="buttons">
				<a href="javaScript:window.close();top.layer.close(top.layer.getFrameIndex(window.name));"  class="btn btn-primary btn-sm fa-back">
					<span>关闭</span>
				</a>
			</div>
		</div>
		<div class="form-container" ng-controller="formCtrl">
			<div class="toolbar-body">
				<ul class="nav nav-tabs padding-none" role="tablist">
					<li class="active">
						<a href="#formPreivew" role="tab" data-toggle="tab">表单预览</a>
					</li>
						<li>
							<a href="#showData" role="tab" data-toggle="tab">查看数据</a>
						</li>
				</ul>
				<!-- Tab panes -->
				<div class="tab-content">
					<div class="tab-pane active" id="formPreivew">
						<div id="formHtml" ht-bind-html="formHtml" style="width: 100%;"></div>
					</div>
					<div class="tab-pane" id="showData">
						<div style="width: 100%;">
							<pre id="pre" style="height: 100%; border-top-width: 0;">{{data | json}}</pre>
						</div>
					</div>
				</div>
			</div>
		</div>
		<textarea id="txtForm" style="display: none;"><c:out value="${bpmForm.formHtml}" escapeXml="true"></c:out></textarea>
	</div>
</body>
</html>