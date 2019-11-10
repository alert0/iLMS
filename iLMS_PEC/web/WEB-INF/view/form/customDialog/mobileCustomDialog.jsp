<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spr" uri="http://www.springframework.org/tags"%>
<%@include file="/commons/include/html_doctype.html"%>
<!DOCTYPE html>
<html ng-app="base">
<head>
<title>显示通用对话框</title>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="${ctx}/mobile/assets/js/importJs.js"></script>

<script type="text/javascript">
importCss(["/mobile/assets/js/ztree/css/zTreeStyle/zTreeStyle.css",
           "/mobile/assets/js/ztree/css/outLook.css"]);

importJs(["/mobile/assets/js/ztree/js/jquery.ztree.all-3.5.min.js",
          "/mobile/assets/js/dialog/customDialogController.js",
          "/mobile/assets/js/ztree/ZtreeCreator.js",]);
          
          

	var treeParamStr='${treeDialog.displayfield}';
	var isSingle=${isSingle};
	var combineField ='${combineField}';// 组合对话组合信息。
	var isCombine =${isCombine};//
	var needTree =${not empty treeDialog};//是否包含树
	var needList =${not empty listDialog};
	var returnField=${returnField};
	var listDialogalias = '${listDialog.alias}';
	var treeAlias ='${treeDialog.alias}'
	var ztreeCreator;
</script>
<style type="text/css">
	table { overflow:hidden; border:1px solid #d3d3d3; background:#fefefe; width:70%; margin:5% auto 0; -moz-border-radius:5px; /* FF1+ */ -webkit-border-radius:5px; /* Saf3-4 */
			border-radius:5px; -moz-box-shadow: 0 0 4px rgba(0, 0, 0, 0.2); -webkit-box-shadow: 0 0 4px rgba(0, 0, 0, 0.2); }
	th, td {padding:18px 28px 18px; text-align:center; }
	th {padding-top:22px; text-shadow: 1px 1px 1px #fff; background:#e8eaeb;}
	td {border-top:1px solid #e0e0e0;}
	tr.odd-row td {background:#f6f6f6;}
	td.first, th.first {text-align:left}
	td.last {border-right:none;}
	td { background: -moz-linear-gradient(100% 25% 90deg, #fefefe, #f9f9f9); background: -webkit-gradient(linear, 0% 0%, 0% 25%, from(#f9f9f9), to(#fefefe)); }
	tr.odd-row td { background: -moz-linear-gradient(100% 25% 90deg, #f6f6f6, #f1f1f1); background: -webkit-gradient(linear, 0% 0%, 0% 25%, from(#f1f1f1), to(#f6f6f6)); }
	th { background: -moz-linear-gradient(100% 20% 90deg, #e8eaeb, #ededed); background: -webkit-gradient(linear, 0% 0%, 0% 20%, from(#ededed), to(#e8eaeb));}
	tr.checked td {  background: #ffd;   }    
</style>
</head>
<body ng-controller="CustomDialogController" >
	<div class="page">
	<!-- 搜索框 begin -->
    	<%-- <c:if test="${fn:length(listDialog.conditionfield)>0}"> --%>
        <div class="bar bar-nav " style="height:auto;">
    	 <c:if test="${fn:length(listDialog.conditionfield)>0}">
	    	 <div class="searchbar row">
	    	  	<div class="search-input col-80" style="width: 64%;margin-left: 0%;">
				     <label class="icon icon-search" for="search" ng-click="reload(1)"></label>
				     <input type="search" id='search' ng-model="param._queryData" placeholder='输入{{placeholder}}'/>
			     </div>
		         <select ng-model="param._queryName" class="col-20" style="height: 1.4rem;border: none;width: 31%;padding:0;margin-left:1%;font-size: 0.8em " ng-change="setPlaceHolder()" id="select">
		           <!-- 查询参数 -->
		           <c:forEach items="${listDialog.conditionfield}" var="col" varStatus="status">
		           <c:if test="${col.defaultType eq '1'}">
		           	<option value="Q^${col.field}"  <c:if test="${!hasInit}">ng-init="param._queryName='Q^${col.field}';placeholder='${col.comment}'" selected</c:if>
		           	 >
		            ${col.comment}</option>
		           	<c:set value="true" var="hasInit"/>
		           	</c:if>
		           </c:forEach>
		         </select>
	     	 </div>
	     	  </c:if>
     	 <div >
		  	<div class="buttons-tab" style="margin-top: 0">
	    	<c:if test="${not empty treeDialog}">
			    <a href="#treeTab" class="tab-link button active" style="top: 0">树查找</a>
			</c:if>
			<c:if test="${not empty listDialog}">
			    <a href="#dataListTab" class="tab-link button active" style="top: 0">查找结果</a>
			</c:if>
		    	<a href="#returnDataTab" class="tab-link button" style="top: 0">已选({{returnData.length}})</a>
			</div>
 		</div>
   	 </div>
   	<%--  </c:if> --%>
     <!-- 搜索框  end -->
    <div >
    	<%-- <div class="buttons-tab" style="height: 51px;">
    	<c:if test="${not empty treeDialog}">
		    <a href="#treeTab" class="tab-link button active">树查找</a>
		</c:if>
		<c:if test="${not empty listDialog}">
		    <a href="#dataListTab" class="tab-link button active">查找结果</a>
		</c:if>
	    	<a href="#returnDataTab" class="tab-link button">已选({{returnData.length}})</a>
		</div> --%>
    
	  <div class="tabs" >
	  <!-- 树形 -->
	   <c:if test="${not empty treeDialog}">
		  <div class="tab active content" id="treeTab" style="margin-top: 15px;" > 
	    	<div class="content-block" style="min-height: 300px;padding-bottom: 20px">
	        	<div id="customerTree" class="ztree" ></div> 
	        </div>
		  </div>
	   </c:if>
  	 	<!-- 表格数据  -->
  	 	<c:if test="${not empty listDialog}">
		  <div class="tab active content" id="dataListTab"   style="<c:choose><c:when test='${fn:length(listDialog.conditionfield)>0}'>margin-top: 65px;</c:when><c:otherwise>margin-top: 15px;</c:otherwise></c:choose>" >
		 <c:if test="${listDialog.needPage}">
		  <div class="content-inner" style="height: 86%;">
		 </c:if>
		 <c:if test="${!listDialog.needPage}">
		  <div class="content-inner" style="height: 86%;overflow: auto;">
		 </c:if>
			<table style="width: 100%;">
		         <thead>
					<c:forEach items="${listDialog.displayfield}" var="field" varStatus="status">
		           		<th style="text-align: center !important;">${field.comment }</th>
		           	</c:forEach>	             
		          	 </tr>
		       </thead>
		       <tbody>
		         <tr ng-repeat="data in dataList" ng-click="selectOne(data)" class="{{$odd?'odd-row':''}} {{data.checked?'checked':''}}">
		           <c:forEach items="${listDialog.displayfield}" var="field" varStatus="status">
						<c:if test="${status.index == 0}"> <c:set var="displayName" value="${fn:toUpperCase(field.field)}"/></c:if>
					<td> 
		           		{{data.${fn:toUpperCase(field.field)}}}
		           	</td>
		           </c:forEach> 
		         </tr>
		        </tbody>
		     </table>
		     
		     
		     <!-- 分页 begin-->
		     <c:if test="${listDialog.needPage}">
		      <div class="button  button-light " style="text-align: left;margin-bottom: 2.8rem;margin-top:0.5rem " >
		      <a href="#" ng-click="prewPage()" class="icon icon-left" ></a>
		      <span>{{pageParam.page}}/{{pageResult.totalPages}}页</span>
		      <a href="#" class="icon icon-right" ng-click="nextPage()"></a>
		     	 每页条数
		     	 <select style="width:70px;margin:0.25rem 0;height: 1.4rem " ng-model="pageParam.pageSize" ng-change="reload()"> 
				  		<option value="5">5</option>
				  		<option value="10">10</option>
				  </select>
				  共{{pageResult.totalCount}}条
		      </div>
			</c:if>
			<!-- 分页 end -->
			
		     </div>
		  </div>
		  </c:if>
		 
		 <!-- 表格数据 end-->
		  <div  id="returnDataTab" class="tab" id="dataListTab"   style="<c:choose><c:when test='${fn:length(listDialog.conditionfield)>0}'>margin-top: 100px;</c:when><c:otherwise>margin-top: 50px;</c:otherwise></c:choose>" >
	    	<div class="row" style="min-height: 300px;padding-bottom: 20px;margin-left: 5px;">
	    		<span style="width:auto;" ng-repeat="data in returnData track by $index" class="col-50">
	    		 <span  class="button" style="margin: 4px 0;"  title="移除该项" ng-click="unChoiceOne($index)">
				        <span class="textSpan" style="text-overflow: ellipsis;overflow: hidden;display: inline-block;margin-bottom: -8px;max-width:250px">{{data.${displayName}}}</span>
				         <span class="fa fa-close"></span></span>
				 </span>
				</span>
			</div>
	     </div>
	  	</div>
	</div>
  	<div class="bar bar-tab buttons-row">
	  	<div style="height:30px;" class="button button-warning close-popover" ng-click="closeDialog()">取消</div>
	  	<div style="height:30px;" class="button button-danger" ng-click="cleanSelect()">清除</div>
       	<div style="height:30px;" class="button button-success" ng-click="dialogOk()" >确定</div>
 	</div>
 	
 	</div>
</body>
</html>