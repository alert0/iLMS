package com.hotent.base.core.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotent.base.api.query.FieldLogic;
import com.hotent.base.api.query.FieldRelation;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.encrypt.Base64;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.DateFormatUtil;
import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.base.db.model.DefaultFieldLogic;
import com.hotent.base.db.model.DefaultQueryField;
import com.hotent.mini.web.util.FtpFileUtil;
import com.hotent.sys.persistence.model.DefaultFile;

public class RequestUtil {

	private static Logger logger = LoggerFactory.getLogger(QueryFilter.class);

	public static final String RETURNURL = "returnUrl";

	private RequestUtil() {
	}

	/**
	 * 取字符串类型的参数。 如果取得的值为null，则返回默认字符串。
	 * 
	 * @param request
	 * @param key
	 *            字段名
	 * @param defaultValue
	 * @return
	 */
	public static String getString(HttpServletRequest request, String key,
			String defaultValue, boolean b) {
		String value = request.getParameter(key);
		if (StringUtil.isEmpty(value))
			return defaultValue;
		if (b) {
			return value.replace("'", "\"").trim();
		} else {
			return value.trim();
		}
	}

	/**
	 * 取字符串类型的参数。 如果取得的值为null，则返回空字符串。
	 * 
	 * @param request
	 * @param key
	 *            字段名
	 * @return
	 */
	public static String getString(HttpServletRequest request, String key) {
		return getString(request, key, "", true);
	}

	/**
	 * 取字符串类型的参数。 如果取得的值为null，则返回空字符串。
	 * 
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(HttpServletRequest request, String key,
			boolean b) {
		return getString(request, key, "", b);
	}

	/**
	 * 取字符串类型的参数。 如果取得的值为null，则返回空字符串。
	 * 
	 * @param request
	 * @param key
	 * @param b
	 *            是否替换'为''
	 * @return
	 */
	public static String getString(HttpServletRequest request, String key,
			String defaultValue) {
		return getString(request, key, defaultValue, true);
	}

	/**
	 * 
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getStringAry(HttpServletRequest request, String key) {
		String[] aryValue = request.getParameterValues(key);
		if (aryValue == null || aryValue.length == 0) {
			return "";
		}
		String tmp = "";
		for (String v : aryValue) {
			if ("".equals(tmp)) {
				tmp += v;
			} else {
				tmp += "," + v;
			}
		}
		return tmp;
	}
	
	/**
	 * 
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String[] getStringArray(HttpServletRequest request, String key) {
		String[] aryValue = request.getParameterValues(key);
		if (aryValue == null || aryValue.length == 0) {
			return new String[]{};
		}
		return aryValue;
	}

	/**
	 * 取得安全字符串。
	 * 
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getSecureString(HttpServletRequest request,
			String key, String defaultValue) {
		String value = request.getParameter(key);
		if (StringUtil.isEmpty(value))
			return defaultValue;
		return filterInject(value);

	}

	/**
	 * 取得安全字符串，防止程序sql注入，脚本攻击。
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getSecureString(HttpServletRequest request, String key) {
		return getSecureString(request, key, "");
	}

	/**
	 * 过滤script|iframe|\\||;|exec|insert|select|delete|update|count|chr|truncate
	 * |char字符串 防止SQL注入
	 * 
	 * @param str
	 * @return
	 */
	public static String filterInject(String str) {
		String injectstr = "\\||;|exec|insert|select|delete|update|count|chr|truncate|char";
		Pattern regex = Pattern.compile(injectstr, Pattern.CANON_EQ
				| Pattern.DOTALL | Pattern.CASE_INSENSITIVE
				| Pattern.UNICODE_CASE);
		Matcher matcher = regex.matcher(str);
		str = matcher.replaceAll("");
		str = str.replace("'", "''");
		str = str.replace("-", "—");
		str = str.replace("(", "（");
		str = str.replace(")", "）");
		str = str.replace("%", "％");

		return str;
	}

	/**
	 * 从Request中取得指定的小写值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getLowercaseString(HttpServletRequest request,
			String key) {
		return getString(request, key).toLowerCase();
	}

	/**
	 * 从request中取得int值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static int getInt(HttpServletRequest request, String key) {
		return getInt(request, key, 0);
	}

	/**
	 * 从request中取得int值,如果无值则返回缺省值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static int getInt(HttpServletRequest request, String key,
			int defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Integer.parseInt(str);

	}

	/**
	 * 从Request中取得long值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static long getLong(HttpServletRequest request, String key) {
		return getLong(request, key, 0);
	}

	/**
	 * 取得长整形数组
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static Long[] getLongAry(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		if (BeanUtils.isEmpty(aryKey))
			return null;
		Long[] aryLong = new Long[aryKey.length];
		for (int i = 0; i < aryKey.length; i++) {
			aryLong[i] = Long.parseLong(aryKey[i]);
		}
		return aryLong;
	}

	/**
	 * 根据一串逗号分隔的长整型字符串取得长整形数组
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static Long[] getLongAryByStr(HttpServletRequest request, String key) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return null;
		str = str.replace("[", "");
		str = str.replace("]", "");
		String[] aryId = str.split(",");
		Long[] lAryId = new Long[aryId.length];
		for (int i = 0; i < aryId.length; i++) {
			lAryId[i] = Long.parseLong(aryId[i]);
		}
		return lAryId;
	}

	/**
	 * 根据一串逗号分隔的字符串取得字符串形数组
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String[] getStringAryByStr(HttpServletRequest request,
			String key) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return null;
		String[] aryId = str.split(",");
		String[] lAryId = new String[aryId.length];
		for (int i = 0; i < aryId.length; i++) {
			lAryId[i] = (aryId[i]);
		}
		return lAryId;
	}

	/**
	 * 根据键值取得整形数组
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static Integer[] getIntAry(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		if (BeanUtils.isEmpty(aryKey))
			return null;
		Integer[] aryInt = new Integer[aryKey.length];
		for (int i = 0; i < aryKey.length; i++) {
			aryInt[i] = Integer.parseInt(aryKey[i]);
		}
		return aryInt;
	}

	public static Float[] getFloatAry(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		if (BeanUtils.isEmpty(aryKey))
			return null;
		Float[] fAryId = new Float[aryKey.length];
		for (int i = 0; i < aryKey.length; i++) {
			fAryId[i] = Float.parseFloat(aryKey[i]);
		}
		return fAryId;
	}

	/**
	 * 从Request中取得long值,如果无值则返回缺省值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static long getLong(HttpServletRequest request, String key,
			long defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Long.parseLong(str.trim());
	}

	/**
	 * 从Request中取得long值,如果无值则返回缺省值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static Long getLong(HttpServletRequest request, String key,
			Long defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Long.parseLong(str.trim());
	}

	/**
	 * 从Request中取得float值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static float getFloat(HttpServletRequest request, String key) {
		return getFloat(request, key, 0);
	}

	/**
	 * 从Request中取得float值,如无值则返回缺省值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static float getFloat(HttpServletRequest request, String key,
			float defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Float.parseFloat(request.getParameter(key));
	}

	/**
	 * 从Request中取得boolean值,如无值则返回缺省值 false, 如值为数字1，则返回true
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(HttpServletRequest request, String key) {
		return getBoolean(request, key, false);
	}

	/**
	 * 从Request中取得boolean值 对字符串,如无值则返回缺省值, 如值为数字1，则返回true
	 * 
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(HttpServletRequest request, String key,
			boolean defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		if (StringUtils.isNumeric(str))
			return (Integer.parseInt(str) == 1 ? true : false);
		return Boolean.parseBoolean(str);
	}

	/**
	 * 从Request中取得boolean值,如无值则返回缺省值 0
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static Short getShort(HttpServletRequest request, String key) {
		return getShort(request, key, (short) 0);
	}

	/**
	 * 从Request中取得Short值 对字符串,如无值则返回缺省值
	 * 
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Short getShort(HttpServletRequest request, String key,
			Short defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Short.parseShort(str);
	}

	/**
	 * 从Request中取得Date值,如无值则返回缺省值,如有值则返回 yyyy-MM-dd HH:mm:ss 格式的日期,或者自定义格式的日期
	 * 
	 * @param request
	 * @param key
	 * @param style
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(HttpServletRequest request, String key,
			String style) throws ParseException {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return null;
		if (StringUtil.isEmpty(style))
			style = "yyyy-MM-dd HH:mm:ss";
		return (Date) DateFormatUtil.parse(str, style);
	}

	/**
	 * 从Request中取得Date值,如无值则返回缺省值null, 如果有值则返回 yyyy-MM-dd 格式的日期
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(HttpServletRequest request, String key)
			throws ParseException {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return null;
		return (Date) DateFormatUtil.parseDate(str);

	}

	/**
	 * 从Request中取得Date值,如无值则返回缺省值 如有值则返回 yyyy-MM-dd HH:mm:ss 格式的日期
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws ParseException
	 */
	public static Date getTimestamp(HttpServletRequest request, String key)
			throws ParseException {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return null;
		return (Date) DateFormatUtil.parseDateTime(str);
	}

	/**
	 * 取得当前页URL,如有参数则带参数
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return
	 */
	public static String getUrl(HttpServletRequest request) {
		StringBuffer urlThisPage = new StringBuffer();
		urlThisPage.append(request.getRequestURI());
		Enumeration<?> e = request.getParameterNames();
		String para = "";
		String values = "";
		urlThisPage.append("?");
		while (e.hasMoreElements()) {
			para = (String) e.nextElement();
			values = request.getParameter(para);
			urlThisPage.append(para);
			urlThisPage.append("=");
			urlThisPage.append(values);
			urlThisPage.append("&");
		}
		return urlThisPage.substring(0, urlThisPage.length() - 1);
	}

	/**
	 * 取得上一页的路径。
	 * 
	 * @param request
	 * @return
	 */
	public static String getPrePage(HttpServletRequest request) {
		if (StringUtil.isEmpty(request.getParameter(RETURNURL))) {
			return request.getHeader("Referer");
		}
		return request.getParameter(RETURNURL);
	}



	private static void addQueryParameter(String key, String[] values,
			Map<String, Object> map) {
		if (values.length == 1) {
			String val = values[0].trim();
			if (StringUtil.isNotEmpty(val))
				map.put(key, values[0].trim());
		} else
			map.put(key, values);

	}
	
	/**
	 * 获取查询字段。
	 * 
	 * @param request
	 * @return 
	 * FieldLogic
	 */
	@SuppressWarnings("rawtypes")
	public static FieldLogic getFieldLogic(HttpServletRequest request) {
		Map parameterMap = request.getParameterMap();
		return getFieldLogic(parameterMap, null);
	}
	
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
			value = paramValue.trim();
		}
		else if ("SL".equals(type)) {
			value = "%" + paramValue.trim() +"%";
		}
		// 字符串 右模糊查询
		else if ("SLR".equals(type)) {
			value =  paramValue.trim() +"%";
		}
		// 字符串 左模糊查询
		else if ("SLL".equals(type)) {
			value = "%" + paramValue.trim();
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

	private static Object[] getObjValue(String type, String[] value) {
		Object[] aryObj = new Object[value.length];
		for (int i = 0; i < aryObj.length; i++) {
			String v = "";
			if (value[i] != null)
				v = value[i].toString();
			aryObj[i] = getObjValue(type, v);
		}
		return aryObj;
	}
	
	public static Map<String,Object>  getParameterValueMap(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("__ctx",  request.getContextPath());
		Enumeration<?> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String key = params.nextElement().toString();
			String[] values = request.getParameterValues(key);
			if (values == null)
				continue;
			if (values.length == 1) {
				String tmpValue = values[0];
				if (tmpValue == null)
					continue;
				tmpValue = tmpValue.trim();
				if (tmpValue.equals(""))
					continue;
				tmpValue = filterInject(tmpValue);
				if (tmpValue.equals(""))
					continue;
				map.put(key, tmpValue);
			} else {
				String rtn = getByAry(values, true);
				if (rtn.length() > 0) 
					map.put(key, rtn);
			}
		}
		return map;
	}

	/**
	 * 把当前上下文的请求封装在map中
	 * 
	 * @param request
	 * @param remainArray
	 *            保持为数组
	 * @param isSecure
	 *            过滤不安全字符
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getParameterValueMap(HttpServletRequest request,
			boolean remainArray, boolean isSecure) {
		Map map = new HashMap();
		map.put("__ctx",  request.getContextPath());
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String key = params.nextElement().toString();
			String[] values = request.getParameterValues(key);
			if (values == null)
				continue;
			if (values.length == 1) {
				String tmpValue = values[0];
				if (tmpValue == null)
					continue;
				tmpValue = tmpValue.trim();
				if (tmpValue.equals(""))
					continue;
				if (isSecure)
					tmpValue = filterInject(tmpValue);
				if (tmpValue.equals(""))
					continue;
				map.put(key, tmpValue);
			} else {
				String rtn = getByAry(values, isSecure);
				if (rtn.length() > 0) {
					if (remainArray)
						map.put(key, rtn.split(","));
					else
						map.put(key, rtn);
				}
			}
		}
		return map;
	}

	/**
	 * 
	 * @param aryTmp
	 * @param isSecure
	 * @return
	 */
	private static String getByAry(String[] aryTmp, boolean isSecure) {
		String rtn = "";
		for (int i = 0; i < aryTmp.length; i++) {
			String str = aryTmp[i].trim();
			if (!str.equals("")) {
				if (isSecure) {
					str = filterInject(str);
					if (!str.equals(""))
						rtn += str + ",";
				} else {
					rtn += str + ",";
				}
			}
		}
		if (rtn.length() > 0)
			rtn = rtn.substring(0, rtn.length() - 1);
		return rtn;
	}

	/**
	 * 根据参数名称获取参数值。
	 * 
	 * <pre>
	 * 1.根据参数名称取得参数值的数组。
	 * 2.将数组使用逗号分隔字符串。
	 * </pre>
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static String getStringValues(HttpServletRequest request,
			String paramName) {
		String[] values = request.getParameterValues(paramName);
		if (BeanUtils.isEmpty(values))
			return "";
		String tmp = "";
		for (int i = 0; i < values.length; i++) {
			if (i == 0) {
				tmp += values[i];
			} else {
				tmp += "," + values[i];
			}
		}
		return tmp;
	}

	/**
	 * 取得local。
	 * 
	 * @param request
	 * @return
	 */
	public static Locale getLocal(HttpServletRequest request) {
		Locale local = request.getLocale();
		if (local == null)
			local = Locale.CHINA;
		return local;
	}

	/**
	 * 获取出错的url
	 * 
	 * @param request
	 * @return
	 */
	public final static String getErrorUrl(HttpServletRequest request) {
		String errorUrl = (String) request
				.getAttribute("javax.servlet.error.request_uri");
		if (errorUrl == null) {
			errorUrl = (String) request
					.getAttribute("javax.servlet.forward.request_uri");
		}
		if (errorUrl == null) {
			errorUrl = (String) request
					.getAttribute("javax.servlet.include.request_uri");
		}
		if (errorUrl == null) {
			errorUrl = request.getRequestURL().toString();
		}
		return errorUrl;
	}

	/**
	 * 获取IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}


	/**
	 * 下载文件。
	 * @param response
	 * @param b		文件的二进制流
	 * @param fileName		文件名称。
	 * @throws IOException
	 */ 
	public static void downLoadFileByByte(HttpServletRequest request,HttpServletResponse response,byte[] b,String fileName) throws IOException {
		OutputStream outp = response.getOutputStream();
		if (b.length>0) {
			response.setContentType("APPLICATION/OCTET-STREAM");
			String filedisplay = fileName;
			String agent = (String)request.getHeader("USER-AGENT");  
			//firefox
			if(agent != null && agent.indexOf("MSIE") == -1) {   
				String enableFileName = "=?UTF-8?B?" + (new String(Base64.getBase64(filedisplay))) + "?=";     
			    response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);    
			}
			else{
				filedisplay=URLEncoder.encode(filedisplay,"utf-8");
				response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
			}	
			outp.write(b);
		} else {
			outp.write("文件不存在!".getBytes("utf-8"));
		}
		if (outp != null) {
			outp.close();
			outp = null;
			response.flushBuffer();
		}
	}
	/**
	 * 下载文件。
	 * @param response
	 * @param fullPath		文件的全路径
	 * @param fileName		文件名称。
	 * @throws IOException
	 */
	public static void downLoadFile(HttpServletRequest request,HttpServletResponse response,String fullPath,String fileName) throws IOException {
		OutputStream outp = response.getOutputStream();
		File file = new File(fullPath);
		if (file.exists()) {
			response.reset();
			response.setContentType("APPLICATION/OCTET-STREAM");
			String filedisplay = fileName;
			String agent = (String)request.getHeader("USER-AGENT");  
			//firefox
			if(agent != null && agent.indexOf("MSIE") == -1 && agent.indexOf("Trident") == -1 ) {
				agent=agent.toLowerCase();
				if(agent.indexOf("iphone")!=-1){
					String ext=fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
					filedisplay=URLEncoder.encode(filedisplay,"utf-8");
					if("bmp,jpg,png,tiff,gif,svg".indexOf(ext)!=-1){
						response.addHeader("Content-Disposition", "inline;filename=" + filedisplay);
					}else{
						response.setContentType("APPLICATION/OCTET-STREAM");
						response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
					}
                }else{
                	String downName = null;
                	try {
                		if (request.getHeader("user-agent").toLowerCase().contains("msie")
                				|| request.getHeader("user-agent").toLowerCase().contains("like gecko") ) {
            				downName = URLEncoder.encode(filedisplay, "UTF-8");
            	    	}else{
            	    		downName = new String(filedisplay.getBytes("UTF-8"), "ISO_8859_1");
            	    	}
            		} catch (UnsupportedEncodingException e) {
            			e.printStackTrace();
            			throw e;
            		}
//                    String enableFileName = "=?UTF-8?B?" + (new String(Base64.getBase64(filedisplay))) + "?=";
//                    response.setHeader("Content-Disposition", "attachment;filename=" + enableFileName);
                	response.setHeader("Content-Disposition", "attachment;filename=" + downName);
                }
			}
			else{
				filedisplay=URLEncoder.encode(filedisplay,"utf-8");
				response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
			}	
			FileInputStream in = null;
			try {
				outp = response.getOutputStream();
				in = new FileInputStream(fullPath);
				byte[] b = new byte[1024];
				int i = 0;
				while ((i = in.read(b)) > 0) {
					outp.write(b, 0, i);
				}
				outp.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					in.close();
					in = null;
				}
				if (outp != null) {
					try {
						outp.close();
						outp = null;
						response.flushBuffer();
					}catch (Exception e){

					}

				}
			}
		} else {
			outp.write("File does not exist!".getBytes("utf-8"));
		}
	}

	/**
	 * 下载FTP服务器文件
	 * @param request
	 * @param response
	 * @param file
	 * @param fileName
	 * @author ZUOSL	
	 * @throws Exception 
	 * @DATE	2018年9月12日 下午6:31:48
	 */
	public static boolean downLoadFileByFtp(HttpServletRequest request, HttpServletResponse response, DefaultFile file,
			String fileName) throws Exception {
		OutputStream outp = response.getOutputStream();
		response.setContentType("APPLICATION/OCTET-STREAM");
		String downName = null;
    	try {
    		if (request.getHeader("user-agent").toLowerCase().contains("msie")
    				|| request.getHeader("user-agent").toLowerCase().contains("like gecko") ) {
				downName = URLEncoder.encode(fileName, "UTF-8");
	    	}else{
	    		downName = new String(fileName.getBytes("UTF-8"), "ISO_8859_1");
	    	}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw e;
		}
    	response.setHeader("Content-disposition", "attachment; filename=" + downName);
    	
		try {
			FtpFileUtil ftputil = new FtpFileUtil();
			return ftputil.downLoadFile(outp, file.getFilePath(), file.getId() + "." + file.getExt());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	/**
	 * 解码获取的参数字符(参数可能有中文时)
	 * @param request
	 * @param string
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月27日 下午3:31:05
	 */
	public static String getDecodeString(HttpServletRequest request, String key) {
		try {
			return java.net.URLDecoder.decode(getString(request, key), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
