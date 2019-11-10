package com.hanthink.gps.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.hanthink.gps.util.logger.LogUtil;

/**
 * 
 * <dd>概要：用于文件共通函数的类
 * 
 * 
 * @version 1.00 2008/09/04
 * @author hjliang
 * 
 */
public class FileUtil {

	/**
	 * 
	 * constructor
	 * 
	 */
	public FileUtil() {
	}

	/**
	 * new direct
	 * 
	 * @param folderPath
	 *            String eg c:/fqf
	 */
	public static boolean newFolder(String folderPath) {
		File file = new File(folderPath);
		if (!file.exists()) {
			return file.mkdirs();
		} else {
			return true;
		}
	}

	/**
	 * new file
	 * 
	 * @param filePathAndName
	 *            String eg c:/fqf.txt
	 * @param fileContent
	 *            String
	 */
	public static void newFile(String filePathAndName, String fileContent)
			throws IOException {
		FileWriter resultFile = null;
		PrintWriter myFile = null;
		try {
			File file = new File(filePathAndName);
			if (!file.exists()) {
				file.createNewFile();
			}
			resultFile = new FileWriter(file);
			myFile = new PrintWriter(resultFile);
			myFile.println(fileContent);
		} finally {
			if (resultFile != null) {
				resultFile.close();
				myFile.flush();
				myFile.close();
			}
		}
	}

	/**
	 * delete file
	 * 
	 * @param filePathAndName
	 *            String eg c:/fqf.txt
	 */
	public static void delFile(String filePathAndName) {
		File delFile = new File(filePathAndName);
		delFile.delete();
	}

	/**
	 * delete file directory
	 * 
	 * @param folderPath
	 *            String eg c:/fqf
	 */
	public static boolean delFolder(String folderPath) {
		delAllFile(folderPath); // delete all file
		File file = new File(folderPath);
		return file.delete(); // delete null file directory
	}

	/**
	 * 删除还有文件的文件夹
	 * @param delpath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static boolean deletefile(String delpath)
			throws FileNotFoundException, IOException {
		try {
			File file = new File(delpath);
			if (!file.isDirectory()) {
				file.delete();
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + "\\" + filelist[i]);
					if (!delfile.isDirectory())
						delfile.delete();
					else if (delfile.isDirectory())
						deletefile(delpath + "\\" + filelist[i]);
				}
				file.delete();
			}
		} catch (FileNotFoundException e) {
		}
		return true;
	}

	/**
	 * delete all file
	 * 
	 * @param path
	 *            String eg c:/fqf
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				// delete file
				delAllFile(path + File.separator + tempList[i]);
				// delete Folder
				delFolder(path + File.separator + tempList[i]);
			}
		}
	}

	/**
	 * write File
	 * 
	 * @param fileData
	 *            StringBuffer
	 * @param fileName
	 *            String
	 * @param charset
	 *            String
	 * @throws IOException
	 */
	public static void writeFile(StringBuffer fileData, String fileName,
			String charset) throws IOException {
		String fileString = new String(fileData);
		OutputStream stream = null;
		Writer sw = null;

		try {
			stream = new FileOutputStream(new File(fileName));

			if (stream != null) {

				// 写入文件
				sw = new BufferedWriter(new OutputStreamWriter(stream, charset));

				sw.write(fileString);
			}
		} finally {
			// Close处理
			if (sw != null) {
				sw.close();
			}

			if (stream != null) {
				stream.close();
			}
		}
	}

	public static void writeFile(String fileString, String filePath,
			String charset) throws IOException {

		OutputStream stream = null;
		Writer sw = null;

		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			stream = new FileOutputStream(file);

			if (stream != null) {

				// 写入文件
				sw = new BufferedWriter(new OutputStreamWriter(stream, charset));

				sw.write(fileString);
			}
		} finally {
			// Close处理
			if (sw != null) {
				sw.close();
			}

			if (stream != null) {
				stream.close();
			}
		}
	}
	
	/**
	 * delete all file but no file
	 * 
	 * @param path
	 *            String eg c:/fqf
	 */
	public static void delAllFileButNoFile(String path) {
		
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			// 删除文件夹不删除文件
			if (temp.isFile()) {
				continue;
			}
			if (temp.isDirectory()) {
				// delete file
				delAllFile(path + File.separator + tempList[i]);
				// delete Folder
				delFolder(path + File.separator + tempList[i]);
			}
		}
	}
	
	/**
	 * 拷贝文件
	 * 
	 * @param src
	 * @param dst
	 */
	public static void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src));
				out = new BufferedOutputStream(new FileOutputStream(dst));
				byte[] buffer = new byte[1024 * 10];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 文件打包
	 * @param files
	 * @param os
	 * @return
	 * @author zuosl 2016-7-3
	 */
	public static boolean zipFile(File[] files, OutputStream os){
		
		byte[] buffer = new byte[4096];  
		try {
			ZipOutputStream out = new ZipOutputStream(os);
			out.setEncoding(System.getProperty("sun.jnu.encoding"));
			for(File file : files){
				
				FileInputStream in = new FileInputStream(file);
				out.putNextEntry(new ZipEntry(file.getName()));
				
				int len = 0;
				while((len = in.read(buffer)) > 0){
					out.write(buffer,0,len); 
				}
				out.closeEntry();
				in.close();
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e);
			return false;
		}
		
		return true;
	}
	
	
}
