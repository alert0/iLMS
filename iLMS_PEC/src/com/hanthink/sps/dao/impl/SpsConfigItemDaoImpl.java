package com.hanthink.sps.dao.impl;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sps.dao.SpsConfigItemDao;
import com.hanthink.sps.model.SpsConfigDetailModel;
import com.hanthink.sps.model.SpsConfigItemModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;



@Repository
public class SpsConfigItemDaoImpl extends MyBatisDaoImpl<String, SpsConfigItemModel> implements SpsConfigItemDao{

	@Override
	public String getNamespace() {
		return SpsConfigItemModel.class.getName();
	}

	/**
	 * 配置项代码数据分页查询
	 * @param model
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SpsConfigItemModel> querySpsConfigItemPage(SpsConfigItemModel model, DefaultPage page) {
		return (PageList<SpsConfigItemModel>) this.getList("querySpsConfigItemPage", model,page);
	}
	/**
	 * 配置项代码更新(失效)
	 * @param model
	 * @return
	 */
	@Override
	public int updateConfigItem(SpsConfigItemModel model) {
		return this.updateByKey("updateConfigItem", model);
	}
	/**
	 * 配置项代码批量删除
	 * @param arrayList
	 */
	@Override
	public void deleteConfigItemByBatch(ArrayList<SpsConfigItemModel> arrayList) {
		this.deleteByKey("deleteConfigItemByBatch", arrayList);
	}
	/**
	 * 配置项代码导出数据查询
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsConfigItemModel> querySpsConfigItemList(SpsConfigItemModel model) {
		return (List<SpsConfigItemModel>) this.getList("querySpsConfigItemPage", model);
	}
	/**
	 * 查询配置项代码是否存在
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsConfigItemModel> queryConfigCode(SpsConfigItemModel model) {
		return (List<SpsConfigItemModel>) this.getList("querySpsConfigCodeisUnquie", model);
	}
	/**
	 * 配置项代码新增和修改
	 * @param model
	 * @return
	 */
	@Override
	public int insertConfigItem(SpsConfigItemModel model) {
		return this.insertByKey("insertConfigItem", model);
	}
	/**
	 * 配置项代码导入临时数据
	 * @param importList
	 */
	@Override
	public void insertSpsConfigItemTempData(List<SpsConfigItemModel> importList) {
		this.insertBatchByKey("insertSpsConfigItemTempData", importList);
		
	}
	/**
	 * 配置项代码导入数据查询
	 * @param ckParamMap
	 */
	@Override
	public void checkImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne(this.getNamespace()+".checkImportData", ckParamMap);
	}
	/**
	 * 配置项代码导入提交
	 * @param ckParamMap
	 */
	@Override
	public void spsConfigItemImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne(this.getNamespace()+".spsConfigItemImportData", ckParamMap);
	}

	/**
	 * 导入临时数据查询
	 * @param model
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SpsConfigItemModel> querySpsConfigTemp(SpsConfigItemModel model, DefaultPage page) {
		return (PageList<SpsConfigItemModel>) this.getList("querySpsConfigTemp", model,page);
	}
	
	/**
	 * 检验临时数据是否存在满足导入条件却未导入的
	 * @param model
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsConfigItemModel> querySpsConfigNotImport(SpsConfigItemModel model) {
		return (List<SpsConfigItemModel>) this.getList("querySpsConfigNotImport", model);
	}
	/**
	 * 配置项代码导入临时数据查询导出
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsConfigItemModel> querySpsConfigTempForExport(
			SpsConfigItemModel model) {
		return (List<SpsConfigItemModel>) this.getList("querySpsConfigTemp", model);
	}

	@Override
	public int removeSpsConfigItemTemp() {
		int deleteByKey = this.deleteByKey("removeSpsConfigItemTemp");
		return deleteByKey;
	}

	/**
	 * @Description: SPS配置向导入临时表
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月27日
	 */
	@Override
	public void insertImportData(Map<String, String> paramMap) {
		this.updateByKey("insertImportData", paramMap);
	}

	/**
	 * @Description: SPS配置向导入临时表
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月18日
	 */
	@Override
	public void updateImportStatus(Map<String, String> paramMap) {
		this.updateByKey("updateImportStatus", paramMap);
	}

	/**
	 * @Description: 通过uuid删除临时表数据
	 * @param: @param uuid    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月27日
	 */
	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteImportTempDataByUUID", uuid);
	}

	/**
	 * @Description:  判断配置项代码是否有配置明细 
	 * @param: @param arrayList
	 * @param: @return    
	 * @return: List<SpsConfigDetailModel>   
	 * @author: dtp
	 * @date: 2018年12月27日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsConfigDetailModel> querySpsConfigDetailList(ArrayList<SpsConfigItemModel> arrayList) {
		return (List<SpsConfigDetailModel>) this.getList("querySpsConfigDetailList", arrayList);
	}

	/**
	 * @Description: 查询导入校验结果是否包含不通过 
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2018年12月30日
	 */
	@Override
	public int queryIsExistsCheckResultFalse(String uuid) {
		return  Integer.parseInt(this.getOne("queryIsExistsCheckResultFalse", uuid).toString());
	}

	

}
