<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GBK">
<title>用户密码重置通知</title>
</head>
<body>
<p>${user.userCName} 您好，这是一封来自i-LMS系统的密码重置邮件！请妥善保存好你的密码。</p>
<table>
<tr><td colspan="2"><a href="http://gps.gacmotor.com/gps/login.jsp">http://gps.gacmotor.com/gps/login.jsp</a></td></tr>
<tr><td>用户名：</td><td>${user.userName}</td></tr>
<tr><td>密码：</td><td>${user.userPwd}</td></tr>
</table>
<p>注：如有问题，请与对应的计划员联系，勿回邮件到此邮箱(gps@gacmotor.com)。</p>
</body>
</html>
