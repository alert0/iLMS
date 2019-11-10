<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/list.jsp"%>
<%@include file="/commons/include/incTree.jsp"%>
<script type="text/javascript"
	src="${ctx}/js/platform/util/customUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/ztree/ztreeCreator.js"></script>
<script type="text/javascript"
	src="${ctx}/js/platform/system/dialog/updateTypeDialog.js"></script>
<script type="text/javascript">
function setTypeId(){
	new updateTypeDialog({categoryKey:'DEF_TYPE',tableName:'xbo_def',typeIdKey:'category_id_',setting:{view:{selectedMulti:false}}}).show();
}
</script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div
			data-options="region:'west',split:true,border:false,title:'业务定义分类'"
			style="width: 200px;">
			<div id="sysTypeTree" class="ztree"></div>
		</div>

		<div data-options="region:'center',border:false">
			<!-- 顶部查询操作 -->
			<div id="gridSearch" class="toolbar-search ">
				<div class="toolbar-head">
					<!-- 顶部按钮 -->
					<div class="buttons">
						<a class="btn btn-primary  btn-sm fa-search"
							href="javaScript:void(0)"> <span>搜索</span>
						</a> <a class="btn btn-primary btn-sm fa-add"
							href="javaScript:void(0)" onClick='openDetail()'> <span>添加</span>
						</a> <a class="btn btn-primary btn-sm fa-remove"
							href="javaScript:void(0)" action="/bo/def/bODef/remove"> <span>删除</span>
						</a> <a href="javaScript:void(0)"
							class="btn btn-sm btn-primary fa-rotate-left"> <span>重置</span>
						</a> <a class="btn btn-primary btn-sm fa-sign-out"
							href="javascript:exportXml()"> <span> 导出</span>
						</a> <a class="btn btn-primary btn-sm fa-sign-in"
							href="javascript:$('#xmlFile').click()"> <span> 导入</span>
						</a> <a href="javascript:;" class="btn btn-sm btn-primary fa-filter"
							onclick="setTypeId()"> 设置分类</a> <input type="file" size="40"
							name="xmlFile" id="xmlFile" accept="application/zip"
							style="display: none;" onchange="importXml()" />
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"> <i
							class="fa  fa-angle-double-up"></i>
						</a>
					</div>
				</div>
				<div class="toolbar-body">
					<form id="searchForm" class="search-form">
						<ul>
							<li class="mar-l-20"><span>别名:</span> <input
								class="inputText" type="text" name="Q^alias_^SL"></li>
							<li class="mar-l-20"><span>描述:</span> <input
								class="inputText" type="text" name="Q^description_^SL">
							</li>
						</ul>
					</form>
				</div>
			</div>
			<div id="grid" class="my-easyui-datagrid"></div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	var selcategoryId = null;//选中的树节点
	$(function() {
		loadGrid();
		new SysTypeTree($('#sysTypeTree'), {
			typeKey : "DEF_TYPE",
			onClick : function(event, treeId, treeNode) {
				var param = {};
				if (treeNode.isRoot != 1) {
					param = {
						'Q^category_id_^L' : treeNode.id
					};
					selcategoryId = treeNode.id;
				}
				$('.my-easyui-datagrid').datagrid('load', param);
			}
		});
	});

	function importXml() {
		var fileName = $("#xmlFile").val();
		if (!fileName) {
			return;
		}
		if (!fileName.endWith("\.zip")) {
			$.topCall.toast('提示信息', '请选择一个zip文件');
			return;
		}
		var frm = new com.hotent.form.Form();
		var frmObj = frm.creatForm("frmImport", "bODef/importXml");
		frmObj.appendChild($("#xmlFile")[0]);
		frmObj.enctype = "multipart/form-data";
		$(frmObj).ajaxForm({
			success : function(data) {
				var r = new com.hotent.form.ResultMessage(data);
				if (r.isSuccess()) {
					$.topCall.error("导入成功",r.getMessage(),function(){
						$('#grid').datagrid("reload");
					},"信息");
				} else {
					$.topCall.error(r.getMessage());
				}
				$("#xmlFile").val("");
			},
			error : function() {
				$("#xmlFile").val("");
			}
		});
		$(frmObj).submit();
	}

	function exportXml() {
		var ids= $.getSelectIds($("#grid"),'id');
		if (!ids) {
			$.topCall.warn( '请选择至少一项记录');
			return;
		}
		var frm = new com.hotent.form.Form();
		frm.creatForm("frmExport", "bODef/exportXml");
		frm.addFormEl("ids", ids);
		frm.submit();
	}

	function openDetail(id,deployed) {
		var title = "编辑业务对象";
		if(!id){
			title = "添加业务对象";
		}else{
			if(deployed){
				title = "查看业务对象";
			}
		}
		var action = "bODefEdit";
		var url = action;
		if (id && id != "") {
			url += "?id=" + id;
		}else{
			if(selcategoryId){
				url += "?categoryId=" + selcategoryId;
			}
		}
		HT.window.openEdit(url, title, action, 'grid', 500, 500, null, null, id, true);
	}
	
	function preview(alias) {
		var title = alias+"-数据结构";
		var url = "bODefJson?boCode="+alias;
		HT.window.openEdit(url, title, "bODefJson", 'grid', 100, 100, null, null, alias, true);
	}
	
	function relation(id) {
		var title ="绑定的流程和表单";
		var url = "bODefRelation?type=boDef&id="+id;
		HT.window.openEdit(url, title, "bODefRelation", 'grid', 100, 100, null, null, id, true);
	}

	function deploy(id) {
		$.ajax({
			url : __ctx + '/bo/def/bODef/deploy?id=' + id,
			type : 'POST',
			dataType : 'json',
			success : function(data) {
				if (data.result == 1) {
					$.topCall.success(data.message, function() {
						$('#grid').datagrid("reload");
					});
				} else {
					if (data.cause) {
						$.topCall.errorStack(data.message, data.cause, "错误信息");
					} else {
						$.topCall.error(data.message);
					}
				}
			}
		});
	}
	
	function setStatus(id,status) {
		$.ajax({
			url : __ctx + '/bo/def/bODef/setStatus?id=' + id+'&status='+status,
			type : 'POST',
			dataType : 'json',
			success : function(data) {
				if (data.result == 1) {
					$.topCall.success(data.message, function() {
						$('#grid').datagrid("reload");
					});
				} else {
					if (data.cause) {
						$.topCall.errorStack(data.message, data.cause, "错误信息");
					} else {
						$.topCall.error(data.message);
					}
				}
			}
		});
	}

	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url : __ctx + "/bo/def/bODef/listJson",
			idField : "id",
			sortName : 'CREATE_TIME_',
			sortOrder : 'desc',
			columns : [ [ 
				{field : 'id',sortName : "id_",checkbox : true,align : 'center'}, 
				{field : 'description',title : '描述',width : 400,align : 'left'} ,
				{field : 'alias',title : '别名',width : 300,align : 'center'}, 
			 	{field : 'deployed',title : '是否已发布',align : 'center',width : 50,
					formatter : function(value) {
						if (value == "1") {
							return "<span style='color:green'>是</span>";
						} else {
							return "<span style='color:red'>否</span>";
						}
					}
				}, 
				{field : 'status',title : '状态',align : 'center',width : 50,
					formatter : function(value) {
						if (value == "normal") {
							return "<span style='color:green'>可用</span>";
						} else {
							return "<span style='color:red'>禁用</span>";
						}
					}
				}, 
				{field : 'colManage',title : '操作',align : 'center',
					formatter : function(value, row, index) {
						var result = "";
						if (row.deployed == "0") {
							result += "<a class='btn btn-default fa fa-edit' onClick='openDetail(" + row.id + ",false)' herf='javaScript:void(0)'>编辑</a>";
						}else{
							result += "<a class='btn btn-default fa fa-eye' onClick='openDetail(" + row.id + ",true)' herf='javaScript:void(0)'>查看</a>";
						}
						result += "<a class='btn btn-default fa fa-eye' onClick='preview(\"" + row.alias + "\")' herf='javaScript:void(0)'>预览数据结构</a>";
						if (row.deployed == "0") {
							result += "<a class='btn btn-default fa fa-eject' onClick='deploy(" + row.id + ")' herf='javaScript:void(0)'>发布</a>";
						}else{
							result += "<a class='btn btn-default fa fa-cloud' onClick='relation(" + row.id + ")' herf='javaScript:void(0)'>绑定关系</a>";
						}
						
						if (row.editable) {
							if (row.status == "normal") {
								result += "<a class='btn btn-default fa fa-lock' onClick='setStatus(" + row.id + ",\"forbidden\")' herf='javaScript:void(0)'>禁用</a>";
							} else {
								result += "<a class='btn btn-default fa fa-unlock' onClick='setStatus(" + row.id + ",\"normal\")' herf='javaScript:void(0)'>启用</a>";
							}
						}
						
						return result;
					}
				}]]
		}));
	}
	
	
	//处理导入成功后，关闭页面时，刷新相关列表
	function reloadForImport (){
		$('.my-easyui-datagrid').datagrid('reload');
	}
</script>