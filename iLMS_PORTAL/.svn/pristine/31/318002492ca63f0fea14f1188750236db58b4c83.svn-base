package com.hanthink.sw.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwSupplierGroupDao;
import com.hanthink.sw.model.SwSupplierGroupModel;
import com.hanthink.sw.model.SwSupplierGroupModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
* <p>Title: SwSupplierGroupDaoImpl.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年10月11日
*/
@Repository
public class SwSupplierGroupDaoImpl extends MyBatisDaoImpl<String, SwSupplierGroupModel>
implements SwSupplierGroupDao{

	@Override
	public String getNamespace() {
		return SwSupplierGroupModel.class.getName();
	}

	/**
	 * 
	* @Title: queryJisoGroupPage 
	* @Description: 分页查询供应商分组信息 
	* @param @param model
	* @param @param p
	* @param @return    
	* @return PageList<SwSupplierGroupModel> 
	* @throws 
	* @author luoxq
	* @date   2018年10月11日 下午5:51:55
	 */
	@Override
	public PageList<SwSupplierGroupModel> queryJisoGroupPage(SwSupplierGroupModel model, DefaultPage p) {
		return (PageList<SwSupplierGroupModel>) this.getByKey("queryJisoGroupPage", model, p);
	}

	/**
	 * 
	* @Title: curdGetGroup 
	* @Description: 获取供应商分组信息 
	* @param @param groupId
	* @param @return    
	* @return SwSupplierGroupModel 
	* @throws 
	* @author luoxq
	* @date   2018年10月11日 下午6:20:04
	 */
	@Override
	public SwSupplierGroupModel curdGetGroup(String groupId) {
		return this.getUnique("curdGetGroup", groupId);
	}

	/**
	 * 
	* @Title: getByGroupName 
	* @Description: 检查分组名称是否已存在 
	* @param @param groupName
	* @param @return    
	* @return SwSupplierGroupModel 
	* @throws 
	* @author luoxq
	* @date   2018年10月11日 下午10:42:35
	 */
	@Override
	public SwSupplierGroupModel getByGroupName(SwSupplierGroupModel swSupplierGroupModel) {
		return this.getUnique("getByGroupName", swSupplierGroupModel);
	}

	/**
	 * 
	* @Title: removeByGroupIds 
	* @Description: 删除分组信息 
	* @param @param groupIds    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 上午10:22:02
	 */
	@Override
	public void removeByGroupIds(String[] groupIds) {
		if (null != groupIds && groupIds.length>0) {
			for (String groupId : groupIds) {
				this.deleteByKey("removeByGroupIds", groupId);
				this.deleteByKey("removeByGroupIdsFromMembers", groupId);
			}
		}
	}

	/**
	 * 
	* @Title: updateGroup 
	* @Description: 修改供应商分组信息 
	* @param @param swSupplierGroupModel    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 上午10:33:37
	 */
	@Override
	public void updateGroup(SwSupplierGroupModel swSupplierGroupModel) {
         if (null != swSupplierGroupModel && !StringUtil.isEmpty(swSupplierGroupModel.getGroupId())) {
			this.updateByKey("updateGroup", swSupplierGroupModel);
		}		
	}

	/**
	 * 
	* @Title: insertGroup 
	* @Description: 新增供应商分组信息 
	* @param @param swSupplierGroupModel    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 上午11:07:49
	 */
	@Override
	public String insertGroup(SwSupplierGroupModel swSupplierGroupModel) {
		  Integer a = this.insertByKey("insertGroup", swSupplierGroupModel);
	      return a.toString();
	}

	/**
	 * 
	* @Title: queryJisoSupplierPage 
	* @Description: 分页查询供应商信息 
	* @param @param model
	* @param @param p
	* @param @return    
	* @return PageList<SwSupplierGroupModel> 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 上午11:29:26
	 */
	@Override
	public PageList<SwSupplierGroupModel> queryJisoSupplierPage(SwSupplierGroupModel model, DefaultPage p) {
		return (PageList<SwSupplierGroupModel>) this.getList("queryJisoSupplierPage", model, p);
	}

	/**
	 * 
	* @Title: insertGroupId 
	* @Description: groupId插入从表 
	* @param @param swSupplierGroupModel    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 上午11:55:57
	 */
	@Override
	public void insertGroupId(SwSupplierGroupModel swSupplierGroupModel) {
        this.insertByKey("insertGroupIdAndSupplier", swSupplierGroupModel);		
	}

	/**
	 * 
	* @Title: getSupplierListUnGroup 
	* @Description: 分页查询未分组供应信息 
	* @param @param model
	* @param @param p
	* @param @return    
	* @return PageList<SwSupplierGroupModel> 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 下午3:36:30
	 */
	@Override
	public PageList<SwSupplierGroupModel> getSupplierListUnGroup(SwSupplierGroupModel model, DefaultPage p) {
		return (PageList<SwSupplierGroupModel>) this.getList("getSupplierListUnGroup", model, p);
	}

	/**
	 * 
	* @Title: removeSupplierByGroupIds 
	* @Description: 删除供应商信息 
	* @param @param groupId
	* @param @param supplierNos    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月12日 下午5:46:00
	 */
	@Override
	public void removeSupplierByGroupIds(String groupId, String[] supplierNos) {
		Map<String, Object> map = new HashMap<>();
		map.put("groupId", groupId);
		for (String supplierNo : supplierNos) {
			map.put("supplierNo", supplierNo);
			this.deleteByKey("removeSupplierByGroupIds", map);
		}
	}

	/**
	 * 
	* @Title: querySwSupplierGroupByKey 
	* @Description: 查询出需要导出的供应商分组数据 
	* @param @param model
	* @param @return    
	* @return List<SwSupplierGroupModel> 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 上午9:47:19
	 */
	@Override
	public List<SwSupplierGroupModel> querySwSupplierGroupByKey(SwSupplierGroupModel model) {
		return (List<SwSupplierGroupModel>) this.getList("querySwSupplierGroupByKey", model);
	}

	/**
	 * 
	* @Title: deleteImportTempDataByUUID 
	* @Description: 根据IMP_UUID删除导入的临时供应商分组数据 
	* @param @param uuid    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 上午11:10:45
	 */
	@Override
	public void deleteImportTempDataByUUID(String uuid) {
        this.deleteByKey("deleteImportTempDataByUUID", uuid);		
	}

	/**
	 * 
	* @Title: queryImportTempData 
	* @Description: 查询导入的临时供应商分组信息订单用量数据  
	* @param @param paramMap
	* @param @param page
	* @param @return    
	* @return PageList<SwSupplierGroupModelImport> 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 上午11:26:47
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SwSupplierGroupModelImport> queryImportTempData(Map<String, String> paramMap, Page page) {
//		PageList<SwSupplierGroupModelImport> pageList= (PageList)this.getByKey("queryImportTempData", paramMap, page);
    	@SuppressWarnings("rawtypes")
		PageList pageList = (PageList) this.getList("queryImportTempData", paramMap, page);
//    	System.out.println(pageList);
			return pageList; 		
		
	}

	/**
	 * 
	* @Title: insertImportTempData 
	* @Description: 数据写入供应商信息临时表 
	* @param @param importList    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 下午2:05:22
	 */
	@Override
	public void insertImportTempData(List<SwSupplierGroupModelImport> importList) {
		this.insertByKey("insertImportTempData", importList);
	}

	/**
	 * 
	* @Title: checkImportData 
	* @Description: 调用存储过程等检查数据准确性  
	* @param @param ckParamMap    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 下午2:15:47
	 */
	@Override
	public void checkImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne("checkImportSupplierGroupData", ckParamMap);
	}

	/**
	 * 
	* @Title: queryIsImportFlag 
	* @Description: 查询是否可以批量导入 
	* @param @param uuid
	* @param @return    
	* @return String 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 下午2:22:17
	 */
	@Override
	public String queryIsImportFlag(String uuid) {
		return this.sqlSessionTemplate.selectOne(getNamespace() + ".querySupplierGroupIsImportFlag", uuid);
	}

	/**
	 * 
	* @Title: insertImportData 
	* @Description: 临时供应商分组维护数据导入正式表 
	* @param @param paramMap    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 下午3:13:29
	 */
	@Override
	public void insertImportData(Map<String, String> paramMap) {
        this.insertByKey("insertSupplierGroupImportData", paramMap);
        this.insertByKey("insertGroupMembersImportData", paramMap);
	}

	/**
	 * 
	* @Title: updateImportDataImpStatus 
	* @Description: 导入成功后更新临时表导入状态 
	* @param @param paramMap    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月13日 下午4:00:12
	 */
	@Override
	public void updateImportDataImpStatus(Map<String, String> paramMap) {
        this.updateByKey("updateSupplierGroupImportDataImpStatus", paramMap);		
	}

	/**
	 * 
	 * @Description: 公告发布管理新增界面，供应商分组弹窗
	 * @param @return   
	 * @return List<SwSupplierGroupModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 下午4:25:44
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwSupplierGroupModel> getSupplierGroupList() {
		return this.getList("getSupplierGroupList", null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SwSupplierGroupModelImport> queryImportInformationForPage(Map<String, String> paramMap) {
		
		return (List)this.getByKey("queryImportInformationForPage", paramMap);
	}

	/**
	 * @Description:  通UUID校验是否有校验结果不通过 
	 * @param: @param uuid
	 * @param: @return    
	 * @return: Integer   
	 * @author: dtp
	 * @date: 2019年1月13日
	 */
	@Override
	public Integer queryIsExistsNotOk(String uuid) {
		return Integer.parseInt(this.getOne("queryIsExistsCheckResultFalse", uuid).toString());
	}

	@Override
	public void insertSupplierGroupImportData(Map<String, String> paramMap) {
		this.insertByKey("insertSupplierGroupImportData", paramMap);
	}

	@Override
	public void insertGroupMembersImportData(Map<String, String> paramMap) {
		this.insertByKey("insertGroupMembersImportData", paramMap);
	}

}
