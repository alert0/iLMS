package com.hotent.base.manager.impl;

import java.io.Serializable;
import java.util.List;

import com.hotent.base.api.Page;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.api.Manager;
/**
 * <pre> 
 * 描述：抽象实体业务管理类实现
 * 构建组：x5-base-db
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2013-12-20-下午8:41:38
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public abstract class AbstractManagerImpl<PK extends Serializable, T extends Serializable> implements Manager<PK, T>{
	//获取基础类

    protected abstract Dao<PK, T > getDao();
    /*
     * (non-Javadoc)
     * @see com.hotent.base.manager.api.Manager#create(java.lang.Object)
     */
    public void create(T entity) {
        getDao().create(entity);
    }
    /*
     * (non-Javadoc)
     * @see com.hotent.base.manager.api.Manager#update(java.lang.Object)
     */
    public void update(T entity) {
        getDao().update(entity);
    }
    /*
     * (non-Javadoc)
     * @see com.hotent.base.manager.api.Manager#remove(java.io.Serializable)
     */
    public void remove(PK entityId) {
        getDao().remove(entityId);
    }
   /*
    * (non-Javadoc)
    * @see com.hotent.base.manager.api.Manager#get(java.io.Serializable)
    */
    public T get(PK entityId) {
        return getDao().get(entityId);
    }
    
    @Override
    public List<T> getByIds(List<PK> entityIds, String idColumn) {
    	return getDao().getByIds(entityIds, idColumn);
    }
    
    /*
     * (non-Javadoc)
     * @see com.hotent.base.manager.api.Manager#removeByIds(PK[])
     */
    public void removeByIds(PK... ids) {
        if(ids!=null){
            for(PK pk:ids){
                remove(pk);
            }
        }
    }
    /*
     * (non-Javadoc)
     * @see com.hotent.base.manager.api.Manager#query(com.hotent.base.api.query.QueryFilter)
     */
    public List<T> query(QueryFilter queryFilter) {
        return getDao().query(queryFilter);
    }
    public T getUnique(String column, Object value) {
    	return getDao().getUniqueByColumn(column, value);
    }
    /*
     * (non-Javadoc)
     * @see com.hotent.base.manager.api.Manager#getAll()
     */
    public List<T> getAll() {
        return getDao().getAll();
    }
    /*
     * (non-Javadoc)
     * @see com.hotent.base.manager.api.Manager#getAllByPage(com.hotent.base.api.Page)
     */
    public List<T> getAllByPage(Page page) {
        return getDao().getAllByPage(page);
    }
}
