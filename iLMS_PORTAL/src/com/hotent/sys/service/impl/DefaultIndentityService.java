package com.hotent.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.sys.api.identity.IdentityService;
import com.hotent.sys.persistence.manager.IdentityManager;

/**
 * {@linkplain IdentityService} 接口实现
 * <pre> 
 * 构建组：x5-sys-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-12-1-下午5:11:30
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service("identityService")
public class DefaultIndentityService implements IdentityService {
	
	@Resource
	IdentityManager identityManager;

	@Override
	public String genNextNo(String alias)  {
		return identityManager.nextId(alias);
	}

	@Override
	public String getPreviewNo(String alias) {
		return identityManager.getCurIdByAlias(alias);
	}
}
