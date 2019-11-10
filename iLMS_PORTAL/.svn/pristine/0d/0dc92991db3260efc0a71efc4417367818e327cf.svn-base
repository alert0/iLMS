<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="formApp" >
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/ngEdit.jsp"%>
		<%@include file="/commons/include/zTree.jsp"%>
		<f:link href="formEdit.css"></f:link>
		<f:link href="component.css"></f:link>
		<script type="text/javascript">
			window.UEDITOR_HOME_URL = "${ctx }/js/lib/ueditor/";
		</script>
		<script type="text/javascript" src="${ctx}/js/platform/form/controller/formDefEdit.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/form/service/formDefService.js"></script>
		<script type="text/javascript">
			var formId = '${formId}';
			var typeId = '${typeId}';
			var type = '${type}'
			var name = '${name}';
			var key = '${key}';
			var bos = '${bos}';
			var desc = '${desc}';
			var sysTypeTree = null;
			
			$(function(){
				$("#field-container").height($("body").height()-255);
				$("#params").height($("body").height()-30);
				if(parent.formNextStep){
					$(".btn.btn-default.fa.fa-back").hide();
					$(".panel.layout-panel.layout-panel-west")[0].style.top=0;
					$(".panel.layout-panel.layout-panel-center")[0].style.top=0;
				}
				//当浏览器高度变化时动态设置字段列表的高度
				$(window).resize(function () {          
					var scope = AngularUtil.getScope();
					scope.$apply(function(){
						scope.fieldListHeight = ($(window).height()-138)+"px";
					});
				});
			})
		</script>
		<style>
			a.togglechevron{
								text-decoration: none;
			}
			a.togglechevron:link{
			text-decoration:none;
			}
			a.togglechevron:visited{
			text-decoration:none;
			}
			a.togglechevron:hover{
			text-decoration:none;
			}
			a.togglechevron:active{
			text-decoration:none;
			}
			div.ng-invalid label{
				border:1px inset;
				border-color: #dd514c !important;
    			-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
    			box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
			}
		</style>
	</head>
	<body ng-controller="fieldCtrl" >
		<!-- container: ：     begin-->
		<div id="container" class="easyui-layout" fit="true" >
			<div class="layout-north " region="north" ng-show="isStepCreate">
				<div class="panel-header" style="width:100%;">
					<c:if test="${formId==''}">
						<div class="panel-title" >添加表单元数据定义</div>
					</c:if>
					<c:if test="${formId!=''}">
						<div class="panel-title" >编辑表单元数据定义</div>
					</c:if>
				</div>
				<div class="toolbar-panel">
					<div class="buttons" >
						<button class="btn btn-primary btn-sm fa-save" ng-click="save()">保存</button>
						<a href="javaScript:window.close()" class="btn btn-default btn-sm fa-back">关闭</a>
					</div>
				</div>
			</div>
			
			<!-- region: ：    左边树    begin-->
			<div id="" data-options="region:'west',title:'BO属性列表'" style="width: 200px;overflow: auto;">
				<div id="sysTypeTree" class="ztree" style=""></div>
			</div>
			<!-- region: ：    左边树    end-->
			<!-- region: ：  中间   begin-->
			<div id="layoutCenter" data-options="region:'center'" ng-click='removeFocusTarget($event);'>
				<form name="formEdit">
					<div class="row">
						<div class="col-md-6">
							<div class="panel panel-primary" style="margin:10px 5px 0 0;">
								<div class="panel-heading">
									<div class="row">
										<h4 class="col-md-10">字段列表</h4>
										<button type="button" class="btn btn-default" style="margin-top:3px;" ng-click="clearList()">清空列表</button>
									</div>
								</div>
								<div class="panel-body" style="overflow-y:auto;" ng-style="{'height':fieldListHeight}">
									<table class="table table-bordered table-hover">
										<thead>
											<tr class="field-header-tr">
												<th width="190">字段描述</th>
												<th width="128">控件类型</th>
												<th width="90">字段类型</th>
												<th width="100">分组</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
											<tr ng-class="{'editing':field.isEditing}" ng-repeat="field in fields" sn="{{field.sn}}">
												<td colspan="5" ng-if="field.children">
													<ul class="field-container" style="padding: 0;" sn="{{field.sn}}">
														<li ng-click="editing(field,true)" style="border: 0px solid #ccc; background-color: #ccc;">
															<div class="c-white border-none" style="height:60px;">
																<div class="form-group col-md-7 navbar-btn">
																	<input type="text" class="form-control" placeholder="请输入标题" ng-model="field.desc" ht-validate="{required:true}"/>
																</div>
																<div class="btn-group col-md-5">
																	<button type="button" class="btn btn-sm navbar-btn btn-default" ng-click="removeFieldTags(field.children)">移除字段</button>
																	<button type="button" class="btn btn-sm navbar-btn btn-default" ng-click="removeGroup(field,$event)">移除实例</button>
																	<a href="javascript:;" data-toggle="collapse" data-target=".{{field.entId}}ent" style="margin-top:15px;"
																	class="navbar-btn fa fa-chevron-up fa-lg togglechevron pull-right" title="显示隐藏字段列表"></a>
																</div>
															</div>
														</li>
														<!-- 组合字段的属性遍历 -->
														<div class="{{field.entId}}ent collapse in">
															<div style="border:1px solid #ccc;border-top-width: 0;">
																<table class="table table-hover">
																	<tbody>
																		<tr ng-repeat="child in field.children | orderBy:'sn'" class="no-top-border" field-list="field.children" field-tag="child" field-parent="field" array-tool="ArrayTool" form-ganged="form.ganged" separators="separators" index="$index"
																			ng-class="{'editing':child.isEditing,'editing-error':child.isError}" ng-click="editing(child)" sn="{{$index}}" pSn="{{$parent.$index}}" ltype="group-li">
																		</tr>
																	</tbody>
																</table>
															</div>
														</div>
													</ul>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<!-- 控件列表 end -->
						<div class="col-md-6">
							<!-- 表单属性 begin  -->
							<div class="panel panel-primary" style="margin:10px 10px 0 0;">
								<div class="panel-heading">
									<div class="row">
										<h5 class="col-md-11">表单属性</h5>
										<a href="javascript:;" data-toggle="collapse" data-target="#formDetial"
										class="fa fa-chevron-up fa-lg togglechevron" title="显示隐藏表单属性" style="margin-top:10px;"></a>
									</div>
								</div>
								<div class="panel-body">
									<div id="formDetial">
										<table class="table no-border">
											<tbody>
												<tr>
													<td align="right">
														<label style="margin-top:8px;">表单标题：</label>
													</td>
													<td>
														<input class="form-control initial field-home " ht-validate="{required:true}" type="text" ng-model="form.name" />
													</td>
												</tr>
												<tr>
													<td align="right">
														<label style="margin-top:8px;">表单Key：</label>
													</td>
													<td>
														<input class="form-control initial field-home" ht-validate="{required:true,varirule:true}" type="text" ng-model="form.key" />
													</td>
												</tr>
												<tr>
													<td align="right">
														<label style="margin-top:8px;">表单说明：</label>
													</td>
													<td>
														<textarea class="form-control initial" rows="3" cols="50" ng-model="form.desc"></textarea>
													</td>
												</tr>
												<tr>
													<td align="right">
														<label style="margin-top:8px;">表单联动：</label>
													</td>
													<td>
														<button class="btn btn-primary btn-sm fa-add" ng-click="openGanged()">添加联动</button>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<!-- 表单属性 end  -->
							<!-- 控件属性 begin  -->
							<div class="panel panel-primary" ng-if="editingField.fieldType=='field'" style="margin:10px 10px 0 0;">
								<div class="panel-heading">
									<h5>字段属性（{{editingField.desc}}）</h5>
								</div>
								<div class="panel-body" style="overflow-y:auto;" ng-style="{'height':fieldProptyHeight}">
									<div id="fieldParams">
										<div class="panel panel-default" style="margin:5px;">
											<div class="panel-heading">基础属性</div>
											<div class="panel-body" style="padding: 5px;">
												<div class="row" style="padding: 5px;" ng-if="editingField.path.indexOf('.sub_')<0">
													<div class="col-md-*">
														<button class="btn btn-primary btn-sm fa-edit" title="将所选字段归类为一个组，统一进行联动处理" style="margin-left: 15px;" ng-click="openSeparator()">编辑分组</button>
														<span>
															<select class="form-control initial w70  field-home" ng-model="editingField.separator">
																<option value="{{m.key}}" ng-selected="editingField.separator == m.key"  ng-repeat="m in separators">{{m.name}}</option>
															</select>
														</span>
													</div>
													
												</div>
												
												<div ng-if="editingField.ctrlType=='multitext'" class="row" style="padding: 5px;">
													<div class="col-md-2">
														<input ng-model="editingField.isEditor" type="checkbox" class="radio-hide" id="__isEditor" />
														<label for="__isEditor" class="btn label-sm">富文本 </label>
													</div>
													<div class="col-md-4">
														<div class="input-group" ng-if="editingField.isEditor">
															<span class="input-group-addon">高</span>
															<input ht-validate="{'posinteger':true}" ng-model="editingField.option.initialFrameHeight" size="4" style="height:29px;"
															class="form-control" type="text" ng-init="editingField.option.initialFrameHeight?'':editingField.option.initialFrameHeight=150"/>
															<span class="input-group-addon">px</span>
														</div>
													</div>
													<div class="col-md-4 col-md-offset-1">
														<div class="input-group" ng-if="editingField.isEditor">
															<span class="input-group-addon">宽</span>
															<input ht-validate="{'posinteger':true}" ng-model="editingField.option.initialFrameWidth" size="4" style="height:29px;"
															class="form-control" type="text" ng-init="editingField.option.initialFrameWidth?'':editingField.option.initialFrameWidth=500"/>
															<span class="input-group-addon">px</span>
														</div>
													</div>
													
												</div>
												
											</div>
										</div>
										
										
										<div class="panel panel-default" ng-show="editingField.ctrlType=='officeplugin'" style="margin:5px;">
											<div class="panel-heading">office控件配置</div>
											<div class="panel-body" style="padding: 5px;">
											
												<div style="margin:10px 0;" class="row">
													<div class="col-md-5">
														<label style="margin-top:5px;">指定文件类型:</label>
														<select ng-model="editingField.option.doctype">
															<option value="">请选择</option>
															<option value="xls">EXCEL</option>
															<option value="ppt">PowerPoint</option>
															<option value="doc">WORD</option>
														</select>
													</div>
													
												</div>
													
														
												<div class="row" style="margin: 7px 0 10px -8px;">
													<div class="col-md-4">
														<div class="input-group">
															<span class="input-group-addon">高</span>
															<input ht-validate="{'posinteger':true}" ng-model="editingField.option.height" size="4" style="height:29px;"
															class="form-control" type="text" ng-init="editingField.option.height?'':editingField.option.height=600"/>
															<span class="input-group-addon">px</span>
														</div>
													</div>
													<div class="col-md-4 col-md-offset-1">
														<div class="input-group">
															<span class="input-group-addon">宽</span>
															<input ht-validate="{'posinteger':true}" ng-model="editingField.option.width" size="4" style="height:29px;"
															class="form-control" type="text" ng-init="editingField.option.width?'':editingField.option.width=800"/>
															<span class="input-group-addon">px</span>
														</div>
													</div>
												</div>
											</div>
										</div>
										
										<div ng-show="editingField&&editingField.isEditing">
											<!-- 下拉复选单选  begin -->
											<div class="panel panel-default" ng-show="isShowDIVByRule('select,checkbox,radio,multiselect')" style="margin:5px;">
												<div class="panel-heading">
													<div class="row">
														<h5 class="col-md-7">
														<span ng-show="editingField.ctrlType=='radio'">单选按钮配置</span>
														<span ng-show="editingField.ctrlType=='checkbox'">复选框配置</span>
														<span ng-show="editingField.ctrlType!='radio' && editingField.ctrlType!='checkbox'">下拉框配置</span>
														</h5>
														<div class="col-md-5" style="margin-top:9px;" ng-show="isShowDIVByRule('select,multiselect')">
															<label class="radio-inline">
																<input type="radio" name="choiceType" ng-model="editingField.option.choiceType" value="static">
																固定选项
															</label>
															<label class="radio-inline">
																<input type="radio" name="choiceType" ng-model="editingField.option.choiceType" value="dynamic">
																动态选项
															</label>
														</div>
													</div>
												</div>
												<div class="panel-body" style="padding: 5px;">
													<!-- static -->
													<div ng-show="editingField.option.choiceType=='static'">
														<table class="table">
															<thead>
																<tr>
																	<th>操作</th>
																	<th>值</th>
																	<th>标签</th>	
																</tr>
															</thead>
															<tbody>
																<tr ng-repeat="opt in editingField.option.choice">
																	<td>
																		<div ng-class="{'btn-group':!$first}" style="margin-top:4px;">
																			<a class="btn btn-default fa fa-add" title="添加" ng-click="addOption()"></a>
																			<a class="btn btn-default fa fa-delete" ng-hide="$first" title="移除" ng-click="removeOption(opt)"></a>
																		</div>
																	</td>
																	<td>
																		<input class="form-control initial field-home" ng-if="editingField.type!='number'" style="width: 88%;" type="text" ng-model="opt.key" ht-validate="{required:true,maxlength:{{editingField.attrLength?editingField.attrLength:30}}}" />
																		<input class="form-control initial field-home" ng-if="editingField.type=='number'" style="width: 88%;" type="text" ng-model="opt.key" ht-validate="{number:true,required:true,maxIntLen:{{editingField.attrLength?editingField.attrLength:10}},maxDecimalLen:{{editingField.decimalLen?editingField.decimalLen:2}}}" />
																	</td>
																	<td>
																		<input class="form-control initial field-home" style="width: 88%;" type="text" ng-model="opt.value" ht-validate="{required:true,maxlength:30}" />
																	</td>
																</tr>
															</tbody>
														</table>
													</div>
													<!-- dynamic -->
													<div ng-show="editingField.option.choiceType=='dynamic'">
														<ul class="margin-10">
															<li>
																<label>自定义查询</label>
																<select ng-model="editingField.option.customQuery.alias" ht-select-ajax="{url:'${ctx}/form/customQuery/listJson',field:'customQuerys',dataRoot:'rows'}" ng-change="setCurrentCustomQuery()" class="form-control initial w70  field-home">
																	<option value="">请选择</option>
																	<option value="{{m.alias}}" ng-repeat="m in customQuerys">{{m.name}}</option>
																</select>
															</li>
															<li ng-if="editingField.option.customQuery.alias">
																<div class="margin-10">
																	<abbr ht-tip title="所绑定的自定义查询返回的数据回填时如何绑定">
																		<strong>值回填</strong>
																	</abbr>
																	<span style="margin-left: 18px;">值</span>
																	<select class="form-control w30 initial field-home" ng-model="editingField.option.customQuery.valueBind" ht-validate="{required:true}">
																		<option ng-repeat="field in editingField.option.customQuery.resultfield" ng-selected="editingField.option.customQuery.valueBind==field.comment" value="{{field.comment}}">{{field.comment}}</option>
																	</select>
																	<span>标签</span>
																	<select class="form-control w30 initial field-home" ng-model="editingField.option.customQuery.labelBind" ht-validate="{required:true}">
																		<option ng-repeat="field in editingField.option.customQuery.resultfield" ng-selected="editingField.option.customQuery.labelBind==field.comment" value="{{field.comment}}">{{field.comment}}</option>
																	</select>
																</div>
																<div class="margin-10">
																	<abbr ht-tip title="自定义查询需要的参数传入">
																		<strong>参数绑定</strong>
																	</abbr>
																	<div ng-repeat="s in editingField.option.bind" style="margin-top:5px;">
																		<div class="input-group">
																			<span class="input-group-addon">{{s.comment}}</span>
																			<span class="form-control inline-block w70" ng-click="!s.hasRequired&&focusBind($event)" style="padding: 0; width:360px!important; height: 40px; -webkit-appearance: textfield;">
																				<span class="span-user owner-span hover-pointer" ng-show="s.json.desc">
																					<span ng-bind="s.json.desc"></span>
																					<a class="fa fa-remove floatTools" ng-click="s.json = null" ng-if="!s.hasRequired"></a>
																				</span>
																				<input type="text" class="hide" ng-model="s.json"></input>
																			</span>
																		</div>
																	</div>
																</div>
															</li>
														</ul>
													</div>
												</div>
											</div>
										</div>
										<!-- 下拉复选单选  end -->
										
										<!-- 日期选项  begin -->
										<div class="panel panel-default" ng-show="isShowDIVByRule('date')" style="margin:5px;">
											<div class="panel-heading">日期选项</div>
											<div class="panel-body" style="padding: 5px 14px;">
												<div class="form-group">
													<label>日期格式:</label>
													<br />
													<select ng-model="editingField.option.dataFormat" class="form-control initial w-inherit" ng-disabled="editingField.option.unmodifiable">
														<option value="">请选择格式类型</option>
														<option value="yyyy-MM-dd">yyyy-MM-dd</option>
														<option value="yyyy-MM-dd HH:mm:ss">yyyy-MM-dd HH:mm:ss</option>
														<option value="HH:mm:ss">HH:mm:ss</option>
														</select><span ng-if="editingField.option.unmodifiable">日期格式类型可在bo字段定义中修改</span>
														<span><input class="radio-hide" ng-model="editingField.option.showCurrentDate" 
														type="checkbox" name="selectorShowCurrentUserName" vlaue="false" id="showCurrentDate" />
															<label for="showCurrentDate" style="margin-bottom:5px;" class="btn label-sm">显示当前时间</label>
													   </span>
												</div>
												<div class="form-group">
													<label>日期校验:</label>
													<div class="field-div">
														<label style="margin-top: 3px; cursor: default;">
															<input ng-model="editingField.daterangestart" type="checkbox" class="radio-hide" id="__daterangestart" ng-change="delAttr('editingField.validRule.daterangestart')"/>
															<label for="__daterangestart" style="margin-top:-6px;" class="btn label-sm">大于等于</label>
															<select ng-model="editingField.validRule.daterangestart.target" class="form-control initial w-inherit"  ng-disabled="!editingField.daterangestart"
																ng-options="a.dateName as a.desc for a in getDateTargets(editingField)">
																<option value="">请选择</option>
															</select>
														</label>
														<label style="margin-top: 3px; cursor: default;">
															<input ng-model="editingField.datemorethan" type="checkbox" class="radio-hide" id="__datemorethan" ng-change="delAttr('editingField.validRule.datemorethan')"/>
															<label for="__datemorethan" style="margin-top:-6px;" class="btn label-sm">大于 </label>
															<select ng-model="editingField.validRule.datemorethan.target" class="form-control initial w-inherit"  ng-disabled="!editingField.datemorethan"
																ng-options="a.dateName as a.desc for a in getDateTargets(editingField)">
																<option value="">请选择</option>
															</select>
														</label>
														<label style="margin-top: 3px; cursor: default;">
															<input ng-model="editingField.daterangeend" type="checkbox" class="radio-hide" id="__daterangeend" ng-change="delAttr('editingField.validRule.daterangeend')"/>
															<label for="__daterangeend" style="margin-top:-6px;" class="btn label-sm">小于等于 </label>
															<select ng-model="editingField.validRule.daterangeend.target" class="form-control initial w-inherit"  ng-disabled="!editingField.daterangeend"
																ng-options="a.dateName as a.desc for a in getDateTargets(editingField)">
																<option value="">请选择</option>
															</select>
														</label>
														<label style="margin-top: 3px; cursor: default;">
															<input ng-model="editingField.datelessthan" type="checkbox" class="radio-hide" id="__datelessthan" ng-change="delAttr('editingField.validRule.datelessthan')"/>
															<label for="__datelessthan" style="margin-top:-6px;" class="btn label-sm">小于 </label>
															<select ng-model="editingField.validRule.datelessthan.target" class="form-control initial w-inherit"  ng-disabled="!editingField.datelessthan"
																ng-options="a.dateName as a.desc for a in getDateTargets(editingField)">
																<option value="">请选择</option>
															</select>
														</label>
													</div>
												</div>
											</div>
										</div>
										<!-- 日期选项  end -->
										<!-- 文件上传  begin -->
										<div class="panel panel-default" ng-show="isShowDIVByRule('fileupload')" style="margin:5px;">
											<div class="panel-heading">文件上传设置</div>
											<div class="row" style="padding: 5px;">
												<div class="panel-body" style="padding: 5px;">
													<div class="col-md-3">
														<input class="radio-hide" ng-model="editingField.option.file.isSingle" type="checkbox" name="upIsSingle" vlaue="false" id="upIsSingle" />
														<label for="upIsSingle" class="btn label-sm">是否单选</label>
													</div>
												</div>
											</div>
											<div class="row" style="padding: 5px;" ng-hide="true">
												<div class="panel-body" style="padding: 5px;">
													<div class="col-md-3">
														<input class="radio-hide" ng-model="editingField.option.file.isSizeLimit" type="checkbox" name="upIsSizeLimit" vlaue="false" id="upIsSizeLimit" />
														<label for="upIsSizeLimit" class="btn label-sm">限制大小(M)</label>
													</div>
													
													<div class="col-md-4">
														<div ng-if="editingField.option.file.isSizeLimit">
															<input class="form-control" type="text" style="width: 85%;" ng-model="editingField.option.file.size"  ht-validate="{'minvalue':0,'number':true}" />
														</div>
													</div>
												</div>
											</div>
											<div class="row" style="padding: 5px;" ng-hide="true">
												<div class="panel-body" style="padding: 5px;">
													<div class="col-md-3">
														<input class="radio-hide" ng-model="editingField.option.file.isFormatLimit" type="checkbox" name="upIsFormatLimit" vlaue="false" id="upIsFormatLimit" />
														<label for="upIsFormatLimit" class="btn label-sm">文件格式</label>
													</div>
													
													<div class="col-md-4">
														<div ng-if="editingField.option.file.isFormatLimit">
															<input class="inputText" style="height: 34px;width: 85%;" type="text" ng-model="editingField.option.file.formatLimit" />
															<a href="javascript:;" style="text-decoration: none;" title="请输入文件后缀名，多个用|隔开，如：jpg|png" class="fa fa-exclamation-circle" ht-tip>　</a>
														</div>
													</div>
												</div>
											</div>
										</div>
										<!-- 文件上传  end -->
										<!-- 数据字典设置  begin -->
										<div class="panel panel-default" ng-show="isShowDIVByRule('office,dic,websign')" style="margin:5px;">
											<div class="panel-heading">数据字典设置</div>
											<div class="panel-body" style="padding: 5px;">
												<div ng-show="isShowDIVByRule('dic')" class="row" style="padding: 10px;">
													<label class="col-md-3 text-right" style="padding-top: 6px;">选择数据字典 : </label>
													<input class="inputText" ht-category="DIC" ng-model="editingField.option.dic" />
												</div>
												<div ng-show="isShowDIVByRule('dic')" class="row" style="padding: 10px;">
													<label class="col-md-3 text-right" style="padding-top: 18px;">
														<abbr ht-tip title="所选择的数据字典的名称绑定到哪个字段">
															<strong>名称：</strong>
														</abbr>
													</label>
													
													<span class="form-control inline-block w40" ng-click="focusBind($event)" style="padding: 0; width:360px!important; height: 40px; -webkit-appearance: textfield;">
														<span class="span-user owner-span hover-pointer"  ng-show="editingField.bindDicName.desc">
															<span ng-bind="editingField.bindDicName.desc"   ></span>
															<a class="fa fa-remove floatTools" ng-click="editingField.bindDicName = null" ></a>
														</span>
														<input type="text" class="hide" ng-model="s.json"></input>
													</span>
												</div>
											</div>
										</div>
										<!-- 数据字典   end -->
										<!-- 选择器设置  begin -->
										<div class="panel panel-default" ng-show="isShowDIVByRule('selector,customdialog')" style="margin:5px;">
											<div class="panel-heading">选择器设置</div>
											<div class="panel-body" style="padding: 5px;">
												<table class="table no-border">
													<tbody>
														<tr ng-if="isShowDIVByRule('selector')">
															<td align="right" width="100"><label style="margin-top:9px;">选择器类型：</label></td>
															<td>
																<select ng-init="editingField.option.selector.type.type='selectors'" ng-change="changeFieldBind()"
																	ht-select-ajax="{url:'${ctx}/form/selectorDef/listJson',field:'selectors',dataRoot:'rows'}"
																	ng-options="(m.alias) as m.name for m in selectors"
																	class="form-control initial field-home" ng-model="editingField.option.selector.type.alias">
																	<option value="">请选择</option>
																</select>
															</td>
															<td>
																<span>
																	<input class="radio-hide" ng-model="editingField.option.selector.isSingle" type="checkbox" name="selectorIsSingle" vlaue="false" id="selectorIsSingle" />
																	<label for="selectorIsSingle" style="margin-bottom:5px;" class="btn label-sm">单选</label>
																</span>
																<span ng-if="'UserDialog'==editingField.option.selector.type.alias" >
																	<input class="radio-hide" ng-model="editingField.option.selector.showCurrentUserName" type="checkbox" name="selectorShowCurrentUserName" vlaue="false" id="selectorShowCurrentUserName" />
																	<label for="selectorShowCurrentUserName" style="margin-bottom:5px;" class="btn label-sm">显示当前用户名</label>
																</span>
																<span ng-if="'OrgDialog'==editingField.option.selector.type.alias" >
																	<input class="radio-hide" ng-model="editingField.option.selector.showCurrentUserDeptName" type="checkbox" name="selectorShowCurrentUserDeptName" vlaue="false" id="showCurrentUserDeptName" />
																	<label for="showCurrentUserDeptName" style="margin-bottom:5px;" class="btn label-sm">显示当前用户的主组织</label>
																</span>
																<span ng-if="'PostDialog'==editingField.option.selector.type.alias" >
																	<input class="radio-hide" ng-model="editingField.option.selector.showCurrentUserPostName" type="checkbox" name="selectorShowCurrentUserPostName" vlaue="false" id="showCurrentUserPostName" />
																	<label for="showCurrentUserPostName" style="margin-bottom:5px;" class="btn label-sm">显示当前用户的主岗位</label>
																</span>
															</td>
														</tr>
														<tr ng-repeat="s in editingField.option.bind"
															ng-show="isShowDIVByRule('selector,customdialog')||(isShowDIVByRule('customdialog')&&!objectIsEmpty(editingField.option.bind))">
															<td align="right">
																<label style="margin-top:20px;">{{s.value}}：</label>
															</td>
															<td>
																<span class="form-control inline-block" ng-click="!s.hasRequired&&focusBind($event)" style="padding: 0; height: 40px; margin-top: 10px; -webkit-appearance: textfield;">
																	<span class="span-user owner-span hover-pointer" ng-show="s.json.desc">
																		<span ng-bind="s.json.desc"></span>
																		<a class="fa fa-remove floatTools" ng-click="s.json = null" ng-if="!s.hasRequired"></a>
																	</span>
																	<input type="text" class="hide" ng-model="s.json"></input>
																</span>
															</td>
															<td></td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
										<!-- 选择器设置  end -->
										<!-- 流水号设置  begin -->
										<div class="panel panel-default" ng-show="isShowDIVByRule('identity')" style="margin:5px;">
											<div class="panel-heading">流水号设置</div>
											<div class="panel-body" style="padding: 5px;">
												<label style="margin:10px;">流水号列表：</label>
												<select ht-select-ajax="{url:'${ctx}/system/identity/listJson',field:'identitys',dataRoot:'rows'}" class="form-control initial w70  field-home" ng-model="editingField.option.identity.alias">
													<option value="">请选择</option>
													<option value="{{m.alias}}" ng-repeat="m in identitys">{{m.name}}</option>
												</select>
											</div>
										</div>
										<!-- 选择器设置  end -->
										<!-- 数字格式化显示  begin -->
										<div class="panel panel-default" ng-show="editingField.type=='number'&&editingField.ctrlType=='onetext'" style="margin:5px;">
											<div class="panel-heading">数字格式化设置</div>
											<div class="panel-body" style="padding: 10px;">
												<div class="row">
													<div class="col-md-3">
														<span>
															<input class="radio-hide" ng-model="editingField.option.numberFormat.isShowComdify"
															ng-disabled="editingField.option.numberFormat.isShowCoin"
															type="checkbox" name="isShowComdify" vlaue="false" id="isShowComdify" />
															<label for="isShowComdify" style="margin-bottom:5px;" class="btn label-sm">千分位</label>
														</span>
													</div>
													<div class="col-md-4">
														<span>
															<input class="radio-hide" ng-model="editingField.option.numberFormat.isShowCoin"
															type="checkbox" name="isShowCoin" vlaue="false" id="isShowCoin" />
															<label for="isShowCoin" style="margin-bottom:5px;" class="btn label-sm">显示为货币</label>
														</span>
													</div>
													<div class="col-md-5">
														<span ng-show="editingField.option.numberFormat.isShowCoin">
															<span id="spanCoinType">
																<select class="form-control initial" style="width: 130px;" id="CoinType" ng-model="editingField.option.numberFormat.coinValue">
																	<option value="">请选择</option>
																	<option value="￥">人民币(￥)</option>
																	<option value="$">美元($)</option>
																</select>
															</span>
														</span>
													</div>
												</div>
												<div class="row" style="margin-top:10px;">
													<div class="col-md-3">
														<span>
															<input class="radio-hide" ng-model="editingField.option.numberFormat.capital"
															type="checkbox" name="isCapital" vlaue="false" id="isCapital" />
															<label for="isCapital" style="margin-bottom:5px;" class="btn label-sm">金额大写</label>
														</span>
													</div>
													<div class="col-md-4">
														<div class="input-group">
															<span class="input-group-addon">整数位</span>
															<input ht-validate="{'posinteger':true}" class="form-control" type="text" id="intLen" ng-model="editingField.option.numberFormat.intValue" ng-disabled="editingField.option.numberFormat.isShowComdify"   size="4" />
														</div>
													</div>
													<div class="col-md-4">
														<div class="input-group">
															<span class="input-group-addon">小数位</span>
															<input ht-validate="{'posinteger':true}" class="form-control" type="text" id="decimalLen" ng-model="editingField.option.numberFormat.decimalValue" ng-disabled="editingField.option.numberFormat.isShowComdify" />
														</div>
													</div>
												</div>
												<div class="row" style="margin-top:10px;">
													<div class="col-md-6">
														<span>
															<input ng-model="editingField.numberBigger" type="checkbox" class="radio-hide" id="__numberBigger" ng-change="delAttr('editingField.validRule.minvalue')"/>
															<label for="__numberBigger" style="margin-top:-4px;" class="btn label-sm">大于等于</label>
														</span>
														<input ht-validate="{'number':true}" class="form-control initial" type="text" ng-disabled="!editingField.numberBigger" ng-model="editingField.validRule.minvalue" size="4" style="width: 80px" />
													</div>
													<div class="col-md-6">
														<span>
															<input ng-model="editingField.numberLess" type="checkbox" class="radio-hide" id="__numberLess" ng-change="delAttr('editingField.validRule.maxvalue')"/>
															<label for="__numberLess" style="margin-top:-4px;" class="btn label-sm">小于等于 </label>
														</span>
														<input ht-validate="{'number':true}" class="form-control initial" type="text" ng-disabled="!editingField.numberLess"  ng-model="editingField.validRule.maxvalue" size="4" style="width: 80px" />
													</div>
												</div>
											</div>
										</div>
										<!-- 数字格式化显示  end -->
										<!-- 字段验证  -->
										<div class="panel panel-default" style="margin:5px;">
											<div class="panel-heading">字段验证</div>
											<div class="panel-body" style="padding: 5px;">
												<table class="table">
													<tbody>
														<tr>
															<td>
																<select class="form-control initial" ng-model="commonRule" ng-change="selectCommonRule(commonRule);">
																	<option value="">常用验证</option>
																	<option ng-repeat="m in common_ruleList" value="{{m.text}}" title="{{m.msg}}">{{m.name}}</option>
																</select>
															</td>
															<td>
																<span>
																	<input ng-model="editingField.validRule.required" ng-disabled="editingField.required" type="checkbox" class="radio-hide" id="__requiredField" />
																	<label for="__requiredField" class="btn label-sm"  ng-disabled="editingField.required" >必填 </label>
																</span>
															</td>
														</tr>
														<tr ng-repeat="rule in editingField.validRule.rules">
															<td align="center">
																<span style="line-height:25px;font-weight:bold;" ng-class="{'no-click':rule.isDefault}">{{rule.name}}</span>
															</td>
															<td>
																<a class="btn btn-danger fa fa-delete" title="删除" ng-click="ArrayTool.del($index,editingField.validRule.rules)"></a>
															</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
										<!-- 字段验证  end -->
										<!-- 函数统计  start-->
										<div class="panel panel-default" ng-show="isShowDIVByRule('onetext,multitext,hidtext')" style="margin:5px;">
											<div class="panel-heading">
												<div class="row">
													<h5 class="col-md-11">统计函数</h5>
													<a href="javascript:;" ng-click="statFun();"
													class="fa fa-edit fa-lg" title="函数统计" style="margin-top:10px;"></a>
												</div>
											</div>
											<div class="panel-body" style="padding: 5px;">
												<table class="table">
													<tbody>
														<tr ng-if="editingField.option.statFun">
															<td align="center">
																<label style="line-height:25px;font-weight:bold;">{{editingField.option.statFun}}</label>
															</td>
															<td>
																<a class="btn btn-danger fa fa-delete" title="删除" ng-click="delAttr('editingField.option.statFun');"></a>
															</td>
														</tr>
														<tr ng-if="!editingField.option.statFun">
															<td colspan="2" align="center">
																<label style="line-height:25px;font-weight:bold;">未设置统计函数</label>
															</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
										<!-- 函数统计 end -->
										<!-- 日期计算 start -->
										<div class="panel panel-default" ng-show="isShowDIVByRule('onetext,multitext,hidtext')" style="margin:5px;">
											<div class="panel-heading">
												<input type="checkbox" ng-model="editingField.datecalc" class="radio-hide" id="__datecalc" ng-change="delAttr('editingField.option.datecalc')"/>
												<label for="__datecalc" class="btn label-sm">日期计算</label>
											</div>
											<div class="panel-body" style="padding: 5px;">
												<table class="table no-border">
													<tbody ng-if="editingField.datecalc">
														<tr>
															<td align="right">
																<label style="margin-top:8px;">开始日期：</label>
															</td>
															<td colspan="2">
																<select ht-validate="{'required':true}" ng-model="editingField.option.datecalc.start" class="form-control initial w-inherit"  ng-disabled="!editingField.datecalc"
																	ng-options="(a.path+'.'+a.name) as a.desc for a in getDateTargets(editingField)">
																	<option value="">请选择</option>
																</select>
															</td>
															<td align="right">
																<label style="margin-top:8px;">结束日期：</label>
															</td>
															<td colspan="2">
																<select ht-validate="{'required':true}" ng-model="editingField.option.datecalc.end" class="form-control initial w-inherit"  ng-disabled="!editingField.datecalc"
																	ng-options="(a.path+'.'+a.name) as a.desc for a in getDateTargets(editingField)">
																	<option value="">请选择</option>
																</select>
															</td>
														</tr>
														<tr>
															<td align="right">
																<label>计算周期：</label>
															</td>
															<td>
																<div ng-model="editingField.option.datecalc.diffType"  ht-validate="{'required':true}" >
																<label class="radio-inline"> <input type="radio" value="month" ng-model="editingField.option.datecalc.diffType"> 月份 </label>
																<label class="radio-inline"> <input type="radio" value="day" ng-model="editingField.option.datecalc.diffType"> 天数 </label>
																<label class="radio-inline"> <input type="radio" value="hour" ng-model="editingField.option.datecalc.diffType"> 小时 </label>
																<label class="radio-inline"> <input type="radio" value="minute" ng-model="editingField.option.datecalc.diffType"> 分钟 </label>
																<label class="radio-inline"> <input type="radio" value="second" ng-model="editingField.option.datecalc.diffType"> 秒数 </label>
																</div>
															</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>