/*
 * 选择一个流程分类
 */

var KisBpmShapeSelectionTypeCtrl = [ '$rootScope', '$scope', '$timeout', '$translate', function($rootScope, $scope, $timeout, $translate) {

    $scope.selectedMorphShapes = [];
    $scope.availableMorphShapes = [];
    window._treeObj = null;
    var tempTypeId = "";
    var tempTypeName =""
    
    $scope.select = function(){
    	if(tempTypeId){
    		$scope.modelData.typeId = tempTypeId;
        	$scope.modelData.typeName = tempTypeName;
    	}
    	else{
    		alert("需要选择一个分类");
    		return true;
    	}
    }
    var setTypeId = function(event, treeId, treeNode){
    	if(!treeNode.getParentNode()){
    		tempTypeId = tempTypeName = "";
    		_treeObj.cancelSelectedNode(treeNode);
    		return;
    	}
    	tempTypeId = treeNode.id;
    	tempTypeName = treeNode.name;
	}
    
    var initZtree = function(){
    	var ztreeCreator = new ZtreeCreator('type__Tree_',__ctx + "/system/sysType/getTypesByKey")
    	.setCallback({onClick:setTypeId})
    	.initZtree({typeKey:"FLOW_TYPE"},function(treeObj){
    		_treeObj = treeObj;
    	}); 
    }
    initZtree();
}];