/**
 * 说明
 * [formtype="window"]主要是处理子表弹出框模式，
 * table.listTable  处理子表的块模式和表内模式
 */

if (typeof QueryUI == 'undefined') {
	QueryUI = {};
}

/**
 * queryedCacheData保存当前页面的已经查询出来的数据
 * 数据格式：
 * queryedCacheData{
 * 		
 * }
 */
QueryUI.queryedCacheData={};

QueryUI.init = function(){
	$("select[selectquery]").each(function(){
		var me = $(this),
			selectquery = me.attr("selectquery");
			
		if (!selectquery)
			return true;
		selectquery = selectquery.replaceAll("'", "\"");
		var queryJson = JSON2.parse(selectquery);
		var query = queryJson.query;
		
		for (var i = 0; i < query.length; i++) {
				if(!query[i].trigger||query[i].trigger=='-1')
				continue;
				// isMain是true 就是绑定主表的字段
				var field = $("[ng-model='data."+query[i].trigger+"']");
				if (field[0])
					QueryUI.change(field, me, queryJson);
		}
		QueryUI.getvalue(me, queryJson);
	});
};

QueryUI.change = function(field, SelectObj, queryJson) {
		field.bind("change", function() {
				QueryUI.getvalue(SelectObj, queryJson);
		});
	};

QueryUI.replaceValue = function(objValue) {
	return objValue.replaceAll(",", "#,");
};


QueryUI.getvalue = function(SelectObj, queryJson) {

	var query = queryJson.query;
	var fieldValue = "";
	var querydataStr = "{";
	for (var i = 0; i < query.length; i++) {
		fieldValue = query[i].trigger?getValByScope(null,"data."+query[i].trigger,getScope()):"";
		//如果对应的绑定字段有值则使用该值，没有值则判断设计时有没有输入预设值，有预设值则使用，没有则不做输入条件
		if (typeof(fieldValue) != "undefined" && fieldValue != ""&& fieldValue!=null) {
			querydataStr += query[i].condition + ":\"" + fieldValue + "\",";
		}else{
			if(query[i].initValue)
				querydataStr += query[i].condition + ":\"" + query[i].initValue + "\",";
		}
	}
	if (querydataStr.length > 1){
		querydataStr = querydataStr.substring(0, querydataStr.length - 1);
		querydataStr += "}";
	}else{
		querydataStr += "}";
	}
	
	var queryedCacheDataString="";
	//如果querydataStr为{}，说明是查询全部数据，引用一个"queryedCacheDataString"标识全部数据
	if(querydataStr=="{}"){
		queryedCacheDataString="queryedCacheDataString";
	}else{
		queryedCacheDataString=querydataStr;
	}
	var tempParams = parseToJson(querydataStr);
	var tempParamsArrs = [];
	for(var i in tempParams){
		tempParamsArrs.push({"key":i,"value":tempParams[i]});
	}
	
	var cacheData=QueryUI.getCascaData(queryJson.name,queryedCacheDataString);
	SelectObj.empty();
	if(cacheData){
		QueryUI.handData(cacheData,queryJson,SelectObj);
	}else{
		queryCond = {
			alias : queryJson.name,
			id : queryJson.id,
			querydata : JSON.stringify(tempParamsArrs),
			page : 0,
			pagesize : 0
		};
		$.ajax({
			   type: "POST",
			   url: __ctx + "/form/customquery/doQuery",
			   async:true,
			   data: queryCond,
			   success: function(data){
					if (data.errors || data.rows.length < 1) {
						return;
					}
					QueryUI.handData(data.rows,queryJson,SelectObj);
					QueryUI.setCascaData(queryJson.name,queryedCacheDataString,data.rows);
			}
		});
	};
};

/**
 * 获取缓存数据
 */
QueryUI.getCascaData = function(alias,querydata){
	if(!QueryUI.queryedCacheData)return;
	var tempAlias=QueryUI.queryedCacheData[alias];
	if(!tempAlias)return ;
	var tempData=tempAlias[querydata];
	return tempData;
};

/**
 * 设置缓存数据
 */
QueryUI.setCascaData = function(alias,querydata,data){
	var aliasObj = QueryUI.queryedCacheData[alias] || {};
	aliasObj[querydata] = data;
	QueryUI.queryedCacheData[alias] = aliasObj;
};


/**
 * 获取数据以后经过处理，显示多下拉列表中
 */
QueryUI.handData=function(data,queryJson,currSelectObj){
	var selectObj=$(currSelectObj);
	var oFrag=document.createDocumentFragment();
	var firstVal = "";
	for (var i = 0; i < data.length; i++) {
		var dataobj = data[i];
		var datavalue = dataobj[queryJson.bind.value];
		var datakey = dataobj[queryJson.bind.key];
		var isSame=false;
		//判断是否有重复的数据
		for (var j=0; j<i;j++){
			if(datakey== data[j][queryJson.bind.key]){
				isSame=true;
				break;
			}
		}
		if(isSame) continue;
		var opt = $("<option>").val(datakey).text(datavalue);
		oFrag.appendChild(opt[0]);
		if(i==0) firstVal = datavalue;
	}
	selectObj.append(oFrag);
	selectObj.val(firstVal);
	selectObj.trigger("change");
};
