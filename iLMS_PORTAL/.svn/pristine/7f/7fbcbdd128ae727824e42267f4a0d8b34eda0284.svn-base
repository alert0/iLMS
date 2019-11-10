<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
<head>
	<%@include file="/commons/include/ngEdit.jsp"%>
	<%@include file="/commons/include/zTree.jsp"%>
	<script type="text/javascript" src="${ctx}/js/common/customform/directiveTpl.js"></script>
	<script type="text/javascript" src="${ctx}/js/platform/system/sysDataSource/sysDataSourceService.js"></script>
	<script type="text/javascript" src="${ctx}/js/platform/bo/ent/bOEntEditController.js"></script>
	<script type="text/javascript">
        var packageId = "${param.packageId}";
        var id = "${param.id}";

        $(function() {
            var scope = AngularUtil.getScope();
            $.fn.addRule("nameCheck", {
                rule : function(v, param, elm) {
                    var b = true;
                    $($("[ng-model='attr.name']")).not(elm).each(function(index) {
                        var val = $(this).val();
                        var sort = $(this).attr("sort");//排序标志，标识为true只有点击完排序按钮后才会有
                        if (sort !="true" && val.toLowerCase() == v.toLowerCase()) {
                            b = false;
                        }
                        var len = $("[ng-model='attr.name']").length-2;//除了自身
                        if(index == len){//当比较到最后一个元素时，排序标志设为false
                            $("[ng-model='attr.name']").attr("sort","false");
                        }
                    });
                    return b;
                },
                msg : "该名字已被使用"
            });
        });
	</script>
</head>
<body ng-controller="ctrl" ng-init="init()">
<div class="easyui-layout" fit="true" scroll="no">
	<div data-options="region:'north',border:false">
		<!-- 顶部按钮 -->
		<div class="toolbar-panel" ng-show="isStepCreate">
			<div class="buttons">
				<a class="btn btn-sm btn-primary fa-save" ng-model="data" ht-save="bOEnt/save" config='{"afterSave":"afterSave"}'>
					<span>保存</span>
				</a>
				<a class="btn btn-sm btn-primary fa-back" href="javascript:HT.window.closeEdit(true,'grid');">
					<span>返回</span>
				</a>
			</div>
		</div>
	</div>

	<div data-options="region:'center',border:false" style="overflow: auto;">
		<form name="form" ht-load="bOEnt/getObject?id=${param.id}" ng-model="data">
			<table class="table-form" cellspacing="0">
				<tr>
					<td>实体分类:</td>
					<td style="width: 300px;">
						<div type="text" ng-model="data.packageId" ht-validate="{required:true}" ht-dic="data.packageId" url="${ctx}/system/sysType/getTypesByKey?typeKey=ENT_TYPE" key-name="id"></div>
					</td>
					<td>
						<span>实体描述:</span>
						<span class="required">*</span>
						<input class="inputText" type="text" ng-model="data.desc" ht-validate="{required:true,maxlength:48}" ng-disabled="!isEditabled" />
						<span>实体名称:</span>
						<span class="required">*</span>
						<input class="inputText" ng-disabled="!isEditabled" type="text" ng-model="data.name" ht-validate="{required:true,varirule:true,fields:true,isexist:'bOEnt/isExist?id=${param.id}'}" ht-pinyin="data.desc" />
						<div style="display: none;">
							<span>可用:</span>
							<input ng-disabled="!isEditabled" type="checkbox" ng-true-value="enabled" ng-false-value="forbidden" ng-model="data.status" />
						</div>
					</td>
				</tr>
			</table>

			<table class="table-list" cellspacing="0" style="margin-bottom: 35px;">
				<tr>
					<td colspan="8">
						<div class="buttons">
							<a class="btn btn-sm btn-primary fa-add" ng-click="addAttr()">
								<span>添加</span>
							</a>
						</div>
					</td>
				</tr>
				<tr>
					<th>注释</th>
					<th>名称</th>
					<th>必填</th>
					<th>数据类型</th>
					<th>属性长度</th>
					<th>默认值</th>
					<th>排序</th>
					<th>操作</th>
				</tr>
				<tr ng-repeat="attr in data.attributeList track by $index">
					<td>
						<input type="text" class="inputText" ng-model="attr.desc" ht-validate="{required:true}" ng-disabled="!isEditabled&&!attr.isNew" />
					</td>
					<td>
						<input type="text" class="inputText" ng-model="attr.name" ht-pinyin="attr.desc" ht-validate="{required:true,nameCheck:true,varirule:true}" ng-disabled="!isEditabled&&!attr.isNew" />
					</td>
					<td>
						<span ht-boolean="1/0" text="必填" ng-model="attr.isRequired" ng-disabled="!isEditabled&&!attr.isNew" />
					</td>
					<td>
						<select class="inputText" ng-model="attr.dataType" ng-change="changeDbType(attr)" ng-disabled="!isEditabled&&!attr.isNew">
							<option value="varchar">字符串</option>
							<option value="number">数字型</option>
							<option value="date">日期型</option>
							<option value="clob">大文本</option>
						</select>
						<select ng-if="attr.dataType=='date'" class="inputText" ng-disabled="attr.format=='HH:mm:ss'&&!attr.isNew&&attr.defaultValue&&attr.defaultValue.length==8" ng-model="attr.format">
							<option value="yyyy-MM-dd HH:mm:ss">yyyy-MM-dd HH:mm:ss</option>
							<option value="yyyy-MM-dd">yyyy-MM-dd</option>
							<option value="HH:mm:ss">HH:mm:ss</option>
						</select>
					</td>
					<td>
						<input type="text" style="width: 50px" ng-if="attr.dataType=='varchar'  || attr.dataType=='number'" ng-model="attr.attrLength" class="inputText" ht-validate="{required:true}" ng-disabled="!isEditabled&&!attr.isNew" />
						<span ng-if="attr.dataType=='number'">.</span>
						<input type="text" style="width: 50px" ng-if="attr.dataType=='number'" ng-model="attr.decimalLen" class="inputText" ht-validate="{required:true}" ng-disabled="!isEditabled&&!attr.isNew" />
					</td>
					<td>
						<input type="text" ng-if="attr.dataType!='clob'&&attr.dataType!='date'&&attr.dataType!='number'" ng-model="attr.defaultValue" class="inputText" ng-disabled="!isEditabled&&!attr.isNew" />
						<input type="text" ng-if="attr.dataType=='number'" ng-model="attr.defaultValue" class="inputText"  ht-validate="{maxDecimalLen:{{attr.decimalLen}},maxIntLen:{{attr.attrLength}},number:true}" ng-disabled="!isEditabled&&!attr.isNew" />
						<input type="text" ng-if="attr.dataType=='date'&&attr.isRequired==0" ht-date="{{attr.format}}" ng-model="attr.defaultValue" class="inputText" ng-disabled="!isEditabled&&!attr.isNew"/>
						<input type="text" ng-if="attr.dataType=='date'&&attr.isRequired==1" ht-date="{{attr.format}}" ng-model="attr.defaultValue" class="inputText" ng-disabled="!isEditabled&&!attr.isNew"  ht-validate="{required:true}" />
					</td>
					<td>
						<a href="javascript:javaScript:void(0)" ng-click="ArrayChange.up($index,data.attributeList)" class="btn btn-sm btn-default fa-chevron-up"></a>
						<a href="javascript:javaScript:void(0)" ng-click="ArrayChange.down($index,data.attributeList)" class="btn btn-sm btn-default fa-chevron-down"></a>
					</td>
					<td>
						<a ng-if="isEditabled||attr.isNew" href="javaScript:void(0)" ng-click="ArrayChange.del($index,data.attributeList)" class="btn btn-sm btn-default fa-times"></a>
					</td>
				</tr>
			</table>

		</form>
	</div>
</div>
</body>
</html>