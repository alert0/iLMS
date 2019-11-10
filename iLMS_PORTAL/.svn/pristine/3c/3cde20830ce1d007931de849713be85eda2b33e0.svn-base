package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.persistence.model.CopyTo;


/**
 * 抄送的数据库访问。
 * <pre> 
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-11-27-下午1:43:39
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface CopyToDao extends Dao<String, CopyTo> {
	
	/**
	 * 根据流程实例删除抄送。
	 * @param instList 
	 * void
	 */
	void delByInstList(List<String> instList);
	
	
	/**
	 * 取得某人接收到的抄送转发。
	 * @param userId
	 * @param filter
	 * @return 
	 * List&lt;CopyTo>
	 */
	PageList<CopyTo> getReceiverCopyTo(String userId,QueryFilter filter);
	
	/**
	 * 获取由我发起的操送。
	 * @param userId
	 * @param filter
	 * @return PageList&lt;CopyTo>
	 */
	PageList<CopyTo> getMyCopyTo(String userId,QueryFilter filter);
	
}
