var app = angular.module('app', [ 'formDirective', 'arrayToolService' ]);
app.controller("ctrl", [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayTool) {
	$scope.ArrayTool = ArrayTool;
	
	$scope.initData = [];
	$scope.sysDemensions = [];
	$scope.initDemId = "";
	
	/**
	 * 获取组织维度
	 */
	function getSysDemensions(event, data ,param , treeUrl){
		$.ajaxSetup({
	        async: false
	    });
		$.post(__ctx+"/org/sysDemension/listJson",function(result){
			$scope.sysDemensions = result.rows;
			if($scope.sysDemensions&&$scope.sysDemensions.length>0){
				$scope.initDemId = $scope.sysDemensions[0].id;
			}
			$('#demCombobox').combobox({
		        data: $scope.sysDemensions,
		        valueField: 'id',
		        textField: 'demName',
		        onLoadSuccess:function(){
		        	if($scope.initDemId){
		        		$('#demCombobox').combobox('select',$scope.initDemId);
		        	}
		        },
		        onChange:function(n,o){
		        	loadZtree(event, data ,param , treeUrl + "&demId="+n);
		        }
		    });
		});
	}
	
	/**
	 * 加载zTree
	 */
	$scope.$on("afterLoadEvent", function(event, data) {
		data.conditionfield = JSON.parse(data.conditionfield);
		data.displayfield = JSON.parse(data.displayfield);
		data.resultfield = JSON.parse(data.resultfield);
		data.sortfield = JSON.parse(data.sortfield);
		
		var param=__param;
		// 处理url上的参数和对话框传来的参数
		if (window.passConf && window.passConf.param) {
			param = jQuery.extend(__param, window.passConf.param);
		}
		if (window.passConf && window.passConf.initData){
			$scope.initData = window.passConf.initData;
		}
		if(window.passConf && window.passConf.selectNum  ){
			data.selectNum = window.passConf.selectNum;
		}
		var treeUrl = __ctx + "/form/customDialog/getTreeData?dialog_alias_=" + data.alias;
		//这里将原来的加载方式抽取为loadZtree方法，如果当前是组织选择器则根据维度下拉框选择值加载，如果是其他的则按原来方式加载
		if(data.alias.indexOf('orgSelector')!=-1){
			getSysDemensions(event, data ,param , treeUrl);
		}else{
			loadZtree(event, data ,param ,treeUrl);
		}
	});
	
	
	function loadZtree(event ,data ,param ,treeUrl ){
		var isMulti = false;
		var idKey =data.displayfield.id;
		var pIdKey =data.displayfield.pid;
		var name = data.displayfield.displayName;
		ztreeCreator = new ZtreeCreator("ztree", treeUrl).setDataKey({
			idKey :idKey,
			pIdKey : pIdKey,
			name : name,
			title:name
		});
		ztreeCreator.setAsync({
			enable : true,
			url : treeUrl,
			autoParam : [ idKey, pIdKey ],
			otherParam:param
		});
		if (data.selectNum != 1) {// 多选
			isMulti = true;
			var str = "";
			if(data.parentCheck==1){
				str+="p";
			}
			if(data.childrenCheck==1){
				str+="s";
			}
			ztreeCreator.setCheckboxType({
				"Y" : str,
				"N" : str
			});
		}
		
		//这里触发组合对话框的事件CombinateDialog/show.jsp
		ztreeCreator.setCallback({
			onClick:function(e, treeId, treeNode, clickFlag){
				if(window.parent.treeClick){
					window.parent.treeClick(getResult());
					initData();
				}
				//点击节点文字，触发checkbox的点击事件
				ztreeCreator.getTreeObj().checkNode(treeNode, !treeNode.checked, true);
			},
			onAsyncSuccess:function(event,treeId){
				initData();
			}
		});
		
		function initData(){
			for(var i=$scope.initData.length-1;i>=0 ;i--){
				var node,obj = $scope.initData[i];
				for(var myKey in obj){
					var treeObj = ztreeCreator.getTreeObj();
					if(treeObj){
						 node = ztreeCreator.getTreeObj().getNodeByParam(idKey, obj[myKey], null);
						 node && ztreeCreator.getTreeObj().checkNode(node, true, true);
						 node && $scope.initData.remove($scope.initData[i]);
					}else{
						$scope.initData.remove($scope.initData[i]);
					}
				}
			}
		}
		
		//修复单选时，用ctrl键可以多选
		param.setting = {
			view: {
				selectedMulti: isMulti,
			},
		};
		
		ztreeCreator.initZtree(param);
	}

} ]);