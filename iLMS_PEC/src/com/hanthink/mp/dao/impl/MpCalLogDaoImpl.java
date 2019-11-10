package com.hanthink.mp.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.mp.dao.MpCalLogDao;
import com.hanthink.mp.model.MpCalLogModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：订单计算 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class MpCalLogDaoImpl extends MyBatisDaoImpl<String, MpCalLogModel> implements MpCalLogDao{

    @Override
    public String getNamespace() {
        return MpCalLogModel.class.getName();
    }
	
    /**
   	 * 查询功能重写
   	 * @param importList
   	 * @author linzhuo	
   	 * @DATE	2018年9月10日 上午11:11:33
   	 */
	@Override
	public List<MpCalLogModel> queryMpCalLogForPage(MpCalLogModel model, DefaultPage p) {
		 return this.getByKey("queryMpCalLogForPage", model, p);
	}

	
	/**
	 * 导出数据查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<MpCalLogModel> queryMpCalLogByKey(MpCalLogModel model) {
		return this.getByKey("queryMpCalLogForPage", model);
	}

	/**
	 * 点击“需求计算”按钮时，要校验当前是否正在进行净需求计算。如果有，要提示错误，且不允许执行。
	 */
	@Override
	public String isLockCheck(MpCalLogModel model) {
		return this.sqlSessionTemplate.selectOne(getNamespace() + ".isLockCheck", model);	
	}

	/**
	 * 集成订购需求推算
	 */
	@Override
	public Integer demandCal(String uuid, String opeId, String typeLock, String arrFactory) {
		HashMap<String,Object> paramMap = new HashMap<String,Object>();   
		paramMap.put("uuid", uuid);
		paramMap.put("opeId", opeId);
		paramMap.put("type", typeLock);
		paramMap.put("arrFactory", arrFactory);
		paramMap.put("outCode", null);
		this.sqlSessionTemplate.selectOne("demandCal",paramMap);
		return (Integer) paramMap.get("outCode");
	}

	/**
	 * 生成采购订单
	 */
	@Override
	public Integer releaseOrder(String uuid, String opeId, String typeLock, String arrFactory) {
		HashMap<String,Object> paramMap = new HashMap<String,Object>();   
		paramMap.put("uuid", uuid);
		paramMap.put("opeId", opeId);
		paramMap.put("type", typeLock);
		paramMap.put("arrFactory", arrFactory);	
		paramMap.put("outCode", null);   
		this.sqlSessionTemplate.selectOne("releaseOrder", paramMap); 
		return (Integer) paramMap.get("outCode");
	}

	/**
	 * 查询状态
	 */
	@Override
	public Integer selectStatus(String arrFactory) {
		return this.sqlSessionTemplate.selectOne(getNamespace() + ".selectStatus", arrFactory);	
	}

    @Override
    public Integer generateOrderNo(String uuid, String opeId, String typeLock, String arrFactory) {
        HashMap<String,Object> paramMap = new HashMap<String,Object>();   
        paramMap.put("uuid", uuid);
        paramMap.put("opeId", opeId);
        paramMap.put("type", typeLock);
        paramMap.put("arrFactory", arrFactory); 
        paramMap.put("outCode", null);   
        this.sqlSessionTemplate.selectOne(getNamespace() + ".generateOrderNo", paramMap); 
        return (Integer) paramMap.get("outCode");
    }

    @Override
    public String queryLastCalLog(String factoryCode) {
        return this.sqlSessionTemplate.selectOne(getNamespace() + ".queryLastCalLog", factoryCode);
    }

    @Override
    public Integer queryPurOrderTempCount(String factoryCode) {
        return this.sqlSessionTemplate.selectOne(getNamespace() +  ".queryPurOrderTempCount", factoryCode);
    }

	
}

