package com.hotent.mini.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;

/**
 * 
 * <pre>
 * 描述：用来存放作为工具后台方法的地方
 * 构建组：x5-mini-platform
 * 作者：aschs
 * 邮箱:liyj@jee-soft.cn
 * 日期:2016年5月3日-下午5:17:09
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/system/util/")
public class UtilController extends GenericController {
	
	/**
	 * 这个控制器方法主要用来根据spring容器的ID返回配置的对象数据。
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getBean")
	public @ResponseBody Object getBean(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String beanId = RequestUtil.getString(request, "beanId");
		Object obj=null;
		if (StringUtil.isNotEmpty(beanId)) {
			obj=AppUtil.getBean(beanId);
		}
		return obj;
	}
}
