package com.cloud.platform;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.cloud.platform.IDao;

public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;  
	
	/**
	 * insert spring application context where system starts
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		
		this.applicationContext = applicationContext;
	}

	/**
	 * get spring bean by bean id
	 * 
	 * @param beanId
	 * @return
	 * @throws BeansException
	 */
	public static Object getBean(String beanId) throws BeansException {
		return applicationContext.getBean(beanId);
	}
	
	/**
	 * get system dao
	 * 
	 * @return
	 */
	public static IDao getDao() {
		return (IDao) getBean("dao");
	}
}
