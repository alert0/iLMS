package com.hotent.sys.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.SysMsgTemplate;


/**
 * 
 * <pre> 
 * 描述：消息模版 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-03-10 09:20:11
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysMsgTemplateDao extends Dao<String, SysMsgTemplate> {

	void setNotDefaultByType(String key);

	void setDefault(String id);

	List<SysMsgTemplate> getByIds(List<String> ids);

	void delByTypeKey(List<String> delTypeKey);

	boolean isExistByKeyAndTypeKey(String key, String typeKey);

}
