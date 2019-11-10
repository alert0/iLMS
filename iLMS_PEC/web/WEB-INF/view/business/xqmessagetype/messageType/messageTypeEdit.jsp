 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/business/xqmessagetype/messageType/messageTypeEditController.js"></script>
	 	<script type="text/javascript" src="${ctx}/js/common/util/dialogUtil.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/system/dialog/bpmDefDialog.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/util/rightsUtil.js"></script>  
	
	</head>
		<body ng-controller="ctrl" ng-init="load('${param.id}')">
		<div class="toolbar-panel col-md-13"><!--0000 -->
			<div class="buttons" style="margin-left:10px;">	      
		       <a id="saveFormLink" class="btn btn-sm btn-primary fa-save" ng-click="save();">保存</a>
		       <a class="btn btn-sm btn-primary fa-back" onclick="HT.window.closeEdit(true,'grid')" href="#">返回</a>
		    </div> 
	    </div>
		
		<form name="bpmDefAuthorizeForm" id="bpmDefAuthorizeForm" action="save" method="post" >
			<input type="hidden" name="id" ng-model="data.id">
			
			<table class="table-form">
					<tr>
						<th width="20%">分类名称:<span style='color:#f00;display:inline-block'> * </span></th>
						<td>
							<input type="text" style="width:300px;" class="form-control input-default" ng-model="data.classificationCode" ht-validate="{'required':true}" />
						</td>
					</tr>
											<tr>								
								<th>发布是否审批:</th>
								<td>
								<label>
								  <input type="radio"  ng-model="data.ispending"  ng-value="1" lablename="单选第二变量" validate="{}" ng-checked="data.ispending!='2' "/>
								 是
								</label
								<label>
								  <input type="radio"   ng-model="data.ispending"  ng-value="0" lablename="单选第二变量" validate="{}" ng-checked="data.ispending=='2' " />
								否
								</label>
								</td>								
							</tr>
							<tr ng-if="data.ispending!='0'">								
								<th>审批人姓名:</th>
								<td>
								 <input class="inputText" type="text" ng-model="data.pendingUserName" readonly="readonly"  ht-validate="{required:true,maxlength:192}"  />
									<input class="inputText" type="hidden" ng-model="data.pendingUserId"   ht-validate="{required:true,maxlength:192}"  />
									<a class="btn btn-primary btn-mini"  ng-click="userDialog()">
										<span>选择</span>
									</a>
								</td>
							</tr>
					<tr>
						<th width="20%">发布授权</th>
						<td>

							<div class="toolbar-panel col-md-13 ">
								<div class="buttons"  style="margin-left:0" >	      
							       <a class="btn btn-sm btn-primary fa fa-add" ng-click="setAuth();">选择</a> 
							   
							    </div>
						    </div>
							
							<table id="bpmDefUserTable"  class="table-list">
									<thead>
										<tr>
											<th width="30%" >权限分类</th>
											<th width="70%" >授权给</th>
										</tr> 
									</thead>
									<tr class="empty-div" ng-if="data.ownerNameJson.length==0">
										<td colspan="2" style="text-align: center;"> 
											没有授权的人员
										</td>
									</tr>
									<tbody ng-if="data.ownerNameJson.length>0">
										
										<tr  ng-repeat="item in data.ownerNameJson track by $index">
											<td> 
												{{item.title}}
											</td>
											<td> 
												{{item.name}}
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

<script>
function saveColumnAuthority(id,data){
	debugger;
	var params ={
	    id : id,
	    rightsData : JSON.stringify(data),
	}
	$.ajax({
		   type: "POST",
		   url:  __ctx + "/business/xqmessagetype/messageType/saveColumnAuthority",
		   async:true,
		   dataType:'json',
		   data: params,
		   success: function(data){
			   if(data.result==1){
				   $.topCall.alert("温馨提示",data.message);
				}else{
					$.topCall.error(data.message);
				}
		}
	});
	
}
</script>



