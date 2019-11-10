<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@ taglib prefix="ht" tagdir="/WEB-INF/tags/wf"%>
<html>
<head>
	<title>参数设置</title>
	<%@include file="/commons/include/ngEdit.jsp"%>
	<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery.fix.clone.js"></script>
	<script type="text/javascript">
		/*KILLDIALOG*/
		var dataParam = [];
		if(window.passConf){
			dataParam = window.passConf.dataParam;
		}
		$(function(){
			initDataParam(dataParam);
			$("a.fa-save").click(function(){
				var json = [];
                if(emptyValid()){
                    $.topCall.error("不允许参数名为空，请检查！","提示信息");
                    return;
                }
				if(nameValid()){
					$.topCall.error("重复的参数值，请检查！","提示信息");
					return;
				}

				$("#trContainer tr[var='paramTr']").each(function(){
					var me = $(this),obj={};
					obj.name =$("[var='name']",me).val();
					obj.type =$("[var='type']",me).val();
					obj.mode =$("[var='mode']",me).val();
					obj.value =$("[var='value']",me).val();
					json.push(obj);
				});
                $.topCall.success("保存成功","提示信息");
				window.passConf.callBack(JSON2.stringify(json));
				window.selfDialog.dialog('close');
			});
		});
		
		function nameValid(){
			var name =new Array();
			$("#trContainer").find("[var='name']").each(function() {
				name.push( $(this).val());
			});
			return isRepeat(name);		
		}
        function emptyValid(){
		    var inde=false;
            $("#trContainer").find("[var='name']").each(function() {
               	if($(this).val()==""||$(this).val()==undefined){
                    inde=true;
				}
            });
            return inde;
        }

		function isRepeat(arr) {
		    var hash = {};
		    for(var i in arr) {
		        if(hash[arr[i]]) {
		            return true;
		        }
		        // 不存在该元素，则赋值为true，可以赋任意值，相应的修改if判断条件即可
		        hash[arr[i]] = true;
		    }
		    return false;
		}
		
		function initDataParam (dataParam) {
			if ($.isEmpty(dataParam))
				return;
			var tr = $($("#paramTemplate .table-detail tr")[0]).clone(true, true);
			var params = $.parseJSON(dataParam);
			for (var i = 0, c; c = params[i++];) {
				$("input[var='name']", tr).val(c.name);
				$("select[var='mode']", tr).val(c.mode);
				$("textarea[var='value']", tr).val(c.value);
				var tr1 = tr.clone(true, true);
				$("#trContainer").append(tr1);
				$("select[var='type']", tr1).val(c.type);
			}
		}
		
		function addDataParam(){

			var tr = $($("#paramTemplate .table-detail tr")[0]).clone(true, true);
			$("#trContainer").append(tr);
		}
		
		function	moveTr (obj, isUp) {
			var thisTr = $(obj).parents("tr");
			if (isUp) {
				var prevTr = $(thisTr).prev();
				if (prevTr) {
					thisTr.insertBefore(prevTr);
				}
			} else {
				var nextTr = $(thisTr).next();
				if (nextTr) {
					thisTr.insertAfter(nextTr);
				}
			}
		}
		function delTr(obj) {
			$(obj).closest("tr").remove();
		}

	</script>
	</head>
<body class="easyui-layout">
    <div region="north" style="height: 45px">
        <div class="toolbar-panel col-md-13 ">
            <div class="buttons" style="margin-left: 10px;">
                <a href="javaScript:void(0)" class="btn btn-primary btn-sm fa-save">保存</a>
                <a href="javaScript:void(0)" onClick="addDataParam();" class="btn btn-primary btn-sm fa-add">添加</a>
                <a href="javaScript:void(0)" onClick="window.selfDialog.dialog('close');" class="btn btn-sm btn-default fa-close">关闭</a>
            </div>
        </div>
    </div>
    
     <div region="center" style="overflow: auto;">
     	
            <div class="panel-body">
                <table class="table-list">
                <tr>
                  <th align="center">参数名</th>
                  <th align="center">参数类型</th>
                  <th align="center">值来源</th>
                  <th align="center">参数值</th>
                  <th align="center">操作</th>
                </tr>
                <tbody id="trContainer">
                </tbody>
            </table>
            </div>
			<div  id="paramTemplate"  style="display: none;">
                <table cellpadding="1" cellspacing="1"  class="table-detail">
                    <tbody>
                    <tr var="paramTr">
                        <td><input type="text" var="name" value=""/> </td>
                        <td>
                            <select var="type">
                                <option value="string">String</option>
                                <option value="int">int</option>
                                <option value="float">float</option>
                                <option value="double">double</option>
                                <option value="byte">byte</option>
                                <option value="short">short</option>
                                <option value="long">long</option>
                                <option value="boolean">boolean</option>
                                <option value="date">date</option>
                            </select>
                        </td>
                        <td>
                            <select var="mode" >
                                <option value="0">固定值</option>
                                <option value="1">动态传入</option>
                                <option value="2">脚本</option>
                            </select>
                        </td>
                        <td>
                            <textarea rows="3" cols="20" var="value"></textarea>
                        </td>
                        <td>
                            <a class="btn btn-sm   btn-default fa-chevron-up" href="javascript:;" title="上移" onclick="moveTr(this,true)"></a>
                            <a class="btn btn-sm btn-default fa-chevron-down" href="javascript:;" title="下移"  onclick="moveTr(this,false)"></a>

                            <a class="btn btn-sm btn-default fa-times" href="javascript:;"  title="删除" onclick="delTr(this)"></a>
                        </td>
                    </tr>
                    </tbody>
                </table>
		   </div>
     	
     </div>


	
</body>
</html>