package com.hotent.mini.web.util;

import java.io.File;
import java.util.Calendar;

import javax.servlet.ServletContext;

import com.hotent.base.core.util.PropertyUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.sys.persistence.model.DefaultFile;
import com.hotent.sys.util.SysPropertyUtil;
 
/**
 * 对象功能:附件帮助
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zxh
 * 创建时间:2014-04-04 08:54:40
 */
public class AppFileUtil {
	
	/**
	 * 附件保存路径
	 */
	public static String ATTACH_PATH = SysPropertyUtil.getByAlias("file.upload", "D:\\x5\\file");	
	/**
	 * 附件保存类型
	 */
	public static String SAVE_TYPE = SysPropertyUtil.getByAlias("file.saveType", DefaultFile.FILE_STORE_DB);
	
	
	/**
	 * 创建文件目录
	 *            文件名称
	 * @return 文件的完整目录
	 */
	public static String createFilePath(String tempPath, String fileName) {
		return createFilePath(tempPath, fileName, true);
	}
	
	public static String createFilePath(String tempPath, String fileName, boolean mkdir) {
		Calendar cal = Calendar.getInstance();
		Integer year = cal.get(Calendar.YEAR); // 当前年份
		Integer month = cal.get(Calendar.MONTH) + 1; // 当前月份
		if(mkdir){
//			File one = new File(tempPath);
			File one = new File(tempPath +  File.separator + year +  File.separator + month);
			if (!one.exists()) {
				one.mkdirs();
			}
			return one.getPath() + File.separator + fileName;
		}else{
			return tempPath +  File.separator + year +  File.separator + month + File.separator + fileName;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(createFilePath("admin/t/", "ad.jpg", false));
	}
	
	/**
	 * 配置文件中获取文件上传路径
	 * 如果为空则采用默认路径/attachFiles/temp
	 * 这个路径返回没有/或\结尾。
	 * 
	 * @author hjx
	 * @version 创建时间：2013-11-4  下午3:46:28
	 * @return
	 */
	public static String getBasePath() {
		String attachPath=ATTACH_PATH;
		if (StringUtil.isEmpty(attachPath)) {
			attachPath = AppFileUtil.getRealPath("/attachFiles/temp");
		}
		attachPath=StringUtil.trimSufffix(attachPath, "\\") ;
		attachPath=StringUtil.trimSufffix(attachPath, "/") ;
		
		return attachPath;
	}
	/**
	 * 配置文件中获取文件存放的类型
	 * @author xcx
	 * @version 创建时间：2013-12-27  下午3:53:20
	 * @return
	 */
	public static String getSaveType() {
		String saveType = SAVE_TYPE;
		if (StringUtil.isEmpty(saveType)) {
			saveType = "Folder";
		}
		return saveType.trim().toLowerCase();
	}

	private static ServletContext servletContext;
	
	
	/**
	 * 
	 * @param servletContext
	 */
	public static void init(ServletContext _servletContext)
	{
		servletContext=_servletContext;
	}

	/**
	 * 在web环境中根据web页面的路径获取对应页面的绝对路径。
	 * @param path
	 * @return
	 */
	public static String getRealPath(String path){
		return servletContext.getRealPath(path);
	}
	
}
