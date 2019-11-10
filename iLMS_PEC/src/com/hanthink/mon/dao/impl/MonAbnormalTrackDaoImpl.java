package com.hanthink.mon.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.mon.dao.MonAbnormalTrackDao;
import com.hanthink.mon.model.MonAbnormalTrackModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：空箱返还看板查询 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class MonAbnormalTrackDaoImpl extends MyBatisDaoImpl<String, MonAbnormalTrackModel> implements MonAbnormalTrackDao{

    @Override
    public String getNamespace() {
        return MonAbnormalTrackModel.class.getName();
    }

	@Override
	public List<MonAbnormalTrackModel> queryAbnormalForPage(MonAbnormalTrackModel model) {
		return (List<MonAbnormalTrackModel>) this.getList("queryAbnormalForPage", model);
	}
    
}

