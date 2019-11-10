package com.hanthink.sys.dao;
import java.util.List;

import com.hanthink.sys.model.SysPdaUserManagerModel;
import com.hotent.base.db.api.Dao;


/**
 * 
 * <pre> 
 * 描述：生产日历查询DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface SysPdaUserManagerDao extends Dao<String, SysPdaUserManagerModel> {

	/**
	 * 查询系统参数表是否有图片信息
	 * <p>return: Integer</p>  
	 * <p>Description: SysPdaUserManagerDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月23日
	 * @version 1.0
	 * @param model 
	 */
	Integer queryPicture(SysPdaUserManagerModel model);

	/**
	 * 更新图片
	 * <p>return: void</p>  
	 * <p>Description: SysPdaUserManagerDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月23日
	 * @version 1.0
	 */
	void updateImageId(SysPdaUserManagerModel model);

	/**
	 * 新增图片
	 * <p>return: void</p>  
	 * <p>Description: SysPdaUserManagerDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月23日
	 * @version 1.0
	 */
	void insertImageId(SysPdaUserManagerModel model);

	/**
	 * 查询之前的图片ID
	 * <p>return: List<SysPdaUserManagerModel></p>  
	 * <p>Description: SysPdaUserManagerDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月23日
	 * @version 1.0
	 */
	List<SysPdaUserManagerModel> queryOlderPicture(SysPdaUserManagerModel model);

}
