package com.hotent.bo.persistence.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.GsonUtil;
import com.hotent.base.core.util.JAXBUtil;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bo.api.exception.BOBaseException;
import com.hotent.bo.api.model.BaseAttribute;
import com.hotent.bo.api.model.BaseBoDef;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bo.api.model.BoDefXml;
import com.hotent.bo.persistence.dao.BODefDao;
import com.hotent.bo.persistence.dao.BOEntDao;
import com.hotent.bo.persistence.dao.BOEntRelDao;
import com.hotent.bo.persistence.dao.BoAttributeDao;
import com.hotent.bo.persistence.manager.BODefManager;
import com.hotent.bo.persistence.manager.BOEntRelManager;
import com.hotent.bo.persistence.model.BODef;
import com.hotent.bo.persistence.model.BOEnt;
import com.hotent.bo.persistence.model.BOEntRel;
import com.hotent.bo.persistence.model.BoAttribute;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre>
 * 描述：xbo_def 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-05 09:58:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("bODefManager")
public class BODefManagerImpl extends AbstractManagerImpl<String, BODef> implements BODefManager {

	protected Logger logger = Logger.getLogger(BODefManagerImpl.class);

	@Resource
	BODefDao bODefDao;
	@Resource
	BOEntRelDao bOEntRelDao;
	@Resource
	BoAttributeDao boAttributeDao;
	@Resource
	BOEntDao boEntDao;
	@Resource
	BOEntRelManager bOEntRelManager;
	@Resource
	BOEntRelManager boEntRelManager;
	

	@Override
	protected Dao<String, BODef> getDao() {
		return bODefDao;
	}

	@Override
	public BODef getByDefId(String defId) {
		BODef boDef = this.get(defId);

		buildBoDef(boDef);

		return boDef;
	}

	@Override
	public BODef getByDefName(String name) {
		BODef boDef = bODefDao.getByAlias(name);

		buildBoDef(boDef);

		return boDef;
	}

	/**
	 * 设置bo定义的属性列表和层次关系。
	 * 
	 * @param boDef
	 */
	private void buildBoDef(BODef boDef) {
		Map<String, List<BOEnt>> refMap = new HashMap<String, List<BOEnt>>();

		Map<String, BOEntRel> entMap = new HashMap<String, BOEntRel>();

		List<BOEntRel> list = bOEntRelDao.getByDefId(boDef.getId());
		for (BOEntRel ref : list) {
			entMap.put(ref.getId(), ref);
		}
		for (BOEntRel ref : list) {
			BOEnt ent = ref.getRefEnt();
			//设置实体的关系类型，一对一 ，一对多，多对多。
			ent.setType(ref.getType());
			// 设置属性。
			List attrList = boAttributeDao.getByEntId(ref.getRefEntId());
			ent.setBoAttrList(attrList);

			// 如果为主实体的情况。
			if (BaseBoDef.BOENT_RELATION.MAIN.equals(ref.getType())) {
				boDef.setBoEnt(ent);
			} else {
				String parentId = entMap.get(ref.getParentId()).getRefEnt().getId();
				if (refMap.containsKey(parentId)) {

					List<BOEnt> tempList = refMap.get(parentId);
					tempList.add(ref.getRefEnt());
				} else {
					List<BOEnt> tempList = new ArrayList<BOEnt>();
					tempList.add(ref.getRefEnt());
					refMap.put(parentId, tempList);
				}
			}

		}
		// 构建实体结构。
		buildRefEnt((BOEnt) boDef.getBoEnt(), refMap);
	}

	/**
	 * 构建实体树形结构。
	 * 
	 * @param boEnt
	 * @param refMap
	 */
	private void buildRefEnt(BOEnt boEnt, Map<String, List<BOEnt>> refMap) {
		List tempList = refMap.get(boEnt.getId());
		if (BeanUtils.isEmpty(tempList)) return;
		boEnt.setChildEntList(tempList);
		for (Object childObj : tempList) {
			BOEnt childEnt = (BOEnt) childObj;
			buildRefEnt(childEnt, refMap);
		}
	}

	@Override
	public List<BaseBoDef> parseXml(String xml) {
		try {
			BoDefXml def = (BoDefXml) JAXBUtil.unmarshall(xml, BoDefXml.class);
			return def.getDefList();
		} catch (Exception e) {
			throw new BOBaseException("解析xml成业务对象定义时出错!");
		}
	}

	@Override
	public String serialToXml(List<BaseBoDef> boDef) {
		String xml = "";
		try {
			BoDefXml defXml = new BoDefXml();
			defXml.setDefList(boDef);
			xml = JAXBUtil.marshall(defXml, BoDefXml.class);
		} catch (JAXBException e) {
			throw new BOBaseException("序列换成XML时出错!");
		}
		return xml;
	}
	
	@Override
	public List<BaseBoDef> importBoDef(List<BaseBoDef> boDefs) {
		List<BaseBoDef> rtnList=new ArrayList<BaseBoDef>();
		for (BaseBoDef boDef : boDefs) {
			BaseBoDef boBoDef=importDef(boDef);
			rtnList.add(boBoDef);
		}
		return rtnList;
	}

	/**
	 * 导入一个bodef。
	 * 
	 * @param def
	 * @param user
	 */
	private BaseBoDef importDef(BaseBoDef def) {
		String defId = UniqueIdUtil.getSuid();
		// 添加实例定义。
		BODef boDef = new BODef(def);
		boDef.setId(defId);
		IUser user = ContextUtil.getCurrentUser();
		boDef.setCreateBy(user.getUserId());
		boDef.setCreateTime(new Date());
		BODef dbDef = getByAlias(def.getAlias());// 数据库已存在的
		if (dbDef == null) {
			bODefDao.create(boDef);
			ThreadMsgUtil.addMsg("定义："+boDef.getDescription()+"，成功导入");
		} else {
			defId=dbDef.getId();
			boDef.setId(defId);
			ThreadMsgUtil.addMsg("定义："+boDef.getDescription()+"，已存在故跳过");
		}

		// 添加实体
		BaseBoEnt baseBoEnt = def.getBoEnt();
		BOEnt boEnt = new BOEnt(baseBoEnt);
		importEnt(defId, boEnt, "0");
		
		return boDef;

	}

	/**
	 * 递归导入实体。
	 * 
	 * @param defId
	 * @param ent
	 * @param parentId
	 * @param user
	 */
	void importEnt(String defId, BOEnt ent, String parentId) {
		// 添加ENT
		String entId = UniqueIdUtil.getSuid();
		ent.setId(entId);
		ent.setCreateBy(ContextUtil.getCurrentUserId());
		ent.setCreateTime(new Date());
		ent.setStatus("enabled");
		if (ent.isExternal()) {// 外部表就初始化为已生成
			ent.setIsCreateTable((short) 1);
		}

		BOEnt dbEnt = boEntDao.getByName(ent.getName());// 在数据库
		if (dbEnt == null) {
			boEntDao.create(ent);
			// 添加属性
			List<BaseAttribute> attrList = ent.getBoAttrList();
			saveAttr(entId, attrList);
			ThreadMsgUtil.addMsg("实例："+ent.getComment()+"，成功导入");
		} else {
			entId=dbEnt.getId();
			ent.setId(entId);
			ThreadMsgUtil.addMsg("实例："+ent.getComment()+"，已存在故跳过");
			
		}
		
		//开始处理def和ent的关系
		String relId = UniqueIdUtil.getSuid();
		BOEntRel dbRel = bOEntRelManager.getByDefIdAndEntId(defId, entId);
		if(dbRel==null){
			// 添加关系
			saveBoRel(relId, defId, parentId, entId, ent.getType());
		}else{
			relId=dbRel.getId();
		}
		
		// 处理子实体
		List<BaseBoEnt> childEnts = ent.getChildEntList();
		if (BeanUtils.isEmpty(childEnts))
			return;

		for (BaseBoEnt childBaseEnt : childEnts) {
			BOEnt boEnt = new BOEnt(childBaseEnt);
			// 处理子实例
			importEnt(defId, boEnt, relId);
		}
	}

	/**
	 * 保存属性。
	 * 
	 * @param entId
	 * @param attrList
	 */
	private void saveAttr(String entId, List<BaseAttribute> attrList) {
		for (BaseAttribute attribute : attrList) {
			String attrId = UniqueIdUtil.getSuid();
			BoAttribute attr = new BoAttribute(attribute);
			attr.setId(attrId);
			attr.setEntId(entId);
			boAttributeDao.create(attr);
		}
	}

	/**
	 * 添加实体关联关系。
	 * 
	 * @param defId
	 * @param parentId
	 * @param entId
	 * @param type
	 */
	private void saveBoRel(String id, String defId, String parentId, String entId, String type) {

		BOEntRel rel = new BOEntRel();
		rel.setId(id);
		rel.setBoDefid(defId);
		rel.setParentId(parentId);
		rel.setRefEntId(entId);
		rel.setType(type);

		bOEntRelDao.create(rel);
	}

	@Override
	public void save(String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		BODef boDef = JSON.parseObject(json, BODef.class);
		if (StringUtil.isEmpty(boDef.getId())) {
			boDef.setId(UniqueIdUtil.getSuid());
			bODefDao.create(boDef);
		} else {
			bODefDao.update(boDef);
			bOEntRelDao.removeByDefId(boDef.getId());
		}

		// 处理关系
		JSONArray ents = jsonObject.getJSONArray("ents");
		String mainId = UniqueIdUtil.getSuid();

		for (int i = 0; i < ents.size(); i++) {
			JSONObject entJO = ents.getJSONObject(i);
			BOEnt ent = GsonUtil.toBean(entJO.toString(), BOEnt.class);

			BOEntRel boEntRel = new BOEntRel();
			boEntRel.setBoDefid(boDef.getId());

			if (entJO.getString("relation").equals(BaseBoDef.BOENT_RELATION.MAIN)) {
				boEntRel.setParentId("0");
				boEntRel.setId(mainId);
			} else {
				boEntRel.setId(UniqueIdUtil.getSuid());
				boEntRel.setParentId(mainId);
			}
			boEntRel.setRefEntId(ent.getId());
			boEntRel.setType(entJO.getString("relation"));

			bOEntRelDao.create(boEntRel);
		}

	}

	@Override
	public BODef getByAlias(String alias) {
		BODef boDef = bODefDao.getByAlias(alias);
		return boDef;

	}

	/**
	 * bo的json
	 * **/
	@Override
	public JSONObject getBOJson(String id) {
		BODef boDef = this.getByDefId(id);
		BaseBoEnt mainBo = boDef.getBoEnt();
		JSONObject json = (JSONObject) JSONObject.toJSON(mainBo);
		json.put("boDefId", boDef.getId());
		json.put("boDefAlias", boDef.getAlias());
		json.put("path", boDef.getAlias());// 主表字段path = boDefAlias

		handelBOJSON(json);
		json.put("nodeType", "main");
		json.put("icon", "fa fa-th-large dark");
		return json;
	}

	// 将表的 字段 和子表作为 children
	private void handelBOJSON(JSONObject boJson) {
		JSONArray children = new JSONArray();
		JSONArray attrList = boJson.getJSONArray("attrs");
		for (int i = 0; i < attrList.size(); i++) {
			JSONObject attr = attrList.getJSONObject(i);
			attr.put("nodeType", "field");
			attr.put("entId", boJson.getString("id"));
			attr.put("boDefId", boJson.getString("boDefId"));
			attr.put("path", boJson.getString("path"));

			String dataType = attr.getString("dataType");
			if ("number".equals(dataType)) {
				attr.put("icon", "ico_int green");
			} else if ("datetime".equals(dataType)) {
				attr.put("icon", "ico_date green");
			} else {
				attr.put("icon", "ico_string dark");
			}
		}

		children.addAll(attrList);
		boJson.remove("attrs");
		// 处理子表
		JSONArray childEntList = boJson.getJSONArray("childEnts");
		if (BeanUtils.isNotEmpty(childEntList))
			for (int i = 0; i < childEntList.size(); i++) {
				JSONObject subTable = childEntList.getJSONObject(i);
				subTable.put("nodeType", "sub");
				subTable.put("icon", "ico_complex blue");
				subTable.put("path", boJson.getString("path") + ".sub_" + subTable.getString("name")); // 子表path
																										// boDefAlias.sub_tableName;
				handelBOJSON(subTable);
			}
		attrList.addAll(childEntList);
		boJson.remove("childEnts");
		boJson.put("children", attrList);
	}

	/**
	 * 先删除boentrel 再删除自身。
	 */
	@Override
	public void remove(String id) {
		//判断是否被表单元数据引用过。
		
		
		List<BOEntRel> list=bOEntRelDao.getByDefId(id);
		for (BOEntRel rel : list) {
			bOEntRelDao.remove(rel.getId());
		}
		super.remove(id);
	}
	
	

	@Override
	public boolean isEditable(String id) {
		return bODefDao.isEditable(id);
	}

	/**
	 * 接下来要做一个很繁琐的检查能不能更新这个实例的过程,要判断当前实例是不是只被当前定义调用 
	 * 
	 * @param entId
	 * @param defId
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	private boolean checkEnt(String entId, String defId) {
		for (BOEntRel rel : boEntRelManager.getByEntId(entId)) {
			BODef boDef = this.get(rel.getBoDefid());
			if (!boDef.getId().equals(defId)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<BODef> getByFormKey(String formKey) {
		return bODefDao.getByFormKey(formKey);
	}
}
