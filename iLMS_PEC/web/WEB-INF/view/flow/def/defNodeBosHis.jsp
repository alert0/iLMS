<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/commons/include/list.jsp" %>
 
	<script type="text/javascript" src="${ctx}/js/platform/system/dialog/boSelectDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/platform/flow/bpmSetFormBoScript.js"></script>
	<script type="text/javascript">
		$(function() {
			var flowId = '${bpmDefinition.defId}';
			var flowKey = '${bpmDefinition.defKey}';
			$("#flowId").val(flowId);
			$("#flowKey").val(flowKey);
			initData();
		});
		
		//初始化BO
		function initData(){
			var rootJson = "${rootJson}";
			if(!rootJson){
				return;
			}
			var root = parseToJson(rootJson);
			if(!root){
				return;
			}
			//初始化BO
			initSetBos(root.boSaveMode,root.arrayBo);
			
			//初始化BOATTR
			initSetBoAttr(root.arraySetBoAttr);
		}
	</script>
	</head>
<body style="border:1px solid #959DA4;">
	<div class="scoll-panel">
		<div class="gray-div">
			<input type="hidden" id="flowId" name="flowId" />
			<input type="hidden" id="flowKey" name="flowKey" />
			<div class="toolbar-panel col-md-13 ">
				<div class="buttons" style="margin-left:6px;">
			       <a class="btn btn-primary fa fa-save" href="javascript:saveSetBos()">保存</a>
			    </div>
		    </div>
			<div id="setBosTab" class="easyui-tabs"  style="text-align:center;height:650px;" >
				<!-- BO选定区  class="container" -->
				<div title="绑定">
					<div class="toolbar-panel col-md-13 ">
						<span style="margin-left:8px;">业务数据保存方式：</span>
						<label class="radio-inline">
							<input type="radio" name="boSaveMode" value="database" checked="checked"/>业务表
						</label>
						<label class="radio-inline">
							<input type="radio" name="boSaveMode" value="boObject"/>实例表
						</label>
						
						<div class="buttons">	      
					       <a class="btn btn-sm btn-primary fa fa-add" href="javascript:selectSetBos('')">选择</a>
					       <a class="btn btn-sm btn-primary fa fa-delete" href="javascript:cleanMySelectBo()">清空</a>
					    </div> 
				    </div>
					
					<div    style="height:540px; overflow:auto">
						<table id="myBoTable" class="table-form"  style="text-align: center;">
							<thead>
								<tr>
									<th width="5%">序号</th>
									<th width="25%">名称</th>
									<th width="25%">描述</th>
									<th width="25%">编码</th>
<!-- 								<th width="10%">必填</th>  -->
									<th width="10%">操作</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
				
				<!-- BO设置区  class="container" -->
				<div title="设置">
					<div class="toolbar-panel col-md-13 ">
						<span style="margin-left:8px;">请选择节点：</span>
						<select id="nodeId" name="nodeId" class="inputText" >
							<c:forEach var="node" items="${nodeList}">
									<option value="${node.nodeId}">${node.name}</option>
				            </c:forEach>
						</select>
						<div class="buttons" >	      
					       <a class="btn btn-sm btn-primary fa fa-add" href="javascript:selectSetNodeBos()">选中节点设置</a> 
					       <a class="btn btn-sm btn-primary fa fa-remove" href="javascript:cleanMyNodeBoAttr()">清空选中节点设置</a>
					       <a class="btn btn-sm btn-primary fa fa-delete" href="javascript:cleanALlBoAttr()">清空所有设置</a>
					    </div>
				    </div>

					<div style="height:540px; overflow:scroll">
						<table id="boAttrSelectTable" class="table-form">
							<c:forEach var="node" items="${nodeList}">
									<tr id="boAttrSelectTr_${node.nodeId}" class="myNodeShowTr" nodeId="${node.nodeId}" style="display: none;text-align: center;vertical-align: baseline;min-height: 50px">
										<th>${node.name}(${node.nodeId})</th>
										<td style="text-align: center;">
											<input type="hidden" id="myBoAttrArryJson_${node.nodeId}" name="myBoAttrArryJson" />
											<br/>
											<table id="boAttrSelectTable_${node.nodeId}" class="table-form"  style="text-align: center;">
												<thead>
													<tr>
														<th width="5%">序号</th>
														<th width="35%">名称</th>
														<th width="25%">设置描述</th>
														<th width="20%">设置类型</th>
														<th width="15%">管理</th>
													</tr>
												</thead>
											</table>
											<br/>
		                                </td>
									</tr>
							</c:forEach>
					   </table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>