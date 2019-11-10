package com.hotent.sys.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.QueryMetafield;

/**
 * 
 * <pre> 
 * 描述：sys_query_metafield DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:Aschs
 * 邮箱:6322665042@qq.com
 * 日期:2016-06-13 16:42:01
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface QueryMetafieldDao extends Dao<String, QueryMetafield> {

	List<QueryMetafield> getBySqlId(String sqlId);

	void removeBySqlId(String sqlId);
}
