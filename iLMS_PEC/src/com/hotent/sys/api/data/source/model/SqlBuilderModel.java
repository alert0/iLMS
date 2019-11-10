/**
 * 描述：TODO
 * 包名：com.hotent.platform.bpm.model
 * 文件名：SqlBuilderModel.java
 * 作者：User-mailto:liyj@jee-soft.cn
 * 日期2014-7-16-下午5:53:46
 *  2014广州宏天软件有限公司版权所有
 * 
 */
package com.hotent.sys.api.data.source.model;

import java.util.Map;

import net.sf.json.JSONArray;

/**
 * <pre>
 * 描述：TODO
 * 作者：lyj
 * 邮箱:liyj@jee-soft.cn
 * 日期:2014-7-16-下午5:53:46
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface SqlBuilderModel {
	public final static String HANDLERTYPE_PAGESETTING = "setting";
	public final static String HANDLERTYPE_DIYSQL = "diy";
	public String getHandlerType() ;

	public String getDbType() ;

	public String getFromName() ;

	public JSONArray getResultField() ;

	public JSONArray getConditionField() ;

	public JSONArray getSortField();
 
	public String getDiySql();

	public Map<String, Object> getValue() ;


}
