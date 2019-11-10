<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GBK">
<title>厂外同步零件明细未维护异常提醒</title>
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
<p>以下为最新锁定计划车辆中，厂外同步基础数据中零件组未维护完整的MTOC以及未维护的零件组：</p>

<table style="width:150mm;">
<tr>
<td class="td_ct" style="width:50mm;">MTOC</td>
<td class="td_ct" style="width:100mm;">未维护零件组</td>
</tr>
  <#list reportContent as bean>
	<tr>
	<td>${bean.mtoc}</td>
	<td>${bean.groupName}</td>
	</tr>
  </#list>
</table>
</body>
</html>