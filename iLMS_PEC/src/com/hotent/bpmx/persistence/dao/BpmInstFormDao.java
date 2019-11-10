package com.hotent.bpmx.persistence.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.bpmx.persistence.model.BpmInstForm;

/**
 * 
 * <pre> 
 * 描述：bpm_inst_form DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:liygui
 * 邮箱:liygui@jee-soft.cn
 * 日期:2017-07-04 15:19:05
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BpmInstFormDao extends Dao<String, BpmInstForm> {

	BpmInstForm getNodeForm(String instId, String defId, String nodeId,
			String type);

	BpmInstForm getGlobalForm(String instId, String type);

	BpmInstForm getInstForm(String instId, String type);

	void removeDataByDefId(String defId);
	
	void removeDataByInstId(String instId);

}
