/**
 * 
 */
package com.docspace.utilities.servicelocator;

/**
 * @author karthic
 *
 */
public abstract class ServiceLocator {
	private static ServiceLocator defaultServiceLocator;
	
	public static ServiceLocator getInstance() {
		if(defaultServiceLocator == null) {
			defaultServiceLocator = getServiceLocatorImplInstance("com.docspace.utilities.servicelocator.ServiceLocatorDefaultImpl");
		}
		return defaultServiceLocator;
	}
	
	public static void clearInstance() {
		if(defaultServiceLocator != null) {
			defaultServiceLocator.closeContext();
			defaultServiceLocator = null;
		}
	}
	
	private static ServiceLocator getServiceLocatorImplInstance(String defaultServiceLocator) {
		ServiceLocator serviceLocator = null;
	    String serviceLocatorImpl = System.getProperty("com.med.utilities.core.serviceLocator.ServiceLocator");
	    if ((serviceLocatorImpl == null) || (serviceLocatorImpl.length() == 0)) {
	      serviceLocatorImpl = defaultServiceLocator;
	    }
	    try
	    {
	      serviceLocator = (ServiceLocator)Class.forName(serviceLocatorImpl).newInstance();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      //throw new NestableRuntimeException("Error creating ServiceLocator instance. System property 'com.med.utilities.core.serviceLocator.ServiceLocator' currently set to '" + serviceLocatorImpl + "'", e);
	    }
	    return serviceLocator;
	}
	
	public abstract Object getBean(String beanName);
	
	public abstract void closeContext();
	
}
