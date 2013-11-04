package com.cloud.system.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloud.platform.DocConstants;
import com.cloud.system.model.SystemConfig;
import com.cloud.system.service.SystemService;

@Controller
@RequestMapping("system")
public class SystemBean {

	@Autowired
	private SystemService systemService;
	
	/**
	 * get system attach config
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAttachConfig.do")
	public String getAttachConfig() {
		
		return String.valueOf(DocConstants.getSystemConfig().getAttachMaxSize());
	}
	
	/**
	 * save system attach config
	 * 
	 * @param size
	 */
	@ResponseBody
	@RequestMapping("/saveAttachConfig.do")
	public void saveAttachConfig(@RequestParam("size") int size) {
		
		SystemConfig systemConfig = DocConstants.getSystemConfig();
		
		systemConfig.setAttachMaxSize(size);
		
		systemService.resetSystemConfig(systemConfig);
	}
	
	/**
	 * get system name
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSystemName.do")
	public String getSystemName() {
		
		return DocConstants.getSystemConfig().getSystemName();
	}
	
	/**
	 * modify system name
	 * 
	 * @param systemName
	 */
	@ResponseBody
	@RequestMapping("/modifySystemName.do")
	public void modifySystemName(@RequestParam("systemName") String systemName) {
		
		SystemConfig systemConfig = DocConstants.getSystemConfig();
		
		systemConfig.setSystemName(systemName);
		
		systemService.resetSystemConfig(systemConfig);
	}
}
