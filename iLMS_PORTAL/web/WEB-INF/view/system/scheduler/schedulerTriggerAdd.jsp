<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/edit.jsp"%>
<script type="text/javascript" src="${ctx }/js/platform/system/scheduler/JobDialog.js"></script>
<script type="text/javascript" src="${ctx }/js/platform/system/scheduler/Trigger.js"></script>
<script type="text/javascript">
	$(function() {
		var frm = $('#frmSubmit').form();
		$("a.btn.fa-save").click(function() {
			frm.ajaxForm({
				success : showResponse
			});
			if (frm.valid()) {
				var str = getPlan();
				$("#planJson").val(str);
				$('#frmSubmit').submit();
			}
		});
	});

	function showResponse(responseText) {
		var resultMessage = new com.hotent.form.ResultMessage(responseText);
		if (resultMessage.isSuccess()) {
			$.topCall.message({
				base : {
					type : 'confirm',
					title : '温馨提示',
					msg : resultMessage.getMessage() + ',是否继续操作',
					fn : function(r) {
						if (r) {
							window.location.reload(true);
						} else {
							var url = '${ctx}/system/scheduler/getTriggersByJob';
							var param = {
								jobName : "${jobName}"
							}
							skipJsp(url,param);
						}
					}
				}
			});
		} else {
			$.topCall.error(resultMessage.getMessage(), resultMessage.getCause());
		}
	}
	function back(){
		var url = '${ctx}/system/scheduler/getTriggersByJob';
		var param = {
			jobName : "${jobName}"
		}
		skipJsp(url,param);
	}
</script>
<style type="text/css">
	.table-form  td {text-align: left;}
</style>
</head>
<body>
	<div class="toolbar-panel col-md-13 ">
		<div class="buttons">
			<a href="javaScript:void(0)" class="btn btn-primary btn-sm fa-save">
				<span>保存</span>
			</a>
			<a href="javaScript:void(0)" onclick="back()" class="btn btn-primary btn-sm fa-back">
				<span>返回</span>
			</a>
			<span>
				添加定时计划:
				<span class="green">${jobName}</span>
			</span>
		</div>
	</div>
	<form id="frmSubmit" method="post" action="addTrigger.ht">
		<table class="table-form"  cellspacing="0" >
			<tr>
				<th>计划名称:</th>
				<td >
					<input type="text" id="name" name="name" class="inputText" validate="{required:true,maxlength:120}" />
					<input id="planJson" name="planJson" type="hidden" />
					<input id="jobName" name="jobName" type="hidden" value="${param.jobName}" />
				</td>
			</tr>
			<tr>
				<th colspan="2" style="text-align: left; padding-left: 5px;">执行计划的方式</th>
			</tr>
			<tr>
				<th>
					<label class="radio-inline"><input type="radio" value="1" name="rdoTimeType" />
					一次:</label>
				</th>
				<td align="left">
					开始:
					<input type="text" id="txtOnceDate" class="Wdate inputText" onclick="WdatePicker({minDate:'%y-%M-{%d}'})" />
					<select id="txtOnceHour" class="inputText input-wh-1">
						<c:forEach begin="0" end="23" step="1" var="tmp">
							<option value="${tmp }">${tmp }时</option>
						</c:forEach>
					</select>
					<select id="txtOnceMinute" class="inputText input-wh-1">
						<c:forEach begin="0" end="55" step="5" var="tmp">
							<option value="${tmp }">${tmp }分</option>
						</c:forEach>
						<option value="59">59分</option>
					</select>
					<select id="txtOnceSecond" class="inputText input-wh-1">
						<c:forEach begin="0" end="55" step="5" var="tmp">
							<option value="${tmp }">${tmp }秒</option>
						</c:forEach>
						<option value="59">59秒</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<label class="radio-inline"><input type="radio" checked="checked" value="2" name="rdoTimeType" />
					每天:</label>
				</th>
				<td align="left">
					<select id="selEveryDay" class="inputText input-wh-1">
						<option value="1">1分钟</option>
						<option value="5">5分钟</option>
						<option value="10">10分钟</option>
						<option value="15">15分钟</option>
						<option value="30">30分钟</option>
						<option value="60">1小时</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<label class="radio-inline"><input type="radio" value="3" name="rdoTimeType" />
					每天:</label>
				</th>
				<td align="left">
					<select id="txtDayHour" class="inputText input-wh-1">
						<c:forEach begin="0" end="23" step="1" var="tmp">
							<option value="${tmp }">${tmp }时</option>
						</c:forEach>
					</select>
					<select id="txtDayMinute" class="inputText input-wh-1">
						<c:forEach begin="0" end="55" step="5" var="tmp">
							<option value="${tmp }">${tmp }分</option>
						</c:forEach>
						<option value="59">59分</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<label class="radio-inline"><input type="radio" value="4" name="rdoTimeType" />
					每周:</label>
				</th>
				<td align="left">
					<label class="checkbox-inline"><input type="checkbox" name="chkWeek" value="MON" />
					星期一</label>
					<label class="checkbox-inline"><input type="checkbox" name="chkWeek" value="TUE" />
					星期二</label>
					<label class="checkbox-inline"><input type="checkbox" name="chkWeek" value="WED" />
					星期三</label>
					<label class="checkbox-inline"><input type="checkbox" name="chkWeek" value="THU" />
					星期四</label>
					<label class="checkbox-inline"><input type="checkbox" name="chkWeek" value="FRI" />
					星期五</label>
					<label class="checkbox-inline"><input type="checkbox" name="chkWeek" value="SAT" />
					星期六</label>
					<label class="checkbox-inline"><input type="checkbox" name="chkWeek" value="SUN" />
					星期日</label>
					<br />
					<select id="txtWeekHour" class="inputText input-wh-1">
						<c:forEach begin="0" end="23" step="1" var="tmp">
							<option value="${tmp }">${tmp }时</option>
						</c:forEach>
					</select>
					<select id="txtWeekMinute" class="inputText input-wh-1">
						<c:forEach begin="0" end="55" step="5" var="tmp">
							<option value="${tmp }">${tmp }分</option>
						</c:forEach>
						<option value="59">59分</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<label class="radio-inline"><input type="radio" value="5" name="rdoTimeType" />
					每月:</label>
				</th>
				<td align="left">
					<c:forEach begin="1" end="31" var="mon">
						<label class="checkbox-inline"><input type="checkbox" name="chkMon" value="${mon}" />${mon}</label>
							</c:forEach>
					<label class="checkbox-inline"><input type="checkbox" name="chkMon" value="L" />
					最后一天</label>
					<br />
					<select id="txtMonHour" class="inputText input-wh-1">
						<c:forEach begin="0" end="23" step="1" var="tmp">
							<option value="${tmp }">${tmp }时</option>
						</c:forEach>
					</select>
					<select id="txtMonMinute" class="inputText input-wh-1">
						<c:forEach begin="0" end="55" step="5" var="tmp">
							<option value="${tmp }">${tmp }分</option>
						</c:forEach>
						<option value="59">59分</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<label class="radio-inline"><input type="radio" value="6" name="rdoTimeType" />
					Cron表达式:</label>
				</th>
				<td align="left">
					<input type="text" id="txtCronExpression" name="txtCronExpression" class="inputText" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>