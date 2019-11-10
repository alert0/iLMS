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
		
	}]);
	
</script>
<script type="text/javascript">
	var id = "${param.id}";
	$(function() {
		//箱种下拉框
	 	HtUtil.loadComboBoxJsonByDict({
			dictArr:[{
				typeKey:'INV_BOX_TYPE',
				el : '#boxType',
				addBlank : true
			}]
		});
		
	 	//箱CODE下拉框
	 	HtUtil.loadComboBox({
	 		url:__ctx+"/pkg/pkgProposal/getUnloadBoxCode",
			dictArr:[{
				el : '#boxCode',
				addBlank : true
			}]
		 }); 
	 	
	 	$('#packLength').attr('disabled',true);
	 	$('#packWidth').attr('disabled',true);
	 	$('#packHeight').attr('disabled',true);
	})
		

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
			<a class="btn btn-sm btn-primary fa-save" ng-model="data" ng-disabled="btnDisable.save" ht-save="${ctx}/pkg/pkgProposal/save?id=${param.id}"><span>保存</span></a> 
			<a class="btn btn-sm btn-primary fa-back" onClick="HT.window.closeEdit(true);"><span>返回</span></a>
		</div>
	</div>
	<div id="tabUser" class="easyui-tabs" style="width: 100%; height: 100%;">
		<div title="基础信息" style="padding: 10px;">
			<form id="form" enctype="multipart/form-data" name="form" method="post" ht-load="${ctx}/pkg/pkgProposal/curdgetJson?id=${param.id}" ng-model="data">
				<table class="table-form" cellspacing="0">
				    <tr>
						<th><span>车型:</span></th>
						<td><input id="carType" class="inputText" type="text"  ng-model="data.carType" readonly="readonly" ht-validate="{maxlength:10}" /></td>
						<th><span>零件编号:</span></th>
						<td><input id="partNo" class="inputText" type="text" ng-model="data.partNo" readonly="readonly" ht-validate="{maxlength:30}" /></td>
						<th><span>零件名称:</span></th>
						<td><input id="partNameCn" class="inputText" type="text" ng-model="data.partNameCn" readonly="readonly" ht-validate="{maxlength:30}" /></td>
					</tr>
					<tr>
					    <th><span>箱种:</span></th>
						<td><select id="boxType" class="inputText" type="text" ng-model="data.boxType"  ></select></td>
						<th><span>箱CODE:</span></th>
						<td><select id="boxCode" class="inputText" type="text" ng-model="data.boxCode" onchange="changeReadonyFun()"></select></td>
						<th><span>工程:</span><span class="required">*</span></th>
						<td><input id="project" class="inputText" type="text" readonly="readonly" ng-model="data.project"  /></td>
					    
					</tr>
					<tr>
					    <th><span>箱长:</span></th>
						<td><input id="packLength" class="inputText" type="text" ng-model="data.packLength"  ht-validate="{maxlength:33}" /></td>
						<th><span>箱宽:</span></th>
						<td><input id="packWidth" class="inputText" type="text" ng-model="data.packWidth"  ht-validate="{maxlength:33}" /></td>
						<th><span>箱高:</span><span class="required">*</span></th>
						<td><input id="packHeight" class="inputText" type="text" ng-model="data.packHeight"  /></td>
					</tr>
					<tr>
						<th><span>规格包装数:</span></th>
						<td><input id="standardPackge" class="inputText" type="text" ng-model="data.standardPackge"  /></td>
						<th><span>零件重量:</span></th>
						<td><input id="partWeight" class="inputText" type="text" ng-model="data.partWeight"  /></td>
						<th><span>箱子重量:</span><span class="required">*</span></th>
						<td><input id="packWeight" class="inputText" type="text" ng-model="data.packWeight" /></td>
					 </tr>
					 <tr>
						<th><span>托盘尺寸长:</span></th>
						<td><input id="standardPackge" class="inputText" type="text" ng-model="data.trayLength"  ht-validate="{maxlength:33}" /></td>
						<th><span>托盘尺寸宽:</span></th>
						<td><input id="partWeight" class="inputText" type="text" ng-model="data.trayWidth"  ht-validate="{maxlength:33}" /></td>
						<th><span>托盘尺寸高:</span><span class="required">*</span></th>
						<td><input id="packWeight" class="inputText" type="text" ng-model="data.trayHeight" ht-validate="{required:true,maxlength:33}" /></td>
					 </tr>
					 <tr>
						<th><span>单一零件摆放图:</span></th>
						<td><input id="singlePartPutPic" class="inputText" type="file" accept="image/gif,image/jpeg,image/jpg,image/png" ng-model="data.singlePartPutPic" name="singlePartPutPic"/></td>
						<th><span>包装侧视图:</span></th>
						<td><input id="packSideLookPic" class="inputText" type="file" accept="image/gif,image/jpeg,image/jpg,image/png" ng-model="data.packSideLookPic" name="packSideLookPic" /></td>
						<th><span>文字说明:</span><span class="required">*</span></th>
						<td><input  class="inputText" type="text" ng-model="data.wordDesc"  /></td>
					 </tr>
					 <tr>
						<th><span>单一零件图:</span></th>
						<td><input id="singlePartPic" class="inputText" type="file" accept="image/gif,image/jpeg,image/jpg,image/png" ng-model="data.singlePartPic" name="singlePartPic" /></td>
						<th><span>包装俯视图:</span></th>
						<td><input id="packOverLookPic" class="inputText" type="file" accept="image/gif,image/jpeg,image/jpg,image/png" ng-model="data.packOverLookPic" name="packOverLookPic" /></td>
					 </tr>
					 <tr>
						<th><span>看板位置:</span></th>
						<td><input  type="radio" name="boardLocation" ng-model="data.boardLocation" value="1"  />L</td>
						<td><input  type="radio" name="boardLocation" ng-model="data.boardLocation" value="2" />W</td>
					 </tr>
					 <tr>
					    <th> </th>
					    <td><input type="checkbox" name="intMate" ng-model="data.intMate" value="1" />内材</td>
						<td><input type="checkbox" name="onePackage" ng-model="data.onePackage" value="1" />逐个包装</td>
						<td><input type="checkbox" name="dustCover" ng-model="data.dustCover" value="1" />防尘罩</td>
						
					 </tr>
				</table>
			</form>

		</div>
	</div>
</body>
</html>

<script>
var fileName=null;
$('#singlePartPutPic').change(function(){
   fileName = $('#singlePartPutPic').text($('#singlePartPutPic').val());
});
console.log(fileName);

function changeReadonyFun(){
	var boxCode = $('#boxCode').val();
	//箱code为其他则长宽高可编辑
	if(boxCode == '其他'){
		$('#packLength').attr('disabled',false);
		$('#packWidth').attr('disabled',false);
		$('#packHeight').attr('disabled',false);
		$('#packLength').val("");
		$('#packWidth').val("");
		$('#packHeight').val("");
	}else{
		$('#packLength').attr('disabled',true);
		$('#packWidth').attr('disabled',true);
		$('#packHeight').attr('disabled',true);
		$.ajax({
		    type: "post",
		    url: __ctx+"/pkg/pkgBox/getMsgByBoxCode",
		    data: {
		    	'boxCode' : boxCode,
		    	'fileName':fileName
		    },
		    dataType: "json",
		    success: function(result){
		    	$('#packLength').val(result.packLength);
		    	$('#packWidth').val(result.packWidth);
		    	$('#packHeight').val(result.packHeight);
		    }
		});
	}
}

function uploadFileFun(){
	$.ajax({
	    type: "post",
	    url: __ctx+"/pkg/pkgBox/uploadFile",
	    data: {
	    	'boxCode' : boxCode
	    },
	    dataType: "json",
	    success: function(result){
	    	$('#packLength').val(result.packLength);
	    	$('#packWidth').val(result.packWidth);
	    	$('#packHeight').val(result.packHeight);
	    }
	});
}

</script>