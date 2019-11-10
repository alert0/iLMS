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
		<div class="toolbar-panel"><!--0000 -->
			<div class="buttons" >	      
		       <a class="btn btn-sm btn-primary fa fa-back" onclick="HT.window.closeEdit(true,'bpmDefAuthorizeList')" href="javascript:;">返回</a>
		    </div> 
	    </div>
		<table class="table-form">
				<tr>
					<th width="20%">权限描述:</th>
					<td>
						 {{data.authorizeDesc}}
					</td>
				</tr>
				<tr>
					<th width="20%">授权人员名称:</th>
					<td>
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
									<tr  ng-repeat="item in data.ownerNameJson  track by $index">
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
				
				<tr>
					<th width="20%">授权流程名称:</th>
					<td>							
						<table id="bpmDefActTable"  class="table-list">
							<thead>
								<tr>
									<th style="text-align: center;">流程名称</th>
									<th style="text-align: center;width:80%;">授权内容</th>
								</tr> 
							</thead>
							<tr  ng-if="data.defNameJson.length==0">
								<td colspan="2" style="text-align: center;"> 
									没有授权的流程
								</td>
							</tr>
							<tbody ng-if="data.defNameJson.length>0">
									<tr  ng-repeat="item in data.defNameJson  track by $index">
										<td> 
											{{item.defName}}
										</td>
										<td> 
											 <div name="m_right_div" ng-show="data.authorizeTypes.management">
											      定义权限(
											      	<span ng-if="item.right.m_edit">设计</span>
											      	<span ng-if="item.right.m_del">删除</span>
											      	<span ng-if="item.right.m_start">启动</span>
											      	<span ng-if="item.right.m_set">设置</span>
											      	<span ng-if="item.right.m_clean">清除数据</span>
											      
										         )
										     </div>
										     <div name="i_right_div" ng-show="data.authorizeTypes.instance">
										     	实例权限<span ng-if="item.right.i_del">删除</span>
										     </div>
										</td>
									</tr>
								</tbody>
						</table>
					</td>
				</tr>
		</table>
		
	</body>
</html>