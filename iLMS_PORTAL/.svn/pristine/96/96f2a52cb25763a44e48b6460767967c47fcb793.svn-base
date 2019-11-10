var hotentUi = angular.module( "ui.hotent", [] );
hotentUi.factory('$transition', ['$q', '$timeout', '$rootScope', function($q, $timeout, $rootScope) {
    var $transition = function(element, trigger, options) {
        options = options || {};
        var deferred = $q.defer();
        var endEventName = $transition[options.animation ? 'animationEndEventName' : 'transitionEndEventName'];
        var transitionEndHandler = function(event) {
            $rootScope.$apply(function() {
                element.unbind(endEventName, transitionEndHandler);
                deferred.resolve(element);
            });
        };
        if (endEventName) {
            element.bind(endEventName, transitionEndHandler);
        }
        $timeout(function() {
            if ( angular.isString(trigger) ) {
                element.addClass(trigger);
            } else if ( angular.isFunction(trigger) ) {
                trigger(element);
            } else if ( angular.isObject(trigger) ) {
                element.css(trigger);
            }
            if ( !endEventName ) {
                deferred.resolve(element);
            }
        });
        deferred.promise.cancel = function() {
            if ( endEventName ) {
                element.unbind(endEventName, transitionEndHandler);
            }
            deferred.reject('Transition cancelled');
        };
        return deferred.promise;
    };
    var transElement = document.createElement('trans');
    var transitionEndEventNames = {
        'WebkitTransition': 'webkitTransitionEnd',
        'MozTransition': 'transitionend',
        'OTransition': 'oTransitionEnd',
        'transition': 'transitionend'
    };
    var animationEndEventNames = {
        'WebkitTransition': 'webkitAnimationEnd',
        'MozTransition': 'animationend',
        'OTransition': 'oAnimationEnd',
        'transition': 'animationend'
    };
    function findEndEventName(endEventNames) {
        for (var name in endEventNames){
            if (transElement.style[name] !== undefined) {
                return endEventNames[name];
            }
        }
    }
    $transition.transitionEndEventName = findEndEventName(transitionEndEventNames);
    $transition.animationEndEventName = findEndEventName(animationEndEventNames);
    return $transition;
}]).directive('collapse', ['$transition', function ($transition) {
    return {
        link: function (scope, element, attrs) {
            var initialAnimSkip = true;
            var currentTransition;
            function doTransition(change) {
                var newTransition = $transition(element, change);
                if (currentTransition) {
                    currentTransition.cancel();
                }
                currentTransition = newTransition;
                newTransition.then(newTransitionDone, newTransitionDone);
                return newTransition;
                function newTransitionDone() {
                    if (currentTransition === newTransition) {
                        currentTransition = undefined;
                    }
                }
            }
            function expand() {
                if (initialAnimSkip) {
                    initialAnimSkip = false;
                    expandDone();
                } else {
                    element.removeClass('collapse').addClass('collapsing');
                    doTransition({ height: element[0].scrollHeight + 'px' }).then(expandDone);
                }
            }
            function expandDone() {
                element.removeClass('collapsing');
                element.addClass('collapse in');
                element.css({height: 'auto'});
            }
            function collapse() {
                if (initialAnimSkip) {
                    initialAnimSkip = false;
                    collapseDone();
                    element.css({height: 0});
                } else {
                    element.css({ height: element[0].scrollHeight + 'px' });
                    var x = element[0].offsetWidth;
                    element.removeClass('collapse in').addClass('collapsing');
                    doTransition({ height: 0 }).then(collapseDone);
                }
            }
            function collapseDone() {
                element.removeClass('collapsing');
                element.addClass('collapse');
            }
            scope.$watch(attrs.collapse, function (shouldCollapse) {
                if (shouldCollapse) {
                    collapse();
                } else {
                    expand();
                }
            });
        }
    };
}])
.constant('accordionConfig', {
    closeOthers: true
})
.controller('AccordionController', ['$scope', '$attrs', 'accordionConfig', function ($scope, $attrs, accordionConfig) {
    this.groups = [];
    this.closeOthers = function (openGroup) {
        var closeOthers = angular.isDefined($attrs.closeOthers) ? $scope.$eval($attrs.closeOthers) : accordionConfig.closeOthers;
        if (closeOthers) {
            angular.forEach(this.groups, function (group) {
                if (group !== openGroup) {
                    group.isOpen = false;
                }
            });
        }
    };
    this.addGroup = function (groupScope) {
        var that = this;
        this.groups.push(groupScope);
        groupScope.$on('$destroy', function (event) {
            that.removeGroup(groupScope);
        });
    };
    this.removeGroup = function (group) {
        var index = this.groups.indexOf(group);
        if (index !== -1) {
            this.groups.splice(index, 1);
        }
    };
}])
.directive('accordion', function () {
    return {
        restrict: 'EA',
        controller: 'AccordionController',
        transclude: true,
        replace: false,
        template: '<div class="panel-group" ng-transclude></div>'
    };
})
.directive('accordionGroup', function () {
    return {
        require: '^accordion',
        restrict: 'EA',
        transclude: true,
        replace: true,
        template: '<div class="panel panel-default">'
                  +'<div class="panel-heading">'
                  +'<h4 class="panel-title">'
                  +'<a href class="accordion-toggle" ng-click="toggleOpen()" accordion-transclude="heading"><span ng-class="{\'text-muted\': isDisabled}">{{heading}}</span></a>'
                  +'</h4>'
                  +'</div>'
                  +'<div class="panel-collapse" collapse="!isOpen">'
                  +'<div class="panel-body" ng-transclude></div>'
                  +'</div>'
                  +'</div>',
        scope: {
            heading: '@',
            isOpen: '=?',
            isDisabled: '=?'
        },
        controller: function () {
            this.setHeading = function (element) {
                this.heading = element;
            };
        },
        link: function (scope, element, attrs, accordionCtrl) {
            accordionCtrl.addGroup(scope);
            scope.$watch('isOpen', function (value) {
                if (value) {
                    accordionCtrl.closeOthers(scope);
                }
            });
            scope.toggleOpen = function () {
                if (!scope.isDisabled) {
                    scope.isOpen = !scope.isOpen;
                }
            };
        }
    };
})
.directive('accordionHeading', function () {
    return {
        restrict: 'EA',
        transclude: true,
        template: '',
        replace: true,
        require: '^accordionGroup',
        link: function (scope, element, attr, accordionGroupCtrl, transclude) {
            accordionGroupCtrl.setHeading(transclude(scope, function () {
            }));
        }
    };
})
.directive('accordionTransclude', function () {
    return {
        require: '^accordionGroup',
        link: function (scope, element, attr, controller) {
            scope.$watch(function () {
                return controller[attr.accordionTransclude];
            }, function (heading) {
                if (heading) {
                    element.html('');
                    element.append(heading);
                }
            });
        }
    };
});