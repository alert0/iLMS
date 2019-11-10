<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/js/lib/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript">
		var dialog;
		$(function(){
			$('#messageReplyList').datagrid({
		        url:__ctx +'/platform/innermsg/sysMessage/getMessageReply.ht?messageId='+${param.messageId},
		        //view: cardview,
		      }); 
			
		});
		
		 var cardview = $.extend({}, $.fn.datagrid.defaults.view, {
			    renderRow: function(target, fields, frozen, rowIndex, rowData){
			    var cc = [];
			    var timeT=rowData[fields[1]];
			    var tirmValue=moment(timeT).format('YYYY-MM-DD HH:mm:ss');
			    if (!frozen){
			    cc.push('<td style="padding:10px 5px;border:0; width:20%">');
			    cc.push('<div style="float:left;margin-left:20px;">');
			    cc.push('<p><span class="c-label">' + rowData[fields[0]] + ':</span><br> <span class="c-label" htmoment="htTime">' + tirmValue + '</span></p>');
			    cc.push('</div>');
			    cc.push('</td>');
			    cc.push('<td colspan="2" style="padding:10px 5px;border:0;">');
			    cc.push(rowData[fields[2]]);
			   /*  cc.push('<textarea id="content_'+rowIndex+'" name="content_'+rowIndex+'" style="height: 100%;width: 100%" >'+rowData[fields[2]]+'</textarea>');
			    var Msgue = UE.getEditor('content_'+rowIndex+'',{  
	                //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个  预览
	                 toolbars:[ ['FullScreen', 'Source', 'Undo', 'Redo','bold','test'] ],   
	                //关闭字数统计  
	                wordCount:false,  
	                //关闭elementPath  
	                elementPathEnabled:false
	            }); */
			    cc.push('</td>');
			    }
			    return cc.join('');
			    }
			    });
		 
		 function replyDialog(conf){
			 var me= $(conf);
			 var url = __ctx+me.attr("action");
			 var passConf = {isDialog:1,dialog:this.dialog};
			 var def={
					 passConf:passConf,title:'消息回复',width:900, height:500, modal:true,resizable:true,iconCls: 'fa fa-user',
				        buttonsAlign:'center',
				        buttons:[{
							text:'回复',
							iconCls:'btn btn-success fa-check-circle',
							handler:function(e){
								var win = dialog.innerWin ; // iframe.contents();
								var frm = win.$('#messageReplyForm').form();
								frm.ajaxForm({success:showResponse});
								var content = win.Replyue.getContent();
								if (content && content!="") {
									win.$('#messageReplyForm').submit();
								}else{
									$.topCall.error("请填写回复内容!");
								}
							}
						},{
							text:'取消',
							iconCls:'btn btn-danger fa-times-circle',
							handler:function(){dialog.dialog('close');}
						}]
			 }
			 this.dialog=$.topCall.dialog({
					src:url,
					base:def
				})
		 }
		 
		  function showResponse(responseText) {
				var resultMessage=new com.hotent.form.ResultMessage(responseText);
				if(resultMessage.isSuccess()){
					$.topCall.success(resultMessage.getMessage(),function(r){
						dialog.dialog('close');
						window.location.reload(true);
					},"温馨提示");
				}else{
					$.topCall.error(resultMessage.getMessage(),resultMessage.getCause());
				}
			}
		 
</script>
</head> 
<body class="easyui-layout">
	<div  class="toolbar-search col-md-13 ">
		<div class="toolbar-box border">
			<div class="toolbar-head">
				<!-- 顶部按钮 -->
				<div class="buttons"> 		
					<a class="btn btn-primary fa-add" href="javascript:;" action="/platform/innermsg/messageReply/edit.ht?id=${messageId}" onclick="replyDialog(this)" ><span>回复</span></a>
				</div>
			</div>
	    </div>
	</div>
	<table id="messageReplyList" idField="id"  
		data-options="fitColumns:false,checkOnSelect:true,pagination:true,toolbar:'.toolbar-search'"
		fit="true">
		<thead>
			<tr>
				<th data-options="width:100,align:'center'" field="reply" sortable="false" sortName="reply_" title="回复人" ></th>
				<th data-options="width:150,align:'center'" field="replyTime" sortable="false" sortName="reply_time_"
					title="回复时间" formatter="ht" dateFormat="YYYY-MM-DD HH:mm:ss"></th>
				<th data-options="align:'center'" field="content" title="回复内容"  ></th>
			</tr>
		</thead>
	</table>
</body>
</html>