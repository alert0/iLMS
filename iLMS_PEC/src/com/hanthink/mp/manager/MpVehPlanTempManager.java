package com.hanthink.mp.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hanthink.mp.model.MpVehPlanTempModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.DefaultFile;


/**
 * 
 * <pre> 
 * 描述：车辆计划  处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpVehPlanTempManager extends Manager<String, MpVehPlanTempModel>{

	 /**
     * 分页查询车辆计划
     * @param model
     * @param p
     * @return
     */
    PageList<MpVehPlanTempModel> queryMpVehPlanTempForPage(MpVehPlanTempModel model, DefaultPage p);
	
	/**
	 * 导出车辆计划
	 * @param model
	 * @return
	 */
	List<MpVehPlanTempModel> queryMpVehPlanTempByKey(MpVehPlanTempModel model);

	/**
	 * 传入2000工厂每天12点自动获取计划
	 * @param string
	 */
    void autoGetPlan(String string);

    /**
     * 传入2000工厂每天16点自动确认
     * @param string
     */
    String autoConfirm(String string);

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
	 * 邮件发送
	 * <p>return: String</p>  
	 * <p>Description: MpVehPlanTempManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年12月13日
	 * @version 1.0
	 * @param request 
	 * @param userEmail 
	 * @throws Exception 
	 */
	String sendMail(HttpServletRequest request, String title, String content, DefaultFile sendFile, String userEmail, String factoryCode) throws Exception;
	

}
