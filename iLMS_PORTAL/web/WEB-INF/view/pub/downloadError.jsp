<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>下载错误提示</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript">
	var params = {};
	var url = '' + window.location;
	if	(url.indexOf('?') != -1){
		var urlSearch = url.substr(url.indexOf('?') + 1);
		var searchParams = urlSearch.split('&');
		for(var i = 0; i < searchParams.length; i++){
			var searchParam = searchParams[i].split('=');
			params[searchParam[0]] = searchParam[1];
		}
	}
	
	if('0' == params['code']){
		$.topCall.error("超过系统最大允许导出条数【" + params['param'] + "】");
	}else if('1' == params['code']){
		$.topCall.error("没有可导出的数据！");
	}
	else if('9' == params['code']){
		$.topCall.error("文件下载异常！");
	}
	
</script>
</head>
<body>
</body>
</html>