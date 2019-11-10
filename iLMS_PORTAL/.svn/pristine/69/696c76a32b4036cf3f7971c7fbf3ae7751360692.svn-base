var boEntList = [];//存放添加的bo
var boDefList = [];//存放添加的boDef
var formDef = null;//存放添加的formDef
var stepsAnchor = null;
var isNotDisCur = true;
var isContinue = false;//是否继续添加
formType = $.getParameter("formType") || "" ;
function formNextStep(step , url, type, param){
	//下一步或继续添加保存数据后将数据放入对应的变量中
	if(param && param!=''){
		putHistoryData(step,type,param);
	}
	//保存成功后的回调函数,主要是对右边导向图的调整
	if(step == "next" && stepsAnchor){
		$.fn.stepClickHandler(stepsAnchor,isNotDisCur);
	}
	//添加BO数据时，支持新增和添加外部表
	if(type == 'bOEntEdit' || type == 'bOEntExtEdit'){
		$("#boEntTypeDiv").show();
	}else{
		$("#boEntTypeDiv").hide();
	}
	//添加BO数据时，如果是新增的BO,将是否生成表字段设置为true
	if(param && (type == "bODefEdit" || type == "bOEntEdit" )){
		createTableByName(param);
	}
	//下一步或继续添加时，重现载入iframe
	if(url){
		var src = __ctx + url;
		$('#buildFormFrame').attr('src',src);
	}
}

/**
 * 下一步时的操作
 * @param anchor
 * @param currentIndex
 * @param isNotDisCur
 * @returns {Boolean}
 */
function formToStep(anchor,currentIndex,isNotDisCur){
	stepsAnchor = anchor;
	isNotDisCur = isNotDisCur;
	isContinue = false;
	if(currentIndex==2){
		addFormStepOne();
	}else if(currentIndex==3){
		var myScope =  getCurrentScope();
		myScope.save();
	}else{
		iframe.find(".fa-save")[0].click();
	}
	return false;
}

/**
 * 选择表单模板
 * @param key
 */
function toSelectTemplate(key){
	putHistoryData('next','newForm',key);
	if(formDef!=null){
		showSelectTemplate(formDef.id,false);
	}
}

/**
 * 继续添加 操作
 * @param index
 */
function toContinueStep(){
	isContinue = true;
	iframe.find(".fa-save")[0].click();
}

/**
 * 表单定义
 */
function addFormStepOne(){
	var myScope =  getCurrentScope();
	var typeId = myScope.form.typeId;
	var name = myScope.form.name;
	var formKey = myScope.form.key;
	var bos = myScope.form.bos;
	if(!typeId){
		 $.topCall.error("请选择分类");
		 return ;
	}else if(!name){
		 $.topCall.error("请输入表单标题");
		 return ;
	}else if(!formKey){
		$.topCall.error("请输入Key");
		 return ;
	}else if($('#key').next("label.wrong")[0]){
		$.topCall.error("表单Key已经存在");
		 return ;
	}else if(!bos || bos.length<1){
		$.topCall.error("请选择BO对象");
		 return ;
	}
	var form = myScope.nextStep();
	if (form) {
		var tempform = form.clone();
		tempform.find("select").val(form.find("select").val());
		tempform.find("textarea").val(form.find("textarea").val());
		tempform[0].action = __ctx + '/form/formDef/formDefEdit';
		$("body").append(tempform);
		tempform.attr("target","buildFormFrame");
		tempform.submit();
		tempform.remove();
		$.fn.stepClickHandler(stepsAnchor,isNotDisCur);
	}
}

/**
 * 获取iframe中的scope
 * @returns
 */
function getCurrentScope(){
	var iframeContentWindow = $("#buildFormFrame")[0].contentWindow;
	var iframeAngular = iframeContentWindow.angular;
	var myScope =  iframeAngular.element('[ng-controller]');
	return myScope.scope();
}

/**
 * 将新增的BO实体生成表
 * @param name
 * @returns {Boolean}
 */
function createTableByName(name) {
	var isSuccess = false;
	$.ajax({
		url : __ctx + "/bo/def/bOEnt/createTableByName?name=" + name,
		type : 'POST',
		dataType : 'json',
		async:false,
		success : function(data) {
			if (data.result == 1) {
				isSuccess = true
			}
		}
	});
	return isSuccess;
}

/**
 * 完成 操作
 */
function formStepFinished(){
	$("#buildFormFrame")[0].contentWindow.formStepFinished(); 
}

/**
 * 下一步或继续添加保存数据后将数据放入对应的变量中
 * @param step
 * @param type
 * @param param
 */
function putHistoryData(step,type,param){
	if(step=='current' && type == 'bODefEdit'){
		type = 'addFormStepOne';
	}
	var url = '';
	switch(type)
	{
		case 'bODefEdit':url = '/bo/def/bOEnt/getObject?name='+param;break;
		case 'addFormStepOne':url = '/bo/def/bODef/getObject?key='+param;break;
		case 'newForm':url = '/form/formDef/getObject?key='+param;break;
		default:url = '/bo/def/bOEnt/getObject?name='+param;
	}
	$.ajax({
		url : __ctx + url,
		dataType : 'json',
		async:false,
		success : function(data) {
			if(type == 'bOEntEdit' || type=="bODefEdit" || type=="bOEntExtEdit"){
				boEntList.push(data);
			}else if(type == 'addFormStepOne'){
				boDefList.push(data);
			}else if(type == 'newForm'){
				formDef = data;
			}
		}
	});
}