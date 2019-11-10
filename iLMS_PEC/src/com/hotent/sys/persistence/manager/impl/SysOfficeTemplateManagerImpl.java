package com.hotent.sys.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.FileUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.SysOfficeTemplateDao;
import com.hotent.sys.persistence.manager.SysOfficeTemplateManager;
import com.hotent.sys.persistence.model.SysOfficeTemplate;
 

/**
 * 
 * <pre> 
 * 描述：OFFICE模版表 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyj
 * 邮箱:lyj@jee-soft.cn
 * 日期:2014-10-31 16:08:45
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysOfficeTemplateManager")
public class SysOfficeTemplateManagerImpl extends AbstractManagerImpl<String, SysOfficeTemplate> implements SysOfficeTemplateManager{
	@Resource
	SysOfficeTemplateDao sysOfficeTemplateDao;
	@Override
	protected Dao<String, SysOfficeTemplate> getDao() {
		return sysOfficeTemplateDao;
	}
	
	/**
	 * 如果是文件保存，要删除文件
	 */
	@Override
	public void removeByIds(String... ids){
		for(String id :ids){
			SysOfficeTemplate sysOfficeTemplate = get(id);
			if(!sysOfficeTemplate.getSaveType().equals(SysOfficeTemplate.SaveType.FILE)){
				continue;
			}
			FileUtil.deleteFile(sysOfficeTemplate.getAbsolutePath());
		}
		sysOfficeTemplateDao.removeByIds(ids);
	}
}
