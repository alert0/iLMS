package com.hotent.form.persistence.manager;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.hotent.bo.api.model.BoData;


/**
 * 
 * <pre> 
 * 描述：form_bus_set 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:miaojf
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-08-23 13:54:13
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface FormBusManager{
	/**
	 * 通过 boKey，id获取bo数据
	 * @param boKey
	 * @param id
	 * @return
	 */
	BoData getBoData(String boKey, String id);
	
	/**
	 *  通过formKey json 保存bo数据
	 * @param formKey
	 * @param json
	 */
	void saveData(String formKey, String json);
	
	/**
	 * 通过formKey 删除业务数据
	 * @param aryIds
	 * @param formKey
	 */
	void removeByIds(String[] aryIds, String formKey);
	
	/**
	 * 通过 formKey 获取业务数据
	 * @param formKey
	 * @param param
	 * @return
	 */
	JSONArray getList(String formKey, Map<String, Object> param);
}
