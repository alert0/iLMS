<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html ng-app="app">
	<head>
		<%@include file="/commons/include/ngEdit.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/system/identity/identityController.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/system/identity/identityService.js"></script> 
		<script type="text/javascript" src="${ctx}/js/common/util/chineseToPinyin.js"></script>
		<script type="text/javascript">
			var id="${param.id}";
		</script>
	</head>
	<body  ng-controller="IdentityController">
			<!-- 顶部按钮 -->
			<div class="toolbar-panel">
				<div class="buttons">	      
					<a class="btn btn-primary fa-save btn-sm" href="javascript:void(0);" ng-click="save()"><span>保存</span></a>
					<a href="javascript:;" ng-click="close();" class="btn btn-primary btn-sm fa-back" ><span>关闭</span></a>
				</div>
			</div>
			<form  name="identityForm" action="save" method="post">
				<table class="table-form"  cellspacing="0" >
						<tr>
				    		<th>流水号名称:<span class="required">*</span></th>
				    		<td >
				    			<input name="id" type="hidden" ng-model="identity.id"/>
					    		<input name="name"  ng-model="identity.name" type="text" class="inputText" ht-validate="{required:true}"  />
				    		</td>
		    			</tr>
		    			<tr>
				    		<th >流水号别名:<span class="required">*</span></th>
				    		<td >
					    		<input name="alias"  id="alias" ng-model="identity.alias" type="text" class="inputText" ht-pinyin="identity.name" ht-validate="{'required':true,'varirule':true}"  placeholder="请输入流水号别名" />
				    			
				    		</td>
		    			</tr>
		    			<tr>
				    		<th >流水号规则:<span class="required">*</span></th>
				    		<td >
					    		<input name="regulation"  ng-model="identity.regulation" type="text" class="inputText" ht-validate="{'required':true}"/>
				    			<ul><li >{yyyy}{MM}{dd}{NO}</li>
				        	 			<li >{yyyy}:表示年份</li>
				        	 			<li >{MM}  :表示月份，如果月份小于10，则加零补齐，如1月份表示为01。</li>
				        	 			<li >{mm}  :表示月份，月份不补齐，如1月份表示为1。</li>
				        	 			<li >{DD}  :表示日，如果小于10号，则加零补齐，如1号表示为01。</li>
				        	 			<li >{dd}  :表示日，日期不补齐，如1号表示为1。</li>
				        	 			<li >{NO}  :表示流水号，前面补零。</li>
				        	 			<li >{no}  :表示流水号，后面补零。</li>
				        	 		</ul>
				    		</td>
		    			</tr>
		    			<tr>
				    		<th >生成类型:<span class="required">*</span></th>
				    		<td >
								<label class="radio-inline" for="genType_everyDay"><input type="radio" id="genType_everyDay" name="genType" ng-model="identity.genType" value="1">每天生成</label>
								
								<label class="radio-inline" for="genType_increase"><input type="radio" id="genType_increase" name="genType" ng-model="identity.genType" value="0">递增</label>
								<br>
									流水号生成规则：
									<ul>
										<li >1.每天生成。每天从初始值开始计数。</li>
										<li >2.递增，流水号一直增加。</li>
									</ul>
				    		</td>
		    			</tr>
		    			<tr>
				    		<th >流水号长度:<span class="required">*</span></th>
				    		<td >
					    		<input name="noLength"  ng-model="identity.noLength" type="text" class="inputText" ht-validate='{"required":true,"number":true}'/>
				    			<br>
									<li >这个长度表示当前流水号的长度数，只包括流水号部分{NO},如果长度为5，当前流水号为5，则在流水号前补4个0，表示为00005。</li>
									<li >{no}如果长度为5，当前流水号为501，则在流水号后面补5个0，表示为50100000，如果长度为1，则流水号一直递增。</li>
				    		</td>
				    		
		    			</tr>
		    			<tr>
				    		<th >初始值:<span class="required">*</span></th>
				    		<td >
					    		<input name="initValue"  ng-model="identity.initValue" type="text" class="inputText" ht-validate='{"required":true,"number":true}'/>
				    		<br>
									<li >这个初始值表示流水号部分{NO}的初始值。如 2015102700001,初始值为1，则流水号部分的初始值为00001</li>
				    		</td>
		    			</tr>
		    			<tr>
				    		<th >步长:<span class="required">*</span></th>
				    		<td >
					    		<input name="step"  ng-model="identity.step" type="text" class="inputText" ht-validate='{"required":true,"number":true}'/>
				    		<br>
									<li >流水号每次递加的数字，默认步长为1。比如步长为2，则每次获取流水号则在原来的基础上加2。</li>
				    		</td>
		    			</tr>
				</table>
			</form>
		</div>
	</body>
</html>