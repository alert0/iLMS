package com.hotent.mini.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.sys.persistence.model.DefaultFile;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * 
 * @Desc : FTP文件管理工具类
 * @FileName : FtpFileUtil.java
 * @CreateOn : 2018年9月12日 下午2:55:36
 * @author : ZUOSL
 *
 *
 * @ChangeList Date Version Editor Reasons
 * 
 */
public class FtpFileUtil {

	private FTPClient ftpClient;
	/** 本地字符编码 */
	private static String LOCAL_CHARSET = "GBK";
	/** FTP协议里面，规定文件名编码为iso-8859-1 */
	private static String SERVER_CHARSET = "ISO-8859-1";
	/** FTP文件路径 */
	private String FTP_FILE_PATH;
	/** 上传IP */
	private String ip;
	/** PORT */
	private int port;
	/** 用户 */
	private String userName;
	/** 密码 */
	private String password;

	public FtpFileUtil(String ip, int port, String userName, String password) throws Exception {
		this.ip = ip;
		this.port = port;
		this.userName = userName;
		this.password = password;
		initClient();
	}

	public FtpFileUtil() throws Exception {
		this.ip = SysPropertyUtil.getByAlias("ftp.ip");
		this.port = Integer.valueOf(SysPropertyUtil.getByAlias("ftp.port"));
		this.userName = SysPropertyUtil.getByAlias("ftp.userName");
		this.password = SysPropertyUtil.getByAlias("ftp.password");
		initClient();
	}

	/**
	 * 初始化FTP CLIENT
	 * 
	 * @throws Exception
	 * @author ZUOSL
	 * @DATE 2018年9月12日 下午4:48:02
	 */
	private void initClient() throws Exception {
		ftpClient = new FTPClient();
		try {
			ftpClient.connect(this.ip, this.port);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("连接异常，请确认ftp服务" + e.toString());
		}
		int reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
			throw new Exception("FTP连接失败");
		}
		boolean flag = ftpClient.login(this.userName, this.password);
		if (!flag) {
			ftpClient.disconnect();
			throw new Exception("FTP登陆失败");
		}

		if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
			LOCAL_CHARSET = "UTF-8";
		}
		ftpClient.setControlEncoding(LOCAL_CHARSET);// 设置编码
		FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
		conf.setServerLanguageCode("zh");
		ftpClient.configure(conf);
		ftpClient.enterLocalPassiveMode();
		ftpClient.changeWorkingDirectory(FTP_FILE_PATH);// 切换工作路径，�?/�?表示根路�?
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// 设置文件类型
	}

	/**
	 * 上传时转换编码格式 utf-8(gbk) 转换为 iso-8859-1
	 * 
	 * @param serStr
	 * @return String
	 */
	public String toCliStr(String serStr) {
		try {
			return new String(serStr.getBytes(SERVER_CHARSET), LOCAL_CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 下载时转换编码格式 utf-8(gbk) 转换为 iso-8859-1
	 * 
	 * @param cliStr
	 * @return String
	 */
	public String toSerStr(String cliStr) {
		try {
			return new String(cliStr.getBytes(LOCAL_CHARSET), SERVER_CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 上传文件
	 * 
	 * @param fileName
	 *            需要上传的文件路径名
	 * @param ftpName
	 *            传入ftp时的文件名
	 * @return
	 * @throws Exception
	 */
	public boolean upLoadFile(InputStream fis, String ftpPath, String ftpName) throws Exception {

		if (null == ftpClient || !ftpClient.isConnected()) {
			initClient();
		}

		try {
			if (ftpPath != null && !"".equals(ftpPath)) {
				createDirectory(ftpPath);
			} else {
				ftpClient.changeWorkingDirectory("/");
			}
			return ftpClient.storeFile(ftpName, fis);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			closeFtp();
		}

	}

	/**
	 * 下载文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean downLoadFile(OutputStream fos, String ftpFilePath, String fileName) throws Exception {
		if (null == ftpClient || !ftpClient.isConnected()) {
			initClient();
		}
		if (null == ftpClient || null == fos) {
			return false;
		}

		boolean flag = true;
		try {
			if (ftpFilePath != null && !"".equals(ftpFilePath)) {
				createDirectory(ftpFilePath);
			} else {
				ftpClient.changeWorkingDirectory("/");
			}

			String encodeFileName = toSerStr(fileName);
			flag = ftpClient.retrieveFile(encodeFileName, fos);

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			throw e;
		} finally {
			closeFtp();
		}

		return flag;
	}

	/**
	 * 下载文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getFile(DefaultFile dfile) throws Exception {
		if (null == ftpClient || !ftpClient.isConnected()) {
			initClient();
		}
		if (null == ftpClient) {
			return null;
		}
		try {
			FtpFileUtil ftputil = new FtpFileUtil();
			File file=new File("D:\\mpVehPlanTemp_email");
			if(!file.exists()){//如果文件夹不存在
				file.mkdir();//创建文件夹
			}
			FileOutputStream fos = new FileOutputStream(new File("D:\\mpVehPlanTemp_email\\"+dfile.getFileName()+"."+dfile.getExt()));
			boolean b =ftputil.downLoadFile(fos, dfile.getFilePath(), dfile.getId() + "." + dfile.getExt());
			//System.out.println(b);
			if(fos!=null) {
			fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			closeFtp();
		}
		return "D:\\mpVehPlanTemp_email\\"+dfile.getFileName()+"."+dfile.getExt();
	}

	/**
	 * 下载文件
	 * 
	 * @return boolean
	 * @throws IOException
	 * @throws SocketException
	 */
	public boolean downLoadImage(String fileName, String FILE_PATH, String FTP_FILE_PATH)
			throws SocketException, IOException {

		String encodeFileName = toSerStr(fileName);
		if (ftpClient != null) {
			File fileFather = new File(FILE_PATH);
			if (!fileFather.exists()) {
				fileFather.mkdirs();
			}
			File file = new File(fileFather, fileName);
			if (FTP_FILE_PATH != null && !"".equals(FTP_FILE_PATH)) {
				createDirectory(FTP_FILE_PATH);
			} else {
				ftpClient.changeWorkingDirectory("/");
			}

			FTPFile[] fs = ftpClient.listFiles();
			boolean isExist = false;
			for (FTPFile ff : fs) {
				String fname = ff.getName();
				if (fname.equals(fileName)) {
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				closeFtp();
				return false;
			}
			// 如果下载的文件名不存在服务器，则会在客户端创建一个空的文件，且返回�?为真
			FileOutputStream fos = new FileOutputStream(file);
			boolean f = ftpClient.retrieveFile(encodeFileName, fos);
			fos.close();
			if (f == false) {
				// 关闭连接
				closeFtp();
				return false;
			} else {
				// 关闭连接
				closeFtp();
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * 关闭FTP连接
	 * 
	 * 
	 */
	private void closeFtp() {
		if (ftpClient != null) {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 在FTP服务器上根目录下依次创建文件
	 * 
	 * @param folderPath
	 *            不能为空，比如:a/b; d\\c
	 * 
	 */
	public boolean createDirectory(String folderPath) {
		boolean result = false;
		if (folderPath == null || "".equals(folderPath) || ftpClient == null) {
			return false;
		}
		try {
			folderPath = folderPath.replaceAll("\\\\", "/");
			String[] folders = folderPath.split("/");
			if (folders.length > 0) {
				ftpClient.changeWorkingDirectory("/");
				for (int i = 0; i < folders.length; i++) {
					String folder = folders[i];
					String encodeFolderName = toSerStr(folder);
					result = ftpClient.makeDirectory(encodeFolderName);
					ftpClient.changeWorkingDirectory(encodeFolderName);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			FtpFileUtil f = new FtpFileUtil("192.168.1.144", 21, "admin", "admin123");

			FileInputStream srcFile = new FileInputStream(new File("e:\\test/tesjpg.jpg"));
			boolean g = f.upLoadFile(srcFile, "admin/test/tes/", "abc1235.jpg");
			System.out.println("upload:" + g);

			// FileOutputStream fos = new FileOutputStream(new
			// File("e:\\test/ftpdown中文jpg.jpg"));
			// Boolean d = f.downLoadFile(fos, "admin/test/tes", "abc123.jpg");
			// System.out.println("download:"+d);

			// FileInputStream srcFile2 = new FileInputStream(new
			// File("e:\\test/tesjpg.jpg"));
			// boolean g2 = f.upLoadFile(srcFile2, "admin/test/tes", "abc1236.jpg");
			// System.out.println("upload:" + g2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
