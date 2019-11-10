package com.hanthink.mp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.mp.dao.MpExcepOrderDemandHisDao;
import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.MpExcepOrderDemandHisModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;

/**
 * 
 * @Desc    : 异常订单Dao实现类
 * @CreateOn: 2018年9月7日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */

@Repository
public class MpExcepOrderDemandHisDaoImpl extends MyBatisDaoImpl<String, MpExcepOrderDemandHisModel> implements MpExcepOrderDemandHisDao{

    @Override
    public String getNamespace() {
        return MpExcepOrderDemandHisModel.class.getName();
    }

    @Override
    public List<MpExcepOrderDemandHisModel> queryMpExcepOrderDemandHisForPage(MpExcepOrderDemandHisModel model, DefaultPage p) {
        return this.getByKey("queryMpExcepOrderDemandHisForPage", model, p);
    }

    /**
	 * 例外订单生成
	 */
	@Override
	public Integer generateMpExcepOrderDemandHis(String curFactoryCode) {
		Map<String,Object> map = new HashMap<>();
		map.put("factoryCode", curFactoryCode);
		map.put("outCode", null);
		this.sqlSessionTemplate.selectOne("generateMpExcepOrderDemandHis",map);	
		/**
		 * 获取存储过程返回值
		 */
		return  (Integer) map.get("outCode");
	}

	/**
	 * 例外订单发布
	 */
	@Override
	public Integer releaseMpExcepOrderDemandHis(String curFactoryCode,String opeId) {
		Map<String,Object> map = new HashMap<>();
		map.put("factoryCode", curFactoryCode);
		map.put("opeId", opeId);
		map.put("outCode", null);   
		this.sqlSessionTemplate.selectOne("releaseMpExcepOrderDemandHis",map);
		return   (Integer) map.get("outCode");
	}

	/**
	 * 导出查询的方法
	 */
	@Override
	public List<MpExcepOrderDemandHisModel> queryMpExcepOrderDemandHisByKey(MpExcepOrderDemandHisModel model) {
		return this.getByKey("queryMpExcepOrderDemandHisForPage", model);
	}

}
