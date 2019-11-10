package com.hotent.mini.controller.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.web.RequestContext;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.persistence.manager.LogErrManager;
import com.hotent.sys.persistence.model.LogErr;
import com.hotent.sys.util.ContextUtil;

/**
 * 系统错误日志记录工具类
 * @author mikel
 *
 */
public class SysErrorUtil {

	private static LogErrManager manager = AppUtil.getBean(LogErrManager.class);
	
	/**
	 * 记录日志到数据库
	 * @param e
	 */
	public static void saveErrorLog(Throwable e) {
		try {
			e.printStackTrace();
			String error = ExceptionUtils.getFullStackTrace(e);

			LogErr logErr =  new LogErr();
			HttpServletRequest request = RequestContext.getHttpServletRequest();
			if (request != null) {
				String errorurl = request.getRequestURI();
			    String ip =RequestUtil.getIpAddr(request);
				logErr.setIp(ip);
				logErr.setUrl(StringUtils.substring(errorurl, 0, 1000));
			}
			IUser sysUser= ContextUtil.getCurrentUser();
			String account = "未知用户";
			if(BeanUtils.isNotEmpty(sysUser)){
				account= sysUser.getAccount();
			}
			logErr.setId(UniqueIdUtil.getSuid());
			logErr.setAccount(account);
			logErr.setContent(error);
			logErr.setCreateTime(new Date());
			
			manager.create(logErr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
