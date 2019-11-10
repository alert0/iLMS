package com.hanthink.mon.dao.impl;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.mon.dao.MonAllowDeviationDao;
import com.hanthink.mon.model.MonAllowDeviationModel;
import com.hanthink.mon.model.MonAllowDeviationModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: DeliveryOrderDaoImpl
 * @Description: 取货单查询
 * @author Midnight
 * @date 2018年11月03日
 */
@Repository
public class MonAllowDeviationDaoImpl extends MyBatisDaoImpl<String, MonAllowDeviationModel> implements MonAllowDeviationDao {

	   @Override
	    public String getNamespace() {
	        return MonAllowDeviationModel.class.getName();
	    }
		
	    /**
	   	 * 查询允许误差
	   	 * @param model
	   	 * @author Midnight
	   	 * @DATE	2018年11月09日 
	   	 */
		@Override
		public PageList<MonAllowDeviationModel> queryAllowDeviationPage(MonAllowDeviationModel model, DefaultPage p) {
			 return (PageList<MonAllowDeviationModel>)this.getByKey("queryAllowDeviationPage", model, p);
		}

	    /**
	     * 批量删除数据
	     */
		@Override
		public void deleteByIds(String[] aryIds) throws Exception{
			this.deleteByKey("deleteByIds", aryIds);
		}
		
	    /**
	   	 * 查询允许误差导出数据
	   	 * @param model
	   	 * @author Midnight
	   	 * @DATE	2018年11月09日 
	   	 */
		@Override
		public List<MonAllowDeviationModel> queryAllowDeviationForExport(MonAllowDeviationModel model) {
			 return this.getByKey("queryAllowDeviationPage", model);
		}

		/**
		 * 根据UUID删除导入的临时数据
		 * @param uuid
	   	 * @author Midnight
	   	 * @DATE	2018年11月09日 
		 */
		@Override
		public void deleteAllowDeviationImportTempDataByUUID(String uuid) {
			this.deleteByKey("deleteAllowDeviationImportTempDataByUUID", uuid);
		}

		/**
		 * 导入数据到临时表
		 * @param uuid
	   	 * @author Midnight
	   	 * @DATE	2018年11月11日 
		 */
		@Override
		public void insertMonAllowDeviationImportTempData(List<MonAllowDeviationModelImport> importList) {
			this.insertBatchByKey("insertAllowDeviationImportTempData", importList);
			
		}

		/**
		 * 检查导入数据
		 * @param uuid
	   	 * @author Midnight
	   	 * @DATE	2018年11月11日 
		 */
		@Override
		public void checkMonAllowDeviationImportData(Map<String, String> ckParamMap) {
			this.sqlSessionTemplate.selectOne("checkMonAllowDeviationImportData", ckParamMap);
		}

		/**
		 * 檢查是否可以批量导入
		 * @param uuid
	   	 * @author Midnight
	   	 * @DATE	2018年11月11日 
		 */
		@Override
		public String queryMonAllowDeviationImportFlag(String uuid) {
			return this.sqlSessionTemplate.selectOne(getNamespace() + ".queryMonAllowDeviationImportFlag", uuid);
		}


		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public PageList<MonAllowDeviationModelImport> queryMonAllowDeviationImportTempData(Map<String, String> paramMap,
				Page page) {
			return (PageList)this.getByKey("queryMonAllowDeviationImportTempData", paramMap,page);
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<MonAllowDeviationModelImport> queryForInsertList(Map<String, Object> paramMap) {
			return (List<MonAllowDeviationModelImport>) this.getList("queryForInsertList", paramMap);
		}

		@Override
		public List<String> queryUpdateDataFromMonAllowDeviationImp(Map<String, Object> paramMap) {
			return this.sqlSessionTemplate.selectList(getNamespace()+".queryUpdateDataFromMonAllowDeviationImp", paramMap);
		}

		@Override
		public void updateMonAllowDeviationImportData(Map<String, Object> paramMap) {
			this.updateByKey("updateMonAllowDeviationImportData", paramMap);
		}

		@Override
		public void insertMonAllowDeviationImportData(Map<String, Object> paramMap) {
			this.insertByKey("insertMonAllowDeviationImportData", paramMap);
		}

		@Override
		public void updateMonAllowDeviationImportDataImpStatus(Map<String, Object> paramMap) {
			this.updateByKey("updateMonAllowDeviationImportDataImpStatus", paramMap);
			
		}

		@Override
		public List<MonAllowDeviationModelImport> queryAllowDeviationImportTempDataForExport(
				Map<String, String> paramMap) {
			return this.sqlSessionTemplate.selectList("queryMonAllowDeviationImportTempData", paramMap);
		}

		/**
		 * @Description: 判断集货路线是否存在 
		 * @param: @param model
		 * @param: @return    
		 * @return: List<MonAllowDeviationModel>   
		 * @author: dtp
		 * @date: 2018年11月27日
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List<MonAllowDeviationModel> queryIsExist(MonAllowDeviationModel model) {
			return (List<MonAllowDeviationModel>) this.getList("queryIsExist", model);
		}

}
