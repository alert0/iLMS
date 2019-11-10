/**
 * 添加参数
 */
function addRow() {
	var objContainer = $("#trContainer");
	var len = objContainer.children().length;
	var strClass = (len % 2 == 0) ? "odd" : "even";
	var strTr = '';
	strTr += '<tr class="' + strClass + '">';
	strTr += '<td style="text-align: center;">';
	strTr += '<input type="text" id="paraName" name="paraName" class="inputText"/>';
	strTr += '</td>';
	strTr += '<td style="text-align: center;">';
	strTr += '<select id="paraType" name="paraType" class="inputText input-wh-4">';
	strTr += '<option value="int">int</option>';
	strTr += '<option value="long">long</option>';
	strTr += '<option value="float">float</option>';
	strTr += '<option value="string">string</option>';
	strTr += '<option value="blooean">blooean</option>';
	strTr += '</select>';
	strTr += '</td>';
	strTr += '<td style="text-align: center;">';
	strTr += '<input type="text" id="paraValue" name="paraValue" class="inputText"/>';
	strTr += '</td>';
	strTr += '<td style="text-align: center;">';
	strTr += '<a onclick="javascript:delRow(this);" class="btn-default btn fa-delete"> </a>';
	strTr += '</td>';
	strTr += '</tr>';
	var trObj = $(strTr);
	var trContainer = $("#trContainer");
	trContainer.append(trObj);
}
function delRow(obj) {
	var obj = $(obj);
	var trObj = obj.parent().parent();
	trObj.remove();
}
function validClass() {
	var className = $("#className").val();
	if (className.length == 0) {
		$.topCall.warn('请先输入任务类名再点击验证按钮', "提示信息");
		return;
	}
	var data = "className=" + className;
	$.post("validClass.ht", data, function(msg) {
		var obj = new com.hotent.form.ResultMessage(msg);
		if (obj.isSuccess()) {
			$.topCall.alert('提醒信息', '操作成功');
		} else {
			$.topCall.error('任务类错误,请查看配置信息');
		}
	});
}
function setParameterXml() {
	var objContainer = $("#trContainer");
	var len = objContainer.children().length;
	var children = objContainer.children();
	var xml = "[";
	children.each(function(i) {
		var name = $(this).find('input[name=paraName]').val();
		var type = $(this).find('select[name=paraType]').val();
		var value = $(this).find('input[name=paraValue]').val();
		if (i < len - 1)
			xml += "{\"name\":\"" + name + "\",\"type\":\"" + type + "\",\"value\":\"" + value + "\"},";
		else
			xml += "{\"name\":\"" + name + "\",\"type\":\"" + type + "\",\"value\":\"" + value + "\"}";

	});
	xml += "]";
	return xml;
}