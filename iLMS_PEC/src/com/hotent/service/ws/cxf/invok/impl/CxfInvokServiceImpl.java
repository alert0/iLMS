package com.hotent.service.ws.cxf.invok.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.service.api.exception.InvokeException;
import com.hotent.service.api.model.InvokeCmd;
import com.hotent.service.api.model.InvokeResult;
import com.hotent.service.model.impl.DefaultInvokeResult;
import com.hotent.service.ws.cxf.invok.CxfInvokService;

@Service
public class CxfInvokServiceImpl implements CxfInvokService {
	private static Logger logger = Logger.getLogger(CxfInvokServiceImpl.class);
	private final static String PREFIX = "api";
	@Override
	public InvokeResult invoke(InvokeCmd invokeCmd) throws Exception {
		if(BeanUtils.isEmpty(invokeCmd)||!"webservice".equals(invokeCmd)){
			SOAPElement soapElement = getSOAPElement(invokeCmd);
			SOAPMessage requestMessage = buildRequest(soapElement,invokeCmd.getOperatorNamespace());
			//TODO 添加用户名和密码的配置界面，获取用户名和密码并设置到请求的header中
			//setAuthorization(requestMessage);
			out(requestMessage);
			SOAPMessage responseMessage = doInvoke(new URL(invokeCmd.getAddress()), requestMessage);
			return responseMessageHandler(responseMessage);
		}
		return null;
	}
	
	// 设置用户名和密码
	private void setAuthorization(SOAPMessage message){
		String loginPassword = "user:password";
	    message.getMimeHeaders().addHeader("Authorization", "Basic " + new String(Base64.encodeBase64(loginPassword.getBytes())));
	}
	
	//打印xml内容
	private void out(SOAPMessage message) throws Exception{
		Document doc = message.getSOAPPart().getEnvelope().getOwnerDocument();
		StringWriter output = new StringWriter();  
		TransformerFactory.newInstance().newTransformer().transform( new DOMSource(doc), new StreamResult(output));
		String responseXml = output.toString(); 
		logger.debug(responseXml);
	}
	
	//构建soap请求的xml
	private SOAPMessage buildRequest(SOAPElement soapElement,String namespace) throws SOAPException {
		// 创建消息工厂
		MessageFactory messageFactory = MessageFactory.newInstance();
		// 根据消息工厂创建SoapMessage
		SOAPMessage message = messageFactory.createMessage();
		// 创建soap消息主体
		SOAPPart soapPart = message.getSOAPPart();
		// 创建soap部分
		SOAPEnvelope envelope = soapPart.getEnvelope();
		// 可以通过SoapEnvelope有效的获取相应的Body和Header等信息
		SOAPBody body = envelope.getBody();
		body.addChildElement(soapElement);
		// Save the message
		message.saveChanges();
		return message;
	}
	
	//创建请求方法的element
	private SOAPElement getSOAPElement(InvokeCmd invokeCmd) throws Exception{
		String opratorName = invokeCmd.getOperatorName();
		String opratorNamespace = invokeCmd.getOperatorNamespace();
		SOAPFactory factory = SOAPFactory.newInstance();
		SOAPElement bodyElement;
		if(StringUtil.isNotEmpty(opratorNamespace)){
			bodyElement = factory.createElement(opratorName,CxfInvokServiceImpl.PREFIX,opratorNamespace);
			if(invokeCmd.getNeedPrefix()){
				bodyElement.addNamespaceDeclaration(CxfInvokServiceImpl.PREFIX,opratorNamespace);
			}
		}
		else{
			bodyElement = factory.createElement(opratorName);
		}
		String jsonParam = invokeCmd.getJsonParam();
		if(StringUtil.isNotEmpty(jsonParam)){
			jsonParam = jsonParam.trim();
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(jsonParam);
			setRequestStruct(json, bodyElement, invokeCmd.getNeedPrefix());
		}
		else{
			String xmlParam = invokeCmd.getXmlParam();
			if(StringUtil.isNotEmpty(xmlParam)){
				SOAPElement xmlSoapElement = getSOAPElementByString(xmlParam);
				bodyElement.addChildElement(xmlSoapElement);
			}
		}
		return bodyElement;
	}
	
	//xml结构的字符串构建为SOAPElement
	private SOAPElement getSOAPElementByString(String xml) throws Exception{
		//TODO 根据needPrefix 属性确认传入的xml结构是否正确处理了 前缀
		StringReader stringReader = new StringReader(xml);
        InputSource inputSource = new InputSource(stringReader);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        SAXParser parser = factory.newSAXParser();
        SoapElementSaxHandler handler = new SoapElementSaxHandler();
        parser.parse(inputSource, handler);
        return handler.getSOAPElement();
	}
	
	//创建参数节点及设置参数值
	private void setRequestStruct(JsonElement jsonElement,SOAPElement soapElement,Boolean needPrefix) throws SOAPException{
		if(jsonElement.isJsonArray()){
			JsonArray jarray = jsonElement.getAsJsonArray();
			int count = jarray.size();
			for(int i=0;i<count;i++){
				JsonElement jelement = jarray.get(i);
				if(i==0){
					setRequestStruct(jelement, soapElement,needPrefix);
				}
				else{
					//从第二个元素开始，需要克隆Node
					SOAPElement cloneNode = (SOAPElement)soapElement.cloneNode(false);
					soapElement.getParentElement().appendChild(cloneNode);
					setRequestStruct(jelement, cloneNode,needPrefix);
				}
			}
		}
		else if(jsonElement.isJsonObject()){
			JsonObject jobject = jsonElement.getAsJsonObject();
			Iterator<Entry<String, JsonElement>> it = jobject.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, JsonElement> entry = it.next();
				SOAPElement element = null;
				if(needPrefix){
					element = soapElement.addChildElement(entry.getKey(),CxfInvokServiceImpl.PREFIX);
				}
				else{
					element = soapElement.addChildElement(entry.getKey());
				}
				setRequestStruct(entry.getValue(), element,needPrefix);
			}
		}
		else if(jsonElement.isJsonPrimitive()){
			soapElement.setValue(jsonElement.getAsString());
//			soapElement.setTextContent(jsonElement.getAsString());
		}
	}
	
	private SOAPMessage doInvoke(URL invokeURL,SOAPMessage requestMessage) throws Exception{
		// 创建连接
		SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection connection = null;
		try {
			URL endPoint = new URL(null, invokeURL.toString(), new URLStreamHandler() {
				@Override
				protected URLConnection openConnection(URL u) throws IOException {
					URL clone_url = new URL(u.toString());
					HttpURLConnection clone_urlconnection = (HttpURLConnection) clone_url.openConnection();
						
					clone_urlconnection.setConnectTimeout(3000);
					clone_urlconnection.setReadTimeout(3000);
					return(clone_urlconnection);
				}
			});
			connection = soapConnFactory.createConnection();
			// 响应消息
			SOAPMessage reply = connection.call(requestMessage, endPoint);
			return reply;
		}catch(Exception ex){
			throw ex;
		}
		finally {
			if (connection != null)
				connection.close();
		}
	}
	
	//将返回的xml转换为json
	@SuppressWarnings("unchecked")
	private void buildResultJson(SOAPMessage message,DefaultInvokeResult invokeResult) throws Exception{
		SOAPBody body = message.getSOAPBody();
		Node reponseNode = body.getFirstChild();
		Node returnNode = reponseNode.getFirstChild();
		int len = 0;
		NodeList childNodes = reponseNode.getChildNodes();
		if(BeanUtils.isNotEmpty(childNodes)){
			len = childNodes.getLength();
		}
		StringWriter output = new StringWriter();
		TransformerFactory.newInstance().newTransformer().transform( new DOMSource(returnNode), new StreamResult(output));
		JSONObject jsonObject = XML.toJSONObject(output.toString());
		String json = "";
		Iterator<String> keys = jsonObject.keys();
		if(len==1&&keys.hasNext()){
			String key = keys.next();
			Object object = jsonObject.get(key);
			json = object.toString();
		}
		else if(len>1){
			json = jsonObject.toString();
		}
		invokeResult.setJson(json);
	}
	
	//处理调用的返回结果
	private InvokeResult responseMessageHandler(SOAPMessage responseMessage) throws Exception{
		checkFault(responseMessage);
		out(responseMessage);
		Node response = responseMessage.getSOAPBody().getFirstChild();
		Node result = response.getFirstChild(); 
		DefaultInvokeResult invokeResult = new DefaultInvokeResult();		
		if (BeanUtils.isEmpty(result)) {// 无返回值
			return invokeResult;
		}
		buildResultJson(responseMessage, invokeResult);
		
		String resultNodeName = result.getNodeName();
		Node nextSibling = result.getNextSibling();
		//返回值为复合类型集合
		if(BeanUtils.isNotEmpty(nextSibling)&&resultNodeName.equals(nextSibling.getNodeName())){
			NodeList results = response.getChildNodes();
			int count = results.getLength();
			//将返回值按照 Node 类型添加到List中
			List<Object> resultList = new ArrayList<Object>();
			for(int i=0;i<count;i++){
				resultList.add(results.item(i));
			}
			invokeResult.setList(resultList);
		}
		else{
			Node firstNode = result.getFirstChild();
			//返回值为纯文本
			if(firstNode instanceof Text){
				//invokeResult.setObject(firstNode.getTextContent());
				invokeResult.setObject(firstNode.getNodeValue());
			}
			else{
				String firstNodeName = firstNode.getNodeName();
				Node nextChild = firstNode.getNextSibling();
				//返回值为基础类型集合
				if(BeanUtils.isNotEmpty(nextChild)&&firstNodeName.equals(nextChild.getNodeName())){
					NodeList resultDetailList = result.getChildNodes();
					int count = resultDetailList.getLength();
					List<Object> list = new ArrayList<Object>();
					for (int i = 0; i < count; i++) {
						Node element = resultDetailList.item(i);
						list.add(element.getNodeValue());
					}
					invokeResult.setList(list);
				}
				//返回值为单个复合类型
				else{
					invokeResult.setObject(result);
				}
			}
		}
		return invokeResult;
	}
	
	//校验是否调用失败
	private void checkFault(SOAPMessage message) throws SOAPException, InvokeException {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		SOAPBody body = envelope.getBody();
		SOAPFault fault = body.getFault();
		if (fault != null && fault.getFaultCode() != null) {// 出现异常
			throw new InvokeException(fault.getFaultCode(), fault.getFaultString());
		}
	}
}
