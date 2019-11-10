<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="calendarApp">
<head>
	<%@include file="/commons/include/ngEdit.jsp"%>
	<title>日历设置</title>
	<link rel="stylesheet" href="${ctx}/js/lib/calendar/month_transparent.css">
	<script type="text/javascript" src="${ctx}/js/lib/calendar/daypilot-all.min.js"></script>
	<style type="text/css">
		th.fc-day-header.fc-widget-header{
			cursor: pointer;
		}
		td.fc-active{
			background:#f00;
		}
		div.month_default_header_inner{
			font-weight:bold;
			cursor:pointer;
		}
	</style>
</head>
<body ng-controller="calendarCtrl">
	<div id="tb" class="toolbar-panel">
		<div class="buttons">
			<a href="javascript:;" class="btn btn-primary fa-save btn-sm" ng-click="save();">保存</a>
			<button onclick="HT.window.closeEdit(true,'shiftList');" class="btn btn-primary btn-sm fa-close">
				<span>关闭</span>
			</button>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="panel panel-default">
				<div class="panel-body" style="margin:0 0 10px 0;padding:10px;">
					<div class="row">
						<div class="col-md-3 col-md-offset-3">
							<h3><span ng-bind="dp.startDate.getYear()"></span>年<span ng-bind="dp.startDate.getMonth() + 1"></span>月</h3>
						</div>
						<div class="col-md-2">
							<div class="btn-group" style="margin-top:20px;">
								<button type="button" title="上一个月" class="btn btn-sm btn-default" ng-click="modifyMonth(-1)"><span class="fa fa-backward"></span></button>
								<button type="button" title="下一个月" class="btn btn-sm btn-default" ng-click="modifyMonth(1)"><span class="fa fa-forward"></span></button>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-8">
							<daypilot-month id="dp" daypilot-config="config" daypilot-events="events"></daypilot-month>
						</div>
						<div class="col-md-4" ng-form name="form">
							<div class="form-horizontal" role="form">
								<div class="form-group">
									<label class="col-sm-2 control-label">名称</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" ng-model="calendar.name" ht-validate="{required:true}" placeholder="工作日历名称">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">说明</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" ng-model="calendar.memo" placeholder="工作日历说明">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="btn-group">
										<button ng-repeat="shift in shifts|filter:{isDefault:'1'}" type="button" 
												class="btn btn-primary" ng-bind="shift.name" ng-click="setDay(shift)"></button>
										<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
											<span class="caret"></span> <span class="sr-only">更多操作</span>
										</button>
										<ul class="dropdown-menu" role="menu">
											<li ng-repeat="shift in shifts|filter:{isDefault:'0'}">
												<a href="javascript:;" ng-bind="shift.name" ng-click="setDay(shift)"></a>
											</li>
										</ul>
									</div>
									<div class="btn-group">
										<button type="button" class="btn btn-info" ng-click="setDay('rest')">休息</button>
										<button type="button" class="btn btn-danger" ng-click="setDay('empty')">置空</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</div>
	<script type="text/javascript">
		var id = '${param.id}';
	
		angular.module('calendarApp', ['daypilot','formDirective']).controller(
				"calendarCtrl", [ '$scope','$http', function($scope,$http) {
					$scope.calendar = {};
					$scope.shifts = [];
					$scope.setting = {};
					$scope.selected = null;
					
					var calendarUrl = __ctx + "/calendar/work/detail?id=" + id;
					//获取工作日历基本信息和班次列表					
					$http.get(calendarUrl).success(function(response){
						if(response && response.calendar){
							$scope.calendar = response.calendar;
						}
						if(response && response.shifts){
							$scope.shifts = response.shifts;
						}
						$scope.getSetting();
					});
					
					//获取工作日历设置
					$scope.getSetting = function(){
						if(!id) return;
						var year = $scope.dp.startDate.getYear();
						if($scope.setting.hasOwnProperty(year)) return;
						var url = __ctx + "/calendar/work/calendarSetting?id=" + id + "&year=" + year;
						$http.get(url).success(function(response){
							if(response && response.result){
								$scope.setting[response.year] = true;
								angular.forEach(response.events, function(event){
									var e = new DayPilot.Event({
							            start: DayPilot.Date.fromYearMonthDay(event.startYear, event.startMonth, event.startDay),
							            end: DayPilot.Date.fromYearMonthDay(event.endYear, event.endMonth, event.endDay + 1),
							            id: DayPilot.guid(),
							            shiftId: event.shiftId,
							            text: event.shiftId?$scope.getEventText($scope.getShiftName(event.shiftId)):"休息",
							            moveDisabled:false
							        });
							        $scope.dp.events.add(e);
								});
							}
						});
					}
					
					$scope.getEventText = function(text){
						return '<span style="text-overflow: ellipsis;overflow: hidden;display: inline-block;width: 100%;" title="'+text+'">'+text+'</span>';
					}
					
					$scope.getShiftName = function(shiftId){
						for(var i=0,c;c=$scope.shifts[i++];){
							if(c.id == shiftId){
								return c.name;
							}
						}
					}
					
					$scope.config = {
						startDate : new DayPilot.Date(),
						locale : "zh-cn",
						cellHeight : 80,
						lineSpace:5,
						eventDeleteHandling: "Update",
						onTimeRangeSelect : function(args){
							$scope.selected = args;
						},
						onBeforeCellRender : function(args){
							var month = args.cell.start.getMonth();
							if(month!=$scope.dp.startDate.getMonth()){
								args.cell.headerHtml = '<span style="color:#eee;">'+args.cell.headerHtml+'</span>';
								args.cell.backColor = "#ccc";
							}
						},
						onBeforeEventRender : function(args){
							args.data.deleteDisabled = false;
						},
						onHeaderClick : function(args){
							
						}
					};
					
					$scope.save = function(){
						if(!$scope.form.$valid){
							$.topCall.error("请完善日历信息");
							return;
						}
						var events = [],
							curYear = $scope.dp.startDate.getYear(),
							curMonth = $scope.dp.startDate.getMonth() + 1;
						angular.forEach($scope.dp.events.list,function(t){
							var event = {dateType:2};
							if(t.shiftId){
								event.dateType = 1;
								event.shiftId = t.shiftId;
							}
							event.startYear = t.start.getYear();
							event.endYear = t.end.getYear();
							event.startMonth = t.start.getMonth() + 1;
							event.endMonth = t.end.getMonth() + 1;
							event.startDay = t.start.getDay();
							event.endDay = t.end.getDay()-1;
							events.push(event);
						});
						var url = __ctx + "/calendar/work/save";
						var calendar = {base:$scope.calendar,settingEvents:events};
						$http.post(url,{calendar:calendar})
							 .success(function(response){
								 if(response.result){
									 $.topCall.success(response.message,function(){
										 HT.window.closeEdit(true,'workList');
										 HT.window.refreshParentGrid();
									 });
								 }
								 else{
									 $.topCall.error(response.message);
								 }
							 });
					}

					$scope.modifyMonth = function(step){
						$scope.dp.startDate = $scope.dp.startDate.addMonths(step); 
						$scope.dp.update();
						$scope.getSetting();
					}
					
					$scope.setDay = function(t){
						if(!$scope.selected) return;
						$scope.dp.clearSelection();
						if(typeof t=='string'){
							switch(t){
								case "rest":
									var e = new DayPilot.Event({
							            start: $scope.selected.start,
							            end: $scope.selected.end,
							            id: DayPilot.guid(),
							            text: "休息"
							        });
							        $scope.dp.events.add(e);
							        $scope.dp.message("已设置为:休息");
									break;
								case "empty":
									break;
							}
						}
						else if(typeof t=='object'){
							var e = new DayPilot.Event({
					            start: $scope.selected.start,
					            end: $scope.selected.end,
					            id: DayPilot.guid(),
					            shiftId: t.id,
					            text:$scope.getEventText(t.name)
					        });
					        $scope.dp.events.add(e);
					        $scope.dp.message("已设置为：" + t.name);
						}
						$scope.selected = null;
					}
				}]);
	</script>
</body>
</html>