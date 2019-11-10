package com.hanthink.pub.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.pub.dao.PubWorkCalendarDao;
import com.hanthink.pub.model.PubWorkCalendarModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：生产日历查询 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class PubWorkCalendarDaoImpl extends MyBatisDaoImpl<String, PubWorkCalendarModel> implements PubWorkCalendarDao{

    @Override
    public String getNamespace() {
        return PubWorkCalendarModel.class.getName();
    }

    /**
     * 执行分页查询
     */
	@Override
	public List<PubWorkCalendarModel> queryPubWorkCalendarForPage(PubWorkCalendarModel model, DefaultPage p) {
		
		return this.getByKey("queryPubWorkCalendarForPage", model, p);
	}

	/**
	 * 右侧栏位显示查询结果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<PubWorkCalendarModel> getRowClick(String idWorkCalendar, DefaultPage p) {
		Map<String,Object> map=new HashMap();
		map.put("id", idWorkCalendar);
		return (List<PubWorkCalendarModel>) this.getList("getRowClick", map,p);
	}

    @Override
    public List<PubWorkCalendarModel> queryWorkTime(PubWorkCalendarModel model) {
        return this.getByKey("queryWorkTime", model);
    }

    @Override
    public List<PubWorkCalendarModel> queryRestTime(PubWorkCalendarModel model) {
        return this.getByKey("queryRestTime", model);
    }

    @Override
    public Integer queryIsWorkDay(PubWorkCalendarModel pubWorkCalendarModel) {
        return this.sqlSessionTemplate.selectOne(this.getNamespace()+".queryIsWorkDay", pubWorkCalendarModel);
    }

    @Override
    public List<PubWorkCalendarModel> queryIsWorkDayForList(List<PubWorkCalendarModel> newPubWorkCalendarModels) {
        return this.getByKey("queryIsWorkDayForList", newPubWorkCalendarModels);
    }
    
}

