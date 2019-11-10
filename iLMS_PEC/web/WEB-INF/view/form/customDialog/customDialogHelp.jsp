<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/edit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/system/customDialog/customDialogService.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/system/customDialog/helpController.js"></script>
		<script type="text/javascript">var id ="${param.id}";</script>
	</head>
	<body ng-app="app" ng-controller="HelpController">
		<table class="table-form">
			<tr>
				<th >步骤1：</th>
				<td>//引入js：<br/>&lt;script type="text/javascript" src="${ctx}/js/platform/util/customUtil.js"&gt;&lt;/script&gt;;</td>
			</tr>
			
			<tr>
				<th>步骤2：</th>
				<td>
				//在需要的地方加入如下代码:<br/>
				conf为可选参数，参数介绍:{<br/><br/>
					selectNum(人为修改多选单选配置):-1 多选/1 单选;<br/><br/>
					initData(初始化数据) :就是返回回调函数中的data数据;<br/><br/>
					param(参数，用于动态传入的条件初始化条件):{a:"1",b:"2",...}<br/><br/>
				}<br/><br/>
				var conf = {<br/>
							selectNum:-1,<br/>
							initData:[<br/>{{resultJson}},<br/>
									  {{resultJson}}],<br/>
						    param:{{paramValueString}}<br/>
						   };<br/><br/>
				CustomDialog.openCustomDialog('{{prop.alias}}',function(data,dialog){<br/><br/>
					&nbsp;&nbsp;&nbsp;&nbsp;alert("返回结果："+JSON.stringify(data));//处理数据，数据格式是设置的返回字段的json格式<br/><br/>
					&nbsp;&nbsp;&nbsp;&nbsp;//本对话框的返回格式:data:[{{resultJson}},{{resultJson}}];这里两个数据<br/><br/>
					&nbsp;&nbsp;&nbsp;&nbsp;//注意：当单选的也是jsonArray，可以data[0]得到结果，然后data[0].name或者data[0].id<br/><br/>
					&nbsp;&nbsp;&nbsp;&nbsp;dialog.dialog('close');//关闭弹出框<br/><br/>
				},conf);
				</td>
			</tr>
		</table>
	</body>
</html>