package com.hotent.portal.persistence.manager.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.encrypt.Base64;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.portal.persistence.dao.SysIndexMyLayoutDao;
import com.hotent.portal.persistence.manager.SysIndexColumnManager;
import com.hotent.portal.persistence.manager.SysIndexLayoutManageManager;
import com.hotent.portal.persistence.manager.SysIndexMyLayoutManager;
import com.hotent.portal.persistence.model.SysIndexColumn;
import com.hotent.portal.persistence.model.SysIndexMyLayout;
import com.hotent.portal.util.PortalUtil;
import com.hotent.sys.util.ContextUtil;

/*//**
 * 
 * <pre> 
 * 描述：sys_index_layout 处理实现类
 * 构建组：x5-bpmx-platform
 * 日期:2017-08-2 09:41:15
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysIndexMyLayoutManager")
public class SysIndexMyLayoutManagerImpl extends AbstractManagerImpl<String, SysIndexMyLayout> implements SysIndexMyLayoutManager{
	@Resource
	SysIndexMyLayoutDao sysIndexMyLayoutDao;
	@Resource
	SysIndexLayoutManageManager sysIndexLayoutManageManager;
	@Resource
	SysIndexColumnManager sysIndexColumnManager;
//	@Resource
//	SysIndexLayoutManageManager sysIndexLayoutManageManager;
	
	@Override
	protected Dao<String, SysIndexMyLayout> getDao() {
		
		return (Dao<String, SysIndexMyLayout>) sysIndexMyLayoutDao;
	}

	/**
	 * 榛樿棣栭〉甯冨眬
	 * 
	 * @return
	 */
	private String defaultIndexLayout() {
		String templateHtml=FileUtil.readFile(PortalUtil.getIndexTemplatePath()+"templates"+File.separator+"defaultIndexPages.ftl");
		return templateHtml;
	}

	@Override
	public SysIndexMyLayout getLayoutList(String userId,
			List<SysIndexColumn> columnList) {
		SysIndexMyLayout sysIndexMyLayout = sysIndexMyLayoutDao.getByUserId(userId);
		if (BeanUtils.isEmpty(sysIndexMyLayout))
			return getDefaultIndexLayout();
		String designHtml = "";
		try {
			designHtml = Base64.getFromBase64(sysIndexMyLayout.getDesignHtml());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		sysIndexMyLayout.setDesignHtml(sysIndexColumnManager.parserDesignHtml(designHtml, columnList));
		return sysIndexMyLayout;
	}


	private SysIndexMyLayout getDefaultIndexLayout() {
		SysIndexMyLayout sysIndexMyLayout = new SysIndexMyLayout();
		sysIndexMyLayout.setDesignHtml(sysIndexLayoutManageManager.getDefaultDesignHtml());
		return sysIndexMyLayout;
	}
	@Override
	public void save(String html, String designHtml) {
		String userId = ContextUtil.getCurrentUserId();
		SysIndexMyLayout sysIndexMyLayout =sysIndexMyLayoutDao.getByUserId(userId);
		if(BeanUtils.isEmpty(sysIndexMyLayout)){
			sysIndexMyLayout = new SysIndexMyLayout();
			sysIndexMyLayout.setDesignHtml(designHtml);
			sysIndexMyLayout.setTemplateHtml(html);
			sysIndexMyLayout.setId(UniqueIdUtil.getSuid());
			sysIndexMyLayout.setUserId(userId);
			sysIndexMyLayoutDao.create(sysIndexMyLayout);
			
		}else{
			sysIndexMyLayout.setDesignHtml(designHtml);
			sysIndexMyLayout.setTemplateHtml(html);
			sysIndexMyLayoutDao.update(sysIndexMyLayout);
			
	    }
	}

	@Override
	public String obtainMyIndexData(String userId) {
		// 1、首先先找到自己定义的布局；
		SysIndexMyLayout sysIndexMyLayout = sysIndexMyLayoutDao.getByUserId(userId);
		if (BeanUtils.isNotEmpty(sysIndexMyLayout))
			return sysIndexMyLayout.getTemplateHtml();
		//2.找自己拥有权限的管理布局 ，按是否默认，排序
		String html = sysIndexLayoutManageManager.getMyHasRightsLayout();
		if (BeanUtils.isNotEmpty(html))
			return html;
		//3、找自己所属子组织没权限但设置默认布局；
		html = sysIndexLayoutManageManager.getHasRightsLayout();
		if (BeanUtils.isNotEmpty(html))
			return html;
		//4、如果找不到找系统管理员的设置默认布局;
		html = sysIndexLayoutManageManager.getManagerLayout();
		if (BeanUtils.isNotEmpty(html))
			return html;
//		//5、再找不到则使用系统默认布局。
//		if (BeanUtils.isEmpty(html))
//			html = defaultIndexLayout();
		return "";
	}

	@Override
	public SysIndexMyLayout getByUser(String currentUserId) {
		return sysIndexMyLayoutDao.getByUserId(currentUserId);
	}

	@Override
	public String obtainIndexMyData(String layoutId) {
		SysIndexMyLayout sysIndexMyLayout = sysIndexMyLayoutDao.get(layoutId);
		if (BeanUtils.isNotEmpty(sysIndexMyLayout))
			return sysIndexMyLayout.getTemplateHtml();
		//2.找自己拥有权限的管理布局 ，按是否默认，排序
		String html = sysIndexLayoutManageManager.getMyHasRightsLayout();
		if (BeanUtils.isNotEmpty(html))
			return html;
		//3、找自己所属子组织没权限但设置默认布局；
		html = sysIndexLayoutManageManager.getHasRightsLayout();
		if (BeanUtils.isNotEmpty(html))
			return html;
		//4、如果找不到找系统管理员的设置默认布局;
		html = sysIndexLayoutManageManager.getManagerLayout();
		if (BeanUtils.isNotEmpty(html))
			return html;
		//5、再找不到则使用系统默认布局。
		if (BeanUtils.isEmpty(html))
			html = defaultIndexLayout();
		return "";
	}
	

}
