<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="component">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
	</head>
	<body>

		<div class="toolbar-panel" >
			<div class="buttons">
				<c:if test="${empty param.topDefKey}"><button onClick="closeCallBack();" class="btn btn-primary btn-sm fa-close" ><span>关闭</span></button></c:if>
				<c:if test="${not empty param.topDefKey}"><button onClick="window.selfDialog.dialog('close')" class="btn btn-primary btn-sm fa-close" ><span>关闭</span></button></c:if>
				
				<a href="${ctx}/flow/def/bpmnXml?defId=${defId}" target="_blank" class="btn btn-sm btn-primary">BPMNXML</a>
				<a href="${ctx}/flow/def/designXml?defId=${defId}" target="_blank" class="btn btn-sm btn-primary">DesignXML</a>
			</div>
		</div>
		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="流程配置" id="myIframe" iframe="true" href="${ctx}/flow/node/nodeDefSetting?defId=${defId}&topDefKey=${param.topDefKey}"></div>
			<div title="明细信息" iframe="true" href="${ctx}/flow/def/defGet?defId=${defId}"></div>
			<div title="业务对象" iframe="true" href="${ctx}/flow/def/defNodeBos?defId=${defId}&topDefKey=${param.topDefKey}"></div>
			<div title="变量管理" iframe="true" href="${ctx}/flow/var/defVarList?defId=${defId}"></div>
			<div title="流程实例" iframe="true" href="${ctx}/flow/def/defInstance?defId=${defId}"></div>
			<div title="版本管理" iframe="true" href="${ctx}/flow/def/defVers?defId=${defId}"></div>
			<div title="其他设置" id="defOtherPage" iframe="true" href="${ctx}/flow/def/defOtherParam?defId=${defId}&topDefKey=${param.topDefKey}"></div>
		</div>
	</body>
	<script type="text/javascript">
		function closeCallBack(){
            window.close();
			//关闭窗口时，刷新父页面的列表
			window.opener.location = "javascript:reloadForImport();";
		}
	</script>
</html>