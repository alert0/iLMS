
//初始化BO
function initSetBos(boSaveMode,arrayBo){
	//保存方式
	if(boSaveMode){
		$("input[name='boSaveMode']").each(function(){
			if($(this).val()==boSaveMode){
				$(this).attr("checked",true);
			}
		});
	}

	//业务对象初始化
	if(!arrayBo||arrayBo.length<=0){
		return;
	}
	initBoTable(arrayBo);
}

//初始化BOATTR
function initSetBoAttr(arraySetBoAttr){
	if(!arraySetBoAttr||arraySetBoAttr.length<=0){
		return;
	}
	for(var i=0;i<arraySetBoAttr.length;i++){
		var aryBoAttr = arraySetBoAttr[i];
		var nodeId = aryBoAttr[0].nodeId;
		initBoAttrTable(aryBoAttr,nodeId);
	}
	
}
//选择BO
function selectSetBos(boData){
	var boSaveMode=$("input[name='boSaveMode']:checked").val();
	
	//获取要初始化选择页面的数据
	if(!boData){
		boData = getBos();
	}
	var passConf={
			aryBo:boData
	}
	//选择BO页面
	new BoSelectDialog({passConf:passConf,isSingle:false,boSaveMode:boSaveMode,callback:function(aryBo,records,dialog){	
		if(aryBo){
			initBoTable(aryBo);
		}
	}}).show();

}

//获取BO数据
function getBos(){
	//获取BO数据
	var boData = new Array();

	var myBoTable = $('#myBoTable');
	$("tr",myBoTable).each(function(index){
		if(index!=0){
			var me = $(this);
			var myBoJsonStr = me.attr("myBoJson");
			if(myBoJsonStr){
				var myBo = $.parseJSON(myBoJsonStr);
				var myCheckbox = $("input[name='isRequired']",me);
				if(myCheckbox.is(":checked")){
					myBo.isRequired='0';
				}else{
					myBo.isRequired='1';
				}
				boData.push(myBo);
			}
		}
	});
	
	return boData;	
}

//创建bo数组对应的tr内容
function initBoTable(aryBo){
	if(!aryBo){
		return;
	}
	var myBoTable = $('#myBoTable');

	//清空之前的数据
	cleanMyBo();
	
	//重新设置新的数据
	if(aryBo.length<=0){
		return;
	}
	
	for(var i=0;i<aryBo.length;i++){
		var bo = aryBo[i];
		var $tr = myBoTableTr(bo,i+1);
		if($tr){
			myBoTable.append($tr);
		}
	}

}

//创建bo对应的tr
function myBoTableTr(bo,index){
	if(bo==null){
		return;
	}
	var boStr = JSON.stringify(bo);
	var $tr = $("<tr></tr>");
	$tr.attr("myBoTableTrId",bo.code);
	$tr.attr("myBoJson",boStr);
	$tr.append($("<td>"+index+"</td>"));
	$tr.append($("<td>"+bo.name+"</td>"));
	$tr.append($("<td>"+bo.desc+"</td>"));
	$tr.append($("<td>"+bo.code+"</td>"));
//	if(bo.isRequired=='0'){
//		$tr.append($("<td><input type='checkbox' id='"+bo.code+"_checkbox' name='isRequired' value='0' checked='checked' /></td>"));
//	}else{
//		$tr.append($("<td><input type='checkbox' id='"+bo.code+"_checkbox' name='isRequired' value='0' /></td>"));
//	}
	$tr.append($("<td><a href='#' onclick='deleteMyBo(this)' class='fa fa-remove' style='width:70px' >删除</a></td>"));
	return $tr;
}

//删除对应的BO内容
function deleteMyBo(obj){

	$.topCall.message({base:{type:'confirm',title:'温馨提示',msg:" 确认要删除该记录吗？",fn:function(r){
		if(r){
			if(!obj){
				return;
			}
			var $tr = $(obj).closest("tr");
			var code = $tr.attr("myBoTableTrId");
			$tr.remove();
			if(code){
				var aryBo = getBos();
				if(!aryBo||aryBo.length<=0){
					return;
				}
				//重新初始化BO列表数据
				initBoTable(aryBo);
			}
		}
	}}});

}


//删除前要确认的 对应的节点的BO设置属性内容方法
function cleanMySelectBo(){
	
	$.topCall.message({base:{type:'confirm',title:'温馨提示',msg:" 确认要清空选择的BO吗？",fn:function(r){
		if(r){
			cleanMyBo();
		}
	}}});
	
}

//删除对应的节点的BO设置属性内容
function cleanMyBo(){
	var myBoTable = $('#myBoTable');
	//清空之前的数据
	$("tr",myBoTable).each(function(index){
		if(index!=0){
			$(this).remove();
		}
	});
	
}


//获取BO设置属性数据
function getBoAttrs(nodeId){
	//获取BO属性数据
	var aryBoAttrStr = $('#myBoAttrArryJson_'+nodeId).val();
	var boAttrData = "";
	if(aryBoAttrStr){
		boAttrData = $.parseJSON(aryBoAttrStr);
	}
	return boAttrData;	
}

//创建bo属性设置的TABLE内容
function initBoAttrTable(aryBoAttr,nodeId){
	if(!aryBoAttr){
		return;
	}
	
	var myBoAttrTable = $('#boAttrSelectTable_'+nodeId);
	
	cleanNodeBoAttr(nodeId);
	
	//重新设置新的数据
	if(aryBoAttr.length>0){
		var aryBoAttrStr = JSON.stringify(aryBoAttr);
		$('#myBoAttrArryJson_'+nodeId).val(aryBoAttrStr);
		for(var i=0;i<aryBoAttr.length;i++){
			var boAttr = aryBoAttr[i];
			var $tr = myBoAttrTableTr(boAttr,i+1);
			myBoAttrTable.append($tr);
		}
		$('#boAttrSelectTr_'+nodeId).show();
	}else{
		$('#boAttrSelectTr_'+nodeId).hide();
	}

}


//创建boAttr对应的tr
function myBoAttrTableTr(boAttr,index){
	var boAttrStr = JSON.stringify(boAttr);
	var $tr = $("<tr></tr>");
	$tr.attr("myBoAttrTableTrId",boAttr.name);
	$tr.attr("myBoAttrJson",boAttrStr);			
	$tr.append($("<td>"+index+"</td>"));
	$tr.append($("<td>"+boAttr.name+"</td>"));
	$tr.append($("<td>"+boAttr.setDesc+"</td>"));
	if(boAttr.setType=='seqNo'){
		$tr.append($("<td><span style='color: red;'>流水号</span></td>"));
	}else{
		$tr.append($("<td>脚本</td>"));
	}
	var btn="<a href='javaScript:void(0)' onclick='selectSetNodeBos(\""+boAttr.name+"\")' class='fa fa-edit' >编辑</a>";
	btn+="<a href='javaScript:void(0)' onclick='deleteMyBoAttr(this,\""+boAttr.nodeId+"\")' class='fa fa-remove' >删除</a>";
	$tr.append($("<td>"+btn+"</td>"));
	return $tr;
}


//打开设置BO属性
function selectSetNodeBos(boAttrName){
	var flowId =  $("#flowId").val();
	var flowKey = $("#flowKey").val();
	var nodeId = $("#nodeId").val();
	var nodeName = $("#nodeId").find("option:selected").text();
	var boData = getBos();
	if(!boData||boData.length<=0){
		$.topCall.message({base:{type:'alert',title:'温馨提示',msg:'请选择BO再进入设置！',icon:'info'}});
		return;
	}
	var setAttrData = getBoAttrs(nodeId);
	var passConf={
			flowId:flowId,
			flowKey:flowKey,
			nodeId:nodeId,
			nodeName:nodeName,
			aryBo:boData,
			setAttrData:setAttrData,
			boAttrName:boAttrName
	}
	//流程BO设置页面
	new BoSetDialog({passConf:passConf,isSingle:false,callback:function(records,dialog){	
		if(records){
			//插入设置属性的内容
			initBoAttrTable(records,nodeId); 
		}
	}}).show();
}


//删除设置的属性行
function deleteMyBoAttr(obj,nodeId){
	
	$.topCall.message({base:{type:'confirm',title:'温馨提示',msg:" 确认要删除该记录吗？",fn:function(r){
		if(r){
			if(!obj){
				return;
			}
			var $tr = $(obj).closest("tr");
			var name = $tr.attr("myBoAttrTableTrId");
			$tr.remove();
			if(name){
				var aryBoAttrStr = $('#myBoAttrArryJson_'+nodeId).val();
				if(!aryBoAttrStr){
					return;
				}
				var aryBoAttr = $.parseJSON(aryBoAttrStr);
				if(!aryBoAttr){
					return;
				}
				var newAryBoAttr=[];
				for(var i=0;i<aryBoAttr.length;i++){
					var boAttr = aryBoAttr[i];
					if(boAttr.name!=name){
						newAryBoAttr.push(boAttr);
					}
				}
				//重新初始化BO设置属性列表数据
				initBoAttrTable(newAryBoAttr,nodeId);
			}
		}
	}}});
	
}


//删除所有节点的BO设置属性内容
function cleanALlBoAttr(){
	$.topCall.message({base:{type:'confirm',title:'温馨提示',msg:" 确认要清空所有节点的设置吗？",fn:function(r){
		if(r){
			$(".myNodeShowTr").each(function(index){
				var nodeId = $(this).attr("nodeId");
				if(nodeId){
					cleanNodeBoAttr(nodeId);
 					$('#boAttrSelectTr_'+nodeId).hide();
				}
			});
		}
	}}});

}


//删除对应的节点的BO设置属性内容
function cleanMyNodeBoAttr(){
	var nodeId = $("#nodeId").val();
	$.topCall.message({base:{type:'confirm',title:'温馨提示',msg:" 确认要清空选中节点（"+nodeId+"）的设置吗？",fn:function(r){
		if(r){
			cleanNodeBoAttr(nodeId);
			$('#boAttrSelectTr_'+nodeId).hide();
		}
	}}});

}

//删除对应的节点的BO设置属性内容
function cleanNodeBoAttr(nodeId){

	if(!nodeId){
		nodeId = $("#nodeId").val();
	}
    var myBoAttrTable = $('#boAttrSelectTable_'+nodeId);
	//清空之前的数据
	$("tr",myBoAttrTable).each(function(index){
		if(index!=0){
			$(this).remove();
		}
	});
	$('#myBoAttrArryJson_'+nodeId).val("");

}

//保存内容
function saveSetBos(){
	var mark = null
	var root = {};
	
	//获取流程信息
	mark = getFlowData(root);
	if(!mark){
		return;
	}
	
	//获取选中的BO内容
	mark = getBoArryData(root);
	
	//获取设置的BO字段内容
	getBoAttrArryData(root);
	
	//保存数据
	var rootStr = JSON.stringify(root);
	var path = __ctx+'/flow/def/saveSetBos'
	$.post(path,{root:rootStr},function(data){	
		if(data){
			if(data.result==1){
				$.topCall.message({base:{type:'alert',title:'温馨提示',msg:data.message,icon:'info'}});
			}else{
				$.topCall.message({base:{type:'alert',title:'错误提示',msg:data.message,icon:'error'}});
			}
		}				
	});
}

//获取流程信息
function getFlowData(root){
	var flowId = $("#flowId").val();
	var flowKey = $("#flowKey").val();
	if(!flowId||!flowKey){
		$.topCall.error('流程相关信息这空！');
		return;
	}
	root.flowId = flowId;
	root.flowKey = flowKey;
	return root;
}

//获取选中的BO内容
function getBoArryData(root){
	var boSaveMode = $("input[name='boSaveMode']").val();
	root.boSaveMode = boSaveMode;
	var arrayBo = getBos();
	root.arrayBo = arrayBo;
	return root;
}

//获取设置的BO字段内容
function getBoAttrArryData(root){

	var arraySetBoAttr = new Array();
	$("input[name=myBoAttrArryJson]").each(function(){
		var me = $(this);
		var aryBoAttrStr = me.val();
		if(aryBoAttrStr){
			var aryBoAttr = $.parseJSON(aryBoAttrStr);
			arraySetBoAttr.push(aryBoAttr);
		}
	}); 
	if(arraySetBoAttr.length>0){
		root.arraySetBoAttr=arraySetBoAttr;
	}
	return root;
}