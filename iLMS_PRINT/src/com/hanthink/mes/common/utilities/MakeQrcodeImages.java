package com.hanthink.mes.common.utilities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;

import com.swetake.util.Qrcode;

/**
 * @Title: MakeQrcodeImages.java
 * @Package: com.hanthink.mes.common.utilities
 * @Description: 二维码工具类
 * @author dtp
 * @date 2018-12-29
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

}
