<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springsecurity.org/jsp"%>
<html>
<head>
<title>测试DEMO</title>
<%@include file="/commons/include/list.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/index/bootstrap-dialog.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/lodop/LodopFuncs.js"></script>
<script>
var orgId="${param.orgId}";
$(function(){
});
function setMaster(userId){
	
}

function syncUserToWx(){
    
}

</script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div id="gridSearch" class="toolbar-search ">
			<div class="toolbar-head">
				<!-- 顶部按钮 -->
				<div class="buttons">
					<a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)">
						<span>搜索</span>
					</a>
					<a href="javascript:;" class="btn btn-sm btn-primary fa-rotate-left">
						<span>重置</span>
					</a>
					<a class="btn btn-sm btn-primary fa-add" id="add" href="javascript:void(0)" onclick="openDetail('','add')">
						<span>添加</span>
					</a>
					<security:authorize name="ADDTEST">
						<a class="btn btn-sm btn-primary fa-add" id="add" href="javascript:void(0)">
							<span>按钮权限测试1</span>
						</a>
					</security:authorize>
					<security:authorize name="ADDTEST2">
						<a class="btn btn-sm btn-primary fa-add" id="add" href="javascript:void(0)">
							<span>按钮权限测试2</span>
						</a>
					</security:authorize>
					<a class="btn btn-sm btn-primary fa-print" href="javascript:void(0)" onclick="printTest()">
						<span>打印测试(LODOP)</span>
					</a>
					<a class="btn btn-sm btn-primary fa-print" href="javascript:void(0)" onclick="pdfPrintTest()">
						<span>打印测试(PDF)</span>
					</a>
					<a class="btn btn-sm btn-primary fa-remove" href="javascript:void(0)" action="/org/user/remove">
						<span>删除</span>
					</a>

					<a class="btn btn-sm btn-primary fa-add" href="javascript:void(0)" onclick="syncUserToWx()" >
						<span>同步微信通讯录</span>
					</a>
					
					<a class="btn btn-primary btn-sm fa-sign-in"  href="javascript:void(0)" onclick="importUser()">
						<span>从excel导入</span>
					</a>

				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse">
						<i class=" fa  fa-angle-double-up"></i>
					</a>
				</div>
			</div>
			<div class="toolbar-body">
				<form id="searchForm" class="search-form">
					<ul>
						<li><span>姓名:</span><input class="inputText" type="text" name="Q^fullname_^SL"></li>
						<li><span>账号:</span><input class="inputText" type="text" name="Q^account_^SL"></li>
						<li><span>状态:</span>
						<select class="inputText" name="Q^status_^SL">
						    <option value="">全部</option>
						  <option value="0">禁用</option>
						   <option value="1">正常</option>
						</select>
						 </li>
					</ul>
				</form>
			</div>
		</div>
		<div id="grid" class="my-easyui-datagrid" ></div>
	</div>
	<div hidden id="printlabelhtml"> <!-- printarea -->
		<div class="">
<STYLE> html { overflow-x: hidden; overflow-y: auto; font-family:"微软雅黑";background-color:white;}  body, h1, h2, h3, h4, h5, h6, hr, p, blockquote,dl, dt, dd, ul, ol, li,pre,fieldset,  lengend, button,select, input, textarea,th, td {  margin: 0;  padding: 0;  }  .table1{  border-collapse: collapse;  border-spacing: 0;  border:3px #000000 blue;  width:100%;  margin-bottom:0mm;  }  .fontStyle{  height:5mm;  font-size:11px;  font-family:"微软雅黑";  }  .tableStyle{  border:0px solid #000000;  width:100%;  height:100%;  }  .allBorderStyle{  border: 1px solid #000000;  }  .borderStyle{  border-left:1px #000000 solid;  border-bottom: 1px #000000 solid;  border-right:1px #000000 solid;  }  .borderStyleOutRight{  border-left:1px #000000 solid;  border-bottom: 1px #000000 solid;  border-top:1px #000000 solid;  }  .borderStyleRight{  border-right:1px #000000 solid;  }  .borderLeftTop{  border-left:1px #000000 solid;  border-top:1px #000000 solid;  }  .borderRihgtTop{  border-right:1px #000000 solid;  border-top:1px #000000 solid;  } .title{text-align:center;}.value{text-align:center;}.divMain{border-collapse:collapse;border:0px solid black;position:relative;top:0mm;left:0mm;height:275mm;width:190mm;}.divWaterMark{position:absolute;border:0px solid red;font-family:Arial;width:93mm;height:67mm;z-index:-2;color:gray;text-align:left;line-height:90mm;background-color:whitefilter: alpha(opacity=50);-moz-opacity: 0.5;-khtml-opacity: 0.5;opacity:0.5;}.divData{border-collapse:collapse;border:0px solid green;position:absolute;height:87mm;width:190mm;}</STYLE>

<DIV class=divMain>
<DIV class=divData style="LEFT: 0mm; TOP: 0mm">
<TABLE class=table1 style="BORDER-TOP: black 2px solid; BORDER-RIGHT: black 2px solid; BORDER-BOTTOM: black 2px solid; BORDER-LEFT: black 2px solid; WIDTH: 100%" cellSpacing=0 cellPadding=0>
<TBODY>
<TR>
<TD style="HEIGHT: 25mm">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD style="BORDER-RIGHT: #000000 1px solid; VERTICAL-ALIGN: top; BORDER-BOTTOM: #000000 1px solid; PADDING-TOP: 1mm; PADDING-LEFT: 1mm; WIDTH: 25mm"><IMG style="HEIGHT: 23mm; WIDTH: 23mm" align=left > </TD>
<TD style="PADDING-LEFT: 1mm; WIDTH: 28%">
<TABLE class=tableStyle>
<TBODY>
<TR>
<TD class=fontStyle>零件名称</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 18px; FONT-WEIGHT: bold; TEXT-ALIGN: center">右侧围上加强板</TD></TR>
<TR>
<TD class=borderStyle style="FONT-SIZE: 18px; FONT-WEIGHT: bold; TEXT-ALIGN: center">4122031CAD0000</TD></TR></TBODY></TABLE></TD>
<TD style="PADDING-LEFT: 1mm; WIDTH: 23%">
<TABLE class=tableStyle>
<TBODY>
<TR>
<TD class=fontStyle>零件简号</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 44px; FONT-WEIGHT: bold; TEXT-ALIGN: center">NW056</TD></TR></TBODY></TABLE></TD>
<TD style="PADDING-LEFT: 1mm; PADDING-RIGHT: 1mm; WIDTH: 23%">
<TABLE class=tableStyle cellSpacing=0 cellPadding=0>
<TBODY>
<TR>
<TD class=fontStyle>正规包装数</TD>
<TD class=fontStyle>本次包装数</TD></TR>
<TR>
<TD class=borderStyleOutRight style="FONT-SIZE: 40px; FONT-WEIGHT: bold; TEXT-ALIGN: center">90</TD>
<TD class="borderStyleOutRight borderStyleRight" style="FONT-SIZE: 40px; FONT-WEIGHT: bold; TEXT-ALIGN: center">90</TD></TR></TBODY></TABLE></TD>
<TD style="VERTICAL-ALIGN: top; BORDER-BOTTOM: #000000 1px solid; PADDING-BOTTOM: 1mm; PADDING-TOP: 1mm; BORDER-LEFT: #000000 1px solid; PADDING-RIGHT: 1mm; WIDTH: 25mm"><IMG style="HEIGHT: 23mm; WIDTH: 23mm" align=right > </TD></TR></TBODY></TABLE></TD></TR>
<TR>
<TD style="PADDING-TOP: 1mm; WIDTH: 100%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD style="WIDTH: 40%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" border=0>
<TBODY>
<TR>
<TD style="PADDING-LEFT: 1mm">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
<TBODY>
<TR>
<TD class=fontStyle width="100%" colSpan=4>供应商</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 16pt; HEIGHT: 100%; WORD-BREAK: break-all; FONT-WEIGHT: bold; TEXT-ALIGN: center; WIDTH: 70%">海斯坦普汽车组件（东莞）有限公司</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 18pt; FONT-WEIGHT: bold; TEXT-ALIGN: center; WIDTH: 30%">VG420</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
<TR>
<TD style="PADDING-TOP: 1mm; PADDING-LEFT: 1mm" height="100%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD class=fontStyle>出货仓库</TD>
<TD>&nbsp;</TD>
<TD class=fontStyle>出货库区</TD>
<TD>&nbsp;</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 16pt; FONT-WEIGHT: bold; TEXT-ALIGN: center; LINE-HEIGHT: 1.2em; WIDTH: 45%">VG420</TD>
<TD style="WIDTH: 3mm">&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 12pt; FONT-WEIGHT: bold; TEXT-ALIGN: center; LINE-HEIGHT: 1em; WIDTH: 55%">&nbsp;</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
<TR>
<TD style="PADDING-TOP: 1mm" height="34%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD class=borderRihgtTop style="VERTICAL-ALIGN: top; PADDING-BOTTOM: 1mm; PADDING-TOP: 1mm; PADDING-LEFT: 1mm; PADDING-RIGHT: 1mm; WIDTH: 25mm"><IMG style="HEIGHT: 23mm; WIDTH: 23mm" align=left > </TD>
<TD style="PADDING-BOTTOM: 1mm; PADDING-LEFT: 1mm; WIDTH: 90%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD class=fontStyle style="WIDTH: 50%">物流订单号</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=fontStyle style="WIDTH: 50%">ERP订单号</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 12pt; TEXT-ALIGN: center; line-heirht: 1em">&nbsp;G2-AA18080600270&nbsp;</TD>
<TD>&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 12pt; TEXT-ALIGN: center; line-heirht: 2em">&nbsp;KA0536284&nbsp;</TD>
<TD>&nbsp;</TD></TR>
<TR>
<TD style="PADDING-TOP: 1mm" colSpan=3>
<TABLE style="WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD class=borderStyleOutRight style="FONT-SIZE: 8pt">&nbsp;PAGE&nbsp;1/10</TD>
<TD class="borderStyleOutRight borderStyleRight" style="FONT-SIZE: 8pt">&nbsp;到达&nbsp;2018-08-06 23:21</TD></TR></TBODY></TABLE></TD>
<TD>&nbsp;</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD>
<TD style="PADDING-BOTTOM: 1mm; WIDTH: 30mm">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD style="HEIGHT: 28mm"><IMG style="HEIGHT: 28mm; WIDTH: 28mm" align=center > </TD></TR>
<TR>
<TD class=fontStyle style="VERTICAL-ALIGN: bottom; TEXT-ALIGN: center">零件批次号</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 12pt; LINE-HEIGHT: 1.3em">&nbsp; </TD></TR>
<TR>
<TD class=fontStyle style="VERTICAL-ALIGN: bottom; TEXT-ALIGN: center">追溯管理方式</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 12pt; LINE-HEIGHT: 1.3em">&nbsp; </TD></TR></TBODY></TABLE></TD>
<TD style="PADDING-LEFT: 1mm; WIDTH: 40%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" border=0>
<TBODY>
<TR>
<TD style="PADDING-LEFT: 1mm">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
<TBODY>
<TR>
<TD class=fontStyle colSpan=2>到货仓库</TD>
<TD class=fontStyle colSpan=2>卸货口</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 16pt; WORD-BREAK: break-all; FONT-WEIGHT: bold; TEXT-ALIGN: center; LINE-HEIGHT: 1.2em; WIDTH: 50%">FW01A</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 14pt; FONT-WEIGHT: bold; TEXT-ALIGN: center; LINE-HEIGHT: 1.2em; WIDTH: 50%">&nbsp;</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
<TR>
<TD style="PADDING-TOP: 2mm; PADDING-LEFT: 1mm" height="33%">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
<TBODY>
<TR>
<TD class=fontStyle colSpan=2>分拣区代码</TD>
<TD class=fontStyle colSpan=2>库区代码</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 16pt; WORD-BREAK: break-all; FONT-WEIGHT: bold; TEXT-ALIGN: center; LINE-HEIGHT: 1.2em; WIDTH: 50%">&nbsp;</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 14pt; FONT-WEIGHT: bold; TEXT-ALIGN: center; WIDTH: 50%">二线焊装</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
<TR>
<TD style="PADDING-TOP: 1mm; PADDING-LEFT: 1mm" height="34%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD class=fontStyle style="WIDTH: 50%">配送落点</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=fontStyle style="WIDTH: 50%">机能区</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=borderLeftTop style="PADDING-BOTTOM: 1mm; PADDING-TOP: 1mm; PADDING-LEFT: 1mm; WIDTH: 25mm" rowSpan=3><IMG style="HEIGHT: 23mm; WIDTH: 23mm" align=center > </TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 8pt; TEXT-ALIGN: center; LINE-HEIGHT: 1em">&nbsp;RBS06&nbsp;&nbsp;</TD>
<TD>&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 8pt; TEXT-ALIGN: center; LINE-HEIGHT: 2em">&nbsp;&nbsp;&nbsp;</TD>
<TD>&nbsp;</TD>
<TR>
<TD style="PADDING-TOP: 1mm" colSpan=3>
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD style="WIDTH: 0mm">&nbsp;</TD>
<TD class=borderLeftTop style="FONT-SIZE: 7pt; TEXT-ALIGN: right">打印时间&nbsp;2018-08-04 16:26:48</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></DIV>
<DIV class=divWaterMark style="FONT-SIZE: 105pt; LEFT: 0mm; TOP: 0mm">&nbsp;&nbsp;G2</DIV>
<DIV class=divWaterMark style="FONT-SIZE: 90pt; LEFT: 95mm; TOP: 0mm">&nbsp;&nbsp;<SPAN style="FONT-SIZE: 70pt">A30</SPAN><SPAN style="FONT-SIZE: 60pt">Ⅰ</SPAN></DIV>
<DIV class=divData style="LEFT: 0mm; TOP: 94mm">
<TABLE class=table1 style="BORDER-TOP: black 2px solid; BORDER-RIGHT: black 2px solid; BORDER-BOTTOM: black 2px solid; BORDER-LEFT: black 2px solid; WIDTH: 100%" cellSpacing=0 cellPadding=0>
<TBODY>
<TR>
<TD style="HEIGHT: 25mm">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD style="BORDER-RIGHT: #000000 1px solid; VERTICAL-ALIGN: top; BORDER-BOTTOM: #000000 1px solid; PADDING-TOP: 1mm; PADDING-LEFT: 1mm; WIDTH: 25mm"><IMG style="HEIGHT: 23mm; WIDTH: 23mm" align=left > </TD>
<TD style="PADDING-LEFT: 1mm; WIDTH: 28%">
<TABLE class=tableStyle>
<TBODY>
<TR>
<TD class=fontStyle>零件名称</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 18px; FONT-WEIGHT: bold; TEXT-ALIGN: center">左B柱加强板总成</TD></TR>
<TR>
<TD class=borderStyle style="FONT-SIZE: 18px; FONT-WEIGHT: bold; TEXT-ALIGN: center">4112121CAD0000</TD></TR></TBODY></TABLE></TD>
<TD style="PADDING-LEFT: 1mm; WIDTH: 23%">
<TABLE class=tableStyle>
<TBODY>
<TR>
<TD class=fontStyle>零件简号</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 44px; FONT-WEIGHT: bold; TEXT-ALIGN: center">NW045</TD></TR></TBODY></TABLE></TD>
<TD style="PADDING-LEFT: 1mm; PADDING-RIGHT: 1mm; WIDTH: 23%">
<TABLE class=tableStyle cellSpacing=0 cellPadding=0>
<TBODY>
<TR>
<TD class=fontStyle>正规包装数</TD>
<TD class=fontStyle>本次包装数</TD></TR>
<TR>
<TD class=borderStyleOutRight style="FONT-SIZE: 40px; FONT-WEIGHT: bold; TEXT-ALIGN: center">20</TD>
<TD class="borderStyleOutRight borderStyleRight" style="FONT-SIZE: 40px; FONT-WEIGHT: bold; TEXT-ALIGN: center">20</TD></TR></TBODY></TABLE></TD>
<TD style="VERTICAL-ALIGN: top; BORDER-BOTTOM: #000000 1px solid; PADDING-BOTTOM: 1mm; PADDING-TOP: 1mm; BORDER-LEFT: #000000 1px solid; PADDING-RIGHT: 1mm; WIDTH: 25mm"><IMG style="HEIGHT: 23mm; WIDTH: 23mm" align=right > </TD></TR></TBODY></TABLE></TD></TR>
<TR>
<TD style="PADDING-TOP: 1mm; WIDTH: 100%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD style="WIDTH: 40%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" border=0>
<TBODY>
<TR>
<TD style="PADDING-LEFT: 1mm">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
<TBODY>
<TR>
<TD class=fontStyle width="100%" colSpan=4>供应商</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 16pt; HEIGHT: 100%; WORD-BREAK: break-all; FONT-WEIGHT: bold; TEXT-ALIGN: center; WIDTH: 70%">海斯坦普汽车组件（东莞）有限公司</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 18pt; FONT-WEIGHT: bold; TEXT-ALIGN: center; WIDTH: 30%">VG420</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
<TR>
<TD style="PADDING-TOP: 1mm; PADDING-LEFT: 1mm" height="100%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD class=fontStyle>出货仓库</TD>
<TD>&nbsp;</TD>
<TD class=fontStyle>出货库区</TD>
<TD>&nbsp;</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 16pt; FONT-WEIGHT: bold; TEXT-ALIGN: center; LINE-HEIGHT: 1.2em; WIDTH: 45%">VG420</TD>
<TD style="WIDTH: 3mm">&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 12pt; FONT-WEIGHT: bold; TEXT-ALIGN: center; LINE-HEIGHT: 1em; WIDTH: 55%">&nbsp;</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
<TR>
<TD style="PADDING-TOP: 1mm" height="34%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD class=borderRihgtTop style="VERTICAL-ALIGN: top; PADDING-BOTTOM: 1mm; PADDING-TOP: 1mm; PADDING-LEFT: 1mm; PADDING-RIGHT: 1mm; WIDTH: 25mm"><IMG style="HEIGHT: 23mm; WIDTH: 23mm" align=left src=""> </TD>
<TD style="PADDING-BOTTOM: 1mm; PADDING-LEFT: 1mm; WIDTH: 90%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD class=fontStyle style="WIDTH: 50%">物流订单号</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=fontStyle style="WIDTH: 50%">ERP订单号</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 12pt; TEXT-ALIGN: center; line-heirht: 1em">&nbsp;G2-AA18080600270&nbsp;</TD>
<TD>&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 12pt; TEXT-ALIGN: center; line-heirht: 2em">&nbsp;KA0536284&nbsp;</TD>
<TD>&nbsp;</TD></TR>
<TR>
<TD style="PADDING-TOP: 1mm" colSpan=3>
<TABLE style="WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD class=borderStyleOutRight style="FONT-SIZE: 8pt">&nbsp;PAGE&nbsp;2/10</TD>
<TD class="borderStyleOutRight borderStyleRight" style="FONT-SIZE: 8pt">&nbsp;到达&nbsp;2018-08-06 23:21</TD></TR></TBODY></TABLE></TD>
<TD>&nbsp;</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD>
<TD style="PADDING-BOTTOM: 1mm; WIDTH: 30mm">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD style="HEIGHT: 28mm"><IMG style="HEIGHT: 28mm; WIDTH: 28mm" align=center > </TD></TR>
<TR>
<TD class=fontStyle style="VERTICAL-ALIGN: bottom; TEXT-ALIGN: center">零件批次号</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 12pt; LINE-HEIGHT: 1.3em">&nbsp; </TD></TR>
<TR>
<TD class=fontStyle style="VERTICAL-ALIGN: bottom; TEXT-ALIGN: center">追溯管理方式</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 12pt; LINE-HEIGHT: 1.3em">&nbsp; </TD></TR></TBODY></TABLE></TD>
<TD style="PADDING-LEFT: 1mm; WIDTH: 40%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" border=0>
<TBODY>
<TR>
<TD style="PADDING-LEFT: 1mm">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
<TBODY>
<TR>
<TD class=fontStyle colSpan=2>到货仓库</TD>
<TD class=fontStyle colSpan=2>卸货口</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 16pt; WORD-BREAK: break-all; FONT-WEIGHT: bold; TEXT-ALIGN: center; LINE-HEIGHT: 1.2em; WIDTH: 50%">FW01A</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 14pt; FONT-WEIGHT: bold; TEXT-ALIGN: center; LINE-HEIGHT: 1.2em; WIDTH: 50%">&nbsp;</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
<TR>
<TD style="PADDING-TOP: 2mm; PADDING-LEFT: 1mm" height="33%">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
<TBODY>
<TR>
<TD class=fontStyle colSpan=2>分拣区代码</TD>
<TD class=fontStyle colSpan=2>库区代码</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 16pt; WORD-BREAK: break-all; FONT-WEIGHT: bold; TEXT-ALIGN: center; LINE-HEIGHT: 1.2em; WIDTH: 50%">&nbsp;</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 14pt; FONT-WEIGHT: bold; TEXT-ALIGN: center; WIDTH: 50%">二线焊装</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
<TR>
<TD style="PADDING-TOP: 1mm; PADDING-LEFT: 1mm" height="34%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD class=fontStyle style="WIDTH: 50%">配送落点</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=fontStyle style="WIDTH: 50%">机能区</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=borderLeftTop style="PADDING-BOTTOM: 1mm; PADDING-TOP: 1mm; PADDING-LEFT: 1mm; WIDTH: 25mm" rowSpan=3><IMG style="HEIGHT: 23mm; WIDTH: 23mm" align=center > </TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 8pt; TEXT-ALIGN: center; LINE-HEIGHT: 1em">&nbsp;LBS06&nbsp;&nbsp;</TD>
<TD>&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 8pt; TEXT-ALIGN: center; LINE-HEIGHT: 2em">&nbsp;&nbsp;&nbsp;</TD>
<TD>&nbsp;</TD>
<TR>
<TD style="PADDING-TOP: 1mm" colSpan=3>
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD style="WIDTH: 0mm">&nbsp;</TD>
<TD class=borderLeftTop style="FONT-SIZE: 7pt; TEXT-ALIGN: right">打印时间&nbsp;2018-08-04 16:26:48</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></DIV>
<DIV class=divWaterMark style="FONT-SIZE: 105pt; LEFT: 0mm; TOP: 94mm">&nbsp;&nbsp;G2</DIV>
<DIV class=divWaterMark style="FONT-SIZE: 90pt; LEFT: 95mm; TOP: 94mm">&nbsp;&nbsp;<SPAN style="FONT-SIZE: 70pt">A30</SPAN><SPAN style="FONT-SIZE: 60pt">Ⅰ</SPAN></DIV>
<DIV class=divData style="LEFT: 0mm; TOP: 188mm">
<TABLE class=table1 style="BORDER-TOP: black 2px solid; BORDER-RIGHT: black 2px solid; BORDER-BOTTOM: black 2px solid; BORDER-LEFT: black 2px solid; WIDTH: 100%" cellSpacing=0 cellPadding=0>
<TBODY>
<TR>
<TD style="HEIGHT: 25mm">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD style="BORDER-RIGHT: #000000 1px solid; VERTICAL-ALIGN: top; BORDER-BOTTOM: #000000 1px solid; PADDING-TOP: 1mm; PADDING-LEFT: 1mm; WIDTH: 25mm"><IMG style="HEIGHT: 23mm; WIDTH: 23mm" align=left > </TD>
<TD style="PADDING-LEFT: 1mm; WIDTH: 28%">
<TABLE class=tableStyle>
<TBODY>
<TR>
<TD class=fontStyle>零件名称</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 18px; FONT-WEIGHT: bold; TEXT-ALIGN: center">左B柱加强板总成</TD></TR>
<TR>
<TD class=borderStyle style="FONT-SIZE: 18px; FONT-WEIGHT: bold; TEXT-ALIGN: center">4112121CAD0000</TD></TR></TBODY></TABLE></TD>
<TD style="PADDING-LEFT: 1mm; WIDTH: 23%">
<TABLE class=tableStyle>
<TBODY>
<TR>
<TD class=fontStyle>零件简号</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 44px; FONT-WEIGHT: bold; TEXT-ALIGN: center">NW045</TD></TR></TBODY></TABLE></TD>
<TD style="PADDING-LEFT: 1mm; PADDING-RIGHT: 1mm; WIDTH: 23%">
<TABLE class=tableStyle cellSpacing=0 cellPadding=0>
<TBODY>
<TR>
<TD class=fontStyle>正规包装数</TD>
<TD class=fontStyle>本次包装数</TD></TR>
<TR>
<TD class=borderStyleOutRight style="FONT-SIZE: 40px; FONT-WEIGHT: bold; TEXT-ALIGN: center">20</TD>
<TD class="borderStyleOutRight borderStyleRight" style="FONT-SIZE: 40px; FONT-WEIGHT: bold; TEXT-ALIGN: center">20</TD></TR></TBODY></TABLE></TD>
<TD style="VERTICAL-ALIGN: top; BORDER-BOTTOM: #000000 1px solid; PADDING-BOTTOM: 1mm; PADDING-TOP: 1mm; BORDER-LEFT: #000000 1px solid; PADDING-RIGHT: 1mm; WIDTH: 25mm"><IMG style="HEIGHT: 23mm; WIDTH: 23mm" align=right > </TD></TR></TBODY></TABLE></TD></TR>
<TR>
<TD style="PADDING-TOP: 1mm; WIDTH: 100%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD style="WIDTH: 40%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" border=0>
<TBODY>
<TR>
<TD style="PADDING-LEFT: 1mm">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
<TBODY>
<TR>
<TD class=fontStyle width="100%" colSpan=4>供应商</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 16pt; HEIGHT: 100%; WORD-BREAK: break-all; FONT-WEIGHT: bold; TEXT-ALIGN: center; WIDTH: 70%">海斯坦普汽车组件（东莞）有限公司</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 18pt; FONT-WEIGHT: bold; TEXT-ALIGN: center; WIDTH: 30%">VG420</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
<TR>
<TD style="PADDING-TOP: 1mm; PADDING-LEFT: 1mm" height="100%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD class=fontStyle>出货仓库</TD>
<TD>&nbsp;</TD>
<TD class=fontStyle>出货库区</TD>
<TD>&nbsp;</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 16pt; FONT-WEIGHT: bold; TEXT-ALIGN: center; LINE-HEIGHT: 1.2em; WIDTH: 45%">VG420</TD>
<TD style="WIDTH: 3mm">&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 12pt; FONT-WEIGHT: bold; TEXT-ALIGN: center; LINE-HEIGHT: 1em; WIDTH: 55%">&nbsp;</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
<TR>
<TD style="PADDING-TOP: 1mm" height="34%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD class=borderRihgtTop style="VERTICAL-ALIGN: top; PADDING-BOTTOM: 1mm; PADDING-TOP: 1mm; PADDING-LEFT: 1mm; PADDING-RIGHT: 1mm; WIDTH: 25mm"><IMG style="HEIGHT: 23mm; WIDTH: 23mm" align=left > </TD>
<TD style="PADDING-BOTTOM: 1mm; PADDING-LEFT: 1mm; WIDTH: 90%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD class=fontStyle style="WIDTH: 50%">物流订单号</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=fontStyle style="WIDTH: 50%">ERP订单号</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 12pt; TEXT-ALIGN: center; line-heirht: 1em">&nbsp;G2-AA18080600270&nbsp;</TD>
<TD>&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 12pt; TEXT-ALIGN: center; line-heirht: 2em">&nbsp;KA0536284&nbsp;</TD>
<TD>&nbsp;</TD></TR>
<TR>
<TD style="PADDING-TOP: 1mm" colSpan=3>
<TABLE style="WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD class=borderStyleOutRight style="FONT-SIZE: 8pt">&nbsp;PAGE&nbsp;3/10</TD>
<TD class="borderStyleOutRight borderStyleRight" style="FONT-SIZE: 8pt">&nbsp;到达&nbsp;2018-08-06 23:21</TD></TR></TBODY></TABLE></TD>
<TD>&nbsp;</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD>
<TD style="PADDING-BOTTOM: 1mm; WIDTH: 30mm">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD style="HEIGHT: 28mm"><IMG style="HEIGHT: 28mm; WIDTH: 28mm" align=center > </TD></TR>
<TR>
<TD class=fontStyle style="VERTICAL-ALIGN: bottom; TEXT-ALIGN: center">零件批次号</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 12pt; LINE-HEIGHT: 1.3em">&nbsp; </TD></TR>
<TR>
<TD class=fontStyle style="VERTICAL-ALIGN: bottom; TEXT-ALIGN: center">追溯管理方式</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 12pt; LINE-HEIGHT: 1.3em">&nbsp; </TD></TR></TBODY></TABLE></TD>
<TD style="PADDING-LEFT: 1mm; WIDTH: 40%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" border=0>
<TBODY>
<TR>
<TD style="PADDING-LEFT: 1mm">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
<TBODY>
<TR>
<TD class=fontStyle colSpan=2>到货仓库</TD>
<TD class=fontStyle colSpan=2>卸货口</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 16pt; WORD-BREAK: break-all; FONT-WEIGHT: bold; TEXT-ALIGN: center; LINE-HEIGHT: 1.2em; WIDTH: 50%">FW01A</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 14pt; FONT-WEIGHT: bold; TEXT-ALIGN: center; LINE-HEIGHT: 1.2em; WIDTH: 50%">&nbsp;</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
<TR>
<TD style="PADDING-TOP: 2mm; PADDING-LEFT: 1mm" height="33%">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
<TBODY>
<TR>
<TD class=fontStyle colSpan=2>分拣区代码</TD>
<TD class=fontStyle colSpan=2>库区代码</TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 16pt; WORD-BREAK: break-all; FONT-WEIGHT: bold; TEXT-ALIGN: center; LINE-HEIGHT: 1.2em; WIDTH: 50%">&nbsp;</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 14pt; FONT-WEIGHT: bold; TEXT-ALIGN: center; WIDTH: 50%">二线焊装</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
<TR>
<TD style="PADDING-TOP: 1mm; PADDING-LEFT: 1mm" height="34%">
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD class=fontStyle style="WIDTH: 50%">配送落点</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=fontStyle style="WIDTH: 50%">机能区</TD>
<TD style="WIDTH: 1mm">&nbsp;</TD>
<TD class=borderLeftTop style="PADDING-BOTTOM: 1mm; PADDING-TOP: 1mm; PADDING-LEFT: 1mm; WIDTH: 25mm" rowSpan=3><IMG style="HEIGHT: 23mm; WIDTH: 23mm" align=center > </TD></TR>
<TR>
<TD class=allBorderStyle style="FONT-SIZE: 8pt; TEXT-ALIGN: center; LINE-HEIGHT: 1em">&nbsp;LBS06&nbsp;&nbsp;</TD>
<TD>&nbsp;</TD>
<TD class=allBorderStyle style="FONT-SIZE: 8pt; TEXT-ALIGN: center; LINE-HEIGHT: 2em">&nbsp;&nbsp;&nbsp;</TD>
<TD>&nbsp;</TD>
<TR>
<TD style="PADDING-TOP: 1mm" colSpan=3>
<TABLE style="HEIGHT: 100%; WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<TBODY>
<TR>
<TD style="WIDTH: 0mm">&nbsp;</TD>
<TD class=borderLeftTop style="FONT-SIZE: 7pt; TEXT-ALIGN: right">打印时间&nbsp;2018-08-04 16:26:48</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></DIV>
<DIV class=divWaterMark style="FONT-SIZE: 105pt; LEFT: 0mm; TOP: 188mm">&nbsp;&nbsp;G2</DIV>
<DIV class=divWaterMark style="FONT-SIZE: 90pt; LEFT: 95mm; TOP: 188mm">&nbsp;&nbsp;<SPAN style="FONT-SIZE: 70pt">A30</SPAN><SPAN style="FONT-SIZE: 60pt">Ⅰ</SPAN></DIV></DIV>
	</div><!-- printarea -->
	
<iframe id="downloadiframe" style='display: none'></iframe>	
</body>
</html>
<script>
	var noSwtich=${cookie.origSwitch==null  };
	var isAdmin = false;
	$(function() {
		try{
			var data = Object.toAjaxJson(__ctx + "/org/user/","getCurrentUser");
			var currentUser = JSON.parse(data.message);
			isAdmin = currentUser.admin;
		}catch(err){}
		loadGrid();
	});
	
		
	function openDetail(id, action) {
		var title = action == "edit" ? "编辑用户" : action == "add" ? "添加用户" : "查看用户";
		action =action == "edit" ? "Edit" : action == "add" ? "Edit" : "Get";
		var url="user" + action+"?orgId="+orgId;
		if(!$.isEmpty(id)){
			url+='&id=' + id;
		}
		HT.window.openEdit(url, title, action, 'grid', 400, 300, null, null, id, true);
	}
	
	
	function loadGrid() {
		$('#grid').datagrid($.extend($defaultOptions,{
			url :  __ctx + "/org/user/listJson?orgId="+orgId,
			idField : "id",
			sortName : 'id_',
			sortOrder : 'desc',
			columns : [ [
			{field : 'id',sortName : "id_",checkbox : true},     
			{field : 'fullname',sortName : "fullname_",title : '姓名',width : 130,align : 'center',sortable : 'true'}, 
			{field : 'account',sortName : "account_",title : '账号',width : 130,align : 'center',sortable : 'true'},
			{field : 'email',sortName : "email_",title : '邮箱',width : 130,align : 'center',sortable : 'true'},
			{field : 'mobile',sortName : "mobile_",title : '手机号码',width : 130,align : 'center',sortable : 'true'}, 
			{field : 'weixin',sortName : "weixin_",title : '微信号',width : 130,align : 'center'}, 
			{field : 'sex',sortName : "sex_",title : '性别',width :80,align : 'center',sortable : 'true'}, 
			{field : 'status',sortName : "status_",title :'状态',width :80,align :'center',sortable: 'true',
				formatter:function(value,row,index){ return value==0?"禁用":"正常";}
			}, 
			{field : 'createTime',sortName : "create_time_",title : '创建时间',width : 150,align : 'center',sortable : 'true',formatter:dateTimeFormatter}, 
			{
				field : 'colManage',
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var result = "<a class='btn btn-default fa fa-edit' onClick='openDetail(\""+ row.id +"\",\"edit\");' herf='javascript:void(0)'>编辑</a>";
					result += "<a class='btn btn-default fa fa-detail' onClick='openDetail(\"" + row.id + "\",\"get\");' herf='javascript:void(0)'>明细</a>";
					
					if(row.account!="admin"){
						result += "<a class='rowInLine btn btn-default fa fa-remove' action='/org/user/remove?id=" + row.id + "' herf='javascript:void(0)'>删除</a>";
					}
					
					if(noSwtich && isAdmin && row.id!=window.parent.currentUserId){
						result += "<a class='btn btn-default fa fa-exchange'  href='"+__ctx+"/j_spring_security_switch_user?username="+row.account+"'>切换用户</a>";
					}
					
					return result;
				}
			} ] ]
		}));
	}
	
	function importUser(){
		
	}
	
	function pdfPrintTest(){
		var page = prompt("请输入打印页数","5");
		
		if (page!=null && page!=""){
			var downurl =  __ctx+"/demo/demo/genPdfTest?page=" + page;
			//$('#downloadiframe').attr('src', downurl);
			//HT.window.openEdit(downurl, '查看', null, null, 400, 300, null, null, 5, true);
			//$.openFullWindow(downurl);
			var def = {
		      title : "打印",
		      width : 800,
		      height : 500,
		      modal : true,
		      resizable : true,
		      buttons : []
		    };  

		    dialog = $.topCall.dialog({
		      src : downurl,
		      base : def
		    });
		}else{
			alert('输入为空！！！');
		}
	}
	
	function printTest(){
		var page = prompt("请输入打印页数","5");
		
		if (page!=null && page!=""){
			
	        var strHTML="<table border='0' width='100%' >";
	        var tmphtml = document.getElementById("printlabelhtml").innerHTML;
			for(var i = 0; i < page; i++){
				strHTML += "<tr><td>";
				strHTML += tmphtml;
				strHTML += "</td></tr>";
			}
			strHTML += "</table>";
			alert('开始初始化打印。。。');
			LODOP=getLodop();  	
			LODOP.PRINT_INIT("printtest");
			LODOP.ADD_PRINT_TABLE(10,10,"100%","100%",strHTML);
			LODOP.PREVIEW();
		}else{
			alert('输入为空！！！');
		}
	}
</script>
