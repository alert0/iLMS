<%@page import="com.hotent.bpmx.api.constant.NodeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/list.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/js/lib/codemirror/lib/codemirror.css">
<script type="text/javascript" src="${ctx}/js/lib/codemirror/lib/codemirror.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/codemirror/mode/groovy/groovy.js"></script>

<script type="text/javascript">
	window.UEDITOR_HOME_URL = "${ctx }/js/lib/ueditor/";
</script>

<script type="text/javascript" charset="utf-8"	src="${ctx }/js/hotent/platform/bpm/bpmdef.udeitor.config.js"></script>
<script type="text/javascript" charset="utf-8"	src="${ctx }/js/lib/ueditor/ueditor.all.min.js"></script>

<script type="text/javascript">
var ue = null;
$().ready(function() {
	ue = UE.getEditor('html');// 格式化富文本框
	bindNeedSendMsg(); // 绑定是否发生催办事件
	bindEvent();// 绑定列表点击事件，查明催办设置信息
});

// 是否发送催办信息的checkbox
function bindNeedSendMsg(){
	$("#needSendMsg").change(function() {
		var sendMsg = $("#needSendMsg").is(':checked');
		if (sendMsg) {
			$(".send-msg-tr").show();
		} else {
			$("#sendTimes").val(0);
			$(".send-msg-tr").hide();
		}
	});
}

/**
 * 绑定列表点击事件，查明催办设置信息
 */
function bindEvent() {
	$('#nodeReminderList').datagrid({
		onClickRow : function(rowIndex, rowData) {
			// 设置数据
			setReminder(rowData);
		}
	});
}

/**
 * 添加数据
 */
function addReminder() {
	resetReminder();
}

/**
 * 将年月日转换为分钟
 * 
 * @param day
 * @param hour
 * @param minute
 * @returns {Number}
 */
function getTotalMinute(day, hour, minute) {
	var t = 0;
	t += parseInt(60 * 24 * day);
	t += parseInt(60 * hour);
	t += parseInt(minute);
	return t;
}

/**
 * 解析时间，将分钟总数转换为天时分
 * 
 * @param time
 * @returns {___anonymous6827_6880}
 */
function parseTime(time) {
	var day = Math.floor(time / (60 * 24));
	var hour =  Math.floor((time -day * (60 * 24)) / 60) ;
	var minute = time - day * (60 * 24) - hour * 60;
	var rtTime = {
		day : day,
		hour : hour,
		minute : minute
	}
	return rtTime;
}

/**
 * 获取发送目标，中间用','隔开
 * 
 * @param name
 * @returns {String}
 */
function getChecked(name) { // jquery获取复选框值
	var s = '';
	$('input[name="' + name + '"]:checked').each(function() {
		s += $(this).val() + ',';
	});
	return s;
}

/**
 * 解析富文本的发送目标
 * 
 * @param name
 * @param value
 */
function parseChecked(name, value) {
	if(!value) return ;
	var str = value.split(",");  
	$('input[name="' + name + '"]').removeAttr("checked"); 
	$('input[name="' + name + '"]').each(function() {
	    for (i=0;i<str.length ;i++ ){  
	    	var val = str[i];
	    	if($(this).val() == val){
	    		this.checked='true';
	    		continue;
	    	}
	    } 
	});
}

/**
 * 保存
 */
function saveReminders() {
	var condValidate = $('#conditionFrom').form().valid(); // 验证过滤
	var messValidate = $('#messageFrom').form().valid(); // 验证过滤
	if(!condValidate || !messValidate) return; // 验证过滤
	var reminderJson = getReminder();
	var reminderStr =  JSON2.stringify(reminderJson);
	var data = {
		reminderJson : reminderStr
	}
	$.post(__ctx + "/flow/taskReminder/save", data, function(
			responseText) {
		var resultMessage = new com.hotent.form.ResultMessage(responseText);
		if (resultMessage.isSuccess()) {
			$.topCall.toast("提示信息",resultMessage.getMessage());
			$('#nodeReminderList').datagrid('reload');
			resetReminder();
		} else {
			$.topCall.error(resultMessage.getMessage(), resultMessage.getCause());
		}
	});
}

/**
 * 删除
 */
function deleteSelect(id){
	var url = '${ctx}/bpmx/core/bpmTaskReminder/remove.ht?id='+id;
	$.topCall.confirm('提示信息','确认删除吗？',function(r){
		if (r){
			$.post(url,function(responseText){
				var resultMessage=new com.hotent.form.ResultMessage(responseText);
				if(resultMessage.isSuccess()){
			   		$.msgShow('提示信息','删除成功！');
			   		$("#nodeReminderList").datagrid('reload');
			   		//deleteSelectGrid(id);
			   		resetReminder();
				}else{
					$.topCall.message({base:{type:'alert',title:'错误提示',msg:resultMessage.getMessage(),icon:'error'}});
				}
		   });
		}
	});
}

/**
 * 获取
 * 
 * @returns {___anonymous5842_6330}
 */
function getReminder() {
	var id = $("#id").val(); // id 
	var name = $("#name").val(); // 名称
	var procDefId = $("#procDefId").val(); // 流程定义节点
	var nodeId = $("#nodeId").val(); // 当前节点
	var relNodeId = $("#relNodeId").val(); // 相对节点
	var relNodeEvent = $("#relNodeEvent").val(); // 相对处理事件
	var relTimeType = $("#relTimeType").val(); // 相对时间类型
	
	var conExpression = $editor_conExpression.getValue(); // 条件表达式
	
	var callScript = $editor_callScript.getValue(); // 调用指定方法
	
	var dueAction = $("#dueAction").val(); // 到期执行动作
	
	var dueTimeDay = $("#dueTimeDay").val(); // 到期时间
	var dueTimeHour = $("#dueTimeHour").val(); // 到期时间
	var dueTimeMinute = $("#dueTimeMinute").val(); // 到期时间
	var dueTime = getTotalMinute(dueTimeDay, dueTimeHour, dueTimeMinute);// 将年月日转换为分钟
	
	var reminderStartDay = $("#reminderStartDay").val(); // 开始发送时间
	var reminderStartHour = $("#reminderStartHour").val(); // 开始发送时间
	var reminderStartMinute = $("#reminderStartMinute").val(); // 开始发送时间
	var startTime = getTotalMinute(reminderStartDay, reminderStartHour,
			reminderStartMinute);// 将年月日转换为分钟
			
	var reminderIntervalDay = $("#reminderIntervalDay").val(); // 发送时间间隔
	var reminderIntervalHour = $("#reminderIntervalHour").val(); // 发送时间间隔
	var reminderIntervalMinute = $("#reminderIntervalMinute").val(); // 发送时间间隔
	var interval = getTotalMinute(reminderIntervalDay, reminderIntervalHour,
			reminderIntervalMinute);// 将年月日转换为分钟

	var sendTimes = $("#sendTimes").val(); // 发送次数

	var html = ue.getContent(); // 富文本内容
	var masTypeHtml = getChecked('masType_html'); // 获取富文本的发送目标
 	
	var plainText = $("#plainText").val(); // 普通文本内容
	var masTypePt = getChecked('masType_pt'); // 获取普通文本的发送目标
	
	var item = {
			id:id,
			name : name,
			procDefId : procDefId,
			nodeId : nodeId,
			relNodeId : relNodeId,
			relNodeEvent : relNodeEvent,
			relTimeType : relTimeType,
			conExpression : conExpression,
			callScript: callScript,
			dueAction : dueAction,
			dueTime : dueTime,
			startTime : startTime,
			interval : interval,
			sendTimes : sendTimes,
			html : html,
			masTypeHtml : masTypeHtml,
			plainText : plainText,
			masTypePt : masTypePt
	}
	return item;
}

/**
 * 重置
 */
function resetReminder() {
	$("#id").val('');
	$("#name").val(''); // 名称
	$("#relNodeId option:first").prop("selected", 'selected');// 相对节点
	$("#relNodeEvent option:first").prop("selected", 'selected');// 相对处理事件
	$("#relTimeType option:first").prop("selected", 'selected');// 相对时间类型

	$editor_conExpression.setValue(''); // 条件表达式
	$editor_callScript.setValue(''); // 调用指定方法
	
	$("#dueAction option:first").prop("selected", 'selected');// 到期执行动作

	$("#dueTimeDay").val(''); // 到期时间
	$("#dueTimeHour option:first").prop("selected", 'selected');// 到期执行动作
	$("#dueTimeMinute option:first").prop("selected", 'selected');// 到期执行动作

	$("#reminderStartDay").val(''); // 开始发送时间
	$("#reminderStartHour option:first").prop("selected", 'selected');// 开始发送时间
	$("#reminderStartMinute option:first").prop("selected", 'selected');// 开始发送时间

	$("#reminderIntervalDay").val(''); // 发送时间间隔
	$("#reminderIntervalHour option:first").prop("selected", 'selected');// 发送时间间隔
	$("#reminderIntervalMinute option:first").prop("selected", 'selected');// 发送时间间隔

	$("#sendTimes option:first").prop("selected", 'selected');// 发送次数

	ue.setContent(''); // 富文本内容
	$("input[name='masType_html']").attr("checked", false);// 获取富文本的发送目标

	$("#plainText").val(''); // 普通文本内容
	$("input[name='masType_pt']").attr("checked", false);// 获取普通文本框的发送目标

}

/**
 * 设置数据
 */
function setReminder(rowData) {
	$("#id").val(rowData['id']);
	$("#name").val(rowData['name']); // 名称
	//$("#nodeId").val(rowData['nodeId']); // 当前节点
	$("#relNodeId").val(rowData['relNodeId']); // 相对节点
	$("#relNodeEvent").val(rowData['relNodeEvent']); // 相对处理事件
	$("#relTimeType").val(rowData['relTimeType']); // 相对时间类型
	
	$editor_conExpression.setValue(rowData['conExpression']); // 条件表达式
	$editor_callScript.setValue(rowData['callScript']); // 调用指定方法
	
	$("#dueAction").val(rowData['dueAction']); // 到期执行动作
	
	callMethod(rowData['dueAction']);

	var dueTime = parseTime(rowData['dueTime']);
	$("#dueTimeDay").val(dueTime.day); // 到期时间
	$("#dueTimeHour").val(dueTime.hour); // 到期时间
	$("#dueTimeMinute").val(dueTime.minute); // 到期时间

	var startTime = parseTime(rowData['startTime']);
	$("#reminderStartDay").val(startTime.day); // 开始发送时间
	$("#reminderStartHour").val(startTime.hour); // 开始发送时间
	$("#reminderStartMinute").val(startTime.minute); // 开始发送时间

	var interval = parseTime(rowData['interval']);
	$("#reminderIntervalDay").val(interval.day); // 发送时间间隔
	$("#reminderIntervalHour").val(interval.hour); // 发送时间间隔
	$("#reminderIntervalMinute").val(interval.minute); // 发送时间间隔
	
	var sendTimes = rowData['sendTimes'];
	$("#sendTimes").val(sendTimes); // 发送次数
	if(sendTimes != null && sendTimes > 0){
		$("#needSendMsg").attr("checked", true);
		$(".send-msg-tr").show();
	}

	//$("#html").val(rowData['remind']['html']['content']); // 富文本内容
	ue.setContent(rowData['html']); // 富文本内容
	parseChecked('masType_html', rowData['masTypeHtml']);// 解析富文本的发送目标

	$("#plainText").val(rowData['plainText']); // 普通文本内容
	parseChecked('masType_pt', rowData['masTypePt']);// 解析富文本的发送目标
}

/**
 * 设置调用方法
 */
function callMethod(obj){
	var value;
	if(typeof obj == 'object'){
		value = $(obj).val();
	}else{
		value = obj;
	}
	if (value == 'call-method') {
		$(".callScript-tr").show();
		$editor_callScript.refresh(); // 重新架构下
	} else {
		$("#callScript").val('');
		$(".callScript-tr").hide();
	}
}
	
	
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<div class="datagrid-toolbar">
			<a href="javascript:;" class="btn btn-primary fa-save" onclick="saveReminders()">保存</a>
			<a onclick="addReminder()" class="btn btn-primary fa-arrow-right">新增</a>
			<a href="javascript:;" class="btn btn-primary fa-close"
				onclick="window.selfDialog.dialog('close')">取消</a>
		</div>
	</div>

	<div data-options="region:'center',border:false">
		<div id="userTab" class="easyui-tabs">
			<div tabid="reminder-tab-base" title="催办基本信息"
				style="overflow: hidden;">
				<div class="foldBox">
					<div class="title">到期条件设置</div>
					<div class="content">
						<form id="conditionFrom">
							<input type="hidden" id="id"/>
							<input type="hidden" id="procDefId" value="${procDefId }"/>
							<table class="table-form" cellspacing="0">
								<tr>
									<th><span>名称:</span><span class="required">*</span></th>
									<td><input type="text" id="name" class="inputText" validate="{required:true,maxlength:128}"/></td>
									<th><span>当前节点:</span></th>
									<td><input type="text" id="nodeId" class="inputText" value="${nodeId}" readonly="readonly"  />
									</td>
								</tr>
								<tr>
									<th><span>相对节点:</span></th>
									<td><select id="relNodeId" class="inputText">
											<c:forEach items="${bpmNodeDefs}" var="bpmNodeDef">
												<option value="${bpmNodeDef.nodeId}"
													<c:if test="${bpmNodeDef.nodeId==reminderType.relNodeId}">selected="selected"</c:if>>${bpmNodeDef.name}(${bpmNodeDef.nodeId})</option>
											</c:forEach>
									</select>
									</td>
									<th><span>相对处理事件:</span></th>
									<td><select id="relNodeEvent" class="inputText">
											<option value="create">创建</option>
											<option value="complete">完成</option>
									</select>
									</td>
								</tr>
								<tr>
									<th><span>相对时间类型:</span></th>
									<td colspan="3">
										<select id="relTimeType" class="inputText" class="inputText" >
											<option value="caltime">日历日</option>
											<option value="worktime">工作日</option>
										</select>
									</td>
								</tr>
								<tr>
									<th><span>相对到期时间:</span></th>
									<td id="completeTr" colspan="3">
									<input type="text" id="dueTimeDay" class="inputText input-wh-2" validate="{digits:true }" /><span>天</span>
										<select id="dueTimeHour" class="inputText input-wh-2" >
											<c:forEach var="i" begin="0" end="23" step="1">
												<option value="${i}"
													<c:if test="${completeTimeHour==i}">selected="selected"</c:if>>${i}小时</option>
											</c:forEach>
										</select> 
										<select id="dueTimeMinute" class="inputText input-wh-2">
											<c:forEach var="i" begin="0" end="4" step="1">
												<option value="${i}"
													<c:if test="${completeTimeMinute==i}">selected="selected"</c:if>>${i}分钟</option>
											</c:forEach>
											<c:forEach var="i" begin="5" end="59" step="5">
												<option value="${i}"
													<c:if test="${completeTimeMinute==i}">selected="selected"</c:if>>${i}分钟</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th><span> <a href="javascript:;"
											style="text-decoration: none;"
											class="fa fa-exclamation-circle"  ht-tip
											title="条件表达要求是返回Boolean值的脚本。返回true,表示满足条件；返回talse,表示条件不满足。如果表达式为空，将视为返回true。"></a>
											条件表达式:</span></th>
									<td colspan="3"><a href="javascript:;" class="link var" title="常用脚本">常用脚本</a>
										<span class="green">表单变量</span> 
										<select name="flowVar" class="inputText input-wh-3">
											<option value="">请选择...</option>
											<optgroup label="表单变量"></optgroup>
											<option value="test">test</option>
											<%-- <c:forEach items="${flowVars}" var="flowVar">
												<option class="flowvar-item" value="${flowVar.fieldName}" fname="${flowVar.fieldName}" fdesc="${flowVar.fieldDesc}"
													ftype="${flowVar.fieldType}">${flowVar.fieldDesc}</option>
											</c:forEach> 
											<c:if test="${not empty defVars}">
												<optgroup label="自定义变量"></optgroup>
												<c:forEach items="${defVars}" var="defVars">
													<option class="flowvar-item" value="${defVars.varKey}"
														fname="${defVars.varKey}" fdesc="${defVars.varName}"
														ftype="${defVars.varDataType}">${defVars.varName}</option>
												</c:forEach>
											</c:if>--%>
										</select> <!-- <span class="green">比较</span>
										<select name="flowOperate"  class="inputText input-wh-2"></select>
										<span class="green">值</span> 
										<input name="flowValue" class="inputText"/> 
										<a onclick="generateExpress(this)" href="javascript:;" class="button" ><span>生成</span></a> -->
										<!-- <textarea id="conExpression" name="conExpression"
											mirrorheight="200px" rows="10" cols="80"></textarea> -->
											
										<bpm:codemirror mode="Groovy" id="conExpression" name="conExpression" lineWrapping="true" 
											value="" lineNumbers="true"></bpm:codemirror>	
									</td>
								</tr>
							</table>
						</form>
					</div>

				</div>

				<div class="foldBox">
					<div class="title">到期动作设置</div>
					<div class="content">
						<table class="table-form" cellspacing="0">
							<tr>
								<th><span>执行动作:</span>
								</th>
								<td><select id="dueAction" class="inputText" onchange="callMethod(this);">
										<option value="no-action">无动作</option>
										<option value="auto-next">执行同意操作</option>
										<option value="end-process">结束该流程</option>
										<option value="call-method">调用指定方法</option>
								</select>
								</td>
							</tr>
							<tr class="callScript-tr" >
								<th><span>执行脚本: </span></th>
								<td>
									<a href="javascript:;"  class="link var" title="常用脚本" onclick="scriptSelectScript(this)">常用脚本</a>
									<span class="green">表单变量</span> 
										<select name="flowVar" class="inputText input-wh-3">
											<option value="">请选择...</option>
											<optgroup label="表单变量"></optgroup>
											<option value="test">test</option>
									</select>
									<!-- <textarea id="callScript" mirrorheight="200px" rows="10" cols="80"></textarea>	 -->
									<bpm:codemirror mode="Groovy" id="callScript" name="callScript" lineWrapping="true" 
											value="" lineNumbers="true"></bpm:codemirror>		
								</td>
							</tr>	
						</table>
					</div>
				</div>

				<div class="foldBox">
					<div class="title">发送催办消息设置</div>
					<div class="content">
						<form id="messageFrom">
							<table class="table-form" cellspacing="0">
								<tr>
									<th><span>发送催办信息:</span></th>
									<td colspan="3"><label>
										<input type="checkbox" id="needSendMsg" />发送</label>
									</td>
								</tr>
								<tr class="send-msg-tr">
									<th><span>开始发送时间:</span></th>
									<td id="startTr" colspan="3">
										<input type="text" id="reminderStartDay" class="inputText input-wh-2" validate="{digits:true }" /> <span>天</span>
										<select id="reminderStartHour" class="inputText input-wh-2">
											<c:forEach var="i" begin="0" end="23" step="1">
												<option value="${i}"
													<c:if test="${reminderStartHour==i}">selected="selected"</c:if>>${i}小时</option>
											</c:forEach>
										</select> 
										<select id="reminderStartMinute" class="inputText input-wh-2">
											<c:forEach var="i" begin="0" end="4" step="1">
												<option value="${i}"
													<c:if test="${reminderStartMinute==i}">selected="selected"</c:if>>${i}分钟</option>
											</c:forEach>
											<c:forEach var="i" begin="5" end="59" step="5">
												<option value="${i}"
													<c:if test="${reminderStartMinute==i}">selected="selected"</c:if>>${i}分钟</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr class="send-msg-tr">
									<th><span> <a href="javascript:;"
											style="text-decoration: none;"
											class="fa fa-exclamation-circle" ht-tip
											title="每过多长的时间发送催办信息。"></a> 发送的间隔: </span>
									</th>
									<td id="endTr">
										<input type="text" id="reminderIntervalDay"	class="inputText input-wh-2" validate="{digits:true }" /> <span>天</span> 
										<select id="reminderIntervalHour" class="inputText input-wh-2">
											<c:forEach var="i" begin="0" end="23" step="1">
												<option value="${i}"
													<c:if test="${reminderEndHour==i}">selected="selected"</c:if>>${i}小时</option>
											</c:forEach>
										</select> 
										<select id="reminderIntervalMinute"	class="inputText input-wh-2">
											<c:forEach var="i" begin="1" end="4" step="1">
												<option value="${i}"
													<c:if test="${reminderEndMinute==i}">selected="selected"</c:if>>${i}分钟</option>
											</c:forEach>
											<c:forEach var="i" begin="5" end="59" step="5">
												<option value="${i}"
													<c:if test="${reminderEndMinute==i}">selected="selected"</c:if>>${i}分钟</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr class="send-msg-tr">
									<th><span>发送信息次数: </span></th>
									<td><select id="sendTimes" class="inputText input-wh-2">
											<c:forEach var="i" begin="0" end="10" step="1">
												<option value="${i}"
													<c:if test="${taskReminder.times==i}">selected="selected"</c:if>>${i}</option>
											</c:forEach>
									</select>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>

			<div tabid="reminder-tab-mail" title="富文本内容">
				<table class="table-form" cellspacing="0">
					<tr>
						<th class="input-wh-2"><span>富文本内容:</span>
						</th>
						<td><script id="html" type="text/plain" class="input-wh-10"	style="height:200px;"></script>
						</td>
					</tr>
					<tr>
						<th><span>发送目标:</span>
						</th>
						<td>
							<c:forEach items="${handlerList}"  var="model">
								<c:if test="${model.type!='sms'}">
									<label><input type="checkbox" name="masType_html" value="${model.type}"/>${model.title}</label>
								</c:if>
							</c:forEach>
						<!-- 	<input type="checkbox" name="masType_html" value="mail" />邮件内容
							<input type="checkbox" name="masType_html" value="inner" />站内消息内容 -->
						</td>
					</tr>
					<tr>
						<td colspan="2">
							提示：可以用{title}来代表任务名称、用{time}来代表收到任务的时间。
						</td>
					</tr>
				</table>
			</div>
			<div tabid="reminder-tab-sms" title="普通文本内容">
				<table class="table-form" cellspacing="0">
					<tr>
						<th class="input-wh-2"><span>普通文本内容:</span></th>
						<td>
							<!-- <div>
								<a href="javascript:;" class="link var" title="选择模板内容" onclick="slectTemplate('mailContent',false)">选择模板内容</a>
							</div> --> <textarea id="plainText" class="inputText input-wh-12"
								style="height: 200px;"></textarea>
						</td>
					</tr>
					<tr>
						<th><span>发送目标:</span>
						</th>
						<td>
							<!-- <input type="checkbox" id="msgType"
								name="masType_pt" value="sms" />手机短信 -->
							<c:forEach items="${handlerList}"  var="model">
								<c:if test="${model.type=='sms'}">
									<label><input type="checkbox" name="masType_html" value="${model.type}"/>${model.title}</label>
								</c:if>
							</c:forEach>	
						</td>
					</tr>
					<tr>
						<td colspan="2">
							提示：可以用{title}来代表任务名称、用{time}来代表收到任务的时间。
						</td>
					</tr>
				</table>
			</div>

		</div>
	</div>

	<div data-options="region:'east',border:false"
		style="text-align: center; width: 280px;">
		<table id="nodeReminderList" class="easyui-datagrid" idField="id"
			data-options="fitColumns:false" fit=true
			url="${ctx}/bpmx/core/bpmTaskReminder/listJson.ht?procDefId=${procDefId}&nodeId=${nodeId}"> 
			<thead>
				<tr>
					<th field="name" >名称</th>
					<th manager="true" >
						<div>
							<a onclick="javascript:deleteSelect('{id}')"
								class="btn-default btn fa-delete"> </a>
						</div>
					</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<style>
.foldBox {
	border-top: 2px solid #488DF5;
	clear: both;
	display: inline-block;
	margin: 10px 5px 5px;
	position: relative;
	width: 99%;
}

.foldBox a.drop {
	cursor: pointer;
	text-decoration: none;
}

.foldBox .title {
	background: none repeat scroll 0 0 #FFFFFF;
	color: #1673FF;
	font-size: 14px;
	font-weight: bold;
	left: 12px;
	padding-left: 10px;
	padding-right: 15px;
	position: absolute;
	top: -15px;
}

.foldBox .drop {
	background: none repeat scroll 0 0 #FFFFFF;
	color: #488DF5;
	font-weight: bold;
	padding: 0 10px;
	position: absolute;
	right: 12px;
	top: -12px;
}

.foldBox .drop span {
	background: url("../images/other/1.png") no-repeat scroll 0 -35px
		transparent;
	display: inline-block;
	height: 6px;
	width: 12px;
}

.foldBox .content {
	margin: 12px 8px;
	padding: 10px;
}

.send-msg-tr {
	display: none;
}

.callScript-tr {
	display: none;
}
</style>

</html>