<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<%@include file="/commons/include/incTree.jsp"%>
		
		<script type="text/javascript">
			window.UEDITOR_HOME_URL="${ctx}/js/lib/ueditor/";
			var formType = "${form.formType}";
		</script>
		
		<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/bpmform.udeitor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/ueditor.all.min.js"></script>
		<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/lang/zh-cn/zh-cn.js"></script>
		<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/plugins/picture.js"></script>
		<script type="text/javascript" src="${ctx}/js/common/util/dialogUtil.js"></script> 
		
		<script type="text/javascript" src="${ctx}/js/mail/mail/mail/MailDef.js"></script>
		<script type="text/javascript" src="${ctx}/js/mail/mail/mail/mailEditController.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/system/dialog/uploadDialog.js"></script>
	
	<style type="text/css">
		html,body {
			padding: 0px;
			margin: 0;
			width: 100%;
			height: 100%;
			overflow: hidden;
		}
		th{
			width:20%;
		}
		td{
			width:80%;
		}
		.tree-title {
			overflow: hidden;
			width: 8000px;
		}
		
		.ztree {
			overflow: auto;
		}
		
		.link-btn {
			cursor: pointer;
			display: inline-block;
			padding: 0 4px 1px 20px;
		}
		
		.link-remove {
			background: url(${ctx}/styles/default/images/button/remove.png) 0px 3px
				no-repeat !important;
			text-decoration: none;
		}
		.tree-toolbar {
		    border-bottom: medium none;
		    border-top: medium none;
		    height: 24px;
		    padding-left: 2px;
		    padding-top: 4px;
		    text-align: center;
		}
		.mail-tree {
		    padding: 2px 6px;
		    line-height: 1.2;
		    font-size: 10px;
		}
	</style>
	<script type="text/javascript">
			//布局大小改变的时候通知tab，面板改变大小
		    function heightChanged(options){
		     	$("#linkmanTree").height(options.middleHeight - 60);
		    };
		</script>

	</head>
	<body  class="easyui-layout" ng-controller="ctrl">
	
			<div data-options="region:'east',border:true" title="最近联系人" style="width:200px;"  >
	            <div class="tree-toolbar" id="pToolbar">
	                <div class="toolBar" style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">
	                	<div class="tree-toolbar">
							<a id="sortingByTimes" ng-click="loadTree('sortingByTimes')" class="btn btn-sm btn-primary fa-refresh mail-tree" > 次数</a> 
							<a id="sortingByLasttime" ng-click="loadTree('sortingByLasttime')" class="btn btn-sm btn-primary fa-refresh mail-tree">最后发送</a> 
						</div>
	                </div>
	            </div>
	            <div id="linkmanTree" class="ztree" style="height:90%;width: 100%;overflow:auto;"></div>
	            
	            <div class="hidden">
			       <div id="orgMenu" class="easyui-menu"  style="width: 120px;">
					 <div ng-click='delNode()'>删除该联系人</div> 
			    </div>
		      </div>
	            
	        </div>
	        <div ng-click='removeFocusTarget($event);' data-options="region:'center',border:true" style="text-align:center;overflow: auto;"  >
	            <div position="center">
	                <div class="panel">
	                	<div class="toolbar-head">
							<!-- 顶部按钮 -->
							<div class="buttons">
								<a  class="btn btn-sm btn-primary fa-send" ng-click="sendMail(2)">
									<span>发送</span>
								</a> 
								<a class="btn btn-sm btn-primary fa-save" href="#"  ng-click="sendMail(3)">
									<span>保存草稿</span>
								</a>
								<a class="btn btn-sm btn-primary fa-rotate-left" href="#" ng-click="reset()">
									<span>  重置</span>
								</a>
							</div>
						</div>
	                    <div class="panel-body">
	                        <form name="form" ht-load="getJson?id=${param.id}" ng-model="data">
	                            <table class="table-form"  cellspacing="0" >
	                                <tr>
	                                    <th>
	                                       		发件人:
	                                        <span  class="required">*</span>
	                                    </th>
	                                    <td>
	                                        <select name="senderAddress" ng-model="data.senderAddress" style="width: 35%;">
	                                            <c:forEach items="${mailSettingList}" var="mail">
	                                                <option value="${mail.mailAddress}" address="${mail.id}" <c:if test="${mail.isDefault==1}">selected='selected'</c:if>>${mail.mailAddress}</option>
	                                            </c:forEach>
	                                        </select>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th>
	                                       		 收件人:
	                                        <span class="required">*</span>
	                                    </th>
	                                    <td height="45">
	                                        <input type="text" address="true" id="receiverAddresses" name="receiverAddresses" ng-model="data.receiverAddresses" class="inputText" style="width: 80%;" />
	                                        <input type="hidden" name="receiverName" ng-model="data.receiverName" class="inputText" style="width: 80%;" />
	                                        <a class="link search" ng-click="selectLinkMan('receiverAddresses');">
	                                            <span></span>
	                                           	 选择
	                                        </a>
	                                        &nbsp;&nbsp;
	                                        <span id="warn" style="color:red;display:none" >*收件人必填</span>
	                                        <br>
	                                        (注：可以直接单击最近联系人列表，也可手动输入，多个收件人以","号分割如两个收件人：aaa@163.com,bbb@163.com)
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th>抄送人:</th>
	                                    <td>
	                                        <input type="text"  address="true" name="copyToAddresses" ng-model="data.copyToAddresses" class="inputText" style="width: 80%;" />
	                                        <input type="hidden" name="copyToName" ng-model="data.copyToName" class="inputText" style="width: 80%;" />
	                                        <a class="link search" ng-click="selectLinkMan('copyToAddresses');">
	                                            <span></span>
	                                            	选择
	                                        </a>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th>暗送人:</th>
	                                    <td>
	                                        <input type="text" address="true" name="bcCAddresses"  ng-model="data.bcCAddresses" class="inputText" style="width: 80%;" />
	                                        <input type="hidden" name="bccName" ng-model="data.bcCAddresses" class="inputText" style="width: 80%;" />
	                                        <a class="link search" ng-click="selectLinkMan('bcCAddresses');">
	                                            <span></span>
	                                          		  选择
	                                        </a>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th>主题:</th>
	                                    <td>
	                                        <input type="text" name="subject" ng-model="data.subject" class="inputText" style="width: 80%;" />
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th>附件:</th>
	                                    <td >
	                                       <div ht-upload="data.fileIds" issingle="" ng-model="data.fileIds"  desc="附件" type="text"></div>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th>发送内容:</th>
	                                    <td>
	                                        <textarea ht-editor="{'initialFrameHeight':'150','width':'600','initialFrameWidth':'95%','height':'600'}" name="content" ng-model="data.content">${outMail.content}</textarea>
	                                    </td>
	                                </tr>
	                            </table>
	                        </form>
	                    </div>
	                </div>
	            </div>
	       </div>
	 </body>
</html>