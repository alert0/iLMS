package com.hotent.mini.controller.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.sys.persistence.model.Resources;

import net.sf.json.JSONArray;

/**
 * 
 * <pre>
 * 描述：sys_res_resources管理
 * 构建组：x5-bpmx-platform
 * 作者:zyp
 * 邮箱:zyp@jee-soft.cn
 * 日期:2014-1-10-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/system/resources")
public class ResourcesController extends GenericController {
 
 

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("resourcesIcons")
	public ModelAndView icons(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int iconType = RequestUtil.getInt(request, "iconType");
		String path = RequestUtil.getString(request, "path");
		String iconTypeStr = "";
		if (path != null && path != "") {
			iconTypeStr = path;
		} else {
			iconTypeStr ="/fonts/fonts.json";// Resources.ICON_PATH;
			if (iconType == 1)
				iconTypeStr = Resources.LOGO_PATH;
		}
		String iconPath = request.getSession().getServletContext()
				.getRealPath(iconTypeStr);
		Object[] iconList = getIconList(iconPath);
		return getAutoView()
				.addObject("iconList", iconList)
				.addObject("iconPath", iconTypeStr);
	}
	/**
	 * 系统logo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("resourcesLogo")
	public ModelAndView logo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String path = RequestUtil.getString(request, "path");
		String iconTypeStr = "";
		if (path != null && path != "") {
			iconTypeStr = path;
		} else {
			iconTypeStr =  Resources.LOGO_PATH;
		}
		String iconPath = request.getSession().getServletContext()
				.getRealPath(iconTypeStr);
		Object[] iconList = getLogoList(iconPath);
		return getAutoView()
				.addObject("iconList", iconList)
				.addObject("iconPath", iconTypeStr);
	}
	
	
	/**
	 * 跳转到排序页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("resourcesSortList")
	public ModelAndView sortList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return getAutoView();
	}
	
	
	
	/**
	 * 跳转到排序页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("resourcesMoveNode")
	public ModelAndView moveNode(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return getAutoView();
	}
	


	/**
	 * 给资源的图标设置对应的颜色图标。
	 * 
	 * @param list
	 * @param ctxPath
	 */
	public static void setIconColor(List<Resources> list, String color,
			String oldColor) {
		for (Iterator<Resources> it = list.iterator(); it.hasNext();) {
			Resources res = it.next();
			String icon = res.getIcon();
			if (StringUtil.isNotEmpty(icon)) {
				icon = icon.replaceAll(oldColor, color);
				res.setIcon(icon);
			}
		}
	}
	/**
	 * 图标文件列表。
	 * @return
	 */
	private String[] getLogoList(String iconPath) {
		File iconFolder = new File(iconPath);
		String[] fileNameList = iconFolder.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String ext = name.substring(name.lastIndexOf(".") + 1).toUpperCase();
				if (Resources.ICON_TYPE.contains(ext)) {
					return true;
				} else {
					return false;
				}
			}
		});
		return fileNameList;
	}
	
	
	/**
	 * 图标文件列表。
	 * 
	 * @return
	 */
	private Object[] getIconList(String iconPath) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			// 文件绝对路径改成你自己的文件路径
			FileReader fr = new FileReader(iconPath);
			 br = new BufferedReader(fr);

			String ls = "";
			while ((ls = br.readLine()) != null) {
				sb.append(ls);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return  JSONArray.fromObject(sb.toString()).toArray();
	}
	
}
