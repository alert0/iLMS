package com.hotent.bo.def;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.JAXBUtil;
import com.hotent.bo.api.bodef.BoDefService;
import com.hotent.bo.api.model.BaseBoDef;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bo.persistence.manager.BODefManager;
import com.hotent.bo.persistence.manager.BOEntManager;
import com.hotent.bo.persistence.model.BODef;
@Service
public class BoDefServiceImpl implements BoDefService {
	
	@Resource
	BODefManager bODefManager;
	@Resource
	BOEntManager boEntManager;
	
	@Override
	public BaseBoDef getByName(String name) {
		BaseBoDef def= bODefManager.getByDefName(name);
		return def;
	}

	@Override
	public BaseBoDef getByDefId(String defId) {
		BaseBoDef def= bODefManager.getByDefId(defId);
		return def;
	}

	@Override
	public String getXmlByDefId(String defId) throws JAXBException {
		BaseBoDef def= bODefManager.getByDefId(defId);
		String xml=JAXBUtil.marshall(def, BaseBoDef.class);
		return xml;
	}

	@Override
	public BaseBoDef parseXml(String xml) throws UnsupportedEncodingException, JAXBException {
		BODef def=(BODef) JAXBUtil.unmarshall(xml, BODef.class);
		return (BaseBoDef)def;
	}

	@Override
	public BaseBoEnt getEntByName(String name) {
		return boEntManager.getByName(name);
	}

	@Override
	public List<BaseBoDef> importBoDef(List<BaseBoDef> boDefs) {
		return bODefManager.importBoDef(boDefs);
	}

	@Override
	public BaseBoDef getByAlias(String boCode) {
		return bODefManager.getByAlias(boCode);
	}

}
