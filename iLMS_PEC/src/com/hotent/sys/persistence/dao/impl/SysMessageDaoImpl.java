package com.hotent.sys.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.sys.persistence.dao.SysMessageDao;
import com.hotent.sys.persistence.model.SysMessage;

/**
 * 
 * <pre> 
 * 描述：sys_msg DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-18 09:03:31
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysMessageDaoImpl extends MyBatisDaoImpl<String, SysMessage> implements SysMessageDao{

    @Override
    public String getNamespace() {
        return SysMessage.class.getName();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<SysMessage> getMsgByUserId(QueryFilter queryFilter) {
		return this.getByQueryFilter("getMsgByUserId", queryFilter);
	}


	/**
	 * 获取还没有读取的信息
	 * currentUserId 当前人ID
	 * isPublish 是否为公告 1=公告，0=非公共，2=全部
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysMessage> getNotReadMsgByUserId(String currentUserId,
			DefaultPage page, int isPublish) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("receiverId", currentUserId);
		param.put("isPublish", isPublish);
		return this.getByKey("getNotReadMsgByUserId",param,page);
	}

	/**
	 * 获取还没有读取的信息
	 * currentUserId 当前人ID
	 * isPublish 是否为公告 1=公告，0=非公共
	 */
	@Override
	public List<SysMessage> getNotReadMsgByUserId(String userId, int isPublish) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("receiverId", userId);
		param.put("isPublish", isPublish);//是否是
		return this.getByKey("getNotReadMsgByUserId",param);
	}
	/**
	 * 获取最新的一条还没有读取的信息
	 * currentUserId 当前人ID
	 */
	@Override
	public SysMessage getOneNotReadMsgByUserId(String userId) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("receiverId", userId);
		return this.getByKey("getOneNotReadMsgByUserId_"+this.getDbType(),param).get(0);
	}

	@Override
	public int getNotReadMsgNum(String userId) {
		return this.getByKey("getNotReadMsgNum",userId).size();
	}

	@Override
	public int getMsgSize(String receiverId) {
		return this.getByKey("getMsgSize", receiverId).size();
	}
	
	
}

