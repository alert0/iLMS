<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>CSS Tables</title>
<link href="styles.css" rel="stylesheet" type="text/css" />
</head>
<style type="text/css">
/* CSS Document */

body {
font: normal 11px auto "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
color: #4f6b72;
background: #E6EAE9;
}

a {
color: #c75f3e;
}

#mytable {
width: 1100px;
padding: 0;
margin: 0;
}

caption {
padding: 0 0 5px 0;
width: 700px; 
font: italic 11px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
text-align: right;
}

th {
font: bold 11px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
color: #4f6b72;
border-right: 1px solid #C1DAD7;
border-bottom: 1px solid #C1DAD7;
border-top: 1px solid #C1DAD7;
letter-spacing: 2px;
text-transform: uppercase;
text-align: left;
padding: 6px 6px 6px 12px;
background: #CAE8EA no-repeat;
}

th.nobg {
border-top: 0;
border-left: 0;
border-right: 1px solid #C1DAD7;
background: none;
}

td {
border-right: 1px solid #C1DAD7;
border-bottom: 1px solid #C1DAD7;
background: #fff;
font-size:11px;
padding: 6px 6px 6px 12px;
color: #4f6b72;
}


td.alt {
background: #F5FAFA;
color: #797268;
}

th.spec {
border-left: 1px solid #C1DAD7;
border-top: 0;
background: #CAE8EA no-repeat;
font: bold 10px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
}

th.specalt {
border-left: 1px solid #C1DAD7;
border-top: 0;
background: #f5fafa url(images/bullet2.gif) no-repeat;
font: bold 10px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
color: #797268;
}
/*---------for IE 5.x bug*/
html>body td{ font-size:12px;}
</style>
<body>
<p>${userInfo.userCName} 您好，这是一封来自i-LMS系统的定时简报邮件，请登录系统处理订单，GPS地址：<a href="http://gps.gacmotor.com/gps/login.jsp">http://gps.gacmotor.com/gps/login.jsp</a></p>
<table id="mytable" cellspacing="0">
<caption> </caption>
<tr>
<th scope="col" abbr="Dual 1">订单分类</th>
<th scope="col" abbr="Dual 1.8">订单数量</th>
<th scope="col" abbr="Dual 2">未下载订单</th>
<th scope="col" abbr="Dual 2.5">未打印订单</th>
<th scope="col" abbr="Dual 3">已打印订单</th>
<th scope="col" abbr="Dual 3.5">未发货订单</th>
<th scope="col" abbr="Dual 4">部分发货订单</th>
<th scope="col" abbr="Dual 4.5">已发货订单</th>
<th scope="col" abbr="Dual 5">未收货订单</th>
<th scope="col" abbr="Dual 5.5">部分收货订单</th>
<th scope="col" abbr="Dual 6">已收货订单</th>
</tr>
  <#list reportContent as bean>
	<tr>
	<th scope="row" abbr="Model" class="spec">${bean.orderTypeName}</th>
	<td>${bean.totalNum}</td>
	<td>${bean.unDownLoadNum}</td>
	<td>${bean.unPrintNum}</td>
	<td>${bean.printNum}</td>
	<td>${bean.unsendNum}</td>
	<td>${bean.sendPartNum}</td>
	<td>${bean.sendNum}</td>
	<td>${bean.unreceiveNum}</td>
	<td>${bean.partreceiveNum}</td>
	<td>${bean.receiveNum}</td>
	</tr>
  </#list>
</table>
<p>注：如有问题，请与对应的计划员联系，勿回邮件到此邮箱(gps@gacmotor.com)。</p>
</body>
</html>