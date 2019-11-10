var app = angular.module("app", [ 'base' ]);
app.controller('ctrl', [ '$scope', 'baseService', function($scope, baseService) {
	

	$scope.right = window.passConf&&window.passConf.right||"";

	$scope.permissionList = CloneUtil.list(window.passConf.permissionList);
	//是否显示需要配置项目。
	$scope.showNeedRight=true;
	
	$scope.init=function(){
	
		var needSetting=[];
		//不需要配置
		var noNeedSetting=[];
		for(var i=0;i<$scope.permissionList.length;i++){
			var obj=$scope.permissionList[i];
			var type=obj.type;
			var objRight=getRight($scope.right,type);
			if(objRight){
				objRight.title=obj.title;
				if(objRight.type=="everyone" || objRight.type=="none"){
					objRight.checked=true;
					$scope.showNeedRight=false;
					noNeedSetting.push(objRight);
				}
				else{
					needSetting.push(objRight);
				}
			}
			else{
				if(obj.type=="everyone" || obj.type=="none"){
					obj.checked=false;
					noNeedSetting.push(obj);
				}
				else{
					obj.id="";
					obj.name="";
					needSetting.push(obj);
				}
			}
		}
		$scope.needSetting=needSetting;
		$scope.noNeedSetting=noNeedSetting;
	}
	
	function getRight(aryRights,type){
		if(!$scope.right || $scope.right.size==0) return null;
		
		for(var i=0;i<aryRights.length;i++){
			var obj=aryRights[i];
			if(obj.type==type){
				return obj;
			}
		}
		return null;
	}
	
	//初始化。
	$scope.init();
	
	/**
	 * 在点击不需要配置的选项，确定是否显示配置块。
	 */
	$scope.checkNoSetting=function(event,item){
		var target=event.currentTarget;
		var checked=target.checked;
		item.checked=checked;
		
		var noNeedChecked=true;
		
		for(var i=0;i<$scope.noNeedSetting.length;i++){
			var obj=$scope.noNeedSetting[i];
			if(checked){
				if(obj!=item){
					obj.checked=false;
				}
				noNeedChecked=false;
			}
		}
		$scope.showNeedRight=noNeedChecked;
	}
	
	/**
	 * 返回数据。
	 * 返回格式如：
	 * [{"type":"everyone"},{type:"user",id:"1,2",name:"ray,tom"]
	 */
	$scope.getResult=function(){
		var rtn=[];
		//everyone,none
		for(var i=0;i<$scope.noNeedSetting.length;i++){
			var obj=$scope.noNeedSetting[i];
			if(obj.checked){
				rtn.push(obj);
				return rtn;
			}
		}
		var setting=$scope.needSetting;
		for(var i=0;i<setting.length;i++){
			var obj=setting[i];
			//设置了值。
			if(obj.id){
				rtn.push(obj);
			}
		}
		return rtn;
	}
	
	
	

	/**
	 * 组选择器的对话框事件。
	 * 这个可以扩展对话框。
	 * item 结构如下：
	 * {type:"user",id:"1,2,3",name:"ray,tom,green"}
	 */
	$scope.dialog = function(item) {
			var type=item.type;
			eval(type + "Dialog(item);");
	};
	
	/**
	 * 重置。
	 */
	$scope.reset=function(item){
		item.id="";
		item.name="";
	}

	
	function scriptDialog(item){
		DialogUtil.openDialog(__ctx+"/js/common/directive/codemirror/scriptDialog.jsp","脚本对话框",{code:item.id,mode:"text/x-java"},function(data,dialog){
			dialog.dialog('close');
			$scope.$apply(function() {
				item.id=data;
				item.name=data;
			});
		});
	}
	
	/**
	 * 用户选择器
	 */
	function userDialog(item) {
		//initData:[{"id":"1",name:"a"},{"id":"2","name":"b"},...],
		var initData=getInitData(item);
		
		CustomDialog.openCustomDialog("userSelector",function(data, dialog) {
			dialog.dialog('close');
			applyByType(data,item);
		},{
			selectNum:-1,
			initData : initData
		});
	}
	/**
	 * 角色选择器
	 */
	function roleDialog(item) {
		//initData:[{"id":"1",name:"a"},{"id":"2","name":"b"},...],
		var initData=getInitData(item);
		
		CustomDialog.openCustomDialog("roleSelector",function(data, dialog) {
			dialog.dialog('close');
			applyByType(data,item);
		},{
			selectNum:-1,
			initData : initData
		});
	}
	/**
	 * 岗位选择器
	 */
	function posDialog(item) {
		//initData:[{"id":"1",name:"a"},{"id":"2","name":"b"},...],
		var initData=getInitData(item);
		
		CustomDialog.openCustomDialog("postSelector",function(data,dialog){
			dialog.dialog('close');
			applyByType(data,item);
		},{
			selectNum:-1,
			initData : initData
		});
	}
	/**
	 * 参数 ： item 
	 * 结构为：
	 * {type:"user",id:"1,2,3",name:"ray,tom,mary"}
	 * 返回格式：
	 * [{"id":"1",name:"ray"},{"id":"2","name":"tom"},{"id":"3",name:"mary"}]
	 * 
	 */
	function getInitData(item){
		var rtn=[];
		if(!item.id) return rtn;
		var aryId=item.id.split(",");
		var aryName=item.name.split(",");
		for(var i=0;i<aryId.length;i++){
			var obj={"id":aryId[i],"name":aryName[i]};
			rtn.push(obj);
		}
		return rtn;
	}

	/**
	 * 组织选择器
	 */
	function orgDialog(item) {
		var initData=getInitData(item);
		var callBack = function(data, dialog) {
			dialog.dialog('close');
			applyByType(data,item);
		};
		CustomDialog.openCustomDialog("orgSelector",callBack,{
			selectNum:-1,
			initData : initData
		});
	}
	
	function applyByType(data,item){
		$scope.$apply(function() {
			var name = "";
			var id = "";
			$(data).each(function() {
				if (id) {
					name += ",";
					id += ",";
				}
				name += this.name;
				id += this.id;
			});
			item.name = name;
			item.id = id;
		});
	}
	
} ]);