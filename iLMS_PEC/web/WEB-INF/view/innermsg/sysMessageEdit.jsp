<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/lib/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/lib/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/innermsg/SysMessageController.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/innermsg/SysMessageService.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/innermsg/MessageTypeService.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/dialog/UserSelectDialog.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/dialog/groupSelectDialog.js"></script>
<script type="text/javascript">
		</script>
</head>
<body ng-app="msgApp" ng-controller="SysMessageController">
	<div data-options="region:'center',border:false">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel col-md-13 ">
			<div class="buttons">
				<a class="btn btn-primary fa-save" ng-click="save()" href="javascript:;"><span>发送消息</span></a>
				<a class="btn btn-primary fa-back" href="list.ht"><span>返回</span></a>
			</div>
		</div>
		<form id="sysMessageForm" action="save.ht" method="post">
			<table class="table-form" cellspacing="0">
				<tr>
					<th><span>主题:</span></th>
					<td><input class="inputText" type="text" id="subject"
						name="subject" ng-model="sysMessage.subject"/>
						</td>
				</tr>
				<tr>
					<th><span>消息类型:</span></th>
					<td><select class="inputText" id="messageType"
						name="messageType" ng-model="sysMessage.messageType"
						ng-change="changeMsgType()"
						ng-options="e.value as e.key for e in displayMsgType"
						>

					</select></td>
				</tr>
				<tr id="receiverNameTr">
					<th><span>收信人:</span></th>
					<td><input class="inputText" type="text" id="receiverName"
						name="receiverName" readonly="readonly"
						ng-model="sysMessage.receiverName" /> <a href="javascript:;"
						ng-click="showUserDialog()" class="link get">选择</a> <a
						href="javascript:;" ng-click="reSetUser()" class="link clean">清空</a>
				</tr>
				<tr id="receiverOrgNameTr">
					<th width="20%">收信组织:</th>
					<td><input class="inputText" type="text" id="receiverOrgName"
						name="receiverOrgName" size="80" readonly="readonly"
						ng-model="sysMessage.receiverOrgName" /> <a href="javascript:;"
						ng-click="showOrgDialog()" class="link get">选择</a> <a
						href="javascript:;" ng-click="reSetOrg()" class="link clean">清空</a>
				</tr>

				<tr id="canReplyTr" style="display: none;">
					<th><span>需要回复:</span></th>
					<td>
								<input type="radio" name="canReply"
						ng-model="sysMessage.canReply" value="1" />是 <input type="radio"
						name="canReply" ng-model="sysMessage.canReply" value="0" />否
					</td>
				</tr>
				<tr>
					<th><span>内容:</span></th>
					<td>
						<textarea id="content" name="content"
							style="height: 100%; width: 100%" ng-model="sysMessage.content"></textarea>
						<script type="text/javascript">
				    				var ue = UE.getEditor('content');
				   				 </script>
					</td>
				</tr>
				<input type="hidden" name="id" ng-model="sysMessage.id" />
			</table>
		</form>
	</div>
</body>
</html>