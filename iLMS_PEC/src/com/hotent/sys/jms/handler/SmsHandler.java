package com.hotent.sys.jms.handler;

import java.util.List;

import javax.annotation.Resource;

import com.hotent.base.core.sms.AlidayuSetting;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.api.jms.handler.JmsHandler;
import com.hotent.sys.api.msg.constants.MsgType;
import com.hotent.sys.api.msg.model.DefaultMsgVo;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 短消息发送处理器。
 * 
 * <pre>
 * 构建组：x5-sys-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-12-16-上午11:48:37
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class SmsHandler implements JmsHandler<DefaultMsgVo> {
	
	@Resource
	AlidayuSetting alidayuSetting;
	
	@Override
	public String getType() {
		return MsgType.SMS.key();
	}
	@Override
	public boolean send(DefaultMsgVo vo) {
		if (BeanUtils.isEmpty(vo.getParms())|| BeanUtils.isEmpty(vo.getExtendVars())) return false;
		
		// 调用阿里大于
		List<IUser> recievers = vo.getReceivers();
		String content = vo.getContent();
		String templateCode = vo.getSmsTemplateNo();
		
		if (StringUtil.isEmpty(content) ||  BeanUtils.isEmpty(recievers)) return false;
		
		
		TaobaoClient client = new DefaultTaobaoClient(alidayuSetting.getUrl(), alidayuSetting.getAppkey(), alidayuSetting.getSecret());
		for (IUser user : recievers) {
			if (StringUtil.isEmpty(user.getMobile())) continue;
			
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			req.setExtend(alidayuSetting.getExtend());
			req.setSmsType("normal");
			req.setSmsFreeSignName(alidayuSetting.getFreeSignName());
			
			String parmString =TaoBaoUtil.buildParams(vo);
			req.setSmsParamString(parmString);
			req.setRecNum(user.getMobile());
			req.setSmsTemplateCode(templateCode);
			try {
				AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			} catch (ApiException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	@Override
	public String getTitle() {
		return "短信";
	}
	@Override
	public boolean getIsDefault() {
		return false;
	}
	@Override
	public boolean getSupportHtml() {
		return true;
	}
	public static void main(String[] args) {
		String appkey = "23441694";
		String secret = "df0917d44889bc15399074b035bf9285";
		String url = "http://gw.api.taobao.com/router/rest";
		String templateCode = "SMS_13410470";
		String extend = "123";
		com.taobao.api.TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend(extend);
		req.setSmsType("normal");
		req.setSmsFreeSignName("x5工作流");
		req.setSmsParamString("{taskSubject:'流程消息提醒'}");
		req.setRecNum("18617318865");
		req.setSmsTemplateCode(templateCode);
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			System.out.println(rsp.getBody());
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}
}
