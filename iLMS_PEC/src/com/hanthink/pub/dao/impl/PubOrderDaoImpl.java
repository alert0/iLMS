package com.hanthink.pub.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.pub.dao.PubOrderDao;
import com.hanthink.pub.model.PubOrderModel;
import com.hanthink.pub.model.PubOrderModelImport;
import com.hanthink.pup.util.PupUtil;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * <pre>
 * 描述：剩余量主数据 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class PubOrderDaoImpl extends MyBatisDaoImpl<String, PubOrderModel> implements PubOrderDao {

    @Override
    public String getNamespace() {
        return PubOrderModel.class.getName();
    }

    @Override
    public PageList<PubOrderModel> querySwPubOrderForPage(PubOrderModel model, DefaultPage p) {
        return (PageList<PubOrderModel>) this.getByKey("querySwPubOrderForPage", model, p);
    }

    @Override
    public PageList<PubOrderModel> queryJitPubOrderForPage(PubOrderModel model, DefaultPage p) {
        return (PageList<PubOrderModel>) this.getByKey("queryJitPubOrderForPage", model, p);
    }

    @Override
    public PageList<PubOrderModel> queryJisoPubOrderForPage(PubOrderModel model, DefaultPage p) {
        return (PageList<PubOrderModel>) this.getByKey("queryJisoPubOrderForPage", model, p);
    }

    @Override
    public PageList<PubOrderModel> queryAllPubOrderForPage(PubOrderModel model, DefaultPage p) {
        return (PageList<PubOrderModel>) this.getByKey("queryAllPubOrderForPage", model, p);
    }

    /**
     * 写入导入数据到临时表
     * 
     * @param importList
     * @author linzhuo
     * @DATE 2018年9月10日 上午11:11:33
     */
    // @Override
    // public void insertPubOrderImportTempData(List<PubOrderModel> importList) {
    // this.insertBatchByKey("insertPubOrderImportTempData", importList);
    // }

    /**
     * 检查导入的临时数据信息
     * 
     * @param ckParamMap
     * @author linzhuo
     * @DATE 2018年9月10日 上午11:12:37
     */
    // @Override
    // public void checkPubOrderImportData(Map<String, String> ckParamMap) {
    // this.sqlSessionTemplate.selectOne("checkPubOrderImportData", ckParamMap);
    // }

    /**
     * 查询导入的临时数据信息
     * 
     * @param params
     * @param page
     * @return
     * @author linzhuo
     * @DATE 2018年9月10日 下午12:30:46
     */
    // @SuppressWarnings({ "rawtypes", "unchecked" })
    // @Override
    // public PageList<PubOrderModel> queryPubOrderImportTempData(Map<String, String> params,Page page) {
    // return (PageList)this.getByKey("queryPubOrderImportTempData", params,page);
    // }

    /**
     * 临时表数据信息写入到正式表
     * 
     * @param paramMap
     * @author linzhuo
     * @DATE 2018年9月10日 下午12:31:10
     */
    // @Override
    // public void insertPubOrderImportData(Map<String, Object> paramMap) {
    // this.insertByKey("insertPubOrderImportData", paramMap);
    // }

    // @Override
    // public void deletePubOrderImportTempDataByUUID(String uuid) {
    // this.deleteByKey("deletePubOrderImportTempDataByUUID", uuid);
    // }

    /**
     * 导出数据查询方法
     * 
     * @param model
     * @author linzhuo
     * @DATE 2018年9月10日 下午4:19:09
     */
    // @Override
    // public List<PubOrderModel> queryPubOrderByKey(PubOrderModel model) {
    // return this.getByKey("queryPubOrderForPage", model);
    // }

    @Override
    public List<String> querySwUpdateData(Map<String, Object> paramMap) {
        // 查询
        return this.sqlSessionTemplate.selectList(getNamespace() + ".querySwUpdateData", paramMap);
    }

    /**
     * 更新导入的方法
     * 
     * @param model
     * @author linzhuo
     * @DATE 2018年9月10日 下午4:19:09
     */
    // @Override
    // public void updatePubOrderImportData(Map<String, Object> paramMap) {
    // this.updateByKey("updatePubOrderImportData", paramMap);
    // }

    /**
     * 查询是否可以批量导入
     * 
     * @param model
     * @author linzhuo
     * @DATE 2018年9月10日 下午4:19:09
     */
    // @Override
    // public String queryPubOrderIsImportFlag(String uuid) {
    // return this.sqlSessionTemplate.selectOne(getNamespace() + ".queryPubOrderIsImportFlag", uuid);
    // }

    /**
     * 导出临时数据信息
     * 
     * @param paramMap
     * @author linzhuo
     * @DATE 2018年9月10日 下午4:19:09
     */
    // @Override
    // public List<PubOrderModel> queryPubOrderImportTempDataForExport(Map<String, String> paramMap) {
    // return this.sqlSessionTemplate.selectList("queryPubOrderImportTempData", paramMap);
    // }

    /**
     * 查询可导入的数据
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PubOrderModel> queryForInsertList(Map<String, Object> paramMap) {
        return (List<PubOrderModel>) this.getList("queryForInsertList", paramMap);
    }

    /**
     * 批量删除数据
     */
    // @Override
    // public void deleteByIds(String[] aryIds) throws Exception{
    // this.deleteByKey("deleteByIds", aryIds);
    // }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<DictVO> queryPlanCode() {
        Map<String, Object> map = new HashMap();
        return this.getList("queryPlanCode", map);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<DictVO> queryPlanCode1() {
        Map<String, Object> map = new HashMap();
        return this.getList("queryPlanCode1", map);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<DictVO> queryArriveDepot() {
        Map<String, Object> map = new HashMap();
        return this.getList("queryArriveDepot", map);
    }

    @Override
    public void deletePubOrderImportTempDataByUUID(String uuid) {
        this.deleteByKey("deletePubOrderImportTempDataByUUID", uuid);
    }

    @Override
    public void insertImportTempData(List<PubOrderModelImport> importList) {
        this.insertBatchByKey("insertImportTempData", importList);
    }

    @Override
    public void insertSimgleImportTempData(PubOrderModelImport pubOrderModelImport) {
        this.insertByKey("insertSimgleImportTempData", pubOrderModelImport);
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
    public PageList<PubOrderModelImport> queryPubOrderPartImportTempData(Map<String, String> paramMap, Page page) {
        return (PageList) this.getByKey("queryPubOrderPartImportTempData", paramMap, page);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PubOrderModelImport> queryPubOrderImportTempDataForExport(Map<String, String> paramMap) {
        return (List<PubOrderModelImport>) this.getList("queryPubOrderPartImportTempData", paramMap);
    }

    @Override
    public List<PubOrderModel> queryPubOrderByKey(PubOrderModel model) throws Exception {
        List<PubOrderModel> list;
        if (!PupUtil.isAllFieldNull(model)) {
            list = this.getByKey("queryAllPubOrderForExport", model);
        } else {
            /**
             * 没有数据返回空行
             */
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public void importSwPurOrderImportData(Map<String, Object> paramMap) {
        this.updateByKey("importSwPurOrderImportData", paramMap);
    }

    @Override
    public void updateSwPurOrder(Map<String, Object> paramMap) {
        this.updateByKey("updateSwPurOrder", paramMap);
    }

    @Override
    public void updatePubOrderImportStatus(Map<String, Object> paramMap) {
        this.updateByKey("updatePubOrderImportStatus", paramMap);
    }

    @Override
    public void updatePartInfo(Map<String, Object> paramMap) {
        this.updateByKey("updatePartInfo", paramMap);
    }

    @Override
    public List<PubOrderModel> queryPartUpdateData(Map<String, Object> paramMap) {
        // 查询
        return this.sqlSessionTemplate.selectList(getNamespace() + ".queryPartUpdateData", paramMap);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<DictVO> queryUnloadPort() {
        Map<String, Object> map = new HashMap();
        return this.getList("queryUnloadPort", map);
    }

    @Override
    public List<String> queryJitUpdateData(Map<String, Object> paramMap) {
        // 查询
        return this.sqlSessionTemplate.selectList(getNamespace() + ".queryJitUpdateData", paramMap);
    }

    @Override
    public void importJitPurOrderImportData(Map<String, Object> paramMap) {
        this.updateByKey("importJitPurOrderImportData", paramMap);
    }

    @Override
    public void importJitOrderPurOrderImportData(Map<String, Object> paramMap) {
        this.updateByKey("importJitOrderSupPurOrderImportData", paramMap);
        this.updateByKey("importJitOrderWxPurOrderImportData", paramMap);
    }

    @Override
    public List<String> queryJitOrderUpdateData(Map<String, Object> paramMap) {
        // 查询
        return this.sqlSessionTemplate.selectList(getNamespace() + ".queryJitOrderUpdateData", paramMap);
    }

    @Override
    public List<PubOrderModel> queryUnloadRelationUpdateData(Map<String, Object> paramMap) {
        // 查询
        return this.sqlSessionTemplate.selectList(getNamespace() + ".queryUnloadRelationUpdateData", paramMap);
    }

    @Override
    public void mergeUnloadRelationToPartNo(Map<String, Object> paramMap) {
        this.updateByKey("mergeUnloadRelationToPartNo", paramMap);
    }

    @Override
    public List<String> queryJisoUpdateData(Map<String, Object> paramMap) {
        // 查询
        return this.sqlSessionTemplate.selectList(getNamespace() + ".queryJisoUpdateData", paramMap);
    }

    @Override
    public List<String> queryJisoPartGroupUpdateData(Map<String, Object> paramMap) {
        // 查询
        return this.sqlSessionTemplate.selectList(getNamespace() + ".queryJisoPartGroupUpdateData", paramMap);
    }

    @Override
    public List<String> queryJisoPartGroupRouteUpdateData(Map<String, Object> paramMap) {
        // 查询
        return this.sqlSessionTemplate.selectList(getNamespace() + ".queryJisoPartGroupRouteUpdateData", paramMap);
    }

    @Override
    public void importJisoPurOrderImportData(Map<String, Object> paramMap) {
        this.updateByKey("importJisoPurOrderImportData", paramMap);
    }

    @Override
    public void importJisoPartGroupPurOrderImportData(Map<String, Object> paramMap) {
        this.updateByKey("importJisoPartGroupPurOrderImportData", paramMap);
    }

    @Override
    public void importJisoPartGroupRoutePurOrderImportData(Map<String, Object> paramMap) {
        this.updateByKey("importJisoPartGroupRoutePurOrderImportData", paramMap);
    }

    @Override
    public List<PubOrderModel> queryAllJisoData(Map<String, Object> paramMap, boolean b) {
        if (b) {
            // 查询
            return this.getByKey("queryExistsJisoPartGroupUpdateData", paramMap);
        } else {
            return this.getByKey("queryNotExistsJisoPartGroupUpdateData", paramMap);
        }

    }

    @Override
    public String queryJisoPartGroupIdFromSeq() {
        // 查询
        return this.sqlSessionTemplate.selectOne(getNamespace() + ".queryJisoPartGroupIdFromSeq");
    }

    @Override
    public void insertJisoPartGroupData(Map<String, Object> paramMap) {
        this.insertByKey("insertJisoPartGroupData", paramMap);
    }

    @Override
    public void insertJisoPartData(Map<String, Object> paramMap) {
        this.insertByKey("insertJisoPartData", paramMap);
    }

    @Override
    public void insertJisoPartGroupRouteData(Map<String, Object> paramMap) {
        this.insertByKey("insertJisoPartGroupRouteData", paramMap);
    }

    @Override
    public void batchRemovePurOrder(String[] arrayIds, String logisticsModel) {
        if ("SW".equals(logisticsModel)) {
            // 如果为循环取货
            this.deleteByKey("batchRemoveSwPurOrder", arrayIds);
        } else if ("JIT".equals(logisticsModel)) {
            // 如果为厂外拉动
            this.deleteByKey("batchRemoveJitPurOrder", arrayIds);
        } else if ("JISO".equals(logisticsModel)) {
            // 如果为厂外同步
            this.deleteByKey("batchRemoveJisoPurOrder", arrayIds);
        }
    }

    @Override
    public String queryErrorInfoByUUID(String uuid) {
        // 查询
        return this.sqlSessionTemplate.selectOne(getNamespace() + ".queryErrorInfoByUUID", uuid);
    }

    @Override
    public List<String> queryNotExistsPartUdaIdList(String curFactoryCode) {
        return this.sqlSessionTemplate.selectList(getNamespace() + ".queryNotExistsPartUdaIdList", curFactoryCode);
    }

    @Override
    public void deleteNotExistsPartUdaData(Map<String, Object> map) {
        this.updateByKey("deleteNotExistsPartUdaData", map);
    }

    @Override
    public List<String> queryNotExistsPartUnloadIdList(String curFactoryCode) {
        return this.sqlSessionTemplate.selectList(getNamespace() + ".queryNotExistsPartUnloadIdList", curFactoryCode);
    }

    @Override
    public void deleteNotExistsPartUnloadData(Map<String, Object> map) {
        this.deleteByKey("deleteNotExistsPartUnloadData", map);
    }

    @Override
    public List<String> queryNotExistsPartJitOrderConfigIdList(String curFactoryCode) {
        return this.sqlSessionTemplate.selectList(getNamespace() + ".queryNotExistsPartJitOrderConfigIdList", curFactoryCode);
    }

    @Override
    public void deleteNotExistsPartJitOrderConfigData(Map<String, Object> map) {
        this.deleteByKey("deleteNotExistsPartJitOrderConfigData", map);
    }

    @Override
    public List<String> queryNotExistsPartJisoPartGroupIdList(String factoryCode) {
        return this.sqlSessionTemplate.selectList(getNamespace() + ".queryNotExistsPartJisoPartGroupIdList", factoryCode);
    }

    @Override
    public void deleteNotExistsPartJisoPartGroupData(Map<String, Object> map) {
        this.deleteByKey("deleteNotExistsPartJisoPartGroupData", map);
    }

    @Override
    public List<String> queryNotExistsPartJisoPartGroupRouteIdList(String factoryCode) {
        return this.sqlSessionTemplate.selectList(getNamespace() + ".queryNotExistsPartJisoPartGroupRouteIdList", factoryCode);
    }

    @Override
    public void deleteNotExistsPartJisoPartGroupRouteData(Map<String, Object> map) {
        this.deleteByKey("deleteNotExistsPartJisoPartGroupRouteData", map);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PubOrderModelImport> queryMList(Map<String, Object> paramMap) {
        return this.getList("queryMList", paramMap);
    }

    @Override
    public void deleteJitPartInfo(PubOrderModelImport m) {
        this.deleteByKey("deleteJitPartInfo", m);
    }

    @Override
    public void deleteJisoPartInfo(PubOrderModelImport m) {
        this.deleteByKey("deleteJisoPartInfo", m);
    }

    @Override
    public void deleteSwPartInfo(PubOrderModelImport m) {
        this.deleteByKey("deleteSwPartInfo", m);
    }

}
