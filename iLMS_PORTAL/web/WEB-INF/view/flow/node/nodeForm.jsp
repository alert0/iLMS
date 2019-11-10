<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/edit.jsp"%>
<script>
	var flowKey = '${flowKey}';
	var parentFlowKey = '${parentFlowKey}';
	var defId="${bpmProcessDef.processDefinitionId}";
</script>

<script type="text/javascript" src="${ctx}/js/platform/flow/bpmFormDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/flow/bpmNodeDefForm.js"></script>
<style type="text/css">
	.formTitle{font-weight: bold;font-size: 16px;}
	.formBlock{margin-top: 10px;margin-left: 20px;}
</style>

</head>
<body class="easyui-layout" style="overflow: hidden; border: 1px solid #959DA4;">
	<div data-options="region:'center',border:false">

		<div class="toolbar-box border">
			<div class="toolbar-head">
				<!-- 顶部按钮 -->
				<div class="buttons">
					<a href="javascript:void(0)" class="btn btn-primary fa-save">保存</a>
				</div>
				<div class="tools">
					
						<span class="fa bigger-120 fa-info-circle" ht-tip
							title="<ul >
									<li>表单
										<ul>
											<li>在线表单,为系统自定义表单。</li>
											<li>url表单,是外部表单。地址写法规则为：如果表单页面平台在同一个应用中，路径从根开始写，<span class='red'>不需要上下文路径</span>，例如 ：/form/addUser。
												如果需要使用的表单不再同一个应用下，则需要写完整路径如:http://***/crm/addUser。
											</li>
										</ul>
									</li>
									<li>处理器
										<ul>
											<li>处理器是需要开发人员定制的,一般情况下<span class='red'>URL表单的处理器需要填写</span>。处理器的写法是 service类名首字母小写 +'.' + 方法名称。
										需要注意的是这个service是需要通过spring容器管理的。另外方法的参数必须是ProcessCmd类。
										例如：userService.add,这个表示是UserService类中有一个，add(ProcessCmd cmd)的方法。</li>
											<li>
											前置和后置处理器区别在于执行时机不同，前置处理器是在启动流程或完成下一步这个动作之前执行的。
											后置处理器在启动流程或完成下一步之后执行的。
											</li>
										</ul>
										
									</li>
								</ul>"></span>
					
				</div>
			</div>

			<form id="dataForm" action="saveForm" method="post">

				<!--  ==================================全局表单====================================================-->
				<div class="formBlock">
					<input type="hidden" name="defId" value="${bpmProcessDef.processDefinitionId}" />
					<input type="hidden" name="parentFlowKey" value="${parentFlowKey}" />
					<input name="nodeId" type="hidden" value="globalFormType" />
					<div class="title">
						<span class="formTitle">全局表单</span>
						<select name="globalFormType" class="selectForm inputText">
							<c:choose>
								<c:when test="${globalForm==null }">
									<option value="" selected="selected">请选择..</option>
									<option value="inner">在线表单</option>
									<option value="frame" >URL表单</option>
								</c:when>
								<c:otherwise>
									<option value="" <c:if test="${globalForm.type=='' }">selected="selected"</c:if>>请选择..</option>
									<option value="inner" <c:if test="${globalForm.type=='INNER' }">selected="selected"</c:if>>在线表单</option>
									<option value="frame"  <c:if test="${globalForm.type=='FRAME'}">selected="selected"</c:if>>URL表单</option> 
								</c:otherwise>
							</c:choose>
						</select>
					</div>

					<div class="content" id="formBox_globalFormType" <c:if test="${globalForm==null}">style="display: none" </c:if>>
						<div class="form_globalFormType" name="globalForm" <c:if test="${globalForm!=null && globalForm.type != 'INNER' }">style="display: none" </c:if>>
							<table width="80%">
								<tr>
									<th>
										PC:
									</th>
									<td>
										<input id="globalFormKey" class="formKey" type="hidden" name="globalFormKey" value="${globalForm.formValue}">
										<input id="globalFormName" class="formName" type="hidden" name="globalFormName" value="${globalForm.name}">
										<span name="spanForm" >
											<a target="_blank" href="${ctx}/form/form/preview?formKey=${globalForm.formValue}">${globalForm.name}</a>
										</span>
										<a href="javascript:void(0);" class="btn btn-sm btn-info fa-search-plus" onClick="bpmNodeDefForm.selectForm(this,'global',true)">选择</a>
										<a href="javascript:void(0);" class="btn btn-sm btn-info fa-undo" onClick="bpmNodeDefForm.clearForm(this)">清除</a>
										<a href="javascript:void(0);" class="btn btn-sm btn-info fa-tachometer" onClick="bpmNodeDefForm.authorize(this,'global','all')">表单授权</a>
									</td>
								</tr>
								<tr>
									<th>
										手机:
									</th>
									<td>
										<input id="globalMobileFormKey" class="formKey" type="hidden" name="globalMobileFormKey" value="${globalMobileForm.formValue}">
										<input id="globalMobileFormName" class="formName" type="hidden" name="globalMobileFormName" value="${globalMobileForm.name}">
										<span name="spanForm">
											<a target="_blank" href="${ctx}/form/form/preview?formKey=${globalMobileForm.formValue}">${globalMobileForm.name}</a>
										</span>
										<a href="javascript:void(0);" class="btn btn-sm btn-info fa-search-plus" onClick="bpmNodeDefForm.selectForm(this,'global',false)">选择</a>
										<a href="javascript:void(0);" class="btn btn-sm btn-info fa-undo" onClick="bpmNodeDefForm.clearForm(this)">清除</a>
										<input type="checkbox" id="setNodeMobile" checked="checked" ><label for="setNodeMobile">设置节点手机表单</label>
									</td>
								</tr>
							</table>
						</div>
						
						<div class="frame_globalFormType" name="globalFrameUrl" <c:if test="${globalForm!=null && globalForm.type != 'FRAME' }">style="display: none" </c:if>>
							URL:
							<input type="text" name="globalFrameUrl" value="${globalForm.formValue}" class="inputText input-wh-12" />
						</div>
								
					</div>
				</div>
				<!--==================================流程实例业务表单====================================================-->
				<c:if test="${empty parentFlowKey}">
					<div class="formBlock" name="instForm">
						<input type="hidden" name="nodeId" value="instFormType" />
						<div class="title">
							<span class="formTitle">流程实例表单:</span>
							<select name="instFormType" id="instFormType" class="selectForm inputText">
								<c:choose>
									<c:when test="${instForm==null }">
										<option value="" selected="selected">请选择..</option>
										<option value="inner">在线表单</option>
										<option value="frame" >URL表单</option> 
									</c:when>
									<c:otherwise>
										<option value="" <c:if test="${instForm.type=='' }">selected="selected"</c:if>>请选择..</option>
										<option value="inner" <c:if test="${instForm.type=='INNER' }">selected="selected"</c:if>>在线表单</option>
										<option value="frame"  <c:if test="${globalForm.type=='FRAME'}">selected="selected"</c:if>>URL表单</option> 
									</c:otherwise>
								</c:choose>
							</select>
						</div>
						<div class="content" id="formBlock_instFormType" class="table-noborder" <c:if test="${instForm==null}">style="display: none" </c:if>>
									
									<div class="form_instFormType" name="instForm" <c:if test="${instForm!=null && instForm.type != 'INNER' }">style="display: none" </c:if>>
										<table width="80%">
											<tr>
												<th>PC:</th>
												<td>
													<input id="instFormKey" class="formKey" type="hidden" name="instFormKey" value="${instForm.formValue }">
													<input id="instFormName" class="formName" type="hidden" name="instFormName" value="${instForm.name }">
													<span name="spanForm" class="spanForm_pc">
														<a target="_blank" href="${ctx}/form/form/preview?formKey=${instForm.formValue}">${instForm.name }</a>
													</span>
													<a href="javascript:void(0);" class="btn btn-sm btn-info fa-search-plus" onClick="bpmNodeDefForm.selectForm(this,'inst',true)">选择</a>
													<a href="javascript:void(0);" class="btn btn-sm btn-info fa-undo" onClick="bpmNodeDefForm.clearForm(this)">清除</a>
													<a href="javascript:void(0);" class="btn btn-sm btn-info fa-tachometer" onClick="bpmNodeDefForm.authorize(this,'global','read',null,true)">表单授权</a>
												</td>
											</tr>
											<tr>
												<th>手机:</th>
												<td>
													<input id="instMobileFormKey" class="formKey" type="hidden" name="instMobileFormKey" >
													<input id="instMobileFormName" class="formName" type="hidden" name="instMobileFormName" >
													<span name="spanForm" class="spanForm_mb" >
														<a target="_blank" href="${ctx}/form/form/preview?formKey=${instMobileForm.formValue}">${instMobileForm.name }</a>
													</span>
													<a href="javascript:void(0);" class="btn btn-sm btn-info fa-search-plus" onClick="bpmNodeDefForm.selectForm(this,'inst',false)">选择</a>
													<a href="javascript:void(0);" class="btn btn-sm btn-info fa-undo" onClick="bpmNodeDefForm.clearForm(this)">清除</a>
												</td>
											</tr>
										</table>
									
										
									</div>
									
									<div class="frame_instFormType" name="instFrameUrl" <c:if test="${instForm!=null && instForm.type != 'FRAME' }">style="display: none" </c:if>>
										URL:
										<input type="text" name="instFormFrameUrl" value="${instForm.formValue}" class="inputText input-wh-12" />
									</div>
									
						</div>
					</div>
				</c:if>
				<!--==================================节点表单====================================================-->
				<div class="foldBox">
					<div class="title">节点表单</div>
					<div class="content">
						<table cellpadding="1" cellspacing="1" class="table-list">
							<tr>
								<th width="10%">节点名</th>
								<th width="5%">
									<label class="checkbox-th">
										<input class="chAll" var="jumpType" type="checkbox" />
										跳转类型
									</label>
								</th>

								<th width="10%">
									<!--<label  class="checkbox-th">
										 <input class="chAll" var="allowExecutorEmpty" type="checkbox" />允许执行人空</label></br> -->
									<label class="checkbox-th">
										<input class="chAll" var="skipExecutorEmpty" type="checkbox" />
										执行人为空跳过
									</label>
								</th>
								<th width="5%">
									<label class="checkbox-th">
										<input class="chAll" var="notifyType" type="checkbox" />
										通知类型
									</label>
								</th>
								<th width="5%">
									<label class="checkbox-th">
										<input class="chAll" var="backMode" type="checkbox" />
										驳回返回方式
									</label>
								</th>
								<th width="20%">处理器</th>
								<th width="20%">表单</th>
							</tr>

							<c:forEach items="${bpmNodeDefList}" var="item" varStatus="status">
								<tr <c:if test="${status.index%2=='0' }">class="odd"</c:if>>
									<td>
										<c:set var="nodeProperties" value="${nodePropertiesMap[item.nodeId]}" />
										<c:set var="nodeForm" value="${item.form}" />
										<c:set var="mobileForm" value="${item.mobileForm}" />
										<c:set var="parentBpmNodeDef" value="${item.parentBpmNodeDef}" />
										<input type="hidden" name="nodeIds" value="${item.nodeId}" />
										<input type="hidden" name="nodeNames" value="${item.name}" />
										<span ht-tip style="cursor: default;" title='${item.name}<c:if test="${parentBpmNodeDef  != null}">(${parentBpmNodeDef.name }) </c:if>'>${item.name}<c:if test="${parentBpmNodeDef  != null}">(${parentBpmNodeDef.name }) </c:if>
										</span>
									</td>
									<td nowrap="nowrap">
										<ul>
											<li>
												<label class="checkbox-inline">
													<input type="checkbox" class="jumpType" name="jumpType_${item.nodeId}" value="common" <c:if test="${nodeProperties!= null && (fn:indexOf(nodeProperties.jumpType,'common')!=-1)}">checked="checked"</c:if> />
													正常跳转
												</label>
											</li>
											<li>
												<label class="checkbox-inline">
													<input type="checkbox" class="jumpType" name="jumpType_${item.nodeId}" value="select" <c:if test="${nodeProperties!= null && (fn:indexOf(nodeProperties.jumpType,'select')!=-1)}">checked="checked"</c:if> />
													选择路径跳转
												</label>
											</li>
											<li>
												<label class="checkbox-inline">
													<input type="checkbox" class="jumpType" name="jumpType_${item.nodeId}" value="free" <c:if test="${nodeProperties!= null && (fn:indexOf(nodeProperties.jumpType,'free')!=-1)}">checked="checked"</c:if> />
													自由跳转
												</label>
											</li>
										</ul>
									</td>
									
									<td>
										<ul>
											<!-- <li><label class="checkbox-td"><input type="checkbox" class="allowExecutorEmpty" name="allowExecutorEmpty_${item.nodeId}" value="true"   <c:if test="${nodeProperties!= null && nodeProperties.allowExecutorEmpty}">checked="checked"</c:if>/>允许执行人空</label></li> -->
											<li>
												<label class="checkbox-td">
													<input type="checkbox" class="skipExecutorEmpty" name="skipExecutorEmpty_${item.nodeId}" value="true" <c:if test="${nodeProperties!= null && nodeProperties.skipExecutorEmpty}">checked="checked"</c:if> />
													执行人为空跳过
												</label>
											</li>
										</ul>
									</td>
									<td>
										<ul>
											<c:forEach items="${handlerList}" var="model">
												<li>
													<label class="checkbox-inline">
														<input type="checkbox" class="notifyType" name="notifyType_${item.nodeId}" value="${model.type}" <c:if test="${nodeProperties!= null && (fn:indexOf(nodeProperties.notifyType,model.type)!=-1)}">checked="checked"</c:if> />${model.title}</label>
												</li>
											</c:forEach>

										</ul>
									</td>
									<td>
									<label><input type="checkbox" class="backMode" name="backMode_${item.nodeId}" value="direct" <c:if test="${nodeProperties!= null && (fn:indexOf(nodeProperties.backMode,'direct')!=-1)}">checked="checked"</c:if> />回到本节点</label>
									<label><input type="checkbox" class="backMode" name="backMode_${item.nodeId}" value="chart" <c:if test="${nodeProperties!= null && (fn:indexOf(nodeProperties.backMode,'chart')!=-1)}">checked="checked"</c:if> />按流程图执行</label>
									</td>
									<td>
										<table class="table-detail">
											<tr>
												<td nowrap="nowrap" class="head">前置:</td>
												<td>
													<input type="text" name="prevHandler_${item.nodeId}" class="inputText handler"  style="width:100%;"  value="${nodeProperties.prevHandler}" />
												</td>
											</tr>
											<tr>
												<td nowrap="nowrap" class="head">后置:</td>
												<td>
													<input type="text" name="postHandler_${item.nodeId}" class="inputText handler"  style="width:100%;"  value="${nodeProperties.postHandler}" />
												</td>
											</tr>
										</table>
									</td>
									<td class='formBlock'>
										<input name="nodeId" type="hidden" value="${item.nodeId}" />
										<table class="table-detail">
											<tr>
												<th nowrap="nowrap" width="20%">表单类型:</th>
												<td>
													<select name="nodeFormType" class="inputText selectForm">
														<option value="" <c:if test="${nodeForm.type=='' }">selected="selected"</c:if>>请选择..</option>
														<option value="inner" <c:if test="${nodeForm.type=='INNER' }">selected="selected"</c:if>>在线表单</option>
														<option value="frame"  <c:if test="${nodeForm.type=='FRAME'}">selected="selected"</c:if>>URL表单</option>
													</select>
												</td>
											</tr>
											<tr class="form_${item.nodeId} trNodeForm" <c:if test="${nodeForm == null||( nodeForm!=null && nodeForm.type!='INNER')}">style="display: none" </c:if>>
												<th nowrap="nowrap" class="head" width="20%">表单:</th>
												<td>
													<table name="tbNodeForm">
														<tr>
															<th>PC:</th>
															<td>
																<input type="hidden" class="formKey" name="formKey" value="${nodeForm.formValue}" />
																<input type="hidden" class="formName" name="formName" value="${nodeForm.name}">
																<span name="spanForm" class="spanNodeForm">
																	<a target="_blank" href="${ctx}/form/form/preview?formKey=${nodeForm.formValue}">${nodeForm.name}</a>
																</span>
																<a href="javascript:void(0);" class="btn btn-sm btn-info fa-search-plus" onClick="bpmNodeDefForm.selectForm(this,'node',true)">选择</a>
																<a href="javascript:void(0);" class="btn btn-sm btn-info fa-undo" onClick="bpmNodeDefForm.clearForm(this)">清除</a>
																<a href="javascript:void(0);" class="btn btn-sm btn-info fa-tachometer" onClick="bpmNodeDefForm.authorize(this,'node','all','${item.nodeId}')">表单授权</a>
																
																<c:if test="${item.type=='USERTASK' or item.type=='SIGNTASK' }">
																	<a href="javascript:void(0);" class="btn btn-sm btn-info fa-tachometer" onClick="bpmNodeDefForm.authorizeSub('${param.defId}','${item.nodeId}','${param.parentDefKey}')">子表数据授权</a>
																</c:if>
															</td>
														</tr>
														<tr>
															<th>手机:</th>
															<td class="tdNodeMobile">
																<input type="hidden" class="formKey" name="mobileFormKey" value="${mobileForm.formValue}" />
																<input type="hidden" class="formName" name="mobileFormName" value="${mobileForm.name}">
																<span name="spanForm" class="spanNodeForm">
																	<a target="_blank" href="${ctx}/form/form/preview?formKey=${mobileForm.formValue}">${mobileForm.name}</a>
																</span>
																<a href="javascript:void(0);" class="btn btn-sm btn-info fa-search-plus" onClick="bpmNodeDefForm.selectForm(this,'node',false)">选择</a>
																<a href="javascript:void(0);" class="btn btn-sm btn-info fa-undo" onClick="bpmNodeDefForm.clearForm(this)">清除</a>
															</td>
														</tr>
													</table>
													
												</td>
											</tr>
											
											<tr class="frame_${item.nodeId} trNodeFrame" <c:if test="${nodeForm == null || (nodeForm!=null && nodeForm.type !='FRAME')}">style="display: none" </c:if>>
												<th class="head">URL</th>
												<td>
													<input type="text" name="frameUrl" value="${nodeForm.formValue}" class="inputText" style="width:100%;" />
												</td>
											</tr>
											
										</table>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>