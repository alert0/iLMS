/**
 * 描述：TODO
 * 包名：com.hotent.platform.bpm.manager
 * 文件名：ISqlBuilder.java
 * 作者：User-mailto:liyj@jee-soft.cn
 * 日期2014-7-16-下午6:07:26
 *  2014广州宏天软件有限公司版权所有
 * 
 */
package com.hotent.sys.api.data.source.sqlbuilder;

import com.hotent.sys.api.data.source.model.SqlBuilderModel;


/**
 * <pre> 
 * 描述：TODO
 * 作者：lyj
 * 邮箱:liyj@jee-soft.cn
 * 日期:2014-7-16-下午6:07:26
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface ISqlBuilderService {

	String getSql(SqlBuilderModel model);
	
}
