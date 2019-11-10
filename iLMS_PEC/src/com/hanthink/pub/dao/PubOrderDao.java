package com.hanthink.pub.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.pub.model.PubOrderModel;
import com.hanthink.pub.model.PubOrderModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * <pre>
 * 描述：剩余量主数据 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface PubOrderDao extends Dao<String, PubOrderModel> {

    /**
     * 当物流模式为取货时查询订购基础数据
     * 
     * @param model
     * @param p
     * @return
     */
    PageList<PubOrderModel> querySwPubOrderForPage(PubOrderModel model, DefaultPage p);

    PageList<PubOrderModel> queryJitPubOrderForPage(PubOrderModel model, DefaultPage p);

    PageList<PubOrderModel> queryJisoPubOrderForPage(PubOrderModel model, DefaultPage p);

    PageList<PubOrderModel> queryAllPubOrderForPage(PubOrderModel model, DefaultPage p);

    /**
     * 导入数据写入到临时表
     * 
     * @param importList
     * @author linzhuo
     * @DATE 2018年9月10日 上午11:08:11
     */
    // void insertPubOrderImportTempData(List<PubOrderModel> importList);

    /**
     * 检查导入到临时表数据准确性
     * 
     * @param ckParamMap
     * @author linzhuo
     * @DATE 2018年9月10日 上午11:08:35
     */
    // void checkPubOrderImportData(Map<String, String> ckParamMap);

    /**
     * 查询导入的临时数据信息
     * 
     * @param paramMap
     * @return
     * @author linzhuo
     * @DATE 2018年9月10日 下午12:29:41
     */
    // PageList<PubOrderModel> queryPubOrderImportTempData(Map<String, String> paramMap, Page page);

    /**
     * 确定导入，将导入的临时数据写入到正式表
     * 
     * @param paramMap
     * @author linzhuo
     * @DATE 2018年9月10日 下午12:30:07
     */
    // void insertPubOrderImportData(Map<String, Object> paramMap);

    /**
     * 根据导入的UUID删除临时数据
     * 
     * @param uuid
     * @author linzhuo
     * @DATE 2018年9月10日 下午4:18:34
     */
    // void deletePubOrderImportTempDataByUUID(String uuid);

    /**
     * 导出数据的查询方法
     * 
     * @param model
     * @author linzhuo
     * @DATE 2018年9月10日 下午4:18:34
     */
    // List<PubOrderModel> queryPubOrderByKey(PubOrderModel model);

    /**
     * 拿出临时表中的ID,根据ID查询出哪些数据要导入
     * 
     * @param model
     * @author linzhuo
     * @DATE 2018年9月10日 下午4:18:34
     */
    // List<String> queryUpdateDataFromPubOrderImp(Map<String, Object> paramMap);

    /**
     * 查询是否可以批量导入
     * 
     * @param uuid
     * @return
     */
    // String queryPubOrderIsImportFlag(String uuid);

    /**
     * 临时数据导出
     * 
     * @param uuid
     * @return
     */
    // List<PubOrderModel> queryPubOrderImportTempDataForExport(Map<String, String> paramMap);

    /**
     * 查询导入的数据
     * 
     * @param paramMap
     * @return
     */
    List<PubOrderModel> queryForInsertList(Map<String, Object> paramMap);

    /**
     * 批量删除数据
     * <p>
     * return: void
     * </p>
     * <p>
     * Description: PubOrderDao.java
     * </p>
     * 
     * @author linzhuo
     * @date 2018年10月30日
     * @version 1.0
     * @throws Exception
     */
    // void deleteByIds(String[] aryIds) throws Exception;

    /**
     * 查询信息点下拉框数据
     * 
     * @return
     */
    List<DictVO> queryPlanCode();
    
    List<DictVO> queryPlanCode1();

    /**
     * 查询到货仓库下拉框的数据
     * 
     * @return
     */
    List<DictVO> queryArriveDepot();

    /**
     * 根据UUID删除临时表数据
     * 
     * @param uuid
     */
    void deletePubOrderImportTempDataByUUID(String uuid);

    /**
     * 将EXCEL数据导入临时表
     * 
     * @param importList
     */
    void insertImportTempData(List<PubOrderModelImport> importList);

    /**
     * 调用存储校验导入数据
     * 
     * @param ckParamMap
     */
    void checkImportData(Map<String, String> ckParamMap);

    /**
     * 查询是否可以导入
     * 
     * @param uuid
     * @return
     */
    String queryIsImportFlag(String uuid);

    /**
     * 查询导入临时表数据
     * 
     * @param paramMap
     * @return
     */
    PageList<PubOrderModelImport> queryPubOrderPartImportTempData(Map<String, String> paramMap, Page page);

    /**
     * 查询导出临时表数据
     * 
     * @param paramMap
     * @return
     */
    List<PubOrderModelImport> queryPubOrderImportTempDataForExport(Map<String, String> paramMap);

    /**
     * 查询导出的数据
     * 
     * @param model
     * @return
     * @throws Exception
     */
    List<PubOrderModel> queryPubOrderByKey(PubOrderModel model) throws Exception;

    /**
     * 查询导入修改的数据
     * 
     * @param paramMap
     * @return
     */
    List<String> querySwUpdateData(Map<String, Object> paramMap);

    /**
     * 写入循环取货零件基础信息数据,存在更新,不存在新增
     * 
     * @param paramMap
     */
    void importSwPurOrderImportData(Map<String, Object> paramMap);

    /**
     * 更新该计算队列对应的到货仓库和车间
     * 
     * @param paramMap
     */
    void updateSwPurOrder(Map<String, Object> paramMap);

    /**
     * 更新导入状态
     * 
     * @param paramMap
     */
    void updatePubOrderImportStatus(Map<String, Object> paramMap);

    /**
     * 更新零件基础信息
     * 
     * @param paramMap
     */
    void updatePartInfo(Map<String, Object> paramMap);

    /**
     * 查询需要更新零件基础数据
     * 
     * @param paramMap
     * @return
     */
    List<PubOrderModel> queryPartUpdateData(Map<String, Object> paramMap);

    /**
     * 获取卸货口下拉框数据
     * 
     * @return
     */
    List<DictVO> queryUnloadPort();

    /**
     * 查询导入的拉动零件更新的基础数据
     * 
     * @param paramMap
     * @return
     */
    List<String> queryJitUpdateData(Map<String, Object> paramMap);

    /**
     * 写入厂外拉动零件基础信息数据,存在更新,不存在新增
     * 
     * @param paramMap
     */
    void importJitPurOrderImportData(Map<String, Object> paramMap);

    /**
     * 写入厂外拉动零件组单基础信息数据,存在更新,不存在新增
     * 
     * @param paramMap
     */
    void importJitOrderPurOrderImportData(Map<String, Object> paramMap);

    /**
     * 查询导入的拉动零件更新拉动组单配置的基础数据
     * 
     * @param paramMap
     * @return
     */
    List<String> queryJitOrderUpdateData(Map<String, Object> paramMap);

    /**
     * 查询需要跟新的零件、物流模式和卸货口的关系
     * 
     * @param paramMap
     * @return
     */
    List<PubOrderModel> queryUnloadRelationUpdateData(Map<String, Object> paramMap);

    /**
     * 记录零件物流模式和卸货口的关系
     * 
     * @param paramMap
     */
    void mergeUnloadRelationToPartNo(Map<String, Object> paramMap);

    /**
     * 查询导入的同步零件更新的基础数据
     * 
     * @param paramMap
     * @return
     */
    List<String> queryJisoUpdateData(Map<String, Object> paramMap);

    /**
     * 查询导入的同步零件组更新的基础数据
     * 
     * @param paramMap
     * @return
     */
    List<String> queryJisoPartGroupUpdateData(Map<String, Object> paramMap);

    /**
     * 查询导入的同步零件组路线需要更新的基础数据
     * 
     * @param paramMap
     * @return
     */
    List<String> queryJisoPartGroupRouteUpdateData(Map<String, Object> paramMap);

    /**
     * 写入厂外同步零件零件基础信息数据,存在更新,不存在新增
     * 
     * @param paramMap
     */
    void importJisoPurOrderImportData(Map<String, Object> paramMap);

    /**
     * 写入厂外同步零件组基础信息数据,存在更新,不存在新增
     * 
     * @param paramMap
     */
    void importJisoPartGroupPurOrderImportData(Map<String, Object> paramMap);

    /**
     * 写入厂外同步零件组路线基础信息数据,存在更新,不存在新增
     * 
     * @param paramMap
     */
    void importJisoPartGroupRoutePurOrderImportData(Map<String, Object> paramMap);

    /**
     * 获取所有的同步校验通过的数据
     * 
     * @param paramMap
     * @return
     */
    List<PubOrderModel> queryAllJisoData(Map<String, Object> paramMap, boolean b);

    /**
     * 获取同步零件组序列号
     * 
     * @return
     */
    String queryJisoPartGroupIdFromSeq();

    /**
     * 当零件不存在的情况,新增零件组数据
     * 
     * @param paramMap
     */
    void insertJisoPartGroupData(Map<String, Object> paramMap);

    /**
     * 当零件不存在的情况,新增零件数据
     * 
     * @param paramMap
     */
    void insertJisoPartData(Map<String, Object> paramMap);

    /**
     * 当零件不存在的情况,新增零件组路线数据
     * 
     * @param paramMap
     */
    void insertJisoPartGroupRouteData(Map<String, Object> paramMap);

    /**
     * 根据物流模式批量删除零件订购基础数据
     * 
     * @param purOrderList
     * @param string
     */
    void batchRemovePurOrder(String[] array, String string);

    /**
     * 查询错误信息
     * 
     * @param uuid
     * @return
     */
    String queryErrorInfoByUUID(String uuid);

    /**
     * 导入单条数据方法
     * 
     * @param pubOrderModelImport
     */
    void insertSimgleImportTempData(PubOrderModelImport pubOrderModelImport);

    /**
     * 查询所有的不存在于零件表的UDA数据
     * 
     * @param curFactoryCode
     * @return
     */
    List<String> queryNotExistsPartUdaIdList(String curFactoryCode);

    /**
     * 删除所有存在于零件表的UDA数据
     */
    void deleteNotExistsPartUdaData(Map<String, Object> map);

    /**
     * 查询所有不存在于零件表的UNLOAD数据
     * 
     * @param curFactoryCode
     * @return
     */
    List<String> queryNotExistsPartUnloadIdList(String curFactoryCode);

    /**
     * 删除所有存在于零件表的UNLOAD数据
     */
    void deleteNotExistsPartUnloadData(Map<String, Object> map);

    /**
     * 查询不存在于拉动零件信息的拉动JIT_ORDER_CONFIG数据
     * 
     * @param curFactoryCode
     * @return
     */
    List<String> queryNotExistsPartJitOrderConfigIdList(String curFactoryCode);

    /**
     * 删除不存在于拉动零件表的JIT_ORDER_CONFIG数据
     * 
     * @param map
     */
    void deleteNotExistsPartJitOrderConfigData(Map<String, Object> map);

    /**
     * 查询不存在于同步零件信息的同步MM_JISO_PARTGROUP数据
     * 
     * @param curFactoryCode
     * @return
     */
    List<String> queryNotExistsPartJisoPartGroupIdList(String factoryCode);

    /**
     * 删除不存在于同步零件信息的同步MM_JISO_PARTGROUP数据
     * 
     * @param map
     */
    void deleteNotExistsPartJisoPartGroupData(Map<String, Object> map);

    /**
     * 查询不存在于同步零件组信息的同步MM_JISO_PARTGROUP_ROUTE数据
     * 
     * @param curFactoryCode
     * @return
     */
    List<String> queryNotExistsPartJisoPartGroupRouteIdList(String factoryCode);

    /**
     * 删除不存在于同步零件组信息的同步MM_JISO_PARTGROUP_ROUTE数据
     * 
     * @param map
     */
    void deleteNotExistsPartJisoPartGroupRouteData(Map<String, Object> map);

    /**
     * 查询物流模式更新的数据
     * 
     * @param paramMap
     * @return
     */
    List<PubOrderModelImport> queryMList(Map<String, Object> paramMap);

    /**
     * 根据车间和零件号删除JIT零件基础数据
     * 
     * @param m
     */
    void deleteJitPartInfo(PubOrderModelImport m);

    /**
     * 根据车间和零件号删除JISO零件基础数据
     * 
     * @param m
     */
    void deleteJisoPartInfo(PubOrderModelImport m);

    /**
     * 根据车间和零件号删除SW零件基础数据
     * 
     * @param m
     */
    void deleteSwPartInfo(PubOrderModelImport m);


}
