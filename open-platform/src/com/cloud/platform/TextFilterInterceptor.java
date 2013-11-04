package com.cloud.platform;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TextFilterInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		if(handler == null || !(handler instanceof HandlerMethod)) {
			return true;
		}
		
		// only deal with filter text when enter save method
		Method m = ((HandlerMethod) handler).getMethod();
		
		if(!m.getName().startsWith("save")) {
			return true;
		}
		
		// get params from front page
		Map paramsMap = request.getParameterMap();
		Object obj = null;
		
		// iterate param
		for (Iterator<Map.Entry> it = paramsMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = it.next();
			Object[] values = (Object[]) entry.getValue();
			
			for (int i = 0; i < values.length; i++) {
				
				obj = values[i];
				
				// if is text, process filter
				if(obj instanceof String && !StringUtil.isNullOrEmpty((String) obj)) {
					values[i] = String.valueOf(obj).replaceAll("<", "&lt;").replaceAll(">", "&gt;");
					
					System.out.println(values);
				}
			}
		}
		
		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		super.postHandle(request, response, handler, modelAndView);
	}
}
