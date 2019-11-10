<%@page import="com.hotent.bpmx.api.constant.NodeType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/datagrid/ht.plugin.topCall.js"></script>
 <script type="text/javascript"> 
    var varTree;
    var json = window.passConf;//初始化传过来的值
    var editor;
    $(function(){
	   json = window.passConf;
 	   if(json)
 	   {
 	       $.each(json,function(i,item){
 		      $("#ownerId").val(item.ownerId);
		      $("#ownerName").val(item.ownerName);
 	       });
 	   }
 	        
	});
    //对话框点击确定时返回的值
    function getResult()
    {
	   var json=[];
	   json.push({ownerId:$("#ownerId").val(),ownerName:$("#ownerName").val()});
	   return json;
   }
    
    function openDialog()
    {
	   $.dialog( __ctx+"/form/customDialog/testDialog","测试",[{"ownerId":"222","ownerName":"测试"}],null,function(data,targetDialog){
	       alert(JSON.stringify(data));
	       targetDialog.dialog('close');
	       
	   });
    }
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<form id="ruleFrom">
			<table class="table-form" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<th width="20%">
						<span>ownerId</span>: 
					</th>
					<td>
			          <input class="inputText" style="width: 260px" id="ownerId">
					</td>
				</tr>
				<tr>
					<th width="20%">
						<span>ownerName</span>
					</th>
					<td>
						<input class="inputText" style="width: 260px" id="ownerName">
						
					</td>
				</tr>
				<tr>
					<th width="20%">
						<span>测试</span>
					</th>
					<td>
					<input type="button" onclick="openDialog()" value="打开窗口"/>
						
					</td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>