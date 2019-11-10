package com.hanthink.mp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.mp.dao.MpDiffNumTempDao;
import com.hanthink.mp.model.MpDiffNumTempModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：w+1,w+2生产计划查询 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class MpDiffNumTempDaoImpl extends MyBatisDaoImpl<String, MpDiffNumTempModel> implements MpDiffNumTempDao{

    @Override
    public String getNamespace() {
        return MpDiffNumTempModel.class.getName();
    }
	
    /**
   	 * 查询功能重写
   	 * @param importList
   	 * @author linzhuo	
   	 * @DATE	2018年9月10日 上午11:11:33
   	 */
	@Override
	public List<MpDiffNumTempModel> queryMpDiffNumTempForPage(MpDiffNumTempModel model, DefaultPage p) {
		 return this.getByKey("queryMpDiffNumTempForPage", model, p);
	}

	
	/**
	 * 导出数据查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<MpDiffNumTempModel> queryMpDiffNumTempByKey(MpDiffNumTempModel model) {
		return this.getByKey("queryMpDiffNumTempForPage", model);
	}

	/**
	 * 获取调整计划
	 */
	@Override
	public Integer getZsbDiffPlan(String curFactoryCode) {
		Map<String,Object> map = new HashMap<>();
		map.put("in_arr_factory", curFactoryCode);
		map.put("outCode", null);   
		this.sqlSessionTemplate.selectOne("getZsbDiffPlan",map);	
		return   (Integer) map.get("outCode");
	}

}

