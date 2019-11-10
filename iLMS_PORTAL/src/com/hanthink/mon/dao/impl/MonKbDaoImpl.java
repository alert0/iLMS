package com.hanthink.mon.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.mon.dao.MonKbDao;
import com.hanthink.mon.model.BigStockKbModel;
import com.hanthink.mon.model.MonKbModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
/**
 * @ClassName: MonKbDaoImpl
 * @Description:物流监控看板
 * @author luocc
 * @date 2018年11月3日
 */
@Repository
public class MonKbDaoImpl extends MyBatisDaoImpl<String, MonKbModel> implements MonKbDao{
	
	@Override
	public String getNamespace() {
		return MonKbModel.class.getName();
	}
	
	/**
	 * @Description:基础看板信息分页查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<MonKbModel>   
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	@Override
	public PageList<MonKbModel> queryMonBaseKbPage(MonKbModel model, DefaultPage page) {
		return (PageList<MonKbModel>) this.getList("queryMonBaseKbPage", model, page);
	}
	/**
	 * @Description:基础看板信息单条新增
	 * @param: @param model
	 * @param: @return    
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	@Override
	public void addMonBaseKbOne(MonKbModel model) {
		this.insertByKey("addMonBaseKbOne", model);
		
	}
	/**
	 * @Description:基础看板信息单条修改
	 * @param: @param model
	 * @param: @return    
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	@Override
	public void modifyMonBaseKbOne(MonKbModel model) {
		this.updateByKey("modifyMonBaseKbOne", model);
		
	}
	/**
	 * @Description:基础看板信息批量删除
	 * @param: @param ids
	 * @param: @return    
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	@Override
	public void removeByKbIds(List<MonKbModel> models) {
		this.deleteByKey("removeByKbIds", models);
		this.deleteByKey("removeDetailByKbIds",models);
	}
	/**
	 * @Description:查询供应商看板
	 * @param: @param model
	 * @param: @return MonKbModel
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月10日
	 */
	@Override
	public MonKbModel selectBatchBySeq(MonKbModel model) {
		return (MonKbModel) this.getOne("selectBatchBySeq", model);
	}
	/**
	 * @Description:查询厂内拉动看板
	 * @param: @param model
	 * @param: @return MonKbModel
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月10日
	 */
	@Override
	public MonKbModel selectBatchBySeqAndSkew(MonKbModel model) {
		return (MonKbModel) this.getOne("selectBatchBySeqAndSkew", model);
	}
	
	/**
	 * @Description:设置厂内拉动看板偏移进度
	 * @param: @param model
	 * @param: 
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月10日
	 */
	@Override
	public void updateSkewProcessNo(MonKbModel model) {
		this.updateByKey("updateSkewProcessNo",model);
		
	}

	/**
	 * @Description:基础看板信息业务主键校验
	 * @param: @param model
	 * @param: @return Integer
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	@Override
	public Integer checkUnique(MonKbModel model) {
		return (Integer)this.getOne("checkUnique", model);
	}
	
	/**
	 * @Description:看板明细业务主键校验
	 * @param: @param model
	 * @param: @return Integer
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	@Override
	public Integer checkKbId(MonKbModel model) {
		return (Integer)this.getOne("checkKbId",model);
	}
	
	/**
	 * @Description:查询看板详情
	 * @param: @param model
	 * @param: @return MonKbModel
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	@Override
	public MonKbModel queryKbDetailOne(MonKbModel model) {
		return (MonKbModel)this.getOne("queryKbDetailOne", model);
	}
	/**
	 * @Description:看板明细信息新增
	 * @param: @param model
	 * @param: @return    
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	@Override
	public void addKbDetailOne(MonKbModel model) {
		this.insertByKey("addKbDetailOne", model);
		
	}
	/**
	 * @Description:看板明细信息修改
	 * @param: @param model
	 * @param: @return    
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	@Override
	public void modifyKbDetailOne(MonKbModel model) {
		this.updateByKey("modifyKbDetailOne", model);
		//this.updateByKey("modifyKbDistLog", model);
	}
	
	/**
	 * @Description:封装下拉框  看板名称
	 * @param: @param model
	 * @param: @return List<DictVO>
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	@Override
	public List<DictVO> queryForMonKbName(MonKbModel model) {
		return (List<DictVO>)this.getList("queryForMonKbName", model);
	}
	/**
	 * @Description:封装下拉框  工程
	 * @param: @param model
	 * @param: @return List<DictVO>
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	@Override
	public List<DictVO> queryForDistriPerson(MonKbModel model) {
		return (List<DictVO>)this.getList("queryForDistriPerson", model);
	}
	/**
	 * @Description:封装下拉框  安灯灯号
	 * @param: @param model
	 * @param: @return List<DictVO>
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	@Override
	public List<DictVO> queryForLampId() {
		return (List<DictVO>)this.getList("queryForLampId",1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigStockKbModel> queryStockKbDetails(Map<String, String> paramMap) throws Exception {
		return (List<BigStockKbModel>) this.getList("queryStockKbDetails", paramMap);
	}
}
