package com.hotent.bpmx.persistence.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmInstFormDao;
import com.hotent.bpmx.persistence.model.BpmInstForm;

/**
 * 
 * <pre> 
 * 描述：bpm_inst_form DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liygui
 * 邮箱:liygui@jee-soft.cn
 * 日期:2017-07-04 15:19:05
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BpmInstFormDaoImpl extends MyBatisDaoImpl<String, BpmInstForm> implements BpmInstFormDao{

    @Override
    public String getNamespace() {
        return BpmInstForm.class.getName();
    }

	@Override
	public BpmInstForm getNodeForm(String instId, String defId, String nodeId,
			String type) {
		Map<String, Object> params = buildMap("instId", instId);
		params.put("defId", defId);
		params.put("nodeId", nodeId);
		params.put("formType", type);
		return this.selectOne("getNodeForm", params);
	}

	@Override
	public BpmInstForm getGlobalForm(String instId, String formType) {
		Map<String, Object> params = buildMap("instId", instId);
		params.put("formType", formType);
		return this.selectOne("getGlobalForm", params);
	}
	
	@Override
	public BpmInstForm getInstForm(String instId, String type) {
		Map<String, Object> params = buildMap("instId", instId);
		params.put("formType", type);
		return this.selectOne("getInstForm", params);
	}

	@Override
	public void removeDataByDefId(String defId) {
		this.deleteByKey("removeDataByDefId", defId);
	}

	@Override
	public void removeDataByInstId(String instId) {
		this.deleteByKey("removeDataByInstId", instId);
	}

	
	private BpmInstForm selectOne(String sqlKey,Map<String, Object> params){
		List<BpmInstForm> list = this.getByKey(sqlKey, params);
		if(BeanUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
}

