package com.hanthink.sw.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.sw.model.SwNonStandardModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface SwNonStandardDao extends Dao<String, SwNonStandardModel>{

	/**
	 * 
	 * @Description: 实物图片上传
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月5日 上午11:10:16
	 */
	void uploadSignProFile(SwNonStandardModel model);

	/**
	 * 
	 * @Description: 分页 查询非标件
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<SwNonStandardModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月5日 下午12:05:59
	 */
	PageList<SwNonStandardModel> queryNonStandardPage(SwNonStandardModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 根据id获取图片id
	 * @param @param map
	 * @param @return   
	 * @return List<SwNonStandardModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月5日 下午4:08:29
	 */
	List<SwNonStandardModel> getUploadPicIdBy(Map<String, String> map);

	/**
	 * 
	 * @Description: 检查提交
	 * @param @param model
	 * @param @return   
	 * @return Object  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月7日 下午6:53:20
	 */
	void submitFeedback(SwNonStandardModel model);

	/**
	 * 
	 * @Description: 更新主表与图片关系表
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月7日 下午6:55:24
	 */
	void submitFeedbackPic(SwNonStandardModel model);

	/**
	 * 
	 * @Description: 定制化信息下载
	 * @param @param model
	 * @param @return   
	 * @return List<SwNonStandardModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月8日 下午5:47:16
	 */
	List<SwNonStandardModel> queryNonStandDetail(SwNonStandardModel model);

	/**
	 * 
	 * @Description: 实物图片上传点击确定，数据写到检查记录表中
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月10日 下午7:26:39
	 */
	void insertNonStandModel(SwNonStandardModel model);

	String getSeqCheck();

	void updateNonStandard(SwNonStandardModel model);
	
	/**
	 * 
	 * @Description: 通过对象获取图片ID
	 * @param @param model
	 * @param @return   
	 * @return List<SwNonStandardModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月10日 下午9:22:28
	 */
	List<SwNonStandardModel> getUploadPicIdByModel(SwNonStandardModel model);

	/**
	 * 
	 * @Description: 查看检查记录
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<SwNonStandardModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月11日 上午12:45:53
	 */
	PageList<SwNonStandardModel> selectDetail(SwNonStandardModel model, DefaultPage p);
	
	/**
	 * 
	 * @Description: TODO
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月11日 上午11:59:32
	 */
	void deleteNonStandPic(SwNonStandardModel model);
	
	/**
	 * 
	 * @Description: 如果没有进行过检查操作上传图片时，删除写入到检查记录表的信息
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月12日 上午12:37:45
	 */
	void deleteNonStandCheck(SwNonStandardModel model);

	/**
	 * 
	 * @Description: 根据检查记录Id获取实物图
	 * @param @param checkId
	 * @param @return   
	 * @return List<SwNonStandardModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月12日 上午1:24:25
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
	 * @date 2019年3月14日 上午11:58:53
	 */
	String getParamsByString(Map<String, String> map);

	/**
	 * 
	 * @Description: TODO
	 * @param @param model
	 * @param @return   
	 * @return List<SwNonStandardModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月15日 上午11:37:59
	 */
	List<SwNonStandardModel> getCheckHistory(SwNonStandardModel model);
}
