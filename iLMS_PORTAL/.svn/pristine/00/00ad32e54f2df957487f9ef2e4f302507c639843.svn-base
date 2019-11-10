package com.hotent.sys.persistence.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.FileDao;
import com.hotent.sys.persistence.model.DefaultFile;


@Repository
public class FileDaoImpl extends MyBatisDaoImpl<String, DefaultFile> implements FileDao{

    @Override
    public String getNamespace() {
        return DefaultFile.class.getName();
    }

	@Override
	public List<DefaultFile> getAllByExt(String[] allowFiles) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("allowFiles", allowFiles);
		return this.getByKey("getAllByExt", params);
	}

	@Override
	public String getFileSuffixInParams(Map<String, String> map) {
		
		return this.sqlSessionTemplate.selectOne("getFileSuffixInParams", map);
	}
	
}

