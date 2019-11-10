package com.hanthink.sys.manager;

import java.util.List;

import com.hanthink.sys.model.SysPdaUserManagerModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：用户数据权限 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface SysPdaUserManagerManager extends Manager<String, SysPdaUserManagerModel>{

	/**
	 * 查询系统参数表是否有图片信息
	 * <p>return: Integer</p>  
	 * <p>Description: SysPdaUserManagerManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月23日
	 * @version 1.0
	 * @param model 
	 */
	Integer queryPicture(SysPdaUserManagerModel model);

	/**
	 * 新增图片
	 * <p>return: void</p>  
	 * <p>Description: SysPdaUserManagerManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月23日
	 * @version 1.0
	 */
	void insertImageId(SysPdaUserManagerModel model);

	/**
	 * 更新图片
	 * <p>return: void</p>  
	 * <p>Description: SysPdaUserManagerManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月23日
	 * @version 1.0
	 */
	void updateImageIdAndLog(SysPdaUserManagerModel model, String ipAddr);

	/**
	 * 图片存在，根据之前的图片ID删除FTP服务器上的图片
	 * <p>return: List<SysPdaUserManagerModel></p>  
	 * <p>Description: SysPdaUserManagerManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月23日
	 * @version 1.0
	 */
	List<SysPdaUserManagerModel> queryOlderPicture(SysPdaUserManagerModel model);

}
