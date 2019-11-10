package com.hotent.mini.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.dom4j.Document;
import org.dom4j.Element;

import com.hotent.base.core.util.Dom4jUtil;
import com.hotent.base.core.util.string.StringUtil;

public class MessageTypeUtil {
	private Document doc=null;
	private static MessageTypeUtil config=null;
	
	private static Lock lock = new ReentrantLock();

	private MessageTypeUtil()
	{
		InputStream is = (InputStream) this.getClass().getClassLoader().getResourceAsStream("conf/messageType.xml");
		doc=Dom4jUtil.loadXml(is);
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 单例模式，获取类的示例。
	 * @return
	 */
	public static MessageTypeUtil getInstance()
	{
		if(config==null)
		{
			lock.lock();
			try{
				if(config==null)
					config=new MessageTypeUtil();
			}
			finally{
				lock.unlock();
			}
		}
		return config;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, String> getMap(String type){
		Map<String, String> msgMap=new HashMap<String, String>(); 
		Element root = doc.getRootElement();
		Element messageEl = (Element) root.selectSingleNode("message");
		List<Element> elements = messageEl.elements();
		for (int i = 0; i < elements.size(); i++) {
			Element message =  elements.get(i);
			if(StringUtil.isNotEmpty(type)){
				String typeValue = message.attributeValue(type);
				if ("1".equals(typeValue)) {
					String name = message.attributeValue("name");
					String key = message.attributeValue("key");
					msgMap.put(key, name);
				}
			}else {
				String name = message.attributeValue("name");
				String key = message.attributeValue("key");
				msgMap.put(key, name);
			}
			
		}
		return msgMap;
	}
	
	private  String getVal(String key){
		String template="message/msgtype[@key='%s']";
		String filter=String.format(template,key);
		Element root= doc.getRootElement();
		Element el=(Element)root.selectSingleNode(filter);
		if(el!=null)
			return el.attributeValue("name");
		return "系统消息";
	}
	
	public static Map<String, String> getDisPlayMsgType(){
		return MessageTypeUtil.getInstance().getMap("display");
	}
	
	
	public static Map<String, String> getReplyMsgType(){
		return MessageTypeUtil.getInstance().getMap("reply");
	}
	
	public static Map<String, String> getAllMsgType(){
		return MessageTypeUtil.getInstance().getMap("");
	}
	
	public  static String  getValue(String key) {
		return MessageTypeUtil.getInstance().getVal(key);
	}
	
	
	
	
	
	

}
