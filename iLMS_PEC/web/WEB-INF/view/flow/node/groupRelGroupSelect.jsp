<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="baseServices">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/system/dialog/flowVarDialog.js"></script>
		<script type="text/javascript">
		var row;
		$(function() {
			//回显数据
			row = window.passConf;
	  	    if(row){
	  	     var source =row["source"];
	  	 	 if(source){   // 如果回显数据的话
	  	 		 $("#groupType").val(row['groupType']);
	  			var theRadio = $('input:radio[value='+source+']');
	  			$(theRadio[0]).attr("checked","checked"); 
	  			if(source == 'spec'){
	  				$('input[name="groupKey"]').val(row["groupKey"]);
	  				$('textarea[name="groupName"]').val(row["groupName"]);
	  			}else if(source == 'var'){
	  				flowVar = row["var"];	 
					flowVarText = flowVar.source+"["+flowVar.executorType+":"+flowVar.name+"]";
					$("#varSpan").text(flowVarText); 
	  			}
	  			changeGroupType(true);
	  	 	 }
	  			changeRadio();
	  	    }
			}); 
		function getRowData(){
			var source = $('input[name=source]:checked').val();
			if(!source) {$.topCall.warn("请选择来源"); return false;}
			// 对当前信息进行描述。
			var sourceText = $('input[name=source]:checked').next().text();
			row["source"] = source;
			var groupType =$("#groupType").val();
			row["groupType"]=groupType;
			if(!groupType) {$.topCall.warn("请选择组类型"); return false;}
			row["relationTypeName"] =$('select[name="relationKey"]').children('option:selected').text();
			if(source == 'spec'){
				row["groupKey"] = $('input[name="groupKey"]').val();
				row["groupName"] = $('textarea[name="groupName"]').val();
				sourceText = sourceText+"["+row["groupName"]+"]";
			}else if(source == 'var'){
				if(!flowVar) {$.topCall.warn("请选择变量"); return false;}
				row["var"] = flowVar;
				sourceText = sourceText+flowVarText;
			}
			row["relationKey"] =$('select[name="relationKey"]').val();
			sourceText=sourceText+",关系类型:"+$('select[name="relationKey"]').children('option:selected').text();
			row["relationParty"] = $('select[name="relationParty"]').val();
			sourceText=sourceText+",关系方:"+$('select[name="relationParty"]').children('option:selected').text();
			row["description"]=sourceText; 
			return row;
		}
		
		// 改变组类型的时候改变关系
		function changeGroupType(initData){
			var dimKey = $("#groupType").val();
			if(!dimKey) { $.topCall.warn("请选择组类型"); return;}
		
			$.post(__ctx+'/flow/node/getGroupRelationTypeByDimKey',{dimKey:dimKey},function(data){
				var relationKey =  $("#relationKey_");
				relationKey.empty();
				relationKey.append($("<option>").val("").text("请选择"));
				$.each(data, function(i,item){
					var aaa = $("<option>").val(item.key).text(item.name).attr("curName",item.currentName).attr("relName",item.relName);
					relationKey.append(aaa);
				});
				if(initData) {
					$("#relationKey_").val(row["relationKey"]);
					changeUserRelation(); 
					$("#relationParty_").val(row["relationParty"]);
				}
			});
		}
		
		function changeRadio(){
			var source = $('input[name=source]:checked').val();
			$("#var").hide(); 
			$("#spec").hide();
			var selectOne = $("#"+source);
			if(selectOne){
				selectOne.show();
			}
		}
		// 掉用人员选择框。选择人员。回显人员。
		function choiceUser(obj){
			 // 拼装回显数据
			var groupKeys =  $(obj).prev().val(); 
			var groups = [];
			if (groupKeys){
				groupKeys = groupKeys.split(',');
				var groupNames = $('textarea[name="groupName"]').val().split(',');
				for(var i = 0; i<groupKeys.length;  i++){
					groups[i] = {key:groupKeys[i],name:groupNames[i]};
				}}
			var passConf = {groupJson:groups}
			
			var dimKey = $("#groupType").val();
			if(!dimKey) { $.topCall.warn("请选择组类型"); return; }
			new GroupSelectDialog({passConf:passConf,type:'5',dimKey:dimKey,callback:function(groupIds,groupNames,groups){
				var groupKeys = [];
				$(groups).each(function(i){
					groupKeys.push(this['key']);
				});
				 $(obj).prev().val(groupKeys); 
				 $(obj).prev().prev().val(groupNames);
			}}).show();
		}
		function changeUserRelation(){
			var option=$("#relationKey_").children('option:selected');
			if(option.val()){
				var relName =option.attr("relName");
				var curName =option.attr("curName");
			}else{
				relName ="对方";curName = "当前方";
			}
			$("#relationParty_").children()[0].text=relName;
			$("#relationParty_").children()[1].text=curName;
		}
		var flowVar;
		var flowVarText;
		function choiceFlowVar(){
			var passConf ={defId:"${defId}",nodeId:"${nodeId}",type:"both"};
			new FlowVarDialog({passConf:passConf,type:'group',callback:function(varData){
				flowVar = varData;	 
				flowVarText = flowVar.source+"["+flowVar.executorType+":"+flowVar.name+"]";
				$("#varSpan").text(flowVarText);
			}}).show();
		}
		
		</script>
	</head>
	<body>
		<form id="userRelForm">
			<table style=" text-align: left !important ;" class="table-form">
			<tr>
				<th><span>组类型</span></th>
				<td>
					<select name='groupType' id="groupType" onchange="changeGroupType()">
						<option value="">请选择</option>
						<c:forEach items="${dimensionList}" var="dimension">
						<option value="${dimension.key}">${dimension.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span>组来源</span>
				</th>
				<td>
				<input type="radio" name="source" value="start" id="source_start" onclick="changeRadio()">
				<label class="normal_label" for="source_start">发起人所在的组</label>　
				<input type="radio" name="source" value="prev" id="source_prev" onclick="changeRadio()">
				<label class="normal_label" for="source_prev">上一步执行人所在的组</label>　 <br>
				<input type="radio" name="source" value="var" id="source_var" onclick="changeRadio()">
				<label class="normal_label" for="source_var">变量</label>　
				<input type="radio" name="source" value="spec" id="source_spec" onclick="changeRadio()" checked="checked">
				<label class="normal_label" for="source_spec">指定组</label>　
				</td>
			</tr>
			<tr id="var" >
				<th> <label>变量选择</label> </th>
				<td>
					<a onclick="choiceFlowVar()" class="btn btn-primary btn-xs">选择变量</a>
					<a href="javascript:;" style="text-decoration: none;" title="如果选择人员变量，则为人员变量所在的组，<br>如果选择组变量，则为该组"
					   class="fa fa-exclamation-circle" ht-tip data-options="position:'right'">　</a> 
					<span id="varSpan"> </span>
				</td>
			</tr>
			<tr id="spec" >
				<th>
					<label>指定组</label>
				</th>
				<td>
				<textarea name="groupName" style="height:37px"></textarea>
				<input type="hidden" name="groupKey">
				<a class="btn btn-primary btn-xs" onclick="choiceUser(this)"> 选择组</a>
				</td>
			</tr>
			<tr>
				<th>
					<span>关系类型</span>
				</th>
				<td>
				<select id="relationKey_" name="relationKey" onchange="changeUserRelation()">
				</select>
				</td>
			</tr>
			<tr id ="supportRelationTr">
				<th>
					<span>关系类型方</span>
				</th>
				<td>
				<select name="relationParty" id="relationParty_">
							<option value="cur">对方</option>
							<option value="rel">当前方</option>
				</select>
				<a href="javascript:;" style="text-decoration: none;" title=" 
						查询出符合组类型的组，根据这些组的关系进行查找组。<br>
						如果双方同一组类型，关系方，当前方存在意义，<br>
						如果双方不同维度而且非双向关系，请选择第一项<br>
						如果关系是双项的，请根据需要进行选择"
				   class="fa fa-exclamation-circle" ht-tip data-options="position:'top'">　</a> 
				</td>
			</tr>
			</table>
		</form>
	</body>
</html>