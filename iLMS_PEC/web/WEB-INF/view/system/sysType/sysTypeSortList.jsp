<%@page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<title>分类排序</title>
 
	<%@include file="/commons/include/list.jsp" %>
	<script type="text/javascript" src="${ctx}/js/common/util/selectOption.js"></script>
	<script type="text/javascript">
	$(function(){
		//格子
		//for(var i=1;i<=10;i++)$("#sel_cell").append("<option value='"+i+"'>"+i+"</option>");
		//顶部、向上、向下、底部
		var obj=document.getElementById('typeIds');
		$("#btn_top").click(function(){
			
			__SelectOption__.moveTop(obj);
		});
		$("#btn_up").click(function(){
			__SelectOption__.moveUp(obj, 1);
		});
		$("#btn_down").click(function(){
			__SelectOption__.moveDown(obj, 1);
		});
		$("#btn_bottom").click(function(){
			__SelectOption__.moveBottom(obj);
		});
		
		$(".fa-save").click(function() {
			var obj=document.getElementById('typeIds');
			var typeIds = "";
			for(var i=0;i<obj.length;i++){
				typeIds+=obj[i].value+",";
			}
			if(typeIds.length>1){
				typeIds=typeIds.substring(0,typeIds.length-1);
				var url="${ctx}/system/sysType/sort";
				var params={"typeIds":typeIds};
	 			$.post(url,params,function(result){
	 				var obj=new com.hotent.form.ResultMessage(result);
	 				if(obj.isSuccess()){//成功
	 					$.topCall.success("排序分类完成!",function(){
	 						window.parentWindow.loadTree();
	 						window.selfDialog.dialog('close');
	 					},'提示信息');	 						
	 				}
	 				else{
	 					$.topCall.error('出错信息',"排序分类失败",obj.getMessage());
	 				}
	 			});
			}
		});
	});
	
	
	</script>
</head>
<body>
	<div class="toolbar-panel col-md-13 ">
		<div class="buttons">
			<button href="javascript:;" role="button" class="btn btn-primary btn-sm fa-save"  plain="true"><span>保存</span></button> 
			<a href="javascript:;" class="btn btn-primary btn-sm  fa-close"  plain="true" onclick="javascript:window.selfDialog.dialog('close');"><span>关闭</span></a>
		</div>
	</div>
	<form id="dataForm" method="post" action="sort">
		<div class="form-table">
			<table class="table-detail" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td class="form_title" align="center" style="padding-left: 50px"><select class="inputText" id="typeIds"
						name="typeIds" size="12" style="width: 100%;" multiple="multiple">
							<c:forEach items="${sysTypes}" var="d">
								<option  value="${d.id }">${d.name }</option>
							</c:forEach>
					</select></td>
					<td class="form_title" style="text-align: left; width: 80px">
						<input type="button" class="btn btn-primary btn-sm" id="btn_top" value="顶部" /><br /> 
						<input type="button" class="btn btn-primary btn-sm" id="btn_up" value="向上" /><br />
						<input type="button" class="btn btn-primary btn-sm" id="btn_down" value="向下" /><br /> 
						<input type="button" class="btn btn-primary btn-sm" id="btn_bottom" value="底部" /><br />
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
