package com.hotent.bpmx.persistence.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmTransReceiverDao;
import com.hotent.bpmx.persistence.model.BpmTransReceiver;

/**
 * 
 * <pre> 
 * 描述：流转任务接收人 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-06 10:51:37
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BpmTransReceiverDaoImpl extends MyBatisDaoImpl<String, BpmTransReceiver> implements BpmTransReceiverDao{

    @Override
    public String getNamespace() {
        return BpmTransReceiver.class.getName();
    }

	@Override
	public List<BpmTransReceiver> getByTransRecordid(String transRecordid) {
		return this.getByKey("getByTransRecordid", transRecordid);
	}

	@Override
	public BpmTransReceiver getByTransRecordAndUserId(Map<String, String> params) {
		return this.getUnique("getByTransRecordAndUserId", params);
	}
	
}

