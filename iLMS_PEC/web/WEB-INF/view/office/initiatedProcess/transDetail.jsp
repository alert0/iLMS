<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<%@include file="/commons/include/edit.jsp"%>
<title>任务流转明细</title>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div data-options="region:'center',border:false">
			<div class="toolbar" style="margin:5px;">
				<button onClick="HT.window.closeEdit();" class="btn btn-primary btn-sm fa-close">
					<span>关闭</span>
				</button>
			</div>
			<div style="height: 100%;width: 100%;overflow:scroll;">
				<table cellspacing="0" class="table-form">
					<tr>
						<th colspan="6" style="text-align: center;">流转规则</th>
					</tr>
					<tr>
						<th>计票策略：</th>
						<td>
							<c:choose>
								<c:when test="${trans.decideType=='agree'}">同意</c:when>
								<c:otherwise>反对票</c:otherwise>
							</c:choose>
						</td>
						<th>
							<span class="pull-right">投票类型：</span>
						</th>
						<td>
							<c:choose>
								<c:when test="${trans.voteType eq 'amount'}">绝对票数</c:when>
								<c:otherwise>百分比</c:otherwise>
							</c:choose>
						</td>
						<th>
							<span class="pull-right">票数：</span>
						</th>
						<td>
							${trans.voteAmount}
							<c:if test="${trans.voteType=='percent'}">%</c:if>
						</td>
					</tr>
					<tr>
						<th>
							<span class="pull-right">流转类型：</span>
						</th>
						<td> 
							<c:choose>
								<c:when test="${trans.signType=='parallel'}">并行</c:when>
								<c:otherwise>串行</c:otherwise>
							</c:choose>
	
						</td>
						<th>
							<span class="pull-right">完成后的动作：</span>
						</th>
						<td>
							<c:choose>
								<c:when test="${trans.action=='submit'}">提交</c:when>
								<c:otherwise>返回</c:otherwise>
							</c:choose>
	
	
						</td>
						<th></th>
						<td></td>
					</tr>
					<tr>
						<th colspan="6" style="text-align: center;">流转审批详情</th>
					</tr>
					
					<c:forEach items="${trans.receiverList}" var="item">
								<tr>
									<th>人员：</th>
									<td>${item.receiver }</td>
									<th>审批状态：</th>
									<td>
									
										<c:choose>
											<c:when test="${trans.status == 2 }">撤销取消；</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${item.checkType == 1 }">同意；</c:when>
													<c:when test="${item.checkType == 2}">反对；</c:when>
													<c:otherwise>尚未审批</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
										
									</td>
									<th>意见：</th>
									<td>${item.opinion }</td>
								</tr>
							</c:forEach>
				</table>
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
</body>
</html>