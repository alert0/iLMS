
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/list.jsp" %>
<title>选择模板</title>
<style type="text/css">
	html,body{height:100%;width:100%; overflow: auto;overflow-x:hidden;}
</style>
<script type="text/javascript">
function ok(callback){
	var aryAlias="";
	var aryTableId="";
	var formType = '${formType}'
	$("select[templateId='templateId']").each(function(i){
		aryTableId+=$(this).attr("tableid")+",";
		aryAlias+=$(this).val()+",";
	});
	var json = {tableNames:aryTableId,templateAlias:aryAlias,formType:formType}
	callback(json);
};
</script>
</head>
<body>
	<div>
		<div class="panel-top">
			<c:if test="${isSimple==0}">
				<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link run" id="dataFormSave" href="javascript:;"><span></span>下一步</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link back" onclick="history.back()"><span></span>返回</a></div>
				</div>
			</div>
			</c:if>
		</div>
		<div>
			<form id="frmDefInfo">
					<c:if test="${bpmForm != null}">
						<c:forEach items="${tableList}" var="table">
							<c:if test="${empty param.tableName || table.name eq param.tableName}">
							<nav class="navbar navbar-default" role="navigation">
						      <div class="container-fluid">
						        <div class="navbar-header">
						          <div class="navbar-brand" style="width: 150px;overflow: hidden;white-space: nowrap;text-overflow:ellipsis;">${table.desc }模板:</div>
							          <div class="" style="width: 210px;float: left;margin-top: 8px;">
							          	<select class="form-control initial" templateId="templateId" tableId="${table.name}">
							          		<c:choose>
							          			<c:when test="${table.type eq 'sub' }">
								          			<c:forEach items="${subTableTemplates}" var="bpmFormTemplate">
														<option value="${bpmFormTemplate.alias}">${bpmFormTemplate.templateName}</option>
													</c:forEach>
							          			</c:when>
							          			<c:otherwise>
								          			<c:forEach items="${mainTableTemplates}" var="bpmFormTemplate">
														<option value="${bpmFormTemplate.alias}">${bpmFormTemplate.templateName}</option>
													</c:forEach>
							          			</c:otherwise>
							          		</c:choose>
										</select>
							        </div>
						        </div>
						      </div>
						    </nav>	
						    </c:if>				
						</c:forEach>
					</c:if>
				</div>
				<input type="hidden" name="templateAlias" id="templateAlias" />
				<input type="hidden" name="templateTableId" id="templateTableId" />
			</form>
	</div> <!-- end of panel -->
</body>
</html>


