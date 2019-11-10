<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
<%@include file="/commons/include/ngEdit.jsp"%>
<%@include file="/commons/include/zTree.jsp"%>
<script type="text/javascript" src="${ctx}/js/common/customform/directiveTpl.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/bo/def/bODefEditController.js"></script>
<script type="text/javascript">
	var categoryId = "${param.categoryId}";
	var id = "${param.id}";
	
	function closePage(){		
		HT.window.closeEdit(true,'grid');
		window.close();
	}
</script>
</head>
<body ng-controller="ctrl" ng-init="init()">
	<div>
		<!-- 顶部按钮 -->
		<div class="toolbar-panel" ng-show="isStepCreate">
			<div class="buttons">
				<a class="btn btn-primary btn-sm fa-save" ng-model="data" ht-save="bODef/save" ng-if="isEditable" config='{"afterSave":"afterSave"}'>
					<span>保存</span>
				</a>
				<a class="btn btn-primary btn-sm fa-back" onClick="closePage()">
					<span>返回</span>
				</a>
			</div>
		</div>
		<form name="form" ht-load="bODef/getObject?id=${param.id}" ng-model="data">
			<table class="table-form" cellspacing="0">
				<tr>
					<th>
						<span>描述:</span>
						<span class="required">*</span>
					</th>
					<td>
						<input class="inputText" type="text" ng-model="data.description" ht-validate="{required:true}" ng-disabled="!isEditable" />
					</td>
					<th>
						<span>别名:</span>
						<span class="required">*</span>
					</th>
					<td>
						<input ng-if="isEditable" class="inputText" type="text" ng-model="data.alias" ht-validate="{required:true,isexist:'bODef/isExist?id=${param.id}',fields:true}" ht-pinyin="data.description" />
						<input ng-if="!isEditable" class="inputText" type="text" ng-model="data.alias" disabled="disabled" />
					</td>
				</tr>

				<tr>
					<th>
						<span>主实例:</span>
						<span class="required">*</span>
					</th>
					<td>
						<span ng-show="mainEnt">{{mainEnt.desc}}</span>

						<a class="btn btn-primary btn-sm fa-rebel" ng-click="selectMainEnt()" ng-if="isEditable">
							<span>选择</span>
						</a>
					</td>
					<th>
						<span>分类:</span>
						<span class="required">*</span>
					</th>
					<td>
						<div ng-if="isEditable"  style="width: 170px" type="text" ht-dic="data.categoryId" ng-model="data.categoryId" ht-validate="{required:true}" url="${ctx }/system/sysType/getTypesByKey?typeKey=DEF_TYPE" key-name="id"></div>
						<div permission="r" ng-if="!isEditable"  style="width: 170px" type="text" ht-dic="data.categoryId" ng-model="data.categoryId" ht-validate="{required:true}" url="${ctx }/system/sysType/getTypesByKey?typeKey=DEF_TYPE" key-name="id"></div>
					</td>
				</tr>

				<tr>
					<th>
						<span>支持数据库:</span>
					</th>
					<td>
						<input type="checkbox" ng-true-value="1" ng-false-value="0" ng-model="data.supportDb" ng-disabled="!isEditable" />
					</td>
					
					<th style="display: none;">
						<span>是否可用:</span>
					</th>
					<td style="display: none;">
						<input type="checkbox" ng-true-value="normal" ng-false-value="forbidden" ng-model="data.status" ng-disabled="!isEditable" />
					</td>
				</tr>


			</table>

			<table class="table-list" cellspacing="0">
				<thead>
					<tr ng-if="isEditable">
						<td colspan="5">
							<div class="buttons">
								<a class="btn btn-primary btn-sm fa-add" ng-click="addSubEnt()">
									<span>添加</span>
								</a>
							</div>
						</td>
					</tr>
				</thead>
				<tr>
					<th>类型</th>
					<th>描叙</th>
					<th>名称</th>
					<th ng-if="isEditable">操作</th>
				</tr>
				<tr ng-repeat="ent in subEnts">
					<td>
						<select class="inputText" ng-model="ent.relation" ng-disabled="!isEditable">
							<option value="onetoone">一对一</option>
							<option value="onetomany">一对多</option>
							<option value="manytomany">多对多</option>
						</select>
					</td>
					<td>{{ent.desc}}</td>
					<td>{{ent.name}}</td>
					<td ng-if="isEditable">
						<a href="javaScript:void(0)" ng-click="ArrayTool.del($index,subEnts)" class="btn btn-sm btn-default fa-times"></a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>