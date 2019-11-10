package com.hanthink.pup.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.pup.dao.PupDcsSealDao;
import com.hanthink.pup.model.PupDcsSealModel;
import com.hanthink.pup.model.PupDcsSealModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * @Desc    : DCS封条号DAO层处理
 * @CreateOn: 2019年1月4日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
@Repository
public class PupDcsSealDaoImpl extends MyBatisDaoImpl<String, PupDcsSealModel> implements PupDcsSealDao{

    @Override
    public String getNamespace() {
        return PupDcsSealModel.class.getName();
    }

    @Override
    public PageList<PupDcsSealModel> queryPupDcsSealForPage(PupDcsSealModel model, DefaultPage p) {
        return (PageList<PupDcsSealModel>) this.getByKey("queryPupDcsSealForPage", model, p);
    }

    @Override
    public List<PupDcsSealModel> queryPupDcsSealByKey(PupDcsSealModel model) {
        return this.getByKey("queryPupDcsSealForPage", model);
    }

    @Override
    public void batchDelDcsSeal(String curFactoryCode) {
        this.deleteByKey("batchDelDcsSeal", curFactoryCode);
    }

    @Override
    public void deleteDcsSealByUUID(String uuid) {
        this.deleteByKey("deleteDcsSealByUUID", uuid);
    }

    @Override
    public void insertImportTempData(List<PupDcsSealModelImport> importList) {
        this.insertBatchByKey("insertImportTempData", importList);
    }

    @Override
    public void checkImportData(Map<String, String> ckParamMap) {
        this.sqlSessionTemplate.selectOne(getNamespace() + ".checkImportData", ckParamMap);
    }

    @Override
    public String queryIsImportFlag(String uuid) {
        return this.sqlSessionTemplate.selectOne(getNamespace() + ".queryIsImportFlag", uuid);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public PageList<PupDcsSealModelImport> queryPupDcsSealImportTempData(Map<String, String> paramMap, Page page) {
        return (PageList) this.getByKey("queryPupDcsSealImportTempData", paramMap, page);
    }

    @SuppressWarnings({ "unchecked"})
    @Override
    public List<PupDcsSealModelImport> queryForInsertList(Map<String, Object> paramMap) {
        return (List<PupDcsSealModelImport>) this.getList("queryForInsertList", paramMap);
    }

    @Override
    public void deleteNotUseDcsSeal(Map<String, Object> paramMap) {
        this.deleteByKey("deleteNotUseDcsSeal", paramMap);
    }

    @Override
    public void insertPupDcsSealFromTemp(Map<String, Object> paramMap) {
        this.insertByKey("insertPupDcsSealFromTemp", paramMap);
    }

    @Override
    public void updateImportStatus(Map<String, Object> paramMap) {
        this.updateByKey("updateImportStatus", paramMap);
    }
}

