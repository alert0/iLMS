<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/scriptSelector.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/identitySelector.js"></script>
 
<script type="text/javascript" src="${ctx}/js/platform/system/dialog/conditionScriptDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/platform/flow/bpmSetFormBoAttrScript.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/javacode/InitMirror.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/javacode/codemirror.js"></script>
<script type="text/javascript">
	var conf = null;
	$(function() {
		// 设置根节点
		var treeObj = new getBosTreeNodeObj(0, null, "all", {
			name : "业务对象根节点",
			desc : "业务对象根节点"
		}, "boRoot");
		treeNodes.push(treeObj);
	
		//加载BO内容,显示数据
		conf = window.passConf;
		if (conf) {
			$("#flowKey").val(conf.flowKey);
			$("#nodeId").val(conf.nodeId);
			$("#myNodeSpan").text(conf.nodeName);
			//设置查询BO数据
			setBosTree(conf.aryBo);
			//初始化原属性设置到设置区域
			addSetAttrData(conf.setAttrData);
		}
		
		//初始化BO的树
		loadBosTree();

		//初始化快速查找事件
		initQuickFind();

		mirrorObjInit(); //获取页面上的编辑器对象
		
		
	});

	function selectConditionScript(id) {
		if (!conf) {
			return;
		}

		if (!conf.flowId || !conf.nodeId) {
			return;
		}
		ConditionScript.showDialog({
			defId : conf.flowId,
			nodeId : conf.nodeId
		}, function(script) {
			if (id == 'beforeShowScript') {
				beforeShowEditor.editor.insertCode(script);
			}
			if (id == 'afterSaveScript') {
				afterSaveEditor.editor.insertCode(script);
			}
			if (id == 'seqNoScript') {
				seqNoEditor.editor.insertCode(script);
			}
		});
	}
	function selectScript(id) {
		if(id=="seqNoScript"){//流水号
			new IdentitySelector(function(script) {seqNoEditor.editor.insertCode(script);}).show();
		}else{//脚本
			new ScriptSelector(function(script) {
				if (id == 'beforeShowScript') {
					beforeShowEditor.editor.insertCode(script);
				}
				if (id == 'afterSaveScript') {
					afterSaveEditor.editor.insertCode(script);
				}
			}).show();
		}
	}
</script>

</head>
<body class="easyui-layout">

	<!-- 隐藏域设置区 -->
	<input id="flowKey" type="hidden" name="flowKey" />
	<input id="nodeId" type="hidden" name="nodeId" />
	<input id="name" type="hidden" name="name" />
	<input id="desc" type="hidden" name="desc" />
	<input id="id" type="hidden" name="id" />
	<input id="defId" type="hidden" name="defId" />
	<input id="defCode" type="hidden" name="defCode" />
	<input id="myObject" type="hidden" name="myObject" />


	<div data-options="region:'west',border:true,headerCls:'background-color:gray'" style="text-align: center; width: 250px;">

		<table class="form-table" style="width: 99.8% !important;">
			<tr>
				<td colspan="2" style="height: 30px; text-align: center;">
					<span>搜索：</span>
					<input class="inputText" style="width: 120px" id="findName" type="text" name="findName" />
				</td>
			</tr>
		</table>

		<div id="bosTree" class="ztree"></div>
	</div>

	<div data-options="region:'center',border:true" style="text-align: center; width: 400px">
		<table class="form-table" style="width: 99.8% !important;">
			<thead>
				<tr>
					<th colspan="2" style="height: 30px; text-align: center;">
						BO字段设置-[
						<span id="myNodeSpan"></span>
						]
					</th>
				</tr>
			</thead>
		</table>

		<table class="form-table" style="width: 99.8% !important;">
			<tr>
				<th>设置描述：</th>
				<td style="text-align: left;">
					<input class="inputText" style="width: 250px" id="setDesc" type="text" name="setDesc" />
				</td>

			</tr>

			<tr>
				<th>设置类型：</th>
				<td style="text-align: left;">
					<select id="setType" name="setType" class="inputText" onchange="changeSetType(this);">
						<option value="script">脚本</option>
						<option value="seqNo">流水号</option>
					</select>
				</td>

			</tr>

			<tr>
				<th>字段名称：</th>
				<td style="text-align: left;">
					<span id="myName"></span>
				</td>

			</tr>

			<tr id="mySetMyNameTr" style="display: none;">
				<th>运算设置：</th>
				<td style="text-align: left;">
					<select id="myNameSelectName" name="myNameSelectName" class="inputText" onchange="changeMyName(this);">
						<option value="no">无</option>
						<option value="row">行运算</option>
					</select>
				</td>
			</tr>

			<tr>
				<td colspan="2">
					<div id="setScriptTab" class="easyui-tabs" fit="true" style="text-align: center; width: 400px; height: 260px;">
						<div title="显示前">
							显示前脚本：(
							<a class="btn btn-primary btn-xs" onclick="selectScript('beforeShowScript')">常用脚本</a>
							<a class="btn btn-primary btn-xs" onclick="selectConditionScript('beforeShowScript')">条件脚本</a>
							<a href="javascript:;" style="text-decoration: none;" title="例子: return '1'"
								   class="fa fa-exclamation-circle ht-help" ht-tip>　</a>
							)
							<textarea id="beforeShowScript" name="beforeShowScript" codemirror="true" rows="12" cols="50"></textarea>
							<!-- <bpm:codemirror mode="Groovy" id="beforeShowScript" name="beforeShowScript" lineWrapping="true"
									value="" lineNumbers="true" ></bpm:codemirror> -->

						</div>
						<div title="保存时">
							保存后脚本：(
							<a class="btn btn-primary btn-xs" onclick="selectScript('afterSaveScript')">常用脚本</a>
							<a class="btn btn-primary btn-xs" onclick="selectConditionScript('afterSaveScript')">条件脚本</a>
							<a href="javascript:;" style="text-decoration: none;" title="例子: return '1'"
								   class="fa fa-exclamation-circle ht-help" ht-tip>　</a>
							)
							<textarea id="afterSaveScript" name="afterSaveScript" codemirror="true" rows="12" cols="50"></textarea>
							<!-- <bpm:codemirror mode="Groovy" id="afterSaveScript" name="afterSaveScript"  lineWrapping="true"
									 value="" lineNumbers="true"></bpm:codemirror> -->
						</div>
					</div>

					<div id="setSeqNoTab" class="easyui-tabs" fit="true" style="text-align: center; width: 400px; display: none;">
						<div title="流水号">
							流水号脚本：(
							<a class="btn btn-primary btn-xs" onclick="selectScript('seqNoScript')">流水号选择</a>
							<!-- <a class="btn btn-primary btn-xs" onclick="selectConditionScript('seqNoScript')">条件脚本</a> -->
							<a href="javascript:;" style="text-decoration: none;" title="例子: return '1'"
								   class="fa fa-exclamation-circle ht-help" ht-tip>　</a>
							)
							<textarea id="seqNoScript" name="seqNoScript" codemirror="true" rows="12" cols="50"></textarea>
							<!-- <bpm:codemirror mode="Groovy" id="seqNoScript" name="seqNoScript"  lineWrapping="true"
								    value="" lineNumbers="true"></bpm:codemirror>-->
						</div>
					</div>

				</td>
			</tr>

			<tr>
				<td colspan="2" style="height: 30px; text-align: center;">
					<a class="btn btn-sm btn-primary fa fa-add" href="javascript:addSetBoAttr()">添加设置</a>
				</td>
			</tr>
		</table>

	</div>

	<div data-options="region:'east',border:true" style="text-align: center; width: 160px">
		<table class="form-table">
			<tr>
				<td colspan="2" style="height: 30px; text-align: center;">
					查询：
					<input class="inputText" style="width: 100px" type="text" id="findSetAttr">
				</td>
			</tr>
		</table>

		<table id="setAttrGrid" class="easyui-datagrid" data-options="fitColumns:false,singleSelect:false,pagination:false,onClickRow:onClickRow">
			<thead>
				<tr>
					<th field="setDesc" width="110px">设置描述</th>
					<th manager="true" width="50px">
						<div>
							<a onclick="javascript:deleteSetAttr(this,'{name}')" class="fa fa-remove"> </a>
						</div>
					</th>
				</tr>
			</thead>
		</table>

	</div>

</body>
</html>