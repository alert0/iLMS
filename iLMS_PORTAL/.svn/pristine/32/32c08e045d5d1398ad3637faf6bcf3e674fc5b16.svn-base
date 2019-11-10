var app = angular.module('app', [ 'formDirective', 'arrayToolService' ]);
app.controller("ctrl", [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayTool) {
	$scope.ArrayTool = ArrayTool;

	$scope.init = function() {
		$scope.selAllExp=true;
		$scope.param = window.passConf;
		$scope.param.getType = "page";
	};

	$scope.exportData = function() {
		if(!$scope.param.expField){
			alert("请选择至少一个字段");
			return;
		}
		
		var url = __ctx + '/system/query/queryView/export?alias=' + __param.alias + "&sqlAlias=" + __param.sqlAlias;
		var frm = new com.hotent.form.Form();
		frm.creatForm("frmExport", url);
		$.each($scope.param,function(key,val){
			if(key=='expField'){
				//实现导出字段列的排序
                val = $scope.sortExpField(val);
            }
			frm.addFormEl(key, val);
		});
		frm.submit();
	};
	
	$scope.sortExpField = function(oldExpField){
        if(oldExpField){
        	try {
        		var oldExpField = oldExpField.split(",")
                var expField = [];
                var shows = $scope.queryView.shows;
                for(var i=0;i<shows.length;i++){
                    if(shows[i]['hidden']==0){
                        for(var m=0;m<oldExpField.length;m++){
                            if(shows[i]['fieldName']==oldExpField[m]){
                                expField.push(oldExpField[m]);
                                break;
                            }
                        }
                    }
                }
                return expField.join(",");
			} catch (e) {}
        }
        return oldExpField;
    }

	$scope.$on("afterLoadEvent", function(event, data) {
		data.shows = JSON.parse(data.shows);
	});
} ]);