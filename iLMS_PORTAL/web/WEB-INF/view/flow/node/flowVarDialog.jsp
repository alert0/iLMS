<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/edit.jsp" %>
		<script type="text/javascript">
		var varTree;
		var defId ;
		var nodeId ; 
		$(function() {
			var defId = "${param.defId}";
			var nodeId = "${param.nodeId}";
			var type="";
			loadGroupsTree();
			if(type !='group'){
				$("#executorType").append("<option value='user'>用户变量</option>");
			}
			if(type !='user'){
				$("#executorType").append("<option value='group'>组变量</option>");  
			}
			changeType();
		});
		function loadGroupsTree(){
			var setting = {
				data: {
					key : {
						name: "name",
					},
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "parentId"
					}
				},
				async: {enable: false},
				view: {
					selectedMulti: true,
					showIconFont:true,
				},
				onRightClick: false,
				onClick:false,
				callback:{
					beforeClick: beforeClick,
					onClick: onClickTree
				}
			};	
			var params={'defId':defId,'nodeId':nodeId};
			$.post(__ctx+"/flow/node/flowVarJson",params,function(result){
				varTree=$.fn.zTree.init($("#varTree"), setting,result); 
				varTree.expandAll(true);
			});
		}
		function beforeClick(treeId,node){
			if(node.type =='bo' || node.type =='root') return false;
		}
		
		function onClickTree(e,treeId,node){
			if(node.fromType == 'boAttr'){
				$("#description").text(node.description);
				$("#name").val(node.code+"."+node.name);
				$("#source").val('BO');
			}else if (node.fromType == 'var'){
				$("#description").text(node.description);
				$("#name").val(node.varKey);
				$("#source").val('flowVar');
			}
		}
		function changeType(){
			var selectType = $("#executorType").val();
			var toHide = selectType == 'user';
			$("#userValTr").attr('hidden',!toHide);
			$("#groupValTr").attr('hidden',toHide);  
		}
		function getVarJson(){
			var frm = $("#formaaa").form();
			if(!frm.valid()) return false;
			var varJson = { source:$("#source").val(),
							name:$("#name").val(),
							executorType:$("#executorType").val(),
							};
			if(varJson.executorType == 'group'){
				varJson.groupValType=$("#groupValType").val(); 
				varJson.dimension=$("#dimension").val();
			}else{
				varJson.userValType=$("#userValType").val();
			}
			return varJson;
			
		}
		</script>
		
	</head>
	<body class="easyui-layout">
		<div  data-options="region:'west',border:true,headerCls:'background-color:gray'"  style=" text-align:center;width:200px;" >
			<div id="varTree" class="ztree" ></div>
		</div>
    	
		<div data-options="region:'center',border:true"  style="text-align:center;width:400px"  >
		<form id="formaaa"> 
			<table class="table" style="width:99.8% !important;">	
				<tr>
					 <th> 变量类型： </th>
			         <td style="text-align:left;">
			       		 <select name="executorType" id="executorType" class="inputText" onchange="changeType()">
			       		 </select>
			         </td>
				</tr>
				<tr id="userValTr" hidden=true;>
					 <th> 用户值类型：  </th>
			         <td style="text-align:left;">
			       		<select name="userValType" id="userValType" class="inputText">
			       		 	<option value="userId">userId</option>
			       		  	<option value="account">account</option>
			       		 </select>
			         </td>
				</tr>
				<tr id="groupValTr"  hidden=true;>
					 <th> 组织值类型：  </th>
			         <td style="text-align:left;">
			       		<select name="groupValType" id="groupValType" class="inputText" style="width: 130px">
			       		 	<option value="groupId">groupId</option>
			       		  	<option value="groupKey">groupKey</option>
			       		 </select>
			       		 <select name="dimension" id="dimension" class="inputText" style="width: 85px"> 
			       			 <option value="">请选择</option> 
			       			 <c:forEach var="dim" items="${dimensionList}">
			       		 		<option value="${dim.alias}">${dim.name}</option>
			       		 	</c:forEach>
			       		 </select>
			         </td>
				</tr>
				<tr>
					 <th> 标识：  </th>
			         <td style="text-align:left;">
			       		<input name="name" id="name" readonly="readonly" class="inputText" style="width: 90%" validate="{required:true}">
			        	<input type="hidden" id="source" name="source">
			         </td>
				</tr>
				
				 <tr>
			         <th> 描述：  </th>
			         <td style="text-align:left;">
			            	<span id="description"></span>
			         </td>
				</tr>	 
			</table>
			</form>
		</div>
	</body>
</html>