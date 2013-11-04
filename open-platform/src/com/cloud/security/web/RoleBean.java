package com.cloud.security.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloud.platform.Constants;
import com.cloud.security.model.Resource;
import com.cloud.security.service.RoleService;
import com.cloud.security.util.RoleResUtil;

public class RoleBean {

	@Autowired
	public RoleService roleService;
	
	/**
	 * has structure manage authority
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/hasStrutManageAuth.do")
	public String hasStrutManageAuth() {
		
		return roleService.hasOperateAuth(RoleResUtil.RES_OP_STRUCTURE_MANAGE) ? Constants.VALID_YES
				: Constants.VALID_NO;
	}
	
	/**
	 * get resources to show in role form page
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getResources.do")
	public String getResources() {
		
		// search resources
		List<Resource> resources = roleService.searchResources();
		
		// format resource data
		Map<String, List<Resource>> result = new HashMap();
		
		for(Resource r : resources) {
			if(!result.containsKey(r.getCatStr())) {
				result.put(r.getCatStr(), new ArrayList());
			}
			
			result.get(r.getCatStr()).add(r);
		}
		
		return JSONObject.fromObject(result).toString();
	}
}
