package com.hanthink.mp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.mp.dao.MpAdjPlanDao;
import com.hanthink.mp.model.MpAdjPlanModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：调整计划 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class MpAdjPlanDaoImpl extends MyBatisDaoImpl<String, MpAdjPlanModel> implements MpAdjPlanDao{

    @Override
    public String getNamespace() {
        return MpAdjPlanModel.class.getName();
    }
	
    /**
   	 * 查询功能重写
   	 * @param importList
   	 * @author linzhuo	
   	 * @DATE	2018年9月10日 上午11:11:33
   	 */
	@Override
	public List<MpAdjPlanModel> queryMpAdjPlanForPage(MpAdjPlanModel model, DefaultPage p) {
		 return this.getByKey("queryMpAdjPlanForPage", model, p);
	}

	
	/**
	 * 导出数据查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<MpAdjPlanModel> queryMpAdjPlanByKey(MpAdjPlanModel model) {
		return this.getByKey("queryMpAdjPlanForPage", model);
	}

	/**
	 * 获取计算队列
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List getUnloadPort() {
		Map<String,Object> map=new HashMap();
		return this.getList("getUnloadPort", map);
	}

	/**
	 * 获取调整计划
	 */
	@Override
	public Integer getAdjPlan(String curFactoryCode, String adjDateStrStart, String adjDateStrEnd) {
		Map<String,Object> map = new HashMap<>();
		map.put("in_arr_factory", curFactoryCode);
		map.put("in_start_date", adjDateStrStart);
		map.put("in_end_date", adjDateStrEnd);
		map.put("outCode", null);   
		this.sqlSessionTemplate.selectOne("getAdjPlan",map);	
		return   (Integer) map.get("outCode");
	}
	
}

