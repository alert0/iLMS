package com.hanthink.gps.util.interceptor;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.hanthink.gps.pub.web.BaseActionSupport;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.exception.BizException;
import com.hanthink.gps.util.logger.Logger;

/**
 * 异常处理的共通拦截器类
 */
public class AppExceptionInterceptor extends AbstractInterceptor {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 936259200089705270L;

	static Logger logger = Logger.getLogger(AppExceptionInterceptor.class
			.getName());
	boolean isLogabled = true;

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = null;
		try {
			result = invocation.invoke();
		} catch (Exception e) {
			if (e.getCause() != null) {
				e = (Exception) e.getCause();
			}
			// 异常时，输出日志信息。
			doLog(invocation, e);

			// 被拦截的方法有返回值时
			if (hasPageBack(invocation)) {
				Map session = invocation.getInvocationContext().getSession();
				// 清除session内的用户信息
				session.remove(Constants.USER_KEY);
				// 返回错误画面
				return Action.ERROR;
			}

			if (invocation.getAction() instanceof BaseActionSupport) {
				// 根据异常，取得错误信息ID
				result = getResultFormException((BaseActionSupport) invocation
						.getAction(), e, result);
			} else {
				throw e;
			}
		}

		return result;
	}

	/**
	 * 检查被拦截的方法是否有返回值
	 * 
	 * @param invocation
	 * @return true:有返回值 false:无返回值
	 */
	private boolean hasPageBack(ActionInvocation invocation) {
		String methodName = invocation.getProxy().getMethod();
		Method method = getMethodByName(invocation.getAction().getClass(),
				methodName);
		if (method != null) {
			Class<?> returnType = method.getReturnType();
			if (returnType != null) {
				return returnType != void.class;
			}
		}
		return false;
	}

	private Method getMethodByName(Class<?> cls, String methodName) {
		while (cls != null && cls != Object.class) {
			for (Method mtd : cls.getDeclaredMethods()) {
				if (mtd.getName().equals(methodName)) {
					return mtd;
				}
			}
			cls = cls.getSuperclass();
		}
		return null;
	}

	/**
	 * 输出日志信息
	 * 
	 * @param invocation 调用者
	 * @param e 异常
	 */
	private void doLog(ActionInvocation invocation, Exception e) {
		if (!isLogabled) {
			return;
		}
		logger.error(e);
		ActionConfig config = invocation.getProxy().getConfig();
		String strActionName = "package: " + config.getPackageName() + " "
				+ config.getClassName() + ": " + config.getMethodName();
		logger.debug(e.getMessage() + "\n\t" + strActionName);
	}

	/**
	 * 根据异常，输出相应的错误信息到Response中
	 * @param invocation 调用者
	 * @param e 异常
	 * @return 结果
	 */
	private String getResultFormException(BaseActionSupport action, Exception e,
			String data) {
		String result = "";
		// 业务异常
		if(e instanceof BizException){
			result = String.format("{success:false,msg:'%1$s',type:'warn'}",e.getMessage());
		}else if(e instanceof DataAccessException){
			// 更新数据时违反了完整性
			if(e instanceof org.springframework.dao.DataIntegrityViolationException)
			{
				result = String.format("{success:false,msg:'%1$s',type:'err'}","信息已存在，请检查输入内容或与管理员取得联系。");	
			}
		}else{
			result = String.format("{success:false,msg:'%1$s',type:'err'}","系统发生错误，请与管理员取得联系。");
		}
		if (!StringUtil.isNullOrEmpty(result)) {
			// 输出错误信息到Response中到Response中
			action.write(StringUtil.formatMsg(result));
		}
		return null;
	}
}
