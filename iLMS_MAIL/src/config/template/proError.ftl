<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GBK">
<title>存储过程异常提醒</title>
<style type="text/css">
body td{ font-size:10pt;}
table,td{
border: 1px solid black;
border-style: solid;
border-collapse: collapse;
}
.td_ct{
text-align:center;
background-color:#C0C0C0;
border: 1px solid black;
border-style: solid;
border-collapse: collapse;
font-size:12pt;
line-height:1.2em;
}"
</style>
</head>
<body>
<p>您好，这是一封来自i-LMS系统的提醒邮件。</p>
<p>有存储过程执行异常，请注意处理。以下为错误信息：</p>

<table style="width:300mm;">
<tr>
<td class="td_ct" style="width:50mm;">错误类别</td>
<td class="td_ct" style="width:20mm;">错误代码</td>
<td class="td_ct" style="width:130mm;">错误描述</td>
<td class="td_ct" style="width:40mm;">发生时间</td>
<td class="td_ct" style="width:60mm;">KEY_NAME</td>
</tr>
  <#list reportContent as bean>
	<tr>
	<td>${bean.alertType}</td>
	<td>${bean.errorName}</td>
	<td>${bean.errorDesc}</td>
	<td>${bean.creationDateStr}</td>
	<td>${bean.keyName}</td>
	</tr>
  </#list>
</table>
</body>
</html>