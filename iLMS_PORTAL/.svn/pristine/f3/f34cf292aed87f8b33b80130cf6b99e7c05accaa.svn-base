package com.hotent.sys.persistence.dao;
import java.util.List;
import java.util.Map;

import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.api.model.SysType;


public interface SysTypeDao extends Dao<String, SysType> {
	List<SysType> getByParentId(Long parentId);

	boolean isKeyExist(String id, String typeGroupKey, String typeKey);

	/**
	 * 
	 * @param groupKey
	 * @param currUserId
	 * @return
	 */
	List<SysType> getByGroupKey(String groupKey, String currUserId);

	/**
	 * 根据path获取其子节点
	 * 不包含本身！！
	 * @param path
	 * @return
	 */
	List<SysType> getByPath(String path);

	/**
	 * 
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
	 * 通过所属分组key 以及parentId获取分组  ：<br>eg: 通过 分组key，分组id 获取 所有分类
	 * @param groupKey
	 * @param id
	 * @return
	 */
	List<SysType> getTypesByParentId(String groupKey, String parentId);
	/**
	 * 通过Key 获取 type
	 * @param typeKey
	 * @return
	 */
	SysType getByTypeKey(String typeKey);
	
	/**
	 * 加载数据字典SYS_TYPE
	 */
	SysType getByTypeKeyAndGroupKey(String groupKey, String typeKey);

	
	/**
	 * 获取数据字典
	 * MM_PUB_DATA_DICT
	 * 
	 *@param
	 *@author
	 *@DATE
	 */
	List queryPubDataDictByCodeType(String typeKey);

	/**
	 * 分页查询分类组数据信息
	 * @param paramMap
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月24日 下午5:49:51
	 */
	PageList<Map<String, Object>> querySysTypeGroup(Map<String, Object> paramMap, Page page);

	/**
	 * 分页查询系统分类数据信息
	 * @param paramMap
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月24日 下午5:50:09
	 */
	PageList<Map<String, Object>> querySysTypeByGroupKey(Map<String, Object> paramMap, Page page);

}
