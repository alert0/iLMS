package com.hanthink.gps.util.barCode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import com.swetake.util.Qrcode;
import com.hanthink.gps.util.DateUtil;
import com.hanthink.gps.util.FileUtil;

public class MakeQrcodeImages {

	/** 系统默认的文件分割符 */
	public static final String SEPERATOR = System.getProperty("file.separator");

	public synchronized static BufferedImage getQrCodeImage(String code) {
		try {
			Qrcode qrcode = new Qrcode();
			qrcode.setQrcodeErrorCorrect('M');
			qrcode.setQrcodeEncodeMode('B');			
			if(code == null) {
				return null;
			}
			//System.out.println("code "+code);
			byte[] bstr;
			bstr = code.getBytes("UTF-8");

			BufferedImage bi = new BufferedImage(139, 139,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bi.createGraphics();

			g.setBackground(Color.WHITE);
//			g.clearRect(0, 0, 120, 120);
			//二维码的大小的规定（x,y,height,width）
			g.fillRect(0, 0, 139, 139);
			g.setColor(Color.BLACK);

			if (bstr.length > 0 && bstr.length < 200) {
				boolean[][] b = qrcode.calQrcode(bstr);
				for (int i = 0; i < b.length; i++) {
					for (int j = 0; j < b.length; j++) {
						if (b[j][i]) {
							g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
						}
					}
				}
			}
			g.dispose();
			bi.flush();
			return bi;

//			ImageIO.write(bi, "jpeg", response.getOutputStream());
			//输出流的关闭
//			response.getOutputStream().close();
		} catch (UnsupportedEncodingException e) {
			return null;
		} catch (Exception e) {
			return null;
		} 
	}
	
	public synchronized static String  getQrCode(String absolutePath, String code, HttpServletResponse response) {
		try {
			if (true) 
				return "servlet/CreateImage.servlet?type=qr&code=" + java.net.URLEncoder.encode(code);
		
//			if (true)
//				return "QrCode?code="+java.net.URLEncoder.encode(code);
			
			BufferedImage bi = getQrCodeImage(code);
			 
			// 创建以日期为文件夹
			//String folderName = DateUtil.formatDate(new Date(), DateUtil.DATE_FORMAT_YYYYMMDD_FILE);
			String folderName = DateUtil.formatDate(new Date(), DateUtil.DATE_FORMAT_YYYYMMDDHH_FILE);
			String folderPath = absolutePath+SEPERATOR+folderName;
			if(FileUtil.newFolder(folderPath)){
				String fileName = (new Date()).getTime()+UUID.randomUUID().toString()+".jpg";
				File outputFile = new File(folderPath + SEPERATOR+fileName);
				ImageIO.write(bi, "jpeg", outputFile);
				return folderName+SEPERATOR+fileName;
			}
//			ImageIO.write(bi, "jpeg", response.getOutputStream());
			//输出流的关闭
//			response.getOutputStream().close();
		} catch (UnsupportedEncodingException e) {
			return "";
		} catch (Exception e) {
			return "";
		} 
		return "";
	}
	
	public synchronized static String  getCodeImg(String absolutePath, String code) {

		return "servlet/CreateImage.servlet?type=bar&code=" + java.net.URLEncoder.encode(code);

		//return "BarCode.jsp?code="+java.net.URLEncoder.encode(code);
		
		/* OutputStream out = null;
		 try {
			 	if(code == null){
			 		return "";
			 	}
	            Code128Bean code128 = new Code128Bean();
	            final int dpi = 150;
	            code128.setModuleWidth(0.32);
	            code128.doQuietZone(false);  
	            code128.setHeight(10);
	           
	            // 创建以日期为文件夹
				String folderName = DateUtil.formatDate(new Date(), DateUtil.DATE_FORMAT_YYYYMMDD_FILE);
//				folderName = folderName + "yiwei";
				// 获取系统的文件夹seperator
				
				String folderPath = absolutePath+SEPERATOR+folderName;
				if(FileUtil.newFolder(folderPath)){
					String fileName = (new Date()).getTime()+UUID.randomUUID().toString()+".jpg";
					File outputFile = new File(folderPath + SEPERATOR+fileName);
					out = new FileOutputStream(outputFile);
	                BitmapCanvasProvider canvas = new BitmapCanvasProvider(
	                        out, "image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
	                code128.generateBarcode(canvas, code);
	                canvas.finish();
	                return folderName+SEPERATOR+fileName;
				}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }  finally {
                try {
					out.close();
				} catch (IOException e) {
					return "";
				}
            }*/
		//return getCodeImg(absolutePath,code,0.33);
	}
	
	public synchronized static Code128Bean getCode128Bean(){
		 try {
	            Code128Bean code128 = new Code128Bean();
	            final int dpi = 150;
	            // 因为肆意放大间距导致程序都无法识别信息,修改为合适的大小的间距 
//	            code128.setModuleWidth(width);
	            code128.setModuleWidth(UnitConv.in2mm(2.0f / dpi));
	            code128.doQuietZone(true);  
	            code128.setHeight(10);
	            code128.setFontSize(2.8);
	            return code128;
	            // 创建以日期为文件夹
				
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
		 return null;
	}
	
	public synchronized static String  getCodeImg(String absolutePath, String code,Double width) {
		return "servlet/CreateImage.servlet?type=bar&code=" + java.net.URLEncoder.encode(code);

			//return "BarCode.jsp?code="+java.net.URLEncoder.encode(code);
			
//		 try {
//			 	if(code == null){
//			 		return "";
//			 	}
//			 	
//
//	            Code128Bean code128 = getCode128Bean();
//	            final int dpi = 150;
//	            
//	            // 创建以日期为文件夹
//				String folderName = DateUtil.formatDate(new Date(), DateUtil.DATE_FORMAT_YYYYMMDD_FILE);
//				// 获取系统的文件夹seperator
//				
//				String folderPath = absolutePath+SEPERATOR+folderName;
//				if(FileUtil.newFolder(folderPath)){
//					String fileName = (new Date()).getTime()+UUID.randomUUID().toString()+".jpg";
//					File outputFile = new File(folderPath + SEPERATOR+fileName);
//					out = new FileOutputStream(outputFile);
//	                BitmapCanvasProvider canvas = new BitmapCanvasProvider(
//	                        out, "image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
////	                Default is new Font("Arial", Font.PLAIN, 11). 
//	                code128.setFontSize(2.8);
//	                code128.generateBarcode(canvas, code);
//	                canvas.finish();
//	                return folderName+SEPERATOR+fileName;
//				}
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }  
//		 finally {
//               try {
//					if (out!=null) out.close();
//				} catch (IOException e) {
//					return "";
//				}
//           }
//		return "";
	}
}
