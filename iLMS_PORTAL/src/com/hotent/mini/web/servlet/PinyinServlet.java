package com.hotent.mini.web.servlet;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotent.base.core.util.PinyinUtil;
import com.hotent.base.core.web.RequestUtil;

/**
 * 汉字转拼音
 * @author Administrator
 *
 */
public class PinyinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();	
		String Chinese = RequestUtil.getString(request, "Chinese");
		int type = RequestUtil.getInt(request, "type");
		//如果是类型是1 则为全拼，否则为首字母
		if(type == 1){
			out.print(PinyinUtil.getPinyin(Chinese));
		}else{
			out.print(PinyinUtil.getPinYinHeadChar(Chinese));
		}
	}
}