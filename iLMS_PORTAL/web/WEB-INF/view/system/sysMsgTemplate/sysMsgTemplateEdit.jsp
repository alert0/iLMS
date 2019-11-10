<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript">
			window.UEDITOR_HOME_URL="${ctx}/js/lib/ueditor/";
		</script>
		<script type="text/javascript" charset="utf-8" src="${ctx}/js/platform/system/sysTemplate/bpmdef.udeitor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/ueditor.all.min.js"> </script>
	    <script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/lang/zh-cn/zh-cn.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/system/sysTemplate/templateService.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/system/sysTemplate/templateController.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/javacode/codemirror.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/javacode/InitMirror.js"></script>
		<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/plugins/msgTemplateVar.js"></script>
		 
		<script type="text/javascript">
		var id="${param.id}";
		var ue;
		
		 $(function() {
		    ue = UE.getEditor('editor');
			ue.addListener("ready",function(editor){
				//获取对象的scope。
				var scope = getScope();
				scope.ueditorReady = true;
				scope.$digest();
			});
			$("a[name='signResult']").click(function(){
			    addToTextarea($(this).attr("result"));
			 });
		 });
		//将条件表达式追加到脚本输入框内
	    function addToTextarea(str){
			InitMirror.editor.insertCode(str);
	    };
		</script>
	</head>
	<body ng-app="templateApp" ng-controller="TemplateController">
		<div data-options="region:'center',border:false">
			<!-- 顶部按钮 -->
			<div class="toolbar-panel col-md-13 ">
				<div class="buttons" >
					<a class="btn btn-primary btn-sm fa-save" href="javascript:void(0);" ng-click="save()"><span>保存</span></a>
					<a class="btn btn-primary btn-sm fa-back" href="#" onclick="HT.window.closeEdit(true,'grid')"><span>返回</span></a>
				</div>
			</div>
			<form name="myForm" action="save" method="post">
				<table class="table-form"  cellspacing="0" >
						<tr>								
							<th><span>模版名称:</span><span class="required">*</span></th>
							<td>
								<input class="inputText" type="text" id="name" name="name" ng-model="prop.template.name" ht-validate="{required:true,maxlength:765}"  />
							</td>								
						</tr>
						<tr>								
							<th><span>模板分类:</span><span class="required">*</span></th>
							<td>
								<select class="inputText" id="typeKey" name="typeKey" ng-model="prop.template.typeKey" ng-change="changeType()" ng-options="e.value as e.key for e in typeSelect" ht-validate="{required:true}" ></select>
							</td>								
						</tr>
						<tr>								
							<th><span>模版业务键:</span><span class="required">*</span></th>
							<td>
								<input class="inputText" type="text" id="key" name="key" ng-model="prop.template.key" ht-validate="{required:true}"  />
							</td>								
						</tr>
						<tr>								
							<th><span>短信模板ID</span> </th>
							<td>
								<input class="inputText" type="text" id="smsTemplateNo" name="smsTemplateNo" ng-model="prop.template.smsTemplateNo"   />
							</td>								
						</tr>
						
						<tr>								
							<th><span>语音模板ID:</span> </th>
							<td>
								<input class="inputText" type="text" id="voiceTemplateNo" name="voiceTemplateNo" ng-model="prop.template.voiceTemplateNo"  />
							</td>								
						</tr>
						
						<tr>								
							<th><span>模板参数:</span></th>
							<td>
								<a href="javascript:void(0);" name="signResult" result='\${instSubject}'>流程实例标题</a>
								<a href="javascript:void(0);" name="signResult" result='\${nodeName}'>节点名称</a>
								<a href="javascript:void(0);" name="signResult" result='\${taskSubject}'>任务标题</a>
								<a href="javascript:void(0);" name="signResult" result='\${cause}'>原因</a>
								<a href="javascript:void(0);" name="signResult" result='\${delegate}'>委托人</a>
								<a href="javascript:void(0);" name="signResult" result='\${agent}'>代理人</a>
								<a href="javascript:void(0);" name="signResult" result='\${receiver}'>收信人</a>
								<a href="javascript:void(0);" name="signResult" result='\${sender}'>发送人</a>
								<br> 选择模板参数的时候，请注意光标位置(光标位置支持[标题，纯文本])。
							</td>								
						</tr>
						
						<tr>								
							<th><span>标题:</span></th>
							<td>
								<textarea class="inputText"  codemirror="true"  mirrorheight="100px"  id="subject" name="subject" style="height: 100px; width: 50%" ng-model="prop.template.subject" ht-validate="{required:true,maxlength:384}"  ></textarea>
							</td>								
						</tr>
						
						<tr>								
							<th><span>纯文本:</span></th>
							<td>
								<textarea class="inputText" codemirror="true"  mirrorheight="100px" id="plain" name="plain" style="height: 100px; width: 100%" ng-model="prop.template.plain"></textarea>
							</td>								
						</tr>
						<tr>								
							<th><span>模版体HTML:</span></th>
							<td>
								<script id="editor" type="text/plain"  style="width:100%;height:100%;"></script>
							</td>								
						</tr>
				</table>
			</form>
		</div>
	</body>
</html>