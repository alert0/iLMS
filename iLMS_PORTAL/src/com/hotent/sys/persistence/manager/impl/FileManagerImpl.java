package com.hotent.sys.persistence.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.FileDao;
import com.hotent.sys.persistence.manager.FileManager;
import com.hotent.sys.persistence.model.DefaultFile;
 

@Service("fileManager")
public class FileManagerImpl extends AbstractManagerImpl<String, DefaultFile> implements FileManager{
	@Resource
	FileDao fileDao;
	@Override
	protected Dao<String, DefaultFile> getDao() {
		return fileDao;
	}
	
	/**
	 * 根据允许的文档格式对应的附件
	 */
	@Override
	public List<DefaultFile> getAllByExt(String[] allowFiles) {
		// TODO Auto-generated method stub
		return fileDao.getAllByExt(allowFiles);
	}

	@Override
	public String getFileSuffixInParams(Map<String, String> map) {
		
		return fileDao.getFileSuffixInParams(map);
	}
}
