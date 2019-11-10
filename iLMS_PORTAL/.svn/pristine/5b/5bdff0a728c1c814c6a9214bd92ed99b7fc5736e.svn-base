package com.hotent.sys.persistence.manager;

import java.io.InputStream;
import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.SysMsgTemplate;


/**
 * 
 * <pre> 
 * 描述：消息模版 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-03-10 09:20:11
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysMsgTemplateManager extends Manager<String, SysMsgTemplate>{

	void setDefault(String id);

	String exportXml(List<String> ids);

	void importXml(InputStream inputStream, boolean clearAll, boolean setDefault)throws Exception;

	boolean isExistByKeyAndTypeKey(String key, String typeKey);
	
}
