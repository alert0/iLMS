package com.hotent.base.api.query;

import java.util.List;
import java.util.Map;
import com.hotent.base.api.Page;

/**
 * 
 * <pre> 
 * 描述：组合条件查询过滤
 * 构建组：x5-base-api
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014-1-3-下午4:01:18
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface QueryFilter {
	/**
	 * 返回分页信息
	 * @return
	 */
	public Page getPage();
	/**
	 * 返回字段组合查询逻辑
	 * @return
	 */
	public FieldLogic getFieldLogic();

	/**
	 * 返回组合的参数映射
	 * @return
	 */
	public Map<String,Object> getParams();
	
	/**
	 * 返回字段排序列表
	 * @return
	 */
	public List<FieldSort> getFieldSortList();
	/**
	 * 添加自定义过滤条件（用于自动组装条件：whereSql）
	 * @param name
	 * @param obj
	 * @param queryType
	 */
	public void addFilter(String name,Object obj,QueryOP queryType);
	
	/**
	 * 添加自定义过滤条件（用于手动组装条件，在MAPPING文件判断用的参数）
	 * @param name
	 * @param obj
	 */
	public void addParamsFilter(String key,Object obj);
	
	/**
	 * 清空查询PAGE 根据过滤条件查询所有数据信息
	 * @author ZUOSL	
	 * @DATE	2018年9月3日 下午5:49:36
	 */
	public void clearPage();
	
	/**
	 * 设置Page信息
	 * @author ZUOSL	
	 * @DATE	2018年9月3日 下午6:28:33
	 */
	public void setPage(Page page);

}
