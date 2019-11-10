package com.hanthink.mon.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.mon.dao.MonKbDao;
import com.hanthink.mon.model.BigStockKbModel;
import com.hanthink.mon.model.EmptyReturnKbModel;
import com.hanthink.mon.model.MonKbModel;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<MonKbModel> queryForKbConfig(String factoryCode, Page page) throws Exception {
		return (List<MonKbModel>) this.getList("queryForKbConfig", factoryCode, page);
	}

	@Override
	public MonKbModel getLoactionShowMessage(Map<String, String> paramMap) throws Exception {
		return this.sqlSessionTemplate.selectOne("getLoactionShowMessage", paramMap);
	}

	@Override
	public void updateCurrentKbStatus(MonKbModel model) throws Exception {
		this.updateByKey("updateCurrentKbStatus", model);
	}
	
	@Override
	public MonKbModel queryKbDetailForPage(Map<String, String> paramMap) throws Exception {
		MonKbModel model = new MonKbModel();
		if (StringUtil.isEmpty(paramMap.get("distriPerson"))) {
			String distriPerson = this.sqlSessionTemplate.selectOne("queryDistriPerson", paramMap);			
			this.sqlSessionTemplate.selectOne("queryKbDetailForPage", paramMap);
			model.setCurrbatchNo(paramMap.get("currBatchNo"));
			model.setProcessCycleNum(paramMap.get("batchCycleNum"));
			model.setRunProcessNo(paramMap.get("runProcessNo"));
			model.setKbStatus(paramMap.get("kbStatus"));
			model.setRunDelayFlag(paramMap.get("runDelayFlag"));
			model.setCurrbatchseqNo(paramMap.get("currbatchseqNo"));
			if (StringUtil.isEmpty(paramMap.get("currBatchNo"))) {
				model.setDistriPerson(null);
			}else {
				model.setDistriPerson(distriPerson);
			}
		}else {
			this.sqlSessionTemplate.selectOne("queryKbDetailForPage", paramMap);
			model.setCurrbatchNo(paramMap.get("currBatchNo"));
			model.setProcessCycleNum(paramMap.get("batchCycleNum"));
			model.setRunProcessNo(paramMap.get("runProcessNo"));
			model.setKbStatus(paramMap.get("kbStatus"));
			model.setRunDelayFlag(paramMap.get("runDelayFlag"));
			model.setCurrbatchseqNo(paramMap.get("currbatchseqNo"));
			if (StringUtil.isEmpty(paramMap.get("kbStatus"))) {
				model.setDistriPerson(null);
			}else {
				model.setDistriPerson(paramMap.get("distriPerson"));				
			}
		}
		return model;
	}

	/**
	 * 大物备货看板
	 */
	/**
	 * 获取批次
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BigStockKbModel> getBatchNo(String kbCode) {
		Map<String,Object> map=new HashMap();
		map.put("kbCode", kbCode);
		return this.getList("getBatchNo", map);
	}

	/**
	 * 根据批次查询单头
	 */
	@Override
	public List<BigStockKbModel> getBigStockKbAC(String distriProductSeqNo, String factoryCode, String workCenter) {
		Map<String,Object> map=new HashMap();
		map.put("distriProductSeqNo", distriProductSeqNo);
		map.put("factoryCode", factoryCode);
		map.put("workCenter", workCenter);
		return this.getList("getBigStockKbAC", map);
	}

	@Override
	public List<BigStockKbModel> getBigStockKbWC(String distriProductSeqNo, String factoryCode, String workCenter) {
		Map<String,Object> map=new HashMap();
		map.put("distriProductSeqNo", distriProductSeqNo);
		map.put("factoryCode", factoryCode);
		map.put("workCenter", workCenter);
		return this.getList("getBigStockKbWC", map);
	}
	
	/**
	 * 调用存储，获取批次
	 */
	@Override
	public String getDistriProductSeqNo(String kbId, String currProcessNo) {
		Map<String,Object> map=new HashMap();
		map.put("kbId", Integer.parseInt(kbId));
		map.put("currProcessNo", Integer.parseInt(currProcessNo));
		return this.sqlSessionTemplate.selectOne("getDistriProductSeqNo",map);	
	}

	/**
	 * 根据单头获取明细
	 */
	@Override
	public List<String> getBigStockKbDetail(String distriPerson) {
		return (List<String>) this.getList("getBigStockKbDetail", distriPerson);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DictVO> queryDistriPersonForSelect(MonKbModel model) throws Exception {
		return (List<DictVO>) this.getList("queryDistriPersonForSelect", model);
	}

	@Override
	public String queryWorkCenterByPlanCode(MonKbModel model) throws Exception {
		return this.sqlSessionTemplate.selectOne("queryWorkCenterByPlanCode", model);
	}

	@Override
	public MonKbModel queryForKbConfig(MonKbModel model) throws Exception {
		return this.sqlSessionTemplate.selectOne("queryForKbConfigForKb", model);
	}
	
	/**
	 * @Description: 获取冲压配送指示   
	 * @param: @param model
	 * @param: @return    
	 * @return: List<MonKbModel>   
	 * @author: dtp
	 * @date: 2019年4月22日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MonKbModel> queryStampKb(MonKbModel model) {
		return this.getListByKey("queryStampKb", model);
	}

	/**
	 * @Description: 获取电池车间指示    
	 * @param: @param model
	 * @param: @return    
	 * @return: List<MonKbModel>   
	 * @author: dtp
	 * @date: 2019年4月22日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MonKbModel> queryBatteryKb(MonKbModel model) {
		return this.getListByKey("queryBatteryKb", model);
	}

	/**
	 * 用户维护的参数值
	 */
	@Override
	public String selectProductSeqNoByParamCode(String productSeqNo) {
		return this.sqlSessionTemplate.selectOne("selectProductSeqNoByParamCode", productSeqNo);
	}

	/**
	 * 通过IP获取车间
	 */
	@Override
	public String selectWorkCenterByIp(String ip) {
		return this.sqlSessionTemplate.selectOne("selectWorkCenterByIp", ip);
	}

	/**
	 * 为空处理
	 */
	@Override
	public List<BigStockKbModel> selectBigStockKbByNull() {
		return (List<BigStockKbModel>) this.getList("selectBigStockKbByNull",null);
	}

	/**
	 * 延迟判定
	 */
	@Override
	public List<String> delayDetermination(String currBatchNo) {
		return (List<String>) this.getList("delayDetermination",currBatchNo);
	}

	/**
	 * 查询看板代码
	 */
	@Override
	public String selectKbCodeByIp(Map<String, String> paramMap) {
		return this.sqlSessionTemplate.selectOne("selectKbCodeByIp", paramMap);
	}

	@Override
	public List<String> getBigStockKbDetail(MonKbModel model) {
		return (List<String>) this.getList("getBigStockKbDetail", model);
	}
	
}
