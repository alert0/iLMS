package com.hotent.sys.persistence.manager.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.Page;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.api.model.SysType;
import com.hotent.sys.constants.CategoryConstants;
import com.hotent.sys.persistence.dao.SysCategoryDao;
import com.hotent.sys.persistence.dao.SysTypeDao;
import com.hotent.sys.persistence.manager.DataDictManager;
import com.hotent.sys.persistence.manager.SysTypeManager;
import com.hotent.sys.persistence.model.SysCategory;

@Service("sysTypeManager")
public class SysTypeManagerImpl extends AbstractManagerImpl<String, SysType> implements SysTypeManager{
	@Resource
	SysTypeDao sysTypeDao;
	@Resource
	SysCategoryDao sysCategoryDao;
	@Resource
	DataDictManager dataDictManager;
	
	@Override
	protected Dao<String, SysType> getDao() {
		return sysTypeDao;
	}
	
	/**
	 * 根据parentId获取
	 */
	@Override
	public List<SysType> getByParentId(Long parentId) {
		return sysTypeDao.getByParentId(parentId);
	}

	
	/**
	 * 取得初始分类类型。
	 * @param isRoot	是否根节点。
	 * @param parentId	父节点。
	 * @return
	 * @throws Exception 
	 */
	@Override
	public SysType getInitSysType(int isRoot, String parentId) {
		SysType sysType=new SysType();
		String typeId=UniqueIdUtil.getSuid();
		//如果是根节点，则从SysCategory获取数据构建分类类型
		if (isRoot==1) {
			SysCategory sysCategory =sysCategoryDao.get(parentId);
			sysType.setTypeKey(sysCategory.getGroupKey());
			sysType.setTypeGroupKey(sysCategory.getGroupKey());
			sysType.setParentId(parentId);
			sysType.setStruType(sysCategory.getType());
			sysType.setPath(parentId+"."+typeId+".");
			sysType.setName(sysCategory.getName());
		}else {
			//获取父类构建分类类型。
			sysType=sysTypeDao.get(parentId);
			String path=sysType.getPath();
			sysType.setPath(path +typeId +".");
		}
		sysType.setId(typeId);
		return sysType;
	}

	@Override
	public boolean isKeyExist(String id, String typeGroupKey, String typeKey) {
		return sysTypeDao.isKeyExist(id,typeGroupKey,typeKey);
	}

	/**
	 * 通过分类组业务主键获取所有的公共分类和属于当前人的私有分类
	 * @param groupKey
	 * @param currUserId
	 * @return
	 */
	@Override
	public List<SysType> getByGroupKey(String groupKey,String currUserId) {
		
		return sysTypeDao.getByGroupKey( groupKey, currUserId);
	}

	/**
	 * 根据Id删除节点和其所有的子节点
	 * 如果是数据字典，删除字典项
	 * @param id
	 */
	@Override
	public void delByIds(String id) {
		if(BeanUtils.isEmpty(id)) return;
		//如果是数据字典则、删除数据字典项
		SysType sysType = sysTypeDao.get(id);
		boolean isDict = sysType.getTypeGroupKey().equals(CategoryConstants.CAT_DIC.key());
		//根据其path获取其子节点
		List<SysType> sysTypes=sysTypeDao.getByPath(sysType.getPath());
		sysTypeDao.remove(id);
		if(isDict) dataDictManager.delByDictTypeId(id);
		for(SysType sysTypeTempSysType : sysTypes){
			String Id=sysTypeTempSysType.getId();
			if(isDict) dataDictManager.delByDictTypeId(Id);
			sysTypeDao.remove(Id);
			//删除数据字典。
		}
		
	}

	@Override
	public List<SysType> getPrivByPartId(String parentId, String userId) {
		
		return sysTypeDao.getPrivByPartId( parentId, userId);
	}

	/**
	 * 更新排序  sn
	 * @param typeId
	 * @param sn
	 */
	@Override
	public void updSn(String typeId, int sn) {
		sysTypeDao.updSn(typeId, sn);
	}

	@Override
	public List<SysType> getRootTypeByCategoryKey(String groupKey) {
		SysCategory sysCategory =  sysCategoryDao.getByKey(groupKey);
		if(sysCategory == null) return Collections.emptyList();
		return sysTypeDao.getTypesByParentId(groupKey,sysCategory.getId());
	}

	@Override
	public List<SysType> getChildByTypeKey(String typeKey) {
		SysType sysType=sysTypeDao.getByTypeKey(typeKey);
		if(sysType == null) return Collections.emptyList();
		
		return sysTypeDao.getByPath(sysType.getPath());
	}

	@Override
	public SysType getByKey(String typeKey) {
		return sysTypeDao.getByTypeKey(typeKey);
		 
	}

	@Override
	public String getXmlByKey(String groupKey,String currUserId) {
		SysCategory category= sysCategoryDao.getByKey(groupKey);
		List<SysType> sysTypes= getByGroupKey( groupKey, currUserId);
		StringBuffer sb = new StringBuffer("<folder id='0' label='全部'>");
		
		contructXml(sysTypes,category.getId(),sb);
		
		sb.append("</folder>");
		return sb.toString();
	}
	
	private void contructXml(List<SysType> sysTypes,String parentId,StringBuffer sb){
		if(BeanUtils.isEmpty(sysTypes)) return;
		for(SysType type:sysTypes){
			if(!parentId.equals( type.getParentId())) continue; 
			sb.append("<folder id='"+type.getId()  +"' label='"+ type.getName() +"'>");
			contructXml(sysTypes, type.getId(), sb);
			sb.append("</folder>");
		}
	}

	/**
	 * 加载数据字典
	 */
	@Override
	public SysType getByTypeKeyAndGroupKey(String groupKey, String typeKey) {
		return sysTypeDao.getByTypeKeyAndGroupKey(groupKey,typeKey);
	}
	/**
	 * 获取数据字典表MM_PUB_DATA_DICT
	 */
	@Override
	public List queryPubDataDictByCodeType(String typeKey) {
		return sysTypeDao.queryPubDataDictByCodeType(typeKey);
	}

	/**
	 * 分页查询分类组数据信息
	 * @param paramMap
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月24日 下午5:45:07
	 */
	@Override
	public PageList<Map<String, Object>> querySysTypeGroup(Map<String, Object> paramMap, Page page) {
		return sysTypeDao.querySysTypeGroup(paramMap, page);
	}

	/**
	 * 分页查询系统分类数据信息
	 * @param paramMap
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月24日 下午5:49:20
	 */
	@Override
	public PageList<Map<String, Object>> querySysTypeByGroupKey(Map<String, Object> paramMap, Page page) {
		return sysTypeDao.querySysTypeByGroupKey(paramMap, page);
	}
	
	
}
