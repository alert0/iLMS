package com.hanthink.gps.util.barCode;

import java.io.*;
import java.awt.*;
import java.awt.image.*;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;

import com.sun.image.codec.jpeg.*;
import com.hanthink.gps.util.logger.LogUtil;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

public class CreateImageServlet extends HttpServlet {
	private static final long serialVersionUID = 8741055194067231125L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException
     {
         response.setContentType("image/jpeg");
         String type=request.getParameter("type");
         if ("qr".equals(type))
        	 createQrImage(response.getOutputStream(), request.getParameter("code"));
         else if ("bar".equals(type))
        	 createBarImage(response.getOutputStream(), request.getParameter("code"));
         
     }
	
    private void createBarImage(OutputStream output, String code) {
        try {
        	 Code128Bean code128 = MakeQrcodeImages.getCode128Bean();

        	 BitmapCanvasProvider canvas = new BitmapCanvasProvider(output, "image/jpeg", 150, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        	 code128.generateBarcode(canvas, code);
        	 canvas.finish();
        }
        catch(Exception e) {
        	LogUtil.error(e);            
        }
    }
    
     private void createQrImage(OutputStream output, String code) {
         try {
			BufferedImage image = MakeQrcodeImages.getQrCodeImage(code);
			ImageIO.write(image, "JPEG", output);  
			output.flush();
			output.close();
         }
         catch(Exception e) {
        	 LogUtil.error(e);            
         }
     }
}
