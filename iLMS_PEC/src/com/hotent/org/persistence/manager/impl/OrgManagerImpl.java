package com.hotent.org.persistence.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.persistence.dao.OrgDao;
import com.hotent.org.persistence.dao.OrgRelDao;
import com.hotent.org.persistence.dao.OrgUserDao;
import com.hotent.org.persistence.dao.SysOrgParamsDao;
import com.hotent.org.persistence.dao.UserDao;
import com.hotent.org.persistence.dao.UserUnderDao;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.model.Org;
import com.hotent.org.persistence.model.User;

/**
 * 
 * <pre> 
 * 描述：组织架构 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-28 15:13:03
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("orgManager")
public class OrgManagerImpl extends AbstractManagerImpl<String, Org> implements OrgManager{
	@Resource
	OrgDao orgDao;
	@Resource
	UserDao userDao;
	@Resource 
	SysOrgParamsDao sysOrgParamsDao;
	@Resource
	OrgRelDao orgrelDao;
	@Resource
	OrgUserDao orgUserDao;
	@Resource
	UserUnderDao userUnderDao;
	 //子节点  
    static  List<Org> childOrg=new ArrayList<Org>(); 
	
	@Override
	protected Dao<String, Org> getDao() {
		return orgDao;
	}
 
	public Org getByCode(String code) {
		return orgDao.getByCode(code);
	}

	public List<Org> getOrgListByUserId(String userId) {
		return orgDao.getOrgListByUserId(userId);
	}

	public List<Org> getOrgListByAccount(String account) {
		User user= userDao.getByAccount(account);
		return orgDao.getOrgListByUserId(user.getId());
	}

	@Override
	public Org getMainGroup(String userId) {
		List<Org> list=orgDao.getOrgListByUserId(userId);
		if(BeanUtils.isEmpty(list)) return null;
		if(list.size()==1) return list.get(0);
		for(Org org:list){
			if(org.getIsMaster()==1) return org;
		}
		return list.get(0);
	}
	
	@Override
	public void removeByIds(String... ids) {
		super.removeByIds(ids);
		//删除组织参数
		sysOrgParamsDao.removeByOrgIds(ids);
		//删除组织岗位
		orgrelDao.removeByOrgIds(ids);
		//删除组织用户
		orgUserDao.removeByOrgIds(ids);
		//删除下属
		userUnderDao.removeByOrgIds(ids);
		
		//查找子节点
		List<Org> allList = orgDao.getAll();
		for(String id:ids){
			treeOrgList(allList,id);
		}
		//删除子节点
		String[] childIds = new String[childOrg.size()];
		for(int i=0;i<childOrg.size();i++){
			String id = childOrg.get(i).getId();
			childIds[i] = id;
			orgDao.remove(id);
		}
		
		//子组织中相关数据的删除
		sysOrgParamsDao.removeByOrgIds(childIds);
		orgrelDao.removeByOrgIds(childIds);
		orgUserDao.removeByOrgIds(childIds);
		userUnderDao.removeByOrgIds(childIds);
	}
	
	@Override
	public void updateByOrg(Org org){
		String pathName = org.getPathName();
		org.setPathName(pathName.substring(0, pathName.lastIndexOf("/")+1)+org.getName());
		orgDao.update(org);
		
		String[] ids = {org.getId()};
		
		// 查找子节点
		List<Org> allList = orgDao.getAll();
		for (String id : ids) {
			treeOrgList(allList, id);
		}
		// 更新子节点
		String[] childIds = new String[childOrg.size()];
		for (int i = 0; i < childOrg.size(); i++) {
			String id = childOrg.get(i).getId();
			childIds[i] = id;
			Org c = orgDao.get(id);
			c.setPathName(org.getPathName()+"/"+c.getName());
			orgDao.update(c);
		}
		
	}
	
	
	/** 
     * 获取某个父节点下面的所有子节点 
     * @param orgList 
     * @param pid 
     * @return 
     */  
    public static List<Org> treeOrgList( List<Org> orgList, String parentId){  
        for(Org org: orgList){  
            //遍历出父id等于参数的id，add进子节点集合  
            if(parentId.equals(org.getParentId())){  
                //递归遍历下一级  
                treeOrgList(orgList,org.getId());  
                childOrg.add(org);  
            }  
        }  
        return childOrg;  
    }

	@Override
	public List<Org> getByParentId(String parentId) {
		List<Org> rtnList = new ArrayList<Org>();
		List<Org> allList = orgDao.getAll();
		return getChild(allList,parentId,rtnList);
	}  
	
	/** 
     * 获取某个父节点下面的所有子节点 
     * @param orgList 
     * @param pid 
     * @return 
     */  
    public static List<Org> getChild( List<Org> orgList, String parentId,List<Org> rtnList){  
        for(Org org: orgList){  
            //遍历出父id等于参数的id，add进子节点集合  
            if(parentId.equals(org.getParentId())){  
                //递归遍历下一级  
            	getChild(orgList,org.getId(),rtnList);  
                rtnList.add(org);  
            }  
        }  
        return rtnList;  
    }
    
    public List<Org> getByOrgName(String orgName){
    	return orgDao.getByOrgName(orgName);
    }

	@Override
	public List<Org> getByPathName(String pathName) {
		return orgDao.getByPathName(pathName);
	}

	@Override
	public List<Org> getByParentAndDem(QueryFilter queryFilter) {
		return orgDao.getByParentAndDem(queryFilter);
	}

	@Override
	public Org getByDemIdAndSonId(String demId, String sonId) {
		return orgDao.getByDemIdAndSonId(demId,sonId);
	}

	@Override
	public Org getOrgIdUserId(String id) {
		return orgDao.getOrgIdUserId(id);
	}
}
