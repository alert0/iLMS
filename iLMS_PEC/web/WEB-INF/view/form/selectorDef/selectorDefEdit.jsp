<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html ng-app="bpmSelectorApp">
	<head>
	    <%@include file="/commons/include/ngEdit.jsp" %>
        <script type="text/javascript" src="${ctx}/js/platform/system/dialog/iconDialog.js"></script>
        <script type="text/javascript" src="${ctx}/js/platform/form/bpmSelectorDef.js"></script>
        <script>
        	var selectorDefId = '${id}';
        </script>
	</head>
	<body ng-controller="selectorCtrl">
	    <div class="toolbar-panel" >
	        <div class="buttons" >
	            <button class="btn btn-primary btn-sm fa-save" ng-click="save()" ng-disabled="form.$invalid||isUnchanged()">
	                <span>保存</span>
	            </button>
	            <a href="javascript:;" ng-click="close();" class="btn btn-primary btn-sm fa-back ">
	                <span>关闭</span>
	            </a>
	        </div>
	    </div>
	    <form novalidate name="form" >
	        <table class="table-form" cellspacing="0">
	            <tr>
	                <th>名称:</th>
	                <td>
	                    <input type="text" class="inputText" ng-model="def.name" required/>
	                </td>
	            </tr>
	            <tr>
	                <th>别名:</th>
	                <td>
	                    <input type="text" class="inputText" ng-model="def.alias" required ht-tip title="别名对应调用js类名 eg: js 执行：userSelector(conf) 可调出userSelector选择框<br>conf.callBack会返回选择的数据数组"/>
	                </td>
	            </tr>
	            <tr>
	                <th>
	                  	  系统预定义:
	                </th>
	                <td>
	                    <label class="radio-inline">
	                        <input type="radio" name="flag" value="1" ng-model="def.flag" validate="{required:true}">是</label>
	                    <label class="radio-inline">
	                        <input type="radio" name="flag" value="0" ng-model="def.flag" validate="{required:true}">否</label>
	                </td>
	            </tr>
	            <tr>
	                <th>
	                    	组合字段:
	                </th>
	                <td>
	                    <table class="table-list">
	                        <thead>
	                            <tr>
	                                <td colspan="3">
	                                    <button class="btn btn-info btn-sm fa-add" ng-click="addItem()">添加字段</button>
	                                    <button class="btn btn-danger btn-sm fa-remove" ng-click="removeAll()">全部删除</button>
	                                </td>
	                            </tr>
	                            <tr>
	                                <th>名称</th>
	                                <th>键</th>
	                                <th>操作</th>
	                            </tr>
	                        </thead>
	                        <tbody>
	                        	<tr ng-repeat="item in def.groupField">
	                        		<td>
	                        			<input type="text" class="inputText" ng-model="item.name" required/>
	                        		</td>
	                        		<td>
	                        			<input type="text" class="inputText" ng-model="item.key" required/>
	                        		</td>
	                        		<td>
	                        			<button class="btn btn-danger btn-sm fa-remove" ng-click="removeItem(item)">删除</a>
	                        		</td>
	                        	</tr>
	                        </tbody>
	                    </table>
	                </td>
	            </tr>
	        </table>
	    </form>
	</body>
</html>