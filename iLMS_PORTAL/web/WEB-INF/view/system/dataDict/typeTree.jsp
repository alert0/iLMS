<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<html>
<head>
<title>数据字典</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
	
	<base target="_self"/> 
	<script type="text/javascript">
		var rootId=0;
		//根节点右键菜单
		var typeMenu;
		//根节点右键菜单
		var rootMenu;
		//私有节点右键菜单
		var priTypeMenu;
		var typeId=1;
		//树
		var typeTree;
		
		$(function(){
			//加载树
			loadTree();  
		});
	  
		//加载树
		function loadTree(expandByDepth){
			var setting = {
				async: {enable: false},
				data: {
					key:{name:"text"}
				},
				view: {
					selectedMulti: false
				},
				edit: {
					enable: false,
					showRemoveBtn: false,
					showRenameBtn: false
				},
				callback:{
					onClick: zTreeOnLeftClick,
					onRightClick: zTreeOnRightClick
				}
			};
			var url=__ctx + "/system/sysType/getTypesByKey"; 
			var params={typeKey:'DIC'}; 
			$.post(url,params,function(result){
				updDicRootNode(result);
				typeTree=$.fn.zTree.init($("#typeTree"), setting,result);
				
	            if(expandByDepth&&expandByDepth!=0)
	            {
	                typeTree.expandAll(false);
					var nodes = typeTree.getNodesByFilter(function(node){
						return (node.level < expandByDepth);
					});
					if(nodes.length>0){
						for(var i=0;i<nodes.length;i++){
							typeTree.expandNode(nodes[i],true,false);
						}
					}
	            }
	            else
	            {
	            	typeTree.expandAll(true);
	            }
			});
		};

		var curSelectNode;
		//左击
		function zTreeOnLeftClick(event, treeId, treeNode){
			if(treeNode.isRoot) return;
			curSelectNode=treeNode;
			var typeId = treeNode.id;
			var url=__ctx +"/system/dataDict/tree?typeId="+typeId+"&isTile="+treeNode.struType;
			$("#listFrame").attr("src",url); 
		}; 
		
		//获取根节点并修改节点的图标。
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
		
		//树右击事件
		function zTreeOnRightClick(e, treeId, treeNode){
			if (treeNode) {
				typeTree.selectNode(treeNode);
				var isfolder=treeNode.isFolder;
				var h=$(window).height() ;
				var w=$(window).width() ;
				var menuWidth=100;
				var menuHeight=75;
				var menu=null;
				if(treeNode!=null){
					// 根节点
					if(treeNode.isRoot==1){
						menu=$('#rootMenu');
					}else if(treeNode.ownerId==0){
						menu=$('#typeMenu');
					}else{
						menu=$('#priTypeMenu');
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
		};
		
		//展开收起
		function treeExpandAll(type){ 
			typeTree = $.fn.zTree.getZTreeObj("typeTree");
			typeTree.expandAll(type);
		};
		
		//添加节点
		function addNode(isPriNode){
			var selectNode=getSelectNode();
			if(!selectNode)return;   
			var isRoot = 0;
			if(selectNode.isRoot) isRoot = 1;  
			//isPriNode标记添加的节点是公共节点还是私有节点  ，0 ==公共节点 ，1==私有节点
			var url="${ctx}/system/sysType/sysTypeEdit?parentId="+selectNode.id+"&isPriNode="+isPriNode+"&isRoot="+isRoot;
		 	url=url.getNewUrl();
		 	showDialog({url:url,width:600,height:300,title:"添加数据字典分类"});
		};
		function edit(){
			var selectNode=getSelectNode();
			if(!selectNode)return;   
			//isPriNode标记添加的节点是公共节点还是私有节点  ，0 ==公共节点 ，1==私有节点
			var url="${ctx}/system/sysType/sysTypeEdit?id="+selectNode.id;
		 	url=url.getNewUrl();
		 	showDialog({url:url,width:600,height:300,title:"编辑数据字典分类"});
		};
		
		//节点排序
		function sortNode(){
			var selectNode=getSelectNode();
			if(!selectNode)return;
			var nodeId=selectNode.id;
			var url='${ctx}/system/sysType/sysTypeSortList?id='+nodeId;
		 	url=url.getNewUrl();
		 	showDialog({url:url,width:300,height:300,title:"数据字典分类排序"});
		};
		//删除
		function delNode(){
			var selectNode=getSelectNode();
			var nodeId=selectNode.id;
			if(nodeId==rootId){
				$.topCall.warn('该节点为系统分类 ,不允许该操作');
				return;
			}
		 	var callback = function(rtn) {
		 		if(!rtn) return;
		 		var url="${ctx}/system/sysType/remove";
		 		var params={id:nodeId};
		 		$.post(url,params,function(responseText){
		 			var obj=new com.hotent.form.ResultMessage(responseText);
		 			if(obj.isSuccess()){//成功
		 				typeTree.removeNode(selectNode);
		 				$("#listFrame").attr("src","about:blank");
		 			}
		 			else{
		 				$.topCall.error(obj.getMessage());
		 			}
		 			refreshNode();
		 		});
			};
			$.topCall.confirm('温馨提示','确定删除？',callback);
		};
		
		//选择资源节点。
		function getSelectNode(){
			typeTree = $.fn.zTree.getZTreeObj("typeTree");
			var nodes  = typeTree.getSelectedNodes();
			var node   = nodes[0];
			return node;
		}; 
		//刷新
		function refreshNode(){
			loadTree();
		};
		
		
		//菜单对应项
		function menuHandler(item){
			if('add'==item.name){
				addNode(0);
			}else if('remove'==item.name){
				delNode();
			}else if('sort'==item.name){
				sortNode();
			}else if('priadd'==item.name){
				addNode(1);
			}else if('edit'==item.name){
				edit();
			}else if('fresh'==item.name){
				refreshNode();
			}
		};
		
		function showDialog(param){
			var baseConfig = {title:'',width:450, height:300, modal:true,resizable:true,passConf:{loadTree:loadTree}};
			$.extend(baseConfig,param);
			$.topCall.dialog({
				  src : baseConfig.url,
				  base : baseConfig
			  });
		};
	</script>
</head>
<body>
	<div class="easyui-layout" style="" fit="true" scroll="no">
		<div data-options="region:'west',split:true,border:false,title:'数据字典分类'" style="width: 200px;overflow:auto;">
			<div id="typeTree" class="ztree"></div>
		</div>
		
		<div data-options="region:'center'" style="text-align:center;margin-left:0px; border-left:1px solid #fff" >	
			<div class="treeFrame">
				<iframe id="listFrame" src="" frameborder="no" width="100%" height="100%"></iframe>	
			</div>
		</div>
		<div class="hidden">
			<div id="rootMenu" class="easyui-menu" data-options="onClick:menuHandler"  style="width:120px;">
			     <div data-options="name:'add'" >增加字典分类</div>
			     <div data-options="name:'fresh'" >刷新</div>
			       <div data-options="iconCls:'icon-save',name:'sort'">子节点排序</div>	
			   
			</div>
			<div id="typeMenu" class="easyui-menu" data-options="onClick:menuHandler"  style="width:120px;">
			     <div data-options="name:'add'" >增加字典分类</div>
			     <div data-options="name:'edit'">编辑字典分类  </div>
		    	 <div data-options="name:'remove'">删除字典分类  </div>
			     <div data-options="name:'fresh'" >刷新</div>	    
			     <div class="menu-sep"></div>
			     <div data-options="iconCls:'icon-save',name:'sort'">子节点排序</div>		    
			</div>
			<div id="priTypeMenu" class="easyui-menu" data-options="onClick:menuHandler"  style="width:120px;">
			   	 <div data-options="name:'priadd'" >增加字典分类</div>
			   	 <div data-options="name:'edit'">编辑字典分类  </div>
			   	 <div data-options="name:'remove'">删除字典分类  </div>
			     <div data-options="name:'fresh'" >刷新</div>	    
			     <div class="menu-sep"></div>
			     <div data-options="iconCls:'icon-save',name:'sort'">子节点排序</div>		    
			</div>
		</div>
	</div>
</body>
</html>