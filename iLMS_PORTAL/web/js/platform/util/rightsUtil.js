var RightsUtil={};

/**
 * 打开通用权限对话框。
 * 参数：
 * rightsBeanId ： 对应spring容器中的beanId。
 * callback: 
 * 定义如下：
 * var callback = function(data, dialog){
 * 	//data :确定后返回的数据
 *  //dialog ：对话框对象。
 * }
 * aryRights : 已选中权限 如：[{type:"everyone"},{type:"user",id:"",name:""}]
 * 
 */
RightsUtil.openDialog=function(rightsBeanId,callback,aryRights){
	var passConf = {};
	passConf.right = aryRights || [];
	var url= __ctx +"/system/util/getBean?beanId="+ rightsBeanId;

	$.ajax({ 
       type: "get", 
       url: url, 
       success: function(data){ 
    	   passConf.permissionList =data;
    	   DialogUtil.openDialog(__ctx+"/rights/fieldDialog", "权限配置", passConf, callback);
       } 
	});
}


/**
 * 打开通用权限对话框。
 * 参数：
 * rightsBeanId ： 对应spring容器中的beanId。
 * callback: 
 * 定义如下：
 * var callback = function(data, dialog){
 * 	//data :确定后返回的数据
 *  //dialog ：对话框对象。
 * }
 * aryRights : 已选中权限 如：[{type:"everyone"},{type:"user",id:"",name:""}]
 * 
 */
RightsUtil.openGroupDialog=function(rightsBeanId,callback,aryRights){
	var passConf = {};
	passConf.right = aryRights || [];
	var url= __ctx +"/system/util/getBean?beanId="+ rightsBeanId;

	$.ajax({ 
       type: "get", 
       url: url, 
       success: function(data){ 
    	   passConf.permissionList =data;       
    	   DialogUtil.openDialog(__ctx+"/org/sysUserGroup/groupSetDialog", "群组人员配置", passConf, callback);
       } 
	});
}


/**
 * 将权限配置页面的返回数据格式化为数组
 */
RightsUtil.rightsDataToArray = function(data){
	var tmpAry=[];
	for(var i=0;i<data.length;i++){
		var obj=data[i];
		if(obj.id){
			var tmp={"type":obj.type,"title":obj.title, "id":obj.id,"name":obj.name};
			tmpAry.push(tmp);
		}
		else{
			var tmp={"type":obj.type,"title":obj.title};
			tmpAry.push(tmp);
		}
	}
	return tmpAry;
}
/**
 * 解析权限数组
 */
RightsUtil.resolveRights = function(data){
	var returnStr='';
	for(var i=0;i<data.length;i++){
		var obj=data[i];
		if(obj.title =='所有人'){
			returnStr+=obj.title;
		}else{
			returnStr+=obj.title+':'+obj.name;
		}
	}
	return returnStr;
}
