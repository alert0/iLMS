<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/innermsg/MessageTypeController.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/innermsg/MessageTypeService.js"></script>
	</head>
	<body  class="easyui-layout" fit="true"   scroll="no">
		<div data-options="region:'center',border:false"  >
			<!-- 顶部查询操作 -->
		    <div  class="toolbar-search col-md-13 ">
				<div class="toolbar-box border">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons"> 		
							<a class="btn btn-primary fa-search"  href="javascript:void(0);" ><span>搜索</span></a>
							<a class="btn btn-primary fa-add"     href="edit.ht" ><span>发送消息</span></a>
					        <a class="btn btn-primary fa-remove"  href="javascript:void(0);"  action="remove.ht"><span>删除</span></a>
						</div>
			 			<div class="tools">
							<a href="javascript:void(0);" class="collapse">
								<i class="bigger-190 fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body" >
						<form  id="searchForm" class="search-form" >
							<ul>
								<li><span>主题:</span><input class="inputText" type="text" name="Q^subject_^SL"></li>
								<li ng-app="msgTypeApp" ng-controller="MessageTypeController"><span>消息类型:</span>
									<select  class="inputText" id="messageType" name="Q^messageType^SL" ng-model="sysMessage.messageType" >
									 	<option value="">全部</option>
										<option ng-repeat="msgType in allMsgType" value="{{msgType.value}}">{{msgType.key}}</option>
									</select>
								</li>
								<li>
									<span>创建时间:</span><input class="inputText datetime" type="text" name="Q^beginreceiveTime^DL">
									<span>至:</span><input class="inputText datetime" type="text" name="Q^endreceiveTime^DG">
								</li>
							</ul>
						</form>
					</div>
			    </div>
			</div>
			<table  class="easyui-datagrid" idField="id"  data-options="fitColumns:false,checkOnSelect:true,pagination:true,toolbar:'.toolbar-search'" fit="true" 
				 url="${ctx}/platform/innermsg/sysMessage/listJson.ht">
			    <thead>
				    <tr>
						<th field="id" checkbox="true" class="pk"></th>
						<th field="subject" sortable="true" sortName="subject_" title="主题" ></th>
						<th field="messageType" sortable="true" sortName="message_type_" title="消息类型" ></th>
						<th field="createTime" sortable="true" sortName="create_time_" title="创建时间" formatter="ht" dateFormat="YYYY-MM-DD HH:mm:ss"></th>
						<th field="receiverName" sortable="false"  title="收信人"></th>
						<th manager="true"  width="40" style="display: none;">	
					    	<f:a alias="sysMessage_detail"  href="getInfo.ht?id={id}&canReply={canReply}"   css="btn btn-default fa-detail" >详细</f:a>
					     	<f:a alias="sysMessage_remove"  href="javascript:void(0);"  action="remove.ht?id={id}"   css="btn btn-default fa-remove" >删除</f:a>
					    </th>
				    </tr>
			    </thead>
		    </table>
		  </div>
	</body>
</html>