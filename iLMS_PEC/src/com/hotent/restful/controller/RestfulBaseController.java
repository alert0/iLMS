package com.hotent.restful.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.exception.RequiredException;
import com.hotent.base.exception.RestError;



@ControllerAdvice
public class RestfulBaseController {

	private static final String defaultMessage = "系统错误";
	private static final String defaultMoreInfoUrl = "http://www.hotent.com/";

	/**
	 * 获取当前登录用户的账号
	 * 
	 * @return
	 */
	protected String current() {
		String account = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
			Object details = auth.getPrincipal();

			if (details != null) {
				if (details instanceof UserDetails) {
					account = ((UserDetails) details).getUsername();
				}
			}
		}
		return account;
	}

	@ResponseBody
	@ExceptionHandler(SQLException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public RestError handleSQLException(HttpServletRequest request, SQLException ex) throws IOException {
		RestError restError = new RestError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(), "数据库错误！", "");
		ex.printStackTrace();
		return handleRestError(restError);
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public RestError handleException(HttpServletRequest request, Exception ex) throws IOException {
		RestError restError = new RestError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), "");
		ex.printStackTrace();
		return handleRestError(restError);
	}
	
	@ResponseBody
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public RestError handleNullPointerException(HttpServletRequest request,NullPointerException ex){
		RestError restError = new RestError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), "");
		ex.printStackTrace();
		return handleRestError(restError);
	}
	
	@ResponseBody
	@ExceptionHandler(RequiredException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public RestError handleRequiredException(HttpServletRequest request,RequiredException ex){
		RestError restError = new RestError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), "");
		return handleRestError(restError);
	}

	private RestError handleRestError(RestError restError) {
		if (restError == null)
			return restError;
		if (StringUtil.isEmpty(restError.getShortMessage())) {
			restError.setShortMessage(RestfulBaseController.defaultMessage);
		}
		if (StringUtil.isEmpty(restError.getMoreInfoUrl())) {
			restError.setMoreInfoUrl(RestfulBaseController.defaultMoreInfoUrl);
		}
		return restError;
	}

	private Map<String, Object> getModelMap() {
		return new HashMap<String, Object>();
	}

	/**
	 * 获取返回对象
	 * 
	 * @return
	 */
	public Map<String, Object> modelMap() {
		return getModelMap();
	}

	/**
	 * 获取返回对象
	 * 
	 * @param result
	 *            执行结果:true > 成功,false > 失败
	 * @return
	 */
	public Map<String, Object> modelMap(Boolean result) {
		Map<String, Object> modelMap = getModelMap();
		modelMap.put("result", result);
		return modelMap;
	}

	/**
	 * 获取返回对象(默认返回结果正常)
	 * 
	 * @param message
	 *            返回的消息
	 * @return
	 */
	public Map<String, Object> modelMap(String message) {
		Map<String, Object> modelMap = modelMap(true);
		modelMap.put("message", message);
		return modelMap;
	}

	/**
	 * 获取返回对象
	 * 
	 * @param result
	 *            执行结果:true > 成功,false > 失败
	 * @param message
	 *            返回的消息
	 * @return
	 */
	public Map<String, Object> modelMap(Boolean result, String message) {
		Map<String, Object> modelMap = modelMap();
		modelMap.put("result", result);
		modelMap.put("message", message);
		return modelMap;
	}

}
