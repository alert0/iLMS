package com.hanthink.sys.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.business.util.MakeQrcodeImages;
import com.hanthink.sys.manager.SysPdaUserManagerManager;
import com.hanthink.sys.model.SysPdaUserManagerModel;
import com.hanthink.util.md5.MD5;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.encrypt.EncryptUtil;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.mini.web.util.AppFileUtil;
import com.hotent.sys.persistence.manager.FileManager;
import com.hotent.sys.persistence.model.DefaultFile;
import com.hotent.sys.util.ContextUtil;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
	 * 分页查询PDA用户信息
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("curdlistJson")
	public @ResponseBody PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse,
			SysPdaUserManagerModel model) {
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			/**
			 * 判断当前登录人是否为超级用户
			 */
			if (ContextUtil.getCurrentUser().isAdmin()) {
				model.setOpeUser("admin");
			} else {
				model.setOpeUser(ContextUtil.getCurrentUser().getUserId());
			}
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<SysPdaUserManagerModel> pageList = (PageList<SysPdaUserManagerModel>) sysPdaUserManagerManager
					.querySysPdaUserManagerForPage(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存信息 @param request @param response @param mpResidual @throws Exception
	 * void @exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SysPdaUserManagerModel sysPdaUserManagerModel) throws Exception {
		String resultMsg = null;
		String id = sysPdaUserManagerModel.getUserId();
		try {
			if (StringUtil.isEmpty(id)) {
				/**
				 * 判断是否主键冲突
				 */
				Integer count = sysPdaUserManagerManager.selectPrimaryKey(sysPdaUserManagerModel);
				if (count > 0) {
					resultMsg = "该数据已存在,请输入不同的姓名";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
					return;
				} else {
					Integer pecCount = sysPdaUserManagerManager.selectPrimaryKeyPEC(sysPdaUserManagerModel);
					if (pecCount > 0) {
						resultMsg = "该数据已存在,请输入不同的姓名";
						writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
						return;
					}
					sysPdaUserManagerModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
					sysPdaUserManagerModel.setCreationUser(ContextUtil.getCurrentUser().getAccount());
					sysPdaUserManagerModel.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
					String password = sysPdaUserManagerModel.getUserPwd();
					String userPwd = MD5.md5(password).toLowerCase();
					String userPwdPEC = EncryptUtil.encryptSha256(password);
					sysPdaUserManagerModel.setUserPwd(userPwd);
					sysPdaUserManagerModel.setUserPwdPEC(userPwdPEC);
					sysPdaUserManagerManager.insertPEC(sysPdaUserManagerModel);
					resultMsg = "添加成功";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
				}
			} else {
				sysPdaUserManagerModel.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
				String password = sysPdaUserManagerModel.getUserPwd();
				String userPwd = MD5.md5(password).toLowerCase();
				String userPwdPEC = EncryptUtil.encryptSha256(password);
				sysPdaUserManagerModel.setUserPwd(userPwd);
				sysPdaUserManagerModel.setUserPwdPEC(userPwdPEC);
				sysPdaUserManagerManager.updateAndLog(sysPdaUserManagerModel, RequestUtil.getIpAddr(request));
				resultMsg = "更新成功";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
			}
		} catch (Exception e) {
			log.error(e.toString());
			if (StringUtil.isEmpty(id)) {
				resultMsg = "新增失败";
			} else {
				resultMsg = "修改失败";
			}
			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
		}
	}

	/**
	 * 批量删除记录 @param request @param response @throws Exception void @exception
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "ids");
			sysPdaUserManagerManager.removeAndLogByIds(aryIds, RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * @Description: 用户信息打印(A4)
	 * @param: @param
	 *             request
	 * @param: @param
	 *             response
	 * @return: void
	 * @author: dtp
	 * @throws IOException
	 * @throws Exception
	 * @date: 2018年9月28日
	 */
	@RequestMapping("userPrint")
	public void userPrint(HttpServletRequest request, HttpServletResponse response) throws IOException {
		/*
		 * 订单每页打印明细行数 int pageSize = 18; String userNameStr =
		 * RequestUtil.getString(request, "userNameStr"); String[] userNameArr =
		 * userNameStr.split(","); List<SysPdaUserManagerModel> list = null; try {
		 * 
		 * if (null != userNameArr && userNameArr.length > 0) { // 打印N张
		 * List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
		 * 
		 * DefaultPage p = new DefaultPage(new
		 * RowBounds(getQueryFilter(request).getPage().getStartIndex(),
		 * getQueryFilter(request).getPage().getPageSize())); for (int i = 0; i <
		 * userNameArr.length; i++) { SysPdaUserManagerModel sysPdaUserManagerModel =
		 * new SysPdaUserManagerModel();
		 * sysPdaUserManagerModel.setUserName(userNameArr[i]); 判断当前登录人是否为超级用户
		 * if(ContextUtil.getCurrentUser().isAdmin()) {
		 * sysPdaUserManagerModel.setOpeUser("admin"); }else {
		 * sysPdaUserManagerModel.setOpeUser(ContextUtil.getCurrentUser().getUserId());
		 * } sysPdaUserManagerModel.setFactoryCode(ContextUtil.getCurrentUser().
		 * getCurFactoryCode()); list =
		 * sysPdaUserManagerManager.querySysPdaUserManagerForPage(
		 * sysPdaUserManagerModel, p); String filenurl = FileUtil.getClassesPath() +
		 * File.separator + "template" + File.separator + "ireport" + File.separator +
		 * "pub" + File.separator + "USER_LABEL.jasper"; for (SysPdaUserManagerModel
		 * model : list) { String password = model.getUserPwd(); String userPwd =
		 * MD5.convertMD5(password); String code = model.getUserName() + "#" + userPwd;
		 * BufferedImage qrImg = MakeQrcodeImages.getQrCodeImage(code, "180", "180");
		 * model.setQRCode(qrImg); } InputStream file = new FileInputStream(filenurl);
		 * 
		 * JRDataSource jRDataSource = new JRBeanCollectionDataSource(list); JasperPrint
		 * jasperPrint = JasperFillManager.fillReport(file, null, jRDataSource);
		 * JasperPrintList.add(jasperPrint); } if (JasperPrintList.size() > 0) {
		 * response.setContentType("application/pdf");
		 * response.setHeader("Content-disposition", "inline;"); JRPdfExporter exporter
		 * = new JRPdfExporter(); OutputStream out = response.getOutputStream();
		 * exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
		 * JasperPrintList); exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
		 * out); exporter.exportReport(); } }
		 */

		String userNameStr = RequestUtil.getString(request, "userNameStr");
		String[] userNameArr = userNameStr.split(",");
		try {
			if (null != userNameArr && userNameArr.length > 0) {
				// 打印N张
				List<SysPdaUserManagerModel> list = new ArrayList<SysPdaUserManagerModel>();
				// 更新打印状态list
				List<SysPdaUserManagerModel> list_printInfo = new ArrayList<SysPdaUserManagerModel>();
				SysPdaUserManagerModel sysPdaUserManagerModel = new SysPdaUserManagerModel();
				Map<String, Object> parameters = new HashMap<String, Object>();
				for (int j = 0; j < userNameArr.length; j++) {
					sysPdaUserManagerModel.setUserName(userNameArr[j]);
					// 判断当前登录人是否为超级用户
					if (ContextUtil.getCurrentUser().isAdmin()) {
						sysPdaUserManagerModel.setOpeUser("admin");
					} else {
						sysPdaUserManagerModel.setOpeUser(ContextUtil.getCurrentUser().getUserId());
					}
					sysPdaUserManagerModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
					SysPdaUserManagerModel bean = sysPdaUserManagerManager
							.querySysPdaUserManagerLabel(sysPdaUserManagerModel);
					// 更新打印状态
					list_printInfo.add(sysPdaUserManagerModel);
					// 二维码
					String code = bean.getUserName() + "#" + bean.getUserPwd();
					bean.setQRCode(MakeQrcodeImages.getQrCodeImage(code, "100", "100"));
					list.add(bean);
				}
				JRDataSource jRDataSource;
				if (list.size() > 0) {
					jRDataSource = new JRBeanCollectionDataSource(list);
				} else {
					jRDataSource = new JREmptyDataSource();
				}
				String filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator + "ireport"
						+ File.separator + "pub" + File.separator + "USER_LABEL.jasper";
				InputStream file = new FileInputStream(filenurl);
				JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters, jRDataSource);
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "inline;");
				JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			try {
				throw new Exception("系统错误,请联系管理员");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

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
//				// 附件保存路径
//				String attachPath = AppFileUtil.getBasePath().replace("/", "\\");
//				List<SysPdaUserManagerModel> list = sysPdaUserManagerManager.queryOlderPicture(model);
//				DefaultFile defaultFile = fileManager.get(list.get(0).getImageId());
//				String filePath = defaultFile.getFilePath();
//				filePath = filePath.replace('\\', '/');
//				// 删除文件
//				if (StringUtil.isEmpty(attachPath)) {
//					filePath = AppFileUtil.getRealPath(filePath);
//				}
//				FileUtil.deleteFile(attachPath + File.separator + filePath);
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
