package com.hanthink.mes.test.print.impl;

import static org.junit.Assert.fail;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import com.hanthink.mes.print.constants.PrintClass;
import com.hanthink.mes.print.constants.PrintType;
import com.hanthink.mes.print.impl.PrintService;
import com.hanthink.mes.print.model.MESPRJobQueue;

public class TestPrintService {


	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testPrintService() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPrinterName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPrintCopies() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrintBarcode() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrintCertification() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrintLogistics() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrintManifest() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrintSpecData() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNextSequence() {
		fail("Not yet implemented");
	}
	
	//***************************************************************************************************//
	//***************************************************************************************************//
	//***************************************************************************************************//
	/**
	 *测试广汽新能源拉动配送单打印
	 */
	@Test
	public void testGacneJitIns(){
		PrintService printService = new PrintService();
		MESPRJobQueue job = new MESPRJobQueue();
		job.setBusiness("JIT");
		job.setClasses(PrintClass.PRINT_CLASS_NETWORK);
		job.setJobName("JIT_INS");
		job.setSerialNumber("10000001");
		job.setPrintType("Logistics JIT Print");
		job.setJobType("JIT");
		printService.printGacneJitIns(job, PrintClass.PRINT_CLASS_NETWORK);
		
	} 
	
	/**
	 * 测试广汽新能源SPS指示票打印
	 */
	@Test
	public void testGacneSps(){
		PrintService printService = new PrintService();
		MESPRJobQueue job = new MESPRJobQueue();
		job.setBusiness("SPS");
		job.setClasses(PrintClass.PRINT_CLASS_NETWORK);
		job.setJobName("T1T2_SPS箱子");
		job.setSerialNumber("A20190307SPS_XH0001");
		job.setPrintType("Logistics SPS Print");
		job.setJobType("SPS");
		printService.printGacneSpsIns(job, PrintClass.PRINT_CLASS_NETWORK);
	}
	
	
}
