<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html ng-app="formApp" style="height: 100%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="moduleEdit" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/mobile/assets/js/importJs.js"></script> 
<script type="text/javascript">
	var FORM_TYPE_ = '${bpmForm.formType}';
	var defId = '${bpmForm.defId}';
	var permission = '${permission}';
	var data ='${data}';
	var FORM_TYPE_ = 'mobile';
	
	importCss(flowAryCss);
	importJs(flowAryJs);
</script>

<script type="text/javascript" src="${ctx}/js/platform/form/controller/formPreview.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/form/service/BpmFormService.js"></script>
</head>
	<body ng-controller="formCtrl"  class="page">
	
	         <!-- 工具栏 -->
	         <nav class="bar bar-tab">
	             <a class="tab-item external disabled" ng-click="showOrHide('pageHtml')">
	                 <span class="icon icon-app"></span>
	                 <span class="tab-label">表单预览</span>
	             </a>
	             <a class="tab-item external disabled" href="javascript:void(0)" ng-click="showOrHide('dataStr')">
	                 <span class="icon icon-settings"></span>
	                 <span class="tab-label">数据预览</span>
	             </a>
	         </nav>
	
	         <div class="content">
	         	<div id="pageHtml">
	         		<div id="formHtml" ht-bind-html="formHtml" style="width: 100%;"></div>
	         	</div>
	         	<div id="dataStr" class="content-block" style="display:none;">
					<pre id="pre" style="height: 100%; border-top-width: 0;width:auto; display:inline-block !important; display:inline;">{{data | json}}</pre>
			   </div>
	         </div>
	         
	  		 <textarea id="txtForm" style="display: none;"><c:out value="${bpmForm.formHtml}" escapeXml="true"></c:out></textarea>
	</body>
	

</html>