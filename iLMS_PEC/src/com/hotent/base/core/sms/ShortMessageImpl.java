package com.hotent.base.core.sms;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.hotent.base.api.sms.IShortMessage;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.SoapUtil;
import com.hotent.base.core.util.string.StringUtil;

/**
 * IShortMessage的Soap实现类，实现短信的发送。 通过SmsFactoryBean实例化和使用。
 * 
 * @author RaiseDragon
 * 
 */
public class ShortMessageImpl implements IShortMessage {
	private static ShortMessageImpl instance;

	private static Lock lock = new ReentrantLock();

	private Properties configproperties;

	private String url;
	private String userID;
	private String account;
	private String password;
	@SuppressWarnings("unused")
	private String content = "";
	private String sendTime;
	private int sendType;
	private String postFixNumber;
	private int sign;

	/**
	 * 初始化url、userId、account、password、content、sendtime、sendtype、postFixNumber、
	 * sign连接soap的参数
	 */
	public void initial() {
		url = (String) configproperties.get("smsUrl");
		userID = (String) configproperties.get("userID");
		account = (String) configproperties.get("smsAccount");
		password = (String) configproperties.get("smsPassword");
		content = (String) configproperties.get("smsContent");
		sendTime = (String) configproperties.get("smsSendTime");
		sendType = Integer.parseInt((String) configproperties
				.get("smssendType"));
		postFixNumber = (String) configproperties.get("smsPostFixNumber");
		sign = Integer.parseInt((String) configproperties.get("smsSign"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hotent.core.sms.IShortMessage#sendSms(java.util.List,
	 * java.lang.String)
	 */
	@Override
	public boolean sendSms(List<String> mobiles, String message) {
		initial();
		String envelop = generateEnvelop(mobiles, message);
		Map<String, Object> map = SoapUtil.getResponse(url, envelop);
		return (Boolean) map.get("success");
	}

	/**
	 * 生成wsdl格式的消息信封
	 * 
	 * @param mobiles
	 *            接收短信的手机号码
	 * @param content
	 *            短信内容
	 * @return String类型的wsdl格式的消息信封
	 */
	private String generateEnvelop(List<String> mobiles, String content) {
		String phones = StringUtil.convertCollectionAsString(mobiles);
		String wsdlStr = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.sms.com\">"
				+ "<soapenv:Header/>"
				+ "<soapenv:Body>"
				+ "<soap:directSend>"
				+ "<soap:userID>"
				+ userID
				+ "</soap:userID>"
				+ "<soap:account>"
				+ account
				+ "</soap:account>"
				+ "<soap:password>"
				+ password
				+ "</soap:password>"
				+ "<soap:phones>"
				+ phones
				+ "</soap:phones>"
				+ "<soap:content>"
				+ content
				+ "</soap:content>"
				+ "<soap:sendTime>"
				+ sendTime
				+ "</soap:sendTime>"
				+ "<soap:sendType>"
				+ sendType
				+ "</soap:sendType>"
				+ "<soap:postFixNumber>"
				+ postFixNumber
				+ "</soap:postFixNumber>"
				+ "<soap:sign>"
				+ sign
				+ "</soap:sign>"
				+ "</soap:directSend>"
				+ "</soapenv:Body>"
				+ "</soapenv:Envelope>";
		return wsdlStr;
	}

	/**
	 * 获取单例对象
	 * 
	 * @return
	 */
	public static ShortMessageImpl getInstance() {
		if (instance != null)
			return instance;
		lock.lock();
		try {
			if (instance == null)
				instance = new ShortMessageImpl();
			instance.setConfigproperties((Properties) AppUtil
					.getBean("configproperties"));
		} finally {
			lock.unlock();
		}

		return instance;
	}

	public void setConfigproperties(Properties configproperties) {
		this.configproperties = configproperties;
	}
}
