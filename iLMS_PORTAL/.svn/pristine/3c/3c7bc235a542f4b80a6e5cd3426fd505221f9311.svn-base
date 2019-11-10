package com.hotent.sys.persistence.manager.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import com.hotent.base.api.db.model.Column;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.JAXBUtil;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.datasource.DataSourceUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.QuerySqldefDao;
import com.hotent.sys.persistence.dao.QueryViewDao;
import com.hotent.sys.persistence.enums.FieldControlType;
import com.hotent.sys.persistence.manager.QueryMetafieldManager;
import com.hotent.sys.persistence.manager.QuerySqldefManager;
import com.hotent.sys.persistence.model.QueryMetafield;
import com.hotent.sys.persistence.model.QuerySqldef;
import com.hotent.sys.persistence.model.QuerySqldefXml;
import com.hotent.sys.persistence.model.QuerySqldefXmlList;
import com.hotent.sys.persistence.model.QueryView;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.json.JSONObject;

/**
 * 
 * <pre>
 * 描述：sys_query_sqldef 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:Aschs
 * 邮箱:6322665042@qq.com
 * 日期:2016-06-13 17:28:47
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("querySqldefManager")
public class QuerySqldefManagerImpl extends AbstractManagerImpl<String, QuerySqldef> 
implements QuerySqldefManager {
	@Resource
	QuerySqldefDao querySqldefDao;
	@Resource
	QueryMetafieldManager queryMetafieldManager;
	@Resource
	QueryViewDao queryViewDao;

	@Override
	protected Dao<String, QuerySqldef> getDao() {
		return querySqldefDao;
	}
	
	

	@Override
	public void remove(String entityId) {
		QuerySqldef querySqldef=this.get(entityId);
		//删除元数据
		queryMetafieldManager.removeBySqlId(entityId);
		//删除视图
		queryViewDao.removeBySqlAlias(querySqldef.getAlias());
		//删除本身
		super.remove(entityId);
	}



	@Override
	public JSONObject checkSql(String dsName, String sql) {
		JSONObject data = new JSONObject();
		JdbcTemplate jdbcTemplate = null;
		try {
			jdbcTemplate = AppUtil.getBean(JdbcTemplate.class);
			jdbcTemplate.execute(sql);
			data.put("result", true);
			data.put("message", "验证通过");
		} catch (Exception e) {
			data.put("result", false);
			data.put("message", e.getMessage());
		}
		throw new RuntimeException(data.toString());
	}

	@Override
	public void save(QuerySqldef querySqldef) {
		checkBeforeSave(querySqldef);
		if (StringUtil.isEmpty(querySqldef.getId())) {// 新增的
			querySqldef.setId(UniqueIdUtil.getSuid());
			create(querySqldef);
			initMetafield(querySqldef);
		} else {
			update(querySqldef);
		}

		// 开始处理queryMetafield字段
		queryMetafieldManager.removeBySqlId(querySqldef.getId());// 删除旧的
		for (QueryMetafield field : querySqldef.getMetafields()) {
			field.setId(UniqueIdUtil.getSuid());
			queryMetafieldManager.create(field);
		}
	}

	/**
	 * 保存前的验证 有问题直接throw异常
	 * 
	 * @param querySqldef
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void checkBeforeSave(QuerySqldef querySqldef) {
		if (!StringUtil.isEmpty(querySqldef.getId())) {
			return;
		}
		if (getByAlias(querySqldef.getAlias()) != null) {
			throw new RuntimeException("别名:" + querySqldef.getAlias() + ",已被使用");
		}
	}

	@Override
	public QuerySqldef getByAlias(String alias) {
		QueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.addFilter("alias_", alias, QueryOP.EQUAL);
		List<QuerySqldef> list = query(queryFilter);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 新增时获取初始化字段配置
	 * 
	 * @param querySqldef
	 * @return List<QueryMetafield>
	 * @exception
	 * @since 1.0.0
	 */
	private void initMetafield(QuerySqldef querySqldef) {
		List<QueryMetafield> list = new ArrayList<QueryMetafield>();
		JdbcTemplate jdbcTemplate = null;
		try {
			jdbcTemplate =DataSourceUtil.getJdbcTempByDsAlias(querySqldef.getDsName());
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}
		SqlRowSet srs = jdbcTemplate.queryForRowSet(querySqldef.getSql());
		SqlRowSetMetaData srsmd = srs.getMetaData();
		// 列从1开始算
		for (int i = 1; i < srsmd.getColumnCount() + 1; i++) {
			String cn = srsmd.getColumnName(i).toUpperCase();
			String ctn = srsmd.getColumnTypeName(i);
			QueryMetafield field = new QueryMetafield();
			field.setSqlId(querySqldef.getId());
			field.setName(cn);
			field.setFieldName(cn);
			field.setFieldDesc(cn);

			field.setDataType(simplifyDataType(ctn));
			field.setIsShow(QueryMetafield.TRUE);
			field.setIsSearch(QueryMetafield.FALSE);
			field.setControlType(FieldControlType.ONETEXT.key);
			field.setIsVirtual(QueryMetafield.FALSE);
			field.setWidth((short) 0);

			field.setSn((short) i);
			list.add(field);
		}

		querySqldef.setMetafields(list);
	}

	/**
	 * 把数据库对应的字段类型 简化成 四种基本的数据库字段类型（varchar,number,date,text）
	 * 
	 * @param type
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	private String simplifyDataType(String type) {
		type = type.toLowerCase();

		String number = SysPropertyUtil.getByAlias("datatype.number");
		String date = SysPropertyUtil.getByAlias("datatype.date");
		String text = SysPropertyUtil.getByAlias("datatype.text");
		String varchar = SysPropertyUtil.getByAlias("datatype.varchar");

		if (varchar.contains(type)) {
			return Column.COLUMN_TYPE_VARCHAR;
		}
		if (text.contains(type)) {
			return Column.COLUMN_TYPE_VARCHAR;
		}
		if (date.contains(type)) {
			return Column.COLUMN_TYPE_DATE;
		}
		if (number.contains(type)) {
			return Column.COLUMN_TYPE_NUMBER;
		}

		return type;
	}

	@Override
	public String export(List<String> idList) throws Exception {
		if(BeanUtils.isEmpty(idList)) return "";
		
		QuerySqldefXmlList list=new QuerySqldefXmlList();
		
		for(String id:idList){
			QuerySqldef def= querySqldefDao.get(id);
			List<QueryMetafield> metaFieldList= queryMetafieldManager.getBySqlId(id);
			List<QueryView> viewList=queryViewDao.getBySqlAlias(def.getAlias());
			
			QuerySqldefXml defXml=new QuerySqldefXml();
			
			defXml.setQuerySqldef(def);
			defXml.setMetafieldList(metaFieldList);
			defXml.setQueryViewList(viewList);
			list.addQuerySqlDef(defXml);
		}
		
		String xml=JAXBUtil.marshall(list, QuerySqldefXmlList.class);
		
		return xml;
	}

	@Override
	public void importDef(String path) {
		try {
			String xml = FileUtil.readFile(path + File.separator + "sqldef.xml");
			if(StringUtil.isEmpty(xml)) return ;
			
			QuerySqldefXmlList list=(QuerySqldefXmlList) JAXBUtil.unmarshall(xml,QuerySqldefXmlList.class);
			List<QuerySqldefXml> sqlDefList=list.getQuerySqlDefList();
			for(QuerySqldefXml def:sqlDefList){
				importDef(def);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("系统未读取到指定内容：sqldef.xml",e);
		}
	}
	
	private void importDef(QuerySqldefXml def){
		QuerySqldef sqlDef= def.getQuerySqldef();
		QuerySqldef tmp= getByAlias(sqlDef.getAlias());
		if(tmp!=null){
			ThreadMsgUtil.addMsg("定义："+tmp.getName()+"，已存在故跳过");
			return ;
		}
		
		String sqlId=UniqueIdUtil.getSuid();
		sqlDef.setId(sqlId);
		querySqldefDao.create(sqlDef);
		//字段
		List<QueryMetafield> fieldList=def.getMetafieldList();
		for(QueryMetafield field:fieldList){
			field.setId(UniqueIdUtil.getSuid());
			field.setSqlId(sqlId);
			
			queryMetafieldManager.create(field);
		}
		//导入视图
		List<QueryView> viewList = def.getQueryViewList();
		for(QueryView view:viewList){
			view.setId(UniqueIdUtil.getSuid());
			queryViewDao.create(view);
		}
		
		ThreadMsgUtil.addMsg("定义："+sqlDef.getName()+"，成功导入!");
	}

}
