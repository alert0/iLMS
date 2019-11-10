/*
 * 选择一个流程分类
 */

var KisBpmShapeSelectionCallactivityTypeCtrl = [ '$rootScope', '$scope', '$timeout', '$translate','$http', function($rootScope, $scope, $timeout, $translate,$http) {

    $scope.selectedSubDefKeys = [];
    
    $scope.select = function(){
    	if($scope.selectedSubDefKeys.length > 0){
    		var subDef = $scope.selectedSubDefKeys[0];
    		var defKey = subDef["defKey"];
        	var selectedShape = $rootScope.editor.getSelection().first();
        	$rootScope.setShapeProperty("oryx-callactivitycalledelement",defKey,selectedShape);
    	}
    };
    
    var initZtree = function(){
    	new ZtreeCreator('type__Tree_',__ctx + "/system/sysType/getTypesByKey")
    	.setCallback({onClick:selectType})
    	.initZtree({typeKey:"FLOW_TYPE"},function(treeObj){}); 
    };
    
    var selectType = function(event, treeId, treeNode){
    	$http({method: 'GET', url: __ctx + "/flow/def/listJson",params:{"Q^type_id_^L":treeNode.id}}).
        success(function (data, status, headers, config) {
        	if(data.rows){
        		$scope.availableMorphShapes = data.rows;
        	}
        }).
        error(function (data, status, headers, config) {
        	alert("无法连接到服务器");
        });
    };
    
    //$scope.property.value = '';
    var loadDefList = function (){
        $scope.gridOptions = {
            data: 'availableMorphShapes',
            enableRowReordering: true,
            headerRowHeight: 28,
            multiSelect: false,
            keepLastSelected : false,
            selectedItems: $scope.selectedSubDefKeys,
            columnDefs: [{ field: 'name', displayName: '流程名称' },
                { field: 'defKey', displayName: '流程KEY'}]
        };
    };
    initZtree();
    loadDefList();
}];