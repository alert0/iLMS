package com.hotent.sys.persistence.manager;

import java.util.List;
import java.util.Map;

import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.sys.api.model.SysType;


public interface SysTypeManager extends Manager<String, SysType>{
	
	List<SysType> getByParentId(Long parentId);

	SysType getInitSysType(int isRoot, String parentId);

	boolean isKeyExist(String id,String typeGroupKey, String typeKey);

	/**
	 * 通过分类组业务主键获取所有的公共分类和属于当前人的私有分类
	 * @param groupKey
	 * @param currUserId
	 * @return
	 */
	List<SysType> getByGroupKey(String groupKey,String currUserId);

	/**
	 * 根据Id删除节点和其所有的子节点
	 * @param id
	 */
	void delByIds(String id);

	/**
	 * 根据节点Id和当前用户Id获取下一级的私有分类和公共分类
	 * @param parentId
	 * @param userId
	 * @return
	 */
	List<SysType> getPrivByPartId(String parentId, String userId);

	/**
	 * 更新排序  sn
	 * @param typeId
	 * @param sn
	 */
	void updSn(String typeId, int sn);
	/**
	 * 通过分类组Key 获取 分类组的 所有分类  
	 * @param string
	 * @return
	 */
	List<SysType> getRootTypeByCategoryKey(String string);
	/**
	 * 通过typeKey获取下级
	 * @param typeKey
	 * @return
	 */
	List<SysType> getChildByTypeKey(String typeKey);
	/**
	 * 根据key获取对象
	 * @param typeKey
	 * @return
	 */
	SysType getByKey(String typeKey);
	
	/**
	 * 根据键值获取xml格式数据。
	 * <pre>
	 * &lt;folder id='0' label='全部'>
	 * 	&lt;folder id='10000001994002' label='采购'>
	 * 		&lt;folder id='10000017790020' label='采购1'/>
	 * 		&lt;folder id='10000017790022' label='采购1'/>
	 * 	&lt;/folder>
	 * &lt;/folder>
	 * </pre>
	 * @param typeKey
	 * @return String
	 */
	String getXmlByKey(String typeKey,String currUserId);
	
	/**
	 * 加载数据字典
	 */
	SysType getByTypeKeyAndGroupKey(String key, String typeKey);
	/**
	 * 数据字典表MM_PUB_DATA_DICT
	 */
	List queryPubDataDictByCodeType(String typeKey);

	/**
	 * 分页查询分类组数据信息
	 * @param paramMap
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月24日 下午5:44:10
	 */
	PageList<Map<String, Object>> querySysTypeGroup(Map<String, Object> paramMap, Page page);

	/**
	 * 分页查询系统分类数据信息
	 * @param paramMap
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月24日 下午5:49:05
	 */
	PageList<Map<String, Object>> querySysTypeByGroupKey(Map<String, Object> paramMap, Page page);

	
}
