/**
 * demo:
 * conf={};
 * conf.defId="10000019440143";//流程id
 * conf.nodeId="UserTask2";//任务节点
 * ConditionScript.showDialog(conf,function(data,dialog){
 * 		alert("返回结果："+data);//data为脚本string
 * 		dialog.dialog('close');//关闭弹出框
 * });
 */
var ConditionScript={
		showDialog:function(conf,method){
			if(conf==null){
				conf={};
			}
			conf.type=conf.type||1;
			if(method==null){
				method = function(data){
					alert("返回脚本："+data);
				}
			}
			var title=conf.type==1?"条件脚本选择器":"人员脚本选择器";
			var dialog;
			if(typeof(conf.calc)=="undefined")conf.calc = "";
			var def = {
			        passConf:conf.calc,title:title,width:1100,height:600, modal:true,resizable:true,
			        buttonsAlign:'center',
					buttons:[{
						text:'确定',
						iconCls:'fa fa-check-circle',
						handler:function(e){
								var win=dialog.innerWin;//获取自定义对话框   				
								var data = win.getResult();//调用自定义对话框的jsp里面的方法获取结果
								method(data,dialog);
								dialog.dialog('close');
						}
					},{
						text:'关闭',
						iconCls:'fa fa-times-circle',
						handler:function(){dialog.dialog('close');
						}
					}]
			};
			if(typeof(conf.defId)=="undefined")conf.defId = "";
			if(typeof(conf.flowKey)=="undefined")conf.flowKey = "";
			var url=__ctx+'/system/conditionScript/conditionScriptSetting?type='+conf.type+'&defId='+conf.defId+'&flowKey='+conf.flowKey;
			if(conf.nodeId!=null){
				url+="&nodeId="+conf.nodeId;
			}
			dialog = $.topCall.dialog({
				src:url,
				base:def
			});
		}
}