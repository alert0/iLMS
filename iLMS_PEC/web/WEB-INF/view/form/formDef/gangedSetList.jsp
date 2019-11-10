<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/form/controller/gangedSetList.js"></script>
<title>联动设置</title>

<script type="text/javascript">
	function getResult() {
		var scope = AngularUtil.getScope();
		if(!scope.form.$valid){
			$.topCall.alert("温馨提示","页面校验不通过！");
			return "validError";
		}
		var res=[];
		for(var i=0,obj;obj=scope.gangedFields[i++];){
			res.push({chooseField:obj.chooseField,changeField:obj.changeField,id:obj.id});
		}
		return parseToJson(res);
	}
</script>

</head>
<body ng-controller="ctrl">
	 <div>
	 <div class="toolbar-search ">
		 <div class="toolbar-head">
			<div class="buttons">
				<a class="btn btn-primary btn-sm fa-add" ng-click="addGanged()"> <span>添加</span></a> 
			</div>
		 </div>
	 </div>
	 <form id="form" name="form" >
		<table cellpadding="1" cellspacing="1" border="1" style="border-collapse:collapse;" class="table-list">
			<thead>
				<tr>
					<th>操作</th>
					<th width="245px">所选字段</th>
					<th width="200px">所选值</th>
					<th width="260px">变换字段</th>
					<th width="150px">变换类型</th>
				</tr>
			</thead>
			<tbody>
				<tr ng-repeat="ganged in gangedFields track by $index">
					<td style="text-align: center;">
						<a class="btn btn-default fa fa-delete" title="移除" ng-click="removeGanged(ganged)"></a>
						<input class="form-control" ng-model="ganged.id" ng-hide="true" ng-init="ganged.id = ganged.id!=0?ganged.id:$index+1 "  type="text"/>
					</td>
					<td colspan="2">
						<table class="table" ng-repeat="chooseField in ganged.chooseField track by $index">
							<tbody>
								<tr>
									<td width="245px">
										<div>
											<select ng-model="chooseField.pathName" ng-change="setFieldOtherInfo(chooseField,chooseField.pathName,this,$parent.$index,$index)" ht-validate="{required:true}" style="width: 60%;">
												 <optgroup label="{{fields.desc}}" ng-repeat="fields in ganged.choosefieldData">
												   		<option value="{{m.path}}.{{m.name}}" ng-if="m.ctrlType!='fileupload'&&m.ctrlType!='officeplugin'&&m.ctrlType!='dic'" ng-selected="chooseField.pathName == m.path + '.' +m.name "  ng-repeat="m in fields.children">{{m.desc}}</option>
												  </optgroup>
											</select>
											<a class="btn btn-default fa fa-add" title="添加" ng-click="addChooseField(ganged.chooseField)"></a>
											<a class="btn btn-default fa fa-delete" ng-hide="$first" title="移除" ng-click="removeChooseField(ganged.chooseField,chooseField)"></a>
										</div>
									</td>
									<td width="200px">
										<input class="form-control" ng-model="chooseField.value" type="text" ng-if="getFieldType(chooseField.pathName)=='text'" />
										
										<input class="form-control" ng-model="chooseField.value" type="text" ng-if="getFieldType(chooseField.pathName)=='date'" ht-date="{{getDataFormat(chooseField.pathName)}}"/>
										
										<select class="form-control"  ng-model="chooseField.value" ng-if="getFieldType(chooseField.pathName)=='select'"  ng-options="c.key as c.value for c in getSelectOpinion(chooseField.pathName)">
										</select>
										
										<span ng-if="getFieldType(chooseField.pathName)=='selector'" ng-if="chooseField.selectedNames!=''" class="span-user owner-span">{{chooseField.selectedNames}}</span>
										<span ng-if="getFieldType(chooseField.pathName)=='selector'" ng-hide="true" ng-model="chooseField.value" class="span-user owner-span" ></span>
										<a ng-if="getFieldType(chooseField.pathName)=='selector'" class="btn btn-sm btn-primary fa-search" ng-click="showSelectDialog(chooseField,chooseField.pathName)">选择</a>
										
									</td>
								</tr>
							</tbody>
						</table>
					</td>
					<td colspan="2">
						<table class="table" ng-repeat="changeField in ganged.changeField track by $index">
							<tbody>
								<tr>
									<td width="260px">
										<div>
											<select ng-model="changeField.pathName" ng-selected="setFieldOtherInfo(changeField,changeField.pathName,this)"  ht-validate="{required:true}" style="width: 65%;">
												  <optgroup label="{{fields.desc}}"  ng-repeat="fields in ganged.changefieldData">
												   	  <option value="{{m.path}}.{{m.name}}" ng-selected="changeField.pathName == m.path+'.'+m.name"  ng-if="m.ctrlType!='officeplugin'" ng-repeat="m in fields.children">{{m.desc}}</option>
												  </optgroup>
												  <optgroup ng-if="separators && ganged.isSub=='false'" label="分组">
												   	  <option value="separator.{{m.key}}" ng-selected="changeField.pathName == 'separator.'+m.key" ng-repeat="m in separators">{{m.name}}</option>
												  </optgroup>
											</select>
											<a class="btn btn-default fa fa-add" title="添加" ng-click="addChangeField(ganged.changeField)"></a>
											<a class="btn btn-default fa fa-delete" ng-hide="$first" title="移除" ng-click="removeChangeField(ganged.changeField,changeField)"></a>
										</div>
									</td>
									<td width="150px">
										<select ng-model="changeField.type"  ht-validate="{required:true}" >
											<option value="1">隐藏</option>
											<option value="2">显示</option>
											<option ng-if="!isSeparator(changeField.pathName)" value="3">只读</option>
											<option ng-if="!isSeparator(changeField.pathName)" value="4">非只读</option>
											<option ng-if="!isSeparator(changeField.pathName)" value="5">必填</option>
											<option ng-if="!isSeparator(changeField.pathName)" value="6">非必填</option>
											<option ng-if="!isSeparator(changeField.pathName)" value="7">置空</option>
											<option ng-if="'select' == changeField.ctrlType&&!isSeparator(changeField.pathName)" value="8">级联</option>
										</select>
										<input class="form-control" ng-if="changeField.cascade" ng-hide="true" type="text" />
										<a class="link done" ng-click="openSetCascade(changeField)" ng-if="changeField.type==8">级联设置</a>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		</form>
	</div> 
</body>
</html>