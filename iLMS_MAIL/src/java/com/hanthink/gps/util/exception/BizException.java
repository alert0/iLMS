package com.hanthink.gps.util.exception;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.LocalizedTextUtil;

public class BizException extends RuntimeException {

	private static final long serialVersionUID = 4338152847544945197L;
	
    /**
     * 构造函数
     * @param msg 信息ID
     */
	public BizException(String msgId, Object...params) {
		super(getText(msgId,params));
	}
	
	
	private static String getText(String msgId, Object...params){
		return String.format(LocalizedTextUtil.findDefaultText(msgId, ActionContext.getContext().getLocale()),params);
	}
	
//   public BizException(String msg,String type, Object...params) {
//		
//		super(msg);
//	}
}
