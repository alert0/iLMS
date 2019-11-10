<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="formApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/ngEdit.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/form/controller/AddFormStepController.js"></script>
<style type="text/css">
	label.error{
		color: red;
	}
</style>

<script>
	function checkKeyIsExist() {
		var url = __ctx + "/form/formDef/checkkeyIsExist";
		var key = $('#key').val();
		if (!key) {
			$('#key').next("label.wrong").remove();
			return;
		}
		$.post(url, {
			key : key
		}, function(data) {
			if (data) {
				if (!$('#key').next("label.wrong")[0])
					$('#key').after("<label class='error wrong'>别名已经存在</label>");
			} else
				$('#key').next("label.wrong").remove();
		});
	}
	
</script>
</head>
<body style="overflow-y: hidden;" fit="true" ng-controller="addStepOneCtrl">
	
		<form method="post" action="formDefEdit" name="myForm">
			<table class="table-form"  cellspacing="0" >
				<tr>
					<th>表单分类</th>
					<td>
						<input type="text" name="typeName"  ng-model="form.typeName" style="display:none;"/>
						<input ng-model="form.typeId" class="easyui-combotree-me " name="typeId" ht-validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<th>表单名称</th>
					<td>
						<input class="inputText form-control initial field-home" ht-validate="{required:true,maxlength:48}" type="text" placeholder="请输入表单标题" name="name" ng-model="form.name" style="width: 65%; font-size: 12px;" />
					</td>
				</tr>
				<tr>
					<th>别名</th>
					<td>
						<input onblur="checkKeyIsExist(this)" ht-pinyin="form.name" id="key" class="inputText" ht-validate='{required:true,maxlength:18,varirule:true}' type="text" placeholder="请输入表单Key" name="key" ng-model="form.key" style="width: 65%; font-size: 12px;" />
					</td>
				</tr>
				<tr>
					<th>说明</th>
					<td>
						<textarea class="inputText form-control initial" rows="3" cols="50" style="width: 65%; font-size: 12px;" placeholder="请输入表单说明" name="desc" ng-model="form.desc"></textarea>
					</td>
				</tr>
				<tr>
					<th>业务对象</th>
					<td>
						<span ng-repeat="bo in form.bos " class="owner-span span-bo" boid="{{bo.id}}">{{bo.desc}}</span>
						<i class="btn btn-primary fa fa-search" ng-click="chooseBo()"></i>
						<input class="inputText form-control " style="display: none" rows="3" cols="50" style="width: 65%; " placeholder="请选择业务对象" name="bos" ng-model="form.bos"></input>
					</td>
					
				</tr>
			</table>
		</form>
				
</body>
</html>