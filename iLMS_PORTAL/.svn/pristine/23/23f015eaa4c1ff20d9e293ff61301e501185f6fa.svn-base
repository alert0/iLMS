<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/system/customQuery/customQueryService.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/system/customQuery/getController.js"></script>
		<script type="text/javascript">var id ="${param.id}";</script>
	</head>
	<body class="easyui-layout" ng-app="app" ng-controller="Controller">
		<div region="north" style="height: 60px">
			<div id="tb" class="toolbar-panel col-md-13 ">
			    <div class="buttons">
			        <button href="javascript:;" class="btn btn-primary btn-sm fa-search" ng-click="showPart=0;search();"><span>查询</span></button>
			        <button href="javascript:;" class="btn btn-sm btn-info fa-help" ng-click="showPart=(showPart+1)%2"><span>帮助</span></button>
			        <button href="javascript:;" class="btn btn-sm btn-default fa-close" onclick="window.selfDialog.dialog('close');"><span>关闭</span></button>
			    </div>
			</div>
		</div>
		<div region="center" style="overflow-y: scroll;">
			<div ng-if="showPart!=1">
				<table class="table-form" cellspacing="0" style="width: 100%">
					<tr ng-repeat="column in prop.conditionfield" ng-if="column.defaultType=='1'">							
						<th><span>{{column.comment}}:</span></th>
						<td>
							<input ng-if="column.dbType!='date'" type="text" ng-model="column.defaultValue" class="inputText"/>
							<input ng-if="column.dbType=='date'" type="text" validate="{date:true}" 
							class="inputText wdateTime" ng-model="column.defaultValue" name="date" ht-date="wdateTime" />
							<span ng-if="column.dbType=='date'&&column.condition=='BETWEEN'">
							至<input type="text" ng-model="column.endDate"  validate="{date:true}" class="inputText wdateTime" name="date"  ht-date="wdateTime"/></span>
						</td>
					</tr>
				</table>
				<!-- 展示结果 -->
				<div ng-repeat="o in page.rows">{{o}}</div>
			</div>
			<div ng-if="showPart==1">
				<table class="table-form" cellspacing="0" style="width: 100%">
					<tr><th ><span>POST参数:</span></th><td>
						请求地址：/platform/bpm/bpmCustomQuery/doQuery.ht<br/>
						JSON格式的查询条件：var querydata = {{querydata}},<br/>
						最终参数：params = {alias:"{{prop.alias}}",page:1,pagesize:{{prop.pageSize}},querydata:querydata};
					</td></tr>
					<tr><th><span>调用的方法(angularJs的方式):</span></th><td>
						CustomQuery.search(params,function(data){$scope.page=data;});<br/>
						该方法定义在js/hotent/platform/bpm/bpmCustomQuery/CustomQueryService.js中,<br/>
						Controller的写法参考：js/hotent/platform/bpm/bpmCustomQuery/GetController.js
					</td></tr>
					<tr><th><span>回调方法:</span></th><td>返回是一个JSON格式的PageJson(可查看PageJson类);PageJson.rows是返回的查询数据；PageJson.pageResult是页面的信息，里面有totalCount,totalSize等等，详情参考PageResult类</td></tr>
					<tr><th><span>其他说明:</span></th>
						<td>
							提供的调用方法是angularJs的方法，如果您不想使用这个方法，可以自己按照POST参数自己构建请求发送到指定url则可得到回调方法所说的对象<br/>
							注意！！！如果参数是日期的between条件，需要传入两个参数，暂时定了格式：“startDate|endDate”，eg:{"date":"2000-1-1|2011-1-1"}这样的方式来传值;日期格式固定为：yyyy-MM-dd HH:mm:ss
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>