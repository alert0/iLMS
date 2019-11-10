package com.hanthink.util.FTP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FTPService {

	private FTPClient ftpClient;
	// 本地字符编码
	private static String LOCAL_CHARSET = "GBK";
	// FTP协议里面，规定文件名编码为iso-8859-1
	private static String SERVER_CHARSET = "ISO-8859-1";
	// FTP文件路径
	private String FTP_FILE_PATH;
	// 本地文件路径
	private String FILE_PATH;
	// 上传IP
	private String Ip;
	// PORT
	private int Port;
	// 用户
	private String User;
	// 密码
	private String Password;
	
	private String result;
	
	public FTPService(String fTP_FILE_PATH, String fILE_PATH, String ip, int port, String user, String password) {
		this.Ip = ip;
		this.Port = port;
		this.User = user;
		this.Password = password;
	}

	/**
	 * 获取ftp连接
	 * 
	 * @return
	 * @throws IOException
	 * @throws SocketException
	 */
	public FTPClient getFTP() throws SocketException, IOException {
		ftpClient = new FTPClient();

		ftpClient.connect(Ip, Port);
		int reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			System.out.println("--FTP连接失败");
			ftpClient.disconnect();
			return null;
		}
		boolean flag = ftpClient.login(User, Password);
		if (!flag) {
			System.out.println("--FTP登陆失败");
			ftpClient.disconnect();
			return null;
		}

		if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
			LOCAL_CHARSET = "UTF-8";
		}
		ftpClient.setControlEncoding(LOCAL_CHARSET);// 设置编码
		FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
		conf.setServerLanguageCode("zh");
		ftpClient.configure(conf);
		ftpClient.enterLocalPassiveMode();
		ftpClient.setRemoteVerificationEnabled(flag);
		ftpClient.changeWorkingDirectory(FTP_FILE_PATH);// 切换工作路径，�?/�?表示根路�?
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// 设置文件类型

		return ftpClient;
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
			// LogUtilities.error(LoggerType.File,
			// e.getStackTrace().toString());
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
	 * @throws IOException
	 */
	public boolean upLoadFile(String fileName, String ftpName) throws IOException {

		ftpClient = getFTP();

		FileInputStream fis = null;
		File srcFile = new File(fileName);
		try {
			fis = new FileInputStream(srcFile);
			if (FTP_FILE_PATH != null && !"".equals(FTP_FILE_PATH)) {
				createDirectory(FTP_FILE_PATH);
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
		}
	}

	/**
	 * 下载文件
	 * 
	 * @return
	 * @throws IOException
	 * @throws SocketException
	 */
	public String downLoadFile(String fileName, String FILE_PATH, String FTP_FILE_PATH)
			throws SocketException, IOException {
		// 获取连接
		ftpClient = getFTP();
		String encodeFileName = toSerStr(fileName);
		if (ftpClient != null) {
			File fileFather = new File(FILE_PATH);
			if (!fileFather.exists()) {
				fileFather.mkdirs();
			}
			File file = new File(fileFather, fileName);
			boolean flag = true;
			if (file.exists()) {
				 flag = false;
			}
			if (flag) {
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
					result =  "服务器文件不存在，请确认";
					 closeFtp();
					 return result;
				}
				// 如果下载的文件名不存在服务器，则会在客户端创建一个空的文件，且返回�?为真
				FileOutputStream fos = new FileOutputStream(file);
				boolean f = ftpClient.retrieveFile(encodeFileName, fos);
				fos.close();
				if (f == false) {
					result = "下载文件失败";
				} else {
					result = "下载文件成功";
				}
			}

			// 关闭连接
			closeFtp();
		} else {
			result = "FTP连接失败";
			return result;
		}
		return result;
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
		// 获取连接
		ftpClient = getFTP();
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
	public void createDirectory(String folderPath) {
		boolean result = false;
		if (folderPath == null || "".equals(folderPath) || ftpClient == null) {
			return;
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
	}

}
