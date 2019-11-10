package com.hotent.sys.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.FileUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.SysSealDao;
import com.hotent.sys.persistence.manager.SysSealManager;
import com.hotent.sys.persistence.model.SysOfficeTemplate;
import com.hotent.sys.persistence.model.SysSeal;
 

/**
 * 
 * <pre> 
 * 描述：电子印章 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyj
 * 邮箱:lyj@jee-soft.cn
 * 日期:2014-11-12 10:14:37
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysSealManager")
public class SysSealManagerImpl extends AbstractManagerImpl<String, SysSeal> implements SysSealManager{
	@Resource
	SysSealDao sysSealDao;
	@Override
	protected Dao<String, SysSeal> getDao() {
		return sysSealDao;
	}
	
	/**
	 * 如果是文件保存，要删除文件
	 */
	@Override
	public void removeByIds(String... ids){
		for(String id :ids){
			SysSeal sysSeal = get(id);
			if(!sysSeal.getSaveType().equals(SysOfficeTemplate.SaveType.FILE)){
				continue;
			}
			FileUtil.deleteFile(sysSeal.getAbsoluteSealPath());
		}
		sysSealDao.removeByIds(ids);
	}
}
