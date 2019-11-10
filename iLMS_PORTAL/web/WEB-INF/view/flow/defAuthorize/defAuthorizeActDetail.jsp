<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
		<script type="text/javascript">
			$(function(){
				setContainerWidth();
			});
			
			function setContainerWidth(){
            	var w=$(".scoll-panel").width();
            	$("#divContainer").width(w-20);
            }
		</script>
	</head>
	<body >
		<div class="scoll-panel">
			<div class="gray-div">  
				
				<div class="toolbar-panel col-md-12 ">
					<div class="buttons">
				        <a href="javascript:window.opener=null;window.open('','_self');window.close();" class="btn btn-primary" plain="true">关闭</a>
				        <a href="${ctx}/flow/def/bpmnXml?defId=${bpmDefinition.defId}" target="_blank"  class="btn btn-primary">BPMNXML</a>
				        <a href="${ctx}/flow/def/designXml?defId=${bpmDefinition.defId}" target="_blank"  class="btn btn-primary">DesignXML</a>  
				    </div>
				</div>
				
				<div>
					<table class="table-form" >
						<tr>
							<th><span>流程名称:</span></th>
							<td>${bpmDefinition.name}</td>
							<th><span>流程业务主键:</span></th>
							<td>${bpmDefinition.defKey}</td>
						</tr>
						<tr>
							<th><span>流程描述:</span></th>
							<td>${bpmDefinition.desc}</td>
							<th><span>所属分类ID:</span></th>
							<td>${bpmDefinition.typeId}</td>
						</tr>
						<tr>
							<th><span>流程状态。草稿、发布、禁用:</span></th>
							<td>${bpmDefinition.status}</td>
							<th><span>测试状态:</span></th>
							<td>${bpmDefinition.testStatus}</td>
						</tr>
						<tr>
							<th><span>BPMN - 流程定义ID:</span></th>
							<td>${bpmDefinition.bpmnDefId}</td>
							<th><span>BPMN - 流程发布ID:</span></th>
							<td>${bpmDefinition.bpmnDeployId}</td>
						</tr>
						<tr>
							<th><span>版本 - 当前版本号:</span></th>
							<td>${bpmDefinition.version}</td>
							<th><span>版本 - 主版本流程ID:</span></th>
							<td>${bpmDefinition.mainDefId}</td>
						</tr>
						<tr>
							<th><span>版本 - 是否主版本:</span></th>
							<td>${bpmDefinition.isMain}</td>
							<th><span>版本 - 变更理由:</span></th>
							<td>${bpmDefinition.reason}</td>
						</tr>
						<tr>
							<th><span>创建人ID:</span></th>
							<td>${bpmDefinition.createBy}</td>
							<th><span>创建时间:</span></th>
							<td><fmt:formatDate value="${bpmDefinition.createTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
						<tr>
							<th><span>创建者所属组织ID:</span></th>
							<td>${bpmDefinition.createOrgId}</td>
							<th><span>更新人ID:</span></th>
							<td>${bpmDefinition.updateBy}</td>
						</tr>
						<tr>
							<th><span>更新时间:</span></th>
							<td colspan="3"><fmt:formatDate
									value="${bpmDefinition.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
					</table>

				</div>
			</div>
		</div>
	</body>
</html>