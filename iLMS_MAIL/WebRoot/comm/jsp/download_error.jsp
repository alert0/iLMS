<!-- 下载错误提示 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>错误页面</title>
</head>
<body>

</body>
<script type="text/javascript">
    var params = {};
    var url = '' + window.location; 
    if (url.indexOf('?') != -1) {
        var urlSearch = url.substr(url.indexOf('?') + 1);
        var searchParams = urlSearch.split('&');
        for(var i = 0; i < searchParams.length; i++) {
            var searchParam = searchParams[i].split('=');
            params[searchParam[0]] = unescape(searchParam[1]);
        }
    }

    if('0' == params['code']){
	    window.parent.gsl.ErrorAlert('文件下载失败！');
    }else if('1' == params['code']){
	    window.parent.gsl.ErrorAlert('导出Excel的记录总条数不能超过' + params['param'] + '条！');
    }else if('2' == params['code']){
    	window.parent.gsl.ErrorAlert('文件不存在！');
    }
  
  </script>
</html>