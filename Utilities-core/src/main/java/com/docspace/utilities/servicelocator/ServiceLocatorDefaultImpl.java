/**
 * 
 */
package com.docspace.utilities.servicelocator;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author karthic
 *
 */
public class ServiceLocatorDefaultImpl extends ServiceLocator {
	private static ClassPathXmlApplicationContext applicationContext;
	
	public ServiceLocatorDefaultImpl() {
		applicationContext = new ClassPathXmlApplicationContext(getApplicationContextList()); 
	}
	
	private String[] getApplicationContextList() {
		return new String[] {"classpath*:/springConfig/docspace-applicationContext.xml"};
	}
	
	@Override
	public Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}


	@Override
	public void closeContext() {
		applicationContext.close();
		applicationContext = null;
	}

}
