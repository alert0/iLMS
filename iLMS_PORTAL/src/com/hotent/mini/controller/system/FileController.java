package com.hotent.mini.controller.system;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.mini.web.util.AppFileUtil;
import com.hotent.mini.web.util.FtpFileUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.persistence.manager.FileManager;
import com.hotent.sys.persistence.model.DefaultFile;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：附件管理
 * 构建组：x5-bpmx-platform
 * 作者:csx
 * 日期:2014-1-10-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 * update : 20180912 ZUOSL 增加FTP文件存储类型
 * update : 20190115 ZUOSL 修改支持多文件上传
 */
@Controller
@RequestMapping("/system/file/")
public class FileController extends GenericController{
	private static Logger log = LoggerFactory.getLogger(FileController.class);
	
	@Resource
	FileManager fileManager;
	//附件保存路径
	private String attachPath = AppFileUtil.getBasePath().replace("/", "\\"); 
	//附件保存类型
	private String saveType = AppFileUtil.getSaveType();
	//文件ID前缀
	private String FILE_SUFFIX = "PORTAL";
	
	/**
	 * 附件列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		PageList<DefaultFile> fileList=(PageList<DefaultFile>)fileManager.query(queryFilter);
		return new PageJson(fileList);
	}
	
	/**
	 * 编辑附件信息页面
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("fileEdit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
		DefaultFile file=null;
		if(StringUtil.isNotEmpty(id)){
			file=fileManager.get(id);
		}
		return getAutoView().addObject("file", file).addObject("returnUrl", preUrl);
	}
	
	/**
	 * 系统用户信息明细页面
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("fileGet")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
		DefaultFile file=null;
		if(StringUtil.isNotEmpty(id)){
			file=fileManager.get(id);
		}
		return getAutoView().addObject("file", file).addObject("returnUrl", preUrl);
	}
	
	/**
	 * 保存系统用户信息
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @param file
	 * @throws Exception 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,DefaultFile file) throws Exception{
		String resultMsg=null;
		String id=file.getId();
		try {
			if(StringUtil.isEmpty(id)){
				file.setId(UniqueIdUtil.getSuid());
				fileManager.create(file);
				resultMsg="添加附件成功";
			}else{
				fileManager.update(file);
				resultMsg="更新附件成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds=RequestUtil.getStringAryByStr(request, "id");
			for (String id : aryIds) {
				DefaultFile file = fileManager.get(id);
				if(file.getStoreType().equals(DefaultFile.FILE_STORE_DISK)){
					String filePath = file.getFilePath();
					FileUtil.deleteFile(AppFileUtil.ATTACH_PATH + File.separator + filePath);
				}
			}
			fileManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除附件成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除附件失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 附件上传操作
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */ 
	@RequestMapping("fileUpload")
	public void fileUpload(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uploadType = RequestUtil.getString(request, "uploadType");               //控件的类型
			String fileFormates = RequestUtil.getString(request, "fileFormates");         //格式要求
			String oldFileId = RequestUtil.getString(request, "fileId");
			String storeType = RequestUtil.getString(request, "storeType", AppFileUtil.SAVE_TYPE); //文件存储类型
			boolean mark = true;
			Map<String, MultipartFile> files = request.getFileMap();
			Iterator<MultipartFile> it = files.values().iterator();

			while (it.hasNext()) {
				MultipartFile f = it.next();
				String oriFileName = f.getOriginalFilename();
				String extName = FileUtil.getFileExt(oriFileName);
				
				if(StringUtil.isNotEmpty(fileFormates)){            //文件格式要求
	                if( !( fileFormates.contains("*."+extName) ) ){       //不符合文件格式要求的就标志为false
	                	mark = false;
	                }
				}
				if(mark){
					DefaultFile file = new DefaultFile();
					dealSaveFile(f, uploadType, oldFileId, storeType, file, user, oriFileName, extName);
					writer.println("{\"success\":true,\"fileId\":\"" + file.getId() + "\",\"fileName\":\"" + oriFileName + "\"}");
				}else{
					writer.println("{\"success\":false,\"msg\":\"系统不允许该类型文件的上传！:"+extName+"\"}");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			writer.println("{\"success\":false,\"msg\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 处理和保存文件
	 * @param file
	 * @param user
	 * @param oriFileName
	 * @param extName
	 * @throws Exception 
	 * @DATE	2019年1月17日 上午9:18:52
	 */
	private void dealSaveFile(MultipartFile f, String uploadType, String oldFileId, String storeType,
			DefaultFile file, IUser user, String oriFileName, String extName) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("factoryCode", user.getCurFactoryCode());
		map.put("FILE_SUFFIX", FILE_SUFFIX);
		String fileSuffix = fileManager.getFileSuffixInParams(map); //从系统参数获取文件ID的前缀
		if (StringUtil.isEmpty(fileSuffix) || (!fileSuffix.matches("[0-9]+"))) { //如果系统参数中配置的值为字符串或者为空，则默认前缀为10
			fileSuffix = "10";
		}
		String fileId = fileSuffix + UniqueIdUtil.getSuid(); //前面添加10 区分门户的图片id和厂内的图片id
		String fileName = fileId + "." + extName;
		String filePath = "";
		String path = "";
		if("pictureShow".equals(uploadType)){            //pictureShow控件要的文件路径要有一点不同
			filePath = AppFileUtil.createFilePath(AppFileUtil.ATTACH_PATH  + File.separator + ContextUtil.getCurrentUser().getAccount() + File.separator +"pictureShow", 
					fileName, DefaultFile.FILE_STORE_DISK.equals(storeType));
			path = AppFileUtil.createFilePath(ContextUtil.getCurrentUser().getAccount() + File.separator +"pictureShow", 
					fileName, false); 
		}else{ 
			filePath = AppFileUtil.createFilePath(AppFileUtil.ATTACH_PATH + File.separator + ContextUtil.getCurrentUser().getAccount(), 
					fileName, DefaultFile.FILE_STORE_DISK.equals(storeType));
			path = AppFileUtil.createFilePath(ContextUtil.getCurrentUser().getAccount(), 
					fileName, false);
		}							
		// 开始写入物理文件
		if(storeType.equals(DefaultFile.FILE_STORE_DB)){       //二进制流动保存到数据库中
			file.setBytes(f.getBytes());
			file.setStoreType(DefaultFile.FILE_STORE_DB);
		}else if(storeType.equals(DefaultFile.FILE_STORE_FTP)){       //文件保存到FTP服务器
			file.setStoreType(DefaultFile.FILE_STORE_FTP);
			FtpFileUtil ftputil = new FtpFileUtil();
			path = AppFileUtil.createFilePath(ContextUtil.getCurrentUser().getAccount(), "", false);
			ftputil.upLoadFile(f.getInputStream(), path, fileName);
		}else{
			file.setStoreType(DefaultFile.FILE_STORE_DISK); 
			FileUtil.writeByte(filePath, f.getBytes());
		}
		// end 写入物理文件
		// 向数据库中添加数据
		file.setId(fileId);
		// 附件名称
		file.setFileName(oriFileName.substring(0, oriFileName.lastIndexOf('.')));
		file.setFilePath(path);  //保存相对路径
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
		
		//FTP上传ID更新错误，直接全部新增
		//更新情况
//		if(StringUtil.isNotEmpty(oldFileId) && files.size()==1){
//			//删除旧文件
//			DefaultFile DefaultFile = fileManager.get(oldFileId);
//			if(null != DefaultFile){
//				String oldfilePath = DefaultFile.getFilePath();
//				oldfilePath = oldfilePath.replace('\\', '/');
//				if (StringUtil.isEmpty(attachPath)) {
//					oldfilePath = AppFileUtil.getRealPath(oldfilePath);
//				}
//				FileUtil.deleteFile(attachPath + File.separator + oldfilePath);
//				
//				file.setId(oldFileId);
//				fileManager.update(file);
//			}else{
//				fileManager.create(file);
//			}
//			
//		}else{
//			fileManager.create(file);
//		}
		
		fileManager.create(file);
	}

	/**
	 * 多个附件一次提交上传操作
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2019年1月17日 上午9:13:32
	 */
	@RequestMapping("multipleFileUpload")
	public void multipleFileUpload(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uploadType = RequestUtil.getString(request, "uploadType");               //控件的类型
			String fileFormates = RequestUtil.getString(request, "fileFormates");         //格式要求
			String oldFileId = RequestUtil.getString(request, "fileId");
			String storeType = RequestUtil.getString(request, "storeType", AppFileUtil.SAVE_TYPE); //文件存储类型
			boolean mark = true;
			
			MultiValueMap<String, MultipartFile> files = request.getMultiFileMap();
			List<MultipartFile> fileList = null;
			if(null != files){
				fileList = files.get("file");
			}
			
			if(null == fileList || 0 >= fileList.size()){
				Map<String, MultipartFile> fileMap = request.getFileMap();
				if(null != fileMap){
					Iterator<MultipartFile> iterator = fileMap.values().iterator();
					fileList = new ArrayList<MultipartFile>();
					while (iterator.hasNext()) {
						fileList.add(iterator.next());
					}
				}
			}
			
			List<String> resultList = new ArrayList<String>();
			for(MultipartFile f : fileList) {
				String oriFileName = f.getOriginalFilename();
				String extName = FileUtil.getFileExt(oriFileName);
				
				if(StringUtil.isNotEmpty(fileFormates)){            //文件格式要求
	                if( !( fileFormates.contains("*."+extName) ) ){       //不符合文件格式要求的就标志为false
	                	mark = false;
	                }
				}
				if(mark){
					DefaultFile file = new DefaultFile();
					dealSaveFile(f, uploadType, oldFileId, storeType, file, user, oriFileName, extName);
					resultList.add("{\"success\":true,\"fileId\":\"" + file.getId() + "\",\"fileName\":\"" + oriFileName + "\"}");
				}else{
					resultList.add("{\"success\":false,\"msg\":\"系统不允许该类型文件的上传！:"+extName+"\"}");
				}
			}

			StringBuffer sbf = new StringBuffer();
			sbf.append("{\"success\":true,\"data\":[");
			for(int i = 0; i < resultList.size(); i ++){
				if(i > 0){
					sbf.append(",");
				}
				sbf.append(resultList.get(i));
			}
			sbf.append("]}");
			writer.println(sbf.toString());
		} catch (Exception e) {
			e.printStackTrace();
			writer.println("{\"success\":false,\"msg\":\""+e.getMessage()+"\"}");
		}
	}

	/**
	 * 附件下载
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("download")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 附件保存路径
		String id = RequestUtil.getString(request, "id","");
		DefaultFile file = fileManager.get(id);
		if (file == null) return;
		
		String fileName = file.getFileName() + "." + file.getExt();
		if(file.getStoreType().equals(DefaultFile.FILE_STORE_DB)){
			RequestUtil.downLoadFileByByte(request, response, file.getBytes(), fileName);
		}else if(DefaultFile.FILE_STORE_FTP.equals(file.getStoreType())){
			try {
				RequestUtil.downLoadFileByFtp(request, response, file, fileName);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.toString());
				String msg = "download error!" + e.toString();
				response.getOutputStream().write(msg.getBytes("utf-8"));
			}
		}else{
			String filePath = file.getFilePath();
			if (StringUtil.isEmpty(filePath))
				return;
			if (StringUtil.isEmpty(AppFileUtil.ATTACH_PATH)) {
				//TODO attachPath = AppUtil.getRealPath("/attachFiles/temp");
			}
			String fullPath = StringUtil.trimSuffix(AppFileUtil.ATTACH_PATH, File.separator) + File.separator + filePath.replace("/", File.separator);
			RequestUtil.downLoadFile(request, response, fullPath, fileName);
		}

	}
	
	/**
	 * 根据文件id取得附件数据。
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getFileById")
	public void getFileById(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String fileId = RequestUtil.getString(request, "fileId","");
		String type = RequestUtil.getString(request, "type");
		DefaultFile file = fileManager.get(fileId);
		if(file == null) return;
		if(file.getStoreType().equals(DefaultFile.FILE_STORE_DB)){
			response.getOutputStream().write(file.getBytes());
		}else if(DefaultFile.FILE_STORE_FTP.equals(file.getStoreType())){
			try {
				String fileName = file.getFileName() + "." + file.getExt();
				RequestUtil.downLoadFileByFtp(request, response, file, fileName);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.toString());
				String msg = "download error!" + e.toString();
				response.getOutputStream().write(msg.getBytes("utf-8"));
			}
		}else{
			String filePath = file.getFilePath();
			String fullPath = StringUtil.trimSuffix(AppFileUtil.ATTACH_PATH, File.separator) + File.separator + filePath.replace("/", File.separator);
			if("small".equals(type)){
				String filePrex = fullPath.substring(0, fullPath.lastIndexOf("."));  
				fullPath = filePrex + "_small" + fullPath.substring(filePrex.length()); 
			}
			byte[] bytes = FileUtil.readByte(fullPath);
			response.getOutputStream().write(bytes);
		}
	}
	
	/**
	 * 图片显示
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("showpicture")
	public void showpicture(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 附件保存路径
		String id = RequestUtil.getString(request, "id","");
		DefaultFile file = fileManager.get(id);
		if (file == null) return;
		String filePath = file.getFilePath();
		if (StringUtil.isEmpty(filePath))
			return;
		if (StringUtil.isEmpty(AppFileUtil.ATTACH_PATH)) {
			//TODO attachPath = AppUtil.getRealPath("/attachFiles/temp");
		}
		
		
		String ext = file.getExt().toLowerCase();
		String type = ext;
		if("jpg".equals(ext)){
			type = "jpeg";
		}
		response.setContentType("image/"+type); 
		OutputStream outp = response.getOutputStream();
		InputStream in = null;
		try {
			if(file.getStoreType().equals(DefaultFile.FILE_STORE_DB)){
				in = new ByteArrayInputStream(file.getBytes());
			}else if(DefaultFile.FILE_STORE_FTP.equals(file.getStoreType())){
				FtpFileUtil ftputil = new FtpFileUtil();
				ByteArrayOutputStream tmpOut = new ByteArrayOutputStream();
				ftputil.downLoadFile(tmpOut, file.getFilePath(), file.getId() + "." + file.getExt());
				in =new ByteArrayInputStream(tmpOut.toByteArray());
			}else{
				String fullPath = StringUtil.trimSuffix(AppFileUtil.ATTACH_PATH, File.separator) + File.separator + filePath.replace("/", File.separator);
				File _file = new File(fullPath);
				if (_file.exists()) {
					in = new FileInputStream(fullPath);
				}else {
					outp.write("File does not exist!".getBytes("utf-8"));
				}
			}
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = in.read(b)) > 0) {
				outp.write(b, 0, i);
			}
			outp.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
				in = null;
			}
			if (outp != null) {
				outp.close();
				outp = null;
			}
		}
		
	

	}
	

	/**
	 * 根据文件id取得附件数据。
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getFileType")
	public void getFileType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		try {
			String fileId = RequestUtil.getString(request, "fileId");
			DefaultFile DefaultFile = null;
			String type = "doc";
			if (StringUtil.isNotEmpty(fileId)) {
				DefaultFile = fileManager.get(fileId);
				type = DefaultFile.getExt().toLowerCase();
			}
			writer.print(type);
		} catch (Exception e) {
			writer.print("doc");
		} finally {
			writer.close();
		}
	}
	/**
	 * 删除附件。
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("delByFileId")
	public void delByFileId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String[] lAryId = RequestUtil.getStringAryByStr(request, "ids");
		try {
			if(!saveType.contains(DefaultFile.FILE_STORE_DB)){              //保存在服务器时才做文件删除
				for (String id : lAryId) {
					DefaultFile DefaultFile = fileManager.get(id);
					String filePath = DefaultFile.getFilePath();
					filePath = filePath.replace('\\', '/');
					// 删除文件
					if (StringUtil.isEmpty(attachPath)) {
						filePath = AppFileUtil.getRealPath(filePath);
					}
					FileUtil.deleteFile(attachPath + File.separator + filePath);
				}
			}
			// 删除数据库中数据（包括文件在数据库的二进制流）
			fileManager.removeByIds(lAryId);
			out.print("{\"success\":\"true\"}");
		} catch (Exception e) {
			out.print("{\"success\":\"false\"}");
		}

	}
}
