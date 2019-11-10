package com.hotent.portal.persistence.manager.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.encrypt.Base64;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
//import com.hotent.bpmx.persistence.manager.BpmDefUserManager;
//import com.hotent.org.api.service.IOrgService;
import com.hotent.org.api.service.IUserService;
import com.hotent.org.persistence.dao.OrgUserDao;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.manager.RoleManager;
import com.hotent.org.persistence.manager.SysUserRelManager;
import com.hotent.org.persistence.model.Org;
import com.hotent.org.persistence.model.OrgUser;
import com.hotent.portal.persistence.dao.SysIndexLayoutManageDao;
import com.hotent.portal.persistence.manager.SysIndexLayoutManageManager;
import com.hotent.portal.persistence.model.SysIndexColumn;
import com.hotent.portal.persistence.model.SysIndexLayoutManage;
import com.hotent.portal.persistence.model.SysObjRights;
import com.hotent.portal.util.PortalUtil;
import com.hotent.sys.api.curuser.CurrentUserService;
import com.hotent.sys.util.ContextUtil;

/**
 * <pre>
 * 对象功能:布局管理 Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:hugh
 * 创建时间:2015-03-18 15:40:13
 * </pre>
 */
@Service("sysIndexLayoutManageService")
public class SysIndexLayoutManageManagerImpl extends
	AbstractManagerImpl<String, SysIndexLayoutManage> implements SysIndexLayoutManageManager{
	@Resource
	private SysIndexLayoutManageDao dao;
	@Resource
	private SysIndexColumnManagerImpl sysIndexColumnService;
	@Resource
	private IUserService sysUserService;
	@Resource
	private RoleManager sysRoleService;
//	@Resource
//	private IOrgService sysOrgService;
	@Resource
	private SysUserRelManager positionService;
	@Resource
	private CurrentUserService currentUserService;
	@Resource
	private OrgUserDao orgUserDao;
	@Resource
	private OrgManager orgManager;
//	@Resource
//	BpmDefUserManager bpmdefUserManager;
	
	public SysIndexLayoutManageManagerImpl() {
	}

	@Override
	protected Dao<String, SysIndexLayoutManage> getDao() {
		return dao;
	}

	@Override
	public SysIndexLayoutManage getLayoutList(String id,
			List<SysIndexColumn> columnList,Short type) {
		SysIndexLayoutManage sysIndexLayoutManage = null;
		if(StringUtil.isNotEmpty(id)){
			sysIndexLayoutManage = dao.get(id);
		}
		if (BeanUtils.isEmpty(sysIndexLayoutManage))
			return getDefaultIndexLayout(type);
		String designHtml = "";
		try {
			designHtml = Base64.getFromBase64(sysIndexLayoutManage.getDesignHtml());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		sysIndexLayoutManage.setDesignHtml(sysIndexColumnService.parserDesignHtml(
				designHtml, columnList));
		return sysIndexLayoutManage;
	}
	


	public String getDefaultMobileDesignHtml() {
		return   "<div class=\"lyrow ui-draggable\" style=\"display: block;\">"
				+ "<div class=\"preview\"><input type=\"text\" value=\"一列(12)\" readonly=\"readonly\" class=\"form-control\"></div>"
				+ "<div class=\"view\">"
				+ "<div class=\"row clearfix\">"
				+ "<div class=\"col-md-12 column ui-sortable\"></div>"
				+ "</div>"
				+ "</div>"
				+ "</div>";
	}
	public String getDefaultDesignHtml() {
		return   "<div class=\"lyrow ui-draggable\" style=\"display: block;\">"
				+ "<a href=\"#close\" class=\"remove label label-danger\"><i class=\"glyphicon-remove glyphicon\"></i> 删除</a>"
				+ "<span class=\"drag label label-default\"><i class=\"glyphicon glyphglyphicon glyphicon-move\"></i> 拖动</span>"
				+ "<div class=\"preview\"><input type=\"text\" value=\"一列(12)\" readonly=\"readonly\" class=\"form-control\"></div>"
				+ "<div class=\"view\">"
				+ "<div class=\"row clearfix\">"
				+ "<div class=\"col-md-12 column ui-sortable\"></div>"
				+ "</div>"
				+ "</div>"
				+ "</div>";
	}
	
	private SysIndexLayoutManage getDefaultIndexLayout(Short type) {
		String designHtml  = getDefaultDesignHtml();
		if(type.equals(SysIndexLayoutManage.TYPE_MOBILE)){
			designHtml = getDefaultMobileDesignHtml();
		}
		SysIndexLayoutManage sysIndexLayoutManage  = new SysIndexLayoutManage();
		sysIndexLayoutManage.setDesignHtml(designHtml);
		sysIndexLayoutManage.setIsDef((short) 0);
		return sysIndexLayoutManage;
	}

//	@Override
//	public String getHasRightsLayout() {
//		//获取当前用户所在组织ids
//		String orgIds = "";
//		List<OrgUser> orgUserList = orgUserDao.getByUserId(ContextUtil.getCurrentUserId());
//		for(int i=0;i<orgUserList.size();i++){
//			if(i==0){
//				orgIds+=orgUserList.get(i).getId();
//    		}
//    		else{
//    			orgIds+="," + orgUserList.get(i).getId();
//    		}
//		}
//		List<SysIndexLayoutManage> list = dao.getManageLayout(orgIds,(short)1);
//		if(BeanUtils.isNotEmpty(list) )
//			return list.get(0).getTemplateHtml();
//		return null;
//	}
//	
//	/**
//	 * 管理员的布局
//	 * @return
//	 */
//	public String getManagerLayout() {
//		List<SysIndexLayoutManage> list = dao.getManageLayout(null,(short)1);
//		if(BeanUtils.isNotEmpty(list) )
//			return list.get(0).getTemplateHtml();
//		return null;
//	}

//	/**
//	 * 找自己所属子组织或公司的我有权限的布局 按默认，排序排序
//	 * @return
//	 */
//	@Override
//	public String getMyHasRightsLayout() {
//		Map<String, Set<String>> relationMap= currentUserService.getUserRightMap();
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("relationMap", relationMap);
//		params.put("objType", SysObjRights.RIGHT_TYPE_INDEX_MANAGE);
//		List<SysIndexLayoutManage> list = dao.getByUserIdFilter(params);
//		if(BeanUtils.isNotEmpty(list))
//			return list.get(0).getTemplateHtml();
//		return "";
//	}
	
	/**
	 * 取得有权限的列表
	 * @param filter
	 * @return
	 *//*
	public List<SysIndexLayoutManage> getList(QueryFilter filter) {
		Map<String, List<Long>> params= currentUserService.getUserRelation(ServiceUtil.getCurrentUser());
	
		filter.addFilter("relationMap", params);
		filter.addFilter("objType", SysObjRights.RIGHT_TYPE_INDEX_MANAGE);
		// 根据流程授权获取流程。
		return dao.getByUserIdFilter(filter);
	}*/
//
	/**
	 * 保存数据
	 * @param sysIndexLayoutManage
	 * @param type
	 */
	@Override
	public void save(SysIndexLayoutManage sysIndexLayoutManage, int type) {
	
		if(type == 0)
			dao.create(sysIndexLayoutManage);
		else
			dao.update(sysIndexLayoutManage);
		
	}

	@Override
	public String getMyHasRightsLayout() {
		Map<String, Set<String>> relationMap= currentUserService.getUserRightMap();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("relationMap", relationMap);
		params.put("objType", SysObjRights.RIGHT_TYPE_INDEX_MANAGE);
		List<SysIndexLayoutManage> list = dao.getByUserIdFilter(params);
		if(BeanUtils.isNotEmpty(list))
			return list.get(0).getTemplateHtml();
		return "";
	}

	@Override
	public String getHasRightsLayout() {
		//获取当前用户所在组织ids
		String orgIds = "";
		List<OrgUser> orgUserList = orgUserDao.getByUserId(ContextUtil.getCurrentUserId());
		for(int i=0;i<orgUserList.size();i++){
			if(i==0){
				orgIds+=orgUserList.get(i).getId();
		    }
		    else{
		    	orgIds+="," + orgUserList.get(i).getId();
		    }
		}
		List<SysIndexLayoutManage> list = dao.getManageLayout(orgIds,(short)1);
		if(BeanUtils.isNotEmpty(list) )
			return list.get(0).getTemplateHtml();
		return null;
	}

	@Override
	public String getManagerLayout() {
		List<SysIndexLayoutManage> list = dao.getManageLayout(null,(short)1);
		if(BeanUtils.isNotEmpty(list) )
			return list.get(0).getTemplateHtml();
		return null;
	}

	@Override
	public String obtainIndexManageData(String layoutId) {
		SysIndexLayoutManage sysIndexLayoutManage = dao.get(layoutId);
		if (BeanUtils.isNotEmpty(sysIndexLayoutManage))
			return sysIndexLayoutManage.getTemplateHtml();
		//2.找自己拥有权限的管理布局 ，按是否默认，排序
		String html = getMyHasRightsLayout();
		if (BeanUtils.isNotEmpty(html))
			return html;
		//3、找自己所属子组织没权限但设置默认布局；
		html = getHasRightsLayout();
		if (BeanUtils.isNotEmpty(html))
			return html;
		//4、如果找不到找系统管理员的设置默认布局;
		html = getManagerLayout();
		if (BeanUtils.isNotEmpty(html))
			return html;
		//5、再找不到则使用系统默认布局。
		if (BeanUtils.isEmpty(html))
			html = getDefaultDesignHtml();
		return "";
	}

	@Override
	public SysIndexLayoutManage getByOrgId(String orgId) {
		return dao.getByOrgId(orgId);
	}

	@Override
	public Boolean isExistName(String name) {
		return dao.isExistName(name);
	}

	@Override
	public SysIndexLayoutManage getByOrgIdAndLayoutType(String orgId, Short layoutType) {
		return dao.getByOrgIdAndLayoutType(orgId,layoutType);
	}

	@Override
	public void cancelOrgIsDef(String orgId, Short layoutType) {
		dao.cancelOrgIsDef(orgId,layoutType);
	}

	@Override
	public String obtainIndexManageMobileData(String layoutId) {
		SysIndexLayoutManage sysIndexLayoutManage = dao.get(layoutId);
		return sysIndexLayoutManage.getTemplateHtml();
	}
	@Override
	public void getMobileLayoutId(String userId) {
//		List<String> authorizeIdsByUserMap = bpmdefUserManager.getAuthorizeIdsByUserMap(SysIndexLayoutManage.INDEX_MANAGE);
		List<String> layoutIds = new ArrayList<String>();
//		if(authorizeIdsByUserMap.size()>0){
//			for(String authorizeId :  authorizeIdsByUserMap){
//				layoutIds.add(authorizeId);
//			}
//		}
		Org org = (Org) ContextUtil.getCurrentGroup();
		if(layoutIds.size()>0 && BeanUtils.isNotEmpty(org)){
			if(StringUtil.isNotEmpty(org.getPath())){
				String[] orgArray = org.getPath().split("\\.");
				for (int i = orgArray.length-1; i >= 0; i--) {
					SysIndexLayoutManage byOrgIdAndLayoutType = dao.getByOrgIdAndLayoutType(orgArray[i],SysIndexLayoutManage.TYPE_MOBILE);
					if(BeanUtils.isNotEmpty(byOrgIdAndLayoutType) && layoutIds.contains(byOrgIdAndLayoutType.getId())){
						PortalUtil.setCurrentUserMobileLayout(byOrgIdAndLayoutType.getId());
						break;
					}
				}
			}
		}
	}

	@Override
	public SysIndexLayoutManage getByIdAndType(String id, Short type) {
		return dao.getByIdAndType(id, type);
	}



}
