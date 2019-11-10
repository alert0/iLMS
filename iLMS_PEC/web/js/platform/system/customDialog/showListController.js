var app = angular.module('app', [ 'formDirective', 'arrayToolService' ]);
app.controller("ctrl", [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayTool) {
	$scope.ArrayTool = ArrayTool;
	$scope.selectedList = [];
	$scope.creteriaList = [];
	$scope.dynamicCondition = [];
	$scope.conditionDic = {
			"number": [{
				key: '等于',
				value: 'EQ'
			}, {
				key: "大于等于",
				value: 'GE'
			}, {
				key: "大于",
				value: 'GT'
			}, {
				key: "小于",
				value: 'LT'
			}, {
				key: "小于等于",
				value: 'LE'
			}, {
				key: "in",
				value: 'IN'
			}],
			"varchar": [{
				key: '等于',
				value: 'EQ'
			}, {
				key: "like",
				value: 'LK'
			}, {
				key: "likeEnd",
				value: 'LFK'
			}, {
				key: "in",
				value: 'IN'
			}],
			"date": [{
				key: '等于',
				value: 'EQ'
			}, {
				key: "between",
				value: 'BETWEEN'
			}, {
				key: "大于等于",
				value: 'GE'
			}, {
				key: "小于等于",
				value: 'LE'
			}]
		};
	
	$scope.$watch("customDialog.conditionfield",function(newVal,oldVal){
		if(!newVal) return;
		angular.forEach($scope.customDialog.conditionfield,function(condition){
			if(condition.defaultType=='7'){
				$scope.dynamicCondition.push(condition);
			}
		});
	},false);
	
	$scope.switchDynamic = function(creteria){
		creteria.condition = "";
		angular.forEach($scope.dynamicCondition,function(dynamic){
			if(dynamic.field==creteria.field){
				creteria.conditionOptions = $scope.conditionDic[dynamic.dbType];
			}
		});
	}
	
	$scope.init = function() {
		var url="getByAlias?alias="+__param.dialog_alias_;
		var defer=baseService.get(url);
		defer.then($scope.handData)
	};
	
	$scope.clear = function() {
		$scope.selectedList=[];
	};
	
	$scope.addCreteria = function(){
		$scope.creteriaList.push({});
	}
	
	$scope.removeCreteria = function(creteria){
		$scope.creteriaList.remove(creteria);
	}

	/**
	 * 加载jqGrid
	 */
	$scope.handData=function( data) {
		$scope.customDialog=data;
		data.conditionfield = JSON.parse(data.conditionfield);
		data.displayfield = JSON.parse(data.displayfield);
		data.resultfield = JSON.parse(data.resultfield);
		data.sortfield = JSON.parse(data.sortfield);
		
		
		// 处理url上的参数和对话框传来的参数
		if (window.passConf && window.passConf.param) {
			jqGridParam.postData = jQuery.extend(__param, window.passConf.param);
		}
		if (window.passConf && window.passConf.selectNum) {//处理修改多选单选参数
			data.selectNum = window.passConf.selectNum;
		}else if(__param){//通过组合对对话框传递
			if(__param.selectNum)data.selectNum = __param.selectNum;
			if(__param.initData){
				var initData = __param.initData;
				var json = parseToJson(initData);
				$(json).each(function() {
					var that = this;
					$(data.resultfield).each(function(){
						that[this["field"].toUpperCase()]=that[this["comment"]];
					});
					pushSelectedList(that);
				});
			}
		}
		
		
		
		if (window.passConf && window.passConf.initData) {//处理回显数据
			$(window.passConf.initData).each(function() {
				var that = this;
				$(data.resultfield).each(function(){
					that[this["field"].toUpperCase()]=that[this["comment"]];
				});
				pushSelectedList(that);
			});
		}
		
		if (data.needPage) {// 分页的情况
			jqGridParam.pager = "#pagerNav";
			jqGridParam.rowNum = data.pageSize;
			jqGridParam.rowList = [5, 10, 15, 20, 30 ];
		}

		// 列数据
		jqGridParam.colModel = [];
		$(data.displayfield).each(function() {// 显示字段
			jqGridParam.colModel.push({
				label : this.comment,
				name : this.field.toUpperCase(),
				align : "center"
			});
		});
		$(data.resultfield).each(function() {// 返回字段，以隐藏列放到数据里面
			jqGridParam.colModel.push({
				name : this.field.toUpperCase(),
				hidden : true
			});
		});
		/**
		 * 数据加载完成时,将数据键统一转成大小。
		 * [{name:"ab"}]-->[{NAME:"ab"}]
		 */
		jqGridParam.loadComplete= function (data) {
			var aryData=[];
			for(var i=0;i<data.rows.length;i++){
				var obj={};
				var tmp=data.rows[i];
				for(var key in tmp){
					obj[key.toUpperCase()]=tmp[key];
				}
				aryData.push(obj);
			}
			//解决jqgrid列表的页码输入框中输入超过最大页数时，按回车键显示为上一次的页码，但列表中无数据
			$('.ui-pg-input.form-control').keydown( function(e) {
			    var key = window.event?e.keyCode:e.which;
			    if(key.toString() == "13"){
			    	try {
			    		var cvalue = $(this).val();
				    	var svalue = $("#sp_1_pagerNav").text();
				    	if(parseInt(cvalue)>parseInt(svalue)){
				    		$("#btnSearch").click();
				    		return false;
				    	}
					} catch (e) {}
			    }
			});
			return aryData;
         };

		jqGridParam.onSelectRow = function(rowid, status) {
			var row = jQuery('#gridList').getRowData(rowid);
			$scope.$apply(function() {
				$scope.activeRow = row;// 标记当前活跃row，供单选时使用
				pushSelectedList(row);
				if(parent.gridOnclick){
					parent.gridOnclick();
				}
			});
		};

		jQuery("#gridList").jqGrid(jqGridParam);
		// 重新构建jqGrid大小
		var isMulti=data.selectNum != 1;
		resizeDialog(isMulti);

		$("#btnSearch").click(function() {
			search();
		});
	}
	
	$(document).on("click","#btnResetCondition",function(){
		$($scope.customDialog.conditionfield).each(function() {
			this.value = "";
		});
		AngularUtil.setData($scope);
		//点击重置后重新查找一次
		search();
	});
	
	function resizeDialog(isMuliti){
		var objSize = $.getPageSize();
		var panelHeight = $("#panelTop").height();
		var width = objSize.width - 2;
		
		var height=objSize.height - panelHeight ;
		// 非单选
		if (isMuliti) {
			width = width - width * 0.2-10;
			$("#divSelected").height(height-60);
		}
		
		$("#gridList").jqGrid('setGridWidth', width)
		.jqGrid('setGridHeight',height-135);
		$("#gview_gridList").css({overflow:"hidden"});
		
	}

	function pushSelectedList(row) {
		var isExist = false;
		for (var i = 0; i < $scope.selectedList.length; i++) {
			var temp = $scope.selectedList[i];
			var allEq = true;
			for ( var key in temp) {
				if (row[key]&&temp[key]&&temp[key]!=row[key]) {
					allEq = false;
					break;
				}
			}
			if (allEq) {
				isExist = true;
				break;
			}
		}
		if (isExist) return;
		$scope.selectedList.push(row);
	}

	function search() {
		var postData = $("#gridList").jqGrid("getGridParam", "postData");
		var myregexp = /^Q\^(.*)\^(.*)$/;
		var prefix = $scope.customDialog.sqlBuildType? "":"Q^";//查询条件使用自定义sql的话，则条件参数中不需要加前缀
		$($scope.customDialog.conditionfield).each(function() {
			if (this.defaultType != "1")
				return;
			var val = this.value ? this.value : "";
			postData[prefix + this.field] = val;
		});
		$.each(postData,function(k,value){
			if(myregexp.test(k)) delete postData[k];
		});
		$($scope.creteriaList).each(function() {
			var me = this;
			if($.isEmptyObject(me)) return;
			var val = me.value ? me.value : "";
			postData["Q^" + me.field + "^" + me.condition] = val;
		});
		$("#gridList").jqGrid().trigger("reloadGrid"); // 重新载入
	}
} ]);