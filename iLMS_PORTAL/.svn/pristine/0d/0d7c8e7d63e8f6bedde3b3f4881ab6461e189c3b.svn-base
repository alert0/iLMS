<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/include/edit.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/util/formdata.js"></script>

<script type="text/javascript" src="${ctx}/js/platform/system/dialog/bpmDefDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/bpm/agentSetting/bpmAgentSettingConditionDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/bpm/agentSetting/bpmAgentSetting.js"></script>
<script type="text/javascript">
	var currentUserId = "${currentUserId}";
	var isMgr = '${isMgr}';
</script>
</head>
<body class="easyui-layout" fit="true">
		<div class="toolbar-panel ">
			<div class="buttons" >
				<button href="javaScript:void(0)" class="btn btn-primary btn-sm fa-save">
					<span>保存</span>
				</button>
				<button href="javaScript:void(0)" onclick="HT.window.closeEdit(true,'grid')" class="btn btn-primary btn-sm fa-back">
					<span>返回</span>
				</button>
			</div>
		</div>
		<form id="bpmAgentSettingForm" action="save" method="post">
			<input type="hidden" id="isMgr" name="isMgr" value="${isMgr}">
			<table class="table-form" cellspacing="0" type="main">
				<input type="hidden" name="id" id="id" value="${bpmAgentSetting.id}" />
				<tr>
					<th>
						代理类型:
					</th>
					<td colspan="3" id="agentType">
							<label class="radio-inline"><input type="radio" name="type" value="1" <c:if test="${bpmAgentSetting.type == 1}"> checked="checked"</c:if> />
							全权代理</label>
							<label class="radio-inline"><input type="radio" name="type" value="2" id="typePart" <c:if test="${bpmAgentSetting==null or bpmAgentSetting.type == 2}"> checked="checked"</c:if> />
							部分代理</label>
							<label class="radio-inline"><input type="radio" name="type" value="3" <c:if test="${bpmAgentSetting.type == 3}"> checked="checked"</c:if> />
							条件代理</label>
					</td>
				</tr>

				<tr>
					<th>
						<span class="required">*</span>标题:
					</th>
					<td colspan="3">
						<input type="text" id="subject" name="subject" value="${bpmAgentSetting.subject}" class="inputText input-width-50" validate="{required:true,maxlength:192}" />
					</td>
				</tr>

				<c:if test="${isMgr eq '1'}">
					<tr>
						<th>
							<span class="required">*</span>授权人姓名:
						</th>
						<td colspan="3">
							<input type="hidden" id="authId" name="authId" value="${bpmAgentSetting.authId}" class="inputText" validate="{required:true,maxlength:192}" />
							<input type="text" id="authName" disabled="disabled" name="authName" value="${bpmAgentSetting.authName}" class="inputText" validate="{required:true,maxlength:192}" />
							<a href="javaScript:void(0)" class="btn btn-sm btn-info  fa-search-plus selectUser" selectId="authId" selectName="authName">
								选择
							</a>
						</td>
					</tr>
				</c:if>

				<tr>
					<th>
						<span class="required">*</span>
						开始日期:
					</th>
					<td>
						<input type="text" id="startDate" name="startDate" validate="{required:true,dateRangeStart:'endDate'}" value="<fmt:formatDate value="${bpmAgentSetting.startDate}" pattern="yyyy-MM-dd HH:mm:ss" />" class="inputText datetime" />
					</td>

					<th>
						<span class="required">*</span>
						结束日期:
					</th>
					<td>
						<input type="text" id="endDate" name="endDate" validate="{required:true,dateRangeEnd:'startDate'}" value="<fmt:formatDate value="${bpmAgentSetting.endDate}" pattern="yyyy-MM-dd HH:mm:ss" />" class="inputText datetime"  />
					</td>
				</tr>
				<tr>
					<th>是否有效:</th>
					<td colspan="3">
							<label class="radio-inline"><input id="enabled_n" name="isEnabled" value="N" type="radio" <c:if test="${bpmAgentSetting.isEnabled!='Y'}" >checked="checked" </c:if> />
							禁止</label>
							<label class="radio-inline"><input id="enabled_y" name="isEnabled" value="Y" type="radio" <c:if test="${bpmAgentSetting==null or bpmAgentSetting.isEnabled=='Y'}" >checked="checked" </c:if> />
							启用</label>
					</td>
				</tr>
				<tr id="agentTr">
					<th>
						代理人:
					</th>
					<td colspan="3">
						<input type="hidden" id="agentId" name="agentId" value="${bpmAgentSetting.agentId}" class="inputText" />
						<input type="text" id="agent" name="agent" value="${bpmAgentSetting.agent}" class="inputText" disabled="disabled" />
						<a href="javaScript:void(0)" class="btn btn-sm btn-info  fa-search-plus selectUser" selectId="agentId" selectName="agent">
						选择
						</a>
					</td>
				</tr>

				<!-- 条件代理，设置流程 -->
				<tr id="conditionFlow" style="display: none">
					<th>
						代理设置:
					</th>
					<td colspan="3">
						<input type="hidden" id="conditionFlowKey" name="flowKey" value="${bpmAgentSetting.flowKey}" />
						<input type="text" id="conditionFlowName" name="flowName" value="${bpmAgentSetting.flowName}" class="inputText input-wh-8" disabled="disabled" />
						&nbsp;
						<a href="javaScript:void(0)" class="btn btn-sm btn-info  fa-share-alt-square selectConditionFlow" selectId="conditionFlowKey" selectName="conditionFlowName">
							选择
						</a>
						<a href="javaScript:void(0)" class="btn btn-sm btn-info  fa-cogs  setCondition">
							设置代理条件
						</a>
					</td>
				</tr>

			</table>
			<!-- 部分代理，设置流程 -->
			<div id="partFlow" <c:if test="${bpmAgentSetting.type==0 or bpmAgentSetting.type==2}"> style="display: none"</c:if>>
				<div class="toolbar-panel ">
					<div class="buttons">
						<a href="javaScript:void(0)" class="btn btn-sm btn-info  fa-share-alt-square addPartFlow">
							<span>新增</span>
						</a>
					</div>
				</div>
				<table class="table-list" id="def" cellpadding="1" cellspacing="1" style="width: 100%" type="sub">
					<thead>
						<tr>
							<th>流程名称</th>
							<th>管理</th>
						</tr>
					</thead>
					<tbody id="bpmAgentItem">
						<c:choose>
							<c:when test="${fn:length(bpmAgentSetting.defList)>0}">
								<c:forEach items="${bpmAgentSetting.defList}" var="bpmAgentItem">
									<tr id="def_${bpmAgentItem.flowKey}" type="subdata">
										<td>
											<input type="hidden" name="flowKey" value="${bpmAgentItem.flowKey}" />
											<input type="hidden" name="flowName" value="${bpmAgentItem.flowName}">
											<a href="${ctx}/flow/def/get?defKey=${bpmAgentItem.flowKey}" target="_blank">${bpmAgentItem.flowName}</a>
										</td>
										<td>
											<a href="javaScript:void(0)" class="btn btn-sm btn-danger fa-remove remove-flow">删除</a>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr id="firstRow">
									<td colspan="2" align="center">
										<font color='red'>还未选择流程</font>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<!-- 条件代理，设置条件 -->
			<div id="agentConditionDiv" style="display: none">
				<table id="condition" type="sub">
					<c:forEach items="${bpmAgentSetting.conditionList}" var="conditionItem">
						<tr type="subdata">
							<input type="hidden" name="condition" value="${fn:escapeXml(conditionItem.condition)}">
							<input type="hidden" name="conditionDesc" value="${conditionItem.conditionDesc}">
							<input type="hidden" name="agentId" value="${conditionItem.agentId}">
							<input type="hidden" name="agentName" value="${conditionItem.agentName}">
						</tr>
					</c:forEach>
				</table>
			</div>
		</form>


	<textarea id="tableRowTemplate" style="display: none;">
		<tr id="def_#defKey" type="subdata">
			<td>
				<input type="hidden" name="flowKey" value="#defKey">
				<input type="hidden" name="flowName" value="#name">
				<a href="${ctx}/flow/def/get?defKey=#defKey" target="_blank">#name</a>
			</td>
			<td>
				<a href="javaScript:void(0)" class="btn btn-sm btn-danger fa-remove remove-flow">删除</a>
			</td>
		</tr>
	</textarea>

	<textarea id="agentConditionTableRowTemplate" style="display: none;">
		<tr type="subdata">
			<td>
				<input type="hidden" name="condition" value="#condition">
				<input type="hidden" name="conditionDesc" value="#conditionDesc">
				<input type="hidden" name="agentId" value="#agentId">
				<input type="hidden" name="agentName" value="#agentName">
			</td>
		</tr>
	</textarea>
</body>
</html>