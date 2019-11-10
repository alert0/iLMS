
/**
 * conf 对象
 * {
 * 		callBack:回调函数
 * 		initData:初始化数据
 * 		isSingle:是否单选
 * }
 */
var OrgDialog = function(conf){
	var callBack = function(data,dialog){
		conf.callBack(data);
	    dialog.dialog('close');
	};
	CustomDialog.openCustomDialog("orgSelector",callBack,{
		initData:conf.initData,
		selectNum:conf.isSingle?"1":"-1"
	});
}

/**
 * conf 对象
 * {
 * 		callBack:回调函数
 * 		initData:初始化数据
 * 		isSingle:是否单选
 * }
 */
var PostDialog = function(conf){
	var callBack = function(data,dialog){
		conf.callBack(data);
	    dialog.dialog('close');
	};
	
	CustomDialog.openCustomDialog("postSelector",callBack,{
		initData:conf.initData,
		selectNum:conf.isSingle?"1":"-1"
	});
	
}

/**
 * conf 对象
 * {
 * 		callBack:回调函数
 * 		initData:初始化数据
 * 		isSingle:是否单选
 * }
 */
var RoleDialog = function(conf){
	var callBack=function(data,dialog){
		conf.callBack(data);
	    dialog.dialog('close');
	};
	CustomDialog.openCustomDialog('roleSelector',callBack,{
		initData:conf.initData,
		selectNum:conf.isSingle?"1":"-1"
	});
}
/**
 * 用户对话框，对话框参数为conf
 * single:选择单个或者多个。
 * callBack:对话框点击选择时的回调函数，回调函数传入选择的data。
 * callBack:function(data){
 * 	//data数据格式如下:
 * 	//[{"id":"10000001080001","name":"张三"}]
 * }
 * 初始化数据:
 * initData:[{"id":"10000001080001","name":"张三"}]
 * 
 * 调用示例如下: 
 * conf:
 * {
 * 		isSingle:false,
 * 		callBack:function(data){
 * 
 * 		},
 * 		initData:[{"id":"10000001080001","name":"张三"}]
 * }
 */
/*var UserDialog = function(conf){
	var callBack =function(data,dialog){
		conf.callBack(data);
	    dialog.dialog('close');
	};
	CustomDialog.openCustomDialog('userSelector',callBack,{
		initData:conf.initData,
		selectNum:conf.isSingle?"1":"-1"
	});
}*/

var UserDialog = function(conf){
	var callBack =function(data,dialog){
		conf.callBack(data);
	    dialog.dialog('close');
	};
	var initData = conf.initData?conf.initData:"";
	var confs = {alias:'userSelector', callBack:callBack,initData:initData,selectNum:conf.isSingle?"1":"-1"};
	CombinateDialog.open(confs);
}


/**
 * conf 对象
 * {
 * 		callBack:回调函数
 * 		initData:初始化数据
 * 		isSingle:是否单选
 * }
 */
var GroupDialog = function(conf){
	var callBack=function(data,dialog){
		conf.callBack(data);
	    dialog.dialog('close');
	};
	CustomDialog.openCustomDialog('groupSelector',callBack,{
		initData:conf.initData,
		selectNum:conf.isSingle?"1":"-1"
	});
}

