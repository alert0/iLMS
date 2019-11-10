/**
 * 组织选择框
 * data:[要回显的数据]
 * conf:{data,single:false,call:function,initData:{}/[]}
 * *//*
var OrgDialog = function(conf){
	conf.title="组织选择";
	conf.url = __ctx+'/mobile/assets/js/dialog/orgDialog.html';
	createPopupDialog(conf,'orgDialog');
}*/
var OrgDialog = function(conf){
	conf.title="组织选择"
	var param={alias:'orgSelector',
			paramValueString:"",
			callBack:function(data,dialog){
				conf.callBack(data);
			    if("undefined" != typeof dialog){
			    	dialog.dialog('close');
			    }
				},
			initData:conf.initData,
			isSingle:conf.isSingle,
			title:"组织选择"
	}
	
	CustomerDialog(param);
}

/**
 * 岗位选择框
 * data:[要回显的数据]
 * conf:{data,single:false,call:function,initData:{}/[]}
 * */
var PostDialog = function(conf){
	conf.title="岗位选择"
	var param={alias:'postSelector',
			paramValueString:"",
			callBack:function(data,dialog){
				conf.callBack(data);
				if("undefined" != typeof dialog){
			    	dialog.dialog('close');
			    }
				},
			initData:conf.initData,
			isSingle:conf.isSingle,
			title:"岗位选择"
	}
	
	CustomerDialog(param);
}


/**
 * data:[要回显的数据]
 * conf:{data,single:false,call:function,initData:{}/[]}
 * */
var RoleDialog = function(conf){
	conf.title="角色选择"
	var param={alias:'roleSelector',
			paramValueString:"",
			callBack:function(data,dialog){
				conf.callBack(data);
				if("undefined" != typeof dialog){
			    	dialog.dialog('close');
			    }
				},
			initData:conf.initData,
			isSingle:conf.isSingle,
			title:"角色选择"
	}
	
	CustomerDialog(param);
}


/**
 * data:[要回显的数据]
 * conf:{data,single:false,call:function,initData:{}/[]}
 * */
var UserDialog = function(conf){
	var param={alias:'userSelector',
			paramValueString:"",
			callBack:function(data,dialog){
				conf.callBack(data);
				if("undefined" != typeof dialog){
			    	dialog.dialog('close');
			    }
				},
			initData:conf.initData,
			isSingle:conf.isSingle,
			title:conf.title||"用户选择"
	}
	
	CustomerDialog(param);
}
/***
 * 上传弹出框
 * conf{type:"",size:"",max"",call"function"}
 */
var UploadDialog = function(conf){
	var type = conf.type||"",
	max = conf.max||100,
	size = conf.size||4000;
	conf.title="文件上传";
	conf.url=__ctx + "/mobile/assets/js/dialog/uploadDialog.html";
	createPopupDialog(conf,'uploadDialog');
}
/**
 * 自定义对话框
 * 	isCombine:true 是否组合对话框 非必须
 *  conf = {dialog_alias_:'org',isCombine:true,callBack:function(data){alert(data)}}
 *  conf.param{参数}
 * 	var aa = CustomerDialog(conf);
 */
var CustomerDialog = function(conf){
	if(!conf.title)conf.title="自定义对话框";
	conf.url = __ctx+'/form/customDialog/mobileCustomDialog?dialog_alias_='+conf.alias+"&isCombine="+conf.isCombine;
	createPopupDialog(conf,'customerDialog');
}
//与 pc 端 对应
var CustomDialog ={
	openCustomDialog:function(conf){
		CustomerDialog(conf);
	}
} 

/**
 * 启动流程对话框。
 */
var FlowStartDialog=function(actDefId){
	conf.title="流程选择";
	var url=__ctx +"/bpmImage?definitionId="+actDefId;
	conf.url=url;
	createPopupDialog(conf,'flowStartDialog');
}


