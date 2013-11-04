package com.cloud.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.platform.DocConstants;
import com.cloud.platform.IDao;
import com.cloud.system.model.SystemConfig;

@Service
public class SystemService {

	@Autowired
	private IDao dao;
	
	/**
	 * reset system config
	 * 
	 * @param systemConfig
	 */
	public void resetSystemConfig(SystemConfig systemConfig) {
		
		dao.saveObject(systemConfig);
		
		DocConstants.systemConfig = null;
	}
}
