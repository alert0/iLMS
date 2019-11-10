/**
 * 广州宏天软件有限公司版权所有
 */
package com.hotent.bpmx.core.defxml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import com.hotent.bpmx.core.defxml.entity.ext.ExtDefinitions;

/**
 * @功能描述：根据各类的输入参数获得TDefinitions对象
 * @开发公司：广州宏天软件有限公司
 * @作者：Winston Yan
 * @邮箱：yancm@jee-soft.cn
 * @创建时间：2013-12-6 上午11:21:24
 */
public class DefXmlUtil {
	/**
	 * 根据配置文件的XML内容生成TDefinitions实体
	 * @param defXml
	 * @return
	 * @throws JAXBException
	 * @throws IOException 
	 */
	public static ExtDefinitions getDefinitionsByXml(String bpmnXml) throws JAXBException, IOException {
		InputStream is = new ByteArrayInputStream(bpmnXml.getBytes("UTF-8"));
		return getDefinitions(is);
	}	
	
	/**
	 * 根据输入流，创建流程对象
	 * @param is
	 * @return
	 * @throws JAXBException
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	private static ExtDefinitions getDefinitions(InputStream is) throws JAXBException, IOException{
		JAXBElement<ExtDefinitions> jAXBElement = (JAXBElement<ExtDefinitions>) unmarshall(is,
				com.hotent.bpmx.core.defxml.entity.ObjectFactory.class,
				com.hotent.bpmx.core.defxml.entity.activiti.ObjectFactory.class,
				com.hotent.bpmx.core.defxml.entity.omgdc.ObjectFactory.class,
				com.hotent.bpmx.core.defxml.entity.omgdi.ObjectFactory.class,
				com.hotent.bpmx.core.defxml.entity.bpmndi.ObjectFactory.class,
				com.hotent.bpmx.core.defxml.entity.ext.ObjectFactory.class
				);
		return jAXBElement.getValue();
	}

	
	
	@SuppressWarnings("unchecked")
	private static Object unmarshall(InputStream is,Class<? extends Object>... classes) throws JAXBException, IOException{
		JAXBContext jctx = ContextFactory.newInstance(classes);
		Unmarshaller unmarshaller = jctx.createUnmarshaller();
		Object obj = unmarshaller.unmarshal(is);
		return obj;
	}
	
}
