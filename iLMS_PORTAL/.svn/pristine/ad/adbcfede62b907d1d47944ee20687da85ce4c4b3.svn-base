angular.module('formDefModel', ['base'])
.service('formDefService', ['$rootScope','baseService', function($rootScope,baseService) {
    var service = {
    		list : [],
    		controlType : [{id:'onetext',name:'单行文本',supports:['varchar','number']},
    		               {id:'hidtext',name:'隐藏域',supports:['varchar','number']},
    		               {id:'multitext',name:'多行文本',supports:['varchar','clob']},
    		               {id:'select',name:'下拉框',supports:['varchar','number']},
    		               {id:'multiselect',name:'下拉框多选',supports:['varchar']},
    		               {id:'checkbox',name:'复选框',supports:['varchar']},
    		               {id:'radio',name:'单选按钮',supports:['varchar','number']},
    		               {id:'date',name:'日期控件',supports:['date']},
	                       {id:'selector',name:'选择器',supports:['varchar']},
	                       {id:'dic',name:'数据字典',supports:['varchar','number']},
	                       {id:'officeplugin',name:'Office控件',supports:['varchar']},
	                       {id:'fileupload',name:'文件上传',supports:['varchar']}]
	                       ,
	           checkFieldGanged:function(field,ganged){
    			if(ganged){
    				var ganged = parseToJson(ganged);
    				for (var i = 0; i < ganged.length; i++) {
    					var changeField = ganged[i].changeField;
    					var chooseField = ganged[i].chooseField;
    					if(changeField){
    						for (var c = 0; c < changeField.length; c++) {
								if(changeField[c].boAttrId==field.boAttrId&&changeField[c].ctrlType!=field.ctrlType){
									$.topCall.alert('提示信息','您改变了字段【'+field.desc+'】的控件类型，请重新设置联动中的变换字段【'+field.desc+'】');
									return ;
								}
							}
    					}
    					if(chooseField){
    						for (var c = 0; c < chooseField.length; c++) {
								if(chooseField[c].boAttrId==field.boAttrId&&chooseField[c].ctrlType!=field.ctrlType){
									$.topCall.alert('提示信息','您改变了字段【'+field.desc+'】的控件类型，请重新设置联动中的所选字段【'+field.desc+'】');
									return ;
								}
							}
    					}
    				}
    			}
    		}
        }
    
    return service;
}]).directive('tabs', function() {
    return {
        restrict: 'A',
        transclude: true,
        scope: {},
        controller: function($scope, $element) {
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
                $scope.$emit('paneSwitched',tabIndex);
            }

            $scope.$on('switchPane',function(event,tabIndex){
                var curPane;
                angular.forEach(panes, function(pane,key) {
                    pane.selected = false;
                    if(key==tabIndex){
                        curPane = pane;
                    }
                });
                if(!curPane){
                    curPane = panes[0];
                }
                curPane.selected = true;
            });

            this.addPane = function(pane) {
                if (panes.length == 0) $scope.select(pane);
                panes.push(pane);
            }
        },
        template: '<div class="tabbable">' 
        		+ '<ul style="padding:0 10px;" class="nav nav-tabs">' 
        		+ '<li ng-repeat="pane in panes" style="margin-top:10px;" ng-class="{active:pane.selected}">' 
        		+ '<a href="" ng-click="select(pane)">{{pane.title}}</a>' 
        		+ '</li>' 
        		+ '</ul>' 
        		+ '<div class="tab-content" ng-transclude></div>' 
        		+ '</div>',
        replace: true
    };
}).directive('pane', function() {
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
}).directive('fieldTag',['formDefService', function(formDefService) {
    return {
        restrict: 'A',
        scope: {
        	fieldTag:'=',
        	fieldList:'=',
        	fieldParent:"=",
        	arrayTool : "=",
        	index : "=",
        	separators:"=",
        	formGanged:"="
        },
        controller:function($scope, $element){
        	$scope.controlType = formDefService.controlType;
        	$scope.checkFieldGanged = formDefService.checkFieldGanged;
        	$scope.remove = function(fieldTag,formGanged){
        		$scope.fieldList.remove(fieldTag);
        		for(var k = 0 ; k < $scope.fieldList.length ; k++){
        			$scope.fieldList[k].sn = k;
				 }
        		var parentScope=$scope.$parent.$parent.$parent.editingField?$scope.$parent.$parent.$parent:$scope.$parent.$parent.$parent.$parent;
        			parentScope.editingField = null;
    			if($scope.fieldList.length==0&&$scope.fieldParent){
    				parentScope.fields.remove($scope.fieldParent);
    				 for(var k = 0 ; k < parentScope.fields.length ; k++){
    					 parentScope.fields[k].sn = k;
					 }
        		}
        		$scope.removeGanged(fieldTag,formGanged,parentScope);
        		try {if (!$scope.$$phase) $scope.$digest(); } catch (e) {}
        	};
        	$scope.removeGanged = function(field,ganged,scope){
        		if(ganged){
    				var ganged = parseToJson(ganged);
    				var removeChooseIds = [];
    				for (var i = 0; i < ganged.length; i++) {
    					var chooseField = ganged[i].chooseField;
						if(chooseField){
    						for (var c = 0; c < chooseField.length; c++) {
								if(chooseField[c].boAttrId==field.boAttrId){
									removeChooseIds.push(i);
									break;
								}
							}
    					}
    				}
    				for (var i = 0; i < removeChooseIds.length; i++) {
    					removeObjFromArr(ganged,removeChooseIds[i]);
    				}
    				removeChooseIds = [];
    				for (var i = 0; i < ganged.length; i++) {
    					var changeField = ganged[i].changeField;
    					if(changeField){
    						for (var c = 0; c < changeField.length; c++) {
								if(changeField[c].boAttrId==field.boAttrId){
									removeObjFromArr(changeField,c);
									if(changeField.length==0){
										removeChooseIds.push(i);
									}
									break;
								}
							}
    					}
    				}
    				for (var i = 0; i < removeChooseIds.length; i++) {
    					removeObjFromArr(ganged,removeChooseIds[i]);
    				}
    				scope.form.ganged = JSON.stringify(ganged);
        		}
        	};
        	$scope.getControlType = function(fieldTag){
        		var name = "";
        		$(formDefService.controlType).each(function(index,control){
        			if(fieldTag.ctrlType&&fieldTag.ctrlType==control.id){
        				name = control.name;
        				return false;
        			}
        		});
        		return name;
        	};
        	$scope.arrayToolUp = function(index){
        		$scope.arrayTool.up(index,$scope.fieldList);
        		for(var k = 0 ; k < $scope.fieldList.length ; k++){
        			$scope.fieldList[k].sn = k;
				 }
        	}
        	$scope.arrayToolDown = function(index){
        		$scope.arrayTool.down(index,$scope.fieldList);
        		for(var k = 0 ; k < $scope.fieldList.length ; k++){
        			$scope.fieldList[k].sn = k;
				 }
        	}
        },
        template: '<tr class="field-div">'
   		 +'<td width="30" style="border-left-width:0;"><input style="margin-top:11px;" type="checkbox" ng-model="fieldTag.selected">'
       	 +'<td width="150"><label class="desc fl text-center"><input type="text" class="hover-pointer span-input field-home" ng-model="fieldTag.desc"/></label></td>'
       	 +'<td width="100"><label class="desc fl w30 text-center">'
	     	 +'<select ng-if=fieldTag.type==\'varchar\' ng-change="checkFieldGanged(fieldTag,formGanged)" ng-model="fieldTag.ctrlType"  class="span-select initial  field-home"'
	       	 +'   ng-options="o.id as o.name for o in controlType">'
	       	 +' <option value="">-- 请选择 --</option>'
	       	 +'</select>'
	       	 +'<select ng-if=fieldTag.type==\'number\' ng-change="checkFieldGanged(fieldTag,formGanged)" ng-model="fieldTag.ctrlType"  class="span-select initial  field-home">'
	       	 +' <option value="">-- 请选择 --</option>'
	       	 +' <option value="onetext">单行文本</option>'
	       	 +' <option value="select">下拉框</option>'
	       	 +' <option value="radio">单选框</option>'
	       	 +' <option value="dic">数据字典</option>'
	       	 +'</select>'
	       	 +'<select ng-if=fieldTag.type==\'clob\' ng-model="fieldTag.ctrlType"  class="span-select initial  field-home">'
	       	 +' <option value="">-- 请选择 --</option>'
	       	 +' <option value="multitext">多行文本</option>'
	       	 +'</select>'
	       	 +'<select ng-if=fieldTag.type==\'date\' ng-model="fieldTag.ctrlType"  class="span-select initial  field-home">'
	       	 +' <option value="">-- 请选择 --</option>'
	       	 +' <option value="date">日期控件</option>'
	       	 +'</select>'
       	 +'</label></td>'
       	 +'<td width="60"><div class="fl w25 text-center " style="padding: 10px;cursor:pointer;" ng-switch="fieldTag.type">'
	       	 +'<span class="label label-primary" ng-switch-when="number">数字</span>'
	       	 +'<span class="label label-success" ng-switch-when="date">日期</span>'
	       	 +'<span class="label label-info" ng-switch-when="varchar">字符串</span>'
	       	 +'<span class="label label-warning" ng-switch-when="clob">大文本</span>'
	       	 +'<span class="label label-danger" ng-switch-default>复合类型{{fieldTag.type}}</span>'
       	 +'</div></td>'
       	 
       	 +'<td width="100"><div class="fl w30 text-center " style="padding: 10px;">'
       	 	+'<span class="label label-info" ng-repeat="m in separators track by $index" ng-if="fieldTag.path.indexOf(\'.sub_\')<0&&m.key==fieldTag.separator">{{m.name}}</span>'
	  	 +'</div></td>'
       	 
       	 +'<td><div class="btn-group" style="margin:8px auto;" ng-show="fieldTag.isEditing">'
       	 +'<button type="button" class="btn btn-xs btn-default" title="上移" ng-click="arrayToolUp(fieldTag.sn)"><span class="fa fa-arrow-up"></span> <span class="sr-only">上移</span></button>'
       	 +'<button type="button" class="btn btn-xs btn-default" title="下移" ng-click="arrayToolDown(fieldTag.sn)"><span class="fa fa-arrow-down"></span> <span class="sr-only">下移</span></button>'
       	 +'<button type="button" class="btn btn-xs btn-default" title="移除" ng-click="remove(fieldTag,formGanged)"><span class="fa fa-times-circle-o"></span> <span class="sr-only">移除</span></button>'
       	 +'</div></td></tr>',
        replace: true
    };
}]);
