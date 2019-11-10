package com.hotent.mini.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.sys.persistence.model.DefaultFile;

/**
 * 
 * @Desc		: 默认文件操作工具类
 * @FileName	: DefaultFileUtil.java
 * @CreateOn	: 2018年11月28日 下午5:34:50
 * @author 		: ZUOSL
 *
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 
 */
public class DefaultFileUtil {
	
	/**
	 * 导出文件
	 * @param defaultFile
	 * @param os
	 * @author ZUOSL	
	 * @throws IOException 
	 * @DATE	2018年11月28日 下午5:36:44
	 */
	public static void exportFile(DefaultFile defaultFile, OutputStream outStream) throws Exception{
		if(null == defaultFile){
			return;
		}
		
		if(DefaultFile.FILE_STORE_DB.equals(defaultFile.getStoreType())){
			
			outStream.write(defaultFile.getBytes());
			
		}else if(DefaultFile.FILE_STORE_FTP.equals(defaultFile.getStoreType())){
			
			FtpFileUtil ftputil = new FtpFileUtil();
			ftputil.downLoadFile(outStream, defaultFile.getFilePath(), defaultFile.getId() + "." + defaultFile.getExt());
			
		}else if(DefaultFile.FILE_STORE_DISK.equals(defaultFile.getStoreType())){
			String filePath = defaultFile.getFilePath();
			String fullPath = StringUtil.trimSuffix(AppFileUtil.ATTACH_PATH, File.separator) + File.separator + filePath.replace("/", File.separator);
			FileInputStream in = new FileInputStream(fullPath);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = in.read(b)) > 0) {
				outStream.write(b, 0, i);
			}
			
		}
		
	}

}
