function UploadDialog(conf) {
	if(!conf) conf={};
	var max = conf.max||1,
		type = conf.type||"",
		storeType = conf.storeType || "",
		size = conf.size||0;
		title = conf.title||"附件上传";
	var url=__ctx + "/system/file/uploadDialog?max="+max+"&type="+type+"&size="+size + "&storeType=" + storeType;
	
	var dialog = null;
	var def = {
		passConf : {dialog:dialog},
		title : title,
		width : 800,
		height : 500,
		modal : true,
		resizable : true,
		buttons:[{
			text:'确定',
			handler:function(e){
				    var win = dialog.innerWin;
				    var scope = win.getData();
				    if(!scope){
				    	$.topCall.error("获取已上传文件信息时出错");
				    	return;
				    }
				    if(scope.uploader.getNotUploadedItems().length>0){
				    	$.topCall.alert("提示信息","有文件尚未上传，请上传该文件或删除该文件.");
				    	return;
				    }
				    if(scope.uploader.queue.length==0){
				    	$.topCall.alert("提示信息","至少需要上传一个文件.");
				    	return;
				    }
					if(conf.callback){
						var ary = [];
						angular.forEach(scope.uploader.queue,function(item){
							ary.push(item.json);
						});
						conf.callback(ary);
						dialog.dialog("close");
					}else{
						dialog.dialog("close");
					}
			}
		},{
			text:'关闭',
			handler:function(){dialog.dialog("close");}
		}]
	};
	dialog = $.topCall.dialog({
		src:url,
		base:def
	});
}