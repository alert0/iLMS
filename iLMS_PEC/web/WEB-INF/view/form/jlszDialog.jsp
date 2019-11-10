<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/customquery/customQueryService.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/ueditor/plugins/gangedSetting/page/js/Controller.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/angular/form/service/BpmFormService.js"></script>
		<script type="text/javascript">
			var formId=window.passConf.formId;
			var formFieldList=window.passConf.formFieldList;
			var formFieldList=window.passConf.formFieldList;
			var gangedsettingjson=window.passConf.jlsz;
			function isEditorContentContain(str){
		    	return true;
		    }
		</script>
	</head>
	
	<body ng-app="app" ng-controller="Controller">
		<fieldset>
    		<legend>查询设置</legend>
    		<table class="table-form">
	    		<tr>
	    			<th>选择查询：</th>
	    			<td col="4">
	    				<select ht-field-valid="{'required':true}" class="inputText field-home must-error" ng-model="prop.id" ng-change="changeCid(prop.id);" ng-options="m.id as m.name for m in customQuerys"></select>
	    			</td>
	    		</tr>
	    		<tr ng-repeat="c in selectedCustomQuery.conditionfield">
	    			<td><input type="text" class="inputText" disabled="disabled" ng-model="prop.query[$index].condition" ng-init="prop.query[$index].condition=c.field" /></td>
	    			<td>
	    				<select class="inputText" ng-model="prop.query[$index].trigger"  ng-options="(m.path+'.'+m.name) as (m.desc!=null?m.desc:m.name) for m in formFieldList |filter:filterFormFieldList">
							<option value="">--请选择--</option>
						</select>
						<input type="text" placeholder="请输入预设值" ng-model="prop.query[$index].initValue" class="inputText" />
	    			</td>
	    		</tr>
    		</table>
    	</fieldset>
    	
    	<fieldset ng-if="selectedCustomQuery!=null">
    		<legend>返回结果设置</legend>
    		<table class="table-form">
    			<th>绑定对象</th>
    			<th style="text-align: left;">返回字段</th>
    			<tr >
		   			<th>key</th>
		   			<td>
			   			<select ht-field-valid="{'required':true}" ng-model="prop.bind.key" class="inputText field-home must-error"  ng-options="m.comment as m.comment for m in selectedCustomQuery.resultfield">
							<option value="">--请选择--</option>
						</select>
					</td>
		   		</tr>
    			<tr >
		   			<th>value</th>
		   			<td>
			   			<select ht-field-valid="{'required':true}" ng-model="prop.bind.value" class="inputText field-home must-error" ng-options="m.comment as m.comment for m in selectedCustomQuery.resultfield">
							<option value="">--请选择--</option>
						</select>
					</td>
		   		</tr>
    		</table>
    	</fieldset>
	</body>
</html>