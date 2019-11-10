package com.hanthink.sw.manager;

import java.util.List;
import java.util.Map;

import com.hanthink.sw.model.SwNonStandardModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface SwNonStandardManager extends Manager<String, SwNonStandardModel>{

	/**
	 * 
	 * @Description: 图片上传
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月5日 下午12:04:54
	 */
	void uploadSignProFile(SwNonStandardModel model);

	/**
	 * 
	 * @Description: 分页查询
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<SwNonStandardModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月5日 下午12:05:04
	 */
	PageList<SwNonStandardModel> queryNonStandardPage(SwNonStandardModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 通过id获取图片ID
	 * @param @param map
	 * @param @return   
	 * @return List<SwNonStandardModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月5日 下午4:07:47
	 */
	List<SwNonStandardModel> getUploadPicIdBy(Map<String, String> map);

	/**
	 * 
	 * @Description: 检查提交
	 * @param @param model
	 * @param @return   
	 * @return List<SwNonStandardModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月7日 下午6:51:25
	 */
	void submitFeedback(SwNonStandardModel model);

	/**
	 * 
	 * @Description: 定制化信息下载
	 * @param @param model
	 * @param @return   
	 * @return List<SwNonStandardModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月8日 下午5:46:16
	 */
	List<SwNonStandardModel> queryNonStandDetail(SwNonStandardModel model);
	
	/**
	 * 
	 * @Description: 获取检查历史记录表中的自增序列id
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月10日 下午7:36:32
	 */
	String getSeqCheck();

	/**
	 * 
	 * @Description: 更新主表实物上传状态为已上传
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月10日 下午7:54:19
	 */
	void updateNonStandard(SwNonStandardModel model);

	/**
	 * 
	 * @Description: 根据对象获取图片ID
	 * @param @param model
	 * @param @return   
	 * @return List<SwNonStandardModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月10日 下午9:21:52
	 */
	List<SwNonStandardModel> getUploadPicIdByModel(SwNonStandardModel model);
	/**
	 * 
	 * @param ipAddr 
	 * @Description: 实物图片上传，数据写入到历史记录检查表中
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月10日 下午11:04:04
	 */
	void insertIntoNonStandCheck(SwNonStandardModel model, String ipAddr);

	/**
	 * 
	 * @Description: 检查记录查看
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<SwNonStandardModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月11日 上午12:45:15
	 */
	PageList<SwNonStandardModel> selectDetail(SwNonStandardModel model, DefaultPage p);
	
	/**
	 * 
	 * @Description: 如果为进行检查操作进行的图片上传，则删除上次上传的图片在新增
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月11日 上午11:58:20
	 */
	void deleteNonStandPic(SwNonStandardModel model);

	/**
	 * 
	 * @Description: 根据检查记录id获取该条记录的实物图
	 * @param @param checkId
	 * @param @return   
	 * @return List<SwNonStandardModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月12日 上午1:23:10
	 */
	List<SwNonStandardModel> getUploadPicIdByCheckId(String checkId);

	/**
	 * 
	 * @Description: 通过参数代码获取参数值
	 * @param @param map
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月14日 上午11:57:52
	 */
	String getParamsByString(Map<String, String> map);

	/**
	 * 
	 * @Description: 获取最近的检查记录
	 * @param @param model
	 * @param @return   
	 * @return List<SwNonStandardModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月15日 上午11:34:33
	 */
	List<SwNonStandardModel> getCheckHistory(SwNonStandardModel model);
}
