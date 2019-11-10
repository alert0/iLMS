package com.hanthink.sps.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sps.dao.SpsShelfLabelTmpDao;
import com.hanthink.sps.model.SpsShelfLabelTmpModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: SpsShelfLabelTmpDao
 * @Description: 货架标签打印
 * @author dtp
 * @date 2018年10月31日
 */
@Repository
public class SpsShelfLabelTmpDaoImpl extends MyBatisDaoImpl<String, SpsShelfLabelTmpModel> implements SpsShelfLabelTmpDao{

	@Override
	public String getNamespace() {
		return SpsShelfLabelTmpModel.class.getName();
	}

	/**
	 * @Description: 保存数据到MM_SPS_SHELF_LABLE_IMP表  
	 * @param: @param importList    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月1日
	 */
	@Override
	public void insertSpsShelfLabelTmp(List<SpsShelfLabelTmpModel> importList) {
		this.insertByKey("insertSpsShelfLabelTmp", importList);
	}

	/**
	 * @Description: 调用存储过程校验数据
	 * @param: @param ckParamMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月1日
	 */
	@Override
	public void checkImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne(this.getNamespace()+".checkImportData", ckParamMap);
	}

	/**
	 * @Description: 查询导入数据 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsShelfLabelTmpModel>   
	 * @author: dtp
	 * @date: 2018年11月1日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SpsShelfLabelTmpModel> querySpsShelfLabelPage(SpsShelfLabelTmpModel model, DefaultPage page) {
		return (PageList<SpsShelfLabelTmpModel>) this.getList("querySpsShelfLabelPage", model, page);
	}

	/**
	 * @Description: 获取货架标签打印信息    
	 * @param: @param idArr
	 * @param: @return    
	 * @return: List<SpsShelfLabelTmpModel>   
	 * @author: dtp
	 * @date: 2018年11月8日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsShelfLabelTmpModel> querySpsShelfLabelList(String[] idArr) {
		return (List<SpsShelfLabelTmpModel>) this.getList("querySpsShelfLabelList", idArr);
	}

	/**
	 * @Description: 根据uuid删除导入临时表数据
	 * @param: @param uuid
	 * @param: @return    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月8日
	 */
	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteImportTempDataByUUID", uuid);
	}

	/**
	 * @Description: 货架标签打印导入信息导出
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsShelfLabelTmpModel>   
	 * @author: dtp
	 * @date: 2018年12月26日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsShelfLabelTmpModel> querySpsShelfLabelList(SpsShelfLabelTmpModel model) {
		return (List<SpsShelfLabelTmpModel>) this.getList("querySpsShelfLabelPage", model);
	}

}
