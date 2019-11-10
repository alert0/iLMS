/**
 * 下拉选项模版。
 */

$(function(){
 
	$("select[name='methodName']").change(methodChange);
 
});


//选择的方法事件
function methodChange(){
	var option = $(this).find("option:selected");

	if(!option)return;
	var	methodInfo = option.data('methodInfo');
	if(!methodInfo)return;
	$("input[name='returnType']").val(methodInfo.returnType);		
	var param = constructParamTable(methodInfo);
	$("#paraInfo").empty().append(param);
	if(!id){//新增时才触发
		$("select[name='paraCt']").trigger("change");
	}
};
//获取该类的方法
function getMethods(url){
	$.post(url,function(response){
		var data=eval("("+ response +")");
		if(!data.result){
			$.topCall.error(data.message,'出错了!');
			return;
		}
		var methods = data.methods,
			methodSelect = $("select[name='methodName']").empty(),
			methodName = $("#methodName").val();

		for(var i=0,c;c=methods[i++];){
			var newOpt = $('<option></option>').val(c.methodName).text(c.methodName);
			if(c.methodName == methodName){
				var paraData = $("textarea[name='argument']").val();
				if(paraData){
					c.para = eval("("+paraData+")");
				};
				$(newOpt).attr("selected",true);
			}
			$(newOpt).data('methodInfo',c);
			methodSelect.append(newOpt);
		}
		methodSelect.trigger("change");
	});
}

//根据表单ID保存并提交
function save(formId){
	//验证
	var frm=$('#'+formId);   
	if(!frm.valid()) return ;
	var json = getParamsJson();
	json = JSON2.stringify(json);
	if(json=='[]'){
		json='';
	}
	$("textarea[name='argument']").val(json);
	frm.submit();
};


/**
* 获取参数行的JSON字符口串
*/
function getParamsJson(){
	var aryJson = [];
	$("#paraInfo").find("tr").each(function(){
		var me = $(this);
		var paraNameSpan= $("span[name='paraName']",me);
		if(!paraNameSpan || paraNameSpan.length==0) return true;
		var paraTypeSpan = $("span[name='paraType']",me),
			paraDescInput = $("input[name='paraDesc']",me),
			paraCtlTypeSelect = $("[name='paraCt']",me),
			paraCtBindField = $("[name='paraCtBindField']",me),
			multiSelected=$("[name='multiSelect']",me);
		var json={};

		json.paraName = paraNameSpan.text();
		json.paraType = paraTypeSpan.text();
		json.paraDesc = paraDescInput.val();
		json.paraCt = paraCtlTypeSelect.val();
		json.paraCtBindName =$("[name='paraCtBindField'] option:selected",me).text();
		json.paraCtBindKey = paraCtBindField.val();
		json.multiSelect=multiSelected.is(':checked');
		aryJson.push(json);
	});
	
	return aryJson;
};



/**
* 创建参数表格
*/
function constructParamTable(params){
	var table = $("#para-txt table").clone();
	var tbody = $("tbody",table).empty();
	
	for(var i=0;i<params.para.length;i++){
		var p = params.para[i];
		var tr = constructParamTr(p);
		tbody.append(tr);
	}
	return table;
};

/**
* 创建参数表格行
*/
function constructParamTr(p){
	var tr = $("#para-txt table tbody tr").clone();
	$("[name='paraName']",tr).text(p.paraName);
	$("[name='paraType']",tr).text(p.paraType);
	$("[name='paraDesc']",tr).val(p.paraDesc);
	$("[name='paraCt']",tr).val(p.paraCt);
	var optionHtml=constructparaCtBindField(p.paraCt,p.paraCtBindKey);
	$("[name='paraCtBindField']",tr).html(optionHtml);
	
	var multiSelect=p.multiSelect?true:false;
	
	$("[name='multiSelect']",tr).attr("checked",multiSelect);
	
	return tr;		
};

function paraCtChange(obj)
{
	var paraCtId=$(obj).val();
	var optionHtml= constructparaCtBindField(paraCtId,"");
	$(obj).parent().next("td").find("select").html(optionHtml);
}

//构建控件类型的绑定字段
function constructparaCtBindField(ctrId,paraCtBindKey){
	var controlBindSourceJson=eval($("[name='controlBindSourceJson']").val());
	var optonHtml="";
	$.each(controlBindSourceJson,function(i,item){
		if(item.id==ctrId){
			var groupFieldJson=eval(item.option);
			$.each(groupFieldJson,function(i,fieldItem){
				var selected=(fieldItem.key==paraCtBindKey)?" selected" :"";
				optonHtml+="<option value='"+fieldItem.key+"' "+selected+" >"+fieldItem.name+"</opton>";
			});
		}
	});
	return optonHtml;
}

/**
* 预览脚本效果
*/
function previewScript(){
	var scriptType = $("#type").val();
	//自定义类型custom\\默认类型
	if(scriptType=='custom'){
		//先保存脚本编辑器内容到scriptContent
		InitMirror.save();
		var scriptContent = $("#scriptContent").val();
		exeCustomScript(scriptContent,"edit");
	}else{
		var classInsName = $("input[name='classInsName']").val();
		var methodName = $("select[name='methodName']").val();
		var json = getTrJson();
		exeScript(classInsName,methodName,json);
	}
};


//执行脚本方法
function exeCustomScript(scriptContent,type){
	if(typeof(scriptContent)==undefined||scriptContent==null||scriptContent==''){
		alert("脚本内容不能为空！");
		return false;
	}	
	var url= __ctx + '/platform/system/aliasScript/toCustomPreview.ht';	
	var conf={ scriptContent:scriptContent,type:type };
	var winArgs="dialogWidth:800px;dialogHeight:600px;help:0;status:0;scroll:1;center:1";
	url=url.getNewUrl();
	var returnValue = window.showModalDialog(url,conf,winArgs);
	if(typeof(returnValue)!=undefined&&returnValue!=null&&type=="edit"){
		//编辑进入的方法才有返回值代码
		InitMirror.editor.setCode(returnValue);
	//	$("#scriptContent").val(returnValue);
	}
	
};


//执行脚本方法
function exeScript(classInsName,methodName,json){
	if(typeof(classInsName)==undefined||classInsName==null||classInsName==''){
		alert("调用类的对象名不能为空！");
		return false;
	}
	if(typeof(methodName)==undefined||methodName==null||methodName==''){
		alert("调用方法名不能为空！");
		return false;
	}
	var scriptStr = classInsName+"."+methodName;
	var url= __ctx + '/platform/system/aliasScript/toPreview.ht';
	var jsonStr = JSON2.stringify(json);
	if(typeof(jsonStr)==undefined||jsonStr==null||jsonStr=='[]'||jsonStr==''){
		jsonStr='';
		scriptStr += "()";
	}else{
		var str = '';
		for ( var i = 0; i < json.length; i++) {
			var obj = json[i];
			str += obj.paraName+","
		}
		str = str.substring(0, str.length-1);
		scriptStr += '('+str+')';
	}
	scriptStr = encodeURIComponent(scriptStr);
	jsonStr = encodeURIComponent(jsonStr);
	url += "?scriptStr="+scriptStr+"&alias="+jsonStr;
	var winArgs='height=500,width=700,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no';
	url=url.getNewUrl();
	window.open (url,"别名脚本预览",winArgs);
};
