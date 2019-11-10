var isSingle = window.isSingleSelector;
var bosTree;
var treeNodes = new Array();
var beforeShowEditor= null;
var afterSaveEditor= null;
var seqNoEditor = null;
//初始化原属性设置到设置区域
function addSetAttrData(setAttrData){
	if(!setAttrData||setAttrData.length<=0){
		return;
	}
	for(var i=0;i<setAttrData.length;i++){
		var rowData = setAttrData[i];
		addToSetAttrTable(rowData);
	}
}

function mirrorObjInit(){
	setTimeout(function(){
		seqNoEditor=InitMirror.getById("seqNoScript");
		afterSaveEditor=InitMirror.getById("afterSaveScript");
		beforeShowEditor=InitMirror.getById("beforeShowScript");
	},200);
	
	setTimeout(function(){//初始化编辑进来的boAttr
		if(window.conf.boAttrName){
			var selectBoAttr = getBoAttrByName(window.conf.boAttrName);
			onClickRow(null,selectBoAttr);		
		}
	},1000);
}

//设置查询BO数据
function setBosTree(aryBo){
	if(!aryBo||aryBo.length<=0){
		return;
	}
	
	var boCodes = '';
	for(var i=0;i<aryBo.length;i++){
		boCodes += aryBo[i].code+",";
	}
	if(boCodes!=''){
		boCodes = boCodes.substring(0,boCodes.length-1);
		var path = __ctx + '/bo/def/listJsonByCodes';
		$.ajaxSetup({async:false});  //同步
		$.post(path,{boCodes:boCodes},function(boDatas){			
		   if(boDatas){
			   setBosTreeNodes(boDatas);
		   }
	    });
		$.ajaxSetup({async:true});  //同步
	}
}


//处理返回的BO数据
function setBosTreeNodes(boDatas){
	if(!boDatas||boDatas.length<=0){
		return;
	}  
	for(var i=0;i<boDatas.length;i++){
		var bo = boDatas[i];
		setBoToNode(bo,0,bo.code,null);
	}
}

//把BO的内容设置成树节点
function setBoToNode(bo,pId,code,refAttr){
	if(!bo){
		return;
	}  
	var treeObj = new getBosTreeNodeObj(bo.id, pId, code, bo,"bo",refAttr);
	treeNodes.push(treeObj);
	
	var boattributeList = bo.bOAttributeList;
	if(!boattributeList||boattributeList.length<=0){
		return;
	}
	
	for(var i=0;i<boattributeList.length;i++){
		var attr = boattributeList[i];
		if(attr.type=='ref'){
			//依赖业务对象的情况
			if(attr.refBODef){
				setBoToNode(attr.refBODef,bo.id,code,attr);
			}
		}else{
			var attrTreeObj = new getBosTreeNodeObj(attr.id, attr.defId,code, attr,"attr","","");
			treeNodes.push(attrTreeObj);
		}
	}
}

//建立树节点对象
function getBosTreeNodeObj(id,parentId,code,me,myObjectType,refAttr){
	var obj = new Object();
	obj.id = id;
	obj.parentId = parentId;
	var desc = me.desc;
	var name = me.name;
	//依赖对象的对应属性的类型
	var refType = ""
	if(refAttr){
		refType = refAttr.refType;
	}
	
	if(!desc){
		desc = me.name;
	}
	
	//如果是依赖对象类型的，要用依赖属性名称做路径
	if(myObjectType=='bo'&&(refType=="hasmany"||refType=="hasone")&&refAttr){
		desc = refAttr.desc;
		name = refAttr.name;
		if(!desc){
			desc = name;
		}
		//设置依赖属性
		obj.refAttr = refAttr;
	}
	
	obj.name = desc;
	obj.title = name;
	obj.myObjectType = myObjectType;
	obj.refType = refType;
	obj.myObject = me;
	obj.defCode = code;
	return obj;	    	
}

function deleteSelect(userId,account){
	var records  = $('#userSelectGrid').datagrid('getRows');
	for(var i=0;i<records.length;i++){
		if(records[i]['userId']==userId || records[i]['account']==account){
			 $('#userSelectGrid').datagrid('deleteRow',i);
		}
	}
}


function loadBosTree(){
	// 根据类型，加载树
	var setting = {
		data: {
			key : {
				name: "name",
				title: "title"
			},
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId",
				rootPId: null
			}
		},
		async: {
			enable:false
		},
		view: {
			nameIsHTML: true,
			selectedMulti: false,
			showIconFont:true,
			showTitle:true,
			showIcon: showIconForTree
		},
		edit: {
			drag: {
				prev: false,inner: false,next: false,isMove:false
			},
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},
		callback:{
			onClick: zTreeOnLeftClick,
			beforeDrop: null,
			onDrop: null
		}
	};
	bosTree = $.fn.zTree.init($("#bosTree"), setting,treeNodes);
	bosTree.expandAll(true);
};




/// 树展示辅助方法
function showIconForTree(treeId, treeNode) {
	if(treeNode.icon == 'false') return false;
	return true; //yun //o/false 都无法 
}

function expand(){
	bosTree.expandAll(true);
}


//设置id的节点为当前资源节点。
function setSelectNode(id){
	var node = bosTree.getNodeByParam("id",id); 
	boTree.selectNode(node,false);
	return node;
};


function zTreeOnLeftClick(event, treeId, treeNode) {
	var type = treeNode.myObjectType;
	if(type!="attr"){
		return;
	}
	var me = treeNode.myObject;
	if(me){
		//获得特定层级下自己的相对路径
		var name = getTheFloorAttrName("",treeNode,1,0,false);
		
		var boAttr = getBoAttrByName(name);
		if(boAttr){//编辑
			$("#defId").val(boAttr.defId);
			$("#defCode").val(boAttr.defCode);
			$("#id").val(boAttr.id);
			$("#name").val(boAttr.name);
			$("#desc").val(boAttr.desc);
			$("#setDesc").val(boAttr.setDesc);
			$("#myName").val(boAttr.name);
			$("#myDesc").val(boAttr.desc);
			beforeShowEditor.editor.setCode(boAttr.beforeShowScript);
			afterSaveEditor.editor.setCode(boAttr.afterSaveScript==null?"":boAttr.afterSaveScript);
			seqNoEditor.editor.setCode(boAttr.seqNoScript);
		}else{//新增，保存用到的值设置
			$("#defId").val(me.defId);
			$("#defCode").val(treeNode.defCode);
			$("#id").val(me.id);
			$("#name").val(name);
			$("#desc").val(me.desc);
			var setDesc = me.desc;
			if(!setDesc){
				setDesc = me.name;
			}
			$("#setDesc").val(setDesc);
			//显示用的值
			$("#myName").html(name);
			$("#myDesc").html(me.desc);
			//要清空的值
			beforeShowEditor.editor.setCode("");
			afterSaveEditor.editor.setCode("");
			seqNoEditor.editor.setCode("");
		}
		$("#setType").val("script");
		isShowSetScriptTab("script");
	}
	
}


//通过nodeId获取自己特定级别下的相对路径
function getTheFloorAttrName(name,node,floor,myIndex,myFloorAttrMark){
	if(node){
		//查询级数：
		myIndex = myIndex+1;
		//层级没自己的高时直接返回自己
		var refType = "";
		if(node.refType){
			refType = node.refType.toLowerCase();
		}
		if(node.myObjectType=="bo"&&refType=="hasmany"){
			//是hasMany才会有行运算
			if(myIndex==2){
				myFloorAttrMark = true;
			}
			name = node.title+"[*]."+name;
		}else{	
			name = node.title+"."+name;
		}
		
		$("#myNameSelectName").val("no");
		if(myFloorAttrMark){
			$("#mySetMyNameTr").show();
		}else{
			$("#mySetMyNameTr").hide();
		} 
		
		if(node.level<=floor){
			name = name.substring(0,name.length-1);
			return name;
		}
		var parentNode = node.getParentNode();
		if(parentNode){
			return getTheFloorAttrName(name,parentNode,floor,myIndex,myFloorAttrMark);
		}
	}
	return "";
}


//快速搜索
function initQuickFind(){
	
	//搜索树节点名称	
	searchTreeAttrName();
	
	//搜索已经设置的字段内容
	searchSetAttrName();
}

//左击事件
function onClickRow(rowIndex,rowData){
	$("#defId").val(rowData.defId);
	$("#defCode").val(rowData.defCode);
	$("#id").val(rowData.id);
	$("#name").val(rowData.name);
	$("#desc").val(rowData.desc);
	$("#setDesc").val(rowData.setDesc);
	$("#myName").html(rowData.name);
	//解析属性的路径
	var name = rowData.name;
	if(name){
		var pos = name.lastIndexOf("[");
		var oldIndexstr =  name.substring(pos+1, pos+2);
		var myVal = "";
		if(oldIndexstr=="i"){
			myVal = "row";
		}else{
			myVal = "no";
		}
		$("#myNameSelectName").val(myVal);
	}
	
	$("#myDesc").html(rowData.desc);
	
	beforeShowEditor.editor.setCode(rowData.beforeShowScript==null?"":rowData.beforeShowScript);
	afterSaveEditor.editor.setCode(rowData.afterSaveScript==null?"":rowData.afterSaveScript);
	seqNoEditor.editor.setCode(rowData.seqNoScript);
	
	$("#flowKey").val(rowData.flowKey);
	$("#nodeId").val(rowData.nodeId);
	
	$("#setType").val(rowData.setType);
	isShowSetScriptTab(rowData.setType);
}

function getBoAttrByName(name){
	var setAttrData = window.passConf.setAttrData;
	for(var i=0;i<setAttrData.length;i++){
		var temp=setAttrData[i];
		if(temp.name==name){
			return temp;
		}
	}
	return null;
}

//搜索树节点名称	
function searchTreeAttrName() { 
	
	$("#findName").bind('keyup',function(){
		var str = $(this).val();
		if(!str){
			return;
		}
		var nodes = bosTree.getNodesByParamFuzzy("name", str, null);
		if(nodes&&nodes.length>0){
			//展示第一下相似的节点
		//	bosTree.expandNode(nodes[0], true, true , false ,false );
			bosTree.selectNode(nodes[0],false);
		}
	});

}

//搜索已经设置的字段内容	
function searchSetAttrName() { 
	$("#findSetAttr").bind('keyup',function(){
		var str = $(this).val();
		if(!str)return;
		var records  = $('#setAttrGrid').datagrid('getRows');
		for(var i=0;i<records.length;i++){
			if(records[i]['setDesc'].indexOf(str)>-1){
				var rowData = records[i];
				 $('#setAttrGrid').datagrid('deleteRow',i);
				 $('#setAttrGrid').datagrid('insertRow',{index:0, row: rowData});
			}
		} 
	});

}

//获取当前选中节点
function getSelectNode(){
	bosTree = $.fn.zTree.getZTreeObj("bosTree");
	var nodes  = bosTree.getSelectedNodes();
	var node   = nodes[0];
	return node;
}	


//添加设置
function addSetBoAttr(){
	var defId = $("#defId").val();
	var id = $("#id").val();
	var name = $("#name").val();
	var desc = $("#desc").val();
	var defCode = $("#defCode").val();
	if(!name||!defCode){
		$.topCall.message({base:{type:'alert',title:'错误提示',msg:"字段信息有错，请重新选择！",icon:'error'}});
		return;
	}
	
	var setDesc = $("#setDesc").val();
	if(!setDesc){
		$.topCall.message({base:{type:'alert',title:'错误提示',msg:"设置描述不能为空，请填写！",icon:'error'}});
		return;
	}
	var setType = $("#setType").val();
	var flowKey = $("#flowKey").val();
	var nodeId = $("#nodeId").val();
	var beforeShowScript = "";
	var afterSaveScript = "";
	var seqNoScript = "";
	if(setType!="seqNo"){
		beforeShowScript=beforeShowEditor.editor.getCode();
		afterSaveScript=afterSaveEditor.editor.getCode();
	}else{
		seqNoScript = seqNoEditor.editor.getCode();
	}
	var setAttrObj = {
			id:id,
			name:name,
			desc:desc,
			setDesc:setDesc,
			setType:setType,
			defId:defId,
			defCode:defCode,
			flowKey:flowKey,
			nodeId:nodeId,
			beforeShowScript:beforeShowScript,
			afterSaveScript:afterSaveScript,
			seqNoScript:seqNoScript
	}
	
	addToSetAttrTable(setAttrObj);
}	

//插入设置内容到选中区域
function addToSetAttrTable(rowData){
	
	var records  = $('#setAttrGrid').datagrid('getRows');
	if(isSingle){
		if(records.length == 1)		
		$('#setAttrGrid').datagrid('deleteRow',0);
	}
	
	for(var i=0;i<records.length;i++){		
		//判断字段是否有设置过
		//如：testMain.sub_testSub1[*].testSub1_1Attr2、
		//   testMain.sub_testSub1[].testSub1_1Attr2
		//   testMain.sub_testSub1[i].testSub1_1Attr2    表示为同一个字段，即忽视最后一个[]里面的内容
		var oldName = records[i]['name'];
		var myOldName = oldName;
		var newName = rowData['name'];
		var myNewName = newName;
		
		//当两个字段对比不相等时再去做特殊判断(hasMany的情况)
		if(myOldName!=myNewName){
			var oldPos = oldName.lastIndexOf("[");
			var oldIndexstr =  oldName.substring(oldPos+1, oldPos+2);
			
			if(oldIndexstr!="]"){
				myOldName = oldName.substring(0, oldPos)  + oldName.substring(oldPos+2, oldName.length);
			}
			
			var newPos = newName.lastIndexOf("[");
			var newIndexstr =  newName.substring(newPos+1, newPos+2);		

			if(newIndexstr!="]"){
				myNewName = newName.substring(0, newPos)  + newName.substring(newPos+2, newName.length);
			}
		}
		
		if(myOldName==myNewName){//删除旧的
			$('#setAttrGrid').datagrid('deleteRow',i);
		}
	} 
	$('#setAttrGrid').datagrid('appendRow',rowData);//添加数据
    
}	

//删除设置的属性行
function deleteSetAttr(obj,name){
	var records  = $('#setAttrGrid').datagrid('getRows');
	for(var i=0;i<records.length;i++){
		if(records[i]['name']==name){
			 $('#setAttrGrid').datagrid('deleteRow',i);
		}
	}
}

//设置类型的变更触发方法
function changeSetType(obj){
	if(obj){
		var me = $(obj);
		var type = me.val();
		isShowSetScriptTab(type);
	}
}

//运算设置事件
function changeMyName(obj){
	if(!obj){
		return;
	}

	var val=$(obj).val();
	var myName = $("#myName").text();
	if(!myName){
		return;
	}
	var indexstr = null;
	var pos = myName.lastIndexOf("[");
	var oldIndexstr =  myName.substring(pos+1, pos+2)
	
	if(val=='row'){
		//行设置时
		if(oldIndexstr==indexstr){
	    	return;
	    }
		indexstr = "i";
		pos = myName.lastIndexOf("*");	
    	if(pos>0){
    		myName = myName.substring(0, pos) + indexstr + myName.substring(pos+1, myName.length);
    	}
	}else{
		//无设置时
		if(oldIndexstr=="]"){
			return;
		}
		indexstr = "*";
    	if(pos>0){
    		myName = myName.substring(0, pos+1) + indexstr + myName.substring(pos+2, myName.length);
    	}
	}
	
	$("#myName").html(myName);
	$("#name").val(myName);
}

//是否显示脚本
function isShowSetScriptTab(type){
	if(type=='seqNo'){
		$('#setScriptTab').hide();
		$('#setSeqNoTab').show();
	}else{
		$('#setScriptTab').show();
		$('#setSeqNoTab').hide();
	}
}


//确定保存设置
function saveSetAttr(){
	var records  = $('#setAttrGrid').datagrid('getRows');
	return records;
}