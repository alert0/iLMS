package com.hotent.biz.message.manager;



import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.manager.api.Manager;
import com.hotent.biz.message.model.MessageType;


/**
 * 
 * <pre> 
 * 描述：xq_message_type 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-04 15:26:02
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface MessageTypeManager extends Manager<String, MessageType>{
	/**
	 * 获取 分类权限
	 * @param id
	 * @param objType
	 * @return
	 */
	public JSONArray getAuthority(String id,String objType);
	/**
	 * 保存权限
	 * @param id
	 * @param objType
	 * @param ownerNameJson
	 * @return
	 */
	public void getSaveAuth(String id,String objType,String ownerNameJson);
	
	public List<String> getAuthorizeIdsByUserMap(String objType);
	
	public List<Map<String,Object>> queryAll(QueryFilter queryFilter);
	
	public String saveOrUpdateAuthorize(MessageType messageType);
	/**
	 * 通过分类名获取对象
	 * @param classificationCode
	 * @return
	 */
	public MessageType getByClassificationCode(String classificationCode);
	/**
	 * 判断分类名是否重复
	 * @param classificationCode
	 * @param id
	 * @return
	 */
	public Boolean isExistCode(String classificationCode, String id);
}
