package com.hanthink.mp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.mp.dao.MpVehPlanDao;
import com.hanthink.mp.model.MpVehPlanModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：车辆计划DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class MpVehPlanDaoImpl extends MyBatisDaoImpl<String, MpVehPlanModel> implements MpVehPlanDao{

    @Override
    public String getNamespace() {
        return MpVehPlanModel.class.getName();
    }
	
    /**
   	 * 查询功能重写
   	 * @param importList
   	 * @author linzhuo	
   	 * @DATE	2018年9月10日 上午11:11:33
   	 */
	@Override
	public List<MpVehPlanModel> queryMpVehPlanForPage(MpVehPlanModel model, DefaultPage p) {
		 return this.getByKey("queryMpVehPlanForPage", model, p);
	}

	
	/**
	 * 导出数据查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<MpVehPlanModel> queryMpVehPlanByKey(MpVehPlanModel model) {
		return this.getByKey("queryMpVehPlanForPage", model);
	}
	
	/**
	 * 校验批量删除里面是否有已订购数据
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public Integer queryMpVehPlanCheck(List<String> listIds) {
		return sqlSessionTemplate.selectOne(getNamespace()+".queryMpVehPlanCheck", listIds);	
	}
	
	/**
	 * 查询未订购数据的SortId用于记录日志
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<String> querySortIdAndLogByCalStatus() {
		
		return (List<String>) this.getList("querySortIdAndLogByCalStatus", null);	
	}

	/**
	 * 直接删除未订购数据
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public void removeAndLogByCalStatus(String factoryCode) {
		
		this.deleteByKey("removeAndLogByCalStatus", factoryCode);	
	}

	/**
	 * 获取生产计划
	 */
	@Override
	public Integer getVehPlan(String params) {
		Map<String,Object> map = new HashMap<>();
		map.put("factoryCode", params);
		map.put("outCode", null);   
		this.sqlSessionTemplate.selectOne("getVehPLan",map);	
		return   (Integer) map.get("outCode");
	}

	/**
	 * 车辆计划批量删除
	 */
	public void deleteByIds(String[] aryIds) throws Exception{
		this.deleteByKey("deleteByIds", aryIds);
	}


	
}

