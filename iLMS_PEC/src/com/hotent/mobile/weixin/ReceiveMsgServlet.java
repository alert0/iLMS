package com.hotent.mobile.weixin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotent.mobile.weixin.encode.AesException;
import com.hotent.mobile.weixin.encode.WXBizMsgCrypt;
import com.hotent.sys.util.SysPropertyUtil;


public class ReceiveMsgServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7238640799352347838L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String token=SysPropertyUtil.getByAlias("token");
		String sCorpID=SysPropertyUtil.getByAlias("corpid");
		
		String sEncodingAESKey=SysPropertyUtil.getByAlias("sEncodingAESKey");
		
		System.err.println(token +"," + sCorpID +"," +sEncodingAESKey);
		try {
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, sEncodingAESKey, sCorpID);
			String msgSignature=req.getParameter("msg_signature");
			String timeStamp=req.getParameter("timestamp");
			String nonce=req.getParameter("nonce");
			String echoStr=req.getParameter("echostr");
			String rtn= wxcpt.VerifyURL(msgSignature, timeStamp, nonce, echoStr);
			resp.getWriter().print(rtn);
		} catch (AesException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
	

}
