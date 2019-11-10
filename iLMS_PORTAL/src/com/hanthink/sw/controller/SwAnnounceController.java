package com.hanthink.sw.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hanthink.sw.manager.SwAnnounceManager;
import com.hanthink.sw.manager.SwUserManager;
import com.hanthink.sw.model.SwAnnounceModel;
import com.hanthink.util.constant.Constant;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.mini.web.util.AppFileUtil;
import com.hotent.mini.web.util.DefaultFileUtil;
import com.hotent.mini.web.util.FtpFileUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.org.persistence.model.User;
import com.hotent.sys.persistence.manager.FileManager;
import com.hotent.sys.persistence.model.DefaultFile;
import com.hotent.sys.util.ContextUtil;

/**
 * <p>
 * Title: SwAnnounceController.java<／p>
 * <p>
 * Description: 公告发布管理和公告查看controller<／p>
 * <p>
 * Company: hanthink<／p>
 * 
 * @author luoxq
 * @date 2018年10月15日
 */
@Controller
@RequestMapping("/sw/swAnnounce")
public class SwAnnounceController extends GenericController {

    @Resource
    private SwAnnounceManager swAnnounceManager;

    @Resource
    private SwUserManager manager;

    @Resource
    FileManager fileManager;

    // 附件保存路径
    // private String attachPath = AppFileUtil.getBasePath().replace("/", "\\");
    // //附件保存类型
    // private String saveType = AppFileUtil.getSaveType();

    static String FILE_NAME_STR = "";

    static String FILE_ID = "";

    // 因为文件名不能是|所以使用|分割多个文件名
    static String SEPARATOR = "||";

    private static Logger log = LoggerFactory.getLogger(SwAnnounceController.class);

    /**
     * @Title: queryJisoGroupPage
     * @Description: 分页查询公告发布管理
     * @param @param request
     * @param @param reponse
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return PageJson
     * @throws
     * @author luoxq
     * @date 2018年10月15日 下午4:06:29
     */
    @RequestMapping("queryJisoGroupPage")
    public @ResponseBody
    PageJson queryJisoGroupPage(HttpServletRequest request, HttpServletResponse reponse, @ModelAttribute("model")
    SwAnnounceModel model) throws Exception {
        DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage()
                .getPageSize()));

        // HttpSession session = request.getSession();
        // session.setAttribute(SessionKey.SW_SUPPLIER_GROUP_QUERYFILTER, model);
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        PageList<SwAnnounceModel> pageList = (PageList<SwAnnounceModel>) swAnnounceManager.queryJisoAnnouncePage(model, p);

        return new PageJson(pageList);
    }

    /**
     * <p>
     * Title: remove
     * </p>
     * <p>
     * Description:删除公告
     * </p>
     * 
     * @param request
     * @param response
     * @throws Exception
     * @authoer luoxq
     * @time 2018年10月16日 上午10:58:01
     */
    @RequestMapping("remove")
    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultMessage message = null;
        try {
            String noticeIdArr = RequestUtil.getString(request, "id");
            String[] noticeIds = noticeIdArr.split(",");
            swAnnounceManager.removeAndLogByIds(noticeIds, RequestUtil.getIpAddr(request));
            message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new Exception("系统错误,请联系管理员");
        }
        writeResultMessage(response.getWriter(), message);
    }

    /**
     * <p>
     * Title: saveGroup
     * </p>
     * <p>
     * Description: 保存新增和修改方法
     * </p>
     * 
     * @param request
     * @param response
     * @param swSupplierGroupModel
     * @throws Exception
     * @authoer luoxq
     * @time 2018年10月16日 上午9:44:20
     */
    @RequestMapping("save")
    public void saveGroup(HttpServletRequest request, HttpServletResponse response, 
    		@RequestBody SwAnnounceModel model) throws Exception {
        String resultMsg = null;
        String noticeId = model.getNoticeId();
        IUser user = ContextUtil.getCurrentUser();
        
        
        try {
        	if (null != model) {
				model.setFactoryCode(user.getCurFactoryCode());
			} 
            if (StringUtil.isEmpty(noticeId)) {
                List<SwAnnounceModel> list = swAnnounceManager.isTitleExists(model);
                if (list.size() > 0) {
                    resultMsg = "公告标题已存在";
                    writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
                    return;
                }
                model.setNoticeStatus(SwAnnounceModel.NOTICE_STATUS);// 新增的数据设置状态为0：创建
                model.setFactoryCode(user.getCurFactoryCode());
                model.setCreationUser(user.getAccount()); // 记录创建人
                swAnnounceManager.insertAnnounce(model);
                resultMsg = "添加成功";
            } else {
                model.setLastModifiedUser(user.getAccount());// 记录修改人
                swAnnounceManager.updateAndLog(model, RequestUtil.getIpAddr(request));
                resultMsg = "更新成功";
            }
            FILE_ID = "";
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new Exception("系统错误,请联系管理员");
        }
    }

    /**
     * <p>
     * Title: curdGetGroup
     * </p>
     * <p>
     * Description: 获取公告发布明细
     * </p>
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @authoer luoxq
     * @time 2018年10月16日 下午3:51:12
     */
    @RequestMapping("get")
    public @ResponseBody
    SwAnnounceModel curdGetGroup(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String noticId = RequestUtil.getString(request, "id");
        if (StringUtil.isEmpty(noticId)) {
            return new SwAnnounceModel();
        }
        try {
            return swAnnounceManager.get(noticId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new Exception("系统错误,请联系管理员");
        }

    }

    /**
     * <p>
     * Title: publish
     * </p>
     * <p>
     * Description: 发布公告
     * </p>
     * 
     * @param request
     * @param response
     * @throws Exception
     * @authoer luoxq
     * @time 2018年10月16日 上午10:57:51
     */
    @RequestMapping("publish")
    public void publish(HttpServletRequest request, HttpServletResponse response, @RequestBody
    List<SwAnnounceModel> list) throws Exception {
        ResultMessage message = null;
        try {
            swAnnounceManager.updatePublishNotice(list);
            message = new ResultMessage(ResultMessage.SUCCESS, "发布成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new Exception("系统错误,请联系管理员");
        }
        writeResultMessage(response.getWriter(), message);
    }

    /**
     * <p>
     * Title: queryJisoFeedbackPage
     * </p>
     * <p>
     * Description: 公告发布管理界面， 供应商反馈查看功能
     * </p>
     * 
     * @param request
     * @param reponse
     * @param model
     * @return
     * @throws Exception
     * @authoer luoxq
     * @time 2018年10月17日 上午11:23:00
     */
    @RequestMapping("queryJisoFeedbackPage")
    public @ResponseBody
    PageJson queryJisoFeedbackPage(HttpServletRequest request, HttpServletResponse reponse, SwAnnounceModel model) throws Exception {
        DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage()
                .getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        model.setAccount(user.getAccount());
        PageList<SwAnnounceModel> pageList = (PageList<SwAnnounceModel>) swAnnounceManager.queryJisoFeedbackPage(model, p);
        return new PageJson(pageList);
    }

    /**
     * <p>
     * Title: viewAnnounceJisoPage
     * </p>
     * <p>
     * Description: 公告查看界面分页查询
     * </p>
     * 
     * @param request
     * @param reponse
     * @param model
     * @return
     * @throws Exception
     * @authoer luoxq
     * @time 2018年10月16日 下午6:30:56
     */
    @RequestMapping("viewAnnounce")
    public @ResponseBody
    PageJson viewAnnounceJisoPage(HttpServletRequest request, HttpServletResponse reponse, @ModelAttribute("model")
    SwAnnounceModel model) throws Exception {
        DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage()
                .getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        model.setAccount(user.getAccount());
        model.setFactoryCode(user.getCurFactoryCode());
        model.setNoticeStatus(SwAnnounceModel.NOTICE_STATUS_YES); // 在公告查看界面只能查看到已发布的数据
        // SwUserModel userModel = manager.getUserModel(user.getAccount());
        try {
            if (IUser.USER_TYPE_SUPP.equals(user.getUserType())) {
                model.setSupplierNo(user.getAccount());
            }
            PageList<SwAnnounceModel> pageList = (PageList<SwAnnounceModel>) swAnnounceManager.viewAnnounceJisoPage(model, p);
            return new PageJson(pageList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new Exception("系统错误,请联系管理员");
        }

    }

    /**
     * @Description: 查询出当前供应商有几条未查看公告
     * @param @param request
     * @param @param reponse
     * @param @param model
     * @param @return
     * @param @throws Exception
     * @return PageJson
     * @throws
     * @author luoxq
     * @date 2018年11月12日 下午1:50:15
     */
    @RequestMapping("viewAnnounceCount")
    public @ResponseBody
    List<SwAnnounceModel> viewAnnounceCount(HttpServletRequest request, HttpServletResponse reponse, SwAnnounceModel model) throws Exception {
        IUser user = ContextUtil.getCurrentUser();
        model.setViewStatus(Constant.SW_NOTICE_VIEW_STATUS_NO);
        model.setFactoryCode(user.getCurFactoryCode());
        model.setAccount(user.getAccount());

        // 如果用户非供应商用户，则返回空集合长度为0
        // SwUserModel userModel = manager.getUserModel(user.getAccount());
        // if (null != userModel && IUser.USER_TYPE_SUPP.equals(user.getUserType())) {
        // return new ArrayList<SwAnnounceModel>();
        // }
        try {
            List<SwAnnounceModel> list = swAnnounceManager.getListCount(model);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new Exception("系统错误,请联系管理员");
        }

    }

    /**
     * <p>
     * Title: feedBack
     * </p>
     * <p>
     * Description: 公告查看界面反馈功能
     * </p>
     * 
     * @param request
     * @param response
     * @param model
     * @throws Exception
     * @authoer luoxq
     * @time 2018年10月17日 上午9:35:52
     */
    @RequestMapping("feedBack")
    public void feedBack(HttpServletRequest request, HttpServletResponse response, @RequestBody
    SwAnnounceModel model) throws Exception {
        String resultMsg = null;
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        try {
        	if(IUser.USER_TYPE_SUPP.equals(user.getUserType())) {
        		model.setLastModifiedUser(user.getAccount());
        		model.setSupplierNo(user.getAccount());           
        		/** 设置公告查看状态为已查看 **/
                model.setViewStatus(Constant.SW_NOTICE_VIEW_STATUS_FEEDBACK_YES);
                swAnnounceManager.updateNoticeView(model);
                resultMsg = "反馈成功";
        	}else {
        		resultMsg = "厂内用户不可反馈";
        	}
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new Exception("系统错误,请联系管理员");
        }
    }

    @RequestMapping("viewBack")
    public void viewBack(HttpServletRequest request, HttpServletResponse response, SwAnnounceModel model) throws Exception {
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        try {
            /** 设置公告查看状态为已查看 **/
            model.setViewStatus(Constant.SW_NOTICE_VIEW_STATUS_YES);
            swAnnounceManager.updateNoticeView(model);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new Exception("系统错误,请联系管理员");
            // resultMsg="操作失败";
            // writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
        }
    }

    /**
     * @Description: 公告查看后修改公告查看状态
     * @param @param request
     * @param @param response
     * @param @param model
     * @param @throws Exception
     * @return void
     * @throws
     * @author luoxq
     * @date 2018年11月12日 下午7:16:14
     */
    @RequestMapping("updateDetail")
    public void updateDetail(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("model")
    SwAnnounceModel model) throws Exception {
        String resultMsg = null;
        IUser user = ContextUtil.getCurrentUser();
        // 当用户类型为供应商时更新公告查看状态
        if (Constant.PUB_USER_FLAG_SU.equals(user.getUserType())) {
            try {
            	model.setSupplierNo("");
                model.setSupplierNo(user.getAccount());
                model.setLastModifiedUser(user.getAccount());// 记录修改人
                /** 设置公告查看状态为已查看 **/
                model.setViewStatus(Constant.SW_NOTICE_VIEW_STATUS_YES);
                swAnnounceManager.updateDetail(model);
                resultMsg = "查看成功";
                writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.toString());
                throw new Exception("系统错误,请联系管理员");
                // resultMsg="查看啊失败";
                // writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
            }
        }
    }

    /**
     * @Description: 附件上传
     * @param @param request
     * @param @param response
     * @param @param fileId
     * @return void
     * @throws Exception
     * @throws
     * @author luoxq
     * @date 2018年11月6日 下午1:57:41
     */
    @RequestMapping("uploadFile")
    public void uploadFile(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
        // 附件保存路径
        // String attachPath = AppFileUtil.getBasePath().replace("/", "\\");
        // 附件保存类型
        // String saveType = AppFileUtil.getSaveType();

        // FileManager fileManager = new FileManagerImpl();
        PrintWriter writer = response.getWriter();
        try {
            IUser user = ContextUtil.getCurrentUser();
            String uploadType = RequestUtil.getString(request, "uploadType"); // 控件的类型
            String fileFormates = RequestUtil.getString(request, "fileFormates"); // 格式要求
            // String oldFileId = RequestUtil.getString(request, "fileId");
            String storeType = RequestUtil.getString(request, "storeType", AppFileUtil.SAVE_TYPE); // 文件存储类型
            boolean mark = true;
            Map<String, MultipartFile> files = request.getFileMap();
            Iterator<MultipartFile> it = files.values().iterator();

            while (it.hasNext()) {
                String fileId = UniqueIdUtil.getSuid();
                MultipartFile f = it.next();
                String oriFileName = f.getOriginalFilename();
                String extName = FileUtil.getFileExt(oriFileName);

                if (StringUtil.isNotEmpty(fileFormates)) { // 文件格式要求
                    if (!(fileFormates.contains("*." + extName))) { // 不符合文件格式要求的就标志为false
                        mark = false;
                    }
                }
                if (mark) {
                    String fileName = fileId + "." + extName;
                    String filePath = "";
                    DefaultFile file = new DefaultFile();
                    String path = "";
                    if ("pictureShow".equals(uploadType)) { // pictureShow控件要的文件路径要有一点不同
                        filePath = AppFileUtil.createFilePath(AppFileUtil.ATTACH_PATH + File.separator + ContextUtil.getCurrentUser().getAccount()
                                + File.separator + "pictureShow", fileName, DefaultFile.FILE_STORE_DISK.equals(storeType));
                        path = AppFileUtil
                                .createFilePath(ContextUtil.getCurrentUser().getAccount() + File.separator + "pictureShow", fileName, false);
                    } else {
                        filePath = AppFileUtil.createFilePath(
                                AppFileUtil.ATTACH_PATH + File.separator + ContextUtil.getCurrentUser().getAccount(),
                                fileName,
                                DefaultFile.FILE_STORE_DISK.equals(storeType));
                        path = AppFileUtil.createFilePath(ContextUtil.getCurrentUser().getAccount(), fileName, false);
                    }
                    // 开始写入物理文件
                    if (storeType.equals(DefaultFile.FILE_STORE_DB)) { // 二进制流动保存到数据库中
                        file.setBytes(f.getBytes());
                        file.setStoreType(DefaultFile.FILE_STORE_DB);
                    } else if (storeType.equals(DefaultFile.FILE_STORE_FTP)) { // 文件保存到FTP服务器
                        file.setStoreType(DefaultFile.FILE_STORE_FTP);
                        FtpFileUtil ftputil = new FtpFileUtil();
                        path = AppFileUtil.createFilePath(ContextUtil.getCurrentUser().getAccount(), "", false);
                        ftputil.upLoadFile(f.getInputStream(), path, fileName);
                    } else {
                        file.setStoreType(DefaultFile.FILE_STORE_DISK);
                        FileUtil.writeByte(filePath, f.getBytes());
                    }
                    // 文件获取到的文件id和文件名称赋值到新增中的文件id和名称中
                    FILE_ID = fileId;
                    FILE_NAME_STR = oriFileName;
                    // end 写入物理文件
                    // 向数据库中添加数据

                    file.setId(fileId);
                    // 附件名称
                    file.setFileName(oriFileName.substring(0, oriFileName.lastIndexOf('.')));
                    file.setFilePath(path); // 保存相对路径
                    // 上传时间
                    file.setCreateTime(new java.util.Date());
                    // 扩展名
                    file.setExt(extName);
                    // 字节总数
                    file.setByteCount(f.getSize());
                    // 说明
                    file.setNote(FileUtil.getSize(f.getSize()));
                    // 当前用户的信息
                    if (user != null) {
                        file.setCreator(user.getFullname());
                        file.setCreateOrgId(user.getUserId());
                    } else {
                        file.setCreatorName(DefaultFile.FILE_UPLOAD_UNKNOWN);
                    }
                    // 总的字节数
                    file.setIsDel(DefaultFile.FILE_NOT_DEL);
                    // 更新情况
                    // if(StringUtil.isNotEmpty(oldFileId) && files.size()==1 && (!"undefined".equals(oldFileId))
                    // && (!"null".equals(oldFileId))){
                    // //删除旧文件
                    // DefaultFile DefaultFile = fileManager.get(oldFileId);
                    // String oldfilePath = DefaultFile.getFilePath();
                    // oldfilePath = oldfilePath.replace('\\', '/');
                    // if (StringUtil.isEmpty(attachPath)) {
                    // oldfilePath = AppFileUtil.getRealPath(oldfilePath);
                    // }
                    // FileUtil.deleteFile(attachPath + File.separator + oldfilePath);
                    //
                    // file.setId(oldFileId);
                    // fileManager.update(file);
                    // }else{
                    //
                    // }
                    fileManager.create(file);
                    writer.println("{\"success\":true,\"fileId\":\"" + file.getId() + "\",\"fileName\":\"" + oriFileName + "\"}");
                } else {
                    writer.println("{\"success\":false,\"msg\":\"系统不允许该类型文件的上传！:" + extName + "\"}");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            writer.println("{\"success\":false,\"msg\":\"" + e.getMessage() + "\"}");
            throw new Exception("系统错误,请联系管理员");
        }

    }

    /**
     * @Description: 清除附件
     * @param @param request
     * @param @param response
     * @return void
     * @throws
     * @author luoxq
     * @date 2018年11月20日 下午2:49:05
     */
    @RequestMapping("removeFile")
    public void removeFile(HttpServletRequest request, HttpServletResponse response) {
        FILE_ID = "";
        FILE_NAME_STR = "";
    }

    /**
     * @Description: 下载附件后更新下载状态
     * @param @param request
     * @param @param response
     * @return void
     * @throws Exception
     * @throws
     * @author luoxq
     * @date 2018年12月21日 下午4:19:16
     */
    @RequestMapping("updateDownloadStatus")
    public void updateDownloadStatus(HttpServletRequest request, HttpServletResponse response, 
    		SwAnnounceModel model) throws Exception {
        try {
            if (null != model) {
                model.setDownloadStatus(SwAnnounceModel.DOWNLOAD_STATUS_YES);// 修改下载状态为已下载
                if(User.USER_TYPE_SUPP.equals(ContextUtil.getCurrentUser().getUserType())) {
                	model.setSupplierNo(ContextUtil.getCurrentUser().getAccount());
                	swAnnounceManager.updateDownloadStatus(model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new Exception("系统错误,请联系管理员");
        }
    }

    /**
     * @Description: 公告置顶
     * @param @param request
     * @param @param response
     * @return void
     * @throws Exception
     * @throws
     * @author luoxq
     * @date 2018年12月25日 下午6:25:06
     */
    @RequestMapping("upIndex")
    public void upIndex(HttpServletRequest request, HttpServletResponse response, SwAnnounceModel model) throws Exception {
        String resultMsg = null;
        String stickNo = null;
        try {
            if (SwAnnounceModel.IS_STICK_YES.equals(model.getIsStick())) {
                stickNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            }
            model.setStickNo(stickNo);
            swAnnounceManager.upIndex(model);
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new Exception("系统错误,请联系管理员");
            // resultMsg="操作失败";
            // writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
        }
    }

    /**
     * @Description: 根据供应商代码查询该供应商是否有新的公告
     * @param: @param request
     * @param: @param response
     * @return: void
     * @author: dtp
     * @throws Exception
     * @date: 2019年1月3日
     */
    @RequestMapping("queryExistsNewAnnounce")
    public @ResponseBody
    PageJson queryExistsNewAnnounce(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("model")
    SwAnnounceModel model) throws Exception {
        // SwAnnounceModel model = new SwAnnounceModel();
        IUser user = ContextUtil.getCurrentUser();
        if (null != model) {
			model.setFactoryCode(user.getCurFactoryCode());
		}
        model.setAccount(user.getAccount());
        model.setNoticeStatus(SwAnnounceModel.NOTICE_STATUS_YES); // 供应商只能查看已发布的公告
        PageList<SwAnnounceModel> pageList = new PageList<>();
        if (Constant.PUB_USER_FLAG_SU.equals(user.getUserType())) {
            try {
                // 添加供应商代码
                if (IUser.USER_TYPE_SUPP.equals(user.getUserType())) {
                    model.setSupplierNo(user.getAccount());
                }
                pageList = swAnnounceManager.queryExistsNewAnnounce(model);

            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.toString());
                throw new Exception("系统错误,请联系管理员");
            }
        }
        return new PageJson(pageList);
    }

    /**
     * 公告信息附件导出
     * 
     * @param request
     * @param response
     */
    @RequestMapping("downloadSignProFile")
    public void downloadSignProFile(HttpServletRequest request, HttpServletResponse response) {

        // 文件ID
        String id = RequestUtil.getString(request, "id");
        if (StringUtil.isEmpty(id)) {
            try {
                ExcelUtil.downloadFileError(request, response, "获取参数失败");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        String[] idArr = id.split(",");
        if (1 == idArr.length) {
            exportSingleSignProFile(id, request, response);
        } else {
            // 多个文件ID，进行压缩打包下载
            exportMultSignProFile(idArr, request, response);
        }

    }

    /**
     * 单个签字提案文件导出
     * 
     * @param id
     * @param request
     * @param response
     * @author ZUOSL
     * @DATE 2019年1月8日 下午17:01:28
     */
    private void exportSingleSignProFile(String id, HttpServletRequest request, HttpServletResponse response) {
        DefaultFile file = fileManager.get(id);
        if (file == null) {
            try {
                ExcelUtil.downloadFileError(request, response, "获取文件信息失败");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            String fileName = file.getFileName() + "." + file.getExt();
            if (file.getStoreType().equals(DefaultFile.FILE_STORE_DB)) {
                RequestUtil.downLoadFileByByte(request, response, file.getBytes(), fileName);
            } else if (DefaultFile.FILE_STORE_FTP.equals(file.getStoreType())) {
                try {
                    RequestUtil.downLoadFileByFtp(request, response, file, fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.toString());
                    String msg = "download error!" + e.toString();
                    response.getOutputStream().write(msg.getBytes("utf-8"));
                }
            } else {
                String filePath = file.getFilePath();
                if (StringUtil.isEmpty(filePath)) {
                    try {
                        ExcelUtil.downloadFileError(request, response, "获取文件信息失败");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                String fullPath = StringUtil.trimSuffix(AppFileUtil.ATTACH_PATH, File.separator) + File.separator
                        + filePath.replace("/", File.separator);
                RequestUtil.downLoadFile(request, response, fullPath, fileName);
            }
        } catch (Exception e) {
            try {
                ExcelUtil.downloadFileError(request, response, "导出异常：" + e.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            log.error(e.toString());
            return;
        }
    }

    /**
     * 多个签字提案文件压缩打包导出
     * 
     * @param id
     * @param request
     * @param response
     * @author ZUOSL
     * @DATE 2019年1月8日 下午17:51:23
     */
    private void exportMultSignProFile(String[] idArr, HttpServletRequest request, HttpServletResponse response) {

        // 导出设置
        String ymdhms = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String exportFileName = "公告信息导出【" + ymdhms + "】.rar";
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String downName = null;
        try {
            if (request.getHeader("user-agent").toLowerCase().contains("msie")
                    || request.getHeader("user-agent").toLowerCase().contains("like gecko")) {
                downName = URLEncoder.encode(exportFileName, "UTF-8");
            } else {
                downName = new String(exportFileName.getBytes("UTF-8"), "ISO_8859_1");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error(e.toString());
            ExcelUtil.exportException(e, request, response);
            return;
        }
        response.setHeader("Content-disposition", "attachment; filename=" + downName);

        byte[] buffer = new byte[4096];
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("获取output异常：" + e.toString());
            return;
        }

        // 遍历文件ID，打包导出
        for (String id : idArr) {

            DefaultFile file = fileManager.get(id);
            if (null == file) {
                continue;
            }

            // 文件打包
            String tmpFileName = file.getFileName() + "." + file.getExt();
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DefaultFileUtil.exportFile(file, baos);
                InputStream in = new ByteArrayInputStream(baos.toByteArray());
                out.putNextEntry(new ZipEntry(tmpFileName));
                int len = 0;
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                out.closeEntry();
                in.close();
                baos.close();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("打包文件导出异常：" + e.toString());
                return;
            }
        }

        try {
            if (null != out) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("关闭out异常：" + e.toString());
            return;
        }
    }
}
