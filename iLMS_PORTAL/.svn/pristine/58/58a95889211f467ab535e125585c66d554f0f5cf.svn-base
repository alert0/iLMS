package com.hotent.form.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.form.persistence.model.BpmFormDef;

/**
 * 
 * <pre> 
 * 描述：bpm_form_def 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:苗继方
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-03-17 10:10:05
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BpmFormDefManager extends Manager<String, BpmFormDef>{

	/**
	 * 根据bo定义ID获取对应的表单元数据定义。
	 * @param BODefId
	 * @return
	 */
	List<BpmFormDef> getByBODefId(String BODefId);

	/**
	 * 根据表单key获取表单元数据定义。
	 * @param formKey
	 * @return
	 */
	BpmFormDef getByKey(String formKey);

	/**
	 * 更新表单意见配置。
	 * @param id
	 * @param config
	 */
	void updateOpinionConf(String id, String config);
	
	/**
	 * 根据表单获取表单元数据key。
	 * @param formKey
	 * @return
	 */
	String getMetaKeyByFormKey(String formKey);

	List<String> getBOCodeByFormId(String formDefId);
}
