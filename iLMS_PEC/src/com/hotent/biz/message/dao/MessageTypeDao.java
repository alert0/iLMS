package com.hotent.biz.message.dao;


import java.util.List;
import java.util.Map;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.biz.message.model.MessageType;


/**
 * 
 * <pre> 
 * 描述：xq_message_type DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-04 15:26:02
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface MessageTypeDao extends Dao<String, MessageType> {

	List<Map<String, Object>> queryAll(QueryFilter queryFilter);
	//public List<BpmDefUser> getUserMap(Map<String,Object> map);
	
	// 通过分类名获取对象
	MessageType getByClassificationCode(String classificationCode);
	//判断分类名是否重复
	Boolean isExistCode(String classificationCode, String id);
}
