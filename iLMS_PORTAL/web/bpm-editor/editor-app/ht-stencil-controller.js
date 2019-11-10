angular.module('activitiModeler')
    .controller('HtStencilController', ['$rootScope', '$scope', '$http', 'templateService', '$timeout', function ($rootScope, $scope, $http, templateService, $timeout) {
        
        $scope.checkRepeatNode = function(shapes,nodeKeys,repeatNames){
        	for(var i = 0 ; i < shapes.length; i++){
        		var overrideid = shapes[i].properties["overrideid"];
        		if(overrideid){
            		if(nodeKeys.indexOf(overrideid) > -1){
            			var name = shapes[i].properties["name"];
            			repeatNames.push(name);
            		}else{
            			nodeKeys.push(overrideid);
            		}
            	}
        		if(shapes[i].childShapes.length > 0){
            		$scope.checkRepeatNode(shapes[i].childShapes,nodeKeys,repeatNames);
            	}
            }
        };
        
        $scope.saveModel = function (deploy,successCallback) {
        	var modelMetaData = $scope.editor.getModelMetaData();
            // Indicator spinner image
            var json = $scope.editor.getJSON();
            json.properties["name"] = modelMetaData.name;
            json.properties["process_id"] = modelMetaData.defKey;
            json.properties["documentation"] = modelMetaData.desc;
            var nodeKeys = [];
            var repeatNames = [];
            $scope.checkRepeatNode(json.childShapes,nodeKeys,repeatNames);
            if(repeatNames.length > 0){
            	alert("节点：【"+repeatNames.join(",")+"】的ID重复");
            	return ;
            }
            if(!modelMetaData.name){
            	alert("请输入一个流程名称");
            	return;
            }
            if(!modelMetaData.defKey){
            	alert("请输入一个流程定义KEY");
            	return;
            }
            if(!modelMetaData.typeId){
            	alert("请选择一个分类");
            	return;
            }
            if(!/^([a-zA-Z]|_)\w*$/.test(modelMetaData.defKey.trim())){
            	alert("流程Key必须为英文字母或者数字，开头必须是字母或者下划线！");
            	return;
            }
            //对流程节点进行自定义验证
            if(!$scope.childNodeVerificat(json.childShapes)){
            	return ;
            }
            json = JSON.stringify(json);
            var selection = $scope.editor.getSelection();
            $scope.editor.setSelection([]);
            
            // Get the serialized svg image source
            var svgClone = $scope.editor.getCanvas().getSVGRepresentation(true);
            $scope.editor.setSelection(selection);
            if ($scope.editor.getCanvas().properties["oryx-showstripableelements"] === false) {
                var stripOutArray = jQuery(svgClone).find(".stripable-element");
                for (var i = stripOutArray.length - 1; i >= 0; i--) {
                	stripOutArray[i].remove();
                }
            }

            // Remove all forced stripable elements
            var stripOutArray = jQuery(svgClone).find(".stripable-element-force");
            for (var i = stripOutArray.length - 1; i >= 0; i--) {
                stripOutArray[i].remove();
            }

            // Parse dom to string
            var svgDOM = DataManager.serialize(svgClone);
            
            $scope.btnDisable.save = true;
            
            var params = {
        		defJson:json,
            	svg_xml:svgDOM,
            	name:modelMetaData.name,
            	defKey:modelMetaData.defKey,
            	desc:modelMetaData.desc,
            	typeId:modelMetaData.typeId,
            	typeName:modelMetaData.typeName,
            	version:modelMetaData.version,
            	reason:modelMetaData.reason,
            	defId:modelMetaData.defId,
            	deploy:deploy
            };
            
            // Update
            $http({    method: 'post',
                data: params,
                ignoreErrors: true,
                headers: {'Accept': 'application/json',
                          'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                transformRequest: function (obj) {
                    var str = [];
                    for (var p in obj) {
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    }
                    return str.join("&");
                },
                url: KISBPM.URL.putModel(modelMetaData.modelId)})

                .success(function (data, status, headers, config) {
                	$scope.btnDisable.save = false;
                	if(data == "success"){
                		$scope.editor.handleEvents({
                            type: ORYX.CONFIG.EVENT_SAVED
                        });
                        $scope.modelData.name = modelMetaData.name;
                        $scope.modelData.lastUpdated = data.lastUpdated;

                        // Fire event to all who is listening
                        var saveEvent = {
                            type: KISBPM.eventBus.EVENT_TYPE_MODEL_SAVED,
                            model: params,
                            modelId: modelMetaData.modelId,
        		            eventType: 'update-model'
                        };
                        KISBPM.eventBus.dispatch(KISBPM.eventBus.EVENT_TYPE_MODEL_SAVED, saveEvent);

                        if (successCallback) {
                            successCallback();
                        }
                        
                        if(deploy){
                        	alert("发布新版成功，请到流程列表刷新查看");
                        	window.close();
                        	window.opener.location.reload(true);
                        	
                        }else{
                        	alert("保存修改成功");
                        	window.location.reload(true);
                        	window.opener.location.reload(true);
                        }
                    }else{
                    	alert(data);
                    }
                })
                .error(function (data, status, headers, config) {
                	alert("无法连接到服务器");
                });
        };
        
        $scope.selectType = function(){
        	layer.ready(function(){
				templateService.get('editor-app/popups/select-type.html?version=' + Date.now(), $scope).then(function(target){
					layer.open({
						  type: 1,
						  title:"选择分类",
						  area: ['300px', '340px'],
						  btn:['确定','取消'],
						  yes:function(index,layerno){
							  var layerScope = layerno.find("[ng-controller='KisBpmShapeSelectionTypeCtrl']").scope(); 
							  layerScope.$apply(function(){
								  var donotClose = layerScope.select();
								  if(!donotClose){
									  layer.close(index);
								  }
							  });
						  },
						  fixed: false, //不固定
						  maxmin: false,
						  content: target
      				});
				});
			});
        };
        
        $rootScope.setShapeProperty = function(name,value,shape){
        	if(!shape){
        		shape = $scope.selectedShape;
        	}
        	$rootScope.forceSelectionRefresh = true;
        	shape.setProperty(name,value);
        	var facade = shape.facade;
        	facade.setSelection([shape]);
        	facade.getCanvas().update();
        	facade.updateSelection();
        };
        
        $rootScope.addNewNodeNum = function(type){
       	 var typeNumJson = $rootScope.stencilIdNumber;
       	 if(!typeNumJson){
       		typeNumJson={};
       	 }
       	 if(typeNumJson[type]){
       		typeNumJson[type] ++;
       	 }else{
       		typeNumJson[type] = 1;
       	 }
       	 $rootScope.stencilIdNumber = typeNumJson;
       };
       //对流程节点进行自定义验证
       $scope.childNodeVerificat = function(childs){
       		//检测外部子流程是否选择
    	   for(var i=0;i<childs.length;i++){
    		   var obj = childs[i];
    		   if(obj.properties){
    			   var properties = obj.properties;
    			   if(properties['ismultiinstance']){
    				   if(properties.hasOwnProperty('callactivitycalledelement')&&!properties['callactivitycalledelement']){
    					   alert("节点“"+properties['name']+"”的key不能为空，请选择！");
    					   return false;
    				   }
    			   }
    		   }
    	   }
    	   return true;
       };
}]);
