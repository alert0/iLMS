package com.hotent.bo.api.instance;

import java.util.List;
import java.util.Map;

import com.hotent.bo.api.model.BaseBoEnt;

/**
 * 获取子表数据接口，获取子表数据有可能会根据当前登录用户信息获取相应的数据。
 * 构建组：x5-bo-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-12-1-下午1:54:24
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface BoSubDataHandler {
	
	/**
	 * 根据主键获取子表数据。 
	 * @param tableName
	 * @param pk
	 * @return  List&lt;Map>
	 */
	List<Map<String,Object>> getSubDataByFk( BaseBoEnt boEnt,  Object fkValue);

}
