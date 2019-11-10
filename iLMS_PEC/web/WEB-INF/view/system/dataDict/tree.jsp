<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<% String typeId = request.getParameter("typeId"); %>
<% String isTile = request.getParameter("isTile"); %>
<html>
<head>
	<title>数据字典</title>
	<%@include file="/commons/include/list.jsp" %>
	<%@include file="/commons/include/incTree.jsp"%>
	<script type="text/javascript">
		var dictTree;
		var selectDictionaryId=0;
		var typeId = "<%=typeId%>";
		//数据字典是否平铺结构
		var isTile = (<%=isTile%>==0);
		$(function(){
			loadDictByTypeId();
		});
		
		/**
		*获取选择的节点。
		*/
		function getSelectNode(){
			dictTree = $.fn.zTree.getZTreeObj("dictTree");
			var nodes = dictTree.getSelectedNodes();
			if(nodes[0]){
				selectDictionaryId=nodes[0].id;
				return nodes[0];
			}
			return null;
		}

		//加载数据字典
		function loadDictByTypeId(expandDepth){ 
			var setting = {
					edit: {
						drag: {
							prev: true,
							next: true,
							isMove:false
						},
						enable: false,
						showRemoveBtn: false,
						showRenameBtn: false
					},
					data: {
						key : {name: "name"},
						simpleData: {enable: true,idKey: "id",pIdKey: "parentId"}
					},
					view: {selectedMulti: false},
					callback:{onRightClick: dictRightClick,
						onDrop: onDrop,beforeDrop:onBeforeDrop }
				}; 
			var url="${ctx}/system/dataDict/getByTypeId"; 
			var params={typeId:typeId};
			
			$.post(url,params,function(result){
				updDicRootNode(result);
				dictTree=$.fn.zTree.init($("#dictTree"), setting,result);

				if(expandDepth&&expandDepth!=0)
				{
					dictTree.expandAll(false);
					var nodes = dictTree.getNodesByFilter(function(node){
						return (node.level < expandDepth);
					});
					if(nodes.length>0){
						for(var i=0;i<nodes.length;i++){
							dictTree.expandNode(nodes[i],true,false);
						}
					}
				}
				else{
					dictTree.expandAll(true);
				}
				
				if(selectDictionaryId>0){
					var node = dictTree.getNodeByParam("id", selectDictionaryId, null);
					dictTree.selectNode(node);
				}
			});
		}
		
		function onBeforeDrop(treeId, treeNodes, targetNode, moveType){
			if(targetNode.isRoot==1 && moveType!="inner"){
				return false;
			}
			return true;
		}
		
		function onDrop(event, treeId, treeNodes, targetNode, moveType) {
			if(targetNode==null || targetNode==undefined) return false;
			var targetId=targetNode.id;
			var dragId=treeNodes[0].id;
			var params={targetId:targetId,dragId:dragId,moveType:moveType};
			$.post(url,params,function(result){});
		}
		
		//标记根节点。
		function updDicRootNode(result){
			for(var i=0;i<result.length;i++){
				var node=result[i];
				if(node.parentId=='-1'){
					node.isRoot=1;
					node.parentId==0;
					node.icon = "fa fa-home";
					break;
				}
			}
		}
		
		//编辑字典数据
		function editDict(){
			var selectNode=getSelectNode();
			var id=selectNode.id;
			
			if(selectNode.isRoot==1){
				return ; 
			}
			var url="${ctx}/system/dataDict/dataDictEdit?id=" + id +"&isAdd=0";
			url=url.getNewUrl();
			showDialog({url:url,width:600,height:300,title:"编辑数据字典"});
		}
		
		function dictRightClick(e, treeId, treeNode){
			if (treeNode) {
				dictTree.selectNode(treeNode);
				 
				var isfolder=treeNode.isFolder;
				var h=$(window).height() ;
				var w=$(window).width() ;
				var menuWidth=100;
				var menuHeight=75;
				var menu=null;
				if(treeNode!=null){
					// 根节点
					if(!treeNode.parentId){
						menu=$('#rootMenu');
					}else{
						if(isTile){
							menu=$('#tileDataDictMenu');							
						}
						else{
							menu=$('#dataDictMenu');
						}
					}
				}
				var x=e.pageX,y=e.pageY;
				if(e.pageY+menuHeight>h){
					y=e.pageY-menuHeight;
				} 
				if(e.pageX+menuWidth>w){
					x=e.pageX-menuWidth;
				}
				  menu.menu('show', {
				    left: x,
				    top: y
				});
				menu.show({ top: y, left: x });
			}
		}
		//菜单对应项
		function menuHandler(item){
			if('add'==item.name){
				addDict();
			}else if('delete'==item.name){
				delDict();
			}else if('sort'==item.name){
				sortDict();
			}else if('edit'==item.name){
				editDict();
			}
		};
		
		//增加字典
		function addDict(){
			var selectNode=getSelectNode();
			var id=selectNode.id;
			var isRoot = 0;
			if(selectNode.isRoot) isRoot = 1;
			var url="${ctx}/system/dataDict/dataDictEdit?id=" + id +"&isAdd=1&isRoot="+isRoot;
			url=url.getNewUrl();
			showDialog({url:url,width:600,height:300,title:"增加数据字典"});
		}
		//数字字典排序
		function sortDict(){
			var selectNode=getSelectNode();
			var id=selectNode.id;
			var url="${ctx}/system/dataDict/sortList?id=" + id;
			url=url.getNewUrl();
			showDialog({url:url,width:600,height:300,title:"数据字典排序"});
		}
		//删除字典
		function delDict(){
			var callback=function(rtn){
				if(!rtn) return;
				var selectNode=getSelectNode();
				var id=selectNode.id;
 			
				var url="${ctx}/system/dataDict/remove";
				var params={id:id};
				$.post(url,params,function(responseText){
					var obj=new  com.hotent.form.ResultMessage(responseText);
					if(obj.isSuccess()){
						$.topCall.success("删除字典项成功!",function(){
							loadDictByTypeId();
						}); 
					}
					else{
						$.topCall.error("删除字典项失败!");
					}
				});
			}; 
			$.topCall.confirm('温馨提示','将删除该字典项及下面的所有字典项目，确认删除吗？',callback);
		}
		
		function showDialog(param){
			var baseConfig = {title:'',width:450, height:300, modal:true,resizable:true,passConf:{callback:loadDictByTypeId}};
			$.extend(baseConfig,param);
			$.topCall.dialog({
				  src : baseConfig.url,
				  base : baseConfig
			  });
		};
	</script>
	 
	
</head>
<body class="easyui-layout">
		<div data-options="region:'center',split:true,border:false,title:'数据字典'"  style="width:250px;overflow:auto;"  >
			<div id="dictTree" class="ztree"> </div>
		</div>
		
		<div id="rootMenu" class="easyui-menu" data-options="onClick:menuHandler"  style="width:120px;">
		     <div data-options="name:'add'" >增加字典项</div>
		     <div data-options="iconCls:'icon-save',name:'sort'">子节点排序</div>	
		</div>
		<div id="tileDataDictMenu" class="easyui-menu" data-options="onClick:menuHandler"  style="width:120px;">
		     <div data-options="name:'edit'" >编辑字典项</div>
		     <div data-options="name:'delete'" >删除字典项</div>
		</div>
		<div id="dataDictMenu" class="easyui-menu" data-options="onClick:menuHandler"  style="width:120px;">
		     <div data-options="name:'add'" >增加字典项</div>
		     <div data-options="name:'edit'" >编辑字典项</div>
		     <div data-options="name:'delete'" >删除字典项</div>
		     <div data-options="iconCls:'icon-save',name:'sort'">子节点排序</div>	    
		</div>
</body>
</html>

