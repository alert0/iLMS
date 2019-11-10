package com.hanthink.mp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.mp.dao.MpAdjOrderDiffDao;
import com.hanthink.mp.model.MpAdjOrderDiffModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：计划对比 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class MpAdjOrderDiffDaoImpl extends MyBatisDaoImpl<String, MpAdjOrderDiffModel> implements MpAdjOrderDiffDao{

    @Override
    public String getNamespace() {
        return MpAdjOrderDiffModel.class.getName();
    }
	
    /**
   	 * 查询功能重写
   	 * @param importList
   	 * @author linzhuo	
   	 * @DATE	2018年9月10日 上午11:11:33
   	 */
	@Override
	public List<MpAdjOrderDiffModel> queryMpAdjOrderDiffForPage(MpAdjOrderDiffModel model, DefaultPage p) {
		 return this.getByKey("queryMpAdjOrderDiffForPage", model, p);
	}

	
	/**
	 * 导出数据查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<MpAdjOrderDiffModel> queryMpAdjOrderDiffByKey(MpAdjOrderDiffModel model) {
		return this.getByKey("queryMpAdjOrderDiffForPage", model);
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
	 * 执行计划对比
	 */
	@Override
	public Integer getAdjOrderDiff(String curFactoryCode, String account) {
		Map<String,Object> map = new HashMap<>();
		map.put("arrFactory", curFactoryCode);
		map.put("opeId", account);
		map.put("outCode", null);   
		this.sqlSessionTemplate.selectOne(this.getNamespace()+".getAdjOrderDiff",map);	
		return   (Integer) map.get("outCode");
	}

	/**
	 * 根据供应商代码查询相关信息
	 */
	@Override
	public List<MpAdjOrderDiffModel> queryEmail(String supplier) {
		return (List<MpAdjOrderDiffModel>) this.getList("queryEmail", supplier);
	}

	/**
	 * 向信息共享平台供应商能力反馈表批量插入数据
	 */
	@Override
	public void insertMpAdjSupFeedback(List<MpAdjOrderDiffModel> pageList) {
		 this.insertBatchByKey("insertMpAdjSupFeedback", pageList);
	}

	/**
	 * 修改发送标识
	 */
	@Override
	public void updateSendFlag(MpAdjOrderDiffModel idVo) {
		this.updateByKey("updateSendFlag", idVo);
	}

}

