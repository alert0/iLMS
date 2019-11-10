<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html ng-app="app">
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/ngEdit.jsp"%>
<script>
	var id = "${param.id}";
	
	var app = angular.module('app',['formDirective']);
	app.controller('ctrl',function($scope){
		$scope.opinionConf =[];
		$.post(__ctx+"/form/formDef/getOpinionConf",{id:id},function(data){
			$scope.opinionConf = JSON.parse(data);
			for(var i=0;i<$scope.opinionConf.length;i++){
				$scope.opinionConf[i].isRead = true;
			}
			$scope.$apply();
		});

		$scope.save = function(){
			for(var i=0,p;p=$scope.opinionConf[i++];){
				if(!p.name || !p.desc){
					$.topCall.error("描述和意见框标识必填！");
					return;
				}
			}
			var param = {opinionConf:JSON.stringify($scope.opinionConf),id:id};
			$.post(__ctx+"/form/formDef/opinionConfSave",param,function(data){
				var result = new com.hotent.form.ResultMessage(data)
				if(result.isSuccess()){
					 $.topCall.message({base:{type:'alert',title:'温馨提示',msg:result.getMessage(),fn:function(r){
						 HT.window.closeEdit();
	     			}}});
				}
				else{
					$.topCall.error(result.getMessage());
				}
			});
		}
		$scope.addOpinion = function(){
			$scope.opinionConf.push({name:"",desc:"",isRead:false});
		}
		$scope.remove = function(index){
			$scope.opinionConf.splice(index,1);
		}
		$scope.nameChange = function(index){
			if(!$scope.opinionConf[index].name) return;
			
			for(var i=0,p;p=$scope.opinionConf[i++];){
				if(i-1!=index && p.name && p.name == $scope.opinionConf[index].name){
					$.topCall.error(name+"不可重复！");
					$scope.opinionConf[index].name = $scope.opinionConf[index].name+"_";
				}
			}
			
		}
	});
	
</script>
</head>

<body ng-controller="ctrl">
	<div class="toolbar-panel col-md-13 ">
		<div class="buttons">
			<button class="btn btn-primary btn-sm" ng-click="save()">保存</button>
			<button onclick="HT.window.closeEdit();" class="btn btn-default btn-sm fa-close">取消</button>
		</div>
	</div>
	
	<table cellspacing="0" class="table-form w100">
		<tr>
			<th><span>说明：</span></th>
			<td colspan="2">
				如果需要在表单中使用意见框，那么需要首先定义意见框，以方便设置基础意见权限。
			</td>
		</tr>
		<tr>
			<td><span>序号</span></td>
			<td><span>描述</span></td>
			<td><span>意见框标识</span></td>
		</tr>
		<tr ng-repeat ="opinion in opinionConf track by $index">
			<td>{{$index+1}}
				　　<button title="移除" class="fa bigger-160 fa-delete" ng-click="remove($index)"></button>
			</td>
			<td><input type="text" ng-model="opinion.desc" class="inputText"></td>
			<td>
				<input ng-if="opinion.isRead" type="text" ng-model="opinion.name" readonly="readonly" class="inputText" ht-pinyin="opinion.desc" >
				<input ng-if="!opinion.isRead" type="text" ng-model="opinion.name" class="inputText" ht-pinyin="opinion.desc" ng-blur="nameChange($index)">
			</td>
		</tr>
		<tr><td colspan="3"><a role="button" href="javascript:;" ng-click="addOpinion()" class="btn btn-primary btn-sm fa fa-add">添加意见项</a></td></tr>
	</table>
</body>
</html>