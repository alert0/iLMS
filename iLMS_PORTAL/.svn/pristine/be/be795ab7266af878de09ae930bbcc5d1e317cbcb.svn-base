/**
 * 判断传入参数是否为日期类型
 * @param date
 * @returns {Boolean}
 */
function isDate(date){
	if(date!=""){
		var oDate1 = new Date(date);
		if(oDate1=="Invalid Date"){
			return false;
		}else{
			return true;
		}
	}else{
		return false;
	}
}

/**
 * 根据type类型返回相应日期
 * @param date1
 * @param date2
 * @param type min:取较小日期  max:取较大日期
 * @returns
 */
function findDatetByType(date1,date2,type){
	if(date1==""&&date2==""){
		return "";
	}
	if(date1==""){
		return date2;
	}
	if(date2==""){
		return date1;
	}
    var oDate1 = new Date(date1);
    var oDate2 = new Date(date2);
    if(oDate1.getTime() > oDate2.getTime()){
    	if(type=="min"){
    		return date2;
    	}else{
    		return date1;
    	}
    }else{
    	if(type=="min"){
    		return date1;
    	}else{
    		return date2;
    	}
    }
}


jQuery.extend($.fn.groupingCalculations, {
  handler: function(d, c, e, a, f, g) {
    var b = {
              sum: function() {
                  return parseFloat(c || 0) + parseFloat(g[e] || 0)
              },
              min: function() {
            	  if(isDate(g[e])){
            		  return "" === c ? g[e]: findDatetByType(c, g[e],"min")
            	  }else{
            		  return "" === c ? parseFloat(g[e] || 0) : Math.min(parseFloat(c), parseFloat(g[e] || 0))
            	  }
              },
              max: function() {
            	  if(isDate(g[e])){
            		  return "" === c ? g[e]: findDatetByType(c, g[e],"max")
            	  }else{
            		  return "" === c ? parseFloat(g[e] || 0) : Math.max(parseFloat(c), parseFloat(g[e] || 0))
            	  }
              },
              count: function() {
                  "" === c && (c = 0);
                  return g.hasOwnProperty(e) ? c + 1 : 0
              },
              avg: function() {
                  return b.sum()
              }
          };
          if (!b[d]) throw "jqGrid Grouping No such method: " + d;
          d = b[d]();
          null != a && ("fixed" == f ? d = d.toFixed(a) : (a = Math.pow(10, a), d = Math.round(d * a) / a));
          return d
      }
});

