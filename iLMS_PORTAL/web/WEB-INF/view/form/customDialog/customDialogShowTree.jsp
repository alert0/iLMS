<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/ngEdit.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
 <script type="text/javascript" src="${ctx}/js/platform/system/customDialog/showTreeController.js"></script>
<script type="text/javascript">
	var ztreeCreator;//树对象
</script>
<script type="text/javascript">
	function getResult() {
		var scope = AngularUtil.getScope();
		var nodes;
		if(scope.customDialog.selectNum==1){//单选
			nodes=ztreeCreator.getTreeObj().getSelectedNodes();
		}else{//多选
			nodes=ztreeCreator.getTreeObj().getCheckedNodes(true);
		}
		var list = [];
		$(nodes).each(function(){
			var json={};
			var temp = this;
			
			for(var k in temp){
				temp[k.toUpperCase()]=temp[k];
			}
			
			$(scope.customDialog.resultfield).each(function(){
				json[this.comment]=temp[this.field.toUpperCase()];
			});
			list.push(json);
		});
		return list.concat(scope.initData);
	}
</script>
<style type="text/css">
	
</style>
</head>
<body ng-controller="ctrl" ht-load="getByAlias?alias=${param.dialog_alias_}" ng-model="customDialog">
	 <div class="easyui-layout"data-options="fit:true">
	 		<c:if test="${fn:contains(param.dialog_alias_,'orgSelector')}">
		 		<div data-options="region:'north'" style="height:45px">
	                	<table class="table-form" cellspacing="0">
	                		<tr>
	                			<th>维度：</th>
	                			<td>
	                				<select id="demCombobox" class="inputText input-wh-9"></select>
	                			</td>
	                		</tr>
	                	</table>
	            </div>
            </c:if>
             <div data-options="region:'center'">
                <div id="ztree" class="ztree" ></div>
            </div>
	 </div>
</body>
</html>