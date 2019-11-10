<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<!DOCTYPE html>
<html ng-app="formApp">
	<head>
		<%@include file="/commons/include/ngEdit.jsp"%>
		<%@include file="/commons/include/zTree.jsp"%>
		<script type="text/javascript">
			window.UEDITOR_HOME_URL="${ctx}/js/lib/ueditor/";
			var formType = "${form.formType}";
		</script>
		<script type="text/javascript"  src="${ctx}/js/lib/ueditor/bpmform.udeitor.config.js"></script>
	    <script type="text/javascript"  src="${ctx}/js/lib/ueditor/ueditor.all.js"> </script>
	    <script type="text/javascript"  src="${ctx}/js/lib/ueditor/lang/zh-cn/zh-cn.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/form/service/FormDef.js"></script>
		
		<script type="text/javascript" src="${ctx}/js/platform/form/controller/BpmFormEdit.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/form/service/BpmFormService.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/system/dialog/uploadDialog.js"></script>
		
		
		<f:link href="image/tab/tab.css"></f:link>

	    <script type="text/javascript" src="${ctx}/js/lib/ueditor/editor_api.js"> </script>
		<script type="text/javascript">
			var defId="${form.defId}";
			var formId="${form.formId}";
			var name = "${form.name}";
			var formKey ="${form.formKey}";
			var isFormPreview=true;
			$(function(){
				var act=formKey==''?'添加':'编辑';
				var formType = "${form.formType}";
				var pageName=formType!='mobile'?'业务表单':'手机表单';
				$($('.panel-header > .panel-title')[0]).text(act+pageName);
			});
			function showResponse(responseText) {
				var resultMessage = new com.hotent.form.ResultMessage(responseText);
				if (resultMessage.isSuccess()) {
					$.topCall.message({base:{type:'alert',title:'温馨提示',msg:resultMessage.getMessage(),fn:function(r){
						 windClose();
	     			}}});
					
				} else {
					$.topCall.error(resultMessage.getMessage());
				}
			}
			
			function windClose(){
				if(parent.formNextStep){
					if(window.parent.opener)window.parent.opener.reload();
					window.parent.close();
				}else{
					if(window.opener)window.opener.location.reload();
					window.close();
				}
			}
			
			function formStepFinished(){
				var scope = AngularUtil.getScope();
				scope.save();
			}
		</script>
		<style>
			*{ line-height:1.44;}
			#editor{
				width:calc(100%-20px);
				margin:0 5px;
				overflow:hidden;
			}
			#edui1_iframeholder{
			height: 700px;
			}
			.panel-body{overflow-x: hidden; overflow-y: auto;}
			
		</style>
	</head>
	<body>
		<div ng-controller="formEditCtrl" class="easyui-layout" data-options="fit:false" style="width: 99%;height: 100%">
			<div data-options="region:'north'" style="width:100%;">
				<div class="panel-header" style="width:100%;">
				 <div class='panel-title' ></div>
				</div>
			</div>
			 <div data-options="region:'west',title:'表单字段'" style="width:200px; border-right:1px solid #d9d9d9">
				<div id="boTree" class="ztree" style="height:465px;"></div>
			</div>
	  		<div data-options="region:'center'" id="center">
				<!-- 顶部按钮 -->
				<div class="toolbar-panel row" ng-show="isStepCreate">
					<div class="buttons" style="margin-left:20px;">
						<a href="javascript:;" class="btn btn-primary btn-sm fa-save" ng-click="save();"><span>保存</span></a>
						<a href="javascript:;" class="btn btn-primary btn-sm fa-eye" ng-click="preview();"><span>表单预览</span></a>
						<c:if test="${!empty form.formId }">
						<a href="javascript:;" class="btn btn-primary btn-sm fa-detail" ng-click="showHis();"><span>显示历史记录</span></a>
						</c:if>
						<a href="javaScript:windClose()"  class="btn btn-sm btn-primary fa-back" ><span>关闭</span></a>
					</div>
				</div>
				<form id="bpmForm" name="bpmForm" action="save" method="post" class="table-form">
					<table style="width: 100%">
						<tr>
						<th>名称</th>
						<td><input name="name" value="${form.name}" class="inputText" style="width: 300px" ng-model="name" ht-validate="{required:true,maxlength:300}"/></td>
						<th>表单标识<span class="red">*</span></th> 
						<td><input name="formKey" value="${form.formKey}" class="inputText" ng-model="formKey" <c:if test="${!empty form.formKey}">readonly</c:if> ht-validate="{required:true,varirule:true,maxlength:300}"/></td>
						</tr>
					</table>
					<input type="hidden" name="defId" value="${form.defId}"/>
					<input type="hidden" name="typeId" value="${form.typeId}"/>
					<input type="hidden" name="typeName" value="${form.typeName}"/>
					<input type="hidden" name="formType" value="${form.formType}"/>
					<input type="hidden" name="isMain" value="${form.isMain}"/>
					<input type="hidden" name="version" value="${form.version}"/>
					<input type="hidden" name="formId" value="${form.formId}"/>
					<input type="hidden" name="status" value="${form.status}"/>
					<input type="hidden" name="formTabTitle" id="formTabTitle" value="${form.formTabTitle}"/>
					<textarea id="editor" name="formHtml" type="text/plain">${fn:escapeXml(form.formSourceHtml)}</textarea>
					
					<div class="pagetab" ng-if="formType!='mobile'">
						<ul id="pageList">
							<li ng-repeat="title in aryTitle track by $index" ng-click="changeEdit($index)" class="{{$index==currentEdit?'current':''}}">
								<b>
									<span ng-bind="title" ng-hide="$index==currentToEdit" ng-dblclick="toEdit($index,true)"></span>
									<input type='text' maxlength='20' ng-model="aryTitle[$index]" ng-blur="toEdit($index,0)" ng-if="$index==currentToEdit" style='border-style:none;height:16px;' size='10'/>
								</b>
							</li>
						</ul>
						<span class="add">
							<img id="addPage" ng-click="addTab()" src="${ctx}/css/image/tab/icon_plus.gif" class="imgPlus" title="插入新页面">
							<img id="delPage" ng-click="delTab()" src="${ctx}/css/image/tab/icon_minus.gif" class="imgDelete" title="删除当前页">
						</span>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>