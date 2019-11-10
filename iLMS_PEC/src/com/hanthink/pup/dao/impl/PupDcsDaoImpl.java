package com.hanthink.pup.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.pup.dao.PupDcsDao;
import com.hanthink.pup.model.PupDcsModel;
import com.hanthink.pup.model.PupDcsModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * @Desc    : DCSDAO层处理
 * @CreateOn: 2019年1月4日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
@Repository
public class PupDcsDaoImpl extends MyBatisDaoImpl<String, PupDcsModel> implements PupDcsDao{

    @Override
    public String getNamespace() {
        return PupDcsModel.class.getName();
    }

    @Override
    public PageList<PupDcsModel> queryPupDcsForPage(PupDcsModel model, DefaultPage p) {
        return (PageList<PupDcsModel>) this.getByKey("queryPupDcsForPage", model, p);
    }

    @Override
    public List<PupDcsModel> queryPupDcsByKey(PupDcsModel model) {
        return this.getByKey("queryPupDcsForPage", model);
    }

    @Override
    public String genDcs(Map<String, Object> m) {
        this.sqlSessionTemplate.selectOne(getNamespace() + ".genDcs", m);
        return (String) m.get("outCode");
    }

    @Override
    public void deleteDcsByUUID(String uuid) {
        this.deleteByKey("deleteDcsByUUID", uuid);
    }

    @Override
    public void insertImportTempData(List<PupDcsModelImport> importList) {
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
    public PageList<PupDcsModelImport> queryPupDcsImportTempData(Map<String, String> paramMap, Page page) {
        return (PageList) this.getByKey("queryPupDcsImportTempData", paramMap, page);
    }

    @Override
    public String insertPupDcsImportData(Map<String, Object> paramMap) {
        this.sqlSessionTemplate.selectOne(getNamespace() + ".importGenDcs", paramMap);
        return (String) paramMap.get("outCode");
    }

    @Override
    public List<PupDcsModel> queryDcsForListToPrint(PupDcsModel pupDcsModel) {
        return this.getByKey("queryDcsForListToPrint", pupDcsModel);
    }

    @Override
    public List<PupDcsModel> queryDcsDetailForList(PupDcsModel pupDcsModel) {
        return this.getByKey("queryDcsDetailForList", pupDcsModel);
    }

    @Override
    public List<PupDcsModel> querySealForList(PupDcsModel pupDcsModel) {
        return this.getByKey("querySealForList", pupDcsModel);
    }

    @Override
    public List<PupDcsModel> queryOrderForList(PupDcsModel detailVo) {
        return this.getByKey("queryOrderForList", detailVo);
    }

    @Override
    public void insertSealTemp(List<PupDcsModel> sealList) {
        this.insertBatchByKey("insertSealTemp", sealList);
    }

    @Override
    public void updateSealStatus(List<PupDcsModel> sealList) {
        this.updateByKey("updateSealStatus", sealList);
    }

    @Override
    public void updateDcsPrintStatus(PupDcsModel pupDcsModel) {
        this.updateByKey("updateDcsPrintStatus", pupDcsModel);
    }
    
    @Override
    public void updateDcsExecuteStatus(PupDcsModel pupDcsModel) {
        this.updateByKey("updateDcsExecuteStatus", pupDcsModel);
    }

    @Override
    public String queryPlateNumByPlanSheetNo(PupDcsModel pupDcsModel) {
        return this.sqlSessionTemplate.selectOne(getNamespace()+".queryPlateNumByPlanSheetNo", pupDcsModel);
    }

    @Override
    public void updaetDcsPlateNum(PupDcsModel pupDcsModel) {
        this.updateByKey("updaetDcsPlateNum", pupDcsModel);
    }

    @Override
    public List<PupDcsModel> queryPrintedSeals(PupDcsModel vo) {
        return this.getByKey("queryPrintedSeals", vo);
    }

    @Override
    public List<PupDcsModel> queryPupDcsDetailByKey(PupDcsModel model) {
        return this.getByKey("queryPupDcsDetailForPage", model);
    }

    

}

