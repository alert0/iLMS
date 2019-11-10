package com.hanthink.sys.controller;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.sys.manager.SysPdaUserManagerManager;
import com.hanthink.sys.model.SysPdaUserManagerModel;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.mini.web.util.AppFileUtil;
import com.hotent.sys.persistence.manager.FileManager;
import com.hotent.sys.persistence.model.DefaultFile;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre>
 *  
 * 描述：
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/sys/sysPdaUser")
public class SysPdaUserManagerController extends GenericController {

	private static Logger log = LoggerFactory.getLogger(SysPdaUserManagerController.class);
		
	@Resource
	FileManager fileManager;
	
	@Resource
	SysPdaUserManagerManager sysPdaUserManagerManager;

	/**
	 * 将图片信息保存到系统参数
	 * @param request 
	 * @param response 
	 * @throws Exception void 
	 * @exception
	 */
	@RequestMapping("putImageId")
	public @ResponseBody void putImageId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * 获取图片Id
		 */
		String imageId = RequestUtil.getString(request, "imageId");
		SysPdaUserManagerModel model = new SysPdaUserManagerModel();
		model.setImageId(imageId);
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		try {
			/**
			 * 先查询系统参数表里面是否有图片信息
			 */
			Integer count = sysPdaUserManagerManager.queryPicture(model);
			if (count == 0) {
				/**
				 * 图片不存在，新增图片参数
				 */
				model.setCreationUser(ContextUtil.getCurrentUser().getAccount());
				sysPdaUserManagerManager.insertImageId(model);
				writeResultMessage(response.getWriter(), "上传图片成功", ResultMessage.SUCCESS);
			} else {
				/**
				 * 图片存在，根据之前的图片ID删除FTP服务器上的图片
				 */
				// 附件保存路径
				/*String attachPath = AppFileUtil.getBasePath().replace("/", "\\");
				List<SysPdaUserManagerModel> list = sysPdaUserManagerManager.queryOlderPicture(model);
				DefaultFile defaultFile = fileManager.get(list.get(0).getImageId());
				String filePath = defaultFile.getFilePath();
				filePath = filePath.replace('\\', '/');
				// 删除文件
				if (StringUtil.isEmpty(attachPath)) {
					filePath = AppFileUtil.getRealPath(filePath);
				}
				FileUtil.deleteFile(attachPath + File.separator + filePath);*/
				/**
				 * 更新图片ID
				 */
				model.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
				model.setLastModifiedIp(RequestUtil.getIpAddr(request));
				sysPdaUserManagerManager.updateImageIdAndLog(model, RequestUtil.getIpAddr(request));
				writeResultMessage(response.getWriter(), "更新图片成功", ResultMessage.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(), "上传图片失败", e.getMessage(), ResultMessage.FAIL);
		}
	}

	/**
	 * 查询图片ID
	 * @param request 
	 * @param response 
	 * @throws Exception void 
	 * @exception
	 */
	@RequestMapping("queryImageId")
	public @ResponseBody String queryImageId(HttpServletRequest request, HttpServletResponse response) {
		SysPdaUserManagerModel model = new SysPdaUserManagerModel();
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		try {
			/**
			 * 先查询系统参数表里面是否有图片信息
			 */
			Integer count = sysPdaUserManagerManager.queryPicture(model);
			if (count == 0) {
				writeResultMessage(response.getWriter(), "图片不存在", ResultMessage.FAIL);
			} else {
				List<SysPdaUserManagerModel> list = sysPdaUserManagerManager.queryOlderPicture(model);
				String olderImageId = list.get(0).getImageId();
				return olderImageId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
