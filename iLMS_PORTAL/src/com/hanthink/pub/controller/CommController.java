package com.hanthink.pub.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;

/**
 * 
 * @Desc		: 公共通用Controller
 * @FileName	: CommController.java
 * @CreateOn	: 2018年9月2日 下午2:40:11
 * @author 		: ZUOSL
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 
 */
@Controller
@RequestMapping("/comm/comm")
public class CommController extends GenericController {
	protected static Logger log = LoggerFactory.getLogger(CommController.class);
	
	@RequestMapping("downloadExcelModel")
	public void genPdfTest (HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		try {
			String fileName = RequestUtil.getString(request, "fileName");
			if(StringUtil.isEmpty(fileName)){
				return;
			}
			fileName = java.net.URLDecoder.decode(fileName, "UTF-8");
			String filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator +"excel" + File.separator + fileName;
            File file = new File(filenurl);
            String exportFileName  = file.getName();
            String downName = null;
            if (request.getHeader("user-agent").toLowerCase().contains("msie")
                    || request.getHeader("user-agent").toLowerCase().contains("like gecko")
            ) {
            	downName = URLEncoder.encode(exportFileName, "UTF-8");
            }else{
            	downName = new String(exportFileName.getBytes("UTF-8"), "ISO_8859_1");
            }
    		response.setContentType("multipart/form-data");
    		response.setHeader("Content-disposition", "attachment; filename=" + downName);
    		OutputStream out = response.getOutputStream();
    		byte[] buffer = new byte[1024];
    		int len = 0;
    		InputStream is = new FileInputStream(file);
    		while((len = is.read(buffer)) != -1){
    			out.write(buffer, 0, len);
    		}
    		out.flush();
    		
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		
	}
	
	
}
