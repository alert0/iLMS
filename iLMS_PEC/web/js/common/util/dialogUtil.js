/*
 * 通用对话框工具类
 * luoxw 2016-04-01
 */
var DialogUtil = {
	/*
	 * 通用打开对话框 url:请求View的地址 title：自定义对话框的标题
	 * initData:初始化数组，回填数组，eg[{"id":"1",name:"a"},{"id":"2","name":"b"},...]
	 * callback：回调函数 width,height:长宽
	 */
	openDialog : function(url, title, passConf, callback, width, height) {
		width = width || 800;
		height = height || 600;
		var dialog;
		var def = {
			passConf : passConf,
			title : title,
			width : width,
			height : height,
			modal : true,
			resizable : true,
			buttons : [ {
				text : '确定',
				iconCls : 'fa fa-save',
				handler : function(e) {
					// 获取自定义对话框
					var win = dialog.innerWin;
					// 调用自定义对话框的jsp里面的方法获取结果
					if(win.getResult){//有回调方法，就处理
						var data = win.getResult();
						callback(data, dialog);
					}else{//否则就直接关闭
						dialog.dialog('close');
					}
				}
			}, {
				text : '关闭',
				iconCls : 'fa fa-remove',
				handler : function() {
					dialog.dialog('close');
				}
			} ]
		};
		dialog = $.topCall.dialog({
			src : url,
			base : def
		});
	}
}