package com.hanthink.mes.test.print.impl;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;

public class PrinterTest {
	public void doPrint() {
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		// 设置打印格式，因为未确定文件类型，这里选择AUTOSENSE
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		// 查找所有的可用打印服务
		// 定位默认的打印服务
		PrintService defaultService = PrintServiceLookup
				.lookupDefaultPrintService();
		// 显示打印对话框
		try {
			DocPrintJob job = defaultService.createPrintJob(); // 创建打印作业
			job.addPrintJobListener(new PrintJobListener() {
				@Override
				public void printJobRequiresAttention(PrintJobEvent arg0) {
					// TODO Auto-generated method stub
					System.out.println("printJobRequiresAttention");
				}

				@Override
				public void printJobNoMoreEvents(PrintJobEvent arg0) {
					// TODO Auto-generated method stub
					System.out.println("printJobNoMoreEvents");
				}

				@Override
				public void printJobFailed(PrintJobEvent arg0) {
					// TODO Auto-generated method stub
					System.out.println("printJobFailed");

				}

				@Override
				public void printJobCompleted(PrintJobEvent arg0) {
					System.out.println("打印结束");

				}

				@Override
				public void printJobCanceled(PrintJobEvent arg0) {
					// TODO Auto-generated method stub
					System.out.println("printJobCanceled");

				}

				@Override
				public void printDataTransferCompleted(PrintJobEvent arg0) {
					// TODO Auto-generated method stub
					System.out.println("printDataTransferCompleted"+"\n");

				}
			});
			FileInputStream fis = new FileInputStream(
					"C:\\TEST_Log.txt"); // 构造待打印的文件流
			DocAttributeSet das = new HashDocAttributeSet();
			Doc doc = new SimpleDoc(fis, flavor, das); // 建立打印文件格式
			job.print(doc, pras); // 进行文件的打印
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		PrinterTest pt = new PrinterTest();
		pt.doPrint();
		File file = new File("C:\\TEST_Log.txt");
		try {
			Desktop.getDesktop().print(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
