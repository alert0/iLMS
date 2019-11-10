package com.hanthink.business.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.swetake.util.Qrcode;

/**
 * @ClassName: MakeQrcodeImages
 * @Description: 生成二维码工具类
 * @author dtp
 * @date 2018年10月22日
 */
public class MakeQrcodeImages {

	/** 系统默认的文件分割符 */
	public static final String SEPERATOR = System.getProperty("file.separator");

	/**
	 * 生成二维码
	 * @param code
	 *            字符串
	 * @param imgWidth
	 *            图片宽度
	 * @param imgHeight
	 *            图片高度
	 * @return BufferedImage
	 */
	public synchronized static BufferedImage getQrCodeImage(String code,
			String imgWidth, String imgHeight) {
		try {
			Qrcode qrcode = new Qrcode();
			qrcode.setQrcodeErrorCorrect('M');
			qrcode.setQrcodeEncodeMode('B');
			if (code == null) {
				return null;
			}
			byte[] bstr;
			bstr = code.getBytes("UTF-8");
			BufferedImage bi = new BufferedImage(Integer.valueOf(imgWidth),
					Integer.valueOf(imgHeight), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bi.createGraphics();
			g.setBackground(Color.WHITE);
			// 二维码的大小的规定（x,y,height,width）
			g.fillRect(0, 0, Integer.parseInt(imgWidth), Integer
					.parseInt(imgHeight));
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
		} catch (UnsupportedEncodingException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 生成二维码数据
	 * 
	 * @return
	 * @param size
	 *            图片长宽
	 * @param version
	 *            版本
	 * @param code
	 *            二维码字符串
	 */
	public static synchronized BufferedImage getQrCodeImage(int size,
			int version, String code) {
		try {
			Qrcode qrcode = new Qrcode();
			qrcode.setQrcodeErrorCorrect('M');
			qrcode.setQrcodeEncodeMode('B');
			qrcode.setQrcodeVersion(version);
			if (code == null) {
				return null;
			}
			byte[] bstr = code.getBytes("UTF-8");

			BufferedImage bi = new BufferedImage(size, size, 1);
			Graphics2D g = bi.createGraphics();

			g.setBackground(Color.WHITE);
			g.clearRect(0, 0, size, size);
			g.setColor(Color.BLACK);
			if ((bstr.length > 0) && (bstr.length < 200)) {
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
		} catch (UnsupportedEncodingException e) {
			return null;
		} catch (Exception localException) {
		}
		return null;
	}
	
	/**
	 * @Description: 生成二维码
	 * @param: @param content
	 * @param: @param errorCorrect
	 * @param: @param mode
	 * @param: @param version
	 * @param: @return
	 * @param: @throws IOException    
	 * @return: BufferedImage   
	 * @author: dtp
	 * @date: 2018年10月22日
	 */
	public static BufferedImage createQrcode(String content, char errorCorrect,
			char mode, int version) throws IOException {
		BufferedImage image =null;
		if (null == content || "".equals(content)) {

		} else {
			Qrcode qrcode = new Qrcode();
			qrcode.setQrcodeErrorCorrect(errorCorrect);
			qrcode.setQrcodeEncodeMode(mode);

			//qrcode.setQrcodeVersion(version);
			// 获取内容的字节数组，设置编码格式
			byte[] bytes = content.getBytes("UTF-8");
			// 图片尺寸,会根据version的变大，而变大，自己需要计算
			int imgSize =  67 + 12 * (version - 1);
			image = new BufferedImage(imgSize, imgSize,
					BufferedImage.TYPE_BYTE_BINARY);
			// 获取画笔
			Graphics2D gs = image.createGraphics();
			// 设置背景色 白色
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, imgSize, imgSize);
			// 设定图像颜色 黑色
			gs.setColor(Color.BLACK);
			// 设置偏移量，不设置可能导致二维码生产错误(解析失败出错)
			int pixoff = 2;
			// 二维码输出
			if (bytes.length > 0 && bytes.length < 120) {
				boolean[][] s = qrcode.calQrcode(bytes);
				for (int i = 0; i < s.length; i++) {
					for (int j = 0; j < s.length; j++) {
						if (s[j][i]) {
							//注意j * 3 + pixoff, i * 3 + pixoff, 3, 3中的pixoff和3也会影响二维码像素，但是影响并不会很大，
							//二维码像素主要受version影响
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);//填充矩形区域
						}
					}
				}
			}
			gs.dispose();
			image.flush();
		}
		return image;
	}
}
