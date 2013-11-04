package com.cloud.security.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.cloud.platform.Constants;
import com.cloud.platform.StringUtil;

public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	
	public void init(FilterConfig arg0) throws ServletException {}
	
	public void destroy() {}
	 
    /**
     * Method that is actually called by the filter chain. Simply delegates to
     * the {@link #invoke(FilterInvocation)} method.
     * 
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
    	
    	// set constants basepath
    	if(StringUtil.isNullOrEmpty(Constants.BASEPATH)) {
    		Constants.BASEPATH = ((HttpServletRequest) request).getContextPath();
    		
    		if(!"/".equals(Constants.BASEPATH)) {
    			Constants.BASEPATH += "/";
    		}
    	}
    	
    	// set basepath request attribute for default page
    	request.setAttribute("BASEPATH", Constants.BASEPATH);
    	
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {
    	
        InterceptorStatusToken token = super.beforeInvocation(fi);
        
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }
    
    public Class<? extends Object> getSecureObjectClass() {
        return FilterInvocation.class;
    }
    
    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public void setSecurityMetadataSource(
            FilterInvocationSecurityMetadataSource newSource) {
        this.securityMetadataSource = newSource;
    }
}
