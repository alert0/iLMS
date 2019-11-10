package com.hanthink.sys.dao;
import java.util.List;

import com.hanthink.sys.model.SysPdaUserManagerModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


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
	 * 查询
	 * @param model
	 * @param p
	 * @return
	 */
	PageList<SysPdaUserManagerModel> querySysPdaUserManagerForPage(SysPdaUserManagerModel model, DefaultPage p);

	/**
	 * 删除
	 * @param aryIds
	 */
	void deleteByIds(String[] aryIds);

	/**
	 * 避免主键冲突
	 * @param sysPdaUserManagerModel
	 * @return
	 */
	Integer selectPrimaryKey(SysPdaUserManagerModel sysPdaUserManagerModel);

	/**
	 * 增加数据
	 * <p>return: void</p>  
	 * <p>Description: SysPdaUserManagerDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月18日
	 * @version 1.0
	 */
	void insert(SysPdaUserManagerModel sysPdaUserManagerModel);

	
	/**
	 * 删除用户关系
	 * <p>return: void</p>  
	 * <p>Description: SysPdaUserManagerDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月21日
	 * @version 1.0
	 */
	void deletePdaMenuByIds(String[] aryIds);

	/**
	 * 更新打印状态
	 * <p>return: void</p>  
	 * <p>Description: SysPdaUserManagerDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月28日
	 * @version 1.0
	 */
	void updatePrintStatus(SysPdaUserManagerModel model);

	/**
	 * 打印用查询
	 * <p>return: SysPdaUserManagerModel</p>  
	 * <p>Description: SysPdaUserManagerDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月14日
	 * @version 1.0
	 */
	SysPdaUserManagerModel querySysPdaUserManagerLabel(SysPdaUserManagerModel sysPdaUserManagerModel);

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

	/**
	 * 新增PEC表用户
	 * <p>return: void</p>  
	 * <p>Description: SysPdaUserManagerDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月25日
	 * @version 1.0
	 */
	void insertPEC(SysPdaUserManagerModel sysPdaUserManagerModel);

	/**
	 * 更新PEC表用户
	 * <p>return: void</p>  
	 * <p>Description: SysPdaUserManagerDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月25日
	 * @version 1.0
	 */
	void updatePEC(SysPdaUserManagerModel sysPdaUserManagerModel);

	/**
	 * 删除PEC表用户
	 * <p>return: void</p>  
	 * <p>Description: SysPdaUserManagerDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月25日
	 * @version 1.0
	 */
	void deleteByIdsPEC(String[] aryIds);

	/**
	 * 避免PEC用户冲突
	 * @param sysPdaUserManagerModel
	 * @return
	 */
	Integer selectPrimaryKeyPEC(SysPdaUserManagerModel sysPdaUserManagerModel);
	

	
	
}
