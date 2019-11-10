package com.hotent.bo.persistence.manager;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.manager.api.Manager;
import com.hotent.bo.api.model.BaseBoDef;
import com.hotent.bo.persistence.model.BODef;
import com.hotent.org.api.model.IUser;

/**
 * 
 * <pre>
 * 描述：xbo_def 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-05 09:58:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BODefManager extends Manager<String, BODef> {

	/**
	 * 根据defId名称获取整个实体定义。
	 * 
	 * @param defId
	 * @return
	 */
	BODef getByDefId(String defId);

	/**
	 * 根据定义名称获取整个实体定义。
	 * 
	 * @param name
	 * @return
	 */
	BODef getByDefName(String name);

	/**
	 * 根据XML解析成bo定义对象。
	 * 
	 * @param xml
	 * @return
	 */
	List<BaseBoDef> parseXml(String xml);

	/**
	 * 将BaseBoDef 序列化成XML字符串。
	 * 
	 * @param boDef
	 * @return
	 */
	String serialToXml(List<BaseBoDef> boDefs);

	/**
	 * 保存bo定义。 这个用于在导入的时候进行使用。
	 * 
	 * @param boDef
	 * @return 返回导入信息
	 */
	List<BaseBoDef> importBoDef(List<BaseBoDef> boDefs);

	/**
	 * controller调用的save方法 数据格式
	 * 
	 * <pre>
	 * {
	 *     "status": "normal",
	 *     "supportDb": "1",
	 *     "deployed": "0",
	 *     "description": "你",
	 *     "alias": "n",
	 *     "ents": [
	 *         {
	 *             "id": "10000016810011",
	 *             "packageId": "",
	 *             "name": "xx",
	 *             "desc": "学校",
	 *             "status": "enabled",
	 *             "createBy": "",
	 *             "createTime": "2016-04-11 11:03:47.0",
	 *             "isCreateTable": "1",
	 *             "dsName": "dataSource_Default",
	 *             "tableName": "W_xx",
	 *             "isExternal": "0",
	 *             "pk": "",
	 *             "fk": "",
	 *             "pkType": "",
	 *             "relation": "onetomany"
	 *         },
	 *         {
	 *             "id": "10000017000016",
	 *             "packageId": "",
	 *             "name": "class",
	 *             "desc": "班级",
	 *             "status": "enabled",
	 *             "createBy": "",
	 *             "createTime": "2016-04-13 18:09:00.0",
	 *             "isCreateTable": "1",
	 *             "dsName": "dataSource_Default",
	 *             "tableName": "W_class",
	 *             "isExternal": "0",
	 *             "pk": "",
	 *             "fk": "",
	 *             "pkType": "",
	 *             "relation": "onetomany"
	 *         },
	 *         {
	 *             "id": "10000016810001",
	 *             "packageId": "",
	 *             "name": "cswbsjy",
	 *             "desc": "测试外部数据源",
	 *             "status": "enabled",
	 *             "createBy": "",
	 *             "createTime": "2016-04-11 10:21:10.0",
	 *             "isCreateTable": "1",
	 *             "dsName": "dataSource_Default",
	 *             "tableName": "act_hi_attachment",
	 *             "isExternal": "1",
	 *             "pk": "ID_",
	 *             "fk": "",
	 *             "pkType": "varchar",
	 *             "relation": "main"
	 *         }
	 *     ]
	 * }
	 * </pre>
	 * 
	 * @param json
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	void save(String json);

	BODef getByAlias(String alias);

	JSONObject getBOJson(String id);

	/**
	 * 判断定义是否可编辑（就是判断有没有绑定表单）
	 * 
	 * @param id
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	boolean isEditable(String id);
	
	/**
	 * 根据表单key 获取关联的bo 列表。
	 * @param formKey
	 * @return
	 */
	List<BODef> getByFormKey(String formKey);

}
