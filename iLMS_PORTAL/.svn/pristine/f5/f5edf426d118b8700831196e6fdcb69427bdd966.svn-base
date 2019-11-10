package com.hotent.sys.persistence.dao;
import java.util.List;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.sys.persistence.model.SysMessage;

/**
 * 
 * <pre> 
 * 描述：sys_msg DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-18 09:03:31
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysMessageDao extends Dao<String, SysMessage> {

	List<SysMessage> getMsgByUserId(QueryFilter queryFilter);

	List<SysMessage> getNotReadMsgByUserId(String currentUserId,
			DefaultPage page, int isPublish);

	List<SysMessage> getNotReadMsgByUserId(String userId, int isPublish);
	
	SysMessage getOneNotReadMsgByUserId(String userId);

	int getNotReadMsgNum(String userId);

	int getMsgSize(String receiverId);
}
