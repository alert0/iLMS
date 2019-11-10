package com.hotent.bpmx.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmExeStackRelation;

/**
 * 
 * <pre> 
 * 描述：堆栈关系表 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-06-17 13:56:55
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BpmExeStackRelationDao extends Dao<String, BpmExeStackRelation> {
	/**
	 * 根据堆栈ID获取关系记录
	 * @param stackId
	 * @param isToOrFrom
	 *  是在ToStackId位置还是以FromStackId字段：to,from
	 * @return
	 */
     BpmExeStackRelation getByStackId(String stackId,String isToOrFrom);
     
     
	 List<BpmExeStackRelation> getListByProcInstId(String procInstId);
	
}
