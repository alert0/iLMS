package com.hanthink.mp.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.inv.model.InvStockTakModel;
import com.hanthink.mp.dao.ExcepOrderDao;
import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.ExcepOrderModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

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
public class ExcepOrderDaoImpl extends MyBatisDaoImpl<String, ExcepOrderModel> implements ExcepOrderDao{

    @Override
    public String getNamespace() {
        return ExcepOrderModel.class.getName();
    }

    /**
     * 分页查询
     */
    @Override
    public List<ExcepOrderModel> queryExcepOrderForPage(ExcepOrderModel model, DefaultPage p) {
        return this.getByKey("queryExcepOrderForPage", model, p);
    }

    @Override
    public void deleteImportTempDataByUUID(String uuid) {
        this.deleteByKey("deleteExcepImportTempDataByUUID", uuid);
    }

    @Override
    public void insertImportTempData(List<ExcepOrderModelImport> importList) {
        this.insertBatchByKey("insertExcepImportTempData", importList);
    }

    @Override
    public void checkImportData(Map<String, String> ckParamMap) {
        this.sqlSessionTemplate.selectOne("checkImportExcepData", ckParamMap);
    }

    @Override
    public PageList<ExcepOrderModelImport> queryImportTempData(Map<String, String> paramMap, Page page) {
        return (PageList)this.getByKey("queryImportExcepTempData", paramMap, page);
    }

    @Override
    public void insertImportData(Map<String, String> paramMap) {
        this.insertByKey("insertExcepImportData", paramMap);
    }

    @Override
    public void updateImportDataImpStatus(Map<String, String> paramMap) {
        this.updateByKey("updateExcepImportDataImpStatus", paramMap);
    }

    @Override
    public void deleteNotDealData(Map<String, String> paramMap) {
       this.deleteByKey("deleteNotDealData", paramMap);
    }

    @Override
    public String queryIsImportFlag(String uuid) {
        return this.sqlSessionTemplate.selectOne(getNamespace() + ".queryExcepIsImportFlag", uuid);
    }

    /**
     * 查询导出的方法
     */
	@Override
	public List<ExcepOrderModel> queryExcepOrderByKey(ExcepOrderModel model) {
		return this.getByKey("queryExcepOrderForPage", model);
	}

	/**
	 * 导出临时数据信息
	 */
	@Override
	public List<ExcepOrderModelImport> queryExcepOrderModelImportTempDataForExport(Map<String, String> paramMap) {
		return this.sqlSessionTemplate.selectList("queryImportExcepTempData", paramMap);
	}

	/**
	 * 查询可导入的数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExcepOrderModelImport> queryForInsertList(Map<String, String> paramMap) throws Exception {
		return (List<ExcepOrderModelImport>) this.getList("queryForInsertList", paramMap);
	}

}
