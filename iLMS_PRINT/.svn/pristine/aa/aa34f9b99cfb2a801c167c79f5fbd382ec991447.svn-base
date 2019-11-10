package com.hanthink.mes.test.print.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.PrinterStateReasons;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;
import javax.print.event.PrintServiceAttributeEvent;
import javax.print.event.PrintServiceAttributeListener;
  
  
public class TextPrinter implements Printable {  
  
    // 半角下的中文字符  
    public static final short HALF_CHINESE = 0;  
  
    // 半角下的英文字符  
    public static final short HALF_ENGLISH = 1;  
  
    protected String text;  
  
    protected String seperator;  
  
    public TextPrinter() {  
  
    }  
  
    public void print(String text, String seperator) {  
  
        this.text = text;  
        this.seperator = seperator;  
  
        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;  
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();  
  
        aset.add(new MediaPrintableArea(20, 20, 700, 700, MediaPrintableArea.MM));  
  
        PrintService services = PrintServiceLookup.lookupDefaultPrintService();  
  
        if (services != null) {  
            DocPrintJob pj = services.createPrintJob();  
            services.addPrintServiceAttributeListener(new PrintServiceAttributeListener() {  
  
                public void attributeUpdate(PrintServiceAttributeEvent psae) {  
                    PrintServiceAttributeSet attribute = psae.getAttributes();  
                }  
  
            });  
            pj.addPrintJobListener(new PrintJobListener() {  
  
                public void printDataTransferCompleted(PrintJobEvent pje) {  
                    System.out.println("111111111111  " + pje.getPrintEventType());  
                }  
  
                public void printJobCanceled(PrintJobEvent pje) {  
                	System.out.println("22222222222222222  " + pje.getPrintEventType());  
                }  
  
                public void printJobCompleted(PrintJobEvent pje) {  
                	System.out.println("33333333333333  " + pje.getPrintEventType());  
                }  
  
                public void printJobFailed(PrintJobEvent pje) {  
                	System.out.println("444444444444444  " + pje.getPrintEventType());  
  
                }  
  
                public void printJobNoMoreEvents(PrintJobEvent pje) {  
                	System.out.println("5555555555555555555  " + pje.getPrintEventType());  
                    System.out.println("打印失败：" + pje.JOB_FAILED);  
                    System.out.println("没有事件：" + pje.NO_MORE_EVENTS);  
                }  
  
                public void printJobRequiresAttention(PrintJobEvent pje) {  
                	System.out.println("66666666666666666666  " + pje.getPrintEventType());  
                }  
  
            });  
            try {  
                Doc doc = new SimpleDoc(this, flavor, null);  
                pj.print(doc, aset);  
                PrinterStateReasons printerStateReasons = services  
                        .getAttribute(PrinterStateReasons.class);  
                System.out.println("-----------" + printerStateReasons);  
            } catch (PrintException e) {  
                e.printStackTrace();  
                System.err.println(e);  
            }  
        } else {  
        	System.out.println("未找到打印机。");  
        }  
    }  
  
    public int print(Graphics g, PageFormat pf, int pageIndex) {  
  
        if (pageIndex == 0) {  
  
            Graphics2D g2d = (Graphics2D) g;  
            g2d.translate(pf.getImageableX(), pf.getImageableY());  
            g2d.setColor(Color.black);  
  
            String[] lines = text.split(seperator);  
  
            for (int i = 0; i < lines.length; i++) {  
                g2d.drawString(lines[i], 0, (i + 1) * 20);  
            }  
  
            return Printable.PAGE_EXISTS;  
        } else {  
            return Printable.NO_SUCH_PAGE;  
        }  
    }  
  
    /** 
     * 为中文字符对齐补空格（打印对齐时候用） 
     *  
     * @param content 
     *            需要补空格的字符 
     * @param expectLength 
     *            占用的最大长度 
     * @param type 
     *            字符的类型：中文、英文 
     * @return 
     */  
    public static String validChineseString(String content, int expectLength, short type) {  
        String resultString = content.trim();  
        String space = null;  
        switch (type) {  
        case HALF_CHINESE: {  
            space = " ";  
            break;  
        }  
        case HALF_ENGLISH: {  
            space = " ";  
            break;  
        }  
  
        default:  
            break;  
        }  
        int contentLength = content.length();  
        if (contentLength < expectLength) {  
            int different = expectLength - contentLength;  
            for (int i = 0; i < different; i++) {  
                resultString += space;  
            }  
        }  
        System.out.println(content + "    " + resultString);  
        return resultString;  
    }  
}  