angular.module('BpmImageService', [ 'base' ])
.directive('htBpmImage',["baseService",function(BaseService) {
		return {
			restrict : 'A',
			scope : {
				htBpmImage : "=",
			},
			controller : function($scope, $element, $timeout, $compile) {
				$scope.nodeOpinion = {
					opinionTemplate : '<ul style="padding-left:0;"><li ng-repeat="opinion in nodeOpinion.result.data"><div style="margin-bottom:10px;"><table class="table-list">' + '<tr><th width="120">任务名称</th><td width="150">{{opinion.taskName}}</td></tr>' + '<tr ng-show="opinion.auditor"><th>执行人</th><td><span class="owner-span"><a href="#" ng-click="showUserInfo(opinion.auditor)">{{opinion.auditorName}}</a></span></td></tr>' + '<tr ng-show="!opinion.auditor"><th><abbr ht-tip title="有资格审批的用户及用户组">候选人</abbr></th><td><div class="owner-div">' + '<span ng-repeat="qualfied in opinion.qualfieds" class="owner-span"><a href="#" ng-click="showDetail(qualfied)">{{qualfied.name}}</a></span>'
							+ '</div></td></tr><tr><th>开始时间</th><td style="font-size:11px;">{{opinion.createTime | date:"yyyy-MM-dd HH:mm:ss"}}</td></tr>' + '<tr><th>结束时间</th><td style="font-size:11px;">{{opinion.completeTime | date:"yyyy-MM-dd HH:mm:ss"}}</td></tr>' + '<tr><th>审批用时</th><td>{{opinion.durMs | htTime}}</td></tr>' + '<tr><th>状态</th><td>{{opinion.statusVal}}</td></tr>' + '<tr><th>意见</th><td>{{opinion.opinion}}</td></tr>' + '</table></div></li></ul>',
					executorTemplate : '<div class="form-table"><table><tr><th width="120">状态</th><td width="150">未产生过任务</td></tr>' + '<tr><th><abbr ht-tip title="暂定的有审批资格的用户及用户组，实际候选人要在产生任务以后才能确定">暂定候选人</abbr></th><td><div class="owner-div">' + '<span ng-repeat="qualfied in nodeOpinion.result.data" class="owner-span"><a href="#" ng-click="showDetail(qualfied)">{{qualfied.name}}</a></span>' + '</div></td></tr></table></div>'
				};

				// 显示用户信息
				$scope.showUserInfo = function(userId) {
					new UserInfoDialog(userId).show();
				}
		
				// 显示用户及用户组信息
				$scope.showDetail = function(info) {
					if (!info)
						return;
					switch (info.type) {
					case "user":
						new UserInfoDialog(info.id).show();
						break;
					case "org":
						break;
					case "role":
						break;
					case "pos":
						break;
					}
				}

				// 编译模板
				$scope.compileContent = function(api) {
					if (!$scope.nodeOpinion.result)
						return;
					// 审批意见的模板
					if ($scope.nodeOpinion.result.hasOpinion) {
						// 将候选人的信息从字符串转换为json
						angular.forEach($scope.nodeOpinion.result.data, function(item) {
							if (item.qualfieds) {
								item.qualfieds = parseToJson(item.qualfieds);
							}
						});
						$scope.nodeOpinion.content = $compile($scope.nodeOpinion.opinionTemplate)($scope);
					}
					// 未审批任务也未产生任务的节点，显示节点执行人信息
					else {
						api.set('content.title', '节点设置详情');
						$scope.nodeOpinion.content = $compile($scope.nodeOpinion.executorTemplate)($scope);
					}
				}

				// 获取节点的审批详情
				$scope.getContent = function(event, api) {
					$timeout(function() {
						// 判断缓存里是否已经存在该节点的审批详情
						if (!$scope.nodeOpinion.content) {
							var url = __ctx + "/flow/task/nodeOpinion";
							BaseService.postForm(url, {
								instId : $scope.htBpmImage.instId,
								nodeId : $scope.htBpmImage.nodeId
							}).then(function(result) {
								$scope.nodeOpinion.result = result;
								// 获取到审批详情的json数据后与模板编译成html代码
								$scope.compileContent(api);
								api.set('content.text', $scope.nodeOpinion.content);
							});
						} else {
							api.set('content.text', $scope.nodeOpinion.content);
						}
						api.reposition();
					}, 100, false);
					return "正在获取内容";
				}
			},
			link : function(scope, element, attrs) {
				// 只有用户任务和会签任务才显示审批详情
				if (("USERTASK,SIGNTASK").indexOf(scope.htBpmImage.nodeType) == -1)
					return;
				var setting = {
					content : {
						text : scope.getContent,
						title : '任务审批详情'
					},
					hide : {
						event : 'mouseleave',
						leave : false,
						fixed : true,
						delay : 200
					},
					position : {
						viewport : $(window),
						my : 'center left',
						at : 'center right'
					},
					style : {
						classes : 'qtip-default  qtip qtip-bootstrap qtip-shadow'
					}
				};
				// 添加tooltip显示审批详情
				element.qtip(setting);
			}
		};
} ]);