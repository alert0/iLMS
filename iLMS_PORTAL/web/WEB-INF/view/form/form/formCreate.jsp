<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<!DOCTYPE html>
<html>
    <head>
    	<title>表单创建</title>
        <%@include file="/commons/include/ngEdit.jsp"%>
        <link href="${ctx}/js/lib/wizard/jquery.steps.css?1" rel="stylesheet">
	    <link href="${ctx}/js/lib/wizard/style.css" rel="stylesheet">
        <script type="text/javascript" src="${ctx}/js/platform/form/formList.js"></script>
        <script type="text/javascript" src="${ctx}/js/lib/wizard/jquery.steps.js"></script>
        <script type="text/javascript" src="${ctx}/js/platform/form/formCreate.js"></script>
    </head>
    <body class="easyui-layout">
    		<div data-options="region:'center',border:false">
		        <div id="editErea" style="width: 100%; height:100%;">
		        	<div id="boEntTypeDiv">
			    		<table class="table-form" cellspacing="0">
							<tr>
								<td style="text-align: right;">实例类型:</td>
								<td>
									<label class="radio-inline"><input type="radio" name="boEntType" style="cursor: pointer;" onclick="formNextStep('current','/bo/def/bOEntEdit','bOEntEdit')" checked="checked">
									<span>创建表</span></label>
									<label class="radio-inline"><input type="radio" name="boEntType" style="cursor: pointer;" onclick="formNextStep('current','/bo/def/bOEntExtEdit','bOEntExtEdit')">
									<span>添加外部表</span></label>
								</td>
							</tr>
						</table>
			    	</div>
		            <iframe id="buildFormFrame" onload="load()" name="buildFormFrame" src="${ctx}/bo/def/bOEntEdit" frameborder="no" width="100%" height="100%"></iframe>
		        </div>
			</div>
    
    	<div data-options="region:'east',border:false" style="width: 280px;">
		    	<section id="main-content">
			        <section class="wrapper" style="padding-top:0px; ">
			        <!-- page start-->
				        <div class="row" id="wrappercontent" style="display: none;">
				            <div class="col-sm-12">
				               
				                <section class="panel">
				                    <div class="panel-body" style="padding-top: 15px;">
				                        <div id="wizard-vertical">
				                            <h2>创建实例</h2>
				                            <section>
				                            	<p></p>
				                            </section>
				
				                            <h2>业务对象定义</h2>
				                            <section>
				                                <p></p>
				                            </section>
				                            
				                            <h2>表单定义</h2>
				                            <section>
				                                <p></p>
				                            </section>
				
				                            <h2>表单元数据定义</h2>
				                            <section>
				                                <p></p>
				                            </section>
				
				                            <h2>创建表单</h2>
				                            <section>
				                                <p></p>
				                            </section>
				                        </div>
				                </section>
				                </div>
				            </div>
				        </div>
				        <!-- page end-->
				        </section>
				    </section>
				    <!--main content end-->
				<!--right sidebar start-->
				<div class="right-sidebar">
				
				<div class="right-stat-bar">
				<ul class="right-side-accordion">
				<li class="widget-collapsible">
				    
				    <ul class="widget-container">
				        <li>
				            <div class="prog-row side-mini-stat clearfix">
				                <div class="side-mini-graph">
				                    <div class="target-sell">
				                    </div>
				                </div>
				            </div>
				            
				           
				        </li>
				    </ul>
				</li>
				
				
				
				</ul>
				</div>
				</div>
				<!--right sidebar end-->
				
				</section>
		</div>
    </body>
</html>
<script>
	var formTypeId = "";
	var iframe;
	$(function (){
		$(document).bind("keydown", function(e) {
			e = window.event || e;
			if (e.keyCode == 116) {
				if(!confirm("您确定要刷新当前页面吗？刷新后将回到第一步“创建实例”！")){
					e.keyCode = 0;
					return false;
				}
			}
	    });
		window.onbeforeunload = function() {				 											
   		  return '你确定吗？';
   	    };
        $("#wizard-vertical").steps({
            headerTag: "h2",
            bodyTag: "section",
            transitionEffect: "slideLeft",
            stepsOrientation: "vertical"
        });
        $("#wrappercontent").show();
    });
	
	function load(){
		iframe = $(window.frames["buildFormFrame"].document);
	}
</script>
