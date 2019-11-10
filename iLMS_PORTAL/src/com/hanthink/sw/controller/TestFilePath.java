package com.hanthink.sw.controller;

import java.io.File;

public class TestFilePath {

	public static void main(String[] args) {
		String imgLastPath = "D:\\MyEclipse\\myeclipse_space\\.metadata\\.me_tcat85\\webapps\\iLMS_PMC\\css\\img\\lxq\\";
		File file = new File(imgLastPath);
		if (file != null && file.exists() && file.isDirectory()) {
			File[] files = file.listFiles();
			// if(files !=null){ //此方法判断错误。
			if (files != null && files.length > 0) { // 此方法判断OK,需要使用数组的长度来判断。
//				SwNonStandardController.delAllFile(imgPath);  // 如文件不为空则删除
//				SwNonStandardController.delAllFile(imgLastPath); 
			} else {  //如果文件夹不存在则创建
				file.mkdir();
			}
		} else {
			file.mkdir();
		}
	}

}
