//这方法用于替换linkButtons.js中的handlerRomveSelect的方法处理 
 $.handlerRomveSelect = function(){
	//单击删除超链接的事件处理	
	var $obj=$("a.fa-remove");
	$obj.unbind('click');
	$obj.bind('click',function(){
		var $datagrid= $(this).parents('.datagrid').find('.my-easyui-datagrid').eq(0),
		options = $datagrid.datagrid('options'),
		pkStr = options.idField;
		if($(this).hasClass('disabled')) return false;

		var records = $datagrid.datagrid('getChecked');
	    if($(this).hasClass('fa-remove') &&  (records==null||records.length<1) ){
			$.topCall.message({base:{type:'alert',title:'温馨提示',msg:'请选择记录!',icon:'error'}});
			return;
	    }
	    var url= __ctx + $(this).attr('action');
	    if(url==null || url==''){
	 	    $.topCall.message({base:{type:'alert',title:'温馨提示',msg:'未找到配置参数[action]!',icon:'error'}});
			return;
	    }
	   
	   //批量删除时，是否有删除权限的过滤
	   var str = "";
	   var delId ="" ;
	   if($(this).hasClass('fa-remove')){
		   var len=records.length;
		   $(records).each(function(i){
				var obj=this;
				var authorizeRight = obj['authorizeRight'];
				if(authorizeRight){
					//有流程分管授权的，要按权限操作
					var i_del = authorizeRight['i_del'];
					if(i_del){
						// 实例的删除权限
						delId+=obj[pkStr] +",";
					}else{
						str += obj['subject'] +"、";
					}
				}else{
					delId+=obj[pkStr] +",";
				}
				
			});
		   
		   if(delId){
			   delId = delId.substring(0,delId.length-1);
		   }
		   
		   if(url.indexOf('?')!=-1){
			    url+='&'+pkStr+'='+delId;
		   }else{
				url+='?'+pkStr+'='+delId;
		   }
	   }
	   
	  if(str){ 
		   str = str.substring(0,str.length-1);
		   if(delId){
			   $.topCall.message({base:{type:'confirm',title:'温馨提示',msg:"您没有["+str+']这些选中记录的删除权限，不可以删除这些记录；确认要移除其它选中的记录吗？',fn:function(rtn){
				   if(rtn){
					   $.post(url,function(responseText){
							var resultMessage=new com.hotent.form.ResultMessage(responseText);
							if(resultMessage.isSuccess()){
								window.location.reload(true) ;							
							}else{
								$.topCall.message({base:{type:'alert',title:'温馨提示',msg:resultMessage.getMessage(),icon:'error'}});
							}
						 
					   });
				   }else{
					   this.window.close;
				   }
			   }}});
		   }else{
			   $.topCall.message({base:{type:'alert',title:'错误提示',msg:"您没有这些选中记录的删除权限,不可以删除这些记录！" ,icon:'warning'}});
		   }
	   }else{
		   $.topCall.message({base:{type:'confirm',title:'温馨提示',msg:'确认移除所选记录吗？',fn:function(rtn){
			   if(rtn){
				   $.post(url,function(responseText){
						var resultMessage=new com.hotent.form.ResultMessage(responseText);
						if(resultMessage.isSuccess()){
							window.location.reload(true) ;							
						}else{
							$.topCall.message({base:{type:'alert',title:'错误提示',msg:resultMessage.getMessage(),icon:'error'}});
						}
					 
				   });
			   }else{
				   this.window.close;
			   }
		   }}});
	   }
	   
	  
		
	});
}
 $(function() {
	 $.handlerRomveSelect();
});
