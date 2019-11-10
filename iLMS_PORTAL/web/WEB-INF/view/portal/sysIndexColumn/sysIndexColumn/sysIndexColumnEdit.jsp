<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<%@include file="/commons/include/zTree.jsp"%>
		<%@include file="/commons/include/codeMirror.jsp"%>
		<script type="text/javascript" src="${ctx}/js/portal/sysIndexColumn/sysIndexColumn/sysIndexColumnEditController.js"></script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary fa-save" ng-model="data" ht-save="save"><span>保存</span></a>
					<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit(true,'grid');"><span>返回</span></a>
				</div>
			</div>
			<form name="form" method="post" ht-load="getJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th>栏目名称:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.name"   ht-validate="{required:true,maxlength:30}"  />
								</td>
								<th>栏目别名:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.alias"   ht-validate="{required:true,maxlength:30,flowkeyrule:true}"  />
									<a href="#" style="text-decoration: none;" title="别名在系统中不能重复" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
								</td>									
							</tr>
							<tr>		
								<th>栏目分类:</th>
								<td>
									<div type="text" ng-model="data.catalog" ht-dic="data.catalog" url="${ctx}/system/sysType/getTypesByKey?typeKey=INDEX_COLUMN_TYPE" key-name="id"></div>
								</td>	
								<th>栏目类型:</th>
								<td>
									<select class="inputText" ng-model="data.colType">
										<option value="0" ng-selected="!data.colType||data.colType==0">一般类型栏目</option>
										<option value="1">图表类型栏目</option>
										<option value="2">日历类型栏目</option>
										<option value="3">滚动类型栏目</option>
									</select>
									<a href="#" style="text-decoration: none;" title="这个是识别栏目是通过什么类型展示数据。" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
								</td>							
							</tr>
							<tr>
								<th>更多路径:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.colUrl"   ht-validate="{required:false,maxlength:300}"  />
								</td>	
								<th>展示效果:</th>
								<td>
									<select class="inputText" ng-model="data.showEffect">
										<option value="0" ng-selected="!data.showEffect||data.showEffect==0">默认效果</option>
										<option value="1">走马灯</option>
										<option value="2">幻灯片</option>
									</select>
								</td>	
							</tr>
							<tr>								
								<th>数据加载方式:</th>
								<td colspan="3">
									<input type="radio"  ng-model="data.dataMode"  value="0" ng-checked="!data.dataMode||data.dataMode==0" />Service方法
									<input type="radio"  ng-model="data.dataMode"  value="1" />自定义查询方式
									<input type="radio"  ng-model="data.dataMode"  value="2" />webservice方法
									<input type="radio"  ng-model="data.dataMode"  value="3" />RESTful接口
								</td>	
							</tr>
							<tr>
								<th ng-if="!data.dataMode||data.dataMode==0||data.dataMode==2||data.dataMode==3">方法路径:</th>
								<td ng-if="!data.dataMode||data.dataMode==0||data.dataMode==2||data.dataMode==3" colspan="3">
									<div style="width: auto;float: left;">
										<input class="inputText" type="text" ng-model="data.dataFrom"   ht-validate="{required:false,maxlength:300}"  />
									</div>
									<div style="width: auto;float: left;padding-left: 5px;">
										<span class="pull-left btn btn-info btn-sm" ng-click="showSetParamDialog()">参数设置</span>
										<a href="#" style="text-decoration: none;" title="填写格式:1、service+“.”+方法名,service为spring的service名称。如果为webservice方法，格式为：方法路径+“?”+方法名，而且必须添加参数：namespace（名称空间）。（注意：路径中不能接参数，需点击“设置参数”按钮添加参数）" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
									</div>
									<div style="clear: both;"></div>
								</td>
								
								
								<th ng-if="data.dataMode==1">自定义查询: </th>
								<td ng-if="data.dataMode==1" colspan="3">
									<div style="width: auto;float: left;">
										<input class="inputText" type="text" ng-model="data.dataFrom"   ht-validate="{required:false,maxlength:300}"  />
									</div>
									<div style="width: auto;float: left;padding-left: 5px;">
										<span class="pull-left btn btn-info btn-sm" ng-click="selectQuery()">选 择...</span>
									</div>
									<div style="clear: both;"></div>
								</td>		
							</tr>
							<tr>								
								<th>栏目使用平台:</th>
								<td>
									<input type="radio"  ng-model="data.isPublic"  value="0"  ng-checked="!data.isPublic||data.isPublic==0" />PC端
									<input type="radio"  ng-model="data.isPublic"  value="1" />手机端
								</td>	
								<th>栏目高度:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.colHeight"   ht-validate="{required:false,number:true,maxIntLen:10}"  />
									<a href="#" style="text-decoration: none;" title="如果不填写，默认320。" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
								</td>	
							</tr>
							
							<tr>	
								<th>是否支持刷新:</th>
								<td>
									<input type="radio"  ng-model="data.supportRefesh"  value="1" />是
									<input type="radio"  ng-model="data.supportRefesh"  value="0" ng-checked="!data.supportRefesh||data.supportRefesh==0" />否
								</td>								
								<th>刷新时间:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.refeshTime"   ht-validate="{required:false,number:true,maxIntLen:10}"  />
									<a href="#" style="text-decoration: none;" title="时间计算是以秒为单位。如果选择支持刷新，默认刷新时间是5秒。" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
								</td>	
							</tr>
							<tr>								
								<th>描述:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.memo"   ht-validate="{required:false,maxlength:768}"  />
								</td>	
								<th>首页分页:</th>
								<td>
									<input type="radio"  ng-model="data.needPage"  value="0" ng-checked="!data.needPage||data.needPage==0" />不分页
									<input type="radio"  ng-model="data.needPage"  value="1" />分页
								</td>								
							</tr>
							<tr>								
								<th>栏目模版:</th>
								<td colspan="3">
									<div style="max-width:900px;">
										<textarea ui-codemirror ng-model="data.templateHtml_temp"></textarea>
									</div>
								</td>	
							</tr>
							
							
							<!-- <tr style="display: none;">								
								<th>数据别名:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.dsAlias"   ht-validate="{required:false,maxlength:300}"  />
								</td>
								<th>数据源名称:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.dsName"   ht-validate="{required:false,maxlength:300}"  />
								</td>									
							</tr>
							<tr style="display: none;">	
								<th>所属组织ID:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.orgId"   ht-validate="{required:false,number:true,maxIntLen:19}"  />
								</td>								
								<th>数据参数:</th>
								<td>
									<input class="inputText" type="text" ng-model="data.dataParam"   ht-validate="{required:false}"  />
								</td>
																
							</tr> -->
				</table>
				
				
			</form>
		
	</body>
</html>