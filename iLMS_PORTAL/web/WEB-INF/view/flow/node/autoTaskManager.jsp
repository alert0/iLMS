<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>自动任务节点</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
		<script type="text/javascript">
		<c:if test="${empty bpmPluginContext}">
		$(function(){
			$.topCall.warn("自动节点支持一种任务节点类型（脚本、消息等），   保存所选择的任务类型后无法修改。");
		});
		</c:if>
		var srcs = "";
		$(function(){
			$('#autoTaskPluginTab').tabs({
			    border:false,
			    onSelect:function(title){
			    	 var tab = $('#autoTaskPluginTab').tabs('getSelected');
			    	 var jqIframe = $("iframe",tab);
			    	 
			    	 var src = jqIframe[0].src;
			    	 if(srcs.indexOf(src)!= -1) return;
			    	 
			    	 jqIframe[0].src = src;
			    	 srcs = srcs+jqIframe[0].src+",";
			    }
			});
		})
		
		</script>
	</head>
	<!-- <h2>自动任务节点</h2>
    <p>自动节点支持一种任务节点类型（脚本、消息等），   保存所选择的任务类型后无法修改</p> -->
	<body class="easyui-layout">
	 <c:choose>
    	<c:when test="${not empty bpmPluginContext}">
	    	<div title="${bpmPluginContext.title}任务节点">
	    	<iframe style="border: 0;" width="100%" height="500px" frameborder="no" marginwidth="0" 
			        marginheight="0" scrolling="auto" allowtransparency="yes" 
			        src="${ctx}/flow/node/autoTaskPluginGet?pluginType=${bpmPluginContext.type}&nodeId=${nodeId}&defId=${defId}" pane-src="2"></iframe>
	    	</div>
    	</c:when>
    	<c:otherwise>
			<div id="autoTaskPluginTab" data-options="fit:true,border:false" style="padding-left:5px;">
		    	<c:forEach items="${autoTaskPluginList}" var="autoTaskPlugIn">
		    		<div title="${autoTaskPlugIn.title}任务节点" iframe="true">
		    			<iframe scrolling="no" frameborder="0"  src="${ctx}/flow/node/autoTaskPluginGet?pluginType=${autoTaskPlugIn.type}&nodeId=${nodeId}&defId=${defId}" style="width:100%;height:100%;"></iframe>
		    		</div>
		    	</c:forEach>
			</div>
    	</c:otherwise> 
    </c:choose>
	</body>
</html>