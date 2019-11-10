var mobileDirective = angular.module("mobileDirective", ['base']);

/**
 * @说明 list页面数据加载事件
 * 下拉刷新必须包含 .pull-to-refresh-content
 * 无限加载 必须包含 .infinite-scroll
 * 
 * 页面scope中变量。
 * @scope.listParam ={page:1,rows:10}; list页面加载的参数
 * @scope.dataList 页面列表参数
 * @scope.pageResult 页面列表加载page结果
 */
mobileDirective.directive('listLoad', ['baseService','$sce',function(baseService,$sce) {
	return {
		link : function(scope, element, attr) {
			var postUrl = attr.listLoad;
			var pageSize = $(element).attr("size") || 10;
			var isLoading = false;
			scope.listParam ={page:1,rows:pageSize}; // 页面提交数据的参数，

			//加载数据
			scope.loadData = function(callBack,isAppend){
				if(isLoading)return;
				else isLoading = true;
				$.showIndicator()//$.showPreloader();
				//如果是无限下拉page ++,其他page = 1;
				if(isAppend){
					scope.listParam.page++;
				}else{
					// 如果加载完毕后又刷新，则恢复无限加载
					if(scope.pageResult&&!scope.pageResult.hasNextPage){
						$.attachInfiniteScroll(".infinite-scroll");
					}
					scope.listParam.page=1
					
				}
				
				baseService.postForm(__ctx+postUrl,scope.listParam).then(function(data){
					$.hideIndicator()//$.hidePreloader();
					isLoading = false;
					
					if(data.result==2) return;
					
					if(isAppend){
						for(var i=0,item;item=data.rows[i++];){
							scope.dataList.push(item); 
						}
					}else{
						scope.dataList = data.rows;
					}
					// task.subject to trust
					$.each(data.rows,function(i,row){
						if(row.subject)row.subject = $sce.trustAsHtml(row.subject); //任务的html类型字段，特殊处理
						if(row.taskSubject)row.taskSubject = $sce.trustAsHtml(row.taskSubject); // 我的转办代理
					});
					
					scope.pageResult = data.pageResult;
					
					if(callBack)callBack();
				},function(){
					$.hideIndicator()//$.hidePreloader();
					isLoading = false;
					if(callBack)callBack();
					$.toast("加载失败");
				});
			}
			
			// 下拉刷新
			$.initPullToRefresh(".pull-to-refresh-content");
			$(document).on('refresh', '.pull-to-refresh-content',function(e) {
				scope.loadData(function(){
					 $.pullToRefreshDone('.pull-to-refresh-content');
					$.toast("刷新成功");
				});
			});
			
			// 无线加载
			$.attachInfiniteScroll(".infinite-scroll");
			 $(document).on('infinite', '.infinite-scroll',function(){
				 scope.loadData(function(){
					 if(!scope.pageResult.hasNextPage){
						 $.toast("已经加载所有数据");
						 $.detachInfiniteScroll($('.infinite-scroll'));
			             $('.infinite-scroll-preloader').remove();
					 }
				},true);
			 });
			 
		   scope.loadData();
		}	
	};
}])
.directive('indexPage',['baseService', function(baseService){
    return {
    	restrict : 'AE',
    	templateUrl:__ctx+"/mobile/bpm/bpmIndex.html",
    	replace:true,
        link:function($scope, element,attrs){
        	$scope.pre_url = __ctx+"/mobile/bpm/"
        	$(document).on('refresh', '.pull-to-refresh-content',function(e) {
				$scope.loadUserMsg();
			});
        	
        	$scope.loadUserMsg = function(){
        		baseService.postForm(__ctx+"/mobile/bpm/getUserMsg",{}).then(function(data){
            		$scope.userMsg = data;
            		$scope.userMsg.date = new Date();
            		if($scope.userMsg.user){
            			if(!$scope.userMsg.user.photo){
            				$scope.userMsg.user.photo = __ctx+"/mobile/assets/img/svg/default_avatar.svg";
            			}else{
            				$scope.userMsg.user.photo = __ctx + $scope.userMsg.user.photo;
            			}
            		}
            		
            	})
        	}
        	
        	$scope.loadUserMsg();
        }
    }
}])
//流程实例的状态
.directive('flowStatus',[ function(){
    return {
    	restrict : 'AE',
    	scope:{
    		flowStatus:"="
    	},
    	replace:true,
        link:function(scope, element,attrs){
        	element.after(statusList[scope.flowStatus]);
        	element.remove();
        }
    }
}])
/**
 * 自动添加资源路径的上下文
 */
.directive('htLink',function(){
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			if( "A" == $(element).prop("tagName")){
				$(element).attr("href",__ctx+attrs.htLink)
			}else{
				$(element).attr("src",__ctx+attrs.htLink)
			}
		}
	
	};
})
;
var statusList={
		revoke:			'撤回',
		revokeToStart:	'撤回到发起人',
		draft:			'草稿',
		running:		'运行中',
		end:			'结束',
		manualend:		'人工结束',
		back:			'驳回',
	//taskTurn 的特殊状态
		finish:			'完成',
		cancel:			'取消',
};

mobileDirective.filter("selfFormateDate",function(){
	return function (cDate) {
		if(cDate != undefined){
			var weeks=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"]; 
			var day = cDate.getUTCDate()+"日";
			var month = cDate.getUTCMonth()+1+"月";
			var year = cDate.getUTCFullYear()+"年";
			var week = weeks[cDate.getUTCDay()];
			return year+month+day+"   "+week;  
		}
	}  
})
mobileDirective.filter("countDate",function(){
	return function (date) {
		var currentDate = new Date().getTime();
		date = new Date(date).getTime();
		if(date != undefined){
			var total = (currentDate - date)/1000;
			var day = parseInt(total / (24*60*60));//计算整数天数
			var afterDay = total - day*24*60*60;//取得算出天数后剩余的秒数
			var hour = parseInt(afterDay/(60*60));//计算整数小时数
			var afterHour = total - day*24*60*60 - hour*60*60;//取得算出小时数后剩余的秒数
			var min = parseInt(afterHour/60);//计算整数分
			var afterMin = parseInt(total - day*24*60*60 - hour*60*60 - min*60);//取得算出分后剩余的秒数
			var val = "";
			if(day != 0){
				val += day+"天";
			}
			if(hour != 0){
				val += hour+"小时";
			}
			if(min != 0){
				val += min+"分钟";
				if(afterMin != 0){
					val += afterMin+"秒前";
				}
			}else{
				val += "刚刚";
			}
			return val;
		}
	}  
})
mobileDirective.filter("countEmailDate",function(){
	return function (date) {
		var currentDate = new Date();
		if(date != undefined){
			var cDate = new Date(date);
			var weeks=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"]; 
			var day = cDate.getUTCDate();
			var month = cDate.getUTCMonth()+1;
			var year = cDate.getUTCFullYear();
			var week = weeks[cDate.getUTCDay()];
			var hour = cDate.getUTCHours();
			var min = cDate.getUTCMinutes();
			var curday = currentDate.getUTCDate();
			var curmonth = currentDate.getUTCMonth()+1;
			var curyear = currentDate.getUTCFullYear();
			if(year == curyear && month == curmonth && day == curday){
				return hour +":"+min;
			}
			if(isSameWeek(cDate,currentDate)){
				return week;
			}
			if(year == curyear){
				return month+"-"+day;
			}else{
				return year+"-"+month+"-"+day;
			}
		}
	}  
})
// 判断是否为同一周
function isSameWeek(old,now){  
    var oneDayTime = 1000*60*60*24;  
    var old_count =parseInt(old.getTime()/oneDayTime);  
    var now_other =parseInt(now.getTime()/oneDayTime);  
        return parseInt((old_count+4)/7) == parseInt((now_other+4)/7);  
}  		


mobileDirective.filter("meetingTimes",function(){
	return function (date) {
		if(date != undefined){
			var weeks=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"]; 
			var day = date.getUTCDate();
			var month = date.getUTCMonth()+1;
			var week = weeks[date.getUTCDay()];
			return month+"月"+day+"日"+" "+week;
		}
	}  
})

mobileDirective.filter("appointmentTime",function(){
	return function (date) {
		if(date != undefined){
			date = new Date(date);
			var hour = date.getHours();
			var min = date.getMinutes();
			return hour +":"+min;
		}
	}  
})
mobileDirective.filter("nameAlias",function(){
	return function (name) {
		if(name != undefined && name.length >= 3){
			if(/[\u4e00-\u9fa5]+/g.test(name)){
				name = name.substring(name.length-2);
			}else{
				name = name.substring(name.length-4)
			}
		}
		return name;
	}  
})