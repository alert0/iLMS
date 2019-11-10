package com.hotent.base.core.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/x5-base-core-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class BaseTestCase {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void Test(){}

}
