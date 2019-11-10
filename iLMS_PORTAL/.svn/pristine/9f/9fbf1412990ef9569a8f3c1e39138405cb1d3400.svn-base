
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html ng-app="app">
	<head>
		<title>流程分管授权编辑</title>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/system/dialog/bpmDefDialog.js"></script>
        <script type="text/javascript" src="${ctx}/js/platform/flow/defAuthorizeController.js"></script>
        <script type="text/javascript" src="${ctx}/js/platform/util/rightsUtil.js"></script>
	</head>
	<body ng-controller="ctrl" ng-init="load('${param.id}')">
		<div class="toolbar-panel col-md-13"><!--0000 -->
			<div class="buttons" style="margin-left:10px;">	      
		       <a id="saveFormLink" class="btn btn-sm btn-primary fa-save" ng-click="save()">保存</a>
		       <a class="btn btn-sm btn-primary fa-back" onclick="HT.window.closeEdit(true,'grid')" href="javascript:;">返回</a>
		    </div> 
	    </div>
		
		<form name="bpmDefAuthorizeForm" id="bpmDefAuthorizeForm" action="save" method="post">
			<input type="hidden" name="id" ng-model="data.id">
			
			<table class="table-form">
					<tr>
						<th width="20%">权限描述:<span style='color:#f00;display:inline-block'> * </span></th>
						<td>
							<input type="text" name="authorizeDesc" style="width:300px;" class="form-control input-default" ng-model="data.authorizeDesc" ht-validate="{'required':true}" />
						</td>
					</tr>

					<tr>
						<th width="20%">权限类型:</th>
						<td>
							<label class="checkbox-inline"><input type="checkbox" ng-model="data.authorizeTypes.start" />启动</label>
							<label class="checkbox-inline"><input type="checkbox" ng-model="data.authorizeTypes.management" />定义</label>
							<label class="checkbox-inline"><input type="checkbox" ng-model="data.authorizeTypes.task" value="task" />任务 </label>
							<label class="checkbox-inline"><input type="checkbox" ng-model="data.authorizeTypes.instance" value="instance" />实例</label>
						</td>
					</tr>
					<tr>
						<th width="20%">授权人员名称:<span style='color:#f00;display:inline-block'> * </span></th>
						<td>

							<div class="toolbar-panel col-md-13 ">
								<div class="buttons"  style="margin-left:0" >	      
							       <a class="btn btn-sm btn-primary fa fa-add" ng-click="setAuth();">选择</a> 
							   
							    </div>
						    </div>
							
							<table id="bpmDefUserTable"  class="table-list">
									<thead>
										<tr>
											<th width="30%" >权限分类</th>
											<th width="70%" >授权给</th>
										</tr> 
									</thead>
									<tr class="empty-div" ng-if="data.ownerNameJson.length==0">
										<td colspan="2" style="text-align: center;"> 
											没有授权的人员
										</td>
									</tr>
									<tbody ng-if="data.ownerNameJson.length>0">
										
										<tr  ng-repeat="item in data.ownerNameJson track by $index">
											<td> 
												{{item.title}}
											</td>
											<td> 
												{{item.name}}
											</td>
										</tr>
									</tbody>
							</table>
						</td>
					</tr>
					
					<tr class="">
						<th width="20%">授权流程名称:<span style='color:#f00;display:inline-block'> * </span></th>
						<td>							
							
							<div class="toolbar-panel col-md-13 ">
								<div class="buttons" style="margin-left:0" >	      
							       <a class="btn btn-sm btn-primary fa fa-add" ng-click="selectDef()" >选择</a>
							       <a class="btn btn-sm btn-primary fa fa-delete" ng-click="delDef()">删除</a> 
							      
							    </div>
						    </div>
						    
						    <div class="tools clearfix">
								<div id="am_right_div" class="f-l"  ng-show="data.authorizeTypes.management">
									<div class="panel panel-default">
										<div class="panel-body" style="padding:10px;">
											<span class="badge">流程定义:</span>
											<label class="checkbox-inline"><input type="checkbox" ng-model="m_edit" ng-click="checkAll($event,'m_edit')">设计</label>
											<label class="checkbox-inline"><input type="checkbox" ng-model="m_del" ng-click="checkAll($event,'m_del')" >删除</label>
											<label class="checkbox-inline"><input type="checkbox" ng-model="m_start" ng-click="checkAll($event,'m_start')">启动 </label>
											<label class="checkbox-inline"><input type="checkbox" ng-model="m_set" ng-click="checkAll($event,'m_set')">设置</label>
											<label class="checkbox-inline"><input type="checkbox" ng-model="m_clean" ng-click="checkAll($event,'m_clean')" >清除数据</label>
										</div>
									</div>
								</div>
								<div id="ai_right_div" class="f-l"  ng-show="data.authorizeTypes.instance">
									<div class="panel panel-default">
										<div class="panel-body" style="padding:10px;">
											<span class="badge">流程实例:</span>
											<label class="checkbox-inline"><input type="checkbox" ng-model="i_del" ng-click="checkAll($event,'i_del')"> 删除</label>
										</div>
									</div>
								</div>
							</div>

							<div id="defName_div" >
								<table id="bpmDefActTable"  class="table-list">
									<thead>
										<tr>
											<th style="width:30px;text-align: left;"><input type="checkbox" id="all_key" ng-model="selectAll"/></th>
											<th style="text-align: center;">流程名称</th>
											<th style="text-align: center;width:80%;">授权内容</th>
										</tr> 
									</thead>
									<tr  ng-if="data.defNameJson.length==0">
										<td colspan="3" style="text-align: center;"> 
											没有授权的流程
										</td>
									</tr>
									
									<tbody ng-if="data.defNameJson.length>0">
											
											<tr  ng-repeat="item in data.defNameJson  track by $index">
												<td> 
													<input  ng-model="item.selected" type="checkbox" ht-checked="selectAll">
												</td>
												<td> 
													{{item.defName}}
												</td>
												<td> 
													 <div name="m_right_div" ng-show="data.authorizeTypes.management">
													      定义(
														     <label class="checkbox-inline"><input type="checkbox" ng-model="item.right.m_edit" >设计</label>
														     <label class="checkbox-inline"><input type="checkbox" ng-model="item.right.m_del" >删除</label>
														     <label class="checkbox-inline"><input type="checkbox" ng-model="item.right.m_start" >启动  </label>
														     <label class="checkbox-inline"><input type="checkbox" ng-model="item.right.m_set"  >设置</label>
														     <label class="checkbox-inline"><input type="checkbox" ng-model="item.right.m_clean" >清除数据</label>
												         )
												     </div>
												     <div name="i_right_div" ng-show="data.authorizeTypes.instance">
														  实例(<label class="checkbox-inline"><input type="checkbox" ng-model="item.right.i_del" >删除</label>)
												     </div>
												</td>
											</tr>
										</tbody>
								</table>
							</div>

						</td>
					</tr>
			</table>
		</form>
	
	  
	</body>
</html>