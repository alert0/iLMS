<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/mail/mail/mailSetting/mailSettingEditController.js"></script>
	</head>
	<body ng-controller="ctrl">
		
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">
					<a class="btn btn-primary fa-save" ng-model="data" ht-save="save"><span>保存</span></a>
					<a class="btn btn-primary fa-back" onClick="HT.window.closeEdit(true,'grid');"><span>返回</span></a>
					<a class="btn btn-primary fa-cog" ng-click="testConnect();"><span>测试连接</span></a>
				</div>
			</div>
			<form id="mailSetingForm" name="form" method="post" ht-load="getJson?id=${param.id}"  ng-model="data">
				<table class="table-form"  cellspacing="0" >
							<tr>								
								<th>用户名称:</th>
								<td>
									<input class="inputText" type="text" name='nickName' ng-model="data.nickName"   ht-validate="{required:true,maxlength:80}"  />
								</td>
								<th>邮件类型:</th>
								<td>
									<select name='mailType' ng-model="data.mailType">
										<option  value="pop3"  class="ng-binding ng-scope" ng-selected="!data.mailType||data.mailType=='pop3'">pop3类型</option>
										<option  value="imap" class="ng-binding ng-scope">imap类型</option>
	                               </select>
								</td>										
							</tr>
							<tr>								
								<th>邮箱地址:</th>
								<td>
									<input class="inputText" name='mailAddress' id="mailAddress" ng-blur="showInfos()" type="text" ng-model="data.mailAddress"   ht-validate="{required:true,maxlength:100}"  />
								</td>	
								<th>邮箱密码:</th>
								<td>
									<input class="inputText" name='password' id="password" type="password" ng-model="data.password"   ht-validate="{required:${param.id==null},maxlength:20}"  />
									<a href="#" style="text-decoration: none;" title="该密码为邮箱授权码，非登录密码。" class="fa fa-exclamation-circle ht-help" ht-tip> </a>
								</td>								
							</tr>
							<tr>								
								<th>smtp主机:</th>
								<td>
									<input class="inputText" name='smtpHost' type="text" ng-model="data.smtpHost"   ht-validate="{required:true,maxlength:384}"  />
								</td>	
								<th>smtp端口:</th>
								<td>
									<input class="inputText" name='smtpPort' type="text" ng-model="data.smtpPort"   ht-validate="{required:true,maxlength:192}"  />
								</td>									
							</tr>
							<tr ng-if="data.mailType!='imap'">								
								<th>pop主机:</th>
								<td>
									<input class="inputText" name='popHost' type="text" ng-model="data.popHost"   ht-validate="{required:false,maxlength:384}"  />
								</td>	
								<th>pop端口:</th>
								<td>
									<input class="inputText" name='popPort' type="text" ng-model="data.popPort"   ht-validate="{required:false,maxlength:192}"  />
								</td>								
							</tr>
							<tr ng-if="data.mailType=='imap'">								
								<th>imap主机:</th>
								<td>
									<input class="inputText" name='imapHost' type="text" ng-model="data.imapHost"   ht-validate="{required:false,maxlength:384}"  />
								</td>	
								<th>imap端口:</th>
								<td>
									<input class="inputText" name='imapPort' type="text" ng-model="data.imapPort"   ht-validate="{required:false,maxlength:192}"  />
								</td>								
							</tr>
							<tr>								
								<th>使用SSL认证:</th>
								<td>
									<input type="checkbox" name='SSL' ng-change="changeSSL()" ng-model="data.sSL"  />
								</td>
								<th>需要身份验证:</th>
								<td>
									<input type="checkbox" name='validate' ng-model="data.validate" />
								</td>
							</tr>
							<tr>								
								<th>删除远程邮件:</th>
								<td>
									<input type="checkbox" name='isDeleteRemote' ng-model="data.isDeleteRemote" />
								</td>	
								<th>下载附件:</th>
								<td>
									<input type="checkbox" name='isHandleAttach'  ng-model="data.isHandleAttach" />
								</td>								
							</tr>
				</table>
			</form>
		
	</body>
</html>