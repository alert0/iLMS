<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<%@include file="/commons/include/zTree.jsp" %>
		<%@include file="/commons/include/codeMirror.jsp"%>
		
		<script type="text/javascript">
			var app = angular.module('app', ['formDirective','ui.codemirror']);
			app.controller("formBusSetCtrl", [ '$scope', 'baseService', function(scope, baseService) {
				scope.canPreviewTreeList = false;
				//获取数据
				baseService.postForm(__ctx+"/form/formBusSet/getJson",{formKey:__param.formKey,}).then(function(data){
					
					scope.formBusSet =data.formBusSet||{formKey:__param.formKey};
					scope.boJson = data.boJson;
					scope.initVarTree();
					scope.boJson.children.push({nodeType:'field',name:'id_',desc:'ID'}); //加上ID
					if(scope.formBusSet.isTreeList){
						data.formBusSet.treeConf = angular.fromJson(data.formBusSet.treeConf);
						scope.canPreviewTreeList = true;
					}else {
						scope.formBusSet.treeConf = {};	
					}
					
					window.setTimeout(function(){
							$('.tab').tabs({ border:false });
						},100)
				})
				
				scope.$root.$on('beforeSaveEvent',function(event,data){
					
				});
				
				scope.save  = function(){
					if (!scope.form.$valid) return;
					var formBusSet = angular.copy(scope.formBusSet);
					if(formBusSet.isTreeList){
						formBusSet.treeConf = angular.toJson(scope.formBusSet.treeConf);
					}
					
					var rtn = baseService.post("${ctx}/form/formBusSet/save",formBusSet);
					rtn.then(function(data) {
						if (data.result == 1) {
							$.topCall.confirm(data.title?data.title:"操作成功",data.message+",是否继续操作", function(r) {
								if(r){
									window.location.reload(true);
								}else{
									HT.window.closeEdit(true,'grid');
								}
							});
						} else {
							if (data.cause) { $.topCall.errorStack(data.message, data.cause, "错误信息"); } 
							else { $.topCall.error(data.message); }
						}
					}, function(status) {
						$.topCall.error("请求失败");
					});
				}
				
				scope.newSqlList = function(){
					HT.window.openEdit("${ctx}/form/formBusSet/createSqlList?formKey="+__param.formKey, "自定义sql列表添加", 'add', 'grid', 500, 500, null, null, "123", true);
				}
				
				scope.permissionSet = function(instId){
					var url = __ctx+"/form/rights/rightsDialog?formKey="+__param.formKey+"&instId="+instId;
					HT.window.openEdit(url,"表单基础权限设置", 'list', 'grid', 500, 500, null, null, "asdf", true);
				}
				
				scope.initVarTree = function(){
					scope.varTree = new ZtreeCreator('varTree', __ctx + "/flow/node/varTree",scope.boJson)
						.setDataKey({ name : 'desc' }).setCallback({onClick :scope.setBoField})
						.makeCombTree("tempTree")
						.initZtree({}, 1);
					
					$(".boVars").bind("click", function(){
						var broadcast = $(this).attr("broadcast")
						scope.CodeMirrorBroadcast = broadcast;
						scope.varTree.showMenu($(this));
					});

				}
			
				
				scope.setBoField = function(event, treeId, node){
					var parentNode = node.getParentNode();
					// 子表情况做提示
					 if (node.nodeType == 'sub'){
						keyStr = "boData.getSubByKey('"+node.name+"')  /*返回子表数据集合 List<BoData> */";
					 }
					 else if(parentNode.nodeType == 'main'){
						keyStr = 'boData.getValByKey("'+node.name+'") /*数据类型：'+node.dataType+'*/';
					}else if(parentNode.nodeType == 'sub'){
						keyStr = '/* eg: List<BoData> list = boData.getSubByKey("'+parentNode.name+'");  list.get(0).getValByKey("'+node.name+'")*/';
					}else return ;
					 scope.varTree.hideMenu(); 
					
					scope.insetCode(keyStr);
				}
				
				scope.insetCode = function(str) {
					if(!scope.CodeMirrorBroadcast)scope.CodeMirrorBroadcast= "CodeMirror";
					scope.$broadcast(scope.CodeMirrorBroadcast, function(CodeMirror) {
						CodeMirror.replaceSelection(str);
					});
				};
				
			}]);
		
			
			
		</script>
		<style type="text/css">
			.CodeMirror {
	   			 height: 200px!important;
			}
		</style>
	</head> 
	<body ng-controller="formBusSetCtrl">
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary fa-save btn-sm" ng-click="save()"><span>保存</span></a>
					<a class="btn btn-primary fa-back btn-sm" onClick="HT.window.closeEdit(true,'grid');"><span>返回</span></a>
					<a href="" ng-if="formBusSet.id"  ng-click="newSqlList()" class="btn btn-primary btn-sm">创建自定义SQL列表页 </a>
					<a href=""  ng-click="permissionSet(false)" class="btn btn-primary btn-sm">编辑页权限设置</a>
					<a href=""  ng-click="permissionSet(true)" class="btn btn-primary btn-sm">详细页权限设置</a>
				</div>
			</div>
			<form name="form">
				 <div class="" ng-if="formBusSet.id">
				      	<a href="${ctx}/form/formBus/{{formBusSet.formKey}}/edit?id=" target="blank" class="btn btn-link">编辑页 </a>
						<a href="${ctx}/form/formBus/{{formBusSet.formKey}}/get?id=" target="blank" class="btn btn-link">详细页 </a>
						<a href="${ctx}/form/formBus/{{formBusSet.formKey}}/treeList" ng-if="canPreviewTreeList" target="blank" class="btn btn-link">树形列表页 </a>
				 </div>
				<div id="tempTree" style="display: none;"></div>
			
				<!-- javaScript 脚本保存 -->
				<div id="javaScriptTab" class="tab" style="width:100%;height:205px;padding: 5px">
				    <div title="保存前置JavaScript 脚本" style="">
						<textarea ng-model="formBusSet.jsPreScript" ui-codemirror broadcast="jsPreScript"></textarea>
				    </div>
				    <div title="保存后置JavaScript 脚本" style="overflow:auto;">
						<textarea ng-model="formBusSet.jsAfterScript" ui-codemirror broadcast="jsAfterScript"></textarea> 
				    </div>
				    <div title="帮助" style="overflow:auto;">
				     	<div style="margin: 10px;">
				     		【前置脚本提交数据前执行】你可以使用<code>data</code>参数 数据格式：<code>{fieldName:val,subBo:[{field:val}]}</code><br>
				     		【返回值】<code>true</code> or <code>false</code> "false"则取消提交eg:
<pre>
if(data.fieldName =='1'){
	$.topCall.error("出错了。");
	return false;
}</pre>
				     		【后置脚本提交返回后执行】你可以使用<code>data</code>参数 ，数据格式：<code>{"result":1/0,"message":"更新**成功/失败","cause":""}</code><br>
				     	</div>
				    </div>
				</div>
				<!-- java 脚本保存 -->
				<div id="javaTab" class="tab" style="width:100%;height:239px;padding: 10px">
				    <div title="保存前置Java 脚本">
				    	<div> 
							<a class="boVars btn btn-primary btn-xs" id="boVarsBtn" broadcast="preScript" style="margin-left: 30px">表单字段</a> 
						</div>
						<textarea ng-model="formBusSet.preScript" ui-codemirror broadcast="preScript"></textarea> 
				    </div>
				    <div title="保存后置Java 脚本" >
						<div> 
							<a class="boVars btn btn-primary btn-xs" broadcast="afterScript" style="margin-left: 30px">表单字段</a> 
						</div>
						<textarea ng-model="formBusSet.afterScript" ui-codemirror broadcast="afterScript"></textarea> 
				    </div>
				      <div title="帮助" style="overflow:auto;">
					      <div style="margin: 10px;">
					     		【前置脚本保存前执行】你可以使用<code>boData</code>参数,<br>
					     		【常用操作数据方法】：
<pre>
validBoDataExist(boData,fieldName,messages)//校验唯一字段。 msg:已存在提示异常信息
String getString(String key)//获取bo某个字段数据
Object getValByKey(String key)//获取bo某个字段数据
List<BoData> getSubByKey(String subKey)//获取子BO数据
void set(String key,Object val)//设置某个字段
</pre>
					     		【后置脚本保存后执行】参数为<code>boData</code><br>
					     		【取消提交】可以抛出异常，让事物回滚<code>throw new RuntimeException("异常说明");
					     		</code>
					     		
					     		
				     	</div>
				    </div>
				</div>
				
				<table class="table-form"  cellspacing="0">
					<tr>								
						<th>是否树形列表:</th>
						<td colspan="3">
							<span ht-boolean="1/0" class="checkbox" text="是/否" ng-model="formBusSet.isTreeList"></span>
						</td>
					</tr>
					<tr ng-if="formBusSet.isTreeList">					
						<th>树ID字段:</th>
						<td>
							<select ng-model="formBusSet.treeConf.idKey" ht-validate="{required:true}" ng-options="f.name as f.desc for f in boJson.children|filter:{nodeType:'field'}">
								<option value="">请选择</option>
							</select>
						</td>	
						<th>父ID字段:</th>
						<td>
							<select ng-model="formBusSet.treeConf.pIdKey" ht-validate="{required:true}" ng-options="f.name as f.desc for f in boJson.children|filter:{nodeType:'field'}">
								<option value="">请选择</option>
							</select>						
						</td>							
					</tr>
					<tr ng-if="formBusSet.isTreeList">								
						<th>展示字段:</th>
						<td>
							<select ng-model="formBusSet.treeConf.name" ht-validate="{required:true}" ng-options="f.name as f.desc for f in boJson.children|filter:{nodeType:'field'}">
								<option value="">请选择</option>
							</select>
						</td>	
						<th>title</th>
						<td>
							<select ng-model="formBusSet.treeConf.title" ng-options="f.name as f.desc for f in boJson.children|filter:{nodeType:'field'}">
								<option value="">请选择</option>
							</select>
						</td>							
					</tr>
				</table>
			</form>
	</body>
</html>