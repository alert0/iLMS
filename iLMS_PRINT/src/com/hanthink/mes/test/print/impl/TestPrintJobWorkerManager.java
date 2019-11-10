package com.hanthink.mes.test.print.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.hanthink.mes.print.constants.PrintClass;
import com.hanthink.mes.print.constants.PrintType;
import com.hanthink.mes.print.impl.PrintJobWorkerManager;

public class TestPrintJobWorkerManager {

	@Test
	public void testStartJobWorker() {
		fail("Not yet implemented");
	}

	@Test
	public void testStopJobWorker() {
		fail("Not yet implemented");
	}

	@Test
	public void testStartAllJobWorker() {
		PrintJobWorkerManager printJobWorkerManager;
		try {
			printJobWorkerManager = new PrintJobWorkerManager(PrintClass.PRINT_CLASS_NETWORK, PrintType.PRINT_TYPE_BARCODE_PRINT);
			printJobWorkerManager.startAllJobWorker();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testStopAllJobWorker() {
		fail("Not yet implemented");
	}

	@Test
	public void testWaitJobWorkerStarted() {
		fail("Not yet implemented");
	}

	@Test
	public void testReloadJobQueue() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrintJobWorkerManager() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateJobQueue() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddPrintJob() {
		fail("Not yet implemented");
	}

}
