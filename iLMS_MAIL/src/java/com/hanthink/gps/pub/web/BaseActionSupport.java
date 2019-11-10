package com.hanthink.gps.pub.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import net.sf.jxls.exception.ParsePropertyException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.hanthink.gps.pub.vo.BaseExcelExportEntity;
import com.hanthink.gps.pub.vo.BaseVO;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.DateUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.pub.vo.UserVO;

public class BaseActionSupport extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 7834736236105975470L;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected UserVO userInfo;
	private Vector<String> errMessage;
	private String focusId = "";
	protected int start;
	protected int limit;
	protected int preStart;
	protected String sort;
	protected String dir;
	
	public int getPreStart() {
		return preStart;
	}

	public void setPreStart(int preStart) {
		this.preStart = preStart;
	}

	public void setServletRequest(HttpServletRequest req) {
		this.request = req;
		this.session = request.getSession();
		this.userInfo = (UserVO) session.getAttribute(Constants.USER_KEY);
	}

	public void setServletResponse(HttpServletResponse res) {
		this.response = res;
	}

	public void setUserInfo(UserVO userInfo) {
		this.userInfo = userInfo;
	}

	public void writeError() {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write("{success:false,errors:true,msg:'");
			if (errMessage != null) {
				for (String msg : errMessage) {
					writer.write(msg);
					writer.write("<br/>");
				}
			}
			writer.write("'");
			writer.write(",focusId:'" + focusId);
			focusId = "";
			errMessage.clear();
			writer.write("'}");
			writer.flush();
			writer.close();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		}
	}

	public void writeJson(Object obj) {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			System.out.println(JSONUtil.serialize(obj));
			writer.write(JSONUtil.serialize(obj));
			writer.flush();
			writer.close();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void writeTreeJson(String obj) {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(obj);
			writer.flush();
			writer.close();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		} 
	}

	public void writeJson(Object obj, boolean isSuccess) {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			if (isSuccess) {
				writer.write("{success:true,data:");
			} else {
				writer.write("{success:false,errors:true,data:");
			}
			if (obj instanceof BaseVO) {
				writer.write(JSONUtil.serialize(obj, null, null, false, false));
			} else {
				writer.write(JSONUtil.serialize(obj));
			}
			writer.write("}");
			writer.flush();
			writer.close();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * pda Action 错误返回Json
	 * @param code
	 * @param message
	 * @author zuosl  2015-10-26
	 */
	public void writePdaErrorInfo(String code, String message){
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write("{success:false,code:\"");
			writer.write(code);
			writer.write("\",message:\"");
			writer.write(message);
			writer.write("\"}");
			writer.flush();
			writer.close();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		}
	}
	
	/**
	 * pda Action 数据返回Json
	 * @param code
	 * @param message
	 * @author zuosl  2015-10-26
	 */
	public void writePdaInfo(JSONObject data){
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("success", true);
			json.put("data", data);
			writer.write(json.toString());
			writer.flush();
			writer.close();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		}
	}

	public void write(String outStr) {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(outStr);
			writer.flush();
			writer.close();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		}
	}

	public void writeXml(String outStr) {
		try {
			response.setContentType("text/xml;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(outStr);
			writer.flush();
			writer.close();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		}
	}

	public void process(BaseExcelExportEntity entity, Workbook book) {
		response.setContentType("application/octet-stream");
		// 取得excel下载文件名
		String strFileName = (entity != null) ? entity.getDownloadFileName()
				: "sw";
		// 设置excel响应的header
		response.setHeader("Content-Disposition", "attachment; filename="
				+ formatFileName(strFileName));
		try {
			// 取得响应的输出流
			OutputStream out = response.getOutputStream();
			// 将excel的workbook信息写到输出流中
			book.write(out);
			// 清楚缓存区域、输出响应
			out.flush();
		} catch (ParsePropertyException e) {
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
	
	
	/**
	 * 文件下载错误提示
	 * @param code 错误代码
	 * @param param 参数
	 * @author zuosl 2016-4-12
	 */
	protected void downloadError(String code, String param){
		try {
			response.sendRedirect("/gps/comm/jsp/download_error.jsp?code="+code+"&param=" + param);
			return;
		} catch (IOException e) {
			LogUtil.error(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断是否有某个界面的按钮的权限
	 * @param pageId 界面权限代码
	 * @param btnId  界面按钮权限代码 
	 * @return
	 * @author zuosl 2016-4-26
	 */
	protected boolean hasPageBtnAuth(String pageId, String btnId){
		//pagesAuth = {'010101':',01010101,','010104':',01010401,','010103':',01010301,'};
		String pagesAuth = (String) session.getAttribute(Constants.USER_PAGES_AUTH_KEY);
		if(null != pagesAuth && JSONUtils.mayBeJSON(pagesAuth)){
			JSONObject json = JSONObject.fromObject(pagesAuth);
			String pageAuth = null;
			try {
				pageAuth = json.getString(pageId);
			} catch (Exception e) {
				pageAuth = null;
			}
			if(null != pageAuth && !"".equals(pageAuth.trim())){
				String btnIdS = "," + btnId + ",";
				if(-1 < pageAuth.indexOf(btnIdS)){
					return true;
				}
			}
		}
		return false;
	}

	protected void addError(String msgId, Object... params) {
		if (errMessage == null) {
			errMessage = new Vector<String>();
		}
		errMessage.add(String.format(getText(msgId), params));
	}

	/**
	 * 校验出错后,画面指定id的控件获取焦点
	 * 
	 * @param id
	 */
	protected void addFocus(String id) {
		focusId = id;
	}

	public boolean isInvalid() {
		if (errMessage != null && errMessage.size() > 0) {
			writeError();
			return true;
		}
		return false;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * 格式化文件名
	 * 
	 * @param string
	 *            fileName
	 * @return String 格式化的文件名
	 * */
	private String formatFileName(String fileName) {
		if (fileName == null) {
			fileName = "sw";
		} else if (fileName.length() > 12) {
			fileName = fileName.substring(0, 8) + "...";
		}
		String strFileName = fileName + "_"
				+ DateUtil.formatDate(new Date(), DateUtil.DB_TIME_PATTERN)
				+ ".xls";
		try {
			strFileName = new String(strFileName.getBytes("GB2312"),
					"ISO8859_1");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		return strFileName;
	}

	/**
	 * 获取客户端的ip地址
	 * 
	 * @param request
	 * @return
	 */
	protected String getIpAddr() {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		//如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串Ｉｐ值，取X-Forwarded-For中第一个非unknown的有效IP字符串。
	    // 如：X-Forwarded-For：192.168.1.110， 192.168.1.120， 192.168.1.130， 192.168.1.100用户真实IP为： 192.168.1.110
		return ip;
	}

	public String getSort() {
		return sort;
	}

	public String getDir() {
		return dir;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

}
