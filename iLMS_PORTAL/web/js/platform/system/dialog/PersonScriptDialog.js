/**
 * demo:
 * conf={};
 * conf.defId="10000019440143";//流程id
 * conf.nodeId="UserTask2";//任务节点
 * PersonScript.showDialog(conf,function(data,dialog){
 * 		alert("返回结果："+data);//data为脚本string
 * 		dialog.dialog('close');//关闭弹出框
 * });
 */
var PersonScript={
		showDialog:function(conf,method){
			//测试信息
			if(conf==null){
				conf={};
				conf.defId="10000019440143";//流程id
			}
			if(method==null){
				method = function(data){
					alert("返回脚本："+data);
				}
			}
			
			var dialog;
			var def = {
			        passConf:null,title:"人员脚本选择器",width:1000,height:600, modal:true,resizable:true,
					buttons:[{
						text:'确定',
						handler:function(e){
								var win=dialog.innerWin;//获取自定义对话框   				
								var data = win.getResult();//调用自定义对话框的jsp里面的方法获取结果
								method(data,dialog);
						}
					},{
						text:'关闭',
						handler:function(){dialog.dialog('close');}
					}]
			};
			var url=__ctx+'/platform/system/personScript/setting.ht?defId='+conf.defId;
			if(conf.nodeId!=null){
				url+="&nodeId="+conf.nodeId;
			}
			dialog = $.topCall.dialog({
				src:url,
				base:def
			});
		}
}