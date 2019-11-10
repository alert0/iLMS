package com.hanthink.pub.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.pub.dao.PubTactTimeDao;
import com.hanthink.pub.model.PubTactTimeModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：生产节拍查询 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class PubTactTimeDaoImpl extends MyBatisDaoImpl<String, PubTactTimeModel> implements PubTactTimeDao{

    @Override
    public String getNamespace() {
        return PubTactTimeModel.class.getName();
    }

    /**
     * 执行分页查询
     */
	@Override
	public List<PubTactTimeModel> queryPubTactTimeForPage(PubTactTimeModel model, DefaultPage p) {
		
		return this.getByKey("queryPubTactTimeForPage", model, p);
	}

	@Override
	public List<PubTactTimeModel> queryPubTactTimeByKey(PubTactTimeModel model) {
		return this.getByKey("queryPubTactTimeForPage", model);
	}
	
}

