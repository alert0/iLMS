package com.hotent.sys.log.listener;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.hotent.sys.api.event.LogModuleCons;
import com.hotent.sys.persistence.manager.LogModuleManager;

/**
 * 
 * <pre>
 * 描述：日志模块管理 初始化监听器
 * 构建组：x5-bpmx-platform
 * 作者:ouxb
 * 邮箱:ouxb@jee-soft.cn
 * 日期:2014-10-29 16:57:57
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service
public class LogModuleInitListener implements
		ApplicationListener<ContextRefreshedEvent> {
	protected static final Logger logger = LoggerFactory
			.getLogger(LogModuleInitListener.class);

	@Resource
	private LogModuleManager logModuleManager;

	/**
	 * 往日志模块数据库插入数据与com.hotent.sys.api.event.LogModuleCons保持一致
	 * 
	 * @param queryFilter
	 * @return
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
//		if (event.getApplicationContext().getParent() == null) { // 防止重复执行。
//			try {
//				logger.debug("开始日志模块数据------------------>");
//				List<String> bdNames = logModuleManager.getNames(); // 获取数据库中全部保存的模块名称
//				this.initData(bdNames);
//			} catch (Exception e) {
//				logger.error("初始化日志模块数据失败，详细请查看:");
//				e.printStackTrace();
//			} finally {
//				logger.debug("结束日志模块数据<---------------------");
//			}
//		}
	}

	/**
	 * 根据com.hotent.sys.api.event.LogModuleCons 的常量初始化数据
	 * 
	 * @param allList 数据库中保存的模块名
	 */
	private void initData(List<String> bdNames) throws Exception{
//		LogModuleCons[] cons = LogModuleCons.values(); // 常量集
//		if (cons == null || cons.length == 0)
//			return;
//		if(bdNames != null){
//			logModuleManager.innsert(cons,bdNames);
//		}else{
//			logModuleManager.innsertAll(cons);
//		}
	}
	
}
