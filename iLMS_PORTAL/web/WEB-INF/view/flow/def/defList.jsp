<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title>流程定义</title>
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
<script type="text/javascript" src="${ctx}/js/lib/ztree/ztreeCreator.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/flow/deleteListByAuthorize.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/updateTypeDialog.js"></script>


</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'west',split:true,border:false,title:'流程分类',minWidth:200" style="width: 200px;">
			<div id="sysTypeTree" class="ztree"></div>
		</div>
		<div data-options="region:'center',border:false">
			<div id="gridSearch" class="toolbar-search ">
					<div class="toolbar-head">
						<!-- 顶部按钮 -->
						<div class="buttons">
							<a class="btn btn-primary btn-sm fa-share-alt" onclick="$.openFullWindow('defDesign')" href="javascript:void(0)">
								<span>在线流程设计</span>
							</a>
							<a class="btn btn-primary btn-sm fa-share-alt" onclick="$.openFullWindow('${ctx}/bpm-editor/modeler.html')" href="javascript:void(0)">
								<span>在线流程设计_web</span>
							</a>
							<a class="btn btn-primary btn-sm fa-sign-out" href="javascript:void(0)">
								<span> 导出</span>
							</a>
							<a class="btn btn-primary btn-sm fa-sign-in" href="javascript:void(0)">
								<span> 导入</span>
							</a>
							<a class="btn btn-primary btn-sm category fa-share-alt" href="javascript:setTypeId()">
								<span> 设置分类</span>
							</a>
							<a class="btn btn-primary btn-sm fa-search" href="javascript:void(0)">
								<span>搜索</span>
							</a>
							<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
								<span>重置</span>
							</a>

						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse">
								<i class="fa  fa-angle-double-up"></i>
							</a>
						</div>
					</div>
					<div class="toolbar-body">
						<form id="searchForm" class="search-form">
							<ul>
								<li>
									<span>流程名称:</span>
									<input class="inputText" type="text" name="Q^name_^SL">
								</li>
								<li>
									<span>流程KEY:</span>
									<input class="inputText" type="text" name="Q^def_key_^SL">
									<input class="inputText" type="hidden" name="Q^type_id_^L"/>
								</li>
								<li>
									<span>流程状态:</span>
									<select name="Q^status_^S" class="inputText">
										<option value="">请选择...</option>
										<option value="deploy">已发布</option>
										<option value="draft">未发布</option>
										<option value="forbidden">禁用</option>
										<option value="forbidden_instance">禁止实例</option>
									</select>
								</li>
							</ul>
							<ul>
								<li>
									<span>创建时间从:</span>
									<input name="Q^create_time_^DL" class="inputText date" />
								</li>
								<li>
									<span>至:</span>
									<input name="Q^create_time_^DG" class="inputText date" />
								</li>
								<li>
									<span>生产状态:</span>
									<select name="Q^test_status_^S" class="inputText">
										<option value="">请选择...</option>
										<option value="run">正式</option>
										<option value="test">测试</option>
									</select>
								</li>
							</ul>
						</form>
					</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>

	<div class="hidden">
		<div id="typeMenu" class="easyui-menu wh-120" data-options="onClick:menuHandler">
			<div data-options="name:'add'" iconCls="fa fa-add">增加分类</div>
			<div data-options="name:'edit'" iconCls="fa fa-edit">编辑分类</div>
			<div data-options="name:'remove'" iconCls="fa fa-delete">删除分类</div>
			<div class="menu-sep"></div>
			<div data-options="iconCls:'icon-save',name:'sort'" iconCls="fa fa-sort">分类排序</div>
			<div data-options="name:'move'" iconCls="fa fa-car">移动分类</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	var sysTypeTree = null;
	$(function() {
		loadGrid();
		$("a.btn.fa-sign-out").click(handExport);
		$("a.btn.fa-sign-in").click(handImport);
		//加载分类树
		sysTypeTree = new CommonFlowTree($('#sysTypeTree'));
	});
	
	
	function setTypeId(){
		new updateTypeDialog({idKey:"def_id_", categoryKey:'FLOW_TYPE',tableName:'bpm_definition',typeIdKey:'type_id_',setting:{view:{selectedMulti:false}}}).show();
	}
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : __ctx + "/flow/def/listJson",
			idField : "defId",
			sortName : 'create_time_',
            singleSelect: false,
			sortOrder : 'desc',
			columns : [ [ 
				{field : 'defId',sortName : "def_id_",checkbox : true}, 
				{field : 'name',sortName : "name_",title : '名称',width : 150,align : 'left',sortable : 'true'},
			    { field : 'defKey',sortName : "def_key_",title : '流程Key',width : 150,align : 'left',sortable : 'true'}, 
			    {field : 'desc',sortName : "desc_",title : '流程描述',width : 150,align : 'left',sortable : 'true'},
				{field : 'status',sortName : "status_",title : '状态',width : 100,align : 'center',sortable : 'true',
					formatter : function(value, row, index) {
						var arr = [ { 'key' : 'draft','value' : '未发布','css' : 'red'}, 
						            {'key' : 'deploy', 'value' : '已发布','css' : 'green'}, 
						            {'key' : 'forbidden','value' : '禁用','css' : 'red'}, 
						            {'key' : 'forbidden_instance','value' : '禁止实例','css' : 'red'} ];
						return Object.formatItemValue(arr,value);
					}
				}, 
				{field : 'testStatus',sortName : "test_status_",title : '生产状态',width : 100,align : 'center',sortable : 'true',
					formatter : function(value, row, index) {
						var arr = [ {'key' : 'test','value' : '测试','css' : 'red'}, {'key' : 'run','value' : '正式','css' : 'green'} ];
						return Object.formatItemValue(arr,value);
					}
				}, 
				{field : 'version',sortName : "version_",title : '版本号',align : 'center',sortable : 'true'}, 
				{field : 'colManage',title : '操作',width : 80,align : 'center',sortable : 'true',formatter : mangerFormater} 
				] ]
        }));
	}

	//菜单对应项
	function menuHandler(item) {

	}
	
	function mangerFormater(value, row, index){
			var result = "";
			if (row.status != 'draft') {
				if (row.authorizeRight.m_start  && row.status != 'forbidden' && row.status != 'forbidden_instance') {
					result += "<a class='btn btn-default fa fa-send' onclick='$.openFullWindow(\"${ctx}/flow/instance/instanceToStart?defId=" + row.defId + "\")' href='#'>启动</a>";
				}
				if (row.authorizeRight.m_set ) {
					result += "<a class='btn btn-default fa fa-cogs' onClick='openDetail(" + row.defId + ",\"" + row.status + "\")' href='#'>设置</a>";
				}
			}

			if (row.authorizeRight.m_edit ) {
				if (row.designer == 'ECLIPSE') {
					result += "<a class='btn btn-default fa fa-edit' onClick='$.openFullWindow(\"edit?defId=" + row.defId + "\")' href='#'>编辑</a>";
				}
				if (row.designer == 'FLASH') {
					result += "<a class='btn btn-default fa fa-edit' onClick='$.openFullWindow(\"defDesign?defId=" + row.defId + "\")' href='#'>设计</a>";
				}
				if (row.designer == 'WEB') {
					result += "<a class='btn btn-default fa fa-edit' onClick='$.openFullWindow(\"${ctx}/bpm-editor/modeler.html?defId=" + row.defId + "\")' href='#'>设计</a>";
				}
			}
			if (row.authorizeRight.m_del ) {
				result += "<a class='btn btn-default fa fa-remove' onClick='defRemove(" + row.defId + ",\"" + row.testStatus + "\")' href='#' >删除</a>";
			}

			if (row.status == 'deploy' && row.authorizeRight.m_clean && row.testStatus != 'run') {

				result += "<a class='btn btn-default fa fa-trash' onclick='cleanData(" + row.defId + ")' href='#'>清除数据</a>";
			}
			if (row.status == 'draft') {
				result += "<a class='btn btn-default fa fa-cogs' onclick='deploy(" + row.defId + ",this)' href='#'>发布</a>";
			}
			
			return result;
	}
	
	function defRemove(defId,testStatus){
		var url = __ctx +  "/flow/def/remove?defId=" + defId;
		var confirmMsg = '确认删除吗？';
		if(testStatus=="run"){
			confirmMsg = '该流程为正式流程，删除正式流程有可能导致运行中的实例或任务被全部删除，此操作不可逆，请确认是否删除！';
		}
		$.topCall.confirm('提示信息', confirmMsg, function(r) {
			if(!r) return;
			$.post(url, function(responseText) {
				var resultMessage = new com.hotent.form.ResultMessage(responseText);
				if (resultMessage.isSuccess()) {
					var msg = resultMessage.getMessage();
					$.topCall.success( msg|| '删除成功！',function(){
						$('.my-easyui-datagrid').datagrid("clearSelections");// 清除选中的
						$('.my-easyui-datagrid').datagrid("reload");
					});
				} else {
					$.topCall.alert("错误提示",resultMessage.getMessage());
				}
			});
			
		});
	}

	function handExport() {
		var defIds= $.getSelectIds($("#grid"),'defId');

		if (!defIds) {
			$.topCall.warn( '请选择至少一项记录');
			return;
		}
		var frm = new com.hotent.form.Form();
		frm.creatForm("frmExport", "exportXml");
		frm.addFormEl("bpmDefIds", defIds);
		frm.submit();
	}


	function handImport() {
		var def = {
			passConf : '',
			title : '导入流程',
			width : 600,
			height : 400,
			modal : true,
			resizable : true,
			iconCls : 'fa fa-user',
			buttonsAlign : 'center'
		};
		$.topCall.dialog({
			src : __ctx + '/flow/def/defImport',
			base : def
		});
	}

	function cleanData(defId) {
		$.topCall.confirm("温馨提示", "确定要清除数据吗?", function(r) {
			if (r) {
				$.post('cleanData', {
					defId : defId
				}, function(data) {
					var obj = new com.hotent.form.ResultMessage(data);
					if (obj.isSuccess()) {
						$.topCall.success(obj.getMessage());
					} else {
						$.topCall.error(obj.getMessage());
					}
				});
			}
		});
	}
	function deploy(defId,_this) {
		$(_this).attr('disabled', 'true');
		$.post('deploy', {
			defId : defId
		}, function(data) {
			var obj = new com.hotent.form.ResultMessage(data);
			if (obj.isSuccess()) {
				$.topCall.success(obj.getMessage());
				$('.my-easyui-datagrid').datagrid('reload');
			} else {
				$.topCall.error(obj.getMessage());
			}
		});
	}
	
	


	function openDetail(defId, status) {
		var href = "defDetail?defId=" + defId + "&status=" + status
		var title = "流程设置";
		$.openFullWindow(href);
		//HT.window.openEdit(href, title, "edit", 'grid', 500, 500, null, null, defId, true);
	}
	
	function copyFlow(defId,name,defKey){
		var url = __ctx + "/flow/def/defCopy?defId="+defId + "&name="+name+"&defKey="+defKey;
		HT.window.openEdit(encodeURI(url), "流程复制", "", 'grid', 300, 200, null, null, "", true);
	}
	
	//处理导入成功后，关闭页面时，刷新相关列表
	function reloadForImport (){
		$('.my-easyui-datagrid').datagrid('reload');
	}
	
	
</script>