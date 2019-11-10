<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/include/edit.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/util/Dialogs.js"></script>
 
<script type="text/javascript">
 
</script>
<style>
.firstRow tr td font {
	color: #000
}
</style>
</head>
<body class="easyui-layout" fit="true">
	<div data-options="region:'center',border:false">
				<table class="table-list" id="def" cellpadding="1" cellspacing="1" style="width: 100%" type="sub">
					<thead>
						<tr>
							<th  width="150px">节点名称</th>
							<th width="250px">人员</th>
							<th width="120px">选择</th>
						</tr>
					</thead>
					<tbody id="listResult">
						<c:choose>
							<c:when test="${fn:length(listNode)>0}">
								<c:forEach items="${listNode}" var="node">
									<tr id="def_${node.nodeId}" >
										<td>
											${node.name}
										</td>
										<td>
											<input id="${node.nodeId}" type="hidden" item="${node.name}" name="${node.nodeId}" value="" />
										 <span id="${node.nodeId}_Span"></span>
										</td>
										<td>
											<a href="javaScript:void(0)" onclick="selectUser('${node.nodeId}')" class="btn btn-sm btn-success fa-remove remove-flow">选择</a>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr id="firstRow">
									<td colspan="2" align="center">
										<font color='red'>找不到后续节点</font>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
		
		</form>
</body>
</html>
<script>
function selectUser(nodeId)
{
	UserDialog({callBack:function(data){
	     var accounts=[];
	     var name=[];
	     $.each(data,function(i,item){
			accounts.push({id:item.id,type:"user", name:item.name});
			name.push(item.name);
		})
		$("#"+nodeId).val(JSON.stringify(accounts));
	    $("#"+nodeId+"_Span").html(name.toString());
		 
    }});
}
function getResult(){
	var json=[];
	$("input[item]").each(function(){
		var value=$(this).val();
		if(value!=""){
			value=eval("("+value+")")
		}
		else{
			$.topCall.error("请选择节点【"+$(this).attr("item")+"】的人员","");
			json=[];
			return false;
		}
		var nodeId=$(this).attr("id");
		json.push({nodeId:nodeId,executors:value})
	});
    return json;
}
</script>