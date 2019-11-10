package com.hanthink.mp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.dao.MpWeekPlanDao;
import com.hanthink.mp.model.MpWeekPlanModel;

import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：剩余量主数据 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class MpWeekPlanDaoImpl extends MyBatisDaoImpl<String, MpWeekPlanModel> implements MpWeekPlanDao{

    @Override
    public String getNamespace() {
        return MpWeekPlanModel.class.getName();
    }
	
    /**
   	 * 查询功能重写
   	 * @param importList
   	 * @author linzhuo	
   	 * @DATE	2018年9月10日 上午11:11:33
   	 */
	@Override
	public List<MpWeekPlanModel> queryMpWeekPlanForPage(MpWeekPlanModel model, DefaultPage p) {
		 return this.getByKey("queryMpWeekPlanForPage", model, p);
	}

	/**
	 * 获取年份下拉框
	 */
	@Override
	public List getYear() {
		Map<String,Object> map=new HashMap();
		return this.getList("getYear", map);
	}
    
}

