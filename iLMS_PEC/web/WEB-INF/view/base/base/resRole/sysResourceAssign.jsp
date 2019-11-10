<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html >
	<head>
		<%@include file="/commons/include/edit.jsp" %>
		<%@include file="/commons/include/zTree.jsp" %>
		<script type="text/javascript">
			var roleId="${roleId}";
			var systemId="${systemId}";
			
			$(function() {
				loadTree();
				
				$("#subsystemList").change(function(){
					systemId=this.value;
					loadTree();
				})
			});
			
			
			var resourcesTree;
			
			function loadTree(){
					var setting = {
						data: {
							key : {name: "name",title: "name"},
							simpleData: {enable: true,idKey: "id",pIdKey: "parentId",rootPId: -1}
						},
						view: {selectedMulti: true},
						check: {enable: true,chkboxType: { "Y": "ps", "N": "s" }}
					};
					//一次性加载
					var url=__ctx + "/base/base/resRole/getTreeData?roleId="+roleId+"&systemId="+systemId;
					
					$.post(url,function(result){
						resourcesTree=$.fn.zTree.init($("#sysResourceTree"), setting,eval(result));
						resourcesTree.expandAll(true);
					})
			}
			
			function getResult(){
				var aryRes=[];
				var nodes= resourcesTree.getCheckedNodes(true);
				for(var i=0;i<nodes.length;i++){
					var node=nodes[i];
					aryRes.push(node.id);
				}
				return "roleId="+roleId+"&systemId="+systemId+"&resIds=" +aryRes.join(",");
			}
		</script>
	</head>
	<body>
		<div >
			<c:if test="${fn:length(systemList) >1}">
				<div >
					<select id="subsystemList" class="form-control" >
						<c:forEach items="${ systemList}" var="item" >
							<option value="${item.id }">${item.name}</option>
						</c:forEach>
					</select>
				</div>
			</c:if>
	   	 	<div id="sysResourceTree" class="ztree"></div>
		
		</div>
	</body>
</html>