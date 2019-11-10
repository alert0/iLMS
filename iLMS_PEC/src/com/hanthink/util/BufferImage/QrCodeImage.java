package com.hanthink.util.BufferImage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.swetake.util.Qrcode;

public class QrCodeImage {
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
