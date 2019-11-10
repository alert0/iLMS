<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html  ng-app="app">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/ngEdit.jsp" %>
		<%@include file="/commons/include/zTree.jsp"%>
		<script type="text/javascript" src="${ctx}/js/platform/system/dialog/iconDialog.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/ueditor/plugins/customButton/custBtnController.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/ueditor/plugins/customButton/dragDivTree.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/ueditor/dialogs/internal.js"></script> 
		
		<link rel="stylesheet" href="${ctx}/js/lib/ueditor/plugins/customButton/custBtn.css" type="text/css" />
		<script type="text/javascript">
			var formId="${param.formId}";
			var formType = "${param.formType}";
			var defId = "${defId}";
		</script>
	</head>
	<body ng-controller="CustBtnController">
		 <ul class="nav nav-tabs" >
		    <li class="active"><a href="#custDialog" id="custTab" data-toggle="tab">自定义对话框</a></li>
		    <li ng-repeat="query in custBtn.custQueryList" ><a href="{{'#'+$index}}" data-toggle="tab">{{query.name}}<span class="fa fa-remove" ng-click="delCustQuery($index)"></span></a></li>
		    <li>
			     <button class="btn btn-default btn-xs" type="button" ng-click="addCustQuery()">添加子查询</button>
			</li>
	  	 </ul>
		 <div class="tab-content">
		    <div class="tab-pane active" id="custDialog" role="tab">
				<fieldset class="scheduler-border">
				   	<legend style="width: 90px;border-bottom:0;margin-bottom:10px;">按钮设置</legend>
				   	<table class="table-form">
				   		<tr>
				   			<th>按钮名称：</th><span class="btn" ng-click="getHtml()">数据</span>
				   			<td><input ng-model="custBtn.name" class="inputText" type="text"/></td>
				   			<th>图标：</th>
				   			<td>
				   				<i class="{{custBtn.icon}}"></i>
				   				<a class="btn btn-info btn-sm fa-search-plus" href="javascript:;" ng-click="selectIcon();">选择</a>
				   			</td>
				   		</tr>
				   		<tr><th>权限继承：</th>
				   			<td colspan="3">
				   				<select ng-model="custBtn.permission" class="inputText">
				   				<c:forEach items="${boEntList}" var="boEnt">
				   				<optgroup label="${boEnt.desc}${boEnt.tableNameNoPrefix}"  ng-if="'${boEnt.tableNameNoPrefix}'==currentSubTable ||('main'=='${boEnt.type}' && !currentSubTable)">
			   						 <c:forEach items="${boEnt.attrs}" var="attr">
		   								<option value="${boEnt.tableNameNoPrefix}.${attr.name}" >${attr.desc}</option>
			   						</c:forEach> 
			   					</optgroup>
				   				</c:forEach>
				   				</select>
				   			</td>
				   		</tr>
				   		<tr><th>对话框选择</th>
				   			<td colspan="3">
				   				<select class="inputText" ng-model="custBtn.custDialog.alias" ng-change="changeCustDialog('custDialogTree')" ng-options="m.alias as m.name for m in customDialogs">
									<option value="">--请选择--</option>
								</select>
				   			</td>
				   	    </tr>
				   	    <tr><th>参数设置</th>
				   			<td colspan="3" rowspan="{{custBtn.custDialog.params.length}}">
			   				<table ng-if="custBtn.custDialog.conditions.length>0">
				   				<thead>
				   				<tr>
				   					<th>参数名</th>
				   					<th style="width: 180px;">取值对象</th>
				   					<th style="width: 180px;">默认值</th>
				   				</tr>
				   				</thead>
				   				<tr ng-repeat="condition in custBtn.custDialog.conditions">
				   				<td>{{condition.comment}}</td>
				   				<td>
				   					<select class="inputText" ng-model="condition.bind" >
				   					<c:forEach items="${boEntList}" var="boEnt">
						   				<optgroup label="${boEnt.desc}${boEnt.name}"  ng-if="'${boEnt.name}'==currentSubTable ||('main'=='${boEnt.type}' && !currentSubTable)">
					   						 <c:forEach items="${boEnt.attrs}" var="attr">
				   								<option value="${boEnt.name}.${attr.name}" >${attr.desc}</option>
					   						</c:forEach> 
					   					</optgroup>
					   				</c:forEach>
					   					<!-- <optgroup label="{{bo.desc}}" ng-repeat="bo in allFields">
				   							<option value="{{bo.name}}.{{attr.name}}" ng-repeat="attr in bo.bOAttributeList">{{attr.desc}}</option>
					   					</optgroup> -->
									</select>
								</td>
								<td>
									<input type="text"  ng-model="condition.defaultValue" placeholder="默认值" class="inputText">
									<label><input type="checkbox" name="isScript{{$index}}" ng-model="condition.isScript"></input>脚本取值</label>
								</td>
				   				</tr>
			   				</table>
				   			</td>
				   	    </tr>
				   	</table>
				</fieldset>
				
				<fieldset class="scheduler-border">
				   	<legend style=" width: 120px;border-bottom:0;margin-bottom:10px;">数据回填设置</legend>
					<div class="fields-div">
						<ul  id="custDialogTree" class="ztree field-ul"></ul>
					</div>
					<div class="domBtnDiv" id="custDialogTree_dom">
						<div ng-repeat="bo in allFields" class="item-div" domId="{{bo.id}}" domDesc="{{bo.desc}}" title="{{bo.type=='main'?'主表':'子表'}}" ng-if="(bo.name)==currentSubTable ||  !custBtn.isInSub">
							<div class="title-div">{{bo.desc}}<span ng-if="(bo.name)==currentSubTable" style="font-weight: normal;font-size: 10">   （自定义按钮位于子表中，仅仅作用当前行）</span></div>
							<span ng-repeat="attr in bo.attrs" class="domBtn" domId="{{bo.type=='main'?'':bo.type+'.'}}{{bo.name}}.{{attr.name}}">{{attr.desc}}</span>
						</div>
					</div>
				</fieldset>
			</div>
		    <div ng-repeat="query in custBtn.custQueryList" class="tab-pane" id="{{$index}}">
				<fieldset class="scheduler-border">
				   	<legend style="width: 90px;border-bottom:0;margin-bottom:10px;">查询设置</legend>
				   	<table class="table-form">
				   		<tr>
					   		<th>子查询名字：</th>
					   		<td><input ng-model="query.name" style="width:120px;" class="inputText"></td>
					   		<th>查询类型：</th>
				   			<td colspan="1">
				   				<select ng-model="query.type" class="inputText" ng-change="changeQueryType($index)">
				   					<option value="">请选择</option>
				   					<option value="custQuery">自定义查询</option>
				   					<!-- <option value="aliasScript">别名脚本</option> -->
				   				</select>
				   			</td>
				   			
				   		</tr>
				   		<tr>
				   			<th>联动设置：</th>
				   			<td>
				   				<select ng-model="query.ganged" class="inputText">
				   					<option value="">不联动 </option>
				   					<option value="inter">回车联动 </option>
				   					<option value="change">值改变联动 </option>
				   				</select>
				   			</td>
				   			<th>联动目标：</th>
				   			<td>
				   				<select ng-model="query.gangedTarget" class="inputText">
				   					<c:forEach items="${boEntList}" var="boEnt">
						   				<optgroup label="${boEnt.desc}${boEnt.name}"  ng-if="'${boEnt.name}'==currentSubTable ||('main'=='${boEnt.type}' && !currentSubTable)">
					   						 <c:forEach items="${boEnt.attrs}" var="attr">
				   								<option value="${boEnt.name}.${attr.name}">${attr.desc}</option>
					   						</c:forEach> 
				   						</optgroup>
				   					</c:forEach>
				   				</select>
				   			</td>
				   		</tr>
				   		<tr><th>查询选择</th>
				   			<td colspan="3">
				   				<select ng-if="query.type=='custQuery'" class="inputText" ng-model="query.alias" ng-change="changeCustQuery($parent.$index)" ng-options="m.alias as m.name for m in custQuerys">
								</select>
								<select ng-if="query.type=='aliasScript'" class="inputText" ng-model="query.alias" ng-change="changeAliasScript($parent.$index)" ng-options="m.alias as m.name for m in aliasScripts">
								</select>
				   			</td>
				   	    </tr>
				   	   
				   	     <tr><th>参数设置</th>
				   			<td colspan="3" rowspan="{{query.conditions.length}}">
				   				<table ng-if="query.conditions.length>0">
					   				<thead>
					   				<tr>
					   					<th>参数名</th>
					   					<th style="width: 260px;">取值对象</th>
					   					<th style="width: 100px;">默认值</th>
					   				</tr>
					   				</thead>
					   				<tr ng-repeat="con in query.conditions track by $index">
					   				<td>{{con.comment}}</td>
					   				<td>
					   					<select class="inputText" ng-model="con.bind" name="asdf">
					   						<option value="">请选择</option>
						   					<c:forEach items="${boEntList}" var="boEnt">
								   				<optgroup label="${boEnt.desc}${boEnt.name}" ng-if="'${boEnt.name}'==currentSubTable ||('main'=='${boEnt.type}' && !currentSubTable)">
							   						 <c:forEach items="${boEnt.attrs}" var="attr">
						   								<option value="${boEnt.name}.${attr.name}" ng-selected="isSelect(con.bind,'${boEnt.name}.${attr.name}')" >${attr.desc}</option>
							   						</c:forEach> 
						   						</optgroup>
						   					</c:forEach>
										</select>
									</td>
									<td>
										<input ng-model="con.defaultValue" placeholder="默认值" class="inputText">
										<label><input type="checkbox" name="isQueryScript{{$index}}" ng-model="con.isScript"></input>脚本取值</label>
									</td>
					   				</tr>
			   					</table>
				   			</td>
				   	    </tr>
				   	</table>
				</fieldset>
				
				<fieldset class="scheduler-border">
				   	<legend style=" width: 120px;border-bottom:0;margin-+:10px;">数据回填设置</legend>
					<div class="fields-div">
						<ul class="ztree field-ul" id="querytree{{$index}}"></ul>
					</div>
					<div class="domBtnDiv" id="querytree{{$index}}_dom">
						<div ng-repeat="bo in allFields" class="item-div" domId="{{bo.id}}" domDesc="{{bo.desc}}" ng-if="(bo.name)==currentSubTable || !custBtn.isInSub"> 
							<div class="title-div">{{bo.desc}}<span ng-if="(bo.name)==currentSubTable">自定义按钮位于子表中，仅仅作用当前行</span></div>
							<span ng-repeat="attr in bo.attrs" class="domBtn" domId="{{bo.type=='main'?'':bo.type+'.'}}{{bo.name}}.{{attr.name}}">{{attr.desc}}</span>
						</div>
					</div>
				</fieldset>
			</div>
		  </div>
		
	</body>
</html>

	<script type="text/javascript">
		     //获取选择焦点，判断是编辑还是新增
		    var selectedDom = editor.selection.getStart();
		    var subDiv = $(selectedDom).closest("[type='subGroup']")
		    var isInSub  = subDiv.length==1;
		    var currentSubTable =""
		    if(isInSub) currentSubTable = subDiv.attr("tablename");
		    
		    
		    var custombuttonjson=null;//编辑的Json
		    if(selectedDom.attributes['ht-custDialog']!=null){
		    	custombuttonjson=JSON.parse(selectedDom.attributes['ht-custDialog'].value);
		    }
		    
		    var content =editor.getContent();
		    function isEditorContentContain(str){
		    	return content.indexOf(str)>=0;
		    }
		    
		    dialog.onok = function(){
		    	var scope =$("body").scope();
		    	if(custombuttonjson!=null){//删除旧的，添加新的
		    		$(selectedDom).after(scope.getHtml());
		    		$(selectedDom).remove(); 
		    	}else{
		    	editor.execCommand('inserthtml',scope.getHtml());
		    	}
			} 
		    
		</script>