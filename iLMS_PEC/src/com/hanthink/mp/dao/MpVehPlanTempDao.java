package com.hanthink.mp.dao;

import java.util.List;

import com.hanthink.mp.model.MpVehPlanTempModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;

/**
 * <pre>
 * 描述：车辆计划 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpVehPlanTempDao extends Dao<String, MpVehPlanTempModel> {

    /**
     * 分页查询车辆计划
     * 
     * @param model
     * @param p
     * @return
     */
    List<MpVehPlanTempModel> queryMpVehPlanTempForPage(MpVehPlanTempModel model, DefaultPage p);

    /**
     * 导出数据的查询方法
     * 
     * @param model
     * @author linzhuo
     * @DATE 2018年9月10日 下午4:18:34
     */
    List<MpVehPlanTempModel> queryMpVehPlanTempByKey(MpVehPlanTempModel model);

    /**
     * 校验批量删除里面是否有已订购数据
     * 
     * @param model
     * @return
     */
    Integer queryMpVehPlanTempCheck(List<String> listIds);

    /**
     * 查询未订购数据的SortId用于记录日志
     * 
     * @param model
     * @return
     */
    List<String> querySortIdAndLogByCalStatus();

    /**
     * 直接删除未订购数据
     * 
     * @param model
     * @return
     */
    void removeAndLogByCalStatus(String factoryCode);

    /**
     * 获取生产计划
     * <p>
     * return: Object
     * </p>
     * <p>
     * Description: MpVehPlanTempDao.java
     * </p>
     * 
     * @author linzhuo
     * @date 2018年9月20日
     * @version 1.0
     * @return
     */
    Integer getVehPlan(String params);

    /**
     * 批量删除车辆计划
     * <p>
     * return: void
     * </p>
     * <p>
     * Description: MpVehPlanTempDao.java
     * </p>
     * 
     * @author linzhuo
     * @date 2018年10月30日
     * @version 1.0
     * @throws Exception
     */
    void deleteByIds(String[] aryIds) throws Exception;

    /**
     * 传入2000工厂每天16点自动确认
     * 
     * @param string
     */
    String autoConfirm(String factoryCode);

    /**
     * 传入2000工厂每天12点自动获取计划
     * 
     * @param string
     */
    void autoGetPlan(String factoryCode);

    /**
     * 记录操作失败原因
     * @param factoryCode
     * @param reason
     * @param opeUser
     */
    void insertOpeLog(String factoryCode, String reason, String opeUser);

    /**
     * 确认导入计划
     * @param curFactoryCode
     */
    String confirmVehPlan(String curFactoryCode);

    /**
     * 查询临时表数据条数
     * @param curFactoryCode
     * @return
     */
    Integer queryVehPlanTemp(String curFactoryCode);

    /**
     * 查询供应商的邮箱
     * @param string
     * @return
     */
	String queryEmail(String string);

	/**
	 * 发送邮件成功
	 * @param factoryCode
	 */
    void deleteAllTempVehPlan(String factoryCode);

}
