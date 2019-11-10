package com.hotent.bo.persistence.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.bo.persistence.model.BODataRel;

/**
 * 
 * <pre> 
 * 描述：BO数据关联表，用于多对多的情况。 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-05 09:58:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BODataRelDao extends Dao<String, BODataRel> {
}
