package com.hotent.sys.persistence.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hotent.base.api.query.FieldSort;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.impl.MyBatisDaoImpl;
 
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.sys.persistence.dao.SysDatagridFieldDao;
import com.hotent.sys.persistence.model.SysDataGridField;
import com.hotent.sys.persistence.model.SysDataGridFieldInfo;


/**
 * 
 * <pre> 
 * 描述：sys_datagrid_field DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-05-13 12:12:51
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysDatagridFieldDaoImpl extends MyBatisDaoImpl<String, SysDataGridField> implements SysDatagridFieldDao{

    @Override
    public String getNamespace() {
        return SysDataGridField.class.getName();
    }

	@Override
	public List<SysDataGridFieldInfo> queryListInfo(QueryFilter queryFilter)
	{
		
    	Map<String,Object> params=queryFilter.getParams();
    	//构建动态条件SQL
    	String dynamicWhereSql=queryFilter.getFieldLogic().getSql();
    	//默认条件过虑
    	String defaultWhere=params.containsKey("defaultWhere")?params.get("defaultWhere").toString():"";
    	if(StringUtils.isNotEmpty(defaultWhere)){
    	   dynamicWhereSql=StringUtils.isNotEmpty(dynamicWhereSql)?dynamicWhereSql+" and "+defaultWhere:defaultWhere;
    	}
    	if(StringUtils.isNotEmpty(dynamicWhereSql)){
    		params.put("whereSql", dynamicWhereSql);
    	}
    	//构建排序SQL
    	if(queryFilter.getFieldSortList().size()>0){
	    	StringBuffer sb=new StringBuffer();
			for(FieldSort fieldSort: queryFilter.getFieldSortList()){
				sb.append(fieldSort.getField()).append(" ").append(fieldSort.getDirection()).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			params.put("orderBySql", sb.toString());
    	}
    	if(queryFilter.getPage()==null){
    		return this.sqlSessionTemplate.selectList("queryListInfo", params);
    	}else{
    		DefaultPage p=new DefaultPage(new RowBounds(queryFilter.getPage().getStartIndex(), queryFilter.getPage().getPageSize()));
    		return this.sqlSessionTemplate.selectList("queryListInfo", params,p);
    	}
		
		
	}

	@Override
	public SysDataGridFieldInfo getInfo(String id)
	{
		return (SysDataGridFieldInfo)this.getOne("getInfo",id);
	}
	
}

