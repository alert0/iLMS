<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>设置图标</title>
	<%@include file="/commons/include/list.jsp" %>
	<script type="text/javascript">
		var selectedImg=null;
		$(function(){
			$("#iconList i").click(function(){
				if(selectedImg){
					$(selectedImg).removeClass('selected');
				}
				$(this).addClass('selected');
				selectedImg=this;
			});
			
			//上传图标
			$("#iconForm").ajaxForm({success:showResponse});
			$("a.upload").click(function(){
				if($("#iconFile").val()==''){
					$.ligerDialog.warn("请选择图标文件","提示信息");
				}else{
					$("#iconForm").submit();
				}
			});
			
			$("a.del").click(function(){
			   if(selectedImg){
					var iconPath=$(selectedImg).attr('path');
				   $.post('delFile.ht',{path:iconPath},function(data){
					   showResponse(data);
				   });
			   }
			});
		});
		
		function showResponse(responseText){
			var obj=new com.hotent.form.ResultMessage(responseText);
			if(obj.isSuccess()){//成功
				$.ligerDialog.success(obj.getMessage(),"提示信息",function(){
					window.location.reload();		
				});
		    }else{//失败
		    	$.ligerDialog.err('出错信息',"保存文件图标失败",obj.getMessage());
		    }
		};
		
	</script>
	<style type="text/css">
		
	
		#iconList a {
			font-size: 300%;
		}
		
		#iconList .selected {
			border-color:  #86a9d1; 
			background-color:#c3dcfc; 
		}
	</style>
</head>
<body>
<div class="panel">
	<div class="panel-body" id="iconList">
		<c:if test="${empty iconList}">
			<span >暂无资源图标</span>
		</c:if>
		<c:forEach items="${iconList}" var="d" varStatus="status">
			<a href="javascript:void(0)" ><i class="btn  ${d.alias}" alias="${d.alias}" title="${d.name }"></i></a>
		</c:forEach>
	</div>
</div>
</body>
</html>
