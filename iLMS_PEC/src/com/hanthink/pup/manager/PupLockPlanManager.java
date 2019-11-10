package com.hanthink.pup.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.pup.model.PupLockPlanModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
/**
 * <pre> 
 * 功能描述:锁定取货计划维护业务接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月25日上午9:53:05
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface PupLockPlanManager extends Manager<String, PupLockPlanModel>{
	/**
	 * 锁定取货计划维护查询接口
	 * @param model 查询参数
	 * @param page 页面参数
	 * @return 与查询结果匹配的锁定计划数据
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月25日
	 */
	PageList<PupLockPlanModel> queryLockPlanForPage(PupLockPlanModel model,Page page)throws Exception;
	/**
	 * 锁定取货计划维护数据删除接口
	 * @param ids 一个/多个数据id
	 * @param ipAddr 
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月25日
	 */
	void deleteLockPlanByIds(String[] ids, String ipAddr)throws Exception;
	/**
	 * 锁定取货计划维护数据导出查询接口
	 * @param model 请求参数
	 * @return 与查询结果匹配的计划数据
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月25日
	 */
	List<PupLockPlanModel> queryLockPlanForExport(PupLockPlanModel model)throws Exception;
	/**
	 * 锁定取货计划维护删除接口
	 * @param id 需要删除的数据的ID
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月26日
	 */
	void deleteLockPlanById(String id)throws Exception;
	/**
	 * 锁定取货计划维护数据导入接口
	 * @param file Excel文件
	 * @param ipAddr 用户IP地址
	 * @return 操作结果集
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月26日
	 */
	Map<String, Object> insertExcelDataToTale(MultipartFile file,String ipAddr)throws Exception;
}
