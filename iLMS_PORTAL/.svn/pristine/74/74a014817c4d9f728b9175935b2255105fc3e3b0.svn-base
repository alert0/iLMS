angular.module('component',['ngAnimate','chieffancypants.loadingBar'])
.factory('loadingBarRejector', ['cfpLoadingBar', function(cfpLoadingBar) {  
    var loadingBarRejector = {
        request: function(config) {
        	cfpLoadingBar.start();
            return config;
        },
        response: function(response){
        	//session过期时重新登录
        	if(angular.isString(response.data) && response.data.indexOf('loginCtrl') != -1){
        		window.location.reload();
        	}
        	else{
        		cfpLoadingBar.complete();
        		return response;
        	}
        }
    };
    return loadingBarRejector;
}])
.config(["$httpProvider",function($httpProvider){
	$httpProvider.interceptors.push('loadingBarRejector');
}])
.directive('pagination',['$timeout',function($timeout){
	return {
		restrict: 'EA',
		scope: {
		  totalPages: '=',
		  ngModel:'=',
	      ngChange:'&'
	    },
	    link: function(scope, element, attrs){
	    	scope.$watch("totalPages",function(newVal,oldVal){
	    		if(newVal!==oldVal){
	    			scope.items = [];
	    	    	
	    	    	for(var i=0;i<scope.totalPages;i++){
	    	    		scope.items.push(i+1);
	    	    	}
	    		}
	    	});
	    	
	    	attrs.$observe("totalPages",function(newVal,oldVal){
	    		if(newVal!==oldVal){
	    			scope.items = [];
	    	    	
	    	    	for(var i=0;i<scope.totalPages;i++){
	    	    		scope.items.push(i+1);
	    	    	}
	    		}
	    	});
	    	
	    	scope.change = function(item){
	    		if(scope.ngModel==item)return;
	    		scope.ngModel=item;
	    		if(scope.ngChange&&typeof scope.ngChange=='function'){
	    			$timeout(function(){
		    			scope.ngChange();
		    		},1);
	    		}
	    	}
	    },
	    template:'<nav>\
	    			<ul class="pagination">\
	    				<li ng-class="{disabled:ngModel==1}">\
	    					<a href="javascript:;" ng-click="change(1)"><span>&laquo;</span></a>\
	    				</li>\
	    				<li ng-repeat="item in items" ng-class="{active:item==ngModel}"><a href="javascript:;" ng-click="change(item)" >{{item}}</a></li>\
	    				<li ng-class="{disabled:ngModel==totalPages}">\
                			<a href="javascript:;" ng-click="change(totalPages)"><span>&raquo;</span></a>\
                		</li>\
	    			</ul>\
	    		</nav>',
	    replace: true
	};
}])
.directive('tabs', function() {
    return {
        restrict: 'A',
        transclude: true,
        scope: {},
        controller: function($scope, $element,$rootScope) {
            var panes = $scope.panes = [];

            $scope.select = function(pane) {
                var tabIndex = -1;
                angular.forEach(panes, function(p,key) {
                    if(pane==p){
                        tabIndex = key;
                    }
                    p.selected = false;
                });
                pane.selected = true;
                $rootScope.$broadcast('paneSwitched',tabIndex);
            }

            this.addPane = function(pane) {
                if (panes.length == 0) $scope.select(pane);
                panes.push(pane);
            }
        },
        template: '<div class="tabbable row">\
        			<ul style="padding:0 10px;" class="nav col-md-1 nav-pills nav-stacked">\
        				<li ng-repeat="pane in panes" ng-class="{active:pane.selected}">\
        					<a href="" ng-click="select(pane)">{{pane.title}}</a>\
        				 </li>\
        			 </ul>\
        			 <div class="tab-content col-md-11" ng-transclude></div>\
        		  </div>',
        replace: true
    };
})
.directive('pane', function() {
    return {
        require: '^tabs',
        restrict: 'A',
        transclude: true,
        scope: {
            title: '@'
        },
        link: function(scope, element, attrs, tabsCtrl) {
            tabsCtrl.addPane(scope);
        },
        template: '<div class="tab-pane" ng-class="{active: selected}" ng-transclude>' + '</div>',
        replace: true
    };
})
.directive('paneSrc',function(){
	return {
		restrict:'A',
		link:function(scope,element,attrs){
			scope.$on('paneSwitched',function(event,tabIndex){
				if(attrs['paneSrc']==tabIndex){
					//if(!$(element).attr("src")){
						$(element).attr("src",attrs['preSrc']);
					//}
				}
            });
		}
	};
});