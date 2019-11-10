package com.hotent.bo.api.bodef;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.hotent.bo.api.model.BaseBoDef;
import com.hotent.bo.api.model.BaseBoEnt;


/**
 * BO定义接口类。
 * <pre> 
 * 构建组：x5-bo-core
 * 作者：heyifan
 * 邮箱:heyf@jee-soft.cn
 * 日期:2014-1-26-上午10:45:00
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface BoDefService {

	/**
	 * 根据bo定义名称获取bo定义，这个定义是完整的BO定义结构，包括相关实体和属性定义。
	 * @param name
	 * @return
	 */
	BaseBoDef getByName(String name);
	
	/**
	 * 根据bo定义ID获取bo定义，这个定义是完整的BO定义结构，包括相关实体和属性定义。
	 * @param defId
	 * @return
	 */
	BaseBoDef getByDefId(String defId);
	
	
	/**
	 * 根据BO定义ID获取BO定义的XML数据结构。
	 * @param defId
	 * @return
	 * @throws JAXBException 
	 */
	String getXmlByDefId(String defId) throws JAXBException;
	
	/**
	 * 根据xml 返回实体定义。
	 * <pre>
	 * 解析XML 返回BaseBoDef 定义。
	 * </pre>
	 * @param xml
	 * @return
	 * @throws JAXBException 
	 * @throws UnsupportedEncodingException 
	 */
	BaseBoDef parseXml(String xml) throws UnsupportedEncodingException, JAXBException;
	
	/**
	 * 根据实体名称获取实体数据。 
	 * @param name
	 * @return 
	 * BaseBoEnt
	 */
	BaseBoEnt getEntByName(String name);

	/**
	 * 外部导入式更新 boDefs，
	 * @param boDefs
	 */
	List<BaseBoDef> importBoDef(List<BaseBoDef> boDefs);
	
	BaseBoDef getByAlias(String boCode);
	
}
