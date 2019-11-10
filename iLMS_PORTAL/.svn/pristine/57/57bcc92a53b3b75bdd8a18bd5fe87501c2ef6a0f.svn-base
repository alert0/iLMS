package com.hotent.biz.message.dao.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.biz.message.dao.MessageTypeDao;
import com.hotent.biz.message.model.MessageType;


/**
 * 
 * <pre> 
 * 描述：xq_message_type DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-04 15:26:02
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class MessageTypeDaoImpl extends MyBatisDaoImpl<String, MessageType> implements MessageTypeDao{

    @Override
    public String getNamespace() {
        return MessageType.class.getName();
    }
  /*  *//**
	 * 获取所有的授权的对象用户
	 * @param map
	 * @return
	 *//*
    public List<BpmDefUser> getUserMap(Map<String,Object> map){
    	return getByKey("getAll", map); 
    }*/

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryAll(QueryFilter queryFilter) {
		// TODO Auto-generated method stub
		return this.getByQueryFilter("queryAll", queryFilter);
	}
	@Override
	public MessageType getByClassificationCode(String classificationCode) {
		return this.getUnique("getByClassificationCode", classificationCode);
	}
	@Override
	public Boolean isExistCode(String classificationCode, String id) {
		Map<String,Object> map=new HashMap<String,Object>();
    	map.put("classificationCode", classificationCode);
    	map.put("id", id);
    	Integer count=(Integer)this.getOne("isExistCode", map);
    	return count>0;
	}
}

