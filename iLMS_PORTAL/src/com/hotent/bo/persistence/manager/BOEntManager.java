package com.hotent.bo.persistence.manager;

import java.sql.SQLException;

import com.hotent.base.manager.api.Manager;
import com.hotent.bo.persistence.model.BOEnt;

/**
 * 
 * <pre>
 * 描述：业务对象定义 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-05 09:58:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BOEntManager extends Manager<String, BOEnt> {

	/**
	 * 根据实体类创建物理表。
	 * 
	 * @param boEnt
	 * @throws SQLException
	 */
	void createTable(BOEnt boEnt) throws SQLException;

	/**
	 * 根据实体ID获取实体类和属性列表。
	 * 
	 * @param entId
	 * @return BOEnt
	 */
	BOEnt getById(String entId);

	/**
	 * controller调用的save方法 在这里面会顺带处理attr属性列表 在这里写是为了事务保护
	 * json数据格式:
	 * <pre>
	 * {
	 *     "status": "enabled",
	 *     "isExternal": "0",
	 *     "dsName": "dataSource_Default",
	 *     "isCreateTable": "0",
	 *     "attributeList": [
	 *         {
	 *             "isRequired": "0",
	 *             "dataType": "varchar",
	 *             "attrLength": "50",
	 *             "desc": "字段一",
	 *             "name": "zdy"
	 *         },
	 *         {
	 *             "isRequired": "0",
	 *             "dataType": "varchar",
	 *             "attrLength": "50",
	 *             "desc": "字段二",
	 *             "name": "zde"
	 *         }
	 *     ],
	 *     "desc": "啊啊啊",
	 *     "name": "aaa"
	 * }
	 * </pre>
	 * 
	 * @param json
	 *            void
	 */
	void save(String json) throws Exception;

	/**
	 * 根据名字返回bo实体对象，不包括属性等信息。
	 * @param name
	 * @return
	 */
	BOEnt getByName(String name);
	
	/**
	 * 获取实体是否可以被修改类型。
	 * 1: 绑定表单后可以增加列
	 * 0: 没有绑定表单可以任意修改。
	 * @param name	实体名称
	 * @return
	 */
	int getCanEditByName(String name);
	
	/**
	 * 根据ENT刷新表单元数据。
	 * <pre>
	 *  1.根据表获取表的元数据。
	 *  2.和当前的元数据进行比较。
	 *  3.删除元数据。
	 *  4.增加元数据。
	 * </pre>
	 * @param entId
	 */
	void reloadByEntId(String entId);
}
