<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@include file="/commons/include/list.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/system/dialog/iconDialog.js"></script>

	</head>
	<body>
		<table class="table-form" cellspacing="0">
			<tr>								
				<th><span>名称:</span></th>
				<td>
					<input type="text" id="buttonName" name="buttonName"  class="inputText"  />
				</td>	
					
			</tr>
			<tr>
				<th><span>图标:</span></th>
				<td>
					
					<input type="hidden" id="buttonIcon" name="buttonIcon"  class="inputText"/>	
					<i  id="iconImg"  class="fa "></i>

					<a class="btn btn-sm btn-info  fa-search-plus" href="javascript:selectIcon();" >选择</a>
				
				</td>	
				
			</tr>
			<tr>
				<th><span>点击事件:</span></th>
				<td>
					<textarea rows="4" cols="30" id="buttonOnclick" name="buttonOnclick" class="inputText" ></textarea>
				</td>
			</tr>
		</table>
	</body>
	
	
				<script type="text/javascript">
	
				var passConf = this.window.passConf;
			$(function() {
				if(passConf){
						$('#buttonName').val(passConf.name);
						$('#buttonIcon').val(passConf.icon);
						$('#iconImg').removeAttr('class').addClass("fa "+passConf.icon);
						$('#buttonOnclick').val(passConf.onclick);
				}
				
			});
			function selectIcon(){
				new IconDialog({callback:function(alias,win){
					$('#buttonIcon').val(alias);
					$('#iconImg').removeAttr('class').addClass("fa "+alias);
					win.dialog('close');
				}}).show();
			}

		</script>
	
</html>