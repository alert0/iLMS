package com.hotent.restful.params;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import com.hotent.base.api.query.FieldLogic;
import com.hotent.base.api.query.FieldRelation;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.base.db.model.DefaultFieldLogic;
import com.hotent.base.db.model.DefaultQueryField;

public class RestParamUtil {
	/**
	 * 通过Param参数Map构建查询条件
	 * <pre>
	 * 	1.参数字段命名规则。
	 * 		Q^参数名称^参数类型
	 * 	2.在这里构建的逻辑都是and逻辑。
	 * 
	 *  参数类型说明。
	 * 	
	 * 	S:字符串
	 * 	L:长整形
	 *  N:整形
	 *  BD:BigDecimal
	 *  FT:float
	 *  SN:short
	 *  DL:date begin
	 *  DG:date end
	 *  SL:字符串 模糊查询
	 *  SLL:字符串 左模糊查询
	 *  SLR:字符串 右模糊查询
	 * </pre>
	 * @param paramMap
	 * @param fieldRelation
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static FieldLogic getFieldLogic(Map paramMap, FieldRelation fieldRelation){
		if(BeanUtils.isEmpty(fieldRelation)){
			fieldRelation = FieldRelation.AND;
		}
		FieldLogic andFieldLogic=new DefaultFieldLogic(fieldRelation);
		Set keySet = paramMap.keySet();
		for (Object keyObj : keySet) {
			String key = keyObj.toString();
			if(!key.startsWith("Q^")) continue;
			
			Object valObj = paramMap.get(keyObj);
			if(BeanUtils.isEmpty(valObj)) continue;
			String value = valObj.toString();
			try {//对参数传过来的格式作处理
				if(value.startsWith("[")&&value.endsWith("]")){
					value = value.replaceAll("\\[", "");
					value = value.replaceAll("\\]", "");
					value = value.replaceAll("\"", "");
				}
			} catch (Exception e) {}
			//如果object为数组，则取数组的值
			if(valObj.getClass().isArray()){
				String[] values = (String[]) valObj;
				value = values[0];
			}
			String[] aryParaKey = key.split("\\^");
			if (aryParaKey.length != 3&&aryParaKey.length != 2) continue;
			 
			String paraName =aryParaKey.length==2?key.substring(2):key.substring(2, key.lastIndexOf("^"));// key.substring(2, key.lastIndexOf("^")); 
			
			//Q^authorizeDesc则=aryParaKey.length== 2 为了自己能够在XML中动态构造SQL
			String type =aryParaKey.length== 2?"S": key.substring(key.lastIndexOf("^") + 1);
			//对日期的特殊处理，防止参数中Key名称一致 带有 ^DG的参数名修改为还 xxx^DG
			if(type.equals("DG"))paraName=key.substring(2);
			//使结束时间的查询字符串日期加1，且为日期类型
			if(aryParaKey.length== 2&&paraName.indexOf("_DG")>-1)
			{
				type=key.substring(key.lastIndexOf("_") + 1);
			}
			andFieldLogic.getWhereClauses().add(new DefaultQueryField(paraName, getCompare(type), getObjValue(type,value)));
			
		}
		return andFieldLogic;
	}
	
	/**
	 * 根据查询字段的类型 取得合适的比较符
	 * @param type
	 * @return 
	 * QueryOP
	 */
	public static QueryOP getCompare(String type){
		// 字符串 精准匹配
		if ("S".equals(type)) {
			return QueryOP.EQUAL;
		}
		// 字符串 模糊查询
		else if ("SL".equals(type)) {
			return QueryOP.LIKE;
		}
		// 字符串 右模糊查询
		else if ("SLR".equals(type)) {
			return QueryOP.LIKE;
		}
		// 字符串 左模糊查询
		else if ("SLL".equals(type)) {
			return QueryOP.LIKE;
		}
		// date begin
		else if ("DL".equals(type)) {
			return QueryOP.GREAT_EQUAL;
		}
		// date end
		else if ("DG".equals(type)) {
			return QueryOP.LESS_EQUAL;
		} else {
			return QueryOP.EQUAL;
		}
		
	}
	
	/**
	 * 根据传入的类型获得真正值的格式
	 * 
	 * </p>
	 * 
	 * @param type
	 * @param paramValue
	 * @return
	 */
	private static Object getObjValue(String type, String paramValue) {
		Object value = null;
		// 字符串 精准匹配
		if ("S".equals(type)) {
			value = paramValue;
		}
		else if ("SL".equals(type)) {
			value = "%" + paramValue +"%";
		}
		// 字符串 右模糊查询
		else if ("SLR".equals(type)) {
			value =  paramValue +"%";
		}
		// 字符串 左模糊查询
		else if ("SLL".equals(type)) {
			value = "%" + paramValue;
		}
		// 长整型
		else if ("L".equals(type)) {
			value = new Long(paramValue);
		}
		// 整型
		else if ("N".equals(type)) {
			value = new Integer(paramValue);
		} else if ("DB".equals(type)) {
			value = new Double(paramValue);
		}
		// decimal
		else if ("BD".equals(type)) {
			value = new BigDecimal(paramValue);
		}
		// FLOAT
		else if ("FT".equals(type)) {
			value = new Float(paramValue);
		}
		// short
		else if ("SN".equals(type)) {
			value = new Short(paramValue);
		}
		// date begin
		else if ("DL".equals(type)) {
			value = TimeUtil.convertString(paramValue, "yyyy-MM-dd");
		}
		// date end
		else if ("DG".equals(type)) {
			value = TimeUtil.getNextDays(
					TimeUtil.convertString(paramValue, "yyyy-MM-dd"), 1);
		} else {
			value = paramValue;
		}
		return value;
	}
}
