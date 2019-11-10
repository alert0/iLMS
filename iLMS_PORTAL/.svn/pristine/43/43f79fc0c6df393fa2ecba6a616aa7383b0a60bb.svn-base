<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript">
	var app = angular.module('app', ['formDirective','arrayToolService']);
	app.controller("ctrl", [ '$scope', 'baseService','ArrayToolService', function($scope, baseService,ArrayTool) {
		$scope.ArrayTool = ArrayTool;
		
		var data={
			
		}
		
		$scope.addRow=function(classVar){
			$scope.data[classVar +"List"].push(angular.copy(obj[classVar]))
		}
		
		//责任组下拉框
		$scope.$on("afterLoadEvent",function(event,data){
		HtUtil.loadComboBox({
			url:__ctx+"/dpm/department/getDepUnloadCode",
			dictArr:[{
				el : '#depCode',
				defaultVal : data.depCode,
				addBlank : true
			}]
		  });
		});
		
		//默认发现区域下拉框
		$scope.$on("afterLoadEvent",function(event,data){
 		HtUtil.loadComboBox({
 			url:__ctx+"/dpm/area/getUnloadCode",
			dictArr:[{
				el : '#defaultDiscoArea',
				defaultVal : data.defaultDiscoArea,
				addBlank : true
			}]
		  }); 
	    }); 
		
	}]);
</script>
<script type="text/javascript">
	var id = "${param.id}";

	$(function() {
		
		
		//数据字典下拉框
/* 		HtUtil.loadComboBoxByDict({
			dictArr:[{
				typeKey:'PUB_WORKCENTER',
				el : '#workcenter',
				addBlank : true
			}]
		}); */
		
	
	});
	
	/**
	* 禁止空格输入
	* @param e
	* @returns {Boolean}
	*/
	function banInputSapce(e)
	{
		var keynum;
		if(window.event) // IE
		{
			keynum = e.keyCode
		}else if(e.which) // Netscape/Firefox/Opera
		{
			keynum = e.which
		}if(keynum == 32){
			return false;
		}
		return true;
	} 
	
	
</script>
</head>
<body ng-controller="ctrl">
	<!-- 顶部按钮 -->
	<div class="toolbar-panel">
		<div class="buttons">
			<a class="btn btn-sm btn-primary fa-save" ng-model="data" ng-disabled="btnDisable.save" ht-save="${ctx}/dpm/depPerson/save"><span>保存</span></a> 
			<a class="btn btn-sm btn-primary fa-back" onClick="HT.window.closeEdit(true);"><span>返回</span></a>
		</div>
	</div>
	<div id="tabUser" class="easyui-tabs" style="width: 100%; height: 100%;">
		<div title="基础信息" style="padding: 10px;">
			<form name="form" method="post" ht-load="${ctx}/dpm/depPerson/curdgetJson?id=${param.id}" ng-model="data">
				<table class="table-form" cellspacing="0">
				    <tr>
						<th><span>人员姓名:</span><span class="required">*</span></th>
						<td><input id="fullname" class="inputText" type="text"  ng-model="data.fullname" ht-validate="{required:true,maxlength:10}" />
						<button>获取人员姓名</button>
						</td>
					</tr>
					<tr>
						<th><span>责任组:</span><span class="required">*</span></th>
						<td><select id="depCode" class="inputText" type="text" ng-model="data.depCode" ht-validate="{required:true,maxlength:30}">
						    </select>
						</td>
					</tr>
					<tr>
						<th><span>默认区域:</span><span class="required">*</span></th>
						<td><select id="defaultDiscoArea" class="inputText" type="text" ng-model="data.defaultDiscoArea" ht-validate="{required:true,maxlength:33}" >
						    </select>
						</td>
					</tr>
				</table>
			</form>

		</div>
	</div>
</body>
</html>

<script>


</script>