package com.hotent.mini.web.listener;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.datasource.DataSourceUtil;
import com.hotent.base.db.datasource.DynamicDataSource;
//import com.hotent.form.persistence.manager.BpmFormTemplateManager;
import com.hotent.form.persistence.model.BpmFormTemplate;
import com.hotent.sys.persistence.manager.SysDataSourceManager;

/**
 * 在spring容器启动时加载数据源： 加载的方式有两种： 1.从spring文件中做加载。 扫描系统spring
 * 配置中数据源动态加入到系统的dataSourceMap数据源中， 这样做的好处是
 * 不需要修改x5-base-db.xml文件可以在外面的配置文件中添加新的数据源。 2.从数据库中定义的数据源进行加载。 这样用户可以自己添加需要的数据源。
 * 
 * <pre>
 * 构建组：x5-base-db
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-4-11-下午2:11:24
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class DataSourceInitListener implements ApplicationListener<ContextRefreshedEvent> {

	protected static final Logger LOGGER = LoggerFactory.getLogger(DataSourceInitListener.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent ev) {
		// 防止重复执行。
		if (ev.getApplicationContext().getParent() != null)
			return;

		ApplicationContext context = ev.getApplicationContext();
		DynamicDataSource dynamicDataSource = (DynamicDataSource) context.getBean(DataSourceUtil.GLOBAL_DATASOURCE);

		// 加载配置文件中的数据源--->start
		loadDataSourceFromFile(context, dynamicDataSource);
		// 加载配置文件中的数据源--->end

		// 加载数据库中的数据源--->start
		SysDataSourceManager sysDataSourceManager = (SysDataSourceManager) context.getBean("sysDataSourceManager");

		// 获取可用的数据源
		Map<String, DataSource> sysDataSources = sysDataSourceManager.getDataSource();
		// 添加数据实例到容器中。
		addDataSource(sysDataSources);

		// 遍历数据源。
		loadDataSource();
		
		// 初始化表单模板
		initFormTemplate();
	}

	/**
	 * 遍历数据源。 void
	 */
	private void loadDataSource() {
		LOGGER.debug("目前容器里的数据源--------------->");
		Map<Object, Object> map = null;
		try {
			map = DataSourceUtil.getDataSources();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (map == null)
			return;
		for (Object object : map.keySet()) {
			boolean bb = false;
			try {
				Connection connection = ((javax.sql.DataSource) map.get(object)).getConnection();
				connection.close();
				bb = true;
			} catch (Exception e) {

			}
			LOGGER.debug("alias:" + object + "--className:" + map.get(object).getClass().getName() + "--connectable:" + bb);
		}
		LOGGER.debug("<----------------------");
		// 加载数据库中的数据源--->end
	}

	/**
	 * 添加到数据源。
	 * 
	 * @param dynamicDataSource
	 * @param maps
	 *            void
	 */
	private void addDataSource(Map<String, DataSource> maps) {
		try {
			Set<String> keySet = maps.keySet();
			for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
				String alias = it.next();
				DataSource dataSource = maps.get(alias);
				DataSourceUtil.addDataSource(alias, dataSource, false);
			}
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
	}

	/**
	 * 从spring文件中加载。
	 * 
	 * @param context
	 * @param dynamicDataSource
	 *            void
	 */
	void loadDataSourceFromFile(ApplicationContext context, DynamicDataSource dynamicDataSource) {
		Map<String, DataSource> mapDataSource = context.getBeansOfType(DataSource.class);

		Set<Entry<String, DataSource>> dsSet = mapDataSource.entrySet();
		for (Iterator<Entry<String, DataSource>> it = dsSet.iterator(); it.hasNext();) {
			Entry<String, DataSource> ent = it.next();
			String key = ent.getKey();
			if (key.equals(DataSourceUtil.GLOBAL_DATASOURCE) || key.equals(DataSourceUtil.DEFAULT_DATASOURCE))
				continue;
			try {
				dynamicDataSource.addDataSource(key, ent.getValue());
			} catch (IllegalAccessException e) {
				LOGGER.debug(e.getMessage());
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				LOGGER.debug(e.getMessage());
				e.printStackTrace();
			}
			LOGGER.debug("add datasource " + ent.getKey());
		}
	}
	
	private void initFormTemplate(){
//		BpmFormTemplateManager bpmFormTemplateManager = AppUtil.getBean(BpmFormTemplateManager.class);
//		List<BpmFormTemplate> all = bpmFormTemplateManager.getAll();
//		if(BeanUtils.isEmpty(all)){
//			try {
//				bpmFormTemplateManager.initAllTemplate();
//				LOGGER.debug("初始化表单模板成功");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
	
}
