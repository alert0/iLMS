package com.hanthink.util.BufferImage;

import java.awt.image.BufferedImage;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.WidthCodedPainter;

public class BarCodeImage {

	public static BufferedImage creatBarCode(String str) {
		JBarcode localJBarcode = new JBarcode(Code128Encoder.getInstance(), 
				WidthCodedPainter.getInstance(), BaseLineTextPainter.getInstance());     
		localJBarcode.setEncoder(Code128Encoder.getInstance());       
		localJBarcode.setPainter(WidthCodedPainter.getInstance());     
		localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());    
		localJBarcode.setShowCheckDigit(false);
		BufferedImage createBarcode = null;
		try {
			createBarcode = localJBarcode.createBarcode(str);
		} catch (InvalidAtributeException e) {
			e.printStackTrace();
		}
		return createBarcode;
	}
}