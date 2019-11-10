package com.hanthink.sys.manager;

import com.hanthink.sys.model.SysDpBaseDataModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：数据权限基础数据 处理接口
 * 作者:linzhuo
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface SysDpBaseDataManager extends Manager<String, SysDpBaseDataModel>{

	/**
	 * 查询数据权限基础数据的分类信息数据
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月25日 下午3:27:23
	 */
	PageList<SysDpBaseDataModel> queryDpBaseDataType(SysDpBaseDataModel model, Page p);

	/**
	 * 根据分类编码查询数据权限基础数据信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月25日 下午3:28:07
	 */
	PageList<SysDpBaseDataModel> queryDpBaseDataByType(SysDpBaseDataModel model, Page p);

	/**
	 *  根据业务主键判断数据是否存在
	 * @param id
	 * @param typeCode
	 * @param valueCode
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月25日 下午4:47:05
	 */
	boolean isKeyExist(String id, String typeCode, String valueCode);

	/**
	 * 删除数据权限基础数据
	 * @param id
	 * @author ZUOSL	
	 * @DATE	2018年12月25日 下午5:23:52
	 */
	void deleteSysDpBaseData(String id);

	
}
