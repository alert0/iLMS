<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GBK">
<title>触发器异常提醒</title>
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
<p>有触发器异常，请注意处理。以下为异常信息：</p>

<table style="width:300mm;">
<tr>
<td class="td_ct" style="width:60mm;">数据库</td>
<td class="td_ct" style="width:60mm;">名称</td>
<td class="td_ct" style="width:80mm;">表名</td>
<td class="td_ct" style="width:40mm;">状态</td>
<td class="td_ct" style="width:40mm;">触发事件</td>
</tr>
  <#list reportContent as bean>
	<tr>
	<td>${bean.dbName}</td>
	<td>${bean.triggerName}</td>
	<td>${bean.tableName}</td>
	<td>${bean.status}</td>
	<td>${bean.triggeringEvent}</td>
	</tr>
  </#list>
</table>
</body>
</html>