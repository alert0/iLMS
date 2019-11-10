package com.hanthink.pub.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.model.DictVO;
import com.hanthink.pub.model.PubOrderModel;
import com.hanthink.pub.model.PubOrderModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;

/**
 * <pre>
 * 描述：剩余量主数据 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface PubOrderManager extends Manager<String, PubOrderModel> {

    /**
     * 当物流模式为取货时查询订购基础数据
     * 
     * @param model
     * @param p
     * @return
     */
    PageList<PubOrderModel> querySwPubOrderForPage(PubOrderModel model, DefaultPage p);

    /**
     * 当物流模式为拉动时查询订购基础数据
     * 
     * @param model
     * @param p
     * @return
     */
    PageList<PubOrderModel> queryJitPubOrderForPage(PubOrderModel model, DefaultPage p);

    /**
     * 当物流模式为同步时查询订购基础数据
     * 
     * @param model
     * @param p
     * @return
     */
    PageList<PubOrderModel> queryJisoPubOrderForPage(PubOrderModel model, DefaultPage p);

    /**
     * 当物流模式为空是查询所有数据
     * 
     * @param model
     * @param p
     * @return
     */
    PageList<PubOrderModel> queryAllPubOrderForPage(PubOrderModel model, DefaultPage p);

    /**
     * 导入Excel数据Demo
     * 
     * @param file
     * @param uuid
     * @return
     * @author linzhuo
     * @DATE 2018年9月10日 上午10:27:10
     */
    // Map<String, Object> importPubOrderModel(MultipartFile file, String uuid,String ipAddr, IUser user);

    /**
     * 查询导入的临时数据
     * 
     * @param paramMap
     * @return
     * @author linzhuo
     * @DATE 2018年9月10日 上午11:51:23
     */
    // PageList<PubOrderModel> queryPubOrderImportTempData(Map<String, String> paramMap, Page page);

    /**
     * 将临时表数据写入正式表
     * 
     * @param paramMap
     * @param ipAddr
     * @throws Exception
     */
    void insertPubOrderImportData(Map<String, Object> paramMap, String ipAddr) throws Exception;

    /**
     * 根据UUID删除导入的临时数据
     * 
     * @param uuid
     * @author linzhuo
     * @DATE 2018年9月10日 下午4:17:24
     */
    // void deletePubOrderImportTempDataByUUID(String uuid);

    /**
     * 更新数据并记录日志
     * 
     * @param demoModel
     * @param opeIp
     * @author linzhuo
     * @DATE 2018年9月10日 上午10:54:40
     */
    // void updateAndLog(PubOrderModel PubOrderModel, String opeIp);

    /**
     * 删除数据并记录日志
     * 
     * @param aryIds
     * @param ipAddr
     * @author linzhuo
     * @throws Exception
     * @DATE 2018年9月10日 上午11:00:04
     */
    // void removeAndLogByIds(String[] aryIds, String ipAddr) throws Exception;
    /**
     * 导出数据指定集合
     * 
     * @param model
     * @return
     */
    // List<PubOrderModel> queryPubOrderByKey(PubOrderModel model);

    /**
     * 导出临时数据信息
     * 
     * @param paramMap
     * @author linzhuo
     * @DATE 2018年9月10日 上午11:00:04
     */
    // List<PubOrderModel> queryPubOrderImportTempDataForExport(Map<String, String> paramMap);

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
     * 将EXCEL文件导入临时表数据
     * 
     * @param file
     * @param uuid
     * @param ipAddr
     * @param user
     * @return
     */
    Map<String, Object> importPubOrderModel(MultipartFile file, String uuid, String ipAddr, IUser user);

    /**
     * 查询导入的临时表数据
     * 
     * @param paramMap
     * @param page
     * @return
     */
    PageList<PubOrderModelImport> queryPubOrderPartImportTempData(Map<String, String> paramMap, Page page);

    /**
     * 导出临时表数据
     * 
     * @param paramMap
     * @return
     */
    List<PubOrderModelImport> queryPubOrderImportTempDataForExport(Map<String, String> paramMap);

    /**
     * 查询需要导出的数据
     * 
     * @param model
     * @return
     * @throws Exception
     */
    List<PubOrderModel> queryPubOrderByKey(PubOrderModel model) throws Exception;

    /**
     * 查询卸货口
     * 
     * @return
     */
    List<DictVO> queryUnloadPort();

    /**
     * 批量删除零件订购基础数据
     * 
     * @param aryIds
     * @param ipAddr
     */
    void batchRemovePurOrder(List<PubOrderModel> purOrderList, String ipAddr, IUser user);

    /**
     * 修改零件订购基础数据
     * 
     * @param pubOrderModel
     * @param ipAddr
     * @throws Exception
     */
    void updatePurOrder(PubOrderModelImport pubOrderModelImport, IUser user, String ip) throws Exception;


}
