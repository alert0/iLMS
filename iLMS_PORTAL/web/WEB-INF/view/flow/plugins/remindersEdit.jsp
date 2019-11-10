<%@page import="com.hotent.sys.api.jms.MessageUtil"%>
<%@page import="com.hotent.bpmx.api.constant.ScriptType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="remindersApp">
<head>
<title>催办设置</title>
<%@include file="/commons/include/ngEdit.jsp" %>
<%@include file="/commons/include/zTree.jsp" %>
<%@include file="/commons/include/codeMirror.jsp"%>

<script type="text/javascript">
	window.UEDITOR_HOME_URL = "${ctx}/js/lib/ueditor/";
</script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/platform/bpm/def/bpmdef.udeitor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript" src="${ctx}/js/platform/system/dialog/scriptSelector.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/flow/plugins/reminders.js"></script>

<style type="text/css">
.CodeMirror-scroll{height: 150px!important;}
.CodeMirror{height: 150px!important;}
</style>
</head>
<body ng-controller="remindersController" class="easyui-layout">
	<div data-options="region:'north',border:false" st>
		<div class="datagrid-toolbar">
			<a href="javascript:;" class="btn btn-primary btn-sm fa-save" ng-click="save()">保存</a>
			<a href="javascript:;" class="btn btn-primary  btn-sm fa-close" onclick="window.selfDialog.dialog('close')">取消</a>
			<a ng-click="add()" class="btn btn-primary btn-sm fa-arrow-right">添加</a>
		</div>
	</div> 
	<div data-options="region:'center',border:false">
		<form name="myForm">
		<div  class="easyui-tabs">
			<div title="催办基本信息">
						<table class="table-form">
							<tr>
								<th>名称:</th>
								<td><input type="text" ng-model="reminder.name" class="inputText" ht-validate="{required:true,maxlength:128}"/></td>
								<th><span>日期类型:</span></th>
									<td>
										<select ng-model="reminder.dateType" class="inputText" >
											<option value="caltime">日历日</option>
											<option value="worktime">工作日</option>
										</select>
								</td>
							</tr>
							<tr>
								<th><span>相对节点:</span></th>
								<td>
									<select ng-model="reminder.relNodeId" class="inputText"  ng-options="node.nodeId as node.name for node in nodeList" ht-validate="{required:true}"
										ht-select-ajax="{url:'${ctx}/flow/node/getNodes?defId=${param.defId}',field:'nodeList'}">
									<option value="">请选择</option> 
									</select> 
								</td>
								<th>节点事件:</th>
								<td>
									<select ng-model="reminder.relNodeEvent" class="inputText">
										<option value="create">创建</option>
										<option value="complete">完成</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>相对到期时间:</th>
								<td colspan="3">
									<span ht-times="reminder.dueTime" ng-model="reminder.dueTime" ht-validate="{required:true,number:true}"></span>
								</td>
							</tr>
							<tr>
								<th>到期动作:
								</th>
								<td colspan="3"><select ng-model="reminder.dueAction" class="inputText">
										<option value="no-action" selected="selected">无动作</option>
										<option value="auto-next">执行同意操作</option>
										<option value="end-process">结束该流程</option>
										<option value="call-method">调用指定方法</option>
								</select>
								</td>
							</tr>
							<tr ng-show="reminder.dueAction=='call-method'">
								<th>执行脚本:</th>
								<td colspan="3">
									<div>
									    <a class="btn btn-primary btn-xs" ng-click="selectScript('callScript')">常用脚本</a>
										<a class="varTree btn btn-primary btn-xs" broadcast="callScript">可选变量</a> 
									</div>  
									<textarea ng-model="reminder.callScript" ui-codemirror broadcast="callScript" ></textarea>	 
								</td>
							</tr>	
							<tr>
								<th><a href="javascript:;" style="text-decoration: none;" class="fa fa-exclamation-circle"  ht-tip
										title="return true,则执行该条催办；return talse,表示不执行该催办。如果表达式为空，将视为返回true。"></a>
										条件表达式:</th>
								<td colspan="3" >
									<div>
									    <a class="btn btn-primary btn-xs" ng-click="selectScript('condition')">常用脚本</a>
										<a class="varTree btn btn-primary btn-xs" broadcast="condition">可选变量</a> <div id="tempTree"></div>
									</div>
									<textarea ng-model="reminder.condition" ui-codemirror broadcast="condition" style="height:200px"></textarea> 
								</td>
							</tr>
						</table>
				</div>

			<div title="消息设置">
				<table  class="table-form" cellspacing="0">
					<tr>
						<td colspan="4">
							<span ht-boolean class="checkbox" text="催办期间定时发送消息" ng-model="reminder.isSendMsg"></span>
						</td>
					</tr>
					<tr ng-if="reminder.isSendMsg">
						<th>
						发送相对时间:</th>
						<td colspan="3">
							<span ht-times="reminder.msgSendTime" ng-model="reminder.msgSendTime" ht-validate="{required:true,number:true}"></span>
						</td>
					</tr>
					<tr ng-if="reminder.isSendMsg">
						<th><span>发送信息次数: </span></th>
						<td>
							<select ng-model="reminder.msgCount" class="inputText" ht-validate="{required:true}">
								<option>请选择</option>
								<c:forEach var="i" begin="1" end="10" step="1"> <option value="${i}">${i}次</option> </c:forEach>
							</select>
						</td>
					</tr>
					<tr ng-if="reminder.isSendMsg">
						<th><span>发送消息间隔: </span></th>
						<td>
							<span ht-times length="5" ng-model="reminder.msgInterval" ht-validate="{required:true,number:true}"></span>
						</td>
					</tr>
					<tr>
						<th>消息类型: </th>
						<td>
							<c:set var="handlerList" value="<%=MessageUtil.getHanlerList()%>"></c:set>
							<c:forEach items="${handlerList}"  var="model">
								<label><input type="checkbox" ht-checkbox  ng-model="reminder.msgType" value="${model.type}" />${model.title}</label>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th>富文本模板:</th>
						<td>
							<div>提示：可以用{title}来代表任务名称、用{time}来代表收到任务的时间。 </div>
							<ht-editor ng-model="reminder.htmlMsg"  editorConfig = "editorConfig" style="width:600px"></ht-editor>
						</td>
					</tr>
					<tr>
						<th>普通文本内容:</th>
						<td>
							<textarea ng-model="reminder.plainMsg" id="plainText" class="inputText" cols="80" rows="5"></textarea>
						</td>
					</tr>
				</table>
			</div>
			<div title="预警配置">
				<div class="toolbar-panel">
					<a class="btn btn-sm  fa-add" ng-click="addWarningSet()">添加预警项</a>
				</div>
				<table class="table">
					<tr>
						<th> 名称</th><th>期限</th><th>预警级别</th><th>管理</th>
					</tr>
					<tr ng-repeat="warnSet in reminder.warningSetList">
						<td><input ng-model="warnSet.warnName" class="inputText" ht-validate="{required:true}"/></td>
						<td><span ht-times="warnSet.warnTime" ng-model="warnSet.warnTime" ht-validate="{required:true,number:true}"></span></td>
						<td>
							<select ng-model="warnSet.level" ng-options="w.level as w.name for w in warnSetting" class="inputText" ht-validate="{required:true}">
							</select>
						</td>
						<td><a href="javascript:;" class="btn fa fa-remove btn-sm" ng-click="ArrayTool.del($index,reminder.warningSetList)" ></a></td>
					</tr>
				</table>
			</div>
		</div>
		</form>
	</div>
	<div data-options="region:'east',border:true" title="催办列表" style="text-align: center; width: 180px;">
		<table class="table-list"> 
			<thead>
				<tr>
					<th width="120px">名称</th>
					<th width="60px">操作</th>
				</tr>
			</thead>
				<tr ng-repeat="r in reminders.reminderList">
					<th ng-click="show(r)">{{r.name}}</th>
					<th><a class="btn btn-sm fa-remove" ng-click="ArrayTool.del($index,reminders.reminderList)"></a></th>
				</tr>
		</table>
	</div>
</body>
</html>
						