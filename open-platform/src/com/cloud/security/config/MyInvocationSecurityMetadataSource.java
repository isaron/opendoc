package com.cloud.security.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.cloud.platform.Constants;
import com.cloud.platform.IDao;
import com.cloud.platform.SpringUtil;
import com.cloud.platform.StringUtil;
import com.cloud.security.model.Resource;
import com.cloud.security.model.Role;

public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private UrlMatcher urlMatcher = new AntUrlPathMatcher();;
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    public MyInvocationSecurityMetadataSource() {
        loadResourceDefine();
    }

    /**
     * ==================== 最重要的部分 ====================
     * 定义资源的允许访问角色
     */
    private void loadResourceDefine() {
        IDao dao = SpringUtil.getDao();
    	resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

        // get url resources
        List<Resource> resources = dao.getAllByHql("from Resource where typeStr = 'URL'");
        
        // get all roles
        List<Role> roles = dao.getAllByHql("from Role");
        
        // combine resource - role map
        Map<String, List<String>> resRoleMap = new HashMap();
        
        for(Role r : roles) {
        	String resIds = r.getResourceIds();
        	
        	if(StringUtil.isNullOrEmpty(resIds)) {
        		continue;
        	}
        	
        	for(String resId : resIds.split(",")) {
        		if(!resRoleMap.containsKey(resId)) {
        			resRoleMap.put(resId, new ArrayList());
        		}
        		
        		resRoleMap.get(resId).add(r.getId());
        	}
        }
        
        // combine resource map
        for(Resource r : resources) {
        	Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
        	
        	if(resRoleMap.containsKey(r.getId())) {
        		for(String roleId : resRoleMap.get(r.getId())) {
        			ConfigAttribute ca = new SecurityConfig(roleId);
                    atts.add(ca);
        		}
        	}
        	
        	// if no role refer to, add a random role id to deny access
    		if(atts.size() == 0) {
    			ConfigAttribute ca = new SecurityConfig(Constants.getID());
                atts.add(ca);
    		}
            
            resourceMap.put(r.getResStr(), atts);
        }
    }

    // According to a URL, Find out permission configuration of this URL.
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
    	
    	// if is admin, can access all resource
    	if(Constants.isAdmin()) {
    		return null;
    	}
    	
    	if(resourceMap == null) {
    		loadResourceDefine();
    	}
    	
        // guess object is a URL.
        String url = ((FilterInvocation)object).getRequestUrl();
        Iterator<String> ite = resourceMap.keySet().iterator();
        
        while (ite.hasNext()) {
            String resURL = ite.next();
            if (urlMatcher.pathMatchesUrl(resURL, url)) {
                return resourceMap.get(resURL);
            }
        }
        
        return null;
    }
    
    /**
     * reset resource map
     */
    public static void resetResourceMap() {
    	resourceMap = null;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }
    
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
}
