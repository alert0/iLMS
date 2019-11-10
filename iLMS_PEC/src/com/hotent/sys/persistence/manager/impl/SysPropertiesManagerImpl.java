package com.hotent.sys.persistence.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.cache.ICache;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.api.system.PropertyService;
import com.hotent.sys.persistence.dao.SysPropertiesDao;
import com.hotent.sys.persistence.manager.SysPropertiesManager;
import com.hotent.sys.persistence.model.SysProperties;

/**
 * 
 * <pre> 
 * 描述：SYS_PROPERTIES 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-07-28 09:19:53
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysPropertiesManager")
public class SysPropertiesManagerImpl extends AbstractManagerImpl<String, SysProperties> implements SysPropertiesManager,PropertyService{
	@Resource
	SysPropertiesDao sysPropertiesDao;
	@Override
	protected Dao<String, SysProperties> getDao() {
		return sysPropertiesDao;
	}
	@Override
	public List<String> getGroups() {
		return sysPropertiesDao.getGroups();
	}
	@Override
	public boolean isExist(SysProperties sysProperties) {
		return sysPropertiesDao.isExist(sysProperties);
	}
	
	
	public  Map<String,String>  reloadProperty(){
		ICache cache=AppUtil.getBean(ICache.class);
		Map<String,String> map=new HashMap<String, String>();
		List<SysProperties> list=this.getAll();
		for(SysProperties property:list){
			map.put(property.getAlias().toLowerCase(), property.getRealVal());
		}
		cache.add("PropertyCache", map);
		return map;
	}
	@Override
	public String getByAlias(String alias) {
		ICache cache=AppUtil.getBean(ICache.class);
		Map<String,String> map=(Map<String,String>) cache.getByKey("PropertyCache");
		if(BeanUtils.isEmpty(map)){
			map=reloadProperty();
		}
		return map.get(alias.toLowerCase());
	}
	@Override
	public String getByAlias(String alias, String defaultValue) {
		String val=getByAlias(alias);
		if(StringUtil.isEmpty(val)) return defaultValue;
		return val;
	}
	@Override
	public Integer getIntByAlias(String alias) {
		String val= getByAlias(alias);
		if(StringUtil.isEmpty(val)) return 0;
		Integer rtn=Integer.parseInt(val);
		return rtn;
	}
	@Override
	public Integer getIntByAlias(String alias, Integer defaulValue) {
		String val= getByAlias(alias);
		if(StringUtil.isEmpty(val)) return defaulValue;
		Integer rtn=Integer.parseInt(val);
		return rtn;
	}
	@Override
	public Long getLongByAlias(String alias) {
		String val= getByAlias(alias);
		if(StringUtil.isEmpty(val)) return 0L;
		Long rtn=Long.parseLong(val);
		return rtn;
	}
	@Override
	public boolean getBooleanByAlias(String alias) {
		String val= getByAlias(alias);
		return Boolean.parseBoolean(val);
	}
	@Override
	public boolean getBooleanByAlias(String alias, boolean defaulValue) {
		String val= getByAlias(alias);
		if(StringUtil.isEmpty(val)) return defaulValue;
		if("1".equals(val)) return true;
		return Boolean.parseBoolean(val);
	}
}
